package com.viaversion.viaversion.libs.javassist.util.proxy;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefinePackageHelper.class */
public class DefinePackageHelper {
    private static final Helper privileged;

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefinePackageHelper$Helper.class */
    public static abstract class Helper {
        abstract Package definePackage(ClassLoader classLoader, String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) throws IllegalArgumentException;

        private Helper() {
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefinePackageHelper$Java9.class */
    private static class Java9 extends Helper {
        private Java9() {
            super();
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefinePackageHelper.Helper
        Package definePackage(ClassLoader loader, String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, URL sealBase) throws IllegalArgumentException {
            throw new RuntimeException("define package has been disabled for jigsaw");
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefinePackageHelper$Java7.class */
    private static class Java7 extends Helper {
        private final SecurityActions stack;
        private final MethodHandle definePackage;

        private Java7() {
            super();
            this.stack = SecurityActions.stack;
            this.definePackage = getDefinePackageMethodHandle();
        }

        private MethodHandle getDefinePackageMethodHandle() {
            if (this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getMethodHandle(ClassLoader.class, "definePackage", new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefinePackageHelper.Helper
        Package definePackage(ClassLoader loader, String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, URL sealBase) throws IllegalArgumentException {
            if (this.stack.getCallerClass() != DefinePackageHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return (Package) this.definePackage.invokeWithArguments(loader, name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
            } catch (Throwable e) {
                if (e instanceof IllegalArgumentException) {
                    throw ((IllegalArgumentException) e);
                }
                if (!(e instanceof RuntimeException)) {
                    return null;
                }
                throw ((RuntimeException) e);
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/util/proxy/DefinePackageHelper$JavaOther.class */
    private static class JavaOther extends Helper {
        private final SecurityActions stack;
        private final Method definePackage;

        private JavaOther() {
            super();
            this.stack = SecurityActions.stack;
            this.definePackage = getDefinePackageMethod();
        }

        private Method getDefinePackageMethod() {
            if (this.stack.getCallerClass() != getClass()) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                return SecurityActions.getDeclaredMethod(ClassLoader.class, "definePackage", new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("cannot initialize", e);
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.util.proxy.DefinePackageHelper.Helper
        Package definePackage(ClassLoader loader, String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, URL sealBase) throws IllegalArgumentException {
            if (this.stack.getCallerClass() != DefinePackageHelper.class) {
                throw new IllegalAccessError("Access denied for caller.");
            }
            try {
                this.definePackage.setAccessible(true);
                return (Package) this.definePackage.invoke(loader, name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
            } catch (Throwable e) {
                if (e instanceof InvocationTargetException) {
                    Throwable t = ((InvocationTargetException) e).getTargetException();
                    if (t instanceof IllegalArgumentException) {
                        throw ((IllegalArgumentException) t);
                    }
                }
                if (!(e instanceof RuntimeException)) {
                    return null;
                }
                throw ((RuntimeException) e);
            }
        }
    }

    static {
        Helper helper;
        if (ClassFile.MAJOR_VERSION >= 53) {
            helper = new Java9();
        } else {
            helper = ClassFile.MAJOR_VERSION >= 51 ? new Java7() : new JavaOther();
        }
        privileged = helper;
    }

    public static void definePackage(String className, ClassLoader loader) throws CannotCompileException {
        try {
            privileged.definePackage(loader, className, null, null, null, null, null, null, null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e2) {
            throw new CannotCompileException(e2);
        }
    }

    private DefinePackageHelper() {
    }
}
