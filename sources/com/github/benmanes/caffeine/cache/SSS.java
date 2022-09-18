package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.stats.StatsCounter;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SSS.class */
public class SSS<K, V> extends C0024SS<K, V> {
    final StatsCounter statsCounter;

    public SSS(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        this.statsCounter = builder.getStatsCounterSupplier().get();
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public final boolean isRecordingStats() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public final Ticker statsTicker() {
        return Ticker.systemTicker();
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public final StatsCounter statsCounter() {
        return this.statsCounter;
    }
}
