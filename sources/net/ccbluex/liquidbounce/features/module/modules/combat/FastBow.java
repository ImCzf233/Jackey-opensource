package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

/* compiled from: FastBow.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n��\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/FastBow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "packetsValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "FastBow", spacedName = "Fast Bow", description = "Turns your bow into a machine gun.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/FastBow.class */
public final class FastBow extends Module {
    @NotNull
    private final IntegerValue packetsValue = new IntegerValue("Packets", 20, 3, 20);
    @NotNull
    private final IntegerValue delay = new IntegerValue("Delay", 0, 0, 500, "ms");
    @NotNull
    private final MSTimer timer = new MSTimer();

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        float f;
        float f2;
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g.func_71039_bw() && MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g() != null && (MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow)) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(BlockPos.field_177992_a, 255, MinecraftInstance.f362mc.field_71439_g.func_71045_bC(), 0.0f, 0.0f, 0.0f));
            if (RotationUtils.targetRotation != null) {
                f = RotationUtils.targetRotation.getYaw();
            } else {
                f = MinecraftInstance.f362mc.field_71439_g.field_70177_z;
            }
            float yaw = f;
            if (RotationUtils.targetRotation != null) {
                f2 = RotationUtils.targetRotation.getPitch();
            } else {
                f2 = MinecraftInstance.f362mc.field_71439_g.field_70125_A;
            }
            float pitch = f2;
            int i = 0;
            int intValue = this.packetsValue.get().intValue();
            while (i < intValue) {
                i++;
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, true));
            }
            if (this.timer.hasTimePassed(this.delay.get().intValue())) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                this.timer.reset();
            }
            MinecraftInstance.f362mc.field_71439_g.field_71072_f = MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g().func_77988_m() - 1;
        }
    }
}
