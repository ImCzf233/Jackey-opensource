package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.LexicalContextExpression */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LexicalContextExpression.class */
public abstract class LexicalContextExpression extends Expression implements LexicalContextNode {
    private static final long serialVersionUID = 1;

    public LexicalContextExpression(LexicalContextExpression expr) {
        super(expr);
    }

    LexicalContextExpression(long token, int start, int finish) {
        super(token, start, finish);
    }

    public LexicalContextExpression(long token, int finish) {
        super(token, finish);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return LexicalContextNode.Acceptor.accept(this, visitor);
    }
}
