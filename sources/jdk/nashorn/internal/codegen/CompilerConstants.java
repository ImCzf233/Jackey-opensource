package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilerConstants.class */
public enum CompilerConstants {
    __FILE__,
    __DIR__,
    __LINE__,
    INIT("<init>"),
    CLINIT("<clinit>"),
    EVAL("eval"),
    SOURCE("source", Source.class),
    CONSTANTS("constants", Object[].class),
    STRICT_MODE("strictMode", Boolean.TYPE),
    DEFAULT_SCRIPT_NAME("Script"),
    ANON_FUNCTION_PREFIX("L:"),
    NESTED_FUNCTION_SEPARATOR("#"),
    ID_FUNCTION_SEPARATOR("-"),
    PROGRAM(":program"),
    CREATE_PROGRAM_FUNCTION(":createProgramFunction"),
    THIS("this", Object.class),
    THIS_DEBUGGER(":this"),
    SCOPE(":scope", ScriptObject.class, 2),
    RETURN(":return"),
    CALLEE(":callee", ScriptFunction.class),
    VARARGS(":varargs", Object[].class),
    ARGUMENTS_VAR("arguments", Object.class),
    ARGUMENTS(":arguments", ScriptObject.class),
    EXPLODED_ARGUMENT_PREFIX(":xarg"),
    ITERATOR_PREFIX(":i", Iterator.class),
    SWITCH_TAG_PREFIX(":s"),
    EXCEPTION_PREFIX(":e", Throwable.class),
    QUICK_PREFIX(":q"),
    TEMP_PREFIX(":t"),
    LITERAL_PREFIX(":l"),
    REGEX_PREFIX(":r"),
    JAVA_THIS((String) null, 0),
    INIT_MAP((String) null, 1),
    INIT_SCOPE((String) null, 2),
    INIT_ARGUMENTS((String) null, 3),
    JS_OBJECT_DUAL_FIELD_PREFIX("JD"),
    JS_OBJECT_SINGLE_FIELD_PREFIX("JO"),
    ALLOCATE("allocate"),
    SPLIT_PREFIX(":split"),
    SPLIT_ARRAY_ARG(":split_array", 3),
    GET_STRING(":getString"),
    GET_MAP(":getMap"),
    SET_MAP(":setMap"),
    GET_ARRAY_PREFIX(":get"),
    GET_ARRAY_SUFFIX("$array");
    
