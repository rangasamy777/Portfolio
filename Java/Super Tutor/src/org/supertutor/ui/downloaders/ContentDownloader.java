package org.supertutor.ui.downloaders;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.tasks.DownloadTask;
import org.supertutor.ui.downloaders.children.ContentPanel;
import org.supertutor.ui.panels.LoginPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class ContentDownloader extends JFrame {

    private final int WIDTH = 320, HEIGHT = 100;

    public ContentDownloader(){
        super("Content Downloader");
        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
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

                root.getChildren().add(new ContentPanel(ContentDownloader.this, scene.widthProperty(), scene.heightProperty()));

                panel.setScene(scene);
            }
        });
    }

    public static void main(String a[]){
        new ContentDownloader();
    }
}
