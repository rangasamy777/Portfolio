package org.envisiontechllc.supertutor.internal.wrappers;

/**
 * Created by EmileBronkhorst on 26/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class FollowerStat {

    private String username, status;
    private String imageBytes;

    public FollowerStat(String username, String status, String imageBytes){
        this.username = username;
        this.status = status;
        this.imageBytes = imageBytes;
    }

    public String getUsername(){
        return this.username;
    }

    public String getStatus(){
        return this.status;
    }

    public String getImageBytes(){
        return this.imageBytes;
    }

}
