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
public class Message extends Analyzer {
    public Message(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int strCount = 0, intCount = 0;

        if(node.superName.equals(Updater.findClassByName("DoubleNode").getClassNode().name)){
            for(FieldNode field: node.fields){
                if(!Modifier.isStatic(field.access)){
                    if(field.desc.equals(String.format("L%s;", "java/lang/String"))){
                        strCount++;
                    } else if(field.desc.equals(String.format("[%s;", "I"))){
                        intCount++;
                    }
                }
            }
        }
        return strCount == 3 && intCount == 3;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName()));


        System.out.println();

        return new Hook(getHookName(), node);
    }
}
