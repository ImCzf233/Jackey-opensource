package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: Parkour.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Parkour;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Parkour", description = "Automatically jumps when reaching the edge of a block.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Parkour.class */
public final class Parkour extends Module {
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MovementUtils.isMoving() && MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71439_g.func_70093_af() && !MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d() && !MinecraftInstance.f362mc.field_71474_y.field_74314_A.func_151470_d() && MinecraftInstance.f362mc.field_71441_e.func_72945_a(MinecraftInstance.f362mc.field_71439_g, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, -0.5d, 0.0d).func_72314_b(-0.001d, 0.0d, -0.001d)).isEmpty()) {
            MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
        }
    }
}
