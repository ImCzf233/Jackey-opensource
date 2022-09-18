package com.viaversion.viaversion.libs.gson.internal.reflect;

import java.lang.reflect.AccessibleObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/reflect/PreJava9ReflectionAccessor.class */
final class PreJava9ReflectionAccessor extends ReflectionAccessor {
    @Override // com.viaversion.viaversion.libs.gson.internal.reflect.ReflectionAccessor
    public void makeAccessible(AccessibleObject ao) {
        ao.setAccessible(true);
    }
}
