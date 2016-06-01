package wrappers.ui;

import javax.swing.*;

import data.User;
import listeners.MenuListener;
import java.awt.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class ApplicationPanel extends JPanel {

    public ApplicationPanel(final int WIDTH, final int HEIGHT){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setLayout(new BorderLayout());
        initialise();
    }

    private int WIDTH;
    private int HEIGHT;

    private User user;
    private MenuListener controller;
    private ClassLoader cL;

    private JMenuBar menu;
    private JMenu file;

    private JMenuItem help;
    private JMenuItem about;
    private JMenuItem exit;

    private JTabbedPane tabController;

    private void initialise(){
        controller = new MenuListener();
        cL = getClass().getClassLoader();
        user = user.getInstance();

        initMenuBar();
        addActions();
        initGeneralInfo();
        setTabControllerIcons();
    }

    private void initMenuBar(){
        menu = new JMenuBar();

        file = new JMenu("File");
        exit = new JMenuItem("Exit");

        file.add(exit);

        help = new JMenuItem("Help");
        about = new JMenuItem("About");

        menu.add(file);
        menu.add(help);

        add(menu, BorderLayout.NORTH);
    }

    private void addActions(){
        help.addActionListener(controller);
        about.addActionListener(controller);
        exit.addActionListener(controller);
    }

    private void initGeneralInfo(){
        tabController = new JTabbedPane();
        tabController.add(new DetailsPanel(WIDTH - 10, HEIGHT), "Home");
        tabController.add(new TimetablePanel(WIDTH - 50, HEIGHT - 50), "Timetable");
        tabController.add(new AttendancePanel(WIDTH - 50, HEIGHT - 50), "Attendance");
        tabController.add(new PerformancePanel(WIDTH - 50, HEIGHT - 50), "Performance");

        if(user != null && (user.isAdmin() || user.isTutor())){
            tabController.add(new ContactPanel(WIDTH - 50, HEIGHT - 50), "Contact Student");
            tabController.add(new SearchPanel(WIDTH - 50, HEIGHT - 50), "Search");
        }

        add(tabController, BorderLayout.CENTER);
    }

    private void setTabControllerIcons(){
        tabController.setIconAt(0, new ImageIcon(cL.getResource("home.png")));
        tabController.setIconAt(1, new ImageIcon(cL.getResource("timetable.png")));
        tabController.setIconAt(2, new ImageIcon(cL.getResource("attendance.png")));
        tabController.setIconAt(3, new ImageIcon(cL.getResource("performance.png")));

        if(user != null && (user.isAdmin() || user.isTutor())){
            tabController.setIconAt(4, new ImageIcon(cL.getResource("contact.png")));
            tabController.setIconAt(5, new ImageIcon(cL.getResource("search.png")));
        }
    }
}
