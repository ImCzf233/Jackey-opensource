package kotlin;

import kotlin.internal.InlineOnly;

/* compiled from: ULong.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��,\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n��\n\u0002\u0010\u0006\n��\n\u0002\u0010\u0007\n��\n\u0002\u0010\b\n��\n\u0002\u0010\t\n��\n\u0002\u0010\n\n\u0002\b\u0002\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001��¢\u0006\u0002\u0010\u0003\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\u0004H\u0087\bø\u0001��¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\u0006H\u0087\bø\u0001��¢\u0006\u0002\u0010\u0007\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001��¢\u0006\u0002\u0010\t\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\nH\u0087\bø\u0001��¢\u0006\u0002\u0010\u000b\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\fH\u0087\bø\u0001��¢\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, m53d2 = {"toULong", "Lkotlin/ULong;", "", "(B)J", "", "(D)J", "", "(F)J", "", "(I)J", "", "(J)J", "", "(S)J", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/ULongKt.class */
public final class ULongKt {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long toULong(byte $this$toULong) {
        return ULong.m1432constructorimpl($this$toULong);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long toULong(short $this$toULong) {
        return ULong.m1432constructorimpl($this$toULong);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long toULong(int $this$toULong) {
        return ULong.m1432constructorimpl($this$toULong);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long toULong(long $this$toULong) {
        return ULong.m1432constructorimpl($this$toULong);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long toULong(float $this$toULong) {
        return UnsignedUtils.doubleToULong($this$toULong);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long toULong(double $this$toULong) {
        return UnsignedUtils.doubleToULong($this$toULong);
    }
}
