package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.BinaryNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BinaryNode.class */
public final class BinaryNode extends Expression implements Assignment<Expression>, Optimistic {
    private static final long serialVersionUID = 1;
    private static final Type OPTIMISTIC_UNDECIDED_TYPE;
    private final Expression lhs;
    private final Expression rhs;
    private final int programPoint;
    private final Type type;
    private transient Type cachedType;
    @Ignore
    private static final Set<TokenType> CAN_OVERFLOW;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !BinaryNode.class.desiredAssertionStatus();
        OPTIMISTIC_UNDECIDED_TYPE = Type.typeFor(new Object() { // from class: jdk.nashorn.internal.ir.BinaryNode.1
        }.getClass());
        CAN_OVERFLOW = Collections.unmodifiableSet(new HashSet(Arrays.asList(TokenType.ADD, TokenType.DIV, TokenType.MOD, TokenType.MUL, TokenType.SUB, TokenType.ASSIGN_ADD, TokenType.ASSIGN_DIV, TokenType.ASSIGN_MOD, TokenType.ASSIGN_MUL, TokenType.ASSIGN_SUB, TokenType.SHR, TokenType.ASSIGN_SHR)));
    }

    public BinaryNode(long token, Expression lhs, Expression rhs) {
        super(token, lhs.getStart(), rhs.getFinish());
        if ($assertionsDisabled || ((!isTokenType(TokenType.AND) && !isTokenType(TokenType.OR)) || (lhs instanceof JoinPredecessorExpression))) {
            this.lhs = lhs;
            this.rhs = rhs;
            this.programPoint = -1;
            this.type = null;
            return;
        }
        throw new AssertionError();
    }

    private BinaryNode(BinaryNode binaryNode, Expression lhs, Expression rhs, Type type, int programPoint) {
        super(binaryNode);
        this.lhs = lhs;
        this.rhs = rhs;
        this.programPoint = programPoint;
        this.type = type;
    }

    public boolean isComparison() {
        switch (tokenType()) {
            case EQ:
            case EQ_STRICT:
            case NE:
            case NE_STRICT:
            case LE:
            case LT:
            case GE:
            case GT:
                return true;
            default:
                return false;
        }
    }

    public boolean isRelational() {
        switch (tokenType()) {
            case LE:
            case LT:
            case GE:
            case GT:
                return true;
            default:
                return false;
        }
    }

    public boolean isLogical() {
        return isLogical(tokenType());
    }

    public static boolean isLogical(TokenType tokenType) {
        switch (tokenType) {
            case AND:
            case OR:
                return true;
            default:
                return false;
        }
    }

    public Type getWidestOperandType() {
        switch (tokenType()) {
            case SHR:
            case ASSIGN_SHR:
                return Type.INT;
            case INSTANCEOF:
                return Type.OBJECT;
            default:
                if (isComparison()) {
                    return Type.OBJECT;
                }
                return getWidestOperationType();
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getWidestOperationType() {
        switch (tokenType()) {
            case AND:
            case OR:
                return Type.widestReturnType(this.lhs.getType(), this.rhs.getType());
            case SHR:
            case ASSIGN_SHR:
                return Type.NUMBER;
            case INSTANCEOF:
                return Type.BOOLEAN;
            case ADD:
            case ASSIGN_ADD:
                Type lhsType = this.lhs.getType();
                Type rhsType = this.rhs.getType();
                if (lhsType == Type.BOOLEAN && rhsType == Type.BOOLEAN) {
                    return Type.INT;
                }
                if (isString(lhsType) || isString(rhsType)) {
                    return Type.CHARSEQUENCE;
                }
                Type widestOperandType = Type.widest(undefinedToNumber(booleanToInt(lhsType)), undefinedToNumber(booleanToInt(rhsType)));
                if (widestOperandType.isNumeric()) {
                    return Type.NUMBER;
                }
                return Type.OBJECT;
            case ASSIGN_SAR:
            case ASSIGN_SHL:
            case BIT_AND:
            case BIT_OR:
            case BIT_XOR:
            case ASSIGN_BIT_AND:
            case ASSIGN_BIT_OR:
            case ASSIGN_BIT_XOR:
            case SAR:
            case SHL:
                return Type.INT;
            case DIV:
            case MOD:
            case ASSIGN_DIV:
            case ASSIGN_MOD:
                return Type.NUMBER;
            case MUL:
            case SUB:
            case ASSIGN_MUL:
            case ASSIGN_SUB:
                Type lhsType2 = this.lhs.getType();
                Type rhsType2 = this.rhs.getType();
                if (lhsType2 == Type.BOOLEAN && rhsType2 == Type.BOOLEAN) {
                    return Type.INT;
                }
                return Type.NUMBER;
            case VOID:
                return Type.UNDEFINED;
            case ASSIGN:
                return this.rhs.getType();
            case COMMALEFT:
                return this.lhs.getType();
            case COMMARIGHT:
                return this.rhs.getType();
            default:
                if (isComparison()) {
                    return Type.BOOLEAN;
                }
                return Type.OBJECT;
        }
    }

    private static boolean isString(Type type) {
        return type == Type.STRING || type == Type.CHARSEQUENCE;
    }

    private static Type booleanToInt(Type type) {
        return type == Type.BOOLEAN ? Type.INT : type;
    }

    private static Type undefinedToNumber(Type type) {
        return type == Type.UNDEFINED ? Type.NUMBER : type;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public boolean isAssignment() {
        switch (tokenType()) {
            case ASSIGN_SHR:
            case ASSIGN_ADD:
            case ASSIGN_SAR:
            case ASSIGN_SHL:
            case ASSIGN_BIT_AND:
            case ASSIGN_BIT_OR:
            case ASSIGN_BIT_XOR:
            case ASSIGN_DIV:
            case ASSIGN_MOD:
            case ASSIGN_MUL:
            case ASSIGN_SUB:
            case ASSIGN:
                return true;
            case INSTANCEOF:
            case ADD:
            case BIT_AND:
            case BIT_OR:
            case BIT_XOR:
            case SAR:
            case SHL:
            case DIV:
            case MOD:
            case MUL:
            case SUB:
            case VOID:
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isSelfModifying() {
        return isAssignment() && !isTokenType(TokenType.ASSIGN);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentDest() {
        if (isAssignment()) {
            return lhs();
        }
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public BinaryNode setAssignmentDest(Expression n) {
        return setLHS(n);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentSource() {
        return rhs();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBinaryNode(this)) {
            return visitor.leaveBinaryNode(setLHS((Expression) this.lhs.accept(visitor)).setRHS((Expression) this.rhs.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        switch (tokenType()) {
            case SHR:
            case ADD:
            case BIT_AND:
            case BIT_OR:
            case BIT_XOR:
            case SAR:
            case SHL:
            case DIV:
            case MOD:
            case MUL:
            case SUB:
                return this.lhs.isLocal() && this.lhs.getType().isJSPrimitive() && this.rhs.isLocal() && this.rhs.getType().isJSPrimitive();
            case ASSIGN_SHR:
            case ASSIGN_ADD:
            case ASSIGN_SAR:
            case ASSIGN_SHL:
            case ASSIGN_BIT_AND:
            case ASSIGN_BIT_OR:
            case ASSIGN_BIT_XOR:
            case ASSIGN_DIV:
            case ASSIGN_MOD:
            case ASSIGN_MUL:
            case ASSIGN_SUB:
                return (this.lhs instanceof IdentNode) && this.lhs.isLocal() && this.lhs.getType().isJSPrimitive() && this.rhs.isLocal() && this.rhs.getType().isJSPrimitive();
            case INSTANCEOF:
            case VOID:
            default:
                return false;
            case ASSIGN:
                return (this.lhs instanceof IdentNode) && this.lhs.isLocal() && this.rhs.isLocal();
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isAlwaysFalse() {
        switch (tokenType()) {
            case COMMALEFT:
                return this.lhs.isAlwaysFalse();
            case COMMARIGHT:
                return this.rhs.isAlwaysFalse();
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isAlwaysTrue() {
        switch (tokenType()) {
            case COMMALEFT:
                return this.lhs.isAlwaysTrue();
            case COMMARIGHT:
                return this.rhs.isAlwaysTrue();
            default:
                return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        TokenType tokenType = tokenType();
        boolean lhsParen = tokenType.needsParens(lhs().tokenType(), true);
        boolean rhsParen = tokenType.needsParens(rhs().tokenType(), false);
        if (lhsParen) {
            sb.append('(');
        }
        lhs().toString(sb, printType);
        if (lhsParen) {
            sb.append(')');
        }
        sb.append(' ');
        switch (tokenType) {
            case COMMALEFT:
                sb.append(",<");
                break;
            case COMMARIGHT:
                sb.append(",>");
                break;
            case INCPREFIX:
            case DECPREFIX:
                sb.append("++");
                break;
            default:
                sb.append(tokenType.getName());
                break;
        }
        if (isOptimistic()) {
            sb.append("%");
        }
        sb.append(' ');
        if (rhsParen) {
            sb.append('(');
        }
        rhs().toString(sb, printType);
        if (rhsParen) {
            sb.append(')');
        }
    }

    public Expression lhs() {
        return this.lhs;
    }

    public Expression rhs() {
        return this.rhs;
    }

    public BinaryNode setLHS(Expression lhs) {
        if (this.lhs == lhs) {
            return this;
        }
        return new BinaryNode(this, lhs, this.rhs, this.type, this.programPoint);
    }

    public BinaryNode setRHS(Expression rhs) {
        if (this.rhs == rhs) {
            return this;
        }
        return new BinaryNode(this, this.lhs, rhs, this.type, this.programPoint);
    }

    public BinaryNode setOperands(Expression lhs, Expression rhs) {
        if (this.lhs == lhs && this.rhs == rhs) {
            return this;
        }
        return new BinaryNode(this, lhs, rhs, this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public boolean canBeOptimistic() {
        return isTokenType(TokenType.ADD) || getMostOptimisticType() != getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public BinaryNode setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new BinaryNode(this, this.lhs, this.rhs, this.type, programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        TokenType tokenType = tokenType();
        if (tokenType == TokenType.ADD || tokenType == TokenType.ASSIGN_ADD) {
            return OPTIMISTIC_UNDECIDED_TYPE;
        }
        if (CAN_OVERFLOW.contains(tokenType)) {
            return Type.INT;
        }
        return getMostPessimisticType();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return getWidestOperationType();
    }

    public boolean isOptimisticUndecidedType() {
        return this.type == OPTIMISTIC_UNDECIDED_TYPE;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        if (this.cachedType == null) {
            this.cachedType = getTypeUncached();
        }
        return this.cachedType;
    }

    private Type getTypeUncached() {
        if (this.type == OPTIMISTIC_UNDECIDED_TYPE) {
            return decideType(this.lhs.getType(), this.rhs.getType());
        }
        Type widest = getWidestOperationType();
        if (this.type == null) {
            return widest;
        }
        if (tokenType() == TokenType.ASSIGN_SHR || tokenType() == TokenType.SHR) {
            return this.type;
        }
        return Type.narrowest(widest, Type.widest(this.type, Type.widest(this.lhs.getType(), this.rhs.getType())));
    }

    private static Type decideType(Type lhsType, Type rhsType) {
        if (isString(lhsType) || isString(rhsType)) {
            return Type.CHARSEQUENCE;
        }
        Type widest = Type.widest(undefinedToNumber(booleanToInt(lhsType)), undefinedToNumber(booleanToInt(rhsType)));
        return widest.isObject() ? Type.OBJECT : widest;
    }

    public BinaryNode decideType() {
        if ($assertionsDisabled || this.type == OPTIMISTIC_UNDECIDED_TYPE) {
            return setType(decideType(this.lhs.getType(), this.rhs.getType()));
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public BinaryNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new BinaryNode(this, this.lhs, this.rhs, type, this.programPoint);
    }
}
