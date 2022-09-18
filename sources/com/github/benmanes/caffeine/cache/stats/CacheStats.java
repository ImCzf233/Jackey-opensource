package com.github.benmanes.caffeine.cache.stats;

import com.google.errorprone.annotations.Immutable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;

@Immutable
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/stats/CacheStats.class */
public final class CacheStats {
    private static final CacheStats EMPTY_STATS = m236of(0, 0, 0, 0, 0, 0, 0);
    private final long hitCount;
    private final long missCount;
    private final long loadSuccessCount;
    private final long loadFailureCount;
    private final long totalLoadTime;
    private final long evictionCount;
    private final long evictionWeight;

    @Deprecated
    public CacheStats(long hitCount, long missCount, long loadSuccessCount, long loadFailureCount, long totalLoadTime, long evictionCount) {
        this(hitCount, missCount, loadSuccessCount, loadFailureCount, totalLoadTime, evictionCount, 0L);
    }

    @Deprecated
    public CacheStats(long hitCount, long missCount, long loadSuccessCount, long loadFailureCount, long totalLoadTime, long evictionCount, long evictionWeight) {
        if (hitCount < 0 || missCount < 0 || loadSuccessCount < 0 || loadFailureCount < 0 || totalLoadTime < 0 || evictionCount < 0 || evictionWeight < 0) {
            throw new IllegalArgumentException();
        }
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.loadSuccessCount = loadSuccessCount;
        this.loadFailureCount = loadFailureCount;
        this.totalLoadTime = totalLoadTime;
        this.evictionCount = evictionCount;
        this.evictionWeight = evictionWeight;
    }

    /* renamed from: of */
    public static CacheStats m236of(long hitCount, long missCount, long loadSuccessCount, long loadFailureCount, long totalLoadTime, long evictionCount, long evictionWeight) {
        return new CacheStats(hitCount, missCount, loadSuccessCount, loadFailureCount, totalLoadTime, evictionCount, evictionWeight);
    }

    public static CacheStats empty() {
        return EMPTY_STATS;
    }

    public long requestCount() {
        return saturatedAdd(this.hitCount, this.missCount);
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 1.0d;
        }
        return this.hitCount / requestCount;
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 0.0d;
        }
        return this.missCount / requestCount;
    }

    public long loadCount() {
        return saturatedAdd(this.loadSuccessCount, this.loadFailureCount);
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public long loadFailureCount() {
        return this.loadFailureCount;
    }

    public double loadFailureRate() {
        long totalLoadCount = saturatedAdd(this.loadSuccessCount, this.loadFailureCount);
        if (totalLoadCount == 0) {
            return 0.0d;
        }
        return this.loadFailureCount / totalLoadCount;
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }

    public double averageLoadPenalty() {
        long totalLoadCount = saturatedAdd(this.loadSuccessCount, this.loadFailureCount);
        if (totalLoadCount == 0) {
            return 0.0d;
        }
        return this.totalLoadTime / totalLoadCount;
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public long evictionWeight() {
        return this.evictionWeight;
    }

    public CacheStats minus(CacheStats other) {
        return m236of(Math.max(0L, saturatedSubtract(this.hitCount, other.hitCount)), Math.max(0L, saturatedSubtract(this.missCount, other.missCount)), Math.max(0L, saturatedSubtract(this.loadSuccessCount, other.loadSuccessCount)), Math.max(0L, saturatedSubtract(this.loadFailureCount, other.loadFailureCount)), Math.max(0L, saturatedSubtract(this.totalLoadTime, other.totalLoadTime)), Math.max(0L, saturatedSubtract(this.evictionCount, other.evictionCount)), Math.max(0L, saturatedSubtract(this.evictionWeight, other.evictionWeight)));
    }

    public CacheStats plus(CacheStats other) {
        return m236of(saturatedAdd(this.hitCount, other.hitCount), saturatedAdd(this.missCount, other.missCount), saturatedAdd(this.loadSuccessCount, other.loadSuccessCount), saturatedAdd(this.loadFailureCount, other.loadFailureCount), saturatedAdd(this.totalLoadTime, other.totalLoadTime), saturatedAdd(this.evictionCount, other.evictionCount), saturatedAdd(this.evictionWeight, other.evictionWeight));
    }

    private static long saturatedSubtract(long a, long b) {
        long naiveDifference = a - b;
        if (((a ^ b) >= 0) | ((a ^ naiveDifference) >= 0)) {
            return naiveDifference;
        }
        return LongCompanionObject.MAX_VALUE + ((naiveDifference >>> 63) ^ 1);
    }

    private static long saturatedAdd(long a, long b) {
        long naiveSum = a + b;
        if (((a ^ b) < 0) | ((a ^ naiveSum) >= 0)) {
            return naiveSum;
        }
        return LongCompanionObject.MAX_VALUE + ((naiveSum >>> 63) ^ 1);
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadFailureCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount), Long.valueOf(this.evictionWeight));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CacheStats)) {
            return false;
        }
        CacheStats other = (CacheStats) o;
        return this.hitCount == other.hitCount && this.missCount == other.missCount && this.loadSuccessCount == other.loadSuccessCount && this.loadFailureCount == other.loadFailureCount && this.totalLoadTime == other.totalLoadTime && this.evictionCount == other.evictionCount && this.evictionWeight == other.evictionWeight;
    }

    public String toString() {
        return getClass().getSimpleName() + "{hitCount=" + this.hitCount + ", missCount=" + this.missCount + ", loadSuccessCount=" + this.loadSuccessCount + ", loadFailureCount=" + this.loadFailureCount + ", totalLoadTime=" + this.totalLoadTime + ", evictionCount=" + this.evictionCount + ", evictionWeight=" + this.evictionWeight + '}';
    }
}
