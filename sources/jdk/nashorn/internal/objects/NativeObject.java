package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import jdk.nashorn.internal.runtime.linker.NashornBeansLinker;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeObject.class */
public final class NativeObject {
    public static final MethodHandle GET__PROTO__ = null;
    public static final MethodHandle SET__PROTO__ = null;
    private static final Object TO_STRING = null;
    private static final MethodType MIRROR_GETTER_TYPE = null;
    private static final MethodType MIRROR_SETTER_TYPE = null;
    private static PropertyMap $nasgenmap$;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeObject$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object setIndexedPropertiesToExternalArrayData;
        private Object getPrototypeOf;
        private Object setPrototypeOf;
        private Object getOwnPropertyDescriptor;
        private Object getOwnPropertyNames;
        private Object create;
        private Object defineProperty;
        private Object defineProperties;
        private Object seal;
        private Object freeze;
        private Object preventExtensions;
        private Object isSealed;
        private Object isFrozen;
        private Object isExtensible;
        private Object keys;
        private Object bindProperties;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$setIndexedPropertiesToExternalArrayData() {
            return this.setIndexedPropertiesToExternalArrayData;
        }

        public void S$setIndexedPropertiesToExternalArrayData(Object obj) {
            this.setIndexedPropertiesToExternalArrayData = obj;
        }

        public Object G$getPrototypeOf() {
            return this.getPrototypeOf;
        }

        public void S$getPrototypeOf(Object obj) {
            this.getPrototypeOf = obj;
        }

        public Object G$setPrototypeOf() {
            return this.setPrototypeOf;
        }

        public void S$setPrototypeOf(Object obj) {
            this.setPrototypeOf = obj;
        }

        public Object G$getOwnPropertyDescriptor() {
            return this.getOwnPropertyDescriptor;
        }

        public void S$getOwnPropertyDescriptor(Object obj) {
            this.getOwnPropertyDescriptor = obj;
        }

        public Object G$getOwnPropertyNames() {
            return this.getOwnPropertyNames;
        }

        public void S$getOwnPropertyNames(Object obj) {
            this.getOwnPropertyNames = obj;
        }

        public Object G$create() {
            return this.create;
        }

        public void S$create(Object obj) {
            this.create = obj;
        }

        public Object G$defineProperty() {
            return this.defineProperty;
        }

        public void S$defineProperty(Object obj) {
            this.defineProperty = obj;
        }

        public Object G$defineProperties() {
            return this.defineProperties;
        }

        public void S$defineProperties(Object obj) {
            this.defineProperties = obj;
        }

        public Object G$seal() {
            return this.seal;
        }

        public void S$seal(Object obj) {
            this.seal = obj;
        }

        public Object G$freeze() {
            return this.freeze;
        }

        public void S$freeze(Object obj) {
            this.freeze = obj;
        }

        public Object G$preventExtensions() {
            return this.preventExtensions;
        }

        public void S$preventExtensions(Object obj) {
            this.preventExtensions = obj;
        }

        public Object G$isSealed() {
            return this.isSealed;
        }

        public void S$isSealed(Object obj) {
            this.isSealed = obj;
        }

        public Object G$isFrozen() {
            return this.isFrozen;
        }

        public void S$isFrozen(Object obj) {
            this.isFrozen = obj;
        }

        public Object G$isExtensible() {
            return this.isExtensible;
        }

        public void S$isExtensible(Object obj) {
            this.isExtensible = obj;
        }

        public Object G$keys() {
            return this.keys;
        }

        public void S$keys(Object obj) {
            this.keys = obj;
        }

        public Object G$bindProperties() {
            return this.bindProperties;
        }

