package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ApplyCaseFold.class */
public final class ApplyCaseFold {
    static final ApplyCaseFold INSTANCE = new ApplyCaseFold();

    ApplyCaseFold() {
    }

    public static void apply(int from, int to, Object o) {
        ApplyCaseFoldArg arg = (ApplyCaseFoldArg) o;
        ScanEnvironment env = arg.env;
        CClassNode cc = arg.f282cc;
        BitSet bs = cc.f297bs;
        boolean inCC = cc.isCodeInCC(from);
        if ((inCC && !cc.isNot()) || (!inCC && cc.isNot())) {
            if (to >= 256) {
                cc.addCodeRange(env, to, to);
            } else {
                bs.set(to);
            }
        }
    }
}
