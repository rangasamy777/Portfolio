package org.supertutor.internal.network.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.internal.wrappers.Topic;
import org.supertutor.library.AppLibrary;
import org.supertutor.ui.STDashboard;
import org.supertutor.ui.downloaders.ContentDownloader;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class DownloadTask extends Task<Integer> {

    private ContentDownloader downloader;
    private AppLibrary library;
    private Network network;
    private boolean openDashboard, done;

    public DownloadTask(ContentDownloader downloader, Network network, boolean openDashboard){
        this.downloader = downloader;
        this.network = network;
        this.library = AppLibrary.getInstance();
        this.openDashboard = openDashboard;
    }

    @Override
    protected Integer call() throws Exception {
        try {
            done = false;
            library.resetLibrary();

            updateMessage("Connected! Downloading content...");
            String contentUrl = String.format(Network.NETWORK_URL, "files/getSubjects");
            URL url = new URL(contentUrl);

            HttpURLConnection conn = network.getConnection(url);
            if(conn != null){

                String contentString = network.getPageData(conn.getInputStream());
                JSONArray subjectArray = (JSONArray) new JSONParser().parse(contentString);
                if (subjectArray != null) {

                    for(int i = 0; i < subjectArray.size(); i++){
                        JSONObject subjectObject = (JSONObject)subjectArray.get(i);
                        if(subjectObject != null){
                            String subjectName = (String)subjectObject.get("subjectName");
                            String description = (String)subjectObject.get("description");
                            long contentSize = (long)subjectObject.get("contentSize");

                            Subject tempSubject = new Subject(subjectName, description);
                            tempSubject.setContentSize(contentSize);

                            JSONArray topicArray = (JSONArray)subjectObject.get("topics");
                            if(topicArray != null){
                                for(int j = 0; j < topicArray.size(); j++){
                                    JSONObject topicObject = (JSONObject)topicArray.get(j);
                                    if(topicObject != null){
                                        String topicName = (String)topicObject.get("topicName");
                                        String topicDescription = (String)topicObject.get("topicTag");

                                        tempSubject.addTopic(new Topic(topicName, topicDescription));
                                    }
                                }
                            }
                            library.addSubject(tempSubject);
                        }
                        updateProgress(i, subjectArray.size());
                    }
                    done = true;
                    if(openDashboard && downloader != null){
                        openDashboard();
                    }
                }
            }
        }catch(Exception ex){ex.printStackTrace();}

        return -1;
    }

    @Override
    public boolean isDone(){
        return done;
    }

    private void openDashboard(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                downloader.dispose();
            }
        });
        new STDashboard();
    }
}
