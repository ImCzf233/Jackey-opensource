package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/IntInsnNode.class */
public class IntInsnNode extends AbstractInsnNode {
    public int operand;

    public IntInsnNode(int opcode, int operand) {
        super(opcode);
        this.operand = operand;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 1;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitIntInsn(this.opcode, this.operand);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new IntInsnNode(this.opcode, this.operand).cloneAnnotations(this);
    }
}
