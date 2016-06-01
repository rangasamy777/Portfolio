package org.supertutor.library;

import org.supertutor.internal.wrappers.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile on 11/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class AppLibrary {

    private static AppLibrary instance;

    private AppLibrary(){
        this.subjects = new ArrayList<>();
    }

    private List<Subject> subjects;

    public void addSubject(Subject subject){
        this.subjects.add(subject);
    }

    public void updateSubject(Subject subject){
        if(!subjects.contains(subject)){
            subjects.add(subject);
            return;
        }
        int index = subjects.indexOf(subject);
        if(index != -1){
            subjects.remove(index);
            subjects.add(index, subject);
        }
    }

    public void resetLibrary(){
        subjects.clear();
    }

    public void removeSubjectIndex(int index){
        this.subjects.remove(index);
    }

    public List<Subject> getLoadedSubjects(){
        return this.subjects;
    }

    public static AppLibrary getInstance(){
        if(instance == null){
            instance = new AppLibrary();
        }
        return instance;
    }
}
