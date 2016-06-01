package org.supertutor.internal.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class Crypter {

    public static String encryptPass(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = password.getBytes();
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

    public static void main(String a[]){
        System.out.println(Crypter.encryptPass("admin"));
    }
}