    private static Set<String> symbolNames;
    private static final String INTERNAL_METHOD_PREFIX = ":";
    private final String symbolName;
    private final Class<?> type;
    private final int slot;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        CompilerConstants[] values;
        $assertionsDisabled = !CompilerConstants.class.desiredAssertionStatus();
        for (CompilerConstants c : values()) {
            String symbolName = c.symbolName();
            if (symbolName != null) {
                symbolName.intern();
            }
        }
    }

    CompilerConstants() {
        this.symbolName = name();
        this.type = null;
        this.slot = -1;
    }

    CompilerConstants(String symbolName) {
        this(symbolName, -1);
    }

    CompilerConstants(String symbolName, int slot) {
        this(symbolName, null, slot);
    }

    CompilerConstants(String symbolName, Class cls) {
        this(symbolName, cls, -1);
    }

    CompilerConstants(String symbolName, Class cls, int slot) {
        this.symbolName = symbolName;
        this.type = cls;
        this.slot = slot;
    }

    public static boolean isCompilerConstant(String name) {
        ensureSymbolNames();
        return symbolNames.contains(name);
    }

    private static void ensureSymbolNames() {
        CompilerConstants[] values;
        if (symbolNames == null) {
            symbolNames = new HashSet();
            for (CompilerConstants cc : values()) {
                symbolNames.add(cc.symbolName);
            }
        }
    }

    public final String symbolName() {
        return this.symbolName;
    }

    public final Class<?> type() {
        return this.type;
    }

    public final int slot() {
        return this.slot;
    }

    public final String descriptor() {
        if ($assertionsDisabled || this.type != null) {
            return typeDescriptor(this.type);
        }
        throw new AssertionError(" asking for descriptor of typeless constant");
    }

    public static String className(Class<?> type) {
        return Type.getInternalName(type);
    }

    public static String methodDescriptor(Class<?> rtype, Class<?>... ptypes) {
        return Type.getMethodDescriptor(rtype, ptypes);
    }

    public static String typeDescriptor(Class<?> clazz) {
        return Type.typeFor(clazz).getDescriptor();
    }

    public static Call constructorNoLookup(Class<?> clazz) {
        return specialCallNoLookup(clazz, INIT.symbolName(), Void.TYPE, new Class[0]);
    }

    public static Call constructorNoLookup(String className, Class<?>... ptypes) {
        return specialCallNoLookup(className, INIT.symbolName(), methodDescriptor(Void.TYPE, ptypes));
    }

    public static Call constructorNoLookup(Class<?> clazz, Class<?>... ptypes) {
        return specialCallNoLookup(clazz, INIT.symbolName(), Void.TYPE, ptypes);
    }

    public static Call specialCallNoLookup(String className, String name, final String desc) {
        return new Call(null, className, name, desc) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokespecial(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(183, this.className, this.name, desc, false);
            }
        };
    }

    public static Call specialCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return specialCallNoLookup(className(clazz), name, methodDescriptor(rtype, ptypes));
    }

    public static Call staticCallNoLookup(String className, String name, final String desc) {
        return new Call(null, className, name, desc) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokestatic(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(184, this.className, this.name, desc, false);
            }
        };
    }

    public static Call staticCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return staticCallNoLookup(className(clazz), name, methodDescriptor(rtype, ptypes));
    }

    public static Call virtualCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return new Call(null, className(clazz), name, methodDescriptor(rtype, ptypes)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokevirtual(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(182, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static Call interfaceCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return new Call(null, className(clazz), name, methodDescriptor(rtype, ptypes)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokeinterface(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(185, this.className, this.name, this.descriptor, true);
            }
        };
    }

    public static FieldAccess virtualField(String className, String name, String desc) {
        return new FieldAccess(className, name, desc) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.5
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public MethodEmitter get(MethodEmitter method) {
                return method.getField(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public void put(MethodEmitter method) {
                method.putField(this.className, this.name, this.descriptor);
            }
        };
    }

    public static FieldAccess virtualField(Class<?> clazz, String name, Class<?> type) {
        return virtualField(className(clazz), name, typeDescriptor(type));
    }

    public static FieldAccess staticField(String className, String name, String desc) {
        return new FieldAccess(className, name, desc) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.6
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public MethodEmitter get(MethodEmitter method) {
                return method.getStatic(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.FieldAccess
            public void put(MethodEmitter method) {
                method.putStatic(this.className, this.name, this.descriptor);
            }
        };
    }

    public static FieldAccess staticField(Class<?> clazz, String name, Class<?> type) {
        return staticField(className(clazz), name, typeDescriptor(type));
    }

    public static Call staticCall(MethodHandles.Lookup lookup, Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return new Call(Lookup.f248MH.findStatic(lookup, clazz, name, Lookup.f248MH.type(rtype, ptypes)), className(clazz), name, methodDescriptor(rtype, ptypes)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.7
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokestatic(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(184, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static Call virtualCall(MethodHandles.Lookup lookup, Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return new Call(Lookup.f248MH.findVirtual(lookup, clazz, name, Lookup.f248MH.type(rtype, ptypes)), className(clazz), name, methodDescriptor(rtype, ptypes)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokevirtual(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(182, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static Call specialCall(MethodHandles.Lookup lookup, Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
        return new Call(Lookup.f248MH.findSpecial(lookup, clazz, name, Lookup.f248MH.type(rtype, ptypes), clazz), className(clazz), name, methodDescriptor(rtype, ptypes)) { // from class: jdk.nashorn.internal.codegen.CompilerConstants.9
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public MethodEmitter invoke(MethodEmitter method) {
                return method.invokespecial(this.className, this.name, this.descriptor);
            }

            @Override // jdk.nashorn.internal.codegen.CompilerConstants.Call
            public void invoke(MethodVisitor mv) {
                mv.visitMethodInsn(183, this.className, this.name, this.descriptor, false);
            }
        };
    }

    public static boolean isInternalMethodName(String methodName) {
        return methodName.startsWith(":") && !methodName.equals(PROGRAM.symbolName);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilerConstants$Access.class */
    public static abstract class Access {
        protected final MethodHandle methodHandle;
        protected final String className;
        protected final String name;
        protected final String descriptor;

        protected Access(MethodHandle methodHandle, String className, String name, String descriptor) {
            this.methodHandle = methodHandle;
            this.className = className;
            this.name = name;
            this.descriptor = descriptor;
        }

        public MethodHandle methodHandle() {
            return this.methodHandle;
        }

        public String className() {
            return this.className;
        }

        public String name() {
            return this.name;
        }

        public String descriptor() {
            return this.descriptor;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilerConstants$FieldAccess.class */
    public static abstract class FieldAccess extends Access {
        public abstract MethodEmitter get(MethodEmitter methodEmitter);

        public abstract void put(MethodEmitter methodEmitter);

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ String descriptor() {
            return super.descriptor();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ String name() {
            return super.name();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ String className() {
            return super.className();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ MethodHandle methodHandle() {
            return super.methodHandle();
        }

        protected FieldAccess(String className, String name, String descriptor) {
            super(null, className, name, descriptor);
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilerConstants$Call.class */
    public static abstract class Call extends Access {
        public abstract MethodEmitter invoke(MethodEmitter methodEmitter);

        public abstract void invoke(MethodVisitor methodVisitor);

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ String descriptor() {
            return super.descriptor();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ String name() {
            return super.name();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ String className() {
            return super.className();
        }

        @Override // jdk.nashorn.internal.codegen.CompilerConstants.Access
        public /* bridge */ /* synthetic */ MethodHandle methodHandle() {
            return super.methodHandle();
        }

        protected Call(String className, String name, String descriptor) {
            super(null, className, name, descriptor);
        }

        protected Call(MethodHandle methodHandle, String className, String name, String descriptor) {
            super(methodHandle, className, name, descriptor);
        }
    }
}
