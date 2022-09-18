package com.github.benmanes.caffeine.cache.stats;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/stats/DisabledStatsCounter.class */
public enum DisabledStatsCounter implements StatsCounter {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordHits(int count) {
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordMisses(int count) {
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordLoadSuccess(long loadTime) {
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordLoadFailure(long loadTime) {
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordEviction() {
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public CacheStats snapshot() {
        return CacheStats.empty();
    }

    @Override // java.lang.Enum
    public String toString() {
        return snapshot().toString();
    }
}
