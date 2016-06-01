package wrappers;

import data.User;
import security.Crypter;
import utils.Database;
import wrappers.ui.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class Login extends JFrame {

    public Login(){
        super("Smart Monitor Login");
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 120;
    private LoginPanel loginPanel;
    private Database db;

    private void initComponents(){

        loginPanel = new LoginPanel(FRAME_WIDTH, FRAME_HEIGHT);
        setContentPane(loginPanel);

        db = Database.getInstance();

        clearBtn = loginPanel.getClearBtn();
        loginBtn = loginPanel.getLoginBtn();
        username = loginPanel.getUsername();
        password = loginPanel.getPassword();

        addListeners();
    }

    private JButton clearBtn;
    private JButton loginBtn;
    private JTextField username;
    private JPasswordField password;


    private void addListeners(){
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                password.setText("");
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    login(username.getText(), new String(password.getPassword()));
                } catch(SQLException ex){
                    new JOptionPane("SQL Error: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void login(String userID, String password) throws SQLException {
        String query = "SELECT * FROM Login WHERE UserID='" + userID.toUpperCase() + "'";

        if(db.executeQuery(query) != null){
            ResultSet results = db.getResults();

            if(db.getResults().next()){
                if(Crypter.decrypt(userID, password)){
                    //System.out.println("Logged in!");
                    //System.out.println("UserID: " + results.getString(1));
                    //System.out.println("Username: " + results.getString(2));
                    //System.out.println("Access: " + results.getInt(4));

                    User.setInstance(results.getString(1), results.getString(2), results.getInt(4));
                    new ApplicationFrame().setVisible(true);
                    setVisible(false);
                }
            } else {
                System.out.println("Incorrect");
                JOptionPane.showMessageDialog(new JOptionPane(), "Incorrect Username", "SPM Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String a[]){
        final Login login = new Login();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                login.setVisible(true);
            }
        });
    }
}
