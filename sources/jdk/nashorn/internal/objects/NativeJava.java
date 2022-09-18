package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ListAdapter;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJava.class */
public final class NativeJava {
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJava$Constructor.class */
    final class Constructor extends ScriptObject {
        private Object isType;
        private Object synchronizedFunc;
        private Object isJavaMethod;
        private Object isJavaFunction;
        private Object isJavaObject;
        private Object isScriptObject;
        private Object isScriptFunction;
        private Object type;
        private Object typeName;

        /* renamed from: to */
        private Object f251to;
        private Object from;
        private Object extend;
        private Object _super;
        private Object asJSONCompatible;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$isType() {
            return this.isType;
        }

        public void S$isType(Object obj) {
            this.isType = obj;
        }

        public Object G$synchronizedFunc() {
            return this.synchronizedFunc;
        }

        public void S$synchronizedFunc(Object obj) {
            this.synchronizedFunc = obj;
        }

        public Object G$isJavaMethod() {
            return this.isJavaMethod;
        }

        public void S$isJavaMethod(Object obj) {
            this.isJavaMethod = obj;
        }

        public Object G$isJavaFunction() {
            return this.isJavaFunction;
        }

        public void S$isJavaFunction(Object obj) {
            this.isJavaFunction = obj;
        }

        public Object G$isJavaObject() {
            return this.isJavaObject;
        }

        public void S$isJavaObject(Object obj) {
            this.isJavaObject = obj;
        }

        public Object G$isScriptObject() {
            return this.isScriptObject;
        }

        public void S$isScriptObject(Object obj) {
            this.isScriptObject = obj;
        }

        public Object G$isScriptFunction() {
            return this.isScriptFunction;
        }

        public void S$isScriptFunction(Object obj) {
            this.isScriptFunction = obj;
        }

        public Object G$type() {
            return this.type;
        }

        public void S$type(Object obj) {
            this.type = obj;
        }

        public Object G$typeName() {
            return this.typeName;
        }

        public void S$typeName(Object obj) {
            this.typeName = obj;
        }

        public Object G$to() {
            return this.f251to;
        }

        public void S$to(Object obj) {
            this.f251to = obj;
        }

        public Object G$from() {
            return this.from;
        }

        public void S$from(Object obj) {
            this.from = obj;
        }

        public Object G$extend() {
            return this.extend;
        }

        public void S$extend(Object obj) {
            this.extend = obj;
        }

        public Object G$_super() {
            return this._super;
        }

        public void S$_super(Object obj) {
            this._super = obj;
        }

        public Object G$asJSONCompatible() {
            return this.asJSONCompatible;
        }

