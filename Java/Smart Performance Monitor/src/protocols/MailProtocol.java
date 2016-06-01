package protocols;

import data.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2014 Etheon Solutions
 */
public class MailProtocol {

    public MailProtocol(){
        emailProperties = new Properties();
        initEmailProperties();
    }

    private String USERNAME = "divinity@rspite.com";
    private String PASSWORD = "paladin12";
    private String SSL_HOST = "s2.dedicatedpanel.net";
    private String SSL_PORT = "465";
    private String NSL_HOST = "mail.rspite.com";
    private String NSL_PORT = "25";
    private Properties emailProperties;
    private Session session;
    private Message message;
    private Address[] recipients;

    private void initEmailProperties(){
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", SSL_HOST);
        emailProperties.put("mail.smtp.port", SSL_PORT);

        session = Session.getInstance(emailProperties,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }

        });
    }

    public Address[] addRecipient(String contact) throws AddressException {
        recipients = new Address[1];

        recipients[0] = new InternetAddress(contact);

        return recipients;
    }

    public void sendMail(String subject, JEditorPane area){

        try{
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setSubject(subject);
            message.setContent(area.getText(), "text/html; charset=utf-8");

            for(int i = 0; i < recipients.length; i++) {
                message.setRecipient(Message.RecipientType.TO, recipients[i]);
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: " + area.getText());
                Transport.send(message);
            }

            JOptionPane.showMessageDialog(new JOptionPane(), "Email has been sent successfully to: " + recipients.length + " recipients.", "Email Update!", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(new JOptionPane(), "Message not sent: " + e.getMessage(), "Email Update!", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
    }
}
