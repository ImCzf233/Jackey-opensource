package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/MultiANewArrayInsnNode.class */
public class MultiANewArrayInsnNode extends AbstractInsnNode {
    public String desc;
    public int dims;

    public MultiANewArrayInsnNode(String desc, int dims) {
        super(197);
        this.desc = desc;
        this.dims = dims;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 13;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitMultiANewArrayInsn(this.desc, this.dims);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
    }
}
