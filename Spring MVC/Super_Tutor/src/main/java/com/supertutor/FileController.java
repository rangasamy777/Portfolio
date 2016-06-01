package com.supertutor;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supertutor.file.FileHelper;
import com.supertutor.utils.Analyzer;
import com.supertutor.utils.JSONUtils;
import com.supertutor.utils.Utilities;
import com.supertutor.wrappers.ProfileFeed;
import com.supertutor.wrappers.Response;
import com.supertutor.wrappers.Subject;
import com.supertutor.wrappers.Topic;
import com.supertutor.wrappers.User;

@RestController
@RequestMapping("/files")
public class FileController {

	private FileHelper fileUtils;
	private JSONUtils utils;
	public static List<Subject> subjects = new ArrayList<>();
	
	public FileController(){
		this.fileUtils = FileHelper.getInstance();
		this.utils = JSONUtils.getInstance();
	}
	
	@RequestMapping(value="uploadSubject", method = RequestMethod.POST)
	public String uploadSubject(@RequestParam String jsonString) throws JsonProcessingException{
		
		try {
			JSONObject subjectData = (JSONObject) new JSONParser().parse(jsonString);
			if(subjectData != null){
				String subjectName = (String)subjectData.get("subjectName");
				String description = (String)subjectData.get("description");
				
				List<Topic> topics = Analyzer.getTopicsFromJSON((JSONArray)subjectData.get("topics"));
				if(topics != null){
					Subject subject = fileUtils.getSubjectByName(subjectName, description);
					if(subject != null){
						subject.addTopics(topics.toArray(new Topic[topics.size()]));
						
						if(!subjects.contains(subject)){
							subjects.add(subject);
						} else {
							Utilities.replaceSubject(subjects, subject);
						}
						fileUtils.writeSubjectObject(subject);
						
						System.out.println("Processed a total of " + subject.getTopics().size() + " topics");
						return utils.printObject(new Response(200));
					}
				}
			}
		}catch(ParseException ex){}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="removeTopic/{subjectName}/{topicName}", produces="application/json")
	public String removeTopicFromSubject(@PathVariable String subjectName, @PathVariable String topicName) throws JsonProcessingException{
		Subject subject = Utilities.getSubjectForName(subjects, subjectName);
		if(subject != null){
			List<Topic> topics = subject.getTopics();
			if(Utilities.hasTopic(topics, topicName)){
				Topic topic = Utilities.getTopicForName(topics, topicName);
				if(topic != null){
					if(subject.getTopics().remove(topic)){
						fileUtils.writeSubjectObject(subject);
						fileUtils.deleteFile(subjectName, topic);
						Utilities.replaceSubject(subjects, subject);
						return utils.printObject(new Response(200));
					}
				}
			}
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="getSubjects", method=RequestMethod.GET)
	public String getAllTopics() throws JsonProcessingException{
		return utils.printObject(subjects);
	}
	
	@RequestMapping(value = "makeLegacy/{subject}", produces = "application/json")
	public String removeSubject(@PathVariable String subject) throws JsonProcessingException{
		
		if(Utilities.listHasSubject(subjects, subject)){
			Subject removableSubject = Utilities.getSubjectForName(subjects, subject);
			if(removableSubject != null){
				fileUtils.makeLegacy(removableSubject.getSubjectName());
				subjects.remove(removableSubject);
				return utils.printObject(new Response(200));
			}
		}
		
		return utils.printObject(new Response(201));
	}
	
	@RequestMapping(value="upload", method = RequestMethod.POST)
	public String uploadSubject(@RequestParam("subjectName") String subjectName,
			@RequestParam("subjectDescription") String subjectDescription,
			@RequestParam("topicName") String topicName, @RequestParam("file") MultipartFile file) throws JsonProcessingException{

		Subject subject = fileUtils.getSubjectByName(subjectName, subjectDescription);
		if(file != null){
			try {
				File subjectDirectory = fileUtils.getSubjectDirectory(subjectName);
				if(subjectDirectory != null){
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(subjectDirectory + "/" + file.getOriginalFilename())));
					FileCopyUtils.copy(file.getInputStream(), out);
					out.close();
					fileUtils.writeSubjectObject(subject);
					return utils.printObject(new Response(200));
				}
			}catch(Exception ex){}
			
		}
		
		return utils.printObject(new Response(202));
	}
	
	@RequestMapping(value="uploadTest", method = RequestMethod.POST)
	public String uploadSubjectTest(@RequestParam("subjectName") String subjectName,
			@RequestParam("subjectDescription") String subjectDescription,
			@RequestParam("file") MultipartFile file) throws JsonProcessingException{

		Subject subject = fileUtils.getSubjectByName(subjectName, subjectDescription);
		if(file != null){
			try {
				File subjectDirectory = fileUtils.getSubjectDirectory(subjectName);
				if(subjectDirectory != null){
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(subjectDirectory + "/" + subject.getSubjectName() + " test.txt")));
					FileCopyUtils.copy(file.getInputStream(), out);
					out.close();
					fileUtils.writeSubjectObject(subject);
					return utils.printObject(new Response(200));
				}
			}catch(Exception ex){}
			
		}
		
		return utils.printObject(new Response(202));
	}
	
	@RequestMapping(value="downloadTest/{subject}")
	public void downloadTestFile(HttpServletResponse response, @PathVariable String subject) throws JsonProcessingException{
		try {
			File subjectDir = fileUtils.getSubjectDirectory(subject);
			if(subjectDir != null && subjectDir.exists()){
				File testFile = new File(subjectDir + "/" + subject + " test.txt");
				if(testFile != null && testFile.exists()){
					int fileLength = (int)testFile.length();
					
					response.setContentLengthLong(fileLength);
					response.setContentType("text/plain");
					
					FileInputStream in = new FileInputStream(testFile);
					IOUtils.copy(in, response.getOutputStream());
					
					response.flushBuffer();
				}
			}
		}catch(Exception ex){}
	}
	
	@RequestMapping(value="download/{username}/{subject}/{filename}")
	public void downloadFile(HttpServletResponse response, @PathVariable String username, @PathVariable String subject, @PathVariable String filename) throws JsonProcessingException{
		
		try {
			File subjectDir = fileUtils.getSubjectDirectory(subject);
			if(subjectDir != null && subjectDir.exists()){
				File downloadFile = new File(subjectDir + "/" +  filename + ".png");
				
				response.setContentLength((int)downloadFile.length());
				response.setContentType("image/png");
				
				FileInputStream ins = new FileInputStream(downloadFile);
				OutputStream outStream = response.getOutputStream();
				 
		        byte[] buffer = new byte[1024];
		        int bytesRead = -1;
		        while ((bytesRead = ins.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }

				response.flushBuffer();

				if(Utilities.listHasUser(UserController.users, username)){
					User user = Utilities.getUserForUsername(UserController.users, username);
					if(user != null){
						user.addSubject(subject);
						fileUtils.writeUserObject(user);
					}
				}
			}
		}catch(Exception ex){}
	}
}
