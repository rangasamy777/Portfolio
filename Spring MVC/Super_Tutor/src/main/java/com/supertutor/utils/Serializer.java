package com.supertutor.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

	private static final long serialVersionUID = 94L;
	
	private FileInputStream stream;
	private ObjectInputStream obj;
	private FileOutputStream file;
	private ObjectOutputStream writer;
	
	public Serializer(){
		//System.out.println("[Serializer]: Successfully initialised.");
	}
	
	public void serializeObject(File tempFile, Object object){
		try {
			file = new FileOutputStream(tempFile);
			writer = new ObjectOutputStream(file);
			writer.writeObject(object);
			writer.close();
			file.close();
			//System.out.println("[SERIALIZER]: Successfully wrote object to file.");
		}catch(IOException ex){
			//ex.printStackTrace();
		} 
	}
	
	public Object deserializeObject(File file){
		
		Object tObject = null;
		
		try {
			stream = new FileInputStream(file);
			obj = new ObjectInputStream(stream);
			
			tObject = obj.readObject();
			if(tObject != null){
				//System.out.println("[DESERIALIZER]: Successfully read object.");
			}
			obj.close();
			stream.close();
		}catch(IOException | ClassNotFoundException ex){
			//ex.printStackTrace();
		}
		
		return tObject;
	}

}
