package com.viaversion.viaversion.libs.javassist.util.proxy;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import com.viaversion.viaversion.libs.javassist.util.proxy.SecurityActions;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper.class */
public class DefineClassHelper {
    private static final Helper privileged;

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper$Helper.class */
    public static abstract class Helper {
        abstract Class<?> defineClass(String str, byte[] bArr, int i, int i2, Class<?> cls, ClassLoader classLoader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException;

        private Helper() {
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper$Java11.class */
    private static class Java11 extends JavaOther {
        private Java11() {
            super();
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefineClassHelper.JavaOther, com.viaversion.viaversion.libs.javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String name, byte[] bcode, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
            if (neighbor != null) {
                return DefineClassHelper.toClass(neighbor, bcode);
            }
            return super.defineClass(name, bcode, off, len, neighbor, loader, protectionDomain);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper$Java9.class */
    public static class Java9 extends Helper {
        private final Object stack;
        private final Method getCallerClass;
        private final ReferencedUnsafe sunMiscUnsafe = getReferencedUnsafe();

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper$Java9$ReferencedUnsafe.class */
        public final class ReferencedUnsafe {
            private final SecurityActions.TheUnsafe sunMiscUnsafeTheUnsafe;
            private final MethodHandle defineClass;

            ReferencedUnsafe(SecurityActions.TheUnsafe usf, MethodHandle meth) {
                Java9.this = this$0;
                this.sunMiscUnsafeTheUnsafe = usf;
                this.defineClass = meth;
            }

            Class<?> defineClass(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError {
                try {
                    if (Java9.this.getCallerClass.invoke(Java9.this.stack, new Object[0]) != Java9.class) {
                        throw new IllegalAccessError("Access denied for caller.");
                    }
                    try {
                        return (Class) this.defineClass.invokeWithArguments(this.sunMiscUnsafeTheUnsafe.theUnsafe, name, b, Integer.valueOf(off), Integer.valueOf(len), loader, protectionDomain);
                    } catch (Throwable e) {
                        if (e instanceof RuntimeException) {
                            throw ((RuntimeException) e);
                        }
                        if (!(e instanceof ClassFormatError)) {
                            throw new ClassFormatError(e.getMessage());
                        }
                        throw ((ClassFormatError) e);
                    }
                } catch (Exception e2) {
                    throw new RuntimeException("cannot initialize", e2);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        Java9() {
            super();
            Class<?> stackWalkerClass = null;
            try {
                stackWalkerClass = Class.forName("java.lang.StackWalker");
            } catch (ClassNotFoundException e) {
            }
            if (stackWalkerClass != null) {
                try {
                    Class<?> optionClass = Class.forName("java.lang.StackWalker$Option");
                    this.stack = stackWalkerClass.getMethod("getInstance", optionClass).invoke(null, optionClass.getEnumConstants()[0]);
                    this.getCallerClass = stackWalkerClass.getMethod("getCallerClass", new Class[0]);
                    return;
                } catch (Throwable e2) {
                    throw new RuntimeException("cannot initialize", e2);
                }
            }
            this.stack = null;
            this.getCallerClass = null;
        }

        private final ReferencedUnsafe getReferencedUnsafe() {
            String str;
            try {
                if (DefineClassHelper.privileged != null && this.getCallerClass.invoke(this.stack, new Object[0]) != getClass()) {
                    throw new IllegalAccessError("Access denied for caller.");
                }
                try {
                    SecurityActions.TheUnsafe usf = SecurityActions.getSunMiscUnsafeAnonymously();
                    List<Method> defineClassMethod = usf.methods.get("defineClass");
                    if (null == defineClassMethod) {
                        return null;
                    }
                    MethodHandle meth = MethodHandles.lookup().unreflect(defineClassMethod.get(0));
                    return new ReferencedUnsafe(usf, meth);
                } finally {
                    RuntimeException runtimeException = new RuntimeException("cannot initialize", e);
                }
            } catch (Exception e) {
                throw new RuntimeException(str, e);
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String name, byte[] b, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError {
            try {
                if (this.getCallerClass.invoke(this.stack, new Object[0]) != DefineClassHelper.class) {
                    throw new IllegalAccessError("Access denied for caller.");
                }
                return this.sunMiscUnsafe.defineClass(name, b, off, len, loader, protectionDomain);
            } catch (Exception e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper$Java7.class */
    private static class Java7 extends Helper {
        private final SecurityActions stack;
        private final MethodHandle defineClass;

        private Java7() {
            super();
            this.stack = SecurityActions.stack;
            this.defineClass = getDefineClassMethodHandle();
        }

        private final MethodHandle getDefineClassMethodHandle() {
            if (DefineClassHelper.privileged != null && this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getMethodHandle(ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String name, byte[] b, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError {
            if (this.stack.getCallerClass() != DefineClassHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return (Class) this.defineClass.invokeWithArguments(loader, name, b, Integer.valueOf(off), Integer.valueOf(len), protectionDomain);
            } catch (Throwable e) {
                if (e instanceof RuntimeException) {
                    throw ((RuntimeException) e);
                }
                if (!(e instanceof ClassFormatError)) {
                    throw new ClassFormatError(e.getMessage());
                }
                throw ((ClassFormatError) e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefineClassHelper$JavaOther.class */
    public static class JavaOther extends Helper {
        private final Method defineClass;
        private final SecurityActions stack;

        private JavaOther() {
            super();
            this.defineClass = getDefineClassMethod();
            this.stack = SecurityActions.stack;
        }

        private final Method getDefineClassMethod() {
            if (DefineClassHelper.privileged != null && this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getDeclaredMethod(ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefineClassHelper.Helper
        Class<?> defineClass(String name, byte[] b, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
            Class<?> klass = this.stack.getCallerClass();
            if (klass != DefineClassHelper.class && klass != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                SecurityActions.setAccessible(this.defineClass, true);
                return (Class) this.defineClass.invoke(loader, name, b, Integer.valueOf(off), Integer.valueOf(len), protectionDomain);
            } catch (Throwable e) {
                if (e instanceof ClassFormatError) {
                    throw ((ClassFormatError) e);
                }
                if (!(e instanceof RuntimeException)) {
                    throw new CannotCompileException(e);
                }
                throw ((RuntimeException) e);
            }
        }
    }

    static {
        Helper helper;
        if (ClassFile.MAJOR_VERSION > 54) {
            helper = new Java11();
        } else if (ClassFile.MAJOR_VERSION >= 53) {
            helper = new Java9();
        } else {
            helper = ClassFile.MAJOR_VERSION >= 51 ? new Java7() : new JavaOther();
        }
        privileged = helper;
    }

    public static Class<?> toClass(String className, Class<?> neighbor, ClassLoader loader, ProtectionDomain domain, byte[] bcode) throws CannotCompileException {
        try {
            return privileged.defineClass(className, bcode, 0, bcode.length, neighbor, loader, domain);
        } catch (CannotCompileException e) {
            throw e;
        } catch (ClassFormatError e2) {
            Throwable t = e2.getCause();
            throw new CannotCompileException(t == null ? e2 : t);
        } catch (RuntimeException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new CannotCompileException(e4);
        }
    }

    public static Class<?> toClass(Class<?> neighbor, byte[] bcode) throws CannotCompileException {
        try {
            DefineClassHelper.class.getModule().addReads(neighbor.getModule());
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandles.Lookup prvlookup = MethodHandles.privateLookupIn(neighbor, lookup);
            return prvlookup.defineClass(bcode);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new CannotCompileException(e.getMessage() + ": " + neighbor.getName() + " has no permission to define the class");
        }
    }

    public static Class<?> toClass(MethodHandles.Lookup lookup, byte[] bcode) throws CannotCompileException {
        try {
            return lookup.defineClass(bcode);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new CannotCompileException(e.getMessage());
        }
    }

    public static Class<?> toPublicClass(String className, byte[] bcode) throws CannotCompileException {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            return lookup.dropLookupMode(2).defineClass(bcode);
        } catch (Throwable t) {
            throw new CannotCompileException(t);
        }
    }

    private DefineClassHelper() {
    }
}
