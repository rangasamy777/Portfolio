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
public class Tile extends Analyzer {

    public Tile(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int instanceCount = 0;

        if(Modifier.isFinal(node.access)){
            if(node.superName.equals(Updater.findClassByName("Node").getClassNode().name)){
                for(FieldNode field: node.fields){
                    if(!Modifier.isStatic(field.access)){
                        if(field.desc.equals(String.format("L%s;", node.name))){
                            instanceCount++;
                        }
                    }
                }
            }
        }
        return instanceCount == 1;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName()) + " (extends " + Updater.findClassByName("Node").getHookName() + ")");

        for(FieldNode field: node.fields){
            if(!Modifier.isStatic(field.access)){
                if(field.desc.equals(String.format("L%s;", node.name))){
                    incrementFields();
                    System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Tile"));
                }
            }
        }

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        System.out.println();

        return new Hook(getHookName(), node);
    }
}
