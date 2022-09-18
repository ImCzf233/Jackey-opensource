package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.ForNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/ForNode.class */
public final class ForNode extends LoopNode {
    private static final long serialVersionUID = 1;
    private final Expression init;
    private final JoinPredecessorExpression modify;
    private final Symbol iterator;
    public static final int IS_FOR_IN = 1;
    public static final int IS_FOR_EACH = 2;
    public static final int PER_ITERATION_SCOPE = 4;
    private final int flags;

    public ForNode(int lineNumber, long token, int finish, Block body, int flags) {
        super(lineNumber, token, finish, body, false);
        this.flags = flags;
        this.init = null;
        this.modify = null;
        this.iterator = null;
    }

    private ForNode(ForNode forNode, Expression init, JoinPredecessorExpression test, Block body, JoinPredecessorExpression modify, int flags, boolean controlFlowEscapes, LocalVariableConversion conversion, Symbol iterator) {
        super(forNode, test, body, controlFlowEscapes, conversion);
        this.init = init;
        this.modify = modify;
        this.flags = flags;
        this.iterator = iterator;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode, jdk.nashorn.internal.p001ir.Node, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lc) {
        return (Node) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterForNode(this)) {
            return visitor.leaveForNode(setInit(lc, this.init == null ? null : (Expression) this.init.accept(visitor)).setTest(lc, this.test == null ? null : (JoinPredecessorExpression) this.test.accept(visitor)).setModify(lc, this.modify == null ? null : (JoinPredecessorExpression) this.modify.accept(visitor)).setBody(lc, (Block) this.body.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printTypes) {
        sb.append("for");
        LocalVariableConversion.toString(this.conversion, sb).append(' ');
        if (isForIn()) {
            this.init.toString(sb, printTypes);
            sb.append(" in ");
            this.modify.toString(sb, printTypes);
        } else {
            if (this.init != null) {
                this.init.toString(sb, printTypes);
            }
            sb.append("; ");
            if (this.test != null) {
                this.test.toString(sb, printTypes);
            }
            sb.append("; ");
            if (this.modify != null) {
                this.modify.toString(sb, printTypes);
            }
        }
        sb.append(')');
    }

    @Override // jdk.nashorn.internal.p001ir.Statement
    public boolean hasGoto() {
        return !isForIn() && this.test == null;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean mustEnter() {
        return !isForIn() && this.test == null;
    }

    public Expression getInit() {
        return this.init;
    }

    public ForNode setInit(LexicalContext lc, Expression init) {
        if (this.init == init) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    public boolean isForIn() {
        return (this.flags & 1) != 0;
    }

    public ForNode setIsForIn(LexicalContext lc) {
        return setFlags(lc, this.flags | 1);
    }

    public boolean isForEach() {
        return (this.flags & 2) != 0;
    }

    public ForNode setIsForEach(LexicalContext lc) {
        return setFlags(lc, this.flags | 2);
    }

    public Symbol getIterator() {
        return this.iterator;
    }

    public ForNode setIterator(LexicalContext lc, Symbol iterator) {
        if (this.iterator == iterator) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, iterator));
    }

    public JoinPredecessorExpression getModify() {
        return this.modify;
    }

    public ForNode setModify(LexicalContext lc, JoinPredecessorExpression modify) {
        if (this.modify == modify) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public ForNode setTest(LexicalContext lc, JoinPredecessorExpression test) {
        if (this.test == test) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public Block getBody() {
        return this.body;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public ForNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public ForNode setControlFlowEscapes(LexicalContext lc, boolean controlFlowEscapes) {
        if (this.controlFlowEscapes == controlFlowEscapes) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, controlFlowEscapes, this.conversion, this.iterator));
    }

    private ForNode setFlags(LexicalContext lc, int flags) {
        if (this.flags == flags) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement
    JoinPredecessor setLocalVariableConversionChanged(LexicalContext lc, LocalVariableConversion conversion) {
        return (JoinPredecessor) Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean hasPerIterationScope() {
        return (this.flags & 4) != 0;
    }

    public ForNode setPerIterationScope(LexicalContext lc) {
        return setFlags(lc, this.flags | 4);
    }
}
