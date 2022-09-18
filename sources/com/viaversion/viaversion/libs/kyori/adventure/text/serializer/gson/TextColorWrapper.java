package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/TextColorWrapper.class */
public final class TextColorWrapper {
    @Nullable
    final TextColor color;
    @Nullable
    final TextDecoration decoration;
    final boolean reset;

    TextColorWrapper(@Nullable final TextColor color, @Nullable final TextDecoration decoration, final boolean reset) {
        this.color = color;
        this.decoration = decoration;
        this.reset = reset;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/TextColorWrapper$Serializer.class */
    static final class Serializer extends TypeAdapter<TextColorWrapper> {
        static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void write(final JsonWriter out, final TextColorWrapper value) {
            throw new JsonSyntaxException("Cannot write TextColorWrapper instances");
        }

        @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
        public TextColorWrapper read(final JsonReader in) throws IOException {
            String input = in.nextString();
            TextColor color = TextColorSerializer.fromString(input);
            TextDecoration decoration = TextDecoration.NAMES.value(input);
            boolean reset = decoration == null && input.equals("reset");
            if (color == null && decoration == null && !reset) {
                throw new JsonParseException("Don't know how to parse " + input + " at " + in.getPath());
            }
            return new TextColorWrapper(color, decoration, reset);
        }
    }
}
