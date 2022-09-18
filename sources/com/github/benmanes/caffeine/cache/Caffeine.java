package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Async;
import com.github.benmanes.caffeine.cache.BoundedLocalCache;
import com.github.benmanes.caffeine.cache.UnboundedLocalCache;
import com.github.benmanes.caffeine.cache.stats.ConcurrentStatsCounter;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.google.errorprone.annotations.FormatMethod;
import java.io.Serializable;
import java.time.Duration;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Caffeine.class */
public final class Caffeine<K, V> {
    static final Logger logger = Logger.getLogger(Caffeine.class.getName());
    static final Supplier<StatsCounter> ENABLED_STATS_COUNTER_SUPPLIER = ConcurrentStatsCounter::new;
    static final int UNSET_INT = -1;
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int DEFAULT_EXPIRATION_NANOS = 0;
    static final int DEFAULT_REFRESH_NANOS = 0;
    boolean strictParsing = true;
    long maximumSize = -1;
    long maximumWeight = -1;
    int initialCapacity = -1;
    long expireAfterWriteNanos = -1;
    long expireAfterAccessNanos = -1;
    long refreshAfterWriteNanos = -1;
    RemovalListener<? super K, ? super V> evictionListener;
    RemovalListener<? super K, ? super V> removalListener;
    Supplier<StatsCounter> statsCounterSupplier;
    CacheWriter<? super K, ? super V> writer;
    Weigher<? super K, ? super V> weigher;
    Expiry<? super K, ? super V> expiry;
    Scheduler scheduler;
    Executor executor;
    Ticker ticker;
    Strength keyStrength;
    Strength valueStrength;

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Caffeine$Strength.class */
    public enum Strength {
        WEAK,
        SOFT
    }

    private Caffeine() {
    }

