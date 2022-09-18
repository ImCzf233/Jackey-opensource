package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Aimbot.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "centerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "clickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "fovValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "jitterValue", "lockValue", "onClickValue", "rangeValue", "turnSpeedValue", "onStrafe", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "LiquidBounce"})
@ModuleInfo(name = "Aimbot", description = "Automatically faces selected entities around you.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Aimbot.class */
public final class Aimbot extends Module {
    @NotNull
    private final FloatValue rangeValue = new FloatValue("Range", 4.4f, 1.0f, 8.0f, "m");
    @NotNull
    private final FloatValue turnSpeedValue = new FloatValue("TurnSpeed", 2.0f, 1.0f, 180.0f, "°");
    @NotNull
    private final FloatValue fovValue = new FloatValue("FOV", 180.0f, 1.0f, 180.0f, "°");
    @NotNull
    private final BoolValue centerValue = new BoolValue("Center", false);
    @NotNull
    private final BoolValue lockValue = new BoolValue("Lock", true);
    @NotNull
    private final BoolValue onClickValue = new BoolValue("OnClick", false);
    @NotNull
    private final BoolValue jitterValue = new BoolValue("Jitter", false);
    @NotNull
    private final MSTimer clickTimer = new MSTimer();

    /* JADX WARN: Removed duplicated region for block: B:83:0x00f3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x008e A[SYNTHETIC] */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStrafe(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.StrafeEvent r8) {
        /*
            Method dump skipped, instructions count: 708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.combat.Aimbot.onStrafe(net.ccbluex.liquidbounce.event.StrafeEvent):void");
    }
}
