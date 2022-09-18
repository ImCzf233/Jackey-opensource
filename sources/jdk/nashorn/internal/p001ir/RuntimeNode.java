package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.RuntimeNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/RuntimeNode.class */
public class RuntimeNode extends Expression {
    private static final long serialVersionUID = 1;
    private final Request request;
    private final List<Expression> args;

    /* renamed from: jdk.nashorn.internal.ir.RuntimeNode$Request */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/RuntimeNode$Request.class */
    public enum Request {
        ADD(TokenType.ADD, Type.OBJECT, 2, true),
        DEBUGGER,
        NEW,
        TYPEOF,
        REFERENCE_ERROR,
        DELETE(TokenType.DELETE, Type.BOOLEAN, 1),
        SLOW_DELETE(TokenType.DELETE, Type.BOOLEAN, 1, false),
        FAIL_DELETE(TokenType.DELETE, Type.BOOLEAN, 1, false),
        EQ_STRICT(TokenType.EQ_STRICT, Type.BOOLEAN, 2, true),
        EQ(TokenType.EQ, Type.BOOLEAN, 2, true),
        GE(TokenType.GE, Type.BOOLEAN, 2, true),
        GT(TokenType.GT, Type.BOOLEAN, 2, true),
        IN(TokenType.IN, Type.BOOLEAN, 2),
        INSTANCEOF(TokenType.INSTANCEOF, Type.BOOLEAN, 2),
        LE(TokenType.LE, Type.BOOLEAN, 2, true),
        LT(TokenType.LT, Type.BOOLEAN, 2, true),
        NE_STRICT(TokenType.NE_STRICT, Type.BOOLEAN, 2, true),
        NE(TokenType.NE, Type.BOOLEAN, 2, true),
        IS_UNDEFINED(TokenType.EQ_STRICT, Type.BOOLEAN, 2),
        IS_NOT_UNDEFINED(TokenType.NE_STRICT, Type.BOOLEAN, 2);
        
        private final TokenType tokenType;
        private final Type returnType;
        private final int arity;
        private final boolean canSpecialize;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !RuntimeNode.class.desiredAssertionStatus();
        }

        Request() {
            this(TokenType.VOID, Type.OBJECT, 0);
        }

        Request(TokenType tokenType, Type returnType, int arity) {
            this(tokenType, returnType, arity, false);
        }

        Request(TokenType tokenType, Type returnType, int arity, boolean canSpecialize) {
            this.tokenType = tokenType;
            this.returnType = returnType;
            this.arity = arity;
            this.canSpecialize = canSpecialize;
        }

        public boolean canSpecialize() {
            return this.canSpecialize;
        }

        public int getArity() {
            return this.arity;
        }

        public Type getReturnType() {
            return this.returnType;
        }

        public TokenType getTokenType() {
            return this.tokenType;
        }

        public String nonStrictName() {
            switch (this) {
                case NE_STRICT:
                    return NE.name();
                case EQ_STRICT:
                    return EQ.name();
                default:
                    return name();
            }
        }

        public static Request requestFor(Expression node) {
            switch (node.tokenType()) {
                case TYPEOF:
                    return TYPEOF;
                case IN:
                    return IN;
                case INSTANCEOF:
                    return INSTANCEOF;
                case EQ_STRICT:
                    return EQ_STRICT;
                case NE_STRICT:
                    return NE_STRICT;
                case EQ:
                    return EQ;
                case NE:
                    return NE;
                case LT:
                    return LT;
                case LE:
                    return LE;
                case GT:
                    return GT;
                case GE:
                    return GE;
                default:
                    if ($assertionsDisabled) {
                        return null;
                    }
                    throw new AssertionError();
            }
        }

        public static boolean isUndefinedCheck(Request request) {
            return request == IS_UNDEFINED || request == IS_NOT_UNDEFINED;
        }

        public static boolean isEQ(Request request) {
            return request == EQ || request == EQ_STRICT;
        }

        public static boolean isNE(Request request) {
            return request == NE || request == NE_STRICT;
        }

        public static boolean isStrict(Request request) {
            return request == EQ_STRICT || request == NE_STRICT;
        }

        public static Request reverse(Request request) {
            switch (request) {
                case NE_STRICT:
                case EQ_STRICT:
                case EQ:
                case NE:
                    return request;
                case LE:
                    return GE;
                case LT:
                    return GT;
                case GE:
                    return LE;
                case GT:
                    return LT;
                default:
                    return null;
            }
        }

        public static Request invert(Request request) {
            switch (request) {
                case NE_STRICT:
                    return EQ_STRICT;
                case EQ_STRICT:
                    return NE_STRICT;
                case EQ:
                    return NE;
                case NE:
                    return EQ;
                case LE:
                    return GT;
                case LT:
                    return GE;
                case GE:
                    return LT;
                case GT:
                    return LE;
                default:
                    return null;
            }
        }

        public static boolean isComparison(Request request) {
            switch (request) {
                case NE_STRICT:
                case EQ_STRICT:
                case EQ:
                case NE:
                case LE:
                case LT:
                case GE:
                case GT:
                case IS_UNDEFINED:
                case IS_NOT_UNDEFINED:
                    return true;
                default:
                    return false;
            }
        }
    }

    public RuntimeNode(long token, int finish, Request request, List<Expression> args) {
        super(token, finish);
        this.request = request;
        this.args = args;
    }

    private RuntimeNode(RuntimeNode runtimeNode, Request request, List<Expression> args) {
        super(runtimeNode);
        this.request = request;
        this.args = args;
    }

    public RuntimeNode(long token, int finish, Request request, Expression... args) {
        this(token, finish, request, Arrays.asList(args));
    }

    public RuntimeNode(Expression parent, Request request, Expression... args) {
        this(parent, request, Arrays.asList(args));
    }

    public RuntimeNode(Expression parent, Request request, List<Expression> args) {
        super(parent);
        this.request = request;
        this.args = args;
    }

    public RuntimeNode(UnaryNode parent, Request request) {
        this(parent, request, parent.getExpression());
    }

    public RuntimeNode(BinaryNode parent) {
        this(parent, Request.requestFor(parent), parent.lhs(), parent.rhs());
    }

    public RuntimeNode setRequest(Request request) {
        if (this.request == request) {
            return this;
        }
        return new RuntimeNode(this, request, this.args);
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return this.request.getReturnType();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterRuntimeNode(this)) {
            return visitor.leaveRuntimeNode(setArgs(Node.accept(visitor, this.args)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("ScriptRuntime.");
        sb.append(this.request);
        sb.append('(');
        boolean first = true;
        for (Node arg : this.args) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            arg.toString(sb, printType);
        }
        sb.append(')');
    }

    public List<Expression> getArgs() {
        return Collections.unmodifiableList(this.args);
    }

    public RuntimeNode setArgs(List<Expression> args) {
        if (this.args == args) {
            return this;
        }
        return new RuntimeNode(this, this.request, args);
    }

    public Request getRequest() {
        return this.request;
    }

    public boolean isPrimitive() {
        for (Expression arg : this.args) {
            if (arg.getType().isObject()) {
                return false;
            }
        }
        return true;
    }
}
