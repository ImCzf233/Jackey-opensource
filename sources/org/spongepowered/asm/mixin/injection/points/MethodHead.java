package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("HEAD")
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/points/MethodHead.class */
public class MethodHead extends InjectionPoint {
    public MethodHead(InjectionPointData data) {
        super(data);
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean checkPriority(int targetPriority, int ownerPriority) {
        return true;
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
        nodes.add(insns.getFirst());
        return true;
    }
}
