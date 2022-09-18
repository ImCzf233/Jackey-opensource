package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.LoopNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LoopNode.class */
public abstract class LoopNode extends BreakableStatement {
    private static final long serialVersionUID = 1;
    protected final Label continueLabel;
    protected final JoinPredecessorExpression test;
    protected final Block body;
    protected final boolean controlFlowEscapes;

    @Override // jdk.nashorn.internal.p001ir.Node, jdk.nashorn.internal.p001ir.BreakableNode
    public abstract Node ensureUniqueLabels(LexicalContext lexicalContext);

    public abstract boolean mustEnter();

    public abstract Block getBody();

    public abstract LoopNode setBody(LexicalContext lexicalContext, Block block);

    public abstract LoopNode setTest(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression);

    public abstract LoopNode setControlFlowEscapes(LexicalContext lexicalContext, boolean z);

    public abstract boolean hasPerIterationScope();

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public /* bridge */ /* synthetic */ LocalVariableConversion getLocalVariableConversion() {
        return super.getLocalVariableConversion();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public /* bridge */ /* synthetic */ JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return super.setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.BreakableNode
    public /* bridge */ /* synthetic */ Label getBreakLabel() {
        return super.getBreakLabel();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.BreakableNode
    public /* bridge */ /* synthetic */ boolean isBreakableWithoutLabel() {
        return super.isBreakableWithoutLabel();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public /* bridge */ /* synthetic */ Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public LoopNode(int lineNumber, long token, int finish, Block body, boolean controlFlowEscapes) {
        super(lineNumber, token, finish, new Label("while_break"));
        this.continueLabel = new Label("while_continue");
        this.test = null;
        this.body = body;
        this.controlFlowEscapes = controlFlowEscapes;
    }

    public LoopNode(LoopNode loopNode, JoinPredecessorExpression test, Block body, boolean controlFlowEscapes, LocalVariableConversion conversion) {
        super(loopNode, conversion);
        this.continueLabel = new Label(loopNode.continueLabel);
        this.test = test;
        this.body = body;
        this.controlFlowEscapes = controlFlowEscapes;
    }

    public boolean controlFlowEscapes() {
        return this.controlFlowEscapes;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        if (mustEnter() && !this.controlFlowEscapes) {
            return this.body.isTerminal() || this.test == null;
        }
        return false;
    }

    public Label getContinueLabel() {
        return this.continueLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.Labels
    public List<Label> getLabels() {
        return Collections.unmodifiableList(Arrays.asList(this.breakLabel, this.continueLabel));
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public boolean isLoop() {
        return true;
    }

    public final JoinPredecessorExpression getTest() {
        return this.test;
    }
}
