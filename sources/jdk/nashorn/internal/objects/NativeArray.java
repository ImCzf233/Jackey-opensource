package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticBuiltins;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;
import jdk.nashorn.internal.runtime.arrays.IntElements;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import jdk.nashorn.internal.runtime.arrays.NumericElements;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray.class */
public final class NativeArray extends ScriptObject implements OptimisticBuiltins {
    private static final Object JOIN = null;
    private static final Object EVERY_CALLBACK_INVOKER = null;
    private static final Object SOME_CALLBACK_INVOKER = null;
    private static final Object FOREACH_CALLBACK_INVOKER = null;
    private static final Object MAP_CALLBACK_INVOKER = null;
    private static final Object FILTER_CALLBACK_INVOKER = null;
    private static final Object REDUCE_CALLBACK_INVOKER = null;
    private static final Object CALL_CMP = null;
    private static final Object TO_LOCALE_STRING = null;
    private static PropertyMap $nasgenmap$;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object isArray;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$isArray() {
            return this.isArray;
        }

        public void S$isArray(Object obj) {
            this.isArray = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: JavaClassParseException: Unknown opcode: 0x5e in method: jdk.nashorn.internal.objects.NativeArray.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            Caused by: jadx.plugins.input.java.utils.JavaClassParseException: Unknown opcode: 0x5e
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:71)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
            	... 6 more
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: JavaClassParseException: Unknown opcode: 0x5e in method: jdk.nashorn.internal.objects.NativeArray.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArray.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object assertNumeric;
        private Object toLocaleString;
        private Object concat;
        private Object join;
        private Object pop;
        private Object push;
        private Object reverse;
        private Object shift;
        private Object slice;
        private Object sort;
        private Object splice;
        private Object unshift;
        private Object indexOf;
        private Object lastIndexOf;
        private Object every;
        private Object some;
        private Object forEach;
        private Object map;
        private Object filter;
        private Object reduce;
        private Object reduceRight;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$assertNumeric() {
            return this.assertNumeric;
        }

        public void S$assertNumeric(Object obj) {
            this.assertNumeric = obj;
        }

        public Object G$toLocaleString() {
            return this.toLocaleString;
        }

        public void S$toLocaleString(Object obj) {
            this.toLocaleString = obj;
        }

        public Object G$concat() {
            return this.concat;
        }

        public void S$concat(Object obj) {
            this.concat = obj;
        }

        public Object G$join() {
            return this.join;
        }

        public void S$join(Object obj) {
            this.join = obj;
        }

        public Object G$pop() {
            return this.pop;
        }

        public void S$pop(Object obj) {
            this.pop = obj;
        }

        public Object G$push() {
            return this.push;
        }

        public void S$push(Object obj) {
            this.push = obj;
        }

        public Object G$reverse() {
            return this.reverse;
        }

        public void S$reverse(Object obj) {
            this.reverse = obj;
        }

        public Object G$shift() {
            return this.shift;
        }

        public void S$shift(Object obj) {
            this.shift = obj;
        }

        public Object G$slice() {
            return this.slice;
        }

        public void S$slice(Object obj) {
            this.slice = obj;
        }

        public Object G$sort() {
            return this.sort;
        }

        public void S$sort(Object obj) {
            this.sort = obj;
        }

        public Object G$splice() {
            return this.splice;
        }

        public void S$splice(Object obj) {
            this.splice = obj;
        }

        public Object G$unshift() {
            return this.unshift;
        }

        public void S$unshift(Object obj) {
            this.unshift = obj;
        }

        public Object G$indexOf() {
            return this.indexOf;
        }

        public void S$indexOf(Object obj) {
            this.indexOf = obj;
        }

        public Object G$lastIndexOf() {
            return this.lastIndexOf;
        }

        public void S$lastIndexOf(Object obj) {
            this.lastIndexOf = obj;
        }

        public Object G$every() {
            return this.every;
        }

        public void S$every(Object obj) {
            this.every = obj;
        }

        public Object G$some() {
            return this.some;
        }

        public void S$some(Object obj) {
            this.some = obj;
        }

        public Object G$forEach() {
            return this.forEach;
        }

        public void S$forEach(Object obj) {
            this.forEach = obj;
        }

        public Object G$map() {
            return this.map;
        }

        public void S$map(Object obj) {
            this.map = obj;
        }

        public Object G$filter() {
            return this.filter;
        }

        public void S$filter(Object obj) {
            this.filter = obj;
        }

        public Object G$reduce() {
            return this.reduce;
        }

        public void S$reduce(Object obj) {
            this.reduce = obj;
        }

        public Object G$reduceRight() {
            return this.reduceRight;
        }

