package org.supertutor.internal.network.tasks;

import javafx.concurrent.Task;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.utils.JSONReader;
import org.supertutor.internal.security.Crypter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class UpdateCredentialsTask extends Task<Integer> {

    private ApplicationContext ctx;
    private String email, username, password;

    public UpdateCredentialsTask(ApplicationContext ctx, String username, String email, String password){
        this.ctx = ctx;
        this.username = username;
        this.email = email;
        this.password = Crypter.encryptPass(password);
    }

    @Override
    protected Integer call() throws Exception {

        try {
            String dataUrl = String.format(Network.NETWORK_URL, "users/update/" + username.replaceAll(" ", "%20") + "/" + email + "/" + password);
            URL url = new URL(dataUrl);

            HttpURLConnection conn = ctx.getNetwork().getConnection(url);
            if(conn != null){
                String jsonResponse = ctx.getNetwork().getPageData(conn.getInputStream());

                int response = JSONReader.getResponseCode(jsonResponse);
                if(response == Network.RESPONSE_OK){
                    updateMessage("Update Credentials; Successfully updated.");
                    return response;
                }
            }
        }catch(IOException ex){}

        return -1;
    }
}
