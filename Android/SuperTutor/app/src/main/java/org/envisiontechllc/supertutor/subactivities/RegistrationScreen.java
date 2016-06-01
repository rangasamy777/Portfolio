package org.envisiontechllc.supertutor.subactivities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.listeners.RegListener;
import org.envisiontechllc.supertutor.internal.enums.XStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 01/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class RegistrationScreen extends Activity {

    private Button register;
    private EditText username, email, pass, confirmPass, dateOfBirth;
    private Spinner ageSpinner, statusSpinner;
    private List<String> status;
    private ArrayAdapter<String> statusAdapter;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.registration_screen);

        initComponents();
    }

    private void initComponents(){
        username = (EditText)findViewById(R.id.reg_username);
        email = (EditText)findViewById(R.id.reg_email);
        pass = (EditText)findViewById(R.id.reg_password);
        confirmPass = (EditText)findViewById(R.id.reg_confirm_pass);
        confirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pass.getText().toString();
                if(password.equals(confirmPass.getText().toString())){
                    confirmPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.tick, 0);
                } else {
                    confirmPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.delete, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dateOfBirth = (EditText)findViewById(R.id.reg_dob);
        statusSpinner = (Spinner)findViewById(R.id.reg_status);

        register = (Button)findViewById(R.id.reg_register);
        register.setOnClickListener(new RegListener(this, username, pass, confirmPass, email, dateOfBirth, statusSpinner));

        status = new ArrayList<>();

        initAdapters();
    }

    private void initAdapters(){
        status.add("Select a status");
        for(XStatus status: XStatus.values()){
            this.status.add(status.getStatus());
        }
        statusAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner, status);
        statusSpinner.setAdapter(statusAdapter);
    }
}
