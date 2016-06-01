package com.supertutor.wrappers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class Topic implements Serializable {
	
	private static final long serialVersionUID = 94L;
	
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

    public String getTopicTag(){
        return this.tag;
    }

    public String getFileName(){
        return this.fileName;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Topic){
    		Topic tTopic = (Topic)obj;
    		return tTopic.getTopicName().equals(this.getTopicName()) && tTopic.getTopicTag().equals(this.getTopicTag());
    	}
    	return super.equals(obj);
    }
}
