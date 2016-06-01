package org.supertutor.ui.downloaders.children;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.tasks.DownloadTask;
import org.supertutor.ui.downloaders.ContentDownloader;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class ContentPanel extends GridPane {

    private ContentDownloader parent;
    private ReadOnlyDoubleProperty width, height;

    public ContentPanel(ContentDownloader parent, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        this.parent = parent;
        this.width = width;
        this.height = height;
        prefWidthProperty().bind(width);
        prefHeightProperty().bind(height);
        init();
    }

    private ProgressIndicator progress;
    private Text label;

    private void init(){
        label = new Text("Connecting to database...");
        progress = new ProgressIndicator();
        progress.prefWidthProperty().bind(width);

        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(label);
        box.prefWidthProperty().bind(width);

        add(box, 0, 0);
        add(progress, 0, 1);

        Network network = new Network();

        DownloadTask task = new DownloadTask(parent, network, true);
        label.textProperty().bind(task.messageProperty());
        progress.progressProperty().bind(task.progressProperty());
        network.downloadContent(task);
    }
}