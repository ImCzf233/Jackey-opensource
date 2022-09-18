package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonSerializationContext.class */
public interface JsonSerializationContext {
    JsonElement serialize(Object obj);

    JsonElement serialize(Object obj, Type type);
}
