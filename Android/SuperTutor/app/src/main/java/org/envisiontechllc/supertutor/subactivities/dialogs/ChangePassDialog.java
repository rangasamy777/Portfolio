package org.envisiontechllc.supertutor.subactivities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.tasks.ChangePasswordTask;
import org.envisiontechllc.supertutor.settings.AppContext;

/**
 * Created by EmileBronkhorst on 30/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ChangePassDialog extends Dialog implements Button.OnClickListener {

    private EditText newPass, confirmPass;
    private Button cancel, submit;

    public ChangePassDialog(Context context) {
        super(context);
        setContentView(R.layout.change_pass_dialog);
        setTitle("Change password");

        initComponents();
    }

    private void initComponents(){
        newPass = (EditText)findViewById(R.id.newPassword);
        confirmPass = (EditText)findViewById(R.id.confirmNewPass);
        cancel = (Button)findViewById(R.id.changePassword_dismiss);
        submit = (Button)findViewById(R.id.confirmChangePass);

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.confirmChangePass:
                String tempPass = newPass.getText().toString(), confirmPass = this.confirmPass.getText().toString();
                if(tempPass != null && confirmPass != null){
                    if(Utilities.checkMatch(tempPass, confirmPass)){
                        AppContext.getContext().getCurrentUser().setPassword(tempPass);
                        new ChangePasswordTask(v.getContext()).execute(AppContext.getContext().getCurrentUser());
                    }
                }
                break;
        }
        dismiss();
    }
}
