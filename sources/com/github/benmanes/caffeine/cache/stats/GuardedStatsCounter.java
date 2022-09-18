package com.github.benmanes.caffeine.cache.stats;

import com.github.benmanes.caffeine.cache.RemovalCause;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/stats/GuardedStatsCounter.class */
public final class GuardedStatsCounter implements StatsCounter {
    static final Logger logger = Logger.getLogger(GuardedStatsCounter.class.getName());
    final StatsCounter delegate;

    public GuardedStatsCounter(StatsCounter delegate) {
        this.delegate = (StatsCounter) Objects.requireNonNull(delegate);
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordHits(int count) {
        try {
            this.delegate.recordHits(count);
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordMisses(int count) {
        try {
            this.delegate.recordMisses(count);
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordLoadSuccess(long loadTime) {
        try {
            this.delegate.recordLoadSuccess(loadTime);
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordLoadFailure(long loadTime) {
        try {
            this.delegate.recordLoadFailure(loadTime);
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordEviction() {
        try {
            this.delegate.recordEviction();
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordEviction(int weight) {
        try {
            this.delegate.recordEviction(weight);
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public void recordEviction(int weight, RemovalCause cause) {
        try {
            this.delegate.recordEviction(weight, cause);
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.stats.StatsCounter
    public CacheStats snapshot() {
        try {
            return this.delegate.snapshot();
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by stats counter", t);
            return CacheStats.empty();
        }
    }

    public String toString() {
        return this.delegate.toString();
    }
}
