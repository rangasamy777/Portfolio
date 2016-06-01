package org.envisiontechllc.supertutor.network.managers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import org.envisiontechllc.supertutor.SuperTutor;
import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.security.Crypter;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;
import org.envisiontechllc.supertutor.settings.AppContext;
import org.envisiontechllc.supertutor.subactivities.LoginScreen;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 22/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class LoginManager extends AsyncTask<Void, Void, User> {

    private Activity ctx;
    private String username, password;
    private ProgressDialog dialog;
    private boolean storeData;

    public LoginManager(Activity ctx, String username, String password, boolean storeData){
        this.ctx = ctx;
        this.username = username.toString();
        this.password = password.toString();
        this.storeData = storeData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Logging in");
        dialog.setMessage("Connecting to server...");
        dialog.show();
    }

    @Override
    protected User doInBackground(Void... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "users/login/" + username + "/" + Crypter.encryptData(password));
            URL serverUrl = new URL(tempUrl);
            HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();

            if(conn.getResponseCode() == Network.RESPONSE_OK){
                String data = Network.getPageData(conn.getInputStream());
                JSONObject ob = new JSONObject(data);

                if(ob != null){
                    String tUsername = ob.getString("username");
                    String tPassword = ob.getString("password");
                    String tStyle = ob.getString("learnerType");
                    String tEmail = ob.getString("email");
                    String tStatus = ob.getString("profileStatus");
                    String imageBytes = ob.getString("imageBytes");
                    boolean isPrivate = ob.getBoolean("private");

                    User tUser = new User(tUsername, tPassword, tEmail, tStyle);
                    tUser.setStatus(tStatus);
                    tUser.setImageBytes(imageBytes);
                    tUser.setPrivate(isPrivate);

                    return tUser;
                }
            }

        }catch(IOException ex){} catch (JSONException e) {}

        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        if(user != null){
            AppContext.getContext().setCurrentUser(user);

            if(storeData){
                storeOfflineData(user);
            }

            Intent dashboard =  new Intent(ctx, SuperTutor.class);
            ctx.startActivity(dashboard);
            ctx.finish();

            dialog.dismiss();
        } else {
            AlertDialog alertDialog = Utilities.createDialog(ctx, "Unable to log in.", "You're username and password are incorrect, try again.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    if(!ctx.getLocalClassName().contains("LoginScreen")){
                        Intent loginIntent = new Intent(ctx, LoginScreen.class);
                        ctx.startActivity(loginIntent);
                        ctx.finish();
                    }
                }
            });
            dialog.dismiss();
            alertDialog.show();
        }
    }

    private void storeOfflineData(User user){
        if(user != null){
            SQLManager manager = new SQLManager(ctx);
            manager.setUser(user.getUsername(), password, user.getEmail(), user.getLearnerType(), user.getProfileStatus(), user.getImageBytes());
        }
    }

}
