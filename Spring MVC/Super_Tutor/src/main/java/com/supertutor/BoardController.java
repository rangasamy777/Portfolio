package com.supertutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supertutor.file.FileHelper;
import com.supertutor.utils.JSONUtils;
import com.supertutor.utils.Utilities;
import com.supertutor.wrappers.Category;
import com.supertutor.wrappers.DiscussionBoard;
import com.supertutor.wrappers.Reply;
import com.supertutor.wrappers.Response;

@RestController
@RequestMapping("/boards")
public class BoardController {

	public static List<Category> categories = new ArrayList<>();
	private FileHelper fileUtils;
	private JSONUtils utils;
	
	public BoardController(){
		this.fileUtils = FileHelper.getInstance();
		this.utils = JSONUtils.getInstance();
	}
	
	@RequestMapping(value="addReply/{board}/{username}/{content}", produces="application/json")
	public String addReplyToBoard(@PathVariable String board, @PathVariable String username, @PathVariable String content) throws JsonProcessingException{
		
		if(Utilities.hasBoard(categories, board)){
			DiscussionBoard tBoard = Utilities.getDiscussionBoard(categories, board);
			tBoard.addReply(new Reply(username, Utilities.getDatestamp() ,content));

			Category category = Utilities.getCategoryForBoard(categories, board);
			fileUtils.writeCategory(category);
			
			return utils.printObject(new Response(200));
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="addCategory/{name}/{description}/{imageName}", produces="application/json")
	public String addCategory(@PathVariable String name, @PathVariable String description, @PathVariable String imageName) throws JsonProcessingException{
		
		Category newCat = new Category(name, description, imageName);
		if(!Utilities.hasCategory(categories, newCat.getTitle())){
			categories.add(newCat);
			fileUtils.writeCategory(newCat);
			return utils.printObject(new Response(200));
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="addBoard/{categoryName}/{board}", produces="application/json")
	public String addBoard(@PathVariable String categoryName, @PathVariable String board) throws JsonProcessingException{
		
		if(!Utilities.hasBoard(categories, board)){
			DiscussionBoard newBoard = new DiscussionBoard(board);
			Category category = Utilities.getCategory(categories, categoryName);
			category.addBoard(newBoard);
			fileUtils.writeCategory(category);
			return utils.printObject(new Response(200));
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="getImage/{name}")
	public String getCategoryImage(HttpServletResponse response, @PathVariable String name) throws IOException{
		
		if(Utilities.hasCategory(categories, name)){
			Category category = Utilities.getCategory(categories, name);
			if(category != null){
				File imageFile = new File(fileUtils.boardImages + "/" + category.getImageName() + ".png");
				
				FileInputStream ins = new FileInputStream(imageFile);
				IOUtils.copy(ins, response.getOutputStream());
				
				response.setContentType("image/png");
				response.setContentLength((int) imageFile.length());
				response.flushBuffer();
				return utils.printObject(new Response(200));
			}
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="getCategories", produces="application/json")
	public String getCategories() throws JsonProcessingException{
		
		if(categories.size() > 0){
			return utils.printObject(categories);
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="getReplies/{boardName}", produces="application/json")
	public String getReplies(@PathVariable String boardName) throws JsonProcessingException{
		
		if(Utilities.hasBoard(categories, boardName)){
			DiscussionBoard board = Utilities.getDiscussionBoard(categories, boardName);
			return utils.printObject(board.getReplies());
		}
		
		return utils.printObject(new Response(201));
	}
}
