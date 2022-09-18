package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.BaseNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BaseNode.class */
public abstract class BaseNode extends Expression implements FunctionCall, Optimistic {
    private static final long serialVersionUID = 1;
    protected final Expression base;
    private final boolean isFunction;
    protected final Type type;
    protected final int programPoint;

    public abstract BaseNode setIsFunction();

    public BaseNode(long token, int finish, Expression base, boolean isFunction) {
        super(token, base.getStart(), finish);
        this.base = base;
        this.isFunction = isFunction;
        this.type = null;
        this.programPoint = -1;
    }

    public BaseNode(BaseNode baseNode, Expression base, boolean isFunction, Type callSiteType, int programPoint) {
        super(baseNode);
        this.base = base;
        this.isFunction = isFunction;
        this.type = callSiteType;
        this.programPoint = programPoint;
    }

    public Expression getBase() {
        return this.base;
    }

    @Override // jdk.nashorn.internal.p001ir.FunctionCall
    public boolean isFunction() {
        return this.isFunction;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return this.type == null ? getMostPessimisticType() : this.type;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        return Type.INT;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return Type.OBJECT;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public boolean canBeOptimistic() {
        return true;
    }

    public boolean isIndex() {
        return isTokenType(TokenType.LBRACKET);
    }
}
