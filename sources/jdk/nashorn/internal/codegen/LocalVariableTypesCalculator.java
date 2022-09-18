package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.BreakableNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.GetSplitState;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessor;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.JumpStatement;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.LocalVariableConversion;
import jdk.nashorn.internal.p001ir.LoopNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.SplitReturn;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator.class */
public final class LocalVariableTypesCalculator extends SimpleNodeVisitor {
    private static final Map<Type, LvarType> TO_LVAR_TYPE;
    private final Compiler compiler;
    private ReturnNode syntheticReturn;
    private boolean alreadyEnteredTopLevelFunction;
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Map<Label, JumpTarget> jumpTargets = new IdentityHashMap();
    private Map<Symbol, LvarType> localVariableTypes = Collections.emptyMap();
    private final Set<Symbol> invalidatedSymbols = new HashSet();
    private final Deque<LvarType> typeStack = new ArrayDeque();
    private boolean reachable = true;
    private Type returnType = Type.UNKNOWN;
    private final Map<JoinPredecessor, LocalVariableConversion> localVariableConversions = new IdentityHashMap();
    private final Map<IdentNode, LvarType> identifierLvarTypes = new IdentityHashMap();
    private final Map<Symbol, SymbolConversions> symbolConversions = new IdentityHashMap();
    private final Deque<Label> catchLabels = new ArrayDeque();

