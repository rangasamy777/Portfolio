package org.supertutor.internal.network;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.supertutor.internal.network.tasks.*;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.internal.wrappers.Topic;
import org.supertutor.ui.STLogin;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile on 05/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class Network {

    public static final String NETWORK_URL = "http://localhost:8080/%s";
    public static final String API_URL = "http://localhost:8080/api/%s";

    public static final String HELP_EMAIL = "mailto:support@envisiontechllc.org";

    public static final int RESPONSE_OK = 200, RESPONSE_ERROR = 201;

    public Network(){
        this.parser = new JSONParser();
    }

    private JSONParser parser;

    public void getUserbase(PieChart chart){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String tempUrl = String.format(API_URL, "stats/getUserbase");
                    HttpURLConnection conn = getConnection(new URL(tempUrl));
                    if(conn != null){
                        String dataString = getPageData(conn.getInputStream());
                        JSONObject data = (JSONObject) parser.parse(dataString);
                        if(data != null){
                            long gce = (long)data.get("GCE Student");
                            long gcse = (long)data.get("GCSE Student");
                            long employed = (long)data.get("Employed");
                            long unemployed = (long)data.get("Unemployed");
                            long undergrad = (long)data.get("Undergraduate");
                            long postGrag = (long)data.get("Postgraduate");

                            new Thread(new UserbaseTask(chart, gce, gcse, undergrad, postGrag, employed, unemployed)).start();
                        }
                    }
                }catch(IOException | ParseException ex){}
            }
        }).start();
    }

    public void performLogin(STLogin loginForm, ProgressIndicator indicator, Text label, Button login, String username, String password){
        new Thread(new LoginTask(loginForm, this, indicator, label, login, username, password)).start();
    }

    public boolean isContentManager(JSONObject ob){
        return (ob.get("status") != null && ((String)ob.get("status")).equalsIgnoreCase("Content Manager") ? true : false);
    }

    public HttpURLConnection getConnection(URL url){
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn.getResponseCode() == RESPONSE_OK){
                return conn;
            }
        }catch(IOException ex){}
        return null;
    }

    public String getPageData(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();

        String input = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while((input = br.readLine()) != null){
            sb.append(input);
        }

        return sb.toString();
    }

    public void uploadTopicFile(Subject subject, Topic topic){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient client = HttpClients.createDefault();
                    HttpPost postRequest = new HttpPost(String.format(NETWORK_URL, "files/upload"));

                    HttpEntity entity = MultipartEntityBuilder.create().addPart("file", new FileBody(topic.getFilePath()))
                            .addTextBody("subjectName", subject.getSubjectName())
                            .addTextBody("subjectDescription", subject.getDescription())
                            .addTextBody("topicName", topic.getTopicName()).build();

                    postRequest.setEntity(entity);
                    HttpResponse response = client.execute(postRequest);
                    System.out.println(response.getStatusLine().getStatusCode());
                }catch(Exception ex){}
            }
        }).start();
    }

    public void openURLDefaultBrowser(String url){
        try {
            Desktop desktop = (Desktop.isDesktopSupported() ? Desktop.getDesktop() : null);
            if(desktop != null){
                desktop.browse(new URL(url).toURI());
            }
        } catch(IOException | URISyntaxException ex){}
    }

    public void openEmail(String sender){
        try {
            Desktop desktop = (Desktop.isDesktopSupported() ? Desktop.getDesktop() : null);
            if(desktop != null){
                desktop.mail(new URL(sender).toURI());
            }
        } catch(IOException | URISyntaxException ex){}
    }

    public void downloadContent(DownloadTask task){
        new Thread(task).start();
    }

    public void makeLegacy(LegacyTask task){
        new Thread(task).start();
    }

    public void updateUserCredentials(UpdateCredentialsTask task){
        new Thread(task).start();
    }

    public void removeTopic(DeleteTopicTask task){
        new Thread(task).start();
    }

    public URI getMailURI(){
        try {
            new URL(HELP_EMAIL).toURI();
        } catch(IOException | URISyntaxException ex){}
        return null;
    }

    public JSONParser getParser(){
        return this.parser;
    }
}
