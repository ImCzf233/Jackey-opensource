package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.annotations.Function;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/NativeJavaPackage.class */
public final class NativeJavaPackage extends ScriptObject {

    /* renamed from: MH */
    private static final MethodHandleFunctionality f269MH = MethodHandleFactory.getFunctionality();
    private static final MethodHandle CLASS_NOT_FOUND = findOwnMH("classNotFound", Void.TYPE, NativeJavaPackage.class);
    private static final MethodHandle TYPE_GUARD = Guards.getClassGuard(NativeJavaPackage.class);
    private final String name;

    public NativeJavaPackage(String name, ScriptObject proto) {
        super(proto, null);
        Context.checkPackageAccess(name);
        this.name = name;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "JavaPackage";
    }

    public boolean equals(Object other) {
        if (other instanceof NativeJavaPackage) {
            return this.name.equals(((NativeJavaPackage) other).name);
        }
        return false;
    }

    public int hashCode() {
        if (this.name == null) {
            return 0;
        }
        return this.name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return toString();
    }

    public String toString() {
        return "[JavaPackage " + this.name + "]";
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getDefaultValue(Class<?> hint) {
        if (hint == String.class) {
            return toString();
        }
        return super.getDefaultValue(hint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
        return createClassNotFoundInvocation(desc);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findCallMethod(CallSiteDescriptor desc, LinkRequest request) {
        return createClassNotFoundInvocation(desc);
    }

    private static GuardedInvocation createClassNotFoundInvocation(CallSiteDescriptor desc) {
        MethodType type = desc.getMethodType();
        return new GuardedInvocation(f269MH.dropArguments(CLASS_NOT_FOUND, 1, type.parameterList().subList(1, type.parameterCount())), type.parameterType(0) == NativeJavaPackage.class ? null : TYPE_GUARD);
    }

    private static void classNotFound(NativeJavaPackage pkg) throws ClassNotFoundException {
        throw new ClassNotFoundException(pkg.name);
    }

    @Function(attributes = 2)
    public static Object __noSuchProperty__(Object self, Object name) {
        throw new AssertionError("__noSuchProperty__ placeholder called");
    }

    @Function(attributes = 2)
    public static Object __noSuchMethod__(Object self, Object... args) {
        throw new AssertionError("__noSuchMethod__ placeholder called");
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchProperty(CallSiteDescriptor desc, LinkRequest request) {
        String propertyName = desc.getNameToken(2);
        createProperty(propertyName);
        return super.lookup(desc, request);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object invokeNoSuchProperty(String key, boolean isScope, int programPoint) {
        Object retval = createProperty(key);
        if (UnwarrantedOptimismException.isValid(programPoint)) {
            throw new UnwarrantedOptimismException(retval, programPoint);
        }
        return retval;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchMethod(CallSiteDescriptor desc, LinkRequest request) {
        return noSuchProperty(desc, request);
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return f269MH.findStatic(MethodHandles.lookup(), NativeJavaPackage.class, name, f269MH.type(rtype, types));
    }

    private Object createProperty(String propertyName) {
        Object propertyValue;
        String fullName = this.name.isEmpty() ? propertyName : this.name + "." + propertyName;
        Context context = Context.getContextTrusted();
        Class<?> javaClass = null;
        try {
            javaClass = context.findClass(fullName);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
        }
        int openBrace = propertyName.indexOf(40);
        int closeBrace = propertyName.lastIndexOf(41);
        if (openBrace != -1 || closeBrace != -1) {
            int lastChar = propertyName.length() - 1;
            if (openBrace == -1 || closeBrace != lastChar) {
                throw ECMAErrors.typeError("improper.constructor.signature", propertyName);
            }
            String className = this.name + "." + propertyName.substring(0, openBrace);
            try {
                Class<?> javaClass2 = context.findClass(className);
                Object constructor = BeansLinker.getConstructorMethod(javaClass2, propertyName.substring(openBrace + 1, lastChar));
                if (constructor != null) {
                    set(propertyName, constructor, 0);
                    return constructor;
                }
                throw ECMAErrors.typeError("no.such.java.constructor", propertyName);
            } catch (ClassNotFoundException | NoClassDefFoundError e2) {
                throw ECMAErrors.typeError(e2, "no.such.java.class", className);
            }
        }
        if (javaClass == null) {
            propertyValue = new NativeJavaPackage(fullName, getProto());
        } else {
            propertyValue = StaticClass.forClass(javaClass);
        }
        set(propertyName, propertyValue, 0);
        return propertyValue;
    }
}
