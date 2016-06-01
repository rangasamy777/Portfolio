package org.supertutor.internal.network.utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class JSONReader {

    private static JSONParser parser = new JSONParser();

    public static int getResponseCode(String data){
        int code = 201;

        try {
            JSONObject object = (JSONObject)parser.parse(data);
            if(object != null){
                long responseCode = (long)object.get("code");
                if(responseCode != -1){
                    code = (int)responseCode;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return code;
    }
}
