package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.ReturnNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/ReturnNode.class */
public class ReturnNode extends Statement {
    private static final long serialVersionUID = 1;
    private final Expression expression;

    public ReturnNode(int lineNumber, long token, int finish, Expression expression) {
        super(lineNumber, token, finish);
        this.expression = expression;
    }

    private ReturnNode(ReturnNode returnNode, Expression expression) {
        super(returnNode);
        this.expression = expression;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return true;
    }

    public boolean isReturn() {
        return isTokenType(TokenType.RETURN);
    }

    public boolean hasExpression() {
        return this.expression != null;
    }

    public boolean isYield() {
        return isTokenType(TokenType.YIELD);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterReturnNode(this)) {
            if (this.expression != null) {
                return visitor.leaveReturnNode(setExpression((Expression) this.expression.accept(visitor)));
            }
            return visitor.leaveReturnNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(isYield() ? "yield" : "return");
        if (this.expression != null) {
            sb.append(' ');
            this.expression.toString(sb, printType);
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ReturnNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ReturnNode(this, expression);
    }
}
