package org.divinity.impl;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import org.divinity.Updater;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class DoubleNode extends Analyzer {

    public DoubleNode(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int ownType = 0;

        for(FieldNode field: node.fields){
            if(node.superName.equals(Updater.findClassByName("Node").getClassNode().name)){
                if((field.access & Opcodes.ACC_STATIC) == 0){
                    if(field.desc.equals(String.format("L%s;", node.name))){
                        ownType++;
                    }
                }
            }
        }
        return ownType == 2;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName() + " (extends " + Updater.findClassByNodeName(node.superName).getHookName() + ")"));
        return new Hook(getHookName(), node);
    }
}
