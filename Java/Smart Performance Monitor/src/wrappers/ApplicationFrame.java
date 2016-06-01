package wrappers;

import wrappers.ui.ApplicationPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class ApplicationFrame extends JFrame {

    public ApplicationFrame(){
        super("Smart Performance Monitor");
        initialise();
    }

    private ApplicationPanel appPanel;

    private final int WIDTH = 720;
    private final int HEIGHT = 480;

    private void initialise(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);

        appPanel = new ApplicationPanel(WIDTH, HEIGHT);
        setContentPane(appPanel);
    }

    public static void main(String a[]){
        ApplicationFrame app = new ApplicationFrame();
        app.setVisible(true);
    }
}
