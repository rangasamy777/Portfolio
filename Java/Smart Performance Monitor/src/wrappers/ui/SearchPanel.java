package wrappers.ui;

import data.User;
import utils.Database;
import wrappers.StudentResultViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class SearchPanel extends JPanel {

    public SearchPanel(int frameWidth, int frameHeight){
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        setSize(frameWidth, frameHeight);
        initialise();
        addListener();
    }

    private int frameWidth;
    private int frameHeight;

    private Database db;

    private JLabel searchLbl;
    private JTextField keywords;
    private JTable resultsTable;
    private DefaultTableModel model;
    private JScrollPane scroll;
    private Object[][] data;
    private Object[] columns;

    private JButton search;

    private List<User> userList;
    private List<User> resultsList;

    private void initialise(){
        db = Database.getInstance();
        userList = new ArrayList<>();
        resultsList = new ArrayList<>();

        searchLbl = new JLabel("Search:");
        keywords = new JTextField();
        keywords.setPreferredSize(new Dimension(frameWidth - 150, 20));
        keywords.setText("Enter search terms");
        search = new JButton("Search");

        loadUserList();
        genTable();

        add(searchLbl);
        add(keywords);
        add(search);
        add(scroll);
    }

    private void genTable(){
        columns = new Object[]{"Student Name", "Student ID"};
        model = new DefaultTableModel(data, columns);

        resultsTable = new JTable(model);
        resultsTable.setAutoCreateRowSorter(true);

        cacheAllStudents();

        scroll = new JScrollPane(resultsTable);
        scroll.setPreferredSize(new Dimension(frameWidth, frameHeight - 90));
    }

    private void cacheAllStudents(){
        Thread grabAllInfo = new Thread(new Runnable() {
            @Override
            public void run() {
                model = new DefaultTableModel(data, columns);
                resultsTable.setModel(model);

                for(User user: userList){
                    Object[] row = new Object[]{user.getUsername(), user.getUserID()};
                    model.addRow(row);
                }
            }
        });
        grabAllInfo.start();
    }

    private void cacheData(){
        Thread grabInfo = new Thread(new Runnable(){
            @Override
            public void run(){
                model = new DefaultTableModel(data, columns);
                resultsTable.setModel(model);

                for(User user: resultsList){
                    Object[] row = new Object[]{user.getUsername(), user.getUserID()};
                    model.addRow(row);
                }

                if(model.getRowCount() == 0){
                    Object[] row = new Object[]{"No Results", "No Results"};
                    model.addRow(row);
                }
            }
        });
        grabInfo.start();
    }

    private void loadUserList(){
        String query = "SELECT Name,UserID FROM UserDetails";

        try{
            if(db.executeQuery(query) != null){
                while(db.getResults().next()){
                    userList.add(new User(db.getResults().getString(2), db.getResults().getString(1), 0));
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private void addListener(){
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("UserList Size: " + userList.size());
                System.out.println("Keyword: " + keywords.getText());

                for (User user : userList) {
                    System.out.println("User: " + user.getUsername());
                    if (user.getUsername().contains(keywords.getText())) {
                        System.out.println("Matches!");
                        resultsList.add(new User(user.getUserID(), user.getUsername(), 0));
                    }
                }

                cacheData();
            }
        });
        keywords.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                resultsList.clear();

                System.out.println("UserList Size: " + userList.size());
                System.out.println("Keyword: " + keywords.getText());

                for(User user: userList){
                    System.out.println("User: " + user.getUsername());
                    if(user.getUsername().contains(keywords.getText())){
                        System.out.println("Matches!");
                        resultsList.add(new User(user.getUserID(), user.getUsername(), 0));
                    }
                }

                cacheData();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        resultsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = resultsTable.rowAtPoint(e.getPoint());
                int column = resultsTable.columnAtPoint(e.getPoint());
                try {
                    String query = "SELECT * From UserDetails WHERE UserID='" + getSelectedUser(model.getValueAt(row, column).toString()).getUserID() + "'";
                    if(db.executeQuery(query) != null){
                        while(db.getResults().next()){
                            final int tempRow = row;
                            final int tempColumn = column;
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    new StudentResultViewer(getSelectedUser(model.getValueAt(tempRow, tempColumn).toString())).setVisible(true);
                                }
                            });
                        }
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private User getSelectedUser(String search){
        for(User user: userList){
            if(user.getUserID().equalsIgnoreCase(search) || user.getUsername().equalsIgnoreCase(search)){
                System.out.println("Found: " + user.getUserID());
                return user;
            }
        }
        return null;
    }
}
