package org.supertutor.internal.network.tasks;

import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.utils.JSONReader;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.internal.wrappers.Topic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emile on 17/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class DeleteTopicTask extends Task<Integer> {

    private ApplicationContext ctx;
    private Subject subject;
    private Topic topic;

    public DeleteTopicTask(ApplicationContext ctx, Subject subject, Topic topic){
        this.ctx = ctx;
        this.subject = subject;
        this.topic = topic;
    }

    @Override
    protected Integer call() throws Exception {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "files/removeTopic/" + replaceSpaces(subject.getSubjectName()) + "/" + replaceSpaces(topic.getTopicName()));
            URL url = new URL(tempUrl);

            HttpURLConnection conn = ctx.getNetwork().getConnection(url);
            if(conn != null){
                String pageData = ctx.getNetwork().getPageData(conn.getInputStream());

                int responseCode = JSONReader.getResponseCode(pageData);
                if(responseCode == Network.RESPONSE_OK){
                    return responseCode;
                }
            }

        }catch(IOException ex){}

        return -1;
    }

    private String replaceSpaces(String string){
        return string.replaceAll(" ", "%20");
    }
}
