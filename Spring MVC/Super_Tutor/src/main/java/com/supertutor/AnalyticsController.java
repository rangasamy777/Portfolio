package com.supertutor;

import java.util.HashMap;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supertutor.utils.JSONUtils;
import com.supertutor.wrappers.User;
import com.supertutor.wrappers.XStatus;

@RestController
@RequestMapping("api/stats")
public class AnalyticsController {
	
	public AnalyticsController(){
		this.printer = JSONUtils.getInstance();
	}
	
	private JSONUtils printer;
	private List<User> users;
	
	@RequestMapping(value="getUserbase", produces="application/json")
	public String getUserbase() throws JsonProcessingException{
		this.users = UserController.users;
		
		HashMap<String, Integer> userList = new HashMap<>();
		populateList(userList);
				
		for(User user: users){
			if(user.getStatus() != null){
				int currentValue = userList.get(user.getStatus());
				userList.replace(user.getStatus(), currentValue, currentValue + 1);
			}
		}
		
		return printer.printObject(userList);
	}
	
	private void populateList(HashMap<String, Integer> userList){
		for(XStatus status: XStatus.values()){
			userList.put(status.getStatus(), 0);
		}
	}
}
