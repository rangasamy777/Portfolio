package org.envisiontechllc.supertutor.network.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 30/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ChangePasswordTask extends AsyncTask<User, Void, Integer> {

    private Context ctx;
    private ProgressDialog dialog;
    private String username, password;

    public ChangePasswordTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onPreExecute(){
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Changing password");
        dialog.setMessage("Sending request, please wait...");
        dialog.show();
    }

    @Override
    protected Integer doInBackground(User... params) {

        try {
            this.username = params[0].getUsername();
            this.password = params[0].getPassword();

            String username = Utilities.encodeParam(params[0].getUsername());
            String password = Utilities.encodeParam(params[0].getPassword());

            String tempUrl = String.format(Network.NETWORK_URL, "users/update/" + username + "/" + params[0].getEmail() + "/" + password);
            URL url = new URL(tempUrl);


            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null){
                String pageData = Network.getPageData(conn.getInputStream());
                if(pageData != null){
                    int responseCode = Network.getResponseCode(pageData);
                    return responseCode;
                }
            }
        }catch(Exception ex){}

        return -1;
    }

    @Override
    public void onPostExecute(Integer response){
        String title = "";
        String msg = "";

        switch(response){
            case 201:
                title = "Failed";
                msg = "There was an issue with your request.";
                break;
            case 200:
                title = "Success";
                msg = "Successfully changed password!";
                new SQLManager(ctx).updateUserPassword(this.username, this.password);
                break;
            case -1:
                title = "Failed";
                msg = "Error connecting to server, make sure you have an active internet connection.";
                break;
        }

        dialog.dismiss();
        Utilities.createDialog(ctx, title, msg, null).show();
    }
}
