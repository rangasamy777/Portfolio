package org.envisiontechllc.supertutor.internal.wrappers.boards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

	private String title, description, imageName;
	private List<XBoard> boards;
	
	public Category(String title, String description, String imageName){
		this.title = title;
		this.description = description;
		this.imageName = imageName;
		this.boards = new ArrayList<>();
	}
	
	public void addBoard(XBoard board){
		this.boards.add(board);
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public XBoard getBoardForName(String name){
		for(XBoard board: boards){
			if(board.getBoardTitle().equals(name)){
				return board;
			}
		}
		return null;
	}
	
	public List<XBoard> getBoards(){
		return this.boards;
	}
	
	public String getImageName(){
		return this.imageName;
	}
	
	@Override
    public boolean equals(Object obj) {
    	if(obj instanceof Category){
    		Category tempCat = (Category)obj;
    		return tempCat.getTitle().equals(this.title) && tempCat.getDescription().equals(this.description);
    	}
    	return super.equals(obj);
    }
}
