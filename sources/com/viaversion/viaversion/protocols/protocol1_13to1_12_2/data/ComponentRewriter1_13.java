package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.io.IOException;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/ComponentRewriter1_13.class */
public class ComponentRewriter1_13 extends ComponentRewriter {
    public ComponentRewriter1_13(Protocol protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
    public void handleHoverEvent(JsonObject hoverEvent) {
        JsonElement value;
        String text;
        super.handleHoverEvent(hoverEvent);
        String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
        if (!action.equals("show_item") || (value = hoverEvent.get("value")) == null || (text = findItemNBT(value)) == null) {
            return;
        }
        try {
            CompoundTag tag = BinaryTagIO.readString(text);
            CompoundTag itemTag = (CompoundTag) tag.get("tag");
            ShortTag damageTag = (ShortTag) tag.get("Damage");
            short damage = damageTag != null ? damageTag.asShort() : (short) 0;
            Item item = new DataItem();
            item.setData(damage);
            item.setTag(itemTag);
            this.protocol.getItemRewriter().handleItemToClient(item);
            if (damage != item.data()) {
                tag.put("Damage", new ShortTag(item.data()));
            }
            if (itemTag != null) {
                tag.put("tag", itemTag);
            }
            JsonArray array = new JsonArray();
            JsonObject object = new JsonObject();
            array.add(object);
            try {
                String serializedNBT = BinaryTagIO.writeString(tag);
                object.addProperty("text", serializedNBT);
                hoverEvent.add("value", array);
            } catch (IOException e) {
                Via.getPlatform().getLogger().warning("Error writing NBT in show_item:" + text);
                e.printStackTrace();
            }
        } catch (Exception e2) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Error reading NBT in show_item:" + text);
                e2.printStackTrace();
            }
        }
    }

    protected String findItemNBT(JsonElement element) {
        if (element.isJsonArray()) {
            Iterator<JsonElement> it = element.getAsJsonArray().iterator();
            while (it.hasNext()) {
                JsonElement jsonElement = it.next();
                String value = findItemNBT(jsonElement);
                if (value != null) {
                    return value;
                }
            }
            return null;
        } else if (!element.isJsonObject()) {
            if (element.isJsonPrimitive()) {
                return element.getAsJsonPrimitive().getAsString();
            }
            return null;
        } else {
            JsonPrimitive text = element.getAsJsonObject().getAsJsonPrimitive("text");
            if (text != null) {
                return text.getAsString();
            }
            return null;
        }
    }

    @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
    public void handleTranslate(JsonObject object, String translate) {
        super.handleTranslate(object, translate);
        String newTranslate = Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().get(translate);
        if (newTranslate == null) {
            newTranslate = Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(translate);
        }
        if (newTranslate != null) {
            object.addProperty("translate", newTranslate);
        }
    }
}
