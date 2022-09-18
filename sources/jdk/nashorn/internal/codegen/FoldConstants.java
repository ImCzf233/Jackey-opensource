package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.EmptyNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "fold")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/FoldConstants.class */
final class FoldConstants extends SimpleNodeVisitor implements Loggable {
    private final DebugLogger log;

    public FoldConstants(Compiler compiler) {
        this.log = initLogger(compiler.getContext());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveUnaryNode(UnaryNode unaryNode) {
        LiteralNode<?> literalNode = new UnaryNodeConstantEvaluator(unaryNode).eval();
        if (literalNode != null) {
            this.log.info("Unary constant folded ", unaryNode, " to ", literalNode);
            return literalNode;
        }
        return unaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBinaryNode(BinaryNode binaryNode) {
        LiteralNode<?> literalNode = new BinaryNodeConstantEvaluator(binaryNode).eval();
        if (literalNode != null) {
            this.log.info("Binary constant folded ", binaryNode, " to ", literalNode);
            return literalNode;
        }
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        return functionNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIfNode(IfNode ifNode) {
        Node test = ifNode.getTest();
        if (test instanceof LiteralNode.PrimitiveLiteralNode) {
            boolean isTrue = ((LiteralNode.PrimitiveLiteralNode) test).isTrue();
            Block executed = isTrue ? ifNode.getPass() : ifNode.getFail();
            Block dropped = isTrue ? ifNode.getFail() : ifNode.getPass();
            List<Statement> statements = new ArrayList<>();
            if (executed != null) {
                statements.addAll(executed.getStatements());
            }
            if (dropped != null) {
                extractVarNodesFromDeadCode(dropped, statements);
            }
            if (statements.isEmpty()) {
                return new EmptyNode(ifNode);
            }
            return BlockStatement.createReplacement(ifNode, ifNode.getFinish(), statements);
        }
        return ifNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTernaryNode(TernaryNode ternaryNode) {
        Node test = ternaryNode.getTest();
        if (test instanceof LiteralNode.PrimitiveLiteralNode) {
            return (((LiteralNode.PrimitiveLiteralNode) test).isTrue() ? ternaryNode.getTrueExpression() : ternaryNode.getFalseExpression()).getExpression();
        }
        return ternaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        return switchNode.setUniqueInteger(this.f247lc, isUniqueIntegerSwitchNode(switchNode));
    }

    private static boolean isUniqueIntegerSwitchNode(SwitchNode switchNode) {
        Set<Integer> alreadySeen = new HashSet<>();
        for (CaseNode caseNode : switchNode.getCases()) {
            Expression test = caseNode.getTest();
            if (test != null && !isUniqueIntegerLiteral(test, alreadySeen)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUniqueIntegerLiteral(Expression expr, Set<Integer> alreadySeen) {
        if (expr instanceof LiteralNode) {
            Object value = ((LiteralNode) expr).getValue();
            if (value instanceof Integer) {
                return alreadySeen.add((Integer) value);
            }
            return false;
        }
        return false;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/FoldConstants$ConstantEvaluator.class */
    public static abstract class ConstantEvaluator<T extends Node> {
        protected T parent;
        protected final long token;
        protected final int finish;

        protected abstract LiteralNode<?> eval();

        protected ConstantEvaluator(T parent) {
            this.parent = parent;
            this.token = parent.getToken();
            this.finish = parent.getFinish();
        }
    }

    public static void extractVarNodesFromDeadCode(Node deadCodeRoot, final List<Statement> statements) {
        deadCodeRoot.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.FoldConstants.1
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterVarNode(VarNode varNode) {
                statements.add(varNode.setInit(null));
                return false;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterFunctionNode(FunctionNode functionNode) {
                return false;
            }
        });
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/FoldConstants$UnaryNodeConstantEvaluator.class */
    private static class UnaryNodeConstantEvaluator extends ConstantEvaluator<UnaryNode> {
        UnaryNodeConstantEvaluator(UnaryNode parent) {
            super(parent);
        }

        @Override // jdk.nashorn.internal.codegen.FoldConstants.ConstantEvaluator
        protected LiteralNode<?> eval() {
            LiteralNode<?> literalNode;
            Node rhsNode = ((UnaryNode) this.parent).getExpression();
            if (!(rhsNode instanceof LiteralNode) || (rhsNode instanceof LiteralNode.ArrayLiteralNode)) {
                return null;
            }
            LiteralNode<?> rhs = (LiteralNode) rhsNode;
            Type rhsType = rhs.getType();
            boolean rhsInteger = rhsType.isInteger() || rhsType.isBoolean();
            switch (((UnaryNode) this.parent).tokenType()) {
                case ADD:
                    if (rhsInteger) {
                        literalNode = LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(rhs.getInt32()));
                        break;
                    } else if (rhsType.isLong()) {
                        literalNode = LiteralNode.newInstance(this.token, this.finish, Long.valueOf(rhs.getLong()));
                        break;
                    } else {
                        literalNode = LiteralNode.newInstance(this.token, this.finish, Double.valueOf(rhs.getNumber()));
                        break;
                    }
                case SUB:
                    if (rhsInteger && rhs.getInt32() != 0) {
                        literalNode = LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(-rhs.getInt32()));
                        break;
                    } else if (rhsType.isLong() && rhs.getLong() != 0) {
                        literalNode = LiteralNode.newInstance(this.token, this.finish, Long.valueOf(-rhs.getLong()));
                        break;
                    } else {
                        literalNode = LiteralNode.newInstance(this.token, this.finish, Double.valueOf(-rhs.getNumber()));
                        break;
                    }
                    break;
                case NOT:
                    literalNode = LiteralNode.newInstance(this.token, this.finish, !rhs.getBoolean());
                    break;
                case BIT_NOT:
                    literalNode = LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(rhs.getInt32() ^ (-1)));
                    break;
                default:
                    return null;
            }
            return literalNode;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/FoldConstants$BinaryNodeConstantEvaluator.class */
    private static class BinaryNodeConstantEvaluator extends ConstantEvaluator<BinaryNode> {
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !FoldConstants.class.desiredAssertionStatus();
        }

        BinaryNodeConstantEvaluator(BinaryNode parent) {
            super(parent);
        }

        @Override // jdk.nashorn.internal.codegen.FoldConstants.ConstantEvaluator
        protected LiteralNode<?> eval() {
            LiteralNode<?> result = reduceTwoLiterals();
            if (result != null) {
                return result;
            }
            LiteralNode<?> result2 = reduceOneLiteral();
            if (result2 != null) {
                return result2;
            }
            return null;
        }

        private LiteralNode<?> reduceOneLiteral() {
            return null;
        }

        private LiteralNode<?> reduceTwoLiterals() {
            double value;
            if (!(((BinaryNode) this.parent).lhs() instanceof LiteralNode) || !(((BinaryNode) this.parent).rhs() instanceof LiteralNode)) {
                return null;
            }
            LiteralNode<?> lhs = (LiteralNode) ((BinaryNode) this.parent).lhs();
            LiteralNode<?> rhs = (LiteralNode) ((BinaryNode) this.parent).rhs();
            if ((lhs instanceof LiteralNode.ArrayLiteralNode) || (rhs instanceof LiteralNode.ArrayLiteralNode)) {
                return null;
            }
            Type widest = Type.widest(lhs.getType(), rhs.getType());
            boolean isInteger = widest.isInteger();
            switch (((BinaryNode) this.parent).tokenType()) {
                case ADD:
                    if (!lhs.isString() && !rhs.isNumeric()) {
                        return null;
                    }
                    if (rhs.isString() || rhs.isNumeric()) {
                        Object res = ScriptRuntime.ADD(lhs.getObject(), rhs.getObject());
                        if (res instanceof Number) {
                            value = ((Number) res).doubleValue();
                            break;
                        } else if (!$assertionsDisabled && !(res instanceof CharSequence)) {
                            throw new AssertionError(res + " was not a CharSequence, it was a " + res.getClass());
                        } else {
                            return LiteralNode.newInstance(this.token, this.finish, res.toString());
                        }
                    } else {
                        return null;
                    }
                    break;
                case SUB:
                    value = lhs.getNumber() - rhs.getNumber();
                    break;
                case NOT:
                case BIT_NOT:
                default:
                    return null;
                case DIV:
                    value = lhs.getNumber() / rhs.getNumber();
                    break;
                case MUL:
                    value = lhs.getNumber() * rhs.getNumber();
                    break;
                case MOD:
                    value = lhs.getNumber() % rhs.getNumber();
                    break;
                case SHR:
                    long result = JSType.toUint32(lhs.getInt32() >>> rhs.getInt32());
                    return LiteralNode.newInstance(this.token, this.finish, JSType.toNarrowestNumber(result));
                case SAR:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(lhs.getInt32() >> rhs.getInt32()));
                case SHL:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(lhs.getInt32() << rhs.getInt32()));
                case BIT_XOR:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(lhs.getInt32() ^ rhs.getInt32()));
                case BIT_AND:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(lhs.getInt32() & rhs.getInt32()));
                case BIT_OR:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(lhs.getInt32() | rhs.getInt32()));
                case GE:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m64GE(lhs.getObject(), rhs.getObject()));
                case LE:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m61LE(lhs.getObject(), rhs.getObject()));
                case GT:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m63GT(lhs.getObject(), rhs.getObject()));
                case LT:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m60LT(lhs.getObject(), rhs.getObject()));
                case NE:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m59NE(lhs.getObject(), rhs.getObject()));
                case NE_STRICT:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.NE_STRICT(lhs.getObject(), rhs.getObject()));
                case EQ:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m65EQ(lhs.getObject(), rhs.getObject()));
                case EQ_STRICT:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.EQ_STRICT(lhs.getObject(), rhs.getObject()));
            }
            if (isInteger & JSType.isStrictlyRepresentableAsInt(value)) {
                return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf((int) value));
            }
            return LiteralNode.newInstance(this.token, this.finish, Double.valueOf(value));
        }
    }
}
