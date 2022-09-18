package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/ArmorType.class */
public enum ArmorType {
    LEATHER_HELMET(1, 298, "minecraft:leather_helmet"),
    LEATHER_CHESTPLATE(3, 299, "minecraft:leather_chestplate"),
    LEATHER_LEGGINGS(2, TokenId.ABSTRACT, "minecraft:leather_leggings"),
    LEATHER_BOOTS(1, TokenId.BOOLEAN, "minecraft:leather_boots"),
    CHAINMAIL_HELMET(2, TokenId.BREAK, "minecraft:chainmail_helmet"),
    CHAINMAIL_CHESTPLATE(5, TokenId.BYTE, "minecraft:chainmail_chestplate"),
    CHAINMAIL_LEGGINGS(4, TokenId.CASE, "minecraft:chainmail_leggings"),
    CHAINMAIL_BOOTS(1, TokenId.CATCH, "minecraft:chainmail_boots"),
    IRON_HELMET(2, TokenId.CHAR, "minecraft:iron_helmet"),
    IRON_CHESTPLATE(6, TokenId.CLASS, "minecraft:iron_chestplate"),
    IRON_LEGGINGS(5, TokenId.CONST, "minecraft:iron_leggings"),
    IRON_BOOTS(2, TokenId.CONTINUE, "minecraft:iron_boots"),
    DIAMOND_HELMET(3, TokenId.DEFAULT, "minecraft:diamond_helmet"),
    DIAMOND_CHESTPLATE(8, TokenId.f172DO, "minecraft:diamond_chestplate"),
    DIAMOND_LEGGINGS(6, TokenId.DOUBLE, "minecraft:diamond_leggings"),
    DIAMOND_BOOTS(3, TokenId.ELSE, "minecraft:diamond_boots"),
    GOLD_HELMET(2, TokenId.EXTENDS, "minecraft:gold_helmet"),
    GOLD_CHESTPLATE(5, TokenId.FINAL, "minecraft:gold_chestplate"),
    GOLD_LEGGINGS(3, TokenId.FINALLY, "minecraft:gold_leggings"),
    GOLD_BOOTS(1, TokenId.FLOAT, "minecraft:gold_boots"),
    NONE(0, 0, "none");
    
    private static final Map<Integer, ArmorType> armor = new HashMap();
    private final int armorPoints;

    /* renamed from: id */
    private final int f205id;
    private final String type;

    static {
        ArmorType[] values;
        for (ArmorType a : values()) {
            armor.put(Integer.valueOf(a.getId()), a);
        }
    }

    ArmorType(int armorPoints, int id, String type) {
        this.armorPoints = armorPoints;
        this.f205id = id;
        this.type = type;
    }

    public int getArmorPoints() {
        return this.armorPoints;
    }

    public String getType() {
        return this.type;
    }

    public static ArmorType findById(int id) {
        ArmorType type = armor.get(Integer.valueOf(id));
        return type == null ? NONE : type;
    }

    public static ArmorType findByType(String type) {
        ArmorType[] values;
        for (ArmorType a : values()) {
            if (a.getType().equals(type)) {
                return a;
            }
        }
        return NONE;
    }

    public static boolean isArmor(int id) {
        return armor.containsKey(Integer.valueOf(id));
    }

    public static boolean isArmor(String type) {
        ArmorType[] values;
        for (ArmorType a : values()) {
            if (a.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return this.f205id;
    }
}
