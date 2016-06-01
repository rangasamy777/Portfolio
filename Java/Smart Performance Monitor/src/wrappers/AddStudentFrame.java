package wrappers;

import data.User;
import security.Crypter;
import utils.Database;
import utils.Validator;
import wrappers.ui.AddStudentPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class AddStudentFrame extends JFrame{

    public AddStudentFrame(){
        super("Add Student");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private final int FRAME_WIDTH = 520;
    private final int FRAME_HEIGHT = 480;

    private AddStudentPanel panel;
    private Database db;
    private User user;

    private void initComponents(){
        panel = new AddStudentPanel(FRAME_WIDTH - 200, FRAME_HEIGHT - 50);
        db = Database.getInstance();
        user = User.getInstance();

        setContentPane(panel);

        addListeners();
    }

    private void addListeners(){
        panel.getCancelBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        panel.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String loginParams = "'" + panel.getStudentID().toUpperCase() + "','" + panel.getUsername() + "','" + Crypter.MD5(panel.getPassword()) + "','" + panel.getAccess() + "'";
                    String infoParams = "'" + panel.getStudentID().toUpperCase() + "','" + panel.getPersonsName() + "','" + panel.getAddress() + "','" + panel.getPostCode()
                            + "','" + panel.getEmail() + "','" + panel.getMobile() + "'";

                    final String queryLogin = "INSERT INTO Login VALUES (" + loginParams + ")";
                    final String queryDetails = "INSERT INTO UserDetails VALUES (" + infoParams + ")";

                    System.out.println("Login: " + queryLogin);
                    System.out.println("Details: " + queryDetails);

                    if(Validator.validate(panel.getLabels())){
                        Thread login = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.execute(queryLogin);
                            }
                        });

                        Thread details = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.execute(queryDetails);
                                JOptionPane.showMessageDialog(new JOptionPane(), "User added successfully!", "SPM Info", JOptionPane.INFORMATION_MESSAGE);
                            }
                        });
                        login.start();
                        details.start();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(new JOptionPane(), "Check that you have entered data for all the fields", "SPM Error", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });
    }
}
