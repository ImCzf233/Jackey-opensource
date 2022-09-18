package kotlin;

import kotlin.internal.InlineOnly;

/* compiled from: UInt.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��,\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n��\n\u0002\u0010\u0006\n��\n\u0002\u0010\u0007\n��\n\u0002\u0010\b\n��\n\u0002\u0010\t\n��\n\u0002\u0010\n\n\u0002\b\u0002\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001��¢\u0006\u0002\u0010\u0003\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\u0004H\u0087\bø\u0001��¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\u0006H\u0087\bø\u0001��¢\u0006\u0002\u0010\u0007\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001��¢\u0006\u0002\u0010\t\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\nH\u0087\bø\u0001��¢\u0006\u0002\u0010\u000b\u001a\u0015\u0010��\u001a\u00020\u0001*\u00020\fH\u0087\bø\u0001��¢\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, m53d2 = {"toUInt", "Lkotlin/UInt;", "", "(B)I", "", "(D)I", "", "(F)I", "", "(I)I", "", "(J)I", "", "(S)I", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/UIntKt.class */
public final class UIntKt {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int toUInt(byte $this$toUInt) {
        return UInt.m1353constructorimpl($this$toUInt);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int toUInt(short $this$toUInt) {
        return UInt.m1353constructorimpl($this$toUInt);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int toUInt(int $this$toUInt) {
        return UInt.m1353constructorimpl($this$toUInt);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int toUInt(long $this$toUInt) {
        return UInt.m1353constructorimpl((int) $this$toUInt);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int toUInt(float $this$toUInt) {
        return UnsignedUtils.doubleToUInt($this$toUInt);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int toUInt(double $this$toUInt) {
        return UnsignedUtils.doubleToUInt($this$toUInt);
    }
}
