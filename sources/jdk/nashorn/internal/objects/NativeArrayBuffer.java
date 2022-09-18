package jdk.nashorn.internal.objects;

import java.nio.ByteBuffer;
import jdk.nashorn.internal.runtime.ECMAErrors;
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
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArrayBuffer.class */
public final class NativeArrayBuffer extends ScriptObject {

    /* renamed from: nb */
    private final ByteBuffer f250nb;
    private static PropertyMap $nasgenmap$;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArrayBuffer$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object isView;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$isView() {
            return this.isView;
        }

        public void S$isView(Object obj) {
            this.isView = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeArrayBuffer.Constructor.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x000E: CONST, method: jdk.nashorn.internal.objects.NativeArrayBuffer.Constructor.<init>():void
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
                java.lang.String r1 = "ArrayBuffer"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r0 = r0[r1]
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                java.lang.String r-2 = "isView"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r8 = r-2
                r-4.isView = r-3
                r-4 = r6
                jdk.nashorn.internal.objects.NativeArrayBuffer$Prototype r-3 = new jdk.nashorn.internal.objects.NativeArrayBuffer$Prototype
                r-2 = r-3
                r-2.<init>()
                r-2 = r-3
                r-1 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-2, r-1)
                r-4.setPrototype(r-3)
                r-4 = r6
                r-3 = 1
                r-4.setArity(r-3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArrayBuffer.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeArrayBuffer$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object slice;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$slice() {
            return this.slice;
        }

        public void S$slice(Object obj) {
            this.slice = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0015: CONST, method: jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0021: CONST, method: jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.<init>():void
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
                r11 = this;
                r0 = r11
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r11
                java.lang.String r1 = "slice"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                jdk.nashorn.internal.runtime.Specialization[] r1 = new jdk.nashorn.internal.runtime.Specialization[r1]
                r2 = r1
                r3 = 0
                jdk.nashorn.internal.runtime.Specialization r4 = new jdk.nashorn.internal.runtime.Specialization
                r5 = r4
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r3.<init>(r4, r5)
                r0[r1] = r2
                r0 = r-1
                r1 = 1
                jdk.nashorn.internal.runtime.Specialization r2 = new jdk.nashorn.internal.runtime.Specialization
                r3 = r2
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r1.<init>(r2, r3)
                r-2[r-1] = r0
                jdk.nashorn.internal.runtime.ScriptFunction r-5 = jdk.nashorn.internal.runtime.ScriptFunction.createBuiltin(r-5, r-4, r-3)
                r-6.slice = r-5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "ArrayBuffer";
        }
    }

    static {
        $clinit$();
    }

    /*  JADX ERROR: Failed to decode insn: 0x000D: CONST, method: jdk.nashorn.internal.objects.NativeArrayBuffer.$clinit$():void
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
            java.lang.String r2 = "byteLength"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            jdk.nashorn.internal.runtime.AccessorProperty r0 = jdk.nashorn.internal.runtime.AccessorProperty.create(r0, r1, r2, r3)
            r-1.add(r0)
            jdk.nashorn.internal.runtime.PropertyMap r-2 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r-2)
            jdk.nashorn.internal.objects.NativeArrayBuffer.$nasgenmap$ = r-2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArrayBuffer.$clinit$():void");
    }

    protected NativeArrayBuffer(ByteBuffer nb, Global global) {
        super(global.getArrayBufferPrototype(), $nasgenmap$);
        this.f250nb = nb;
    }

    protected NativeArrayBuffer(ByteBuffer nb) {
        this(nb, Global.instance());
    }

    public NativeArrayBuffer(int byteLength) {
        this(ByteBuffer.allocateDirect(byteLength));
    }

    protected NativeArrayBuffer(NativeArrayBuffer other, int begin, int end) {
        this(cloneBuffer(other.getNioBuffer(), begin, end));
    }

    public static NativeArrayBuffer constructor(boolean newObj, Object self, Object... args) {
        if (!newObj) {
            throw ECMAErrors.typeError("constructor.requires.new", "ArrayBuffer");
        }
        if (args.length == 0) {
            return new NativeArrayBuffer(0);
        }
        return new NativeArrayBuffer(JSType.toInt32(args[0]));
    }

    private static ByteBuffer cloneBuffer(ByteBuffer original, int begin, int end) {
        ByteBuffer clone = ByteBuffer.allocateDirect(original.capacity());
        original.rewind();
        clone.put(original);
        original.rewind();
        clone.flip();
        clone.position(begin);
        clone.limit(end);
        return clone.slice();
    }

    public ByteBuffer getNioBuffer() {
        return this.f250nb;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "ArrayBuffer";
    }

    public static int byteLength(Object self) {
        return ((NativeArrayBuffer) self).getByteLength();
    }

    public static boolean isView(Object self, Object obj) {
        return obj instanceof ArrayBufferView;
    }

    public static NativeArrayBuffer slice(Object self, Object begin0, Object end0) {
        NativeArrayBuffer arrayBuffer = (NativeArrayBuffer) self;
        int byteLength = arrayBuffer.getByteLength();
        int begin = adjustIndex(JSType.toInt32(begin0), byteLength);
        int end = adjustIndex(end0 != ScriptRuntime.UNDEFINED ? JSType.toInt32(end0) : byteLength, byteLength);
        return new NativeArrayBuffer(arrayBuffer, begin, Math.max(end, begin));
    }

    public static Object slice(Object self, int begin, int end) {
        NativeArrayBuffer arrayBuffer = (NativeArrayBuffer) self;
        int byteLength = arrayBuffer.getByteLength();
        return new NativeArrayBuffer(arrayBuffer, adjustIndex(begin, byteLength), Math.max(adjustIndex(end, byteLength), begin));
    }

    public static Object slice(Object self, int begin) {
        return slice(self, begin, ((NativeArrayBuffer) self).getByteLength());
    }

    public static int adjustIndex(int index, int length) {
        return index < 0 ? clamp(index + length, length) : clamp(index, length);
    }

    private static int clamp(int index, int length) {
        if (index < 0) {
            return 0;
        }
        if (index > length) {
            return length;
        }
        return index;
    }

    public int getByteLength() {
        return this.f250nb.limit();
    }

    public ByteBuffer getBuffer() {
        return this.f250nb;
    }

    public ByteBuffer getBuffer(int offset) {
        return (ByteBuffer) this.f250nb.duplicate().position(offset);
    }

    public ByteBuffer getBuffer(int offset, int length) {
        return (ByteBuffer) getBuffer(offset).limit(length);
    }
}
