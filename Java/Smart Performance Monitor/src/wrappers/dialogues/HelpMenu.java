package wrappers.dialogues;

import javax.swing.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class HelpMenu extends JOptionPane {

    public HelpMenu(){
        initialise();
        showMessageDialog(new JOptionPane(), scroll, "SPM Help", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private JScrollPane scroll;
    private JTextArea area;
    private Icon icon;
    private ClassLoader cL;

    private void initialise(){
        cL = getClass().getClassLoader();
        area = new JTextArea();
        icon = new ImageIcon(cL.getResource("help_icon.png"));

        initText();
    }

    private void initText(){
        area.setEditable(false);

        area.append("SPM Help\n\n" +
        "Access Legend: \n" + "1: Student\n" + "2: Tutor\n" + "3: Administrator\n\n"
        + "Data that is not registered will not be displayed in the corresponding tables");
        scroll = new JScrollPane(area);
        add(scroll);
    }

    public static void main(String a[]){
        new HelpMenu();
    }
}