    @FormatMethod
    public static void requireArgument(boolean expression, String template, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(template, args));
        }
    }

    public static void requireArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void requireState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    @FormatMethod
    public static void requireState(boolean expression, String template, Object... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(template, args));
        }
    }

    public static int ceilingPowerOfTwo(int x) {
        return 1 << (-Integer.numberOfLeadingZeros(x - 1));
    }

    public static long ceilingPowerOfTwo(long x) {
        return 1 << (-Long.numberOfLeadingZeros(x - 1));
    }

    public static Caffeine<Object, Object> newBuilder() {
        return new Caffeine<>();
    }

    public static Caffeine<Object, Object> from(CaffeineSpec spec) {
        Caffeine<Object, Object> builder = spec.toBuilder();
        builder.strictParsing = false;
        return builder;
    }

    public static Caffeine<Object, Object> from(String spec) {
        return from(CaffeineSpec.parse(spec));
    }

    public Caffeine<K, V> initialCapacity(int initialCapacity) {
        requireState(this.initialCapacity == -1, "initial capacity was already set to %s", Integer.valueOf(this.initialCapacity));
        requireArgument(initialCapacity >= 0);
        this.initialCapacity = initialCapacity;
        return this;
    }

    public boolean hasInitialCapacity() {
        return this.initialCapacity != -1;
    }

    public int getInitialCapacity() {
        if (hasInitialCapacity()) {
            return this.initialCapacity;
        }
        return 16;
    }

    public Caffeine<K, V> executor(Executor executor) {
        requireState(this.executor == null, "executor was already set to %s", this.executor);
        this.executor = (Executor) Objects.requireNonNull(executor);
        return this;
    }

    public Executor getExecutor() {
        return this.executor == null ? ForkJoinPool.commonPool() : this.executor;
    }

    public Caffeine<K, V> scheduler(Scheduler scheduler) {
        requireState(this.scheduler == null, "scheduler was already set to %s", this.scheduler);
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler);
        return this;
    }

    public Scheduler getScheduler() {
        if (this.scheduler == null || this.scheduler == Scheduler.disabledScheduler()) {
            return Scheduler.disabledScheduler();
        }
        if (this.scheduler == Scheduler.systemScheduler()) {
            return this.scheduler;
        }
        return Scheduler.guardedScheduler(this.scheduler);
    }

    public Caffeine<K, V> maximumSize(long maximumSize) {
        requireState(this.maximumSize == -1, "maximum size was already set to %s", Long.valueOf(this.maximumSize));
        requireState(this.maximumWeight == -1, "maximum weight was already set to %s", Long.valueOf(this.maximumWeight));
        requireState(this.weigher == null, "maximum size can not be combined with weigher", new Object[0]);
        requireArgument(maximumSize >= 0, "maximum size must not be negative", new Object[0]);
        this.maximumSize = maximumSize;
        return this;
    }

    public Caffeine<K, V> maximumWeight(long maximumWeight) {
        requireState(this.maximumWeight == -1, "maximum weight was already set to %s", Long.valueOf(this.maximumWeight));
        requireState(this.maximumSize == -1, "maximum size was already set to %s", Long.valueOf(this.maximumSize));
        requireArgument(maximumWeight >= 0, "maximum weight must not be negative", new Object[0]);
        this.maximumWeight = maximumWeight;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K1 extends K, V1 extends V> Caffeine<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
        Objects.requireNonNull(weigher);
        requireState(this.weigher == null, "weigher was already set to %s", this.weigher);
        requireState(!this.strictParsing || this.maximumSize == -1, "weigher can not be combined with maximum size", new Object[0]);
        this.weigher = weigher;
        return this;
    }

    public boolean evicts() {
        return getMaximum() != -1;
    }

    public boolean isWeighted() {
        return this.weigher != null;
    }

    public long getMaximum() {
        return isWeighted() ? this.maximumWeight : this.maximumSize;
    }

    public <K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher(boolean isAsync) {
        Weigher<K1, V1> weigher;
        if (this.weigher == null || this.weigher == Weigher.singletonWeigher()) {
            weigher = Weigher.singletonWeigher();
        } else {
            weigher = Weigher.boundedWeigher(this.weigher);
        }
        Weigher<K1, V1> delegate = weigher;
        return isAsync ? new Async.AsyncWeigher(delegate) : delegate;
    }

    public Caffeine<K, V> weakKeys() {
        requireState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        requireState(this.writer == null, "Weak keys may not be used with CacheWriter", new Object[0]);
        this.keyStrength = Strength.WEAK;
        return this;
    }

    public boolean isStrongKeys() {
        return this.keyStrength == null;
    }

    public Caffeine<K, V> weakValues() {
        requireState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = Strength.WEAK;
        return this;
    }

    public boolean isStrongValues() {
        return this.valueStrength == null;
    }

    public boolean isWeakValues() {
        return this.valueStrength == Strength.WEAK;
    }

    public Caffeine<K, V> softValues() {
        requireState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = Strength.SOFT;
        return this;
    }

    public Caffeine<K, V> expireAfterWrite(Duration duration) {
        return expireAfterWrite(saturatedToNanos(duration), TimeUnit.NANOSECONDS);
    }

    public Caffeine<K, V> expireAfterWrite(long duration, TimeUnit unit) {
        requireState(this.expireAfterWriteNanos == -1, "expireAfterWrite was already set to %s ns", Long.valueOf(this.expireAfterWriteNanos));
        requireState(this.expiry == null, "expireAfterWrite may not be used with variable expiration", new Object[0]);
        requireArgument(duration >= 0, "duration cannot be negative: %s %s", Long.valueOf(duration), unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        return this;
    }

    public long getExpiresAfterWriteNanos() {
        if (expiresAfterWrite()) {
            return this.expireAfterWriteNanos;
        }
        return 0L;
    }

    public boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos != -1;
    }

    public Caffeine<K, V> expireAfterAccess(Duration duration) {
        return expireAfterAccess(saturatedToNanos(duration), TimeUnit.NANOSECONDS);
    }

    public Caffeine<K, V> expireAfterAccess(long duration, TimeUnit unit) {
        requireState(this.expireAfterAccessNanos == -1, "expireAfterAccess was already set to %s ns", Long.valueOf(this.expireAfterAccessNanos));
        requireState(this.expiry == null, "expireAfterAccess may not be used with variable expiration", new Object[0]);
        requireArgument(duration >= 0, "duration cannot be negative: %s %s", Long.valueOf(duration), unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        return this;
    }

    public long getExpiresAfterAccessNanos() {
        if (expiresAfterAccess()) {
            return this.expireAfterAccessNanos;
        }
        return 0L;
    }

    public boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos != -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K1 extends K, V1 extends V> Caffeine<K1, V1> expireAfter(Expiry<? super K1, ? super V1> expiry) {
        Objects.requireNonNull(expiry);
        requireState(this.expiry == null, "Expiry was already set to %s", this.expiry);
        requireState(this.expireAfterAccessNanos == -1, "Expiry may not be used with expiresAfterAccess", new Object[0]);
        requireState(this.expireAfterWriteNanos == -1, "Expiry may not be used with expiresAfterWrite", new Object[0]);
        this.expiry = expiry;
        return this;
    }

    public boolean expiresVariable() {
        return this.expiry != null;
    }

    public Expiry<K, V> getExpiry(boolean isAsync) {
        if (isAsync && this.expiry != null) {
            return new Async.AsyncExpiry(this.expiry);
        }
        return (Expiry<? super K, ? super V>) this.expiry;
    }

    public Caffeine<K, V> refreshAfterWrite(Duration duration) {
        return refreshAfterWrite(saturatedToNanos(duration), TimeUnit.NANOSECONDS);
    }

    public Caffeine<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
        Objects.requireNonNull(unit);
        requireState(this.refreshAfterWriteNanos == -1, "refreshAfterWriteNanos was already set to %s ns", Long.valueOf(this.refreshAfterWriteNanos));
        requireArgument(duration > 0, "duration must be positive: %s %s", Long.valueOf(duration), unit);
        this.refreshAfterWriteNanos = unit.toNanos(duration);
        return this;
    }

    public long getRefreshAfterWriteNanos() {
        if (refreshAfterWrite()) {
            return this.refreshAfterWriteNanos;
        }
        return 0L;
    }

    public boolean refreshAfterWrite() {
        return this.refreshAfterWriteNanos != -1;
    }

    public Caffeine<K, V> ticker(Ticker ticker) {
        requireState(this.ticker == null, "Ticker was already set to %s", this.ticker);
        this.ticker = (Ticker) Objects.requireNonNull(ticker);
        return this;
    }

    public Ticker getTicker() {
        boolean useTicker = expiresVariable() || expiresAfterAccess() || expiresAfterWrite() || refreshAfterWrite() || isRecordingStats();
        if (useTicker) {
            return this.ticker == null ? Ticker.systemTicker() : this.ticker;
        }
        return Ticker.disabledTicker();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K1 extends K, V1 extends V> Caffeine<K1, V1> evictionListener(RemovalListener<? super K1, ? super V1> evictionListener) {
        requireState(this.evictionListener == null, "eviction listener was already set to %s", this.evictionListener);
        this.evictionListener = (RemovalListener) Objects.requireNonNull(evictionListener);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K1 extends K, V1 extends V> Caffeine<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener) {
        requireState(this.removalListener == null, "removal listener was already set to %s", this.removalListener);
        this.removalListener = (RemovalListener) Objects.requireNonNull(removalListener);
        return this;
    }

    public <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener(boolean async) {
        RemovalListener removalListener = (RemovalListener<? super K, ? super V>) this.removalListener;
        if (async && removalListener != null) {
            return new Async.AsyncRemovalListener(removalListener, getExecutor());
        }
        return removalListener;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public <K1 extends K, V1 extends V> Caffeine<K1, V1> writer(CacheWriter<? super K1, ? super V1> writer) {
        requireState(this.writer == null, "Writer was already set to %s", this.writer);
        requireState(this.keyStrength == null, "Weak keys may not be used with CacheWriter", new Object[0]);
        requireState(this.evictionListener == null, "Eviction listener may not be used with CacheWriter", new Object[0]);
        this.writer = (CacheWriter) Objects.requireNonNull(writer);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K1 extends K, V1 extends V> CacheWriter<K1, V1> getCacheWriter(boolean async) {
        CacheWriter cacheWriter;
        if (this.evictionListener == null) {
            cacheWriter = (CacheWriter<? super K, ? super V>) this.writer;
        } else {
            cacheWriter = new CacheWriterAdapter(this.evictionListener, async);
        }
        return cacheWriter == null ? CacheWriter.disabledWriter() : cacheWriter;
    }

    public Caffeine<K, V> recordStats() {
        requireState(this.statsCounterSupplier == null, "Statistics recording was already set", new Object[0]);
        this.statsCounterSupplier = ENABLED_STATS_COUNTER_SUPPLIER;
        return this;
    }

    public Caffeine<K, V> recordStats(Supplier<? extends StatsCounter> statsCounterSupplier) {
        requireState(this.statsCounterSupplier == null, "Statistics recording was already set", new Object[0]);
        Objects.requireNonNull(statsCounterSupplier);
        this.statsCounterSupplier = () -> {
            return StatsCounter.guardedStatsCounter((StatsCounter) statsCounterSupplier.get());
        };
        return this;
    }

    public boolean isRecordingStats() {
        return this.statsCounterSupplier != null;
    }

    public Supplier<StatsCounter> getStatsCounterSupplier() {
        if (this.statsCounterSupplier == null) {
            return StatsCounter::disabledStatsCounter;
        }
        return this.statsCounterSupplier;
    }

    boolean isBounded() {
        return (this.maximumSize == -1 && this.maximumWeight == -1 && this.expireAfterAccessNanos == -1 && this.expireAfterWriteNanos == -1 && this.expiry == null && this.keyStrength == null && this.valueStrength == null) ? false : true;
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        requireWeightWithWeigher();
        requireNonLoadingCache();
        return isBounded() ? new BoundedLocalCache.BoundedLocalManualCache(this) : new UnboundedLocalCache.UnboundedLocalManualCache(this);
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
        requireWeightWithWeigher();
        return (isBounded() || refreshAfterWrite()) ? new BoundedLocalCache.BoundedLocalLoadingCache(this, loader) : new UnboundedLocalCache.UnboundedLocalLoadingCache(this, loader);
    }

    public <K1 extends K, V1 extends V> AsyncCache<K1, V1> buildAsync() {
        requireState(this.valueStrength == null, "Weak or soft values can not be combined with AsyncCache", new Object[0]);
        requireState(this.writer == null, "CacheWriter can not be combined with AsyncCache", new Object[0]);
        requireState(isStrongKeys() || this.evictionListener == null, "Weak keys cannot be combined eviction listener and with AsyncLoadingCache", new Object[0]);
        requireWeightWithWeigher();
        requireNonLoadingCache();
        return isBounded() ? new BoundedLocalCache.BoundedLocalAsyncCache(this) : new UnboundedLocalCache.UnboundedLocalAsyncCache(this);
    }

    public <K1 extends K, V1 extends V> AsyncLoadingCache<K1, V1> buildAsync(CacheLoader<? super K1, V1> loader) {
        return buildAsync((AsyncCacheLoader) loader);
    }

    public <K1 extends K, V1 extends V> AsyncLoadingCache<K1, V1> buildAsync(AsyncCacheLoader<? super K1, V1> loader) {
        requireState(isStrongValues(), "Weak or soft values cannot be combined with AsyncLoadingCache", new Object[0]);
        requireState(this.writer == null, "CacheWriter cannot be combined with AsyncLoadingCache", new Object[0]);
        requireState(isStrongKeys() || this.evictionListener == null, "Weak keys cannot be combined eviction listener and with AsyncLoadingCache", new Object[0]);
        requireWeightWithWeigher();
        Objects.requireNonNull(loader);
        return (isBounded() || refreshAfterWrite()) ? new BoundedLocalCache.BoundedLocalAsyncLoadingCache(this, loader) : new UnboundedLocalCache.UnboundedLocalAsyncLoadingCache(this, loader);
    }

    void requireNonLoadingCache() {
        requireState(this.refreshAfterWriteNanos == -1, "refreshAfterWrite requires a LoadingCache", new Object[0]);
    }

    void requireWeightWithWeigher() {
        if (this.weigher == null) {
            requireState(this.maximumWeight == -1, "maximumWeight requires weigher", new Object[0]);
        } else if (this.strictParsing) {
            requireState(this.maximumWeight != -1, "weigher requires maximumWeight", new Object[0]);
        } else if (this.maximumWeight == -1) {
            logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
        }
    }

    private static long saturatedToNanos(Duration duration) {
        try {
            return duration.toNanos();
        } catch (ArithmeticException e) {
            if (!duration.isNegative()) {
                return LongCompanionObject.MAX_VALUE;
            }
            return Long.MIN_VALUE;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(75);
        s.append(getClass().getSimpleName()).append('{');
        int baseLength = s.length();
        if (this.initialCapacity != -1) {
            s.append("initialCapacity=").append(this.initialCapacity).append(", ");
        }
        if (this.maximumSize != -1) {
            s.append("maximumSize=").append(this.maximumSize).append(", ");
        }
        if (this.maximumWeight != -1) {
            s.append("maximumWeight=").append(this.maximumWeight).append(", ");
        }
        if (this.expireAfterWriteNanos != -1) {
            s.append("expireAfterWrite=").append(this.expireAfterWriteNanos).append("ns, ");
        }
        if (this.expireAfterAccessNanos != -1) {
            s.append("expireAfterAccess=").append(this.expireAfterAccessNanos).append("ns, ");
        }
        if (this.expiry != null) {
            s.append("expiry, ");
        }
        if (this.refreshAfterWriteNanos != -1) {
            s.append("refreshAfterWriteNanos=").append(this.refreshAfterWriteNanos).append("ns, ");
        }
        if (this.keyStrength != null) {
            s.append("keyStrength=").append(this.keyStrength.toString().toLowerCase(Locale.US)).append(", ");
        }
        if (this.valueStrength != null) {
            s.append("valueStrength=").append(this.valueStrength.toString().toLowerCase(Locale.US)).append(", ");
        }
        if (this.evictionListener != null) {
            s.append("evictionListener, ");
        }
        if (this.removalListener != null) {
            s.append("removalListener, ");
        }
        if (this.writer != null) {
            s.append("writer, ");
        }
        if (s.length() > baseLength) {
            s.deleteCharAt(s.length() - 2);
        }
        return s.append('}').toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Caffeine$CacheWriterAdapter.class */
    public static final class CacheWriterAdapter<K, V> implements CacheWriter<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final RemovalListener<? super K, ? super V> delegate;
        final boolean isAsync;

        CacheWriterAdapter(RemovalListener<? super K, ? super V> delegate, boolean isAsync) {
            this.delegate = delegate;
            this.isAsync = isAsync;
        }

        @Override // com.github.benmanes.caffeine.cache.CacheWriter
        public void write(K key, V value) {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.CacheWriter
        public void delete(K key, V value, RemovalCause cause) {
            if (cause.wasEvicted()) {
                try {
                    if (this.isAsync && value != null) {
                        CompletableFuture<V> future = (CompletableFuture) value;
                        value = Async.getIfReady(future);
                    }
                    this.delegate.onRemoval(key, value, cause);
                } catch (Throwable t) {
                    Caffeine.logger.log(Level.WARNING, "Exception thrown by eviction listener", t);
                }
            }
        }
    }
}
