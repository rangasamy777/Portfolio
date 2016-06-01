package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class Module {

    public Module(String moduleName, int result){
        this.moduleName = moduleName;
        this.result = result;
        this.results = new ArrayList<>();
        this.results.add(new Result(null, null, null, result));
    }

    private String moduleName;
    private int result;

    private List<Result> results;

    public void addResult(int result){
        this.result += result;
        this.results.add(new Result(null, null, null, result));
    }

    public int getResult(){
        return this.result;
    }

    public String getModuleName(){
        return this.moduleName;
    }

    public List<Result> getResults(){
        return this.results;
    }

}
