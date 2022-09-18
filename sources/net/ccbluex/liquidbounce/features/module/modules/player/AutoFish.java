package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.item.ItemFishingRod;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoFish.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoFish;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rodOutTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoFish", spacedName = "Auto Fish", description = "Automatically catches fish when using a rod.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoFish.class */
public final class AutoFish extends Module {
    @NotNull
    private final MSTimer rodOutTimer = new MSTimer();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.f362mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemFishingRod)) {
            return;
        }
        if (!this.rodOutTimer.hasTimePassed(500L) || MinecraftInstance.f362mc.field_71439_g.field_71104_cf != null) {
            if (MinecraftInstance.f362mc.field_71439_g.field_71104_cf == null) {
                return;
            }
            if (!(MinecraftInstance.f362mc.field_71439_g.field_71104_cf.field_70159_w == 0.0d)) {
                return;
            }
            if (!(MinecraftInstance.f362mc.field_71439_g.field_71104_cf.field_70179_y == 0.0d)) {
                return;
            }
            if (MinecraftInstance.f362mc.field_71439_g.field_71104_cf.field_70181_x == 0.0d) {
                return;
            }
        }
        MinecraftInstance.f362mc.func_147121_ag();
        this.rodOutTimer.reset();
    }
}
