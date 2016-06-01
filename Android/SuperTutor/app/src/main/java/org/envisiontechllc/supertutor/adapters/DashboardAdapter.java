package org.envisiontechllc.supertutor.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.network.tasks.TestDownloader;
import org.envisiontechllc.supertutor.subactivities.ContentViewer;

import java.util.List;

/**
 * Created by EmileBronkhorst on 27/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class DashboardAdapter extends ArrayAdapter<Subject> {

    private Context ctx;
    private List<Subject> subjects;

    public DashboardAdapter(Context context, int resource, List<Subject> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.subjects = objects;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Subject getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(ctx).inflate(R.layout.dashboard_subject, parent, false);
        }

        final Subject subject = getItem(position);
        if(subject != null){
            TextView title = (TextView)view.findViewById(R.id.subjectName_dashboard);
            TextView progressPercentage = (TextView)view.findViewById(R.id.subjectPercentage_dashboard);
            ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.subjectProgress_dashboard);
            Button takeTest = (Button)view.findViewById(R.id.subjectTest_dashboard);

            title.setText(subject.getSubjectName());

            progressBar.setMax(subject.getTopics().size());
            progressBar.setProgress((subject.getBookmark() + 1));

            progressPercentage.setText(getProgressTitle(progressBar));

            takeTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TestDownloader(v.getContext()).execute(subject);
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent contentViewer = new Intent(ctx, ContentViewer.class);
                    contentViewer.putExtra("subjectName", subject.getSubjectName());
                    ctx.startActivity(contentViewer);
                }
            });
        }

        return view;
    }

    private String getProgressTitle(ProgressBar bar){
        int current = bar.getProgress(), max = bar.getMax();
        int percentage = (int)(((float)current / max) * 100);
        return String.format("%d", percentage) + "% completed";
    }
}
