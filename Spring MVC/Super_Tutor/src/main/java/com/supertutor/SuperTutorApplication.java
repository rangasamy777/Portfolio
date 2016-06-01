package com.supertutor;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.supertutor.file.FileHelper;
import com.supertutor.graph.Graph;
import com.supertutor.graph.Vertex;

@SpringBootApplication
public class SuperTutorApplication {
	
	private static String separator = "===================================================================================================";

	public static void main(String[] args) {
		SpringApplication.run(SuperTutorApplication.class, args);
		
		FileHelper fileUtils = FileHelper.getInstance();
		
		System.out.println(separator);
		
		System.out.println("Loading Subject files from Database...");
		FileController.subjects = fileUtils.getAllSubjects();
		
		System.out.println("Loading User data from Database...");
		UserController.users = fileUtils.loadAllUsers();
		
		System.out.println("Loading Discussion boards from Database...");
		BoardController.categories = fileUtils.getAllCategories();

		System.out.println("Loading Followers Map from Database...");
		System.out.println("Successfully loaded map with " + FollowerController.graph.getVertices().size() + " connections!");
		
		System.out.println("Loading Device IDs");
		Notifier.notification = fileUtils.getNotification();
		System.out.println("Successfully loaded " + Notifier.notification.getRegistrationIDs().size() + " devices.");
		
		System.out.println(separator);
	}
}
