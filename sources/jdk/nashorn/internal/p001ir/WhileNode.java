package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.WhileNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/WhileNode.class */
public final class WhileNode extends LoopNode {
    private static final long serialVersionUID = 1;
    private final boolean isDoWhile;

    public WhileNode(int lineNumber, long token, int finish, boolean isDoWhile) {
        super(lineNumber, token, finish, (Block) null, false);
        this.isDoWhile = isDoWhile;
    }

    private WhileNode(WhileNode whileNode, JoinPredecessorExpression test, Block body, boolean controlFlowEscapes, LocalVariableConversion conversion) {
        super(whileNode, test, body, controlFlowEscapes, conversion);
        this.isDoWhile = whileNode.isDoWhile;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode, jdk.nashorn.internal.p001ir.Node, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lc) {
        return (Node) Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, this.controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.Statement
    public boolean hasGoto() {
        return this.test == null;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterWhileNode(this)) {
            if (isDoWhile()) {
                return visitor.leaveWhileNode(setBody(lc, (Block) this.body.accept(visitor)).setTest(lc, (JoinPredecessorExpression) this.test.accept(visitor)));
            }
            return visitor.leaveWhileNode(setTest(lc, (JoinPredecessorExpression) this.test.accept(visitor)).setBody(lc, (Block) this.body.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public WhileNode setTest(LexicalContext lc, JoinPredecessorExpression test) {
        if (this.test == test) {
            return this;
        }
        return (WhileNode) Node.replaceInLexicalContext(lc, this, new WhileNode(this, test, this.body, this.controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public Block getBody() {
        return this.body;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public WhileNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return (WhileNode) Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, body, this.controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public WhileNode setControlFlowEscapes(LexicalContext lc, boolean controlFlowEscapes) {
        if (this.controlFlowEscapes == controlFlowEscapes) {
            return this;
        }
        return (WhileNode) Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, controlFlowEscapes, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement
    JoinPredecessor setLocalVariableConversionChanged(LexicalContext lc, LocalVariableConversion conversion) {
        return (JoinPredecessor) Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, this.controlFlowEscapes, conversion));
    }

    public boolean isDoWhile() {
        return this.isDoWhile;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("while (");
        this.test.toString(sb, printType);
        sb.append(')');
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean mustEnter() {
        return isDoWhile() || this.test == null;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean hasPerIterationScope() {
        return false;
    }
}
