package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/BackRefNode.class */
public final class BackRefNode extends StateNode {
    public final int backRef;

    public BackRefNode(int backRef, ScanEnvironment env) {
        this.backRef = backRef;
        if (backRef <= env.numMem && env.memNodes[backRef] == null) {
            setRecursion();
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public int getType() {
        return 4;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String getName() {
        return "Back Ref";
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode, jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int level) {
        StringBuilder value = new StringBuilder(super.toString(level));
        value.append("\n  back: ").append(this.backRef);
        return value.toString();
    }
}
