package org.envisiontechllc.supertutor.subactivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.SuperTutor;
import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.library.AppLibrary;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.managers.GCMRegistration;
import org.envisiontechllc.supertutor.network.managers.LoginManager;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by EmileBronkhorst on 15/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class SplashScreen extends Activity {

    private AppContext appData;
    private SQLManager dbManager;

    private CountDownTimer timer;
    private boolean firstUse = true;

    /**
     * Default constructor for the Super activity
     * @param savedInstance instance of the Bundle to be used during initialisation (handled by Super class)
     */
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.splash_screen);

        checkFileSystem();
        checkPreferences();
    }

    /**
     * Retrieves the value stored in SharedPreferences to see if this is the users first use.
     * Displays app tour if returns true, if false display the LoginScreen
     */
    private void checkPreferences(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);

        firstUse = preferences.getBoolean("firstUse", firstUse);

        if(firstUse){
            Intent tourIntent = new Intent(this, AppTour.class);
            startActivity(tourIntent);
            finish();
            return;
        }
        checkDatabase();
    }

    /**
     * Presents the Login activity to the user after failing the credentials storage check
     */
    private void initApplicationContents(){
        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
        finish();
    }


    /**
     * Initialises the application settings, and the local Database manager.
     * If there is credentials saved in the local database, it will login with these credentials.
     */
    private void checkDatabase(){
        appData = AppContext.getContext();
        dbManager = new SQLManager(this);
        new GCMRegistration(this).execute();

        timer = new CountDownTimer(1100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                List<Subject> list = dbManager.getDownloadedSubjects();
                if(list != null && list.size() > 0){
                    AppLibrary library = AppLibrary.getInstance();
                    library.addSubjectsToMyLibrary(list.toArray(new Subject[list.size()]));
                }
                HashMap<String, String> userCredentials = dbManager.getUserCredentials();
                if(userCredentials != null){
                    User tUser = new User(userCredentials.get("Username"), userCredentials.get("Password"),
                            userCredentials.get("Email"), userCredentials.get("LearnerType"));
                    tUser.setStatus(userCredentials.get("Status"));
                    tUser.setImageBytes(userCredentials.get("Image"));

                    AppContext.getContext().setCurrentUser(tUser);
                    Intent dashBoardIntent = new Intent(SplashScreen.this, SuperTutor.class);
                    startActivity(dashBoardIntent);
                    finish();
                } else {
                    if(Utilities.checkConnectivity(SplashScreen.this)){
                        initApplicationContents();
                        return;
                    } else {
                        AlertDialog dialog = Utilities.createDialog(SplashScreen.this, "Error", "Unable to connect to server, check your internet connection and try again.", null);
                        dialog.show();
                    }
                }
            }
        }.start();
    }

    /**
     * Creates a new folder within the system where all data will be stored (i.e. images, documents etc.)
     */
    private void checkFileSystem(){
        File file = Utilities.getFileSystem();
        if(!file.exists()){
            file.mkdir();
        }
    }
}
