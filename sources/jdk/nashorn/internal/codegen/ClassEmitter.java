package jdk.nashorn.internal.codegen;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.debug.NashornClassReader;
import jdk.nashorn.internal.p001ir.debug.NashornTextifier;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.scripts.C1655JS;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ClassEmitter.class */
public class ClassEmitter {
    private static final EnumSet<Flag> DEFAULT_METHOD_FLAGS;
    private boolean classStarted;
    private boolean classEnded;
    private final HashSet<MethodEmitter> methodsStarted;

    /* renamed from: cw */
    protected final ClassWriter f224cw;
    protected final Context context;
    private String unitClassName;
    private Set<Class<?>> constantMethodNeeded;
    private int methodCount;
    private int initCount;
    private int clinitCount;
    private int fieldCount;
    private final Set<String> methodNames;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ClassEmitter.class.desiredAssertionStatus();
        DEFAULT_METHOD_FLAGS = EnumSet.of(Flag.PUBLIC);
    }

    private ClassEmitter(Context context, ClassWriter cw) {
        this.context = context;
        this.f224cw = cw;
        this.methodsStarted = new HashSet<>();
        this.methodNames = new HashSet();
    }

    public Set<String> getMethodNames() {
        return Collections.unmodifiableSet(this.methodNames);
    }

    public ClassEmitter(Context context, String className, String superClassName, String... interfaceNames) {
        this(context, new ClassWriter(3));
        this.f224cw.visit(51, 33, className, (String) null, superClassName, interfaceNames);
    }

    public ClassEmitter(Context context, String sourceName, String unitClassName, boolean strictMode) {
        this(context, new ClassWriter(3) { // from class: jdk.nashorn.internal.codegen.ClassEmitter.1
            private static final String OBJECT_CLASS = "java/lang/Object";

            protected String getCommonSuperClass(String type1, String type2) {
                try {
                    return super.getCommonSuperClass(type1, type2);
                } catch (RuntimeException e) {
                    if (ClassEmitter.isScriptObject(Compiler.SCRIPTS_PACKAGE, type1) && ClassEmitter.isScriptObject(Compiler.SCRIPTS_PACKAGE, type2)) {
                        return CompilerConstants.className(ScriptObject.class);
                    }
                    return OBJECT_CLASS;
                }
            }
        });
        this.unitClassName = unitClassName;
        this.constantMethodNeeded = new HashSet();
        this.f224cw.visit(51, 33, unitClassName, (String) null, pathName(C1655JS.class.getName()), (String[]) null);
        this.f224cw.visitSource(sourceName, (String) null);
        defineCommonStatics(strictMode);
    }

    public Context getContext() {
        return this.context;
    }

    public String getUnitClassName() {
        return this.unitClassName;
    }

    public int getMethodCount() {
        return this.methodCount;
    }

    public int getClinitCount() {
        return this.clinitCount;
    }

    public int getInitCount() {
        return this.initCount;
    }

    public int getFieldCount() {
        return this.fieldCount;
    }

    private static String pathName(String name) {
        return name.replace('.', '/');
    }

    private void defineCommonStatics(boolean strictMode) {
        field(EnumSet.of(Flag.PRIVATE, Flag.STATIC), CompilerConstants.SOURCE.symbolName(), Source.class);
        field(EnumSet.of(Flag.PRIVATE, Flag.STATIC), CompilerConstants.CONSTANTS.symbolName(), Object[].class);
        field(EnumSet.of(Flag.PUBLIC, Flag.STATIC, Flag.FINAL), CompilerConstants.STRICT_MODE.symbolName(), Boolean.TYPE, Boolean.valueOf(strictMode));
    }

    private void defineCommonUtilities() {
        if ($assertionsDisabled || this.unitClassName != null) {
            if (this.constantMethodNeeded.contains(String.class)) {
                MethodEmitter getStringMethod = method(EnumSet.of(Flag.PRIVATE, Flag.STATIC), CompilerConstants.GET_STRING.symbolName(), String.class, Integer.TYPE);
                getStringMethod.begin();
                getStringMethod.getStatic(this.unitClassName, CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor()).load(Type.INT, 0).arrayload().checkcast(String.class)._return();
                getStringMethod.end();
            }
            if (this.constantMethodNeeded.contains(PropertyMap.class)) {
                MethodEmitter getMapMethod = method(EnumSet.of(Flag.PUBLIC, Flag.STATIC), CompilerConstants.GET_MAP.symbolName(), PropertyMap.class, Integer.TYPE);
                getMapMethod.begin();
                getMapMethod.loadConstants().load(Type.INT, 0).arrayload().checkcast(PropertyMap.class)._return();
                getMapMethod.end();
                MethodEmitter setMapMethod = method(EnumSet.of(Flag.PUBLIC, Flag.STATIC), CompilerConstants.SET_MAP.symbolName(), Void.TYPE, Integer.TYPE, PropertyMap.class);
                setMapMethod.begin();
                setMapMethod.loadConstants().load(Type.INT, 0).load(Type.OBJECT, 1).arraystore();
                setMapMethod.returnVoid();
                setMapMethod.end();
            }
            for (Class<?> clazz : this.constantMethodNeeded) {
                if (clazz.isArray()) {
                    defineGetArrayMethod(clazz);
                }
            }
            return;
        }
        throw new AssertionError();
    }

    private void defineGetArrayMethod(Class<?> clazz) {
        if ($assertionsDisabled || this.unitClassName != null) {
            String methodName = getArrayMethodName(clazz);
            MethodEmitter getArrayMethod = method(EnumSet.of(Flag.PRIVATE, Flag.STATIC), methodName, clazz, Integer.TYPE);
            getArrayMethod.begin();
            getArrayMethod.getStatic(this.unitClassName, CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor()).load(Type.INT, 0).arrayload().checkcast(clazz).invoke(CompilerConstants.virtualCallNoLookup(clazz, "clone", Object.class, new Class[0])).checkcast(clazz)._return();
            getArrayMethod.end();
            return;
        }
        throw new AssertionError();
    }

    public static String getArrayMethodName(Class<?> clazz) {
        if ($assertionsDisabled || clazz.isArray()) {
            return CompilerConstants.GET_ARRAY_PREFIX.symbolName() + clazz.getComponentType().getSimpleName() + CompilerConstants.GET_ARRAY_SUFFIX.symbolName();
        }
        throw new AssertionError();
    }

    public void needGetConstantMethod(Class<?> clazz) {
        this.constantMethodNeeded.add(clazz);
    }

    public static boolean isScriptObject(String scriptPrefix, String type) {
        if (type.startsWith(scriptPrefix) || type.equals(CompilerConstants.className(ScriptObject.class)) || type.startsWith(Compiler.OBJECTS_PACKAGE)) {
            return true;
        }
        return false;
    }

    public void begin() {
        this.classStarted = true;
    }

    public void end() {
        if ($assertionsDisabled || this.classStarted) {
            if (this.unitClassName != null) {
                MethodEmitter initMethod = init(EnumSet.of(Flag.PRIVATE), new Class[0]);
                initMethod.begin();
                initMethod.load(Type.OBJECT, 0);
                initMethod.newInstance(C1655JS.class);
                initMethod.returnVoid();
                initMethod.end();
                defineCommonUtilities();
            }
            this.f224cw.visitEnd();
            this.classStarted = false;
            this.classEnded = true;
            if (!$assertionsDisabled && !this.methodsStarted.isEmpty()) {
                throw new AssertionError("methodsStarted not empty " + this.methodsStarted);
            }
            return;
        }
        throw new AssertionError("class not started for " + this.unitClassName);
    }

    public static String disassemble(byte[] bytecode) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos);
        Throwable th = null;
        try {
            NashornClassReader cr = new NashornClassReader(bytecode);
            Context ctx = (Context) AccessController.doPrivileged(new PrivilegedAction<Context>() { // from class: jdk.nashorn.internal.codegen.ClassEmitter.2
                @Override // java.security.PrivilegedAction
                public Context run() {
                    return Context.getContext();
                }
            });
            TraceClassVisitor tcv = new TraceClassVisitor((ClassVisitor) null, new NashornTextifier(ctx.getEnv(), cr), pw);
            cr.accept(tcv, 0);
            if (pw != null) {
                if (0 != 0) {
                    try {
                        pw.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    pw.close();
                }
            }
            String str = new String(baos.toByteArray());
            return str;
        } finally {
        }
    }

    public void beginMethod(MethodEmitter method) {
        if ($assertionsDisabled || !this.methodsStarted.contains(method)) {
            this.methodsStarted.add(method);
            return;
        }
        throw new AssertionError();
    }

    public void endMethod(MethodEmitter method) {
        if ($assertionsDisabled || this.methodsStarted.contains(method)) {
            this.methodsStarted.remove(method);
            return;
        }
        throw new AssertionError();
    }

    MethodEmitter method(String methodName, Class<?> rtype, Class<?>... ptypes) {
        return method(DEFAULT_METHOD_FLAGS, methodName, rtype, ptypes);
    }

    public MethodEmitter method(EnumSet<Flag> methodFlags, String methodName, Class<?> rtype, Class<?>... ptypes) {
        this.methodCount++;
        this.methodNames.add(methodName);
        return new MethodEmitter(this, methodVisitor(methodFlags, methodName, rtype, ptypes));
    }

    MethodEmitter method(String methodName, String descriptor) {
        return method(DEFAULT_METHOD_FLAGS, methodName, descriptor);
    }

    public MethodEmitter method(EnumSet<Flag> methodFlags, String methodName, String descriptor) {
        this.methodCount++;
        this.methodNames.add(methodName);
        return new MethodEmitter(this, this.f224cw.visitMethod(Flag.getValue(methodFlags), methodName, descriptor, (String) null, (String[]) null));
    }

    public MethodEmitter method(FunctionNode functionNode) {
        this.methodCount++;
        this.methodNames.add(functionNode.getName());
        FunctionSignature signature = new FunctionSignature(functionNode);
        MethodVisitor mv = this.f224cw.visitMethod(9 | (functionNode.isVarArg() ? 128 : 0), functionNode.getName(), signature.toString(), (String) null, (String[]) null);
        return new MethodEmitter(this, mv, functionNode);
    }

    public MethodEmitter restOfMethod(FunctionNode functionNode) {
        this.methodCount++;
        this.methodNames.add(functionNode.getName());
        MethodVisitor mv = this.f224cw.visitMethod(9, functionNode.getName(), Type.getMethodDescriptor(functionNode.getReturnType().getTypeClass(), RewriteException.class), (String) null, (String[]) null);
        return new MethodEmitter(this, mv, functionNode);
    }

    MethodEmitter clinit() {
        this.clinitCount++;
        return method(EnumSet.of(Flag.STATIC), CompilerConstants.CLINIT.symbolName(), Void.TYPE, new Class[0]);
    }

    public MethodEmitter init() {
        this.initCount++;
        return method(CompilerConstants.INIT.symbolName(), Void.TYPE, new Class[0]);
    }

    public MethodEmitter init(Class<?>... ptypes) {
        this.initCount++;
        return method(CompilerConstants.INIT.symbolName(), Void.TYPE, ptypes);
    }

    MethodEmitter init(EnumSet<Flag> flags, Class<?>... ptypes) {
        this.initCount++;
        return method(flags, CompilerConstants.INIT.symbolName(), Void.TYPE, ptypes);
    }

    final void field(EnumSet<Flag> fieldFlags, String fieldName, Class<?> fieldType, Object value) {
        this.fieldCount++;
        this.f224cw.visitField(Flag.getValue(fieldFlags), fieldName, CompilerConstants.typeDescriptor(fieldType), (String) null, value).visitEnd();
    }

    public final void field(EnumSet<Flag> fieldFlags, String fieldName, Class<?> fieldType) {
        field(fieldFlags, fieldName, fieldType, null);
    }

    public final void field(String fieldName, Class<?> fieldType) {
        field(EnumSet.of(Flag.PUBLIC), fieldName, fieldType, null);
    }

    public byte[] toByteArray() {
        if ($assertionsDisabled || this.classEnded) {
            if (!this.classEnded) {
                return null;
            }
            return this.f224cw.toByteArray();
        }
        throw new AssertionError();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ClassEmitter$Flag.class */
    public enum Flag {
        HANDLE_STATIC(6),
        HANDLE_NEWSPECIAL(8),
        HANDLE_SPECIAL(7),
        HANDLE_VIRTUAL(5),
        HANDLE_INTERFACE(9),
        FINAL(16),
        STATIC(8),
        PUBLIC(1),
        PRIVATE(2);
        
        private int value;

        Flag(int value) {
            this.value = value;
        }

        int getValue() {
            return this.value;
        }

        public static int getValue(EnumSet<Flag> flags) {
            int v = 0;
            Iterator it = flags.iterator();
            while (it.hasNext()) {
                Flag flag = (Flag) it.next();
                v |= flag.getValue();
            }
            return v;
        }
    }

    private MethodVisitor methodVisitor(EnumSet<Flag> flags, String methodName, Class<?> rtype, Class<?>... ptypes) {
        return this.f224cw.visitMethod(Flag.getValue(flags), methodName, CompilerConstants.methodDescriptor(rtype, ptypes), (String) null, (String[]) null);
    }
}
