package org.envisiontechllc.supertutor.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 26/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ProfilePager extends FragmentPagerAdapter {

    private List<String> pageTitle;
    private List<Fragment> fragments;

    public ProfilePager(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.pageTitle = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle.get(position);
    }

    public void addFragment(String title, Fragment fragment){
        this.pageTitle.add(title);
        this.fragments.add(fragment);
    }
}
