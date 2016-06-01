package org.envisiontechllc.supertutor.subactivities.fragments.tourFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;

/**
 * Created by EmileBronkhorst on 25/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class GenericTourFragment extends Fragment {

    private AppCompatActivity activity;

    private Bundle args;
    private TextView textDescription;
    private ImageView imageIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        return inflater.inflate(R.layout.generic_tour_template, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (AppCompatActivity)getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initFragment(view);
    }

    private void initFragment(View view){
        args = getArguments();
        if(args != null){
            textDescription = (TextView)view.findViewById(R.id.appTour_description);
            imageIcon = (ImageView)view.findViewById(R.id.appTour_image);

            textDescription.setText(args.getString("description"));
            imageIcon.setImageDrawable(getResources().getDrawable(args.getInt("image"), activity.getTheme()));
        }
    }
}
