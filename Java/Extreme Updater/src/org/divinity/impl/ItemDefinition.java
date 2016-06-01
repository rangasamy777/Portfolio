package org.divinity.impl;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import org.divinity.Updater;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

import java.lang.reflect.Modifier;

/**
 * Created by Emile on 28/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class ItemDefinition extends Analyzer {
    public ItemDefinition(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int stringArr = 0, shortArr = 0;

        if(node.superName.equals(Updater.findClassByName("DoubleNode").getClassNode().name)){
            for(FieldNode field: node.fields){
                if(!Modifier.isStatic(field.access)){
                    if(field.desc.equals("[Ljava/lang/String;")){
                        stringArr++;
                    } else if (field.desc.equals("[S")) {
                        shortArr++;
                    }
                }
            }
        }

        return stringArr == 2 && shortArr == 4;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName() + " (extends " + Updater.findClassByName("DoubleNode").getHookName() + ")"));

        for(FieldNode field: node.fields){
            if(!Modifier.isStatic(field.access)){

                /* Need to clean this up returns 2 variables, #93 ao.aa is the correct one */
                if(field.desc.equals(String.format("[L%s", "java/lang/String;"))){
                    incrementFields();
                    System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".ItemActions[]"));
                }
            }
        }

        System.out.println();
        return new Hook(getHookName(), node);
    }
}
