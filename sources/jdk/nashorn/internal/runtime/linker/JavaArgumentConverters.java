package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/JavaArgumentConverters.class */
public final class JavaArgumentConverters {
    private static final MethodHandle TO_BOOLEAN = findOwnMH("toBoolean", Boolean.class, Object.class);
    private static final MethodHandle TO_STRING = findOwnMH("toString", String.class, Object.class);
    private static final MethodHandle TO_DOUBLE = findOwnMH("toDouble", Double.class, Object.class);
    private static final MethodHandle TO_NUMBER = findOwnMH("toNumber", Number.class, Object.class);
    private static final MethodHandle TO_LONG = findOwnMH("toLong", Long.class, Object.class);
    private static final MethodHandle TO_LONG_PRIMITIVE = findOwnMH("toLongPrimitive", Long.TYPE, Object.class);
    private static final MethodHandle TO_CHAR = findOwnMH("toChar", Character.class, Object.class);
    private static final MethodHandle TO_CHAR_PRIMITIVE = findOwnMH("toCharPrimitive", Character.TYPE, Object.class);
    private static final Map<Class<?>, MethodHandle> CONVERTERS = new HashMap();

    static {
        CONVERTERS.put(Number.class, TO_NUMBER);
        CONVERTERS.put(String.class, TO_STRING);
        CONVERTERS.put(Boolean.TYPE, JSType.TO_BOOLEAN.methodHandle());
        CONVERTERS.put(Boolean.class, TO_BOOLEAN);
        CONVERTERS.put(Character.TYPE, TO_CHAR_PRIMITIVE);
        CONVERTERS.put(Character.class, TO_CHAR);
        CONVERTERS.put(Double.TYPE, JSType.TO_NUMBER.methodHandle());
        CONVERTERS.put(Double.class, TO_DOUBLE);
        CONVERTERS.put(Long.TYPE, TO_LONG_PRIMITIVE);
        CONVERTERS.put(Long.class, TO_LONG);
        putLongConverter(Byte.class);
        putLongConverter(Short.class);
        putLongConverter(Integer.class);
        putDoubleConverter(Float.class);
    }

    private JavaArgumentConverters() {
    }

    public static MethodHandle getConverter(Class<?> targetType) {
        return CONVERTERS.get(targetType);
    }

