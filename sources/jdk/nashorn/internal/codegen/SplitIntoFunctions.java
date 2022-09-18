package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockLexicalContext;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.GetSplitState;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.JumpStatement;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.SetSplitState;
import jdk.nashorn.internal.p001ir.SplitNode;
import jdk.nashorn.internal.p001ir.SplitReturn;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/SplitIntoFunctions.class */
final class SplitIntoFunctions extends NodeVisitor<BlockLexicalContext> {
    private static final int FALLTHROUGH_STATE = -1;
    private static final int RETURN_STATE = 0;
    private static final int BREAK_STATE = 1;
    private static final int FIRST_JUMP_STATE = 2;
    private static final String THIS_NAME;
    private static final String RETURN_NAME;
    private static final String RETURN_PARAM_NAME;
    private final Namespace namespace;
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Deque<FunctionState> functionStates = new ArrayDeque();
    private final Deque<SplitState> splitStates = new ArrayDeque();
    private boolean artificialBlock = false;
    private int nextFunctionId = -2;

    static {
        $assertionsDisabled = !SplitIntoFunctions.class.desiredAssertionStatus();
        THIS_NAME = CompilerConstants.THIS.symbolName();
        RETURN_NAME = CompilerConstants.RETURN.symbolName();
        RETURN_PARAM_NAME = RETURN_NAME + "-in";
    }

