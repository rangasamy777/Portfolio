package com.supertutor.wrappers;

import java.io.Serializable;

public class Reply implements Serializable {

	private static final long serialVersionUID = 94L;
	
	private String username, timestamp, content;
	
	public Reply(String username, String timestamp, String content){
		this.username = username;
		this.timestamp = timestamp;
		this.content = content;
	}
	
	public String getAuthorName(){
		return this.username;
	}
	
	public String getTimestamp(){
		return this.timestamp;
	}
	
	public String getContent(){
		return this.content;
	}

}
