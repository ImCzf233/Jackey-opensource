package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl.NBTLegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/ChatRewriter.class */
public final class ChatRewriter {
    public static final GsonComponentSerializer HOVER_GSON_SERIALIZER = GsonComponentSerializer.builder().emitLegacyHoverEvent().legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.get()).build();

    public static String legacyTextToJsonString(String message, boolean itemData) {
        TextComponent deserialize = LegacyComponentSerializer.legacySection().deserialize(message);
        if (itemData) {
            deserialize = Component.text().decoration(TextDecoration.ITALIC, false).append((Component) deserialize).build();
        }
        return GsonComponentSerializer.gson().serialize(deserialize);
    }

    public static String legacyTextToJsonString(String legacyText) {
        return legacyTextToJsonString(legacyText, false);
    }

    public static JsonElement legacyTextToJson(String legacyText) {
        return JsonParser.parseString(legacyTextToJsonString(legacyText, false));
    }

    public static String jsonToLegacyText(String value) {
        try {
            Component component = HOVER_GSON_SERIALIZER.deserialize(value);
            return LegacyComponentSerializer.legacySection().serialize(component);
        } catch (Exception e) {
            Via.getPlatform().getLogger().warning("Error converting json text to legacy: " + value);
            e.printStackTrace();
            return "";
        }
    }

    @Deprecated
    public static void processTranslate(JsonElement value) {
        ((Protocol1_13To1_12_2) Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class)).getComponentRewriter().processText(value);
    }
}