    public SplitIntoFunctions(Compiler compiler) {
        super(new BlockLexicalContext() { // from class: jdk.nashorn.internal.codegen.SplitIntoFunctions.1
            static final /* synthetic */ boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !SplitIntoFunctions.class.desiredAssertionStatus();
            }

            @Override // jdk.nashorn.internal.p001ir.BlockLexicalContext
            protected Block afterSetStatements(Block block) {
                for (Statement stmt : block.getStatements()) {
                    if (!$assertionsDisabled && (stmt instanceof SplitNode)) {
                        throw new AssertionError();
                    }
                }
                return block;
            }
        });
        this.namespace = new Namespace(compiler.getScriptEnvironment().getNamespace());
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        this.functionStates.push(new FunctionState(functionNode));
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        this.functionStates.pop();
        return functionNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveDefault(Node node) {
        if (node instanceof Statement) {
            appendStatement((Statement) node);
        }
        return node;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitNode(SplitNode splitNode) {
        getCurrentFunctionState().splitDepth++;
        this.splitStates.push(new SplitState(splitNode));
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSplitNode(SplitNode splitNode) {
        Expression callWithReturn;
        Statement splitStateHandler;
        SplitState parentSplit;
        FunctionState fnState = getCurrentFunctionState();
        String name = splitNode.getName();
        Block body = splitNode.getBody();
        int firstLineNumber = body.getFirstStatementLineNumber();
        long token = body.getToken();
        int finish = body.getFinish();
        FunctionNode originalFn = fnState.f234fn;
        if ($assertionsDisabled || originalFn == ((BlockLexicalContext) this.f247lc).getCurrentFunction()) {
            boolean isProgram = originalFn.isProgram();
            TokenType tokenType = TokenType.FUNCTION;
            int i = this.nextFunctionId;
            this.nextFunctionId = i - 1;
            long newFnToken = Token.toDesc(tokenType, i, 0);
            FunctionNode fn = new FunctionNode(originalFn.getSource(), body.getFirstStatementLineNumber(), newFnToken, finish, 0L, this.namespace, createIdent(name), originalFn.getName() + ArgsClassGenerator.GETTER_PREFIX + name, isProgram ? Collections.singletonList(createReturnParamIdent()) : Collections.emptyList(), FunctionNode.Kind.NORMAL, 529).setBody(this.f247lc, body).setCompileUnit(this.f247lc, splitNode.getCompileUnit());
            IdentNode thisIdent = createIdent(THIS_NAME);
            Expression callNode = new CallNode(firstLineNumber, token, finish, new AccessNode(0L, 0, fn, "call"), isProgram ? Arrays.asList(thisIdent, createReturnIdent()) : Collections.singletonList(thisIdent), false);
            SplitState splitState = this.splitStates.pop();
            fnState.splitDepth--;
            boolean hasReturn = splitState.hasReturn;
            if (hasReturn && fnState.splitDepth > 0 && (parentSplit = this.splitStates.peek()) != null) {
                parentSplit.hasReturn = true;
            }
            if (hasReturn || isProgram) {
                callWithReturn = new BinaryNode(Token.recast(token, TokenType.ASSIGN), createReturnIdent(), callNode);
            } else {
                callWithReturn = callNode;
            }
            appendStatement(new ExpressionStatement(firstLineNumber, token, finish, callWithReturn));
            List<JumpStatement> jumpStatements = splitState.jumpStatements;
            int jumpCount = jumpStatements.size();
            if (jumpCount > 0) {
                List<CaseNode> cases = new ArrayList<>(jumpCount + (hasReturn ? 1 : 0));
                if (hasReturn) {
                    addCase(cases, 0, createReturnFromSplit());
                }
                int i2 = 2;
                for (JumpStatement jump : jumpStatements) {
                    int i3 = i2;
                    i2++;
                    addCase(cases, i3, enblockAndVisit(jump));
                }
                splitStateHandler = new SwitchNode(-1, token, finish, GetSplitState.INSTANCE, cases, null);
            } else {
                splitStateHandler = null;
            }
            if (splitState.hasBreak) {
                splitStateHandler = makeIfStateEquals(firstLineNumber, token, finish, 1, enblockAndVisit(new BreakNode(-1, token, finish, null)), splitStateHandler);
            }
            if (hasReturn && jumpCount == 0) {
                splitStateHandler = makeIfStateEquals(-1, token, finish, 0, createReturnFromSplit(), splitStateHandler);
            }
            if (splitStateHandler != null) {
                appendStatement(splitStateHandler);
            }
            return splitNode;
        }
        throw new AssertionError();
    }

    private static void addCase(List<CaseNode> cases, int i, Block body) {
        cases.add(new CaseNode(0L, 0, intLiteral(i), body));
    }

    private static LiteralNode<Number> intLiteral(int i) {
        return LiteralNode.newInstance(0L, 0, Integer.valueOf(i));
    }

    private static Block createReturnFromSplit() {
        return new Block(0L, 0, createReturnReturn());
    }

    private static ReturnNode createReturnReturn() {
        return new ReturnNode(-1, 0L, 0, createReturnIdent());
    }

    private static IdentNode createReturnIdent() {
        return createIdent(RETURN_NAME);
    }

    private static IdentNode createReturnParamIdent() {
        return createIdent(RETURN_PARAM_NAME);
    }

    private static IdentNode createIdent(String name) {
        return new IdentNode(0L, 0, name);
    }

    private Block enblockAndVisit(JumpStatement jump) {
        this.artificialBlock = true;
        Block block = (Block) new Block(0L, 0, jump).accept(this);
        this.artificialBlock = false;
        return block;
    }

    private static IfNode makeIfStateEquals(int lineNumber, long token, int finish, int value, Block pass, Statement fail) {
        return new IfNode(lineNumber, token, finish, new BinaryNode(Token.recast(token, TokenType.EQ_STRICT), GetSplitState.INSTANCE, intLiteral(value)), pass, fail == null ? null : new Block(0L, 0, fail));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        if (!inSplitNode()) {
            return super.enterVarNode(varNode);
        }
        if (!$assertionsDisabled && varNode.isBlockScoped()) {
            throw new AssertionError();
        }
        Expression init = varNode.getInit();
        getCurrentFunctionState().varStatements.add(varNode.setInit(null));
        if (init != null) {
            long token = Token.recast(varNode.getToken(), TokenType.ASSIGN);
            new ExpressionStatement(varNode.getLineNumber(), token, varNode.getFinish(), new BinaryNode(token, varNode.getName(), varNode.getInit())).accept(this);
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlock(Block block) {
        if (!this.artificialBlock) {
            if (((BlockLexicalContext) this.f247lc).isFunctionBody()) {
                ((BlockLexicalContext) this.f247lc).prependStatements(getCurrentFunctionState().varStatements);
            } else if (((BlockLexicalContext) this.f247lc).isSplitBody()) {
                appendSplitReturn(-1, -1);
                if (getCurrentFunctionState().f234fn.isProgram()) {
                    ((BlockLexicalContext) this.f247lc).prependStatement(new ExpressionStatement(-1, 0L, 0, new BinaryNode(Token.toDesc(TokenType.ASSIGN, 0, 0), createReturnIdent(), createReturnParamIdent())));
                }
            }
        }
        return block;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBreakNode(BreakNode breakNode) {
        return leaveJumpNode(breakNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveContinueNode(ContinueNode continueNode) {
        return leaveJumpNode(continueNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        return leaveJumpNode(jumpToInlinedFinally);
    }

    private JumpStatement leaveJumpNode(JumpStatement jump) {
        if (inSplitNode()) {
            SplitState splitState = getCurrentSplitState();
            SplitNode splitNode = splitState.splitNode;
            if (((BlockLexicalContext) this.f247lc).isExternalTarget(splitNode, jump.getTarget(this.f247lc))) {
                appendSplitReturn(splitState.getSplitStateIndex(jump), jump.getLineNumber());
                return jump;
            }
        }
        appendStatement(jump);
        return jump;
    }

    private void appendSplitReturn(int splitState, int lineNumber) {
        appendStatement(new SetSplitState(splitState, lineNumber));
        if (getCurrentFunctionState().f234fn.isProgram()) {
            appendStatement(createReturnReturn());
        } else {
            appendStatement(SplitReturn.INSTANCE);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveReturnNode(ReturnNode returnNode) {
        if (inSplitNode()) {
            appendStatement(new SetSplitState(0, returnNode.getLineNumber()));
            getCurrentSplitState().hasReturn = true;
        }
        appendStatement(returnNode);
        return returnNode;
    }

    private void appendStatement(Statement statement) {
        ((BlockLexicalContext) this.f247lc).appendStatement(statement);
    }

    private boolean inSplitNode() {
        return getCurrentFunctionState().splitDepth > 0;
    }

    private FunctionState getCurrentFunctionState() {
        return this.functionStates.peek();
    }

    private SplitState getCurrentSplitState() {
        return this.splitStates.peek();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/SplitIntoFunctions$FunctionState.class */
    public static class FunctionState {

        /* renamed from: fn */
        final FunctionNode f234fn;
        final List<Statement> varStatements = new ArrayList();
        int splitDepth;

        FunctionState(FunctionNode fn) {
            this.f234fn = fn;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/SplitIntoFunctions$SplitState.class */
    public static class SplitState {
        final SplitNode splitNode;
        boolean hasReturn;
        boolean hasBreak;
        final List<JumpStatement> jumpStatements = new ArrayList();

        int getSplitStateIndex(JumpStatement jump) {
            if ((jump instanceof BreakNode) && jump.getLabelName() == null) {
                this.hasBreak = true;
                return 1;
            }
            int i = 0;
            for (JumpStatement exJump : this.jumpStatements) {
                if (jump.getClass() == exJump.getClass() && Objects.equals(jump.getLabelName(), exJump.getLabelName())) {
                    return i + 2;
                }
                i++;
            }
            this.jumpStatements.add(jump);
            return i + 2;
        }

        SplitState(SplitNode splitNode) {
            this.splitNode = splitNode;
        }
    }
}
