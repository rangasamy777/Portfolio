package com.supertutor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	private static JSONUtils instance;
	private ObjectMapper printer;
	
	private JSONUtils(){
		this.printer = new ObjectMapper();
	}
	
	public String printObject(Object object) throws JsonProcessingException{
		return printer.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}
	
	public static JSONUtils getInstance(){
		if(instance == null){
			instance = new JSONUtils();
		}
		return instance;
	}
}
