package org.divinity.impl;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import org.divinity.Updater;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

import java.lang.reflect.Modifier;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Character extends Analyzer {
    public Character(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int boolTypes = 0;

        if(node.superName.equals(Updater.findClassByName("Renderable").getClassNode().name)){
            if(Modifier.isAbstract(node.access)){
                for(FieldNode field: node.fields){
                    if(field.desc.equals("Z")){
                        boolTypes++;
                    }
                }
            }
        }
        return boolTypes == 4;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName() + " (extends " + Updater.findClassByNodeName(node.superName).getHookName() + ")"));

        for(FieldNode field: node.fields){
            if(!Modifier.isStatic(field.access)){
                if(field.desc.equals("Ljava/lang/String;")){
                    incrementFields();
                    System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Speech"));
                }
            }
        }

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        System.out.println();
        
        return new Hook(getHookName(), node);
    }
}
