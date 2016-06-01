package org.divinity.impl;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import org.divinity.Updater;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

import java.lang.reflect.Modifier;

/**
 * Created by Emile on 26/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Client extends Analyzer {
    public Client(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        if(Modifier.isFinal(node.access)){
            if(node.superName.equals(Updater.findClassByName("Engine").getClassNode().name)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName()) + " (extends " + Updater.findClassByNodeName(node.superName).getHookName() + ")");

        for(FieldNode field: node.fields){
            if(field.desc.equals(String.format("[L%s;", Updater.findClassByName("NPC").getClassNode().name))){
                incrementFields();
                System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".NPC[]"));
            } else if(field.desc.equals(String.format("[L%s;", Updater.findClassByName("Player").getClassNode().name))){
                incrementFields();
                System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Player[]"));
            }
        }

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets found");

        return new Hook(getHookName(), node);
    }
}
