package jdk.nashorn.internal.codegen.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/Type.class */
public abstract class Type implements Comparable<Type>, BytecodeOps, Serializable {
    private static final long serialVersionUID = 1;
    private final transient String name;
    private final transient String descriptor;
    private final transient int weight;
    private final transient int slots;
    private final Class<?> clazz;
    private static final Map<Class<?>, jdk.internal.org.objectweb.asm.Type> INTERNAL_TYPE_CACHE;
    private final transient jdk.internal.org.objectweb.asm.Type internalType;
    protected static final int MIN_WEIGHT = -1;
    protected static final int MAX_WEIGHT = 20;
    static final CompilerConstants.Call BOOTSTRAP;
    static final Handle MATHBOOTSTRAP;
    private static final ConcurrentMap<Class<?>, Type> cache;
    public static final Type BOOLEAN;
    public static final BitwiseType INT;
    public static final NumericType NUMBER;
    public static final Type LONG;
    public static final Type STRING;
    public static final Type CHARSEQUENCE;
    public static final Type OBJECT;
    public static final Type UNDEFINED;
    public static final Type SCRIPT_OBJECT;
    public static final ArrayType INT_ARRAY;
    public static final ArrayType LONG_ARRAY;
    public static final ArrayType NUMBER_ARRAY;
    public static final ArrayType OBJECT_ARRAY;
    public static final Type THIS;
    public static final Type SCOPE;
    public static final Type UNKNOWN;
    public static final Type SLOT_2;
    static final /* synthetic */ boolean $assertionsDisabled;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/Type$Unknown.class */
    private interface Unknown {
    }

    public abstract char getBytecodeStackType();

