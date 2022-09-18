package com.github.benmanes.caffeine.cache;

import com.google.errorprone.annotations.CompatibleWith;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Policy.class */
public interface Policy<K, V> {
    boolean isRecordingStats();

    Optional<Eviction<K, V>> eviction();

    Optional<Expiration<K, V>> expireAfterAccess();

    Optional<Expiration<K, V>> expireAfterWrite();

    Optional<Expiration<K, V>> refreshAfterWrite();

    default V getIfPresentQuietly(@CompatibleWith("K") Object key) {
        throw new UnsupportedOperationException();
    }

    default Optional<VarExpiration<K, V>> expireVariably() {
        return Optional.empty();
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Policy$Eviction.class */
    public interface Eviction<K, V> {
        boolean isWeighted();

        OptionalLong weightedSize();

        long getMaximum();

        void setMaximum(long j);

        Map<K, V> coldest(int i);

        Map<K, V> hottest(int i);

        default OptionalInt weightOf(K key) {
            return OptionalInt.empty();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Policy$Expiration.class */
    public interface Expiration<K, V> {
        OptionalLong ageOf(K k, TimeUnit timeUnit);

        long getExpiresAfter(TimeUnit timeUnit);

        void setExpiresAfter(long j, TimeUnit timeUnit);

        Map<K, V> oldest(int i);

        Map<K, V> youngest(int i);

        default Optional<Duration> ageOf(K key) {
            OptionalLong duration = ageOf(key, TimeUnit.NANOSECONDS);
            if (duration.isPresent()) {
                return Optional.of(Duration.ofNanos(duration.getAsLong()));
            }
            return Optional.empty();
        }

        default Duration getExpiresAfter() {
            return Duration.ofNanos(getExpiresAfter(TimeUnit.NANOSECONDS));
        }

        default void setExpiresAfter(Duration duration) {
            setExpiresAfter(duration.toNanos(), TimeUnit.NANOSECONDS);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Policy$VarExpiration.class */
    public interface VarExpiration<K, V> {
        OptionalLong getExpiresAfter(K k, TimeUnit timeUnit);

        void setExpiresAfter(K k, long j, TimeUnit timeUnit);

        Map<K, V> oldest(int i);

        Map<K, V> youngest(int i);

        default Optional<Duration> getExpiresAfter(K key) {
            OptionalLong duration = getExpiresAfter(key, TimeUnit.NANOSECONDS);
            if (duration.isPresent()) {
                return Optional.of(Duration.ofNanos(duration.getAsLong()));
            }
            return Optional.empty();
        }

        default void setExpiresAfter(K key, Duration duration) {
            setExpiresAfter(key, duration.toNanos(), TimeUnit.NANOSECONDS);
        }

        default boolean putIfAbsent(K key, V value, long duration, TimeUnit unit) {
            throw new UnsupportedOperationException();
        }

        default boolean putIfAbsent(K key, V value, Duration duration) {
            return putIfAbsent(key, value, duration.toNanos(), TimeUnit.NANOSECONDS);
        }

        default void put(K key, V value, long duration, TimeUnit unit) {
            throw new UnsupportedOperationException();
        }

        default void put(K key, V value, Duration duration) {
            put(key, value, duration.toNanos(), TimeUnit.NANOSECONDS);
        }
    }
}
