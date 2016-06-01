package wrappers;

import components.InfoLabel;
import net.miginfocom.swing.MigLayout;
import utils.Database;
import utils.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class AddResult extends JFrame {

    public AddResult(){
        super("Add Result Entry");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialise();
    }

    private final int FRAME_WIDTH = 380;
    private final int FRAME_HEIGHT = 280;

    private Database db;
    private AddResultPanel panel;

    private void initialise(){
        db = Database.getInstance();
        panel = new AddResultPanel(FRAME_WIDTH - 50, FRAME_HEIGHT - 60);

        setContentPane(panel);

        addListeners();
    }

    private void addListeners(){
        panel.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInfo();
                setVisible(false);
            }
        });
        panel.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private void addInfo(){
        String params = "'" + panel.getUserID().toUpperCase() + "','" + panel.getModule() + "','" + panel.getAssessmentName() + "','" + panel.getResult() + "'";
        String insert = "INSERT INTO Results VALUES(" + params + ")";

        System.out.println("Insert: " + insert);

        if(Validator.validate(panel.getLabels())){
            if(db.execute(insert)){
                JOptionPane.showMessageDialog(new JOptionPane(), "Successfully added!", "SPM Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(new JOptionPane(), "Check that you have entered data for all the fields", "SPM Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class AddResultPanel extends JPanel{

        public AddResultPanel(int width, int height){
            setPreferredSize(new Dimension(width, height));
            setLayout(new MigLayout());
            initLabels();
        }

        private List<InfoLabel> labels;

        private InfoLabel userID;
        private InfoLabel module;
        private InfoLabel assessmentName;
        private InfoLabel result;

        private JButton cancel;
        private JButton submit;

        private void initLabels(){
            labels = new ArrayList<>();

            userID = new InfoLabel("UserID:", 9);
            module = new InfoLabel("Module Code:", 15);
            assessmentName = new InfoLabel("Assessment Name:", 10);
            result = new InfoLabel("Result:", 8);

            labels.add(userID);
            labels.add(module);
            labels.add(assessmentName);
            labels.add(result);

            add(userID, "cell 0 0");
            add(module, "cell 0 1");
            add(assessmentName, "cell 0 2");
            add(result, "cell 0 3");

            cancel = new JButton("Cancel");
            submit = new JButton("Submit");

            add(cancel, "cell 0 4");
            add(submit, "cell 0 4");
        }

        public JButton getCancel(){
            return this.cancel;
        }

        public JButton getSubmit(){
            return this.submit;
        }

        public String getUserID(){
            return this.userID.getDataField().getText();
        }

        public String getModule(){
            return this.module.getDataField().getText();
        }

        public String getAssessmentName(){
            return this.assessmentName.getDataField().getText();
        }

        public String getResult(){
            return this.result.getDataField().getText();
        }

        public List<InfoLabel> getLabels(){
            return this.labels;
        }
    }
}
