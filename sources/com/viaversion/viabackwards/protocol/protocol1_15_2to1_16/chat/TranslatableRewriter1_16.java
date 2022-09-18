package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/chat/TranslatableRewriter1_16.class */
public class TranslatableRewriter1_16 extends TranslatableRewriter {
    private static final ChatColor[] COLORS = {new ChatColor("black", 0), new ChatColor("dark_blue", 170), new ChatColor("dark_green", 43520), new ChatColor("dark_aqua", 43690), new ChatColor("dark_red", 11141120), new ChatColor("dark_purple", 11141290), new ChatColor("gold", 16755200), new ChatColor("gray", 11184810), new ChatColor("dark_gray", 5592405), new ChatColor("blue", 5592575), new ChatColor("green", 5635925), new ChatColor("aqua", 5636095), new ChatColor("red", 16733525), new ChatColor("light_purple", 16733695), new ChatColor("yellow", 16777045), new ChatColor("white", 16777215)};

    public TranslatableRewriter1_16(BackwardsProtocol protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
    public void processText(JsonElement value) {
        super.processText(value);
        if (value == null || !value.isJsonObject()) {
            return;
        }
        JsonObject object = value.getAsJsonObject();
        JsonPrimitive color = object.getAsJsonPrimitive("color");
        if (color != null) {
            String colorName = color.getAsString();
            if (!colorName.isEmpty() && colorName.charAt(0) == '#') {
                int rgb = Integer.parseInt(colorName.substring(1), 16);
                String closestChatColor = getClosestChatColor(rgb);
                object.addProperty("color", closestChatColor);
            }
        }
        JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
        if (hoverEvent != null) {
            try {
                Component component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(object);
                JsonObject processedHoverEvent = ((JsonObject) ChatRewriter.HOVER_GSON_SERIALIZER.serializeToTree(component)).getAsJsonObject("hoverEvent");
                processedHoverEvent.remove("contents");
                object.add("hoverEvent", processedHoverEvent);
            } catch (Exception e) {
                ViaBackwards.getPlatform().getLogger().severe("Error converting hover event component: " + object);
                e.printStackTrace();
            }
        }
    }

    private String getClosestChatColor(int rgb) {
        ChatColor[] chatColorArr;
        int r = (rgb >> 16) & 255;
        int g = (rgb >> 8) & 255;
        int b = rgb & 255;
        ChatColor closest = null;
        int smallestDiff = 0;
        for (ChatColor color : COLORS) {
            if (color.rgb == rgb) {
                return color.colorName;
            }
            int rAverage = (color.f21r + r) / 2;
            int rDiff = color.f21r - r;
            int gDiff = color.f22g - g;
            int bDiff = color.f23b - b;
            int diff = ((2 + (rAverage >> 8)) * rDiff * rDiff) + (4 * gDiff * gDiff) + ((2 + ((255 - rAverage) >> 8)) * bDiff * bDiff);
            if (closest == null || diff < smallestDiff) {
                closest = color;
                smallestDiff = diff;
            }
        }
        return closest.colorName;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/chat/TranslatableRewriter1_16$ChatColor.class */
    public static final class ChatColor {
        private final String colorName;
        private final int rgb;

        /* renamed from: r */
        private final int f21r;

        /* renamed from: g */
        private final int f22g;

        /* renamed from: b */
        private final int f23b;

        ChatColor(String colorName, int rgb) {
            this.colorName = colorName;
            this.rgb = rgb;
            this.f21r = (rgb >> 16) & 255;
            this.f22g = (rgb >> 8) & 255;
            this.f23b = rgb & 255;
        }
    }
}
