package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/LdcInsnNode.class */
public class LdcInsnNode extends AbstractInsnNode {
    public Object cst;

    public LdcInsnNode(Object cst) {
        super(18);
        this.cst = cst;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 9;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitLdcInsn(this.cst);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new LdcInsnNode(this.cst).cloneAnnotations(this);
    }
}
