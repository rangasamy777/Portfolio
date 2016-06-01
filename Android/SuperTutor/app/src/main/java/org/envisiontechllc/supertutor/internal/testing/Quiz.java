package org.envisiontechllc.supertutor.internal.testing;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.TestQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Quiz {

    private static Quiz instance;
    private List<TestQuestion> questions;
    private List<String> answers;
    private int currentIndex;

    private Quiz(){
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.currentIndex = 0;
    }

    public void setQuestions(List<TestQuestion> questions){
        this.questions = questions;
        this.answers.clear();
    }

    public void addAnswer(int index, String answer){
        try {
            if(answers.get(index) != null){
                Collections.replaceAll(answers, answers.get(index), answer);
                return;
            }
        }catch(Exception ex){}
        this.answers.add(answer);
    }

    public List<TestQuestion> getQuestions(){
        return this.questions;
    }

    public void calculateScore(final Activity ctx){
        int score = 0;

        try {
            if(validateQuiz()){
                for(TestQuestion question: questions){
                    String answer = question.getAnswers().get(question.getCorrectIndex());
                    if(answer != null){
                        String chosenAnswer = answers.get(question.getQuestionNumber() - 1);
                        if(chosenAnswer.equalsIgnoreCase(answer)){
                            score++;
                        }
                    }
                }
                Utilities.createDialog(ctx, "Results", "You have scored: " + score + " /" + questions.size(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctx.finish();
                    }
                }).show();
            } else {
                Utilities.showToast(ctx, "You have not answered all the questions. Answer them all to check your score!", Toast.LENGTH_SHORT);
            }
        }catch(Exception ex){ex.printStackTrace();}
    }

    private boolean validateQuiz(){
        return (answers.size() >= questions.size() ? true : false);
    }

    public static Quiz getInstance(){
        if(instance == null){
            instance = new Quiz();
        }
        return instance;
    }
}
