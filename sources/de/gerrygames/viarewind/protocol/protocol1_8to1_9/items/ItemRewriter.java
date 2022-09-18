package de.gerrygames.viarewind.protocol.protocol1_8to1_9.items;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.utils.Enchantments;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/items/ItemRewriter.class */
public class ItemRewriter {
    private static Map<String, Integer> ENTTIY_NAME_TO_ID;
    private static Map<Integer, String> ENTTIY_ID_TO_NAME;
    private static Map<String, Integer> POTION_NAME_TO_ID;
    private static Map<Integer, String> POTION_ID_TO_NAME;
    private static Map<String, String> POTION_NAME_INDEX = new HashMap();

    static {
        Field[] declaredFields;
        for (Field field : ItemRewriter.class.getDeclaredFields()) {
            try {
                Field other = com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter.class.getDeclaredField(field.getName());
                other.setAccessible(true);
                field.setAccessible(true);
                field.set(null, other.get(null));
            } catch (Exception e) {
            }
        }
        POTION_NAME_TO_ID.put("luck", 8203);
        POTION_NAME_INDEX.put("water", "§rWater Bottle");
        POTION_NAME_INDEX.put("mundane", "§rMundane Potion");
        POTION_NAME_INDEX.put("thick", "§rThick Potion");
        POTION_NAME_INDEX.put("awkward", "§rAwkward Potion");
        POTION_NAME_INDEX.put("water_splash", "§rSplash Water Bottle");
        POTION_NAME_INDEX.put("mundane_splash", "§rMundane Splash Potion");
        POTION_NAME_INDEX.put("thick_splash", "§rThick Splash Potion");
        POTION_NAME_INDEX.put("awkward_splash", "§rAwkward Splash Potion");
        POTION_NAME_INDEX.put("water_lingering", "§rLingering Water Bottle");
        POTION_NAME_INDEX.put("mundane_lingering", "§rMundane Lingering Potion");
        POTION_NAME_INDEX.put("thick_lingering", "§rThick Lingering Potion");
        POTION_NAME_INDEX.put("awkward_lingering", "§rAwkward Lingering Potion");
        POTION_NAME_INDEX.put("night_vision_lingering", "§rLingering Potion of Night Vision");
        POTION_NAME_INDEX.put("long_night_vision_lingering", "§rLingering Potion of Night Vision");
        POTION_NAME_INDEX.put("invisibility_lingering", "§rLingering Potion of Invisibility");
        POTION_NAME_INDEX.put("long_invisibility_lingering", "§rLingering Potion of Invisibility");
        POTION_NAME_INDEX.put("leaping_lingering", "§rLingering Potion of Leaping");
        POTION_NAME_INDEX.put("long_leaping_lingering", "§rLingering Potion of Leaping");
        POTION_NAME_INDEX.put("strong_leaping_lingering", "§rLingering Potion of Leaping");
        POTION_NAME_INDEX.put("fire_resistance_lingering", "§rLingering Potion of Fire Resistance");
        POTION_NAME_INDEX.put("long_fire_resistance_lingering", "§rLingering Potion of Fire Resistance");
        POTION_NAME_INDEX.put("swiftness_lingering", "§rLingering Potion of Swiftness");
        POTION_NAME_INDEX.put("long_swiftness_lingering", "§rLingering Potion of Swiftness");
        POTION_NAME_INDEX.put("strong_swiftness_lingering", "§rLingering Potion of Swiftness");
        POTION_NAME_INDEX.put("slowness_lingering", "§rLingering Potion of Slowness");
        POTION_NAME_INDEX.put("long_slowness_lingering", "§rLingering Potion of Slowness");
        POTION_NAME_INDEX.put("water_breathing_lingering", "§rLingering Potion of Water Breathing");
        POTION_NAME_INDEX.put("long_water_breathing_lingering", "§rLingering Potion of Water Breathing");
        POTION_NAME_INDEX.put("healing_lingering", "§rLingering Potion of Healing");
        POTION_NAME_INDEX.put("strong_healing_lingering", "§rLingering Potion of Healing");
        POTION_NAME_INDEX.put("harming_lingering", "§rLingering Potion of Harming");
        POTION_NAME_INDEX.put("strong_harming_lingering", "§rLingering Potion of Harming");
        POTION_NAME_INDEX.put("poison_lingering", "§rLingering Potion of Poisen");
        POTION_NAME_INDEX.put("long_poison_lingering", "§rLingering Potion of Poisen");
        POTION_NAME_INDEX.put("strong_poison_lingering", "§rLingering Potion of Poisen");
        POTION_NAME_INDEX.put("regeneration_lingering", "§rLingering Potion of Regeneration");
        POTION_NAME_INDEX.put("long_regeneration_lingering", "§rLingering Potion of Regeneration");
        POTION_NAME_INDEX.put("strong_regeneration_lingering", "§rLingering Potion of Regeneration");
        POTION_NAME_INDEX.put("strength_lingering", "§rLingering Potion of Strength");
        POTION_NAME_INDEX.put("long_strength_lingering", "§rLingering Potion of Strength");
        POTION_NAME_INDEX.put("strong_strength_lingering", "§rLingering Potion of Strength");
        POTION_NAME_INDEX.put("weakness_lingering", "§rLingering Potion of Weakness");
        POTION_NAME_INDEX.put("long_weakness_lingering", "§rLingering Potion of Weakness");
        POTION_NAME_INDEX.put("luck_lingering", "§rLingering Potion of Luck");
        POTION_NAME_INDEX.put("luck", "§rPotion of Luck");
        POTION_NAME_INDEX.put("luck_splash", "§rSplash Potion of Luck");
    }

