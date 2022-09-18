package net.ccbluex.liquidbounce.utils.item;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.utils.RegexUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/* compiled from: ItemHelper.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��T\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018��2\u00020\u0001:\u0002\"#B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tH\u0002J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0016\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016J\"\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00162\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010J\u0016\u0010 \u001a\u00020!2\u0006\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006$"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ItemHelper;", "", "()V", "armorDamageReduceEnchantments", "", "Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$Enchant;", "[Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$Enchant;", "otherArmorEnchantments", "compareArmor", "", "o1", "Lnet/ccbluex/liquidbounce/utils/item/ArmorPart;", "o2", "nbtedPriority", "", "goal", "Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$EnumNBTPriorityType;", "getArmorDamageReduction", "defensePoints", "toughness", "getArmorEnchantmentThreshold", "itemStack", "Lnet/minecraft/item/ItemStack;", "getArmorThresholdedDamageReduction", "getArmorThresholdedEnchantmentDamageReduction", "getEnchantment", "enchantment", "Lnet/minecraft/enchantment/Enchantment;", "getEnchantmentCount", "getWeaponEnchantFactor", "", "stack", "hasNBTGoal", "", "Enchant", "EnumNBTPriorityType", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ItemHelper.class */
public final class ItemHelper {
    @NotNull
    public static final ItemHelper INSTANCE = new ItemHelper();
    @NotNull
    private static final Enchant[] armorDamageReduceEnchantments;
    @NotNull
    private static final Enchant[] otherArmorEnchantments;

    /* compiled from: ItemHelper.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$EnumNBTPriorityType;", "", "(Ljava/lang/String;I)V", "HAS_NAME", "HAS_LORE", "HAS_DISPLAY_TAG", "NONE", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ItemHelper$EnumNBTPriorityType.class */
    public enum EnumNBTPriorityType {
        HAS_NAME,
        HAS_LORE,
        HAS_DISPLAY_TAG,
        NONE
    }

    private ItemHelper() {
    }

    public final int getEnchantment(@NotNull ItemStack itemStack, @NotNull Enchantment enchantment) {
        Intrinsics.checkNotNullParameter(itemStack, "itemStack");
        Intrinsics.checkNotNullParameter(enchantment, "enchantment");
        if (itemStack.func_77986_q() == null || itemStack.func_77986_q().func_82582_d()) {
            return 0;
        }
        int i = 0;
        int func_74745_c = itemStack.func_77986_q().func_74745_c();
        while (i < func_74745_c) {
            int i2 = i;
            i++;
            NBTTagCompound tagCompound = itemStack.func_77986_q().func_150305_b(i2);
            if ((tagCompound.func_74764_b("ench") && tagCompound.func_74765_d("ench") == enchantment.field_77352_x) || (tagCompound.func_74764_b("id") && tagCompound.func_74765_d("id") == enchantment.field_77352_x)) {
                return tagCompound.func_74765_d("lvl");
            }
        }
        return 0;
    }

    public final int getEnchantmentCount(@NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter(itemStack, "itemStack");
        if (itemStack.func_77986_q() == null || itemStack.func_77986_q().func_82582_d()) {
            return 0;
        }
        int c = 0;
        int i = 0;
        int func_74745_c = itemStack.func_77986_q().func_74745_c();
        while (i < func_74745_c) {
            int i2 = i;
            i++;
            NBTTagCompound tagCompound = itemStack.func_77986_q().func_150305_b(i2);
            if (tagCompound.func_74764_b("ench") || tagCompound.func_74764_b("id")) {
                c++;
            }
        }
        return c;
    }

