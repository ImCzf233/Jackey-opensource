package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoBow.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoBow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "waitForBowAimbot", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoBow", spacedName = "Auto Bow", description = "Automatically shoots an arrow whenever your bow is fully loaded.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoBow.class */
public final class AutoBow extends Module {
    @NotNull
    private final BoolValue waitForBowAimbot = new BoolValue("WaitForBowAimbot", true);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(BowAimbot.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot");
        }
        BowAimbot bowAimbot = (BowAimbot) module;
        if (MinecraftInstance.f362mc.field_71439_g.func_71039_bw()) {
            ItemStack func_70694_bm = MinecraftInstance.f362mc.field_71439_g.func_70694_bm();
            if (!Intrinsics.areEqual(func_70694_bm == null ? null : func_70694_bm.func_77973_b(), Items.field_151031_f) || MinecraftInstance.f362mc.field_71439_g.func_71057_bx() <= 20) {
                return;
            }
            if (!this.waitForBowAimbot.get().booleanValue() || !bowAimbot.getState() || bowAimbot.hasTarget()) {
                MinecraftInstance.f362mc.field_71439_g.func_71034_by();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            }
        }
    }
}
