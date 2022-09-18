package com.github.benmanes.caffeine.cache.stats;

import com.github.benmanes.caffeine.cache.RemovalCause;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/stats/StatsCounter.class */
public interface StatsCounter {
    void recordHits(int i);

    void recordMisses(int i);

    void recordLoadSuccess(long j);

    void recordLoadFailure(long j);

    @Deprecated
    void recordEviction();

    CacheStats snapshot();

    @Deprecated
    default void recordEviction(int weight) {
        recordEviction();
    }

    default void recordEviction(int weight, RemovalCause cause) {
        recordEviction(weight);
    }

    static StatsCounter disabledStatsCounter() {
        return DisabledStatsCounter.INSTANCE;
    }

    static StatsCounter guardedStatsCounter(StatsCounter statsCounter) {
        if (statsCounter instanceof GuardedStatsCounter) {
            return statsCounter;
        }
        return new GuardedStatsCounter(statsCounter);
    }
}
