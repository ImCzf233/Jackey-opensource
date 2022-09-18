package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Timer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Timer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoDisableValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onMoveValue", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "Timer", description = "Changes the speed of the entire game.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Timer.class */
public final class Timer extends Module {
    @NotNull
    private final FloatValue speedValue = new FloatValue("Speed", 2.0f, 0.1f, 10.0f, "x");
    @NotNull
    private final BoolValue onMoveValue = new BoolValue("OnMove", true);
    @NotNull
    private final BoolValue autoDisableValue = new BoolValue("AutoDisable", true);

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null || MinecraftInstance.f362mc.field_71441_e == null) {
            return;
        }
        if (MovementUtils.isMoving() || !this.onMoveValue.get().booleanValue()) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = this.speedValue.get().floatValue();
            return;
        }
        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getWorldClient() == null && this.autoDisableValue.get().booleanValue()) {
            setState(false);
        }
    }
}
