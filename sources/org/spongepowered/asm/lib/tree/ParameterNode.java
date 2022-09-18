package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/ParameterNode.class */
public class ParameterNode {
    public String name;
    public int access;

    public ParameterNode(String name, int access) {
        this.name = name;
        this.access = access;
    }

    public void accept(MethodVisitor mv) {
        mv.visitParameter(this.name, this.access);
    }
}
