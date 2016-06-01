package org.supertutor.ui.panels;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.security.Crypter;
import org.supertutor.ui.STLogin;

/**
 * Created by Emile on 05/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class LoginPanel extends GridPane {

    private STLogin loginFrame;
    private ReadOnlyDoubleProperty width, height;

    public LoginPanel(STLogin loginFrame, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        this.loginFrame = loginFrame;
        this.width = width;
        this.height = height;
        setPadding(new Insets(5, 5, 5, 5));
        prefWidthProperty().bind(width);
        prefHeightProperty().bind(height);
        initComponents();
    }

    private ApplicationContext ctx;
    private TextField username;
    private PasswordField password;
    private Button login;
    private ImageView image;

    private Text updateLbl;
    private ProgressIndicator indicator;

    private void initComponents(){
        ctx = ApplicationContext.getContext();

        username = new TextField();
        username.prefWidthProperty().bind(width);
        username.setPromptText("Username");

        password = new PasswordField();
        password.prefWidthProperty().bind(width);
        password.setPromptText("Password");

        login = new Button("Login");
        login.prefWidthProperty().bind(width);
        login.setOnAction((event -> {
            login.setDisable(true);
            indicator.setVisible(true);
            updateLbl.setVisible(true);

            String passwordHash = Crypter.encryptPass(password.getText());

            ctx.getNetwork().performLogin(loginFrame, indicator, updateLbl, login, username.getText(), passwordHash);
        }));

        image = new ImageView(new Image("file:resources/logo.png"));

        indicator = new ProgressIndicator();
        indicator.setMaxHeight(150);
        indicator.prefWidthProperty().bind(width.divide(10));
        indicator.setVisible(false);

        updateLbl = new Text("Logging in, please wait...");
        updateLbl.setVisible(false);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(updateLbl);

        add(image, 0, 0);
        add(username, 0, 2);
        add(new Text(""), 0, 3);
        add(password, 0, 4);
        add(new Text(""), 0, 5);
        add(login, 0, 6);
        add(ctx.getHorizontalSeperator(width), 0, 7);
        add(indicator, 0, 8);
        add(hBox, 0, 9);
    }
}
