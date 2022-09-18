package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.Enchantments;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/items/ItemRewriter.class */
public class ItemRewriter {
    public static Item toClient(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag == null) {
            CompoundTag compoundTag = new CompoundTag();
            tag = compoundTag;
            item.setTag(compoundTag);
        }
        CompoundTag viaVersionTag = new CompoundTag();
        tag.put("ViaRewind1_7_6_10to1_8", viaVersionTag);
        viaVersionTag.put("id", new ShortTag((short) item.identifier()));
        viaVersionTag.put("data", new ShortTag(item.data()));
        CompoundTag display = (CompoundTag) tag.get("display");
        if (display != null && display.contains("Name")) {
            viaVersionTag.put("displayName", new StringTag((String) display.get("Name").getValue()));
        }
        if (display != null && display.contains("Lore")) {
            viaVersionTag.put("lore", new ListTag(((ListTag) display.get("Lore")).getValue()));
        }
        if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
            ListTag enchTag = tag.contains("ench") ? (ListTag) tag.get("ench") : (ListTag) tag.get("StoredEnchantments");
            List<Tag> lore = new ArrayList<>();
            Iterator it = new ArrayList(enchTag.getValue()).iterator();
            while (it.hasNext()) {
                Tag ench = (Tag) it.next();
                short id = ((NumberTag) ((CompoundTag) ench).get("id")).asShort();
                short lvl = ((NumberTag) ((CompoundTag) ench).get("lvl")).asShort();
                if (id == 8) {
                    enchTag.remove(ench);
                    String s = "§r§7Depth Strider " + Enchantments.ENCHANTMENTS.getOrDefault(Short.valueOf(lvl), "enchantment.level." + ((int) lvl));
                    lore.add(new StringTag(s));
                }
            }
            if (!lore.isEmpty()) {
                if (display == null) {
                    CompoundTag compoundTag2 = new CompoundTag();
                    display = compoundTag2;
                    tag.put("display", compoundTag2);
                    viaVersionTag.put("noDisplay", new ByteTag());
                }
                ListTag loreTag = (ListTag) display.get("Lore");
                if (loreTag == null) {
                    ListTag listTag = new ListTag(StringTag.class);
                    loreTag = listTag;
                    display.put("Lore", listTag);
                }
                lore.addAll(loreTag.getValue());
                loreTag.setValue(lore);
            }
        }
        if (item.identifier() == 387 && tag.contains("pages")) {
            ListTag pages = (ListTag) tag.get("pages");
            ListTag oldPages = new ListTag(StringTag.class);
            viaVersionTag.put("pages", oldPages);
            for (int i = 0; i < pages.size(); i++) {
                StringTag page = (StringTag) pages.get(i);
                String value = page.getValue();
                oldPages.add(new StringTag(value));
                page.setValue(ChatUtil.jsonToLegacy(value));
            }
        }
        ReplacementRegistry1_7_6_10to1_8.replace(item);
        if (viaVersionTag.size() == 2 && ((Short) viaVersionTag.get("id").getValue()).shortValue() == item.identifier() && ((Short) viaVersionTag.get("data").getValue()).shortValue() == item.data()) {
            item.tag().remove("ViaRewind1_7_6_10to1_8");
            if (item.tag().isEmpty()) {
                item.setTag(null);
            }
        }
        return item;
    }

    public static Item toServer(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag == null || !item.tag().contains("ViaRewind1_7_6_10to1_8")) {
            return item;
        }
        CompoundTag viaVersionTag = (CompoundTag) tag.remove("ViaRewind1_7_6_10to1_8");
        item.setIdentifier(((Short) viaVersionTag.get("id").getValue()).shortValue());
        item.setData(((Short) viaVersionTag.get("data").getValue()).shortValue());
        if (viaVersionTag.contains("noDisplay")) {
            tag.remove("display");
        }
        if (viaVersionTag.contains("displayName")) {
            CompoundTag display = (CompoundTag) tag.get("display");
            if (display == null) {
                CompoundTag compoundTag = new CompoundTag();
                display = compoundTag;
                tag.put("display", compoundTag);
            }
            StringTag name = (StringTag) display.get("Name");
            if (name == null) {
                display.put("Name", new StringTag((String) viaVersionTag.get("displayName").getValue()));
            } else {
                name.setValue((String) viaVersionTag.get("displayName").getValue());
            }
        } else if (tag.contains("display")) {
            ((CompoundTag) tag.get("display")).remove("Name");
        }
        if (item.identifier() == 387) {
            ListTag oldPages = (ListTag) viaVersionTag.get("pages");
            tag.remove("pages");
            tag.put("pages", oldPages);
        }
        return item;
    }
}
