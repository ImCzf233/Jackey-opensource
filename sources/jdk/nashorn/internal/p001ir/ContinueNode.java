package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.ContinueNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/ContinueNode.class */
public class ContinueNode extends JumpStatement {
    private static final long serialVersionUID = 1;

    public ContinueNode(int lineNumber, long token, int finish, String labelName) {
        super(lineNumber, token, finish, labelName);
    }

    private ContinueNode(ContinueNode continueNode, LocalVariableConversion conversion) {
        super(continueNode, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterContinueNode(this)) {
            return visitor.leaveContinueNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    JumpStatement createNewJumpStatement(LocalVariableConversion conversion) {
        return new ContinueNode(this, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    String getStatementName() {
        return "continue";
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public BreakableNode getTarget(LexicalContext lc) {
        return lc.getContinueTo(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    Label getTargetLabel(BreakableNode target) {
        return ((LoopNode) target).getContinueLabel();
    }
}
