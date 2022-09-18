package jdk.nashorn.internal.objects;

import java.util.Collections;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.NativeJavaPackage;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJavaImporter.class */
public final class NativeJavaImporter extends ScriptObject {
    private final Object[] args;
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJavaImporter$Constructor.class */
    final class Constructor extends ScriptFunction {
        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeJavaImporter.Constructor.<init>():void
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
                r5 = this;
                r0 = r5
                java.lang.String r1 = "JavaImporter"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeJavaImporter$Prototype r-1 = new jdk.nashorn.internal.objects.NativeJavaImporter$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                r-2 = r5
                r-1 = 1
                r-2.setArity(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJavaImporter.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJavaImporter$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object __noSuchProperty__;
        private Object __noSuchMethod__;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$__noSuchProperty__() {
            return this.__noSuchProperty__;
        }

        public void S$__noSuchProperty__(Object obj) {
            this.__noSuchProperty__ = obj;
        }

        public Object G$__noSuchMethod__() {
            return this.__noSuchMethod__;
        }

        public void S$__noSuchMethod__(Object obj) {
            this.__noSuchMethod__ = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeJavaImporter.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeJavaImporter.Prototype.<init>():void
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
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeJavaImporter.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "__noSuchProperty__"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r1
                r-1.__noSuchProperty__ = r0
                r-1 = r4
                java.lang.String r0 = "__noSuchMethod__"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r0
                r-2.__noSuchMethod__ = r-1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJavaImporter.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "JavaImporter";
        }
    }

    static {
        $clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private NativeJavaImporter(Object[] args, ScriptObject proto, PropertyMap map) {
        super(proto, map);
        this.args = args;
    }

    private NativeJavaImporter(Object[] args, Global global) {
        this(args, global.getJavaImporterPrototype(), $nasgenmap$);
    }

    private NativeJavaImporter(Object[] args) {
        this(args, Global.instance());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "JavaImporter";
    }

    public static NativeJavaImporter constructor(boolean isNew, Object self, Object... args) {
        return new NativeJavaImporter(args);
    }

    public static Object __noSuchProperty__(Object self, Object name) {
        if (!(self instanceof NativeJavaImporter)) {
            throw ECMAErrors.typeError("not.a.java.importer", ScriptRuntime.safeToString(self));
        }
        return ((NativeJavaImporter) self).createProperty(JSType.toString(name));
    }

    public static Object __noSuchMethod__(Object self, Object... args) {
        throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(args[0]));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchProperty(CallSiteDescriptor desc, LinkRequest request) {
        return createAndSetProperty(desc) ? super.lookup(desc, request) : super.noSuchProperty(desc, request);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchMethod(CallSiteDescriptor desc, LinkRequest request) {
        return createAndSetProperty(desc) ? super.lookup(desc, request) : super.noSuchMethod(desc, request);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object invokeNoSuchProperty(String name, boolean isScope, int programPoint) {
        Object retval = createProperty(name);
        if (UnwarrantedOptimismException.isValid(programPoint)) {
            throw new UnwarrantedOptimismException(retval, programPoint);
        }
        return retval;
    }

    private boolean createAndSetProperty(CallSiteDescriptor desc) {
        String name = desc.getNameToken(2);
        Object value = createProperty(name);
        if (value != null) {
            set(name, value, 0);
            return true;
        }
        return false;
    }

    private Object createProperty(String name) {
        int len = this.args.length;
        for (int i = len - 1; i > -1; i--) {
            Object obj = this.args[i];
            if (obj instanceof StaticClass) {
                if (((StaticClass) obj).getRepresentedClass().getSimpleName().equals(name)) {
                    return obj;
                }
            } else if (obj instanceof NativeJavaPackage) {
                String pkgName = ((NativeJavaPackage) obj).getName();
                String fullName = pkgName.isEmpty() ? name : pkgName + "." + name;
                Context context = Global.instance().getContext();
                try {
                    return StaticClass.forClass(context.findClass(fullName));
                } catch (ClassNotFoundException e) {
                }
            } else {
                continue;
            }
        }
        return null;
    }
}
