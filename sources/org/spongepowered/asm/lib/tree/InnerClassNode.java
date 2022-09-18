package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.ClassVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/InnerClassNode.class */
public class InnerClassNode {
    public String name;
    public String outerName;
    public String innerName;
    public int access;

    public InnerClassNode(String name, String outerName, String innerName, int access) {
        this.name = name;
        this.outerName = outerName;
        this.innerName = innerName;
        this.access = access;
    }

    public void accept(ClassVisitor cv) {
        cv.visitInnerClass(this.name, this.outerName, this.innerName, this.access);
    }
}
