package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: NoRotateSet.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/NoRotateSet;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "confirmValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "illegalRotationValue", "noZeroValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"})
@ModuleInfo(name = "NoRotateSet", spacedName = "No Rotate Set", description = "Prevents the server from rotating your head.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/NoRotateSet.class */
public final class NoRotateSet extends Module {
    @NotNull
    private final BoolValue confirmValue = new BoolValue("Confirm", true);
    @NotNull
    private final BoolValue illegalRotationValue = new BoolValue("ConfirmIllegalRotation", false);
    @NotNull
    private final BoolValue noZeroValue = new BoolValue("NoZero", false);

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b7, code lost:
        if ((r0.func_148930_g() == net.ccbluex.liquidbounce.utils.RotationUtils.serverRotation.getPitch()) == false) goto L38;
     */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPacket(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.PacketEvent r8) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.misc.NoRotateSet.onPacket(net.ccbluex.liquidbounce.event.PacketEvent):void");
    }
}
