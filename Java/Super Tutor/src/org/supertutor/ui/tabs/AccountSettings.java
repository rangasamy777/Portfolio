package org.supertutor.ui.tabs;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.network.tasks.UpdateCredentialsTask;
import org.supertutor.internal.wrappers.AnalyticsCell;

/**
 * Created by Emile on 06/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class AccountSettings extends Tab {

    private ReadOnlyDoubleProperty width, height;

    public AccountSettings(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        super("Account Settings");
        this.width = width;
        this.height = height;
        this.setGraphic(new ImageView(new Image("file:resources/profile.png")));
        this.initContent();
    }

    private ApplicationContext ctx;
    private BorderPane borderPane;
    private GridPane contentPane;
    private TextField usernameField, emailField, dateOfBirthField, statusField;
    private PasswordField passwordField;
    private Text usernameLbl, emailLbl, dobLbl, passwordLbl, statusLbl;
    private Button update;
    private TableView<AnalyticsCell> statsTable;
    private TableColumn field, value;

    private void initContent(){
        ctx = ApplicationContext.getContext();

        contentPane = ctx.initGridPane(contentPane, width, height, new Insets(5, 5, 5, 5));

        usernameLbl = ctx.initLabelWithTitle(usernameLbl, "Username: ");
        usernameField = initTextField(usernameField, 5);
        usernameField.setEditable(false);

        passwordLbl = ctx.initLabelWithTitle(passwordLbl, "    Password: ");
        passwordField = initPasswordField(passwordField);

        emailLbl = ctx.initLabelWithTitle(emailLbl, "Email: ");
        emailField = initTextField(emailField, 3);

        dobLbl = ctx.initLabelWithTitle(dobLbl, "Date of birth: ");
        dateOfBirthField = initTextField(dateOfBirthField, 7);
        dateOfBirthField.setEditable(false);

        statusLbl = ctx.initLabelWithTitle(statusLbl, "Status: ");
        statusField = initTextField(statusField, 3);
        statusField.setEditable(false);

        if(ctx.getCurrentUser() != null){
            statusField.setText(ctx.getCurrentUser().getStatus());
            dateOfBirthField.setText(ctx.getCurrentUser().getDateOfBirth());
            passwordField.setText(ctx.getCurrentUser().getPassword());
            usernameField.setText(ctx.getCurrentUser().getUsername());
            emailField.setText(ctx.getCurrentUser().getEmail());
        }

        update = new Button("Update information");
        update.prefWidthProperty().bind(width);
        update.setOnAction((event) -> {
            UpdateCredentialsTask task = new UpdateCredentialsTask(ctx, usernameField.getText(), emailField.getText(), passwordField.getText());
            if(task != null){
                update.textProperty().bind(task.messageProperty());
                ctx.getNetwork().updateUserCredentials(task);
            }
        });

        statsTable = initTableView(statsTable);

        contentPane.add(usernameLbl, 0, 0);
        contentPane.add(usernameField, 1, 0, 4, 1);

        contentPane.add(passwordLbl, 5, 0);
        contentPane.add(passwordField, 6, 0, 1, 1);
        contentPane.add(ctx.getHorizontalSeperator(width), 0, 1, 7, 1);
        contentPane.add(emailLbl, 0, 2);
        contentPane.add(emailField, 1, 2, 7, 1);

        contentPane.add(ctx.getHorizontalSeperator(width), 0, 3, 7, 1);
        contentPane.add(statusLbl, 0, 4);
        contentPane.add(statusField, 1, 4, 3, 1);

        contentPane.add(dobLbl, 5, 4);
        contentPane.add(dateOfBirthField, 6, 4);

        contentPane.add(ctx.getHorizontalSeperator(width), 0, 5, 7, 1);
        contentPane.add(statsTable, 0, 6, 7, 1);

        borderPane = new BorderPane();
        borderPane.setCenter(contentPane);
        borderPane.setBottom(update);

        setContent(borderPane);
    }

    private PasswordField initPasswordField(PasswordField field){
        if(field == null){
            field = new PasswordField();
            field.prefWidthProperty().bind(width.divide(5));
        }
        return field;
    }

    private TextField initTextField(TextField textfield, int divisor){
        if(textfield == null){
            textfield = new TextField();
            textfield.prefWidthProperty().bind(width.divide(divisor));
        }
        return textfield;
    }

    private TableView<AnalyticsCell> initTableView(TableView view){
        if(view == null){
            view = new TableView(getTableInformation());
            view.prefWidthProperty().bind(width);
        }

        field = new TableColumn<String, Object>("Field");
        field.prefWidthProperty().bind(view.prefWidthProperty().divide(2));
        field.setCellValueFactory(new PropertyValueFactory<>("field"));
        value = new TableColumn<String, Object>("Value");
        value.prefWidthProperty().bind(view.prefWidthProperty().divide(2));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        view.getColumns().addAll(field, value);
        return view;
    }

    private ObservableList<AnalyticsCell> getTableInformation(){
        ObservableList<AnalyticsCell> list = FXCollections.observableArrayList();

        if(ctx.getCurrentUser() != null){
            list.add(new AnalyticsCell("Last Logged in", ctx.getCurrentUser().getLastLogged()));
        }

        return list;
    }

    public String getEmail(){
        return emailField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }
}
