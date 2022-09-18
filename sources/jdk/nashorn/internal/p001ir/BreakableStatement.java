package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.BreakableStatement */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BreakableStatement.class */
public abstract class BreakableStatement extends LexicalContextStatement implements BreakableNode {
    private static final long serialVersionUID = 1;
    protected final Label breakLabel;
    final LocalVariableConversion conversion;

    abstract JoinPredecessor setLocalVariableConversionChanged(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion);

    public BreakableStatement(int lineNumber, long token, int finish, Label breakLabel) {
        super(lineNumber, token, finish);
        this.breakLabel = breakLabel;
        this.conversion = null;
    }

    public BreakableStatement(BreakableStatement breakableNode, LocalVariableConversion conversion) {
        super(breakableNode);
        this.breakLabel = new Label(breakableNode.getBreakLabel());
        this.conversion = conversion;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public boolean isBreakableWithoutLabel() {
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public Label getBreakLabel() {
        return this.breakLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.Labels
    public List<Label> getLabels() {
        return Collections.unmodifiableList(Collections.singletonList(this.breakLabel));
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (this.conversion == conversion) {
            return this;
        }
        return setLocalVariableConversionChanged(lc, conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
