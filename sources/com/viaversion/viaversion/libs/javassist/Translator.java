package com.viaversion.viaversion.libs.javassist;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/Translator.class */
public interface Translator {
    void start(ClassPool classPool) throws NotFoundException, CannotCompileException;

    void onLoad(ClassPool classPool, String str) throws NotFoundException, CannotCompileException;
}
