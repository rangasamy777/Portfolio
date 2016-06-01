package com.supertutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supertutor.file.FileHelper;
import com.supertutor.graph.Graph;
import com.supertutor.graph.Vertex;
import com.supertutor.utils.Crypter;
import com.supertutor.utils.JSONUtils;
import com.supertutor.utils.Utilities;
import com.supertutor.wrappers.Response;
import com.supertutor.wrappers.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final String FROM_EMAIL = "noreply@envisiontechllc.org", 
			PORT = "465", 
			PASSWORD = "Waffl3jnr",
			HOST_NAME = "srv66.hosting24.com";
	
	public UserController(){
		this.printer = JSONUtils.getInstance();
		this.fileUtils = FileHelper.getInstance();
		this.graph = FollowerController.graph;
	}
	
	private FileHelper fileUtils;
	private JSONUtils printer;
	private Graph graph;
	public static List<User> users = new ArrayList<>();

	@RequestMapping(value="login/{username}/{password}", produces = "application/json")
	public String getUser(@PathVariable String username, @PathVariable String password) throws JsonProcessingException{
		String output = "";
		
		User user = null;
		
		if(Utilities.listHasUser(users, username)){
			user = Utilities.getUserForUsername(users, username);
			if(user != null){
				String cryptedPass = Crypter.encryptPass(user.getPassword());
				if(cryptedPass.equalsIgnoreCase(password)){
					output = printer.printObject(user);
					user.setLastLogged(Utilities.getTimestamp());
				}
			}
		}
		
		if(user == null){
			output = printer.printObject(new Response(201));
		}
		
		return output;
	}
	
	@RequestMapping(value="register/{username}/{password}/{email}/{status}/{dob}", produces = "application/json")
	public String registerUser(@PathVariable String username, @PathVariable String password, 
			@PathVariable String email, @PathVariable String status, @PathVariable String dob) throws IOException{
		
		User user = new User(username, password, email, "Not set", status, dob);
		
		if(!Utilities.listHasUser(users, username)){
			if(users.add(user)){
				fileUtils.writeUserObject(user);
				graph.addVertex(new Vertex(graph.getVertices().size(), username));
				fileUtils.writeGraph(graph);
				
				return printer.printObject(new Response(200));
			}
		} else {
			return printer.printObject(new Response(202));
		}
		
		return printer.printObject(new Response(201));
	}	
	
	@RequestMapping(value="update/{username}/{email}/{password}", produces="application/json")
	public String updateUserFile(@PathVariable String username, @PathVariable String email, @PathVariable String password) throws IOException{
		
		if(Utilities.listHasUser(users, username)){
			User user = Utilities.getUserForUsername(users, username);
			user.setPassword(password);
			user.setEmail(email);
			fileUtils.writeUserObject(user);
			return printer.printObject(new Response(200));
		}
		
		return printer.printObject(new Response(201));
	}
	
	@RequestMapping(value="updateProfile/{username}/{status}", method = RequestMethod.POST)
	public String updateProfile(@RequestParam String imageData, @PathVariable String username, @PathVariable String status) throws IOException{
		if (Utilities.listHasUser(users, username)){
			User user=Utilities.getUserForUsername(users, username);
			if(user != null){
				user.setStatus(status);
				user.setImageBytes(imageData);
				fileUtils.writeUserObject(user);
				return printer.printObject(new Response(200));
			}
		}
		return printer.printObject(new Response(201));
	}
	
	@RequestMapping(value="updatePersonality/{username}/{learnerType}", produces="application/json")
	public String updateUserStat(@PathVariable String username, @PathVariable String learnerType) throws IOException{
		
		if(Utilities.listHasUser(users, username)){
			User user = Utilities.getUserForUsername(users, username);
			user.setLearnerType(learnerType);
			fileUtils.writeUserObject(user);
			return printer.printObject(new Response(200));
		}
		
		return printer.printObject(new Response(201));
	}
	
	@RequestMapping(value="makePrivate/{username}/{value}", produces="application/json")
	public String setPrivate(@PathVariable String username, @PathVariable boolean value) throws IOException{
		
		if(Utilities.listHasUser(users, username)){
			User user = Utilities.getUserForUsername(users, username);
			if(user != null){
				user.setPrivateMode(value);
				fileUtils.writeUserObject(user);
				return printer.printObject(new Response(200));
			}
		}
		
		return printer.printObject(new Response(201));
	}
	
	@RequestMapping(value="forgotPassword/{email}", produces="application/json")
	public String getPassword(@PathVariable String email) throws JsonProcessingException{
		
		try {

			if(Utilities.listHasUserForEmail(users, email)){
				User user = Utilities.getUserForEmail(users, email);
				if(user != null){
					Properties props = new Properties();

			        props.put("mail.transport.protocol", "smtp");
			        props.put("mail.smtp.host", HOST_NAME);
			        props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.starttls.enable", "true");
			        
					Session session = Session.getDefaultInstance(props,
							  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
						}
					  });
					//session.setDebug(true);
					
					if(session != null){
						MimeMessage msg = new MimeMessage(session);
						msg.setFrom(new InternetAddress(FROM_EMAIL));
				        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				        
				        msg.setSubject("[Super Tutor]: Looks like you forgot your password!");
				        msg.setContent(Utilities.EMAIL_CONTENTS + user.getPassword() + "" + Utilities.EMAIL_CONTENTS_2, "text/html");
				        
				        Transport.send(msg);
				        return printer.printObject(new Response(200));
					}
				}
			}
			
		}catch(Exception ex){ex.printStackTrace();}
		return printer.printObject(new Response(201));
	}
}
