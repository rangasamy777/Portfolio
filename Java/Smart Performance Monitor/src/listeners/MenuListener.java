package listeners;

import wrappers.dialogues.HelpMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class MenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Exit":
                closeApplication();
                break;
            case "Help":
                openHelpMenu();
                break;
        }
    }

    private void openHelpMenu(){
        new HelpMenu();
    }

    private void closeApplication(){
        System.exit(0);
    }

}