        public void S$reduceRight(Object obj) {
            this.reduceRight = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeArray.Prototype.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$Prototype.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Prototype() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeArray.Prototype.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArray.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Array";
        }
    }

    /*  JADX ERROR: Failed to decode insn: 0x000E: CONST, method: jdk.nashorn.internal.objects.NativeArray.$clinit$():void
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:49)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
        	at jadx.core.ProcessClass.process(ProcessClass.java:67)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
        */
    public static void $clinit$() {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = 1
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "length"
            r3 = 6
            // decode failed: Unsupported constant type: METHOD_HANDLE
            short r2 = r2[r3]
            jdk.nashorn.internal.runtime.AccessorProperty.create(r-1, r0, r1, r2)
            boolean r-2 = r-2.add(r-1)
            jdk.nashorn.internal.runtime.PropertyMap r-3 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r-3)
            jdk.nashorn.internal.objects.NativeArray.$nasgenmap$ = r-3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArray.$clinit$():void");
    }

    static {
        $assertionsDisabled = !NativeArray.class.desiredAssertionStatus();
        JOIN = new Object();
        EVERY_CALLBACK_INVOKER = new Object();
        SOME_CALLBACK_INVOKER = new Object();
        FOREACH_CALLBACK_INVOKER = new Object();
        MAP_CALLBACK_INVOKER = new Object();
        FILTER_CALLBACK_INVOKER = new Object();
        REDUCE_CALLBACK_INVOKER = new Object();
        CALL_CMP = new Object();
        TO_LOCALE_STRING = new Object();
        $clinit$();
    }

    public NativeArray() {
        this(ArrayData.initialArray());
    }

    public NativeArray(long length) {
        this(ArrayData.allocate(length));
    }

    public NativeArray(int[] array) {
        this(ArrayData.allocate(array));
    }

    public NativeArray(double[] array) {
        this(ArrayData.allocate(array));
    }

    public NativeArray(long[] array) {
        this(ArrayData.allocate(array.length));
        ArrayData arrayData = getArray();
        Class<?> widest = Integer.TYPE;
        for (int index = 0; index < array.length; index++) {
            long value = array[index];
            if (widest == Integer.TYPE && JSType.isRepresentableAsInt(value)) {
                arrayData = arrayData.set(index, (int) value, false);
            } else if (widest != Object.class && JSType.isRepresentableAsDouble(value)) {
                arrayData = arrayData.set(index, value, false);
                widest = Double.TYPE;
            } else {
                arrayData = arrayData.set(index, (Object) Long.valueOf(value), false);
                widest = Object.class;
            }
        }
        setArray(arrayData);
    }

    public NativeArray(Object[] array) {
        this(ArrayData.allocate(array.length));
        ArrayData arrayData;
        ArrayData arrayData2 = getArray();
        for (int index = 0; index < array.length; index++) {
            Object value = array[index];
            if (value == ScriptRuntime.EMPTY) {
                arrayData = arrayData2.delete(index);
            } else {
                arrayData = arrayData2.set(index, value, false);
            }
            arrayData2 = arrayData;
        }
        setArray(arrayData2);
    }

    public NativeArray(ArrayData arrayData) {
        this(arrayData, Global.instance());
    }

    public NativeArray(ArrayData arrayData, Global global) {
        super(global.getArrayPrototype(), $nasgenmap$);
        setArray(arrayData);
        setIsArray();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
        GuardedInvocation inv = getArray().findFastGetMethod(getArray().getClass(), desc, request, operator);
        if (inv != null) {
            return inv;
        }
        return super.findGetMethod(desc, request, operator);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
        GuardedInvocation inv = getArray().findFastGetIndexMethod(getArray().getClass(), desc, request);
        if (inv != null) {
            return inv;
        }
        return super.findGetIndexMethod(desc, request);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findSetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
        GuardedInvocation inv = getArray().findFastSetIndexMethod(getArray().getClass(), desc, request);
        if (inv != null) {
            return inv;
        }
        return super.findSetIndexMethod(desc, request);
    }

    private static InvokeByName getJOIN() {
        return Global.instance().getInvokeByName(JOIN, new Callable<InvokeByName>() { // from class: jdk.nashorn.internal.objects.NativeArray.1
            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("join", ScriptObject.class);
            }
        });
    }

    private static MethodHandle createIteratorCallbackInvoker(Object key, final Class<?> rtype) {
        return Global.instance().getDynamicInvoker(key, new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.objects.NativeArray.2
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", rtype, Object.class, Object.class, Object.class, Double.TYPE, Object.class);
            }
        });
    }

    public static MethodHandle getEVERY_CALLBACK_INVOKER() {
        return createIteratorCallbackInvoker(EVERY_CALLBACK_INVOKER, Boolean.TYPE);
    }

    public static MethodHandle getSOME_CALLBACK_INVOKER() {
        return createIteratorCallbackInvoker(SOME_CALLBACK_INVOKER, Boolean.TYPE);
    }

    public static MethodHandle getFOREACH_CALLBACK_INVOKER() {
        return createIteratorCallbackInvoker(FOREACH_CALLBACK_INVOKER, Void.TYPE);
    }

    public static MethodHandle getMAP_CALLBACK_INVOKER() {
        return createIteratorCallbackInvoker(MAP_CALLBACK_INVOKER, Object.class);
    }

    public static MethodHandle getFILTER_CALLBACK_INVOKER() {
        return createIteratorCallbackInvoker(FILTER_CALLBACK_INVOKER, Boolean.TYPE);
    }

    public static MethodHandle getREDUCE_CALLBACK_INVOKER() {
        return Global.instance().getDynamicInvoker(REDUCE_CALLBACK_INVOKER, new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.objects.NativeArray.3
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Undefined.class, Object.class, Object.class, Double.TYPE, Object.class);
            }
        });
    }

    public static MethodHandle getCALL_CMP() {
        return Global.instance().getDynamicInvoker(CALL_CMP, new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.objects.NativeArray.4
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Double.TYPE, Object.class, Object.class, Object.class, Object.class);
            }
        });
    }

    private static InvokeByName getTO_LOCALE_STRING() {
        return Global.instance().getInvokeByName(TO_LOCALE_STRING, new Callable<InvokeByName>() { // from class: jdk.nashorn.internal.objects.NativeArray.5
            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("toLocaleString", ScriptObject.class, String.class, new Class[0]);
            }
        });
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "Array";
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getLength() {
        long length = getArray().length();
        if ($assertionsDisabled || length >= 0) {
            if (length <= 2147483647L) {
                return Integer.valueOf((int) length);
            }
            return Long.valueOf(length);
        }
        throw new AssertionError();
    }

    private boolean defineLength(long oldLen, PropertyDescriptor oldLenDesc, PropertyDescriptor desc, boolean reject) {
        if (!desc.has("value")) {
            return super.defineOwnProperty("length", desc, reject);
        }
        long newLen = validLength(desc.getValue());
        desc.setValue(JSType.toNarrowestNumber(newLen));
        if (newLen >= oldLen) {
            return super.defineOwnProperty("length", desc, reject);
        }
        if (!oldLenDesc.isWritable()) {
            if (reject) {
                throw ECMAErrors.typeError("property.not.writable", "length", ScriptRuntime.safeToString(this));
            }
            return false;
        }
        boolean newWritable = !desc.has(PropertyDescriptor.WRITABLE) || desc.isWritable();
        if (!newWritable) {
            desc.setWritable(true);
        }
        boolean succeeded = super.defineOwnProperty("length", desc, reject);
        if (!succeeded) {
            return false;
        }
        long o = oldLen;
        while (newLen < o) {
            o--;
            boolean deleteSucceeded = delete(o, false);
            if (!deleteSucceeded) {
                desc.setValue(Long.valueOf(o + 1));
                if (!newWritable) {
                    desc.setWritable(false);
                }
                super.defineOwnProperty("length", desc, false);
                if (reject) {
                    throw ECMAErrors.typeError("property.not.writable", "length", ScriptRuntime.safeToString(this));
                }
                return false;
            }
        }
        if (!newWritable) {
            ScriptObject newDesc = Global.newEmptyInstance();
            newDesc.set((Object) PropertyDescriptor.WRITABLE, (Object) false, 0);
            return super.defineOwnProperty("length", newDesc, false);
        }
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean defineOwnProperty(String key, Object propertyDesc, boolean reject) {
        PropertyDescriptor desc = toPropertyDescriptor(Global.instance(), propertyDesc);
        PropertyDescriptor oldLenDesc = (PropertyDescriptor) super.getOwnPropertyDescriptor("length");
        long oldLen = JSType.toUint32(oldLenDesc.getValue());
        if ("length".equals(key)) {
            boolean result = defineLength(oldLen, oldLenDesc, desc, reject);
            if (desc.has(PropertyDescriptor.WRITABLE) && !desc.isWritable()) {
                setIsLengthNotWritable();
            }
            return result;
        }
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            long longIndex = ArrayIndex.toLongIndex(index);
            if (longIndex >= oldLen && !oldLenDesc.isWritable()) {
                if (reject) {
                    throw ECMAErrors.typeError("property.not.writable", Long.toString(longIndex), ScriptRuntime.safeToString(this));
                }
                return false;
            }
            boolean succeeded = super.defineOwnProperty(key, desc, false);
            if (!succeeded) {
                if (reject) {
                    throw ECMAErrors.typeError("cant.redefine.property", key, ScriptRuntime.safeToString(this));
                }
                return false;
            } else if (longIndex >= oldLen) {
                oldLenDesc.setValue(Long.valueOf(longIndex + 1));
                super.defineOwnProperty("length", oldLenDesc, false);
                return true;
            } else {
                return true;
            }
        }
        return super.defineOwnProperty(key, desc, reject);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public final void defineOwnProperty(int index, Object value) {
        if ($assertionsDisabled || ArrayIndex.isValidArrayIndex(index)) {
            long longIndex = ArrayIndex.toLongIndex(index);
            if (longIndex >= getArray().length()) {
                setArray(getArray().ensure(longIndex));
            }
            setArray(getArray().set(index, value, false));
            return;
        }
        throw new AssertionError("invalid array index");
    }

    public Object[] asObjectArray() {
        return getArray().asObjectArray();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public void setIsLengthNotWritable() {
        super.setIsLengthNotWritable();
        setArray(ArrayData.setIsLengthNotWritable(getArray()));
    }

    public static boolean isArray(Object self, Object arg) {
        return isArray(arg) || ((arg instanceof JSObject) && ((JSObject) arg).isArray());
    }

    public static Object length(Object self) {
        if (isArray(self)) {
            long length = ((ScriptObject) self).getArray().length();
            if (!$assertionsDisabled && length < 0) {
                throw new AssertionError();
            }
            if (length <= 2147483647L) {
                return Integer.valueOf((int) length);
            }
            return Double.valueOf(length);
        }
        return 0;
    }

    public static void length(Object self, Object length) {
        if (isArray(self)) {
            ((ScriptObject) self).setLength(validLength(length));
        }
    }

    public static Object getProtoLength(Object self) {
        return length(self);
    }

    public static void setProtoLength(Object self, Object length) {
        length(self, length);
    }

    public static long validLength(Object length) {
        double doubleLength = JSType.toNumber(length);
        if (doubleLength != JSType.toUint32(length)) {
            throw ECMAErrors.rangeError("inappropriate.array.length", ScriptRuntime.safeToString(length));
        }
        return (long) doubleLength;
    }

    public static Object toString(Object self) {
        Object obj = Global.toObject(self);
        if (obj instanceof ScriptObject) {
            InvokeByName joinInvoker = getJOIN();
            ScriptObject sobj = (ScriptObject) obj;
            try {
                Object join = joinInvoker.getGetter().invokeExact(sobj);
                if (Bootstrap.isCallable(join)) {
                    return joinInvoker.getInvoker().invokeExact(join, sobj);
                }
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return ScriptRuntime.builtinObjectToString(self);
    }

    public static Object assertNumeric(Object self) {
        if (!(self instanceof NativeArray) || !((NativeArray) self).getArray().getOptimisticType().isNumeric()) {
            throw ECMAErrors.typeError("not.a.numeric.array", ScriptRuntime.safeToString(self));
        }
        return Boolean.TRUE;
    }

    public static String toLocaleString(Object self) {
        StringBuilder sb = new StringBuilder();
        Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(self, true);
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (obj != null && obj != ScriptRuntime.UNDEFINED) {
                Object val = JSType.toScriptObject(obj);
                try {
                    if (val instanceof ScriptObject) {
                        InvokeByName localeInvoker = getTO_LOCALE_STRING();
                        ScriptObject sobj = (ScriptObject) val;
                        Object toLocaleString = localeInvoker.getGetter().invokeExact(sobj);
                        if (Bootstrap.isCallable(toLocaleString)) {
                            sb.append(localeInvoker.getInvoker().invokeExact(toLocaleString, sobj));
                        } else {
                            throw ECMAErrors.typeError("not.a.function", "toLocaleString");
                        }
                    }
                } catch (Error | RuntimeException t) {
                    throw t;
                } catch (Throwable t2) {
                    throw new RuntimeException(t2);
                }
            }
            if (iter.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static NativeArray construct(boolean newObj, Object self, Object... args) {
        switch (args.length) {
            case 0:
                return new NativeArray(0L);
            case 1:
                Object len = args[0];
                if (len instanceof Number) {
                    if ((len instanceof Integer) || (len instanceof Long)) {
                        long length = ((Number) len).longValue();
                        if (length >= 0 && length < JSType.MAX_UINT) {
                            return new NativeArray(length);
                        }
                    }
                    long length2 = JSType.toUint32(len);
                    double numberLength = ((Number) len).doubleValue();
                    if (length2 != numberLength) {
                        throw ECMAErrors.rangeError("inappropriate.array.length", JSType.toString(numberLength));
                    }
                    return new NativeArray(length2);
                }
                return new NativeArray(new Object[]{args[0]});
            default:
                return new NativeArray(args);
        }
    }

    public static NativeArray construct(boolean newObj, Object self) {
        return new NativeArray(0L);
    }

    public static Object construct(boolean newObj, Object self, boolean element) {
        return new NativeArray(new Object[]{Boolean.valueOf(element)});
    }

    public static NativeArray construct(boolean newObj, Object self, int length) {
        if (length >= 0) {
            return new NativeArray(length);
        }
        return construct(newObj, self, Integer.valueOf(length));
    }

    public static NativeArray construct(boolean newObj, Object self, long length) {
        if (length >= 0 && length <= JSType.MAX_UINT) {
            return new NativeArray(length);
        }
        return construct(newObj, self, Long.valueOf(length));
    }

    public static NativeArray construct(boolean newObj, Object self, double length) {
        long uint32length = JSType.toUint32(length);
        if (uint32length == length) {
            return new NativeArray(uint32length);
        }
        return construct(newObj, self, Double.valueOf(length));
    }

    public static NativeArray concat(Object self, int arg) {
        ContinuousArrayData newData = getContinuousArrayDataCCE(self, Integer.class).copy();
        newData.fastPush(arg);
        return new NativeArray(newData);
    }

    public static NativeArray concat(Object self, long arg) {
        ContinuousArrayData newData = getContinuousArrayDataCCE(self, Long.class).copy();
        newData.fastPush(arg);
        return new NativeArray(newData);
    }

    public static NativeArray concat(Object self, double arg) {
        ContinuousArrayData newData = getContinuousArrayDataCCE(self, Double.class).copy();
        newData.fastPush(arg);
        return new NativeArray(newData);
    }

    public static NativeArray concat(Object self, Object arg) {
        ContinuousArrayData newData;
        ContinuousArrayData selfData = getContinuousArrayDataCCE(self);
        if (arg instanceof NativeArray) {
            ContinuousArrayData argData = (ContinuousArrayData) ((NativeArray) arg).getArray();
            if (argData.isEmpty()) {
                newData = selfData.copy();
            } else if (selfData.isEmpty()) {
                newData = argData.copy();
            } else {
                Class<?> widestElementType = selfData.widest(argData).getBoxedElementType();
                newData = ((ContinuousArrayData) selfData.convert(widestElementType)).fastConcat((ContinuousArrayData) argData.convert(widestElementType));
            }
        } else {
            newData = getContinuousArrayDataCCE(self, Object.class).copy();
            newData.fastPush(arg);
        }
        return new NativeArray(newData);
    }

    public static NativeArray concat(Object self, Object... args) {
        ArrayList<Object> list = new ArrayList<>();
        concatToList(list, Global.toObject(self));
        for (Object obj : args) {
            concatToList(list, obj);
        }
        return new NativeArray(list.toArray());
    }

    private static void concatToList(ArrayList<Object> list, Object obj) {
        boolean isScriptArray = isArray(obj);
        boolean isScriptObject = isScriptArray || (obj instanceof ScriptObject);
        if (isScriptArray || (obj instanceof Iterable) || (obj != null && obj.getClass().isArray())) {
            Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(obj, true);
            if (iter.hasNext()) {
                int i = 0;
                while (iter.hasNext()) {
                    Object value = iter.next();
                    boolean lacksIndex = obj != null && !((ScriptObject) obj).has(i);
                    if (value == ScriptRuntime.UNDEFINED && isScriptObject && lacksIndex) {
                        list.add(ScriptRuntime.EMPTY);
                    } else {
                        list.add(value);
                    }
                    i++;
                }
                return;
            } else if (!isScriptArray) {
                list.add(obj);
                return;
            } else {
                return;
            }
        }
        list.add(obj);
    }

    public static String join(Object self, Object separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(self, true);
        String sep = separator == ScriptRuntime.UNDEFINED ? "," : JSType.toString(separator);
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (obj != null && obj != ScriptRuntime.UNDEFINED) {
                sb.append(JSType.toString(obj));
            }
            if (iter.hasNext()) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    public static int popInt(Object self) {
        return getContinuousNonEmptyArrayDataCCE(self, IntElements.class).fastPopInt();
    }

    public static double popDouble(Object self) {
        return getContinuousNonEmptyArrayDataCCE(self, NumericElements.class).fastPopDouble();
    }

    public static Object popObject(Object self) {
        return getContinuousArrayDataCCE(self, null).fastPopObject();
    }

    public static Object pop(Object self) {
        try {
            ScriptObject sobj = (ScriptObject) self;
            if (bulkable(sobj)) {
                return sobj.getArray().pop();
            }
            long len = JSType.toUint32(sobj.getLength());
            if (len == 0) {
                sobj.set((Object) "length", 0, 2);
                return ScriptRuntime.UNDEFINED;
            }
            long index = len - 1;
            Object element = sobj.get(index);
            sobj.delete(index, true);
            sobj.set("length", index, 2);
            return element;
        } catch (ClassCastException | NullPointerException e) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
        }
    }

    public static double push(Object self, int arg) {
        return getContinuousArrayDataCCE(self, Integer.class).fastPush(arg);
    }

    public static double push(Object self, long arg) {
        return getContinuousArrayDataCCE(self, Long.class).fastPush(arg);
    }

    public static double push(Object self, double arg) {
        return getContinuousArrayDataCCE(self, Double.class).fastPush(arg);
    }

    public static double pushObject(Object self, Object arg) {
        return getContinuousArrayDataCCE(self, Object.class).fastPush(arg);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [jdk.nashorn.internal.runtime.ScriptObject, long] */
    public static Object push(Object self, Object... args) {
        try {
            ?? r0 = (ScriptObject) self;
            if (bulkable(r0) && r0.getArray().length() + args.length <= JSType.MAX_UINT) {
                ArrayData newData = r0.getArray().push(true, args);
                r0.setArray(newData);
                return JSType.toNarrowestNumber(newData.length());
            }
            long len = JSType.toUint32(r0.getLength());
            for (Object element : args) {
                len++;
                r0.set((double) r0, element, 2);
            }
            r0.set("length", len, 2);
            return JSType.toNarrowestNumber(len);
        } catch (ClassCastException | NullPointerException e) {
            throw ECMAErrors.typeError(Context.getGlobal(), e, "not.an.object", ScriptRuntime.safeToString(self));
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [jdk.nashorn.internal.runtime.ScriptObject, long] */
    public static double push(Object self, Object arg) {
        try {
            ?? r0 = (ScriptObject) self;
            ArrayData arrayData = r0.getArray();
            long length = arrayData.length();
            if (bulkable(r0) && length < JSType.MAX_UINT) {
                r0.setArray(arrayData.push(true, arg));
                return length + 1;
            }
            long len = JSType.toUint32(r0.getLength()) + 1;
            r0.set((double) r0, arg, 2);
            r0.set("length", len, 2);
            return len;
        } catch (ClassCastException | NullPointerException e) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
        }
    }

    public static Object reverse(Object self) {
        try {
            ScriptObject sobj = (ScriptObject) self;
            long len = JSType.toUint32(sobj.getLength());
            long middle = len / 2;
            for (long lower = 0; lower != middle; lower++) {
                long upper = (len - lower) - 1;
                Object lowerValue = sobj.get(lower);
                Object upperValue = sobj.get(upper);
                boolean lowerExists = sobj.has(lower);
                boolean upperExists = sobj.has(upper);
                if (lowerExists && upperExists) {
                    sobj.set(lower, upperValue, 2);
                    sobj.set(upper, lowerValue, 2);
                } else if (!lowerExists && upperExists) {
                    sobj.set(lower, upperValue, 2);
                    sobj.delete(upper, true);
                } else if (lowerExists && !upperExists) {
                    sobj.delete(lower, true);
                    sobj.set(upper, lowerValue, 2);
                }
            }
            return sobj;
        } catch (ClassCastException | NullPointerException e) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
        }
    }

    public static Object shift(Object self) {
        long len;
        Object obj = Global.toObject(self);
        Object first = ScriptRuntime.UNDEFINED;
        if (!(obj instanceof ScriptObject)) {
            return first;
        }
        ScriptObject sobj = (ScriptObject) obj;
        long len2 = JSType.toUint32(sobj.getLength());
        if (len2 > 0) {
            first = sobj.get(0);
            if (bulkable(sobj)) {
                sobj.getArray().shiftLeft(1);
            } else {
                boolean hasPrevious = true;
                long j = 1;
                while (true) {
                    long k = j;
                    if (k >= len2) {
                        break;
                    }
                    boolean hasCurrent = sobj.has(k);
                    if (hasCurrent) {
                        sobj.set(k - 1, sobj.get(k), 2);
                    } else if (hasPrevious) {
                        sobj.delete(k - 1, true);
                    }
                    hasPrevious = hasCurrent;
                    j = k + 1;
                }
            }
            long j2 = len2 - 1;
            len = j2;
            sobj.delete(j2, true);
        } else {
            len = 0;
        }
        sobj.set("length", len, 2);
        return first;
    }

    public static Object slice(Object self, Object start, Object end) {
        Object obj = Global.toObject(self);
        if (!(obj instanceof ScriptObject)) {
            return ScriptRuntime.UNDEFINED;
        }
        ScriptObject sobj = (ScriptObject) obj;
        long len = JSType.toUint32(sobj.getLength());
        long relativeStart = JSType.toLong(start);
        long relativeEnd = end == ScriptRuntime.UNDEFINED ? len : JSType.toLong(end);
        long k = relativeStart < 0 ? Math.max(len + relativeStart, 0L) : Math.min(relativeStart, len);
        long finale = relativeEnd < 0 ? Math.max(len + relativeEnd, 0L) : Math.min(relativeEnd, len);
        if (k >= finale) {
            return new NativeArray(0L);
        }
        if (bulkable(sobj)) {
            return new NativeArray(sobj.getArray().slice(k, finale));
        }
        NativeArray copy = new NativeArray(finale - k);
        long n = 0;
        while (k < finale) {
            if (sobj.has(k)) {
                copy.defineOwnProperty(ArrayIndex.getArrayIndex(n), sobj.get(k));
            }
            n++;
            k++;
        }
        return copy;
    }

    private static Object compareFunction(Object comparefn) {
        if (comparefn == ScriptRuntime.UNDEFINED) {
            return null;
        }
        if (!Bootstrap.isCallable(comparefn)) {
            throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(comparefn));
        }
        return comparefn;
    }

    private static Object[] sort(Object[] array, Object comparefn) {
        final Object cmp = compareFunction(comparefn);
        List<Object> list = Arrays.asList(array);
        final Object cmpThis = (cmp == null || Bootstrap.isStrictCallable(cmp)) ? ScriptRuntime.UNDEFINED : Global.instance();
        try {
            Collections.sort(list, new Comparator<Object>() { // from class: jdk.nashorn.internal.objects.NativeArray.6
                private final MethodHandle call_cmp = NativeArray.getCALL_CMP();

                @Override // java.util.Comparator
                public int compare(Object x, Object y) {
                    if (x == ScriptRuntime.UNDEFINED && y == ScriptRuntime.UNDEFINED) {
                        return 0;
                    }
                    if (x == ScriptRuntime.UNDEFINED) {
                        return 1;
                    }
                    if (y == ScriptRuntime.UNDEFINED) {
                        return -1;
                    }
                    if (cmp != null) {
                        try {
                            return (int) Math.signum(this.call_cmp.invokeExact(cmp, cmpThis, x, y));
                        } catch (Error | RuntimeException e) {
                            throw e;
                        } catch (Throwable t) {
                            throw new RuntimeException(t);
                        }
                    }
                    return JSType.toString(x).compareTo(JSType.toString(y));
                }
            });
        } catch (IllegalArgumentException e) {
        }
        return list.toArray(new Object[array.length]);
    }

    public static ScriptObject sort(Object self, Object comparefn) {
        try {
            ScriptObject sobj = (ScriptObject) self;
            long len = JSType.toUint32(sobj.getLength());
            ArrayData array = sobj.getArray();
            if (len > 1) {
                ArrayList<Object> src = new ArrayList<>();
                Iterator<Long> iter = array.indexIterator();
                while (iter.hasNext()) {
                    long index = iter.next().longValue();
                    if (index >= len) {
                        break;
                    }
                    src.add(array.getObject((int) index));
                }
                Object[] sorted = sort(src.toArray(), comparefn);
                for (int i = 0; i < sorted.length; i++) {
                    array = array.set(i, sorted[i], true);
                }
                if (sorted.length != len) {
                    array = array.delete(sorted.length, len - 1);
                }
                sobj.setArray(array);
            }
            return sobj;
        } catch (ClassCastException | NullPointerException e) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
        }
    }

    public static Object splice(Object self, Object... args) {
        Object[] items;
        NativeArray returnValue;
        Object obj = Global.toObject(self);
        if (!(obj instanceof ScriptObject)) {
            return ScriptRuntime.UNDEFINED;
        }
        Object start = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
        Object deleteCount = args.length > 1 ? args[1] : ScriptRuntime.UNDEFINED;
        if (args.length > 2) {
            items = new Object[args.length - 2];
            System.arraycopy(args, 2, items, 0, items.length);
        } else {
            items = ScriptRuntime.EMPTY_ARRAY;
        }
        ScriptObject sobj = (ScriptObject) obj;
        long len = JSType.toUint32(sobj.getLength());
        long relativeStart = JSType.toLong(start);
        long actualStart = relativeStart < 0 ? Math.max(len + relativeStart, 0L) : Math.min(relativeStart, len);
        long actualDeleteCount = Math.min(Math.max(JSType.toLong(deleteCount), 0L), len - actualStart);
        if (actualStart <= 2147483647L && actualDeleteCount <= 2147483647L && bulkable(sobj)) {
            try {
                returnValue = new NativeArray(sobj.getArray().fastSplice((int) actualStart, (int) actualDeleteCount, items.length));
                int k = (int) actualStart;
                int i = 0;
                while (i < items.length) {
                    sobj.defineOwnProperty(k, items[i]);
                    i++;
                    k++;
                }
            } catch (UnsupportedOperationException e) {
                returnValue = slowSplice(sobj, actualStart, actualDeleteCount, items, len);
            }
        } else {
            returnValue = slowSplice(sobj, actualStart, actualDeleteCount, items, len);
        }
        return returnValue;
    }

    private static NativeArray slowSplice(ScriptObject sobj, long start, long deleteCount, Object[] items, long len) {
        NativeArray array = new NativeArray(deleteCount);
        long j = 0;
        while (true) {
            long k = j;
            if (k >= deleteCount) {
                break;
            }
            long from = start + k;
            if (sobj.has(from)) {
                array.defineOwnProperty(ArrayIndex.getArrayIndex(k), sobj.get(from));
            }
            j = k + 1;
        }
        if (items.length < deleteCount) {
            long j2 = start;
            while (true) {
                long k2 = j2;
                if (k2 >= len - deleteCount) {
                    break;
                }
                long from2 = k2 + deleteCount;
                long to = k2 + items.length;
                if (sobj.has(from2)) {
                    sobj.set(to, sobj.get(from2), 2);
                } else {
                    sobj.delete(to, true);
                }
                j2 = k2 + 1;
            }
            long j3 = len;
            while (true) {
                long k3 = j3;
                if (k3 <= (len - deleteCount) + items.length) {
                    break;
                }
                sobj.delete(k3 - 1, true);
                j3 = k3 - 1;
            }
        } else if (items.length > deleteCount) {
            long j4 = len;
            long j5 = deleteCount;
            while (true) {
                long k4 = j4 - j5;
                if (k4 <= start) {
                    break;
                }
                long from3 = (k4 + deleteCount) - 1;
                long to2 = (k4 + items.length) - 1;
                if (sobj.has(from3)) {
                    Object fromValue = sobj.get(from3);
                    sobj.set(to2, fromValue, 2);
                } else {
                    sobj.delete(to2, true);
                }
                j4 = k4;
                j5 = 1;
            }
        }
        long k5 = start;
        int i = 0;
        while (i < items.length) {
            sobj.set(k5, items[i], 2);
            i++;
            k5++;
        }
        long newLength = (len - deleteCount) + items.length;
        sobj.set("length", newLength, 2);
        return array;
    }

    public static Object unshift(Object self, Object... items) {
        Object obj = Global.toObject(self);
        if (!(obj instanceof ScriptObject)) {
            return ScriptRuntime.UNDEFINED;
        }
        ScriptObject sobj = (ScriptObject) obj;
        long len = JSType.toUint32(sobj.getLength());
        if (items == null) {
            return ScriptRuntime.UNDEFINED;
        }
        if (bulkable(sobj)) {
            sobj.getArray().shiftRight(items.length);
            for (int j = 0; j < items.length; j++) {
                sobj.setArray(sobj.getArray().set(j, items[j], true));
            }
        } else {
            long j2 = len;
            while (true) {
                long k = j2;
                if (k <= 0) {
                    break;
                }
                long from = k - 1;
                long to = (k + items.length) - 1;
                if (sobj.has(from)) {
                    Object fromValue = sobj.get(from);
                    sobj.set(to, fromValue, 2);
                } else {
                    sobj.delete(to, true);
                }
                j2 = k - 1;
            }
            for (int j3 = 0; j3 < items.length; j3++) {
                sobj.set(j3, items[j3], 2);
            }
        }
        long newLength = len + items.length;
        sobj.set("length", newLength, 2);
        return JSType.toNarrowestNumber(newLength);
    }

    public static double indexOf(Object self, Object searchElement, Object fromIndex) {
        try {
            ScriptObject sobj = (ScriptObject) Global.toObject(self);
            long len = JSType.toUint32(sobj.getLength());
            if (len == 0) {
                return -1.0d;
            }
            long n = JSType.toLong(fromIndex);
            if (n >= len) {
                return -1.0d;
            }
            for (long k = Math.max(0L, n < 0 ? len - Math.abs(n) : n); k < len; k++) {
                if (sobj.has(k) && ScriptRuntime.EQ_STRICT(sobj.get(k), searchElement)) {
                    return k;
                }
            }
            return -1.0d;
        } catch (ClassCastException | NullPointerException e) {
            return -1.0d;
        }
    }

    public static double lastIndexOf(Object self, Object... args) {
        try {
            ScriptObject sobj = (ScriptObject) Global.toObject(self);
            long len = JSType.toUint32(sobj.getLength());
            if (len == 0) {
                return -1.0d;
            }
            Object searchElement = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
            long n = args.length > 1 ? JSType.toLong(args[1]) : len - 1;
            for (long k = n < 0 ? len - Math.abs(n) : Math.min(n, len - 1); k >= 0; k--) {
                if (sobj.has(k) && ScriptRuntime.EQ_STRICT(sobj.get(k), searchElement)) {
                    return k;
                }
            }
            return -1.0d;
        } catch (ClassCastException | NullPointerException e) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
        }
    }

    public static boolean every(Object self, Object callbackfn, Object thisArg) {
        return applyEvery(Global.toObject(self), callbackfn, thisArg);
    }

    private static boolean applyEvery(Object self, Object callbackfn, Object thisArg) {
        return new IteratorAction<Boolean>(Global.toObject(self), callbackfn, thisArg, true) { // from class: jdk.nashorn.internal.objects.NativeArray.7
            private final MethodHandle everyInvoker = NativeArray.getEVERY_CALLBACK_INVOKER();

            /* JADX WARN: Type inference failed for: r1v3, types: [T, java.lang.Boolean] */
            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            protected boolean forEach(Object val, double i) throws Throwable {
                ?? valueOf = Boolean.valueOf(this.everyInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self));
                this.result = valueOf;
                return ((Boolean) valueOf).booleanValue();
            }
        }.apply().booleanValue();
    }

    public static boolean some(Object self, Object callbackfn, Object thisArg) {
        return new IteratorAction<Boolean>(Global.toObject(self), callbackfn, thisArg, false) { // from class: jdk.nashorn.internal.objects.NativeArray.8
            private final MethodHandle someInvoker = NativeArray.getSOME_CALLBACK_INVOKER();

            /* JADX WARN: Type inference failed for: r1v3, types: [T, java.lang.Boolean] */
            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            protected boolean forEach(Object val, double i) throws Throwable {
                ?? valueOf = Boolean.valueOf(this.someInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self));
                this.result = valueOf;
                return !valueOf.booleanValue();
            }
        }.apply().booleanValue();
    }

    public static Object forEach(Object self, Object callbackfn, Object thisArg) {
        return new IteratorAction<Object>(Global.toObject(self), callbackfn, thisArg, ScriptRuntime.UNDEFINED) { // from class: jdk.nashorn.internal.objects.NativeArray.9
            private final MethodHandle forEachInvoker = NativeArray.getFOREACH_CALLBACK_INVOKER();

            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            protected boolean forEach(Object val, double i) throws Throwable {
                this.forEachInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self);
                return true;
            }
        }.apply();
    }

    public static NativeArray map(Object self, Object callbackfn, Object thisArg) {
        return new IteratorAction<NativeArray>(Global.toObject(self), callbackfn, thisArg, null) { // from class: jdk.nashorn.internal.objects.NativeArray.10
            private final MethodHandle mapInvoker = NativeArray.getMAP_CALLBACK_INVOKER();

            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            protected boolean forEach(Object val, double i) throws Throwable {
                Object r = this.mapInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self);
                ((NativeArray) this.result).defineOwnProperty(ArrayIndex.getArrayIndex(this.index), r);
                return true;
            }

            /* JADX WARN: Type inference failed for: r1v0, types: [jdk.nashorn.internal.objects.NativeArray, T] */
            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            public void applyLoopBegin(ArrayLikeIterator<Object> iter0) {
                this.result = new NativeArray(iter0.getLength());
            }
        }.apply();
    }

    public static NativeArray filter(Object self, Object callbackfn, Object thisArg) {
        return new IteratorAction<NativeArray>(Global.toObject(self), callbackfn, thisArg, new NativeArray()) { // from class: jdk.nashorn.internal.objects.NativeArray.11

            /* renamed from: to */
            private long f249to = 0;
            private final MethodHandle filterInvoker = NativeArray.getFILTER_CALLBACK_INVOKER();

            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            protected boolean forEach(Object val, double i) throws Throwable {
                if (this.filterInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self)) {
                    long j = this.f249to;
                    this.f249to = j + 1;
                    ((NativeArray) this.result).defineOwnProperty(ArrayIndex.getArrayIndex(j), val);
                    return true;
                }
                return true;
            }
        }.apply();
    }

    private static Object reduceInner(ArrayLikeIterator<Object> iter, Object self, Object... args) {
        Object callbackfn = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
        boolean initialValuePresent = args.length > 1;
        Object initialValue = initialValuePresent ? args[1] : ScriptRuntime.UNDEFINED;
        if (callbackfn == ScriptRuntime.UNDEFINED) {
            throw ECMAErrors.typeError("not.a.function", "undefined");
        }
        if (!initialValuePresent) {
            if (iter.hasNext()) {
                initialValue = iter.next();
            } else {
                throw ECMAErrors.typeError("array.reduce.invalid.init", new String[0]);
            }
        }
        return new IteratorAction<Object>(Global.toObject(self), callbackfn, ScriptRuntime.UNDEFINED, initialValue, iter) { // from class: jdk.nashorn.internal.objects.NativeArray.12
            private final MethodHandle reduceInvoker = NativeArray.getREDUCE_CALLBACK_INVOKER();

            /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.Object] */
            @Override // jdk.nashorn.internal.runtime.arrays.IteratorAction
            protected boolean forEach(Object val, double i) throws Throwable {
                this.result = this.reduceInvoker.invokeExact(this.callbackfn, ScriptRuntime.UNDEFINED, this.result, val, i, this.self);
                return true;
            }
        }.apply();
    }

    public static Object reduce(Object self, Object... args) {
        return reduceInner(ArrayLikeIterator.arrayLikeIterator(self), self, args);
    }

    public static Object reduceRight(Object self, Object... args) {
        return reduceInner(ArrayLikeIterator.reverseArrayLikeIterator(self), self, args);
    }

    private static boolean bulkable(ScriptObject self) {
        return self.isArray() && !hasInheritedArrayEntries(self) && !self.isLengthNotWritable();
    }

    private static boolean hasInheritedArrayEntries(ScriptObject self) {
        ScriptObject proto = self.getProto();
        while (true) {
            ScriptObject proto2 = proto;
            if (proto2 != null) {
                if (proto2.hasArrayEntries()) {
                    return true;
                }
                proto = proto2.getProto();
            } else {
                return false;
            }
        }
    }

    public String toString() {
        return "NativeArray@" + Debug.m67id(this) + " [" + getArray().getClass().getSimpleName() + ']';
    }

    @Override // jdk.nashorn.internal.runtime.OptimisticBuiltins
    public SpecializedFunction.LinkLogic getLinkLogic(Class<? extends SpecializedFunction.LinkLogic> clazz) {
        if (clazz != PushLinkLogic.class) {
            if (clazz != PopLinkLogic.class) {
                if (clazz != ConcatLinkLogic.class) {
                    return null;
                }
                return ConcatLinkLogic.INSTANCE;
            }
            return PopLinkLogic.INSTANCE;
        }
        return PushLinkLogic.INSTANCE;
    }

    @Override // jdk.nashorn.internal.runtime.OptimisticBuiltins
    public boolean hasPerInstanceAssumptions() {
        return true;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$ArrayLinkLogic.class */
    public static abstract class ArrayLinkLogic extends SpecializedFunction.LinkLogic {
        protected ArrayLinkLogic() {
        }

        protected static ContinuousArrayData getContinuousArrayData(Object self) {
            try {
                return (ContinuousArrayData) ((NativeArray) self).getArray();
            } catch (Exception e) {
                return null;
            }
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public Class<? extends Throwable> getRelinkException() {
            return ClassCastException.class;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$ConcatLinkLogic.class */
    private static final class ConcatLinkLogic extends ArrayLinkLogic {
        private static final SpecializedFunction.LinkLogic INSTANCE = new ConcatLinkLogic();

        private ConcatLinkLogic() {
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
            Object[] args = request.getArguments();
            if (args.length != 3) {
                return false;
            }
            ContinuousArrayData selfData = getContinuousArrayData(self);
            if (selfData == null) {
                return false;
            }
            Object arg = args[2];
            if (arg instanceof NativeArray) {
                ContinuousArrayData argData = getContinuousArrayData(arg);
                if (argData == null) {
                    return false;
                }
                return true;
            }
            return true;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$PushLinkLogic.class */
    private static final class PushLinkLogic extends ArrayLinkLogic {
        private static final SpecializedFunction.LinkLogic INSTANCE = null;

        private PushLinkLogic() {
        }

        static {
            INSTANCE = new PushLinkLogic();
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
            return getContinuousArrayData(self) != null;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArray$PopLinkLogic.class */
    private static final class PopLinkLogic extends ArrayLinkLogic {
        private static final SpecializedFunction.LinkLogic INSTANCE = null;

        private PopLinkLogic() {
        }

        static {
            INSTANCE = new PopLinkLogic();
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
            ContinuousArrayData data = getContinuousNonEmptyArrayData(self);
            if (data != null) {
                Class<?> elementType = data.getElementType();
                Class<?> returnType = desc.getMethodType().returnType();
                boolean typeFits = JSType.getAccessorTypeIndex(returnType) >= JSType.getAccessorTypeIndex(elementType);
                return typeFits;
            }
            return false;
        }

        private static ContinuousArrayData getContinuousNonEmptyArrayData(Object self) {
            ContinuousArrayData data = getContinuousArrayData(self);
            if (data == null || data.length() == 0) {
                return null;
            }
            return data;
        }
    }

    private static final <T> ContinuousArrayData getContinuousNonEmptyArrayDataCCE(Object self, Class<T> clazz) {
        try {
            ContinuousArrayData data = (ContinuousArrayData) ((NativeArray) self).getArray();
            if (data.length() != 0) {
                return data;
            }
        } catch (NullPointerException e) {
        }
        throw new ClassCastException();
    }

    private static final ContinuousArrayData getContinuousArrayDataCCE(Object self) {
        try {
            return (ContinuousArrayData) ((NativeArray) self).getArray();
        } catch (NullPointerException e) {
            throw new ClassCastException();
        }
    }

    private static final ContinuousArrayData getContinuousArrayDataCCE(Object self, Class<?> elementType) {
        try {
            return (ContinuousArrayData) ((NativeArray) self).getArray(elementType);
        } catch (NullPointerException e) {
            throw new ClassCastException();
        }
    }
}
