package jdk.nashorn.internal.runtime.linker;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.DumpBytecode;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterClassLoader.class */
public final class JavaAdapterClassLoader {
    private static final AccessControlContext CREATE_LOADER_ACC_CTXT;
    private static final AccessControlContext GET_CONTEXT_ACC_CTXT;
    private static final Collection<String> VISIBLE_INTERNAL_CLASS_NAMES;
    private final String className;
    private final byte[] classBytes;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaAdapterClassLoader.class.desiredAssertionStatus();
        CREATE_LOADER_ACC_CTXT = ClassAndLoader.createPermAccCtxt("createClassLoader");
        GET_CONTEXT_ACC_CTXT = ClassAndLoader.createPermAccCtxt(Context.NASHORN_GET_CONTEXT);
        VISIBLE_INTERNAL_CLASS_NAMES = Collections.unmodifiableCollection(new HashSet(Arrays.asList(JavaAdapterServices.class.getName(), ScriptObject.class.getName(), ScriptFunction.class.getName(), JSType.class.getName())));
    }

    public JavaAdapterClassLoader(String className, byte[] classBytes) {
        this.className = className.replace('/', '.');
        this.classBytes = classBytes;
    }

    public StaticClass generateClass(final ClassLoader parentLoader, final ProtectionDomain protectionDomain) {
        if ($assertionsDisabled || protectionDomain != null) {
            return (StaticClass) AccessController.doPrivileged(new PrivilegedAction<StaticClass>() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterClassLoader.1
                @Override // java.security.PrivilegedAction
                public StaticClass run() {
                    try {
                        return StaticClass.forClass(Class.forName(JavaAdapterClassLoader.this.className, true, JavaAdapterClassLoader.this.createClassLoader(parentLoader, protectionDomain)));
                    } catch (ClassNotFoundException e) {
                        throw new AssertionError(e);
                    }
                }
            }, CREATE_LOADER_ACC_CTXT);
        }
        throw new AssertionError();
    }

    public ClassLoader createClassLoader(ClassLoader parentLoader, final ProtectionDomain protectionDomain) {
        return new SecureClassLoader(parentLoader) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterClassLoader.2
            private final ClassLoader myLoader = getClass().getClassLoader();
            static final /* synthetic */ boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !JavaAdapterClassLoader.class.desiredAssertionStatus();
            }

            @Override // java.lang.ClassLoader
            public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                try {
                    Context.checkPackageAccess(name);
                    return super.loadClass(name, resolve);
                } catch (SecurityException se) {
                    if (JavaAdapterClassLoader.VISIBLE_INTERNAL_CLASS_NAMES.contains(name)) {
                        return this.myLoader.loadClass(name);
                    }
                    throw se;
                }
            }

            @Override // java.lang.ClassLoader
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                if (name.equals(JavaAdapterClassLoader.this.className)) {
                    if (!$assertionsDisabled && JavaAdapterClassLoader.this.classBytes == null) {
                        throw new AssertionError("what? already cleared .class bytes!!");
                    }
                    Context ctx = (Context) AccessController.doPrivileged(new PrivilegedAction<Context>() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterClassLoader.2.1
                        @Override // java.security.PrivilegedAction
                        public Context run() {
                            return Context.getContext();
                        }
                    }, JavaAdapterClassLoader.GET_CONTEXT_ACC_CTXT);
                    DumpBytecode.dumpBytecode(ctx.getEnv(), ctx.getLogger(Compiler.class), JavaAdapterClassLoader.this.classBytes, name);
                    return defineClass(name, JavaAdapterClassLoader.this.classBytes, 0, JavaAdapterClassLoader.this.classBytes.length, protectionDomain);
                }
                throw new ClassNotFoundException(name);
            }
        };
    }
}
