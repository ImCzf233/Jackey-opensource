package com.viaversion.viaversion.libs.javassist.scopedpool;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/scopedpool/ScopedClassPoolRepository.class */
public interface ScopedClassPoolRepository {
    void setClassPoolFactory(ScopedClassPoolFactory scopedClassPoolFactory);

    ScopedClassPoolFactory getClassPoolFactory();

    boolean isPrune();

    void setPrune(boolean z);

    ScopedClassPool createScopedClassPool(ClassLoader classLoader, ClassPool classPool);

    ClassPool findClassPool(ClassLoader classLoader);

    ClassPool registerClassLoader(ClassLoader classLoader);

    Map<ClassLoader, ScopedClassPool> getRegisteredCLs();

    void clearUnregisteredClassLoaders();

    void unregisterClassLoader(ClassLoader classLoader);
}
