package org.supertutor.ui.panels;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.enums.Tags;
import org.supertutor.internal.network.tasks.DeleteTopicTask;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.internal.wrappers.Topic;
import org.supertutor.ui.STopicManager;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile on 08/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class TopicPanel extends GridPane {

    private STopicManager parent;
    private ReadOnlyDoubleProperty width, height;
    private Subject subject;
    private FileChooser fileChooser;
    private File selectedFile;

    public TopicPanel(STopicManager parent, Subject subject, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        this.parent = parent;
        this.subject = subject;
        this.width = width;
        this.height = height;
        this.initComponents();
    }

    private ApplicationContext ctx;
    private TableView<Topic> topicTable;
    private TableColumn topicNameCol, topicDescCol;
    private List<Topic> topicsList;
    private TextField topicName, fileLocation;
    private ComboBox<String> description;
    private Button addTopic, uploadSubject;

    private ContextMenu listMenu;
    private MenuItem delete;

    private void initComponents(){
        ctx = ApplicationContext.getContext();
        ctx.initGridPane(this, width, height, new Insets(5, 5, 5, 5));
        topicsList = new ArrayList<>();

        for(Topic t: subject.getTopicsList()){
            topicsList.add(t);
        }

        topicTable = initTableView(topicTable);
        listMenu = initContextMenu(listMenu);
        topicTable.setContextMenu(listMenu);

        topicName = initTextField(topicName, "Topic name", 2);
        description = initTextArea(description);

        fileLocation = initTextField(fileLocation, "Click to select a file", 5);
        fileLocation.setEditable(false);
        fileLocation.setOnMouseClicked((event -> {
            fileChooser = initFileChooser(fileChooser);

            selectedFile = fileChooser.showOpenDialog(TopicPanel.this.getScene().getWindow());
            if(selectedFile != null){
                fileLocation.setText(selectedFile.getAbsolutePath());
            }
        }));

        addTopic = initButton(addTopic, "Upload Topic", 2);
        addTopic.setOnAction((event) -> {
            if(validateFields(topicName, fileLocation, description)){
                Topic tempTopic = new Topic(topicName.getText(), description.getSelectionModel().getSelectedItem(), selectedFile);
                if(!topicTable.getItems().contains(tempTopic)){
                    subject.addTopic(tempTopic);
                    topicsList.add(tempTopic);
                    topicTable.getItems().add(tempTopic);
                    topicName.setText("");
                    fileLocation.setText("");

                    ctx.getNetwork().uploadTopicFile(subject, tempTopic);
                    ctx.getUploadQueue().addToQueue(subject);
                } else {
                    ctx.createAlert("Already added", "There is already a topic of the same content in the list.", Alert.AlertType.ERROR).show();
                }
            } else {
                ctx.createAlert("Incomplete form", "Ensure you have filled out ALL the fields appropriately.", Alert.AlertType.ERROR).show();
            }
        });

        uploadSubject = initButton(uploadSubject, "Build Subject", 2);
        uploadSubject.setOnAction((click) -> {
            ctx.getLibrary().updateSubject(subject);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parent.getSubjectManager().getSubjectTable().setItems(parent.getSubjectManager().getSubjects());
                    parent.getSubjectManager().getTopicTable().setItems(parent.getSubjectManager().getSubjectTopics());
                }
            });
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    parent.dispose();
                }
            });
        });


        add(topicTable, 0, 0, 1, 6);
        add(uploadSubject, 0, 6, 1, 1);
        add(fileLocation, 1, 0, 1, 1);
        add(ctx.getHorizontalSeperator(width), 1, 1);
        add(topicName, 1, 2, 1, 1);
        add(ctx.getHorizontalSeperator(width), 1, 3, 1, 1);
        add(description, 1, 4, 1, 1);
        add(addTopic, 1, 6);
    }

    private Button initButton(Button button, String text, int divisor){
        if(button == null){
            button = new Button(text);
            button.prefWidthProperty().bind(width.divide(divisor));
        }
        return button;
    }

    private ContextMenu initContextMenu(ContextMenu menu){
        if(menu == null){
            menu = new ContextMenu();
            delete = new MenuItem("Delete");
            delete.setOnAction((click) -> {
                Topic tempTopic = topicTable.getSelectionModel().getSelectedItem();
                if(tempTopic != null){
                    DeleteTopicTask task = new DeleteTopicTask(ctx, subject, tempTopic);
                    ctx.getNetwork().removeTopic(task);

                    topicsList.remove(tempTopic);
                    topicTable.setItems(getTopicsList());
                    subject.removeTopic(tempTopic);

                    ctx.getLibrary().updateSubject(subject);
                }
            });
            menu.getItems().add(delete);
        }
        return menu;
    }

    private TableView<Topic> initTableView(TableView<Topic> table){
        if(table == null){
            table = new TableView<>(getTopicsList());
            table.prefWidthProperty().bind(width.divide(2));
            table.prefHeightProperty().bind(height.divide(1.125));
            topicNameCol = new TableColumn("Topic name");
            topicNameCol.prefWidthProperty().bind(table.prefWidthProperty().divide(2));
            topicNameCol.setCellValueFactory(new PropertyValueFactory<>("topicName"));
            topicDescCol = new TableColumn("Tag");
            topicDescCol.prefWidthProperty().bind(table.prefWidthProperty().divide(2));
            topicDescCol.setCellValueFactory(new PropertyValueFactory<>("tag"));

            table.getColumns().addAll(topicNameCol, topicDescCol);
        }
        return table;
    }

    private ComboBox<String> initTextArea(ComboBox<String> field){
        if(field == null){
            field = new ComboBox<>();
            field.prefWidthProperty().bind(width.divide(2.5));
            field.setPromptText("Tag");

            for(Tags tag: Tags.values()){
                field.getItems().add(tag.getTag());
            }
        }
        return field;
    }

    private TextField initTextField(TextField field, String prompt, int divisor){
        if(field == null){
            field = new TextField();
            field.prefWidthProperty().bind(width.divide(divisor));
            field.setPromptText(prompt);
        }
        return field;
    }

    private FileChooser initFileChooser(FileChooser chooser){
        if(chooser == null){
            chooser = new FileChooser();
            chooser.setTitle("Select a file to upload");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        }
        return chooser;
    }

    private boolean validateFields(TextField name, TextField path, ComboBox<String> description){
        return (name.getText() != null && !name.getText().isEmpty() && path.getText() != null && !path.getText().isEmpty() && description.getSelectionModel().getSelectedItem() != null && !description.getSelectionModel().getSelectedItem().isEmpty() ? true : false);
    }

    private ObservableList<Topic> getTopicsList(){
        ObservableList<Topic> list = FXCollections.observableArrayList();

        for(Topic topic: topicsList){
            list.add(topic);
        }

        return list;
    }
}
