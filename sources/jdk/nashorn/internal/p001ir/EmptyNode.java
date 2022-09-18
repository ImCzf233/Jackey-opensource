package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.EmptyNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/EmptyNode.class */
public final class EmptyNode extends Statement {
    private static final long serialVersionUID = 1;

    public EmptyNode(Statement node) {
        super(node);
    }

    public EmptyNode(int lineNumber, long token, int finish) {
        super(lineNumber, token, finish);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterEmptyNode(this)) {
            return visitor.leaveEmptyNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printTypes) {
        sb.append(';');
    }
}
