package org.envisiontechllc.supertutor.network.managers;

import android.content.Context;
import android.os.AsyncTask;

import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ProfileManager extends AsyncTask<User,Void,Integer> {

    private Context ctx;

    public ProfileManager(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected Integer doInBackground(User... params) {
        try{

            String username = Utilities.encodeParam(params[0].getUsername()), status = Utilities.encodeParam(params[0].getProfileStatus());
            String imageData= Utilities.encodeParam(params[0].getImageBytes());

            String tempurl = String.format(Network.NETWORK_URL, "users/updateProfile/" + username + "/" + status);
            URL url = new URL(tempurl);

            HttpURLConnection conn = Network.getConnection(url);
            if (conn != null){
                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                if(writer != null){
                    writer.write("imageData=" + imageData);
                    writer.flush();
                }

                String pageData= Network.getPageData(conn.getInputStream());
                if (pageData != null){
                    JSONObject responseObject = new JSONObject(pageData);
                    if (responseObject != null) {
                        int responseCode = responseObject.getInt("code");
                        return responseCode;
                    }
                }
            }

        }catch (IOException | JSONException ex){}
        return -1;
    }

    @Override
    public void onPostExecute(Integer response){

    }
}
