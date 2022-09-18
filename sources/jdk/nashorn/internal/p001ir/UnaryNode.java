package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.UnaryNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/UnaryNode.class */
public final class UnaryNode extends Expression implements Assignment<Expression>, Optimistic {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final int programPoint;
    private final Type type;
    @Ignore
    private static final List<TokenType> CAN_OVERFLOW;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !UnaryNode.class.desiredAssertionStatus();
        CAN_OVERFLOW = Collections.unmodifiableList(Arrays.asList(TokenType.ADD, TokenType.SUB, TokenType.DECPREFIX, TokenType.DECPOSTFIX, TokenType.INCPREFIX, TokenType.INCPOSTFIX));
    }

    public UnaryNode(long token, Expression rhs) {
        this(token, Math.min(rhs.getStart(), Token.descPosition(token)), Math.max(Token.descPosition(token) + Token.descLength(token), rhs.getFinish()), rhs);
    }

    public UnaryNode(long token, int start, int finish, Expression expression) {
        super(token, start, finish);
        this.expression = expression;
        this.programPoint = -1;
        this.type = null;
    }

    private UnaryNode(UnaryNode unaryNode, Expression expression, Type type, int programPoint) {
        super(unaryNode);
        this.expression = expression;
        this.programPoint = programPoint;
        this.type = type;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public boolean isAssignment() {
        switch (tokenType()) {
            case DECPOSTFIX:
            case DECPREFIX:
            case INCPOSTFIX:
            case INCPREFIX:
                return true;
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isSelfModifying() {
        return isAssignment();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getWidestOperationType() {
        switch (tokenType()) {
            case ADD:
                Type operandType = getExpression().getType();
                if (operandType == Type.BOOLEAN) {
                    return Type.INT;
                }
                if (operandType.isObject()) {
                    return Type.NUMBER;
                }
                if (!$assertionsDisabled && !operandType.isNumeric()) {
                    throw new AssertionError();
                }
                return operandType;
            case SUB:
                return Type.NUMBER;
            case NOT:
            case DELETE:
                return Type.BOOLEAN;
            case BIT_NOT:
                return Type.INT;
            case VOID:
                return Type.UNDEFINED;
            default:
                return isAssignment() ? Type.NUMBER : Type.OBJECT;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentDest() {
        if (isAssignment()) {
            return getExpression();
        }
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public UnaryNode setAssignmentDest(Expression n) {
        return setExpression(n);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentSource() {
        return getAssignmentDest();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterUnaryNode(this)) {
            return visitor.leaveUnaryNode(setExpression((Expression) this.expression.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        switch (tokenType()) {
            case DECPOSTFIX:
            case DECPREFIX:
            case INCPOSTFIX:
            case INCPREFIX:
                return (this.expression instanceof IdentNode) && this.expression.isLocal() && this.expression.getType().isJSPrimitive();
            case ADD:
            case SUB:
            case NOT:
            case BIT_NOT:
                return this.expression.isLocal() && this.expression.getType().isJSPrimitive();
            case DELETE:
            case VOID:
            default:
                return this.expression.isLocal();
            case NEW:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(final StringBuilder sb, final boolean printType) {
        toString(sb, new Runnable() { // from class: jdk.nashorn.internal.ir.UnaryNode.1
            @Override // java.lang.Runnable
            public void run() {
                UnaryNode.this.getExpression().toString(sb, printType);
            }
        }, printType);
    }

    public void toString(StringBuilder sb, Runnable rhsStringBuilder, boolean printType) {
        TokenType tokenType = tokenType();
        String name = tokenType.getName();
        boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
        if (isOptimistic()) {
            sb.append("%");
        }
        boolean rhsParen = tokenType.needsParens(getExpression().tokenType(), false);
        if (!isPostfix) {
            if (name == null) {
                sb.append(tokenType.name());
                rhsParen = true;
            } else {
                sb.append(name);
                if (tokenType.ordinal() > TokenType.BIT_NOT.ordinal()) {
                    sb.append(' ');
                }
            }
        }
        if (rhsParen) {
            sb.append('(');
        }
        rhsStringBuilder.run();
        if (rhsParen) {
            sb.append(')');
        }
        if (isPostfix) {
            sb.append(tokenType == TokenType.DECPOSTFIX ? "--" : "++");
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public UnaryNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new UnaryNode(this, expression, this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public UnaryNode setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new UnaryNode(this, this.expression, this.type, programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public boolean canBeOptimistic() {
        return getMostOptimisticType() != getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        if (CAN_OVERFLOW.contains(tokenType())) {
            return Type.INT;
        }
        return getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return getWidestOperationType();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        Type widest = getWidestOperationType();
        if (this.type == null) {
            return widest;
        }
        return Type.narrowest(widest, Type.widest(this.type, this.expression.getType()));
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public UnaryNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new UnaryNode(this, this.expression, type, this.programPoint);
    }
}
