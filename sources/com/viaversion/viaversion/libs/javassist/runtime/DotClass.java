package com.viaversion.viaversion.libs.javassist.runtime;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/runtime/DotClass.class */
public class DotClass {
    public static NoClassDefFoundError fail(ClassNotFoundException e) {
        return new NoClassDefFoundError(e.getMessage());
    }
}
