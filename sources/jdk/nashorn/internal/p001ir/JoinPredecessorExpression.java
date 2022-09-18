package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.JoinPredecessorExpression */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/JoinPredecessorExpression.class */
public class JoinPredecessorExpression extends Expression implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final LocalVariableConversion conversion;

    public JoinPredecessorExpression() {
        this(null);
    }

    public JoinPredecessorExpression(Expression expression) {
        this(expression, null);
    }

    private JoinPredecessorExpression(Expression expression, LocalVariableConversion conversion) {
        super(expression == null ? 0L : expression.getToken(), expression == null ? 0 : expression.getStart(), expression == null ? 0 : expression.getFinish());
        this.expression = expression;
        this.conversion = conversion;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (conversion == this.conversion) {
            return this;
        }
        return new JoinPredecessorExpression(this.expression, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return this.expression.getType();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isAlwaysFalse() {
        return this.expression != null && this.expression.isAlwaysFalse();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isAlwaysTrue() {
        return this.expression != null && this.expression.isAlwaysTrue();
    }

    public Expression getExpression() {
        return this.expression;
    }

    public JoinPredecessorExpression setExpression(Expression expression) {
        if (expression == this.expression) {
            return this;
        }
        return new JoinPredecessorExpression(expression, this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterJoinPredecessorExpression(this)) {
            Expression expr = getExpression();
            return visitor.leaveJoinPredecessorExpression(expr == null ? this : setExpression((Expression) expr.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        if (this.expression != null) {
            this.expression.toString(sb, printType);
        }
        if (this.conversion != null) {
            this.conversion.toString(sb);
        }
    }
}
