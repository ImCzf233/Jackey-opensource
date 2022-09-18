package jdk.nashorn.internal.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDataView.class */
public class NativeDataView extends ScriptObject {
    private static PropertyMap $nasgenmap$;
    public final Object buffer;
    public final int byteOffset;
    public final int byteLength;
    private final ByteBuffer buf;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDataView$Constructor.class */
    final class Constructor extends ScriptFunction {
        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeDataView.Constructor.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x000E: CONST, method: jdk.nashorn.internal.objects.NativeDataView.Constructor.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x001A: CONST, method: jdk.nashorn.internal.objects.NativeDataView.Constructor.<init>():void
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
                r11 = this;
                r0 = r11
                java.lang.String r1 = "DataView"
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
                r-6.<init>(r-5, r-4, r-3)
                r-6 = r11
                jdk.nashorn.internal.objects.NativeDataView$Prototype r-5 = new jdk.nashorn.internal.objects.NativeDataView$Prototype
                r-4 = r-5
                r-4.<init>()
                r-4 = r-5
                r-3 = r11
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-4, r-3)
                r-6.setPrototype(r-5)
                r-6 = r11
                r-5 = 1
                r-6.setArity(r-5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDataView.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDataView$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object getInt8;
        private Object getUint8;
        private Object getInt16;
        private Object getUint16;
        private Object getInt32;
        private Object getUint32;
        private Object getFloat32;
        private Object getFloat64;
        private Object setInt8;
        private Object setUint8;
        private Object setInt16;
        private Object setUint16;
        private Object setInt32;
        private Object setUint32;
        private Object setFloat32;
        private Object setFloat64;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$getInt8() {
            return this.getInt8;
        }

        public void S$getInt8(Object obj) {
            this.getInt8 = obj;
        }

        public Object G$getUint8() {
            return this.getUint8;
        }

        public void S$getUint8(Object obj) {
            this.getUint8 = obj;
        }

        public Object G$getInt16() {
            return this.getInt16;
        }

        public void S$getInt16(Object obj) {
            this.getInt16 = obj;
        }

        public Object G$getUint16() {
            return this.getUint16;
        }

        public void S$getUint16(Object obj) {
            this.getUint16 = obj;
        }

        public Object G$getInt32() {
            return this.getInt32;
        }

        public void S$getInt32(Object obj) {
            this.getInt32 = obj;
        }

        public Object G$getUint32() {
            return this.getUint32;
        }

        public void S$getUint32(Object obj) {
            this.getUint32 = obj;
        }

        public Object G$getFloat32() {
            return this.getFloat32;
        }

        public void S$getFloat32(Object obj) {
            this.getFloat32 = obj;
        }

        public Object G$getFloat64() {
            return this.getFloat64;
        }

        public void S$getFloat64(Object obj) {
            this.getFloat64 = obj;
        }

        public Object G$setInt8() {
            return this.setInt8;
        }

        public void S$setInt8(Object obj) {
            this.setInt8 = obj;
        }

        public Object G$setUint8() {
            return this.setUint8;
        }

        public void S$setUint8(Object obj) {
            this.setUint8 = obj;
        }

        public Object G$setInt16() {
            return this.setInt16;
        }

        public void S$setInt16(Object obj) {
            this.setInt16 = obj;
        }

        public Object G$setUint16() {
            return this.setUint16;
        }

        public void S$setUint16(Object obj) {
            this.setUint16 = obj;
        }

        public Object G$setInt32() {
            return this.setInt32;
        }

        public void S$setInt32(Object obj) {
            this.setInt32 = obj;
        }

        public Object G$setUint32() {
            return this.setUint32;
        }

        public void S$setUint32(Object obj) {
            this.setUint32 = obj;
        }

        public Object G$setFloat32() {
            return this.setFloat32;
        }

        public void S$setFloat32(Object obj) {
            this.setFloat32 = obj;
        }

        public Object G$setFloat64() {
            return this.setFloat64;
        }

        public void S$setFloat64(Object obj) {
            this.setFloat64 = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDataView.Prototype.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDataView$Prototype.class
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
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDataView.Prototype.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDataView$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDataView.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "DataView";
        }
    }

    static {
        $clinit$();
    }