    static {
        $assertionsDisabled = !Type.class.desiredAssertionStatus();
        INTERNAL_TYPE_CACHE = Collections.synchronizedMap(new WeakHashMap());
        BOOTSTRAP = CompilerConstants.staticCallNoLookup(Bootstrap.class, "mathBootstrap", CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Integer.TYPE);
        MATHBOOTSTRAP = new Handle(6, BOOTSTRAP.className(), "mathBootstrap", BOOTSTRAP.descriptor());
        cache = new ConcurrentHashMap();
        BOOLEAN = putInCache(new BooleanType());
        INT = (BitwiseType) putInCache(new IntType());
        NUMBER = (NumericType) putInCache(new NumberType());
        LONG = putInCache(new LongType());
        STRING = putInCache(new ObjectType(String.class));
        CHARSEQUENCE = putInCache(new ObjectType(CharSequence.class));
        OBJECT = putInCache(new ObjectType());
        UNDEFINED = putInCache(new ObjectType(Undefined.class));
        SCRIPT_OBJECT = putInCache(new ObjectType(ScriptObject.class));
        INT_ARRAY = (ArrayType) putInCache(new ArrayType(int[].class) { // from class: jdk.nashorn.internal.codegen.types.Type.1
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public void astore(MethodVisitor method) {
                method.visitInsn(79);
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public Type aload(MethodVisitor method) {
                method.visitInsn(46);
                return INT;
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public Type newarray(MethodVisitor method) {
                method.visitIntInsn(188, 10);
                return this;
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType
            public Type getElementType() {
                return INT;
            }
        });
        LONG_ARRAY = (ArrayType) putInCache(new ArrayType(long[].class) { // from class: jdk.nashorn.internal.codegen.types.Type.2
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public void astore(MethodVisitor method) {
                method.visitInsn(80);
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public Type aload(MethodVisitor method) {
                method.visitInsn(47);
                return LONG;
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public Type newarray(MethodVisitor method) {
                method.visitIntInsn(188, 11);
                return this;
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType
            public Type getElementType() {
                return LONG;
            }
        });
        NUMBER_ARRAY = (ArrayType) putInCache(new ArrayType(double[].class) { // from class: jdk.nashorn.internal.codegen.types.Type.3
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public void astore(MethodVisitor method) {
                method.visitInsn(82);
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public Type aload(MethodVisitor method) {
                method.visitInsn(49);
                return NUMBER;
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType, jdk.nashorn.internal.codegen.types.BytecodeArrayOps
            public Type newarray(MethodVisitor method) {
                method.visitIntInsn(188, 7);
                return this;
            }

            @Override // jdk.nashorn.internal.codegen.types.ArrayType
            public Type getElementType() {
                return NUMBER;
            }
        });
        OBJECT_ARRAY = (ArrayType) putInCache(new ArrayType(Object[].class));
        THIS = new ObjectType() { // from class: jdk.nashorn.internal.codegen.types.Type.4
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.Type
            public String toString() {
                return "this";
            }
        };
        SCOPE = new ObjectType() { // from class: jdk.nashorn.internal.codegen.types.Type.5
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.Type
            public String toString() {
                return "scope";
            }
        };
        UNKNOWN = new ValueLessType("<unknown>") { // from class: jdk.nashorn.internal.codegen.types.Type.6
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.Type
            public String getDescriptor() {
                return "<unknown>";
            }

            @Override // jdk.nashorn.internal.codegen.types.Type
            public char getBytecodeStackType() {
                return 'U';
            }
        };
        SLOT_2 = new ValueLessType("<slot_2>") { // from class: jdk.nashorn.internal.codegen.types.Type.7
            private static final long serialVersionUID = 1;

            @Override // jdk.nashorn.internal.codegen.types.Type
            public String getDescriptor() {
                return "<slot_2>";
            }

            @Override // jdk.nashorn.internal.codegen.types.Type
            public char getBytecodeStackType() {
                throw new UnsupportedOperationException("getBytecodeStackType");
            }
        };
    }

    public Type(String name, Class<?> clazz, int weight, int slots) {
        this.name = name;
        this.clazz = clazz;
        this.descriptor = jdk.internal.org.objectweb.asm.Type.getDescriptor(clazz);
        this.weight = weight;
        if ($assertionsDisabled || (weight >= -1 && weight <= 20)) {
            this.slots = slots;
            this.internalType = getInternalType(clazz);
            return;
        }
        throw new AssertionError("illegal type weight: " + weight);
    }

    public int getWeight() {
        return this.weight;
    }

    public Class<?> getTypeClass() {
        return this.clazz;
    }

    public Type nextWider() {
        return null;
    }

    public Class<?> getBoxedType() {
        if ($assertionsDisabled || !getTypeClass().isPrimitive()) {
            return null;
        }
        throw new AssertionError();
    }

    public static String getMethodDescriptor(Type returnType, Type... types) {
        jdk.internal.org.objectweb.asm.Type[] itypes = new jdk.internal.org.objectweb.asm.Type[types.length];
        for (int i = 0; i < types.length; i++) {
            itypes[i] = types[i].getInternalType();
        }
        return jdk.internal.org.objectweb.asm.Type.getMethodDescriptor(returnType.getInternalType(), itypes);
    }

    public static String getMethodDescriptor(Class<?> returnType, Class<?>... types) {
        jdk.internal.org.objectweb.asm.Type[] itypes = new jdk.internal.org.objectweb.asm.Type[types.length];
        for (int i = 0; i < types.length; i++) {
            itypes[i] = getInternalType(types[i]);
        }
        return jdk.internal.org.objectweb.asm.Type.getMethodDescriptor(getInternalType(returnType), itypes);
    }

    public static char getShortSignatureDescriptor(Type type) {
        if (type instanceof BooleanType) {
            return 'Z';
        }
        return type.getBytecodeStackType();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static Type typeFor(jdk.internal.org.objectweb.asm.Type itype) {
        switch (itype.getSort()) {
            case 0:
                return null;
            case 1:
                return BOOLEAN;
            case 2:
            case 3:
            case 4:
            case 6:
            default:
                if ($assertionsDisabled) {
                    return null;
                }
                throw new AssertionError("Unknown itype : " + itype + " sort " + itype.getSort());
            case 5:
                return INT;
            case 7:
                return LONG;
            case 8:
                return NUMBER;
            case 9:
                switch (itype.getElementType().getSort()) {
                    case 5:
                        return INT_ARRAY;
                    case 6:
                    case 9:
                    default:
                        if (!$assertionsDisabled) {
                            throw new AssertionError();
                        }
                        break;
                    case 7:
                        return LONG_ARRAY;
                    case 8:
                        return NUMBER_ARRAY;
                    case 10:
                        break;
                }
                return OBJECT_ARRAY;
            case 10:
                if (Context.isStructureClass(itype.getClassName())) {
                    return SCRIPT_OBJECT;
                }
                try {
                    return typeFor(Class.forName(itype.getClassName()));
                } catch (ClassNotFoundException e) {
                    throw new AssertionError(e);
                }
        }
    }

    public static Type getMethodReturnType(String methodDescriptor) {
        return typeFor(jdk.internal.org.objectweb.asm.Type.getReturnType(methodDescriptor));
    }

    public static Type[] getMethodArguments(String methodDescriptor) {
        jdk.internal.org.objectweb.asm.Type[] itypes = jdk.internal.org.objectweb.asm.Type.getArgumentTypes(methodDescriptor);
        Type[] types = new Type[itypes.length];
        for (int i = 0; i < itypes.length; i++) {
            types[i] = typeFor(itypes[i]);
        }
        return types;
    }

    public static void writeTypeMap(Map<Integer, Type> typeMap, DataOutput output) throws IOException {
        byte b;
        if (typeMap == null) {
            output.writeInt(0);
            return;
        }
        output.writeInt(typeMap.size());
        for (Map.Entry<Integer, Type> e : typeMap.entrySet()) {
            output.writeInt(e.getKey().intValue());
            Type type = e.getValue();
            if (type == OBJECT) {
                b = 76;
            } else if (type == NUMBER) {
                b = 68;
            } else if (type == LONG) {
                b = 74;
            } else {
                throw new AssertionError();
            }
            byte typeChar = b;
            output.writeByte(typeChar);
        }
    }

    public static Map<Integer, Type> readTypeMap(DataInput input) throws IOException {
        Type type;
        int size = input.readInt();
        if (size <= 0) {
            return null;
        }
        Map<Integer, Type> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            int pp = input.readInt();
            int typeChar = input.readByte();
            switch (typeChar) {
                case 68:
                    type = NUMBER;
                    break;
                case 74:
                    type = LONG;
                    break;
                case 76:
                    type = OBJECT;
                    break;
                default:
            }
            map.put(Integer.valueOf(pp), type);
        }
        return map;
    }

    static jdk.internal.org.objectweb.asm.Type getInternalType(String className) {
        return jdk.internal.org.objectweb.asm.Type.getType(className);
    }

    private jdk.internal.org.objectweb.asm.Type getInternalType() {
        return this.internalType;
    }

    private static jdk.internal.org.objectweb.asm.Type lookupInternalType(Class<?> type) {
        Map<Class<?>, jdk.internal.org.objectweb.asm.Type> c = INTERNAL_TYPE_CACHE;
        jdk.internal.org.objectweb.asm.Type itype = c.get(type);
        if (itype != null) {
            return itype;
        }
        jdk.internal.org.objectweb.asm.Type itype2 = jdk.internal.org.objectweb.asm.Type.getType(type);
        c.put(type, itype2);
        return itype2;
    }

    private static jdk.internal.org.objectweb.asm.Type getInternalType(Class<?> type) {
        return lookupInternalType(type);
    }

    public static void invokestatic(MethodVisitor method, CompilerConstants.Call call) {
        method.visitMethodInsn(184, call.className(), call.name(), call.descriptor(), false);
    }

    public String getInternalName() {
        return jdk.internal.org.objectweb.asm.Type.getInternalName(getTypeClass());
    }

    public static String getInternalName(Class<?> clazz) {
        return jdk.internal.org.objectweb.asm.Type.getInternalName(clazz);
    }

    public boolean isUnknown() {
        return equals(UNKNOWN);
    }

    public boolean isJSPrimitive() {
        return !isObject() || isString();
    }

    public boolean isBoolean() {
        return equals(BOOLEAN);
    }

    public boolean isInteger() {
        return equals(INT);
    }

    public boolean isLong() {
        return equals(LONG);
    }

    public boolean isNumber() {
        return equals(NUMBER);
    }

    public boolean isNumeric() {
        return this instanceof NumericType;
    }

    public boolean isArray() {
        return this instanceof ArrayType;
    }

    public boolean isCategory2() {
        return getSlots() == 2;
    }

    public boolean isObject() {
        return this instanceof ObjectType;
    }

    public boolean isPrimitive() {
        return !isObject();
    }

    public boolean isString() {
        return equals(STRING);
    }

    public boolean isCharSequence() {
        return equals(CHARSEQUENCE);
    }

    public boolean isEquivalentTo(Type type) {
        return weight() == type.weight() || (isObject() && type.isObject());
    }

    public static boolean isAssignableFrom(Type type0, Type type1) {
        return (!type0.isObject() || !type1.isObject()) ? type0.weight() == type1.weight() : type0.weight() >= type1.weight();
    }

    public boolean isAssignableFrom(Type type) {
        return isAssignableFrom(this, type);
    }

    public static boolean areEquivalent(Type type0, Type type1) {
        return type0.isEquivalentTo(type1);
    }

    public int getSlots() {
        return this.slots;
    }

    public static Type widest(Type type0, Type type1) {
        if (type0.isArray() && type1.isArray()) {
            return ((ArrayType) type0).getElementType() == ((ArrayType) type1).getElementType() ? type0 : OBJECT;
        } else if (type0.isArray() != type1.isArray()) {
            return OBJECT;
        } else {
            if (type0.isObject() && type1.isObject() && type0.getTypeClass() != type1.getTypeClass()) {
                return OBJECT;
            }
            return type0.weight() > type1.weight() ? type0 : type1;
        }
    }

    public static Class<?> widest(Class<?> type0, Class<?> type1) {
        return widest(typeFor(type0), typeFor(type1)).getTypeClass();
    }

    public static Type widestReturnType(Type t1, Type t2) {
        if (t1.isUnknown()) {
            return t2;
        }
        if (t2.isUnknown()) {
            return t1;
        }
        if (t1.isBoolean() != t2.isBoolean() || t1.isNumeric() != t2.isNumeric()) {
            return OBJECT;
        }
        return widest(t1, t2);
    }

    public static Type generic(Type type) {
        return type.isObject() ? OBJECT : type;
    }

    public static Type narrowest(Type type0, Type type1) {
        return type0.narrowerThan(type1) ? type0 : type1;
    }

    public boolean narrowerThan(Type type) {
        return weight() < type.weight();
    }

    public boolean widerThan(Type type) {
        return weight() > type.weight();
    }

    public static Type widest(Type type0, Type type1, Type limit) {
        Type type = widest(type0, type1);
        if (type.weight() > limit.weight()) {
            return limit;
        }
        return type;
    }

    public static Type narrowest(Type type0, Type type1, Type limit) {
        Type type = type0.weight() < type1.weight() ? type0 : type1;
        if (type.weight() < limit.weight()) {
            return limit;
        }
        return type;
    }

    public Type narrowest(Type other) {
        return narrowest(this, other);
    }

    public Type widest(Type other) {
        return widest(this, other);
    }

    int weight() {
        return this.weight;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public String getShortDescriptor() {
        return this.descriptor;
    }

    public String toString() {
        return this.name;
    }

    public static Type typeFor(Class<?> clazz) {
        Type newType;
        Type type = cache.get(clazz);
        if (type != null) {
            return type;
        }
        if (!$assertionsDisabled && clazz.isPrimitive() && clazz != Void.TYPE) {
            throw new AssertionError();
        }
        if (clazz.isArray()) {
            newType = new ArrayType(clazz);
        } else {
            newType = new ObjectType(clazz);
        }
        Type existingType = cache.putIfAbsent(clazz, newType);
        return existingType == null ? newType : existingType;
    }

    public int compareTo(Type o) {
        return o.weight() - weight();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type dup(MethodVisitor method, int depth) {
        return dup(method, this, depth);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type swap(MethodVisitor method, Type other) {
        swap(method, this, other);
        return other;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type pop(MethodVisitor method) {
        pop(method, this);
        return this;
    }

    public Type loadEmpty(MethodVisitor method) {
        if (!$assertionsDisabled) {
            throw new AssertionError("unsupported operation");
        }
        return null;
    }

    protected static void pop(MethodVisitor method, Type type) {
        method.visitInsn(type.isCategory2() ? 88 : 87);
    }

    private static Type dup(MethodVisitor method, Type type, int depth) {
        boolean cat2 = type.isCategory2();
        switch (depth) {
            case 0:
                method.visitInsn(cat2 ? 92 : 89);
                break;
            case 1:
                method.visitInsn(cat2 ? 93 : 90);
                break;
            case 2:
                method.visitInsn(cat2 ? 94 : 91);
                break;
            default:
                return null;
        }
        return type;
    }

    private static void swap(MethodVisitor method, Type above, Type below) {
        if (below.isCategory2()) {
            if (above.isCategory2()) {
                method.visitInsn(94);
                method.visitInsn(88);
                return;
            }
            method.visitInsn(91);
            method.visitInsn(87);
        } else if (above.isCategory2()) {
            method.visitInsn(93);
            method.visitInsn(88);
        } else {
            method.visitInsn(95);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/Type$ValueLessType.class */
    public static abstract class ValueLessType extends Type {
        private static final long serialVersionUID = 1;

        @Override // jdk.nashorn.internal.codegen.types.Type, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Type type) {
            return super.compareTo(type);
        }

        ValueLessType(String name) {
            super(name, Unknown.class, -1, 1);
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public Type load(MethodVisitor method, int slot) {
            throw new UnsupportedOperationException("load " + slot);
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public void store(MethodVisitor method, int slot) {
            throw new UnsupportedOperationException("store " + slot);
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public Type ldc(MethodVisitor method, Object c) {
            throw new UnsupportedOperationException("ldc " + c);
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public Type loadUndefined(MethodVisitor method) {
            throw new UnsupportedOperationException("load undefined");
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public Type loadForcedInitializer(MethodVisitor method) {
            throw new UnsupportedOperationException("load forced initializer");
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public Type convert(MethodVisitor method, Type to) {
            throw new UnsupportedOperationException("convert => " + to);
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public void _return(MethodVisitor method) {
            throw new UnsupportedOperationException("return");
        }

        @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
        public Type add(MethodVisitor method, int programPoint) {
            throw new UnsupportedOperationException("add");
        }
    }

    private static <T extends Type> T putInCache(T type) {
        cache.put(type.getTypeClass(), type);
        return type;
    }

    protected final Object readResolve() {
        return typeFor(this.clazz);
    }
}
