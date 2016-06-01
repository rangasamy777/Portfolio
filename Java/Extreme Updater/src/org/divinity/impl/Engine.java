package org.divinity.impl;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

import java.lang.reflect.Modifier;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Engine extends Analyzer {
    public Engine(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int boolCount = 0;
        if(node.superName.equals("java/applet/Applet")){
            if(Modifier.isAbstract(node.access)){
                for(FieldNode field: node.fields){
                    if(!Modifier.isStatic(field.access)){
                        if(field.desc.equals("Z")){
                            boolCount++;
                        }
                    }
                }
            }
        }
        return boolCount == 1;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName() + " (extends " + node.superName + ")"));
        System.out.println();
        return new Hook(getHookName(), node);
    }
}
