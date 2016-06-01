package org.supertutor.ui.panels;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.supertutor.ApplicationContext;
import org.supertutor.internal.enums.Strings;
import org.supertutor.internal.network.Network;
import org.supertutor.internal.network.tasks.NotifyTask;
import org.supertutor.ui.STLogin;
import org.supertutor.ui.STDashboard;
import org.supertutor.ui.tabs.AccountSettings;
import org.supertutor.ui.tabs.AppAnalytics;
import org.supertutor.ui.tabs.SubjectManagement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Emile on 06/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class DashboardPanel extends GridPane {

    private STDashboard parent;
    private ReadOnlyDoubleProperty width, height;

    public DashboardPanel(STDashboard parent, ApplicationContext ctx, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        this.parent = parent;
        this.ctx = ctx;
        this.width = width;
        this.height = height;
        prefWidthProperty().bind(width);
        prefHeightProperty().bind(height);
        setPadding(new Insets(5, 5, 5, 5));
        initComponents();
    }

    private ApplicationContext ctx;
    private BorderPane borderPane;
    private TabPane tabPane;
    private Tab subjectControlTab, accountTab, analyticsTab;
    private MenuBar appMenu;
    private Menu file, help, logout, extraHelp;
    private MenuItem visitWebsite, aboutHelp, fontHelp, topicHelp, analyticsHelp, accountInfoHelp, email, exit, sendNotification;

    private void initComponents(){
        borderPane = new BorderPane();

        tabPane = initTabPane(tabPane);
        tabPane.getTabs().addAll(getTabs());

        appMenu = initMenuBar(appMenu);

        borderPane.setTop(appMenu);
        borderPane.setCenter(tabPane);

        add(borderPane, 0, 0);
    }

    private MenuBar initMenuBar(MenuBar bar){
        if(bar == null){
            bar = new MenuBar();
        }

        file = initFileMenu(file);
        help = initHelpMenu(help);
        logout = initLogoutMenu(logout);

        bar.getMenus().addAll(file, help, logout);

        return bar;
    }

    private Menu initFileMenu(Menu menu){
        if(menu == null){
            menu = new Menu("File");
            email = new MenuItem("Email Admin");
            email.setOnAction((click) -> {
                try {
                    Desktop desktop = (Desktop.isDesktopSupported() ? Desktop.getDesktop() : null);
                    if(desktop != null){
                        if(desktop.isSupported(Desktop.Action.MAIL)){
                            URI mailUri = (ctx.getNetwork().getMailURI() != null ? ctx.getNetwork().getMailURI() : null);
                            if(mailUri != null){
                                desktop.mail(mailUri);
                            }
                        }
                    }
                }catch(IOException ex){}
            });
            sendNotification = new MenuItem("Send notification");
            sendNotification.setOnAction((click) -> {
                Optional<Pair<String, String>> dialog = ctx.createNotifierDialog().showAndWait();
                if(dialog.isPresent()){
                    String title = dialog.get().getKey(), description = dialog.get().getValue();
                    if(title != null && title.length() > 3 && description != null && description.length() > 3){
                        try {
                            NotifyTask task = new NotifyTask(ctx, title, description);
                            new Thread(task).start();
                        }catch(Exception ex){}
                    }
                }
            });
            exit = new MenuItem("Close");
            exit.setOnAction((click) -> {
                Optional<ButtonType> choice = ctx.createAlert("Close application", "Are you sure you wish to close Super Tutor?", Alert.AlertType.CONFIRMATION).showAndWait();
                if(choice != null && choice.get().equals(ButtonType.OK)){
                    System.exit(0);
                }
            });
            menu.getItems().addAll(sendNotification, email, exit);
        }
        return menu;
    }

    private Menu initLogoutMenu(Menu menu){
        if(menu == null){
            menu = new Menu();
            Text logout = new Text("Logout");
            menu.setGraphic(logout);
            logout.setOnMouseClicked((event) -> {
                ctx.getLibrary().resetLibrary();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new STLogin();
                        parent.dispose();
                    }
                });
            });
        }
        return menu;
    }

    private Menu initHelpMenu(Menu menu){
        if(menu == null){
            menu = new Menu("Help");
            extraHelp = new Menu("Extra Help");
            fontHelp = new MenuItem("Font");
            fontHelp.setOnAction((click) -> {
                ctx.createExpandibleAlert(Strings.FONT_HELP.getTitle(), "Click to expand to see detailed instructions", Strings.FONT_HELP.getHelpText(), Strings.FONT_HELP.getContent()).showAndWait();
            });
            topicHelp = new MenuItem("Topic/Subject uploading");
            topicHelp.setOnAction((click) -> {
                ctx.createExpandibleAlert(Strings.SUBJECT_TOPIC_HELP.getTitle(), "Steps to uploading a Subject/Topic", Strings.SUBJECT_TOPIC_HELP.getHelpText(), Strings.SUBJECT_TOPIC_HELP.getContent()).showAndWait();
            });
            analyticsHelp = new MenuItem("Analytics Help");
            analyticsHelp.setOnAction((click) -> {
                ctx.createAlert(Strings.ANALYTICS_HELP.getTitle(), Strings.ANALYTICS_HELP.getContent(), Alert.AlertType.INFORMATION).showAndWait();
            });
            accountInfoHelp = new MenuItem("Account Updating");
            accountInfoHelp.setOnAction((click) -> {
                ctx.createAlert(Strings.ACCOUNT_UPDATE_HELP.getTitle(), Strings.ACCOUNT_UPDATE_HELP.getContent(), Alert.AlertType.INFORMATION).showAndWait();
            });
            extraHelp.getItems().addAll(fontHelp, topicHelp, analyticsHelp, accountInfoHelp);

            aboutHelp = new MenuItem("About");
            aboutHelp.setOnAction((click) -> {
                ctx.createAlert(Strings.ABOUT_HELP.getTitle(), Strings.ABOUT_HELP.getContent(), Alert.AlertType.INFORMATION).show();
            });
            visitWebsite = new MenuItem("Visit website");
            visitWebsite.setOnAction((click) -> {
                ctx.getNetwork().openURLDefaultBrowser("http://www.envisiontechllc.org");
            });
            menu.getItems().addAll(extraHelp, visitWebsite, aboutHelp);
        }
        return menu;
    }

    private List<Tab> getTabs(){
        List<Tab> list = new ArrayList<>();

        subjectControlTab = new SubjectManagement(parent, width, height);
        analyticsTab = new AppAnalytics(width, height);
        accountTab = new AccountSettings(width, height);

        Collections.addAll(list, subjectControlTab, analyticsTab, accountTab);

        return list;
    }

    private TabPane initTabPane(TabPane pane){
        if(pane == null){
            pane = new TabPane();
        }

        pane.prefHeightProperty().bind(height);
        pane.prefWidthProperty().bind(width);
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        return pane;
    }

}
