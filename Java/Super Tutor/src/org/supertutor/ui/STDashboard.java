package org.supertutor.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.supertutor.ApplicationContext;
import org.supertutor.ui.panels.DashboardPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile on 05/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class STDashboard extends JFrame {

    private final int WIDTH = 720, HEIGHT = 480;

    public STDashboard(){
        super("Super Tutor Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();
    }

    private ApplicationContext ctx;
    private JFXPanel panel;
    private Group root;
    private Scene scene;

    private void initComponents(){
        ctx = ApplicationContext.getContext();

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

                root.getChildren().add(new DashboardPanel(STDashboard.this, ctx, scene.widthProperty(), scene.heightProperty()));

                panel.setScene(scene);
            }
        });
    }

    public JFXPanel getPanel(){
        return this.panel;
    }

    public static void main(String a[]){
        new STDashboard();
    }
}
