package org.envisiontechllc.supertutor.subactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.testing.Questionnaire;
import org.envisiontechllc.supertutor.internal.testing.test_wrappers.TQuestion;
import org.envisiontechllc.supertutor.internal.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class PersonalityActivity extends AppCompatActivity implements Button.OnClickListener {

    private Questionnaire questionnaire;
    private Toolbar toolbar;

    private List<RadioButton> btns;
    private RadioGroup btnGroup;
    private Button nextQuestion;
    private TextView question, percentage;
    private ProgressBar questionProgress;

    private String answer = "";

    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.questionnaire_layout);

        initComponents();
        generateQuestion();

        Utilities.createDialog(this, "Instructions", getResources().getString(R.string.questionnaire_welcome), null).show();
    }

    private void generateQuestion(){
        TQuestion currentQuestion = questionnaire.getQuestion(questionnaire.getCurrentIndex());
        if(currentQuestion != null){
            question.setText(currentQuestion.getQuestion());
            questionProgress.setIndeterminate(false);
            questionProgress.setMax(10);
            questionProgress.setProgress(questionnaire.getCurrentIndex() + 1);
            percentage.setText((questionnaire.getCurrentIndex() + 1) + "/10");
            for(final RadioButton btn: btns){
                btn.setText(currentQuestion.getAnswers().get(btns.indexOf(btn)).getAnswer());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answer = btn.getText().toString();
                    }
                });
            }
        }
    }

    private void initComponents(){
        questionnaire = new Questionnaire();
        toolbar = (Toolbar)findViewById(R.id.questionnaire_toolbar);
        toolbar.setTitle("Personality quiz");
        setSupportActionBar(toolbar);

        btns = new ArrayList<>();
        btnGroup = (RadioGroup)findViewById(R.id.questionnaireGroup);
        btns.add((RadioButton)findViewById(R.id.questionnaireAnswer1));
        btns.add((RadioButton)findViewById(R.id.questionnaireAnswer2));
        btns.add((RadioButton)findViewById(R.id.questionnaireAnswer3));
        btns.add((RadioButton)findViewById(R.id.questionnaireAnswer4));

        question = (TextView)findViewById(R.id.questionnaireQuestion);
        percentage = (TextView)findViewById(R.id.questionnairePercentage);
        nextQuestion = (Button)findViewById(R.id.questionnaireNext);
        nextQuestion.setOnClickListener(this);
        questionProgress = (ProgressBar)findViewById(R.id.questionnaireProgress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questionnaire_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.questionnaire_close:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int currentIndex = questionnaire.getCurrentIndex();
        if(currentIndex < questionnaire.getQuestions().size() - 1){
            if(btnGroup.getCheckedRadioButtonId() != -1){
                if(currentIndex >= questionnaire.getQuestions().size() - 2){
                    nextQuestion.setText("Complete quiz");
                }
                questionnaire.addAnswer(answer);
                questionnaire.nextIndex();
                btnGroup.clearCheck();
                generateQuestion();
            } else {
                Utilities.showToast(this, "Select an answer before proceeding.", Toast.LENGTH_SHORT);
            }
        } else {
            questionnaire.addAnswer(answer);
            questionnaire.calculatePreference(this);
        }
    }
}
