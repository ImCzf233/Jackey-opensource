package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeError.class */
public final class NativeError extends ScriptObject {
    static final MethodHandle GET_COLUMNNUMBER = null;
    static final MethodHandle SET_COLUMNNUMBER = null;
    static final MethodHandle GET_LINENUMBER = null;
    static final MethodHandle SET_LINENUMBER = null;
    static final MethodHandle GET_FILENAME = null;
    static final MethodHandle SET_FILENAME = null;
    static final MethodHandle GET_STACK = null;
    static final MethodHandle SET_STACK = null;
    static final String MESSAGE = "message";
    static final String NAME = "name";
    static final String STACK = "__stack__";
    static final String LINENUMBER = "__lineNumber__";
    static final String COLUMNNUMBER = "__columnNumber__";
    static final String FILENAME = "__fileName__";
    public Object instMessage;
    public Object nashornException;
    private static PropertyMap $nasgenmap$;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeError$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object captureStackTrace;
        private Object dumpStack;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$captureStackTrace() {
            return this.captureStackTrace;
        }

        public void S$captureStackTrace(Object obj) {
            this.captureStackTrace = obj;
        }

        public Object G$dumpStack() {
            return this.dumpStack;
        }

        public void S$dumpStack(Object obj) {
            this.dumpStack = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeError.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x000E: CONST, method: jdk.nashorn.internal.objects.NativeError.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0018: CONST, method: jdk.nashorn.internal.objects.NativeError.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        Constructor() {
            /*
                r6 = this;
                r0 = r6
                java.lang.String r1 = "Error"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r8 = r1
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                java.lang.String r-2 = "captureStackTrace"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-4[r-3] = r-2
                r-6.captureStackTrace = r-5
                r-6 = r6
                java.lang.String r-5 = "dumpStack"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-7[r-6] = r-5
                r-9.dumpStack = r-8
                r-9 = r6
                jdk.nashorn.internal.objects.NativeError$Prototype r-8 = new jdk.nashorn.internal.objects.NativeError$Prototype
                r-7 = r-8
                r-7.<init>()
                r-7 = r-8
                r-6 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-7, r-6)
                r-9.setPrototype(r-8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeError.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeError$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object name;
        private Object message;
        private Object printStackTrace;
        private Object getStackTrace;
        private Object toString;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$name() {
            return this.name;
        }

        public void S$name(Object obj) {
            this.name = obj;
        }

        public Object G$message() {
            return this.message;
        }

        public void S$message(Object obj) {
            this.message = obj;
        }

        public Object G$printStackTrace() {
            return this.printStackTrace;
        }

        public void S$printStackTrace(Object obj) {
            this.printStackTrace = obj;
        }

        public Object G$getStackTrace() {
            return this.getStackTrace;
        }

        public void S$getStackTrace(Object obj) {
            this.getStackTrace = obj;
        }

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeError.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeError.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x001E: CONST, method: jdk.nashorn.internal.objects.NativeError.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        Prototype() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeError.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "printStackTrace"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 % r1
                r-1.printStackTrace = r0
                r-1 = r4
                java.lang.String r0 = "getStackTrace"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 % r0
                r-2.getStackTrace = r-1
                r-2 = r4
                java.lang.String r-1 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 % r-1
                r-3.toString = r-2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeError.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Error";
        }
    }

    /*  JADX ERROR: Failed to decode insn: 0x000C: CONST, method: jdk.nashorn.internal.objects.NativeError.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x001E: CONST, method: jdk.nashorn.internal.objects.NativeError.$clinit$():void
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
            r2 = 2
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "message"
            r3 = 2
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r191 = r3
            r3 = 0
            r8 = r3
            boolean r1 = r1.add(r2)
            r1 = r0
            java.lang.String r2 = "nashornException"
            r3 = 2
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r10 = r3
            jdk.nashorn.internal.runtime.AccessorProperty.create(r-1, r0, r1, r2)
            boolean r-2 = r-2.add(r-1)
            jdk.nashorn.internal.runtime.PropertyMap r-3 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r-3)
            jdk.nashorn.internal.objects.NativeError.$nasgenmap$ = r-3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeError.$clinit$():void");
    }

    public Object G$instMessage() {
        return this.instMessage;
    }

    public void S$instMessage(Object obj) {
        this.instMessage = obj;
    }

    public Object G$nashornException() {
        return this.nashornException;
    }

    public void S$nashornException(Object obj) {
        this.nashornException = obj;
    }

    static {
        GET_COLUMNNUMBER = findOwnMH("getColumnNumber", Object.class, Object.class);
        SET_COLUMNNUMBER = findOwnMH("setColumnNumber", Object.class, Object.class, Object.class);
        GET_LINENUMBER = findOwnMH("getLineNumber", Object.class, Object.class);
        SET_LINENUMBER = findOwnMH("setLineNumber", Object.class, Object.class, Object.class);
        GET_FILENAME = findOwnMH("getFileName", Object.class, Object.class);
        SET_FILENAME = findOwnMH("setFileName", Object.class, Object.class, Object.class);
        GET_STACK = findOwnMH("getStack", Object.class, Object.class);
        SET_STACK = findOwnMH("setStack", Object.class, Object.class, Object.class);
        $clinit$();
    }

    private NativeError(Object msg, ScriptObject proto, PropertyMap map) {
        super(proto, map);
        if (msg != ScriptRuntime.UNDEFINED) {
            this.instMessage = JSType.toString(msg);
        } else {
            delete(MESSAGE, false);
        }
        initException(this);
    }

    public NativeError(Object msg, Global global) {
        this(msg, global.getErrorPrototype(), $nasgenmap$);
    }

    private NativeError(Object msg) {
        this(msg, Global.instance());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "Error";
    }

    public static NativeError constructor(boolean newObj, Object self, Object msg) {
        return new NativeError(msg);
    }

    public static void initException(ScriptObject self) {
        new ECMAException(self, null);
    }

    public static Object captureStackTrace(Object self, Object errorObj) {
        ScriptObject sobj = Global.checkObject(errorObj);
        initException(sobj);
        sobj.delete((Object) STACK, false);
        if (!sobj.has("stack")) {
            ScriptFunction getStack = ScriptFunction.createBuiltin("getStack", GET_STACK);
            ScriptFunction setStack = ScriptFunction.createBuiltin("setStack", SET_STACK);
            sobj.addOwnProperty("stack", 2, getStack, setStack);
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object dumpStack(Object self) {
        Thread.dumpStack();
        return ScriptRuntime.UNDEFINED;
    }

    public static Object printStackTrace(Object self) {
        return ECMAException.printStackTrace(Global.checkObject(self));
    }

    public static Object getStackTrace(Object self) {
        Object[] res;
        ScriptObject sobj = Global.checkObject(self);
        Object exception = ECMAException.getException(sobj);
        if (exception instanceof Throwable) {
            res = NashornException.getScriptFrames((Throwable) exception);
        } else {
            res = ScriptRuntime.EMPTY_ARRAY;
        }
        return new NativeArray(res);
    }

    public static Object getLineNumber(Object self) {
        ScriptObject sobj = Global.checkObject(self);
        return sobj.has(LINENUMBER) ? sobj.get(LINENUMBER) : ECMAException.getLineNumber(sobj);
    }

    public static Object setLineNumber(Object self, Object value) {
        ScriptObject sobj = Global.checkObject(self);
        if (sobj.hasOwnProperty(LINENUMBER)) {
            sobj.put(LINENUMBER, value, false);
        } else {
            sobj.addOwnProperty(LINENUMBER, 2, value);
        }
        return value;
    }

    public static Object getColumnNumber(Object self) {
        ScriptObject sobj = Global.checkObject(self);
        return sobj.has(COLUMNNUMBER) ? sobj.get(COLUMNNUMBER) : ECMAException.getColumnNumber((ScriptObject) self);
    }

    public static Object setColumnNumber(Object self, Object value) {
        ScriptObject sobj = Global.checkObject(self);
        if (sobj.hasOwnProperty(COLUMNNUMBER)) {
            sobj.put(COLUMNNUMBER, value, false);
        } else {
            sobj.addOwnProperty(COLUMNNUMBER, 2, value);
        }
        return value;
    }

    public static Object getFileName(Object self) {
        ScriptObject sobj = Global.checkObject(self);
        return sobj.has(FILENAME) ? sobj.get(FILENAME) : ECMAException.getFileName((ScriptObject) self);
    }

    public static Object setFileName(Object self, Object value) {
        ScriptObject sobj = Global.checkObject(self);
        if (sobj.hasOwnProperty(FILENAME)) {
            sobj.put(FILENAME, value, false);
        } else {
            sobj.addOwnProperty(FILENAME, 2, value);
        }
        return value;
    }

    public static Object getStack(Object self) {
        ScriptObject sobj = Global.checkObject(self);
        if (sobj.has(STACK)) {
            return sobj.get(STACK);
        }
        Object exception = ECMAException.getException(sobj);
        if (exception instanceof Throwable) {
            Object value = getScriptStackString(sobj, (Throwable) exception);
            if (sobj.hasOwnProperty(STACK)) {
                sobj.put(STACK, value, false);
            } else {
                sobj.addOwnProperty(STACK, 2, value);
            }
            return value;
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object setStack(Object self, Object value) {
        ScriptObject sobj = Global.checkObject(self);
        if (sobj.hasOwnProperty(STACK)) {
            sobj.put(STACK, value, false);
        } else {
            sobj.addOwnProperty(STACK, 2, value);
        }
        return value;
    }

    public static Object toString(Object self) {
        Object name;
        Object msg;
        ScriptObject sobj = Global.checkObject(self);
        Object name2 = sobj.get(NAME);
        if (name2 == ScriptRuntime.UNDEFINED) {
            name = "Error";
        } else {
            name = JSType.toString(name2);
        }
        Object msg2 = sobj.get(MESSAGE);
        if (msg2 == ScriptRuntime.UNDEFINED) {
            msg = "";
        } else {
            msg = JSType.toString(msg2);
        }
        if (((String) name).isEmpty()) {
            return msg;
        }
        if (((String) msg).isEmpty()) {
            return name;
        }
        return name + ": " + msg;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NativeError.class, name, Lookup.f248MH.type(rtype, types));
    }

    private static String getScriptStackString(ScriptObject sobj, Throwable exp) {
        return JSType.toString(sobj) + "\n" + NashornException.getScriptStackString(exp);
    }
}
