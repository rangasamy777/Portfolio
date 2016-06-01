package org.supertutor;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.manager.UploadQueue;
import org.supertutor.internal.wrappers.User;
import org.supertutor.library.AppLibrary;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by Emile on 05/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class ApplicationContext {

    private static ApplicationContext instance;

    private ApplicationContext(){
        this.network = new Network();
        this.library = AppLibrary.getInstance();
        this.uploadQueue = new UploadQueue();
    }

    private Network network;
    private User currentUser;
    private AppLibrary library;
    private UploadQueue uploadQueue;

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public Network getNetwork(){
        return this.network;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    public AppLibrary getLibrary(){
        return this.library;
    }

    public UploadQueue getUploadQueue(){
        return this.uploadQueue;
    }

    public GridPane initGridPane(GridPane pane, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height, Insets insets){
        if(pane == null){
            pane = new GridPane();
            pane.setPadding(insets);
            pane.prefWidthProperty().bind(width);
            pane.prefHeightProperty().bind(height);
        }
        return pane;
    }

    public Text initLabelWithTitle(Text label, String title){
        if(label == null){
            label = new Text(title);
        }
        return label;
    }

    public Separator getHorizontalSeperator(ReadOnlyDoubleProperty width){
        Separator ls = new Separator();
        ls.setMinHeight(30);
        ls.prefWidthProperty().bind(width);
        return ls;
    }

    public Separator getVerticalSeperator(double minWidth){
        Separator ls = new Separator(Orientation.VERTICAL);
        ls.setMinWidth(minWidth);
        return ls;
    }

    public Alert createAlert(String title, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(title);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert;
    }

    public Alert createExpandibleAlert(String title, String header, String contentText, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        javafx.scene.control.TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setText(text);

        area.setMaxWidth(Double.MAX_VALUE);
        area.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(area, Priority.ALWAYS);
        GridPane.setHgrow(area, Priority.ALWAYS);

        GridPane pane = new GridPane();
        pane.add(area, 0, 0);

        alert.getDialogPane().setExpandableContent(pane);
        return alert;
    }

    public TextInputDialog createInputDialog(String title, String msg){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(title);
        dialog.setTitle(title);
        dialog.setContentText(msg);
        return dialog;
    }

    public Dialog<Pair<String,String>> createNotifierDialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Notification");
        dialog.setHeaderText("Enter the details of the notification below.");

        ButtonType loginBtn = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginBtn, ButtonType.CANCEL);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));

        TextField nTitle, nDescription;
        nTitle = new TextField();
        nTitle.setPromptText("Notification title");
        nTitle.prefWidthProperty().bind(pane.prefWidthProperty());

        nDescription = new TextField();
        nDescription.setPromptText("Notification message");
        nDescription.prefWidthProperty().bind(pane.prefWidthProperty());

        pane.add(nTitle, 0, 0);
        pane.add(nDescription, 0, 1);

        Platform.runLater(() -> nTitle.requestFocus());

        dialog.getDialogPane().setContent(pane);
        dialog.setResultConverter((dialogBtn) ->{
                if(dialogBtn == loginBtn){
                    return new Pair<>(nTitle.getText(), nDescription.getText());
                }
            return null;
        });

        return dialog;
    }

    public String formatFileSize(long size) {
        if(size <= 0) {
            return "0";
        }
        final String[] units = new String[] { "Bytes", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static ApplicationContext getContext(){
        if(instance == null){
            instance = new ApplicationContext();
        }
        return instance;
    }

}
