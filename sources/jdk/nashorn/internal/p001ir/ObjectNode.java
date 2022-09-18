package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.ObjectNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/ObjectNode.class */
public final class ObjectNode extends Expression implements LexicalContextNode, Splittable {
    private static final long serialVersionUID = 1;
    private final List<PropertyNode> elements;
    private final List<Splittable.SplitRange> splitRanges;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ObjectNode.class.desiredAssertionStatus();
    }

    public ObjectNode(long token, int finish, List<PropertyNode> elements) {
        super(token, finish);
        this.elements = elements;
        this.splitRanges = null;
        if ($assertionsDisabled || (elements instanceof RandomAccess)) {
            return;
        }
        throw new AssertionError("Splitting requires random access lists");
    }

    private ObjectNode(ObjectNode objectNode, List<PropertyNode> elements, List<Splittable.SplitRange> splitRanges) {
        super(objectNode);
        this.elements = elements;
        this.splitRanges = splitRanges;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return LexicalContextNode.Acceptor.accept(this, visitor);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterObjectNode(this)) {
            return visitor.leaveObjectNode(setElements(lc, Node.accept(visitor, this.elements)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return Type.OBJECT;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append('{');
        if (!this.elements.isEmpty()) {
            sb.append(' ');
            boolean first = true;
            for (Node element : this.elements) {
                if (!first) {
                    sb.append(", ");
                }
                first = false;
                element.toString(sb, printType);
            }
            sb.append(' ');
        }
        sb.append('}');
    }

    public List<PropertyNode> getElements() {
        return Collections.unmodifiableList(this.elements);
    }

    private ObjectNode setElements(LexicalContext lc, List<PropertyNode> elements) {
        if (this.elements == elements) {
            return this;
        }
        return (ObjectNode) Node.replaceInLexicalContext(lc, this, new ObjectNode(this, elements, this.splitRanges));
    }

    public ObjectNode setSplitRanges(LexicalContext lc, List<Splittable.SplitRange> splitRanges) {
        if (this.splitRanges == splitRanges) {
            return this;
        }
        return (ObjectNode) Node.replaceInLexicalContext(lc, this, new ObjectNode(this, this.elements, splitRanges));
    }

    @Override // jdk.nashorn.internal.p001ir.Splittable
    public List<Splittable.SplitRange> getSplitRanges() {
        if (this.splitRanges == null) {
            return null;
        }
        return Collections.unmodifiableList(this.splitRanges);
    }
}
