package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/ShowItemSerializer.class */
final class ShowItemSerializer extends TypeAdapter<HoverEvent.ShowItem> {

    /* renamed from: ID */
    static final String f185ID = "id";
    static final String COUNT = "count";
    static final String TAG = "tag";
    private final Gson gson;

    public static TypeAdapter<HoverEvent.ShowItem> create(final Gson gson) {
        return new ShowItemSerializer(gson).nullSafe();
    }

    private ShowItemSerializer(final Gson gson) {
        this.gson = gson;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public HoverEvent.ShowItem read(final JsonReader in) throws IOException {
        in.beginObject();
        Key key = null;
        int count = 1;
        BinaryTagHolder nbt = null;
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (fieldName.equals(f185ID)) {
                key = (Key) this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
            } else if (fieldName.equals(COUNT)) {
                count = in.nextInt();
            } else if (fieldName.equals(TAG)) {
                JsonToken token = in.peek();
                if (token == JsonToken.STRING || token == JsonToken.NUMBER) {
                    nbt = BinaryTagHolder.m125of(in.nextString());
                } else if (token == JsonToken.BOOLEAN) {
                    nbt = BinaryTagHolder.m125of(String.valueOf(in.nextBoolean()));
                } else if (token == JsonToken.NULL) {
                    in.nextNull();
                } else {
                    throw new JsonParseException("Expected tag to be a string");
                }
            } else {
                in.skipValue();
            }
        }
        if (key == null) {
            throw new JsonParseException("Not sure how to deserialize show_item hover event");
        }
        in.endObject();
        return HoverEvent.ShowItem.m110of(key, count, nbt);
    }

    public void write(final JsonWriter out, final HoverEvent.ShowItem value) throws IOException {
        out.beginObject();
        out.name(f185ID);
        this.gson.toJson(value.item(), SerializerFactory.KEY_TYPE, out);
        int count = value.count();
        if (count != 1) {
            out.name(COUNT);
            out.value(count);
        }
        BinaryTagHolder nbt = value.nbt();
        if (nbt != null) {
            out.name(TAG);
            out.value(nbt.string());
        }
        out.endObject();
    }
}
