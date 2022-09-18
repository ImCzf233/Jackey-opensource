package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ApplyCaseFoldArg.class */
public final class ApplyCaseFoldArg {
    final ScanEnvironment env;

    /* renamed from: cc */
    final CClassNode f282cc;
    ConsAltNode altRoot;
    ConsAltNode tail;

    public ApplyCaseFoldArg(ScanEnvironment env, CClassNode cc) {
        this.env = env;
        this.f282cc = cc;
    }
}