        public void S$bindProperties(Object obj) {
            this.bindProperties = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeObject.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeObject$Constructor.class
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
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeObject.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeObject$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeObject.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeObject$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object toLocaleString;
        private Object valueOf;
        private Object hasOwnProperty;
        private Object isPrototypeOf;
        private Object propertyIsEnumerable;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$toLocaleString() {
            return this.toLocaleString;
        }

        public void S$toLocaleString(Object obj) {
            this.toLocaleString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        public Object G$hasOwnProperty() {
            return this.hasOwnProperty;
        }

        public void S$hasOwnProperty(Object obj) {
            this.hasOwnProperty = obj;
        }

        public Object G$isPrototypeOf() {
            return this.isPrototypeOf;
        }

        public void S$isPrototypeOf(Object obj) {
            this.isPrototypeOf = obj;
        }

        public Object G$propertyIsEnumerable() {
            return this.propertyIsEnumerable;
        }

        public void S$propertyIsEnumerable(Object obj) {
            this.propertyIsEnumerable = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x001E: CONST, method: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0028: CONST, method: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0032: CONST, method: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x003C: CONST, method: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void
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
        Prototype() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeObject.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 >>> r1
                r-1.toString = r0
                r-1 = r4
                java.lang.String r0 = "toLocaleString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 >>> r0
                r-2.toLocaleString = r-1
                r-2 = r4
                java.lang.String r-1 = "valueOf"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 >>> r-1
                r-3.valueOf = r-2
                r-3 = r4
                java.lang.String r-2 = "hasOwnProperty"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = r-3 >>> r-2
                r-4.hasOwnProperty = r-3
                r-4 = r4
                java.lang.String r-3 = "isPrototypeOf"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-4 = r-4 >>> r-3
                r-5.isPrototypeOf = r-4
                r-5 = r4
                java.lang.String r-4 = "propertyIsEnumerable"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-5 = r-5 >>> r-4
                r-6.propertyIsEnumerable = r-5
                return
                r-6 = 0
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Object";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $assertionsDisabled = !NativeObject.class.desiredAssertionStatus();
        GET__PROTO__ = findOwnMH("get__proto__", ScriptObject.class, Object.class);
        SET__PROTO__ = findOwnMH("set__proto__", Object.class, Object.class, Object.class);
        TO_STRING = new Object();
        MIRROR_GETTER_TYPE = MethodType.methodType(Object.class, ScriptObjectMirror.class);
        MIRROR_SETTER_TYPE = MethodType.methodType(Object.class, ScriptObjectMirror.class, Object.class);
        $clinit$();
    }

    private static InvokeByName getTO_STRING() {
        return Global.instance().getInvokeByName(TO_STRING, new Callable<InvokeByName>() { // from class: jdk.nashorn.internal.objects.NativeObject.1
            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("toString", ScriptObject.class);
            }
        });
    }

    private static ScriptObject get__proto__(Object self) {
        ScriptObject sobj = Global.checkObject(Global.toObject(self));
        return sobj.getProto();
    }

    private static Object set__proto__(Object self, Object proto) {
        Global.checkObjectCoercible(self);
        if (!(self instanceof ScriptObject)) {
            return ScriptRuntime.UNDEFINED;
        }
        ScriptObject sobj = (ScriptObject) self;
        if (proto == null || (proto instanceof ScriptObject)) {
            sobj.setPrototypeOf(proto);
        }
        return ScriptRuntime.UNDEFINED;
    }

    private NativeObject() {
        throw new UnsupportedOperationException();
    }

    private static ECMAException notAnObject(Object obj) {
        return ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
    }

    public static ScriptObject setIndexedPropertiesToExternalArrayData(Object self, Object obj, Object buf) {
        Global.checkObject(obj);
        ScriptObject sobj = (ScriptObject) obj;
        if (buf instanceof ByteBuffer) {
            sobj.setArray(ArrayData.allocate((ByteBuffer) buf));
            return sobj;
        }
        throw ECMAErrors.typeError("not.a.bytebuffer", "setIndexedPropertiesToExternalArrayData's buf argument");
    }

    public static Object getPrototypeOf(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getProto();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).getProto();
        }
        JSType type = JSType.m66of(obj);
        if (type == JSType.OBJECT) {
            return null;
        }
        throw notAnObject(obj);
    }

    public static Object setPrototypeOf(Object self, Object obj, Object proto) {
        if (obj instanceof ScriptObject) {
            ((ScriptObject) obj).setPrototypeOf(proto);
            return obj;
        } else if (obj instanceof ScriptObjectMirror) {
            ((ScriptObjectMirror) obj).setProto(proto);
            return obj;
        } else {
            throw notAnObject(obj);
        }
    }

    public static Object getOwnPropertyDescriptor(Object self, Object obj, Object prop) {
        if (obj instanceof ScriptObject) {
            String key = JSType.toString(prop);
            ScriptObject sobj = (ScriptObject) obj;
            return sobj.getOwnPropertyDescriptor(key);
        } else if (obj instanceof ScriptObjectMirror) {
            String key2 = JSType.toString(prop);
            ScriptObjectMirror sobjMirror = (ScriptObjectMirror) obj;
            return sobjMirror.getOwnPropertyDescriptor(key2);
        } else {
            throw notAnObject(obj);
        }
    }

    public static ScriptObject getOwnPropertyNames(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return new NativeArray(((ScriptObject) obj).getOwnKeys(true));
        }
        if (obj instanceof ScriptObjectMirror) {
            return new NativeArray(((ScriptObjectMirror) obj).getOwnKeys(true));
        }
        throw notAnObject(obj);
    }

    public static ScriptObject create(Object self, Object proto, Object props) {
        if (proto != null) {
            Global.checkObject(proto);
        }
        ScriptObject newObj = Global.newEmptyInstance();
        newObj.setProto((ScriptObject) proto);
        if (props != ScriptRuntime.UNDEFINED) {
            defineProperties(self, newObj, props);
        }
        return newObj;
    }

    public static ScriptObject defineProperty(Object self, Object obj, Object prop, Object attr) {
        ScriptObject sobj = Global.checkObject(obj);
        sobj.defineOwnProperty(JSType.toString(prop), attr, true);
        return sobj;
    }

    public static ScriptObject defineProperties(Object self, Object obj, Object props) {
        ScriptObject sobj = Global.checkObject(obj);
        Object propsObj = Global.toObject(props);
        if (propsObj instanceof ScriptObject) {
            Object[] keys = ((ScriptObject) propsObj).getOwnKeys(false);
            for (Object key : keys) {
                String prop = JSType.toString(key);
                sobj.defineOwnProperty(prop, ((ScriptObject) propsObj).get(prop), true);
            }
        }
        return sobj;
    }

    public static Object seal(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).seal();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).seal();
        }
        throw notAnObject(obj);
    }

    public static Object freeze(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).freeze();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).freeze();
        }
        throw notAnObject(obj);
    }

    public static Object preventExtensions(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).preventExtensions();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).preventExtensions();
        }
        throw notAnObject(obj);
    }

    public static boolean isSealed(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).isSealed();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).isSealed();
        }
        throw notAnObject(obj);
    }

    public static boolean isFrozen(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).isFrozen();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).isFrozen();
        }
        throw notAnObject(obj);
    }

    public static boolean isExtensible(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).isExtensible();
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).isExtensible();
        }
        throw notAnObject(obj);
    }

    public static ScriptObject keys(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            ScriptObject sobj = (ScriptObject) obj;
            return new NativeArray(sobj.getOwnKeys(false));
        } else if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror sobjMirror = (ScriptObjectMirror) obj;
            return new NativeArray(sobjMirror.getOwnKeys(false));
        } else {
            throw notAnObject(obj);
        }
    }

    public static Object construct(boolean newObj, Object self, Object value) {
        JSType type = JSType.ofNoFunction(value);
        if (newObj || type == JSType.NULL || type == JSType.UNDEFINED) {
            switch (type) {
                case BOOLEAN:
                case NUMBER:
                case STRING:
                    return Global.toObject(value);
                case OBJECT:
                    return value;
                case NULL:
                case UNDEFINED:
                default:
                    return Global.newEmptyInstance();
            }
        }
        return Global.toObject(value);
    }

    public static String toString(Object self) {
        return ScriptRuntime.builtinObjectToString(self);
    }

    public static Object toLocaleString(Object self) {
        Object obj = JSType.toScriptObject(self);
        if (obj instanceof ScriptObject) {
            InvokeByName toStringInvoker = getTO_STRING();
            ScriptObject sobj = (ScriptObject) obj;
            try {
                Object toString = toStringInvoker.getGetter().invokeExact(sobj);
                if (Bootstrap.isCallable(toString)) {
                    return toStringInvoker.getInvoker().invokeExact(toString, sobj);
                }
                throw ECMAErrors.typeError("not.a.function", "toString");
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return ScriptRuntime.builtinObjectToString(self);
    }

    public static Object valueOf(Object self) {
        return Global.toObject(self);
    }

    public static boolean hasOwnProperty(Object self, Object v) {
        Object key = JSType.toPrimitive(v, String.class);
        Object obj = Global.toObject(self);
        return (obj instanceof ScriptObject) && ((ScriptObject) obj).hasOwnProperty(key);
    }

    public static boolean isPrototypeOf(Object self, Object v) {
        if (!(v instanceof ScriptObject)) {
            return false;
        }
        Object obj = Global.toObject(self);
        ScriptObject proto = (ScriptObject) v;
        do {
            proto = proto.getProto();
            if (proto == obj) {
                return true;
            }
        } while (proto != null);
        return false;
    }

    public static boolean propertyIsEnumerable(Object self, Object v) {
        Property property;
        String str = JSType.toString(v);
        Object obj = Global.toObject(self);
        return (obj instanceof ScriptObject) && (property = ((ScriptObject) obj).getMap().findProperty(str)) != null && property.isEnumerable();
    }

    public static Object bindProperties(Object self, Object target, Object source) {
        ScriptObject targetObj = Global.checkObject(target);
        Global.checkObjectCoercible(source);
        if (source instanceof ScriptObject) {
            ScriptObject sourceObj = (ScriptObject) source;
            PropertyMap sourceMap = sourceObj.getMap();
            Property[] properties = sourceMap.getProperties();
            ArrayList<Property> propList = new ArrayList<>();
            for (Property prop : properties) {
                if (prop.isEnumerable()) {
                    Object value = sourceObj.get(prop.getKey());
                    prop.setType(Object.class);
                    prop.setValue(sourceObj, sourceObj, value, false);
                    propList.add(prop);
                }
            }
            if (!propList.isEmpty()) {
                targetObj.addBoundProperties(sourceObj, (Property[]) propList.toArray(new Property[propList.size()]));
            }
        } else if (source instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) source;
            String[] keys = mirror.getOwnKeys(false);
            if (keys.length == 0) {
                return target;
            }
            AccessorProperty[] props = new AccessorProperty[keys.length];
            for (int idx = 0; idx < keys.length; idx++) {
                String name = keys[idx];
                MethodHandle getter = Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + name, MIRROR_GETTER_TYPE);
                MethodHandle setter = Bootstrap.createDynamicInvoker("dyn:setProp|setElem:" + name, MIRROR_SETTER_TYPE);
                props[idx] = AccessorProperty.create(name, 0, getter, setter);
            }
            targetObj.addBoundProperties(source, props);
        } else if (source instanceof StaticClass) {
            Class<?> clazz = ((StaticClass) source).getRepresentedClass();
            Bootstrap.checkReflectionAccess(clazz, true);
            bindBeanProperties(targetObj, source, BeansLinker.getReadableStaticPropertyNames(clazz), BeansLinker.getWritableStaticPropertyNames(clazz), BeansLinker.getStaticMethodNames(clazz));
        } else {
            Class<?> clazz2 = source.getClass();
            Bootstrap.checkReflectionAccess(clazz2, false);
            bindBeanProperties(targetObj, source, BeansLinker.getReadableInstancePropertyNames(clazz2), BeansLinker.getWritableInstancePropertyNames(clazz2), BeansLinker.getInstanceMethodNames(clazz2));
        }
        return target;
    }

    public static Object bindAllProperties(ScriptObject target, ScriptObjectMirror source) {
        Set<String> keys = source.keySet();
        AccessorProperty[] props = new AccessorProperty[keys.size()];
        int idx = 0;
        for (String name : keys) {
            MethodHandle getter = Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + name, MIRROR_GETTER_TYPE);
            MethodHandle setter = Bootstrap.createDynamicInvoker("dyn:setProp|setElem:" + name, MIRROR_SETTER_TYPE);
            props[idx] = AccessorProperty.create(name, 0, getter, setter);
            idx++;
        }
        target.addBoundProperties(source, props);
        return target;
    }

    private static void bindBeanProperties(ScriptObject targetObj, Object source, Collection<String> readablePropertyNames, Collection<String> writablePropertyNames, Collection<String> methodNames) {
        MethodHandle getter;
        MethodHandle setter;
        Set<String> propertyNames = new HashSet<>(readablePropertyNames);
        propertyNames.addAll(writablePropertyNames);
        Class<?> clazz = source.getClass();
        MethodType getterType = MethodType.methodType(Object.class, clazz);
        MethodType setterType = MethodType.methodType(Object.class, clazz, Object.class);
        GuardingDynamicLinker linker = BeansLinker.getLinkerForClass(clazz);
        List<AccessorProperty> properties = new ArrayList<>(propertyNames.size() + methodNames.size());
        for (String methodName : methodNames) {
            try {
                MethodHandle method = getBeanOperation(linker, "dyn:getMethod:" + methodName, getterType, source);
                properties.add(AccessorProperty.create(methodName, 1, getBoundBeanMethodGetter(source, method), Lookup.EMPTY_SETTER));
            } catch (IllegalAccessError e) {
            }
        }
        for (String propertyName : propertyNames) {
            if (readablePropertyNames.contains(propertyName)) {
                try {
                    getter = getBeanOperation(linker, "dyn:getProp:" + propertyName, getterType, source);
                } catch (IllegalAccessError e2) {
                    getter = Lookup.EMPTY_GETTER;
                }
            } else {
                getter = Lookup.EMPTY_GETTER;
            }
            boolean isWritable = writablePropertyNames.contains(propertyName);
            if (isWritable) {
                try {
                    setter = getBeanOperation(linker, "dyn:setProp:" + propertyName, setterType, source);
                } catch (IllegalAccessError e3) {
                    setter = Lookup.EMPTY_SETTER;
                }
            } else {
                setter = Lookup.EMPTY_SETTER;
            }
            if (getter != Lookup.EMPTY_GETTER || setter != Lookup.EMPTY_SETTER) {
                properties.add(AccessorProperty.create(propertyName, isWritable ? 0 : 1, getter, setter));
            }
        }
        targetObj.addBoundProperties(source, (AccessorProperty[]) properties.toArray(new AccessorProperty[properties.size()]));
    }

    private static MethodHandle getBoundBeanMethodGetter(Object source, MethodHandle methodGetter) {
        try {
            return MethodHandles.dropArguments(MethodHandles.constant(Object.class, Bootstrap.bindCallable(methodGetter.invoke(source), source, null)), 0, Object.class);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static MethodHandle getBeanOperation(GuardingDynamicLinker linker, String operation, MethodType methodType, Object source) {
        try {
            GuardedInvocation inv = NashornBeansLinker.getGuardedInvocation(linker, createLinkRequest(operation, methodType, source), Bootstrap.getLinkerServices());
            if (!$assertionsDisabled && !passesGuard(source, inv.getGuard())) {
                throw new AssertionError();
            } else if (!$assertionsDisabled && inv.getSwitchPoints() != null) {
                throw new AssertionError();
            } else {
                return inv.getInvocation();
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static boolean passesGuard(Object obj, MethodHandle guard) throws Throwable {
        return guard == null || guard.invoke(obj);
    }

    private static LinkRequest createLinkRequest(String operation, MethodType methodType, Object source) {
        return new LinkRequestImpl(CallSiteDescriptorFactory.create(MethodHandles.publicLookup(), operation, methodType), null, 0, false, source);
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NativeObject.class, name, Lookup.f248MH.type(rtype, types));
    }
}
