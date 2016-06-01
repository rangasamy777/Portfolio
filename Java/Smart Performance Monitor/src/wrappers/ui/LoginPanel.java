package wrappers.ui;

import data.User;
import security.Crypter;
import utils.Database;
import wrappers.ApplicationFrame;

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
public class LoginPanel extends JPanel{

    public LoginPanel(int frameWidth, int frameHeight){
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        setSize(frameWidth, frameHeight);
        initComponents();
    }

    private JLabel usernameLbl;
    private JLabel passwordLbl;
    private JTextField username;
    private JPasswordField password;
    private JButton loginBtn;
    private JButton clearBtn;

    private int frameWidth;
    private int frameHeight;

    private void initComponents(){
        usernameLbl = new JLabel("Username:");
        passwordLbl = new JLabel("Password:");
        username = new JTextField();
        password = new JPasswordField();
        loginBtn = new JButton("Login");
        clearBtn = new JButton("Clear");

        username.setPreferredSize(new Dimension(frameWidth - 100, 20));
        password.setPreferredSize(new Dimension(frameWidth - 100, 20));

        positionComponents();
    }

    private void positionComponents(){
        add(usernameLbl);
        add(username);
        add(passwordLbl);
        add(password);
        add(clearBtn);
        add(loginBtn);
    }

    public JButton getClearBtn(){
        return this.clearBtn;
    }

    public JButton getLoginBtn(){
        return this.loginBtn;
    }

    public JPasswordField getPassword(){
        return this.password;
    }

    public JTextField getUsername(){
        return this.username;
    }


}
