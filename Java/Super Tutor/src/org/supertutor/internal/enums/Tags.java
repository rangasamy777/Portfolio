package org.supertutor.internal.enums;

/**
 * Created by Emile on 01/05/2016.
 * Copyright 2015 Envision Tech LLC
 */
public enum Tags {

    TITLE("Title"),
    INTRODUCTION("Introduction"),
    LEADING_EXPLANATION("Leading explanation"),
    TRAILING_EXPLANATION("Trailing explanation"),
    EXAMPLE("Example"),
    ;

    private String tag;

    Tags(String tag){
        this.tag = tag;
    }

    public String getTag(){
        return this.tag;
    }
}
