package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.AccessNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/AccessNode.class */
public final class AccessNode extends BaseNode {
    private static final long serialVersionUID = 1;
    private final String property;

    public AccessNode(long token, int finish, Expression base, String property) {
        super(token, finish, base, false);
        this.property = property;
    }

    private AccessNode(AccessNode accessNode, Expression base, String property, boolean isFunction, Type type, int id) {
        super(accessNode, base, isFunction, type, id);
        this.property = property;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterAccessNode(this)) {
            return visitor.leaveAccessNode(setBase((Expression) this.base.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        boolean needsParen = tokenType().needsParens(getBase().tokenType(), true);
        if (printType) {
            optimisticTypeToString(sb);
        }
        if (needsParen) {
            sb.append('(');
        }
        this.base.toString(sb, printType);
        if (needsParen) {
            sb.append(')');
        }
        sb.append('.');
        sb.append(this.property);
    }

    public String getProperty() {
        return this.property;
    }

    private AccessNode setBase(Expression base) {
        if (this.base == base) {
            return this;
        }
        return new AccessNode(this, base, this.property, isFunction(), this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public AccessNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, isFunction(), type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public AccessNode setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, isFunction(), this.type, programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.BaseNode
    public AccessNode setIsFunction() {
        if (isFunction()) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, true, this.type, this.programPoint);
    }
}
