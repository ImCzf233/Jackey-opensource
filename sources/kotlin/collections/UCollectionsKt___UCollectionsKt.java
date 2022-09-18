package kotlin.collections;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.Unsigned;
import kotlin.WasExperimental;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: _UCollections.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��F\n��\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010��\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010��\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001��¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010��\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001��¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010��\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001��¢\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007ø\u0001��¢\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007ø\u0001��¢\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007ø\u0001��¢\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007ø\u0001��¢\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, m53d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, m48xs = "kotlin/collections/UCollectionsKt")
/* loaded from: Jackey Client b2.jar:kotlin/collections/UCollectionsKt___UCollectionsKt.class */
class UCollectionsKt___UCollectionsKt {
    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    public static final byte[] toUByteArray(@NotNull Collection<UByte> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        byte[] result = UByteArray.m1279constructorimpl(collection.size());
        int index = 0;
        for (UByte uByte : collection) {
            byte element = uByte.m1276unboximpl();
            int i = index;
            index = i + 1;
            UByteArray.m1281setVurrAj0(result, i, element);
        }
        return result;
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    public static final int[] toUIntArray(@NotNull Collection<UInt> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        int[] result = UIntArray.m1358constructorimpl(collection.size());
        int index = 0;
        for (UInt uInt : collection) {
            int element = uInt.m1355unboximpl();
            int i = index;
            index = i + 1;
            UIntArray.m1360setVXSXFK8(result, i, element);
        }
        return result;
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    public static final long[] toULongArray(@NotNull Collection<ULong> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        long[] result = ULongArray.m1437constructorimpl(collection.size());
        int index = 0;
        for (ULong uLong : collection) {
            long element = uLong.m1434unboximpl();
            int i = index;
            index = i + 1;
            ULongArray.m1439setk8EXiF4(result, i, element);
        }
        return result;
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    public static final short[] toUShortArray(@NotNull Collection<UShort> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        short[] result = UShortArray.m1543constructorimpl(collection.size());
        int index = 0;
        for (UShort uShort : collection) {
            short element = uShort.m1540unboximpl();
            int i = index;
            index = i + 1;
            UShortArray.m1545set01HTLdE(result, i, element);
        }
        return result;
    }

    @SinceKotlin(version = "1.5")
    @JvmName(name = "sumOfUInt")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int sumOfUInt(@NotNull Iterable<UInt> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        int sum = 0;
        for (UInt uInt : iterable) {
            int element = uInt.m1355unboximpl();
            sum = UInt.m1353constructorimpl(sum + element);
        }
        return sum;
    }

    @SinceKotlin(version = "1.5")
    @JvmName(name = "sumOfULong")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final long sumOfULong(@NotNull Iterable<ULong> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        long sum = 0;
        for (ULong uLong : iterable) {
            long element = uLong.m1434unboximpl();
            sum = ULong.m1432constructorimpl(sum + element);
        }
        return sum;
    }

    @SinceKotlin(version = "1.5")
    @JvmName(name = "sumOfUByte")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int sumOfUByte(@NotNull Iterable<UByte> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        int sum = 0;
        for (UByte uByte : iterable) {
            byte element = uByte.m1276unboximpl();
            sum = UInt.m1353constructorimpl(sum + UInt.m1353constructorimpl(element & 255));
        }
        return sum;
    }

    @SinceKotlin(version = "1.5")
    @JvmName(name = "sumOfUShort")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int sumOfUShort(@NotNull Iterable<UShort> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        int sum = 0;
        for (UShort uShort : iterable) {
            short element = uShort.m1540unboximpl();
            sum = UInt.m1353constructorimpl(sum + UInt.m1353constructorimpl(element & 65535));
        }
        return sum;
    }
}
