package org.supertutor.ui.tabs;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.tasks.DownloadTask;
import org.supertutor.internal.network.tasks.LegacyTask;
import org.supertutor.internal.network.tasks.UploadTestTask;
import org.supertutor.internal.wrappers.Subject;
import org.supertutor.internal.wrappers.Topic;
import org.supertutor.ui.STDashboard;
import org.supertutor.ui.STopicManager;
import org.supertutor.ui.downloaders.ContentDownloader;

import javax.swing.*;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emile on 06/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class SubjectManagement extends Tab {

    private STDashboard parent;
    private ReadOnlyDoubleProperty width, height;
    private Subject currentSubject;

    public SubjectManagement(STDashboard parent, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        super("Subject Management");
        this.width = width;
        this.height = height;
        this.parent = parent;
        this.setGraphic(new ImageView(new Image("file:resources/dashboard.png")));
        this.initContent();
    }

    private ApplicationContext ctx;
    private GridPane contentPane;
    private TableView<Subject> subjectTable;
    private TableView<Topic> topicTable;
    private TextField subjectName, subjectDescription, subjectFilesize;
    private Button addTopic;
    private TableColumn sColName, sColDesc, tColName, tColDesc;

    private ContextMenu rightClick;
    private MenuItem addSubject, refreshTable, deleteSubject, addTest;
    private Text statusLabel;
    private FileChooser chooser;

    private void initContent(){
        ctx = ApplicationContext.getContext();

        contentPane = ctx.initGridPane(contentPane, width, height, new Insets(10, 10, 10, 10));

        subjectTable = initTableview(subjectTable);
        addSubjectListener(subjectTable);
        initSubjectTable(subjectTable);

        rightClick = createContextMenu(rightClick);
        subjectTable.setContextMenu(rightClick);

        topicTable = initTableview(topicTable);
        initTopicTable(topicTable);

        subjectName = initFieldWithPlaceholder(subjectName, "Name");
        subjectDescription = initFieldWithPlaceholder(subjectDescription, "Description");
        subjectFilesize = initFieldWithPlaceholder(subjectFilesize, "Content size");

        statusLabel = ctx.initLabelWithTitle(statusLabel, "Status: Waiting input...");

        addTopic = new Button("Add Subject");
        addTopic.setOnAction((click) -> {
            createSubject();
        });

        contentPane.add(subjectTable, 0, 0, 1, 10);
        contentPane.add(ctx.getVerticalSeperator(10), 1, 0, 1, 10);
        contentPane.add(subjectName, 2, 0);
        contentPane.add(getSeparator(), 2, 1);
        contentPane.add(subjectDescription, 2, 2);
        contentPane.add(getSeparator(), 2, 3);
        contentPane.add(subjectFilesize, 2, 4);
        contentPane.add(getSeparator(), 2, 5);
        contentPane.add(topicTable, 2, 6);
        contentPane.add(getSeparator(), 2, 7);
        contentPane.add(getAlignmentHolder(addTopic, statusLabel), 2, 8);
        setContent(contentPane);
    }

    private HBox getAlignmentHolder(Button add, Text statusLabel){
        HBox holder = new HBox();
        holder.setAlignment(Pos.CENTER);
        holder.prefWidthProperty().bind(width.divide(2));

        HBox rightPlace = new HBox();
        rightPlace.setAlignment(Pos.CENTER_RIGHT);
        rightPlace.getChildren().add(add);

        HBox leftPlace = new HBox();
        leftPlace.setAlignment(Pos.CENTER_LEFT);
        leftPlace.getChildren().add(statusLabel);

        holder.getChildren().addAll(leftPlace, ctx.getVerticalSeperator(100),  rightPlace);
        return holder;
    }

    private TextField initFieldWithPlaceholder(TextField field, String placeholder){
        if(field == null){
            field = new TextField();
            field.setPromptText(placeholder);
        }
        return field;
    }

    private TableView initTableview(TableView view){
        if(view == null){
            view = new TableView<>();
            view.prefWidthProperty().bind(width.divide(2));
            view.setEditable(false);
        }
        return view;
    }

    private void initSubjectTable(TableView<Subject> table){
        sColName = new TableColumn("Subject");
        sColName.prefWidthProperty().bind(table.prefWidthProperty().divide(2));
        sColName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

        sColDesc = new TableColumn("Description");
        sColDesc.prefWidthProperty().bind(table.prefWidthProperty().divide(2));
        sColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.setItems(getSubjects());

        table.getColumns().addAll(sColName, sColDesc);
        table.setOnMouseClicked((click) -> {
            if(table.getSelectionModel().getSelectedItem() != null){
                addTopic.setText("Add Topic");
            }
        });
    }

    private void initTopicTable(TableView<Topic> table){
        tColName = new TableColumn("Topic");
        tColName.prefWidthProperty().bind(table.prefWidthProperty().divide(2));
        tColName.setCellValueFactory(new PropertyValueFactory<>("topicName"));

        tColDesc = new TableColumn("Tag");
        tColDesc.prefWidthProperty().bind(table.prefWidthProperty().divide(2));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("tag"));

        table.getColumns().addAll(tColName, tColDesc);
    }

    private Separator getSeparator(){
        Separator ls = new Separator();
        ls.setMinHeight(20);
        ls.prefWidthProperty().bind(width.divide(2));
        return ls;
    }

    private void addSubjectListener(TableView<Subject> table){
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if(event.getClickCount() >= 1){
                currentSubject = table.getSelectionModel().getSelectedItem();
                if(currentSubject != null){
                    subjectName.setText(currentSubject.getSubjectName());
                    subjectDescription.setText(currentSubject.getDescription());

                    subjectFilesize.setText("" + ctx.formatFileSize(currentSubject.getContentSize()) + "");

                    topicTable.setItems(getSubjectTopics());
                }
            }
        });
    }

    public ObservableList<Subject> getSubjects(){
        ObservableList<Subject> list = FXCollections.observableArrayList();

        for(Subject subject: ctx.getLibrary().getLoadedSubjects()){
            list.add(subject);
        }

        return list;
    }

    public ObservableList<Topic> getSubjectTopics(){
        ObservableList<Topic> list = FXCollections.observableArrayList();

        if(currentSubject != null){
            for(Map.Entry<String, Topic> entry: currentSubject.getTopics().entrySet()){
                list.add(entry.getValue());
            }
        }

        return list;
    }

    private ContextMenu createContextMenu(ContextMenu menu){
        if(menu == null){
            menu = new ContextMenu();
            addSubject = new MenuItem("Add New Subject");
            addSubject.setOnAction((click) -> {
                currentSubject = null;
                createSubject();
            });
            refreshTable = new MenuItem("Refresh data");
            refreshTable.setOnAction((click) -> {
                subjectTable.setItems(getSubjects());
            });
            deleteSubject = new MenuItem("Classify legacy/delete");
            deleteSubject.setOnAction((event) -> {
                int index = subjectTable.getSelectionModel().getSelectedIndex();
                if(index != -1){
                    System.out.println("Should delete object ");
                    LegacyTask task = new LegacyTask(ctx, currentSubject);
                    if(task != null) {
                        statusLabel.textProperty().bind(task.messageProperty());

                        ctx.getNetwork().makeLegacy(task);
                        ctx.getLibrary().removeSubjectIndex(index);

                        currentSubject = null;
                        topicTable.setItems(getSubjectTopics());
                        subjectTable.setItems(getSubjects());
                    }
                }
            });
            addTest = new MenuItem("Add Subject Test");
            addTest.setOnAction((event) -> {
                Subject chosenitem = subjectTable.getSelectionModel().getSelectedItem();
                if(chosenitem != null){
                    chooser = initFileChooser(chooser);

                    File selectedFile = chooser.showOpenDialog(parent.getPanel().getScene().getWindow());
                    if(selectedFile != null){
                        try {
                            UploadTestTask testTask = new UploadTestTask(chosenitem, selectedFile);
                            new Thread(testTask).start();
                            if(testTask.get() == Network.RESPONSE_OK){
                                ctx.createAlert("Success", "Successfully uploaded test file", Alert.AlertType.INFORMATION).show();
                            }
                        }catch(ExecutionException | InterruptedException ex){}
                    }
                }
            });
            menu.getItems().addAll(addTest, addSubject, refreshTable, deleteSubject);
        }
        return menu;
    }

    private void createSubject(){
        Subject subjectData = (currentSubject != null ? currentSubject : requestSubject());

        if(subjectData != null){
            new STopicManager(SubjectManagement.this, subjectData);
        }
    }

    private Subject requestSubject(){
        Subject newSubject = null;

        do {
            Optional<String> optional = ctx.createInputDialog("Subject name", "Enter a name for the new Subject.").showAndWait();
            if(optional != null && optional.isPresent() && !optional.get().isEmpty()){
                String subjectName = optional.get();
                Optional<String> description = ctx.createInputDialog("Subject description", "Enter a brief description for the subject you wish to upload.").showAndWait();
                if(description != null && description.isPresent() && !description.get().isEmpty()){
                    String descriptionString = description.get();
                    newSubject = new Subject(subjectName, descriptionString);
                }
            }
            break;
        }while(newSubject == null);

        return newSubject;
    }

    private FileChooser initFileChooser(FileChooser chooser){
        if(chooser == null){
            chooser = new FileChooser();
            chooser.setTitle("Select a test file to upload");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Txt Files", "*.txt"));
        }
        return chooser;
    }

    public TableView getSubjectTable(){
        return this.subjectTable;
    }

    public TableView getTopicTable(){
        return this.topicTable;
    }
}
