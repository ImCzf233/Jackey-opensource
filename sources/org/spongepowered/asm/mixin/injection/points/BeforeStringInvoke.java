package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode("INVOKE_STRING")
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/points/BeforeStringInvoke.class */
public class BeforeStringInvoke extends BeforeInvoke {
    private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
    private final String ldcValue;
    private boolean foundLdc;

    public BeforeStringInvoke(InjectionPointData data) {
        super(data);
        this.ldcValue = data.get("ldc", (String) null);
        if (this.ldcValue == null) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
        }
        if (!STRING_VOID_SIG.equals(this.target.desc)) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires target method with with signature " + STRING_VOID_SIG);
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke, org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
        this.foundLdc = false;
        return super.find(desc, insns, nodes);
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    protected void inspectInsn(String desc, InsnList insns, AbstractInsnNode insn) {
        if (insn instanceof LdcInsnNode) {
            LdcInsnNode node = (LdcInsnNode) insn;
            if ((node.cst instanceof String) && this.ldcValue.equals(node.cst)) {
                log("{} > found a matching LDC with value {}", this.className, node.cst);
                this.foundLdc = true;
                return;
            }
        }
        this.foundLdc = false;
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    public boolean matchesInsn(MemberInfo nodeInfo, int ordinal) {
        log("{} > > found LDC \"{}\" = {}", this.className, this.ldcValue, Boolean.valueOf(this.foundLdc));
        return this.foundLdc && super.matchesInsn(nodeInfo, ordinal);
    }
}
