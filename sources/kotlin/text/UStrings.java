package kotlin.text;

import jdk.nashorn.internal.runtime.JSType;
import kotlin.ExceptionsH;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.WasExperimental;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.CharCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��,\n��\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010��\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010��\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010��\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010��\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001��¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001��\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001��¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001��\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001��¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001��\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001��¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001��\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001��\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, m53d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"})
@JvmName(name = "UStringsKt")
/* renamed from: kotlin.text.UStringsKt */
/* loaded from: Jackey Client b2.jar:kotlin/text/UStringsKt.class */
public final class UStrings {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: toString-LxnNnR4 */
    public static final String m2591toStringLxnNnR4(byte $this$toString, int radix) {
        String num = Integer.toString($this$toString & 255, CharsKt.checkRadix(radix));
        Intrinsics.checkNotNullExpressionValue(num, "toString(this, checkRadix(radix))");
        return num;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: toString-olVBNx4 */
    public static final String m2592toStringolVBNx4(short $this$toString, int radix) {
        String num = Integer.toString($this$toString & 65535, CharsKt.checkRadix(radix));
        Intrinsics.checkNotNullExpressionValue(num, "toString(this, checkRadix(radix))");
        return num;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: toString-V7xB4Y4 */
    public static final String m2593toStringV7xB4Y4(int $this$toString, int radix) {
        String l = Long.toString($this$toString & JSType.MAX_UINT, CharsKt.checkRadix(radix));
        Intrinsics.checkNotNullExpressionValue(l, "toString(this, checkRadix(radix))");
        return l;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: toString-JSWoG40 */
    public static final String m2594toStringJSWoG40(long $this$toString, int radix) {
        return UnsignedUtils.ulongToString($this$toString, CharsKt.checkRadix(radix));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final byte toUByte(@NotNull String $this$toUByte) {
        Intrinsics.checkNotNullParameter($this$toUByte, "<this>");
        UByte uByteOrNull = toUByteOrNull($this$toUByte);
        if (uByteOrNull == null) {
            StringsKt.numberFormatError($this$toUByte);
            throw new ExceptionsH();
        }
        return uByteOrNull.m1276unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final byte toUByte(@NotNull String $this$toUByte, int radix) {
        Intrinsics.checkNotNullParameter($this$toUByte, "<this>");
        UByte uByteOrNull = toUByteOrNull($this$toUByte, radix);
        if (uByteOrNull == null) {
            StringsKt.numberFormatError($this$toUByte);
            throw new ExceptionsH();
        }
        return uByteOrNull.m1276unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final short toUShort(@NotNull String $this$toUShort) {
        Intrinsics.checkNotNullParameter($this$toUShort, "<this>");
        UShort uShortOrNull = toUShortOrNull($this$toUShort);
        if (uShortOrNull == null) {
            StringsKt.numberFormatError($this$toUShort);
            throw new ExceptionsH();
        }
        return uShortOrNull.m1540unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final short toUShort(@NotNull String $this$toUShort, int radix) {
        Intrinsics.checkNotNullParameter($this$toUShort, "<this>");
        UShort uShortOrNull = toUShortOrNull($this$toUShort, radix);
        if (uShortOrNull == null) {
            StringsKt.numberFormatError($this$toUShort);
            throw new ExceptionsH();
        }
        return uShortOrNull.m1540unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int toUInt(@NotNull String $this$toUInt) {
        Intrinsics.checkNotNullParameter($this$toUInt, "<this>");
        UInt uIntOrNull = toUIntOrNull($this$toUInt);
        if (uIntOrNull == null) {
            StringsKt.numberFormatError($this$toUInt);
            throw new ExceptionsH();
        }
        return uIntOrNull.m1355unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int toUInt(@NotNull String $this$toUInt, int radix) {
        Intrinsics.checkNotNullParameter($this$toUInt, "<this>");
        UInt uIntOrNull = toUIntOrNull($this$toUInt, radix);
        if (uIntOrNull == null) {
            StringsKt.numberFormatError($this$toUInt);
            throw new ExceptionsH();
        }
        return uIntOrNull.m1355unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final long toULong(@NotNull String $this$toULong) {
        Intrinsics.checkNotNullParameter($this$toULong, "<this>");
        ULong uLongOrNull = toULongOrNull($this$toULong);
        if (uLongOrNull == null) {
            StringsKt.numberFormatError($this$toULong);
            throw new ExceptionsH();
        }
        return uLongOrNull.m1434unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final long toULong(@NotNull String $this$toULong, int radix) {
        Intrinsics.checkNotNullParameter($this$toULong, "<this>");
        ULong uLongOrNull = toULongOrNull($this$toULong, radix);
        if (uLongOrNull == null) {
            StringsKt.numberFormatError($this$toULong);
            throw new ExceptionsH();
        }
        return uLongOrNull.m1434unboximpl();
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final UByte toUByteOrNull(@NotNull String $this$toUByteOrNull) {
        Intrinsics.checkNotNullParameter($this$toUByteOrNull, "<this>");
        return toUByteOrNull($this$toUByteOrNull, 10);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final UByte toUByteOrNull(@NotNull String $this$toUByteOrNull, int radix) {
        Intrinsics.checkNotNullParameter($this$toUByteOrNull, "<this>");
        UInt uIntOrNull = toUIntOrNull($this$toUByteOrNull, radix);
        if (uIntOrNull == null) {
            return null;
        }
        int m1355unboximpl = uIntOrNull.m1355unboximpl();
        if (UnsignedUtils.uintCompare(m1355unboximpl, UInt.m1353constructorimpl((-1) & 255)) <= 0) {
            return UByte.m1275boximpl(UByte.m1274constructorimpl((byte) m1355unboximpl));
        }
        return null;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final UShort toUShortOrNull(@NotNull String $this$toUShortOrNull) {
        Intrinsics.checkNotNullParameter($this$toUShortOrNull, "<this>");
        return toUShortOrNull($this$toUShortOrNull, 10);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final UShort toUShortOrNull(@NotNull String $this$toUShortOrNull, int radix) {
        Intrinsics.checkNotNullParameter($this$toUShortOrNull, "<this>");
        UInt uIntOrNull = toUIntOrNull($this$toUShortOrNull, radix);
        if (uIntOrNull == null) {
            return null;
        }
        int m1355unboximpl = uIntOrNull.m1355unboximpl();
        if (UnsignedUtils.uintCompare(m1355unboximpl, UInt.m1353constructorimpl((-1) & CharCompanionObject.MAX_VALUE)) <= 0) {
            return UShort.m1539boximpl(UShort.m1538constructorimpl((short) m1355unboximpl));
        }
        return null;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final UInt toUIntOrNull(@NotNull String $this$toUIntOrNull) {
        Intrinsics.checkNotNullParameter($this$toUIntOrNull, "<this>");
        return toUIntOrNull($this$toUIntOrNull, 10);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final UInt toUIntOrNull(@NotNull String $this$toUIntOrNull, int radix) {
        int start;
        Intrinsics.checkNotNullParameter($this$toUIntOrNull, "<this>");
        CharsKt.checkRadix(radix);
        int length = $this$toUIntOrNull.length();
        if (length == 0) {
            return null;
        }
        char firstChar = $this$toUIntOrNull.charAt(0);
        if (Intrinsics.compare((int) firstChar, 48) < 0) {
            if (length == 1 || firstChar != '+') {
                return null;
            }
            start = 1;
        } else {
            start = 0;
        }
        int limitBeforeMul = 119304647;
        int uradix = UInt.m1353constructorimpl(radix);
        int result = 0;
        int i = start;
        while (i < length) {
            int i2 = i;
            i++;
            int digit = CharsKt.digitOf($this$toUIntOrNull.charAt(i2), radix);
            if (digit < 0) {
                return null;
            }
            if (UnsignedUtils.uintCompare(result, limitBeforeMul) > 0) {
                if (limitBeforeMul == 119304647) {
                    limitBeforeMul = UnsignedUtils.m1563uintDivideJ1ME1BU(-1, uradix);
                    if (UnsignedUtils.uintCompare(result, limitBeforeMul) > 0) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            int result2 = UInt.m1353constructorimpl(result * uradix);
            result = UInt.m1353constructorimpl(result2 + UInt.m1353constructorimpl(digit));
            if (UnsignedUtils.uintCompare(result, result2) < 0) {
                return null;
            }
        }
        return UInt.m1354boximpl(result);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final ULong toULongOrNull(@NotNull String $this$toULongOrNull) {
        Intrinsics.checkNotNullParameter($this$toULongOrNull, "<this>");
        return toULongOrNull($this$toULongOrNull, 10);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @Nullable
    public static final ULong toULongOrNull(@NotNull String $this$toULongOrNull, int radix) {
        int start;
        Intrinsics.checkNotNullParameter($this$toULongOrNull, "<this>");
        CharsKt.checkRadix(radix);
        int length = $this$toULongOrNull.length();
        if (length == 0) {
            return null;
        }
        char firstChar = $this$toULongOrNull.charAt(0);
        if (Intrinsics.compare((int) firstChar, 48) < 0) {
            if (length == 1 || firstChar != '+') {
                return null;
            }
            start = 1;
        } else {
            start = 0;
        }
        long limitBeforeMul = 512409557603043100L;
        long uradix = ULong.m1432constructorimpl(radix);
        long result = 0;
        int i = start;
        while (i < length) {
            int i2 = i;
            i++;
            int digit = CharsKt.digitOf($this$toULongOrNull.charAt(i2), radix);
            if (digit < 0) {
                return null;
            }
            if (UnsignedUtils.ulongCompare(result, limitBeforeMul) > 0) {
                if (limitBeforeMul == 512409557603043100L) {
                    limitBeforeMul = UnsignedUtils.m1565ulongDivideeb3DHEI(-1L, uradix);
                    if (UnsignedUtils.ulongCompare(result, limitBeforeMul) > 0) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            long result2 = ULong.m1432constructorimpl(result * uradix);
            result = ULong.m1432constructorimpl(result2 + ULong.m1432constructorimpl(UInt.m1353constructorimpl(digit) & JSType.MAX_UINT));
            if (UnsignedUtils.ulongCompare(result, result2) < 0) {
                return null;
            }
        }
        return ULong.m1433boximpl(result);
    }
}
