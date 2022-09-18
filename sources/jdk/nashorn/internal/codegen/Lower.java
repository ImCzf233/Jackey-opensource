package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BaseNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockLexicalContext;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.EmptyNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.JumpStatement;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.LoopNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "lower")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/Lower.class */
public final class Lower extends NodeOperatorVisitor<BlockLexicalContext> implements Loggable {
    private final DebugLogger log;
    private static Pattern SAFE_PROPERTY_NAME;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Lower.class.desiredAssertionStatus();
        SAFE_PROPERTY_NAME = Pattern.compile("[a-zA-Z_$][\\w$]*");
    }

    public Lower(Compiler compiler) {
        super(new BlockLexicalContext() { // from class: jdk.nashorn.internal.codegen.Lower.1
            @Override // jdk.nashorn.internal.p001ir.BlockLexicalContext
            public List<Statement> popStatements() {
                List<Statement> newStatements = new ArrayList<>();
                boolean terminated = false;
                List<Statement> statements = super.popStatements();
                for (Statement statement : statements) {
                    if (!terminated) {
                        newStatements.add(statement);
                        if (statement.isTerminal() || (statement instanceof JumpStatement)) {
                            terminated = true;
                        }
                    } else {
                        FoldConstants.extractVarNodesFromDeadCode(statement, newStatements);
                    }
                }
                return newStatements;
            }

            /* JADX WARN: Removed duplicated region for block: B:5:0x001b  */
            @Override // jdk.nashorn.internal.p001ir.BlockLexicalContext
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected jdk.nashorn.internal.p001ir.Block afterSetStatements(jdk.nashorn.internal.p001ir.Block r5) {
                /*
                    r4 = this;
                    r0 = r5
                    java.util.List r0 = r0.getStatements()
                    r6 = r0
                    r0 = r6
                    r1 = r6
                    int r1 = r1.size()
                    java.util.ListIterator r0 = r0.listIterator(r1)
                    r7 = r0
                L12:
                    r0 = r7
                    boolean r0 = r0.hasPrevious()
                    if (r0 == 0) goto L47
                    r0 = r7
                    java.lang.Object r0 = r0.previous()
                    jdk.nashorn.internal.ir.Statement r0 = (jdk.nashorn.internal.p001ir.Statement) r0
                    r8 = r0
                    r0 = r8
                    boolean r0 = r0 instanceof jdk.nashorn.internal.p001ir.VarNode
                    if (r0 == 0) goto L39
                    r0 = r8
                    jdk.nashorn.internal.ir.VarNode r0 = (jdk.nashorn.internal.p001ir.VarNode) r0
                    jdk.nashorn.internal.ir.Expression r0 = r0.getInit()
                    if (r0 == 0) goto L44
                L39:
                    r0 = r5
                    r1 = r4
                    r2 = r8
                    boolean r2 = r2.isTerminal()
                    jdk.nashorn.internal.ir.Block r0 = r0.setIsTerminal(r1, r2)
                    return r0
                L44:
                    goto L12
                L47:
                    r0 = r5
                    r1 = r4
                    r2 = 0
                    jdk.nashorn.internal.ir.Block r0 = r0.setIsTerminal(r1, r2)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.codegen.Lower.C15011.afterSetStatements(jdk.nashorn.internal.ir.Block):jdk.nashorn.internal.ir.Block");
            }
        });
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
    public boolean enterBreakNode(BreakNode breakNode) {
        addStatement(breakNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCallNode(CallNode callNode) {
        return checkEval(callNode.setFunction(markerFunction(callNode.getFunction())));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCatchNode(CatchNode catchNode) {
        return addStatement(catchNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode continueNode) {
        addStatement(continueNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        addStatement(jumpToInlinedFinally);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterEmptyNode(EmptyNode emptyNode) {
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIndexNode(IndexNode indexNode) {
        String name = getConstantPropertyName(indexNode.getIndex());
        if (name != null) {
            if (!$assertionsDisabled && !indexNode.isIndex()) {
                throw new AssertionError();
            }
            return new AccessNode(indexNode.getToken(), indexNode.getFinish(), indexNode.getBase(), name);
        }
        return super.leaveIndexNode(indexNode);
    }

    private static String getConstantPropertyName(Expression expression) {
        if (expression instanceof LiteralNode.PrimitiveLiteralNode) {
            Object value = ((LiteralNode) expression).getValue();
            if ((value instanceof String) && SAFE_PROPERTY_NAME.matcher((String) value).matches()) {
                return (String) value;
            }
            return null;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveExpressionStatement(ExpressionStatement expressionStatement) {
        Expression expr = expressionStatement.getExpression();
        ExpressionStatement node = expressionStatement;
        FunctionNode currentFunction = ((BlockLexicalContext) this.f247lc).getCurrentFunction();
        if (currentFunction.isProgram() && !isInternalExpression(expr) && !isEvalResultAssignment(expr)) {
            node = expressionStatement.setExpression(new BinaryNode(Token.recast(expressionStatement.getToken(), TokenType.ASSIGN), compilerConstant(CompilerConstants.RETURN), expr));
        }
        return addStatement(node);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlockStatement(BlockStatement blockStatement) {
        return addStatement(blockStatement);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveForNode(ForNode forNode) {
        ForNode newForNode = forNode;
        Expression test = forNode.getTest();
        if (!forNode.isForIn() && Expression.isAlwaysTrue(test)) {
            newForNode = forNode.setTest(this.f247lc, (JoinPredecessorExpression) null);
        }
        ForNode newForNode2 = (ForNode) checkEscape(newForNode);
        if (newForNode2.isForIn()) {
            addStatementEnclosedInBlock(newForNode2);
        } else {
            addStatement(newForNode2);
        }
        return newForNode2;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        this.log.info("END FunctionNode: ", functionNode.getName());
        return functionNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIfNode(IfNode ifNode) {
        return addStatement(ifNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveIN(BinaryNode binaryNode) {
        return new RuntimeNode(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveINSTANCEOF(BinaryNode binaryNode) {
        return new RuntimeNode(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveLabelNode(LabelNode labelNode) {
        return addStatement(labelNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveReturnNode(ReturnNode returnNode) {
        addStatement(returnNode);
        return returnNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCaseNode(CaseNode caseNode) {
        Node test = caseNode.getTest();
        if (test instanceof LiteralNode) {
            LiteralNode<?> lit = (LiteralNode) test;
            if (lit.isNumeric() && !(lit.getValue() instanceof Integer) && JSType.isRepresentableAsInt(lit.getNumber())) {
                return caseNode.setTest((Expression) LiteralNode.newInstance(lit, Integer.valueOf(lit.getInt32())).accept(this));
            }
        }
        return caseNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        if (!switchNode.isUniqueInteger()) {
            addStatementEnclosedInBlock(switchNode);
        } else {
            addStatement(switchNode);
        }
        return switchNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveThrowNode(ThrowNode throwNode) {
        return addStatement(throwNode);
    }

    public static <T extends Node> T ensureUniqueNamesIn(T node) {
        return (T) node.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Lower.2
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveFunctionNode(FunctionNode functionNode) {
                String name = functionNode.getName();
                return functionNode.setName(this.f247lc, this.f247lc.getCurrentFunction().uniqueName(name));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveDefault(Node labelledNode) {
                return labelledNode.ensureUniqueLabels(this.f247lc);
            }
        });
    }

    private static Block createFinallyBlock(Block finallyBody) {
        List<Statement> newStatements = new ArrayList<>();
        for (Statement statement : finallyBody.getStatements()) {
            newStatements.add(statement);
            if (statement.hasTerminalFlags()) {
                break;
            }
        }
        return finallyBody.setStatements(null, newStatements);
    }

    private Block catchAllBlock(TryNode tryNode) {
        int lineNumber = tryNode.getLineNumber();
        long token = tryNode.getToken();
        int finish = tryNode.getFinish();
        IdentNode exception = new IdentNode(token, finish, ((BlockLexicalContext) this.f247lc).getCurrentFunction().uniqueName(CompilerConstants.EXCEPTION_PREFIX.symbolName()));
        Block catchBody = new Block(token, finish, new ThrowNode(lineNumber, token, finish, new IdentNode(exception), true));
        if ($assertionsDisabled || catchBody.isTerminal()) {
            CatchNode catchAllNode = new CatchNode(lineNumber, token, finish, new IdentNode(exception), null, catchBody, true);
            Block catchAllBlock = new Block(token, finish, catchAllNode);
            return (Block) catchAllBlock.accept(this);
        }
        throw new AssertionError();
    }

    private IdentNode compilerConstant(CompilerConstants cc) {
        FunctionNode functionNode = ((BlockLexicalContext) this.f247lc).getCurrentFunction();
        return new IdentNode(functionNode.getToken(), functionNode.getFinish(), cc.symbolName());
    }

    public static boolean isTerminalFinally(Block finallyBlock) {
        return finallyBlock.getLastStatement().hasTerminalFlags();
    }

    private TryNode spliceFinally(TryNode tryNode, final ThrowNode rethrow, Block finallyBody) {
        if ($assertionsDisabled || tryNode.getFinallyBody() == null) {
            final Block finallyBlock = createFinallyBlock(finallyBody);
            final ArrayList<Block> inlinedFinallies = new ArrayList<>();
            final FunctionNode fn = ((BlockLexicalContext) this.f247lc).getCurrentFunction();
            TryNode newTryNode = (TryNode) tryNode.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Lower.3
                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterFunctionNode(FunctionNode functionNode) {
                    return false;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveThrowNode(ThrowNode throwNode) {
                    if (rethrow == throwNode) {
                        return new BlockStatement(Lower.prependFinally(finallyBlock, throwNode));
                    }
                    return throwNode;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveBreakNode(BreakNode breakNode) {
                    return leaveJumpStatement(breakNode);
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveContinueNode(ContinueNode continueNode) {
                    return leaveJumpStatement(continueNode);
                }

                private Node leaveJumpStatement(JumpStatement jump) {
                    if (jump.getTarget(this.f247lc) == null) {
                        return Lower.createJumpToInlinedFinally(fn, inlinedFinallies, Lower.prependFinally(finallyBlock, jump));
                    }
                    return jump;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveReturnNode(ReturnNode returnNode) {
                    Expression expr = returnNode.getExpression();
                    if (Lower.isTerminalFinally(finallyBlock)) {
                        if (expr == null) {
                            return Lower.createJumpToInlinedFinally(fn, inlinedFinallies, (Block) Lower.ensureUniqueNamesIn(finallyBlock));
                        }
                        List<Statement> newStatements = new ArrayList<>(2);
                        int retLineNumber = returnNode.getLineNumber();
                        long retToken = returnNode.getToken();
                        newStatements.add(new ExpressionStatement(retLineNumber, retToken, returnNode.getFinish(), expr));
                        newStatements.add(Lower.createJumpToInlinedFinally(fn, inlinedFinallies, (Block) Lower.ensureUniqueNamesIn(finallyBlock)));
                        return new BlockStatement(retLineNumber, new Block(retToken, finallyBlock.getFinish(), newStatements));
                    } else if (expr == null || (expr instanceof LiteralNode.PrimitiveLiteralNode) || ((expr instanceof IdentNode) && CompilerConstants.RETURN.symbolName().equals(((IdentNode) expr).getName()))) {
                        return Lower.createJumpToInlinedFinally(fn, inlinedFinallies, Lower.prependFinally(finallyBlock, returnNode));
                    } else {
                        List<Statement> newStatements2 = new ArrayList<>();
                        int retLineNumber2 = returnNode.getLineNumber();
                        long retToken2 = returnNode.getToken();
                        int retFinish = returnNode.getFinish();
                        Expression resultNode = new IdentNode(expr.getToken(), expr.getFinish(), CompilerConstants.RETURN.symbolName());
                        newStatements2.add(new ExpressionStatement(retLineNumber2, retToken2, retFinish, new BinaryNode(Token.recast(returnNode.getToken(), TokenType.ASSIGN), resultNode, expr)));
                        newStatements2.add(Lower.createJumpToInlinedFinally(fn, inlinedFinallies, Lower.prependFinally(finallyBlock, returnNode.setExpression(resultNode))));
                        return new BlockStatement(retLineNumber2, new Block(retToken2, retFinish, newStatements2));
                    }
                }
            });
            addStatement(inlinedFinallies.isEmpty() ? newTryNode : newTryNode.setInlinedFinallies(this.f247lc, inlinedFinallies));
            addStatement(new BlockStatement(finallyBlock));
            return newTryNode;
        }
        throw new AssertionError();
    }

    public static JumpToInlinedFinally createJumpToInlinedFinally(FunctionNode fn, List<Block> inlinedFinallies, Block finallyBlock) {
        String labelName = fn.uniqueName(":finally");
        long token = finallyBlock.getToken();
        int finish = finallyBlock.getFinish();
        inlinedFinallies.add(new Block(token, finish, new LabelNode(finallyBlock.getFirstStatementLineNumber(), token, finish, labelName, finallyBlock)));
        return new JumpToInlinedFinally(labelName);
    }

    public static Block prependFinally(Block finallyBlock, Statement statement) {
        Block inlinedFinally = (Block) ensureUniqueNamesIn(finallyBlock);
        if (isTerminalFinally(finallyBlock)) {
            return inlinedFinally;
        }
        List<Statement> stmts = inlinedFinally.getStatements();
        List<Statement> newStmts = new ArrayList<>(stmts.size() + 1);
        newStmts.addAll(stmts);
        newStmts.add(statement);
        return new Block(inlinedFinally.getToken(), statement.getFinish(), newStmts);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTryNode(TryNode tryNode) {
        Block finallyBody = tryNode.getFinallyBody();
        TryNode newTryNode = tryNode.setFinallyBody(this.f247lc, null);
        if (finallyBody == null || finallyBody.getStatementCount() == 0) {
            List<CatchNode> catches = newTryNode.getCatches();
            if (catches == null || catches.isEmpty()) {
                return addStatement(new BlockStatement(tryNode.getBody()));
            }
            return addStatement(ensureUnconditionalCatch(newTryNode));
        }
        Block catchAll = catchAllBlock(tryNode);
        final List<ThrowNode> rethrows = new ArrayList<>(1);
        catchAll.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Lower.4
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterThrowNode(ThrowNode throwNode) {
                rethrows.add(throwNode);
                return true;
            }
        });
        if (!$assertionsDisabled && rethrows.size() != 1) {
            throw new AssertionError();
        }
        if (!tryNode.getCatchBlocks().isEmpty()) {
            Block outerBody = new Block(newTryNode.getToken(), newTryNode.getFinish(), ensureUnconditionalCatch(newTryNode));
            newTryNode = newTryNode.setBody(this.f247lc, outerBody).setCatchBlocks(this.f247lc, null);
        }
        return (TryNode) ((BlockLexicalContext) this.f247lc).replace(tryNode, spliceFinally(newTryNode.setCatchBlocks(this.f247lc, Arrays.asList(catchAll)), rethrows.get(0), finallyBody));
    }

    private TryNode ensureUnconditionalCatch(TryNode tryNode) {
        List<CatchNode> catches = tryNode.getCatches();
        if (catches == null || catches.isEmpty() || catches.get(catches.size() - 1).getExceptionCondition() == null) {
            return tryNode;
        }
        List<Block> newCatchBlocks = new ArrayList<>(tryNode.getCatchBlocks());
        newCatchBlocks.add(catchAllBlock(tryNode));
        return tryNode.setCatchBlocks(this.f247lc, newCatchBlocks);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveVarNode(VarNode varNode) {
        addStatement(varNode);
        if (varNode.getFlag(4) && ((BlockLexicalContext) this.f247lc).getCurrentFunction().isProgram()) {
            new ExpressionStatement(varNode.getLineNumber(), varNode.getToken(), varNode.getFinish(), new IdentNode(varNode.getName())).accept(this);
        }
        return varNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveWhileNode(WhileNode whileNode) {
        Expression test = whileNode.getTest();
        Block body = whileNode.getBody();
        if (Expression.isAlwaysTrue(test)) {
            ForNode forNode = (ForNode) new ForNode(whileNode.getLineNumber(), whileNode.getToken(), whileNode.getFinish(), body, 0).accept(this);
            ((BlockLexicalContext) this.f247lc).replace(whileNode, forNode);
            return forNode;
        }
        return addStatement(checkEscape(whileNode));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveWithNode(WithNode withNode) {
        return addStatement(withNode);
    }

    private static Expression markerFunction(Expression function) {
        if (function instanceof IdentNode) {
            return ((IdentNode) function).setIsFunction();
        }
        if (function instanceof BaseNode) {
            return ((BaseNode) function).setIsFunction();
        }
        return function;
    }

    private String evalLocation(IdentNode node) {
        Source source = ((BlockLexicalContext) this.f247lc).getCurrentFunction().getSource();
        int pos = node.position();
        return source.getName() + '#' + source.getLine(pos) + ':' + source.getColumn(pos) + "<eval>";
    }

    private CallNode checkEval(CallNode callNode) {
        if (callNode.getFunction() instanceof IdentNode) {
            List<Expression> args = callNode.getArgs();
            IdentNode callee = (IdentNode) callNode.getFunction();
            if (args.size() >= 1 && CompilerConstants.EVAL.symbolName().equals(callee.getName())) {
                List<Expression> evalArgs = new ArrayList<>(args.size());
                for (Expression arg : args) {
                    evalArgs.add((Expression) ((Expression) ensureUniqueNamesIn(arg)).accept(this));
                }
                return callNode.setEvalArgs(new CallNode.EvalArgs(evalArgs, evalLocation(callee)));
            }
        }
        return callNode;
    }

    private static boolean controlFlowEscapes(final LexicalContext lex, Block loopBody) {
        final List<Node> escapes = new ArrayList<>();
        loopBody.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Lower.5
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBreakNode(BreakNode node) {
                escapes.add(node);
                return node;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveContinueNode(ContinueNode node) {
                if (lex.contains(node.getTarget(lex))) {
                    escapes.add(node);
                }
                return node;
            }
        });
        return !escapes.isEmpty();
    }

    private <T extends LoopNode> T checkEscape(T loopNode) {
        boolean escapes = controlFlowEscapes(this.f247lc, loopNode.getBody());
        if (escapes) {
            return (T) loopNode.setBody(this.f247lc, loopNode.getBody().setIsTerminal(this.f247lc, false)).setControlFlowEscapes(this.f247lc, escapes);
        }
        return loopNode;
    }

    private Node addStatement(Statement statement) {
        ((BlockLexicalContext) this.f247lc).appendStatement(statement);
        return statement;
    }

    private void addStatementEnclosedInBlock(Statement stmt) {
        BlockStatement b = BlockStatement.createReplacement(stmt, Collections.singletonList(stmt));
        if (stmt.isTerminal()) {
            b = b.setBlock(b.getBlock().setIsTerminal(null, true));
        }
        addStatement(b);
    }

    private static boolean isInternalExpression(Expression expression) {
        Symbol symbol;
        return (expression instanceof IdentNode) && (symbol = ((IdentNode) expression).getSymbol()) != null && symbol.isInternal();
    }

    private static boolean isEvalResultAssignment(Node expression) {
        if (expression instanceof BinaryNode) {
            Node lhs = ((BinaryNode) expression).lhs();
            if (lhs instanceof IdentNode) {
                return ((IdentNode) lhs).getName().equals(CompilerConstants.RETURN.symbolName());
            }
            return false;
        }
        return false;
    }
}
