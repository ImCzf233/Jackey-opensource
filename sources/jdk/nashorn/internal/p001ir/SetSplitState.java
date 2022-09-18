package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.Scope;

/* renamed from: jdk.nashorn.internal.ir.SetSplitState */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/SetSplitState.class */
public final class SetSplitState extends Statement {
    private static final long serialVersionUID = 1;
    private final int state;

    public SetSplitState(int state, int lineNumber) {
        super(lineNumber, 0L, 0);
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return visitor.enterSetSplitState(this) ? visitor.leaveSetSplitState(this) : this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(CompilerConstants.SCOPE.symbolName()).append('.').append(Scope.SET_SPLIT_STATE.name()).append('(').append(this.state).append(");");
    }
}
