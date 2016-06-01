package org.supertutor.internal.wrappers;

/**
 * Created by Emile on 06/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class AnalyticsCell {

    private String field;
    private Object value;

    public AnalyticsCell(String field, Object value){
        this.field = field;
        this.value = value;
    }

    public String getField(){
        return this.field;
    }

    public Object getValue(){
        return this.value;
    }
}
