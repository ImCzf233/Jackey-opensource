package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/TypeInsnNode.class */
public class TypeInsnNode extends AbstractInsnNode {
    public String desc;

    public TypeInsnNode(int opcode, String desc) {
        super(opcode);
        this.desc = desc;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 3;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        mv.visitTypeInsn(this.opcode, this.desc);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
    }
}
