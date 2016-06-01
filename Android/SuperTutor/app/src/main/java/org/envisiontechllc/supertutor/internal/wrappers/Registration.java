package org.envisiontechllc.supertutor.internal.wrappers;

import org.envisiontechllc.supertutor.internal.enums.XStatus;
import org.envisiontechllc.supertutor.internal.wrappers.interfaces.XProcess;

/**
 * Created by EmileBronkhorst on 01/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class Registration implements XProcess {

    private String username, password, email;
    private int age = -1;
    private XStatus status;

    public Registration(String username, String password){
        this.username = username;
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public XStatus getStatus(){
        return this.status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStatus(String status){
        for(XStatus stat: XStatus.values()){
            if(stat.getStatus().equals(status)){
                this.status = stat;
            }
        }
    }

    @Override
    public boolean isComplete() {
        return username != null && password != null && email != null && age != -1
                && status != null;
    }

    @Override
    public boolean inProgress() {
        return username != null && password != null;
    }

    public int submitForm(){
        return -1;
    }
}
