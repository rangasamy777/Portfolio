package org.envisiontechllc.supertutor.subactivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.pagers.TourPager;
import org.envisiontechllc.supertutor.internal.enums.XTour;

/**
 * Created by EmileBronkhorst on 25/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class AppTour extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager appTourPager;
    private TourPager tourPager;
    private TabLayout tabLayout;

    private boolean loginScreen = true;

    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.app_tour);

        instance = getIntent().getExtras();
        if(instance != null){
            loginScreen = instance.getBoolean("loginScreen");
        }

        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_tour_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.appTour_close) {
            savePreferences();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void savePreferences(){
        if(loginScreen){
            SharedPreferences preferences = getSharedPreferences(getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("firstUse", false);
            if(edit.commit()){
                Intent loginIntent = new Intent(this, LoginScreen.class);
                startActivity(loginIntent);
            }
        }
        finish();
    }

    private void initComponents(){
        toolbar = (Toolbar)findViewById(R.id.appTour_toolbar);
        toolbar.setTitle("Take a tour");
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.appTour_tabLayout);
        appTourPager = (ViewPager)findViewById(R.id.appTour_pager);
        tourPager = new TourPager(getSupportFragmentManager());

        addFragments(tourPager);

        appTourPager.setAdapter(tourPager);
        tabLayout.setupWithViewPager(appTourPager);
    }

    private void addFragments(TourPager pager){
        for(XTour tour: XTour.values()){
            pager.addFragment(tour.getTitle(), tour.getDescription(), tour.getImage(), tour.getFragment());
        }
    }
}
