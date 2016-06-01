package org.divinity.impl;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.divinity.Updater;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

import java.lang.reflect.Modifier;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Renderable extends Analyzer {
    public Renderable(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int intCount = 0, methodCount = 0;

        if(node.superName.equals(Updater.findClassByName("DoubleNode").getClassNode().name)){
            if(Modifier.isAbstract(node.access)){
                for(FieldNode field: node.fields){
                    if(!Modifier.isStatic(field.access)){
                        if(field.desc.equals("I")){
                            intCount++;
                        }
                    }
                }
                for(MethodNode method: node.methods){
                    if(!Modifier.isStatic(method.access)){
                        if(method.desc.equals("(IIIIIIIII)V")){
                            methodCount++;
                        }
                    }
                }
            }
        }
        return intCount == 1 && methodCount >= 1;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName() + " (extends " + Updater.findClassByNodeName(node.superName).getHookName() + ")"));

        for(FieldNode field: node.fields){
            if(!Modifier.isStatic(field.access)){
                if(field.desc.equals("I")){
                    incrementFields();
                    System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".ModelHeight"));
                }
            }
        }

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        return new Hook(getHookName(), node);
    }
}
