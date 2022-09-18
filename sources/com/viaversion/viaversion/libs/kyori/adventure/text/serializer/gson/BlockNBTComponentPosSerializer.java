package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/BlockNBTComponentPosSerializer.class */
final class BlockNBTComponentPosSerializer extends TypeAdapter<BlockNBTComponent.Pos> {
    static final TypeAdapter<BlockNBTComponent.Pos> INSTANCE = new BlockNBTComponentPosSerializer().nullSafe();

    private BlockNBTComponentPosSerializer() {
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public BlockNBTComponent.Pos read(final JsonReader in) throws IOException {
        String string = in.nextString();
        try {
            return BlockNBTComponent.Pos.fromString(string);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Don't know how to turn " + string + " into a Position");
        }
    }

    public void write(final JsonWriter out, final BlockNBTComponent.Pos value) throws IOException {
        out.value(value.asString());
    }
}
