package jdk.nashorn.internal.p001ir;

import java.util.Objects;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.JumpToInlinedFinally */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/JumpToInlinedFinally.class */
public final class JumpToInlinedFinally extends JumpStatement {
    private static final long serialVersionUID = 1;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JumpToInlinedFinally.class.desiredAssertionStatus();
    }

    public JumpToInlinedFinally(String labelName) {
        super(-1, 0L, 0, (String) Objects.requireNonNull(labelName));
    }

    private JumpToInlinedFinally(JumpToInlinedFinally breakNode, LocalVariableConversion conversion) {
        super(breakNode, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterJumpToInlinedFinally(this)) {
            return visitor.leaveJumpToInlinedFinally(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    JumpStatement createNewJumpStatement(LocalVariableConversion conversion) {
        return new JumpToInlinedFinally(this, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    String getStatementName() {
        return ":jumpToInlinedFinally";
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public Block getTarget(LexicalContext lc) {
        return lc.getInlinedFinally(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public TryNode getPopScopeLimit(LexicalContext lc) {
        return lc.getTryNodeForInlinedFinally(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    Label getTargetLabel(BreakableNode target) {
        if ($assertionsDisabled || target != null) {
            return ((Block) target).getEntryLabel();
        }
        throw new AssertionError();
    }
}