    static {
        LvarType[] values;
        $assertionsDisabled = !LocalVariableTypesCalculator.class.desiredAssertionStatus();
        TO_LVAR_TYPE = new IdentityHashMap();
        for (LvarType lvarType : LvarType.values()) {
            TO_LVAR_TYPE.put(lvarType.type, lvarType);
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator$JumpOrigin.class */
    public static class JumpOrigin {
        final JoinPredecessor node;
        final Map<Symbol, LvarType> types;

        JumpOrigin(JoinPredecessor node, Map<Symbol, LvarType> types) {
            this.node = node;
            this.types = types;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator$JumpTarget.class */
    public static class JumpTarget {
        private final List<JumpOrigin> origins;
        private Map<Symbol, LvarType> types;

        private JumpTarget() {
            this.origins = new LinkedList();
            this.types = Collections.emptyMap();
        }

        void addOrigin(JoinPredecessor originNode, Map<Symbol, LvarType> originTypes, LocalVariableTypesCalculator calc) {
            this.origins.add(new JumpOrigin(originNode, originTypes));
            this.types = calc.getUnionTypes(this.types, originTypes);
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator$LvarType.class */
    public enum LvarType {
        UNDEFINED(Type.UNDEFINED),
        BOOLEAN(Type.BOOLEAN),
        INT(Type.INT),
        DOUBLE(Type.NUMBER),
        OBJECT(Type.OBJECT);
        
        private final Type type;
        private final TypeHolderExpression typeExpression;

        LvarType(Type type) {
            this.type = type;
            this.typeExpression = new TypeHolderExpression(type);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator$TypeHolderExpression.class */
    public static class TypeHolderExpression extends Expression {
        private static final long serialVersionUID = 1;
        private final Type type;

        TypeHolderExpression(Type type) {
            super(0L, 0, 0);
            this.type = type;
        }

        @Override // jdk.nashorn.internal.p001ir.Node
        public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
            throw new AssertionError();
        }

        @Override // jdk.nashorn.internal.p001ir.Expression
        public Type getType() {
            return this.type;
        }

        @Override // jdk.nashorn.internal.p001ir.Node
        public void toString(StringBuilder sb, boolean printType) {
            throw new AssertionError();
        }
    }

    private static HashMap<Symbol, LvarType> cloneMap(Map<Symbol, LvarType> map) {
        return (HashMap) ((HashMap) map).clone();
    }

    private LocalVariableConversion createConversion(Symbol symbol, LvarType branchLvarType, Map<Symbol, LvarType> joinLvarTypes, LocalVariableConversion next) {
        if (this.invalidatedSymbols.contains(symbol)) {
            return next;
        }
        LvarType targetType = joinLvarTypes.get(symbol);
        if (!$assertionsDisabled && targetType == null) {
            throw new AssertionError();
        }
        if (targetType == branchLvarType) {
            return next;
        }
        symbolIsConverted(symbol, branchLvarType, targetType);
        return new LocalVariableConversion(symbol, branchLvarType.type, targetType.type, next);
    }

    public Map<Symbol, LvarType> getUnionTypes(Map<Symbol, LvarType> types1, Map<Symbol, LvarType> types2) {
        Map<Symbol, LvarType> union;
        if (types1 == types2 || types1.isEmpty()) {
            return types2;
        }
        if (types2.isEmpty()) {
            return types1;
        }
        Set<Symbol> commonSymbols = new HashSet<>(types1.keySet());
        commonSymbols.retainAll(types2.keySet());
        int commonSize = commonSymbols.size();
        int types1Size = types1.size();
        int types2Size = types2.size();
        if (commonSize == types1Size && commonSize == types2Size) {
            boolean matches1 = true;
            boolean matches2 = true;
            Map<Symbol, LvarType> union2 = null;
            for (Symbol symbol : commonSymbols) {
                LvarType type1 = types1.get(symbol);
                LvarType type2 = types2.get(symbol);
                LvarType widest = widestLvarType(type1, type2);
                if (widest != type1 && matches1) {
                    matches1 = false;
                    if (!matches2) {
                        union2 = cloneMap(types1);
                    }
                }
                if (widest != type2 && matches2) {
                    matches2 = false;
                    if (!matches1) {
                        union2 = cloneMap(types2);
                    }
                }
                if (!matches1 && !matches2) {
                    if (!$assertionsDisabled && union2 == null) {
                        throw new AssertionError();
                    }
                    union2.put(symbol, widest);
                }
            }
            return matches1 ? types1 : matches2 ? types2 : union2;
        }
        if (types1Size > types2Size) {
            union = cloneMap(types1);
            union.putAll(types2);
        } else {
            union = cloneMap(types2);
            union.putAll(types1);
        }
        for (Symbol symbol2 : commonSymbols) {
            union.put(symbol2, widestLvarType(types1.get(symbol2), types2.get(symbol2)));
        }
        union.keySet().removeAll(this.invalidatedSymbols);
        return union;
    }

    private static void symbolIsUsed(Symbol symbol, LvarType type) {
        if (type != LvarType.UNDEFINED) {
            symbol.setHasSlotFor(type.type);
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator$SymbolConversions.class */
    public static class SymbolConversions {
        private static final byte I2D = 1;
        private static final byte I2O = 2;
        private static final byte D2O = 4;
        private byte conversions;

        private SymbolConversions() {
        }

        void recordConversion(LvarType from, LvarType to) {
            switch (from) {
                case DOUBLE:
                    if (to == LvarType.OBJECT) {
                        recordConversion((byte) 4);
                        return;
                    }
                    return;
                case OBJECT:
                default:
                    illegalConversion(from, to);
                    return;
                case UNDEFINED:
                    return;
                case INT:
                case BOOLEAN:
                    switch (to) {
                        case DOUBLE:
                            recordConversion((byte) 1);
                            return;
                        case OBJECT:
                            recordConversion((byte) 2);
                            return;
                        default:
                            illegalConversion(from, to);
                            return;
                    }
            }
        }

        private static void illegalConversion(LvarType from, LvarType to) {
            throw new AssertionError("Invalid conversion from " + from + " to " + to);
        }

        void recordConversion(byte convFlag) {
            this.conversions = (byte) (this.conversions | convFlag);
        }

        boolean hasConversion(byte convFlag) {
            return (this.conversions & convFlag) != 0;
        }

        void calculateTypeLiveness(Symbol symbol) {
            if (symbol.hasSlotFor(Type.OBJECT)) {
                if (hasConversion((byte) 4)) {
                    symbol.setHasSlotFor(Type.NUMBER);
                }
                if (hasConversion((byte) 2)) {
                    symbol.setHasSlotFor(Type.INT);
                }
            }
            if (symbol.hasSlotFor(Type.NUMBER) && hasConversion((byte) 1)) {
                symbol.setHasSlotFor(Type.INT);
            }
        }
    }

    private void symbolIsConverted(Symbol symbol, LvarType from, LvarType to) {
        SymbolConversions conversions = this.symbolConversions.get(symbol);
        if (conversions == null) {
            conversions = new SymbolConversions();
            this.symbolConversions.put(symbol, conversions);
        }
        conversions.recordConversion(from, to);
    }

    private static LvarType toLvarType(Type type) {
        if ($assertionsDisabled || type != null) {
            LvarType lvarType = TO_LVAR_TYPE.get(type);
            if (lvarType != null) {
                return lvarType;
            }
            if (!$assertionsDisabled && !type.isObject()) {
                throw new AssertionError("Unsupported primitive type: " + type);
            }
            return LvarType.OBJECT;
        }
        throw new AssertionError();
    }

    private static LvarType widestLvarType(LvarType t1, LvarType t2) {
        if (t1 == t2) {
            return t1;
        }
        if (t1.ordinal() < LvarType.INT.ordinal() || t2.ordinal() < LvarType.INT.ordinal()) {
            return LvarType.OBJECT;
        }
        return LvarType.values()[Math.max(t1.ordinal(), t2.ordinal())];
    }

    public LocalVariableTypesCalculator(Compiler compiler) {
        this.compiler = compiler;
    }

    private JumpTarget createJumpTarget(Label label) {
        if ($assertionsDisabled || !this.jumpTargets.containsKey(label)) {
            JumpTarget jumpTarget = new JumpTarget();
            this.jumpTargets.put(label, jumpTarget);
            return jumpTarget;
        }
        throw new AssertionError();
    }

    private void doesNotContinueSequentially() {
        this.reachable = false;
        this.localVariableTypes = Collections.emptyMap();
        assertTypeStackIsEmpty();
    }

    private boolean pushExpressionType(Expression expr) {
        this.typeStack.push(toLvarType(expr.getType()));
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterAccessNode(AccessNode accessNode) {
        visitExpression(accessNode.getBase());
        return pushExpressionType(accessNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBinaryNode(BinaryNode binaryNode) {
        LvarType lhsType;
        Expression lhs = binaryNode.lhs();
        if (!(lhs instanceof IdentNode) || !binaryNode.isTokenType(TokenType.ASSIGN)) {
            lhsType = visitExpression(lhs);
        } else {
            lhsType = LvarType.UNDEFINED;
        }
        boolean isLogical = binaryNode.isLogical();
        Label joinLabel = isLogical ? new Label("") : null;
        if (isLogical) {
            jumpToLabel((JoinPredecessor) lhs, joinLabel);
        }
        Expression rhs = binaryNode.rhs();
        LvarType rhsType = visitExpression(rhs);
        if (isLogical) {
            jumpToLabel((JoinPredecessor) rhs, joinLabel);
        }
        joinOnLabel(joinLabel);
        LvarType type = toLvarType(binaryNode.setOperands(lhsType.typeExpression, rhsType.typeExpression).getType());
        if (binaryNode.isAssignment() && (lhs instanceof IdentNode)) {
            if (binaryNode.isSelfModifying()) {
                onSelfAssignment((IdentNode) lhs, type);
            } else {
                onAssignment((IdentNode) lhs, type);
            }
        }
        this.typeStack.push(type);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        boolean cloned = false;
        for (Symbol symbol : block.getSymbols()) {
            if (symbol.isBytecodeLocal()) {
                if (getLocalVariableTypeOrNull(symbol) == null) {
                    if (!cloned) {
                        cloneOrNewLocalVariableTypes();
                        cloned = true;
                    }
                    this.localVariableTypes.put(symbol, LvarType.UNDEFINED);
                }
                this.invalidatedSymbols.remove(symbol);
            }
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBreakNode(BreakNode breakNode) {
        return enterJumpStatement(breakNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCallNode(CallNode callNode) {
        visitExpression(callNode.getFunction());
        visitExpressions(callNode.getArgs());
        CallNode.EvalArgs evalArgs = callNode.getEvalArgs();
        if (evalArgs != null) {
            visitExpressions(evalArgs.getArgs());
        }
        return pushExpressionType(callNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode continueNode) {
        return enterJumpStatement(continueNode);
    }

    private boolean enterJumpStatement(JumpStatement jump) {
        if (!this.reachable) {
            return false;
        }
        assertTypeStackIsEmpty();
        jumpToLabel(jump, jump.getTargetLabel(this.f247lc), getBreakTargetTypes(jump.getPopScopeLimit(this.f247lc)));
        doesNotContinueSequentially();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterDefault(Node node) {
        return this.reachable;
    }

    private void enterDoWhileLoop(WhileNode loopNode) {
        assertTypeStackIsEmpty();
        JoinPredecessorExpression test = loopNode.getTest();
        Block body = loopNode.getBody();
        Label continueLabel = loopNode.getContinueLabel();
        Label breakLabel = loopNode.getBreakLabel();
        Map<Symbol, LvarType> beforeLoopTypes = this.localVariableTypes;
        Label repeatLabel = new Label("");
        while (true) {
            jumpToLabel(loopNode, repeatLabel, beforeLoopTypes);
            Map<Symbol, LvarType> beforeRepeatTypes = this.localVariableTypes;
            body.accept(this);
            if (this.reachable) {
                jumpToLabel(body, continueLabel);
            }
            joinOnLabel(continueLabel);
            if (!this.reachable) {
                break;
            }
            visitExpressionOnEmptyStack(test);
            jumpToLabel(test, breakLabel);
            if (Expression.isAlwaysFalse(test)) {
                break;
            }
            jumpToLabel(test, repeatLabel);
            joinOnLabel(repeatLabel);
            if (this.localVariableTypes.equals(beforeRepeatTypes)) {
                break;
            }
            resetJoinPoint(continueLabel);
            resetJoinPoint(breakLabel);
            resetJoinPoint(repeatLabel);
        }
        if (Expression.isAlwaysTrue(test)) {
            doesNotContinueSequentially();
        }
        leaveBreakable(loopNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        if (this.reachable) {
            visitExpressionOnEmptyStack(expressionStatement.getExpression());
            return false;
        }
        return false;
    }

    private void assertTypeStackIsEmpty() {
        if ($assertionsDisabled || this.typeStack.isEmpty()) {
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveDefault(Node node) {
        if ($assertionsDisabled || !(node instanceof Expression)) {
            if (!$assertionsDisabled && (node instanceof Statement) && !this.typeStack.isEmpty()) {
                throw new AssertionError();
            }
            return node;
        }
        throw new AssertionError();
    }

    private LvarType visitExpressionOnEmptyStack(Expression expr) {
        assertTypeStackIsEmpty();
        return visitExpression(expr);
    }

    private LvarType visitExpression(Expression expr) {
        int stackSize = this.typeStack.size();
        expr.accept(this);
        if ($assertionsDisabled || this.typeStack.size() == stackSize + 1) {
            return this.typeStack.pop();
        }
        throw new AssertionError();
    }

    private void visitExpressions(List<Expression> exprs) {
        for (Expression expr : exprs) {
            if (expr != null) {
                visitExpression(expr);
            }
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterForNode(ForNode forNode) {
        if (!this.reachable) {
            return false;
        }
        Expression init = forNode.getInit();
        if (forNode.isForIn()) {
            JoinPredecessorExpression iterable = forNode.getModify();
            visitExpression(iterable);
            enterTestFirstLoop(forNode, null, init, !this.compiler.useOptimisticTypes() || (!forNode.isForEach() && this.compiler.hasStringPropertyIterator(iterable.getExpression())));
        } else {
            if (init != null) {
                visitExpressionOnEmptyStack(init);
            }
            enterTestFirstLoop(forNode, forNode.getModify(), null, false);
        }
        assertTypeStackIsEmpty();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (this.alreadyEnteredTopLevelFunction) {
            this.typeStack.push(LvarType.OBJECT);
            return false;
        }
        int pos = 0;
        if (!functionNode.isVarArg()) {
            for (IdentNode param : functionNode.getParameters()) {
                Symbol symbol = param.getSymbol();
                if (!$assertionsDisabled && !symbol.hasSlot()) {
                    throw new AssertionError();
                }
                Type callSiteParamType = this.compiler.getParamType(functionNode, pos);
                LvarType paramType = callSiteParamType == null ? LvarType.OBJECT : toLvarType(callSiteParamType);
                setType(symbol, paramType);
                symbolIsUsed(symbol);
                setIdentifierLvarType(param, paramType);
                pos++;
            }
        }
        setCompilerConstantAsObject(functionNode, CompilerConstants.THIS);
        if (functionNode.hasScopeBlock() || functionNode.needsParentScope()) {
            setCompilerConstantAsObject(functionNode, CompilerConstants.SCOPE);
        }
        if (functionNode.needsCallee()) {
            setCompilerConstantAsObject(functionNode, CompilerConstants.CALLEE);
        }
        if (functionNode.needsArguments()) {
            setCompilerConstantAsObject(functionNode, CompilerConstants.ARGUMENTS);
        }
        this.alreadyEnteredTopLevelFunction = true;
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterGetSplitState(GetSplitState getSplitState) {
        return pushExpressionType(getSplitState);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIdentNode(IdentNode identNode) {
        Symbol symbol = identNode.getSymbol();
        if (symbol.isBytecodeLocal()) {
            symbolIsUsed(symbol);
            LvarType type = getLocalVariableType(symbol);
            setIdentifierLvarType(identNode, type);
            this.typeStack.push(type);
            return false;
        }
        pushExpressionType(identNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIfNode(IfNode ifNode) {
        processIfNode(ifNode);
        return false;
    }

    private void processIfNode(IfNode ifNode) {
        boolean reachableFromPass;
        Map<Symbol, LvarType> passLvarTypes;
        if (!this.reachable) {
            return;
        }
        Expression test = ifNode.getTest();
        Block pass = ifNode.getPass();
        Block fail = ifNode.getFail();
        visitExpressionOnEmptyStack(test);
        boolean isTestAlwaysTrue = Expression.isAlwaysTrue(test);
        if (Expression.isAlwaysFalse(test)) {
            passLvarTypes = null;
            reachableFromPass = false;
        } else {
            Map<Symbol, LvarType> afterTestLvarTypes = this.localVariableTypes;
            pass.accept(this);
            assertTypeStackIsEmpty();
            if (isTestAlwaysTrue) {
                return;
            }
            passLvarTypes = this.localVariableTypes;
            reachableFromPass = this.reachable;
            this.localVariableTypes = afterTestLvarTypes;
            this.reachable = true;
        }
        if (!$assertionsDisabled && isTestAlwaysTrue) {
            throw new AssertionError();
        }
        if (fail != null) {
            fail.accept(this);
            assertTypeStackIsEmpty();
        }
        if (this.reachable) {
            if (reachableFromPass) {
                Map<Symbol, LvarType> failLvarTypes = this.localVariableTypes;
                this.localVariableTypes = getUnionTypes(passLvarTypes, failLvarTypes);
                setConversion(pass, passLvarTypes, this.localVariableTypes);
                setConversion(fail != null ? fail : ifNode, failLvarTypes, this.localVariableTypes);
            }
        } else if (reachableFromPass) {
            if (!$assertionsDisabled && passLvarTypes == null) {
                throw new AssertionError();
            }
            this.localVariableTypes = passLvarTypes;
            this.reachable = true;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIndexNode(IndexNode indexNode) {
        visitExpression(indexNode.getBase());
        visitExpression(indexNode.getIndex());
        return pushExpressionType(indexNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinExpr) {
        Expression expr = joinExpr.getExpression();
        if (expr != null) {
            expr.accept(this);
            return false;
        }
        this.typeStack.push(LvarType.UNDEFINED);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        return enterJumpStatement(jumpToInlinedFinally);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLiteralNode(LiteralNode<?> literalNode) {
        List<Expression> expressions;
        if ((literalNode instanceof LiteralNode.ArrayLiteralNode) && (expressions = ((LiteralNode.ArrayLiteralNode) literalNode).getElementExpressions()) != null) {
            visitExpressions(expressions);
        }
        pushExpressionType(literalNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterObjectNode(ObjectNode objectNode) {
        for (PropertyNode propertyNode : objectNode.getElements()) {
            Expression value = propertyNode.getValue();
            if (value != null) {
                visitExpression(value);
            }
        }
        return pushExpressionType(objectNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterPropertyNode(PropertyNode propertyNode) {
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterReturnNode(ReturnNode returnNode) {
        Type returnExprType;
        if (!this.reachable) {
            return false;
        }
        Expression returnExpr = returnNode.getExpression();
        if (returnExpr != null) {
            returnExprType = visitExpressionOnEmptyStack(returnExpr).type;
        } else {
            assertTypeStackIsEmpty();
            returnExprType = Type.UNDEFINED;
        }
        this.returnType = Type.widestReturnType(this.returnType, returnExprType);
        doesNotContinueSequentially();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
        visitExpressions(runtimeNode.getArgs());
        return pushExpressionType(runtimeNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitReturn(SplitReturn splitReturn) {
        doesNotContinueSequentially();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSwitchNode(SwitchNode switchNode) {
        if (!this.reachable) {
            return false;
        }
        visitExpressionOnEmptyStack(switchNode.getExpression());
        List<CaseNode> cases = switchNode.getCases();
        if (cases.isEmpty()) {
            return false;
        }
        boolean isInteger = switchNode.isUniqueInteger();
        Label breakLabel = switchNode.getBreakLabel();
        boolean hasDefault = switchNode.getDefaultCase() != null;
        boolean tagUsed = false;
        for (CaseNode caseNode : cases) {
            Expression test = caseNode.getTest();
            if (!isInteger && test != null) {
                visitExpressionOnEmptyStack(test);
                if (!tagUsed) {
                    symbolIsUsed(switchNode.getTag(), LvarType.OBJECT);
                    tagUsed = true;
                }
            }
            jumpToLabel(caseNode, caseNode.getBody().getEntryLabel());
        }
        if (!hasDefault) {
            jumpToLabel(switchNode, breakLabel);
        }
        doesNotContinueSequentially();
        Block previousBlock = null;
        for (CaseNode caseNode2 : cases) {
            Block body = caseNode2.getBody();
            Label entryLabel = body.getEntryLabel();
            if (previousBlock != null && this.reachable) {
                jumpToLabel(previousBlock, entryLabel);
            }
            joinOnLabel(entryLabel);
            if (!$assertionsDisabled && !this.reachable) {
                throw new AssertionError();
            }
            body.accept(this);
            previousBlock = body;
        }
        if (previousBlock != null && this.reachable) {
            jumpToLabel(previousBlock, breakLabel);
        }
        leaveBreakable(switchNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTernaryNode(TernaryNode ternaryNode) {
        LvarType trueType;
        LvarType falseType;
        Expression test = ternaryNode.getTest();
        Expression trueExpr = ternaryNode.getTrueExpression();
        Expression falseExpr = ternaryNode.getFalseExpression();
        visitExpression(test);
        Map<Symbol, LvarType> testExitLvarTypes = this.localVariableTypes;
        if (!Expression.isAlwaysFalse(test)) {
            trueType = visitExpression(trueExpr);
        } else {
            trueType = null;
        }
        Map<Symbol, LvarType> trueExitLvarTypes = this.localVariableTypes;
        this.localVariableTypes = testExitLvarTypes;
        if (!Expression.isAlwaysTrue(test)) {
            falseType = visitExpression(falseExpr);
        } else {
            falseType = null;
        }
        Map<Symbol, LvarType> falseExitLvarTypes = this.localVariableTypes;
        this.localVariableTypes = getUnionTypes(trueExitLvarTypes, falseExitLvarTypes);
        setConversion((JoinPredecessor) trueExpr, trueExitLvarTypes, this.localVariableTypes);
        setConversion((JoinPredecessor) falseExpr, falseExitLvarTypes, this.localVariableTypes);
        this.typeStack.push(trueType != null ? falseType != null ? widestLvarType(trueType, falseType) : trueType : (LvarType) assertNotNull(falseType));
        return false;
    }

    private static <T> T assertNotNull(T t) {
        if ($assertionsDisabled || t != null) {
            return t;
        }
        throw new AssertionError();
    }

    private void enterTestFirstLoop(LoopNode loopNode, JoinPredecessorExpression modify, Expression iteratorValues, boolean iteratorValuesAreObject) {
        JoinPredecessorExpression test = loopNode.getTest();
        if (Expression.isAlwaysFalse(test)) {
            visitExpressionOnEmptyStack(test);
            return;
        }
        Label continueLabel = loopNode.getContinueLabel();
        Label breakLabel = loopNode.getBreakLabel();
        Label repeatLabel = modify == null ? continueLabel : new Label("");
        Map<Symbol, LvarType> beforeLoopTypes = this.localVariableTypes;
        while (true) {
            jumpToLabel(loopNode, repeatLabel, beforeLoopTypes);
            Map<Symbol, LvarType> beforeRepeatTypes = this.localVariableTypes;
            if (test != null) {
                visitExpressionOnEmptyStack(test);
            }
            if (!Expression.isAlwaysTrue(test)) {
                jumpToLabel(test, breakLabel);
            }
            if (iteratorValues instanceof IdentNode) {
                IdentNode ident = (IdentNode) iteratorValues;
                onAssignment(ident, iteratorValuesAreObject ? LvarType.OBJECT : toLvarType(this.compiler.getOptimisticType(ident)));
            }
            Block body = loopNode.getBody();
            body.accept(this);
            if (this.reachable) {
                jumpToLabel(body, continueLabel);
            }
            joinOnLabel(continueLabel);
            if (!this.reachable) {
                break;
            }
            if (modify != null) {
                visitExpressionOnEmptyStack(modify);
                jumpToLabel(modify, repeatLabel);
                joinOnLabel(repeatLabel);
            }
            if (this.localVariableTypes.equals(beforeRepeatTypes)) {
                break;
            }
            resetJoinPoint(continueLabel);
            resetJoinPoint(breakLabel);
            resetJoinPoint(repeatLabel);
        }
        if (Expression.isAlwaysTrue(test) && iteratorValues == null) {
            doesNotContinueSequentially();
        }
        leaveBreakable(loopNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterThrowNode(ThrowNode throwNode) {
        if (!this.reachable) {
            return false;
        }
        visitExpressionOnEmptyStack(throwNode.getExpression());
        jumpToCatchBlock(throwNode);
        doesNotContinueSequentially();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTryNode(TryNode tryNode) {
        if (!this.reachable) {
            return false;
        }
        Label catchLabel = new Label("");
        this.catchLabels.push(catchLabel);
        jumpToLabel(tryNode, catchLabel);
        Block body = tryNode.getBody();
        body.accept(this);
        this.catchLabels.pop();
        Label endLabel = new Label("");
        boolean canExit = false;
        if (this.reachable) {
            jumpToLabel(body, endLabel);
            canExit = true;
        }
        doesNotContinueSequentially();
        for (Block inlinedFinally : tryNode.getInlinedFinallies()) {
            Block finallyBody = TryNode.getLabelledInlinedFinallyBlock(inlinedFinally);
            joinOnLabel(finallyBody.getEntryLabel());
            if (this.reachable) {
                finallyBody.accept(this);
                if (!$assertionsDisabled && this.reachable) {
                    throw new AssertionError();
                }
            }
        }
        joinOnLabel(catchLabel);
        for (CatchNode catchNode : tryNode.getCatches()) {
            IdentNode exception = catchNode.getException();
            onAssignment(exception, LvarType.OBJECT);
            Expression condition = catchNode.getExceptionCondition();
            if (condition != null) {
                visitExpression(condition);
            }
            Map<Symbol, LvarType> afterConditionTypes = this.localVariableTypes;
            Block catchBody = catchNode.getBody();
            this.reachable = true;
            catchBody.accept(this);
            if (this.reachable) {
                jumpToLabel(catchBody, endLabel);
                canExit = true;
            }
            this.localVariableTypes = afterConditionTypes;
        }
        doesNotContinueSequentially();
        if (canExit) {
            joinOnLabel(endLabel);
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterUnaryNode(UnaryNode unaryNode) {
        Expression expr = unaryNode.getExpression();
        LvarType unaryType = toLvarType(unaryNode.setExpression(visitExpression(expr).typeExpression).getType());
        if (unaryNode.isSelfModifying() && (expr instanceof IdentNode)) {
            onSelfAssignment((IdentNode) expr, unaryType);
        }
        this.typeStack.push(unaryType);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        Expression init;
        if (this.reachable && (init = varNode.getInit()) != null) {
            onAssignment(varNode.getName(), visitExpression(init));
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWhileNode(WhileNode whileNode) {
        if (!this.reachable) {
            return false;
        }
        if (whileNode.isDoWhile()) {
            enterDoWhileLoop(whileNode);
            return false;
        }
        enterTestFirstLoop(whileNode, null, null, false);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWithNode(WithNode withNode) {
        if (this.reachable) {
            visitExpression(withNode.getExpression());
            withNode.getBody().accept(this);
            return false;
        }
        return false;
    }

    private Map<Symbol, LvarType> getBreakTargetTypes(LexicalContextNode target) {
        Map<Symbol, LvarType> types = this.localVariableTypes;
        Iterator<LexicalContextNode> it = this.f247lc.getAllNodes();
        while (it.hasNext()) {
            LexicalContextNode node = it.next();
            if (node instanceof Block) {
                for (Symbol symbol : ((Block) node).getSymbols()) {
                    if (this.localVariableTypes.containsKey(symbol)) {
                        Map<Symbol, LvarType> map = types;
                        types = types;
                        if (map == this.localVariableTypes) {
                            types = cloneMap(this.localVariableTypes);
                        }
                        types.remove(symbol);
                    }
                }
            }
            if (node == target) {
                break;
            }
        }
        return types;
    }

    private LvarType getLocalVariableType(Symbol symbol) {
        LvarType type = getLocalVariableTypeOrNull(symbol);
        if ($assertionsDisabled || type != null) {
            return type;
        }
        throw new AssertionError();
    }

    private LvarType getLocalVariableTypeOrNull(Symbol symbol) {
        return this.localVariableTypes.get(symbol);
    }

    private JumpTarget getOrCreateJumpTarget(Label label) {
        JumpTarget jumpTarget = this.jumpTargets.get(label);
        if (jumpTarget == null) {
            jumpTarget = createJumpTarget(label);
        }
        return jumpTarget;
    }

    private void joinOnLabel(Label label) {
        JumpTarget jumpTarget = this.jumpTargets.remove(label);
        if (jumpTarget == null) {
            return;
        }
        if (!$assertionsDisabled && jumpTarget.origins.isEmpty()) {
            throw new AssertionError();
        }
        this.reachable = true;
        this.localVariableTypes = getUnionTypes(jumpTarget.types, this.localVariableTypes);
        for (JumpOrigin jumpOrigin : jumpTarget.origins) {
            setConversion(jumpOrigin.node, jumpOrigin.types, this.localVariableTypes);
        }
    }

    private void jumpToCatchBlock(JoinPredecessor jumpOrigin) {
        Label currentCatchLabel = this.catchLabels.peek();
        if (currentCatchLabel != null) {
            jumpToLabel(jumpOrigin, currentCatchLabel);
        }
    }

    private void jumpToLabel(JoinPredecessor jumpOrigin, Label label) {
        jumpToLabel(jumpOrigin, label, this.localVariableTypes);
    }

    private void jumpToLabel(JoinPredecessor jumpOrigin, Label label, Map<Symbol, LvarType> types) {
        getOrCreateJumpTarget(label).addOrigin(jumpOrigin, types, this);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlock(Block block) {
        LabelNode labelNode;
        if (this.f247lc.isFunctionBody()) {
            if (this.reachable) {
                createSyntheticReturn(block);
                if (!$assertionsDisabled && this.reachable) {
                    throw new AssertionError();
                }
            }
            calculateReturnType();
        }
        boolean cloned = false;
        for (Symbol symbol : block.getSymbols()) {
            if (symbol.hasSlot()) {
                if (symbol.isBytecodeLocal()) {
                    if (this.localVariableTypes.containsKey(symbol) && !cloned) {
                        this.localVariableTypes = cloneMap(this.localVariableTypes);
                        cloned = true;
                    }
                    invalidateSymbol(symbol);
                }
                SymbolConversions conversions = this.symbolConversions.get(symbol);
                if (conversions != null) {
                    conversions.calculateTypeLiveness(symbol);
                }
                if (symbol.slotCount() == 0) {
                    symbol.setNeedsSlot(false);
                }
            }
        }
        if (this.reachable && (labelNode = this.f247lc.getCurrentBlockLabelNode()) != null) {
            jumpToLabel(labelNode, block.getBreakLabel());
        }
        leaveBreakable(block);
        return block;
    }

    private void calculateReturnType() {
        if (this.returnType.isUnknown()) {
            this.returnType = Type.OBJECT;
        }
    }

    private void createSyntheticReturn(Block body) {
        IdentNode returnExpr;
        FunctionNode functionNode = this.f247lc.getCurrentFunction();
        long token = functionNode.getToken();
        int finish = functionNode.getFinish();
        List<Statement> statements = body.getStatements();
        int lineNumber = statements.isEmpty() ? functionNode.getLineNumber() : statements.get(statements.size() - 1).getLineNumber();
        if (functionNode.isProgram()) {
            returnExpr = new IdentNode(token, finish, CompilerConstants.RETURN.symbolName()).setSymbol(getCompilerConstantSymbol(functionNode, CompilerConstants.RETURN));
        } else {
            returnExpr = null;
        }
        this.syntheticReturn = new ReturnNode(lineNumber, token, finish, returnExpr);
        this.syntheticReturn.accept(this);
    }

    private void leaveBreakable(BreakableNode breakable) {
        joinOnLabel(breakable.getBreakLabel());
        assertTypeStackIsEmpty();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        SimpleNodeVisitor applyChangesVisitor = new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.LocalVariableTypesCalculator.1
            private boolean inOuterFunction = true;
            private final Deque<JoinPredecessor> joinPredecessors = new ArrayDeque();
            static final /* synthetic */ boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !LocalVariableTypesCalculator.class.desiredAssertionStatus();
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterDefault(Node node) {
                if (!this.inOuterFunction) {
                    return false;
                }
                if (node instanceof JoinPredecessor) {
                    this.joinPredecessors.push((JoinPredecessor) node);
                }
                return this.inOuterFunction;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterFunctionNode(FunctionNode fn) {
                if (LocalVariableTypesCalculator.this.compiler.isOnDemandCompilation()) {
                    return false;
                }
                this.inOuterFunction = false;
                return true;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBinaryNode(BinaryNode binaryNode) {
                if (binaryNode.isComparison()) {
                    Expression lhs = binaryNode.lhs();
                    Expression rhs = binaryNode.rhs();
                    TokenType tt = binaryNode.tokenType();
                    switch (C15002.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tt.ordinal()]) {
                        case 1:
                        case 2:
                            Expression undefinedNode = LocalVariableTypesCalculator.createIsUndefined(binaryNode, lhs, rhs, tt == TokenType.EQ_STRICT ? RuntimeNode.Request.IS_UNDEFINED : RuntimeNode.Request.IS_NOT_UNDEFINED);
                            if (undefinedNode != binaryNode) {
                                return undefinedNode;
                            }
                            if (lhs.getType().isBoolean() != rhs.getType().isBoolean()) {
                                return new RuntimeNode(binaryNode);
                            }
                            break;
                    }
                    if (lhs.getType().isObject() && rhs.getType().isObject()) {
                        return new RuntimeNode(binaryNode);
                    }
                } else if (binaryNode.isOptimisticUndecidedType()) {
                    return binaryNode.decideType();
                }
                return binaryNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveDefault(Node node) {
                if (node instanceof JoinPredecessor) {
                    JoinPredecessor original = this.joinPredecessors.pop();
                    if (!$assertionsDisabled && original.getClass() != node.getClass()) {
                        throw new AssertionError(original.getClass().getName() + "!=" + node.getClass().getName());
                    }
                    JoinPredecessor newNode = setLocalVariableConversion(original, (JoinPredecessor) node);
                    if (newNode instanceof LexicalContextNode) {
                        this.f247lc.replace((LexicalContextNode) node, (LexicalContextNode) newNode);
                    }
                    return (Node) newNode;
                }
                return node;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBlock(Block block) {
                if (this.inOuterFunction && LocalVariableTypesCalculator.this.syntheticReturn != null && this.f247lc.isFunctionBody()) {
                    ArrayList<Statement> stmts = new ArrayList<>(block.getStatements());
                    stmts.add((ReturnNode) LocalVariableTypesCalculator.this.syntheticReturn.accept(this));
                    return block.setStatements(this.f247lc, stmts);
                }
                return super.leaveBlock(block);
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveFunctionNode(FunctionNode nestedFunctionNode) {
                this.inOuterFunction = true;
                FunctionNode newNestedFunction = (FunctionNode) nestedFunctionNode.accept(new LocalVariableTypesCalculator(LocalVariableTypesCalculator.this.compiler));
                this.f247lc.replace(nestedFunctionNode, newNestedFunction);
                return newNestedFunction;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveIdentNode(IdentNode identNode) {
                IdentNode original = (IdentNode) this.joinPredecessors.pop();
                Symbol symbol = identNode.getSymbol();
                if (symbol == null) {
                    if (!$assertionsDisabled && !identNode.isPropertyName()) {
                        throw new AssertionError();
                    }
                    return identNode;
                }
                if (symbol.hasSlot()) {
                    if (!$assertionsDisabled && symbol.isScope() && !symbol.isParam()) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && !original.getName().equals(identNode.getName())) {
                        throw new AssertionError();
                    }
                    LvarType lvarType = (LvarType) LocalVariableTypesCalculator.this.identifierLvarTypes.remove(original);
                    if (lvarType != null) {
                        return (Node) setLocalVariableConversion(original, identNode.setType(lvarType.type));
                    }
                    if (!$assertionsDisabled && LocalVariableTypesCalculator.this.localVariableConversions.get(original) != null) {
                        throw new AssertionError();
                    }
                } else if (!$assertionsDisabled && !LocalVariableTypesCalculator.this.identIsDeadAndHasNoLiveConversions(original)) {
                    throw new AssertionError();
                }
                return identNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveLiteralNode(LiteralNode<?> literalNode) {
                return literalNode.initialize(this.f247lc);
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveRuntimeNode(RuntimeNode runtimeNode) {
                RuntimeNode.Request request = runtimeNode.getRequest();
                boolean isEqStrict = request == RuntimeNode.Request.EQ_STRICT;
                if (isEqStrict || request == RuntimeNode.Request.NE_STRICT) {
                    return LocalVariableTypesCalculator.createIsUndefined(runtimeNode, runtimeNode.getArgs().get(0), runtimeNode.getArgs().get(1), isEqStrict ? RuntimeNode.Request.IS_UNDEFINED : RuntimeNode.Request.IS_NOT_UNDEFINED);
                }
                return runtimeNode;
            }

            private <T extends JoinPredecessor> T setLocalVariableConversion(JoinPredecessor original, T jp) {
                return (T) jp.setLocalVariableConversion(this.f247lc, (LocalVariableConversion) LocalVariableTypesCalculator.this.localVariableConversions.get(original));
            }
        };
        FunctionNode newFunction = functionNode.setBody(this.f247lc, (Block) functionNode.getBody().accept(applyChangesVisitor)).setReturnType(this.f247lc, this.returnType);
        return newFunction.setParameters(this.f247lc, newFunction.visitParameters(applyChangesVisitor));
    }

    /* renamed from: jdk.nashorn.internal.codegen.LocalVariableTypesCalculator$2 */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/LocalVariableTypesCalculator$2.class */
    public static /* synthetic */ class C15002 {
        static final /* synthetic */ int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            f233xf47a80b1 = new int[LvarType.values().length];
            try {
                f233xf47a80b1[LvarType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f233xf47a80b1[LvarType.OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f233xf47a80b1[LvarType.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f233xf47a80b1[LvarType.INT.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f233xf47a80b1[LvarType.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static Expression createIsUndefined(Expression parent, Expression lhs, Expression rhs, RuntimeNode.Request request) {
        if (isUndefinedIdent(lhs) || isUndefinedIdent(rhs)) {
            return new RuntimeNode(parent, request, lhs, rhs);
        }
        return parent;
    }

    private static boolean isUndefinedIdent(Expression expr) {
        return (expr instanceof IdentNode) && "undefined".equals(((IdentNode) expr).getName());
    }

    public boolean identIsDeadAndHasNoLiveConversions(IdentNode identNode) {
        LocalVariableConversion conv = this.localVariableConversions.get(identNode);
        return conv == null || !conv.isLive();
    }

    private void onAssignment(IdentNode identNode, LvarType type) {
        LvarType finalType;
        Symbol symbol = identNode.getSymbol();
        if ($assertionsDisabled || symbol != null) {
            if (!symbol.isBytecodeLocal()) {
                return;
            }
            if (!$assertionsDisabled && type == null) {
                throw new AssertionError();
            }
            if (type == LvarType.UNDEFINED && getLocalVariableType(symbol) != LvarType.UNDEFINED) {
                finalType = LvarType.OBJECT;
                symbol.setFlag(8192);
            } else {
                finalType = type;
            }
            setType(symbol, finalType);
            setIdentifierLvarType(identNode, finalType);
            jumpToCatchBlock(identNode);
            return;
        }
        throw new AssertionError(identNode.getName());
    }

    private void onSelfAssignment(IdentNode identNode, LvarType type) {
        Symbol symbol = identNode.getSymbol();
        if ($assertionsDisabled || symbol != null) {
            if (!symbol.isBytecodeLocal()) {
                return;
            }
            if (!$assertionsDisabled && (type == null || type == LvarType.UNDEFINED || type == LvarType.BOOLEAN)) {
                throw new AssertionError();
            }
            setType(symbol, type);
            jumpToCatchBlock(identNode);
            return;
        }
        throw new AssertionError(identNode.getName());
    }

    private void resetJoinPoint(Label label) {
        this.jumpTargets.remove(label);
    }

    private void setCompilerConstantAsObject(FunctionNode functionNode, CompilerConstants cc) {
        Symbol symbol = getCompilerConstantSymbol(functionNode, cc);
        setType(symbol, LvarType.OBJECT);
        symbolIsUsed(symbol);
    }

    private static Symbol getCompilerConstantSymbol(FunctionNode functionNode, CompilerConstants cc) {
        return functionNode.getBody().getExistingSymbol(cc.symbolName());
    }

    private void setConversion(JoinPredecessor node, Map<Symbol, LvarType> branchLvarTypes, Map<Symbol, LvarType> joinLvarTypes) {
        if (node == null) {
            return;
        }
        if (branchLvarTypes.isEmpty() || joinLvarTypes.isEmpty()) {
            this.localVariableConversions.remove(node);
        }
        LocalVariableConversion conversion = null;
        if (node instanceof IdentNode) {
            Symbol symbol = ((IdentNode) node).getSymbol();
            conversion = createConversion(symbol, branchLvarTypes.get(symbol), joinLvarTypes, null);
        } else {
            for (Map.Entry<Symbol, LvarType> entry : branchLvarTypes.entrySet()) {
                Symbol symbol2 = entry.getKey();
                LvarType branchLvarType = entry.getValue();
                conversion = createConversion(symbol2, branchLvarType, joinLvarTypes, conversion);
            }
        }
        if (conversion != null) {
            this.localVariableConversions.put(node, conversion);
        } else {
            this.localVariableConversions.remove(node);
        }
    }

    private void setIdentifierLvarType(IdentNode identNode, LvarType type) {
        if ($assertionsDisabled || type != null) {
            this.identifierLvarTypes.put(identNode, type);
            return;
        }
        throw new AssertionError();
    }

    private void setType(Symbol symbol, LvarType type) {
        if (getLocalVariableTypeOrNull(symbol) == type) {
            return;
        }
        if (!$assertionsDisabled && !symbol.hasSlot()) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && symbol.isGlobal()) {
            throw new AssertionError();
        }
        cloneOrNewLocalVariableTypes();
        this.localVariableTypes.put(symbol, type);
    }

    private void cloneOrNewLocalVariableTypes() {
        this.localVariableTypes = this.localVariableTypes.isEmpty() ? new HashMap<>() : cloneMap(this.localVariableTypes);
    }

    private void invalidateSymbol(Symbol symbol) {
        this.localVariableTypes.remove(symbol);
        this.invalidatedSymbols.add(symbol);
    }

    private void symbolIsUsed(Symbol symbol) {
        symbolIsUsed(symbol, getLocalVariableType(symbol));
    }
}
