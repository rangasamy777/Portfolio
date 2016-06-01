package org.supertutor.internal.wrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emile on 08/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class Subject {

    private String subjectName, description;
    private HashMap<String, Topic> topics;
    private long contentSize;

    public Subject(String subjectName, String description){
        this.subjectName = subjectName;
        this.description = description;
        this.topics = new HashMap<>();
    }

    public void addTopic(Topic topic){
        topics.put(topic.getTopicName(), topic);
    }

    public void removeTopic(Topic topic){
        topics.remove(topic.getTopicName());
    }

    public void setContentSize(long size){
        this.contentSize = size;
    }

    public String getSubjectName(){
        return this.subjectName;
    }

    public String getDescription(){
        return this.description;
    }

    public long getContentSize(){
        return this.contentSize;
    }

    public HashMap<String, Topic> getTopics(){
        return this.topics;
    }

    public List<Topic> getTopicsList(){
        List<Topic> list = new ArrayList<>();
        for(Map.Entry<String, Topic> entry: topics.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        List<Topic> topicsList = getTopicsList();

        sb.append("{");
        sb.append("\"subjectName\":\"" + subjectName + "\",");
        sb.append("\"description\":\"" + description + "\",");
        sb.append("\"topics\":[");

        for(Topic topic: topicsList){
            sb.append("{\"topicName\":\"" + topic.getTopicName() + "\",");
            sb.append("\"topicTag\":\"" + topic.getTag() + "\",");
            sb.append("\"fileName\":\"" + (topic.getFilePath() != null ? getFilename(topic.getFilePath()) : "") + "\"}");
            if(topicsList.indexOf(topic) < topicsList.size() - 1){
                sb.append(",");
            }
        }

        sb.append("],");
        sb.append("\"contentSize\":" + contentSize + "}");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Subject){
            Subject tSubject = (Subject)obj;
            return tSubject.getSubjectName().equals(this.getSubjectName()) && tSubject.getDescription().equals(this.getDescription());
        }
        return super.equals(obj);
    }

    private String getFilename(File file){
        String path = file.getAbsolutePath();
        return "..." + path.substring(path.lastIndexOf("/"), path.length());
    }
}
