package data;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class User {

    public User(String userID, String username, int accessLevel){
        this.userID = userID;
        this.username = username;
        this.accessLevel = accessLevel;
    }

    public static User instance;

    private String userID;
    private String username;
    private int accessLevel;

    public String getUserID(){
        return this.userID;
    }

    public String getUsername(){
        return this.username;
    }

    public int getAccessLevel(){
        return this.accessLevel;
    }

    public boolean isAdmin(){
        return accessLevel == 3;
    }

    public boolean isTutor(){
        return accessLevel == 2;
    }

    public static User getInstance(){
        return instance;
    }

    public static void setInstance(String userID, String username, int accessLevel){
        if(instance == null){
            instance = new User(userID, username, accessLevel);
        }
    }
}
