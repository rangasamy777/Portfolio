package org.supertutor.ui.tabs;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.supertutor.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emile on 06/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class AppAnalytics extends Tab {

    private ReadOnlyDoubleProperty width, height;

    public AppAnalytics(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        super("Application Analytics");
        this.width = width;
        this.height = height;
        this.setGraphic(new ImageView(new Image("file:resources/analytics.png")));
        this.initContent();
    }

    private ApplicationContext ctx;
    private GridPane contentPane;
    private PieChart userBaseChart;

    private void initContent(){
        ctx = ApplicationContext.getContext();

        contentPane = ctx.initGridPane(contentPane, width, height, new Insets(10, 10, 10, 10));

        userBaseChart = initUserbaseChart(userBaseChart);
        final Label caption = new Label();
        addPiechatCaption(caption);

        contentPane.add(userBaseChart, 0, 0);

        setContent(contentPane);
    }

    private PieChart initUserbaseChart(PieChart chart){
        if(chart == null){
            chart = new PieChart();
            ctx.getNetwork().getUserbase(chart);
            chart.prefWidthProperty().bind(width);
            chart.prefHeightProperty().bind(height);
        }
        return chart;
    }

    private void addPiechatCaption(Label caption){
        for(PieChart.Data data: userBaseChart.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (event -> {
                caption.setTextFill(Color.WHITE);
                caption.setStyle("-fx-font: 24 arial;");
                caption.setTranslateX(event.getSceneX());
                caption.setTranslateY(event.getSceneY());
                caption.setText(data.getPieValue() + " " + data.getName());
            }));
        }
    }
}
