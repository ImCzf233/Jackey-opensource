package kotlin.collections.builders;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ListBuilder.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��2\n��\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a!\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H��¢\u0006\u0002\u0010\u0005\u001a+\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0001\"\u0004\b��\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\b\u001a\u00020\u0004H��¢\u0006\u0002\u0010\t\u001a%\u0010\n\u001a\u00020\u000b\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\f\u001a\u00020\u0004H��¢\u0006\u0002\u0010\r\u001a-\u0010\u000e\u001a\u00020\u000b\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H��¢\u0006\u0002\u0010\u0011\u001a9\u0010\u0012\u001a\u00020\u0013\"\u0004\b��\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0002¢\u0006\u0002\u0010\u0018\u001a-\u0010\u0019\u001a\u00020\u0004\"\u0004\b��\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u001a\u001a/\u0010\u001b\u001a\u00020\u001c\"\u0004\b��\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u001d¨\u0006\u001e"}, m53d2 = {"arrayOfUninitializedElements", "", "E", "size", "", "(I)[Ljava/lang/Object;", "copyOfUninitializedElements", "T", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "resetAt", "", "index", "([Ljava/lang/Object;I)V", "resetRange", "fromIndex", "toIndex", "([Ljava/lang/Object;II)V", "subarrayContentEquals", "", "offset", "length", "other", "", "([Ljava/lang/Object;IILjava/util/List;)Z", "subarrayContentHashCode", "([Ljava/lang/Object;II)I", "subarrayContentToString", "", "([Ljava/lang/Object;II)Ljava/lang/String;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/builders/ListBuilderKt.class */
public final class ListBuilderKt {
    @NotNull
    public static final <E> E[] arrayOfUninitializedElements(int size) {
        if (!(size >= 0)) {
            throw new IllegalArgumentException("capacity must be non-negative.".toString());
        }
        return (E[]) new Object[size];
    }

    public static final <T> String subarrayContentToString(T[] tArr, int offset, int length) {
        StringBuilder sb = new StringBuilder(2 + (length * 3));
        sb.append("[");
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                if (i2 > 0) {
                    sb.append(", ");
                }
                sb.append(tArr[offset + i2]);
                i = i2 + 1;
            } else {
                sb.append("]");
                String sb2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(sb2, "sb.toString()");
                return sb2;
            }
        }
    }

    public static final <T> int subarrayContentHashCode(T[] tArr, int offset, int length) {
        int result = 1;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                T t = tArr[offset + i2];
                result = (result * 31) + (t == null ? 0 : t.hashCode());
                i = i2 + 1;
            } else {
                return result;
            }
        }
    }

    public static final <T> boolean subarrayContentEquals(T[] tArr, int offset, int length, List<?> list) {
        if (length != list.size()) {
            return false;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                if (!Intrinsics.areEqual(tArr[offset + i2], list.get(i2))) {
                    return false;
                }
                i = i2 + 1;
            } else {
                return true;
            }
        }
    }

    @NotNull
    public static final <T> T[] copyOfUninitializedElements(@NotNull T[] tArr, int newSize) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, newSize);
        Intrinsics.checkNotNullExpressionValue(tArr2, "copyOf(this, newSize)");
        return tArr2;
    }

    public static final <E> void resetAt(@NotNull E[] eArr, int index) {
        Intrinsics.checkNotNullParameter(eArr, "<this>");
        eArr[index] = null;
    }

    public static final <E> void resetRange(@NotNull E[] eArr, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(eArr, "<this>");
        int i = fromIndex;
        while (i < toIndex) {
            int index = i;
            i++;
            resetAt(eArr, index);
        }
    }
}
