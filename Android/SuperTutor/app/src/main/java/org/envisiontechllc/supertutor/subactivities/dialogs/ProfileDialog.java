package org.envisiontechllc.supertutor.subactivities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.network.tasks.FeedDownloader;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ProfileDialog extends Dialog {

    private String followerName;
    private ListView subjectList;
    private Button button;

    public ProfileDialog(Context context, Bundle bundle) {
        super(context);
        setContentView(R.layout.profile_dialog);

        if(bundle != null){
            followerName = bundle.getString("username");
            setTitle(followerName + " is Studying");
        }

        initComponents(context);
    }

    private void initComponents(Context ctx){
        subjectList = (ListView) findViewById(R.id.followerStudying);
        button = (Button)findViewById(R.id.followerStudyingClose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        new FeedDownloader(ctx, subjectList).execute(followerName);
    }
}
