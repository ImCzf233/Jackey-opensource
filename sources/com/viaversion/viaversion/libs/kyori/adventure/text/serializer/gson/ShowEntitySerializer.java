package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import java.io.IOException;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/ShowEntitySerializer.class */
final class ShowEntitySerializer extends TypeAdapter<HoverEvent.ShowEntity> {
    static final String TYPE = "type";

    /* renamed from: ID */
    static final String f184ID = "id";
    static final String NAME = "name";
    private final Gson gson;

    public static TypeAdapter<HoverEvent.ShowEntity> create(final Gson gson) {
        return new ShowEntitySerializer(gson).nullSafe();
    }

    private ShowEntitySerializer(final Gson gson) {
        this.gson = gson;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public HoverEvent.ShowEntity read(final JsonReader in) throws IOException {
        in.beginObject();
        Key type = null;
        UUID id = null;
        Component name = null;
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (fieldName.equals(TYPE)) {
                type = (Key) this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
            } else if (fieldName.equals(f184ID)) {
                id = UUID.fromString(in.nextString());
            } else if (fieldName.equals(NAME)) {
                name = (Component) this.gson.fromJson(in, SerializerFactory.COMPONENT_TYPE);
            } else {
                in.skipValue();
            }
        }
        if (type == null || id == null) {
            throw new JsonParseException("A show entity hover event needs type and id fields to be deserialized");
        }
        in.endObject();
        return HoverEvent.ShowEntity.m114of(type, id, name);
    }

    public void write(final JsonWriter out, final HoverEvent.ShowEntity value) throws IOException {
        out.beginObject();
        out.name(TYPE);
        this.gson.toJson(value.type(), SerializerFactory.KEY_TYPE, out);
        out.name(f184ID);
        out.value(value.m117id().toString());
        Component name = value.name();
        if (name != null) {
            out.name(NAME);
            this.gson.toJson(name, SerializerFactory.COMPONENT_TYPE, out);
        }
        out.endObject();
    }
}
