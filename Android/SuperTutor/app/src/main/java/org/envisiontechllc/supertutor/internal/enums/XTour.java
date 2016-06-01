package org.envisiontechllc.supertutor.internal.enums;

import android.support.v4.app.Fragment;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.subactivities.fragments.tourFragments.GenericTourFragment;

/**
 * Created by EmileBronkhorst on 25/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public enum XTour {

    DASHBOARD("Dashboard", "Get a breakdown of how you are doing on topics you are studying!", R.drawable.dashboard_tour, new GenericTourFragment()),
    LIBRARY("Library", "Browse our large collection of subjects available for studying. When new content is added it will automatically be available here.", R.drawable.library_tour, new GenericTourFragment()),
    DISCUSSION_BOARD("Discussion Board", "Need help on a topic you are revising? Pop into the discussion board and get help from the community!", R.drawable.discussion_tour, new GenericTourFragment()),
    PROFILE("Personalised profile", "Want to chat with a friend or build your educational portfolio? Now you can with the easy to use Profile page!", R.drawable.profile_tour, new GenericTourFragment())
    ;


    private String title, description;
    private int resourceID;
    private Fragment fragment;

    XTour(String title, String description, int resourceID, Fragment fragment){
        this.title = title;
        this.description = description;
        this.resourceID = resourceID;
        this.fragment = fragment;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public int getImage(){
        return this.resourceID;
    }

    public Fragment getFragment(){
        return this.fragment;
    }
}
