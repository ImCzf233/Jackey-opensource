package jdk.nashorn.internal.codegen;

import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.LocalVariableConversion;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.parser.TokenType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/BranchOptimizer.class */
public final class BranchOptimizer {
    private final CodeGenerator codegen;
    private final MethodEmitter method;

    public BranchOptimizer(CodeGenerator codegen, MethodEmitter method) {
        this.codegen = codegen;
        this.method = method;
    }

    public void execute(Expression node, Label label, boolean state) {
        branchOptimizer(node, label, state);
    }

    private void branchOptimizer(UnaryNode unaryNode, Label label, boolean state) {
        if (unaryNode.isTokenType(TokenType.NOT)) {
            branchOptimizer(unaryNode.getExpression(), label, !state);
        } else {
            loadTestAndJump(unaryNode, label, state);
        }
    }

    private void branchOptimizer(BinaryNode binaryNode, Label label, boolean state) {
        Expression lhs = binaryNode.lhs();
        Expression rhs = binaryNode.rhs();
        switch (binaryNode.tokenType()) {
            case AND:
                if (state) {
                    Label skip = new Label("skip");
                    optimizeLogicalOperand(lhs, skip, false, false);
                    optimizeLogicalOperand(rhs, label, true, true);
                    this.method.label(skip);
                    return;
                }
                optimizeLogicalOperand(lhs, label, false, false);
                optimizeLogicalOperand(rhs, label, false, true);
                return;
            case OR:
                if (state) {
                    optimizeLogicalOperand(lhs, label, true, false);
                    optimizeLogicalOperand(rhs, label, true, true);
                    return;
                }
                Label skip2 = new Label("skip");
                optimizeLogicalOperand(lhs, skip2, true, false);
                optimizeLogicalOperand(rhs, label, false, true);
                this.method.label(skip2);
                return;
            case EQ:
            case EQ_STRICT:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(state ? Condition.EQ : Condition.NE, true, label);
                return;
            case NE:
            case NE_STRICT:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(state ? Condition.NE : Condition.EQ, true, label);
                return;
            case GE:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(state ? Condition.GE : Condition.LT, false, label);
                return;
            case GT:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(state ? Condition.GT : Condition.LE, false, label);
                return;
            case LE:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(state ? Condition.LE : Condition.GT, true, label);
                return;
            case LT:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(state ? Condition.LT : Condition.GE, true, label);
                return;
            default:
                loadTestAndJump(binaryNode, label, state);
                return;
        }
    }

    private void optimizeLogicalOperand(Expression expr, Label label, boolean state, boolean isRhs) {
        JoinPredecessorExpression jpexpr = (JoinPredecessorExpression) expr;
        if (LocalVariableConversion.hasLiveConversion(jpexpr)) {
            Label after = new Label("after");
            branchOptimizer(jpexpr.getExpression(), after, !state);
            this.method.beforeJoinPoint(jpexpr);
            this.method._goto(label);
            this.method.label(after);
            if (isRhs) {
                this.method.beforeJoinPoint(jpexpr);
                return;
            }
            return;
        }
        branchOptimizer(jpexpr.getExpression(), label, state);
    }

    private void branchOptimizer(Expression node, Label label, boolean state) {
        if (node instanceof BinaryNode) {
            branchOptimizer((BinaryNode) node, label, state);
        } else if (node instanceof UnaryNode) {
            branchOptimizer((UnaryNode) node, label, state);
        } else {
            loadTestAndJump(node, label, state);
        }
    }

    private void loadTestAndJump(Expression node, Label label, boolean state) {
        this.codegen.loadExpressionAsBoolean(node);
        if (state) {
            this.method.ifne(label);
        } else {
            this.method.ifeq(label);
        }
    }
}
