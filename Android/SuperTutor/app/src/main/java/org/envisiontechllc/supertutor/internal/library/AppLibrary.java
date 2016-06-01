package org.envisiontechllc.supertutor.internal.library;

import android.content.Context;
import android.widget.ListView;

import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.Topic;
import org.envisiontechllc.supertutor.network.tasks.SubjectDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 18/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class AppLibrary {

    private static AppLibrary instance;
    private static Context ctx;
    private List<Subject> subjects;
    private List<Subject> myLibrary;

    private SubjectDownloader downloader;

    private AppLibrary(){
        this.subjects = new ArrayList<>();
        this.myLibrary = new ArrayList<>();
    }

    public Subject getSubjectForName(String name){
        for(Subject subject: myLibrary){
            if(subject.getSubjectName().equalsIgnoreCase(name)){
                return subject;
            }
        }
        return null;
    }

    public void addSubjectToMyLibrary(Subject subject){
        if(!myLibrary.contains(subject)){
            this.myLibrary.add(subject);
        }
    }

    public void addSubjectsToMyLibrary(Subject... subjects){
        for(Subject subject: subjects){
            if(!myLibrary.contains(subject)){
                this.myLibrary.add(subject);
            }
        }
    }

    public void downloadSubjects(Context ctx, ListView list){
        subjects.clear();

        downloader = new SubjectDownloader(ctx, subjects, list);
        downloader.execute();
    }

    public int getTopicIndex(Topic topic){
        for(Subject subject: myLibrary){
            if(subject.getTopics().contains(topic)){
                return subject.getTopics().indexOf(topic);
            }
        }

        return -1;
    }

    public List<Subject> getLibrarySubjects(){
        return this.subjects;
    }

    public List<Subject> getMyLibrary(){
        return this.myLibrary;
    }

    public void resetLibrary(){
        this.myLibrary = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public static AppLibrary getInstance(){
        if(instance == null){
            instance = new AppLibrary();
        }
        return instance;
    }
}
