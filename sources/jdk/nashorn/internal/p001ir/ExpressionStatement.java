package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.ExpressionStatement */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/ExpressionStatement.class */
public final class ExpressionStatement extends Statement {
    private static final long serialVersionUID = 1;
    private final Expression expression;

    public ExpressionStatement(int lineNumber, long token, int finish, Expression expression) {
        super(lineNumber, token, finish);
        this.expression = expression;
    }

    private ExpressionStatement(ExpressionStatement expressionStatement, Expression expression) {
        super(expressionStatement);
        this.expression = expression;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterExpressionStatement(this)) {
            return visitor.leaveExpressionStatement(setExpression((Expression) this.expression.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printTypes) {
        this.expression.toString(sb, printTypes);
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ExpressionStatement setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ExpressionStatement(this, expression);
    }
}
