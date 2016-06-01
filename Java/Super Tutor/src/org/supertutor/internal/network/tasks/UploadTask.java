package org.supertutor.internal.network.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.utils.JSONReader;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.internal.wrappers.Topic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Emile on 13/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class UploadTask extends Task<Integer> {

    public UploadTask(Queue<Subject> queue, Subject subject){
        this.queue = queue;
        this.subject = subject;
    }

    private Queue<Subject> queue;
    private Subject subject;

    @Override
    protected Integer call() throws Exception {
        int responseCode = -1;

        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(String.format(Network.NETWORK_URL, "files/uploadSubject"));

            postRequest.setEntity(new UrlEncodedFormEntity(getParams()));
            HttpResponse response = client.execute(postRequest);

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == Network.RESPONSE_OK){
                String jsonResponse = EntityUtils.toString(response.getEntity());
                responseCode = JSONReader.getResponseCode(jsonResponse);
                if(responseCode == Network.RESPONSE_OK){
                    queue.remove(subject);
                }
            }

        }catch(IOException ex){}

        return responseCode;
    }

    private List<NameValuePair> getParams(){
        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("jsonString", subject.toString()));

        return params;
    }
}
