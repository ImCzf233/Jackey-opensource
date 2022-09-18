package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/TypeAnnotationNode.class */
public class TypeAnnotationNode extends AnnotationNode {
    public int typeRef;
    public TypePath typePath;

    public TypeAnnotationNode(int typeRef, TypePath typePath, String desc) {
        this(Opcodes.ASM5, typeRef, typePath, desc);
        if (getClass() != TypeAnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public TypeAnnotationNode(int api, int typeRef, TypePath typePath, String desc) {
        super(api, desc);
        this.typeRef = typeRef;
        this.typePath = typePath;
    }
}
