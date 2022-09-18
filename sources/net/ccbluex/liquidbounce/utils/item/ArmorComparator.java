package net.ccbluex.liquidbounce.utils.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ArmorComparator.class */
public class ArmorComparator implements Comparator<ArmorPiece> {
    private static final Enchantment[] DAMAGE_REDUCTION_ENCHANTMENTS = {Enchantment.field_180310_c, Enchantment.field_180308_g, Enchantment.field_77329_d, Enchantment.field_77327_f};
    private static final float[] ENCHANTMENT_FACTORS = {1.5f, 0.4f, 0.39f, 0.38f};
    private static final float[] ENCHANTMENT_DAMAGE_REDUCTION_FACTOR = {0.04f, 0.08f, 0.15f, 0.08f};
    private static final Enchantment[] OTHER_ENCHANTMENTS = {Enchantment.field_180309_e, Enchantment.field_92091_k, Enchantment.field_180317_h, Enchantment.field_77341_i, Enchantment.field_77347_r};
    private static final float[] OTHER_ENCHANTMENT_FACTORS = {3.0f, 1.0f, 0.1f, 0.05f, 0.01f};

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    public int compare(ArmorPiece o1, ArmorPiece o2) {
        int compare = Double.compare(round(getThresholdedDamageReduction(o2.getItemStack()), 3), round(getThresholdedDamageReduction(o1.getItemStack()), 3));
        if (compare == 0) {
            int otherEnchantmentCmp = Double.compare(round(getEnchantmentThreshold(o1.getItemStack()), 3), round(getEnchantmentThreshold(o2.getItemStack()), 3));
            if (otherEnchantmentCmp == 0) {
                int enchantmentCountCmp = Integer.compare(ItemUtils.getEnchantmentCount(o1.getItemStack()), ItemUtils.getEnchantmentCount(o2.getItemStack()));
                if (enchantmentCountCmp != 0) {
                    return enchantmentCountCmp;
                }
                ItemArmor o1a = o1.getItemStack().func_77973_b();
                ItemArmor o2a = o2.getItemStack().func_77973_b();
                int durabilityCmp = Integer.compare(o1a.func_82812_d().func_78046_a(o1a.field_77881_a), o2a.func_82812_d().func_78046_a(o2a.field_77881_a));
                if (durabilityCmp != 0) {
                    return durabilityCmp;
                }
                return Integer.compare(o1a.func_82812_d().func_78045_a(), o2a.func_82812_d().func_78045_a());
            }
            return otherEnchantmentCmp;
        }
        return compare;
    }

    private float getThresholdedDamageReduction(ItemStack itemStack) {
        ItemArmor item = itemStack.func_77973_b();
        return getDamageReduction(item.func_82812_d().func_78044_b(item.field_77881_a), 0) * (1.0f - getThresholdedEnchantmentDamageReduction(itemStack));
    }

    private float getDamageReduction(int defensePoints, int toughness) {
        return 1.0f - (Math.min(20.0f, Math.max(defensePoints / 5.0f, defensePoints - (1.0f / (2.0f + (toughness / 4.0f))))) / 25.0f);
    }

    private float getThresholdedEnchantmentDamageReduction(ItemStack itemStack) {
        float sum = 0.0f;
        for (int i = 0; i < DAMAGE_REDUCTION_ENCHANTMENTS.length; i++) {
            sum += ItemUtils.getEnchantment(itemStack, DAMAGE_REDUCTION_ENCHANTMENTS[i]) * ENCHANTMENT_FACTORS[i] * ENCHANTMENT_DAMAGE_REDUCTION_FACTOR[i];
        }
        return sum;
    }

    private float getEnchantmentThreshold(ItemStack itemStack) {
        float sum = 0.0f;
        for (int i = 0; i < OTHER_ENCHANTMENTS.length; i++) {
            sum += ItemUtils.getEnchantment(itemStack, OTHER_ENCHANTMENTS[i]) * OTHER_ENCHANTMENT_FACTORS[i];
        }
        return sum;
    }
}
