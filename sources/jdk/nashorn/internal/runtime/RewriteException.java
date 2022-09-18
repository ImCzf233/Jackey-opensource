package jdk.nashorn.internal.runtime;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.Global;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/RewriteException.class */
public final class RewriteException extends Exception {

    /* renamed from: MH */
    private static final MethodHandleFunctionality f270MH;
    private ScriptObject runtimeScope;
    private Object[] byteCodeSlots;
    private final int[] previousContinuationEntryPoints;
    public static final CompilerConstants.Call GET_BYTECODE_SLOTS;
    public static final CompilerConstants.Call GET_PROGRAM_POINT;
    public static final CompilerConstants.Call GET_RETURN_VALUE;
    public static final CompilerConstants.Call BOOTSTRAP;
    private static final CompilerConstants.Call POPULATE_ARRAY;
    public static final CompilerConstants.Call TO_LONG_ARRAY;
    public static final CompilerConstants.Call TO_DOUBLE_ARRAY;
    public static final CompilerConstants.Call TO_OBJECT_ARRAY;
    public static final CompilerConstants.Call INSTANCE_OR_NULL;
    public static final CompilerConstants.Call ASSERT_ARRAY_LENGTH;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RewriteException.class.desiredAssertionStatus();
        f270MH = MethodHandleFactory.getFunctionality();
        GET_BYTECODE_SLOTS = CompilerConstants.virtualCallNoLookup(RewriteException.class, "getByteCodeSlots", Object[].class, new Class[0]);
        GET_PROGRAM_POINT = CompilerConstants.virtualCallNoLookup(RewriteException.class, "getProgramPoint", Integer.TYPE, new Class[0]);
        GET_RETURN_VALUE = CompilerConstants.virtualCallNoLookup(RewriteException.class, "getReturnValueDestructive", Object.class, new Class[0]);
        BOOTSTRAP = CompilerConstants.staticCallNoLookup(RewriteException.class, "populateArrayBootstrap", CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Integer.TYPE);
        POPULATE_ARRAY = CompilerConstants.staticCall(MethodHandles.lookup(), RewriteException.class, "populateArray", Object[].class, Object[].class, Integer.TYPE, Object[].class);
        TO_LONG_ARRAY = CompilerConstants.staticCallNoLookup(RewriteException.class, "toLongArray", long[].class, Object.class, RewriteException.class);
        TO_DOUBLE_ARRAY = CompilerConstants.staticCallNoLookup(RewriteException.class, "toDoubleArray", double[].class, Object.class, RewriteException.class);
        TO_OBJECT_ARRAY = CompilerConstants.staticCallNoLookup(RewriteException.class, "toObjectArray", Object[].class, Object.class, RewriteException.class);
        INSTANCE_OR_NULL = CompilerConstants.staticCallNoLookup(RewriteException.class, "instanceOrNull", Object.class, Object.class, Class.class);
        ASSERT_ARRAY_LENGTH = CompilerConstants.staticCallNoLookup(RewriteException.class, "assertArrayLength", Void.TYPE, Object[].class, Integer.TYPE);
    }

    private RewriteException(UnwarrantedOptimismException e, Object[] byteCodeSlots, String[] byteCodeSymbolNames, int[] previousContinuationEntryPoints) {
        super("", e, false, Context.DEBUG);
        this.byteCodeSlots = byteCodeSlots;
        this.runtimeScope = mergeSlotsWithScope(byteCodeSlots, byteCodeSymbolNames);
        this.previousContinuationEntryPoints = previousContinuationEntryPoints;
    }

    public static RewriteException create(UnwarrantedOptimismException e, Object[] byteCodeSlots, String[] byteCodeSymbolNames) {
        return create(e, byteCodeSlots, byteCodeSymbolNames, null);
    }

    public static RewriteException create(UnwarrantedOptimismException e, Object[] byteCodeSlots, String[] byteCodeSymbolNames, int[] previousContinuationEntryPoints) {
        return new RewriteException(e, byteCodeSlots, byteCodeSymbolNames, previousContinuationEntryPoints);
    }

    public static CallSite populateArrayBootstrap(MethodHandles.Lookup lookup, String name, MethodType type, int startIndex) {
        MethodHandle mh = POPULATE_ARRAY.methodHandle();
        return new ConstantCallSite(f270MH.asType(f270MH.asCollector(f270MH.insertArguments(mh, 1, Integer.valueOf(startIndex)), Object[].class, type.parameterCount() - 1), type));
    }

    private static ScriptObject mergeSlotsWithScope(Object[] byteCodeSlots, String[] byteCodeSymbolNames) {
        ScriptObject locals = Global.newEmptyInstance();
        int l = Math.min(byteCodeSlots.length, byteCodeSymbolNames.length);
        ScriptObject runtimeScope = null;
        String scopeName = CompilerConstants.SCOPE.symbolName();
        for (int i = 0; i < l; i++) {
            String name = byteCodeSymbolNames[i];
            Object value = byteCodeSlots[i];
            if (scopeName.equals(name)) {
                if (!$assertionsDisabled && runtimeScope != null) {
                    throw new AssertionError();
                }
                runtimeScope = (ScriptObject) value;
            } else if (name != null) {
                locals.set(name, value, 2);
            }
        }
        locals.setProto(runtimeScope);
        return locals;
    }

    public static Object[] populateArray(Object[] arrayToBePopluated, int startIndex, Object[] items) {
        System.arraycopy(items, 0, arrayToBePopluated, startIndex, items.length);
        return arrayToBePopluated;
    }

    public static long[] toLongArray(Object obj, RewriteException e) {
        if (obj instanceof long[]) {
            return (long[]) obj;
        }
        if (!$assertionsDisabled && !(obj instanceof int[])) {
            throw new AssertionError();
        }
        int[] in = (int[]) obj;
        long[] out = new long[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return (long[]) e.replaceByteCodeValue(in, out);
    }

    public static double[] toDoubleArray(Object obj, RewriteException e) {
        if (obj instanceof double[]) {
            return (double[]) obj;
        }
        if (!$assertionsDisabled && !(obj instanceof int[]) && !(obj instanceof long[])) {
            throw new AssertionError();
        }
        int l = Array.getLength(obj);
        double[] out = new double[l];
        for (int i = 0; i < l; i++) {
            out[i] = Array.getDouble(obj, i);
        }
        return (double[]) e.replaceByteCodeValue(obj, out);
    }

    public static Object[] toObjectArray(Object obj, RewriteException e) {
        if (obj instanceof Object[]) {
            return (Object[]) obj;
        }
        if (!$assertionsDisabled && !(obj instanceof int[]) && !(obj instanceof long[]) && !(obj instanceof double[])) {
            throw new AssertionError(obj + " is " + obj.getClass().getName());
        }
        int l = Array.getLength(obj);
        Object[] out = new Object[l];
        for (int i = 0; i < l; i++) {
            out[i] = Array.get(obj, i);
        }
        return (Object[]) e.replaceByteCodeValue(obj, out);
    }

    public static Object instanceOrNull(Object obj, Class<?> clazz) {
        if (clazz.isInstance(obj)) {
            return obj;
        }
        return null;
    }

    public static void assertArrayLength(Object[] arr, int length) {
        int i = arr.length;
        do {
            int i2 = i;
            i--;
            if (i2 <= length) {
                return;
            }
        } while (arr[i] == ScriptRuntime.UNDEFINED);
        throw new AssertionError(String.format("Expected array length %d, but it is %d", Integer.valueOf(length), Integer.valueOf(i + 1)));
    }

    private <T> T replaceByteCodeValue(Object in, T out) {
        for (int i = 0; i < this.byteCodeSlots.length; i++) {
            if (this.byteCodeSlots[i] == in) {
                this.byteCodeSlots[i] = out;
            }
        }
        return out;
    }

    private UnwarrantedOptimismException getUOE() {
        return (UnwarrantedOptimismException) getCause();
    }

    public Object getReturnValueDestructive() {
        if ($assertionsDisabled || this.byteCodeSlots != null) {
            this.byteCodeSlots = null;
            this.runtimeScope = null;
            return getUOE().getReturnValueDestructive();
        }
        throw new AssertionError();
    }

    public Object getReturnValueNonDestructive() {
        return getUOE().getReturnValueNonDestructive();
    }

    public Type getReturnType() {
        return getUOE().getReturnType();
    }

    public int getProgramPoint() {
        return getUOE().getProgramPoint();
    }

    public Object[] getByteCodeSlots() {
        if (this.byteCodeSlots == null) {
            return null;
        }
        return (Object[]) this.byteCodeSlots.clone();
    }

    public int[] getPreviousContinuationEntryPoints() {
        if (this.previousContinuationEntryPoints == null) {
            return null;
        }
        return (int[]) this.previousContinuationEntryPoints.clone();
    }

    public ScriptObject getRuntimeScope() {
        return this.runtimeScope;
    }

    private static String stringify(Object returnValue) {
        if (returnValue == null) {
            return Configurator.NULL;
        }
        String str = returnValue.toString();
        if (returnValue instanceof String) {
            str = '\'' + str + '\'';
        } else if (returnValue instanceof Double) {
            str = str + 'd';
        } else if (returnValue instanceof Long) {
            str = str + 'l';
        }
        return str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return getMessage(false);
    }

    public String getMessageShort() {
        return getMessage(true);
    }

    private String getMessage(boolean isShort) {
        Object[] slots;
        StringBuilder sb = new StringBuilder();
        sb.append("[pp=").append(getProgramPoint()).append(", ");
        if (!isShort && (slots = this.byteCodeSlots) != null) {
            sb.append("slots=").append(Arrays.asList(slots)).append(", ");
        }
        sb.append("type=").append(getReturnType()).append(", ");
        sb.append("value=").append(stringify(getReturnValueNonDestructive())).append(")]");
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream out) throws NotSerializableException {
        throw new NotSerializableException(getClass().getName());
    }

    private void readObject(ObjectInputStream in) throws NotSerializableException {
        throw new NotSerializableException(getClass().getName());
    }
}
