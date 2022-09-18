package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/KeySerializer.class */
final class KeySerializer extends TypeAdapter<Key> {
    static final TypeAdapter<Key> INSTANCE = new KeySerializer().nullSafe();

    private KeySerializer() {
    }

    public void write(final JsonWriter out, final Key value) throws IOException {
        out.value(value.asString());
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public Key read(final JsonReader in) throws IOException {
        return Key.key(in.nextString());
    }
}
