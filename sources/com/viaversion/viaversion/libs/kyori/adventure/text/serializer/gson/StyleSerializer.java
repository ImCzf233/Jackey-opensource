package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/StyleSerializer.class */
final class StyleSerializer extends TypeAdapter<Style> {
    private static final TextDecoration[] DECORATIONS;
    static final String FONT = "font";
    static final String COLOR = "color";
    static final String INSERTION = "insertion";
    static final String CLICK_EVENT = "clickEvent";
    static final String CLICK_EVENT_ACTION = "action";
    static final String CLICK_EVENT_VALUE = "value";
    static final String HOVER_EVENT = "hoverEvent";
    static final String HOVER_EVENT_ACTION = "action";
    static final String HOVER_EVENT_CONTENTS = "contents";
    @Deprecated
    static final String HOVER_EVENT_VALUE = "value";
    private final LegacyHoverEventSerializer legacyHover;
    private final boolean emitLegacyHover;
    private final Gson gson;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        TextDecoration[] textDecorationArr;
        $assertionsDisabled = !StyleSerializer.class.desiredAssertionStatus();
        DECORATIONS = new TextDecoration[]{TextDecoration.BOLD, TextDecoration.ITALIC, TextDecoration.UNDERLINED, TextDecoration.STRIKETHROUGH, TextDecoration.OBFUSCATED};
        Set<TextDecoration> knownDecorations = EnumSet.allOf(TextDecoration.class);
        for (TextDecoration decoration : DECORATIONS) {
            knownDecorations.remove(decoration);
        }
        if (!knownDecorations.isEmpty()) {
            throw new IllegalStateException("Gson serializer is missing some text decorations: " + knownDecorations);
        }
    }

    public static TypeAdapter<Style> create(@Nullable final LegacyHoverEventSerializer legacyHover, final boolean emitLegacyHover, final Gson gson) {
        return new StyleSerializer(legacyHover, emitLegacyHover, gson).nullSafe();
    }

    private StyleSerializer(@Nullable final LegacyHoverEventSerializer legacyHover, final boolean emitLegacyHover, final Gson gson) {
        this.legacyHover = legacyHover;
        this.emitLegacyHover = emitLegacyHover;
        this.gson = gson;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public Style read(final JsonReader in) throws IOException {
        JsonPrimitive serializedAction;
        Object value;
        in.beginObject();
        Style.Builder style = Style.style();
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (fieldName.equals(FONT)) {
                style.font((Key) this.gson.fromJson(in, SerializerFactory.KEY_TYPE));
            } else if (fieldName.equals(COLOR)) {
                TextColorWrapper color = (TextColorWrapper) this.gson.fromJson(in, SerializerFactory.COLOR_WRAPPER_TYPE);
                if (color.color != null) {
                    style.color(color.color);
                } else if (color.decoration != null) {
                    style.decoration(color.decoration, TextDecoration.State.TRUE);
                }
            } else if (TextDecoration.NAMES.keys().contains(fieldName)) {
                style.decoration(TextDecoration.NAMES.value(fieldName), readBoolean(in));
            } else if (fieldName.equals(INSERTION)) {
                style.insertion(in.nextString());
            } else if (fieldName.equals(CLICK_EVENT)) {
                in.beginObject();
                ClickEvent.Action action = null;
                String value2 = null;
                while (in.hasNext()) {
                    String clickEventField = in.nextName();
                    if (clickEventField.equals("action")) {
                        action = (ClickEvent.Action) this.gson.fromJson(in, SerializerFactory.CLICK_ACTION_TYPE);
                    } else if (clickEventField.equals("value")) {
                        value2 = in.peek() == JsonToken.NULL ? null : in.nextString();
                    } else {
                        in.skipValue();
                    }
                }
                if (action != null && action.readable() && value2 != null) {
                    style.clickEvent(ClickEvent.clickEvent(action, value2));
                }
                in.endObject();
            } else if (fieldName.equals(HOVER_EVENT)) {
                JsonObject hoverEventObject = (JsonObject) this.gson.fromJson(in, JsonObject.class);
                if (hoverEventObject != null && (serializedAction = hoverEventObject.getAsJsonPrimitive("action")) != null) {
                    HoverEvent.Action<Object> action2 = (HoverEvent.Action) this.gson.fromJson((JsonElement) serializedAction, (Class<Object>) SerializerFactory.HOVER_ACTION_TYPE);
                    if (action2.readable()) {
                        if (hoverEventObject.has(HOVER_EVENT_CONTENTS)) {
                            JsonElement rawValue = hoverEventObject.get(HOVER_EVENT_CONTENTS);
                            Class<?> actionType = action2.type();
                            if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(actionType)) {
                                value = this.gson.fromJson(rawValue, (Class<Object>) SerializerFactory.COMPONENT_TYPE);
                            } else if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(actionType)) {
                                value = this.gson.fromJson(rawValue, (Class<Object>) SerializerFactory.SHOW_ITEM_TYPE);
                            } else if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(actionType)) {
                                value = this.gson.fromJson(rawValue, (Class<Object>) SerializerFactory.SHOW_ENTITY_TYPE);
                            } else {
                                value = null;
                            }
                        } else if (hoverEventObject.has("value")) {
                            value = legacyHoverEventContents(action2, (Component) this.gson.fromJson(hoverEventObject.get("value"), (Class<Object>) SerializerFactory.COMPONENT_TYPE));
                        } else {
                            value = null;
                        }
                        if (value != null) {
                            style.hoverEvent(HoverEvent.hoverEvent(action2, value));
                        }
                    }
                }
            } else {
                in.skipValue();
            }
        }
        in.endObject();
        return style.build();
    }

    private boolean readBoolean(final JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        if (peek == JsonToken.BOOLEAN) {
            return in.nextBoolean();
        }
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            return Boolean.parseBoolean(in.nextString());
        }
        throw new JsonParseException("Token of type " + peek + " cannot be interpreted as a boolean");
    }

    private Object legacyHoverEventContents(final HoverEvent.Action<?> action, final Component rawValue) {
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return rawValue;
        }
        if (this.legacyHover != null) {
            try {
                if (action == HoverEvent.Action.SHOW_ENTITY) {
                    return this.legacyHover.deserializeShowEntity(rawValue, decoder());
                }
                if (action == HoverEvent.Action.SHOW_ITEM) {
                    return this.legacyHover.deserializeShowItem(rawValue);
                }
            } catch (IOException ex) {
                throw new JsonParseException(ex);
            }
        }
        throw new UnsupportedOperationException();
    }

    private Codec.Decoder<Component, String, JsonParseException> decoder() {
        return string -> {
            return (Component) this.gson.fromJson(string, (Class<Object>) SerializerFactory.COMPONENT_TYPE);
        };
    }

    private Codec.Encoder<Component, String, JsonParseException> encoder() {
        return component -> {
            return this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE);
        };
    }

    public void write(final JsonWriter out, final Style value) throws IOException {
        out.beginObject();
        int length = DECORATIONS.length;
        for (int i = 0; i < length; i++) {
            TextDecoration decoration = DECORATIONS[i];
            TextDecoration.State state = value.decoration(decoration);
            if (state != TextDecoration.State.NOT_SET) {
                String name = TextDecoration.NAMES.key(decoration);
                if (!$assertionsDisabled && name == null) {
                    throw new AssertionError();
                }
                out.name(name);
                out.value(state == TextDecoration.State.TRUE);
            }
        }
        TextColor color = value.color();
        if (color != null) {
            out.name(COLOR);
            this.gson.toJson(color, SerializerFactory.COLOR_TYPE, out);
        }
        String insertion = value.insertion();
        if (insertion != null) {
            out.name(INSERTION);
            out.value(insertion);
        }
        ClickEvent clickEvent = value.clickEvent();
        if (clickEvent != null) {
            out.name(CLICK_EVENT);
            out.beginObject();
            out.name("action");
            this.gson.toJson(clickEvent.action(), SerializerFactory.CLICK_ACTION_TYPE, out);
            out.name("value");
            out.value(clickEvent.value());
            out.endObject();
        }
        HoverEvent<?> hoverEvent = value.hoverEvent();
        if (hoverEvent != null) {
            out.name(HOVER_EVENT);
            out.beginObject();
            out.name("action");
            HoverEvent.Action<?> action = hoverEvent.action();
            this.gson.toJson(action, SerializerFactory.HOVER_ACTION_TYPE, out);
            out.name(HOVER_EVENT_CONTENTS);
            if (action == HoverEvent.Action.SHOW_ITEM) {
                this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ITEM_TYPE, out);
            } else if (action == HoverEvent.Action.SHOW_ENTITY) {
                this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ENTITY_TYPE, out);
            } else if (action == HoverEvent.Action.SHOW_TEXT) {
                this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
            } else {
                throw new JsonParseException("Don't know how to serialize " + hoverEvent.value());
            }
            if (this.emitLegacyHover) {
                out.name("value");
                serializeLegacyHoverEvent(hoverEvent, out);
            }
            out.endObject();
        }
        Key font = value.font();
        if (font != null) {
            out.name(FONT);
            this.gson.toJson(font, SerializerFactory.KEY_TYPE, out);
        }
        out.endObject();
    }

    private void serializeLegacyHoverEvent(final HoverEvent<?> hoverEvent, final JsonWriter out) throws IOException {
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
        } else if (this.legacyHover != null) {
            Component serialized = null;
            try {
                if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
                    serialized = this.legacyHover.serializeShowEntity((HoverEvent.ShowEntity) hoverEvent.value(), encoder());
                } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
                    serialized = this.legacyHover.serializeShowItem((HoverEvent.ShowItem) hoverEvent.value());
                }
                if (serialized != null) {
                    this.gson.toJson(serialized, SerializerFactory.COMPONENT_TYPE, out);
                } else {
                    out.nullValue();
                }
            } catch (IOException ex) {
                throw new JsonSyntaxException(ex);
            }
        } else {
            out.nullValue();
        }
    }
}
