package org.envisiontechllc.supertutor.internal.enums;

/**
 * Created by EmileBronkhorst on 01/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public enum XStatus {

    GCSE_STUDENT("GCSE Student"),
    GCE_STUDENT("GCE Student"),
    UNDERGRADUATE("Undergraduate"),
    POST_GRADUATE("Postgraduate"),
    UNEMPLOYED("Unemployed"),
    EMPLOYED("Employed")
    ;

    private String status;

    XStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
