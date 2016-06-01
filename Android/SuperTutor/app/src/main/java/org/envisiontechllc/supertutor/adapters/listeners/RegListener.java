package org.envisiontechllc.supertutor.adapters.listeners;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.managers.RegistrationManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EmileBronkhorst on 04/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class RegListener implements Button.OnClickListener {

    private Context ctx;
    private EditText username, password, confirmPass, email, dob;
    private Spinner statusSpinner;

    private List<EditText> nodes = new ArrayList<>();

    public RegListener(Context ctx, EditText username, EditText password, EditText confirmPass, EditText email, EditText dob, Spinner statusSpinner){
        this.ctx = ctx;
        this.username = username;
        this.password = password;
        this.confirmPass = confirmPass;
        this.email = email;
        this.dob = dob;
        this.statusSpinner = statusSpinner;

        Collections.addAll(nodes, username, password, confirmPass, email, dob);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reg_register:
                try {
                    registerUser();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void registerUser() throws NumberFormatException, UnsupportedEncodingException {
        if(validForm()){
            String pass = password.getText().toString(), confirm = confirmPass.getText().toString();
            if(pass != null && confirm != null){
                if(isMatch(pass, confirm)){
                    String uName = username.getText().toString(), mail = email.getText().toString();
                    if(uName != null && mail != null){
                        Pattern p = Pattern.compile(Utilities.EMAIL_PATTERN);
                        Matcher m = p.matcher(mail);

                        if(m.find()){
                            String dateofBirth = dob.getText().toString();
                            dateofBirth = dateofBirth.replaceAll("/", "-");
                            String status = (String)statusSpinner.getSelectedItem();

                            if (status != null && !status.equalsIgnoreCase("Select a status")) {
                                new RegistrationManager(ctx, uName, pass, mail, status, dateofBirth).execute();
                            }
                        } else {
                            Utilities.showToast(ctx, "You have entered an invalid email address, please ensure you enter a valid email.", 1);
                        }
                    }
                } else {
                    Utilities.showToast(ctx, "The passwords do not match! Please try again.", 1);
                }
            }
            return;
        }

        Utilities.showToast(ctx, "Please ensure you have filled out the form correctly.", 1);
    }

    private boolean isMatch(String one, String two){
        return one.equals(two);
    }

    private boolean validForm(){
        boolean isValid = true;

        for(EditText text: nodes){
            if(text.getText() != null && text.getText().toString().isEmpty()){
                isValid = false;
            }
        }

        return isValid;
    }
}
