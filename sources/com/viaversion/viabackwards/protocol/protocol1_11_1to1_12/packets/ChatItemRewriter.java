package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.util.Iterator;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/packets/ChatItemRewriter.class */
public class ChatItemRewriter {
    public static void toClient(JsonElement element, UserConnection user) {
        if (!(element instanceof JsonObject)) {
            if (element instanceof JsonArray) {
                JsonArray array = (JsonArray) element;
                Iterator<JsonElement> it = array.iterator();
                while (it.hasNext()) {
                    toClient(it.next(), user);
                }
                return;
            }
            return;
        }
        JsonObject obj = (JsonObject) element;
        if (obj.has("hoverEvent")) {
            if (obj.get("hoverEvent") instanceof JsonObject) {
                JsonObject hoverEvent = (JsonObject) obj.get("hoverEvent");
                if (hoverEvent.has("action") && hoverEvent.has("value")) {
                    String type = hoverEvent.get("action").getAsString();
                    if (type.equals("show_item") || type.equals("show_entity")) {
                        JsonElement value = hoverEvent.get("value");
                        if (value.isJsonArray()) {
                            JsonArray newArray = new JsonArray();
                            Iterator<JsonElement> it2 = value.getAsJsonArray().iterator();
                            while (it2.hasNext()) {
                                JsonElement valueElement = it2.next();
                                if (valueElement.isJsonPrimitive() && valueElement.getAsJsonPrimitive().isString()) {
                                    String newValue = 0 + CallSiteDescriptor.TOKEN_DELIMITER + valueElement.getAsString();
                                    newArray.add(new JsonPrimitive(newValue));
                                }
                            }
                            hoverEvent.add("value", newArray);
                        }
                    }
                }
            }
        } else if (obj.has("extra")) {
            toClient(obj.get("extra"), user);
        }
    }
}
