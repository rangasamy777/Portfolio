package org.envisiontechllc.supertutor.internal.wrappers;

/**
 * Created by EmileBronkhorst on 27/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ProfileFeed {

    private String subjectName, category;
    private String timestamp;

    public ProfileFeed(String subjectName, String category, String timestamp){
        this.subjectName = subjectName;
        this.category = category;
        this.timestamp = timestamp;
    }
    public String getSubjectname(){
        return this.subjectName;
    }

    public String getCategoryname(){
        return this.category;
    }

    public String getTimestamp(){
        return this.timestamp;
    }
}
