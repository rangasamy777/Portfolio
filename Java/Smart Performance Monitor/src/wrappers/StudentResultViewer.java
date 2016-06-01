package wrappers;

import components.InfoLabel;
import components.ResultLabel;
import data.Module;
import data.Result;
import data.User;
import net.miginfocom.swing.MigLayout;
import utils.Database;
import utils.PerformanceTracker;
import wrappers.ui.PerformancePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class StudentResultViewer extends JFrame{

    public StudentResultViewer(User user){
        super("View Student");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.user = user;
        initComponents();
    }

    private final int FRAME_WIDTH = 520;
    private final int FRAME_HEIGHT = 640;

    private ResultsPanel panel;
    private Database db;
    private User user;


    private InfoLabel name;
    private InfoLabel address;
    private InfoLabel postCode;
    private InfoLabel email;
    private InfoLabel contact;


    private void initComponents(){
        db = Database.getInstance();

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        JPanel north = new JPanel();
        north.setLayout(new MigLayout());

        JPanel center = new JPanel();
        panel = new ResultsPanel(FRAME_WIDTH - 50, FRAME_HEIGHT - 50, user);
        center.add(panel);

        name = new InfoLabel("Name:", 30);
        address = new InfoLabel("Address: ", 28);
        postCode = new InfoLabel("Post Code:", 9);
        email = new InfoLabel("Email:", 20);
        contact = new InfoLabel("Mobile:", 11);

        north.add(name, "wrap");
        north.add(address, "wrap");
        north.add(postCode, "wrap");
        north.add(email, "wrap");
        north.add(contact, "wrap");

        main.add(north, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        setContentPane(main);
        setComponents();
    }

    private void setComponents(){
        String query = "SELECT * FROM UserDetails WHERE UserID='" + user.getUserID() + "'";

        try{
            if(db.executeQuery(query) != null){
                while(db.getResults().next()){
                    name.setDataField(db.getResults().getString(2));
                    address.setDataField(db.getResults().getString(3));
                    postCode.setDataField(db.getResults().getString(4));
                    email.setDataField(db.getResults().getString(5));
                    contact.setDataField(db.getResults().getString(6));
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private class ResultsPanel extends JPanel{

        public ResultsPanel(int frameWidth, int frameHeight, User user){
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
            this.user = user;
            setPreferredSize(new Dimension(frameWidth, frameHeight));
            setLayout(new BorderLayout());
            initialise();
        }

        private Database db;
        private User user;

        private java.util.List<Result> resultList;
        private java.util.List<Module> modules;

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

        private void initialise(){
            db = Database.getInstance();
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

            filterColumns = new Object[]{"Module Code", "Current %", "Assignment Count"};
            modelTwo = new DefaultTableModel(dbINFO, filterColumns);
            resultsFilterTable = new JTable(modelTwo);
            resultsFilterTable.setAutoCreateRowSorter(true);
            resultsFilterScroll = new JScrollPane(resultsFilterTable);
            resultsFilterScroll.setPreferredSize(new Dimension(frameWidth - 50, 150));
            center.add(resultsFilterScroll, "cell 0 1");

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
            if(resultList.size() > 0){
                for(Result res: resultList){
                    Object[] tempRow = new Object[]{res.getModuleCode(), res.getAssessmentName(), res.getResult()};
                    model.addRow(tempRow);
                }
            } else {
                Object[] row = new Object[]{"No results", "No Results", "No Results"};
                model.addRow(row);
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

}
