package jdk.nashorn.internal.codegen;

import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Optimistic;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ProgramPoints.class */
class ProgramPoints extends SimpleNodeVisitor {
    private final IntDeque nextProgramPoint = new IntDeque();
    private final Set<Node> noProgramPoint = new HashSet();

    private int next() {
        int next = this.nextProgramPoint.getAndIncrement();
        if (next > 2097151) {
            throw new AssertionError("Function has more than 2097151 program points");
        }
        return next;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        this.nextProgramPoint.push(1);
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        this.nextProgramPoint.pop();
        return functionNode;
    }

    private Expression setProgramPoint(Optimistic optimistic) {
        if (this.noProgramPoint.contains(optimistic)) {
            return (Expression) optimistic;
        }
        return (Expression) (optimistic.canBeOptimistic() ? optimistic.setProgramPoint(next()) : optimistic);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        this.noProgramPoint.add(varNode.getName());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIdentNode(IdentNode identNode) {
        if (identNode.isInternal()) {
            this.noProgramPoint.add(identNode);
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIdentNode(IdentNode identNode) {
        if (identNode.isPropertyName()) {
            return identNode;
        }
        return setProgramPoint(identNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCallNode(CallNode callNode) {
        return setProgramPoint(callNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveAccessNode(AccessNode accessNode) {
        return setProgramPoint(accessNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIndexNode(IndexNode indexNode) {
        return setProgramPoint(indexNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBinaryNode(BinaryNode binaryNode) {
        return setProgramPoint(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveUnaryNode(UnaryNode unaryNode) {
        return setProgramPoint(unaryNode);
    }
}
