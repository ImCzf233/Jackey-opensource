package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.codegen.ApplySpecialization;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeObject;
import jdk.nashorn.internal.p001ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.Lexer;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptRuntime.class */
public final class ScriptRuntime {
    public static final Object[] EMPTY_ARRAY;
    public static final Undefined UNDEFINED;
    public static final Undefined EMPTY;
    public static final CompilerConstants.Call ADD;
    public static final CompilerConstants.Call EQ_STRICT;
    public static final CompilerConstants.Call OPEN_WITH;
    public static final CompilerConstants.Call MERGE_SCOPE;
    public static final CompilerConstants.Call TO_PROPERTY_ITERATOR;
    public static final CompilerConstants.Call TO_VALUE_ITERATOR;
    public static final CompilerConstants.Call APPLY;
    public static final CompilerConstants.Call THROW_REFERENCE_ERROR;
    public static final CompilerConstants.Call THROW_CONST_TYPE_ERROR;
    public static final CompilerConstants.Call INVALIDATE_RESERVED_BUILTIN_NAME;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ScriptRuntime.class.desiredAssertionStatus();
        EMPTY_ARRAY = new Object[0];
        UNDEFINED = Undefined.getUndefined();
        EMPTY = Undefined.getEmpty();
        ADD = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "ADD", Object.class, Object.class, Object.class);
        EQ_STRICT = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "EQ_STRICT", Boolean.TYPE, Object.class, Object.class);
        OPEN_WITH = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "openWith", ScriptObject.class, ScriptObject.class, Object.class);
        MERGE_SCOPE = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "mergeScope", ScriptObject.class, ScriptObject.class);
        TO_PROPERTY_ITERATOR = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "toPropertyIterator", Iterator.class, Object.class);
        TO_VALUE_ITERATOR = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "toValueIterator", Iterator.class, Object.class);
        APPLY = CompilerConstants.staticCall(MethodHandles.lookup(), ScriptRuntime.class, "apply", Object.class, ScriptFunction.class, Object.class, Object[].class);
        THROW_REFERENCE_ERROR = CompilerConstants.staticCall(MethodHandles.lookup(), ScriptRuntime.class, "throwReferenceError", Void.TYPE, String.class);
        THROW_CONST_TYPE_ERROR = CompilerConstants.staticCall(MethodHandles.lookup(), ScriptRuntime.class, "throwConstTypeError", Void.TYPE, String.class);
        INVALIDATE_RESERVED_BUILTIN_NAME = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "invalidateReservedBuiltinName", Void.TYPE, String.class);
    }

    private ScriptRuntime() {
    }

    public static int switchTagAsInt(Object tag, int deflt) {
        if (tag instanceof Number) {
            double d = ((Number) tag).doubleValue();
            if (JSType.isRepresentableAsInt(d)) {
                return (int) d;
            }
        }
        return deflt;
    }

    public static int switchTagAsInt(boolean tag, int deflt) {
        return deflt;
    }

    public static int switchTagAsInt(long tag, int deflt) {
        return JSType.isRepresentableAsInt(tag) ? (int) tag : deflt;
    }

    public static int switchTagAsInt(double tag, int deflt) {
        return JSType.isRepresentableAsInt(tag) ? (int) tag : deflt;
    }

    public static String builtinObjectToString(Object self) {
        String className;
        JSType type = JSType.ofNoFunction(self);
        switch (type) {
            case BOOLEAN:
                className = "Boolean";
                break;
            case NUMBER:
                className = "Number";
                break;
            case STRING:
                className = "String";
                break;
            case NULL:
                className = "Null";
                break;
            case UNDEFINED:
                className = "Undefined";
                break;
            case OBJECT:
                if (self instanceof ScriptObject) {
                    className = ((ScriptObject) self).getClassName();
                    break;
                } else if (self instanceof JSObject) {
                    className = ((JSObject) self).getClassName();
                    break;
                } else {
                    className = self.getClass().getName();
                    break;
                }
            default:
                className = self.getClass().getName();
                break;
        }
        return "[object " + className + ']';
    }

    public static String safeToString(Object obj) {
        return JSType.toStringImpl(obj, true);
    }

    public static Iterator<?> toPropertyIterator(Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).propertyIterator();
        }
        if (obj != null && obj.getClass().isArray()) {
            return new RangeIterator(Array.getLength(obj));
        }
        if (obj instanceof JSObject) {
            return ((JSObject) obj).keySet().iterator();
        }
        if (obj instanceof List) {
            return new RangeIterator(((List) obj).size());
        }
        if (obj instanceof Map) {
            return ((Map) obj).keySet().iterator();
        }
        Object wrapped = Global.instance().wrapAsObject(obj);
        if (wrapped instanceof ScriptObject) {
            return ((ScriptObject) wrapped).propertyIterator();
        }
        return Collections.emptyIterator();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptRuntime$RangeIterator.class */
    private static final class RangeIterator implements Iterator<Integer> {
        private final int length;
        private int index;

        RangeIterator(int length) {
            this.length = length;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.length;
        }

        @Override // java.util.Iterator
        public Integer next() {
            int i = this.index;
            this.index = i + 1;
            return Integer.valueOf(i);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static Iterator<?> toValueIterator(final Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).valueIterator();
        }
        if (obj != null && obj.getClass().isArray()) {
            final int length = Array.getLength(obj);
            return new Iterator<Object>() { // from class: jdk.nashorn.internal.runtime.ScriptRuntime.1
                private int index = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < length;
                }

                @Override // java.util.Iterator
                public Object next() {
                    if (this.index >= length) {
                        throw new NoSuchElementException();
                    }
                    Object obj2 = obj;
                    int i = this.index;
                    this.index = i + 1;
                    return Array.get(obj2, i);
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException("remove");
                }
            };
        } else if (obj instanceof JSObject) {
            return ((JSObject) obj).values().iterator();
        } else {
            if (obj instanceof Map) {
                return ((Map) obj).values().iterator();
            }
            if (obj instanceof Iterable) {
                return ((Iterable) obj).iterator();
            }
            Object wrapped = Global.instance().wrapAsObject(obj);
            if (wrapped instanceof ScriptObject) {
                return ((ScriptObject) wrapped).valueIterator();
            }
            return Collections.emptyIterator();
        }
    }

    public static ScriptObject mergeScope(ScriptObject scope) {
        ScriptObject parentScope = scope.getProto();
        parentScope.addBoundProperties(scope);
        return parentScope;
    }

    public static Object apply(ScriptFunction target, Object self, Object... args) {
        try {
            return target.invoke(self, args);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static void throwReferenceError(String name) {
        throw ECMAErrors.referenceError("not.defined", name);
    }

    public static void throwConstTypeError(String name) {
        throw ECMAErrors.typeError("assign.constant", name);
    }

    public static Object construct(ScriptFunction target, Object... args) {
        try {
            return target.construct(args);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static boolean sameValue(Object x, Object y) {
        JSType xType = JSType.ofNoFunction(x);
        JSType yType = JSType.ofNoFunction(y);
        if (xType != yType) {
            return false;
        }
        if (xType == JSType.UNDEFINED || xType == JSType.NULL) {
            return true;
        }
        if (xType == JSType.NUMBER) {
            double xVal = ((Number) x).doubleValue();
            double yVal = ((Number) y).doubleValue();
            if (Double.isNaN(xVal) && Double.isNaN(yVal)) {
                return true;
            }
            return (xVal != 0.0d || Double.doubleToLongBits(xVal) == Double.doubleToLongBits(yVal)) && xVal == yVal;
        } else if (xType == JSType.STRING || yType == JSType.BOOLEAN) {
            return x.equals(y);
        } else {
            return x == y;
        }
    }

    public static String parse(String code, String name, boolean includeLoc) {
        return JSONWriter.parse(Context.getContextTrusted(), code, name, includeLoc);
    }

    public static boolean isJSWhitespace(char ch) {
        return Lexer.isJSWhitespace(ch);
    }

    public static ScriptObject openWith(ScriptObject scope, Object expression) {
        Global global = Context.getGlobal();
        if (expression == UNDEFINED) {
            throw ECMAErrors.typeError(global, "cant.apply.with.to.undefined", new String[0]);
        }
        if (expression == null) {
            throw ECMAErrors.typeError(global, "cant.apply.with.to.null", new String[0]);
        }
        if (expression instanceof ScriptObjectMirror) {
            Object unwrapped = ScriptObjectMirror.unwrap(expression, global);
            if (unwrapped instanceof ScriptObject) {
                return new WithObject(scope, (ScriptObject) unwrapped);
            }
            ScriptObject exprObj = global.newObject();
            NativeObject.bindAllProperties(exprObj, (ScriptObjectMirror) expression);
            return new WithObject(scope, exprObj);
        }
        Object wrappedExpr = JSType.toScriptObject(global, expression);
        if (wrappedExpr instanceof ScriptObject) {
            return new WithObject(scope, (ScriptObject) wrappedExpr);
        }
        throw ECMAErrors.typeError(global, "cant.apply.with.to.non.scriptobject", new String[0]);
    }

    public static Object ADD(Object x, Object y) {
        boolean xIsNumber = x instanceof Number;
        boolean yIsNumber = y instanceof Number;
        if (xIsNumber && yIsNumber) {
            return Double.valueOf(((Number) x).doubleValue() + ((Number) y).doubleValue());
        }
        boolean xIsUndefined = x == UNDEFINED;
        boolean yIsUndefined = y == UNDEFINED;
        if ((xIsNumber && yIsUndefined) || ((xIsUndefined && yIsNumber) || (xIsUndefined && yIsUndefined))) {
            return Double.valueOf(Double.NaN);
        }
        Object xPrim = JSType.toPrimitive(x);
        Object yPrim = JSType.toPrimitive(y);
        if (JSType.isString(xPrim) || JSType.isString(yPrim)) {
            try {
                return new ConsString(JSType.toCharSequence(xPrim), JSType.toCharSequence(yPrim));
            } catch (IllegalArgumentException iae) {
                throw ECMAErrors.rangeError(iae, "concat.string.too.big", new String[0]);
            }
        }
        return Double.valueOf(JSType.toNumber(xPrim) + JSType.toNumber(yPrim));
    }

    public static Object DEBUGGER() {
        return UNDEFINED;
    }

    public static Object NEW(Object clazz, Object... args) {
        return UNDEFINED;
    }

    public static Object TYPEOF(Object object, Object property) {
        Object obj = object;
        if (property != null) {
            if (obj instanceof ScriptObject) {
                obj = ((ScriptObject) obj).get(property);
                if (Global.isLocationPropertyPlaceholder(obj)) {
                    if (CompilerConstants.__LINE__.name().equals(property)) {
                        obj = 0;
                    } else {
                        obj = "";
                    }
                }
            } else if (object instanceof Undefined) {
                obj = ((Undefined) obj).get(property);
            } else if (object == null) {
                throw ECMAErrors.typeError("cant.get.property", safeToString(property), Configurator.NULL);
            } else {
                if (JSType.isPrimitive(obj)) {
                    obj = ((ScriptObject) JSType.toScriptObject(obj)).get(property);
                } else if (obj instanceof JSObject) {
                    obj = ((JSObject) obj).getMember(property.toString());
                } else {
                    obj = UNDEFINED;
                }
            }
        }
        return JSType.m66of(obj).typeName();
    }

    public static Object REFERENCE_ERROR(Object lhs, Object rhs, Object msg) {
        throw ECMAErrors.referenceError("cant.be.used.as.lhs", Objects.toString(msg));
    }

    public static boolean DELETE(Object obj, Object property, Object strict) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).delete(property, Boolean.TRUE.equals(strict));
        }
        if (obj instanceof Undefined) {
            return ((Undefined) obj).delete(property, false);
        }
        if (obj == null) {
            throw ECMAErrors.typeError("cant.delete.property", safeToString(property), Configurator.NULL);
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).delete(property);
        }
        if (JSType.isPrimitive(obj)) {
            return ((ScriptObject) JSType.toScriptObject(obj)).delete(property, Boolean.TRUE.equals(strict));
        }
        if (obj instanceof JSObject) {
            ((JSObject) obj).removeMember(Objects.toString(property));
            return true;
        }
        return true;
    }

    public static boolean SLOW_DELETE(Object obj, Object property, Object strict) {
        if (obj instanceof ScriptObject) {
            String key = property.toString();
            for (ScriptObject sobj = (ScriptObject) obj; sobj != null && sobj.isScope(); sobj = sobj.getProto()) {
                FindProperty find = sobj.findProperty(key, false);
                if (find != null) {
                    return sobj.delete(key, Boolean.TRUE.equals(strict));
                }
            }
        }
        return DELETE(obj, property, strict);
    }

    public static boolean FAIL_DELETE(Object property, Object strict) {
        if (Boolean.TRUE.equals(strict)) {
            throw ECMAErrors.syntaxError("strict.cant.delete", safeToString(property));
        }
        return false;
    }

    /* renamed from: EQ */
    public static boolean m65EQ(Object x, Object y) {
        return equals(x, y);
    }

    /* renamed from: NE */
    public static boolean m59NE(Object x, Object y) {
        return !m65EQ(x, y);
    }

    private static boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if ((x instanceof ScriptObject) && (y instanceof ScriptObject)) {
            return false;
        }
        if ((x instanceof ScriptObjectMirror) || (y instanceof ScriptObjectMirror)) {
            return ScriptObjectMirror.identical(x, y);
        }
        return equalValues(x, y);
    }

    private static boolean equalValues(Object x, Object y) {
        JSType xType = JSType.ofNoFunction(x);
        JSType yType = JSType.ofNoFunction(y);
        if (xType == yType) {
            return equalSameTypeValues(x, y, xType);
        }
        return equalDifferentTypeValues(x, y, xType, yType);
    }

    private static boolean equalSameTypeValues(Object x, Object y, JSType type) {
        if (type == JSType.UNDEFINED || type == JSType.NULL) {
            return true;
        }
        if (type == JSType.NUMBER) {
            return ((Number) x).doubleValue() == ((Number) y).doubleValue();
        } else if (type == JSType.STRING) {
            return x.toString().equals(y.toString());
        } else {
            return type == JSType.BOOLEAN ? ((Boolean) x).booleanValue() == ((Boolean) y).booleanValue() : x == y;
        }
    }

    private static boolean equalDifferentTypeValues(Object x, Object y, JSType xType, JSType yType) {
        if (isUndefinedAndNull(xType, yType) || isUndefinedAndNull(yType, xType)) {
            return true;
        }
        if (isNumberAndString(xType, yType)) {
            return equalNumberToString(x, y);
        }
        if (isNumberAndString(yType, xType)) {
            return equalNumberToString(y, x);
        }
        if (xType == JSType.BOOLEAN) {
            return equalBooleanToAny(x, y);
        }
        if (yType == JSType.BOOLEAN) {
            return equalBooleanToAny(y, x);
        }
        if (isNumberOrStringAndObject(xType, yType)) {
            return equalNumberOrStringToObject(x, y);
        }
        if (isNumberOrStringAndObject(yType, xType)) {
            return equalNumberOrStringToObject(y, x);
        }
        return false;
    }

    private static boolean isUndefinedAndNull(JSType xType, JSType yType) {
        return xType == JSType.UNDEFINED && yType == JSType.NULL;
    }

    private static boolean isNumberAndString(JSType xType, JSType yType) {
        return xType == JSType.NUMBER && yType == JSType.STRING;
    }

    private static boolean isNumberOrStringAndObject(JSType xType, JSType yType) {
        return (xType == JSType.NUMBER || xType == JSType.STRING) && yType == JSType.OBJECT;
    }

    private static boolean equalNumberToString(Object num, Object str) {
        return ((Number) num).doubleValue() == JSType.toNumber(str.toString());
    }

    private static boolean equalBooleanToAny(Object bool, Object any) {
        return equals(Double.valueOf(JSType.toNumber((Boolean) bool)), any);
    }

    private static boolean equalNumberOrStringToObject(Object numOrStr, Object any) {
        return equals(numOrStr, JSType.toPrimitive(any));
    }

    public static boolean EQ_STRICT(Object x, Object y) {
        return strictEquals(x, y);
    }

    public static boolean NE_STRICT(Object x, Object y) {
        return !EQ_STRICT(x, y);
    }

    private static boolean strictEquals(Object x, Object y) {
        JSType xType = JSType.ofNoFunction(x);
        JSType yType = JSType.ofNoFunction(y);
        if (xType != yType) {
            return false;
        }
        return equalSameTypeValues(x, y, xType);
    }

    /* renamed from: IN */
    public static boolean m62IN(Object property, Object obj) {
        JSType rvalType = JSType.ofNoFunction(obj);
        if (rvalType == JSType.OBJECT) {
            if (obj instanceof ScriptObject) {
                return ((ScriptObject) obj).has(property);
            }
            if (obj instanceof JSObject) {
                return ((JSObject) obj).hasMember(Objects.toString(property));
            }
            return false;
        }
        throw ECMAErrors.typeError("in.with.non.object", rvalType.toString().toLowerCase(Locale.ENGLISH));
    }

    public static boolean INSTANCEOF(Object obj, Object clazz) {
        if (clazz instanceof ScriptFunction) {
            if (obj instanceof ScriptObject) {
                return ((ScriptObject) clazz).isInstance((ScriptObject) obj);
            }
            return false;
        } else if (clazz instanceof StaticClass) {
            return ((StaticClass) clazz).getRepresentedClass().isInstance(obj);
        } else {
            if (clazz instanceof JSObject) {
                return ((JSObject) clazz).isInstance(obj);
            }
            if (obj instanceof JSObject) {
                return ((JSObject) obj).isInstanceOf(clazz);
            }
            throw ECMAErrors.typeError("instanceof.on.non.object", new String[0]);
        }
    }

    /* renamed from: LT */
    public static boolean m60LT(Object x, Object y) {
        Object px = JSType.toPrimitive(x, Number.class);
        Object py = JSType.toPrimitive(y, Number.class);
        return areBothString(px, py) ? px.toString().compareTo(py.toString()) < 0 : JSType.toNumber(px) < JSType.toNumber(py);
    }

    private static boolean areBothString(Object x, Object y) {
        return JSType.isString(x) && JSType.isString(y);
    }

    /* renamed from: GT */
    public static boolean m63GT(Object x, Object y) {
        Object px = JSType.toPrimitive(x, Number.class);
        Object py = JSType.toPrimitive(y, Number.class);
        return areBothString(px, py) ? px.toString().compareTo(py.toString()) > 0 : JSType.toNumber(px) > JSType.toNumber(py);
    }

    /* renamed from: LE */
    public static boolean m61LE(Object x, Object y) {
        Object px = JSType.toPrimitive(x, Number.class);
        Object py = JSType.toPrimitive(y, Number.class);
        return areBothString(px, py) ? px.toString().compareTo(py.toString()) <= 0 : JSType.toNumber(px) <= JSType.toNumber(py);
    }

    /* renamed from: GE */
    public static boolean m64GE(Object x, Object y) {
        Object px = JSType.toPrimitive(x, Number.class);
        Object py = JSType.toPrimitive(y, Number.class);
        return areBothString(px, py) ? px.toString().compareTo(py.toString()) >= 0 : JSType.toNumber(px) >= JSType.toNumber(py);
    }

    public static void invalidateReservedBuiltinName(String name) {
        Context context = Context.getContextTrusted();
        SwitchPoint sp = context.getBuiltinSwitchPoint(name);
        if ($assertionsDisabled || sp != null) {
            context.getLogger(ApplySpecialization.class).info("Overwrote special name '" + name + "' - invalidating switchpoint");
            SwitchPoint.invalidateAll(new SwitchPoint[]{sp});
            return;
        }
        throw new AssertionError();
    }
}
