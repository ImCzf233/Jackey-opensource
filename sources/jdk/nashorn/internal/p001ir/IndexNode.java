package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.IndexNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/IndexNode.class */
public final class IndexNode extends BaseNode {
    private static final long serialVersionUID = 1;
    private final Expression index;

    public IndexNode(long token, int finish, Expression base, Expression index) {
        super(token, finish, base, false);
        this.index = index;
    }

    private IndexNode(IndexNode indexNode, Expression base, Expression index, boolean isFunction, Type type, int programPoint) {
        super(indexNode, base, isFunction, type, programPoint);
        this.index = index;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterIndexNode(this)) {
            return visitor.leaveIndexNode(setBase((Expression) this.base.accept(visitor)).setIndex((Expression) this.index.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        boolean needsParen = tokenType().needsParens(this.base.tokenType(), true);
        if (needsParen) {
            sb.append('(');
        }
        if (printType) {
            optimisticTypeToString(sb);
        }
        this.base.toString(sb, printType);
        if (needsParen) {
            sb.append(')');
        }
        sb.append('[');
        this.index.toString(sb, printType);
        sb.append(']');
    }

    public Expression getIndex() {
        return this.index;
    }

    private IndexNode setBase(Expression base) {
        if (this.base == base) {
            return this;
        }
        return new IndexNode(this, base, this.index, isFunction(), this.type, this.programPoint);
    }

    public IndexNode setIndex(Expression index) {
        if (this.index == index) {
            return this;
        }
        return new IndexNode(this, this.base, index, isFunction(), this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public IndexNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, isFunction(), type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.BaseNode
    public IndexNode setIsFunction() {
        if (isFunction()) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, true, this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public IndexNode setProgramPoint(int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, isFunction(), this.type, programPoint);
    }
}
