package org.envisiontechllc.supertutor.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by EmileBronkhorst on 18/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class SQLManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "SuperTutor.db";
    public static final String COL_ID = "ID";

    public static final String USER_TABLE = "User";
    public static final String COL_USERNAME = "Username";
    public static final String COL_PASSWORD = "Password";
    public static final String COL_LEARNER_TYPE = "LearnerType";
    public static final String COL_EMAIL = "Email";
    public static final String COL_STATUS = "Status";
    public static final String COL_IMAGE = "Image";

    public static final String SUBJECT_TABLE = "Subjects";
    public static final String SUBJECT_TITLE = "Subject_Title";
    public static final String SUBJECT_DESC = "Subject_Desc";
    public static final String SUBJECT_TOPIC = "Subject_Topics";
    public static final String SUBJECT_BOOKMARk = "Subject_Bookmark";

    public static final String TOPIC_TABLE = "Topics";
    public static final String TOPIC_TITLE = "Topic_Title";
    public static final String TOPIC_DESC = "Topic_Desc";
    public static final String TOPIC_FILE = "Topic_File";


    /**
     * Creates a new local database manager.
     * @param context the application context giving access to method provider.
     */
    public SQLManager(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * Creates a new SQLiteDatabase when the class is instantiated
     * @param db the super class' SQLiteDatabase instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_USERNAME + " TEXT, "
                + COL_PASSWORD + " TEXT, " + COL_EMAIL + " TEXT, " + COL_LEARNER_TYPE + " TEXT, " + COL_STATUS + " TEXT, " + COL_IMAGE + " TEXT)");

        db.execSQL("CREATE TABLE " + SUBJECT_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT_TITLE + " TEXT, "
        + SUBJECT_DESC + " TEXT, " + SUBJECT_TOPIC + " TEXT, " + SUBJECT_BOOKMARk + " INTEGER)");

        db.execSQL("CREATE TABLE " + TOPIC_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TOPIC_TITLE + " TEXT, "
                + TOPIC_DESC + " TEXT, " + TOPIC_FILE + " TEXT)");
    }

    /**
     * Upgrades the database at every lifecycle point (e.g. onResume, onPause etc.).
     * @param db the super class' SQLiteDatabase instance
     * @param oldVersion the version number of the oldDatabase
     * @param newVersion the version number of the newly updatedDatabase
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Reset the current database (when users logs out/wishes to forget stored data)
     */
    public void resetDatabase(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TOPIC_TABLE);
        onCreate(db);

        db.close();
    }

    /**
     * Sets the image for the current user
     * @param username
     * @param imageData
     */
    public void setUserImage(String username, String imageData){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(COL_IMAGE, imageData);

            long id = db.update(USER_TABLE, values, COL_USERNAME + " = ?", new String[]{username});
            db.close();
        }
    }

    /**
     * Updates the password for the user data stored offline
     * @param username the username of the password to change
     * @param password the new password for the user
     */
    public void updateUserPassword(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(COL_PASSWORD, password);

            long id = db.update(USER_TABLE, values, COL_USERNAME + " = ?", new String[]{username});
            if(id != -1){

            }
        }
    }

    /**
     * Stores the values of the current users credentials in the local database for offline use.
     * @param username the Username of the user
     * @param password the Password of the user
     */
    public void setUser(String username, String password, String email, String learnerType, String status, String imageBytes){

        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();

            values.put(COL_USERNAME, username);
            values.put(COL_PASSWORD, password);
            values.put(COL_LEARNER_TYPE, learnerType);
            values.put(COL_EMAIL, email);
            values.put(COL_STATUS, status);
            values.put(COL_IMAGE, imageBytes);

            db.insert(USER_TABLE, null, values);
        }

        db.close();
    }

    /**
     * Returns a Hashmap containing the username and password of the stored information (if selected at login previously).
     * @return HashMap<String, String> containing the Username and Password of the user.
     */
    public HashMap<String, String> getUserCredentials(){

        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            String query = "SELECT * FROM " + USER_TABLE;
            Cursor pointer = db.rawQuery(query, null);

            if(pointer != null && pointer.moveToFirst()){
                HashMap<String, String> values = new HashMap<>();
                values.put("Username", pointer.getString(1));
                values.put("Password", pointer.getString(2));
                values.put("Email", pointer.getString(3));
                values.put("LearnerType", pointer.getString(4));
                values.put("Status", pointer.getString(5));
                values.put("Image", pointer.getString(6));

                pointer.close();
                db.close();
                return values;
            }
        }
        db.close();

        return null;
    }

    /**
     * Adds the subject to the offline mode tables
     * @param subject The subject to store offline
     * @return true if the subject and its data was successfully stored
     */
    public boolean addSubjectToTable(Subject subject){
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder topicID = new StringBuilder();

        if(db != null){
            ContentValues topicValues = new ContentValues(), subjectValues = new ContentValues();
            for(Topic topic: subject.getTopics()){
                topicValues.put(TOPIC_TITLE, topic.getTopicName());
                topicValues.put(TOPIC_DESC, topic.getTag());
                topicValues.put(TOPIC_FILE, topic.getFileName());

                long id = db.insert(TOPIC_TABLE, null, topicValues);
                topicID.append(id + ",");
            }
            subjectValues.put(SUBJECT_TITLE, subject.getSubjectName());
            subjectValues.put(SUBJECT_DESC, subject.getDescription());
            subjectValues.put(SUBJECT_BOOKMARk, subject.getBookmark());
            subjectValues.put(SUBJECT_TOPIC, topicID.toString());

            long subjectID = db.insert(SUBJECT_TABLE, null, subjectValues);
            if(subjectID != -1){
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }

    /**
     * Updates the bookmark for the subject so that the user may resume from that position
     * @param subject the Subject for which bookmark to update
     * @return true if the subject was updated
     */
    public boolean updateBookmarkForSubject(Subject subject){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(SUBJECT_BOOKMARk, subject.getBookmark());

            int rowID = db.update(SUBJECT_TABLE, values, SUBJECT_TITLE + " = ?", new String[]{subject.getSubjectName()});
            if(rowID != -1){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the subject is already downloaded or not
     * @param subject The subject to check against
     * @return true if the subject data is found in the SQL Tables
     */
    public boolean isDownloaded(Subject subject){
        String query = "SELECT * FROM " + SUBJECT_TABLE + " WHERE " + SUBJECT_TITLE + " ='" + subject.getSubjectName() + "'";
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            Cursor pointer = db.rawQuery(query, null);
            if(pointer != null && pointer.moveToFirst()){
                pointer.close();
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }

    /**
     * Gets the list of subjects stored on the users phone, this is all subjects that were requested to be made available offline
     * @return List containing a list of subjects
     */
    public List<Subject> getDownloadedSubjects(){
        SQLiteDatabase db = getWritableDatabase();

        List<Subject> list = new ArrayList<>();
        String subjectQuery = "SELECT * FROM " + SUBJECT_TABLE;

        if(db != null){
            Cursor subjectPointer = db.rawQuery(subjectQuery, null);
            if(subjectPointer != null && subjectPointer.moveToFirst()){
                do {
                    String title = subjectPointer.getString(1);
                    String description = subjectPointer.getString(2);
                    String topicIDS = subjectPointer.getString(3);
                    int bookmark = subjectPointer.getInt(4);

                    Log.d("BOOKMARK", "" + bookmark);

                    Subject subject = new Subject(title, description, -1);
                    subject.setBookmark(bookmark);

                    if(topicIDS != null){
                        String[] topics = topicIDS.split(",");
                        for(String topicID: topics){
                            try {
                                int tID = Integer.parseInt(topicID);
                                if(tID != -1){
                                    String topicQuery = "SELECT * FROM " + TOPIC_TABLE + " WHERE " + COL_ID + " ='" + tID + "'";
                                    Cursor topicPointer = db.rawQuery(topicQuery, null);

                                    if(topicPointer != null && topicPointer.moveToFirst()){
                                        do {
                                            String topicTitle = topicPointer.getString(1);
                                            String topicDesc = topicPointer.getString(2);
                                            String fileName = topicPointer.getString(3);

                                            subject.addTopic(new Topic(topicTitle, topicDesc, fileName));
                                        }while(topicPointer.moveToNext());
                                    }
                                }
                            }catch(NumberFormatException ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                    list.add(subject);
                }while(subjectPointer.moveToNext());
            }
            subjectPointer.close();
            db.close();
        }

        return list;
    }

    /**
     * Gets the Writable SQLiteDatabase
     * @return the Writable SQLiteDatabase
     */
    public SQLiteDatabase getSQLDatabase(){
        return this.getWritableDatabase();
    }
}
