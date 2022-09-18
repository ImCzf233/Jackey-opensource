package com.viaversion.viaversion.libs.javassist.scopedpool;

import com.viaversion.viaversion.libs.javassist.ClassPool;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/scopedpool/ScopedClassPoolFactory.class */
public interface ScopedClassPoolFactory {
    ScopedClassPool create(ClassLoader classLoader, ClassPool classPool, ScopedClassPoolRepository scopedClassPoolRepository);

    ScopedClassPool create(ClassPool classPool, ScopedClassPoolRepository scopedClassPoolRepository);
}
