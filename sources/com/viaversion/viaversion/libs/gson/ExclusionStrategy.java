package com.viaversion.viaversion.libs.gson;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/ExclusionStrategy.class */
public interface ExclusionStrategy {
    boolean shouldSkipField(FieldAttributes fieldAttributes);

    boolean shouldSkipClass(Class<?> cls);
}
