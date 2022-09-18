package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: SafeWalk.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/SafeWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airSafeValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onMove", "", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "LiquidBounce"})
@ModuleInfo(name = "SafeWalk", spacedName = "Safe Walk", description = "Prevents you from falling down as if you were sneaking.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/SafeWalk.class */
public final class SafeWalk extends Module {
    @NotNull
    private final BoolValue airSafeValue = new BoolValue("AirSafe", false);

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.airSafeValue.get().booleanValue() || MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
            event.setSafeWalk(true);
        }
    }
}
