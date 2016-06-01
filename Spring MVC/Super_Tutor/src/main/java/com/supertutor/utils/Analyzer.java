package com.supertutor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.supertutor.wrappers.Topic;

public class Analyzer {

	public static List<Topic> createTopics(String[] titles, String[] descriptions, String[] fileNames){
		List<Topic> list = new ArrayList<>();
		
		System.out.println("Received " + titles.length + " topics to analyze.");
		
		for(int i =0; i < titles.length; i++){
			try {
				String title = titles[i], description = descriptions[i], fileName = stripFilename(fileNames[i]);
				list.add(new Topic(title, description, fileName));
			}catch(ArrayIndexOutOfBoundsException ex){}
		}
		
		
		return list;
	}
	
	public static List<Topic> getTopicsFromJSON(JSONArray array){
		List<Topic> topicsList = new ArrayList<>();
		
		try {
			for(int i = 0; i < array.size(); i++){
				JSONObject topicObj = (JSONObject)array.get(i);
				String title = (String)topicObj.get("topicName");
				String description = (String)topicObj.get("topicTag");
				String fileName = stripFilename((String)topicObj.get("fileName"));
				
				topicsList.add(new Topic(title, description, fileName));
			}
		}catch(ArrayIndexOutOfBoundsException ex){}
		
		return topicsList;
	}
	
	public static String stripFilename(String filename){
		return filename.substring(filename.lastIndexOf("/") + 1, filename.length());
	}
}
