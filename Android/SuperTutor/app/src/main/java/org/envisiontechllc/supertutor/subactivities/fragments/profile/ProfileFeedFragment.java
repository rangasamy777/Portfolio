package org.envisiontechllc.supertutor.subactivities.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.FeedAdapter;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.tasks.FeedDownloader;

/**
 * Created by EmileBronkhorst on 27/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ProfileFeedFragment extends Fragment {

    private AppCompatActivity activity;

    private ListView profileFeed;
    private FeedAdapter adapter;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        return inflater.inflate(R.layout.profile_iist_fragment, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        profileFeed = (ListView)view.findViewById(R.id.profileFeed_list);

        new FeedDownloader(activity, profileFeed).execute(currentUser.getUsername());
    }

    private void initFragment(){
        activity = (AppCompatActivity)getActivity();

        Bundle bundle = getArguments();
        if(bundle != null){
            currentUser = (User)bundle.getSerializable("user");
        }
    }
}
