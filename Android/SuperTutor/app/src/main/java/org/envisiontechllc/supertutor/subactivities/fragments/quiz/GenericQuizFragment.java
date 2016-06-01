package org.envisiontechllc.supertutor.subactivities.fragments.quiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.testing.Quiz;
import org.envisiontechllc.supertutor.internal.wrappers.TestQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class GenericQuizFragment extends Fragment {

    private AppCompatActivity activity;

    private Bundle args;
    private TextView question;
    private RadioButton a1, a2, a3, a4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        return inflater.inflate(R.layout.quiz_fragment, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (AppCompatActivity)getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initFragment(view);
    }

    private void initFragment(View view){
        args = getArguments();
        if(args != null){
            final TestQuestion question = (TestQuestion) args.getSerializable("question");

            this.question = (TextView)view.findViewById(R.id.quizQuestion);
            this.question.setText(question.getQuestion());

            List<RadioButton> answers = new ArrayList<>();
            a1 = (RadioButton)view.findViewById(R.id.quizAnswer1);
            a2 = (RadioButton)view.findViewById(R.id.quizAnswer2);
            a3 = (RadioButton)view.findViewById(R.id.quizAnswer3);
            a4 = (RadioButton)view.findViewById(R.id.quizAnswer4);

            Collections.addAll(answers, a1, a2, a3, a4);

            for(int i = 0; i < question.getAnswers().size(); i++){
                final RadioButton btn = answers.get(i);
                btn.setText(question.getAnswers().get(i));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Quiz.getInstance().addAnswer(question.getQuestionNumber() - 1, btn.getText().toString());
                    }
                });
            }
        }
    }
}
