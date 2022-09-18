package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/Subroutine.class */
class Subroutine {
    LabelNode start;
    boolean[] access;
    List<JumpInsnNode> callers;

    private Subroutine() {
    }

    public Subroutine(LabelNode start, int maxLocals, JumpInsnNode caller) {
        this.start = start;
        this.access = new boolean[maxLocals];
        this.callers = new ArrayList();
        this.callers.add(caller);
    }

    public Subroutine copy() {
        Subroutine result = new Subroutine();
        result.start = this.start;
        result.access = new boolean[this.access.length];
        System.arraycopy(this.access, 0, result.access, 0, this.access.length);
        result.callers = new ArrayList(this.callers);
        return result;
    }

    public boolean merge(Subroutine subroutine) throws AnalyzerException {
        boolean changes = false;
        for (int i = 0; i < this.access.length; i++) {
            if (subroutine.access[i] && !this.access[i]) {
                this.access[i] = true;
                changes = true;
            }
        }
        if (subroutine.start == this.start) {
            for (int i2 = 0; i2 < subroutine.callers.size(); i2++) {
                JumpInsnNode caller = subroutine.callers.get(i2);
                if (!this.callers.contains(caller)) {
                    this.callers.add(caller);
                    changes = true;
                }
            }
        }
        return changes;
    }
}