    public static Item toClient(Item item) {
        String str;
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
        tag.put("ViaRewind1_8to1_9", viaVersionTag);
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
                if (id == 70) {
                    str = "§r§7Mending ";
                } else if (id == 9) {
                    str = "§r§7Frost Walker ";
                }
                String s = str;
                enchTag.remove(ench);
                lore.add(new StringTag(s + Enchantments.ENCHANTMENTS.getOrDefault(Short.valueOf(lvl), "enchantment.level." + ((int) lvl))));
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
        if (item.data() != 0 && tag.contains("Unbreakable")) {
            ByteTag unbreakable = (ByteTag) tag.get("Unbreakable");
            if (unbreakable.asByte() != 0) {
                viaVersionTag.put("Unbreakable", new ByteTag(unbreakable.asByte()));
                tag.remove("Unbreakable");
                if (display == null) {
                    CompoundTag compoundTag3 = new CompoundTag();
                    display = compoundTag3;
                    tag.put("display", compoundTag3);
                    viaVersionTag.put("noDisplay", new ByteTag());
                }
                ListTag loreTag2 = (ListTag) display.get("Lore");
                if (loreTag2 == null) {
                    ListTag listTag2 = new ListTag(StringTag.class);
                    loreTag2 = listTag2;
                    display.put("Lore", listTag2);
                }
                loreTag2.add(new StringTag("§9Unbreakable"));
            }
        }
        if (tag.contains("AttributeModifiers")) {
            viaVersionTag.put("AttributeModifiers", tag.get("AttributeModifiers").clone());
        }
        if (item.identifier() == 383 && item.data() == 0) {
            int data = 0;
            if (tag.contains("EntityTag")) {
                CompoundTag entityTag = (CompoundTag) tag.get("EntityTag");
                if (entityTag.contains("id")) {
                    StringTag id2 = (StringTag) entityTag.get("id");
                    if (ENTTIY_NAME_TO_ID.containsKey(id2.getValue())) {
                        data = ENTTIY_NAME_TO_ID.get(id2.getValue()).intValue();
                    } else if (display == null) {
                        CompoundTag compoundTag4 = new CompoundTag();
                        display = compoundTag4;
                        tag.put("display", compoundTag4);
                        viaVersionTag.put("noDisplay", new ByteTag());
                        display.put("Name", new StringTag("§rSpawn " + id2.getValue()));
                    }
                }
            }
            item.setData((short) data);
        }
        ReplacementRegistry1_8to1_9.replace(item);
        if (item.identifier() == 373 || item.identifier() == 438 || item.identifier() == 441) {
            int data2 = 0;
            if (tag.contains("Potion")) {
                StringTag potion = (StringTag) tag.get("Potion");
                String potionName = potion.getValue().replace("minecraft:", "");
                if (POTION_NAME_TO_ID.containsKey(potionName)) {
                    data2 = POTION_NAME_TO_ID.get(potionName).intValue();
                }
                if (item.identifier() == 438) {
                    potionName = potionName + "_splash";
                } else if (item.identifier() == 441) {
                    potionName = potionName + "_lingering";
                }
                if ((display == null || !display.contains("Name")) && POTION_NAME_INDEX.containsKey(potionName)) {
                    if (display == null) {
                        CompoundTag compoundTag5 = new CompoundTag();
                        display = compoundTag5;
                        tag.put("display", compoundTag5);
                        viaVersionTag.put("noDisplay", new ByteTag());
                    }
                    display.put("Name", new StringTag(POTION_NAME_INDEX.get(potionName)));
                }
            }
            if (item.identifier() == 438 || item.identifier() == 441) {
                item.setIdentifier(373);
                data2 += 8192;
            }
            item.setData((short) data2);
        }
        if (tag.contains("AttributeModifiers")) {
            ListTag attributes = (ListTag) tag.get("AttributeModifiers");
            int i = 0;
            while (i < attributes.size()) {
                CompoundTag attribute = (CompoundTag) attributes.get(i);
                String str2 = (String) attribute.get("AttributeName").getValue();
                if (!Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(attribute)) {
                    attributes.remove(attribute);
                    i--;
                }
                i++;
            }
        }
        if (viaVersionTag.size() == 2 && ((Short) viaVersionTag.get("id").getValue()).shortValue() == item.identifier() && ((Short) viaVersionTag.get("data").getValue()).shortValue() == item.data()) {
            item.tag().remove("ViaRewind1_8to1_9");
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
        if (item.identifier() == 383 && item.data() != 0) {
            if (tag == null) {
                CompoundTag compoundTag = new CompoundTag();
                tag = compoundTag;
                item.setTag(compoundTag);
            }
            if (!tag.contains("EntityTag") && ENTTIY_ID_TO_NAME.containsKey(Integer.valueOf(item.data()))) {
                CompoundTag entityTag = new CompoundTag();
                entityTag.put("id", new StringTag(ENTTIY_ID_TO_NAME.get(Integer.valueOf(item.data()))));
                tag.put("EntityTag", entityTag);
            }
            item.setData((short) 0);
        }
        if (item.identifier() == 373 && (tag == null || !tag.contains("Potion"))) {
            if (tag == null) {
                CompoundTag compoundTag2 = new CompoundTag();
                tag = compoundTag2;
                item.setTag(compoundTag2);
            }
            if (item.data() >= 16384) {
                item.setIdentifier(438);
                item.setData((short) (item.data() - 8192));
            }
            tag.put("Potion", new StringTag("minecraft:" + (item.data() == 8192 ? "water" : com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter.potionNameFromDamage(item.data()))));
            item.setData((short) 0);
        }
        if (tag == null || !item.tag().contains("ViaRewind1_8to1_9")) {
            return item;
        }
        CompoundTag viaVersionTag = (CompoundTag) tag.remove("ViaRewind1_8to1_9");
        item.setIdentifier(((Short) viaVersionTag.get("id").getValue()).shortValue());
        item.setData(((Short) viaVersionTag.get("data").getValue()).shortValue());
        if (viaVersionTag.contains("noDisplay")) {
            tag.remove("display");
        }
        if (viaVersionTag.contains("Unbreakable")) {
            tag.put("Unbreakable", viaVersionTag.get("Unbreakable").clone());
        }
        if (viaVersionTag.contains("displayName")) {
            CompoundTag display = (CompoundTag) tag.get("display");
            if (display == null) {
                CompoundTag compoundTag3 = new CompoundTag();
                display = compoundTag3;
                tag.put("display", compoundTag3);
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
        if (viaVersionTag.contains("lore")) {
            CompoundTag display2 = (CompoundTag) tag.get("display");
            if (display2 == null) {
                CompoundTag compoundTag4 = new CompoundTag();
                display2 = compoundTag4;
                tag.put("display", compoundTag4);
            }
            ListTag lore = (ListTag) display2.get("Lore");
            if (lore == null) {
                display2.put("Lore", new ListTag((List) viaVersionTag.get("lore").getValue()));
            } else {
                lore.setValue((List) viaVersionTag.get("lore").getValue());
            }
        } else if (tag.contains("display")) {
            ((CompoundTag) tag.get("display")).remove("Lore");
        }
        tag.remove("AttributeModifiers");
        if (viaVersionTag.contains("AttributeModifiers")) {
            tag.put("AttributeModifiers", viaVersionTag.get("AttributeModifiers"));
        }
        return item;
    }
}
