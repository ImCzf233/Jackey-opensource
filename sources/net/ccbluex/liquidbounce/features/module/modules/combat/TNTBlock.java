package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;

/* compiled from: TNTBlock.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/TNTBlock;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoSwordValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blocked", "", "fuseValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onMotionUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"})
@ModuleInfo(name = "TNTBlock", spacedName = "TNT Block", description = "Automatically blocks with your sword when TNT around you explodes.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/TNTBlock.class */
public final class TNTBlock extends Module {
    @NotNull
    private final IntegerValue fuseValue = new IntegerValue("Fuse", 10, 0, 80);
    @NotNull
    private final FloatValue rangeValue = new FloatValue("Range", 9.0f, 1.0f, 20.0f);
    @NotNull
    private final BoolValue autoSwordValue = new BoolValue("AutoSword", true);
    private boolean blocked;

    @EventTarget
    public final void onMotionUpdate(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null || MinecraftInstance.f362mc.field_71441_e == null) {
            return;
        }
        for (EntityTNTPrimed entityTNTPrimed : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if ((entityTNTPrimed instanceof EntityTNTPrimed) && MinecraftInstance.f362mc.field_71439_g.func_70032_d(entityTNTPrimed) <= this.rangeValue.get().floatValue() && entityTNTPrimed.field_70516_a <= this.fuseValue.get().intValue()) {
                if (this.autoSwordValue.get().booleanValue()) {
                    int slot = -1;
                    float bestDamage = 1.0f;
                    int i = 0;
                    while (i < 9) {
                        int i2 = i;
                        i++;
                        ItemStack itemStack = MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70301_a(i2);
                        if (itemStack != null && (itemStack.func_77973_b() instanceof ItemSword)) {
                            ItemSword func_77973_b = itemStack.func_77973_b();
                            if (func_77973_b == null) {
                                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemSword");
                            }
                            float itemDamage = func_77973_b.func_150931_i() + 4.0f;
                            if (itemDamage > bestDamage) {
                                bestDamage = itemDamage;
                                slot = i2;
                            }
                        }
                    }
                    if (slot != -1 && slot != MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c) {
                        MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c = slot;
                        MinecraftInstance.f362mc.field_71442_b.func_78765_e();
                    }
                }
                if (MinecraftInstance.f362mc.field_71439_g.func_70694_bm() != null && (MinecraftInstance.f362mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword)) {
                    MinecraftInstance.f362mc.field_71474_y.field_74313_G.field_74513_e = true;
                    this.blocked = true;
                    return;
                }
                return;
            }
        }
        if (this.blocked && !GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74313_G)) {
            MinecraftInstance.f362mc.field_71474_y.field_74313_G.field_74513_e = false;
            this.blocked = false;
        }
    }
}
