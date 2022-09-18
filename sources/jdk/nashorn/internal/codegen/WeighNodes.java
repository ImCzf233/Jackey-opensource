package jdk.nashorn.internal.codegen;

import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.SplitNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/WeighNodes.class */
public final class WeighNodes extends NodeOperatorVisitor<LexicalContext> {
    static final long FUNCTION_WEIGHT = 40;
    static final long AASTORE_WEIGHT = 2;
    static final long ACCESS_WEIGHT = 4;
    static final long ADD_WEIGHT = 10;
    static final long BREAK_WEIGHT = 1;
    static final long CALL_WEIGHT = 10;
    static final long CATCH_WEIGHT = 10;
    static final long COMPARE_WEIGHT = 6;
    static final long CONTINUE_WEIGHT = 1;
    static final long IF_WEIGHT = 2;
    static final long LITERAL_WEIGHT = 10;
    static final long LOOP_WEIGHT = 4;
    static final long NEW_WEIGHT = 6;
    static final long FUNC_EXPR_WEIGHT = 20;
    static final long RETURN_WEIGHT = 2;
    static final long SPLIT_WEIGHT = 40;
    static final long SWITCH_WEIGHT = 8;
    static final long THROW_WEIGHT = 2;
    static final long VAR_WEIGHT = 40;
    static final long WITH_WEIGHT = 8;
    static final long OBJECT_WEIGHT = 16;
    static final long SETPROP_WEIGHT = 5;
    private long weight;
    private final Map<Node, Long> weightCache;
    private final FunctionNode topFunction;

    private WeighNodes(FunctionNode topFunction, Map<Node, Long> weightCache) {
        super(new LexicalContext());
        this.topFunction = topFunction;
        this.weightCache = weightCache;
    }

    public static long weigh(Node node) {
        return weigh(node, null);
    }

    public static long weigh(Node node, Map<Node, Long> weightCache) {
        WeighNodes weighNodes = new WeighNodes(node instanceof FunctionNode ? (FunctionNode) node : null, weightCache);
        node.accept(weighNodes);
        return weighNodes.weight;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveAccessNode(AccessNode accessNode) {
        this.weight += 4;
        return accessNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        if (this.weightCache != null && this.weightCache.containsKey(block)) {
            this.weight += this.weightCache.get(block).longValue();
            return false;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBreakNode(BreakNode breakNode) {
        this.weight++;
        return breakNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCallNode(CallNode callNode) {
        this.weight += 10;
        return callNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCatchNode(CatchNode catchNode) {
        this.weight += 10;
        return catchNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveContinueNode(ContinueNode continueNode) {
        this.weight++;
        return continueNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveExpressionStatement(ExpressionStatement expressionStatement) {
        return expressionStatement;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveForNode(ForNode forNode) {
        this.weight += 4;
        return forNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (functionNode == this.topFunction) {
            return true;
        }
        this.weight += FUNC_EXPR_WEIGHT;
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIdentNode(IdentNode identNode) {
        this.weight += 4;
        return identNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIfNode(IfNode ifNode) {
        this.weight += 2;
        return ifNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIndexNode(IndexNode indexNode) {
        this.weight += 4;
        return indexNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        this.weight++;
        return jumpToInlinedFinally;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLiteralNode(LiteralNode literalNode) {
        this.weight += 10;
        if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
            LiteralNode.ArrayLiteralNode arrayLiteralNode = (LiteralNode.ArrayLiteralNode) literalNode;
            Node[] value = arrayLiteralNode.getValue();
            int[] postsets = arrayLiteralNode.getPostsets();
            List<Splittable.SplitRange> units = arrayLiteralNode.getSplitRanges();
            if (units == null) {
                for (int postset : postsets) {
                    this.weight += 2;
                    Node element = value[postset];
                    if (element != null) {
                        element.accept(this);
                    }
                }
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterObjectNode(ObjectNode objectNode) {
        this.weight += OBJECT_WEIGHT;
        List<PropertyNode> properties = objectNode.getElements();
        boolean isSpillObject = properties.size() > CodeGenerator.OBJECT_SPILL_THRESHOLD;
        for (PropertyNode property : properties) {
            if (!LiteralNode.isConstant(property.getValue())) {
                this.weight += SETPROP_WEIGHT;
                property.getValue().accept(this);
            } else if (!isSpillObject) {
                this.weight += SETPROP_WEIGHT;
            }
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leavePropertyNode(PropertyNode propertyNode) {
        this.weight += 10;
        return propertyNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveReturnNode(ReturnNode returnNode) {
        this.weight += 2;
        return returnNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveRuntimeNode(RuntimeNode runtimeNode) {
        this.weight += 10;
        return runtimeNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitNode(SplitNode splitNode) {
        this.weight += 40;
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        this.weight += 8;
        return switchNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveThrowNode(ThrowNode throwNode) {
        this.weight += 2;
        return throwNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTryNode(TryNode tryNode) {
        this.weight += 2;
        return tryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveVarNode(VarNode varNode) {
        this.weight += 40;
        return varNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveWhileNode(WhileNode whileNode) {
        this.weight += 4;
        return whileNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveWithNode(WithNode withNode) {
        this.weight += 8;
        return withNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveADD(UnaryNode unaryNode) {
        return unaryNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveBIT_NOT(UnaryNode unaryNode) {
        return unaryNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveDECINC(UnaryNode unaryNode) {
        return unaryNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveDELETE(UnaryNode unaryNode) {
        return runtimeNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveNEW(UnaryNode unaryNode) {
        this.weight += 6;
        return unaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveNOT(UnaryNode unaryNode) {
        return unaryNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveSUB(UnaryNode unaryNode) {
        return unaryNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveTYPEOF(UnaryNode unaryNode) {
        return runtimeNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveVOID(UnaryNode unaryNode) {
        return unaryNodeWeight(unaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveADD(BinaryNode binaryNode) {
        this.weight += 10;
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveAND(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_ADD(BinaryNode binaryNode) {
        this.weight += 10;
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_BIT_AND(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_BIT_OR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_BIT_XOR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_DIV(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_MOD(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_MUL(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_SAR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_SHL(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_SHR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveASSIGN_SUB(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveBIND(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveBIT_AND(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveBIT_OR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveBIT_XOR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveCOMMALEFT(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveCOMMARIGHT(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveDIV(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveEQ(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveEQ_STRICT(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveGE(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveGT(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveIN(BinaryNode binaryNode) {
        this.weight += 10;
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveINSTANCEOF(BinaryNode binaryNode) {
        this.weight += 10;
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveLE(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveLT(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveMOD(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveMUL(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveNE(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveNE_STRICT(BinaryNode binaryNode) {
        return compareWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveOR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveSAR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveSHL(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveSHR(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveSUB(BinaryNode binaryNode) {
        return binaryNodeWeight(binaryNode);
    }

    private Node unaryNodeWeight(UnaryNode unaryNode) {
        this.weight++;
        return unaryNode;
    }

    private Node binaryNodeWeight(BinaryNode binaryNode) {
        this.weight++;
        return binaryNode;
    }

    private Node runtimeNodeWeight(UnaryNode unaryNode) {
        this.weight += 10;
        return unaryNode;
    }

    private Node compareWeight(BinaryNode binaryNode) {
        this.weight += 6;
        return binaryNode;
    }
}
