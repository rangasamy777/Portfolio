package org.envisiontechllc.supertutor.internal.wrappers;

import org.envisiontechllc.supertutor.internal.wrappers.interfaces.XUser;

import java.io.Serializable;

/**
 * Created by EmileBronkhorst on 15/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class User implements XUser, Serializable {

    private String username, password, email, learnerType;
    private String status, imageBytes;
    private boolean isPrivate;

    /**
     * Creates a new User object.
     * @param username The username to parse to the current user instance.
     * @param email The email to parse to the current user instance.
     * @param learnerType The learner type to parse of the current user instance.
     */
    public User(String username, String password, String email, String learnerType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.learnerType = learnerType;
        this.status = "";
    }

    /**
     * Sets the bytes for the users image in Base64 encoded format.
     * @param imageBytes the Base64 encoded representation of the image
     */
    public void setImageBytes(String imageBytes){
        this.imageBytes = imageBytes;
    }

    /**
     * Sets the value for whethe to privatise the users profile
     * @param value the boolean value
     */
    @Override
    public void setPrivate(boolean value) {
        this.isPrivate = value;
    }

    /**
     * Sets the style of the user
     * @param style the style of the user
     */
    @Override
    public void setLearningStyle(String style) {
        this.learnerType = style;
    }

    /**
     * Sets the password for the current user
     * @param password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the status of the current user
     * @param status
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * The username of the current user.
     * @return The username of the user currently logged into the application.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * The password of the current user.
     * @return The password of the user currently logged into the application.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * The email of the current user.
     * @return The Email of the user currently logged into the application.
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * The learner type of the current user.
     * @return The Learner Type of the user currently logged into the application.
     */
    @Override
    public String getLearnerType() {
        return learnerType;
    }

    /**
     * Gets the status for the current users profile
     * @return the Status the user has chosen
     */
    @Override
    public String getProfileStatus(){
        return status;
    }

    /**
     * Returns the Base64 encoded represenation of the users profile image
     * @return
     */
    @Override
    public String getImageBytes() {
        return imageBytes;
    }

    /**
     * @return true if users profile is set to private
     */
    @Override
    public boolean isPrivate() {
        return isPrivate;
    }
}
