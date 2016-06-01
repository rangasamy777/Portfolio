package data;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class Result {

    public Result(String userID, String moduleCode, String assessmentName, int result){
        this.userID = userID;
        this.moduleCode = moduleCode;
        this.assessmentName = assessmentName;
        this.result = result;
    }

    private String userID;
    private String moduleCode;
    private String assessmentName;
    private int result;

    public String getUserID(){
        return this.userID;
    }

    public String getModuleCode(){
        return this.moduleCode;
    }

    public String getAssessmentName(){
        return this.assessmentName;
    }

    public int getResult(){
        return this.result;
    }
}
