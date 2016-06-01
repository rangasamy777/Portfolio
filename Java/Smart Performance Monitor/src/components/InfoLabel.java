package components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class InfoLabel extends JPanel {

    public InfoLabel(String title, int fieldWidth){
        this.title = new JLabel(title);
        this.field = new JTextField(fieldWidth);

        Box box = Box.createHorizontalBox();
        box.add(this.title);
        box.add(this.field);
        add(box);
    }

    private JLabel title;
    private JTextField field;

    public JTextField getDataField(){
        return this.field;
    }

    public void setDataField(String data){
        this.field.setText(data);
    }
}
