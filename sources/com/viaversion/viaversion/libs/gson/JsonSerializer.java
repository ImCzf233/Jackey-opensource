package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonSerializer.class */
public interface JsonSerializer<T> {
    JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext);
}
