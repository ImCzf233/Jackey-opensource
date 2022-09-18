package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: FastBreak.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/FastBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "breakDamage", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "FastBreak", spacedName = "Fast Break", description = "Allows you to break blocks faster.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/FastBreak.class */
public final class FastBreak extends Module {
    @NotNull
    private final FloatValue breakDamage = new FloatValue("BreakDamage", 0.8f, 0.1f, 1.0f);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.f362mc.field_71442_b.field_78781_i = 0;
        if (MinecraftInstance.f362mc.field_71442_b.field_78770_f > this.breakDamage.get().floatValue()) {
            MinecraftInstance.f362mc.field_71442_b.field_78770_f = 1.0f;
        }
        if (Fucker.INSTANCE.getCurrentDamage() > this.breakDamage.get().floatValue()) {
            Fucker.INSTANCE.setCurrentDamage(1.0f);
        }
    }
}
