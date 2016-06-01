package org.envisiontechllc.supertutor.network.tasks;

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
public class Privatizer extends AsyncTask<User, Void, Integer> {

    @Override
    protected Integer doInBackground(User... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "users/makePrivate/" + Utilities.encodeParam(params[0].getUsername())+ "/" + String.valueOf(params[0].isPrivate()));
            URL url = new URL(tempUrl);

            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null){
                String pageData = Network.getPageData(conn.getInputStream());
                if(pageData != null){
                    int code = Network.getResponseCode(pageData);
                    return code;
                }
            }
        }catch(IOException ex){}

        return -1;
    }
}
