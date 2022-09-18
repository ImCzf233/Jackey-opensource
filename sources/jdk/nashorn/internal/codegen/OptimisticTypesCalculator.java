package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.LoopNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Optimistic;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/OptimisticTypesCalculator.class */
final class OptimisticTypesCalculator extends SimpleNodeVisitor {
    final Compiler compiler;
    final Deque<BitSet> neverOptimistic = new ArrayDeque();
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !OptimisticTypesCalculator.class.desiredAssertionStatus();
    }

    public OptimisticTypesCalculator(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterAccessNode(AccessNode accessNode) {
        tagNeverOptimistic(accessNode.getBase());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterPropertyNode(PropertyNode propertyNode) {
        if (propertyNode.getKeyName().equals(ScriptObject.PROTO_PROPERTY_NAME)) {
            tagNeverOptimistic(propertyNode.getValue());
        }
        return super.enterPropertyNode(propertyNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBinaryNode(BinaryNode binaryNode) {
        if (!binaryNode.isAssignment()) {
            if (binaryNode.isTokenType(TokenType.INSTANCEOF)) {
                tagNeverOptimistic(binaryNode.lhs());
                tagNeverOptimistic(binaryNode.rhs());
                return true;
            }
            return true;
        }
        Expression lhs = binaryNode.lhs();
        if (!binaryNode.isSelfModifying()) {
            tagNeverOptimistic(lhs);
        }
        if (lhs instanceof IdentNode) {
            Symbol symbol = ((IdentNode) lhs).getSymbol();
            if (symbol.isInternal() && !binaryNode.rhs().isSelfModifying()) {
                tagNeverOptimistic(binaryNode.rhs());
                return true;
            }
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCallNode(CallNode callNode) {
        tagNeverOptimistic(callNode.getFunction());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCatchNode(CatchNode catchNode) {
        tagNeverOptimistic(catchNode.getExceptionCondition());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        Expression expr = expressionStatement.getExpression();
        if (!expr.isSelfModifying()) {
            tagNeverOptimistic(expr);
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterForNode(ForNode forNode) {
        if (forNode.isForIn()) {
            tagNeverOptimistic(forNode.getModify());
            return true;
        }
        tagNeverOptimisticLoopTest(forNode);
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (!this.neverOptimistic.isEmpty() && this.compiler.isOnDemandCompilation()) {
            return false;
        }
        this.neverOptimistic.push(new BitSet());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIfNode(IfNode ifNode) {
        tagNeverOptimistic(ifNode.getTest());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIndexNode(IndexNode indexNode) {
        tagNeverOptimistic(indexNode.getBase());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTernaryNode(TernaryNode ternaryNode) {
        tagNeverOptimistic(ternaryNode.getTest());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterUnaryNode(UnaryNode unaryNode) {
        if (unaryNode.isTokenType(TokenType.NOT) || unaryNode.isTokenType(TokenType.NEW)) {
            tagNeverOptimistic(unaryNode.getExpression());
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        tagNeverOptimistic(varNode.getName());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWhileNode(WhileNode whileNode) {
        tagNeverOptimisticLoopTest(whileNode);
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveDefault(Node node) {
        if (node instanceof Optimistic) {
            return leaveOptimistic((Optimistic) node);
        }
        return node;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        this.neverOptimistic.pop();
        return functionNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIdentNode(IdentNode identNode) {
        Symbol symbol = identNode.getSymbol();
        if (symbol == null) {
            if (!$assertionsDisabled && !identNode.isPropertyName()) {
                throw new AssertionError();
            }
            return identNode;
        } else if (symbol.isBytecodeLocal()) {
            return identNode;
        } else {
            if (symbol.isParam() && this.f247lc.getCurrentFunction().isVarArg()) {
                return identNode.setType(identNode.getMostPessimisticType());
            }
            if (!$assertionsDisabled && !symbol.isScope()) {
                throw new AssertionError();
            }
            return leaveOptimistic(identNode);
        }
    }

    private Expression leaveOptimistic(Optimistic opt) {
        int pp = opt.getProgramPoint();
        if (UnwarrantedOptimismException.isValid(pp) && !this.neverOptimistic.peek().get(pp)) {
            return (Expression) opt.setType(this.compiler.getOptimisticType(opt));
        }
        return (Expression) opt;
    }

    private void tagNeverOptimistic(Expression expr) {
        if (expr instanceof Optimistic) {
            int pp = ((Optimistic) expr).getProgramPoint();
            if (UnwarrantedOptimismException.isValid(pp)) {
                this.neverOptimistic.peek().set(pp);
            }
        }
    }

    private void tagNeverOptimisticLoopTest(LoopNode loopNode) {
        JoinPredecessorExpression test = loopNode.getTest();
        if (test != null) {
            tagNeverOptimistic(test.getExpression());
        }
    }
}
