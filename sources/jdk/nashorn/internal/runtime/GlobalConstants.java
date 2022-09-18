package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "const")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/GlobalConstants.class */
public final class GlobalConstants implements Loggable {
    public static final boolean GLOBAL_ONLY = true;
    private static final MethodHandles.Lookup LOOKUP;
    private static final MethodHandle INVALIDATE_SP;
    private static final MethodHandle RECEIVER_GUARD;
    private final DebugLogger log;
    private final Map<String, Access> map = new HashMap();
    private final AtomicBoolean invalidatedForever = new AtomicBoolean(false);
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !GlobalConstants.class.desiredAssertionStatus();
        LOOKUP = MethodHandles.lookup();
        INVALIDATE_SP = CompilerConstants.virtualCall(LOOKUP, GlobalConstants.class, "invalidateSwitchPoint", Object.class, Object.class, Access.class).methodHandle();
        RECEIVER_GUARD = CompilerConstants.staticCall(LOOKUP, GlobalConstants.class, "receiverGuard", Boolean.TYPE, Access.class, Object.class, Object.class).methodHandle();
    }

    public GlobalConstants(DebugLogger log) {
        this.log = log == null ? DebugLogger.DISABLED_LOGGER : log;
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return DebugLogger.DISABLED_LOGGER;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/GlobalConstants$Access.class */
    public static class Access {
        private final String name;

        /* renamed from: sp */
        private SwitchPoint f268sp;
        private int invalidations;
        private boolean guardFailed;
        private static final int MAX_RETRIES = 2;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !GlobalConstants.class.desiredAssertionStatus();
        }

        private Access(String name, SwitchPoint sp) {
            this.name = name;
            this.f268sp = sp;
        }

        public boolean hasBeenInvalidated() {
            return this.f268sp.hasBeenInvalidated();
        }

        public boolean guardFailed() {
            return this.guardFailed;
        }

        public void failGuard() {
            invalidateOnce();
            this.guardFailed = true;
        }

        public void newSwitchPoint() {
            if ($assertionsDisabled || hasBeenInvalidated()) {
                this.f268sp = new SwitchPoint();
                return;
            }
            throw new AssertionError();
        }

        private void invalidate(int count) {
            if (!this.f268sp.hasBeenInvalidated()) {
                SwitchPoint.invalidateAll(new SwitchPoint[]{this.f268sp});
                this.invalidations += count;
            }
        }

        public void invalidateUncounted() {
            invalidate(0);
        }

        public void invalidateOnce() {
            invalidate(1);
        }

        public void invalidateForever() {
            invalidate(2);
        }

        public boolean mayRetry() {
            return this.invalidations < 2;
        }

        public String toString() {
            return "[" + DebugLogger.quote(this.name) + " <id=" + Debug.m67id(this) + "> inv#=" + this.invalidations + "/2 sp_inv=" + this.f268sp.hasBeenInvalidated() + ']';
        }

        String getName() {
            return this.name;
        }

        SwitchPoint getSwitchPoint() {
            return this.f268sp;
        }
    }

    public void invalidateAll() {
        if (!this.invalidatedForever.get()) {
            this.log.info("New global created - invalidating all constant callsites without increasing invocation count.");
            synchronized (this) {
                for (Access acc : this.map.values()) {
                    acc.invalidateUncounted();
                }
            }
        }
    }

    public void invalidateForever() {
        if (this.invalidatedForever.compareAndSet(false, true)) {
            this.log.info("New global created - invalidating all constant callsites.");
            synchronized (this) {
                for (Access acc : this.map.values()) {
                    acc.invalidateForever();
                }
                this.map.clear();
            }
        }
    }

    private synchronized Object invalidateSwitchPoint(Object obj, Access acc) {
        if (this.log.isEnabled()) {
            this.log.info("*** Invalidating switchpoint " + acc.getSwitchPoint() + " for receiver=" + obj + " access=" + acc);
        }
        acc.invalidateOnce();
        if (acc.mayRetry()) {
            if (this.log.isEnabled()) {
                this.log.info("Retry is allowed for " + acc + "... Creating a new switchpoint.");
            }
            acc.newSwitchPoint();
        } else if (this.log.isEnabled()) {
            this.log.info("This was the last time I allowed " + DebugLogger.quote(acc.getName()) + " to relink as constant.");
        }
        return obj;
    }

    private Access getOrCreateSwitchPoint(String name) {
        Access acc = this.map.get(name);
        if (acc != null) {
            return acc;
        }
        SwitchPoint sp = new SwitchPoint();
        Map<String, Access> map = this.map;
        Access acc2 = new Access(name, sp);
        map.put(name, acc2);
        return acc2;
    }

    public void delete(String name) {
        if (!this.invalidatedForever.get()) {
            synchronized (this) {
                Access acc = this.map.get(name);
                if (acc != null) {
                    acc.invalidateForever();
                }
            }
        }
    }

    private static boolean receiverGuard(Access acc, Object boundReceiver, Object receiver) {
        boolean id = receiver == boundReceiver;
        if (!id) {
            acc.failGuard();
        }
        return id;
    }

    private static boolean isGlobalSetter(ScriptObject receiver, FindProperty find) {
        if (find == null) {
            return receiver.isScope();
        }
        return find.getOwner().isGlobal();
    }

    public GuardedInvocation findSetMethod(FindProperty find, ScriptObject receiver, GuardedInvocation inv, CallSiteDescriptor desc, LinkRequest request) {
        if (this.invalidatedForever.get() || !isGlobalSetter(receiver, find)) {
            return null;
        }
        String name = desc.getNameToken(2);
        synchronized (this) {
            Access acc = getOrCreateSwitchPoint(name);
            if (this.log.isEnabled()) {
                this.log.fine("Trying to link constant SETTER ", acc);
            }
            if (acc.mayRetry() && !this.invalidatedForever.get()) {
                if (acc.hasBeenInvalidated()) {
                    this.log.info("New chance for " + acc);
                    acc.newSwitchPoint();
                }
                if (!$assertionsDisabled && acc.hasBeenInvalidated()) {
                    throw new AssertionError();
                }
                MethodHandle target = inv.getInvocation();
                Class<?> receiverType = target.type().parameterType(0);
                MethodHandle boundInvalidator = Lookup.f248MH.bindTo(INVALIDATE_SP, this);
                MethodHandle invalidator = Lookup.f248MH.asType(boundInvalidator, boundInvalidator.type().changeParameterType(0, receiverType).changeReturnType(receiverType));
                MethodHandle mh = Lookup.f248MH.filterArguments(inv.getInvocation(), 0, Lookup.f248MH.insertArguments(invalidator, 1, acc));
                if (!$assertionsDisabled && inv.getSwitchPoints() != null) {
                    throw new AssertionError(Arrays.asList(inv.getSwitchPoints()));
                }
                this.log.info("Linked setter " + DebugLogger.quote(name) + " " + acc.getSwitchPoint());
                return new GuardedInvocation(mh, inv.getGuard(), acc.getSwitchPoint(), inv.getException());
            }
            if (this.log.isEnabled()) {
                this.log.fine("*** SET: Giving up on " + DebugLogger.quote(name) + " - retry count has exceeded " + DynamicLinker.getLinkedCallSiteLocation());
            }
            return null;
        }
    }

    public static MethodHandle staticConstantGetter(Object c) {
        return Lookup.f248MH.dropArguments(JSType.unboxConstant(c), 0, Object.class);
    }

    private MethodHandle constantGetter(Object c) {
        MethodHandle mh = staticConstantGetter(c);
        if (this.log.isEnabled()) {
            return MethodHandleFactory.addDebugPrintout(this.log, Level.FINEST, mh, "getting as constant");
        }
        return mh;
    }

    public GuardedInvocation findGetMethod(FindProperty find, ScriptObject receiver, CallSiteDescriptor desc) {
        MethodHandle mh;
        MethodHandle guard;
        if (this.invalidatedForever.get() || !NashornCallSiteDescriptor.isFastScope(desc) || !find.getOwner().isGlobal() || (find.getProperty() instanceof UserAccessorProperty)) {
            return null;
        }
        boolean isOptimistic = NashornCallSiteDescriptor.isOptimistic(desc);
        int programPoint = isOptimistic ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1;
        Class<?> retType = desc.getMethodType().returnType();
        String name = desc.getNameToken(2);
        synchronized (this) {
            Access acc = getOrCreateSwitchPoint(name);
            this.log.fine("Starting to look up object value " + name);
            Object c = find.getObjectValue();
            if (this.log.isEnabled()) {
                this.log.fine("Trying to link constant GETTER " + acc + " value = " + c);
            }
            if (acc.hasBeenInvalidated() || acc.guardFailed() || this.invalidatedForever.get()) {
                if (this.log.isEnabled()) {
                    this.log.info("*** GET: Giving up on " + DebugLogger.quote(name) + " - retry count has exceeded " + DynamicLinker.getLinkedCallSiteLocation());
                }
                return null;
            }
            MethodHandle cmh = constantGetter(c);
            if (isOptimistic) {
                if (JSType.getAccessorTypeIndex(cmh.type().returnType()) <= JSType.getAccessorTypeIndex(retType)) {
                    mh = Lookup.f248MH.asType(cmh, cmh.type().changeReturnType(retType));
                } else {
                    mh = Lookup.f248MH.dropArguments(Lookup.f248MH.insertArguments(JSType.THROW_UNWARRANTED.methodHandle(), 0, c, Integer.valueOf(programPoint)), 0, Object.class);
                }
            } else {
                mh = Lookup.filterReturnType(cmh, retType);
            }
            if (find.getOwner().isGlobal()) {
                guard = null;
            } else {
                guard = Lookup.f248MH.insertArguments(RECEIVER_GUARD, 0, acc, receiver);
            }
            if (this.log.isEnabled()) {
                this.log.info("Linked getter " + DebugLogger.quote(name) + " as MethodHandle.constant() -> " + c + " " + acc.getSwitchPoint());
                mh = MethodHandleFactory.addDebugPrintout(this.log, Level.FINE, mh, "get const " + acc);
            }
            return new GuardedInvocation(mh, guard, acc.getSwitchPoint(), (Class<? extends Throwable>) null);
        }
    }
}
