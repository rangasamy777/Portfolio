package org.divinity.generic;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.divinity.Updater;
import org.divinity.model.Hook;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public abstract class Analyzer implements Opcodes {

    public Analyzer(String hookName){
        this.hookName = hookName;
    }

    private String hookName;
    private int fieldCount;

    public void run(ClassNode... nodes){
        for(ClassNode node: nodes){
            if(this.canRun(node)){
                Updater.putClass(hookName, this.analyze(node));
            }
        }
    }

    public void incrementFields(){
        this.fieldCount += 1;
    }

    protected abstract boolean canRun(ClassNode node);
    protected abstract Hook analyze(ClassNode node);

    public String getHookName(){
        return this.hookName;
    }

    public int getFieldCount(){
        return this.fieldCount;
    }
}
