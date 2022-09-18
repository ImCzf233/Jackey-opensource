package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.TernaryNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/TernaryNode.class */
public final class TernaryNode extends Expression {
    private static final long serialVersionUID = 1;
    private final Expression test;
    private final JoinPredecessorExpression trueExpr;
    private final JoinPredecessorExpression falseExpr;

    public TernaryNode(long token, Expression test, JoinPredecessorExpression trueExpr, JoinPredecessorExpression falseExpr) {
        super(token, falseExpr.getFinish());
        this.test = test;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    private TernaryNode(TernaryNode ternaryNode, Expression test, JoinPredecessorExpression trueExpr, JoinPredecessorExpression falseExpr) {
        super(ternaryNode);
        this.test = test;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterTernaryNode(this)) {
            Expression newTest = (Expression) getTest().accept(visitor);
            JoinPredecessorExpression newTrueExpr = (JoinPredecessorExpression) this.trueExpr.accept(visitor);
            JoinPredecessorExpression newFalseExpr = (JoinPredecessorExpression) this.falseExpr.accept(visitor);
            return visitor.leaveTernaryNode(setTest(newTest).setTrueExpression(newTrueExpr).setFalseExpression(newFalseExpr));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        TokenType tokenType = tokenType();
        boolean testParen = tokenType.needsParens(getTest().tokenType(), true);
        boolean trueParen = tokenType.needsParens(getTrueExpression().tokenType(), false);
        boolean falseParen = tokenType.needsParens(getFalseExpression().tokenType(), false);
        if (testParen) {
            sb.append('(');
        }
        getTest().toString(sb, printType);
        if (testParen) {
            sb.append(')');
        }
        sb.append(" ? ");
        if (trueParen) {
            sb.append('(');
        }
        getTrueExpression().toString(sb, printType);
        if (trueParen) {
            sb.append(')');
        }
        sb.append(" : ");
        if (falseParen) {
            sb.append('(');
        }
        getFalseExpression().toString(sb, printType);
        if (falseParen) {
            sb.append(')');
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        return getTest().isLocal() && getTrueExpression().isLocal() && getFalseExpression().isLocal();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return Type.widestReturnType(getTrueExpression().getType(), getFalseExpression().getType());
    }

    public Expression getTest() {
        return this.test;
    }

    public JoinPredecessorExpression getTrueExpression() {
        return this.trueExpr;
    }

    public JoinPredecessorExpression getFalseExpression() {
        return this.falseExpr;
    }

    public TernaryNode setTest(Expression test) {
        if (this.test == test) {
            return this;
        }
        return new TernaryNode(this, test, this.trueExpr, this.falseExpr);
    }

    public TernaryNode setTrueExpression(JoinPredecessorExpression trueExpr) {
        if (this.trueExpr == trueExpr) {
            return this;
        }
        return new TernaryNode(this, this.test, trueExpr, this.falseExpr);
    }

    public TernaryNode setFalseExpression(JoinPredecessorExpression falseExpr) {
        if (this.falseExpr == falseExpr) {
            return this;
        }
        return new TernaryNode(this, this.test, this.trueExpr, falseExpr);
    }
}
