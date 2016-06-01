package org.supertutor.internal.enums;

/**
 * Created by Emile on 16/04/2016.
 * Copyright 2015 Envision Tech LLC
 */
public enum Strings {

    FONT_HELP("Changing Font", "Windows:", "->Control Panel \n--> Type window color in the search window, click Change window colors and metrics \n--> In the item list click 'Font' and change the size accordingly."),
    SUBJECT_TOPIC_HELP("Topic/Subject Upload", "Follow the instructions below:", "Click the 'Add Topic' button and enter the details of the subject to create (if subject is not already selected from table)\n\n" +
            "Topic file: This is the (PDF) file associated to the topic \n-->Click the text field associated with the file name and select the file to upload (10MB File size limit)" +
            "\n\nTopic name: This is the name of the topic which you wish to upload \n\n" +
            "Topic description: This is the description which you give to the topic. \n\n" +
            "Once you have uploaded all appropriate files/topics click 'Build Subject'"),
    ABOUT_HELP("About Super Tutor", "", "Super Tutor 2016 © Envision Tech LLC \n\nCreated to help students engage in personal learning. For more information visit the website.\n\n"),
    ANALYTICS_HELP("About Analytics", "", "The analytics section will give you a good indication of the types of users that are registered to the service. " +
            "This can be used for demographic information, weekly/monthly usages and other vital information which may aid in the creation of unique content."),
    ACCOUNT_UPDATE_HELP("Account Information Updating", "", "Your account information can be updated at any time via the 'Account Settings' tab.\n\n" +
            "->Your Email address and Password can be updated by changing the values in the field\n" +
            "->Information is encrypted for user safety\n\n" +
            "Once you have filled out all the appropriate changes, click 'Update information'"),
    ;

    private String title, content, helpText;

    Strings(String title, String helpText, String content){
        this.title = title;
        this.content = content;
        this.helpText = helpText;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public String getHelpText(){
        return this.helpText;
    }
}
