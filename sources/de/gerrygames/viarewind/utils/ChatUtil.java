package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import de.gerrygames.viarewind.ViaRewind;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/ChatUtil.class */
public class ChatUtil {
    private static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");

    public static String jsonToLegacy(String json) {
        if (json == null || json.equals(Configurator.NULL) || json.isEmpty()) {
            return "";
        }
        try {
            String legacy = LegacyComponentSerializer.legacySection().serialize(ChatRewriter.HOVER_GSON_SERIALIZER.deserialize(json));
            while (legacy.startsWith("§f")) {
                legacy = legacy.substring(2);
            }
            return legacy;
        } catch (Exception ex) {
            ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + json, (Throwable) ex);
            return "";
        }
    }

    public static String jsonToLegacy(JsonElement component) {
        if (!component.isJsonNull()) {
            if (component.isJsonArray() && component.getAsJsonArray().isEmpty()) {
                return "";
            }
            if (component.isJsonObject() && component.getAsJsonObject().size() == 0) {
                return "";
            }
            if (component.isJsonPrimitive()) {
                return component.getAsString();
            }
            return jsonToLegacy(component.toString());
        }
        return "";
    }

    public static String legacyToJson(String legacy) {
        return legacy == null ? "" : GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(legacy));
    }

    public static String removeUnusedColor(String legacy, char last) {
        if (legacy == null) {
            return null;
        }
        String legacy2 = UNUSED_COLOR_PATTERN.matcher(legacy).replaceAll("$1$2");
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < legacy2.length()) {
            char current = legacy2.charAt(i);
            if (current != 167 || i == legacy2.length() - 1) {
                builder.append(current);
            } else {
                i++;
                char current2 = legacy2.charAt(i);
                if (current2 != last) {
                    builder.append((char) 167).append(current2);
                    last = current2;
                }
            }
            i++;
        }
        return builder.toString();
    }
}
