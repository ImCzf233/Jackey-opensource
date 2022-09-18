package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.ArrayBufferView;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint8ClampedArray.class */
public final class NativeUint8ClampedArray extends ArrayBufferView {
    public static final int BYTES_PER_ELEMENT = 1;
    private static PropertyMap $nasgenmap$;
    private static final ArrayBufferView.Factory FACTORY = null;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint8ClampedArray$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        public int G$BYTES_PER_ELEMENT() {
            return NativeUint8ClampedArray.BYTES_PER_ELEMENT;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeUint8ClampedArray.Constructor.<init>():void
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
                r6 = this;
                r0 = r6
                java.lang.String r1 = "Uint8ClampedArray"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r0 = r0[r1]
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                jdk.nashorn.internal.objects.NativeUint8ClampedArray$Prototype r-2 = new jdk.nashorn.internal.objects.NativeUint8ClampedArray$Prototype
                r-1 = r-2
                r-1.<init>()
                r-1 = r-2
                r0 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-1, r0)
                r-3.setPrototype(r-2)
                r-3 = r6
                r-2 = 1
                r-3.setArity(r-2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeUint8ClampedArray.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint8ClampedArray$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object set;
        private Object subarray;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$set() {
            return this.set;
        }

        public void S$set(Object obj) {
            this.set = obj;
        }

        public Object G$subarray() {
            return this.subarray;
        }

        public void S$subarray(Object obj) {
            this.subarray = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeUint8ClampedArray.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeUint8ClampedArray.Prototype.<init>():void
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
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeUint8ClampedArray.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "set"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r1
                r-1.set = r0
                r-1 = r4
                java.lang.String r0 = "subarray"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r0
                r-2.subarray = r-1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeUint8ClampedArray.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Uint8ClampedArray";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        FACTORY = new ArrayBufferView.Factory(1) { // from class: jdk.nashorn.internal.objects.NativeUint8ClampedArray.1
            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public ArrayBufferView construct(NativeArrayBuffer buffer, int byteOffset, int length) {
                return new NativeUint8ClampedArray(buffer, byteOffset, length);
            }

            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public Uint8ClampedArrayData createArrayData(ByteBuffer nb, int start, int end) {
                return new Uint8ClampedArrayData(nb, start, end);
            }

            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public String getClassName() {
                return "Uint8ClampedArray";
            }
        };
        $clinit$();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint8ClampedArray$Uint8ClampedArrayData.class */
    public static final class Uint8ClampedArrayData extends TypedArrayData<ByteBuffer> {
        private static final MethodHandle GET_ELEM = null;
        private static final MethodHandle SET_ELEM = null;
        private static final MethodHandle RINT_D = null;
        private static final MethodHandle RINT_O = null;
        private static final MethodHandle CLAMP_LONG = null;

        static {
            GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint8ClampedArrayData.class, "getElem", Integer.TYPE, Integer.TYPE).methodHandle();
            SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint8ClampedArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
            RINT_D = CompilerConstants.staticCall(MethodHandles.lookup(), Uint8ClampedArrayData.class, "rint", Double.TYPE, Double.TYPE).methodHandle();
            RINT_O = CompilerConstants.staticCall(MethodHandles.lookup(), Uint8ClampedArrayData.class, "rint", Object.class, Object.class).methodHandle();
            CLAMP_LONG = CompilerConstants.staticCall(MethodHandles.lookup(), Uint8ClampedArrayData.class, "clampLong", Long.TYPE, Long.TYPE).methodHandle();
        }

        private Uint8ClampedArrayData(ByteBuffer nb, int start, int end) {
            super(((ByteBuffer) nb.position(start).limit(end)).slice(), end - start);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        protected MethodHandle getGetElem() {
            return GET_ELEM;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        protected MethodHandle getSetElem() {
            return SET_ELEM;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class<?> getElementType() {
            return Integer.TYPE;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class<?> getBoxedElementType() {
            return Integer.TYPE;
        }

        private int getElem(int index) {
            try {
                return ((ByteBuffer) this.f275nb).get(index) & 255;
            } catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData, jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public MethodHandle getElementSetter(Class<?> elementType) {
            MethodHandle setter = super.getElementSetter(elementType);
            if (setter != null) {
                if (elementType == Object.class) {
                    return Lookup.f248MH.filterArguments(setter, 2, RINT_O);
                }
                if (elementType == Double.TYPE) {
                    return Lookup.f248MH.filterArguments(setter, 2, RINT_D);
                }
                if (elementType == Long.TYPE) {
                    return Lookup.f248MH.filterArguments(setter, 2, CLAMP_LONG);
                }
            }
            return setter;
        }

        private void setElem(int index, int elem) {
            byte clamped;
            try {
                if (index < ((ByteBuffer) this.f275nb).limit()) {
                    if ((elem & (-256)) == 0) {
                        clamped = (byte) elem;
                    } else {
                        clamped = elem < 0 ? (byte) 0 : (byte) -1;
                    }
                    ((ByteBuffer) this.f275nb).put(index, clamped);
                }
            } catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        public boolean isClamped() {
            return true;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        public boolean isUnsigned() {
            return true;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public int getInt(int index) {
            return getElem(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public int getIntOptimistic(int index, int programPoint) {
            return getElem(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDouble(int index) {
            return getInt(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDoubleOptimistic(int index, int programPoint) {
            return getElem(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object getObject(int index) {
            return Integer.valueOf(getInt(index));
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int index, Object value, boolean strict) {
            return set(index, JSType.toNumber(value), strict);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int index, int value, boolean strict) {
            setElem(index, value);
            return this;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int index, double value, boolean strict) {
            return set(index, rint(value), strict);
        }

        private static double rint(double rint) {
            return (int) Math.rint(rint);
        }

        private static Object rint(Object rint) {
            return Double.valueOf(rint(JSType.toNumber(rint)));
        }

        private static long clampLong(long l) {
            if (l < 0) {
                return 0L;
            }
            if (l > 255) {
                return 255L;
            }
            return l;
        }
    }

    public static NativeUint8ClampedArray constructor(boolean newObj, Object self, Object... args) {
        return (NativeUint8ClampedArray) constructorImpl(newObj, args, FACTORY);
    }

    NativeUint8ClampedArray(NativeArrayBuffer buffer, int byteOffset, int length) {
        super(buffer, byteOffset, length);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ArrayBufferView.Factory factory() {
        return FACTORY;
    }

    protected static Object set(Object self, Object array, Object offset) {
        return ArrayBufferView.setImpl(self, array, offset);
    }

    protected static NativeUint8ClampedArray subarray(Object self, Object begin, Object end) {
        return (NativeUint8ClampedArray) ArrayBufferView.subarrayImpl(self, begin, end);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ScriptObject getPrototype(Global global) {
        return global.getUint8ClampedArrayPrototype();
    }
}
