package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.LabelNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LabelNode.class */
public final class LabelNode extends LexicalContextStatement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final String labelName;
    private final Block body;
    private final LocalVariableConversion localVariableConversion;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public /* bridge */ /* synthetic */ Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public LabelNode(int lineNumber, long token, int finish, String labelName, Block body) {
        super(lineNumber, token, finish);
        this.labelName = labelName;
        this.body = body;
        this.localVariableConversion = null;
    }

    private LabelNode(LabelNode labelNode, String labelName, Block body, LocalVariableConversion localVariableConversion) {
        super(labelNode);
        this.labelName = labelName;
        this.body = body;
        this.localVariableConversion = localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterLabelNode(this)) {
            return visitor.leaveLabelNode(setBody(lc, (Block) this.body.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(this.labelName).append(':');
    }

    public Block getBody() {
        return this.body;
    }

    public LabelNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return (LabelNode) Node.replaceInLexicalContext(lc, this, new LabelNode(this, this.labelName, body, this.localVariableConversion));
    }

    public String getLabelName() {
        return this.labelName;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LabelNode setLocalVariableConversion(LexicalContext lc, LocalVariableConversion localVariableConversion) {
        if (this.localVariableConversion == localVariableConversion) {
            return this;
        }
        return (LabelNode) Node.replaceInLexicalContext(lc, this, new LabelNode(this, this.labelName, this.body, localVariableConversion));
    }
}
