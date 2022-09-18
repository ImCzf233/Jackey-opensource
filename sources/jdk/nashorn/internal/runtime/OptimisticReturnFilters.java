package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/OptimisticReturnFilters.class */
public final class OptimisticReturnFilters {
    private static final MethodHandle[] ENSURE_INT;
    private static final MethodHandle[] ENSURE_NUMBER;
    private static final int VOID_TYPE_INDEX;
    private static final int BOOLEAN_TYPE_INDEX;
    private static final int CHAR_TYPE_INDEX;
    private static final int LONG_TYPE_INDEX;
    private static final int FLOAT_TYPE_INDEX;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !OptimisticReturnFilters.class.desiredAssertionStatus();
        MethodHandle INT_DOUBLE = findOwnMH("ensureInt", Integer.TYPE, Double.TYPE, Integer.TYPE);
        ENSURE_INT = new MethodHandle[]{null, INT_DOUBLE, findOwnMH("ensureInt", Integer.TYPE, Object.class, Integer.TYPE), findOwnMH("ensureInt", Integer.TYPE, Integer.TYPE), findOwnMH("ensureInt", Integer.TYPE, Boolean.TYPE, Integer.TYPE), findOwnMH("ensureInt", Integer.TYPE, Character.TYPE, Integer.TYPE), findOwnMH("ensureInt", Integer.TYPE, Long.TYPE, Integer.TYPE), INT_DOUBLE.asType(INT_DOUBLE.type().changeParameterType(0, Float.TYPE))};
        VOID_TYPE_INDEX = ENSURE_INT.length - 5;
        BOOLEAN_TYPE_INDEX = ENSURE_INT.length - 4;
        CHAR_TYPE_INDEX = ENSURE_INT.length - 3;
        LONG_TYPE_INDEX = ENSURE_INT.length - 2;
        FLOAT_TYPE_INDEX = ENSURE_INT.length - 1;
        ENSURE_NUMBER = new MethodHandle[]{null, null, findOwnMH("ensureNumber", Double.TYPE, Object.class, Integer.TYPE), ENSURE_INT[VOID_TYPE_INDEX].asType(ENSURE_INT[VOID_TYPE_INDEX].type().changeReturnType(Double.TYPE)), ENSURE_INT[BOOLEAN_TYPE_INDEX].asType(ENSURE_INT[BOOLEAN_TYPE_INDEX].type().changeReturnType(Double.TYPE)), ENSURE_INT[CHAR_TYPE_INDEX].asType(ENSURE_INT[CHAR_TYPE_INDEX].type().changeReturnType(Double.TYPE)), findOwnMH("ensureNumber", Double.TYPE, Long.TYPE, Integer.TYPE), null};
    }

    public static MethodHandle filterOptimisticReturnValue(MethodHandle mh, Class<?> expectedReturnType, int programPoint) {
        if (!UnwarrantedOptimismException.isValid(programPoint)) {
            return mh;
        }
        MethodType type = mh.type();
        Class<?> actualReturnType = type.returnType();
        if (TypeUtilities.isConvertibleWithoutLoss(actualReturnType, expectedReturnType)) {
            return mh;
        }
        MethodHandle guard = getOptimisticTypeGuard(expectedReturnType, actualReturnType);
        return guard == null ? mh : Lookup.f248MH.filterReturnValue(mh, Lookup.f248MH.insertArguments(guard, guard.type().parameterCount() - 1, Integer.valueOf(programPoint)));
    }

    public static GuardedInvocation filterOptimisticReturnValue(GuardedInvocation inv, CallSiteDescriptor desc) {
        if (!NashornCallSiteDescriptor.isOptimistic(desc)) {
            return inv;
        }
        return inv.replaceMethods(filterOptimisticReturnValue(inv.getInvocation(), desc.getMethodType().returnType(), NashornCallSiteDescriptor.getProgramPoint(desc)), inv.getGuard());
    }

    private static MethodHandle getOptimisticTypeGuard(Class<?> actual, Class<?> provable) {
        MethodHandle guard;
        int provableTypeIndex = getProvableTypeIndex(provable);
        if (actual == Integer.TYPE) {
            guard = ENSURE_INT[provableTypeIndex];
        } else if (actual == Double.TYPE) {
            guard = ENSURE_NUMBER[provableTypeIndex];
        } else {
            guard = null;
            if (!$assertionsDisabled && actual.isPrimitive()) {
                throw new AssertionError(actual + ", " + provable);
            }
        }
        if (guard != null && !provable.isPrimitive()) {
            return guard.asType(guard.type().changeParameterType(0, provable));
        }
        return guard;
    }

    private static int getProvableTypeIndex(Class<?> provable) {
        int accTypeIndex = JSType.getAccessorTypeIndex(provable);
        if (accTypeIndex != -1) {
            return accTypeIndex;
        }
        if (provable == Boolean.TYPE) {
            return BOOLEAN_TYPE_INDEX;
        }
        if (provable == Void.TYPE) {
            return VOID_TYPE_INDEX;
        }
        if (provable == Byte.TYPE || provable == Short.TYPE) {
            return 0;
        }
        if (provable == Character.TYPE) {
            return CHAR_TYPE_INDEX;
        }
        if (provable == Long.TYPE) {
            return LONG_TYPE_INDEX;
        }
        if (provable == Float.TYPE) {
            return FLOAT_TYPE_INDEX;
        }
        throw new AssertionError(provable.getName());
    }

    private static int ensureInt(long arg, int programPoint) {
        if (JSType.isRepresentableAsInt(arg)) {
            return (int) arg;
        }
        throw UnwarrantedOptimismException.createNarrowest(Long.valueOf(arg), programPoint);
    }

    private static int ensureInt(double arg, int programPoint) {
        if (JSType.isStrictlyRepresentableAsInt(arg)) {
            return (int) arg;
        }
        throw new UnwarrantedOptimismException(Double.valueOf(arg), programPoint, Type.NUMBER);
    }

    public static int ensureInt(Object arg, int programPoint) {
        if (isPrimitiveNumberWrapper(arg)) {
            double d = ((Number) arg).doubleValue();
            if (JSType.isStrictlyRepresentableAsInt(d)) {
                return (int) d;
            }
        }
        throw UnwarrantedOptimismException.createNarrowest(arg, programPoint);
    }

    private static boolean isPrimitiveNumberWrapper(Object obj) {
        if (obj == null) {
            return false;
        }
        Class<?> c = obj.getClass();
        return c == Integer.class || c == Double.class || c == Long.class || c == Float.class || c == Short.class || c == Byte.class;
    }

    private static int ensureInt(boolean arg, int programPoint) {
        throw new UnwarrantedOptimismException(Boolean.valueOf(arg), programPoint, Type.OBJECT);
    }

    private static int ensureInt(char arg, int programPoint) {
        throw new UnwarrantedOptimismException(Character.valueOf(arg), programPoint, Type.OBJECT);
    }

    private static int ensureInt(int programPoint) {
        throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint, Type.OBJECT);
    }

    private static double ensureNumber(long arg, int programPoint) {
        if (JSType.isRepresentableAsDouble(arg)) {
            return arg;
        }
        throw new UnwarrantedOptimismException(Long.valueOf(arg), programPoint, Type.OBJECT);
    }

    public static double ensureNumber(Object arg, int programPoint) {
        if (isPrimitiveNumberWrapper(arg) && (arg.getClass() != Long.class || JSType.isRepresentableAsDouble(((Long) arg).longValue()))) {
            return ((Number) arg).doubleValue();
        }
        throw new UnwarrantedOptimismException(arg, programPoint, Type.OBJECT);
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), OptimisticReturnFilters.class, name, Lookup.f248MH.type(rtype, types));
    }
}
