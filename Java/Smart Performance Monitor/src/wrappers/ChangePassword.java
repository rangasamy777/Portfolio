package wrappers;

import data.User;
import security.Crypter;
import utils.Database;
import utils.Validator;
import wrappers.ui.ChangePasswordPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class ChangePassword extends JFrame {

    public ChangePassword(){
        super("Change Password");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 150;

    private ChangePasswordPanel panel;
    private User user;
    private Database db;

    private void initComponents(){
        panel = new ChangePasswordPanel(FRAME_WIDTH, FRAME_HEIGHT);
        db = Database.getInstance();
        user = User.getInstance();

        setContentPane(panel);

        clearBtn = panel.getClearBtn();
        changeBtn = panel.getChangeBtn();
        oldPass = panel.getOldPass();
        newPass = panel.getNewPass();
        confirmNewPass = panel.getConfirmNewPass();

        addListeners();
    }

    private JButton clearBtn;
    private JButton changeBtn;
    private JPasswordField oldPass;
    private JPasswordField newPass;
    private JPasswordField confirmNewPass;

    private void addListeners(){
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldPass.setText("");
                confirmNewPass.setText("");
                newPass.setText("");
            }
        });
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword(new String(oldPass.getPassword()), new String(newPass.getPassword()), new String(confirmNewPass.getPassword()));
            }
        });
    }

    private void changePassword(String oldPass, String newPass, String confirm){
        if(Validator.validatePasswords(panel.getPassList())){
            if(newPass.equals(confirm)){
                String updateQuery = "UPDATE Login SET Password='" + Crypter.MD5(newPass) + "' WHERE UserID='" + user.getUserID() + "'";
                String matchPass = "SELECT Password FROM Login WHERE UserID='" + user.getUserID() + "'";

                System.out.println("Old Pass: " + oldPass);
                System.out.println("New Pass: " + newPass);
                System.out.println("Confirm Pass: " + confirm);
                System.out.println("User ID: " + user.getUserID());
                System.out.println("New Hash: " + Crypter.MD5(newPass));
                System.out.println("Update Query: " + updateQuery);

                try{
                    if(db.executeQuery(matchPass) != null){
                        if(Crypter.decrypt(user.getUserID(), oldPass)){
                            if(db.executeUpdate(updateQuery) != -1){
                                db.getResults().next();
                                JOptionPane.showMessageDialog(new JOptionPane(), "Successfully Changed password", "SPM Success", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(new JOptionPane(), "Failed to change Password", "SPM Error" ,JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(new JOptionPane(), "Your old password does not match the one in the DB");
                        }
                    }
                } catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(new JOptionPane(), "Check that you have entered data for all the fields", "SPM Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String a[]){
        new ChangePassword().setVisible(true);
    }
}
