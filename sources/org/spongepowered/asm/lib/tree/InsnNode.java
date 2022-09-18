package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/InsnNode.class */
public class InsnNode extends AbstractInsnNode {
    public InsnNode(int opcode) {
        super(opcode);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 0;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitInsn(this.opcode);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new InsnNode(this.opcode).cloneAnnotations(this);
    }
}
