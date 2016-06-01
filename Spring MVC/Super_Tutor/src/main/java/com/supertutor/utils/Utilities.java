package com.supertutor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.supertutor.wrappers.Category;
import com.supertutor.wrappers.DiscussionBoard;
import com.supertutor.wrappers.Subject;
import com.supertutor.wrappers.Topic;
import com.supertutor.wrappers.User;

public class Utilities {
	
	public static final String EMAIL_CONTENTS = "<html><body><table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n\t<tbody>\r\n\t\t<tr>\r\n\t\t\t<td>\r\n\t\t\t\t<div class=\"innerbg\">\r\n\t\t\t\t</div>\r\n\t\t\t\t<table bgcolor=\"#ffffff\" width=\"580\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidth\">\r\n\t\t\t\t\t<tbody>\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<td width=\"100%\" height=\"20\">\r\n\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t\t<table width=\"540\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidthinner\">\r\n\t\t\t\t\t\t\t\t\t<tbody>\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<!-- start of image -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"imgpop\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"uploader_wrap\" style=\"width: 540px; margin-top: 121px; opacity: 0;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"upload_buttons\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"img_link\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"img_upload\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"img_edit\" style=\"visibility: visible;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div> <a href=\"#\"><img width=\"540\" border=\"0\" height=\"282\" alt=\"\" style=\"display:block; border:none; outline:none; text-decoration:none;\" src=\"http://i.imgur.com/kvWpnx2.png\" class=\"bigimage\" /></a>\r\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- end of image -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" height=\"20\">\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<!-- title -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\tDear User\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- end of title -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" height=\"20\">\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<!-- content -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: Helvetica, arial, sans-serif; font-size: 13px; color: #95a5a6; text-align:left;line-height: 24px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\tThis email is a confirmation of your password recovery. Your password last known is:\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t<span style=\"color: rgb(51, 102, 255);\"><strong>";
	public static final String EMAIL_CONTENTS_2 = "</strong></span>\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\tKind regards,\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\tSuper Tutor Support Team\r\n\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- end of content -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" height=\"10\">\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- button -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"buttonbg\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t\t<table height=\"30\" align=\"left\" valign=\"middle\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablet-button\" bgcolor=\"#0db9ea\" style=\"background-color:#0db9ea; border-top-left-radius:4px; border-bottom-left-radius:4px;border-top-right-radius:4px; border-bottom-right-radius:4px; background-clip: padding-box;font-size:13px; font-family:Helvetica, arial, sans-serif; text-align:center;  color:#ffffff; font-weight: 300; padding-left:18px; padding-right:18px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"auto\" align=\"center\" valign=\"middle\" height=\"30\" style=\"padding-left:18px; padding-right:18px;font-family:Helvetica, arial, sans-serif; text-align:center;  color:#ffffff; font-weight: 300;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span style=\"color: #ffffff; font-weight: 300;\"></span>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\r\n\t\t\t\t\t\t\t\t\t\t\t\t</table>\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- /button -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" height=\"20\">\r\n\t\t\t\t\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t\t\t\t\t<!-- Spacing -->\r\n\r\n\t\t\t\t\t\t\t\t\t</tbody>\r\n\t\t\t\t\t\t\t\t</table>\r\n\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</tbody>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</tbody>\r\n</table></body>\r\n</html>";
			
	public static boolean listHasUser(List<User> objects, String predicate){		
		return objects.stream().filter(user -> user != null && user.getUsername().equalsIgnoreCase(predicate)).findFirst().isPresent();
	}
	
	public static User getUserForUsername(List<User> objects, String username){
		return objects.stream().filter(user -> user != null && user.getUsername().equalsIgnoreCase(username)).findFirst().get();
	}
	
	public static boolean listHasUserForEmail(List<User> list, String email){
		return list.stream().filter(user -> user != null && user.getEmail().contains(email)).findFirst().isPresent();
	}
	
	public static User getUserForEmail(List<User> list, String email){
		return list.stream().filter(user -> user != null && user.getEmail().contains(email)).findFirst().get();
	}
	
	public static boolean listHasSubject(List<Subject> subjects, String name){
		return subjects.stream().filter(subject -> subject != null && subject.getSubjectName().equals(name)).findFirst().isPresent();
	}
	
	public static Subject getSubjectForName(List<Subject> subjects, String name){
		return subjects.stream().filter(subject -> subject != null && subject.getSubjectName().equals(name)).findFirst().get();
	}
	
	public static boolean hasTopic(List<Topic> topics, String name){
		return topics.stream().filter(topic -> topic != null && topic.getTopicName().equalsIgnoreCase(name)).findAny().isPresent();
	}
	
	public static Topic getTopicForName(List<Topic> topics, String name){
		return topics.stream().filter(topic -> topic != null && topic.getTopicName().equalsIgnoreCase(name)).findFirst().get();
	}
	
	public static boolean hasCategory(List<Category> categories, String name){
		return categories.stream().filter(cat -> cat != null && cat.getTitle().equalsIgnoreCase(name)).findFirst().isPresent();
	}
	
	public static Category getCategory(List<Category> list, String title){
		return list.stream().filter(cat -> cat != null && cat.getTitle().equalsIgnoreCase(title)).findFirst().get();
	}
	
	public static boolean hasBoard(List<Category> list, String boardName){
		for(Category category: list){
			for(DiscussionBoard board: category.getBoards()){
				if(board.getBoardTitle().equals(boardName)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static DiscussionBoard getDiscussionBoard(List<Category> list, String boardName){
		for(Category category: list){
			for(DiscussionBoard board: category.getBoards()){
				if(board.getBoardTitle().equals(boardName)){
					return board;
				}
			}
		}
		return null;
	}
	
	public static Category getCategoryForBoard(List<Category> list, String boardName){
		for(Category cat: list){
			for(DiscussionBoard board: cat.getBoards()){
				if(board.getBoardTitle().equals(boardName)){
					return cat;
				}
			}
		}
		return null;
	}
	
	public static void replaceSubject(List<Subject> subjects, Subject subject){
		int index = subjects.indexOf(subject);
		if(index != -1){
			Subject tempSubject = subjects.get(index);
			if(tempSubject != null){
				List<Topic> topics = tempSubject.getTopics();
				subjects.remove(index);
				subject.addTopics(topics.toArray(new Topic[topics.size()]));
				subjects.add(index, subject);
			}
		}
	}
	
	public static String getFileIdentifier(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
		return sdf.format(new Date());
	}
	
	public static String getTimestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM : HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getDatestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
		return sdf.format(new Date());
	}
	
}
