package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.annotations.JsonAdapter;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/bind/JsonAdapterAnnotationTypeAdapterFactory.class */
public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> targetType) {
        Class<? super T> rawType = targetType.getRawType();
        JsonAdapter annotation = (JsonAdapter) rawType.getAnnotation(JsonAdapter.class);
        if (annotation == null) {
            return null;
        }
        return (TypeAdapter<T>) getTypeAdapter(this.constructorConstructor, gson, targetType, annotation);
    }

    public TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson, TypeToken<?> type, JsonAdapter annotation) {
        TypeAdapter<?> typeAdapter;
        JsonSerializer<?> jsonSerializer;
        JsonDeserializer<?> jsonDeserializer;
        Object instance = constructorConstructor.get(TypeToken.get((Class) annotation.value())).construct();
        if (instance instanceof TypeAdapter) {
            typeAdapter = (TypeAdapter) instance;
        } else if (instance instanceof TypeAdapterFactory) {
            typeAdapter = ((TypeAdapterFactory) instance).create(gson, type);
        } else if ((instance instanceof JsonSerializer) || (instance instanceof JsonDeserializer)) {
            if (instance instanceof JsonSerializer) {
                jsonSerializer = (JsonSerializer) instance;
            } else {
                jsonSerializer = null;
            }
            JsonSerializer<?> serializer = jsonSerializer;
            if (instance instanceof JsonDeserializer) {
                jsonDeserializer = (JsonDeserializer) instance;
            } else {
                jsonDeserializer = null;
            }
            JsonDeserializer<?> deserializer = jsonDeserializer;
            typeAdapter = new TreeTypeAdapter<>(serializer, deserializer, gson, type, null);
        } else {
            throw new IllegalArgumentException("Invalid attempt to bind an instance of " + instance.getClass().getName() + " as a @JsonAdapter for " + type.toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
        }
        if (typeAdapter != null && annotation.nullSafe()) {
            typeAdapter = typeAdapter.nullSafe();
        }
        return typeAdapter;
    }
}
