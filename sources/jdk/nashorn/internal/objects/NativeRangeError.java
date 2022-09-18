package jdk.nashorn.internal.objects;

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
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRangeError.class */
public final class NativeRangeError extends ScriptObject {
    public Object instMessage;
    public Object nashornException;
    private static PropertyMap $nasgenmap$;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRangeError$Constructor.class */
    final class Constructor extends ScriptFunction {
        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeRangeError.Constructor.<init>():void
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
                r5 = this;
                r0 = r5
                java.lang.String r1 = "RangeError"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeRangeError$Prototype r-1 = new jdk.nashorn.internal.objects.NativeRangeError$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRangeError.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRangeError$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object name;
        private Object message;
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

        Prototype() {
            super($nasgenmap$);
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Error";
        }
    }

    static {
        $clinit$();
    }

    /*  JADX ERROR: Failed to decode insn: 0x000C: CONST, method: jdk.nashorn.internal.objects.NativeRangeError.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x001C: CONST, method: jdk.nashorn.internal.objects.NativeRangeError.$clinit$():void
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
            long r2 = r2 - r3
            jdk.nashorn.internal.runtime.AccessorProperty.create(r-1, r0, r1, r2)
            boolean r-2 = r-2.add(r-1)
            r-2 = r-3
            java.lang.String r-1 = "nashornException"
            r0 = 2
            // decode failed: Unsupported constant type: METHOD_HANDLE
            int r-1 = r-1 >> r0
            jdk.nashorn.internal.runtime.AccessorProperty r-4 = jdk.nashorn.internal.runtime.AccessorProperty.create(r-4, r-3, r-2, r-1)
            boolean r-5 = r-5.add(r-4)
            jdk.nashorn.internal.runtime.PropertyMap r-6 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r-6)
            jdk.nashorn.internal.objects.NativeRangeError.$nasgenmap$ = r-6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRangeError.$clinit$():void");
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

    private NativeRangeError(Object msg, ScriptObject proto, PropertyMap map) {
        super(proto, map);
        if (msg != ScriptRuntime.UNDEFINED) {
            this.instMessage = JSType.toString(msg);
        } else {
            delete("message", false);
        }
        NativeError.initException(this);
    }

    public NativeRangeError(Object msg, Global global) {
        this(msg, global.getRangeErrorPrototype(), $nasgenmap$);
    }

    private NativeRangeError(Object msg) {
        this(msg, Global.instance());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "Error";
    }

    public static NativeRangeError constructor(boolean newObj, Object self, Object msg) {
        return new NativeRangeError(msg);
    }
}
