package org.spongepowered.asm.lib.tree.analysis;

import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/SourceValue.class */
public class SourceValue implements Value {
    public final int size;
    public final Set<AbstractInsnNode> insns;

    public SourceValue(int size) {
        this(size, SmallSet.emptySet());
    }

    public SourceValue(int size, AbstractInsnNode insn) {
        this.size = size;
        this.insns = new SmallSet(insn, null);
    }

    public SourceValue(int size, Set<AbstractInsnNode> insns) {
        this.size = size;
        this.insns = insns;
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.Value
    public int getSize() {
        return this.size;
    }

    public boolean equals(Object value) {
        if (!(value instanceof SourceValue)) {
            return false;
        }
        SourceValue v = (SourceValue) value;
        return this.size == v.size && this.insns.equals(v.insns);
    }

    public int hashCode() {
        return this.insns.hashCode();
    }
}
