package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

/* JADX INFO: Access modifiers changed from: package-private */
@Logger(name = "symbols")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/AssignSymbols.class */
public final class AssignSymbols extends SimpleNodeVisitor implements Loggable {
    private final DebugLogger log;
    private final boolean debug;
    private final Deque<Set<String>> thisProperties = new ArrayDeque();
    private final Map<String, Symbol> globalSymbols = new HashMap();
    private final Compiler compiler;
    private final boolean isOnDemand;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AssignSymbols.class.desiredAssertionStatus();
    }

    private static boolean isParamOrVar(IdentNode identNode) {
        Symbol symbol = identNode.getSymbol();
        return symbol.isParam() || symbol.isVar();
    }

    private static String name(Node node) {
        String cn = node.getClass().getName();
        int lastDot = cn.lastIndexOf(46);
        if (lastDot == -1) {
            return cn;
        }
        return cn.substring(lastDot + 1);
    }

    private static FunctionNode removeUnusedSlots(FunctionNode functionNode) {
        Symbol selfSymbol;
        if (!functionNode.needsCallee()) {
            functionNode.compilerConstant(CompilerConstants.CALLEE).setNeedsSlot(false);
        }
        if (!functionNode.hasScopeBlock() && !functionNode.needsParentScope()) {
            functionNode.compilerConstant(CompilerConstants.SCOPE).setNeedsSlot(false);
        }
        if (functionNode.isNamedFunctionExpression() && !functionNode.usesSelfSymbol() && (selfSymbol = functionNode.getBody().getExistingSymbol(functionNode.getIdent().getName())) != null && selfSymbol.isFunctionSelf()) {
            selfSymbol.setNeedsSlot(false);
            selfSymbol.clearFlag(2);
        }
        return functionNode;
    }

    public AssignSymbols(Compiler compiler) {
        this.compiler = compiler;
        this.log = initLogger(compiler.getContext());
        this.debug = this.log.isEnabled();
        this.isOnDemand = compiler.isOnDemandCompilation();
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

    private void acceptDeclarations(FunctionNode functionNode, final Block body) {
        body.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.AssignSymbols.1
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterDefault(Node node) {
                return !(node instanceof Expression);
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveVarNode(VarNode varNode) {
                IdentNode ident = varNode.getName();
                boolean blockScoped = varNode.isBlockScoped();
                if (blockScoped && this.f247lc.inUnprotectedSwitchContext()) {
                    AssignSymbols.this.throwUnprotectedSwitchError(varNode);
                }
                Block block = blockScoped ? this.f247lc.getCurrentBlock() : body;
                Symbol symbol = AssignSymbols.this.defineSymbol(block, ident.getName(), ident, varNode.getSymbolFlags());
                if (varNode.isFunctionDeclaration()) {
                    symbol.setIsFunctionDeclaration();
                }
                return varNode.setName(ident.setSymbol(symbol));
            }
        });
    }

    private IdentNode compilerConstantIdentifier(CompilerConstants cc) {
        return createImplicitIdentifier(cc.symbolName()).setSymbol(this.f247lc.getCurrentFunction().compilerConstant(cc));
    }

    private IdentNode createImplicitIdentifier(String name) {
        FunctionNode fn = this.f247lc.getCurrentFunction();
        return new IdentNode(fn.getToken(), fn.getFinish(), name);
    }

    private Symbol createSymbol(String name, int flags) {
        if ((flags & 3) == 1) {
            Symbol global = this.globalSymbols.get(name);
            if (global == null) {
                global = new Symbol(name, flags);
                this.globalSymbols.put(name, global);
            }
            return global;
        }
        return new Symbol(name, flags);
    }

    private VarNode createSyntheticInitializer(IdentNode name, CompilerConstants initConstant, FunctionNode fn) {
        IdentNode init = compilerConstantIdentifier(initConstant);
        if ($assertionsDisabled || (init.getSymbol() != null && init.getSymbol().isBytecodeLocal())) {
            VarNode synthVar = new VarNode(fn.getLineNumber(), fn.getToken(), fn.getFinish(), name, init);
            Symbol nameSymbol = fn.getBody().getExistingSymbol(name.getName());
            if (!$assertionsDisabled && nameSymbol == null) {
                throw new AssertionError();
            }
            return (VarNode) synthVar.setName(name.setSymbol(nameSymbol)).accept(this);
        }
        throw new AssertionError();
    }

    private FunctionNode createSyntheticInitializers(FunctionNode functionNode) {
        ArrayList arrayList = new ArrayList(2);
        Block body = functionNode.getBody();
        this.f247lc.push(body);
        try {
            if (functionNode.usesSelfSymbol()) {
                arrayList.add(createSyntheticInitializer(functionNode.getIdent(), CompilerConstants.CALLEE, functionNode));
            }
            if (functionNode.needsArguments()) {
                arrayList.add(createSyntheticInitializer(createImplicitIdentifier(CompilerConstants.ARGUMENTS_VAR.symbolName()), CompilerConstants.ARGUMENTS, functionNode));
            }
            if (arrayList.isEmpty()) {
                return functionNode;
            }
            ListIterator<VarNode> it = arrayList.listIterator();
            while (it.hasNext()) {
                it.set((VarNode) it.next().accept(this));
            }
            this.f247lc.pop(body);
            List<Statement> stmts = body.getStatements();
            List<Statement> newStatements = new ArrayList<>(stmts.size() + arrayList.size());
            newStatements.addAll(arrayList);
            newStatements.addAll(stmts);
            return functionNode.setBody(this.f247lc, body.setStatements(this.f247lc, newStatements));
        } finally {
            this.f247lc.pop(body);
        }
    }

    public Symbol defineSymbol(Block block, String name, Node origin, int symbolFlags) {
        FunctionNode function;
        Symbol symbol;
        Block symbolBlock;
        int flags = symbolFlags;
        boolean isBlockScope = ((flags & 16) == 0 && (flags & 32) == 0) ? false : true;
        boolean isGlobal = (flags & 3) == 1;
        if (isBlockScope) {
            symbol = block.getExistingSymbol(name);
            function = this.f247lc.getCurrentFunction();
        } else {
            symbol = findSymbol(block, name);
            function = this.f247lc.getFunction(block);
        }
        if (isGlobal) {
            flags |= 4;
        }
        if (this.f247lc.getCurrentFunction().isProgram()) {
            flags |= 512;
        }
        boolean isParam = (flags & 3) == 3;
        boolean isVar = (flags & 3) == 2;
        if (symbol != null) {
            if (isParam) {
                if (!isLocal(function, symbol)) {
                    symbol = null;
                } else if (symbol.isParam()) {
                    throw new AssertionError("duplicate parameter");
                }
            } else if (isVar) {
                if (isBlockScope) {
                    if (symbol.hasBeenDeclared()) {
                        throwParserException(ECMAErrors.getMessage("syntax.error.redeclare.variable", name), origin);
                    } else {
                        symbol.setHasBeenDeclared();
                        if (function.isProgram() && function.getBody() == block) {
                            symbol.setIsScope();
                        }
                    }
                } else if ((flags & 64) != 0) {
                    symbol = null;
                } else {
                    if (symbol.isBlockScoped() && isLocal(this.f247lc.getCurrentFunction(), symbol)) {
                        throwParserException(ECMAErrors.getMessage("syntax.error.redeclare.variable", name), origin);
                    }
                    if (!isLocal(function, symbol) || symbol.less(2)) {
                        symbol = null;
                    }
                }
            }
        }
        if (symbol == null) {
            if (isVar && ((flags & 64) != 0 || isBlockScope)) {
                symbolBlock = block;
            } else if (isGlobal) {
                symbolBlock = this.f247lc.getOutermostFunction().getBody();
            } else {
                symbolBlock = this.f247lc.getFunctionBody(function);
            }
            symbol = createSymbol(name, flags);
            symbolBlock.putSymbol(symbol);
            if ((flags & 4) == 0) {
                symbol.setNeedsSlot(true);
            }
        } else if (symbol.less(flags)) {
            symbol.setFlags(flags);
        }
        return symbol;
    }

    private <T extends Node> T end(T node) {
        return (T) end(node, true);
    }

    private <T extends Node> T end(T node, boolean printNode) {
        if (this.debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("[LEAVE ").append(name(node)).append("] ").append(printNode ? node.toString() : "").append(" in '").append(this.f247lc.getCurrentFunction().getName()).append('\'');
            if (node instanceof IdentNode) {
                Symbol symbol = ((IdentNode) node).getSymbol();
                if (symbol == null) {
                    sb.append(" <NO SYMBOL>");
                } else {
                    sb.append(" <symbol=").append(symbol).append('>');
                }
            }
            this.log.unindent();
            this.log.info(sb);
        }
        return node;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        start(block);
        if (this.f247lc.isFunctionBody()) {
            if (!$assertionsDisabled && block.hasSymbols()) {
                throw new AssertionError();
            }
            FunctionNode fn = this.f247lc.getCurrentFunction();
            if (isUnparsedFunction(fn)) {
                for (String name : this.compiler.getScriptFunctionData(fn.getId()).getExternalSymbolNames()) {
                    nameIsUsed(name, null);
                }
                if (!$assertionsDisabled && !block.getStatements().isEmpty()) {
                    throw new AssertionError();
                }
                return false;
            }
            enterFunctionBody();
            return true;
        }
        return true;
    }

    private boolean isUnparsedFunction(FunctionNode fn) {
        return this.isOnDemand && fn != this.f247lc.getOutermostFunction();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCatchNode(CatchNode catchNode) {
        IdentNode exception = catchNode.getException();
        Block block = this.f247lc.getCurrentBlock();
        start(catchNode);
        String exname = exception.getName();
        boolean isInternal = exname.startsWith(CompilerConstants.EXCEPTION_PREFIX.symbolName());
        Symbol symbol = defineSymbol(block, exname, catchNode, 18 | (isInternal ? 64 : 0) | 8192);
        symbol.clearFlag(16);
        return true;
    }

    private void enterFunctionBody() {
        FunctionNode functionNode = this.f247lc.getCurrentFunction();
        Block body = this.f247lc.getCurrentBlock();
        initFunctionWideVariables(functionNode, body);
        acceptDeclarations(functionNode, body);
        defineFunctionSelfSymbol(functionNode, body);
    }

    private void defineFunctionSelfSymbol(FunctionNode functionNode, Block body) {
        if (!functionNode.isNamedFunctionExpression()) {
            return;
        }
        String name = functionNode.getIdent().getName();
        if (!$assertionsDisabled && name == null) {
            throw new AssertionError();
        }
        if (body.getExistingSymbol(name) != null) {
            return;
        }
        defineSymbol(body, name, functionNode, 8322);
        if (functionNode.allVarsInScope()) {
            this.f247lc.setFlag(functionNode, 16384);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        start(functionNode, false);
        this.thisProperties.push(new HashSet());
        if ($assertionsDisabled || functionNode.getBody() != null) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        start(varNode);
        if (varNode.isFunctionDeclaration()) {
            defineVarIdent(varNode);
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveVarNode(VarNode varNode) {
        if (!varNode.isFunctionDeclaration()) {
            defineVarIdent(varNode);
        }
        return super.leaveVarNode(varNode);
    }

    private void defineVarIdent(VarNode varNode) {
        int flags;
        IdentNode ident = varNode.getName();
        if (!varNode.isBlockScoped() && this.f247lc.getCurrentFunction().isProgram()) {
            flags = 4;
        } else {
            flags = 0;
        }
        defineSymbol(this.f247lc.getCurrentBlock(), ident.getName(), ident, varNode.getSymbolFlags() | flags);
    }

    private Symbol exceptionSymbol() {
        return newObjectInternal(CompilerConstants.EXCEPTION_PREFIX);
    }

    private FunctionNode finalizeParameters(FunctionNode functionNode) {
        List<IdentNode> newParams = new ArrayList<>();
        boolean isVarArg = functionNode.isVarArg();
        Block body = functionNode.getBody();
        for (IdentNode param : functionNode.getParameters()) {
            Symbol paramSymbol = body.getExistingSymbol(param.getName());
            if (!$assertionsDisabled && paramSymbol == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !paramSymbol.isParam()) {
                throw new AssertionError(paramSymbol + " " + paramSymbol.getFlags());
            }
            newParams.add(param.setSymbol(paramSymbol));
            if (isVarArg) {
                paramSymbol.setNeedsSlot(false);
            }
        }
        return functionNode.setParameters(this.f247lc, newParams);
    }

    private Symbol findSymbol(Block block, String name) {
        Iterator<Block> blocks = this.f247lc.getBlocks(block);
        while (blocks.hasNext()) {
            Symbol symbol = blocks.next().getExistingSymbol(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

    private void functionUsesGlobalSymbol() {
        Iterator<FunctionNode> fns = this.f247lc.getFunctions();
        while (fns.hasNext()) {
            this.f247lc.setFlag(fns.next(), 512);
        }
    }

    private void functionUsesScopeSymbol(Symbol symbol) {
        String name = symbol.getName();
        Iterator<LexicalContextNode> contextNodeIter = this.f247lc.getAllNodes();
        while (contextNodeIter.hasNext()) {
            LexicalContextNode node = contextNodeIter.next();
            if (node instanceof Block) {
                Block block = (Block) node;
                if (block.getExistingSymbol(name) != null) {
                    if (!$assertionsDisabled && !this.f247lc.contains(block)) {
                        throw new AssertionError();
                    }
                    this.f247lc.setBlockNeedsScope(block);
                    return;
                }
            } else if (node instanceof FunctionNode) {
                this.f247lc.setFlag(node, 512);
            }
        }
    }

    private void functionUsesSymbol(Symbol symbol) {
        if ($assertionsDisabled || symbol != null) {
            if (symbol.isScope()) {
                if (symbol.isGlobal()) {
                    functionUsesGlobalSymbol();
                    return;
                } else {
                    functionUsesScopeSymbol(symbol);
                    return;
                }
            } else if (!$assertionsDisabled && symbol.isGlobal()) {
                throw new AssertionError();
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    private void initCompileConstant(CompilerConstants cc, Block block, int flags) {
        defineSymbol(block, cc.symbolName(), null, flags).setNeedsSlot(true);
    }

    private void initFunctionWideVariables(FunctionNode functionNode, Block body) {
        initCompileConstant(CompilerConstants.CALLEE, body, 8259);
        initCompileConstant(CompilerConstants.THIS, body, 8203);
        if (functionNode.isVarArg()) {
            initCompileConstant(CompilerConstants.VARARGS, body, 8259);
            if (functionNode.needsArguments()) {
                initCompileConstant(CompilerConstants.ARGUMENTS, body, 8258);
                defineSymbol(body, CompilerConstants.ARGUMENTS_VAR.symbolName(), null, 8194);
            }
        }
        initParameters(functionNode, body);
        initCompileConstant(CompilerConstants.SCOPE, body, 8258);
        initCompileConstant(CompilerConstants.RETURN, body, 66);
    }

    private void initParameters(FunctionNode functionNode, Block body) {
        boolean isVarArg = functionNode.isVarArg();
        boolean scopeParams = functionNode.allVarsInScope() || isVarArg;
        for (IdentNode param : functionNode.getParameters()) {
            Symbol symbol = defineSymbol(body, param.getName(), param, 3);
            if (scopeParams) {
                symbol.setIsScope();
                if (!$assertionsDisabled && !symbol.hasSlot()) {
                    throw new AssertionError();
                }
                if (isVarArg) {
                    symbol.setNeedsSlot(false);
                }
            }
        }
    }

    private boolean isLocal(FunctionNode function, Symbol symbol) {
        FunctionNode definingFn = this.f247lc.getDefiningFunction(symbol);
        if ($assertionsDisabled || definingFn != null) {
            return definingFn == function;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBinaryNode(BinaryNode binaryNode) {
        if (binaryNode.isTokenType(TokenType.ASSIGN)) {
            return leaveASSIGN(binaryNode);
        }
        return super.leaveBinaryNode(binaryNode);
    }

    private Node leaveASSIGN(BinaryNode binaryNode) {
        Expression lhs = binaryNode.lhs();
        if (lhs instanceof AccessNode) {
            AccessNode accessNode = (AccessNode) lhs;
            Expression base = accessNode.getBase();
            if (base instanceof IdentNode) {
                Symbol symbol = ((IdentNode) base).getSymbol();
                if (symbol.isThis()) {
                    this.thisProperties.peek().add(accessNode.getProperty());
                }
            }
        }
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveUnaryNode(UnaryNode unaryNode) {
        switch (unaryNode.tokenType()) {
            case DELETE:
                return leaveDELETE(unaryNode);
            case TYPEOF:
                return leaveTYPEOF(unaryNode);
            default:
                return super.leaveUnaryNode(unaryNode);
        }
    }

    private Node leaveDELETE(UnaryNode unaryNode) {
        FunctionNode currentFunctionNode = this.f247lc.getCurrentFunction();
        boolean strictMode = currentFunctionNode.isStrict();
        Expression rhs = unaryNode.getExpression();
        Expression strictFlagNode = (Expression) LiteralNode.newInstance(unaryNode, strictMode).accept(this);
        RuntimeNode.Request request = RuntimeNode.Request.DELETE;
        List<Expression> args = new ArrayList<>();
        if (rhs instanceof IdentNode) {
            IdentNode ident = (IdentNode) rhs;
            String name = ident.getName();
            Symbol symbol = ident.getSymbol();
            if (symbol.isThis()) {
                return LiteralNode.newInstance((Node) unaryNode, true);
            }
            Expression literalNode = LiteralNode.newInstance(unaryNode, name);
            boolean failDelete = strictMode || (!symbol.isScope() && (symbol.isParam() || (symbol.isVar() && !symbol.isProgramLevel())));
            if (!failDelete) {
                args.add(compilerConstantIdentifier(CompilerConstants.SCOPE));
            }
            args.add(literalNode);
            args.add(strictFlagNode);
            if (failDelete) {
                request = RuntimeNode.Request.FAIL_DELETE;
            } else if ((symbol.isGlobal() && !symbol.isFunctionDeclaration()) || symbol.isProgramLevel()) {
                request = RuntimeNode.Request.SLOW_DELETE;
            }
        } else if (rhs instanceof AccessNode) {
            Expression base = ((AccessNode) rhs).getBase();
            String property = ((AccessNode) rhs).getProperty();
            args.add(base);
            args.add(LiteralNode.newInstance(unaryNode, property));
            args.add(strictFlagNode);
        } else if (rhs instanceof IndexNode) {
            IndexNode indexNode = (IndexNode) rhs;
            Expression base2 = indexNode.getBase();
            Expression index = indexNode.getIndex();
            args.add(base2);
            args.add(index);
            args.add(strictFlagNode);
        } else {
            return LiteralNode.newInstance((Node) unaryNode, true);
        }
        return new RuntimeNode(unaryNode, request, args);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveForNode(ForNode forNode) {
        if (forNode.isForIn()) {
            return forNode.setIterator(this.f247lc, newObjectInternal(CompilerConstants.ITERATOR_PREFIX));
        }
        return end(forNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        FunctionNode finalizedFunction;
        if (isUnparsedFunction(functionNode)) {
            finalizedFunction = functionNode;
        } else {
            finalizedFunction = markProgramBlock(removeUnusedSlots(createSyntheticInitializers(finalizeParameters((FunctionNode) this.f247lc.applyTopFlags(functionNode)))).setThisProperties(this.f247lc, this.thisProperties.pop().size()));
        }
        return finalizedFunction;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIdentNode(IdentNode identNode) {
        if (identNode.isPropertyName()) {
            return identNode;
        }
        Symbol symbol = nameIsUsed(identNode.getName(), identNode);
        if (!identNode.isInitializedHere()) {
            symbol.increaseUseCount();
        }
        IdentNode newIdentNode = identNode.setSymbol(symbol);
        if (symbol.isBlockScoped() && !symbol.hasBeenDeclared() && !identNode.isDeclaredHere() && isLocal(this.f247lc.getCurrentFunction(), symbol)) {
            newIdentNode = newIdentNode.markDead();
        }
        return end(newIdentNode);
    }

    private Symbol nameIsUsed(String name, IdentNode origin) {
        Block block = this.f247lc.getCurrentBlock();
        Symbol symbol = findSymbol(block, name);
        if (symbol != null) {
            this.log.info("Existing symbol = ", symbol);
            if (symbol.isFunctionSelf()) {
                FunctionNode functionNode = this.f247lc.getDefiningFunction(symbol);
                if (!$assertionsDisabled && functionNode == null) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && this.f247lc.getFunctionBody(functionNode).getExistingSymbol(CompilerConstants.CALLEE.symbolName()) == null) {
                    throw new AssertionError();
                }
                this.f247lc.setFlag(functionNode, 16384);
            }
            maybeForceScope(symbol);
        } else {
            this.log.info("No symbol exists. Declare as global: ", name);
            symbol = defineSymbol(block, name, origin, 5);
        }
        functionUsesSymbol(symbol);
        return symbol;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        if (!switchNode.isUniqueInteger()) {
            return switchNode.setTag(this.f247lc, newObjectInternal(CompilerConstants.SWITCH_TAG_PREFIX));
        }
        return switchNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTryNode(TryNode tryNode) {
        if ($assertionsDisabled || tryNode.getFinallyBody() == null) {
            end(tryNode);
            return tryNode.setException(this.f247lc, exceptionSymbol());
        }
        throw new AssertionError();
    }

    private Node leaveTYPEOF(UnaryNode unaryNode) {
        Expression rhs = unaryNode.getExpression();
        List<Expression> args = new ArrayList<>();
        if ((rhs instanceof IdentNode) && !isParamOrVar((IdentNode) rhs)) {
            args.add(compilerConstantIdentifier(CompilerConstants.SCOPE));
            args.add(LiteralNode.newInstance(rhs, ((IdentNode) rhs).getName()));
        } else {
            args.add(rhs);
            args.add(LiteralNode.newInstance(unaryNode));
        }
        Node runtimeNode = new RuntimeNode(unaryNode, RuntimeNode.Request.TYPEOF, args);
        end(unaryNode);
        return runtimeNode;
    }

    private FunctionNode markProgramBlock(FunctionNode functionNode) {
        if (this.isOnDemand || !functionNode.isProgram()) {
            return functionNode;
        }
        return functionNode.setBody(this.f247lc, functionNode.getBody().setFlag(this.f247lc, 8));
    }

    private void maybeForceScope(Symbol symbol) {
        if (!symbol.isScope() && symbolNeedsToBeScope(symbol)) {
            Symbol.setSymbolIsScope(this.f247lc, symbol);
        }
    }

    private Symbol newInternal(CompilerConstants cc, int flags) {
        return defineSymbol(this.f247lc.getCurrentBlock(), this.f247lc.getCurrentFunction().uniqueName(cc.symbolName()), null, 66 | flags);
    }

    private Symbol newObjectInternal(CompilerConstants cc) {
        return newInternal(cc, 8192);
    }

    private boolean start(Node node) {
        return start(node, true);
    }

    private boolean start(Node node, boolean printNode) {
        if (this.debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ENTER ").append(name(node)).append("] ").append(printNode ? node.toString() : "").append(" in '").append(this.f247lc.getCurrentFunction().getName()).append("'");
            this.log.info(sb);
            this.log.indent();
            return true;
        }
        return true;
    }

    private boolean symbolNeedsToBeScope(Symbol symbol) {
        if (symbol.isThis() || symbol.isInternal()) {
            return false;
        }
        FunctionNode func = this.f247lc.getCurrentFunction();
        if (func.allVarsInScope()) {
            return true;
        }
        if (!symbol.isBlockScoped() && func.isProgram()) {
            return true;
        }
        boolean previousWasBlock = false;
        Iterator<LexicalContextNode> it = this.f247lc.getAllNodes();
        while (it.hasNext()) {
            LexicalContextNode node = it.next();
            if ((node instanceof FunctionNode) || isSplitLiteral(node)) {
                return true;
            }
            if (node instanceof WithNode) {
                if (previousWasBlock) {
                    return true;
                }
                previousWasBlock = false;
            } else if (node instanceof Block) {
                if (((Block) node).getExistingSymbol(symbol.getName()) == symbol) {
                    return false;
                }
                previousWasBlock = true;
            } else {
                previousWasBlock = false;
            }
        }
        throw new AssertionError();
    }

    private static boolean isSplitLiteral(LexicalContextNode expr) {
        return (expr instanceof Splittable) && ((Splittable) expr).getSplitRanges() != null;
    }

    public void throwUnprotectedSwitchError(VarNode varNode) {
        String[] strArr = new String[1];
        strArr[0] = varNode.isLet() ? "let" : "const";
        String msg = ECMAErrors.getMessage("syntax.error.unprotected.switch.declaration", strArr);
        throwParserException(msg, varNode);
    }

    private void throwParserException(String message, Node origin) {
        if (origin == null) {
            throw new ParserException(message);
        }
        Source source = this.compiler.getSource();
        long token = origin.getToken();
        int line = source.getLine(origin.getStart());
        int column = source.getColumn(origin.getStart());
        String formatted = ErrorManager.format(message, source, line, column, token);
        throw new ParserException(JSErrorType.SYNTAX_ERROR, formatted, source, line, column, token);
    }
}
