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
public class Model extends Analyzer {
    public Model(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int intArrayCount = 0;

        if (node.superName.equals(Updater.findClassByName("Renderable").getClassNode().name)) {
            for(FieldNode field: node.fields){
                if(!Modifier.isStatic(field.access)){
                    if(field.desc.equals("[I")){
                        intArrayCount++;
                    }
                }
            }
        }
        return intArrayCount > 10;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName() + " (extends " + Updater.findClassByNodeName(node.superName).getHookName() + ")"));
        return new Hook(getHookName(), node);
    }
}
