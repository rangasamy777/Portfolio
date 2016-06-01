package org.envisiontechllc.supertutor.network.managers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.Network;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class GCMRegistration extends AsyncTask<Void, Void, String> {

    private Context ctx;
    private SharedPreferences prefs;
    private GoogleCloudMessaging gcmObject;
    private String regID;

    public GCMRegistration(Context ctx){
        this.ctx = ctx;
        this.prefs = ctx.getSharedPreferences(ctx.getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
    }

    @Override
    public void onPreExecute(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
        if(resultCode != ConnectionResult.SUCCESS){
            Utilities.createDialog(ctx, "Error registering notifications", "There was an error registering your device for push notifications. You will not receive any notifications.", null).show();
            cancel(true);
        }
        if(prefs.contains("deviceID")){
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            if(gcmObject == null){
                gcmObject = GoogleCloudMessaging.getInstance(ctx);
            }
            regID = gcmObject.register(Network.GOOGLE_PROJECT_ID);
        }catch(Exception ex){}

        return regID;
    }

    @Override
    public void onPostExecute(String result){
        Log.d("REG-ID", "" + result);
        if(prefs != null){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("deviceID", regID);
            editor.commit();

            registerWebServer(result);
        }
    }

    private void registerWebServer(final String regID){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    String tempUrl = String.format(Network.NETWORK_URL, "notifier/register/" + Utilities.encodeParam(regID));
                    URL url = new URL(tempUrl);

                    HttpURLConnection conn = Network.getConnection(url);
                    if(conn != null){
                        String pageData = Network.getPageData(conn.getInputStream());
                        int responseCode = Network.getResponseCode(pageData);

                        Log.d("WEB SERVER", "" + responseCode);
                    }
                }catch(Exception ex){}

                return null;
            }
        }.execute();
    }
}
