package org.envisiontechllc.supertutor.internal.enums;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public enum XType {

    AUDITORY("Auditory"),
    VISUAL("Visual"),
    KINAESTHETIC("Kinaesthetic"),
    READ_WRITE("Read/Write")
    ;

    private String type;

    XType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
