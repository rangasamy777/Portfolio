package com.supertutor.wrappers;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.supertutor.BoardController;
import com.supertutor.utils.Utilities;

public class ProfileFeed {

	private String subjectName, category;
	private String timestamp;
	
	public ProfileFeed(String subjectName){
		this.subjectName = subjectName;
		this.category = getCategory(subjectName);
		this.setTimestamp();
	}
	
	public String getCategory(String subject){
		for(Category category: BoardController.categories){
			for(DiscussionBoard board: category.getBoards()){
				if(subject.equalsIgnoreCase(board.getBoardTitle())){
					return category.getTitle();
				}
			}
		}
		return "";
	}
	
	private void setTimestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timestamp = sdf.format(new Date());
	}
	
	public String getSubjectname(){
		return this.subjectName;
	}
	
	public String getCategoryname(){
		return this.category;
	}
	
	public String getTimestamp(){
		return this.timestamp;
	}
	
}
