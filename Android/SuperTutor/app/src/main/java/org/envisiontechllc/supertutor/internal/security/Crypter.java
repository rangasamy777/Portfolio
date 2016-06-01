package org.envisiontechllc.supertutor.internal.security;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by EmileBronkhorst on 18/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Crypter {

    public static String encryptData(String data){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = data.getBytes();
            md.reset();
            byte[] digestedValues = md.digest(passBytes);
            StringBuilder buffer = new StringBuilder();

            for(byte b: digestedValues){
                buffer.append(Integer.toHexString(0xff & b));
            }

            return buffer.toString();
        }catch(NoSuchAlgorithmException ex){}
        return null;
    }

}
