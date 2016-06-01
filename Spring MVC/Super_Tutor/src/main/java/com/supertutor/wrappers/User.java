package com.supertutor.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = 94L;
	
	private String username, password, email, learnerType, status, dob;
	private String lastLogged, profileStatus;
	private String imageBytes;
	private boolean isPrivate;
	private List<String> subjects;
	
	public User(String username, String password, String email, String learnerType, String status, String dob){
		this.username = username;
		this.password = password;
		this.email = email;
		this.learnerType = learnerType;
		this.status = status;
		this.dob = dob;
		this.subjects = new ArrayList<>();
		this.profileStatus = "Hasn't set a status yet!";
	}
	
	public void addSubject(String subject){
		if(!subjects.contains(subject)){
			this.subjects.add(subject);
		}
	}
	
	public void setPrivateMode(boolean value){
		this.isPrivate = value;
	}
	
	public void setImageBytes(String bytes){
		this.imageBytes = bytes;
	}
	
	public void setStatus(String status){
		this.profileStatus = status;
	}
	
	public void setLastLogged(String lastLogged){
		this.lastLogged = lastLogged;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setLearnerType(String learnerType){
		this.learnerType = learnerType;
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
	
	public String getProfileStatus(){
		return this.profileStatus;
	}
	
	public String getDateOfBirth(){
		return this.dob;
	}
	
	public String getLastLoggedIn(){
		return this.lastLogged;
	}
	
	public String getImageBytes(){
		return this.imageBytes;
	}
	
	public boolean isPrivate(){
		return this.isPrivate;
	}
	
	public List<String> getSubjects(){
		return this.subjects;
	}
}
