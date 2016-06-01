package components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class ResultLabel extends JPanel {

    public ResultLabel(String title, String result){
        this.titleLbl = new JLabel(title);
        this.resultLbl = new JLabel(result);

        Box box = Box.createHorizontalBox();
        box.add(this.titleLbl);
        box.add(this.resultLbl);
        add(box);
    }

    private JLabel titleLbl;
    private JLabel resultLbl;

    public JLabel getResultLbl(){
        return this.resultLbl;
    }
}
