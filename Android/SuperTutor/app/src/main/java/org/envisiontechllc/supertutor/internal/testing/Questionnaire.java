package org.envisiontechllc.supertutor.internal.testing;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.envisiontechllc.supertutor.internal.enums.XQuestions;
import org.envisiontechllc.supertutor.internal.machine.MachineAlgorithm;
import org.envisiontechllc.supertutor.internal.testing.test_wrappers.TAnswer;
import org.envisiontechllc.supertutor.internal.testing.test_wrappers.TQuestion;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.network.tasks.UpdateLearnerTask;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 20/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Questionnaire {

    private int currentIndex;
    private List<TQuestion> questions;
    private List<TAnswer> answers;

    public Questionnaire(){
        this.currentIndex = 0;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.initQuestionnaire();
    }

    public void nextIndex(){
        this.currentIndex++;
    }

    public int getCurrentIndex(){
        return this.currentIndex;
    }

    public TQuestion getQuestion(int index){
        return questions.get(index);
    }

    public void addAnswer(String text){
        TQuestion currentQuestion = questions.get(currentIndex);
        if(currentQuestion != null){
            for(TAnswer answer: currentQuestion.getAnswers()){
                if(answer.getAnswer().equals(text)){
                    answers.add(answer);
                }
            }
        }
    }

    public List<TQuestion> getQuestions(){
        return this.questions;
    }

    private void initQuestionnaire(){
        for(XQuestions question: XQuestions.values()){
            this.questions.add(question.getQuestion());
        }
    }

    public void calculatePreference(final AppCompatActivity ctx){
        int visual = 0, auditory = 0, kinaesthetic = 0, readWrite = 0;

        for(TAnswer answer: answers){
            switch(answer.getOpCode()){
                case "R":
                    readWrite++;
                    break;
                case "V":
                    visual++;
                    break;
                case "K":
                    kinaesthetic++;
                    break;
                case "A":
                    auditory++;
                    break;
            }
        }

        String style = MachineAlgorithm.determineLearnerType(visual, auditory, kinaesthetic, readWrite);
        AppContext.getContext().getCurrentUser().setLearningStyle(style);
        new UpdateLearnerTask().execute();

        Utilities.createDialog(ctx, "Results of Quiz", "Your learning preference is: " + style, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ctx.finish();
            }
        }).show();
    }
}
