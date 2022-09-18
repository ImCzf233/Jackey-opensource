package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/FinalScriptFunctionData.class */
public final class FinalScriptFunctionData extends ScriptFunctionData {
    private static final long serialVersionUID = -930632846167768864L;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FinalScriptFunctionData.class.desiredAssertionStatus();
    }

    public FinalScriptFunctionData(String name, int arity, List<CompiledFunction> functions, int flags) {
        super(name, arity, flags);
        this.code.addAll(functions);
        if ($assertionsDisabled || !needsCallee()) {
            return;
        }
        throw new AssertionError();
    }

    public FinalScriptFunctionData(String name, MethodHandle mh, Specialization[] specs, int flags) {
        super(name, methodHandleArity(mh), flags);
        addInvoker(mh);
        if (specs != null) {
            for (Specialization spec : specs) {
                addInvoker(spec.getMethodHandle(), spec);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public boolean needsCallee() {
        boolean needsCallee = this.code.getFirst().needsCallee();
        if ($assertionsDisabled || allNeedCallee(needsCallee)) {
            return needsCallee;
        }
        throw new AssertionError();
    }

    private boolean allNeedCallee(boolean needCallee) {
        Iterator<CompiledFunction> it = this.code.iterator();
        while (it.hasNext()) {
            CompiledFunction inv = it.next();
            if (inv.needsCallee() != needCallee) {
                return false;
            }
        }
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    CompiledFunction getBest(MethodType callSiteType, ScriptObject runtimeScope, Collection<CompiledFunction> forbidden, boolean linkLogicOkay) {
        if ($assertionsDisabled || isValidCallSite(callSiteType)) {
            CompiledFunction best = null;
            Iterator<CompiledFunction> it = this.code.iterator();
            while (it.hasNext()) {
                CompiledFunction candidate = it.next();
                if (linkLogicOkay || !candidate.hasLinkLogic()) {
                    if (!forbidden.contains(candidate) && candidate.betterThanFinal(best, callSiteType)) {
                        best = candidate;
                    }
                }
            }
            return best;
        }
        throw new AssertionError(callSiteType);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    MethodType getGenericType() {
        int max = 0;
        Iterator<CompiledFunction> it = this.code.iterator();
        while (it.hasNext()) {
            CompiledFunction fn = it.next();
            MethodType t = fn.type();
            if (ScriptFunctionData.isVarArg(t)) {
                return MethodType.genericMethodType(2, true);
            }
            int paramCount = t.parameterCount() - (ScriptFunctionData.needsCallee(t) ? 1 : 0);
            if (paramCount > max) {
                max = paramCount;
            }
        }
        return MethodType.genericMethodType(max + 1);
    }

    private CompiledFunction addInvoker(MethodHandle mh, Specialization specialization) {
        CompiledFunction invoker;
        if ($assertionsDisabled || !needsCallee(mh)) {
            if (isConstructor(mh)) {
                if (!$assertionsDisabled && !isConstructor()) {
                    throw new AssertionError();
                }
                invoker = CompiledFunction.createBuiltInConstructor(mh);
            } else {
                invoker = new CompiledFunction(mh, null, specialization);
            }
            this.code.add(invoker);
            return invoker;
        }
        throw new AssertionError();
    }

    private CompiledFunction addInvoker(MethodHandle mh) {
        return addInvoker(mh, null);
    }

    private static int methodHandleArity(MethodHandle mh) {
        if (isVarArg(mh)) {
            return LinkerCallSite.ARGLIMIT;
        }
        return ((mh.type().parameterCount() - 1) - (needsCallee(mh) ? 1 : 0)) - (isConstructor(mh) ? 1 : 0);
    }

    private static boolean isConstructor(MethodHandle mh) {
        return mh.type().parameterCount() >= 1 && mh.type().parameterType(0) == Boolean.TYPE;
    }
}
