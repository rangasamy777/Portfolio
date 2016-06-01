package org.envisiontechllc.supertutor.subactivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.network.managers.LoginManager;
import org.envisiontechllc.supertutor.subactivities.dialogs.ForgotPassDialog;

/**
 * Created by EmileBronkhorst on 21/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class LoginScreen extends Activity {

    private SQLManager dbManager;

    private Button loginBtn, createAccount;
    private EditText username, password;
    private CheckBox rememberMe;
    private TextView forgotPassword;

    /**
     * Default constructor for the super class
     * @param instance the stored instance for the intent
     */
    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.login_screen);

        initSQLManager();
        initComponents();
    }


    /**
     * Initialises all the necessary components within the view
     */
    private void initComponents(){
        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);

        rememberMe = (CheckBox)findViewById(R.id.login_remember);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        forgotPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ForgotPassDialog(v.getContext()).show();
            }
        });

        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginManager(LoginScreen.this, username.getText().toString(), password.getText().toString(), rememberMe.isChecked()).execute();
            }
        });
        createAccount = (Button)findViewById(R.id.login_createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration = new Intent(v.getContext(), RegistrationScreen.class);
                if(registration != null){
                    startActivity(registration);
                }
            }
        });
    }

    /**
     * Initialises the SQL manager instance
     */
    private void initSQLManager(){
        dbManager = new SQLManager(this);
    }
}
