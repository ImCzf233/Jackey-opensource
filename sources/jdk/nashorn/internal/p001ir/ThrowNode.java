package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.ThrowNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/ThrowNode.class */
public final class ThrowNode extends Statement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final LocalVariableConversion conversion;
    private final boolean isSyntheticRethrow;

    public ThrowNode(int lineNumber, long token, int finish, Expression expression, boolean isSyntheticRethrow) {
        super(lineNumber, token, finish);
        this.expression = expression;
        this.isSyntheticRethrow = isSyntheticRethrow;
        this.conversion = null;
    }

    private ThrowNode(ThrowNode node, Expression expression, boolean isSyntheticRethrow, LocalVariableConversion conversion) {
        super(node);
        this.expression = expression;
        this.isSyntheticRethrow = isSyntheticRethrow;
        this.conversion = conversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterThrowNode(this)) {
            return visitor.leaveThrowNode(setExpression((Expression) this.expression.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("throw ");
        if (this.expression != null) {
            this.expression.toString(sb, printType);
        }
        if (this.conversion != null) {
            this.conversion.toString(sb);
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ThrowNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ThrowNode(this, expression, this.isSyntheticRethrow, this.conversion);
    }

    public boolean isSyntheticRethrow() {
        return this.isSyntheticRethrow;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (this.conversion == conversion) {
            return this;
        }
        return new ThrowNode(this, this.expression, this.isSyntheticRethrow, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
