package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.IdentNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/IdentNode.class */
public final class IdentNode extends Expression implements PropertyKey, FunctionCall, Optimistic, JoinPredecessor {
    private static final long serialVersionUID = 1;
    private static final int PROPERTY_NAME = 1;
    private static final int INITIALIZED_HERE = 2;
    private static final int FUNCTION = 4;
    private static final int FUTURESTRICT_NAME = 8;
    private static final int IS_DECLARED_HERE = 16;
    private static final int IS_DEAD = 32;
    private final String name;
    private final Type type;
    private final int flags;
    private final int programPoint;
    private final LocalVariableConversion conversion;
    private Symbol symbol;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !IdentNode.class.desiredAssertionStatus();
    }

    public IdentNode(long token, int finish, String name) {
        super(token, finish);
        this.name = name;
        this.type = null;
        this.flags = 0;
        this.programPoint = -1;
        this.conversion = null;
    }

    private IdentNode(IdentNode identNode, String name, Type type, int flags, int programPoint, LocalVariableConversion conversion) {
        super(identNode);
        this.name = name;
        this.type = type;
        this.flags = flags;
        this.programPoint = programPoint;
        this.conversion = conversion;
        this.symbol = identNode.symbol;
    }

    public IdentNode(IdentNode identNode) {
        super(identNode);
        this.name = identNode.getName();
        this.type = identNode.type;
        this.flags = identNode.flags;
        this.conversion = identNode.conversion;
        this.programPoint = -1;
        this.symbol = identNode.symbol;
    }

    public static IdentNode createInternalIdentifier(Symbol symbol) {
        return new IdentNode(Token.toDesc(TokenType.IDENT, 0, 0), 0, symbol.getName()).setSymbol(symbol);
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        if (this.type != null) {
            return this.type;
        }
        if (this.symbol != null && this.symbol.isScope()) {
            return Type.OBJECT;
        }
        return Type.UNDEFINED;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterIdentNode(this)) {
            return visitor.leaveIdentNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        if (printType) {
            optimisticTypeToString(sb, this.symbol == null || !this.symbol.hasSlot());
        }
        sb.append(this.name);
    }

    public String getName() {
        return this.name;
    }

    @Override // jdk.nashorn.internal.p001ir.PropertyKey
    public String getPropertyName() {
        return getName();
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public boolean isLocal() {
        return !getSymbol().isScope();
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public IdentNode setSymbol(Symbol symbol) {
        if (this.symbol == symbol) {
            return this;
        }
        IdentNode newIdent = (IdentNode) clone();
        newIdent.symbol = symbol;
        return newIdent;
    }

    public boolean isPropertyName() {
        return (this.flags & 1) == 1;
    }

    public IdentNode setIsPropertyName() {
        if (isPropertyName()) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags | 1, this.programPoint, this.conversion);
    }

    public boolean isFutureStrictName() {
        return (this.flags & 8) == 8;
    }

    public IdentNode setIsFutureStrictName() {
        if (isFutureStrictName()) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags | 8, this.programPoint, this.conversion);
    }

    public boolean isInitializedHere() {
        return (this.flags & 2) == 2;
    }

    public IdentNode setIsInitializedHere() {
        if (isInitializedHere()) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags | 2, this.programPoint, this.conversion);
    }

    public boolean isDead() {
        return (this.flags & 32) != 0;
    }

    public IdentNode markDead() {
        return new IdentNode(this, this.name, this.type, this.flags | 32, this.programPoint, this.conversion);
    }

    public boolean isDeclaredHere() {
        return (this.flags & 16) != 0;
    }

    public IdentNode setIsDeclaredHere() {
        if (isDeclaredHere()) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags | 16, this.programPoint, this.conversion);
    }

    public boolean isCompileTimePropertyName() {
        return this.name.equals(CompilerConstants.__DIR__.symbolName()) || this.name.equals(CompilerConstants.__FILE__.symbolName()) || this.name.equals(CompilerConstants.__LINE__.symbolName());
    }

    @Override // jdk.nashorn.internal.p001ir.FunctionCall
    public boolean isFunction() {
        return (this.flags & 4) == 4;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public IdentNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new IdentNode(this, this.name, type, this.flags, this.programPoint, this.conversion);
    }

    public IdentNode setIsFunction() {
        if (isFunction()) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags | 4, this.programPoint, this.conversion);
    }

    public IdentNode setIsNotFunction() {
        if (!isFunction()) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags & (-5), this.programPoint, this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Optimistic setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags, programPoint, this.conversion);
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

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (this.conversion == conversion) {
            return this;
        }
        return new IdentNode(this, this.name, this.type, this.flags, this.programPoint, conversion);
    }

    public boolean isInternal() {
        if ($assertionsDisabled || this.name != null) {
            return this.name.charAt(0) == ':';
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
