package org.envisiontechllc.supertutor.subactivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.pagers.QuizPager;
import org.envisiontechllc.supertutor.internal.testing.Quiz;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TestActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ViewPager quizPager;
    private TabLayout quizTabLayout;
    private QuizPager adapter;

    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.quiz_layout);

        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.quiz_close:
                finish();
                break;
            case R.id.quiz_submit:
                Quiz.getInstance().calculateScore(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents(){
        quizPager = (ViewPager)findViewById(R.id.quizViewpager);
        setupPagerTabs(quizPager);

        quizTabLayout = (TabLayout)findViewById(R.id.quiz_tabLayout);
        quizTabLayout.setupWithViewPager(quizPager);

        toolbar = (Toolbar)findViewById(R.id.quiz_toolbar);
        toolbar.setTitle("Swipe to navigate");
        setSupportActionBar(toolbar);
    }

    private void setupPagerTabs(ViewPager pager){
        adapter = new QuizPager(getSupportFragmentManager(), Quiz.getInstance().getQuestions());
        pager.setAdapter(adapter);
    }
}
