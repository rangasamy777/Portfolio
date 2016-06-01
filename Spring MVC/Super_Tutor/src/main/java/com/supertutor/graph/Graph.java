package com.supertutor.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable {
	
	private static final long serialVersionUID = 94L;

	private List<Vertex> vertices;

	public Graph(){
		this.vertices = new ArrayList<>();
	}
	
	public boolean isFollowing(String username, String queryName){
		if(containsUsername(username)){
			for(Edge e: getVertice(username).getEdges()){
				if(e != null && e.getToNode().getUsername().equalsIgnoreCase(queryName)){
					return true;
				}
			}
		}
		return false;
	}
	
	public List<Edge> getFollowers(String username){
		List<Edge> list = new ArrayList<>();
		
		if(containsUsername(username)){
			Vertex vertex = getVertice(username);
			if(vertex != null){
				for(Edge edge: vertex.getEdges()){
					list.add(edge);
				}
			}
		}
		
		return list;
	}
	
	public void addVertex(Vertex vertex){
		if(!containsUsername(vertex.getUsername())){
			vertices.add(vertex);
			return;
		}
	}
	
	public void addConnection(String username, Vertex vertex){
		if(containsUsername(username)){
			Vertex storedVertex = getVertice(username);
			if(storedVertex != null){
				storedVertex.addConnection(vertex);
			}
		} else {
			Vertex newUser = new Vertex(vertices.size(), username);
			newUser.addConnection(vertex);
			vertices.add(newUser);
		}
	}

	public boolean containsUsername(String username){
		return vertices.stream().filter(user -> user != null && user.getUsername().equalsIgnoreCase(username)).findAny().isPresent();
	}
	
	public Vertex getVertice(String username){
		return vertices.stream().filter(user -> user != null && user.getUsername().equalsIgnoreCase(username)).findFirst().get();
	}
	
	public List<Vertex> getVertices(){
		return this.vertices;
	}
}
