package com.viaversion.viaversion.libs.gson;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonNull.class */
public final class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public JsonNull deepCopy() {
        return INSTANCE;
    }

    public int hashCode() {
        return JsonNull.class.hashCode();
    }

    public boolean equals(Object other) {
        return this == other || (other instanceof JsonNull);
    }
}
