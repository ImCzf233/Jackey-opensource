package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.scripts.C1654JO;
import org.apache.log4j.spi.Configurator;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJSAdapter.class */
public final class NativeJSAdapter extends ScriptObject {
    public static final String __get__ = "__get__";
    public static final String __put__ = "__put__";
    public static final String __call__ = "__call__";
    public static final String __new__ = "__new__";
    public static final String __getIds__ = "__getIds__";
    public static final String __getKeys__ = "__getKeys__";
    public static final String __getValues__ = "__getValues__";
    public static final String __has__ = "__has__";
    public static final String __delete__ = "__delete__";
    public static final String __preventExtensions__ = "__preventExtensions__";
    public static final String __isExtensible__ = "__isExtensible__";
    public static final String __seal__ = "__seal__";
    public static final String __isSealed__ = "__isSealed__";
    public static final String __freeze__ = "__freeze__";
    public static final String __isFrozen__ = "__isFrozen__";
    private final ScriptObject adaptee;
    private final boolean overrides;
    private static final MethodHandle IS_JSADAPTOR = null;
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJSAdapter$Constructor.class */
    final class Constructor extends ScriptFunction {
        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeJSAdapter.Constructor.<init>():void
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:49)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:110)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        Constructor() {
            /*
                r5 = this;
                r0 = r5
                java.lang.String r1 = "JSAdapter"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeJSAdapter$Prototype r-1 = new jdk.nashorn.internal.objects.NativeJSAdapter$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJSAdapter.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJSAdapter$Prototype.class */
    final class Prototype extends PrototypeObject {
        Prototype() {
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "JSAdapter";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        IS_JSADAPTOR = findOwnMH("isJSAdaptor", Boolean.TYPE, Object.class, Object.class, MethodHandle.class, Object.class, ScriptFunction.class);
        $clinit$();
    }

    NativeJSAdapter(Object overrides, ScriptObject adaptee, ScriptObject proto, PropertyMap map) {
        super(proto, map);
        this.adaptee = wrapAdaptee(adaptee);
        if (overrides instanceof ScriptObject) {
            this.overrides = true;
            ScriptObject sobj = (ScriptObject) overrides;
            addBoundProperties(sobj);
            return;
        }
        this.overrides = false;
    }

    private static ScriptObject wrapAdaptee(ScriptObject adaptee) {
        return new C1654JO(adaptee);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "JSAdapter";
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object key, int programPoint) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdapteeInt(programPoint, __get__, key) : super.getInt(key, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double key, int programPoint) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdapteeInt(programPoint, __get__, Double.valueOf(key)) : super.getInt(key, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int key, int programPoint) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdapteeInt(programPoint, __get__, Integer.valueOf(key)) : super.getInt(key, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object key, int programPoint) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdapteeDouble(programPoint, __get__, key) : super.getDouble(key, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double key, int programPoint) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdapteeDouble(programPoint, __get__, Double.valueOf(key)) : super.getDouble(key, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int key, int programPoint) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdapteeDouble(programPoint, __get__, Integer.valueOf(key)) : super.getDouble(key, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(Object key) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdaptee(__get__, key) : super.get(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double key) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdaptee(__get__, Double.valueOf(key)) : super.get(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int key) {
        return (!this.overrides || !super.hasOwnProperty(key)) ? callAdaptee(__get__, Integer.valueOf(key)) : super.get(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, int value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, key, Integer.valueOf(value), Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, double value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, key, Double.valueOf(value), Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, Object value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, key, value, Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, int value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, Double.valueOf(key), Integer.valueOf(value), Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, double value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, Double.valueOf(key), Double.valueOf(value), Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, Object value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, Double.valueOf(key), value, Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, int value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, Integer.valueOf(key), Integer.valueOf(value), Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, double value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, Integer.valueOf(key), Double.valueOf(value), Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, Object value, int flags) {
        if (this.overrides && super.hasOwnProperty(key)) {
            super.set(key, value, flags);
        } else {
            callAdaptee(__put__, Integer.valueOf(key), value, Integer.valueOf(flags));
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(Object key) {
        if (this.overrides && super.hasOwnProperty(key)) {
            return true;
        }
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __has__, key));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int key) {
        if (this.overrides && super.hasOwnProperty(key)) {
            return true;
        }
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __has__, Integer.valueOf(key)));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double key) {
        if (this.overrides && super.hasOwnProperty(key)) {
            return true;
        }
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __has__, Double.valueOf(key)));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int key, boolean strict) {
        if (this.overrides && super.hasOwnProperty(key)) {
            return super.delete(key, strict);
        }
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __delete__, Integer.valueOf(key), Boolean.valueOf(strict)));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double key, boolean strict) {
        if (this.overrides && super.hasOwnProperty(key)) {
            return super.delete(key, strict);
        }
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __delete__, Double.valueOf(key), Boolean.valueOf(strict)));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object key, boolean strict) {
        if (this.overrides && super.hasOwnProperty(key)) {
            return super.delete(key, strict);
        }
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __delete__, key, Boolean.valueOf(strict)));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Iterator<String> propertyIterator() {
        Object obj;
        Object func = this.adaptee.get(__getIds__);
        if (!(func instanceof ScriptFunction)) {
            func = this.adaptee.get(__getKeys__);
        }
        if (func instanceof ScriptFunction) {
            obj = ScriptRuntime.apply((ScriptFunction) func, this.adaptee, new Object[0]);
        } else {
            obj = new NativeArray(0L);
        }
        List<String> array = new ArrayList<>();
        Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(obj);
        while (iter.hasNext()) {
            array.add((String) iter.next());
        }
        return array.iterator();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Iterator<Object> valueIterator() {
        Object obj = callAdaptee(new NativeArray(0L), __getValues__, new Object[0]);
        return ArrayLikeIterator.arrayLikeIterator(obj);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public ScriptObject preventExtensions() {
        callAdaptee(__preventExtensions__, new Object[0]);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isExtensible() {
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __isExtensible__, new Object[0]));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public ScriptObject seal() {
        callAdaptee(__seal__, new Object[0]);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isSealed() {
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __isSealed__, new Object[0]));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public ScriptObject freeze() {
        callAdaptee(__freeze__, new Object[0]);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isFrozen() {
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __isFrozen__, new Object[0]));
    }

    public static NativeJSAdapter construct(boolean isNew, Object self, Object... args) {
        Object adaptee;
        Object proto = ScriptRuntime.UNDEFINED;
        Object overrides = ScriptRuntime.UNDEFINED;
        if (args == null || args.length == 0) {
            throw ECMAErrors.typeError("not.an.object", Configurator.NULL);
        }
        switch (args.length) {
            case 1:
                adaptee = args[0];
                break;
            case 2:
                overrides = args[0];
                adaptee = args[1];
                break;
            case 3:
            default:
                proto = args[0];
                overrides = args[1];
                adaptee = args[2];
                break;
        }
        if (!(adaptee instanceof ScriptObject)) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(adaptee));
        }
        Global global = Global.instance();
        if (proto != null && !(proto instanceof ScriptObject)) {
            proto = global.getJSAdapterPrototype();
        }
        return new NativeJSAdapter(overrides, (ScriptObject) adaptee, (ScriptObject) proto, $nasgenmap$);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
        return findHook(desc, __new__, false);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findCallMethodMethod(CallSiteDescriptor desc, LinkRequest request) {
        if (this.overrides && super.hasOwnProperty(desc.getNameToken(2))) {
            try {
                GuardedInvocation inv = super.findCallMethodMethod(desc, request);
                if (inv != null) {
                    return inv;
                }
            } catch (Exception e) {
            }
        }
        return findHook(desc, __call__);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operation) {
        String name = desc.getNameToken(2);
        if (this.overrides && super.hasOwnProperty(name)) {
            try {
                GuardedInvocation inv = super.findGetMethod(desc, request, operation);
                if (inv != null) {
                    return inv;
                }
            } catch (Exception e) {
            }
        }
        boolean z = true;
        switch (operation.hashCode()) {
            case -75566075:
                if (operation.equals("getElem")) {
                    z = true;
                    break;
                }
                break;
            case -75232295:
                if (operation.equals("getProp")) {
                    z = false;
                    break;
                }
                break;
            case 618460119:
                if (operation.equals("getMethod")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
                return findHook(desc, __get__);
            case true:
                FindProperty find = this.adaptee.findProperty(__call__, true);
                if (find != null) {
                    Object value = find.getObjectValue();
                    if (value instanceof ScriptFunction) {
                        ScriptFunction func = (ScriptFunction) value;
                        return new GuardedInvocation(Lookup.f248MH.dropArguments(Lookup.f248MH.constant(Object.class, func.createBound(this, new Object[]{name})), 0, Object.class), testJSAdaptor(this.adaptee, null, null, null), this.adaptee.getProtoSwitchPoints(__call__, find.getOwner()), (Class<? extends Throwable>) null);
                    }
                }
                throw ECMAErrors.typeError("no.such.function", desc.getNameToken(2), ScriptRuntime.safeToString(this));
            default:
                throw new AssertionError("should not reach here");
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
        if (this.overrides && super.hasOwnProperty(desc.getNameToken(2))) {
            try {
                GuardedInvocation inv = super.findSetMethod(desc, request);
                if (inv != null) {
                    return inv;
                }
            } catch (Exception e) {
            }
        }
        return findHook(desc, __put__);
    }

    private Object callAdaptee(String name, Object... args) {
        return callAdaptee(ScriptRuntime.UNDEFINED, name, args);
    }

    private double callAdapteeDouble(int programPoint, String name, Object... args) {
        return JSType.toNumberMaybeOptimistic(callAdaptee(name, args), programPoint);
    }

    private int callAdapteeInt(int programPoint, String name, Object... args) {
        return JSType.toInt32MaybeOptimistic(callAdaptee(name, args), programPoint);
    }

    private Object callAdaptee(Object retValue, String name, Object... args) {
        Object func = this.adaptee.get(name);
        if (func instanceof ScriptFunction) {
            return ScriptRuntime.apply((ScriptFunction) func, this.adaptee, args);
        }
        return retValue;
    }

    private GuardedInvocation findHook(CallSiteDescriptor desc, String hook) {
        return findHook(desc, hook, true);
    }

    private GuardedInvocation findHook(CallSiteDescriptor desc, String hook, boolean useName) {
        MethodHandle methodHandle;
        FindProperty findData = this.adaptee.findProperty(hook, true);
        MethodType type = desc.getMethodType();
        if (findData != null) {
            String name = desc.getNameTokenCount() > 2 ? desc.getNameToken(2) : null;
            Object value = findData.getObjectValue();
            if (value instanceof ScriptFunction) {
                ScriptFunction func = (ScriptFunction) value;
                MethodHandle methodHandle2 = getCallMethodHandle(findData, type, useName ? name : null);
                if (methodHandle2 != null) {
                    return new GuardedInvocation(methodHandle2, testJSAdaptor(this.adaptee, findData.getGetter(Object.class, -1, null), findData.getOwner(), func), this.adaptee.getProtoSwitchPoints(hook, findData.getOwner()), (Class<? extends Throwable>) null);
                }
            }
        }
        boolean z = true;
        switch (hook.hashCode()) {
            case -596047202:
                if (hook.equals(__call__)) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                throw ECMAErrors.typeError("no.such.function", desc.getNameToken(2), ScriptRuntime.safeToString(this));
            default:
                if (hook.equals(__put__)) {
                    methodHandle = Lookup.f248MH.asType(Lookup.EMPTY_SETTER, type);
                } else {
                    methodHandle = Lookup.emptyGetter(type.returnType());
                }
                return new GuardedInvocation(methodHandle, testJSAdaptor(this.adaptee, null, null, null), this.adaptee.getProtoSwitchPoints(hook, null), (Class<? extends Throwable>) null);
        }
    }

    private static MethodHandle testJSAdaptor(Object adaptee, MethodHandle getter, Object where, ScriptFunction func) {
        return Lookup.f248MH.insertArguments(IS_JSADAPTOR, 1, adaptee, getter, where, func);
    }

    private static boolean isJSAdaptor(Object self, Object adaptee, MethodHandle getter, Object where, ScriptFunction func) {
        boolean res = (self instanceof NativeJSAdapter) && ((NativeJSAdapter) self).getAdaptee() == adaptee;
        if (res && getter != null) {
            try {
                return getter.invokeExact(where) == func;
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return res;
    }

    public ScriptObject getAdaptee() {
        return this.adaptee;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NativeJSAdapter.class, name, Lookup.f248MH.type(rtype, types));
    }
}
