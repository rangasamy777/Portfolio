package org.envisiontechllc.supertutor.internal.machine;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class AlgorithmValue {

    private String opCode;
    private int value;

    public AlgorithmValue(String opCode, int value){
        this.opCode = opCode;
        this.value = value;
    }

    public String getOpCode(){
        return this.opCode;
    }

    public int getValue(){
        return this.value;
    }
}
