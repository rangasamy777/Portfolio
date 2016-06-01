package org.envisiontechllc.supertutor.settings;

import org.envisiontechllc.supertutor.internal.library.AppLibrary;
import org.envisiontechllc.supertutor.internal.wrappers.User;

/**
 * Created by EmileBronkhorst on 15/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class AppContext {

    private static AppContext instance;
    private User currentUser;
    private AppLibrary library;

    private AppContext(){
        this.library = AppLibrary.getInstance();
    }

    /**
     * Sets the current user of the application to the instance parsed.
     * @param user The user to be logged into the application.
     */
    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    /**
     * Returns the user currently logged into the application (if applicable).
     * @return User
     */
    public User getCurrentUser(){
        return this.currentUser;
    }

    /**
     * Returns the Application library containing subject information
     * @return the Application library
     */
    public AppLibrary getLibrary(){
        return this.library;
    }

    /**
     * Creates a new instance of the AppContext (if not already existing)
     * This instance is only ever instantiated once
     * @return instance of AppContext
     */
    public static AppContext getContext(){
        if(instance == null){
            instance = new AppContext();
        }
        return instance;
    }
}