    private static Boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj == null || obj == ScriptRuntime.UNDEFINED) {
            return null;
        }
        if (obj instanceof Number) {
            double num = ((Number) obj).doubleValue();
            return Boolean.valueOf(num != 0.0d && !Double.isNaN(num));
        } else if (JSType.isString(obj)) {
            return Boolean.valueOf(((CharSequence) obj).length() > 0);
        } else if (obj instanceof ScriptObject) {
            return true;
        } else {
            throw assertUnexpectedType(obj);
        }
    }

    private static Character toChar(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            int ival = ((Number) o).intValue();
            if (ival >= 0 && ival <= 65535) {
                return Character.valueOf((char) ival);
            }
            throw ECMAErrors.typeError("cant.convert.number.to.char", new String[0]);
        }
        String s = toString(o);
        if (s == null) {
            return null;
        }
        if (s.length() != 1) {
            throw ECMAErrors.typeError("cant.convert.string.to.char", new String[0]);
        }
        return Character.valueOf(s.charAt(0));
    }

    public static char toCharPrimitive(Object obj0) {
        Character c = toChar(obj0);
        if (c == null) {
            return (char) 0;
        }
        return c.charValue();
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSType.toString(obj);
    }

    private static Double toDouble(Object obj0) {
        Object obj = obj0;
        while (true) {
            Object obj2 = obj;
            if (obj2 == null) {
                return null;
            }
            if (obj2 instanceof Double) {
                return (Double) obj2;
            }
            if (obj2 instanceof Number) {
                return Double.valueOf(((Number) obj2).doubleValue());
            }
            if (obj2 instanceof String) {
                return Double.valueOf(JSType.toNumber((String) obj2));
            }
            if (obj2 instanceof ConsString) {
                return Double.valueOf(JSType.toNumber(obj2.toString()));
            }
            if (obj2 instanceof Boolean) {
                return Double.valueOf(((Boolean) obj2).booleanValue() ? 1.0d : 0.0d);
            } else if (obj2 instanceof ScriptObject) {
                obj = JSType.toPrimitive(obj2, Number.class);
            } else if (obj2 == ScriptRuntime.UNDEFINED) {
                return Double.valueOf(Double.NaN);
            } else {
                throw assertUnexpectedType(obj2);
            }
        }
    }

    private static Number toNumber(Object obj0) {
        Object obj = obj0;
        while (true) {
            Object obj2 = obj;
            if (obj2 == null) {
                return null;
            }
            if (obj2 instanceof Number) {
                return (Number) obj2;
            }
            if (obj2 instanceof String) {
                return Double.valueOf(JSType.toNumber((String) obj2));
            }
            if (obj2 instanceof ConsString) {
                return Double.valueOf(JSType.toNumber(obj2.toString()));
            }
            if (obj2 instanceof Boolean) {
                return Double.valueOf(((Boolean) obj2).booleanValue() ? 1.0d : 0.0d);
            } else if (obj2 instanceof ScriptObject) {
                obj = JSType.toPrimitive(obj2, Number.class);
            } else if (obj2 == ScriptRuntime.UNDEFINED) {
                return Double.valueOf(Double.NaN);
            } else {
                throw assertUnexpectedType(obj2);
            }
        }
    }

    private static Long toLong(Object obj0) {
        Object obj = obj0;
        while (true) {
            Object obj2 = obj;
            if (obj2 == null) {
                return null;
            }
            if (obj2 instanceof Long) {
                return (Long) obj2;
            }
            if (obj2 instanceof Integer) {
                return Long.valueOf(((Integer) obj2).longValue());
            }
            if (obj2 instanceof Double) {
                Double d = (Double) obj2;
                if (Double.isInfinite(d.doubleValue())) {
                    return 0L;
                }
                return Long.valueOf(d.longValue());
            } else if (obj2 instanceof Float) {
                Float f = (Float) obj2;
                if (Float.isInfinite(f.floatValue())) {
                    return 0L;
                }
                return Long.valueOf(f.longValue());
            } else if (obj2 instanceof Number) {
                return Long.valueOf(((Number) obj2).longValue());
            } else {
                if (JSType.isString(obj2)) {
                    return Long.valueOf(JSType.toLong(obj2));
                }
                if (obj2 instanceof Boolean) {
                    return Long.valueOf(((Boolean) obj2).booleanValue() ? 1L : 0L);
                } else if (obj2 instanceof ScriptObject) {
                    obj = JSType.toPrimitive(obj2, Number.class);
                } else if (obj2 == ScriptRuntime.UNDEFINED) {
                    return null;
                } else {
                    throw assertUnexpectedType(obj2);
                }
            }
        }
    }

    private static AssertionError assertUnexpectedType(Object obj) {
        return new AssertionError("Unexpected type" + obj.getClass().getName() + ". Guards should have prevented this");
    }

    private static long toLongPrimitive(Object obj0) {
        Long l = toLong(obj0);
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), JavaArgumentConverters.class, name, Lookup.f248MH.type(rtype, types));
    }

    private static void putDoubleConverter(Class<?> targetType) {
        Class<?> primitive = TypeUtilities.getPrimitiveType(targetType);
        CONVERTERS.put(primitive, Lookup.f248MH.explicitCastArguments(JSType.TO_NUMBER.methodHandle(), JSType.TO_NUMBER.methodHandle().type().changeReturnType(primitive)));
        CONVERTERS.put(targetType, Lookup.f248MH.filterReturnValue(TO_DOUBLE, findOwnMH(primitive.getName() + "Value", targetType, Double.class)));
    }

    private static void putLongConverter(Class<?> targetType) {
        Class<?> primitive = TypeUtilities.getPrimitiveType(targetType);
        CONVERTERS.put(primitive, Lookup.f248MH.explicitCastArguments(TO_LONG_PRIMITIVE, TO_LONG_PRIMITIVE.type().changeReturnType(primitive)));
        CONVERTERS.put(targetType, Lookup.f248MH.filterReturnValue(TO_LONG, findOwnMH(primitive.getName() + "Value", targetType, Long.class)));
    }

    private static Byte byteValue(Long l) {
        if (l == null) {
            return null;
        }
        return Byte.valueOf(l.byteValue());
    }

    private static Short shortValue(Long l) {
        if (l == null) {
            return null;
        }
        return Short.valueOf(l.shortValue());
    }

    private static Integer intValue(Long l) {
        if (l == null) {
            return null;
        }
        return Integer.valueOf(l.intValue());
    }

    private static Float floatValue(Double d) {
        if (d == null) {
            return null;
        }
        return Float.valueOf(d.floatValue());
    }
}
