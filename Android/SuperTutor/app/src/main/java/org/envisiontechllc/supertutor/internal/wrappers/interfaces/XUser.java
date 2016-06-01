package org.envisiontechllc.supertutor.internal.wrappers.interfaces;

/**
 * Created by EmileBronkhorst on 15/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public interface XUser {

    void setLearningStyle(String style);
    void setPassword(String password);
    void setStatus(String status);
    void setImageBytes(String imageBytes);
    void setPrivate(boolean value);

    String getUsername();
    String getPassword();
    String getEmail();
    String getLearnerType();
    String getProfileStatus();
    String getImageBytes();
    boolean isPrivate();

}
