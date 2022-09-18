package jdk.nashorn.internal.parser;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BaseNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockLexicalContext;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.BreakableNode;
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
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.LoopNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.PropertyKey;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.debug.ASTWriter;
import jdk.nashorn.internal.p001ir.debug.PrintVisitor;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptingFunctions;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.Timing;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "parser")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/Parser.class */
public class Parser extends AbstractParser implements Loggable {
    private static final String ARGUMENTS_NAME;
    private final ScriptEnvironment env;
    private final boolean scripting;
    private List<Statement> functionDeclarations;

    /* renamed from: lc */
    private final BlockLexicalContext f255lc;
    private final Deque<Object> defaultNames;
    private final Namespace namespace;
    private final DebugLogger log;
    protected final Lexer.LineInfoReceiver lineInfoReceiver;
    private RecompilableScriptFunctionData reparsedFunction;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Parser.class.desiredAssertionStatus();
        ARGUMENTS_NAME = CompilerConstants.ARGUMENTS_VAR.symbolName();
    }

    public Parser(ScriptEnvironment env, Source source, ErrorManager errors) {
        this(env, source, errors, env._strict, null);
    }

    public Parser(ScriptEnvironment env, Source source, ErrorManager errors, boolean strict, DebugLogger log) {
        this(env, source, errors, strict, 0, log);
    }

    public Parser(ScriptEnvironment env, Source source, ErrorManager errors, boolean strict, int lineOffset, DebugLogger log) {
        super(source, errors, strict, lineOffset);
        this.f255lc = new BlockLexicalContext();
        this.defaultNames = new ArrayDeque();
        this.env = env;
        this.namespace = new Namespace(env.getNamespace());
        this.scripting = env._scripting;
        if (this.scripting) {
            this.lineInfoReceiver = new Lexer.LineInfoReceiver() { // from class: jdk.nashorn.internal.parser.Parser.1
                @Override // jdk.nashorn.internal.parser.Lexer.LineInfoReceiver
                public void lineInfo(int receiverLine, int receiverLinePosition) {
                    Parser.this.line = receiverLine;
                    Parser.this.linePosition = receiverLinePosition;
                }
            };
        } else {
            this.lineInfoReceiver = null;
        }
        this.log = log == null ? DebugLogger.DISABLED_LOGGER : log;
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

    public void setFunctionName(String name) {
        this.defaultNames.push(createIdentNode(0L, 0, name));
    }

    public void setReparsedFunction(RecompilableScriptFunctionData reparsedFunction) {
        this.reparsedFunction = reparsedFunction;
    }

    public FunctionNode parse() {
        return parse(CompilerConstants.PROGRAM.symbolName(), 0, this.source.getLength(), false);
    }

    public FunctionNode parse(String scriptName, int startPos, int len, boolean allowPropertyFunction) {
        boolean isTimingEnabled = this.env.isTimingEnabled();
        long t0 = isTimingEnabled ? System.nanoTime() : 0L;
        this.log.info(this, " begin for '", scriptName, "'");
        try {
            try {
                this.stream = new TokenStream();
                this.lexer = new Lexer(this.source, startPos, len, this.stream, this.scripting && !this.env._no_syntax_extensions, this.reparsedFunction != null);
                Lexer lexer = this.lexer;
                Lexer lexer2 = this.lexer;
                int i = this.lineOffset + 1;
                lexer2.pendingLine = i;
                lexer.line = i;
                this.line = this.lineOffset;
                this.f254k = -1;
                next();
                FunctionNode program = program(scriptName, allowPropertyFunction);
                String end = this + " end '" + scriptName + "'";
                if (isTimingEnabled) {
                    this.env._timing.accumulateTime(toString(), System.nanoTime() - t0);
                    this.log.info(end, "' in ", Timing.toMillisPrint(System.nanoTime() - t0), " ms");
                } else {
                    this.log.info(end);
                }
                return program;
            } catch (Exception e) {
                handleParseException(e);
                String end2 = this + " end '" + scriptName + "'";
                if (isTimingEnabled) {
                    this.env._timing.accumulateTime(toString(), System.nanoTime() - t0);
                    this.log.info(end2, "' in ", Timing.toMillisPrint(System.nanoTime() - t0), " ms");
                } else {
                    this.log.info(end2);
                }
                return null;
            }
        } catch (Throwable th) {
            String end3 = this + " end '" + scriptName + "'";
            if (isTimingEnabled) {
                this.env._timing.accumulateTime(toString(), System.nanoTime() - t0);
                this.log.info(end3, "' in ", Timing.toMillisPrint(System.nanoTime() - t0), " ms");
            } else {
                this.log.info(end3);
            }
            throw th;
        }
    }

    public List<IdentNode> parseFormalParameterList() {
        try {
            this.stream = new TokenStream();
            this.lexer = new Lexer(this.source, this.stream, this.scripting && !this.env._no_syntax_extensions);
            this.f254k = -1;
            next();
            return formalParameterList(TokenType.EOF);
        } catch (Exception e) {
            handleParseException(e);
            return null;
        }
    }

    public FunctionNode parseFunctionBody() {
        try {
            this.stream = new TokenStream();
            this.lexer = new Lexer(this.source, this.stream, this.scripting && !this.env._no_syntax_extensions);
            int functionLine = this.line;
            this.f254k = -1;
            next();
            long functionToken = Token.toDesc(TokenType.FUNCTION, 0, this.source.getLength());
            FunctionNode function = newFunctionNode(functionToken, new IdentNode(functionToken, Token.descPosition(functionToken), CompilerConstants.PROGRAM.symbolName()), new ArrayList(), FunctionNode.Kind.NORMAL, functionLine);
            this.functionDeclarations = new ArrayList();
            sourceElements(false);
            addFunctionDeclarations(function);
            this.functionDeclarations = null;
            expect(TokenType.EOF);
            function.setFinish(this.source.getLength() - 1);
            FunctionNode function2 = restoreFunctionNode(function, this.token);
            FunctionNode function3 = function2.setBody(this.f255lc, function2.getBody().setNeedsScope(this.f255lc));
            printAST(function3);
            return function3;
        } catch (Exception e) {
            handleParseException(e);
            return null;
        }
    }

    private void handleParseException(Exception e) {
        String message = e.getMessage();
        if (message == null) {
            message = e.toString();
        }
        if (e instanceof ParserException) {
            this.errors.error((ParserException) e);
        } else {
            this.errors.error(message);
        }
        if (this.env._dump_on_error) {
            e.printStackTrace(this.env.getErr());
        }
    }

    private void recover(Exception e) {
        if (e != null) {
            String message = e.getMessage();
            if (message == null) {
                message = e.toString();
            }
            if (e instanceof ParserException) {
                this.errors.error((ParserException) e);
            } else {
                this.errors.error(message);
            }
            if (this.env._dump_on_error) {
                e.printStackTrace(this.env.getErr());
            }
        }
        while (true) {
            switch (this.type) {
                case EOF:
                    return;
                case EOL:
                case SEMICOLON:
                case RBRACE:
                    next();
                    return;
                default:
                    nextOrEOL();
            }
        }
    }

    private Block newBlock() {
        return (Block) this.f255lc.push(new Block(this.token, Token.descPosition(this.token), new Statement[0]));
    }

    private FunctionNode newFunctionNode(long startToken, IdentNode ident, List<IdentNode> parameters, FunctionNode.Kind kind, int functionLine) {
        StringBuilder sb = new StringBuilder();
        FunctionNode parentFunction = this.f255lc.getCurrentFunction();
        if (parentFunction != null && !parentFunction.isProgram()) {
            sb.append(parentFunction.getName()).append(CompilerConstants.NESTED_FUNCTION_SEPARATOR.symbolName());
        }
        if ($assertionsDisabled || ident.getName() != null) {
            sb.append(ident.getName());
            String name = this.namespace.uniqueName(sb.toString());
            if (!$assertionsDisabled && parentFunction == null && !name.equals(CompilerConstants.PROGRAM.symbolName()) && !name.startsWith(RecompilableScriptFunctionData.RECOMPILATION_PREFIX)) {
                throw new AssertionError("name = " + name);
            }
            int flags = 0;
            if (this.isStrictMode) {
                flags = 0 | 4;
            }
            if (parentFunction == null) {
                flags |= 8192;
            }
            FunctionNode functionNode = new FunctionNode(this.source, functionLine, this.token, Token.descPosition(this.token), startToken, this.namespace, ident, name, parameters, kind, flags);
            this.f255lc.push(functionNode);
            newBlock();
            return functionNode;
        }
        throw new AssertionError();
    }

    private Block restoreBlock(Block block) {
        return (Block) this.f255lc.pop(block);
    }

    private FunctionNode restoreFunctionNode(FunctionNode functionNode, long lastToken) {
        Block newBody = restoreBlock(this.f255lc.getFunctionBody(functionNode));
        return ((FunctionNode) this.f255lc.pop(functionNode)).setBody(this.f255lc, newBody).setLastToken(this.f255lc, lastToken);
    }

    private Block getBlock(boolean needsBraces) {
        Block newBlock;
        Block newBlock2 = newBlock();
        if (needsBraces) {
            try {
                expect(TokenType.LBRACE);
            } finally {
                restoreBlock(newBlock2);
            }
        }
        statementList();
        int possibleEnd = Token.descPosition(this.token) + Token.descLength(this.token);
        if (needsBraces) {
            expect(TokenType.RBRACE);
        }
        newBlock.setFinish(possibleEnd);
        return newBlock;
    }

    private Block getStatement() {
        if (this.type == TokenType.LBRACE) {
            return getBlock(true);
        }
        Block newBlock = newBlock();
        try {
            statement(false, false, true);
            return restoreBlock(newBlock);
        } finally {
            restoreBlock(newBlock);
        }
    }

    private void detectSpecialFunction(IdentNode ident) {
        String name = ident.getName();
        if (CompilerConstants.EVAL.symbolName().equals(name)) {
            markEval(this.f255lc);
        }
    }

    private void detectSpecialProperty(IdentNode ident) {
        if (isArguments(ident)) {
            this.f255lc.setFlag(this.f255lc.getCurrentFunction(), 8);
        }
    }

    private boolean useBlockScope() {
        return this.env._es6;
    }

    private static boolean isArguments(String name) {
        return ARGUMENTS_NAME.equals(name);
    }

    private static boolean isArguments(IdentNode ident) {
        return isArguments(ident.getName());
    }

    private static boolean checkIdentLValue(IdentNode ident) {
        return ident.tokenType().getKind() != TokenKind.KEYWORD;
    }

    private Expression verifyAssignment(long op, Expression lhs, Expression rhs) {
        TokenType opType = Token.descType(op);
        switch (opType) {
            case ASSIGN:
            case ASSIGN_ADD:
            case ASSIGN_BIT_AND:
            case ASSIGN_BIT_OR:
            case ASSIGN_BIT_XOR:
            case ASSIGN_DIV:
            case ASSIGN_MOD:
            case ASSIGN_MUL:
            case ASSIGN_SAR:
            case ASSIGN_SHL:
            case ASSIGN_SHR:
            case ASSIGN_SUB:
                if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode) && !(lhs instanceof IdentNode)) {
                    return referenceError(lhs, rhs, this.env._early_lvalue_error);
                }
                if (lhs instanceof IdentNode) {
                    if (!checkIdentLValue((IdentNode) lhs)) {
                        return referenceError(lhs, rhs, false);
                    }
                    verifyStrictIdent((IdentNode) lhs, "assignment");
                    break;
                }
                break;
        }
        if (BinaryNode.isLogical(opType)) {
            return new BinaryNode(op, new JoinPredecessorExpression(lhs), new JoinPredecessorExpression(rhs));
        }
        return new BinaryNode(op, lhs, rhs);
    }

    private static UnaryNode incDecExpression(long firstToken, TokenType tokenType, Expression expression, boolean isPostfix) {
        if (isPostfix) {
            return new UnaryNode(Token.recast(firstToken, tokenType == TokenType.DECPREFIX ? TokenType.DECPOSTFIX : TokenType.INCPOSTFIX), expression.getStart(), Token.descPosition(firstToken) + Token.descLength(firstToken), expression);
        }
        return new UnaryNode(firstToken, expression);
    }

    private FunctionNode program(String scriptName, boolean allowPropertyFunction) {
        long functionToken = Token.toDesc(TokenType.FUNCTION, Token.descPosition(Token.withDelimiter(this.token)), this.source.getLength());
        int functionLine = this.line;
        FunctionNode script = newFunctionNode(functionToken, new IdentNode(functionToken, Token.descPosition(functionToken), scriptName), new ArrayList(), FunctionNode.Kind.SCRIPT, functionLine);
        this.functionDeclarations = new ArrayList();
        sourceElements(allowPropertyFunction);
        addFunctionDeclarations(script);
        this.functionDeclarations = null;
        expect(TokenType.EOF);
        script.setFinish(this.source.getLength() - 1);
        FunctionNode script2 = restoreFunctionNode(script, this.token);
        return script2.setBody(this.f255lc, script2.getBody().setNeedsScope(this.f255lc));
    }

    private String getDirective(Node stmt) {
        if (stmt instanceof ExpressionStatement) {
            Node expr = ((ExpressionStatement) stmt).getExpression();
            if (expr instanceof LiteralNode) {
                LiteralNode<?> lit = (LiteralNode) expr;
                long litToken = lit.getToken();
                TokenType tt = Token.descType(litToken);
                if (tt == TokenType.STRING || tt == TokenType.ESCSTRING) {
                    return this.source.getString(lit.getStart(), Token.descLength(litToken));
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private void sourceElements(boolean shouldAllowPropertyFunction) {
        int flag;
        List<Node> directiveStmts = null;
        boolean checkDirective = true;
        boolean allowPropertyFunction = shouldAllowPropertyFunction;
        boolean oldStrictMode = this.isStrictMode;
        while (this.type != TokenType.EOF && this.type != TokenType.RBRACE) {
            try {
                try {
                    statement(true, allowPropertyFunction, false);
                    allowPropertyFunction = false;
                    if (checkDirective) {
                        Node lastStatement = this.f255lc.getLastStatement();
                        String directive = getDirective(lastStatement);
                        checkDirective = directive != null;
                        if (checkDirective) {
                            if (!oldStrictMode) {
                                if (directiveStmts == null) {
                                    directiveStmts = new ArrayList<>();
                                }
                                directiveStmts.add(lastStatement);
                            }
                            if ("use strict".equals(directive)) {
                                this.isStrictMode = true;
                                FunctionNode function = this.f255lc.getCurrentFunction();
                                this.f255lc.setFlag(this.f255lc.getCurrentFunction(), 4);
                                if (!oldStrictMode && directiveStmts != null) {
                                    for (Node statement : directiveStmts) {
                                        getValue(statement.getToken());
                                    }
                                    verifyStrictIdent(function.getIdent(), "function name");
                                    for (IdentNode param : function.getParameters()) {
                                        verifyStrictIdent(param, "function parameter");
                                    }
                                }
                            } else if (Context.DEBUG && (flag = FunctionNode.getDirectiveFlag(directive)) != 0) {
                                this.f255lc.setFlag(this.f255lc.getCurrentFunction(), flag);
                            }
                        }
                    }
                } catch (Exception e) {
                    recover(e);
                }
                this.stream.commit(this.f254k);
            } finally {
                this.isStrictMode = oldStrictMode;
            }
        }
    }

    private void statement() {
        statement(false, false, false);
    }

    private void statement(boolean topLevel, boolean allowPropertyFunction, boolean singleStatement) {
        if (this.type == TokenType.FUNCTION) {
            functionExpression(true, topLevel);
            return;
        }
        switch (this.type) {
            case EOF:
            case RPAREN:
            case RBRACKET:
                expect(TokenType.SEMICOLON);
                return;
            case EOL:
            case RBRACE:
            case ASSIGN:
            case ASSIGN_ADD:
            case ASSIGN_BIT_AND:
            case ASSIGN_BIT_OR:
            case ASSIGN_BIT_XOR:
            case ASSIGN_DIV:
            case ASSIGN_MOD:
            case ASSIGN_MUL:
            case ASSIGN_SAR:
            case ASSIGN_SHL:
            case ASSIGN_SHR:
            case ASSIGN_SUB:
            default:
                if (useBlockScope() && (this.type == TokenType.LET || this.type == TokenType.CONST)) {
                    if (singleStatement) {
                        throw error(AbstractParser.message("expected.stmt", this.type.getName() + " declaration"), this.token);
                    }
                    variableStatement(this.type, true);
                    return;
                } else if (this.env._const_as_var && this.type == TokenType.CONST) {
                    variableStatement(TokenType.VAR, true);
                    return;
                } else {
                    if (this.type == TokenType.IDENT || isNonStrictModeIdent()) {
                        if (m68T(this.f254k + 1) == TokenType.COLON) {
                            labelStatement();
                            return;
                        } else if (allowPropertyFunction) {
                            String ident = (String) getValue();
                            long propertyToken = this.token;
                            int propertyLine = this.line;
                            if (PropertyDescriptor.GET.equals(ident)) {
                                next();
                                addPropertyFunctionStatement(propertyGetterFunction(propertyToken, propertyLine));
                                return;
                            } else if (PropertyDescriptor.SET.equals(ident)) {
                                next();
                                addPropertyFunctionStatement(propertySetterFunction(propertyToken, propertyLine));
                                return;
                            }
                        }
                    }
                    expressionStatement();
                    return;
                }
            case SEMICOLON:
                emptyStatement();
                return;
            case LBRACE:
                block();
                return;
            case VAR:
                variableStatement(this.type, true);
                return;
            case IF:
                ifStatement();
                return;
            case FOR:
                forStatement();
                return;
            case WHILE:
                whileStatement();
                return;
            case DO:
                doStatement();
                return;
            case CONTINUE:
                continueStatement();
                return;
            case BREAK:
                breakStatement();
                return;
            case RETURN:
                returnStatement();
                return;
            case YIELD:
                yieldStatement();
                return;
            case WITH:
                withStatement();
                return;
            case SWITCH:
                switchStatement();
                return;
            case THROW:
                throwStatement();
                return;
            case TRY:
                tryStatement();
                return;
            case DEBUGGER:
                debuggerStatement();
                return;
        }
    }

    private void addPropertyFunctionStatement(PropertyFunction propertyFunction) {
        FunctionNode fn = propertyFunction.functionNode;
        this.functionDeclarations.add(new ExpressionStatement(fn.getLineNumber(), fn.getToken(), this.finish, fn));
    }

    private void block() {
        appendStatement(new BlockStatement(this.line, getBlock(true)));
    }

    private void statementList() {
        while (this.type != TokenType.EOF) {
            switch (this.type) {
                case EOF:
                case RBRACE:
                case CASE:
                case DEFAULT:
                    return;
                default:
                    statement();
            }
        }
    }

    private void verifyStrictIdent(IdentNode ident, String contextString) {
        if (this.isStrictMode) {
            String name = ident.getName();
            boolean z = true;
            switch (name.hashCode()) {
                case -2035517098:
                    if (name.equals("arguments")) {
                        z = true;
                        break;
                    }
                    break;
                case 3125404:
                    if (name.equals("eval")) {
                        z = false;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                    throw error(AbstractParser.message("strict.name", ident.getName(), contextString), ident.getToken());
                default:
                    if (ident.isFutureStrictName()) {
                        throw error(AbstractParser.message("strict.name", ident.getName(), contextString), ident.getToken());
                    }
                    return;
            }
        }
    }

    private List<VarNode> variableStatement(TokenType varType, boolean isStatement) {
        next();
        List<VarNode> vars = new ArrayList<>();
        int varFlags = 0;
        if (varType == TokenType.LET) {
            varFlags = 0 | 1;
        } else if (varType == TokenType.CONST) {
            varFlags = 0 | 2;
        }
        while (true) {
            int varLine = this.line;
            long varToken = this.token;
            IdentNode name = getIdent();
            verifyStrictIdent(name, "variable name");
            Expression init = null;
            if (this.type == TokenType.ASSIGN) {
                next();
                this.defaultNames.push(name);
                try {
                    init = assignmentExpression(!isStatement);
                    this.defaultNames.pop();
                } catch (Throwable th) {
                    this.defaultNames.pop();
                    throw th;
                }
            } else if (varType == TokenType.CONST) {
                throw error(AbstractParser.message("missing.const.assignment", name.getName()));
            }
            VarNode var = new VarNode(varLine, varToken, this.finish, name.setIsDeclaredHere(), init, varFlags);
            vars.add(var);
            appendStatement(var);
            if (this.type == TokenType.COMMARIGHT) {
                next();
            } else {
                if (isStatement) {
                    boolean semicolon = this.type == TokenType.SEMICOLON;
                    endOfLine();
                    if (semicolon) {
                        this.f255lc.getCurrentBlock().setFinish(this.finish);
                    }
                }
                return vars;
            }
        }
    }

    private void emptyStatement() {
        if (this.env._empty_statements) {
            appendStatement(new EmptyNode(this.line, this.token, Token.descPosition(this.token) + Token.descLength(this.token)));
        }
        next();
    }

    private void expressionStatement() {
        int expressionLine = this.line;
        long expressionToken = this.token;
        Expression expression = expression();
        ExpressionStatement expressionStatement = null;
        if (expression != null) {
            expressionStatement = new ExpressionStatement(expressionLine, expressionToken, this.finish, expression);
            appendStatement(expressionStatement);
        } else {
            expect(null);
        }
        endOfLine();
        if (expressionStatement != null) {
            expressionStatement.setFinish(this.finish);
            this.f255lc.getCurrentBlock().setFinish(this.finish);
        }
    }

    private void ifStatement() {
        int ifLine = this.line;
        long ifToken = this.token;
        next();
        expect(TokenType.LPAREN);
        Expression test = expression();
        expect(TokenType.RPAREN);
        Block pass = getStatement();
        Block fail = null;
        if (this.type == TokenType.ELSE) {
            next();
            fail = getStatement();
        }
        appendStatement(new IfNode(ifLine, ifToken, fail != null ? fail.getFinish() : pass.getFinish(), test, pass, fail));
    }

    private void forStatement() {
        int startLine = this.start;
        Block outer = useBlockScope() ? newBlock() : null;
        ForNode forNode = new ForNode(this.line, this.token, Token.descPosition(this.token), null, 0);
        this.f255lc.push(forNode);
        try {
            next();
            if (!this.env._no_syntax_extensions && this.type == TokenType.IDENT && "each".equals(getValue())) {
                forNode = forNode.setIsForEach(this.f255lc);
                next();
            }
            expect(TokenType.LPAREN);
            List<VarNode> vars = null;
            switch (this.type) {
                case SEMICOLON:
                    break;
                case VAR:
                    vars = variableStatement(this.type, false);
                    break;
                default:
                    if (useBlockScope() && (this.type == TokenType.LET || this.type == TokenType.CONST)) {
                        if (this.type == TokenType.LET) {
                            forNode = forNode.setPerIterationScope(this.f255lc);
                        }
                        vars = variableStatement(this.type, false);
                        break;
                    } else if (this.env._const_as_var && this.type == TokenType.CONST) {
                        vars = variableStatement(TokenType.VAR, false);
                        break;
                    } else {
                        Expression expression = expression(unaryExpression(), TokenType.COMMARIGHT.getPrecedence(), true);
                        forNode = forNode.setInit(this.f255lc, expression);
                        break;
                    }
                    break;
            }
            switch (this.type) {
                case SEMICOLON:
                    if (forNode.isForEach()) {
                        throw error(AbstractParser.message("for.each.without.in", new String[0]), this.token);
                    }
                    expect(TokenType.SEMICOLON);
                    if (this.type != TokenType.SEMICOLON) {
                        forNode = forNode.setTest((LexicalContext) this.f255lc, joinPredecessorExpression());
                    }
                    expect(TokenType.SEMICOLON);
                    if (this.type != TokenType.RPAREN) {
                        forNode = forNode.setModify(this.f255lc, joinPredecessorExpression());
                        break;
                    }
                    break;
                case IN:
                    ForNode forNode2 = forNode.setIsForIn(this.f255lc).setTest((LexicalContext) this.f255lc, new JoinPredecessorExpression());
                    if (vars != null) {
                        if (vars.size() == 1) {
                            forNode2 = forNode2.setInit(this.f255lc, new IdentNode(vars.get(0).getName()));
                        } else {
                            throw error(AbstractParser.message("many.vars.in.for.in.loop", new String[0]), vars.get(1).getToken());
                        }
                    } else {
                        Node init = forNode2.getInit();
                        if (!$assertionsDisabled && init == null) {
                            throw new AssertionError("for..in init expression can not be null here");
                        }
                        if (!(init instanceof AccessNode) && !(init instanceof IndexNode) && !(init instanceof IdentNode)) {
                            throw error(AbstractParser.message("not.lvalue.for.in.loop", new String[0]), init.getToken());
                        }
                        if (init instanceof IdentNode) {
                            if (!checkIdentLValue((IdentNode) init)) {
                                throw error(AbstractParser.message("not.lvalue.for.in.loop", new String[0]), init.getToken());
                            }
                            verifyStrictIdent((IdentNode) init, "for-in iterator");
                        }
                    }
                    next();
                    forNode = forNode2.setModify(this.f255lc, joinPredecessorExpression());
                    break;
                default:
                    expect(TokenType.SEMICOLON);
                    break;
            }
            expect(TokenType.RPAREN);
            Block body = getStatement();
            ForNode forNode3 = forNode.setBody((LexicalContext) this.f255lc, body);
            forNode3.setFinish(body.getFinish());
            appendStatement(forNode3);
            this.f255lc.pop(forNode3);
            if (outer != null) {
                outer.setFinish(forNode3.getFinish());
                appendStatement(new BlockStatement(startLine, restoreBlock(outer)));
            }
        } catch (Throwable th) {
            this.f255lc.pop(forNode);
            throw th;
        }
    }

    private void whileStatement() {
        long whileToken = this.token;
        next();
        WhileNode whileNode = new WhileNode(this.line, whileToken, Token.descPosition(whileToken), false);
        this.f255lc.push(whileNode);
        try {
            expect(TokenType.LPAREN);
            int whileLine = this.line;
            JoinPredecessorExpression test = joinPredecessorExpression();
            expect(TokenType.RPAREN);
            Block body = getStatement();
            WhileNode body2 = new WhileNode(whileLine, whileToken, this.finish, false).setTest((LexicalContext) this.f255lc, test).setBody((LexicalContext) this.f255lc, body);
            whileNode = body2;
            appendStatement(body2);
            this.f255lc.pop(whileNode);
        } catch (Throwable th) {
            this.f255lc.pop(whileNode);
            throw th;
        }
    }

    private void doStatement() {
        long doToken = this.token;
        next();
        WhileNode doWhileNode = new WhileNode(-1, doToken, Token.descPosition(doToken), true);
        this.f255lc.push(doWhileNode);
        try {
            Block body = getStatement();
            expect(TokenType.WHILE);
            expect(TokenType.LPAREN);
            int doLine = this.line;
            JoinPredecessorExpression test = joinPredecessorExpression();
            expect(TokenType.RPAREN);
            if (this.type == TokenType.SEMICOLON) {
                endOfLine();
            }
            doWhileNode.setFinish(this.finish);
            WhileNode test2 = new WhileNode(doLine, doToken, this.finish, true).setBody((LexicalContext) this.f255lc, body).setTest((LexicalContext) this.f255lc, test);
            doWhileNode = test2;
            appendStatement(test2);
            this.f255lc.pop(doWhileNode);
        } catch (Throwable th) {
            this.f255lc.pop(doWhileNode);
            throw th;
        }
    }

    private void continueStatement() {
        int continueLine = this.line;
        long continueToken = this.token;
        nextOrEOL();
        LabelNode labelNode = null;
        switch (this.type) {
            case EOF:
            case EOL:
            case SEMICOLON:
            case RBRACE:
                break;
            default:
                IdentNode ident = getIdent();
                labelNode = this.f255lc.findLabel(ident.getName());
                if (labelNode == null) {
                    throw error(AbstractParser.message("undefined.label", ident.getName()), ident.getToken());
                }
                break;
        }
        String labelName = labelNode == null ? null : labelNode.getLabelName();
        LoopNode targetNode = this.f255lc.getContinueTo(labelName);
        if (targetNode == null) {
            throw error(AbstractParser.message("illegal.continue.stmt", new String[0]), continueToken);
        }
        endOfLine();
        appendStatement(new ContinueNode(continueLine, continueToken, this.finish, labelName));
    }

    private void breakStatement() {
        int breakLine = this.line;
        long breakToken = this.token;
        nextOrEOL();
        LabelNode labelNode = null;
        switch (this.type) {
            case EOF:
            case EOL:
            case SEMICOLON:
            case RBRACE:
                break;
            default:
                IdentNode ident = getIdent();
                labelNode = this.f255lc.findLabel(ident.getName());
                if (labelNode == null) {
                    throw error(AbstractParser.message("undefined.label", ident.getName()), ident.getToken());
                }
                break;
        }
        String labelName = labelNode == null ? null : labelNode.getLabelName();
        BreakableNode targetNode = this.f255lc.getBreakable(labelName);
        if (targetNode == null) {
            throw error(AbstractParser.message("illegal.break.stmt", new String[0]), breakToken);
        }
        endOfLine();
        appendStatement(new BreakNode(breakLine, breakToken, this.finish, labelName));
    }

    private void returnStatement() {
        if (this.f255lc.getCurrentFunction().getKind() == FunctionNode.Kind.SCRIPT) {
            throw error(AbstractParser.message("invalid.return", new String[0]));
        }
        int returnLine = this.line;
        long returnToken = this.token;
        nextOrEOL();
        Expression expression = null;
        switch (this.type) {
            case EOF:
            case EOL:
            case SEMICOLON:
            case RBRACE:
                break;
            default:
                expression = expression();
                break;
        }
        endOfLine();
        appendStatement(new ReturnNode(returnLine, returnToken, this.finish, expression));
    }

    private void yieldStatement() {
        int yieldLine = this.line;
        long yieldToken = this.token;
        nextOrEOL();
        Expression expression = null;
        switch (this.type) {
            case EOF:
            case EOL:
            case SEMICOLON:
            case RBRACE:
                break;
            default:
                expression = expression();
                break;
        }
        endOfLine();
        appendStatement(new ReturnNode(yieldLine, yieldToken, this.finish, expression));
    }

    private void withStatement() {
        int withLine = this.line;
        long withToken = this.token;
        next();
        if (this.isStrictMode) {
            throw error(AbstractParser.message("strict.no.with", new String[0]), withToken);
        }
        WithNode withNode = new WithNode(withLine, withToken, this.finish);
        try {
            this.f255lc.push(withNode);
            expect(TokenType.LPAREN);
            WithNode withNode2 = withNode.setExpression(this.f255lc, expression());
            expect(TokenType.RPAREN);
            withNode = withNode2.setBody(this.f255lc, getStatement());
            this.f255lc.pop(withNode);
            appendStatement(withNode);
        } catch (Throwable th) {
            this.f255lc.pop(withNode);
            throw th;
        }
    }

    private void switchStatement() {
        int switchLine = this.line;
        long switchToken = this.token;
        next();
        SwitchNode switchNode = new SwitchNode(switchLine, switchToken, Token.descPosition(switchToken), null, new ArrayList(), null);
        this.f255lc.push(switchNode);
        try {
            expect(TokenType.LPAREN);
            SwitchNode switchNode2 = switchNode.setExpression(this.f255lc, expression());
            expect(TokenType.RPAREN);
            expect(TokenType.LBRACE);
            List<CaseNode> cases = new ArrayList<>();
            CaseNode defaultCase = null;
            while (this.type != TokenType.RBRACE) {
                Expression caseExpression = null;
                long caseToken = this.token;
                switch (this.type) {
                    case CASE:
                        next();
                        caseExpression = expression();
                        break;
                    case DEFAULT:
                        if (defaultCase != null) {
                            throw error(AbstractParser.message("duplicate.default.in.switch", new String[0]));
                        }
                        next();
                        break;
                    default:
                        expect(TokenType.CASE);
                        break;
                }
                expect(TokenType.COLON);
                Block statements = getBlock(false);
                CaseNode caseNode = new CaseNode(caseToken, this.finish, caseExpression, statements);
                statements.setFinish(this.finish);
                if (caseExpression == null) {
                    defaultCase = caseNode;
                }
                cases.add(caseNode);
            }
            SwitchNode switchNode3 = switchNode2.setCases(this.f255lc, cases, defaultCase);
            next();
            switchNode3.setFinish(this.finish);
            appendStatement(switchNode3);
            this.f255lc.pop(switchNode3);
        } catch (Throwable th) {
            this.f255lc.pop(switchNode);
            throw th;
        }
    }

    private void labelStatement() {
        long labelToken = this.token;
        IdentNode ident = getIdent();
        expect(TokenType.COLON);
        if (this.f255lc.findLabel(ident.getName()) != null) {
            throw error(AbstractParser.message("duplicate.label", ident.getName()), labelToken);
        }
        LabelNode labelNode = new LabelNode(this.line, labelToken, this.finish, ident.getName(), null);
        try {
            this.f255lc.push(labelNode);
            labelNode = labelNode.setBody(this.f255lc, getStatement());
            labelNode.setFinish(this.finish);
            appendStatement(labelNode);
            if (!$assertionsDisabled && !(this.f255lc.peek() instanceof LabelNode)) {
                throw new AssertionError();
            }
            this.f255lc.pop(labelNode);
        } catch (Throwable th) {
            if (!$assertionsDisabled && !(this.f255lc.peek() instanceof LabelNode)) {
                throw new AssertionError();
            }
            this.f255lc.pop(labelNode);
            throw th;
        }
    }

    private void throwStatement() {
        int throwLine = this.line;
        long throwToken = this.token;
        nextOrEOL();
        Expression expression = null;
        switch (this.type) {
            case EOL:
            case SEMICOLON:
            case RBRACE:
                break;
            default:
                expression = expression();
                break;
        }
        if (expression == null) {
            throw error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
        }
        endOfLine();
        appendStatement(new ThrowNode(throwLine, throwToken, this.finish, expression, false));
    }

    private void tryStatement() {
        Expression ifExpression;
        int tryLine = this.line;
        long tryToken = this.token;
        next();
        int startLine = this.line;
        Block outer = newBlock();
        try {
            Block tryBody = getBlock(true);
            List<Block> catchBlocks = new ArrayList<>();
            while (this.type == TokenType.CATCH) {
                int catchLine = this.line;
                long catchToken = this.token;
                next();
                expect(TokenType.LPAREN);
                IdentNode exception = getIdent();
                verifyStrictIdent(exception, "catch argument");
                if (!this.env._no_syntax_extensions && this.type == TokenType.IF) {
                    next();
                    ifExpression = expression();
                } else {
                    ifExpression = null;
                }
                expect(TokenType.RPAREN);
                Block catchBlock = newBlock();
                Block catchBody = getBlock(true);
                CatchNode catchNode = new CatchNode(catchLine, catchToken, this.finish, exception, ifExpression, catchBody, false);
                appendStatement(catchNode);
                catchBlocks.add(restoreBlock(catchBlock));
                if (ifExpression == null) {
                    break;
                }
            }
            Block finallyStatements = null;
            if (this.type == TokenType.FINALLY) {
                next();
                finallyStatements = getBlock(true);
            }
            if (catchBlocks.isEmpty() && finallyStatements == null) {
                throw error(AbstractParser.message("missing.catch.or.finally", new String[0]), tryToken);
            }
            TryNode tryNode = new TryNode(tryLine, tryToken, Token.descPosition(tryToken), tryBody, catchBlocks, finallyStatements);
            if (!$assertionsDisabled && this.f255lc.peek() != outer) {
                throw new AssertionError();
            }
            appendStatement(tryNode);
            tryNode.setFinish(this.finish);
            outer.setFinish(this.finish);
            appendStatement(new BlockStatement(startLine, restoreBlock(outer)));
        } catch (Throwable th) {
            restoreBlock(outer);
            throw th;
        }
    }

    private void debuggerStatement() {
        int debuggerLine = this.line;
        long debuggerToken = this.token;
        next();
        endOfLine();
        appendStatement(new ExpressionStatement(debuggerLine, debuggerToken, this.finish, new RuntimeNode(debuggerToken, this.finish, RuntimeNode.Request.DEBUGGER, new ArrayList())));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Expression primaryExpression() {
        int primaryLine = this.line;
        long primaryToken = this.token;
        switch (this.type) {
            case LBRACE:
                return objectLiteral();
            case VAR:
            case IF:
            case FOR:
            case WHILE:
            case DO:
            case CONTINUE:
            case BREAK:
            case RETURN:
            case YIELD:
            case WITH:
            case SWITCH:
            case THROW:
            case TRY:
            case DEBUGGER:
            case RPAREN:
            case RBRACKET:
            case CASE:
            case DEFAULT:
            case IN:
            default:
                if (this.lexer.scanLiteral(primaryToken, this.type, this.lineInfoReceiver)) {
                    next();
                    return getLiteral();
                } else if (isNonStrictModeIdent()) {
                    return getIdent();
                } else {
                    return null;
                }
            case THIS:
                String name = this.type.getName();
                next();
                this.f255lc.setFlag(this.f255lc.getCurrentFunction(), 32768);
                return new IdentNode(primaryToken, this.finish, name);
            case IDENT:
                IdentNode ident = getIdent();
                if (ident != null) {
                    detectSpecialProperty(ident);
                    return ident;
                }
                return null;
            case OCTAL:
                if (this.isStrictMode) {
                    throw error(AbstractParser.message("strict.no.octal", new String[0]), this.token);
                }
                break;
            case STRING:
            case ESCSTRING:
            case DECIMAL:
            case HEXADECIMAL:
            case FLOATING:
            case REGEX:
            case XML:
                break;
            case EXECSTRING:
                return execString(primaryLine, primaryToken);
            case FALSE:
                next();
                return LiteralNode.newInstance(primaryToken, this.finish, false);
            case TRUE:
                next();
                return LiteralNode.newInstance(primaryToken, this.finish, true);
            case NULL:
                next();
                return LiteralNode.newInstance(primaryToken, this.finish);
            case LBRACKET:
                return arrayLiteral();
            case LPAREN:
                next();
                Expression expression = expression();
                expect(TokenType.RPAREN);
                return expression;
        }
        return getLiteral();
    }

    CallNode execString(int primaryLine, long primaryToken) {
        IdentNode execIdent = new IdentNode(primaryToken, this.finish, ScriptingFunctions.EXEC_NAME);
        next();
        expect(TokenType.LBRACE);
        List<Expression> arguments = Collections.singletonList(expression());
        expect(TokenType.RBRACE);
        return new CallNode(primaryLine, primaryToken, this.finish, execIdent, arguments, false);
    }

    private LiteralNode<Expression[]> arrayLiteral() {
        long arrayToken = this.token;
        next();
        List<Expression> elements = new ArrayList<>();
        boolean z = true;
        while (true) {
            boolean elision = z;
            switch (this.type) {
                case RBRACKET:
                    next();
                    return LiteralNode.newInstance(arrayToken, this.finish, elements);
                case COMMARIGHT:
                    next();
                    if (elision) {
                        elements.add(null);
                    }
                    z = true;
                    break;
                default:
                    if (!elision) {
                        throw error(AbstractParser.message("expected.comma", this.type.getNameOrType()));
                    }
                    Expression expression = assignmentExpression(false);
                    if (expression != null) {
                        elements.add(expression);
                    } else {
                        expect(TokenType.RBRACKET);
                    }
                    z = false;
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x01d7, code lost:
        throw error(jdk.nashorn.internal.parser.AbstractParser.message("property.redefinition", r0), r0.getToken());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private jdk.nashorn.internal.p001ir.ObjectNode objectLiteral() {
        /*
            Method dump skipped, instructions count: 557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.parser.Parser.objectLiteral():jdk.nashorn.internal.ir.ObjectNode");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private PropertyKey propertyName() {
        switch (this.type) {
            case IDENT:
                return getIdent().setIsPropertyName();
            case OCTAL:
                if (this.isStrictMode) {
                    throw error(AbstractParser.message("strict.no.octal", new String[0]), this.token);
                }
                break;
            case STRING:
            case ESCSTRING:
            case DECIMAL:
            case HEXADECIMAL:
            case FLOATING:
                break;
            default:
                return getIdentifierName().setIsPropertyName();
        }
        return getLiteral();
    }

    private PropertyNode propertyAssignment() {
        PropertyKey propertyName;
        long propertyToken = this.token;
        int functionLine = this.line;
        if (this.type == TokenType.IDENT) {
            String ident = (String) expectValue(TokenType.IDENT);
            if (this.type != TokenType.COLON) {
                boolean z = true;
                switch (ident.hashCode()) {
                    case 102230:
                        if (ident.equals(PropertyDescriptor.GET)) {
                            z = false;
                            break;
                        }
                        break;
                    case 113762:
                        if (ident.equals(PropertyDescriptor.SET)) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        PropertyFunction getter = propertyGetterFunction(propertyToken, functionLine);
                        return new PropertyNode(propertyToken, this.finish, getter.ident, null, getter.functionNode, null);
                    case true:
                        PropertyFunction setter = propertySetterFunction(propertyToken, functionLine);
                        return new PropertyNode(propertyToken, this.finish, setter.ident, null, null, setter.functionNode);
                }
            }
            propertyName = createIdentNode(propertyToken, this.finish, ident).setIsPropertyName();
        } else {
            propertyName = propertyName();
        }
        expect(TokenType.COLON);
        this.defaultNames.push(propertyName);
        try {
            PropertyNode propertyNode = new PropertyNode(propertyToken, this.finish, propertyName, assignmentExpression(false), null, null);
            this.defaultNames.pop();
            return propertyNode;
        } catch (Throwable th) {
            this.defaultNames.pop();
            throw th;
        }
    }

    private PropertyFunction propertyGetterFunction(long getSetToken, int functionLine) {
        PropertyKey getIdent = propertyName();
        String getterName = getIdent.getPropertyName();
        IdentNode getNameNode = createIdentNode(((Node) getIdent).getToken(), this.finish, NameCodec.encode("get " + getterName));
        expect(TokenType.LPAREN);
        expect(TokenType.RPAREN);
        FunctionNode functionNode = functionBody(getSetToken, getNameNode, new ArrayList(), FunctionNode.Kind.GETTER, functionLine);
        return new PropertyFunction(getIdent, functionNode);
    }

    private PropertyFunction propertySetterFunction(long getSetToken, int functionLine) {
        IdentNode argIdent;
        PropertyKey setIdent = propertyName();
        String setterName = setIdent.getPropertyName();
        IdentNode setNameNode = createIdentNode(((Node) setIdent).getToken(), this.finish, NameCodec.encode("set " + setterName));
        expect(TokenType.LPAREN);
        if (this.type == TokenType.IDENT || isNonStrictModeIdent()) {
            argIdent = getIdent();
            verifyStrictIdent(argIdent, "setter argument");
        } else {
            argIdent = null;
        }
        expect(TokenType.RPAREN);
        List<IdentNode> parameters = new ArrayList<>();
        if (argIdent != null) {
            parameters.add(argIdent);
        }
        FunctionNode functionNode = functionBody(getSetToken, setNameNode, parameters, FunctionNode.Kind.SETTER, functionLine);
        return new PropertyFunction(setIdent, functionNode);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/Parser$PropertyFunction.class */
    public static class PropertyFunction {
        final PropertyKey ident;
        final FunctionNode functionNode;

        PropertyFunction(PropertyKey ident, FunctionNode function) {
            this.ident = ident;
            this.functionNode = function;
        }
    }

    private Expression leftHandSideExpression() {
        int callLine = this.line;
        long callToken = this.token;
        Expression lhs = memberExpression();
        if (this.type == TokenType.LPAREN) {
            List<Expression> arguments = optimizeList(argumentList());
            if (lhs instanceof IdentNode) {
                detectSpecialFunction((IdentNode) lhs);
            }
            lhs = new CallNode(callLine, callToken, this.finish, lhs, arguments, false);
        }
        while (true) {
            int callLine2 = this.line;
            long callToken2 = this.token;
            switch (this.type) {
                case LBRACKET:
                    next();
                    Expression rhs = expression();
                    expect(TokenType.RBRACKET);
                    lhs = new IndexNode(callToken2, this.finish, lhs, rhs);
                    break;
                case LPAREN:
                    List<Expression> arguments2 = optimizeList(argumentList());
                    lhs = new CallNode(callLine2, callToken2, this.finish, lhs, arguments2, false);
                    break;
                case COMMARIGHT:
                default:
                    return lhs;
                case PERIOD:
                    next();
                    IdentNode property = getIdentifierName();
                    lhs = new AccessNode(callToken2, this.finish, lhs, property.getName());
                    break;
            }
        }
    }

    private Expression newExpression() {
        ArrayList<Expression> arguments;
        long newToken = this.token;
        next();
        int callLine = this.line;
        Expression constructor = memberExpression();
        if (constructor == null) {
            return null;
        }
        if (this.type == TokenType.LPAREN) {
            arguments = argumentList();
        } else {
            arguments = new ArrayList<>();
        }
        if (!this.env._no_syntax_extensions && this.type == TokenType.LBRACE) {
            arguments.add(objectLiteral());
        }
        CallNode callNode = new CallNode(callLine, constructor.getToken(), this.finish, constructor, optimizeList(arguments), true);
        return new UnaryNode(newToken, callNode);
    }

    private Expression memberExpression() {
        Expression expression;
        switch (this.type) {
            case NEW:
                expression = newExpression();
                break;
            case FUNCTION:
                expression = functionExpression(false, false);
                break;
            default:
                expression = primaryExpression();
                break;
        }
        while (true) {
            Expression lhs = expression;
            long callToken = this.token;
            switch (this.type) {
                case LBRACKET:
                    next();
                    Expression index = expression();
                    expect(TokenType.RBRACKET);
                    expression = new IndexNode(callToken, this.finish, lhs, index);
                    break;
                case PERIOD:
                    if (lhs == null) {
                        throw error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
                    }
                    next();
                    IdentNode property = getIdentifierName();
                    expression = new AccessNode(callToken, this.finish, lhs, property.getName());
                    break;
                default:
                    return lhs;
            }
        }
    }

    private ArrayList<Expression> argumentList() {
        ArrayList<Expression> nodeList = new ArrayList<>();
        next();
        boolean first = true;
        while (this.type != TokenType.RPAREN) {
            if (!first) {
                expect(TokenType.COMMARIGHT);
            } else {
                first = false;
            }
            nodeList.add(assignmentExpression(false));
        }
        expect(TokenType.RPAREN);
        return nodeList;
    }

    private static <T> List<T> optimizeList(ArrayList<T> list) {
        switch (list.size()) {
            case 0:
                return Collections.emptyList();
            case 1:
                return Collections.singletonList(list.get(0));
            default:
                list.trimToSize();
                return list;
        }
    }

    private Expression functionExpression(boolean isStatement, boolean topLevel) {
        long functionToken = this.token;
        int functionLine = this.line;
        next();
        IdentNode name = null;
        if (this.type == TokenType.IDENT || isNonStrictModeIdent()) {
            name = getIdent();
            verifyStrictIdent(name, "function name");
        } else if (isStatement && this.env._no_syntax_extensions && this.reparsedFunction == null) {
            expect(TokenType.IDENT);
        }
        boolean isAnonymous = false;
        if (name == null) {
            String tmpName = getDefaultValidFunctionName(functionLine, isStatement);
            name = new IdentNode(functionToken, Token.descPosition(functionToken), tmpName);
            isAnonymous = true;
        }
        expect(TokenType.LPAREN);
        List<IdentNode> parameters = formalParameterList();
        expect(TokenType.RPAREN);
        hideDefaultName();
        try {
            FunctionNode functionNode = functionBody(functionToken, name, parameters, FunctionNode.Kind.NORMAL, functionLine);
            this.defaultNames.pop();
            if (isStatement) {
                if (topLevel || useBlockScope()) {
                    functionNode = functionNode.setFlag((LexicalContext) this.f255lc, 2);
                } else if (this.isStrictMode) {
                    throw error(JSErrorType.SYNTAX_ERROR, AbstractParser.message("strict.no.func.decl.here", new String[0]), functionToken);
                } else {
                    if (this.env._function_statement == ScriptEnvironment.FunctionStatementBehavior.ERROR) {
                        throw error(JSErrorType.SYNTAX_ERROR, AbstractParser.message("no.func.decl.here", new String[0]), functionToken);
                    }
                    if (this.env._function_statement == ScriptEnvironment.FunctionStatementBehavior.WARNING) {
                        warning(JSErrorType.SYNTAX_ERROR, AbstractParser.message("no.func.decl.here.warn", new String[0]), functionToken);
                    }
                }
                if (isArguments(name)) {
                    this.f255lc.setFlag(this.f255lc.getCurrentFunction(), 256);
                }
            }
            if (isAnonymous) {
                functionNode = functionNode.setFlag((LexicalContext) this.f255lc, 1);
            }
            int arity = parameters.size();
            boolean strict = functionNode.isStrict();
            if (arity > 1) {
                HashSet<String> parametersSet = new HashSet<>(arity);
                for (int i = arity - 1; i >= 0; i--) {
                    IdentNode parameter = parameters.get(i);
                    String parameterName = parameter.getName();
                    if (isArguments(parameterName)) {
                        functionNode = functionNode.setFlag((LexicalContext) this.f255lc, 256);
                    }
                    if (parametersSet.contains(parameterName)) {
                        if (strict) {
                            throw error(AbstractParser.message("strict.param.redefinition", parameterName), parameter.getToken());
                        }
                        parameterName = functionNode.uniqueName(parameterName);
                        long parameterToken = parameter.getToken();
                        parameters.set(i, new IdentNode(parameterToken, Token.descPosition(parameterToken), functionNode.uniqueName(parameterName)));
                    }
                    parametersSet.add(parameterName);
                }
            } else if (arity == 1 && isArguments(parameters.get(0))) {
                functionNode = functionNode.setFlag((LexicalContext) this.f255lc, 256);
            }
            if (isStatement) {
                if (isAnonymous) {
                    appendStatement(new ExpressionStatement(functionLine, functionToken, this.finish, functionNode));
                    return functionNode;
                }
                int varFlags = (topLevel || !useBlockScope()) ? 0 : 1;
                VarNode varNode = new VarNode(functionLine, functionToken, this.finish, name, functionNode, varFlags);
                if (topLevel) {
                    this.functionDeclarations.add(varNode);
                } else if (useBlockScope()) {
                    prependStatement(varNode);
                } else {
                    appendStatement(varNode);
                }
            }
            return functionNode;
        } catch (Throwable th) {
            this.defaultNames.pop();
            throw th;
        }
    }

    private String getDefaultValidFunctionName(int functionLine, boolean isStatement) {
        String defaultFunctionName = getDefaultFunctionName();
        if (isValidIdentifier(defaultFunctionName)) {
            if (isStatement) {
                return CompilerConstants.ANON_FUNCTION_PREFIX.symbolName() + defaultFunctionName;
            }
            return defaultFunctionName;
        }
        return CompilerConstants.ANON_FUNCTION_PREFIX.symbolName() + functionLine;
    }

    private static boolean isValidIdentifier(String name) {
        if (name == null || name.isEmpty() || !Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }
        for (int i = 1; i < name.length(); i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String getDefaultFunctionName() {
        if (!this.defaultNames.isEmpty()) {
            Object nameExpr = this.defaultNames.peek();
            if (nameExpr instanceof PropertyKey) {
                markDefaultNameUsed();
                return ((PropertyKey) nameExpr).getPropertyName();
            } else if (nameExpr instanceof AccessNode) {
                markDefaultNameUsed();
                return ((AccessNode) nameExpr).getProperty();
            } else {
                return null;
            }
        }
        return null;
    }

    private void markDefaultNameUsed() {
        this.defaultNames.pop();
        hideDefaultName();
    }

    private void hideDefaultName() {
        this.defaultNames.push("");
    }

    private List<IdentNode> formalParameterList() {
        return formalParameterList(TokenType.RPAREN);
    }

    private List<IdentNode> formalParameterList(TokenType endType) {
        ArrayList<IdentNode> parameters = new ArrayList<>();
        boolean first = true;
        while (this.type != endType) {
            if (!first) {
                expect(TokenType.COMMARIGHT);
            } else {
                first = false;
            }
            IdentNode ident = getIdent();
            verifyStrictIdent(ident, "function parameter");
            parameters.add(ident);
        }
        parameters.trimToSize();
        return parameters;
    }

    private FunctionNode functionBody(long firstToken, IdentNode ident, List<IdentNode> parameters, FunctionNode.Kind kind, int functionLine) {
        RecompilableScriptFunctionData data;
        long lastToken = 0;
        Object endParserState = null;
        try {
            FunctionNode functionNode = newFunctionNode(firstToken, ident, parameters, kind, functionLine);
            if (!$assertionsDisabled && functionNode == null) {
                throw new AssertionError();
            }
            int functionId = functionNode.getId();
            boolean parseBody = this.reparsedFunction == null || functionId <= this.reparsedFunction.getFunctionNodeId();
            if (!this.env._no_syntax_extensions && this.type != TokenType.LBRACE) {
                Expression expr = assignmentExpression(true);
                lastToken = this.previousToken;
                if (!$assertionsDisabled && this.f255lc.getCurrentBlock() != this.f255lc.getFunctionBody(functionNode)) {
                    throw new AssertionError();
                }
                int lastFinish = Token.descPosition(lastToken) + (Token.descType(lastToken) == TokenType.EOL ? 0 : Token.descLength(lastToken));
                if (parseBody) {
                    ReturnNode returnNode = new ReturnNode(functionNode.getLineNumber(), expr.getToken(), lastFinish, expr);
                    appendStatement(returnNode);
                }
                functionNode.setFinish(lastFinish);
            } else {
                expectDontAdvance(TokenType.LBRACE);
                if (parseBody || !skipFunctionBody(functionNode)) {
                    next();
                    List<Statement> prevFunctionDecls = this.functionDeclarations;
                    this.functionDeclarations = new ArrayList();
                    sourceElements(false);
                    addFunctionDeclarations(functionNode);
                    this.functionDeclarations = prevFunctionDecls;
                    lastToken = this.token;
                    if (parseBody) {
                        endParserState = new ParserState(Token.descPosition(this.token), this.line, this.linePosition);
                    }
                }
                expect(TokenType.RBRACE);
                functionNode.setFinish(this.finish);
            }
            FunctionNode functionNode2 = restoreFunctionNode(functionNode, lastToken);
            if (parseBody) {
                functionNode2 = functionNode2.setEndParserState(this.f255lc, endParserState);
            } else if (functionNode2.getBody().getStatementCount() > 0) {
                functionNode2 = functionNode2.setBody(null, functionNode2.getBody().setStatements(null, Collections.emptyList()));
            }
            if (this.reparsedFunction != null && (data = this.reparsedFunction.getScriptFunctionData(functionNode2.getId())) != null) {
                functionNode2 = functionNode2.setFlags((LexicalContext) this.f255lc, data.getFunctionFlags());
                if (functionNode2.hasNestedEval()) {
                    if (!$assertionsDisabled && !functionNode2.hasScopeBlock()) {
                        throw new AssertionError();
                    }
                    functionNode2 = functionNode2.setBody(this.f255lc, functionNode2.getBody().setNeedsScope(null));
                }
            }
            printAST(functionNode2);
            return functionNode2;
        } catch (Throwable th) {
            restoreFunctionNode(null, 0L);
            throw th;
        }
    }

    private boolean skipFunctionBody(FunctionNode functionNode) {
        RecompilableScriptFunctionData data;
        if (this.reparsedFunction == null || (data = this.reparsedFunction.getScriptFunctionData(functionNode.getId())) == null) {
            return false;
        }
        ParserState parserState = (ParserState) data.getEndParserState();
        if (!$assertionsDisabled && parserState == null) {
            throw new AssertionError();
        }
        this.stream.reset();
        this.lexer = parserState.createLexer(this.source, this.lexer, this.stream, this.scripting && !this.env._no_syntax_extensions);
        this.line = parserState.line;
        this.linePosition = parserState.linePosition;
        this.type = TokenType.SEMICOLON;
        this.f254k = -1;
        next();
        return true;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/Parser$ParserState.class */
    public static class ParserState implements Serializable {
        private final int position;
        private final int line;
        private final int linePosition;
        private static final long serialVersionUID = -2382565130754093694L;

        ParserState(int position, int line, int linePosition) {
            this.position = position;
            this.line = line;
            this.linePosition = linePosition;
        }

        Lexer createLexer(Source source, Lexer lexer, TokenStream stream, boolean scripting) {
            Lexer newLexer = new Lexer(source, this.position, lexer.limit - this.position, stream, scripting, true);
            newLexer.restoreState(new Lexer.State(this.position, Integer.MAX_VALUE, this.line, -1, this.linePosition, TokenType.SEMICOLON));
            return newLexer;
        }
    }

    private void printAST(FunctionNode functionNode) {
        if (functionNode.getFlag(524288)) {
            this.env.getErr().println(new ASTWriter(functionNode));
        }
        if (functionNode.getFlag(131072)) {
            this.env.getErr().println(new PrintVisitor(functionNode, true, false));
        }
    }

    private void addFunctionDeclarations(FunctionNode functionNode) {
        VarNode lastDecl = null;
        for (int i = this.functionDeclarations.size() - 1; i >= 0; i--) {
            Statement decl = this.functionDeclarations.get(i);
            if (lastDecl == null && (decl instanceof VarNode)) {
                Statement flag = ((VarNode) decl).setFlag(4);
                lastDecl = flag;
                decl = flag;
                this.f255lc.setFlag(functionNode, 1024);
            }
            prependStatement(decl);
        }
    }

    private RuntimeNode referenceError(Expression lhs, Expression rhs, boolean earlyError) {
        if (earlyError) {
            throw error(JSErrorType.REFERENCE_ERROR, AbstractParser.message("invalid.lvalue", new String[0]), lhs.getToken());
        }
        ArrayList<Expression> args = new ArrayList<>();
        args.add(lhs);
        if (rhs == null) {
            args.add(LiteralNode.newInstance(lhs.getToken(), lhs.getFinish()));
        } else {
            args.add(rhs);
        }
        args.add(LiteralNode.newInstance(lhs.getToken(), lhs.getFinish(), lhs.toString()));
        return new RuntimeNode(lhs.getToken(), lhs.getFinish(), RuntimeNode.Request.REFERENCE_ERROR, args);
    }

    private Expression unaryExpression() {
        int unaryLine = this.line;
        long unaryToken = this.token;
        switch (this.type) {
            case DELETE:
                next();
                Expression expr = unaryExpression();
                if ((expr instanceof BaseNode) || (expr instanceof IdentNode)) {
                    return new UnaryNode(unaryToken, expr);
                }
                appendStatement(new ExpressionStatement(unaryLine, unaryToken, this.finish, expr));
                return LiteralNode.newInstance(unaryToken, this.finish, true);
            case VOID:
            case TYPEOF:
            case ADD:
            case SUB:
            case BIT_NOT:
            case NOT:
                next();
                return new UnaryNode(unaryToken, unaryExpression());
            case INCPREFIX:
            case DECPREFIX:
                TokenType opType = this.type;
                next();
                Expression lhs = leftHandSideExpression();
                if (lhs == null) {
                    throw error(AbstractParser.message("expected.lvalue", this.type.getNameOrType()));
                }
                if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode) && !(lhs instanceof IdentNode)) {
                    return referenceError(lhs, null, this.env._early_lvalue_error);
                }
                if (lhs instanceof IdentNode) {
                    if (!checkIdentLValue((IdentNode) lhs)) {
                        return referenceError(lhs, null, false);
                    }
                    verifyStrictIdent((IdentNode) lhs, "operand for " + opType.getName() + " operator");
                }
                return incDecExpression(unaryToken, opType, lhs, false);
            default:
                Expression expression = leftHandSideExpression();
                if (this.last != TokenType.EOL) {
                    switch (this.type) {
                        case INCPREFIX:
                        case DECPREFIX:
                            TokenType opType2 = this.type;
                            if (expression == null) {
                                throw error(AbstractParser.message("expected.lvalue", this.type.getNameOrType()));
                            }
                            if (!(expression instanceof AccessNode) && !(expression instanceof IndexNode) && !(expression instanceof IdentNode)) {
                                next();
                                return referenceError(expression, null, this.env._early_lvalue_error);
                            }
                            if (expression instanceof IdentNode) {
                                if (!checkIdentLValue((IdentNode) expression)) {
                                    next();
                                    return referenceError(expression, null, false);
                                }
                                verifyStrictIdent((IdentNode) expression, "operand for " + opType2.getName() + " operator");
                            }
                            expression = incDecExpression(this.token, this.type, expression, true);
                            next();
                            break;
                            break;
                    }
                }
                if (expression == null) {
                    throw error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
                }
                return expression;
        }
    }

    private Expression expression() {
        return expression(unaryExpression(), TokenType.COMMARIGHT.getPrecedence(), false);
    }

    private JoinPredecessorExpression joinPredecessorExpression() {
        return new JoinPredecessorExpression(expression());
    }

    private Expression expression(Expression exprLhs, int minPrecedence, boolean noIn) {
        Expression expression;
        int precedence = this.type.getPrecedence();
        Expression lhs = exprLhs;
        while (this.type.isOperator(noIn) && precedence >= minPrecedence) {
            long op = this.token;
            if (this.type == TokenType.TERNARY) {
                next();
                Expression trueExpr = expression(unaryExpression(), TokenType.ASSIGN.getPrecedence(), false);
                expect(TokenType.COLON);
                Expression falseExpr = expression(unaryExpression(), TokenType.ASSIGN.getPrecedence(), noIn);
                expression = new TernaryNode(op, lhs, new JoinPredecessorExpression(trueExpr), new JoinPredecessorExpression(falseExpr));
            } else {
                next();
                boolean isAssign = Token.descType(op) == TokenType.ASSIGN;
                if (isAssign) {
                    this.defaultNames.push(lhs);
                }
                try {
                    Expression rhs = unaryExpression();
                    int nextPrecedence = this.type.getPrecedence();
                    while (this.type.isOperator(noIn) && (nextPrecedence > precedence || (nextPrecedence == precedence && !this.type.isLeftAssociative()))) {
                        rhs = expression(rhs, nextPrecedence, noIn);
                        nextPrecedence = this.type.getPrecedence();
                    }
                    expression = verifyAssignment(op, lhs, rhs);
                } finally {
                    if (isAssign) {
                        this.defaultNames.pop();
                    }
                }
            }
            lhs = expression;
            precedence = this.type.getPrecedence();
        }
        return lhs;
    }

    private Expression assignmentExpression(boolean noIn) {
        return expression(unaryExpression(), TokenType.ASSIGN.getPrecedence(), noIn);
    }

    private void endOfLine() {
        switch (this.type) {
            case EOF:
            case RBRACE:
            case RPAREN:
            case RBRACKET:
                return;
            case EOL:
            case SEMICOLON:
                next();
                return;
            default:
                if (this.last != TokenType.EOL) {
                    expect(TokenType.SEMICOLON);
                    return;
                }
                return;
        }
    }

    public String toString() {
        return "'JavaScript Parsing'";
    }

    private static void markEval(LexicalContext lc) {
        Iterator<FunctionNode> iter = lc.getFunctions();
        boolean flaggedCurrentFn = false;
        while (iter.hasNext()) {
            FunctionNode fn = iter.next();
            if (!flaggedCurrentFn) {
                lc.setFlag(fn, 32);
                flaggedCurrentFn = true;
            } else {
                lc.setFlag(fn, 64);
            }
            lc.setBlockNeedsScope(lc.getFunctionBody(fn));
        }
    }

    private void prependStatement(Statement statement) {
        this.f255lc.prependStatement(statement);
    }

    private void appendStatement(Statement statement) {
        this.f255lc.appendStatement(statement);
    }
}
