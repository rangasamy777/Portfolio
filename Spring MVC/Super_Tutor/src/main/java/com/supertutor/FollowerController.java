package com.supertutor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supertutor.file.FileHelper;
import com.supertutor.graph.Edge;
import com.supertutor.graph.Graph;
import com.supertutor.graph.Vertex;
import com.supertutor.utils.JSONUtils;
import com.supertutor.utils.Utilities;
import com.supertutor.wrappers.FollowerStat;
import com.supertutor.wrappers.ProfileFeed;
import com.supertutor.wrappers.Response;
import com.supertutor.wrappers.User;

@RestController
@RequestMapping("api/followers")
public class FollowerController {
	
	public static Graph graph;
	private JSONUtils utils;
	private FileHelper fileUtils;
	
	public FollowerController(){
		this.utils = JSONUtils.getInstance();
		this.fileUtils = FileHelper.getInstance();
		this.graph = fileUtils.getGraphFile();
	}
	
	@RequestMapping(value="addFollower/{username}/{follower}", produces="application/json")
	public String addFollower(@PathVariable String username, @PathVariable String follower) throws JsonProcessingException{
		
		if(graph.containsUsername(username)){
			if(Utilities.listHasUser(UserController.users, follower)){
				User userFollower = Utilities.getUserForUsername(UserController.users, follower);
				if(userFollower != null && !userFollower.isPrivate()){
					Vertex newFollower = (graph.containsUsername(follower) ? graph.getVertice(follower) : new Vertex(graph.getVertices().size(), follower));
					graph.addConnection(username, newFollower);
					
					fileUtils.writeGraph(graph);
					
					return utils.printObject(new Response(200));
				} else {
					return utils.printObject(new Response(203));
				}
			} else {
				return utils.printObject(new Response(202));
			}
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="getFollowers/{username}", produces="application/json")
	public String getFollowers(@PathVariable String username) throws JsonProcessingException{
		
		if(Utilities.listHasUser(UserController.users, username)){
			List<Edge> followers = graph.getFollowers(username);
			if(followers != null && followers.size() > 0){
				List<FollowerStat> users = new ArrayList<>();
				
				for(Edge follower: followers){
					User user = Utilities.getUserForUsername(UserController.users, follower.getToNode().getUsername());
					if(user != null){
						users.add(new FollowerStat(user.getUsername(), user.getProfileStatus(), user.getImageBytes()));
					}
				}
				return utils.printObject(users);
			}
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="getFeed/{username}", produces="application/json")
	public String getProfileFeed(@PathVariable String username) throws JsonProcessingException{
		
		if(Utilities.listHasUser(UserController.users, username)){
			User user = Utilities.getUserForUsername(UserController.users, username);
			
			List<ProfileFeed> feed = new ArrayList<>();
			
			if(user != null){
				for(String studying: user.getSubjects()){
					feed.add(new ProfileFeed(studying));
				}
				return utils.printObject(feed);
			}
		}
		
		return utils.printObject(new Response(201));
	}
}
