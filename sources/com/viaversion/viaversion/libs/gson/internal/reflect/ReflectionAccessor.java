package com.viaversion.viaversion.libs.gson.internal.reflect;

import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import java.lang.reflect.AccessibleObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/reflect/ReflectionAccessor.class */
public abstract class ReflectionAccessor {
    private static final ReflectionAccessor instance;

    public abstract void makeAccessible(AccessibleObject accessibleObject);

    static {
        instance = JavaVersion.getMajorJavaVersion() < 9 ? new PreJava9ReflectionAccessor() : new UnsafeReflectionAccessor();
    }

    public static ReflectionAccessor getInstance() {
        return instance;
    }
}
