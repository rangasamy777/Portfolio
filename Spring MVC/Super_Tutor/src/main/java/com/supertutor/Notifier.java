package com.supertutor;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.supertutor.file.FileHelper;
import com.supertutor.notifier.Notification;
import com.supertutor.utils.JSONUtils;
import com.supertutor.wrappers.Response;

@RestController
@RequestMapping("/notifier")
public class Notifier {
	
	private JSONUtils printer;
	private FileHelper fileUtils;
	private final String SERVER_API_KEY = "AIzaSyB1PBu3jqBAsjrlYs3fodOqhhh8siQiwiA", SERVER_URL = "https://android.googleapis.com/gcm/send";
	public static Notification notification = new Notification();
	
	public Notifier(){
		this.printer = JSONUtils.getInstance();
		this.fileUtils = FileHelper.getInstance();
	}
	
	@RequestMapping(value="send/{title}/{message}", produces="application/json")
	public String sendNotification(@PathVariable String title, @PathVariable String message) throws IOException{
		
		Sender sender = new Sender(SERVER_API_KEY);
		Message msg = new Message.Builder().timeToLive(30).delayWhileIdle(true)
				.collapseKey("GCMessage").addData("title", title)
				.addData("message", message).build();
		
		MulticastResult result = sender.send(msg, notification.getRegistrationIDs(), 1);
		if(result.getResults() != null && result.getSuccess() == 1){
			return printer.printObject(new Response(200));
		}

		return printer.printObject(new Response(201));
	}
	
	@RequestMapping(value="register/{device}", produces="application/json")
	public String registerDevice(@PathVariable String device) throws JsonProcessingException{
		
		if(device != null && device.length() > 3){
			if(!notification.getRegistrationIDs().contains(device)){
				if(notification.addRegistrationID(device)){
					System.out.println("Registering device " + device);
					fileUtils.writeNotifierObject(notification);
					return printer.printObject(new Response(200));
				}
			}
		}
		
		return printer.printObject(new Response(201));
	}
}
