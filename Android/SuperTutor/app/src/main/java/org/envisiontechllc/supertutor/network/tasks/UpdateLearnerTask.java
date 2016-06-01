package org.envisiontechllc.supertutor.network.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class UpdateLearnerTask extends AsyncTask<Void, Void, Integer> {

    private User currentUser;

    public void onPreExecute(){
        this.currentUser = AppContext.getContext().getCurrentUser();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            String username = Utilities.encodeParam(currentUser.getUsername()), learnerType = Utilities.encodeParam(currentUser.getLearnerType());

            String tempUrl = String.format(Network.NETWORK_URL, "users/updatePersonality/" + username + "/" + learnerType);
            URL url = new URL(tempUrl);

            HttpURLConnection conn = Network.getConnection(url);
            if (conn != null) {
                String pageData = Network.getPageData(conn.getInputStream());

                int responseCode = Network.getResponseCode(pageData);
                Log.d("RESPONSE", "" + responseCode);
                return responseCode;
            }
        }catch(Exception ex){}

        return -1;
    }
}
