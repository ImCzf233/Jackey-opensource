package jdk.nashorn.internal.runtime.linker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.ChainedCallSite;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite.class */
public class LinkerCallSite extends ChainedCallSite {
    public static final int ARGLIMIT = 250;
    private int catchInvalidations;
    private static LongAdder count;
    private static LongAdder missCount;
    private static final String PROFILEFILE = Options.getStringProperty("nashorn.profilefile", "NashornProfile.txt");
    private static final MethodHandle INCREASE_MISS_COUNTER = Lookup.f248MH.findStatic(MethodHandles.lookup(), LinkerCallSite.class, "increaseMissCount", Lookup.f248MH.type(Object.class, String.class, Object.class));
    private static final MethodHandle ON_CATCH_INVALIDATION = Lookup.f248MH.findStatic(MethodHandles.lookup(), LinkerCallSite.class, "onCatchInvalidation", Lookup.f248MH.type(ChainedCallSite.class, LinkerCallSite.class));
    private static final HashMap<String, AtomicInteger> missCounts = new HashMap<>();

    /* renamed from: r */
    private static final Random f280r = new Random();
    private static final int missSamplingPercentage = Options.getIntProperty("nashorn.tcs.miss.samplePercent", 1);

    static {
        if (Context.DEBUG) {
            count = new LongAdder();
            missCount = new LongAdder();
        }
    }

    LinkerCallSite(NashornCallSiteDescriptor descriptor) {
        super(descriptor);
        if (Context.DEBUG) {
            count.increment();
        }
    }

    @Override // jdk.internal.dynalink.ChainedCallSite
    public MethodHandle getPruneCatches() {
        return Lookup.f248MH.filterArguments(super.getPruneCatches(), 0, ON_CATCH_INVALIDATION);
    }

    private static ChainedCallSite onCatchInvalidation(LinkerCallSite callSite) {
        callSite.catchInvalidations++;
        return callSite;
    }

    public static int getCatchInvalidationCount(Object callSiteToken) {
        if (callSiteToken instanceof LinkerCallSite) {
            return ((LinkerCallSite) callSiteToken).catchInvalidations;
        }
        return 0;
    }

    public static LinkerCallSite newLinkerCallSite(MethodHandles.Lookup lookup, String name, MethodType type, int flags) {
        NashornCallSiteDescriptor desc = NashornCallSiteDescriptor.get(lookup, name, type, flags);
        if (desc.isProfile()) {
            return ProfilingLinkerCallSite.newProfilingLinkerCallSite(desc);
        }
        if (desc.isTrace()) {
            return new TracingLinkerCallSite(desc);
        }
        return new LinkerCallSite(desc);
    }

    public String toString() {
        return getDescriptor().toString();
    }

    public NashornCallSiteDescriptor getNashornDescriptor() {
        return (NashornCallSiteDescriptor) getDescriptor();
    }

    @Override // jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
    public void relink(GuardedInvocation invocation, MethodHandle relink) {
        super.relink(invocation, getDebuggingRelink(relink));
    }

    @Override // jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
    public void resetAndRelink(GuardedInvocation invocation, MethodHandle relink) {
        super.resetAndRelink(invocation, getDebuggingRelink(relink));
    }

    private MethodHandle getDebuggingRelink(MethodHandle relink) {
        return Context.DEBUG ? Lookup.f248MH.filterArguments(relink, 0, getIncreaseMissCounter(relink.type().parameterType(0))) : relink;
    }

    private MethodHandle getIncreaseMissCounter(Class<?> type) {
        MethodHandle missCounterWithDesc = Lookup.f248MH.bindTo(INCREASE_MISS_COUNTER, getDescriptor().getName() + " @ " + getScriptLocation());
        if (type == Object.class) {
            return missCounterWithDesc;
        }
        return Lookup.f248MH.asType(missCounterWithDesc, missCounterWithDesc.type().changeParameterType(0, type).changeReturnType(type));
    }

