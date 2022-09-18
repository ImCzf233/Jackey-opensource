package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Projectiles.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0013"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Projectiles;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorRedValue", "interpolateHSB", "Ljava/awt/Color;", "startColor", "endColor", "process", "", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"})
@ModuleInfo(name = "Projectiles", description = "Allows you to see where arrows will land.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Projectiles.class */
public final class Projectiles extends Module {
    @NotNull
    private final ListValue colorMode = new ListValue("Color", new String[]{"Custom", "BowPower", "Rainbow"}, "Custom");
    @NotNull
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    @NotNull
    private final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    @NotNull
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);

    /* JADX WARN: Removed duplicated region for block: B:89:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0589  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x05a4  */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRender3D(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.Render3DEvent r18) {
        /*
            Method dump skipped, instructions count: 1685
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.render.Projectiles.onRender3D(net.ccbluex.liquidbounce.event.Render3DEvent):void");
    }

    @Nullable
    public final Color interpolateHSB(@NotNull Color startColor, @NotNull Color endColor, float process) {
        Intrinsics.checkNotNullParameter(startColor, "startColor");
        Intrinsics.checkNotNullParameter(endColor, "endColor");
        float[] startHSB = Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), (float[]) null);
        float[] endHSB = Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), (float[]) null);
        float brightness = (startHSB[2] + endHSB[2]) / 2;
        float saturation = (startHSB[1] + endHSB[1]) / 2;
        float hueMax = startHSB[0] > endHSB[0] ? startHSB[0] : endHSB[0];
        float hueMin = startHSB[0] > endHSB[0] ? endHSB[0] : startHSB[0];
        float hue = ((hueMax - hueMin) * process) + hueMin;
        return Color.getHSBColor(hue, saturation, brightness);
    }
}
