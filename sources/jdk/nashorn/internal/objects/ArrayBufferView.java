package jdk.nashorn.internal.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/ArrayBufferView.class */
public abstract class ArrayBufferView extends ScriptObject {
    private final NativeArrayBuffer buffer;
    private final int byteOffset;
    private static PropertyMap $nasgenmap$;
    static final /* synthetic */ boolean $assertionsDisabled;

    protected abstract Factory factory();

    protected abstract ScriptObject getPrototype(Global global);

    /*  JADX ERROR: Failed to decode insn: 0x000E: CONST, method: jdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x001F: CONST, method: jdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x0030: CONST, method: jdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void
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
    /*  JADX ERROR: Failed to decode insn: 0x0041: CONST, method: jdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void
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
            r2 = 4
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "buffer"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r4 = 0
            r5 = r3; r6 = r4; 
            boolean r5 = r5.add(r6)
            r5 = r4
            java.lang.String r6 = "byteOffset"
            r7 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r8 = 0
            r9 = r7; r10 = r8; 
            boolean r9 = r9.add(r10)
            r9 = r8
            java.lang.String r10 = "byteLength"
            r11 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r12 = 0
            r13 = r11; r14 = r12; 
            boolean r13 = r13.add(r14)
            r13 = r12
            java.lang.String r14 = "length"
            r15 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r16 = 0
            r17 = r15; r18 = r16; 
            boolean r17 = r17.add(r18)
            jdk.nashorn.internal.runtime.PropertyMap r16 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r16)
            jdk.nashorn.internal.objects.ArrayBufferView.$nasgenmap$ = r16
            return
            r16 = -1
            r17 = 0
            long r16 = r16 << r17
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void");
    }

    static {
        $assertionsDisabled = !ArrayBufferView.class.desiredAssertionStatus();
        $clinit$();
    }

    private ArrayBufferView(NativeArrayBuffer buffer, int byteOffset, int elementLength, Global global) {
        super($nasgenmap$);
        int bytesPerElement = bytesPerElement();
        checkConstructorArgs(buffer.getByteLength(), bytesPerElement, byteOffset, elementLength);
        setProto(getPrototype(global));
        this.buffer = buffer;
        this.byteOffset = byteOffset;
        if ($assertionsDisabled || byteOffset % bytesPerElement == 0) {
            int start = byteOffset / bytesPerElement;
            ByteBuffer newNioBuffer = buffer.getNioBuffer().duplicate().order(ByteOrder.nativeOrder());
            ArrayData data = factory().createArrayData(newNioBuffer, start, start + elementLength);
            setArray(data);
            return;
        }
        throw new AssertionError();
    }

    public ArrayBufferView(NativeArrayBuffer buffer, int byteOffset, int elementLength) {
        this(buffer, byteOffset, elementLength, Global.instance());
    }

    private static void checkConstructorArgs(int byteLength, int bytesPerElement, int byteOffset, int elementLength) {
        if (byteOffset < 0 || elementLength < 0) {
            throw new RuntimeException("byteOffset or length must not be negative, byteOffset=" + byteOffset + ", elementLength=" + elementLength + ", bytesPerElement=" + bytesPerElement);
        }
        if (byteOffset + (elementLength * bytesPerElement) > byteLength) {
            throw new RuntimeException("byteOffset + byteLength out of range, byteOffset=" + byteOffset + ", elementLength=" + elementLength + ", bytesPerElement=" + bytesPerElement);
        }
        if (byteOffset % bytesPerElement != 0) {
            throw new RuntimeException("byteOffset must be a multiple of the element size, byteOffset=" + byteOffset + " bytesPerElement=" + bytesPerElement);
        }
    }

    private int bytesPerElement() {
        return factory().bytesPerElement;
    }

    public static Object buffer(Object self) {
        return ((ArrayBufferView) self).buffer;
    }

    public static int byteOffset(Object self) {
        return ((ArrayBufferView) self).byteOffset;
    }

    public static int byteLength(Object self) {
        ArrayBufferView view = (ArrayBufferView) self;
        return ((TypedArrayData) view.getArray()).getElementLength() * view.bytesPerElement();
    }

    public static int length(Object self) {
        return ((ArrayBufferView) self).elementLength();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public final Object getLength() {
        return Integer.valueOf(elementLength());
    }

    private int elementLength() {
        return ((TypedArrayData) getArray()).getElementLength();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/ArrayBufferView$Factory.class */
    public static abstract class Factory {
        final int bytesPerElement;
        final int maxElementLength;

        public abstract ArrayBufferView construct(NativeArrayBuffer nativeArrayBuffer, int i, int i2);

        public abstract TypedArrayData<?> createArrayData(ByteBuffer byteBuffer, int i, int i2);

