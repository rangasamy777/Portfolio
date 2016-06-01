package utils;

import components.InfoLabel;

import javax.swing.*;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class Validator {

    public static boolean validate(List<InfoLabel> infoLabelList){
        int count = 0;
        for(InfoLabel label : infoLabelList){
            if(label.getDataField().getText() != null && !label.getDataField().getText().equalsIgnoreCase("")){
                count++;
            }
        }
        return count == infoLabelList.size();
    }

    public static boolean validatePasswords(List<JPasswordField> passwordFields){
        int count = 0;

        for(JPasswordField pass : passwordFields){
            if(String.valueOf(pass.getPassword()) != null && !String.valueOf(pass.getPassword()).equals("")){
                count++;
            }
        }
        return count == passwordFields.size();
    }
}
