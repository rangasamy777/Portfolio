package org.envisiontechllc.supertutor.internal.wrappers.boards;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reply implements Serializable {

	private String username, timestamp, content;
	
	public Reply(String username, String timestamp, String content){
		this.username = username;
		this.content = content;
		this.setTimestamp(timestamp);
	}
	
	public String getAuthorName(){
		return this.username;
	}
	
	public String getTimestamp(){
		return this.timestamp;
	}
	
	public String getContent(){
		return this.content;
	}

	private void setTimestamp(String timestamp){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd mm yyyy");
			Date dateOne = sdf.parse(timestamp), dateTwo = sdf.parse(sdf.format(new Date()));

			long diff = dateTwo.getTime() - dateOne.getTime();
			this.timestamp = setUnit(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		}catch(Exception ex){}
	}

	private String setUnit(long days){
		return (days > 0 ? days + " days ago" : "Today");
	}
}
