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
public class Player extends Analyzer {
    public Player(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        if (node.superName.equals(Updater.findClassByName("Character").getClassNode().name)) {
            if(Modifier.isFinal(node.access)){
                for(FieldNode field: node.fields){
                    if(!Modifier.isStatic(field.access)){
                        if(field.desc.equals(String.format("L%s;", Updater.findClassByName("Model").getClassNode().name))){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println("");
        System.out.println(String.format("%s", "Class " + node.name + " identified as Player (extends " + Updater.findClassByNodeName(node.superName).getHookName() + ")"));

        for(FieldNode field: node.fields){
            if(field.desc.equals("Ljava/lang/String;")){
                incrementFields();
                System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Name"));
            } else if(field.desc.equals(String.format("L%s;", Updater.findClassByName("Model").getClassNode().name))){
                incrementFields();
                System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Model"));
            }
        }

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        System.out.println();

        return new Hook(getHookName(), node);
    }
}
