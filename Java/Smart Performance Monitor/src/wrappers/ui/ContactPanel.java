package wrappers.ui;

import com.sun.codemodel.internal.JOp;
import protocols.MailProtocol;
import utils.Database;

import javax.mail.internet.AddressException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class ContactPanel extends JPanel {

    public ContactPanel(int frameWidth, int frameHeight){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        initialise();
        addListeners();
    }

    private int frameWidth;
    private int frameHeight;

    private MailProtocol mailController;
    private Database db;

    private JLabel studentLbl;
    private JTextField studentEmail;
    private JEditorPane emailPane;
    private JScrollPane scroll;

    private JButton reset;
    private JButton send;

    private void initialise(){
        mailController = new MailProtocol();
        db = Database.getInstance();

        studentLbl = new JLabel("Student:");
        studentEmail = new JTextField();
        studentEmail.setPreferredSize(new Dimension(frameWidth - 50, 20));
        studentEmail.setText("Enter the student ID/Email");

        emailPane = new JEditorPane();
        emailPane.setPreferredSize(new Dimension(frameWidth - 20, frameHeight - 120));
        scroll = new JScrollPane(emailPane);

        add(studentLbl);
        add(studentEmail);
        add(scroll);

        reset = new JButton("Reset");
        send = new JButton("Send");

        add(reset);
        add(send);
    }

    private void addListeners(){
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailPane.setText("");
            }
        });
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mailStudent(studentEmail.getText());
            }
        });
    }

    private void mailStudent(String userID){
        String query =  "SELECT Email FROM UserDetails WHERE UserID='" + userID.toUpperCase() + "'";

        try{
            if(db.executeQuery(query) != null){
                while(db.getResults().next()){
                    String email = db.getResults().getString(1);
                    System.out.println("Email: " + email);
                    mailController.addRecipient(email);
                    mailController.sendMail("", emailPane);
                }
            } else {
                JOptionPane.showMessageDialog(new JOptionPane(), "No students with that email found", "SPM Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(SQLException ex){
            ex.getMessage();
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }

}