        public void S$asJSONCompatible(Object obj) {
            this.asJSONCompatible = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeJava.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJava$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:110)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeJava.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJava$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJava.Constructor.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Java";
        }
    }

    static {
        $clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private NativeJava() {
        throw new UnsupportedOperationException();
    }

    public static boolean isType(Object self, Object type) {
        return type instanceof StaticClass;
    }

    public static Object synchronizedFunc(Object self, Object func, Object obj) {
        if (func instanceof ScriptFunction) {
            return ((ScriptFunction) func).createSynchronized(obj);
        }
        throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(func));
    }

    public static boolean isJavaMethod(Object self, Object obj) {
        return Bootstrap.isDynamicMethod(obj);
    }

    public static boolean isJavaFunction(Object self, Object obj) {
        return Bootstrap.isCallable(obj) && !(obj instanceof ScriptFunction);
    }

    public static boolean isJavaObject(Object self, Object obj) {
        return obj != null && !(obj instanceof ScriptObject);
    }

    public static boolean isScriptObject(Object self, Object obj) {
        return obj instanceof ScriptObject;
    }

    public static boolean isScriptFunction(Object self, Object obj) {
        return obj instanceof ScriptFunction;
    }

    public static Object type(Object self, Object objTypeName) throws ClassNotFoundException {
        return type(objTypeName);
    }

    private static StaticClass type(Object objTypeName) throws ClassNotFoundException {
        return StaticClass.forClass(type(JSType.toString(objTypeName)));
    }

    private static Class<?> type(String typeName) throws ClassNotFoundException {
        if (typeName.endsWith("[]")) {
            return arrayType(typeName);
        }
        return simpleType(typeName);
    }

    public static Object typeName(Object self, Object type) {
        if (type instanceof StaticClass) {
            return ((StaticClass) type).getRepresentedClass().getName();
        }
        if (type instanceof Class) {
            return ((Class) type).getName();
        }
        return ScriptRuntime.UNDEFINED;
    }

    /* renamed from: to */
    public static Object m69to(Object self, Object obj, Object objType) throws ClassNotFoundException {
        Class<?> targetClass;
        StaticClass targetType;
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof ScriptObject) && !(obj instanceof JSObject)) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
        }
        if (objType == ScriptRuntime.UNDEFINED) {
            targetClass = Object[].class;
        } else {
            if (objType instanceof StaticClass) {
                targetType = (StaticClass) objType;
            } else {
                targetType = type(objType);
            }
            targetClass = targetType.getRepresentedClass();
        }
        if (targetClass.isArray()) {
            try {
                return JSType.toJavaArray(obj, targetClass.getComponentType());
            } catch (Exception exp) {
                throw ECMAErrors.typeError(exp, "java.array.conversion.failed", targetClass.getName());
            }
        } else if (targetClass == List.class || targetClass == Deque.class || targetClass == Queue.class || targetClass == Collection.class) {
            return ListAdapter.create(obj);
        } else {
            throw ECMAErrors.typeError("unsupported.java.to.type", targetClass.getName());
        }
    }

    public static NativeArray from(Object self, Object objArray) {
        if (objArray == null) {
            return null;
        }
        if (objArray instanceof Collection) {
            return new NativeArray(((Collection) objArray).toArray());
        }
        if (objArray instanceof Object[]) {
            return new NativeArray((Object[]) ((Object[]) objArray).clone());
        }
        if (objArray instanceof int[]) {
            return new NativeArray((int[]) ((int[]) objArray).clone());
        }
        if (objArray instanceof double[]) {
            return new NativeArray((double[]) ((double[]) objArray).clone());
        }
        if (objArray instanceof long[]) {
            return new NativeArray((long[]) ((long[]) objArray).clone());
        }
        if (objArray instanceof byte[]) {
            return new NativeArray(copyArray((byte[]) objArray));
        }
        if (objArray instanceof short[]) {
            return new NativeArray(copyArray((short[]) objArray));
        }
        if (objArray instanceof char[]) {
            return new NativeArray(copyArray((char[]) objArray));
        }
        if (objArray instanceof float[]) {
            return new NativeArray(copyArray((float[]) objArray));
        }
        if (objArray instanceof boolean[]) {
            return new NativeArray(copyArray((boolean[]) objArray));
        }
        throw ECMAErrors.typeError("cant.convert.to.javascript.array", objArray.getClass().getName());
    }

    private static int[] copyArray(byte[] in) {
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    private static int[] copyArray(short[] in) {
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    private static int[] copyArray(char[] in) {
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    private static double[] copyArray(float[] in) {
        double[] out = new double[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    private static Object[] copyArray(boolean[] in) {
        Object[] out = new Object[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = Boolean.valueOf(in[i]);
        }
        return out;
    }

    private static Class<?> simpleType(String typeName) throws ClassNotFoundException {
        Class<?> primClass = TypeUtilities.getPrimitiveTypeByName(typeName);
        if (primClass != null) {
            return primClass;
        }
        Context ctx = Global.getThisContext();
        try {
            return ctx.findClass(typeName);
        } catch (ClassNotFoundException e) {
            StringBuilder nextName = new StringBuilder(typeName);
            int lastDot = nextName.length();
            while (true) {
                lastDot = nextName.lastIndexOf(".", lastDot - 1);
                if (lastDot == -1) {
                    throw e;
                }
                nextName.setCharAt(lastDot, '$');
                try {
                    return ctx.findClass(nextName.toString());
                } catch (ClassNotFoundException e2) {
                }
            }
        }
    }

    private static Class<?> arrayType(String typeName) throws ClassNotFoundException {
        return Array.newInstance(type(typeName.substring(0, typeName.length() - 2)), 0).getClass();
    }

    public static Object extend(Object self, Object... types) {
        ScriptObject classOverrides;
        int typesLen;
        MethodHandles.Lookup lookup;
        if (types == null || types.length == 0) {
            throw ECMAErrors.typeError("extend.expects.at.least.one.argument", new String[0]);
        }
        int l = types.length;
        if (types[l - 1] instanceof ScriptObject) {
            classOverrides = (ScriptObject) types[l - 1];
            typesLen = l - 1;
            if (typesLen == 0) {
                throw ECMAErrors.typeError("extend.expects.at.least.one.type.argument", new String[0]);
            }
        } else {
            classOverrides = null;
            typesLen = l;
        }
        Class<?>[] stypes = new Class[typesLen];
        for (int i = 0; i < typesLen; i++) {
            try {
                stypes[i] = ((StaticClass) types[i]).getRepresentedClass();
            } catch (ClassCastException e) {
                throw ECMAErrors.typeError("extend.expects.java.types", new String[0]);
            }
        }
        if (self instanceof MethodHandles.Lookup) {
            lookup = (MethodHandles.Lookup) self;
        } else {
            lookup = MethodHandles.publicLookup();
        }
        return JavaAdapterFactory.getAdapterClassFor(stypes, classOverrides, lookup);
    }

    public static Object _super(Object self, Object adapter) {
        return Bootstrap.createSuperAdapter(adapter);
    }

    public static Object asJSONCompatible(Object self, Object obj) {
        return ScriptObjectMirror.wrapAsJSONCompatible(obj, Context.getGlobal());
    }
}
