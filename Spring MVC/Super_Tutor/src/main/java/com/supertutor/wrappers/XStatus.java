package com.supertutor.wrappers;

public enum XStatus {

	CONTENT_MANAGER("Content Manager"),
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
