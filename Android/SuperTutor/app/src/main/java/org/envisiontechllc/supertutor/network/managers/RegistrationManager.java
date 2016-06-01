package org.envisiontechllc.supertutor.network.managers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.envisiontechllc.supertutor.SuperTutor;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;
import org.envisiontechllc.supertutor.settings.AppContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 04/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class RegistrationManager extends AsyncTask<Void, Void, Integer> {

    private Context ctx;
    private String username, pass, email, status, dob;
    private ProgressDialog dialog;

    public RegistrationManager(Context ctx, String username, String pass, String email, String status, String dob){
        this.ctx = ctx;
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.status = status;
        this.dob = dob;
    }

    @Override
    public void onPreExecute(){
        dialog = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Registering...");
        dialog.setMessage("Attempting to register...");
        dialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            dob = Uri.encode(dob);
            username = Uri.encode(username);
            status = Uri.encode(status);

            String urlParams = "" + username + "/" + pass + "/" + email + "/" + status + "/" + dob;

            String tempUrl = String.format(Network.NETWORK_URL, "users/register/" + urlParams);
            URL url = new URL(tempUrl);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn.getResponseCode() == Network.RESPONSE_OK){
                String data = Network.getPageData(conn.getInputStream());
                JSONObject ob = new JSONObject(data);
                if(ob != null){
                    int response = ob.getInt("code");
                    if(response != -1){
                        return response;
                    }
                }
            }
        }catch(IOException | JSONException ex){}

        return 201;
    }

    @Override
    public void onPostExecute(Integer response){
        dialog.dismiss();

        switch(response){
            case 200:
                Utilities.showToast(ctx, "Successfully registered, welcome " + username, 1);
                createUser();
                break;
            case 201:
                Utilities.showToast(ctx, "There was an error with your registration, please try again later.", 1);
                break;
            case 202:
                Utilities.showToast(ctx, "Username already exists.", 1);
                break;
        }
    }

    private void createUser(){
        User user = new User(username, pass, email, "None set");
        AppContext.getContext().setCurrentUser(user);

        Intent intent = new Intent(ctx, SuperTutor.class);
        ctx.startActivity(intent);
    }
}
