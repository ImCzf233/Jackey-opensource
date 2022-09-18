package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.Tuples;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.apache.log4j.spi.Configurator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Arrays.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��H\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u00032\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a#\u0010\u0007\u001a\u00020\b\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b��\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a;\u0010\u0017\u001a\u0002H\u0018\"\u0010\b��\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\bø\u0001��¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b��\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010��¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b��\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\""}, m53d2 = {"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, m48xs = "kotlin/collections/ArraysKt")
/* loaded from: Jackey Client b2.jar:kotlin/collections/ArraysKt__ArraysKt.class */
public class ArraysKt__ArraysKt extends ArraysJVM {
    @NotNull
    public static final <T> List<T> flatten(@NotNull T[][] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        T[][] tArr2 = tArr;
        int i = 0;
        int i2 = 0;
        int length = tArr2.length;
        while (i2 < length) {
            Object[] objArr = tArr2[i2];
            i2++;
            Object[] it = objArr;
            i += it.length;
        }
        ArrayList result = new ArrayList(i);
        int i3 = 0;
        int length2 = tArr.length;
        while (i3 < length2) {
            i3++;
            CollectionsKt.addAll(result, tArr[i3]);
        }
        return result;
    }

    @NotNull
    public static final <T, R> Tuples<List<T>, List<R>> unzip(@NotNull Tuples<? extends T, ? extends R>[] tuplesArr) {
        Intrinsics.checkNotNullParameter(tuplesArr, "<this>");
        ArrayList listT = new ArrayList(tuplesArr.length);
        ArrayList listR = new ArrayList(tuplesArr.length);
        int i = 0;
        int length = tuplesArr.length;
        while (i < length) {
            Tuples<? extends T, ? extends R> tuples = tuplesArr[i];
            i++;
            listT.add(tuples.getFirst());
            listR.add(tuples.getSecond());
        }
        return TuplesKt.m46to(listT, listR);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean isNullOrEmpty(Object[] $this$isNullOrEmpty) {
        if ($this$isNullOrEmpty != null) {
            if (!($this$isNullOrEmpty.length == 0)) {
                return false;
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object ifEmpty(Object[] $this$ifEmpty, Functions defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return $this$ifEmpty.length == 0 ? defaultValue.invoke() : $this$ifEmpty;
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @JvmName(name = "contentDeepEquals")
    public static final <T> boolean contentDeepEquals(@Nullable T[] tArr, @Nullable T[] tArr2) {
        if (tArr == tArr2) {
            return true;
        }
        if (tArr == null || tArr2 == null || tArr.length != tArr2.length) {
            return false;
        }
        int i = 0;
        int length = tArr.length;
        while (i < length) {
            int i2 = i;
            i++;
            T t = tArr[i2];
            T t2 = tArr2[i2];
            if (t != t2) {
                if (t == null || t2 == null) {
                    return false;
                }
                if ((t instanceof Object[]) && (t2 instanceof Object[])) {
                    if (!ArraysKt.contentDeepEquals((Object[]) t, (Object[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof byte[]) && (t2 instanceof byte[])) {
                    if (!Arrays.equals((byte[]) t, (byte[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof short[]) && (t2 instanceof short[])) {
                    if (!Arrays.equals((short[]) t, (short[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof int[]) && (t2 instanceof int[])) {
                    if (!Arrays.equals((int[]) t, (int[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof long[]) && (t2 instanceof long[])) {
                    if (!Arrays.equals((long[]) t, (long[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof float[]) && (t2 instanceof float[])) {
                    if (!Arrays.equals((float[]) t, (float[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof double[]) && (t2 instanceof double[])) {
                    if (!Arrays.equals((double[]) t, (double[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof char[]) && (t2 instanceof char[])) {
                    if (!Arrays.equals((char[]) t, (char[]) t2)) {
                        return false;
                    }
                } else if ((t instanceof boolean[]) && (t2 instanceof boolean[])) {
                    if (!Arrays.equals((boolean[]) t, (boolean[]) t2)) {
                        return false;
                    }
                } else if (!(t instanceof UByteArray) || !(t2 instanceof UByteArray)) {
                    if (!(t instanceof UShortArray) || !(t2 instanceof UShortArray)) {
                        if (!(t instanceof UIntArray) || !(t2 instanceof UIntArray)) {
                            if (!(t instanceof ULongArray) || !(t2 instanceof ULongArray)) {
                                if (!Intrinsics.areEqual(t, t2)) {
                                    return false;
                                }
                            } else if (!UArraysKt.m1973contentEqualslec5QzE(((ULongArray) t).m1452unboximpl(), ((ULongArray) t2).m1452unboximpl())) {
                                return false;
                            }
                        } else if (!UArraysKt.m1972contentEqualsKJPZfPQ(((UIntArray) t).m1373unboximpl(), ((UIntArray) t2).m1373unboximpl())) {
                            return false;
                        }
                    } else if (!UArraysKt.m1975contentEqualsFGO6Aew(((UShortArray) t).m1558unboximpl(), ((UShortArray) t2).m1558unboximpl())) {
                        return false;
                    }
                } else if (!UArraysKt.m1974contentEqualskV0jMPg(((UByteArray) t).m1294unboximpl(), ((UByteArray) t2).m1294unboximpl())) {
                    return false;
                }
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "contentDeepToString")
    @NotNull
    @PublishedApi
    public static final <T> String contentDeepToString(@Nullable T[] tArr) {
        if (tArr == null) {
            return Configurator.NULL;
        }
        int length = (RangesKt.coerceAtMost(tArr.length, 429496729) * 5) + 2;
        StringBuilder $this$contentDeepToStringImpl_u24lambda_u2d2 = new StringBuilder(length);
        contentDeepToStringInternal$ArraysKt__ArraysKt(tArr, $this$contentDeepToStringImpl_u24lambda_u2d2, new ArrayList());
        String sb = $this$contentDeepToStringImpl_u24lambda_u2d2.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "StringBuilder(capacity).…builderAction).toString()");
        return sb;
    }

    private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] tArr, StringBuilder result, List<Object[]> list) {
        if (list.contains(tArr)) {
            result.append("[...]");
            return;
        }
        list.add(tArr);
        result.append('[');
        int i = 0;
        int length = tArr.length;
        while (i < length) {
            int i2 = i;
            i++;
            if (i2 != 0) {
                result.append(", ");
            }
            T t = tArr[i2];
            if (t == null) {
                result.append(Configurator.NULL);
            } else if (t instanceof Object[]) {
                contentDeepToStringInternal$ArraysKt__ArraysKt((Object[]) t, result, list);
            } else if (t instanceof byte[]) {
                String arrays = Arrays.toString((byte[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
                result.append(arrays);
            } else if (t instanceof short[]) {
                String arrays2 = Arrays.toString((short[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays2, "toString(this)");
                result.append(arrays2);
            } else if (t instanceof int[]) {
                String arrays3 = Arrays.toString((int[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays3, "toString(this)");
                result.append(arrays3);
            } else if (t instanceof long[]) {
                String arrays4 = Arrays.toString((long[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays4, "toString(this)");
                result.append(arrays4);
            } else if (t instanceof float[]) {
                String arrays5 = Arrays.toString((float[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays5, "toString(this)");
                result.append(arrays5);
            } else if (t instanceof double[]) {
                String arrays6 = Arrays.toString((double[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays6, "toString(this)");
                result.append(arrays6);
            } else if (t instanceof char[]) {
                String arrays7 = Arrays.toString((char[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays7, "toString(this)");
                result.append(arrays7);
            } else if (t instanceof boolean[]) {
                String arrays8 = Arrays.toString((boolean[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays8, "toString(this)");
                result.append(arrays8);
            } else if (t instanceof UByteArray) {
                UByteArray uByteArray = (UByteArray) t;
                result.append(UArraysKt.m1990contentToString2csIQuQ(uByteArray != null ? uByteArray.m1294unboximpl() : null));
            } else if (t instanceof UShortArray) {
                UShortArray uShortArray = (UShortArray) t;
                result.append(UArraysKt.m1991contentToStringd6D3K8(uShortArray != null ? uShortArray.m1558unboximpl() : null));
            } else if (t instanceof UIntArray) {
                UIntArray uIntArray = (UIntArray) t;
                result.append(UArraysKt.m1988contentToStringXUkPCBk(uIntArray != null ? uIntArray.m1373unboximpl() : null));
            } else if (t instanceof ULongArray) {
                ULongArray uLongArray = (ULongArray) t;
                result.append(UArraysKt.m1989contentToStringuLth9ew(uLongArray != null ? uLongArray.m1452unboximpl() : null));
            } else {
                result.append(t.toString());
            }
        }
        result.append(']');
        list.remove(CollectionsKt.getLastIndex(list));
    }
}