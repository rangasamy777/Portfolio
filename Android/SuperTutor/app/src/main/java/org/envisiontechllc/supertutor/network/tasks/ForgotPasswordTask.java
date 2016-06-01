package org.envisiontechllc.supertutor.network.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.Network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ForgotPasswordTask extends AsyncTask<String, Void, Integer> {

    private Context ctx;
    private ProgressDialog dialog;

    public ForgotPasswordTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onPreExecute(){
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Requesting");
        dialog.setMessage("Please wait...");

        dialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "users/forgotPassword/" + Utilities.encodeParam(params[0]));
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
    public void onPostExecute(Integer result){
        String resultMsg = "";

        switch(result){
            case -1:
                resultMsg = "There was an error connecting to the server, try again later.";
                break;
            case 200:
                resultMsg = "Email successfully sent!";
                break;
            case 201:
                resultMsg = "Ooops! Something went wrong...";
                break;
        }

        dialog.dismiss();
        Utilities.createDialog(ctx, "Response", resultMsg, null).show();
    }
}
