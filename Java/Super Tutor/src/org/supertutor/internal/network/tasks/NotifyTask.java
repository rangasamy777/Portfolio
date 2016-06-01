package org.supertutor.internal.network.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.Network;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emile on 01/05/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class NotifyTask extends Task<Integer> {

    public NotifyTask(ApplicationContext ctx, String title, String description){
        this.network = ctx.getNetwork();
        this.title = title;
        this.description = description;
    }

    private Network network;
    private String title, description;

    @Override
    protected Integer call() throws Exception {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "notifier/send/" + title + "/" + description);
            tempUrl = tempUrl.replaceAll(" ", "%20");
            URL url = new URL(tempUrl);

            HttpURLConnection conn = network.getConnection(url);
            if(conn != null){
                String pageData = network.getPageData(conn.getInputStream());
                System.out.println(pageData);
                if(pageData != null){
                    JSONObject dataObject = (JSONObject) new JSONParser().parse(pageData);
                    if(dataObject != null){
                        long responseCode = (long)dataObject.get("code");
                        return (int)responseCode;
                    }
                }
            }
        }catch(Exception ex){}

        return -1;
    }
}
