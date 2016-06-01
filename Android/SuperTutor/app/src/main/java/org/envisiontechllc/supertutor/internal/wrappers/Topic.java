package org.envisiontechllc.supertutor.internal.wrappers;

/**
 * Created by EmileBronkhorst on 18/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Topic {

    private String topicName, tag;
    private String fileName;

    public Topic(String topicName, String tag, String fileName){
        this.topicName = topicName;
        this.tag = tag;
        this.fileName = fileName;
    }

    public String getTopicName(){
        return this.topicName;
    }

    public String getTag(){
        return this.tag;
    }

    public String getFileName(){
        return this.fileName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Topic){
            Topic tTopic = (Topic)obj;
            return tTopic.getTopicName().equals(this.getTopicName()) && tTopic.getTag().equals(this.getTag());
        }
        return super.equals(obj);
    }
}