    public static String getScriptLocation() {
        StackTraceElement caller = DynamicLinker.getLinkedCallSiteLocation();
        return caller == null ? "unknown location" : caller.getFileName() + CallSiteDescriptor.TOKEN_DELIMITER + caller.getLineNumber();
    }

    public static Object increaseMissCount(String desc, Object self) {
        missCount.increment();
        if (f280r.nextInt(100) < missSamplingPercentage) {
            AtomicInteger i = missCounts.get(desc);
            if (i == null) {
                missCounts.put(desc, new AtomicInteger(1));
            } else {
                i.incrementAndGet();
            }
        }
        return self;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite$ProfilingLinkerCallSite.class */
    public static class ProfilingLinkerCallSite extends LinkerCallSite {
        private long startTime;
        private int depth;
        private long totalTime;
        private long hitCount;
        private static LinkedList<ProfilingLinkerCallSite> profileCallSites = null;
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static final MethodHandle PROFILEENTRY = Lookup.f248MH.findVirtual(LOOKUP, ProfilingLinkerCallSite.class, "profileEntry", Lookup.f248MH.type(Object.class, Object.class));
        private static final MethodHandle PROFILEEXIT = Lookup.f248MH.findVirtual(LOOKUP, ProfilingLinkerCallSite.class, "profileExit", Lookup.f248MH.type(Object.class, Object.class));
        private static final MethodHandle PROFILEVOIDEXIT = Lookup.f248MH.findVirtual(LOOKUP, ProfilingLinkerCallSite.class, "profileVoidExit", Lookup.f248MH.type(Void.TYPE, new Class[0]));

        ProfilingLinkerCallSite(NashornCallSiteDescriptor desc) {
            super(desc);
        }

        public static ProfilingLinkerCallSite newProfilingLinkerCallSite(NashornCallSiteDescriptor desc) {
            if (profileCallSites == null) {
                profileCallSites = new LinkedList<>();
                Thread profileDumperThread = new Thread(new ProfileDumper());
                Runtime.getRuntime().addShutdownHook(profileDumperThread);
            }
            ProfilingLinkerCallSite callSite = new ProfilingLinkerCallSite(desc);
            profileCallSites.add(callSite);
            return callSite;
        }

        @Override // java.lang.invoke.MutableCallSite, java.lang.invoke.CallSite
        public void setTarget(MethodHandle newTarget) {
            MethodHandle methodHandle;
            MethodType type = type();
            boolean isVoid = type.returnType() == Void.TYPE;
            Class<?> newSelfType = newTarget.type().parameterType(0);
            MethodHandle selfFilter = Lookup.f248MH.bindTo(PROFILEENTRY, this);
            if (newSelfType != Object.class) {
                MethodType selfFilterType = MethodType.methodType(newSelfType, newSelfType);
                selfFilter = selfFilter.asType(selfFilterType);
            }
            MethodHandle methodHandle2 = Lookup.f248MH.filterArguments(newTarget, 0, selfFilter);
            if (isVoid) {
                methodHandle = Lookup.f248MH.filterReturnValue(methodHandle2, Lookup.f248MH.bindTo(PROFILEVOIDEXIT, this));
            } else {
                MethodType filter = Lookup.f248MH.type(type.returnType(), type.returnType());
                methodHandle = Lookup.f248MH.filterReturnValue(methodHandle2, Lookup.f248MH.asType(Lookup.f248MH.bindTo(PROFILEEXIT, this), filter));
            }
            super.setTarget(methodHandle);
        }

        public Object profileEntry(Object self) {
            if (this.depth == 0) {
                this.startTime = System.nanoTime();
            }
            this.depth++;
            this.hitCount++;
            return self;
        }

        public Object profileExit(Object result) {
            this.depth--;
            if (this.depth == 0) {
                this.totalTime += System.nanoTime() - this.startTime;
            }
            return result;
        }

        public void profileVoidExit() {
            this.depth--;
            if (this.depth == 0) {
                this.totalTime += System.nanoTime() - this.startTime;
            }
        }

        /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite$ProfilingLinkerCallSite$ProfileDumper.class */
        public static class ProfileDumper implements Runnable {
            ProfileDumper() {
            }

            @Override // java.lang.Runnable
            public void run() {
                PrintWriter out = null;
                boolean fileOutput = false;
                try {
                    try {
                        out = new PrintWriter(new FileOutputStream(LinkerCallSite.PROFILEFILE));
                        fileOutput = true;
                    } catch (FileNotFoundException e) {
                        out = Context.getCurrentErr();
                    }
                    dump(out);
                    if (out != null && fileOutput) {
                        out.close();
                    }
                } catch (Throwable th) {
                    if (out != null && fileOutput) {
                        out.close();
                    }
                    throw th;
                }
            }

            private static void dump(PrintWriter out) {
                int index = 0;
                Iterator it = ProfilingLinkerCallSite.profileCallSites.iterator();
                while (it.hasNext()) {
                    ProfilingLinkerCallSite callSite = (ProfilingLinkerCallSite) it.next();
                    int i = index;
                    index++;
                    out.println("" + i + '\t' + callSite.getDescriptor().getName() + '\t' + callSite.totalTime + '\t' + callSite.hitCount);
                }
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite$TracingLinkerCallSite.class */
    public static class TracingLinkerCallSite extends LinkerCallSite {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static final MethodHandle TRACEOBJECT = Lookup.f248MH.findVirtual(LOOKUP, TracingLinkerCallSite.class, "traceObject", Lookup.f248MH.type(Object.class, MethodHandle.class, Object[].class));
        private static final MethodHandle TRACEVOID = Lookup.f248MH.findVirtual(LOOKUP, TracingLinkerCallSite.class, "traceVoid", Lookup.f248MH.type(Void.TYPE, MethodHandle.class, Object[].class));
        private static final MethodHandle TRACEMISS = Lookup.f248MH.findVirtual(LOOKUP, TracingLinkerCallSite.class, "traceMiss", Lookup.f248MH.type(Void.TYPE, String.class, Object[].class));

        TracingLinkerCallSite(NashornCallSiteDescriptor desc) {
            super(desc);
        }

        @Override // java.lang.invoke.MutableCallSite, java.lang.invoke.CallSite
        public void setTarget(MethodHandle newTarget) {
            if (!getNashornDescriptor().isTraceEnterExit()) {
                super.setTarget(newTarget);
                return;
            }
            MethodType type = type();
            boolean isVoid = type.returnType() == Void.TYPE;
            MethodHandle traceMethodHandle = isVoid ? TRACEVOID : TRACEOBJECT;
            super.setTarget(Lookup.f248MH.asType(Lookup.f248MH.asCollector(Lookup.f248MH.bindTo(Lookup.f248MH.bindTo(traceMethodHandle, this), newTarget), Object[].class, type.parameterCount()), type));
        }

        @Override // jdk.internal.dynalink.support.AbstractRelinkableCallSite, jdk.internal.dynalink.RelinkableCallSite
        public void initialize(MethodHandle relinkAndInvoke) {
            super.initialize(getFallbackLoggingRelink(relinkAndInvoke));
        }

        @Override // jdk.nashorn.internal.runtime.linker.LinkerCallSite, jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
        public void relink(GuardedInvocation invocation, MethodHandle relink) {
            super.relink(invocation, getFallbackLoggingRelink(relink));
        }

        @Override // jdk.nashorn.internal.runtime.linker.LinkerCallSite, jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
        public void resetAndRelink(GuardedInvocation invocation, MethodHandle relink) {
            super.resetAndRelink(invocation, getFallbackLoggingRelink(relink));
        }

        private MethodHandle getFallbackLoggingRelink(MethodHandle relink) {
            if (!getNashornDescriptor().isTraceMisses()) {
                return relink;
            }
            MethodType type = relink.type();
            return Lookup.f248MH.foldArguments(relink, Lookup.f248MH.asType(Lookup.f248MH.asCollector(Lookup.f248MH.insertArguments(TRACEMISS, 0, this, "MISS " + LinkerCallSite.getScriptLocation() + " "), Object[].class, type.parameterCount()), type.changeReturnType(Void.TYPE)));
        }

        private void printObject(PrintWriter out, Object arg) {
            if (!getNashornDescriptor().isTraceObjects()) {
                out.print(arg instanceof ScriptObject ? "ScriptObject" : arg);
            } else if (arg instanceof ScriptObject) {
                ScriptObject object = (ScriptObject) arg;
                boolean isFirst = true;
                Set<Object> keySet = object.keySet();
                if (keySet.isEmpty()) {
                    out.print(ScriptRuntime.safeToString(arg));
                    return;
                }
                out.print("{ ");
                for (Object key : keySet) {
                    if (!isFirst) {
                        out.print(", ");
                    }
                    out.print(key);
                    out.print(CallSiteDescriptor.TOKEN_DELIMITER);
                    Object value = object.get(key);
                    if (value instanceof ScriptObject) {
                        out.print("...");
                    } else {
                        printObject(out, value);
                    }
                    isFirst = false;
                }
                out.print(" }");
            } else {
                out.print(ScriptRuntime.safeToString(arg));
            }
        }

        private void tracePrint(PrintWriter out, String tag, Object[] args, Object result) {
            out.print(Debug.m67id(this) + " TAG " + tag);
            out.print(getDescriptor().getName() + "(");
            if (args.length > 0) {
                printObject(out, args[0]);
                for (int i = 1; i < args.length; i++) {
                    Object arg = args[i];
                    out.print(", ");
                    if (!(arg instanceof ScriptObject) || !((ScriptObject) arg).isScope()) {
                        printObject(out, arg);
                    } else {
                        out.print("SCOPE");
                    }
                }
            }
            out.print(")");
            if (tag.equals("EXIT  ")) {
                out.print(" --> ");
                printObject(out, result);
            }
            out.println();
        }

        public Object traceObject(MethodHandle mh, Object... args) throws Throwable {
            PrintWriter out = Context.getCurrentErr();
            tracePrint(out, "ENTER ", args, null);
            Object result = mh.invokeWithArguments(args);
            tracePrint(out, "EXIT  ", args, result);
            return result;
        }

        public void traceVoid(MethodHandle mh, Object... args) throws Throwable {
            PrintWriter out = Context.getCurrentErr();
            tracePrint(out, "ENTER ", args, null);
            mh.invokeWithArguments(args);
            tracePrint(out, "EXIT  ", args, null);
        }

        public void traceMiss(String desc, Object... args) throws Throwable {
            tracePrint(Context.getCurrentErr(), desc, args, null);
        }
    }

    @Override // jdk.internal.dynalink.ChainedCallSite
    protected int getMaxChainLength() {
        return 8;
    }

    public static long getCount() {
        return count.longValue();
    }

    public static long getMissCount() {
        return missCount.longValue();
    }

    public static int getMissSamplingPercentage() {
        return missSamplingPercentage;
    }

    public static void getMissCounts(PrintWriter out) {
        ArrayList<Map.Entry<String, AtomicInteger>> entries = new ArrayList<>(missCounts.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, AtomicInteger>>() { // from class: jdk.nashorn.internal.runtime.linker.LinkerCallSite.1
            public int compare(Map.Entry<String, AtomicInteger> o1, Map.Entry<String, AtomicInteger> o2) {
                return o2.getValue().get() - o1.getValue().get();
            }
        });
        Iterator<Map.Entry<String, AtomicInteger>> it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry<String, AtomicInteger> entry = it.next();
            out.println("  " + entry.getKey() + "\t" + entry.getValue().get());
        }
    }
}
