package org.envisiontechllc.supertutor.subactivities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.network.tasks.Privatizer;
import org.envisiontechllc.supertutor.settings.AppContext;
import org.envisiontechllc.supertutor.subactivities.AppTour;

/**
 * Created by EmileBronkhorst on 26/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class SettingsDialog extends Dialog implements View.OnClickListener {

    private Context appContext;
    private AppContext ctx;

    private Switch privateSwitch;
    private Button cancel, confirm, takeTour, changePassword;

    public SettingsDialog(Context context) {
        super(context);
        setContentView(R.layout.settings_dialog);
        setTitle("Settings");

        appContext = context;

        setup();
    }

    private void setup(){
        ctx = AppContext.getContext();

        privateSwitch = (Switch)findViewById(R.id.setting_profile);
        privateSwitch.setChecked(ctx.getCurrentUser().isPrivate());

        cancel = (Button)findViewById(R.id.settings_cancel);
        cancel.setOnClickListener(this);
        confirm = (Button)findViewById(R.id.settings_confirm);
        confirm.setOnClickListener(this);
        takeTour = (Button)findViewById(R.id.settings_tour);
        takeTour.setOnClickListener(this);
        changePassword = (Button)findViewById(R.id.settings_changePassword);
        changePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settings_cancel:
                break;
            case R.id.settings_confirm:
                ctx.getCurrentUser().setPrivate(privateSwitch.isChecked());
                new Privatizer().execute(ctx.getCurrentUser());
                break;
            case R.id.settings_tour:
                Intent appTour = new Intent(appContext, AppTour.class);

                Bundle bundle = new Bundle();
                bundle.putBoolean("loginScreen", false);
                appTour.putExtras(bundle);

                appContext.startActivity(appTour);
                break;
            case R.id.settings_changePassword:
                new ChangePassDialog(v.getContext()).show();
                break;
        }
        dismiss();
    }
}
