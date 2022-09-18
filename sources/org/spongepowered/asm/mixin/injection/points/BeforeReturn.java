package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("RETURN")
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/points/BeforeReturn.class */
public class BeforeReturn extends InjectionPoint {
    private final int ordinal;

    public BeforeReturn(InjectionPointData data) {
        super(data);
        this.ordinal = data.getOrdinal();
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean checkPriority(int targetPriority, int ownerPriority) {
        return true;
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
        boolean found = false;
        int returnOpcode = Type.getReturnType(desc).getOpcode(172);
        int ordinal = 0;
        ListIterator<AbstractInsnNode> iter = insns.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if ((insn instanceof InsnNode) && insn.getOpcode() == returnOpcode) {
                if (this.ordinal == -1 || this.ordinal == ordinal) {
                    nodes.add(insn);
                    found = true;
                }
                ordinal++;
            }
        }
        return found;
    }
}
