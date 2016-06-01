package org.envisiontechllc.supertutor.internal.enums;

import org.envisiontechllc.supertutor.internal.testing.test_wrappers.TAnswer;
import org.envisiontechllc.supertutor.internal.testing.test_wrappers.TQuestion;

/**
 * Created by EmileBronkhorst on 20/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public enum XQuestions {

    QUESTION_1(new TQuestion("You are watching a tutorial on how to draw a graph, you would remember this mostly by...",
            new TAnswer("Seeing the graph being drawn", "V"), new TAnswer("Listening to the video", "A"),
            new TAnswer("Drawing the graph with the tutorial", "K"), new TAnswer("Reading the words displayed on the video", "R"))),
    QUESTION_2(new TQuestion("You are preparing a dinner for a family member, do you...", new TAnswer("Cook something from you know how to cook well", ""),
            new TAnswer("Look in a recipe book for a good recipe", "R"), new TAnswer("Watch a video/picture on how to cook the items", "V"), new TAnswer("Call a friend and ask for suggestions", "A"))),
    QUESTION_3(new TQuestion("You are about to purchase a new computer, what would most influence your decision other than price?", new TAnswer("A salesperson talking you through all the features", "A"),
            new TAnswer("The design of the computer", "V"), new TAnswer("Reading its features/specifications on the internet", "R"), new TAnswer("Exploring the computer for yourself and testing its features", "K"))),
    QUESTION_4(new TQuestion("When in your lecture, you learn best when the lecturer...", new TAnswer("Uses diagrams and charts etc.", "V"), new TAnswer("Talks about the topic ", "A"),
            new TAnswer("Demonstrates the topic being taught", "K"), new TAnswer("Allows you time to read the lecture slides", "R"))),
    QUESTION_5(new TQuestion("A friend is coming to your house, do you...", new TAnswer("Call them and talk them through the directions", "A"), new TAnswer("Show them images of the streets they should be walking down", "V"),
            new TAnswer("Meet them at a location and take them to your house", "K"), new TAnswer("Text them directions to your home", "R"))),
    QUESTION_6(new TQuestion("You are at the doctors for a checkup and he has diagnosed you, would you prefer him to...", new TAnswer("Tell you about your diagnosis", "A"), new TAnswer("Show you diagrams about how the illness is affecting you", "V"),
            new TAnswer("Give you a leaflet/information sheet on the diagnosis", "R"), new TAnswer("Use a skeleton model to show you where the issue lies", "K"))),
    QUESTION_7(new TQuestion("You are planning a holiday to a different country, do you...", new TAnswer("Choose the country based on recommendations from friends", "A"), new TAnswer("Look up images/videos of destinations", "V"),
            new TAnswer("Read information leaflets on popular locations", "R"), new TAnswer("Stick to places you know and have already been", "K"))),
    QUESTION_8(new TQuestion("Remember the last topic you studied for, did you...", new TAnswer("Watch a demonstration on the topic", "K"), new TAnswer("Look at some images/graphs based on the topic", "V"),
            new TAnswer("Spoke the words out loud till it stuck in your memory", "A"), new TAnswer("Wrote out the text repeatedly till it stuck in your memory", "R"))),
    QUESTION_9(new TQuestion("You are about to learn a new programming language or game on the computer, do you...", new TAnswer("Ask friends and family how to play it", "A"), new TAnswer("Look at images/videos on how to use it", "V"),
            new TAnswer("Read instructions on the internet/manual", "R"), new TAnswer("Play around with it until you get the hang of it", "K"))),
    QUESTION_10(new TQuestion("You have just completed a quiz, you would prefer your results to be...", new TAnswer("Shown in a graph/diagram", "V"), new TAnswer("Someone talk you through your results", "A"),
            new TAnswer("Revisit the quiz and see where you went wrong alone", "K"), new TAnswer("See a written description of your results", "R")))
    ;

    private TQuestion question;

    XQuestions(TQuestion question){
        this.question = question;
    }

    public TQuestion getQuestion(){
        return this.question;
    }
}
