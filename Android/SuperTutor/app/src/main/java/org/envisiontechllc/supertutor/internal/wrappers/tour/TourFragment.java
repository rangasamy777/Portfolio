package org.envisiontechllc.supertutor.internal.wrappers.tour;

import android.support.v4.app.Fragment;

/**
 * Created by EmileBronkhorst on 25/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TourFragment {

    private String title, description;
    private int resourceID;
    private Fragment fragment;

    public TourFragment(String title, String description, int resourceID, Fragment fragment){
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

    public int getImageName(){
        return this.resourceID;
    }

    public Fragment getFragment(){
        return this.fragment;
    }
}
