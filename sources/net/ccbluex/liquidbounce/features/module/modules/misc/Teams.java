package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/* compiled from: Teams.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u000e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/Teams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorColorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "armorIndexValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorValue", "gommeSWValue", "scoreboardValue", "isInYourTeam", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "LiquidBounce"})
@ModuleInfo(name = "Teams", description = "Prevents Killaura from attacking team mates.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/Teams.class */
public final class Teams extends Module {
    @NotNull
    private final BoolValue scoreboardValue = new BoolValue("ScoreboardTeam", true);
    @NotNull
    private final BoolValue colorValue = new BoolValue("Color", true);
    @NotNull
    private final BoolValue gommeSWValue = new BoolValue("GommeSW", false);
    @NotNull
    private final BoolValue armorColorValue = new BoolValue("ArmorColor", false);
    @NotNull
    private final IntegerValue armorIndexValue = new IntegerValue("ArmorIndex", 3, 0, 3, new Teams$armorIndexValue$1(this));

    public final boolean isInYourTeam(@NotNull EntityLivingBase entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return false;
        }
        if (this.scoreboardValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.func_96124_cp() != null && entity.func_96124_cp() != null && MinecraftInstance.f362mc.field_71439_g.func_96124_cp().func_142054_a(entity.func_96124_cp())) {
            return true;
        }
        if (this.armorColorValue.get().booleanValue()) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            if (MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70460_b[this.armorIndexValue.get().intValue()] != null && entityPlayer.field_71071_by.field_70460_b[this.armorIndexValue.get().intValue()] != null) {
                ItemStack myHead = MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70460_b[this.armorIndexValue.get().intValue()];
                Intrinsics.checkNotNull(myHead);
                ItemArmor func_77973_b = myHead.func_77973_b();
                Intrinsics.checkNotNull(func_77973_b);
                ItemArmor myItemArmor = func_77973_b;
                ItemStack entityHead = entityPlayer.field_71071_by.field_70460_b[this.armorIndexValue.get().intValue()];
                ItemArmor func_77973_b2 = myHead.func_77973_b();
                Intrinsics.checkNotNull(func_77973_b2);
                ItemArmor entityItemArmor = func_77973_b2;
                int func_82814_b = myItemArmor.func_82814_b(myHead);
                Intrinsics.checkNotNull(entityHead);
                if (func_82814_b == entityItemArmor.func_82814_b(entityHead)) {
                    return true;
                }
            }
        }
        if (this.gommeSWValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.func_145748_c_() != null && entity.func_145748_c_() != null) {
            String func_150254_d = entity.func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(func_150254_d, "entity.displayName.formattedText");
            String targetName = StringsKt.replace$default(func_150254_d, "§r", "", false, 4, (Object) null);
            String func_150254_d2 = MinecraftInstance.f362mc.field_71439_g.func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(func_150254_d2, "mc.thePlayer.displayName.formattedText");
            String clientName = StringsKt.replace$default(func_150254_d2, "§r", "", false, 4, (Object) null);
            if (StringsKt.startsWith$default(targetName, "T", false, 2, (Object) null) && StringsKt.startsWith$default(clientName, "T", false, 2, (Object) null) && Character.isDigit(targetName.charAt(1)) && Character.isDigit(clientName.charAt(1))) {
                return targetName.charAt(1) == clientName.charAt(1);
            }
        }
        if (this.colorValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.func_145748_c_() != null && entity.func_145748_c_() != null) {
            String func_150254_d3 = entity.func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(func_150254_d3, "entity.displayName.formattedText");
            String targetName2 = StringsKt.replace$default(func_150254_d3, "§r", "", false, 4, (Object) null);
            String func_150254_d4 = MinecraftInstance.f362mc.field_71439_g.func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(func_150254_d4, "mc.thePlayer.displayName.formattedText");
            return StringsKt.startsWith$default(targetName2, Intrinsics.stringPlus("§", Character.valueOf(StringsKt.replace$default(func_150254_d4, "§r", "", false, 4, (Object) null).charAt(1))), false, 2, (Object) null);
        }
        return false;
    }
}