        public abstract String getClassName();

        public Factory(int bytesPerElement) {
            this.bytesPerElement = bytesPerElement;
            this.maxElementLength = Integer.MAX_VALUE / bytesPerElement;
        }

        public final ArrayBufferView construct(int elementLength) {
            if (elementLength > this.maxElementLength) {
                throw ECMAErrors.rangeError("inappropriate.array.buffer.length", JSType.toString(elementLength));
            }
            return construct(new NativeArrayBuffer(elementLength * this.bytesPerElement), 0, elementLength);
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public final String getClassName() {
        return factory().getClassName();
    }

    protected boolean isFloatArray() {
        return false;
    }

    public static ArrayBufferView constructorImpl(boolean newObj, Object[] args, Factory factory) {
        int length;
        ArrayBufferView dest;
        int length2;
        Object arg0 = args.length != 0 ? args[0] : 0;
        if (!newObj) {
            throw ECMAErrors.typeError("constructor.requires.new", factory.getClassName());
        }
        if (arg0 instanceof NativeArrayBuffer) {
            NativeArrayBuffer buffer = (NativeArrayBuffer) arg0;
            int byteOffset = args.length > 1 ? JSType.toInt32(args[1]) : 0;
            if (args.length > 2) {
                length2 = JSType.toInt32(args[2]);
            } else if ((buffer.getByteLength() - byteOffset) % factory.bytesPerElement != 0) {
                throw new RuntimeException("buffer.byteLength - byteOffset must be a multiple of the element size");
            } else {
                length2 = (buffer.getByteLength() - byteOffset) / factory.bytesPerElement;
            }
            return factory.construct(buffer, byteOffset, length2);
        }
        if (arg0 instanceof ArrayBufferView) {
            length = ((ArrayBufferView) arg0).elementLength();
            dest = factory.construct(length);
        } else if (arg0 instanceof NativeArray) {
            length = lengthToInt(((NativeArray) arg0).getArray().length());
            dest = factory.construct(length);
        } else {
            double dlen = JSType.toNumber(arg0);
            int length3 = lengthToInt(Double.isInfinite(dlen) ? 0L : JSType.toLong(dlen));
            return factory.construct(length3);
        }
        copyElements(dest, length, (ScriptObject) arg0, 0);
        return dest;
    }

    public static Object setImpl(Object self, Object array, Object offset0) {
        int length;
        ArrayBufferView dest = (ArrayBufferView) self;
        if (array instanceof ArrayBufferView) {
            length = ((ArrayBufferView) array).elementLength();
        } else if (array instanceof NativeArray) {
            length = (int) (((NativeArray) array).getArray().length() & 2147483647L);
        } else {
            throw new RuntimeException("argument is not of array type");
        }
        ScriptObject source = (ScriptObject) array;
        int offset = JSType.toInt32(offset0);
        if (dest.elementLength() < length + offset || offset < 0) {
            throw new RuntimeException("offset or array length out of bounds");
        }
        copyElements(dest, length, source, offset);
        return ScriptRuntime.UNDEFINED;
    }

    private static void copyElements(ArrayBufferView dest, int length, ScriptObject source, int offset) {
        if (!dest.isFloatArray()) {
            int i = 0;
            int j = offset;
            while (i < length) {
                dest.set(j, source.getInt(i, -1), 0);
                i++;
                j++;
            }
            return;
        }
        int i2 = 0;
        int j2 = offset;
        while (i2 < length) {
            dest.set(j2, source.getDouble(i2, -1), 0);
            i2++;
            j2++;
        }
    }

    private static int lengthToInt(long length) {
        if (length > 2147483647L || length < 0) {
            throw ECMAErrors.rangeError("inappropriate.array.buffer.length", JSType.toString(length));
        }
        return (int) (length & 2147483647L);
    }

    public static ScriptObject subarrayImpl(Object self, Object begin0, Object end0) {
        ArrayBufferView arrayView = (ArrayBufferView) self;
        int byteOffset = arrayView.byteOffset;
        int bytesPerElement = arrayView.bytesPerElement();
        int elementLength = arrayView.elementLength();
        int begin = NativeArrayBuffer.adjustIndex(JSType.toInt32(begin0), elementLength);
        int end = NativeArrayBuffer.adjustIndex(end0 != ScriptRuntime.UNDEFINED ? JSType.toInt32(end0) : elementLength, elementLength);
        int length = Math.max(end - begin, 0);
        if ($assertionsDisabled || byteOffset % bytesPerElement == 0) {
            return arrayView.factory().construct(arrayView.buffer, (begin * bytesPerElement) + byteOffset, length);
        }
        throw new AssertionError();
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
}
