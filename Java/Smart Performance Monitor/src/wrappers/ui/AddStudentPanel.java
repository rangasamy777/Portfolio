package wrappers.ui;

import components.InfoLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class AddStudentPanel extends JPanel {

    public AddStudentPanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        initialise();
    }

    private int width;
    private int height;

    private List<InfoLabel> labels;

    private InfoLabel studentID;
    private InfoLabel username;
    private InfoLabel password;
    private InfoLabel name;
    private InfoLabel address;
    private InfoLabel postCode;
    private InfoLabel mobile;
    private InfoLabel email;
    private InfoLabel access;

    private JButton submit;
    private JButton cancel;

    private void initialise(){
        cancel = new JButton("Cancel");
        submit = new JButton("Submit");

        labels = new ArrayList<>();

        studentID = new InfoLabel("Student ID: ", 9);
        username = new InfoLabel("Username: ", 20);
        password = new InfoLabel("Password: ", 20);
        access = new InfoLabel("Access Level: ", 3);
        name = new InfoLabel("Name: ", 20);
        address = new InfoLabel("Address: ", 20);
        postCode = new InfoLabel("Post Code: ", 10);
        email = new InfoLabel("Email: ", 20);
        mobile = new InfoLabel("Contact: ", 12);

        addLabelsForValidation();

        setLayout(new MigLayout());
        add(studentID, "cell 0 0");
        add(username, "cell 0 1");
        add(password, "cell 0 2");
        add(access, "cell 0 3");
        add(name, "cell 0 4");
        add(address, "cell 0 5");
        add(postCode, "cell 0 6");
        add(mobile, "cell 0 7");
        add(email, "cell 0 8");

        add(cancel, "cell 0 9");
        add(submit, "cell 1 9");
    }

    private void addLabelsForValidation(){
        labels.add(studentID);
        labels.add(username);
        labels.add(password);
        labels.add(access);
        labels.add(name);
        labels.add(address);
        labels.add(postCode);
        labels.add(email);
        labels.add(mobile);
    }

    public JButton getCancelBtn(){
        return this.cancel;
    }

    public JButton getSubmit(){
        return this.submit;
    }

    public String getStudentID(){
        return this.studentID.getDataField().getText();
    }

    public String getUsername(){
        return this.username.getDataField().getText();
    }

    public String getPassword(){
        return this.password.getDataField().getText();
    }

    public String getPersonsName(){
        return this.name.getDataField().getText();
    }

    public String getAddress(){
        return this.address.getDataField().getText();
    }

    public String getPostCode(){
        return this.postCode.getDataField().getText();
    }

    public String getMobile(){
        return this.mobile.getDataField().getText();
    }

    public String getEmail(){
        return this.email.getDataField().getText();
    }

    public String getAccess(){
        return this.access.getDataField().getText();
    }

    public List<InfoLabel> getLabels(){
        return this.labels;
    }
}
