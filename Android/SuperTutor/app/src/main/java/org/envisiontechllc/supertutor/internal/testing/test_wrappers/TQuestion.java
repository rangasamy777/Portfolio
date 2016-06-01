package org.envisiontechllc.supertutor.internal.testing.test_wrappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 20/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TQuestion {

    private String question;
    private List<TAnswer> answers;

    public TQuestion(String question, TAnswer... answers){
        this.question = question;
        this.answers = new ArrayList<>();
        this.initAnswers(answers);
    }

    private void initAnswers(TAnswer... answers){
        for(TAnswer answer: answers){
            this.answers.add(answer);
        }
    }

    public String getQuestion(){
        return this.question;
    }

    public List<TAnswer> getAnswers(){
        return this.answers;
    }
}
