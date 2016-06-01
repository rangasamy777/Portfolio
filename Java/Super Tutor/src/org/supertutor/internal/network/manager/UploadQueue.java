package org.supertutor.internal.network.manager;

import org.supertutor.internal.network.tasks.UploadTask;
import org.supertutor.internal.wrappers.Subject;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Emile on 16/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public class UploadQueue {

    private Queue<Subject> subjects;

    public UploadQueue(){
        this.subjects = new PriorityQueue<>();
    }

    public void addToQueue(Subject subject){
        if(!subjects.contains(subject)){
            subjects.add(subject);
            performUpload(subject);
        }
    }

    private void performUpload(Subject subject){
        UploadTask task = new UploadTask(subjects, subject);
        new Thread(task).start();
    }
}
