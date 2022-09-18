package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.reflect.TypeToken;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/TypeAdapterFactory.class */
public interface TypeAdapterFactory {
    <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);
}
