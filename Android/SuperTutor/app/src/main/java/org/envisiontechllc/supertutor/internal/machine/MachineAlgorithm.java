package org.envisiontechllc.supertutor.internal.machine;

import android.util.Log;

import org.envisiontechllc.supertutor.internal.enums.XType;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class MachineAlgorithm {

    private static List<AlgorithmValue> values;

    public static List<Topic> organiseTopics(String learnerType, Subject subject){
        List<Topic> topics = new ArrayList<>();

        if(validForArranging(learnerType)){
            LinkedList<Topic> queue = new LinkedList<>();
            for(Topic topic: subject.getTopics()){
                processTopic(topics, queue, topic);
            }
            if(queue.size() > 0){
                processQueue(topics, queue);
            }

            return topics;
        }

        return subject.getTopics();
    }

    public static String determineLearnerType(int visual, int auditory, int kinaesthetic, int readWrite){

        values = new ArrayList<>();
        AlgorithmValue v = new AlgorithmValue("V", visual), a = new AlgorithmValue("A", auditory),
                k = new AlgorithmValue("K", kinaesthetic), r = new AlgorithmValue("R", readWrite);

        Collections.addAll(values, v, a, r, k);
        Collections.sort(values, valueComparator);

        AlgorithmValue chosenStyle = null;

        for(AlgorithmValue value: values){
            if(chosenStyle == null){
                chosenStyle = value;
                continue;
            }
            if(chosenStyle.getValue() < value.getValue()){
                chosenStyle = value;
            }
        }

        switch(chosenStyle.getOpCode()){
            case "V":
                return "Visual";
            case "R":
                return "Read/Write";
            case "K":
                return "Kinaesthetic";
            case "A":
                return "Auditory";
        }

        return "Not set";
    }


    private static void insertElementAtIndex(List<Topic> topics, int index, Topic element){
        try {
            Topic tTopic = topics.get(index);
            if(tTopic != null){
                topics.add(index, element);
                topics.add(tTopic);
            }
        }catch(Exception ex){}
    }

    private static boolean validForArranging(String learnerType){
        return (learnerType != null && (learnerType.equalsIgnoreCase(XType.VISUAL.getType()) || learnerType.equalsIgnoreCase(XType.KINAESTHETIC.getType())));
    }

    private static void processQueue(List<Topic> topics, List<Topic> source){
        for(Topic topic: source){
            topics.add(topic);
        }
        source.clear();
    }

    private static void processTopic(List<Topic> topics, List<Topic> queue, Topic topic){
        int index = 0;

        switch(topic.getTag()){
            case "Title":
                topics.add(0, topic);
                break;
            case "Introduction":
                topics.add(1, topic);
                break;
            case "Leading explanation":
                queue.add(topic);
                index = queue.indexOf(topic);
                break;
            case "Example":
                queue.add(topic);
                if(index != -1){
                    insertElementAtIndex(queue, index, topic);
                }
                break;
            case "Trailing explanation":
                queue.add(topic);
                if(queue.size() > 0){
                    index = -1;
                    processQueue(topics, queue);
                }
                break;
        }
    }

    private static Comparator<AlgorithmValue> valueComparator = new Comparator<AlgorithmValue>() {
        @Override
        public int compare(AlgorithmValue lhs, AlgorithmValue rhs) {
            return rhs.getValue() - lhs.getValue();
        }
    };
}
