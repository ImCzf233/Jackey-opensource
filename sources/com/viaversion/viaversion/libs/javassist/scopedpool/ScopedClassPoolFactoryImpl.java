package com.viaversion.viaversion.libs.javassist.scopedpool;

import com.viaversion.viaversion.libs.javassist.ClassPool;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/scopedpool/ScopedClassPoolFactoryImpl.class */
public class ScopedClassPoolFactoryImpl implements ScopedClassPoolFactory {
    @Override // com.viaversion.viaversion.libs.javassist.scopedpool.ScopedClassPoolFactory
    public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository) {
        return new ScopedClassPool(cl, src, repository, false);
    }

    @Override // com.viaversion.viaversion.libs.javassist.scopedpool.ScopedClassPoolFactory
    public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository) {
        return new ScopedClassPool(null, src, repository, true);
    }
}
