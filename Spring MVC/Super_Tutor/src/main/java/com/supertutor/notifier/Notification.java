package com.supertutor.notifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification implements Serializable {

	private static final long serialVersionUID = 94L;

	private List<String> registrationIDs;
	private Map<String, String> messages;
	
	public Notification(){
		this.registrationIDs = new ArrayList<>();
		this.messages = new HashMap<>();
	}
	
	public boolean addRegistrationID(String id){
		return this.registrationIDs.add(id);
	}
	
	public void addMessage(String title, String message){
		this.messages.put("title", title);
		this.messages.put("message", message);
	}
	
	public List<String> getRegistrationIDs(){
		return this.registrationIDs;
	}
	
	public Map<String, String> getMessages(){
		return this.messages;
	}
}
