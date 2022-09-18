package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.support.AbstractCallSiteDescriptor;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/NashornCallSiteDescriptor.class */
public final class NashornCallSiteDescriptor extends AbstractCallSiteDescriptor {
    public static final int CALLSITE_SCOPE = 1;
    public static final int CALLSITE_STRICT = 2;
    public static final int CALLSITE_FAST_SCOPE = 4;
    public static final int CALLSITE_OPTIMISTIC = 8;
    public static final int CALLSITE_APPLY_TO_CALL = 16;
    public static final int CALLSITE_DECLARE = 32;
    public static final int CALLSITE_PROFILE = 64;
    public static final int CALLSITE_TRACE = 128;
    public static final int CALLSITE_TRACE_MISSES = 256;
    public static final int CALLSITE_TRACE_ENTEREXIT = 512;
    public static final int CALLSITE_TRACE_VALUES = 1024;
    public static final int CALLSITE_PROGRAM_POINT_SHIFT = 11;
    public static final int MAX_PROGRAM_POINT_VALUE = 2097151;
    public static final int FLAGS_MASK = 2047;
    private static final ClassValue<ConcurrentMap<NashornCallSiteDescriptor, NashornCallSiteDescriptor>> canonicals;
    private final MethodHandles.Lookup lookup;
    private final String operator;
    private final String operand;
    private final MethodType methodType;
    private final int flags;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornCallSiteDescriptor.class.desiredAssertionStatus();
        canonicals = new ClassValue<ConcurrentMap<NashornCallSiteDescriptor, NashornCallSiteDescriptor>>() { // from class: jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor.1
            protected ConcurrentMap<NashornCallSiteDescriptor, NashornCallSiteDescriptor> computeValue(Class<?> type) {
                return new ConcurrentHashMap();
            }
        };
    }

    public static String toString(int flags) {
        StringBuilder sb = new StringBuilder();
        if ((flags & 1) != 0) {
            if ((flags & 4) != 0) {
                sb.append("fastscope ");
            } else if (!$assertionsDisabled && (flags & 4) != 0) {
                throw new AssertionError("can't be fastscope without scope");
            } else {
                sb.append("scope ");
            }
            if ((flags & 32) != 0) {
                sb.append("declare ");
            }
        }
        if ((flags & 16) != 0) {
            sb.append("apply2call ");
        }
        if ((flags & 2) != 0) {
            sb.append("strict ");
        }
        return sb.length() == 0 ? "" : " " + sb.toString().trim();
    }

    public static NashornCallSiteDescriptor get(MethodHandles.Lookup lookup, String name, MethodType methodType, int flags) {
        String[] tokenizedName = CallSiteDescriptorFactory.tokenizeName(name);
        if ($assertionsDisabled || tokenizedName.length >= 2) {
            if (!$assertionsDisabled && !"dyn".equals(tokenizedName[0])) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && tokenizedName[1] == null) {
                throw new AssertionError();
            }
            return get(lookup, tokenizedName[1], tokenizedName.length == 3 ? tokenizedName[2].intern() : null, methodType, flags);
        }
        throw new AssertionError();
    }

    private static NashornCallSiteDescriptor get(MethodHandles.Lookup lookup, String operator, String operand, MethodType methodType, int flags) {
        NashornCallSiteDescriptor csd = new NashornCallSiteDescriptor(lookup, operator, operand, methodType, flags);
        ConcurrentMap<NashornCallSiteDescriptor, NashornCallSiteDescriptor> classCanonicals = (ConcurrentMap) canonicals.get(lookup.lookupClass());
        NashornCallSiteDescriptor canonical = classCanonicals.putIfAbsent(csd, csd);
        return canonical != null ? canonical : csd;
    }

    private NashornCallSiteDescriptor(MethodHandles.Lookup lookup, String operator, String operand, MethodType methodType, int flags) {
        this.lookup = lookup;
        this.operator = operator;
        this.operand = operand;
        this.methodType = methodType;
        this.flags = flags;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public int getNameTokenCount() {
        return this.operand == null ? 2 : 3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getNameToken(int i) {
        switch (i) {
            case 0:
                return "dyn";
            case 1:
                return this.operator;
            case 2:
                if (this.operand != null) {
                    return this.operand;
                }
                break;
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    @Override // jdk.internal.dynalink.support.AbstractCallSiteDescriptor, jdk.internal.dynalink.CallSiteDescriptor
    public MethodHandles.Lookup getLookup() {
        return this.lookup;
    }

    @Override // jdk.internal.dynalink.support.AbstractCallSiteDescriptor
    public boolean equals(CallSiteDescriptor csd) {
        return super.equals(csd) && this.flags == getFlags(csd);
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodType getMethodType() {
        return this.methodType;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getFirstOperator() {
        int delim = this.operator.indexOf(CallSiteDescriptor.OPERATOR_DELIMITER);
        return delim == -1 ? this.operator : this.operator.substring(0, delim);
    }

    public String getOperand() {
        return this.operand;
    }

    public String getFunctionDescription() {
        if ($assertionsDisabled || getFirstOperator().equals("call") || getFirstOperator().equals("new")) {
            if (getNameTokenCount() <= 2) {
                return null;
            }
            return getNameToken(2);
        }
        throw new AssertionError();
    }

    public static String getFunctionDescription(CallSiteDescriptor desc) {
        if (desc instanceof NashornCallSiteDescriptor) {
            return ((NashornCallSiteDescriptor) desc).getFunctionDescription();
        }
        return null;
    }

    public String getFunctionErrorMessage(Object obj) {
        String funcDesc = getFunctionDescription();
        return funcDesc != null ? funcDesc : ScriptRuntime.safeToString(obj);
    }

    public static String getFunctionErrorMessage(CallSiteDescriptor desc, Object obj) {
        if (desc instanceof NashornCallSiteDescriptor) {
            return ((NashornCallSiteDescriptor) desc).getFunctionErrorMessage(obj);
        }
        return ScriptRuntime.safeToString(obj);
    }

    public static int getFlags(CallSiteDescriptor desc) {
        if (desc instanceof NashornCallSiteDescriptor) {
            return ((NashornCallSiteDescriptor) desc).flags;
        }
        return 0;
    }

    private boolean isFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    private static boolean isFlag(CallSiteDescriptor desc, int flag) {
        return (getFlags(desc) & flag) != 0;
    }

    public static boolean isScope(CallSiteDescriptor desc) {
        return isFlag(desc, 1);
    }

    public static boolean isFastScope(CallSiteDescriptor desc) {
        return isFlag(desc, 4);
    }

    public static boolean isStrict(CallSiteDescriptor desc) {
        return isFlag(desc, 2);
    }

    public static boolean isApplyToCall(CallSiteDescriptor desc) {
        return isFlag(desc, 16);
    }

    public static boolean isOptimistic(CallSiteDescriptor desc) {
        return isFlag(desc, 8);
    }

    public static boolean isDeclaration(CallSiteDescriptor desc) {
        return isFlag(desc, 32);
    }

    public static boolean isStrictFlag(int flags) {
        return (flags & 2) != 0;
    }

    public static boolean isScopeFlag(int flags) {
        return (flags & 1) != 0;
    }

    public static int getProgramPoint(CallSiteDescriptor desc) {
        if ($assertionsDisabled || isOptimistic(desc)) {
            return getFlags(desc) >> 11;
        }
        throw new AssertionError("program point requested from non-optimistic descriptor " + desc);
    }

    public boolean isProfile() {
        return isFlag(64);
    }

    public boolean isTrace() {
        return isFlag(128);
    }

    public boolean isTraceMisses() {
        return isFlag(256);
    }

    public boolean isTraceEnterExit() {
        return isFlag(512);
    }

    public boolean isTraceObjects() {
        return isFlag(1024);
    }

    boolean isOptimistic() {
        return isFlag(8);
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType newMethodType) {
        return get(getLookup(), this.operator, this.operand, newMethodType, this.flags);
    }
}
