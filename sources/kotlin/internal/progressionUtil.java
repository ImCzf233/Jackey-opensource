package kotlin.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.PublishedApi;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0012\n��\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\u001a \u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u001a \u0010��\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a \u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0001\u001a\u0018\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0002¨\u0006\u000b"}, m53d2 = {"differenceModulo", "", "a", "b", "c", "", "getProgressionLastElement", "start", AsmConstants.END, "step", "mod", "kotlin-stdlib"})
/* renamed from: kotlin.internal.ProgressionUtilKt */
/* loaded from: Jackey Client b2.jar:kotlin/internal/ProgressionUtilKt.class */
public final class progressionUtil {
    private static final int mod(int a, int b) {
        int mod = a % b;
        return mod >= 0 ? mod : mod + b;
    }

    private static final long mod(long a, long b) {
        long mod = a % b;
        return mod >= 0 ? mod : mod + b;
    }

    private static final int differenceModulo(int a, int b, int c) {
        return mod(mod(a, c) - mod(b, c), c);
    }

    private static final long differenceModulo(long a, long b, long c) {
        return mod(mod(a, c) - mod(b, c), c);
    }

    @PublishedApi
    public static final int getProgressionLastElement(int start, int end, int step) {
        if (step > 0) {
            return start >= end ? end : end - differenceModulo(end, start, step);
        } else if (step >= 0) {
            throw new IllegalArgumentException("Step is zero.");
        } else {
            return start <= end ? end : end + differenceModulo(start, end, -step);
        }
    }

    @PublishedApi
    public static final long getProgressionLastElement(long start, long end, long step) {
        if (step > 0) {
            return start >= end ? end : end - differenceModulo(end, start, step);
        } else if (step >= 0) {
            throw new IllegalArgumentException("Step is zero.");
        } else {
            return start <= end ? end : end + differenceModulo(start, end, -step);
        }
    }
}
