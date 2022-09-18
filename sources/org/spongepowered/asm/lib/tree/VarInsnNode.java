package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/VarInsnNode.class */
public class VarInsnNode extends AbstractInsnNode {
    public int var;

    public VarInsnNode(int opcode, int var) {
        super(opcode);
        this.var = var;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 2;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitVarInsn(this.opcode, this.var);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new VarInsnNode(this.opcode, this.var).cloneAnnotations(this);
    }
}
