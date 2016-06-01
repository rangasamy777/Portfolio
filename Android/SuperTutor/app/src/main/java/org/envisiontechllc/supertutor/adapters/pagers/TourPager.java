package org.envisiontechllc.supertutor.adapters.pagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.envisiontechllc.supertutor.internal.wrappers.tour.TourFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 25/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TourPager extends FragmentPagerAdapter {

    private List<TourFragment> fragments;

    public TourPager(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    public void addFragment(String title, String description, int resourceID, Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("description", description);
        bundle.putInt("image", resourceID);
        fragment.setArguments(bundle);
        this.fragments.add(new TourFragment(title, description, resourceID, fragment));
    }
}
