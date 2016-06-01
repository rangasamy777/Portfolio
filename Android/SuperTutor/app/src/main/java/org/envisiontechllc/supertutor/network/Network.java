package org.envisiontechllc.supertutor.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 22/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Network {

    public static final int RESPONSE_OK = 200, RESPONSE_ERROR = 201;
    public static final String NETWORK_URL = "http://localhost:8080/%s";
    public static final String API_URL = "http://localhost:8080/api/%s";
    public static final String GOOGLE_PROJECT_ID = "662459186239";

    public static HttpURLConnection getConnection(URL url){
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){
                return conn;
            }
        }catch(IOException ex){}
        return null;
    }

    public static String getPageData(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();

        String input = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while((input = br.readLine()) != null){
            sb.append(input);
        }

        return sb.toString();
    }

    public static int getResponseCode(String data){

        try {
            JSONObject response = new JSONObject(data);
            if(response != null){
                int code = response.getInt("code");
                return code;
            }
        }catch(JSONException | NumberFormatException  ex){}

        return RESPONSE_ERROR;
    }
}
