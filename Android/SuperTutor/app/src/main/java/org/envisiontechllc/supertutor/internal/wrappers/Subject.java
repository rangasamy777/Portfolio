package org.envisiontechllc.supertutor.internal.wrappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 18/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Subject {
    private static final long serialVersionUID = 94L;

    private String subjectName, description;
    private List<Topic> topics;
    private long size;
    private int bookmark;

    public Subject(String subjectName, String description, long size){
        this.subjectName = subjectName;
        this.description = description;
        this.topics = new ArrayList<>();
        this.size = size;
    }

    public void setTopics(List<Topic> topics){
        this.topics = topics;
    }

    public void setBookmark(int bookmark){
        this.bookmark = bookmark;
    }

    public void addTopics(Topic... topics){
        for(Topic topic: topics){
            if(!this.topics.contains(topic)){
                this.topics.add(topic);
            }
        }
    }

    public void addTopic(Topic topic){
        topics.add(topic);
    }

    public void removeTopic(String name){
        topics.remove(name);
    }

    public String getSubjectName(){
        return this.subjectName;
    }

    public String getDescription(){
        return this.description;
    }

    public List<Topic> getTopics(){
        return this.topics;
    }

    public long getContentSize(){
        return this.size;
    }

    public int getBookmark(){
        return this.bookmark;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Subject){
            Subject tSubject = (Subject)obj;
            return tSubject.getSubjectName().equals(this.getSubjectName()) && tSubject.getDescription().equals(this.getDescription());
        }
        return super.equals(obj);
    }
}
