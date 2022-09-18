package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.LexicalContextStatement */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LexicalContextStatement.class */
public abstract class LexicalContextStatement extends Statement implements LexicalContextNode {
    private static final long serialVersionUID = 1;

    public LexicalContextStatement(int lineNumber, long token, int finish) {
        super(lineNumber, token, finish);
    }

    public LexicalContextStatement(LexicalContextStatement node) {
        super(node);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return LexicalContextNode.Acceptor.accept(this, visitor);
    }
}
