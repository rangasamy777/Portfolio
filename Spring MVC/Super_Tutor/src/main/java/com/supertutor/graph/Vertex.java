package com.supertutor.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Serializable {
	
	private static final long serialVersionUID = 94L;

	private int vertexID;
	private String username;
	private List<Edge> edges;
	
	public Vertex(int vertexID, String username){
		this.vertexID = vertexID;
		this.username = username;
		this.edges = new ArrayList<>();
	}
	
	public void addConnection(Vertex vertex){
		Edge tEdge = new Edge(this, vertex);
		if(!edges.contains(tEdge)){
			this.edges.add(tEdge);
		}
	}
	
	public int getVertexID(){
		return this.vertexID;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public List<Edge> getEdges(){
		return this.edges;
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof Vertex){
			Vertex tempVertex = (Vertex)object;
			return tempVertex.getUsername().equals(this.getUsername());
		}
		return false;
	}
}
