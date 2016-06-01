package org.envisiontechllc.supertutor.subactivities.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.DashboardAdapter;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.settings.AppContext;
import org.envisiontechllc.supertutor.subactivities.PersonalityActivity;

/**
 * Created by EmileBronkhorst on 18/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class DashboardFragment extends Fragment implements FloatingActionButton.OnClickListener {

    private AppContext ctx;
    private AppCompatActivity activity;
    private DashboardAdapter adapter;

    private ListView list;
    private TextView welcomeTxt, styleText;
    private FloatingActionButton refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        return inflater.inflate(R.layout.dashboard_content, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ctx = AppContext.getContext();

        initWelcomeMessage(view);
        initListView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initFragment();
    }

    private void initFragment(){
        activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setTitle("Dashboard");
    }

    private void initListView(View view){
        list = (ListView)view.findViewById(R.id.dashboard_subjectList);
        if(list != null){
            adapter = new DashboardAdapter(activity, R.layout.dashboard_content, ctx.getLibrary().getMyLibrary());
            list.setAdapter(adapter);
        }

        refresh = (FloatingActionButton)view.findViewById(R.id.dashboard_refresh);
        refresh.setOnClickListener(this);
    }

    private void initWelcomeMessage(View view){
        welcomeTxt = (TextView)view.findViewById(R.id.dashboard_welcome);
        styleText = (TextView)view.findViewById(R.id.dashboard_style);

        if(welcomeTxt != null && styleText != null){
            welcomeTxt.setText("Welcome " + ctx.getCurrentUser().getUsername());

            setLearnerType();
        }
    }

    @Override
    public void onClick(View v) {
        adapter = new DashboardAdapter(activity, R.layout.dashboard_content, ctx.getLibrary().getMyLibrary());
        setLearnerType();
        if(adapter != null){
            list.setAdapter(adapter);
            Utilities.showToast(activity, "Successfully refreshed list", Toast.LENGTH_SHORT);
        }
    }

    private void setLearnerType(){
        String styleMsg = (ctx.getCurrentUser().getLearnerType().equalsIgnoreCase("Not set") ? "Click to take the personality quiz!" : "Style: " + ctx.getCurrentUser().getLearnerType());
        styleText.setText(styleMsg);

        if(styleMsg.equals("Click to take the personality quiz!")){
            styleText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            styleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent quizIntent = new Intent(activity, PersonalityActivity.class);
                    activity.startActivity(quizIntent);
                }
            });
        }
    }
}
