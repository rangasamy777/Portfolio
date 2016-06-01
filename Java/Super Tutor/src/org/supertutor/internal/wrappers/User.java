package org.supertutor.internal.wrappers;

/**
 * Created by Emile on 05/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class User {

    private String username, password, email, learnerType, status, dob, lastLogged;

    public User(String username, String password, String email, String learnerType, String status, String dob, String lastLogged){
        this.username = username;
        this.password = password;
        this.email = email;
        this.learnerType = learnerType;
        this.status = status;
        this.dob = dob;
        this.lastLogged = lastLogged;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getLearnerType(){
        return this.learnerType;
    }

    public String getStatus(){
        return this.status;
    }

    public String getDateOfBirth(){
        return this.dob;
    }

    public String getLastLogged(){
        return this.lastLogged;
    }

}
