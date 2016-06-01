package org.supertutor.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.supertutor.ui.panels.LoginPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile on 05/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class STLogin extends JFrame {

    private final int WIDTH = 260, HEIGHT = 400;

    public STLogin(){
        super("Super Tutor Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        initComponents();
    }

    private JFXPanel panel;
    private Group root;
    private Scene scene;

    private void initComponents(){
        panel = new JFXPanel();

        add(panel);
        setVisible(true);

        initFXScene(panel);
    }

    private void initFXScene(final JFXPanel panel){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root  =  new Group();
                scene  =  new Scene(root, WIDTH, HEIGHT);

                root.getChildren().add(new LoginPanel(STLogin.this, scene.widthProperty(), scene.heightProperty()));

                panel.setScene(scene);
            }
        });
    }

    public static void main(String a[]){
        new STLogin();
    }
}
