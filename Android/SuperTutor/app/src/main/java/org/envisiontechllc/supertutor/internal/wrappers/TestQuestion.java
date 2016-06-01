package org.envisiontechllc.supertutor.internal.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 29/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TestQuestion implements Serializable {

    private int questionNumber, correctIndex;
    private String question;
    private List<String> answers;

    public TestQuestion(int questionNumber, String question, int correctIndex){
        this.questionNumber = questionNumber;
        this.question = question;
        this.correctIndex = correctIndex;
        this.answers = new ArrayList<>();
    }

    public void addAnswer(String answer){
        if(!this.answers.contains(answer)){
            this.answers.add(answer);
        }
    }

    public int getQuestionNumber(){
        return this.questionNumber;
    }

    public int getCorrectIndex(){
        return this.correctIndex;
    }

    public String getQuestion(){
        return this.question;
    }

    public List<String> getAnswers(){
        return this.answers;
    }
}
