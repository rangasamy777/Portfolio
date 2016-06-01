package wrappers.ui;

import data.User;
import utils.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class AttendancePanel extends JPanel {

    public AttendancePanel(int frameWidth, int frameHeight){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        initialise();
    }

    private int frameWidth;
    private int frameHeight;

    private Database db;
    private User user;

    private DefaultTableModel model;
    private JTable timetable;
    private JScrollPane scroll;
    private Object[][] dbInfo;
    private Object[] columns;

    private void initialise(){
        db = Database.getInstance();
        user = User.getInstance();

        columns = new Object[]{"Module Code", "Date", "Present"};

        model = new DefaultTableModel(dbInfo, columns);

        loadDBData();

        timetable = new JTable(model);
        timetable.setAutoCreateRowSorter(true);

        scroll = new JScrollPane(timetable);
        scroll.setPreferredSize(new Dimension(frameWidth - 20, frameHeight - 90));

        add(scroll);
    }

    private void loadDBData(){
        String query = "SELECT * FROM Attendance WHERE UserID='" + user.getUserID() + "'";

        try{
            if(db.executeQuery(query) != null){
                while(db.getResults().next()){
                    Object[] row = new Object[]{db.getResults().getString(2), db.getResults().getString(3), db.getResults().getString(4)};
                    model.addRow(row);
                }
            }
        } catch(SQLException ex){
            ex.getMessage();
        }
    }
}
