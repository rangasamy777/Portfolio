package wrappers.ui;

import components.InfoLabel;
import data.User;
import net.miginfocom.swing.MigLayout;
import utils.Database;
import wrappers.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class DetailsPanel extends JPanel {

    public DetailsPanel(int frameWidth, int frameHeight){
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        initialise();
        initMenu();
        setDataFields();
    }

    private User user;
    private Database db;

    private int frameWidth;
    private int frameHeight;

    private ClassLoader cL;

    private JLabel studentImage;
    private InfoLabel name;
    private InfoLabel address;
    private InfoLabel postCode;
    private InfoLabel email;
    private InfoLabel contact;

    private void initialise(){
        cL = getClass().getClassLoader();
        user = User.getInstance();
        db = Database.getInstance();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("User Details:"));

        JPanel leftPane = new JPanel();
        studentImage = new JLabel();
        studentImage.setPreferredSize(new Dimension(120, 120));
        studentImage.setBorder(new LineBorder(Color.RED));
        leftPane.add(studentImage);
        add(leftPane, BorderLayout.WEST);

        JPanel detailsPane = new JPanel();
        detailsPane.setLayout(new MigLayout());
        detailsPane.setAlignmentX(LEFT_ALIGNMENT);
        detailsPane.setAlignmentY(TOP_ALIGNMENT);

        name = new InfoLabel("Name:", 30);
        detailsPane.add(name, "cell 0 1");

        address = new InfoLabel("Address: ", 28);
        detailsPane.add(address, "cell 0 2");

        postCode = new InfoLabel("Post Code:", 9);
        detailsPane.add(postCode, "cell 0 3");

        email = new InfoLabel("Email:", 20);
        detailsPane.add(email, "cell 0 4");

        contact = new InfoLabel("Mobile:", 11);
        detailsPane.add(contact, "cell 0 5");

        add(detailsPane, BorderLayout.CENTER);
    }

    private JButton password;
    private JButton addStudent;
    private JButton addTimetable;
    private JButton addResult;
    private JButton addAttendance;

    private void initMenu(){
        JPanel south = new JPanel();

        String title = user != null ? user.getUsername() : "Testing Admin";

        south.setBorder(new TitledBorder("Logged in as: " + title));

        password = new JButton("Security");
        password.setIcon(new ImageIcon(cL.getResource("password.png")));
        south.add(password);

        if(user.isAdmin()){
            addStudent = new JButton("Add Student");
            addTimetable = new JButton("Add Timetable");
            addAttendance = new JButton("Add Attendance");
            addResult = new JButton("Add Result");
            addStudent.setIcon(new ImageIcon(cL.getResource("student.png")));
            addTimetable.setIcon(new ImageIcon(cL.getResource("timetable.png")));
            addAttendance.setIcon(new ImageIcon(cL.getResource("attendance.png")));
            addResult.setIcon(new ImageIcon(cL.getResource("performance.png")));

            south.add(addStudent);
            south.add(addTimetable);
            south.add(addAttendance);
            south.add(addResult);
        }

        add(south, BorderLayout.SOUTH);
        addListener();
    }

    private void setDataFields(){
        String userID = user != null ? user.getUserID() : "Testing ADMIN" ;

        String query = "SELECT * FROM UserDetails WHERE UserID='" + userID + "'";

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
            System.out.println(ex.getMessage());
        }
    }

    private void addListener(){
        password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new ChangePassword().setVisible(true);
                    }
                });
            }
        });
        if(user.isAdmin()){
            addStudent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new AddStudentFrame().setVisible(true);
                        }
                    });
                }
            });
            addTimetable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new AddTimeTable().setVisible(true);
                        }
                    });
                }
            });
            addAttendance.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new AddAttendance().setVisible(true);
                        }
                    });
                }
            });
            addResult.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new AddResult().setVisible(true);
                        }
                    });
                }
            });
        }
    }
}
