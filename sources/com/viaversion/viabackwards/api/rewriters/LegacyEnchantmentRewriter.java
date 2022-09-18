package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/LegacyEnchantmentRewriter.class */
public class LegacyEnchantmentRewriter {
    private final Map<Short, String> enchantmentMappings = new HashMap();
    private final String nbtTagName;
    private Set<Short> hideLevelForEnchants;

    public LegacyEnchantmentRewriter(String nbtTagName) {
        this.nbtTagName = nbtTagName;
    }

    public void registerEnchantment(int id, String replacementLore) {
        this.enchantmentMappings.put(Short.valueOf((short) id), replacementLore);
    }

    public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
        String key = storedEnchant ? "StoredEnchantments" : "ench";
        ListTag enchantments = (ListTag) tag.get(key);
        ListTag remappedEnchantments = new ListTag(CompoundTag.class);
        List<Tag> lore = new ArrayList<>();
        Iterator<Tag> it = enchantments.clone().iterator();
        while (it.hasNext()) {
            Tag enchantmentEntry = it.next();
            Tag idTag = ((CompoundTag) enchantmentEntry).get("id");
            if (idTag != null) {
                short newId = ((NumberTag) idTag).asShort();
                String enchantmentName = this.enchantmentMappings.get(Short.valueOf(newId));
                if (enchantmentName != null) {
                    enchantments.remove(enchantmentEntry);
                    short level = ((NumberTag) ((CompoundTag) enchantmentEntry).get("lvl")).asShort();
                    if (this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(Short.valueOf(newId))) {
                        lore.add(new StringTag(enchantmentName));
                    } else {
                        lore.add(new StringTag(enchantmentName + " " + EnchantmentRewriter.getRomanNumber(level)));
                    }
                    remappedEnchantments.add(enchantmentEntry);
                }
            }
        }
        if (!lore.isEmpty()) {
            if (!storedEnchant && enchantments.size() == 0) {
                CompoundTag dummyEnchantment = new CompoundTag();
                dummyEnchantment.put("id", new ShortTag((short) 0));
                dummyEnchantment.put("lvl", new ShortTag((short) 0));
                enchantments.add(dummyEnchantment);
                tag.put(this.nbtTagName + "|dummyEnchant", new ByteTag());
                IntTag hideFlags = (IntTag) tag.get("HideFlags");
                if (hideFlags == null) {
                    hideFlags = new IntTag();
                } else {
                    tag.put(this.nbtTagName + "|oldHideFlags", new IntTag(hideFlags.asByte()));
                }
                int flags = hideFlags.asByte() | 1;
                hideFlags.setValue(flags);
                tag.put("HideFlags", hideFlags);
            }
            tag.put(this.nbtTagName + CallSiteDescriptor.OPERATOR_DELIMITER + key, remappedEnchantments);
            CompoundTag display = (CompoundTag) tag.get("display");
            if (display == null) {
                CompoundTag compoundTag = new CompoundTag();
                display = compoundTag;
                tag.put("display", compoundTag);
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

    public void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnchant) {
        String key = storedEnchant ? "StoredEnchantments" : "ench";
        ListTag remappedEnchantments = (ListTag) tag.remove(this.nbtTagName + CallSiteDescriptor.OPERATOR_DELIMITER + key);
        ListTag enchantments = (ListTag) tag.get(key);
        if (enchantments == null) {
            enchantments = new ListTag(CompoundTag.class);
        }
        if (!storedEnchant && tag.remove(this.nbtTagName + "|dummyEnchant") != null) {
            Iterator<Tag> it = enchantments.clone().iterator();
            while (it.hasNext()) {
                Tag enchantment = it.next();
                short id = ((NumberTag) ((CompoundTag) enchantment).get("id")).asShort();
                short level = ((NumberTag) ((CompoundTag) enchantment).get("lvl")).asShort();
                if (id == 0 && level == 0) {
                    enchantments.remove(enchantment);
                }
            }
            IntTag hideFlags = (IntTag) tag.remove(this.nbtTagName + "|oldHideFlags");
            if (hideFlags != null) {
                tag.put("HideFlags", new IntTag(hideFlags.asByte()));
            } else {
                tag.remove("HideFlags");
            }
        }
        CompoundTag display = (CompoundTag) tag.get("display");
        ListTag lore = display != null ? (ListTag) display.get("Lore") : null;
        Iterator<Tag> it2 = remappedEnchantments.clone().iterator();
        while (it2.hasNext()) {
            enchantments.add(it2.next());
            if (lore != null && lore.size() != 0) {
                lore.remove(lore.get(0));
            }
        }
        if (lore != null && lore.size() == 0) {
            display.remove("Lore");
            if (display.isEmpty()) {
                tag.remove("display");
            }
        }
        tag.put(key, enchantments);
    }

    public void setHideLevelForEnchants(int... enchants) {
        this.hideLevelForEnchants = new HashSet();
        for (int enchant : enchants) {
            this.hideLevelForEnchants.add(Short.valueOf((short) enchant));
        }
    }
}
