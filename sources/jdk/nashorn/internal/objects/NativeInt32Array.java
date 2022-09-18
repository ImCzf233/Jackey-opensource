package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
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
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeInt32Array.class */
public final class NativeInt32Array extends ArrayBufferView {
    public static final int BYTES_PER_ELEMENT = 4;
    private static PropertyMap $nasgenmap$;
    private static final ArrayBufferView.Factory FACTORY = null;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeInt32Array$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        public int G$BYTES_PER_ELEMENT() {
            return NativeInt32Array.BYTES_PER_ELEMENT;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeInt32Array.Constructor.<init>():void
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
                java.lang.String r1 = "Int32Array"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r0 = r0[r1]
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                jdk.nashorn.internal.objects.NativeInt32Array$Prototype r-2 = new jdk.nashorn.internal.objects.NativeInt32Array$Prototype
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
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeInt32Array.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeInt32Array$Prototype.class */
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

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeInt32Array.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeInt32Array.Prototype.<init>():void
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
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeInt32Array.Prototype.$nasgenmap$
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
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeInt32Array.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Int32Array";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        FACTORY = new ArrayBufferView.Factory(4) { // from class: jdk.nashorn.internal.objects.NativeInt32Array.1
            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public ArrayBufferView construct(NativeArrayBuffer buffer, int byteOffset, int length) {
                return new NativeInt32Array(buffer, byteOffset, length);
            }

            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public Int32ArrayData createArrayData(ByteBuffer nb, int start, int length) {
                return new Int32ArrayData(nb.asIntBuffer(), start, length);
            }

            @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
            public String getClassName() {
                return "Int32Array";
            }
        };
        $clinit$();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeInt32Array$Int32ArrayData.class */
    public static final class Int32ArrayData extends TypedArrayData<IntBuffer> {
        private static final MethodHandle GET_ELEM = null;
        private static final MethodHandle SET_ELEM = null;

        static {
            GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Int32ArrayData.class, "getElem", Integer.TYPE, Integer.TYPE).methodHandle();
            SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Int32ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
        }

        private Int32ArrayData(IntBuffer nb, int start, int end) {
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

        private int getElem(int index) {
            try {
                return ((IntBuffer) this.f275nb).get(index);
            } catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
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

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class<?> getElementType() {
            return Integer.TYPE;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class<?> getBoxedElementType() {
            return Integer.class;
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

    public static NativeInt32Array constructor(boolean newObj, Object self, Object... args) {
        return (NativeInt32Array) constructorImpl(newObj, args, FACTORY);
    }

    NativeInt32Array(NativeArrayBuffer buffer, int byteOffset, int length) {
        super(buffer, byteOffset, length);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ArrayBufferView.Factory factory() {
        return FACTORY;
    }

    protected static Object set(Object self, Object array, Object offset) {
        return ArrayBufferView.setImpl(self, array, offset);
    }

    protected static NativeInt32Array subarray(Object self, Object begin, Object end) {
        return (NativeInt32Array) ArrayBufferView.subarrayImpl(self, begin, end);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ScriptObject getPrototype(Global global) {
        return global.getInt32ArrayPrototype();
    }
}
