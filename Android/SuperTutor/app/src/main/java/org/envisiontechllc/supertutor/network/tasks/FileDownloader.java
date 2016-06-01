package org.envisiontechllc.supertutor.network.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.library.AppLibrary;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.Topic;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 20/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class FileDownloader extends AsyncTask<User, String, Void> {

    private Context ctx;
    private SQLManager manager;
    private ProgressDialog dialog;
    private Subject subject;
    private File storageFile;

    public FileDownloader(Context ctx, Subject subject){
        this.ctx = ctx;
        this.subject = subject;
        this.manager = new SQLManager(ctx);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        storageFile = new File(Utilities.getFileSystem() + "/" + subject.getSubjectName());
        if(!storageFile.exists()){
            storageFile.mkdir();
        }
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("Downloading...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(User... params) {

        try {

            for(Topic topic: subject.getTopics()){
                String filename = Utilities.stripFilename(topic.getFileName());
                String subjectName = Utilities.encodeParam(subject.getSubjectName());
                String username = Utilities.encodeParam(params[0].getUsername());

                String tempUrl = String.format(Network.NETWORK_URL, "files/download/" + username + "/" + subjectName + "/" + Utilities.encodeParam(filename));

                Log.d("URL", tempUrl);

                URL url = new URL(tempUrl);
                HttpURLConnection conn = Network.getConnection(url);
                if(conn != null && conn.getResponseCode() == Network.RESPONSE_OK){
                    int fileLength = conn.getContentLength();

                    InputStream is = new BufferedInputStream(conn.getInputStream());
                    OutputStream out = new FileOutputStream(new File(Utilities.getFileSystem() + "/" + subject.getSubjectName() + "/" + topic.getFileName()));

                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;

                    while ((count = is.read(data)) != -1) {
                        if(isCancelled()){
                            return null;
                        }
                        total += count;
                        if (fileLength > 0) {
                            publishProgress("" + (int) ((total * 100) / fileLength));
                        }
                        out.write(data, 0, count);
                    }

                    out.flush();

                    is.close();
                    out.close();
                }
            }
        }catch(Exception ex){}

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(manager.addSubjectToTable(subject)){
            AppLibrary.getInstance().addSubjectToMyLibrary(subject);
            dialog.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        dialog.setIndeterminate(false);
        dialog.setMax(100);
        dialog.setProgress(Integer.parseInt(values[0]));
    }
}
