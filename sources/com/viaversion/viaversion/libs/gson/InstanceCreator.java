package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/InstanceCreator.class */
public interface InstanceCreator<T> {
    T createInstance(Type type);
}
