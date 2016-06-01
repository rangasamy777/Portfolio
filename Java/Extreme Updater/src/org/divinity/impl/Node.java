package org.divinity.impl;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;
import org.divinity.generic.Analyzer;
import org.divinity.model.Hook;

import java.lang.reflect.Modifier;
import java.util.ListIterator;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Node extends Analyzer {
    public Node(String hookName) {
        super(hookName);
    }

    @Override
    protected boolean canRun(ClassNode node) {
        int ownType = 0, longType = 0;

        for(FieldNode field: node.fields){
            if((field.access & Opcodes.ACC_STATIC) == 0){
                if(field.desc.equals(String.format("L%s;", node.name))){
                    ownType++;
                } else if(field.desc.equals("J")) {
                    longType++;
                }
            }
        }

        return ownType == 2 && longType == 1;
    }

    @Override
    protected Hook analyze(ClassNode node) {
        System.out.println();
        System.out.println(String.format("%s", "Class " + node.name + " identified as " + getHookName()));

        String previousName = "";
        FieldInsnNode previousField = null;

        for(MethodNode method: node.methods){
            if(!Modifier.isStatic(method.access)){
                if(method.desc.equals("()Z")){
                    ListIterator<AbstractInsnNode> ainIt = method.instructions.iterator();
                    while(ainIt.hasNext()){
                        AbstractInsnNode ain = ainIt.next();
                        if(ain instanceof FieldInsnNode){
                            previousField = ((FieldInsnNode)ain);
                            previousName = ((FieldInsnNode)ain).name;
                            break;
                        }
                    }
                }
            }
        }

        for(FieldNode field: node.fields){
            if(!Modifier.isStatic(field.access)){
                if(field.desc.equals("J")){
                    incrementFields();
                    System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".UID"));
                } else if(field.desc.equals(String.format("L%s;", node.name)) && !field.name.equals(previousName)){
                    incrementFields();
                    System.out.println(String.format("          Field:  " + node.name + "." + field.name + " | identified as " + getHookName() + ".Next"));
                }
            }
        }

        incrementFields();
        System.out.println(String.format("          Field:  " + node.name + "." + previousField.name + " | identified as " + getHookName() + ".Previous"));

        System.out.println("Found: " + getFieldCount() + "/" + node.fields.size() + " targets");
        System.out.println();

        return new Hook(getHookName(), node);
    }
}
