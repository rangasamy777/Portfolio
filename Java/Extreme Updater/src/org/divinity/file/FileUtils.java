package org.divinity.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class FileUtils {

    private static File workingDir = new File(System.getProperty("user.home") + "/Desktop/OSRS Updater");
    private static File jarFile = new File(workingDir + "/client.jar");
    private static FileOutputStream outputStream;
    private static InputStream inputStream;
    private static String jarUrl = "http://oldschool58.runescape.com/gamepack.jar";
    private static int bytes;

    public static void downloadRSClient() throws IOException{
        System.out.println("Downloading RS Client please wait...");

        URL url = new URL(jarUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        inputStream = conn.getInputStream();
        outputStream = new FileOutputStream(jarFile);

        while((bytes = inputStream.read()) != -1){
            outputStream.write(bytes);
        }

        inputStream.close();
        outputStream.close();

        System.out.println("Download completed. Running Updater!");
    }

    public static void checkDirs() throws IOException {
        if(!workingDir.exists()){
            if(workingDir.mkdirs()){
                downloadRSClient();
            }
        } else {
            if(!jarFile.exists()){
                downloadRSClient();
            }
        }
    }

    public static File getJarFile(){
        return jarFile;
    }
}
