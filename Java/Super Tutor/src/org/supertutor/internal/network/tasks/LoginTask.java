package org.supertutor.internal.network.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.security.Crypter;
import org.supertutor.internal.wrappers.User;
import org.supertutor.ui.STLogin;
import org.supertutor.ui.downloaders.ContentDownloader;

import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emile on 07/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class LoginTask extends Task<Integer> {

    private ApplicationContext ctx;
    private STLogin loginForm;
    private Network network;
    private Button loginBtn;
    private String username, password;
    private ProgressIndicator indicator;
    private Text label;
    private String loginMsg;

    public LoginTask(STLogin loginForm, Network network, ProgressIndicator indicator, Text label, Button login, String username, String password){
        this.loginForm = loginForm;
        this.network = network;
        this.indicator = indicator;
        this.label = label;
        this.loginBtn = login;
        this.username = username;
        this.password = password;
        this.ctx = ApplicationContext.getContext();
    }

    @Override
    protected Integer call() throws Exception {
        try {
            String tempUrl = String.format(Network.NETWORK_URL, "users/login/" + username.replaceAll(" ", "%20") + "/" + Crypter.encryptPass(password));
            HttpURLConnection conn = network.getConnection(new URL(tempUrl));
            if(conn != null){
                if(conn.getResponseCode() == Network.RESPONSE_OK){
                    String dataString = network.getPageData(conn.getInputStream());
                    if(dataString != null && dataString.length() > 3){
                        JSONObject data = (JSONObject) network.getParser().parse(dataString);
                        if(data != null && data.get("status") != null){
                            if(network.isContentManager(data)){
                                String status = (String)data.get("status");
                                String email = (String)data.get("email");
                                String dob = (String)data.get("dateOfBirth");
                                String lastLogged = (String)data.get("lastLoggedIn");

                                ctx.setCurrentUser(new User(username, password, email, null, status, dob, lastLogged));
                                openContentDownloader();
                                disposeForm();
                                return 200;
                            } else {
                                loginMsg = "You are not authorised to use this application. Contact the administrator";
                            }
                        }
                    }
                }
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(loginMsg == null){
                        loginMsg = "Invalid login details. Try again.";
                    }
                    loginBtn.setDisable(false);
                    indicator.setVisible(false);
                    label.setText("Failed login.");
                    ctx.createAlert("Invalid details", loginMsg, Alert.AlertType.ERROR).showAndWait();
                }
            });
        }catch(IOException | ParseException ex){ex.printStackTrace();}
        return 201;
    }

    private void openContentDownloader(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContentDownloader();
            }
        });
    }

    private void disposeForm(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginForm.dispose();
            }
        });
    }
}
