package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SerializationProxy.class */
public final class SerializationProxy<K, V> implements Serializable {
    private static final long serialVersionUID = 1;
    boolean async;
    boolean weakKeys;
    boolean weakValues;
    boolean softValues;
    boolean isRecordingStats;
    long refreshAfterWriteNanos;
    long expiresAfterWriteNanos;
    long expiresAfterAccessNanos;
    long maximumSize = -1;
    long maximumWeight = -1;
    Ticker ticker;
    Expiry<?, ?> expiry;
    Weigher<?, ?> weigher;
    CacheWriter<?, ?> writer;
    AsyncCacheLoader<?, ?> loader;
    RemovalListener<?, ?> removalListener;

    Caffeine<Object, Object> recreateCaffeine() {
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (this.ticker != null) {
            builder.ticker(this.ticker);
        }
        if (this.isRecordingStats) {
            builder.recordStats();
        }
        if (this.maximumSize != -1) {
            builder.maximumSize(this.maximumSize);
        }
        if (this.weigher != null) {
            builder.maximumWeight(this.maximumWeight);
            builder.weigher(this.weigher);
        }
        if (this.expiry != null) {
            builder.expireAfter(this.expiry);
        }
        if (this.expiresAfterWriteNanos > 0) {
            builder.expireAfterWrite(this.expiresAfterWriteNanos, TimeUnit.NANOSECONDS);
        }
        if (this.expiresAfterAccessNanos > 0) {
            builder.expireAfterAccess(this.expiresAfterAccessNanos, TimeUnit.NANOSECONDS);
        }
        if (this.refreshAfterWriteNanos > 0) {
            builder.refreshAfterWrite(this.refreshAfterWriteNanos, TimeUnit.NANOSECONDS);
        }
        if (this.weakKeys) {
            builder.weakKeys();
        }
        if (this.weakValues) {
            builder.weakValues();
        }
        if (this.softValues) {
            builder.softValues();
        }
        if (this.removalListener != null) {
            builder.removalListener(this.removalListener);
        }
        if (this.writer != null && this.writer != CacheWriter.disabledWriter()) {
            if (this.writer instanceof Caffeine.CacheWriterAdapter) {
                builder.evictionListener((RemovalListener<? super K, ? super V>) ((Caffeine.CacheWriterAdapter) this.writer).delegate);
            } else {
                builder.writer(this.writer);
            }
        }
        return builder;
    }

    Object readResolve() {
        Caffeine<Object, Object> builder = recreateCaffeine();
        if (this.async) {
            if (this.loader == null) {
                return builder.buildAsync();
            }
            return builder.buildAsync(this.loader);
        } else if (this.loader == null) {
            return builder.build();
        } else {
            return builder.build((CacheLoader) this.loader);
        }
    }
}
