package org.envisiontechllc.supertutor.subactivities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.tasks.ForgotPasswordTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ForgotPassDialog extends Dialog implements Button.OnClickListener {

    private EditText userInput;
    private Button submit, dismiss;

    public ForgotPassDialog(Context context) {
        super(context);
        setContentView(R.layout.forgot_pass_dialog);
        setTitle("Forgot password");

        initComponents();
    }

    private void initComponents(){
        userInput = (EditText)findViewById(R.id.forgotPassword_field);
        submit = (Button)findViewById(R.id.forgotPass_submit);
        dismiss = (Button)findViewById(R.id.forgotPass_dismiss);

        dismiss.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.forgotPass_submit:
                String email = userInput.getText().toString();

                Pattern p = Pattern.compile(Utilities.EMAIL_PATTERN);
                Matcher m = p.matcher(email);

                if(m.find()){
                    new ForgotPasswordTask(v.getContext()).execute(userInput.getText().toString());
                } else {
                    Utilities.showToast(v.getContext(), "You have entered an invalid email address. Try again.", Toast.LENGTH_SHORT);
                }

                break;
        }
        dismiss();
    }
}
