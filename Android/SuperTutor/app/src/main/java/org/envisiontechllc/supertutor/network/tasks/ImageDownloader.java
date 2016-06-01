package org.envisiontechllc.supertutor.network.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.envisiontechllc.supertutor.network.Network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ImageDownloader extends AsyncTask<String, Void, File> {

    private Context ctx;
    private ImageView imageView;

    public ImageDownloader(Context ctx, ImageView imageView){
        this.ctx = ctx;
        this.imageView = imageView;
    }

    @Override
    protected File doInBackground(String... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "boards/getImage/" + Uri.encode(params[0]));

            URL url = new URL(tempUrl);
            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null && conn.getResponseCode() == Network.RESPONSE_OK){

                InputStream is = new BufferedInputStream(conn.getInputStream());
                File tempFile = File.createTempFile("image", ".png");
                OutputStream out = new FileOutputStream(tempFile);

                byte data[] = new byte[1024];
                long total = 0;
                int count;

                while ((count = is.read(data)) != -1) {
                    if (isCancelled()) {
                        return null;
                    }
                    out.write(data, 0, count);
                }

                is.close();
                out.flush();
                out.close();
                return tempFile;
            }
        }catch(IOException ex){}

        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        if(file != null){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
