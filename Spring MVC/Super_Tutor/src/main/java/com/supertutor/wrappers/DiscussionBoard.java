package com.supertutor.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DiscussionBoard implements Serializable {

	private static final long serialVersionUID = 94L;
	
	private String boardName;
	private List<Reply> replies;
	
	public DiscussionBoard(String boardName){
		this.boardName = boardName;
		this.replies = new ArrayList<>();
	}
	
	public void addReply(Reply reply){
		this.replies.add(reply);
	}

	public String getBoardTitle(){
		return this.boardName;
	}
	
	public List<Reply> getReplies(){
		return this.replies;
	}
}
