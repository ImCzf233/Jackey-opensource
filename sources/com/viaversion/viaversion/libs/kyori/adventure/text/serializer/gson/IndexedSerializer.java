package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/IndexedSerializer.class */
final class IndexedSerializer<E> extends TypeAdapter<E> {
    private final String name;
    private final Index<String, E> map;

    /* renamed from: of */
    public static <E> TypeAdapter<E> m104of(final String name, final Index<String, E> map) {
        return new IndexedSerializer(name, map).nullSafe();
    }

    private IndexedSerializer(final String name, final Index<String, E> map) {
        this.name = name;
        this.map = map;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public void write(final JsonWriter out, final E value) throws IOException {
        out.value(this.map.key(value));
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public E read(final JsonReader in) throws IOException {
        String string = in.nextString();
        E value = this.map.value(string);
        if (value != null) {
            return value;
        }
        throw new JsonParseException("invalid " + this.name + ":  " + string);
    }
}
