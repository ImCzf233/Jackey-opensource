package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
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
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint32Array.class */
public final class NativeUint32Array extends ArrayBufferView {
    public static final int BYTES_PER_ELEMENT = 4;
    private static PropertyMap $nasgenmap$;
    private static final ArrayBufferView.Factory FACTORY = null;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint32Array$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        public int G$BYTES_PER_ELEMENT() {
            return NativeUint32Array.BYTES_PER_ELEMENT;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeUint32Array.Constructor.<init>():void
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
                java.lang.String r1 = "Uint32Array"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r0 = r0[r1]
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                jdk.nashorn.internal.objects.NativeUint32Array$Prototype r-2 = new jdk.nashorn.internal.objects.NativeUint32Array$Prototype
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
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeUint32Array.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint32Array$Prototype.class */
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

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeUint32Array.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeUint32Array.Prototype.<init>():void
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
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeUint32Array.Prototype.$nasgenmap$
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
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeUint32Array.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Uint32Array";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        FACTORY = new ArrayBufferView.Factory(4) { // from class: jdk.nashorn.internal.objects.NativeUint32Array.1
            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public ArrayBufferView construct(NativeArrayBuffer buffer, int byteBegin, int length) {
                return new NativeUint32Array(buffer, byteBegin, length);
            }

            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public Uint32ArrayData createArrayData(ByteBuffer nb, int start, int end) {
                return new Uint32ArrayData(nb.order(ByteOrder.nativeOrder()).asIntBuffer(), start, end);
            }

            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public String getClassName() {
                return "Uint32Array";
            }
        };
        $clinit$();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeUint32Array$Uint32ArrayData.class */
    public static final class Uint32ArrayData extends TypedArrayData<IntBuffer> {
        private static final MethodHandle GET_ELEM = null;
        private static final MethodHandle SET_ELEM = null;

        static {
            GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint32ArrayData.class, "getElem", Double.TYPE, Integer.TYPE).methodHandle();
            SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint32ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
        }

        private Uint32ArrayData(IntBuffer nb, int start, int end) {
            super(((IntBuffer) nb.position(start).limit(end)).slice(), end - start);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        protected MethodHandle getGetElem() {
            return GET_ELEM;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        protected MethodHandle getSetElem() {
            return SET_ELEM;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData, jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
            if (returnType == Integer.TYPE) {
                return null;
            }
            return getContinuousElementGetter(getClass(), GET_ELEM, returnType, programPoint);
        }

        private int getRawElem(int index) {
            try {
                return ((IntBuffer) this.f275nb).get(index);
            } catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        private double getElem(int index) {
            return JSType.toUint32(getRawElem(index));
        }

        private void setElem(int index, int elem) {
            try {
                if (index < ((IntBuffer) this.f275nb).limit()) {
                    ((IntBuffer) this.f275nb).put(index, elem);
                }
            } catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        public boolean isUnsigned() {
            return true;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class<?> getElementType() {
            return Double.TYPE;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class<?> getBoxedElementType() {
            return Double.class;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public int getInt(int index) {
            return getRawElem(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public int getIntOptimistic(int index, int programPoint) {
            return JSType.toUint32Optimistic(getRawElem(index), programPoint);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDouble(int index) {
            return getElem(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDoubleOptimistic(int index, int programPoint) {
            return getElem(index);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object getObject(int index) {
            return Double.valueOf(getElem(index));
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int index, Object value, boolean strict) {
            return set(index, JSType.toInt32(value), strict);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int index, int value, boolean strict) {
            setElem(index, value);
            return this;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int index, double value, boolean strict) {
            return set(index, (int) value, strict);
        }
    }

    public static NativeUint32Array constructor(boolean newObj, Object self, Object... args) {
        return (NativeUint32Array) constructorImpl(newObj, args, FACTORY);
    }

    NativeUint32Array(NativeArrayBuffer buffer, int byteOffset, int length) {
        super(buffer, byteOffset, length);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ArrayBufferView.Factory factory() {
        return FACTORY;
    }

    protected static Object set(Object self, Object array, Object offset) {
        return ArrayBufferView.setImpl(self, array, offset);
    }

    protected static NativeUint32Array subarray(Object self, Object begin, Object end) {
        return (NativeUint32Array) ArrayBufferView.subarrayImpl(self, begin, end);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ScriptObject getPrototype(Global global) {
        return global.getUint32ArrayPrototype();
    }
}
