package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("INVOKE_ASSIGN")
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/points/AfterInvoke.class */
public class AfterInvoke extends BeforeInvoke {
    public AfterInvoke(InjectionPointData data) {
        super(data);
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    public boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
        MethodInsnNode methodNode = (MethodInsnNode) insn;
        if (Type.getReturnType(methodNode.desc) == Type.VOID_TYPE) {
            return false;
        }
        AbstractInsnNode insn2 = InjectionPoint.nextNode(insns, insn);
        if ((insn2 instanceof VarInsnNode) && insn2.getOpcode() >= 54) {
            insn2 = InjectionPoint.nextNode(insns, insn2);
        }
        nodes.add(insn2);
        return true;
    }
}
