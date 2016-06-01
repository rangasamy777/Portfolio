package org.envisiontechllc.supertutor.internal.testing.test_wrappers;

/**
 * Created by EmileBronkhorst on 20/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TAnswer {

    private String answer, opCode;

    public TAnswer(String answer, String opCode){
        this.answer = answer;
        this.opCode = opCode;
    }

    public String getAnswer(){
        return this.answer;
    }

    public String getOpCode(){
        return this.opCode;
    }
}
