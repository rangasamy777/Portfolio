package security;

import utils.Database;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2014 Etheon Solutions
 */

public class Crypter {

    private static boolean connectionFailed = false;
    private static Database db;

    public static boolean decrypt(String userID, String pass) {
        db = Database.getInstance();

        String query  = "SELECT Password FROM Login WHERE UserID='" + userID + "'";
        //System.out.println("UserID: " + userID);
        //System.out.println("PassDecrypt: " + pass);

        if(db.executeQuery(query) != null){
            try{
                while(db.getResults().next()){
                    //System.out.println("Hash: " + MD5(pass));
                    //System.out.println("Result: " + db.getResults().getString(1));
                    if(db.getResults().getString(1).equalsIgnoreCase(MD5(pass))){
                        System.out.println("Password found");
                        return true;
                    }
                }
            } catch(SQLException ex){

            }
        }
        JOptionPane.showMessageDialog(new JOptionPane(), "Incorrect password, please try again", "SPM Error", JOptionPane.ERROR_MESSAGE);

        return false;
    }

    public String readString(HttpURLConnection conn){
    	String input = "";
    	
    	try{
    		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	input = br.readLine();
        	br.close();
    	} catch(IOException ex){ ex.printStackTrace(); }
    	
    	return input;   	
    }
    
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void main(String a[]){
        System.out.println(MD5("paladin"));
    }
}
