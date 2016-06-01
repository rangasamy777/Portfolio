package wrappers.ui;

import components.ResultLabel;
import data.Module;
import data.Result;
import data.User;
import net.miginfocom.swing.MigLayout;
import utils.Database;
import utils.PerformanceTracker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class PerformancePanel extends JPanel {

    public PerformancePanel(int frameWidth, int frameHeight){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(new BorderLayout());
        initialise();
    }

    private Database db;
    private User user;

    private List<Result> resultList;
    private List<Module> modules;

    private int frameWidth;
    private int frameHeight;

    private DefaultTableModel model;
    private JTable resultsTable;
    private JScrollPane scroll;
    private Object[][] dbInfo;
    private Object[] columns;

    private DefaultTableModel modelTwo;
    private JTable resultsFilterTable;
    private JScrollPane resultsFilterScroll;
    private Object[][] dbINFO;
    private Object[] filterColumns;

    private JPanel north;
    private JPanel center;

    private ResultLabel average;

    private void initialise(){
        db = Database.getInstance();
        user = User.getInstance();
        resultList = new ArrayList<>();
        modules = new ArrayList<>();

        genTable();
        initComponents();
    }

    private void initComponents(){
        north = new JPanel();
        north.add(scroll);

        center = new JPanel();
        center.setBorder(new TitledBorder("Results Breakdown"));
        center.setLayout(new MigLayout());
        average = new ResultLabel("Average Grade Attained: ", String.valueOf(PerformanceTracker.getAverage(resultList)) + "%");
        center.add(average, "cell 0 1");

        filterColumns = new Object[]{"Module Code", "Average Grade", "Assignment Count"};
        modelTwo = new DefaultTableModel(dbINFO, filterColumns);
        resultsFilterTable = new JTable(modelTwo);
        resultsFilterTable.setAutoCreateRowSorter(true);
        resultsFilterScroll = new JScrollPane(resultsFilterTable);
        resultsFilterScroll.setPreferredSize(new Dimension(frameWidth - 50, 150));
        center.add(resultsFilterScroll, "cell 0 2");

        filterToModules();
        genFilterTable();

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

    }

    private void genFilterTable(){
        for(Module m: modules){
            Object[] row = new Object[]{m.getModuleName(), PerformanceTracker.filterModuleResults(m), m.getResults().size()};
            modelTwo.addRow(row);
        }
    }

    private void filterToModules(){
        for(Result result: resultList){
            if(!containsModule(result.getModuleCode())){
                System.out.println("Adding: " + result.getModuleCode());
                modules.add(new Module(result.getModuleCode(), result.getResult()));
            } else {
                System.out.println("Adding: " + result.getResult());
                modules.get(modules.indexOf(getModule(result.getModuleCode()))).addResult(result.getResult());
            }
        }
    }

    private boolean containsModule(String moduleCode){
        boolean found = false;

        for(Module mod: modules){
            if(mod.getModuleName().equalsIgnoreCase(moduleCode)){
                found = true;
            }
        }
        return found;
    }

    private Module getModule(String moduleCode){
        for(Module module: modules){
            if(module.getModuleName().equalsIgnoreCase(moduleCode)){
                return module;
            }
        }
        return null;
    }

    private void genTable(){
        columns =  new Object[]{"Module Code", "Assessment Name", "Result"};
        model = new DefaultTableModel(dbInfo, columns);
        resultsTable = new JTable(model);
        resultsTable.setAutoCreateRowSorter(true);

        cacheData();
        populateTable();

        scroll = new JScrollPane(resultsTable);
        scroll.setPreferredSize(new Dimension(frameWidth - 50, 150));
    }

    private void populateTable(){
        for(Result res: resultList){
            Object[] tempRow = new Object[]{res.getModuleCode(), res.getAssessmentName(), res.getResult()};
            model.addRow(tempRow);
        }
    }

    private void cacheData(){
        String query = "SELECT * FROM Results WHERE UserID='" + user.getUserID() + "'";

        try{
            if(db.executeQuery(query) != null){
                while(db.getResults().next()){
                    resultList.add(new Result(db.getResults().getString(1), db.getResults().getString(2), db.getResults().getString(3), db.getResults().getInt(4)));
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
