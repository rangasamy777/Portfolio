package org.supertutor.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.ui.panels.TopicPanel;
import org.supertutor.ui.tabs.SubjectManagement;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile on 08/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class STopicManager extends JFrame {

    private SubjectManagement parent;
    private final int WIDTH = 720, HEIGHT = 380;

    public STopicManager(SubjectManagement parent, Subject subject){
        super(subject.getSubjectName());
        this.subject = subject;
        this.parent = parent;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(new Dimension(WIDTH, HEIGHT));
        initComponents();
    }

    private JFXPanel panel;
    private Group root;
    private Scene scene;
    private Subject subject;

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

                root.getChildren().add(new TopicPanel(STopicManager.this, subject, scene.widthProperty(), scene.heightProperty()));

                panel.setScene(scene);
            }
        });
    }

    public SubjectManagement getSubjectManager(){
        return this.parent;
    }
}
