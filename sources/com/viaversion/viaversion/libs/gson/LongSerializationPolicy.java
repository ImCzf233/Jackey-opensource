package com.viaversion.viaversion.libs.gson;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/LongSerializationPolicy.class */
public enum LongSerializationPolicy {
    DEFAULT { // from class: com.viaversion.viaversion.libs.gson.LongSerializationPolicy.1
        @Override // com.viaversion.viaversion.libs.gson.LongSerializationPolicy
        public JsonElement serialize(Long value) {
            return new JsonPrimitive(value);
        }
    },
    STRING { // from class: com.viaversion.viaversion.libs.gson.LongSerializationPolicy.2
        @Override // com.viaversion.viaversion.libs.gson.LongSerializationPolicy
        public JsonElement serialize(Long value) {
            return new JsonPrimitive(String.valueOf(value));
        }
    };

    public abstract JsonElement serialize(Long l);
}
