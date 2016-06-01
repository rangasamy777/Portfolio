package org.envisiontechllc.supertutor.network.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 26/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class AddFollower extends AsyncTask<User, Void, Integer> {

    private ProgressDialog dialog;
    private Context ctx;
    private String followerName;

    public AddFollower(Context ctx, String followerName){
        this.ctx = ctx;
        this.followerName = followerName;
    }

    @Override
    public void onPreExecute(){
        dialog = new ProgressDialog(ctx);
        dialog.setTitle("Searching for user");
        dialog.setMessage("Please wait...");
        dialog.show();
    }

    @Override
    protected Integer doInBackground(User... params) {

        try {
            String tempUrl = String.format(Network.API_URL, "followers/addFollower/" + Utilities.encodeParam(params[0].getUsername()) + "/" + followerName);
            URL url = new URL(tempUrl);

            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null){
                String pageData = Network.getPageData(conn.getInputStream());
                if(pageData != null){
                    int responseCode = Network.getResponseCode(pageData);
                    return responseCode;
                }
            }
        }catch(IOException ex){}

        return -1;
    }

    @Override
    public void onPostExecute(Integer response){
        String responseMsg = "Error! Try again later.";

        switch(response){
            case 200:
                responseMsg = "Successfully added user.";
                break;
            case 202:
                responseMsg = "User does not exist, make sure you have typed the username correctly.";
                break;
            case 203:
                responseMsg = "This users profile is set to private.";
                break;
            case -1:
                responseMsg = "Error connecting to server, try again later.";
                break;
        }

        dialog.dismiss();
        Utilities.createDialog(ctx, "Add user", responseMsg, null).show();
    }
}
