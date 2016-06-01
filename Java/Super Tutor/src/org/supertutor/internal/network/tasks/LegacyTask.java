package org.supertutor.internal.network.tasks;

import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.wrappers.Subject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class LegacyTask extends Task<Integer> {

    private ApplicationContext ctx;
    private Network network;
    private Subject subject;

    public LegacyTask(ApplicationContext ctx, Subject subject){
        this.ctx = ctx;
        this.network = ctx.getNetwork();
        this.subject = subject;
    }

    @Override
    protected Integer call() throws Exception {

        try {
            String urlData = String.format(Network.NETWORK_URL, "files/makeLegacy/" + subject.getSubjectName().replaceAll(" ", "%20"));
            URL url = new URL(urlData);

            HttpURLConnection conn = network.getConnection(url);
            System.out.println("Conn: " + String.valueOf(conn != null));
            if(conn != null){
                String responseString = network.getPageData(conn.getInputStream());
                System.out.println("String: " + responseString);
                JSONObject responseObject = (JSONObject)network.getParser().parse(responseString);
                if(responseObject != null){
                    long responseCode = (long)responseObject.get("code");
                    System.out.println("Response code: " + responseCode);
                    if(responseCode != -1){
                        switch((int)responseCode){
                            case 200:
                                updateMessage("Successfully deleted.");
                                break;
                            case 201:
                                updateMessage("Failed to delete");
                                break;
                        }
                        return (int)responseCode;
                    }
                }
            }
        }catch(Exception ex){ex.printStackTrace();}

        return -1;
    }
}
