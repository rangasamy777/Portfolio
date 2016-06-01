package org.supertutor.internal.network.tasks;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * Created by Emile on 07/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class UserbaseTask implements Runnable {

    private PieChart chart;
    private long gceStudent, gcseStudent, undergrad, postgrad, employed, unemployed;
    private int total;

    public UserbaseTask(PieChart chart, long gceStudent, long gcseStudent, long undergrad, long postgrad, long employed, long unemployed){
        this.chart = chart;
        this.gceStudent = gceStudent;
        this.employed = employed;
        this.gcseStudent = gcseStudent;
        this.undergrad = undergrad;
        this.employed = employed;
        this.postgrad = postgrad;
        this.unemployed = unemployed;
        this.total = (int)(gcseStudent + undergrad + postgrad + gceStudent + employed + unemployed);
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                        new PieChart.Data("GCE Student", (int) gceStudent),
                        new PieChart.Data("GCSE Student", (int)gcseStudent),
                        new PieChart.Data("Undergraduate", (int)undergrad),
                        new PieChart.Data("Post graduate", (int)postgrad),
                        new PieChart.Data("Unemployed", (int)unemployed));
                        new PieChart.Data("Employed", (int)employed);
                chart.setData(list);
                chart.setLegendVisible(true);
                chart.setLabelsVisible(true);
                chart.setTitle("Proportion of Users \n       " +
                        "[" + total + " users]");
            }
        });
    }
}
