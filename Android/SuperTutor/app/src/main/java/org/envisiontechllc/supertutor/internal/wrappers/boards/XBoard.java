package org.envisiontechllc.supertutor.internal.wrappers.boards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XBoard implements Serializable {
	
	private String boardName;
	private List<Reply> replies;
	
	public XBoard(String boardName){
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

	public ArrayList<Reply> getSerializable(){
		return new ArrayList<>(replies);
	}
}
