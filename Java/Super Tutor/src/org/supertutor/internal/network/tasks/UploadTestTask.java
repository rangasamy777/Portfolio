package org.supertutor.internal.network.tasks;

import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.utils.JSONReader;
import org.supertutor.internal.wrappers.Subject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Emile on 13/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class UploadTestTask extends Task<Integer> {

    public UploadTestTask(Subject subject, File file){
        this.subject = subject;
        this.file = file;
    }

    private Subject subject;
    private File file;

    @Override
    protected Integer call() throws Exception {
        int responseCode = -1;

        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(String.format(Network.NETWORK_URL, "files/uploadTest"));

            HttpEntity entity = MultipartEntityBuilder.create().addPart("file", new FileBody(file))
                    .addTextBody("subjectName", subject.getSubjectName()).addTextBody("subjectDescription", subject.getDescription()).build();

            postRequest.setEntity(entity);
            HttpResponse response = client.execute(postRequest);
            return response.getStatusLine().getStatusCode();

        }catch(IOException ex){}

        return responseCode;
    }
}
