package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import kotlin.jvm.internal.CharCompanionObject;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/JSType.class */
public enum JSType {
    UNDEFINED("undefined"),
    NULL("object"),
    BOOLEAN("boolean"),
    NUMBER("number"),
    STRING("string"),
    OBJECT("object"),
    FUNCTION("function");
    
    private final String typeName;
    public static final long MAX_UINT = 4294967295L;
    private static final MethodHandles.Lookup JSTYPE_LOOKUP;
    public static final CompilerConstants.Call TO_BOOLEAN;
    public static final CompilerConstants.Call TO_BOOLEAN_D;
    public static final CompilerConstants.Call TO_INTEGER;
    public static final CompilerConstants.Call TO_LONG;
    public static final CompilerConstants.Call TO_LONG_D;
    public static final CompilerConstants.Call TO_NUMBER;
    public static final CompilerConstants.Call TO_NUMBER_OPTIMISTIC;
    public static final CompilerConstants.Call TO_STRING;
    public static final CompilerConstants.Call TO_INT32;
    public static final CompilerConstants.Call TO_INT32_L;
    public static final CompilerConstants.Call TO_INT32_OPTIMISTIC;
    public static final CompilerConstants.Call TO_INT32_D;
    public static final CompilerConstants.Call TO_UINT32_OPTIMISTIC;
    public static final CompilerConstants.Call TO_UINT32_DOUBLE;
    public static final CompilerConstants.Call TO_UINT32;
    public static final CompilerConstants.Call TO_UINT32_D;
    public static final CompilerConstants.Call TO_STRING_D;
    public static final CompilerConstants.Call TO_PRIMITIVE_TO_STRING;
    public static final CompilerConstants.Call TO_PRIMITIVE_TO_CHARSEQUENCE;
    public static final CompilerConstants.Call THROW_UNWARRANTED;
    public static final CompilerConstants.Call ADD_EXACT;
    public static final CompilerConstants.Call SUB_EXACT;
    public static final CompilerConstants.Call MUL_EXACT;
    public static final CompilerConstants.Call DIV_EXACT;
    public static final CompilerConstants.Call DIV_ZERO;
    public static final CompilerConstants.Call REM_ZERO;
    public static final CompilerConstants.Call REM_EXACT;
    public static final CompilerConstants.Call DECREMENT_EXACT;
    public static final CompilerConstants.Call INCREMENT_EXACT;
    public static final CompilerConstants.Call NEGATE_EXACT;
    public static final CompilerConstants.Call TO_JAVA_ARRAY;
    public static final CompilerConstants.Call VOID_RETURN;
    public static final CompilerConstants.Call IS_STRING;
    public static final CompilerConstants.Call IS_NUMBER;
    private static final List<Type> ACCESSOR_TYPES;
    public static final int TYPE_UNDEFINED_INDEX = -1;
    public static final int TYPE_INT_INDEX = 0;
    public static final int TYPE_DOUBLE_INDEX = 1;
    public static final int TYPE_OBJECT_INDEX = 2;
    public static final List<MethodHandle> CONVERT_OBJECT;
    public static final List<MethodHandle> CONVERT_OBJECT_OPTIMISTIC;
    public static final int UNDEFINED_INT = 0;
    public static final long UNDEFINED_LONG = 0;
    public static final double UNDEFINED_DOUBLE = Double.NaN;
    private static final long MAX_PRECISE_DOUBLE = 9007199254740992L;
    private static final long MIN_PRECISE_DOUBLE = -9007199254740992L;
    public static final List<MethodHandle> GET_UNDEFINED;
    private static final double INT32_LIMIT = 4.294967296E9d;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JSType.class.desiredAssertionStatus();
        JSTYPE_LOOKUP = MethodHandles.lookup();
        TO_BOOLEAN = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toBoolean", Boolean.TYPE, Object.class);
        TO_BOOLEAN_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toBoolean", Boolean.TYPE, Double.TYPE);
        TO_INTEGER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInteger", Integer.TYPE, Object.class);
        TO_LONG = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toLong", Long.TYPE, Object.class);
        TO_LONG_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toLong", Long.TYPE, Double.TYPE);
        TO_NUMBER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toNumber", Double.TYPE, Object.class);
        TO_NUMBER_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toNumberOptimistic", Double.TYPE, Object.class, Integer.TYPE);
        TO_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toString", String.class, Object.class);
        TO_INT32 = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, Object.class);
        TO_INT32_L = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, Long.TYPE);
        TO_INT32_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32Optimistic", Integer.TYPE, Object.class, Integer.TYPE);
        TO_INT32_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, Double.TYPE);
        TO_UINT32_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32Optimistic", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        TO_UINT32_DOUBLE = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32Double", Double.TYPE, Integer.TYPE);
        TO_UINT32 = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32", Long.TYPE, Object.class);
        TO_UINT32_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32", Long.TYPE, Double.TYPE);
        TO_STRING_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toString", String.class, Double.TYPE);
        TO_PRIMITIVE_TO_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toPrimitiveToString", String.class, Object.class);
        TO_PRIMITIVE_TO_CHARSEQUENCE = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toPrimitiveToCharSequence", CharSequence.class, Object.class);
        THROW_UNWARRANTED = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "throwUnwarrantedOptimismException", Object.class, Object.class, Integer.TYPE);
        ADD_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "addExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        SUB_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "subExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        MUL_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "mulExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        DIV_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "divExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        DIV_ZERO = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "divZero", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        REM_ZERO = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "remZero", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        REM_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "remExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        DECREMENT_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "decrementExact", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        INCREMENT_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "incrementExact", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        NEGATE_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "negateExact", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        TO_JAVA_ARRAY = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toJavaArray", Object.class, Object.class, Class.class);
        VOID_RETURN = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "voidReturn", Void.TYPE, new Class[0]);
        IS_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "isString", Boolean.TYPE, Object.class);
        IS_NUMBER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "isNumber", Boolean.TYPE, Object.class);
        ACCESSOR_TYPES = Collections.unmodifiableList(Arrays.asList(Type.INT, Type.NUMBER, Type.OBJECT));
        CONVERT_OBJECT = toUnmodifiableList(TO_INT32.methodHandle(), TO_NUMBER.methodHandle(), null);
        CONVERT_OBJECT_OPTIMISTIC = toUnmodifiableList(TO_INT32_OPTIMISTIC.methodHandle(), TO_NUMBER_OPTIMISTIC.methodHandle(), null);
        GET_UNDEFINED = toUnmodifiableList(Lookup.f248MH.constant(Integer.TYPE, 0), Lookup.f248MH.constant(Double.TYPE, Double.valueOf(Double.NaN)), Lookup.f248MH.constant(Object.class, Undefined.getUndefined()));
    }

    JSType(String typeName) {
        this.typeName = typeName;
    }

    public final String typeName() {
        return this.typeName;
    }

    /* renamed from: of */
    public static JSType m66of(Object obj) {
        if (obj == null) {
            return NULL;
        }
        if (obj instanceof ScriptObject) {
            return obj instanceof ScriptFunction ? FUNCTION : OBJECT;
        } else if (obj instanceof Boolean) {
            return BOOLEAN;
        } else {
            if (isString(obj)) {
                return STRING;
            }
            if (isNumber(obj)) {
                return NUMBER;
            }
            if (obj == ScriptRuntime.UNDEFINED) {
                return UNDEFINED;
            }
            return Bootstrap.isCallable(obj) ? FUNCTION : OBJECT;
        }
    }

    public static JSType ofNoFunction(Object obj) {
        if (obj == null) {
            return NULL;
        }
        if (obj instanceof ScriptObject) {
            return OBJECT;
        }
        if (obj instanceof Boolean) {
            return BOOLEAN;
        }
        if (isString(obj)) {
            return STRING;
        }
        if (isNumber(obj)) {
            return NUMBER;
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return UNDEFINED;
        }
        return OBJECT;
    }

    public static void voidReturn() {
    }

    public static boolean isRepresentableAsInt(long number) {
        return ((long) ((int) number)) == number;
    }

    public static boolean isRepresentableAsInt(double number) {
        return ((double) ((int) number)) == number;
    }

    public static boolean isStrictlyRepresentableAsInt(double number) {
        return isRepresentableAsInt(number) && isNotNegativeZero(number);
    }

    public static boolean isRepresentableAsInt(Object obj) {
        if (obj instanceof Number) {
            return isRepresentableAsInt(((Number) obj).doubleValue());
        }
        return false;
    }

    public static boolean isRepresentableAsLong(double number) {
        return ((double) ((long) number)) == number;
    }

    public static boolean isRepresentableAsDouble(long number) {
        return MAX_PRECISE_DOUBLE >= number && number >= MIN_PRECISE_DOUBLE;
    }

    private static boolean isNotNegativeZero(double number) {
        return Double.doubleToRawLongBits(number) != Long.MIN_VALUE;
    }

    public static boolean isPrimitive(Object obj) {
        return obj == null || obj == ScriptRuntime.UNDEFINED || isString(obj) || isNumber(obj) || (obj instanceof Boolean);
    }

    public static Object toPrimitive(Object obj) {
        return toPrimitive(obj, (Class<?>) null);
    }

    public static Object toPrimitive(Object obj, Class<?> hint) {
        if (obj instanceof ScriptObject) {
            return toPrimitive((ScriptObject) obj, hint);
        }
        if (isPrimitive(obj)) {
            return obj;
        }
        if (hint == Number.class && (obj instanceof Number)) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        if (obj instanceof JSObject) {
            return toPrimitive((JSObject) obj, hint);
        }
        if (obj instanceof StaticClass) {
            String name = ((StaticClass) obj).getRepresentedClass().getName();
            return new StringBuilder(12 + name.length()).append("[JavaClass ").append(name).append(']').toString();
        }
        return obj.toString();
    }

    private static Object toPrimitive(ScriptObject sobj, Class<?> hint) {
        return requirePrimitive(sobj.getDefaultValue(hint));
    }

    private static Object requirePrimitive(Object result) {
        if (!isPrimitive(result)) {
            throw ECMAErrors.typeError("bad.default.value", result.toString());
        }
        return result;
    }

    public static Object toPrimitive(JSObject jsobj, Class<?> hint) {
        try {
            return requirePrimitive(AbstractJSObject.getDefaultValue(jsobj, hint));
        } catch (UnsupportedOperationException e) {
            throw new ECMAException(Context.getGlobal().newTypeError(e.getMessage()), e);
        }
    }

    public static String toPrimitiveToString(Object obj) {
        return toString(toPrimitive(obj));
    }

    public static CharSequence toPrimitiveToCharSequence(Object obj) {
        return toCharSequence(toPrimitive(obj));
    }

    public static boolean toBoolean(double num) {
        return num != 0.0d && !Double.isNaN(num);
    }

    public static boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (nullOrUndefined(obj)) {
            return false;
        }
        if (!(obj instanceof Number)) {
            return !isString(obj) || ((CharSequence) obj).length() > 0;
        }
        double num = ((Number) obj).doubleValue();
        return num != 0.0d && !Double.isNaN(num);
    }

    public static String toString(Object obj) {
        return toStringImpl(obj, false);
    }

    public static CharSequence toCharSequence(Object obj) {
        if (obj instanceof ConsString) {
            return (CharSequence) obj;
        }
        return toString(obj);
    }

    public static boolean isString(Object obj) {
        return (obj instanceof String) || (obj instanceof ConsString);
    }

    public static boolean isNumber(Object obj) {
        if (obj != null) {
            Class<?> c = obj.getClass();
            return c == Integer.class || c == Double.class || c == Float.class || c == Short.class || c == Byte.class;
        }
        return false;
    }

    public static String toString(int num) {
        return Integer.toString(num);
    }

    public static String toString(double num) {
        if (isRepresentableAsInt(num)) {
            return Integer.toString((int) num);
        }
        if (num == Double.POSITIVE_INFINITY) {
            return "Infinity";
        }
        if (num == Double.NEGATIVE_INFINITY) {
            return "-Infinity";
        }
        if (Double.isNaN(num)) {
            return "NaN";
        }
        return NumberToString.stringFor(num);
    }

    public static String toString(double num, int radix) {
        if ($assertionsDisabled || (radix >= 2 && radix <= 36)) {
            if (isRepresentableAsInt(num)) {
                return Integer.toString((int) num, radix);
            }
            if (num == Double.POSITIVE_INFINITY) {
                return "Infinity";
            }
            if (num == Double.NEGATIVE_INFINITY) {
                return "-Infinity";
            }
            if (Double.isNaN(num)) {
                return "NaN";
            }
            if (num == 0.0d) {
                return "0";
            }
            StringBuilder sb = new StringBuilder();
            boolean negative = num < 0.0d;
            double signedNum = negative ? -num : num;
            double intPart = Math.floor(signedNum);
            double decPart = signedNum - intPart;
            do {
                double remainder = intPart % radix;
                sb.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((int) remainder));
                intPart = (intPart - remainder) / radix;
            } while (intPart >= 1.0d);
            if (negative) {
                sb.append('-');
            }
            sb.reverse();
            if (decPart > 0.0d) {
                int dot = sb.length();
                sb.append('.');
                do {
                    double decPart2 = decPart * radix;
                    double d = Math.floor(decPart2);
                    sb.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((int) d));
                    decPart = decPart2 - d;
                    if (decPart <= 0.0d) {
                        break;
                    }
                } while (sb.length() - dot < 1100);
            }
            return sb.toString();
        }
        throw new AssertionError("invalid radix");
    }

    public static double toNumber(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue();
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return toNumberGeneric(obj);
    }

    public static double toNumberForEq(Object obj) {
        if (obj == null) {
            return Double.NaN;
        }
        return toNumber(obj);
    }

    public static double toNumberForStrictEq(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue();
        }
        if (isNumber(obj)) {
            return ((Number) obj).doubleValue();
        }
        return Double.NaN;
    }

    public static Number toNarrowestNumber(long l) {
        return Double.valueOf(isRepresentableAsInt(l) ? Integer.valueOf((int) l).intValue() : Double.valueOf(l).doubleValue());
    }

    public static double toNumber(Boolean b) {
        return b.booleanValue() ? 1.0d : 0.0d;
    }

    public static double toNumber(ScriptObject obj) {
        return toNumber(toPrimitive(obj, (Class<?>) Number.class));
    }

    public static double toNumberOptimistic(Object obj, int programPoint) {
        Class<?> clz;
        if (obj != null && ((clz = obj.getClass()) == Double.class || clz == Integer.class || clz == Long.class)) {
            return ((Number) obj).doubleValue();
        }
        throw new UnwarrantedOptimismException(obj, programPoint);
    }

    public static double toNumberMaybeOptimistic(Object obj, int programPoint) {
        return UnwarrantedOptimismException.isValid(programPoint) ? toNumberOptimistic(obj, programPoint) : toNumber(obj);
    }

    public static int digit(char ch, int radix) {
        return digit(ch, radix, false);
    }

    public static int digit(char ch, int radix, boolean onlyIsoLatin1) {
        char maxInRadix = (char) ((97 + (radix - 1)) - 10);
        char c = Character.toLowerCase(ch);
        if (c >= 'a' && c <= maxInRadix) {
            return Character.digit(ch, radix);
        }
        if (Character.isDigit(ch)) {
            if (!onlyIsoLatin1 || (ch >= '0' && ch <= '9')) {
                return Character.digit(ch, radix);
            }
            return -1;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
        if (jdk.nashorn.internal.parser.Lexer.isJSWhitespace(r6.charAt(r7 - 1)) == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
        r7 = r7 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0043, code lost:
        if (r9 != '-') goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004b, code lost:
        if (r8 != r7) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
        return Double.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0052, code lost:
        r9 = r6.charAt(r8);
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0061, code lost:
        if (r9 != '+') goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0069, code lost:
        if (r8 != r7) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006c, code lost:
        return Double.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0070, code lost:
        r9 = r6.charAt(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0076, code lost:
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007d, code lost:
        if ((r8 + 1) >= r7) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0083, code lost:
        if (r9 != '0') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0092, code lost:
        if (java.lang.Character.toLowerCase(r6.charAt(r8 + 1)) != 'x') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0095, code lost:
        r11 = parseRadix(r6.toCharArray(), r8 + 2, r7, 16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00aa, code lost:
        if (r9 != 'I') goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b2, code lost:
        if ((r7 - r8) != 8) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c0, code lost:
        if (r6.regionMatches(r8, "Infinity", 0, 8) == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c5, code lost:
        if (r10 == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00c8, code lost:
        return Double.NEGATIVE_INFINITY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ce, code lost:
        return Double.POSITIVE_INFINITY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d2, code lost:
        r13 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d8, code lost:
        if (r13 >= r7) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00db, code lost:
        r0 = r6.charAt(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e5, code lost:
        if (r0 < '0') goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00eb, code lost:
        if (r0 <= '9') goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f1, code lost:
        if (r0 == '.') goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00f7, code lost:
        if (r0 == 'e') goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00fd, code lost:
        if (r0 == 'E') goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0103, code lost:
        if (r0 == '+') goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0109, code lost:
        if (r0 == '-') goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x010c, code lost:
        return Double.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0110, code lost:
        r13 = r13 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0116, code lost:
        r11 = java.lang.Double.parseDouble(r6.substring(r8, r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0126, code lost:
        return Double.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x012c, code lost:
        if (r10 == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0137, code lost:
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:?, code lost:
        return -r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static double toNumber(java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.JSType.toNumber(java.lang.String):double");
    }

    public static int toInteger(Object obj) {
        return (int) toNumber(obj);
    }

    public static long toLong(Object obj) {
        return obj instanceof Long ? ((Long) obj).longValue() : toLong(toNumber(obj));
    }

    public static long toLong(double num) {
        return (long) num;
    }

    public static int toInt32(Object obj) {
        return toInt32(toNumber(obj));
    }

    public static int toInt32Optimistic(Object obj, int programPoint) {
        if (obj != null && obj.getClass() == Integer.class) {
            return ((Integer) obj).intValue();
        }
        throw new UnwarrantedOptimismException(obj, programPoint);
    }

    public static int toInt32MaybeOptimistic(Object obj, int programPoint) {
        return UnwarrantedOptimismException.isValid(programPoint) ? toInt32Optimistic(obj, programPoint) : toInt32(obj);
    }

    public static int toInt32(long num) {
        return (int) ((num < MIN_PRECISE_DOUBLE || num > MAX_PRECISE_DOUBLE) ? (long) (num % INT32_LIMIT) : num);
    }

    public static int toInt32(double num) {
        return (int) doubleToInt32(num);
    }

    public static long toUint32(Object obj) {
        return toUint32(toNumber(obj));
    }

    public static long toUint32(double num) {
        return doubleToInt32(num) & MAX_UINT;
    }

    public static long toUint32(int num) {
        return num & MAX_UINT;
    }

    public static int toUint32Optimistic(int num, int pp) {
        if (num >= 0) {
            return num;
        }
        throw new UnwarrantedOptimismException(Double.valueOf(toUint32Double(num)), pp, Type.NUMBER);
    }

    public static double toUint32Double(int num) {
        return toUint32(num);
    }

    public static int toUint16(Object obj) {
        return toUint16(toNumber(obj));
    }

    public static int toUint16(int num) {
        return num & CharCompanionObject.MAX_VALUE;
    }

    public static int toUint16(long num) {
        return ((int) num) & CharCompanionObject.MAX_VALUE;
    }

    public static int toUint16(double num) {
        return ((int) doubleToInt32(num)) & CharCompanionObject.MAX_VALUE;
    }

    private static long doubleToInt32(double num) {
        int exponent = Math.getExponent(num);
        if (exponent < 31) {
            return (long) num;
        }
        if (exponent >= 84) {
            return 0L;
        }
        double d = num >= 0.0d ? Math.floor(num) : Math.ceil(num);
        return (long) (d % INT32_LIMIT);
    }

    public static boolean isFinite(double num) {
        return !Double.isInfinite(num) && !Double.isNaN(num);
    }

    public static Double toDouble(double num) {
        return Double.valueOf(num);
    }

    public static Double toDouble(long num) {
        return Double.valueOf(num);
    }

    public static Double toDouble(int num) {
        return Double.valueOf(num);
    }

    public static Object toObject(boolean bool) {
        return Boolean.valueOf(bool);
    }

    public static Object toObject(int num) {
        return Integer.valueOf(num);
    }

    public static Object toObject(long num) {
        return Long.valueOf(num);
    }

    public static Object toObject(double num) {
        return Double.valueOf(num);
    }

    public static Object toObject(Object obj) {
        return obj;
    }

    public static Object toScriptObject(Object obj) {
        return toScriptObject(Context.getGlobal(), obj);
    }

    public static Object toScriptObject(Global global, Object obj) {
        if (nullOrUndefined(obj)) {
            throw ECMAErrors.typeError(global, "not.an.object", ScriptRuntime.safeToString(obj));
        }
        if (obj instanceof ScriptObject) {
            return obj;
        }
        return global.wrapAsObject(obj);
    }

    public static Object toJavaArray(Object obj, Class<?> componentType) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getArray().asArrayOfType(componentType);
        }
        if (obj instanceof JSObject) {
            ArrayLikeIterator<?> itr = ArrayLikeIterator.arrayLikeIterator(obj);
            int len = (int) itr.getLength();
            Object[] res = new Object[len];
            int idx = 0;
            while (itr.hasNext()) {
                int i = idx;
                idx++;
                res[i] = itr.next();
            }
            return convertArray(res, componentType);
        } else if (obj == null) {
            return null;
        } else {
            throw new IllegalArgumentException("not a script object");
        }
    }

    public static Object convertArray(Object[] src, Class<?> componentType) {
        if (componentType == Object.class) {
            for (int i = 0; i < src.length; i++) {
                Object e = src[i];
                if (e instanceof ConsString) {
                    src[i] = e.toString();
                }
            }
        }
        int l = src.length;
        Object dst = Array.newInstance(componentType, l);
        MethodHandle converter = Bootstrap.getLinkerServices().getTypeConverter(Object.class, componentType);
        for (int i2 = 0; i2 < src.length; i2++) {
            try {
                Array.set(dst, i2, invoke(converter, src[i2]));
            } catch (Error | RuntimeException e2) {
                throw e2;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return dst;
    }

    public static boolean nullOrUndefined(Object obj) {
        return obj == null || obj == ScriptRuntime.UNDEFINED;
    }

    public static String toStringImpl(Object obj, boolean safe) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ConsString) {
            return obj.toString();
        }
        if (isNumber(obj)) {
            return toString(((Number) obj).doubleValue());
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            return "undefined";
        }
        if (obj == null) {
            return Configurator.NULL;
        }
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        if (safe && (obj instanceof ScriptObject)) {
            ScriptObject sobj = (ScriptObject) obj;
            Global gobj = Context.getGlobal();
            if (gobj.isError(sobj)) {
                return ECMAException.safeToString(sobj);
            }
            return sobj.safeToString();
        }
        return toString(toPrimitive(obj, String.class));
    }

    public static String trimLeft(String str) {
        int start = 0;
        while (start < str.length() && Lexer.isJSWhitespace(str.charAt(start))) {
            start++;
        }
        return str.substring(start);
    }

    private static Object throwUnwarrantedOptimismException(Object value, int programPoint) {
        throw new UnwarrantedOptimismException(value, programPoint);
    }

    public static int addExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
        try {
            return Math.addExact(x, y);
        } catch (ArithmeticException e) {
            throw new UnwarrantedOptimismException(Double.valueOf(x + y), programPoint);
        }
    }

    public static int subExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
        try {
            return Math.subtractExact(x, y);
        } catch (ArithmeticException e) {
            throw new UnwarrantedOptimismException(Double.valueOf(x - y), programPoint);
        }
    }

    public static int mulExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
        try {
            return Math.multiplyExact(x, y);
        } catch (ArithmeticException e) {
            throw new UnwarrantedOptimismException(Double.valueOf(x * y), programPoint);
        }
    }

    public static int divExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
        try {
            int res = x / y;
            int rem = x % y;
            if (rem == 0) {
                return res;
            }
            throw new UnwarrantedOptimismException(Double.valueOf(x / y), programPoint);
        } catch (ArithmeticException e) {
            if (!$assertionsDisabled && y != 0) {
                throw new AssertionError();
            }
            throw new UnwarrantedOptimismException(Double.valueOf(x > 0 ? Double.POSITIVE_INFINITY : x < 0 ? Double.NEGATIVE_INFINITY : Double.NaN), programPoint);
        }
    }

    public static int divZero(int x, int y) {
        if (y == 0) {
            return 0;
        }
        return x / y;
    }

    public static int remZero(int x, int y) {
        if (y == 0) {
            return 0;
        }
        return x % y;
    }

    public static int remExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
        try {
            return x % y;
        } catch (ArithmeticException e) {
            if (!$assertionsDisabled && y != 0) {
                throw new AssertionError();
            }
            throw new UnwarrantedOptimismException(Double.valueOf(Double.NaN), programPoint);
        }
    }

    public static int decrementExact(int x, int programPoint) throws UnwarrantedOptimismException {
        try {
            return Math.decrementExact(x);
        } catch (ArithmeticException e) {
            throw new UnwarrantedOptimismException(Double.valueOf(x - 1.0d), programPoint);
        }
    }

    public static int incrementExact(int x, int programPoint) throws UnwarrantedOptimismException {
        try {
            return Math.incrementExact(x);
        } catch (ArithmeticException e) {
            throw new UnwarrantedOptimismException(Double.valueOf(x + 1.0d), programPoint);
        }
    }

    public static int negateExact(int x, int programPoint) throws UnwarrantedOptimismException {
        try {
            if (x == 0) {
                throw new UnwarrantedOptimismException(Double.valueOf(-0.0d), programPoint);
            }
            return Math.negateExact(x);
        } catch (ArithmeticException e) {
            throw new UnwarrantedOptimismException(Double.valueOf(-x), programPoint);
        }
    }

    public static int getAccessorTypeIndex(Type type) {
        return getAccessorTypeIndex(type.getTypeClass());
    }

    public static int getAccessorTypeIndex(Class<?> type) {
        if (type == null) {
            return -1;
        }
        if (type == Integer.TYPE) {
            return 0;
        }
        if (type == Double.TYPE) {
            return 1;
        }
        if (!type.isPrimitive()) {
            return 2;
        }
        return -1;
    }

    public static Type getAccessorType(int index) {
        return ACCESSOR_TYPES.get(index);
    }

    public static int getNumberOfAccessorTypes() {
        return ACCESSOR_TYPES.size();
    }

    private static double parseRadix(char[] chars, int start, int length, int radix) {
        int pos = 0;
        for (int i = start; i < length; i++) {
            if (digit(chars[i], radix) == -1) {
                return Double.NaN;
            }
            pos++;
        }
        if (pos == 0) {
            return Double.NaN;
        }
        double value = 0.0d;
        for (int i2 = start; i2 < start + pos; i2++) {
            value = (value * radix) + digit(chars[i2], radix);
        }
        return value;
    }

    private static double toNumberGeneric(Object obj) {
        if (obj == null) {
            return 0.0d;
        }
        if (obj instanceof String) {
            return toNumber((String) obj);
        }
        if (obj instanceof ConsString) {
            return toNumber(obj.toString());
        }
        if (obj instanceof Boolean) {
            return toNumber((Boolean) obj);
        }
        if (obj instanceof ScriptObject) {
            return toNumber((ScriptObject) obj);
        }
        if (obj instanceof Undefined) {
            return Double.NaN;
        }
        return toNumber(toPrimitive(obj, Number.class));
    }

    private static Object invoke(MethodHandle mh, Object arg) {
        try {
            return mh.invoke(arg);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static MethodHandle unboxConstant(Object o) {
        if (o != null) {
            if (o.getClass() == Integer.class) {
                return Lookup.f248MH.constant(Integer.TYPE, Integer.valueOf(((Integer) o).intValue()));
            }
            if (o.getClass() == Double.class) {
                return Lookup.f248MH.constant(Double.TYPE, Double.valueOf(((Double) o).doubleValue()));
            }
        }
        return Lookup.f248MH.constant(Object.class, o);
    }

    public static Class<?> unboxedFieldType(Object o) {
        if (o == null) {
            return Object.class;
        }
        if (o.getClass() == Integer.class) {
            return Integer.TYPE;
        }
        if (o.getClass() == Double.class) {
            return Double.TYPE;
        }
        return Object.class;
    }

    private static final List<MethodHandle> toUnmodifiableList(MethodHandle... methodHandles) {
        return Collections.unmodifiableList(Arrays.asList(methodHandles));
    }
}
