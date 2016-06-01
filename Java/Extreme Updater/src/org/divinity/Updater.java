package org.divinity;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.divinity.file.FileUtils;
import org.divinity.generic.Analyzer;
import org.divinity.impl.*;
import org.divinity.impl.Character;
import org.divinity.model.Hook;

import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Emile on 23/09/15.
 * Copyright 2015 Envision Tech LLC
 */
public class Updater {

    public Updater(){
        try {
            initUpdater();
        } catch(IOException ex){
            System.out.println("Exception: " + ex.toString());
        }
    }

    private static HashMap<String, ClassNode> classes = new HashMap<>();
    private static HashMap<String, Hook> classMap = new HashMap<>();
    private List<Analyzer> analyzers = new ArrayList<>();

    private void initUpdater() throws IOException {
        System.out.println("====================== Running Extreme Updater ======================");

        FileUtils.checkDirs();

        JarFile jarFile = new JarFile(FileUtils.getJarFile());
        this.classes = parseJar(jarFile);

        this.loadAnalyzers();

        System.out.println();
        System.out.println("    *************** Refactored: " + classMap.size() + "/" + classes.size() + " classes ***************");
        System.out.println("====================== Client Revision: #" + getClientRevision() + " ======================");
    }

    private HashMap<String, ClassNode> parseJar(JarFile jarFile) throws IOException {
        HashMap<String, ClassNode> list = new HashMap<>();

        Enumeration<?> entries = jarFile.entries();
        while(entries.hasMoreElements()){
            JarEntry entry = (JarEntry)entries.nextElement();
            if(entry.getName().endsWith(".class")){
                ClassReader reader = new ClassReader(jarFile.getInputStream(entry));
                ClassNode node = new ClassNode();
                reader.accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

                list.put(node.name, node);
            }
        }

        jarFile.close();
        return list;
    }

    public static Hook findClassByName(String name){
        for(Map.Entry entry: classMap.entrySet()){
            if(((Hook)entry.getValue()).getHookName().equals(name)){
                //System.out.println("Found hooK: " + ((Hook)entry.getValue()).getClassNode().name);
                return (Hook)entry.getValue();
            }
        }
        return null;
    }

    public static Hook findClassByNode(ClassNode node){
        for(Map.Entry entry: classMap.entrySet()){
            if(((Hook)entry.getValue()).getClassNode().equals(node)){
                return (Hook)entry.getValue();
            }
        }
        return null;
    }

    public static Hook findClassByNodeName(String name){
        for(Map.Entry entry: classMap.entrySet()){
            if(((Hook)entry.getValue()).getClassNode().name.equals(name)){
                return (Hook)entry.getValue();
            }
        }
        return null;
    }

    private void loadAnalyzers(){
        Collections.addAll(analyzers,
                new Node("Node"),
                new DoubleNode("DoubleNode"),
                new Renderable("Renderable"),
                new Character("Character"),
                new Model("Model"),
                new Player("Player"),
                new NPCDefinition("NpcDefinition"),
                new NPC("NPC"),
                new GroundItemPile("GroundItemPIle"),
                new Tile("Tile"),
                new Widget("Widget"),
                new ItemDefinition("ItemDefinition"),
                new Message("Message"),

                new Engine("Engine"),
                new Client("Client"));

        for(Analyzer analyzer: analyzers){
            analyzer.run(classes.values().toArray(new ClassNode[classes.size()]));
        }
    }

    private int getClientRevision(){
        int revision = -1;

        Hook hook = classMap.get("Client");

        if(hook != null){
            for(MethodNode method: hook.getClassNode().methods){
               ListIterator<?> insList = method.instructions.iterator();
                while(insList.hasNext()){
                    AbstractInsnNode ins = (AbstractInsnNode)insList.next();
                    if(ins != null){
                        if(ins instanceof IntInsnNode && ((IntInsnNode) ins).operand == 503){
                            return ((IntInsnNode)insList.next()).operand;
                        }
                    }
                }
            }
        }

        return revision;
    }

    public static void putClass(String hookName, Hook hook){
        classMap.put(hookName, hook);
    }

    public static void main(String a[]){
        new Updater();
    }
}