    /*  JADX ERROR: Failed to decode insn: 0x000E: CONST, method: jdk.nashorn.internal.objects.NativeDataView.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x001F: CONST, method: jdk.nashorn.internal.objects.NativeDataView.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x0030: CONST, method: jdk.nashorn.internal.objects.NativeDataView.$clinit$():void
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
            r2 = 3
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "buffer"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r4 = 0
            r5 = r192
            r6 = 0
            r7 = r7
            r8 = -1
            r8 = r7
            java.lang.String r9 = "byteOffset"
            r10 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r11 = 0
            r12 = r192
            r13 = 0
            r14 = r7
            r15 = -1
            r15 = r14
            java.lang.String r16 = "byteLength"
            r17 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r18 = 0
            r19 = r192
            r20 = 0
            r21 = r7
            r22 = -1
            jdk.nashorn.internal.runtime.PropertyMap r21 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r21)
            jdk.nashorn.internal.objects.NativeDataView.$nasgenmap$ = r21
            return
            r21 = 0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDataView.$clinit$():void");
    }

    public Object G$buffer() {
        return this.buffer;
    }

    public int G$byteOffset() {
        return this.byteOffset;
    }

    public int G$byteLength() {
        return this.byteLength;
    }

    private NativeDataView(NativeArrayBuffer arrBuf) {
        this(arrBuf, arrBuf.getBuffer(), 0);
    }

    private NativeDataView(NativeArrayBuffer arrBuf, int offset) {
        this(arrBuf, bufferFrom(arrBuf, offset), offset);
    }

    private NativeDataView(NativeArrayBuffer arrBuf, int offset, int length) {
        this(arrBuf, bufferFrom(arrBuf, offset, length), offset, length);
    }

    private NativeDataView(NativeArrayBuffer arrBuf, ByteBuffer buf, int offset) {
        this(arrBuf, buf, offset, buf.capacity() - offset);
    }

    private NativeDataView(NativeArrayBuffer arrBuf, ByteBuffer buf, int offset, int length) {
        super(Global.instance().getDataViewPrototype(), $nasgenmap$);
        this.buffer = arrBuf;
        this.byteOffset = offset;
        this.byteLength = length;
        this.buf = buf;
    }

    public static NativeDataView constructor(boolean newObj, Object self, Object... args) {
        if (args.length == 0 || !(args[0] instanceof NativeArrayBuffer)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[0]);
        }
        NativeArrayBuffer arrBuf = (NativeArrayBuffer) args[0];
        switch (args.length) {
            case 1:
                return new NativeDataView(arrBuf);
            case 2:
                return new NativeDataView(arrBuf, JSType.toInt32(args[1]));
            default:
                return new NativeDataView(arrBuf, JSType.toInt32(args[1]), JSType.toInt32(args[2]));
        }
    }

    public static NativeDataView constructor(boolean newObj, Object self, Object arrBuf, int offset) {
        if (!(arrBuf instanceof NativeArrayBuffer)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[0]);
        }
        return new NativeDataView((NativeArrayBuffer) arrBuf, offset);
    }

    public static NativeDataView constructor(boolean newObj, Object self, Object arrBuf, int offset, int length) {
        if (!(arrBuf instanceof NativeArrayBuffer)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[0]);
        }
        return new NativeDataView((NativeArrayBuffer) arrBuf, offset, length);
    }

    public static int getInt8(Object self, Object byteOffset) {
        try {
            return getBuffer(self).get(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt8(Object self, int byteOffset) {
        try {
            return getBuffer(self).get(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getUint8(Object self, Object byteOffset) {
        try {
            return 255 & getBuffer(self).get(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getUint8(Object self, int byteOffset) {
        try {
            return 255 & getBuffer(self).get(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt16(Object self, Object byteOffset, Object littleEndian) {
        try {
            return getBuffer(self, littleEndian).getShort(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt16(Object self, int byteOffset) {
        try {
            return getBuffer(self, false).getShort(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt16(Object self, int byteOffset, boolean littleEndian) {
        try {
            return getBuffer(self, littleEndian).getShort(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getUint16(Object self, Object byteOffset, Object littleEndian) {
        try {
            return 65535 & getBuffer(self, littleEndian).getShort(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getUint16(Object self, int byteOffset) {
        try {
            return 65535 & getBuffer(self, false).getShort(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getUint16(Object self, int byteOffset, boolean littleEndian) {
        try {
            return 65535 & getBuffer(self, littleEndian).getShort(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt32(Object self, Object byteOffset, Object littleEndian) {
        try {
            return getBuffer(self, littleEndian).getInt(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt32(Object self, int byteOffset) {
        try {
            return getBuffer(self, false).getInt(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static int getInt32(Object self, int byteOffset, boolean littleEndian) {
        try {
            return getBuffer(self, littleEndian).getInt(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getUint32(Object self, Object byteOffset, Object littleEndian) {
        try {
            return JSType.MAX_UINT & getBuffer(self, littleEndian).getInt(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getUint32(Object self, int byteOffset) {
        try {
            return JSType.toUint32(getBuffer(self, false).getInt(JSType.toInt32(byteOffset)));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getUint32(Object self, int byteOffset, boolean littleEndian) {
        try {
            return JSType.toUint32(getBuffer(self, littleEndian).getInt(JSType.toInt32(byteOffset)));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat32(Object self, Object byteOffset, Object littleEndian) {
        try {
            return getBuffer(self, littleEndian).getFloat(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat32(Object self, int byteOffset) {
        try {
            return getBuffer(self, false).getFloat(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat32(Object self, int byteOffset, boolean littleEndian) {
        try {
            return getBuffer(self, littleEndian).getFloat(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat64(Object self, Object byteOffset, Object littleEndian) {
        try {
            return getBuffer(self, littleEndian).getDouble(JSType.toInt32(byteOffset));
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat64(Object self, int byteOffset) {
        try {
            return getBuffer(self, false).getDouble(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat64(Object self, int byteOffset, boolean littleEndian) {
        try {
            return getBuffer(self, littleEndian).getDouble(byteOffset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt8(Object self, Object byteOffset, Object value) {
        try {
            getBuffer(self).put(JSType.toInt32(byteOffset), (byte) JSType.toInt32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt8(Object self, int byteOffset, int value) {
        try {
            getBuffer(self).put(byteOffset, (byte) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint8(Object self, Object byteOffset, Object value) {
        try {
            getBuffer(self).put(JSType.toInt32(byteOffset), (byte) JSType.toInt32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint8(Object self, int byteOffset, int value) {
        try {
            getBuffer(self).put(byteOffset, (byte) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt16(Object self, Object byteOffset, Object value, Object littleEndian) {
        try {
            getBuffer(self, littleEndian).putShort(JSType.toInt32(byteOffset), (short) JSType.toInt32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt16(Object self, int byteOffset, int value) {
        try {
            getBuffer(self, false).putShort(byteOffset, (short) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt16(Object self, int byteOffset, int value, boolean littleEndian) {
        try {
            getBuffer(self, littleEndian).putShort(byteOffset, (short) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint16(Object self, Object byteOffset, Object value, Object littleEndian) {
        try {
            getBuffer(self, littleEndian).putShort(JSType.toInt32(byteOffset), (short) JSType.toInt32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint16(Object self, int byteOffset, int value) {
        try {
            getBuffer(self, false).putShort(byteOffset, (short) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint16(Object self, int byteOffset, int value, boolean littleEndian) {
        try {
            getBuffer(self, littleEndian).putShort(byteOffset, (short) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt32(Object self, Object byteOffset, Object value, Object littleEndian) {
        try {
            getBuffer(self, littleEndian).putInt(JSType.toInt32(byteOffset), JSType.toInt32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt32(Object self, int byteOffset, int value) {
        try {
            getBuffer(self, false).putInt(byteOffset, value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt32(Object self, int byteOffset, int value, boolean littleEndian) {
        try {
            getBuffer(self, littleEndian).putInt(byteOffset, value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint32(Object self, Object byteOffset, Object value, Object littleEndian) {
        try {
            getBuffer(self, littleEndian).putInt(JSType.toInt32(byteOffset), (int) JSType.toUint32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint32(Object self, int byteOffset, double value) {
        try {
            getBuffer(self, false).putInt(byteOffset, (int) JSType.toUint32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint32(Object self, int byteOffset, double value, boolean littleEndian) {
        try {
            getBuffer(self, littleEndian).putInt(byteOffset, (int) JSType.toUint32(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat32(Object self, Object byteOffset, Object value, Object littleEndian) {
        try {
            getBuffer(self, littleEndian).putFloat((int) JSType.toUint32(byteOffset), (float) JSType.toNumber(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat32(Object self, int byteOffset, double value) {
        try {
            getBuffer(self, false).putFloat(byteOffset, (float) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat32(Object self, int byteOffset, double value, boolean littleEndian) {
        try {
            getBuffer(self, littleEndian).putFloat(byteOffset, (float) value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat64(Object self, Object byteOffset, Object value, Object littleEndian) {
        try {
            getBuffer(self, littleEndian).putDouble((int) JSType.toUint32(byteOffset), JSType.toNumber(value));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat64(Object self, int byteOffset, double value) {
        try {
            getBuffer(self, false).putDouble(byteOffset, value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat64(Object self, int byteOffset, double value, boolean littleEndian) {
        try {
            getBuffer(self, littleEndian).putDouble(byteOffset, value);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.offset", new String[0]);
        }
    }

    private static ByteBuffer bufferFrom(NativeArrayBuffer nab, int offset) {
        try {
            return nab.getBuffer(offset);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.constructor.offset", new String[0]);
        }
    }

    private static ByteBuffer bufferFrom(NativeArrayBuffer nab, int offset, int length) {
        try {
            return nab.getBuffer(offset, length);
        } catch (IllegalArgumentException iae) {
            throw ECMAErrors.rangeError(iae, "dataview.constructor.offset", new String[0]);
        }
    }

    private static NativeDataView checkSelf(Object self) {
        if (!(self instanceof NativeDataView)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", ScriptRuntime.safeToString(self));
        }
        return (NativeDataView) self;
    }

    private static ByteBuffer getBuffer(Object self) {
        return checkSelf(self).buf;
    }

    private static ByteBuffer getBuffer(Object self, Object littleEndian) {
        return getBuffer(self, JSType.toBoolean(littleEndian));
    }

    private static ByteBuffer getBuffer(Object self, boolean littleEndian) {
        return getBuffer(self).order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
    }
}
