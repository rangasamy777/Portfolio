package com.supertutor.wrappers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supertutor.file.FileHelper;
import com.supertutor.utils.Analyzer;

public class Subject implements Serializable {
	
	private static final long serialVersionUID = 94L;

	private String subjectName, description;
    private List<Topic> topics;

    public Subject(String subjectName, String description){
        this.subjectName = subjectName;
        this.description = description;
        this.topics = new ArrayList<>();
    }
    
    public void setTopics(List<Topic> topics){
    	this.topics = topics;
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

    public long getContentSize(){
		long contentSize = 0;
    	try {
    		for(Topic topic: topics){
    			File file = new File(FileHelper.subjectDirectory + "/" + subjectName + "/" + Analyzer.stripFilename(topic.getFileName()));
        		if(file != null){
        			contentSize += Files.size(file.toPath());
        		}
    		}
    	}catch(IOException ex){}
        return contentSize;
    }

    public List<Topic> getTopics(){
        return this.topics;
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
