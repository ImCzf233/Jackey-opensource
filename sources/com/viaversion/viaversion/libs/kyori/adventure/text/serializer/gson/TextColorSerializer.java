package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/TextColorSerializer.class */
final class TextColorSerializer extends TypeAdapter<TextColor> {
    static final TypeAdapter<TextColor> INSTANCE = new TextColorSerializer(false).nullSafe();
    static final TypeAdapter<TextColor> DOWNSAMPLE_COLOR = new TextColorSerializer(true).nullSafe();
    private final boolean downsampleColor;

    private TextColorSerializer(final boolean downsampleColor) {
        this.downsampleColor = downsampleColor;
    }

    public void write(final JsonWriter out, final TextColor value) throws IOException {
        if (value instanceof NamedTextColor) {
            out.value(NamedTextColor.NAMES.key((NamedTextColor) value));
        } else if (this.downsampleColor) {
            out.value(NamedTextColor.NAMES.key(NamedTextColor.nearestTo(value)));
        } else {
            out.value(value.asHexString());
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    @Nullable
    public TextColor read(final JsonReader in) throws IOException {
        TextColor color = fromString(in.nextString());
        if (color == null) {
            return null;
        }
        return this.downsampleColor ? NamedTextColor.nearestTo(color) : color;
    }

    @Nullable
    public static TextColor fromString(@NotNull final String value) {
        if (value.startsWith("#")) {
            return TextColor.fromHexString(value);
        }
        return NamedTextColor.NAMES.value(value);
    }
}
