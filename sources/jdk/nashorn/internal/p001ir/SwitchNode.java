package jdk.nashorn.internal.p001ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.SwitchNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/SwitchNode.class */
public final class SwitchNode extends BreakableStatement {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final List<CaseNode> cases;
    private final int defaultCaseIndex;
    private final boolean uniqueInteger;
    private final Symbol tag;

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public /* bridge */ /* synthetic */ LocalVariableConversion getLocalVariableConversion() {
        return super.getLocalVariableConversion();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public /* bridge */ /* synthetic */ JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return super.setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.Labels
    public /* bridge */ /* synthetic */ List getLabels() {
        return super.getLabels();
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

    public SwitchNode(int lineNumber, long token, int finish, Expression expression, List<CaseNode> cases, CaseNode defaultCase) {
        super(lineNumber, token, finish, new Label("switch_break"));
        this.expression = expression;
        this.cases = cases;
        this.defaultCaseIndex = defaultCase == null ? -1 : cases.indexOf(defaultCase);
        this.uniqueInteger = false;
        this.tag = null;
    }

    private SwitchNode(SwitchNode switchNode, Expression expression, List<CaseNode> cases, int defaultCaseIndex, LocalVariableConversion conversion, boolean uniqueInteger, Symbol tag) {
        super(switchNode, conversion);
        this.expression = expression;
        this.cases = cases;
        this.defaultCaseIndex = defaultCaseIndex;
        this.tag = tag;
        this.uniqueInteger = uniqueInteger;
    }

    @Override // jdk.nashorn.internal.p001ir.Node, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lc) {
        List<CaseNode> newCases = new ArrayList<>();
        for (CaseNode caseNode : this.cases) {
            newCases.add(new CaseNode(caseNode, caseNode.getTest(), caseNode.getBody(), caseNode.getLocalVariableConversion()));
        }
        return (Node) Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, newCases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        if (!this.cases.isEmpty() && this.defaultCaseIndex != -1) {
            for (CaseNode caseNode : this.cases) {
                if (!caseNode.isTerminal()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterSwitchNode(this)) {
            return visitor.leaveSwitchNode(setExpression(lc, (Expression) this.expression.accept(visitor)).setCases(lc, Node.accept(visitor, this.cases), this.defaultCaseIndex));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("switch (");
        this.expression.toString(sb, printType);
        sb.append(')');
    }

    public CaseNode getDefaultCase() {
        if (this.defaultCaseIndex == -1) {
            return null;
        }
        return this.cases.get(this.defaultCaseIndex);
    }

    public List<CaseNode> getCases() {
        return Collections.unmodifiableList(this.cases);
    }

    public SwitchNode setCases(LexicalContext lc, List<CaseNode> cases) {
        return setCases(lc, cases, this.defaultCaseIndex);
    }

    private SwitchNode setCases(LexicalContext lc, List<CaseNode> cases, int defaultCaseIndex) {
        if (this.cases == cases) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, cases, defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
    }

    public SwitchNode setCases(LexicalContext lc, List<CaseNode> cases, CaseNode defaultCase) {
        return setCases(lc, cases, defaultCase == null ? -1 : cases.indexOf(defaultCase));
    }

    public Expression getExpression() {
        return this.expression;
    }

    public SwitchNode setExpression(LexicalContext lc, Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lc, this, new SwitchNode(this, expression, this.cases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
    }

    public Symbol getTag() {
        return this.tag;
    }

    public SwitchNode setTag(LexicalContext lc, Symbol tag) {
        if (this.tag == tag) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, tag));
    }

    public boolean isUniqueInteger() {
        return this.uniqueInteger;
    }

    public SwitchNode setUniqueInteger(LexicalContext lc, boolean uniqueInteger) {
        if (this.uniqueInteger == uniqueInteger) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, this.conversion, uniqueInteger, this.tag));
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement
    JoinPredecessor setLocalVariableConversionChanged(LexicalContext lc, LocalVariableConversion conversion) {
        return (JoinPredecessor) Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, conversion, this.uniqueInteger, this.tag));
    }
}
