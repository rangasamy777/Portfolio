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
public class NPCDefinition extends Analyzer {
    public NPCDefinition(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int shortArray = 0, intArray = 0;

        if(node.superName.equals(Updater.findClassByName("DoubleNode").getClassNode().name)){
            for(FieldNode field: node.fields){
                if(!Modifier.isStatic(field.access)){
                    if(field.desc.equals("[S")){
                        shortArray++;
                    } else if(field.desc.equals("[I")){
                        intArray++;
                    }
                }
            }
        }

        return (shortArray == 4 && intArray == 3);
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName()));

        for(FieldNode field: node.fields){
            if(field.desc.equals("Ljava/lang/String;")){
                incrementFields();
                System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Name"));
            } else if(field.desc.equals("[Ljava/lang/String;")){
                incrementFields();
                System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Actions[]"));
            }
        }

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        System.out.println();

        return new Hook(getHookName(), node);
    }
}