    public static /* synthetic */ double getWeaponEnchantFactor$default(ItemHelper itemHelper, ItemStack itemStack, float f, EnumNBTPriorityType enumNBTPriorityType, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 0.0f;
        }
        if ((i & 4) != 0) {
            enumNBTPriorityType = EnumNBTPriorityType.NONE;
        }
        return itemHelper.getWeaponEnchantFactor(itemStack, f, enumNBTPriorityType);
    }

    public final double getWeaponEnchantFactor(@NotNull ItemStack stack, float nbtedPriority, @NotNull EnumNBTPriorityType goal) {
        Intrinsics.checkNotNullParameter(stack, "stack");
        Intrinsics.checkNotNullParameter(goal, "goal");
        Enchantment sharpness = Enchantment.field_180314_l;
        Intrinsics.checkNotNullExpressionValue(sharpness, "sharpness");
        Enchantment fireAspect = Enchantment.field_77334_n;
        Intrinsics.checkNotNullExpressionValue(fireAspect, "fireAspect");
        return (1.25d * getEnchantment(stack, sharpness)) + (1.0d * getEnchantment(stack, fireAspect)) + (hasNBTGoal(stack, goal) ? nbtedPriority : 0.0f);
    }

    static {
        Enchantment protection = Enchantment.field_180310_c;
        Intrinsics.checkNotNullExpressionValue(protection, "protection");
        Enchantment projectileProtection = Enchantment.field_180308_g;
        Intrinsics.checkNotNullExpressionValue(projectileProtection, "projectileProtection");
        Enchantment fireProtection = Enchantment.field_77329_d;
        Intrinsics.checkNotNullExpressionValue(fireProtection, "fireProtection");
        Enchantment blastProtection = Enchantment.field_77327_f;
        Intrinsics.checkNotNullExpressionValue(blastProtection, "blastProtection");
        armorDamageReduceEnchantments = new Enchant[]{new Enchant(protection, 0.06f), new Enchant(projectileProtection, 0.032f), new Enchant(fireProtection, 0.0585f), new Enchant(blastProtection, 0.0304f)};
        Enchantment featherFalling = Enchantment.field_180309_e;
        Intrinsics.checkNotNullExpressionValue(featherFalling, "featherFalling");
        Enchantment thorns = Enchantment.field_92091_k;
        Intrinsics.checkNotNullExpressionValue(thorns, "thorns");
        Enchantment respiration = Enchantment.field_180317_h;
        Intrinsics.checkNotNullExpressionValue(respiration, "respiration");
        Enchantment aquaAffinity = Enchantment.field_77341_i;
        Intrinsics.checkNotNullExpressionValue(aquaAffinity, "aquaAffinity");
        Enchantment unbreaking = Enchantment.field_77347_r;
        Intrinsics.checkNotNullExpressionValue(unbreaking, "unbreaking");
        otherArmorEnchantments = new Enchant[]{new Enchant(featherFalling, 3.0f), new Enchant(thorns, 1.0f), new Enchant(respiration, 0.1f), new Enchant(aquaAffinity, 0.05f), new Enchant(unbreaking, 0.01f)};
    }

    public static /* synthetic */ int compareArmor$default(ItemHelper itemHelper, ArmorPart armorPart, ArmorPart armorPart2, float f, EnumNBTPriorityType enumNBTPriorityType, int i, Object obj) {
        if ((i & 4) != 0) {
            f = 0.0f;
        }
        if ((i & 8) != 0) {
            enumNBTPriorityType = EnumNBTPriorityType.NONE;
        }
        return itemHelper.compareArmor(armorPart, armorPart2, f, enumNBTPriorityType);
    }

    public final int compareArmor(@NotNull ArmorPart o1, @NotNull ArmorPart o2, float nbtedPriority, @NotNull EnumNBTPriorityType goal) {
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        Intrinsics.checkNotNullParameter(goal, "goal");
        int compare = Double.compare(RegexUtils.INSTANCE.round(getArmorThresholdedDamageReduction(o2.getItemStack()) - (hasNBTGoal(o2.getItemStack(), goal) ? nbtedPriority / 5.0f : 0.0f), 3), RegexUtils.INSTANCE.round(getArmorThresholdedDamageReduction(o1.getItemStack()) - (hasNBTGoal(o1.getItemStack(), goal) ? nbtedPriority / 5.0f : 0.0f), 3));
        if (compare == 0) {
            int otherEnchantmentCmp = Double.compare(RegexUtils.INSTANCE.round(getArmorEnchantmentThreshold(o1.getItemStack()), 3), RegexUtils.INSTANCE.round(getArmorEnchantmentThreshold(o2.getItemStack()), 3));
            if (otherEnchantmentCmp == 0) {
                int enchantmentCountCmp = Intrinsics.compare(getEnchantmentCount(o1.getItemStack()), getEnchantmentCount(o2.getItemStack()));
                if (enchantmentCountCmp != 0) {
                    return enchantmentCountCmp;
                }
                ItemArmor func_77973_b = o1.getItemStack().func_77973_b();
                if (func_77973_b == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
                }
                ItemArmor o1a = func_77973_b;
                ItemArmor func_77973_b2 = o2.getItemStack().func_77973_b();
                if (func_77973_b2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
                }
                ItemArmor o2a = func_77973_b2;
                int durabilityCmp = Intrinsics.compare(o1a.func_82812_d().func_78046_a(o1a.field_77881_a), o2a.func_82812_d().func_78046_a(o2a.field_77881_a));
                if (durabilityCmp != 0) {
                    return durabilityCmp;
                }
                return Intrinsics.compare(o1a.func_82812_d().func_78045_a(), o2a.func_82812_d().func_78045_a());
            }
            return otherEnchantmentCmp;
        }
        return compare;
    }

    private final float getArmorThresholdedDamageReduction(ItemStack itemStack) {
        ItemArmor func_77973_b = itemStack.func_77973_b();
        if (func_77973_b == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
        }
        ItemArmor item = func_77973_b;
        return getArmorDamageReduction(item.func_82812_d().func_78044_b(item.field_77881_a), 0) * (1 - getArmorThresholdedEnchantmentDamageReduction(itemStack));
    }

    private final float getArmorDamageReduction(int defensePoints, int toughness) {
        return 1 - (RangesKt.coerceAtMost(20.0f, RangesKt.coerceAtLeast(defensePoints / 5.0f, defensePoints - (1 / (2 + (toughness / 4.0f))))) / 25.0f);
    }

    private final float getArmorThresholdedEnchantmentDamageReduction(ItemStack itemStack) {
        float sum = 0.0f;
        int i = 0;
        int length = armorDamageReduceEnchantments.length;
        while (i < length) {
            int i2 = i;
            i++;
            sum += getEnchantment(itemStack, armorDamageReduceEnchantments[i2].getEnchantment()) * armorDamageReduceEnchantments[i2].getFactor();
        }
        return sum;
    }

    private final float getArmorEnchantmentThreshold(ItemStack itemStack) {
        float sum = 0.0f;
        int i = 0;
        int length = otherArmorEnchantments.length;
        while (i < length) {
            int i2 = i;
            i++;
            sum += getEnchantment(itemStack, otherArmorEnchantments[i2].getEnchantment()) * otherArmorEnchantments[i2].getFactor();
        }
        return sum;
    }

    /* compiled from: ItemHelper.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$Enchant;", "", "enchantment", "Lnet/minecraft/enchantment/Enchantment;", "factor", "", "(Lnet/minecraft/enchantment/Enchantment;F)V", "getEnchantment", "()Lnet/minecraft/enchantment/Enchantment;", "getFactor", "()F", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ItemHelper$Enchant.class */
    public static final class Enchant {
        @NotNull
        private final Enchantment enchantment;
        private final float factor;

        public Enchant(@NotNull Enchantment enchantment, float factor) {
            Intrinsics.checkNotNullParameter(enchantment, "enchantment");
            this.enchantment = enchantment;
            this.factor = factor;
        }

        @NotNull
        public final Enchantment getEnchantment() {
            return this.enchantment;
        }

        public final float getFactor() {
            return this.factor;
        }
    }

    public final boolean hasNBTGoal(@NotNull ItemStack stack, @NotNull EnumNBTPriorityType goal) {
        Intrinsics.checkNotNullParameter(stack, "stack");
        Intrinsics.checkNotNullParameter(goal, "goal");
        if (stack.func_77942_o() && stack.func_77978_p().func_150297_b("display", 10)) {
            NBTTagCompound display = stack.func_77978_p().func_74775_l("display");
            if (goal == EnumNBTPriorityType.HAS_DISPLAY_TAG) {
                return true;
            }
            if (goal == EnumNBTPriorityType.HAS_NAME) {
                return display.func_74764_b("Name");
            }
            return goal == EnumNBTPriorityType.HAS_LORE && display.func_74764_b("Lore") && display.func_150295_c("Lore", 8).func_74745_c() > 0;
        }
        return false;
    }
}
