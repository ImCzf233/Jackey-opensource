package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonDeserializationContext.class */
public interface JsonDeserializationContext {
    <T> T deserialize(JsonElement jsonElement, Type type) throws JsonParseException;
}
