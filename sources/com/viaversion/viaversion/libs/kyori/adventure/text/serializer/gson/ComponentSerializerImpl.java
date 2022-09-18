package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/ComponentSerializerImpl.class */
public final class ComponentSerializerImpl extends TypeAdapter<Component> {
    static final String TEXT = "text";
    static final String TRANSLATE = "translate";
    static final String TRANSLATE_WITH = "with";
    static final String SCORE = "score";
    static final String SCORE_NAME = "name";
    static final String SCORE_OBJECTIVE = "objective";
    static final String SCORE_VALUE = "value";
    static final String SELECTOR = "selector";
    static final String KEYBIND = "keybind";
    static final String EXTRA = "extra";
    static final String NBT = "nbt";
    static final String NBT_INTERPRET = "interpret";
    static final String NBT_BLOCK = "block";
    static final String NBT_ENTITY = "entity";
    static final String NBT_STORAGE = "storage";
    static final String SEPARATOR = "separator";
    static final Type COMPONENT_LIST_TYPE = new TypeToken<List<Component>>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.ComponentSerializerImpl.1
    }.getType();
    private final Gson gson;

    public static TypeAdapter<Component> create(final Gson gson) {
        return new ComponentSerializerImpl(gson).nullSafe();
    }

    private ComponentSerializerImpl(final Gson gson) {
        this.gson = gson;
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    /* renamed from: read */
    public Component read2(final JsonReader in) throws IOException {
        ComponentBuilder<?, ?> builder;
        JsonToken token = in.peek();
        if (token == JsonToken.STRING || token == JsonToken.NUMBER || token == JsonToken.BOOLEAN) {
            return Component.text(readString(in));
        }
        if (token == JsonToken.BEGIN_ARRAY) {
            ComponentBuilder<?, ?> parent = null;
            in.beginArray();
            while (in.hasNext()) {
                BuildableComponent<?, ?> child = read2(in);
                if (parent == null) {
                    parent = child.toBuilder();
                } else {
                    parent.append((Component) child);
                }
            }
            if (parent == null) {
                throw notSureHowToDeserialize(in.getPath());
            }
            in.endArray();
            return parent.build();
        } else if (token != JsonToken.BEGIN_OBJECT) {
            throw notSureHowToDeserialize(in.getPath());
        } else {
            JsonObject style = new JsonObject();
            List<Component> extra = Collections.emptyList();
            String text = null;
            String translate = null;
            List<Component> translateWith = null;
            String scoreName = null;
            String scoreObjective = null;
            String scoreValue = null;
            String selector = null;
            String keybind = null;
            String nbt = null;
            boolean nbtInterpret = false;
            BlockNBTComponent.Pos nbtBlock = null;
            String nbtEntity = null;
            Key nbtStorage = null;
            Component separator = null;
            in.beginObject();
            while (in.hasNext()) {
                String fieldName = in.nextName();
                if (fieldName.equals(TEXT)) {
                    text = readString(in);
                } else if (fieldName.equals(TRANSLATE)) {
                    translate = in.nextString();
                } else if (fieldName.equals(TRANSLATE_WITH)) {
                    translateWith = (List) this.gson.fromJson(in, COMPONENT_LIST_TYPE);
                } else if (fieldName.equals(SCORE)) {
                    in.beginObject();
                    while (in.hasNext()) {
                        String scoreFieldName = in.nextName();
                        if (scoreFieldName.equals(SCORE_NAME)) {
                            scoreName = in.nextString();
                        } else if (scoreFieldName.equals(SCORE_OBJECTIVE)) {
                            scoreObjective = in.nextString();
                        } else if (scoreFieldName.equals("value")) {
                            scoreValue = in.nextString();
                        } else {
                            in.skipValue();
                        }
                    }
                    if (scoreName == null || scoreObjective == null) {
                        throw new JsonParseException("A score component requires a name and objective");
                    }
                    in.endObject();
                } else if (fieldName.equals(SELECTOR)) {
                    selector = in.nextString();
                } else if (fieldName.equals(KEYBIND)) {
                    keybind = in.nextString();
                } else if (fieldName.equals(NBT)) {
                    nbt = in.nextString();
                } else if (fieldName.equals(NBT_INTERPRET)) {
                    nbtInterpret = in.nextBoolean();
                } else if (fieldName.equals(NBT_BLOCK)) {
                    nbtBlock = (BlockNBTComponent.Pos) this.gson.fromJson(in, SerializerFactory.BLOCK_NBT_POS_TYPE);
                } else if (fieldName.equals(NBT_ENTITY)) {
                    nbtEntity = in.nextString();
                } else if (fieldName.equals(NBT_STORAGE)) {
                    nbtStorage = (Key) this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
                } else if (fieldName.equals(EXTRA)) {
                    extra = (List) this.gson.fromJson(in, COMPONENT_LIST_TYPE);
                } else if (fieldName.equals(SEPARATOR)) {
                    separator = read2(in);
                } else {
                    style.add(fieldName, (JsonElement) this.gson.fromJson(in, JsonElement.class));
                }
            }
            if (text != null) {
                builder = Component.text().content(text);
            } else if (translate != null) {
                if (translateWith != null) {
                    builder = Component.translatable().key(translate).args(translateWith);
                } else {
                    builder = Component.translatable().key(translate);
                }
            } else if (scoreName != null && scoreObjective != null) {
                if (scoreValue == null) {
                    builder = Component.score().name(scoreName).objective(scoreObjective);
                } else {
                    builder = Component.score().name(scoreName).objective(scoreObjective).value(scoreValue);
                }
            } else if (selector != null) {
                builder = Component.selector().pattern(selector).separator(separator);
            } else if (keybind != null) {
                builder = Component.keybind().keybind(keybind);
            } else if (nbt != null) {
                if (nbtBlock != null) {
                    builder = ((BlockNBTComponent.Builder) nbt(Component.blockNBT(), nbt, nbtInterpret, separator)).pos(nbtBlock);
                } else if (nbtEntity != null) {
                    builder = ((EntityNBTComponent.Builder) nbt(Component.entityNBT(), nbt, nbtInterpret, separator)).selector(nbtEntity);
                } else if (nbtStorage != null) {
                    builder = ((StorageNBTComponent.Builder) nbt(Component.storageNBT(), nbt, nbtInterpret, separator)).storage(nbtStorage);
                } else {
                    throw notSureHowToDeserialize(in.getPath());
                }
            } else {
                throw notSureHowToDeserialize(in.getPath());
            }
            builder.style((Style) this.gson.fromJson((JsonElement) style, (Class<Object>) SerializerFactory.STYLE_TYPE)).append(extra);
            in.endObject();
            return builder.build();
        }
    }

    private static String readString(final JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            return in.nextString();
        }
        if (peek == JsonToken.BOOLEAN) {
            return String.valueOf(in.nextBoolean());
        }
        throw new JsonParseException("Token of type " + peek + " cannot be interpreted as a string");
    }

    private static <C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> B nbt(final B builder, final String nbt, final boolean interpret, @Nullable final Component separator) {
        return (B) builder.nbtPath(nbt).interpret(interpret).separator(separator);
    }

    public void write(final JsonWriter out, final Component value) throws IOException {
        out.beginObject();
        if (value.hasStyling()) {
            JsonElement style = this.gson.toJsonTree(value.style(), SerializerFactory.STYLE_TYPE);
            if (style.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : style.getAsJsonObject().entrySet()) {
                    out.name(entry.getKey());
                    this.gson.toJson(entry.getValue(), out);
                }
            }
        }
        if (!value.children().isEmpty()) {
            out.name(EXTRA);
            this.gson.toJson(value.children(), COMPONENT_LIST_TYPE, out);
        }
        if (value instanceof TextComponent) {
            out.name(TEXT);
            out.value(((TextComponent) value).content());
        } else if (value instanceof TranslatableComponent) {
            TranslatableComponent translatable = (TranslatableComponent) value;
            out.name(TRANSLATE);
            out.value(translatable.key());
            if (!translatable.args().isEmpty()) {
                out.name(TRANSLATE_WITH);
                this.gson.toJson(translatable.args(), COMPONENT_LIST_TYPE, out);
            }
        } else if (value instanceof ScoreComponent) {
            ScoreComponent score = (ScoreComponent) value;
            out.name(SCORE);
            out.beginObject();
            out.name(SCORE_NAME);
            out.value(score.name());
            out.name(SCORE_OBJECTIVE);
            out.value(score.objective());
            if (score.value() != null) {
                out.name("value");
                out.value(score.value());
            }
            out.endObject();
        } else if (value instanceof SelectorComponent) {
            SelectorComponent selector = (SelectorComponent) value;
            out.name(SELECTOR);
            out.value(selector.pattern());
            serializeSeparator(out, selector.separator());
        } else if (value instanceof KeybindComponent) {
            out.name(KEYBIND);
            out.value(((KeybindComponent) value).keybind());
        } else if (value instanceof NBTComponent) {
            NBTComponent<?, ?> nbt = (NBTComponent) value;
            out.name(NBT);
            out.value(nbt.nbtPath());
            out.name(NBT_INTERPRET);
            out.value(nbt.interpret());
            serializeSeparator(out, nbt.separator());
            if (value instanceof BlockNBTComponent) {
                out.name(NBT_BLOCK);
                this.gson.toJson(((BlockNBTComponent) value).pos(), SerializerFactory.BLOCK_NBT_POS_TYPE, out);
            } else if (value instanceof EntityNBTComponent) {
                out.name(NBT_ENTITY);
                out.value(((EntityNBTComponent) value).selector());
            } else if (value instanceof StorageNBTComponent) {
                out.name(NBT_STORAGE);
                this.gson.toJson(((StorageNBTComponent) value).storage(), SerializerFactory.KEY_TYPE, out);
            } else {
                throw notSureHowToSerialize(value);
            }
        } else {
            throw notSureHowToSerialize(value);
        }
        out.endObject();
    }

    private void serializeSeparator(final JsonWriter out, @Nullable final Component separator) throws IOException {
        if (separator != null) {
            out.name(SEPARATOR);
            write(out, separator);
        }
    }

    public static JsonParseException notSureHowToDeserialize(final Object element) {
        return new JsonParseException("Don't know how to turn " + element + " into a Component");
    }

    private static IllegalArgumentException notSureHowToSerialize(final Component component) {
        return new IllegalArgumentException("Don't know how to serialize " + component + " as a Component");
    }
}
