package com.supertutor.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.supertutor.graph.Graph;
import com.supertutor.notifier.Notification;
import com.supertutor.utils.Serializer;
import com.supertutor.utils.Utilities;
import com.supertutor.wrappers.Category;
import com.supertutor.wrappers.Subject;
import com.supertutor.wrappers.Topic;
import com.supertutor.wrappers.User;

public class FileHelper {

	public static File baseDirectory = new File(System.getProperty("user.home") + "/Super Tutor/");
	public static File userDirectory = new File(baseDirectory + "/users");
	public static File userImageDirectory = new File(userDirectory + "/images");
	public static File subjectDirectory = new File(baseDirectory + "/subjects");
	public static File legacyDirectory = new File(subjectDirectory + "/legacy_files");
	public static File boardDirectory = new File(baseDirectory + "/Boards");
	public static File boardImages = new File(boardDirectory + "/images");
	public static File graphDirectory = new File(baseDirectory + "/map");
	public static File graphFile = new File(graphDirectory + "/FollowersMap.ser");
	public static File notifierFile = new File(baseDirectory + "/Notifier.ser");
	
	private static FileHelper instance;
	private Serializer serializer;
	
	private FileHelper(){
		this.initialiseModule();
		this.serializer = new Serializer();
	}
	
	public void initialiseModule(){
		System.out.println("[FILE HELPER]: Initialising module...");
		
		if(!baseDirectory.exists()){
			baseDirectory.mkdirs();
		}
		
		if(!userDirectory.exists()){
			userDirectory.mkdirs();
		}
		
		if(!userImageDirectory.exists()){
			userImageDirectory.mkdir();
		}
		
		if(!subjectDirectory.exists()){
			subjectDirectory.mkdirs();
		}
		
		if(!legacyDirectory.exists()){
			legacyDirectory.mkdirs();
		}
		
		if(!boardDirectory.exists()){
			boardDirectory.mkdir();
		}
		
		if(!boardImages.exists()){
			boardImages.mkdir();
		}
		
		if(!graphDirectory.exists()){
			graphDirectory.mkdir();
		}
		
		if(!graphFile.exists()){
			try {
				graphFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!notifierFile.exists()){
			try {
				notifierFile.createNewFile();
			}catch(Exception ex){}
		}
		
		System.out.println("[FILE HELPER]: Successfully initialised module..");
	}
	
	public void makeLegacy(String name){
		try {
			File subjectFolder = new File(subjectDirectory + "/" + name);
			if(subjectFolder != null && subjectFolder.exists()){
				File destinationPath = new File(legacyDirectory + "/" + name + "_" + Utilities.getFileIdentifier());
				Files.move(subjectFolder.toPath(), destinationPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("[LEGACY MOVE] Moved " + name + " to " + destinationPath.getPath());
			}
		}catch(Exception ex){}
	}
	
	public void deleteFile(String subject, Topic topic){
		try {
			File file = new File(subjectDirectory + "/" + subject + "/" + topic.getFileName());
			if(file != null){
				if(file.delete()){
					System.out.println("Successfully deleted file.");
				}
			}
		}catch(Exception ex){}
	}
	
	public List<User> loadAllUsers(){
		List<User> list = new ArrayList<>();
		File[] files = getUserFiles();
		
		for(File file: files){
			User tUser = readUserObject(file);
			if(tUser != null){
				list.add(tUser);
			}
		}
		
		System.out.println("Loaded " + list.size() + " User files.");
		
		return list;
	}
	
	public User findUser(String name){
		File[] files = getUserFiles();
		
		for(File file: files){
			User tUser = readUserObject(file);
			if(tUser != null){
				if(tUser.getUsername().equals(name)){
					System.out.println("[FILE SEARCHER]: Found user " + tUser.getUsername());
					return tUser;
				}
			}
		}
		
		System.out.println("[FILE SEARCHER]: No results.");
		
		return null;
	}
	
	public File getUserImage(String username){
		File userImage = new File(userImageDirectory + "/" + username + ".png");
		return userImage;
	}
	
	public void writeUserObject(User user) throws IOException{
		File userFile = new File(userDirectory + "/" + user.getUsername() + ".ser");
		if(userFile != null){
			serializer.serializeObject(userFile, user);
		}
	}
	
	public void writeSubjectObject(Subject subject){
		File subjectDirectory = new File(this.subjectDirectory + "/" + subject.getSubjectName());
		File subjectFile = new File(subjectDirectory + "/" + subject.getSubjectName() + ".ser");
		if(subjectFile != null){
			serializer.serializeObject(subjectFile, subject);
		}
	}
	
	public void writeNotifierObject(Notification notification){
		serializer.serializeObject(notifierFile, notification);
	}
	
	public void writeCategory(Category category){
		try {
			File catFile = new File(boardDirectory + "/" + category.getTitle() + ".ser");
			if(!catFile.exists()){
				catFile.createNewFile();
			}
			serializer.serializeObject(catFile, category);
		}catch(IOException ex){}
	}
	
	public void writeGraph(Graph graph){
		if(graphFile != null){
			serializer.serializeObject(graphFile, graph);
		}
	}
	
	public boolean checkFileExists(String filename){
		File tempFile = new File(subjectDirectory + "/" + filename);
		if(!tempFile.exists()){
			return tempFile.mkdirs();
		}
		return false;
	}
	
	public File getSubjectDirectory(String name){
		return new File(subjectDirectory + "/" + name);
	}
	
	public List<Subject> getAllSubjects(){
		List<Subject> list = new ArrayList<>();
		File[] subjectFiles = getSubjectFiles();
		
		for(File file: subjectFiles){
			String subjectFolder = file.getName();
			if(!subjectFolder.contains("_")){
				File subjectFile = new File(file + "/" + subjectFolder + ".ser");
				if(subjectFile != null){
					Subject subjectObject = readSubjectObject(subjectFile);
					if(subjectObject != null){
						list.add(subjectObject);
					}
				}
			}
		}
		System.out.println("Loaded " + list.size() + " Subject files.");
		
		return list;
	}
	
	public List<Category> getAllCategories(){
		List<Category> list = new ArrayList<>();
		File[] categoryFiles = boardDirectory.listFiles();
		
		int discussionBoards = 0;
		
		for(File file: categoryFiles){
			Category category = (Category)serializer.deserializeObject(file);
			if(category != null){
				discussionBoards += category.getBoards().size();
				list.add(category);
			}
		}
		
		System.out.println("Loaded " + list.size() + " Categories with " + discussionBoards + " Discussion boards.");
		
		return list;
	}
	
	public Subject getSubjectByName(String name, String description){
		File subject = new File(subjectDirectory + "/" + name + "/" + name + ".ser");
		if(subject.exists()){
			return readSubjectObject(subject);
		} 
		new File(subjectDirectory + "/" + name).mkdirs();
		return new Subject(name, description);
	}
	
	public Category getCategoryForName(String name){
		File file = new File(boardDirectory + "/" + name + ".ser");
		if(file.exists()){
			Category category = (Category)serializer.deserializeObject(file);
			if(category != null){
				return category;
			}
		}
		return null;
	}
	
	public User readUserObject(File file){
		return (User)serializer.deserializeObject(file);
	}
	
	public Subject readSubjectObject(File file){
		return (Subject)serializer.deserializeObject(file);
	}
	
	public Graph getGraphFile(){
		Graph graph = (Graph)serializer.deserializeObject(graphFile);
		if(graph != null){
			return graph;
		}
		return new Graph();
	}
	
	public Notification getNotification(){
		Notification nt = (Notification)serializer.deserializeObject(notifierFile);
		if(nt != null){
			return nt;
		}
		return new Notification();
	}

	public File[] getUserFiles(){
		return userDirectory.listFiles();
	}
	
	public File[] getSubjectFiles(){
		return subjectDirectory.listFiles();
	}
	
	public static FileHelper getInstance(){
		if(instance == null){
			instance = new FileHelper();
		}
		return instance;
	}
}
