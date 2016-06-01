package utils;

import data.Module;
import data.Result;

import java.util.List;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class PerformanceTracker {

    private static int average;

    public static int getAverage(List<Result> resultList){
        for(Result result: resultList){
            average += result.getResult();
        }
        return average / (resultList.size() != 0? resultList.size() : 1) != 0 ? average / resultList.size() : 0;
    }

    public static int filterModuleResults(Module module){
        int moduleSize = module.getResults().size() != 0 ? module.getResults().size() : 1;

        return (average = module.getResult()) / moduleSize != 0 ? (average = module.getResult()) / moduleSize : 0;
    }

}
