package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/NoSuchClassError.class */
public class NoSuchClassError extends Error {
    private static final long serialVersionUID = 1;
    private String className;

    public NoSuchClassError(String className, Error cause) {
        super(cause.toString(), cause);
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }
}
