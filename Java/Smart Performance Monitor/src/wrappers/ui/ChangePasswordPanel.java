package wrappers.ui;

import security.Crypter;
import utils.Database;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class ChangePasswordPanel extends JPanel {

    public ChangePasswordPanel(int frameWidth, int frameHeight){
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        setSize(frameWidth, frameHeight);
        initComponents();
    }

    private Database db;

    private JLabel oldPassLbl;
    private JLabel newPassLbl;
    private JLabel confirmNewPassLbl;
    private List<JPasswordField> passList;
    private JPasswordField oldPass;
    private JPasswordField newPass;
    private JPasswordField confirmNewPass;
    private JButton changeBtn;
    private JButton clearBtn;

    private int frameWidth;
    private int frameHeight;

    private void initComponents(){
        db = Database.getInstance();
        passList = new ArrayList<>();

        oldPassLbl = new JLabel("Old Password:");
        newPassLbl = new JLabel("New Password:");
        confirmNewPassLbl = new JLabel("Confirm Password:");

        oldPass = new JPasswordField();
        newPass = new JPasswordField();
        confirmNewPass = new JPasswordField();

        addLabelsForValidation();

        changeBtn = new JButton("Change");
        clearBtn = new JButton("Clear");

        oldPass.setPreferredSize(new Dimension(frameWidth - 100, 20));
        newPass.setPreferredSize(new Dimension(frameWidth - 140, 20));
        confirmNewPass.setPreferredSize(new Dimension(frameWidth - 150, 20));

        positionComponents();
    }

    private void addLabelsForValidation(){
        passList.add(oldPass);
        passList.add(newPass);
        passList.add(confirmNewPass);
    }

    private void positionComponents(){
        add(oldPassLbl);
        add(oldPass);
        add(newPassLbl);
        add(newPass);
        add(confirmNewPassLbl);
        add(confirmNewPass);
        add(clearBtn);
        add(changeBtn);
    }

    public JButton getClearBtn(){
        return this.clearBtn;
    }

    public JButton getChangeBtn(){
        return this.changeBtn;
    }

    public JPasswordField getOldPass(){
        return this.oldPass;
    }

    public JPasswordField getNewPass(){
        return this.newPass;
    }

    public JPasswordField getConfirmNewPass(){
        return this.confirmNewPass;
    }

    public List<JPasswordField> getPassList(){
        return this.passList;
    }
}
