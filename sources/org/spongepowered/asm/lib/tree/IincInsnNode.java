package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/IincInsnNode.class */
public class IincInsnNode extends AbstractInsnNode {
    public int var;
    public int incr;

    public IincInsnNode(int var, int incr) {
        super(132);
        this.var = var;
        this.incr = incr;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 10;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitIincInsn(this.var, this.incr);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new IincInsnNode(this.var, this.incr).cloneAnnotations(this);
    }
}
