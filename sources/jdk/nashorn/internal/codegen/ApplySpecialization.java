package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(name = "apply2call")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ApplySpecialization.class */
public final class ApplySpecialization extends SimpleNodeVisitor implements Loggable {
    private static final boolean USE_APPLY2CALL;
    private final DebugLogger log;
    private final Compiler compiler;
    private final Set<Integer> changed = new HashSet();
    private final Deque<List<IdentNode>> explodedArguments = new ArrayDeque();
    private final Deque<MethodType> callSiteTypes = new ArrayDeque();
    private static final String ARGUMENTS;
    private static final AppliesFoundException HAS_APPLIES;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ApplySpecialization.class.desiredAssertionStatus();
        USE_APPLY2CALL = Options.getBooleanProperty("nashorn.apply2call", true);
        ARGUMENTS = CompilerConstants.ARGUMENTS_VAR.symbolName();
        HAS_APPLIES = new AppliesFoundException();
    }

    public ApplySpecialization(Compiler compiler) {
        this.compiler = compiler;
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

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ApplySpecialization$TransformFailedException.class */
    public static class TransformFailedException extends RuntimeException {
        TransformFailedException(FunctionNode fn, String message) {
            super(ApplySpecialization.massageURL(fn.getSource().getURL()) + '.' + fn.getName() + " => " + message, null, false, false);
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ApplySpecialization$AppliesFoundException.class */
    private static class AppliesFoundException extends RuntimeException {
        AppliesFoundException() {
            super("applies_found", null, false, false);
        }
    }

    private boolean hasApplies(final FunctionNode functionNode) {
        try {
            functionNode.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.ApplySpecialization.1
                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterFunctionNode(FunctionNode fn) {
                    return fn == functionNode;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterCallNode(CallNode callNode) {
                    if (ApplySpecialization.isApply(callNode)) {
                        throw ApplySpecialization.HAS_APPLIES;
                    }
                    return true;
                }
            });
            this.log.fine("There are no applies in ", DebugLogger.quote(functionNode.getName()), " - nothing to do.");
            return false;
        } catch (AppliesFoundException e) {
            return true;
        }
    }

    private static void checkValidTransform(final FunctionNode functionNode) {
        final Set<Expression> argumentsFound = new HashSet<>();
        final Deque<Set<Expression>> stack = new ArrayDeque<>();
        functionNode.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.ApplySpecialization.2
            private boolean isCurrentArg(Expression expr) {
                return !stack.isEmpty() && ((Set) stack.peek()).contains(expr);
            }

            private boolean isArguments(Expression expr) {
                if ((expr instanceof IdentNode) && ApplySpecialization.ARGUMENTS.equals(((IdentNode) expr).getName())) {
                    argumentsFound.add(expr);
                    return true;
                }
                return false;
            }

            private boolean isParam(String name) {
                for (IdentNode param : functionNode.getParameters()) {
                    if (param.getName().equals(name)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveIdentNode(IdentNode identNode) {
                if (isParam(identNode.getName())) {
                    throw new TransformFailedException(this.f247lc.getCurrentFunction(), "parameter: " + identNode.getName());
                }
                if (isArguments(identNode) && !isCurrentArg(identNode)) {
                    throw new TransformFailedException(this.f247lc.getCurrentFunction(), "is 'arguments': " + identNode.getName());
                }
                return identNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterCallNode(CallNode callNode) {
                HashSet hashSet = new HashSet();
                if (ApplySpecialization.isApply(callNode)) {
                    List<Expression> argList = callNode.getArgs();
                    if (argList.size() != 2 || !isArguments(argList.get(argList.size() - 1))) {
                        throw new TransformFailedException(this.f247lc.getCurrentFunction(), "argument pattern not matched: " + argList);
                    }
                    hashSet.addAll(callNode.getArgs());
                }
                stack.push(hashSet);
                return true;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveCallNode(CallNode callNode) {
                stack.pop();
                return callNode;
            }
        });
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCallNode(CallNode callNode) {
        return !this.explodedArguments.isEmpty();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCallNode(CallNode callNode) {
        Collection<? extends Expression> newParams = (List) this.explodedArguments.peek();
        if (isApply(callNode)) {
            List<Expression> newArgs = new ArrayList<>();
            for (Expression arg : callNode.getArgs()) {
                if ((arg instanceof IdentNode) && ARGUMENTS.equals(((IdentNode) arg).getName())) {
                    newArgs.addAll(newParams);
                } else {
                    newArgs.add(arg);
                }
            }
            this.changed.add(Integer.valueOf(this.f247lc.getCurrentFunction().getId()));
            CallNode newCallNode = callNode.setArgs(newArgs).setIsApplyToCall();
            if (this.log.isEnabled()) {
                this.log.fine("Transformed ", callNode, " from apply to call => ", newCallNode, " in ", DebugLogger.quote(this.f247lc.getCurrentFunction().getName()));
            }
            return newCallNode;
        }
        return callNode;
    }

    private void pushExplodedArgs(FunctionNode functionNode) {
        int start = 0;
        MethodType actualCallSiteType = this.compiler.getCallSiteType(functionNode);
        if (actualCallSiteType == null) {
            throw new TransformFailedException(this.f247lc.getCurrentFunction(), "No callsite type");
        }
        if (!$assertionsDisabled && actualCallSiteType.parameterType(actualCallSiteType.parameterCount() - 1) == Object[].class) {
            throw new AssertionError("error vararg callsite passed to apply2call " + functionNode.getName() + " " + actualCallSiteType);
        }
        TypeMap ptm = this.compiler.getTypeMap();
        if (ptm.needsCallee()) {
            start = 0 + 1;
        }
        int start2 = start + 1;
        if (!$assertionsDisabled && functionNode.getNumOfParams() != 0) {
            throw new AssertionError("apply2call on function with named paramaters!");
        }
        List<IdentNode> newParams = new ArrayList<>();
        long to = actualCallSiteType.parameterCount() - start2;
        for (int i = 0; i < to; i++) {
            newParams.add(new IdentNode(functionNode.getToken(), functionNode.getFinish(), CompilerConstants.EXPLODED_ARGUMENT_PREFIX.symbolName() + i));
        }
        this.callSiteTypes.push(actualCallSiteType);
        this.explodedArguments.push(newParams);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (!USE_APPLY2CALL || !this.compiler.isOnDemandCompilation() || !functionNode.needsArguments() || functionNode.hasEval() || functionNode.getNumOfParams() != 0) {
            return false;
        }
        if (!Global.isBuiltinFunctionPrototypeApply()) {
            this.log.fine("Apply transform disabled: apply/call overridden");
            if (!$assertionsDisabled && Global.isBuiltinFunctionPrototypeCall()) {
                throw new AssertionError("call and apply should have the same SwitchPoint");
            }
            return false;
        } else if (!hasApplies(functionNode)) {
            return false;
        } else {
            if (this.log.isEnabled()) {
                this.log.info("Trying to specialize apply to call in '", functionNode.getName(), "' params=", functionNode.getParameters(), " id=", Integer.valueOf(functionNode.getId()), " source=", massageURL(functionNode.getSource().getURL()));
            }
            try {
                checkValidTransform(functionNode);
                pushExplodedArgs(functionNode);
                return true;
            } catch (TransformFailedException e) {
                this.log.info("Failure: ", e.getMessage());
                return false;
            }
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        FunctionNode newFunctionNode = functionNode;
        String functionName = newFunctionNode.getName();
        if (this.changed.contains(Integer.valueOf(newFunctionNode.getId()))) {
            newFunctionNode = newFunctionNode.clearFlag(this.f247lc, 8).setFlag(this.f247lc, 4096).setParameters(this.f247lc, this.explodedArguments.peek());
            if (this.log.isEnabled()) {
                this.log.info("Success: ", massageURL(newFunctionNode.getSource().getURL()), '.', functionName, "' id=", Integer.valueOf(newFunctionNode.getId()), " params=", this.callSiteTypes.peek());
            }
        }
        this.callSiteTypes.pop();
        this.explodedArguments.pop();
        return newFunctionNode;
    }

    public static boolean isApply(CallNode callNode) {
        Expression f = callNode.getFunction();
        return (f instanceof AccessNode) && "apply".equals(((AccessNode) f).getProperty());
    }

    public static String massageURL(URL url) {
        if (url == null) {
            return "<null>";
        }
        String str = url.toString();
        int slash = str.lastIndexOf(47);
        if (slash == -1) {
            return str;
        }
        return str.substring(slash + 1);
    }
}
