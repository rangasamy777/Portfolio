package org.divinity.model;

import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Hook {

    public Hook(String hookName, ClassNode node){
        this.hookName = hookName;
        this.node = node;
        this.multiplier = 0;
    }

    public Hook(String className, String fieldName, String fieldValue, String desc, int multiplier) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.desc = desc;
        this.multiplier = multiplier;
    }

    private String hookName, className, fieldName, fieldValue, desc;
    private ClassNode node;
    private int multiplier;
    private HashMap<String, Hook> fields = new HashMap<>();

    public String getHookName(){
        return this.hookName;
    }

    public String getClassName(){
        return this.className;
    }

    public String getFieldName(){
        return this.fieldName;
    }

    public String getFieldValue(){
        return this.fieldValue;
    }

    public String getDesc(){
        return this.desc;
    }

    public int getMultiplier(){
        return this.multiplier;
    }

    public ClassNode getClassNode(){
        return this.node;
    }

    public HashMap<String, Hook> getFieldMap(){
        return this.fields;
    }
}
