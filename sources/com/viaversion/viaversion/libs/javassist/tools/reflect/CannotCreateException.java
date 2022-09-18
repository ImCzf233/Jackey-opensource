package com.viaversion.viaversion.libs.javassist.tools.reflect;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/reflect/CannotCreateException.class */
public class CannotCreateException extends Exception {
    private static final long serialVersionUID = 1;

    public CannotCreateException(String s) {
        super(s);
    }

    public CannotCreateException(Exception e) {
        super("by " + e.toString());
    }
}
