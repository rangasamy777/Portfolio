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
public class GroundItemPile extends Analyzer {
    public GroundItemPile(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int RenderableCount = 0;
        if(Modifier.isFinal(node.access)){
            for(FieldNode field: node.fields){
                if(!Modifier.isStatic(field.access)){
                    if(field.desc.equals(String.format("L%s;", Updater.findClassByName("Renderable").getClassNode().name))){
                        RenderableCount++;
                    }
                }
            }
        }
        return RenderableCount == 3;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName()) + " (extends " + Updater.findClassByName("Renderable").getHookName() + ")");

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        System.out.println();
        return new Hook(getHookName(), node);
    }
}
