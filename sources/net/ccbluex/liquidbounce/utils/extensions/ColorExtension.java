package net.ccbluex.liquidbounce.utils.extensions;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0014\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n��\n\u0002\u0010\b\n��\u001a\u0012\u0010��\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0005¨\u0006\u0006"}, m53d2 = {"darker", "Ljava/awt/Color;", "factor", "", "setAlpha", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/extensions/ColorExtensionKt.class */
public final class ColorExtension {
    @NotNull
    public static final Color darker(@NotNull Color $this$darker, float factor) {
        Intrinsics.checkNotNullParameter($this$darker, "<this>");
        return new Color(($this$darker.getRed() / 255.0f) * RangesKt.coerceIn(factor, 0.0f, 1.0f), ($this$darker.getGreen() / 255.0f) * RangesKt.coerceIn(factor, 0.0f, 1.0f), ($this$darker.getBlue() / 255.0f) * RangesKt.coerceIn(factor, 0.0f, 1.0f), $this$darker.getAlpha() / 255.0f);
    }

    @NotNull
    public static final Color setAlpha(@NotNull Color $this$setAlpha, float factor) {
        Intrinsics.checkNotNullParameter($this$setAlpha, "<this>");
        return new Color($this$setAlpha.getRed() / 255.0f, $this$setAlpha.getGreen() / 255.0f, $this$setAlpha.getBlue() / 255.0f, RangesKt.coerceIn(factor, 0.0f, 1.0f));
    }

    @NotNull
    public static final Color setAlpha(@NotNull Color $this$setAlpha, int factor) {
        Intrinsics.checkNotNullParameter($this$setAlpha, "<this>");
        return new Color($this$setAlpha.getRed(), $this$setAlpha.getGreen(), $this$setAlpha.getBlue(), RangesKt.coerceIn(factor, 0, 255));
    }
}
