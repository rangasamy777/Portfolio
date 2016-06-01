package org.envisiontechllc.supertutor.subactivities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.LibraryAdapter;
import org.envisiontechllc.supertutor.settings.AppContext;

/**
 * Created by EmileBronkhorst on 18/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class LibraryFragment extends Fragment {

    private ListView libraryList;
    private LibraryAdapter adapter;
    private AppContext appContext;
    private AppCompatActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_library_content, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        libraryList = (ListView)activity.findViewById(R.id.libraryList);
        appContext.getLibrary().downloadSubjects(activity, libraryList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initFragment();
    }

    private void initFragment(){
        activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setTitle("Library");

        appContext = AppContext.getContext();
    }
}
