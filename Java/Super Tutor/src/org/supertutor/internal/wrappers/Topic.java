package org.supertutor.internal.wrappers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Emile on 08/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class Topic {

    private String topicName, tag;
    private File filePath;
    private long fileSize;

    public Topic(String topicName, String tag, File filePath){
        this.topicName = topicName;
        this.tag = tag;
        this.filePath = filePath;
        this.fileSize = getFilesize();
    }

    public Topic(String topicName, String tag){
        this.topicName = topicName;
        this.tag = tag;
    }

    public long getFilesize(){
        try {
            fileSize = Files.size(filePath.toPath());
            return fileSize;
        }catch(IOException ex){}
        return -1;
    }

    public String getTopicName(){
        return this.topicName;
    }

    public String getTag(){
        return this.tag;
    }

    public File getFilePath(){
        return this.filePath;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Topic){
            Topic topic = (Topic)obj;
            if(topic != null){
                return topic.getTopicName().equalsIgnoreCase(this.topicName) &&
                        topic.getTag().equalsIgnoreCase(this.tag);
            }
        }
        return super.equals(obj);
    }

}
