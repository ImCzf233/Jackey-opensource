package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringIO;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/legacyimpl/NBTLegacyHoverEventSerializerImpl.class */
final class NBTLegacyHoverEventSerializerImpl implements LegacyHoverEventSerializer {
    static final NBTLegacyHoverEventSerializerImpl INSTANCE = new NBTLegacyHoverEventSerializerImpl();
    private static final TagStringIO SNBT_IO = TagStringIO.get();
    private static final Codec<CompoundBinaryTag, String, IOException, IOException> SNBT_CODEC;
    static final String ITEM_TYPE = "id";
    static final String ITEM_COUNT = "Count";
    static final String ITEM_TAG = "tag";
    static final String ENTITY_NAME = "name";
    static final String ENTITY_TYPE = "type";
    static final String ENTITY_ID = "id";

    static {
        TagStringIO tagStringIO = SNBT_IO;
        Objects.requireNonNull(tagStringIO);
        Codec.Decoder decoder = this::asCompound;
        TagStringIO tagStringIO2 = SNBT_IO;
        Objects.requireNonNull(tagStringIO2);
        SNBT_CODEC = Codec.m102of(decoder, this::asString);
    }

    private NBTLegacyHoverEventSerializerImpl() {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer
    public HoverEvent.ShowItem deserializeShowItem(@NotNull final Component input) throws IOException {
        assertTextComponent(input);
        CompoundBinaryTag contents = SNBT_CODEC.decode(((TextComponent) input).content());
        CompoundBinaryTag tag = contents.getCompound(ITEM_TAG);
        return HoverEvent.ShowItem.m110of(Key.key(contents.getString("id")), contents.getByte(ITEM_COUNT, (byte) 1), tag == CompoundBinaryTag.empty() ? null : BinaryTagHolder.encode(tag, SNBT_CODEC));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer
    public HoverEvent.ShowEntity deserializeShowEntity(@NotNull final Component input, final Codec.Decoder<Component, String, ? extends RuntimeException> componentCodec) throws IOException {
        assertTextComponent(input);
        CompoundBinaryTag contents = SNBT_CODEC.decode(((TextComponent) input).content());
        return HoverEvent.ShowEntity.m114of(Key.key(contents.getString(ENTITY_TYPE)), UUID.fromString(contents.getString("id")), componentCodec.decode(contents.getString(ENTITY_NAME)));
    }

    private static void assertTextComponent(final Component component) {
        if (!(component instanceof TextComponent) || !component.children().isEmpty()) {
            throw new IllegalArgumentException("Legacy events must be single Component instances");
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer
    @NotNull
    public Component serializeShowItem(final HoverEvent.ShowItem input) throws IOException {
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder().putString("id", input.item().asString()).putByte(ITEM_COUNT, (byte) input.count());
        BinaryTagHolder nbt = input.nbt();
        if (nbt != null) {
            builder.put(ITEM_TAG, (BinaryTag) nbt.get(SNBT_CODEC));
        }
        return Component.text(SNBT_CODEC.encode(builder.build()));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer
    @NotNull
    public Component serializeShowEntity(final HoverEvent.ShowEntity input, final Codec.Encoder<Component, String, ? extends RuntimeException> componentCodec) throws IOException {
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder().putString("id", input.m117id().toString()).putString(ENTITY_TYPE, input.type().asString());
        Component name = input.name();
        if (name != null) {
            builder.putString(ENTITY_NAME, componentCodec.encode(name));
        }
        return Component.text(SNBT_CODEC.encode(builder.build()));
    }
}
