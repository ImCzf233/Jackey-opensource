package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.jetbrains.annotations.NotNull;

/* compiled from: AntiFireBall.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiFireBall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rotationValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "swingValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AntiFireBall", spacedName = "Anti Fire Ball", description = "Automatically punch fireballs away from you.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AntiFireBall.class */
public final class AntiFireBall extends Module {
    @NotNull
    private final MSTimer timer = new MSTimer();
    @NotNull
    private final ListValue swingValue = new ListValue("Swing", new String[]{"Normal", "Packet", "None"}, "Normal");
    @NotNull
    private final BoolValue rotationValue = new BoolValue("Rotation", true);

    @EventTarget
    private final void onUpdate(UpdateEvent event) {
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if ((entity instanceof EntityFireball) && MinecraftInstance.f362mc.field_71439_g.func_70032_d(entity) < 5.5d && this.timer.hasTimePassed(300L)) {
                if (this.rotationValue.get().booleanValue()) {
                    RotationUtils.setTargetRotation(RotationUtils.getRotations(entity));
                }
                MinecraftInstance.f362mc.field_71439_g.field_71174_a.func_147297_a(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                if (this.swingValue.get().equals("Normal")) {
                    MinecraftInstance.f362mc.field_71439_g.func_71038_i();
                } else if (this.swingValue.get().equals("Packet")) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
                }
                this.timer.reset();
                return;
            }
        }
    }
}
