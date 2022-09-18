package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data.AchievementTranslationMapping;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_12to1_11_1/TranslateRewriter.class */
public class TranslateRewriter {
    private static final ComponentRewriter achievementTextRewriter = new ComponentRewriter() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.TranslateRewriter.1
        @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
        public void handleTranslate(JsonObject object, String translate) {
            String text = AchievementTranslationMapping.get(translate);
            if (text != null) {
                object.addProperty("translate", text);
            }
        }

        @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
        public void handleHoverEvent(JsonObject hoverEvent) {
            String textValue;
            String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
            if (!action.equals("show_achievement")) {
                super.handleHoverEvent(hoverEvent);
                return;
            }
            JsonElement value = hoverEvent.get("value");
            if (value.isJsonObject()) {
                textValue = value.getAsJsonObject().get("text").getAsString();
            } else {
                textValue = value.getAsJsonPrimitive().getAsString();
            }
            if (AchievementTranslationMapping.get(textValue) == null) {
                JsonObject invalidText = new JsonObject();
                invalidText.addProperty("text", "Invalid statistic/achievement!");
                invalidText.addProperty("color", "red");
                hoverEvent.addProperty("action", "show_text");
                hoverEvent.add("value", invalidText);
                super.handleHoverEvent(hoverEvent);
                return;
            }
            try {
                JsonObject newLine = new JsonObject();
                newLine.addProperty("text", "\n");
                JsonArray baseArray = new JsonArray();
                baseArray.add("");
                JsonObject namePart = new JsonObject();
                JsonObject typePart = new JsonObject();
                baseArray.add(namePart);
                baseArray.add(newLine);
                baseArray.add(typePart);
                if (textValue.startsWith("achievement")) {
                    namePart.addProperty("translate", textValue);
                    namePart.addProperty("color", AchievementTranslationMapping.isSpecial(textValue) ? "dark_purple" : "green");
                    typePart.addProperty("translate", "stats.tooltip.type.achievement");
                    JsonObject description = new JsonObject();
                    typePart.addProperty("italic", (Boolean) true);
                    description.addProperty("translate", value + ".desc");
                    baseArray.add(newLine);
                    baseArray.add(description);
                } else if (textValue.startsWith("stat")) {
                    namePart.addProperty("translate", textValue);
                    namePart.addProperty("color", "gray");
                    typePart.addProperty("translate", "stats.tooltip.type.statistic");
                    typePart.addProperty("italic", (Boolean) true);
                }
                hoverEvent.addProperty("action", "show_text");
                hoverEvent.add("value", baseArray);
            } catch (Exception e) {
                Via.getPlatform().getLogger().warning("Error rewriting show_achievement: " + hoverEvent);
                e.printStackTrace();
                JsonObject invalidText2 = new JsonObject();
                invalidText2.addProperty("text", "Invalid statistic/achievement!");
                invalidText2.addProperty("color", "red");
                hoverEvent.addProperty("action", "show_text");
                hoverEvent.add("value", invalidText2);
            }
            super.handleHoverEvent(hoverEvent);
        }
    };

    public static void toClient(JsonElement element, UserConnection user) {
        JsonObject obj;
        JsonElement translate;
        if ((element instanceof JsonObject) && (translate = (obj = (JsonObject) element).get("translate")) != null && translate.getAsString().startsWith("chat.type.achievement")) {
            achievementTextRewriter.processText(obj);
        }
    }
}
