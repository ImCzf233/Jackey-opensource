package net.ccbluex.liquidbounce.p004ui.client.hud.element;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Element.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��,\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\b\u0086\b\u0018��2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0006\u0010\u0012\u001a\u00020\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\f\u0010\t¨\u0006\u001b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "", "x", "", "y", "x2", "y2", "(FFFF)V", "getX", "()F", "getX2", "getY", "getY2", "component1", "component2", "component3", "component4", "copy", "draw", "", "equals", "", "other", "hashCode", "", "toString", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Border */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Border.class */
public final class Border {

    /* renamed from: x */
    private final float f348x;

    /* renamed from: y */
    private final float f349y;

    /* renamed from: x2 */
    private final float f350x2;

    /* renamed from: y2 */
    private final float f351y2;

    public final float component1() {
        return this.f348x;
    }

    public final float component2() {
        return this.f349y;
    }

    public final float component3() {
        return this.f350x2;
    }

    public final float component4() {
        return this.f351y2;
    }

    @NotNull
    public final Border copy(float x, float y, float x2, float y2) {
        return new Border(x, y, x2, y2);
    }

    public static /* synthetic */ Border copy$default(Border border, float f, float f2, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = border.f348x;
        }
        if ((i & 2) != 0) {
            f2 = border.f349y;
        }
        if ((i & 4) != 0) {
            f3 = border.f350x2;
        }
        if ((i & 8) != 0) {
            f4 = border.f351y2;
        }
        return border.copy(f, f2, f3, f4);
    }

    @NotNull
    public String toString() {
        return "Border(x=" + this.f348x + ", y=" + this.f349y + ", x2=" + this.f350x2 + ", y2=" + this.f351y2 + ')';
    }

    public int hashCode() {
        int result = Float.hashCode(this.f348x);
        return (((((result * 31) + Float.hashCode(this.f349y)) * 31) + Float.hashCode(this.f350x2)) * 31) + Float.hashCode(this.f351y2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Border)) {
            return false;
        }
        Border border = (Border) other;
        return Intrinsics.areEqual((Object) Float.valueOf(this.f348x), (Object) Float.valueOf(border.f348x)) && Intrinsics.areEqual((Object) Float.valueOf(this.f349y), (Object) Float.valueOf(border.f349y)) && Intrinsics.areEqual((Object) Float.valueOf(this.f350x2), (Object) Float.valueOf(border.f350x2)) && Intrinsics.areEqual((Object) Float.valueOf(this.f351y2), (Object) Float.valueOf(border.f351y2));
    }

    public Border(float x, float y, float x2, float y2) {
        this.f348x = x;
        this.f349y = y;
        this.f350x2 = x2;
        this.f351y2 = y2;
    }

    public final float getX() {
        return this.f348x;
    }

    public final float getY() {
        return this.f349y;
    }

    public final float getX2() {
        return this.f350x2;
    }

    public final float getY2() {
        return this.f351y2;
    }

    public final void draw() {
        RenderUtils.drawBorderedRect(this.f348x, this.f349y, this.f350x2, this.f351y2, 1.0f, Integer.MIN_VALUE, 0);
    }
}
