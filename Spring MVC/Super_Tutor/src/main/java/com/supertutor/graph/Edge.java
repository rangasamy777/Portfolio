package com.supertutor.graph;

import java.io.Serializable;

public class Edge implements Serializable {
	
	private static final long serialVersionUID = 94L;
	
	private Vertex fromNode, toNode;
	
	public Edge(Vertex fromNode, Vertex toNode){
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
	
	public Vertex getFromNode(){
		return this.fromNode;
	}
	
	public Vertex getToNode(){
		return this.toNode;
	}
	
	@Override 
	public boolean equals(Object obj){
		if(obj instanceof Edge){
			Edge tEdge = (Edge)obj;
			return tEdge.getFromNode().getUsername().equals(this.fromNode.getUsername()) && tEdge.getToNode().getUsername().equalsIgnoreCase(this.toNode.getUsername());
		}
		return false;
	}

}
