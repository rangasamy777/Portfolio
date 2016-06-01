package org.envisiontechllc.supertutor.subactivities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.managers.BoardManager;
import org.envisiontechllc.supertutor.settings.AppContext;

/**
 * Created by EmileBronkhorst on 22/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class DiscussionFragment extends Fragment {

    private final float BLACK_ALPHA = 0.7f;

    private AppCompatActivity activity;
    private AppContext ctx;
    private Context viewContext;

    private RecyclerView cardView;
    private LinearLayout headerBackground;
    private TextView headerTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.discussion_board, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ctx = AppContext.getContext();
        viewContext = view.getContext();

        cardView = initRecycleView(view, cardView);
        headerBackground = initLinearLayout(view, headerBackground);

        new BoardManager(activity, view.getContext(), cardView).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setTitle("Discussion Board");
    }

    private RecyclerView initRecycleView(View view, RecyclerView recycleView){
        if(recycleView == null){
            recycleView= (RecyclerView)view.findViewById(R.id.category_view);
        }
        return recycleView;
    }

    private LinearLayout initLinearLayout(View view, LinearLayout layout){
        if(layout == null){
            layout = (LinearLayout)view.findViewById(R.id.discussion_header_background);
            layout.setAlpha(BLACK_ALPHA);
            headerTitle = (TextView)view.findViewById(R.id.discussion_header_title);
        }
        return layout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.board_refresh:
                Utilities.showToast(activity, "Refreshing discussion board...", Toast.LENGTH_SHORT);
                new BoardManager(activity, viewContext, cardView).execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.discussion_menu, menu);
    }
}
