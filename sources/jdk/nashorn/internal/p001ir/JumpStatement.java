package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;

/* renamed from: jdk.nashorn.internal.ir.JumpStatement */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/JumpStatement.class */
public abstract class JumpStatement extends Statement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final String labelName;
    private final LocalVariableConversion conversion;

    abstract String getStatementName();

    public abstract BreakableNode getTarget(LexicalContext lexicalContext);

    abstract Label getTargetLabel(BreakableNode breakableNode);

    abstract JumpStatement createNewJumpStatement(LocalVariableConversion localVariableConversion);

    public JumpStatement(int lineNumber, long token, int finish, String labelName) {
        super(lineNumber, token, finish);
        this.labelName = labelName;
        this.conversion = null;
    }

    public JumpStatement(JumpStatement jumpStatement, LocalVariableConversion conversion) {
        super(jumpStatement);
        this.labelName = jumpStatement.labelName;
        this.conversion = conversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement
    public boolean hasGoto() {
        return true;
    }

    public String getLabelName() {
        return this.labelName;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(getStatementName());
        if (this.labelName != null) {
            sb.append(' ').append(this.labelName);
        }
    }

    public Label getTargetLabel(LexicalContext lc) {
        return getTargetLabel(getTarget(lc));
    }

    public LexicalContextNode getPopScopeLimit(LexicalContext lc) {
        return getTarget(lc);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JumpStatement setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (this.conversion == conversion) {
            return this;
        }
        return createNewJumpStatement(conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
