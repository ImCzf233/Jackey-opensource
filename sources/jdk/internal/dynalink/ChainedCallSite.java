package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.AbstractRelinkableCallSite;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/ChainedCallSite.class */
public class ChainedCallSite extends AbstractRelinkableCallSite {
    private static final MethodHandle PRUNE_CATCHES = MethodHandles.insertArguments(Lookup.findOwnSpecial(MethodHandles.lookup(), "prune", MethodHandle.class, MethodHandle.class, Boolean.TYPE), 2, true);
    private static final MethodHandle PRUNE_SWITCHPOINTS = MethodHandles.insertArguments(Lookup.findOwnSpecial(MethodHandles.lookup(), "prune", MethodHandle.class, MethodHandle.class, Boolean.TYPE), 2, false);
    private final AtomicReference<LinkedList<GuardedInvocation>> invocations = new AtomicReference<>();

    public ChainedCallSite(CallSiteDescriptor descriptor) {
        super(descriptor);
    }

    protected int getMaxChainLength() {
        return 8;
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void relink(GuardedInvocation guardedInvocation, MethodHandle fallback) {
        relinkInternal(guardedInvocation, fallback, false, false);
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle fallback) {
        relinkInternal(guardedInvocation, fallback, true, false);
    }

    private MethodHandle relinkInternal(GuardedInvocation invocation, MethodHandle relink, boolean reset, boolean removeCatches) {
        LinkedList<GuardedInvocation> currentInvocations = this.invocations.get();
        LinkedList<GuardedInvocation> newInvocations = (currentInvocations == null || reset) ? new LinkedList<>() : (LinkedList) currentInvocations.clone();
        Iterator<GuardedInvocation> it = newInvocations.iterator();
        while (it.hasNext()) {
            GuardedInvocation inv = it.next();
            if (inv.hasBeenInvalidated() || (removeCatches && inv.getException() != null)) {
                it.remove();
            }
        }
        if (invocation != null) {
            if (newInvocations.size() == getMaxChainLength()) {
                newInvocations.removeFirst();
            }
            newInvocations.addLast(invocation);
        }
        MethodHandle pruneAndInvokeSwitchPoints = makePruneAndInvokeMethod(relink, getPruneSwitchpoints());
        MethodHandle pruneAndInvokeCatches = makePruneAndInvokeMethod(relink, getPruneCatches());
        MethodHandle target = relink;
        Iterator<GuardedInvocation> it2 = newInvocations.iterator();
        while (it2.hasNext()) {
            target = it2.next().compose(target, pruneAndInvokeSwitchPoints, pruneAndInvokeCatches);
        }
        if (this.invocations.compareAndSet(currentInvocations, newInvocations)) {
            setTarget(target);
        }
        return target;
    }

    protected MethodHandle getPruneSwitchpoints() {
        return PRUNE_SWITCHPOINTS;
    }

    public MethodHandle getPruneCatches() {
        return PRUNE_CATCHES;
    }

    private MethodHandle makePruneAndInvokeMethod(MethodHandle relink, MethodHandle prune) {
        MethodHandle boundPrune = MethodHandles.insertArguments(prune, 0, this, relink);
        MethodHandle ignoreArgsPrune = MethodHandles.dropArguments(boundPrune, 0, type().parameterList());
        return MethodHandles.foldArguments(MethodHandles.exactInvoker(type()), ignoreArgsPrune);
    }

    private MethodHandle prune(MethodHandle relink, boolean catches) {
        return relinkInternal(null, relink, false, catches);
    }
}
