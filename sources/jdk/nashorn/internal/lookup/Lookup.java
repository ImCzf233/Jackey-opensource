package jdk.nashorn.internal.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/lookup/Lookup.class */
public final class Lookup {

    /* renamed from: MH */
    public static final MethodHandleFunctionality f248MH;
    public static final MethodHandle EMPTY_GETTER;
    public static final MethodHandle EMPTY_SETTER;
    public static final MethodHandle TYPE_ERROR_THROWER_GETTER;
    public static final MethodHandle TYPE_ERROR_THROWER_SETTER;
    public static final MethodType GET_OBJECT_TYPE;
    public static final MethodType SET_OBJECT_TYPE;
    public static final MethodType GET_PRIMITIVE_TYPE;
    public static final MethodType SET_PRIMITIVE_TYPE;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Lookup.class.desiredAssertionStatus();
        f248MH = MethodHandleFactory.getFunctionality();
        EMPTY_GETTER = findOwnMH("emptyGetter", Object.class, Object.class);
        EMPTY_SETTER = findOwnMH("emptySetter", Void.TYPE, Object.class, Object.class);
        TYPE_ERROR_THROWER_GETTER = findOwnMH("typeErrorThrowerGetter", Object.class, Object.class);
        TYPE_ERROR_THROWER_SETTER = findOwnMH("typeErrorThrowerSetter", Void.TYPE, Object.class, Object.class);
        GET_OBJECT_TYPE = f248MH.type(Object.class, Object.class);
        SET_OBJECT_TYPE = f248MH.type(Void.TYPE, Object.class, Object.class);
        GET_PRIMITIVE_TYPE = f248MH.type(Long.TYPE, Object.class);
        SET_PRIMITIVE_TYPE = f248MH.type(Void.TYPE, Object.class, Long.TYPE);
    }

    private Lookup() {
    }

    public static Object emptyGetter(Object self) {
        return ScriptRuntime.UNDEFINED;
    }

    public static void emptySetter(Object self, Object value) {
    }

    public static MethodHandle emptyGetter(Class<?> type) {
        return filterReturnType(EMPTY_GETTER, type);
    }

    public static Object typeErrorThrowerGetter(Object self) {
        throw ECMAErrors.typeError("strict.getter.setter.poison", ScriptRuntime.safeToString(self));
    }

    public static void typeErrorThrowerSetter(Object self, Object value) {
        throw ECMAErrors.typeError("strict.getter.setter.poison", ScriptRuntime.safeToString(self));
    }

    public static MethodHandle filterArgumentType(MethodHandle mh, int n, Class<?> from) {
        Class<?> to = mh.type().parameterType(n);
        if (from != Integer.TYPE) {
            if (from == Long.TYPE) {
                if (to == Integer.TYPE) {
                    return f248MH.filterArguments(mh, n, JSType.TO_INT32_L.methodHandle());
                }
            } else if (from == Double.TYPE) {
                if (to == Integer.TYPE) {
                    return f248MH.filterArguments(mh, n, JSType.TO_INT32_D.methodHandle());
                }
                if (to == Long.TYPE) {
                    return f248MH.filterArguments(mh, n, JSType.TO_UINT32_D.methodHandle());
                }
            } else if (!from.isPrimitive()) {
                if (to == Integer.TYPE) {
                    return f248MH.filterArguments(mh, n, JSType.TO_INT32.methodHandle());
                }
                if (to == Long.TYPE) {
                    return f248MH.filterArguments(mh, n, JSType.TO_UINT32.methodHandle());
                }
                if (to == Double.TYPE) {
                    return f248MH.filterArguments(mh, n, JSType.TO_NUMBER.methodHandle());
                }
                if (!to.isPrimitive()) {
                    return mh;
                }
                if (!$assertionsDisabled) {
                    throw new AssertionError("unsupported Lookup.filterReturnType type " + from + " -> " + to);
                }
            }
        }
        return f248MH.explicitCastArguments(mh, mh.type().changeParameterType(n, from));
    }

    public static MethodHandle filterReturnType(MethodHandle mh, Class<?> type) {
        Class<?> retType = mh.type().returnType();
        if (retType != Integer.TYPE) {
            if (retType == Long.TYPE) {
                if (type == Integer.TYPE) {
                    return f248MH.filterReturnValue(mh, JSType.TO_INT32_L.methodHandle());
                }
            } else if (retType == Double.TYPE) {
                if (type == Integer.TYPE) {
                    return f248MH.filterReturnValue(mh, JSType.TO_INT32_D.methodHandle());
                }
                if (type == Long.TYPE) {
                    return f248MH.filterReturnValue(mh, JSType.TO_UINT32_D.methodHandle());
                }
            } else if (!retType.isPrimitive()) {
                if (type == Integer.TYPE) {
                    return f248MH.filterReturnValue(mh, JSType.TO_INT32.methodHandle());
                }
                if (type == Long.TYPE) {
                    return f248MH.filterReturnValue(mh, JSType.TO_UINT32.methodHandle());
                }
                if (type == Double.TYPE) {
                    return f248MH.filterReturnValue(mh, JSType.TO_NUMBER.methodHandle());
                }
                if (!type.isPrimitive()) {
                    return mh;
                }
                if (!$assertionsDisabled) {
                    throw new AssertionError("unsupported Lookup.filterReturnType type " + retType + " -> " + type);
                }
            }
        }
        return f248MH.explicitCastArguments(mh, mh.type().changeReturnType(type));
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return f248MH.findStatic(MethodHandles.lookup(), Lookup.class, name, f248MH.type(rtype, types));
    }
}
