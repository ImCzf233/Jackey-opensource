package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.SplitReturn */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/SplitReturn.class */
public final class SplitReturn extends Statement {
    private static final long serialVersionUID = 1;
    public static final SplitReturn INSTANCE = new SplitReturn();

    private SplitReturn() {
        super(-1, 0L, 0);
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return visitor.enterSplitReturn(this) ? visitor.leaveSplitReturn(this) : this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(":splitreturn;");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
