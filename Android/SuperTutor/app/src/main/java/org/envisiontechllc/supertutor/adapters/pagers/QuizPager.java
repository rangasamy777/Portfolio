package org.envisiontechllc.supertutor.adapters.pagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.envisiontechllc.supertutor.internal.wrappers.TestQuestion;
import org.envisiontechllc.supertutor.subactivities.fragments.quiz.GenericQuizFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class QuizPager extends FragmentPagerAdapter {

    private List<TestQuestion> questions;
    private List<Fragment> fragments;

    public QuizPager(FragmentManager fm, List<TestQuestion> questions) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.questions = questions;
        this.initFragment(questions);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Q" + questions.get(position).getQuestionNumber();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void initFragment(List<TestQuestion> questions){
        for(TestQuestion question: questions){
            Bundle bundle = new Bundle();
            bundle.putSerializable("question", question);
            Fragment newFragment = new GenericQuizFragment();
            newFragment.setArguments(bundle);
            fragments.add(newFragment);
        }
    }
}
