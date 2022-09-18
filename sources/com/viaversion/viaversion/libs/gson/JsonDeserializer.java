package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonDeserializer.class */
public interface JsonDeserializer<T> {
    T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;
}
