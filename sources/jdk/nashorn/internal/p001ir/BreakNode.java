package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.BreakNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BreakNode.class */
public final class BreakNode extends JumpStatement {
    private static final long serialVersionUID = 1;

    public BreakNode(int lineNumber, long token, int finish, String labelName) {
        super(lineNumber, token, finish, labelName);
    }

    private BreakNode(BreakNode breakNode, LocalVariableConversion conversion) {
        super(breakNode, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBreakNode(this)) {
            return visitor.leaveBreakNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    JumpStatement createNewJumpStatement(LocalVariableConversion conversion) {
        return new BreakNode(this, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    String getStatementName() {
        return "break";
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public BreakableNode getTarget(LexicalContext lc) {
        return lc.getBreakable(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    Label getTargetLabel(BreakableNode target) {
        return target.getBreakLabel();
    }
}
