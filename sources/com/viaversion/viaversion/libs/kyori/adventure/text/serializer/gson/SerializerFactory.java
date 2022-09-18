package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextColorWrapper;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/SerializerFactory.class */
public final class SerializerFactory implements TypeAdapterFactory {
    static final Class<Key> KEY_TYPE = Key.class;
    static final Class<Component> COMPONENT_TYPE = Component.class;
    static final Class<Style> STYLE_TYPE = Style.class;
    static final Class<ClickEvent.Action> CLICK_ACTION_TYPE = ClickEvent.Action.class;
    static final Class<HoverEvent.Action> HOVER_ACTION_TYPE = HoverEvent.Action.class;
    static final Class<HoverEvent.ShowItem> SHOW_ITEM_TYPE = HoverEvent.ShowItem.class;
    static final Class<HoverEvent.ShowEntity> SHOW_ENTITY_TYPE = HoverEvent.ShowEntity.class;
    static final Class<TextColorWrapper> COLOR_WRAPPER_TYPE = TextColorWrapper.class;
    static final Class<TextColor> COLOR_TYPE = TextColor.class;
    static final Class<TextDecoration> TEXT_DECORATION_TYPE = TextDecoration.class;
    static final Class<BlockNBTComponent.Pos> BLOCK_NBT_POS_TYPE = BlockNBTComponent.Pos.class;
    private final boolean downsampleColors;
    private final LegacyHoverEventSerializer legacyHoverSerializer;
    private final boolean emitLegacyHover;

    public SerializerFactory(final boolean downsampleColors, @Nullable final LegacyHoverEventSerializer legacyHoverSerializer, final boolean emitLegacyHover) {
        this.downsampleColors = downsampleColors;
        this.legacyHoverSerializer = legacyHoverSerializer;
        this.emitLegacyHover = emitLegacyHover;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (COMPONENT_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ComponentSerializerImpl.create(gson);
        }
        if (KEY_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) KeySerializer.INSTANCE;
        }
        if (STYLE_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) StyleSerializer.create(this.legacyHoverSerializer, this.emitLegacyHover, gson);
        }
        if (CLICK_ACTION_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ClickEventActionSerializer.INSTANCE;
        }
        if (HOVER_ACTION_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) HoverEventActionSerializer.INSTANCE;
        }
        if (SHOW_ITEM_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ShowItemSerializer.create(gson);
        }
        if (SHOW_ENTITY_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ShowEntitySerializer.create(gson);
        }
        if (COLOR_WRAPPER_TYPE.isAssignableFrom(rawType)) {
            return TextColorWrapper.Serializer.INSTANCE;
        }
        if (COLOR_TYPE.isAssignableFrom(rawType)) {
            return this.downsampleColors ? (TypeAdapter<T>) TextColorSerializer.DOWNSAMPLE_COLOR : (TypeAdapter<T>) TextColorSerializer.INSTANCE;
        } else if (TEXT_DECORATION_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) TextDecorationSerializer.INSTANCE;
        } else {
            if (BLOCK_NBT_POS_TYPE.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) BlockNBTComponentPosSerializer.INSTANCE;
            }
            return null;
        }
    }
}
