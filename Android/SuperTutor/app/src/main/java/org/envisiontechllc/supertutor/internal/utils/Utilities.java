package org.envisiontechllc.supertutor.internal.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by EmileBronkhorst on 04/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Utilities {

    public static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static void showToast(Context ctx, String msg, int type){
        Toast.makeText(ctx, msg, (type == 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)).show();
    }

    public static AlertDialog createDialog(Context ctx, String title, String message, Dialog.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title).setMessage(message).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(listener != null){
            builder.setPositiveButton("Got it", listener);
        }

        return builder.create();
    }

    public static String formatFileSize(long size) {
        if(size <= 0) {
            return "0";
        }
        final String[] units = new String[] { "Bytes", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static boolean checkConnectivity(Context ctx){
        ConnectivityManager manager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null && info.isConnectedOrConnecting()){
                return true;
            }
        }
        return false;
    }

    public static String stripFilename(String name){
        return name.substring(0, name.indexOf("."));
    }

    public static File getFileSystem(){
        return new File(Environment.getExternalStorageDirectory() + "/SuperTutor");
    }

    public static File getFileForName(String subjectName, String name){
        return new File(getFileSystem() + "/" + subjectName + "/" + name);
    }

    public static String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM : HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String encodeParam(String param){
        try {
            return Uri.encode(param);
        }catch(Exception ex){}
        return param;
    }

    public static String encodeImage(Bitmap image){
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bOut);
            byte[] byteArray = bOut.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }catch(Exception ex){}
        return "";
    }

    public static Bitmap decodeImageFromStream(String imageData){
        try {
            byte[] bytes = Base64.decode(imageData.getBytes(), Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if(image != null){
                return image;
            }
        }catch(Exception ex){}
        return null;
    }

    public static boolean checkMatch(String one, String two){
        return one != null && one.length() > 3 && two != null && two.length() > 3 && one.equals(two);
    }
}
