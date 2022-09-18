package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache.class */
public interface LocalAsyncCache<K, V> extends AsyncCache<K, V> {
    public static final Logger logger = Logger.getLogger(LocalAsyncCache.class.getName());

    LocalCache<K, CompletableFuture<V>> cache();

    Policy<K, V> policy();

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default CompletableFuture<V> getIfPresent(Object key) {
        return cache().getIfPresent(key, true);
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default CompletableFuture<V> get(K key, Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        return get((LocalAsyncCache<K, V>) key, (BiFunction<? super LocalAsyncCache<K, V>, Executor, CompletableFuture<V>>) k1, executor -> {
            return CompletableFuture.supplyAsync(() -> {
                return mappingFunction.apply(key);
            }, executor);
        });
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default CompletableFuture<V> get(K key, BiFunction<? super K, Executor, CompletableFuture<V>> mappingFunction) {
        return get(key, mappingFunction, true);
    }

    default CompletableFuture<V> get(K key, BiFunction<? super K, Executor, CompletableFuture<V>> mappingFunction, boolean recordStats) {
        long startTime = cache().statsTicker().read();
        CompletableFuture<V>[] result = new CompletableFuture[1];
        CompletableFuture<V> future = cache().computeIfAbsent(key, k -> {
            result[0] = (CompletableFuture) result.apply(mappingFunction, cache().executor());
            return (CompletableFuture) Objects.requireNonNull(result[0]);
        }, recordStats, false);
        if (result[0] != null) {
            handleCompletion(key, result[0], startTime, false);
        }
        return future;
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys, Function<Iterable<? extends K>, Map<K, V>> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        return getAll(keys, keysToLoad, executor -> {
            return CompletableFuture.supplyAsync(() -> {
                return (Map) mappingFunction.apply(keysToLoad);
            }, executor);
        });
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys, BiFunction<Iterable<? extends K>, Executor, CompletableFuture<Map<K, V>>> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        Objects.requireNonNull(keys);
        Map<K, CompletableFuture<V>> futures = new LinkedHashMap<>();
        Map<K, CompletableFuture<V>> proxies = new HashMap<>();
        for (K key : keys) {
            if (!futures.containsKey(key)) {
                CompletableFuture<V> future = cache().getIfPresent(key, false);
                if (future == null) {
                    CompletableFuture<V> proxy = new CompletableFuture<>();
                    future = cache().putIfAbsent(key, proxy);
                    if (future == null) {
                        future = proxy;
                        proxies.put(key, proxy);
                    }
                }
                futures.put(key, future);
            }
        }
        cache().statsCounter().recordMisses(proxies.size());
        cache().statsCounter().recordHits(futures.size() - proxies.size());
        if (proxies.isEmpty()) {
            return composeResult(futures);
        }
        AsyncBulkCompleter<K, V> completer = new AsyncBulkCompleter<>(cache(), proxies);
        try {
            mappingFunction.apply(proxies.keySet(), cache().executor()).whenComplete((BiConsumer<? super Map<K, V>, ? super Throwable>) completer);
            return composeResult(futures);
        } catch (Throwable t) {
            completer.accept((Map) null, t);
            throw t;
        }
    }

    default CompletableFuture<Map<K, V>> composeResult(Map<K, CompletableFuture<V>> futures) {
        if (futures.isEmpty()) {
            return CompletableFuture.completedFuture(Collections.emptyMap());
        }
        CompletableFuture<?>[] array = (CompletableFuture[]) futures.values().toArray(new CompletableFuture[0]);
        return (CompletableFuture<Map<K, V>>) CompletableFuture.allOf(array).thenApply(ignored -> {
            Map<K, V> result = new LinkedHashMap<>(futures.size());
            futures.forEach(key, future -> {
                Object now = future.getNow(null);
                if (now != null) {
                    result.put(key, now);
                }
            });
            return Collections.unmodifiableMap(result);
        });
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default void put(K key, CompletableFuture<V> valueFuture) {
        if (valueFuture.isCompletedExceptionally() || (valueFuture.isDone() && valueFuture.join() == null)) {
            cache().statsCounter().recordLoadFailure(0L);
            cache().remove(key);
            return;
        }
        long startTime = cache().statsTicker().read();
        cache().put(key, valueFuture);
        handleCompletion(key, valueFuture, startTime, false);
    }

    default void handleCompletion(K key, CompletableFuture<V> valueFuture, long startTime, boolean recordMiss) {
        AtomicBoolean completed = new AtomicBoolean();
        valueFuture.whenComplete(value, error -> {
            if (!completed.compareAndSet(false, true)) {
                return;
            }
            long loadTime = cache().statsTicker().read() - completed;
            if (recordMiss == null) {
                if (error != null) {
                    logger.log(Level.WARNING, "Exception thrown during asynchronous load", error);
                }
                cache().remove(startTime, key);
                cache().statsCounter().recordLoadFailure(loadTime);
                if (valueFuture) {
                    cache().statsCounter().recordMisses(1);
                    return;
                }
                return;
            }
            cache().replace(startTime, key, key);
            cache().statsCounter().recordLoadSuccess(loadTime);
            if (valueFuture) {
                cache().statsCounter().recordMisses(1);
            }
        });
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AsyncBulkCompleter.class */
    public static final class AsyncBulkCompleter<K, V> implements BiConsumer<Map<K, V>, Throwable> {
        private final LocalCache<K, CompletableFuture<V>> cache;
        private final Map<K, CompletableFuture<V>> proxies;
        private final long startTime;

        @Override // java.util.function.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object obj, Throwable th) {
            accept((Map) ((Map) obj), th);
        }

        AsyncBulkCompleter(LocalCache<K, CompletableFuture<V>> cache, Map<K, CompletableFuture<V>> proxies) {
            this.startTime = cache.statsTicker().read();
            this.proxies = proxies;
            this.cache = cache;
        }

        public void accept(Map<K, V> result, Throwable error) {
            long loadTime = this.cache.statsTicker().read() - this.startTime;
            if (result == null) {
                if (error == null) {
                    error = new NullMapCompletionException();
                }
                for (Map.Entry<K, CompletableFuture<V>> entry : this.proxies.entrySet()) {
                    this.cache.remove(entry.getKey(), entry.getValue());
                    entry.getValue().obtrudeException(error);
                }
                this.cache.statsCounter().recordLoadFailure(loadTime);
                LocalAsyncCache.logger.log(Level.WARNING, "Exception thrown during asynchronous load", error);
                return;
            }
            fillProxies(result);
            addNewEntries(result);
            this.cache.statsCounter().recordLoadSuccess(loadTime);
        }

        private void fillProxies(Map<K, V> result) {
            this.proxies.forEach(key, future -> {
                Object obj = result.get(result);
                future.obtrudeValue(obj);
                if (obj == null) {
                    this.cache.remove(result, future);
                } else {
                    this.cache.replace(result, future, future);
                }
            });
        }

        private void addNewEntries(Map<K, V> result) {
            if (this.proxies.size() == result.size()) {
                return;
            }
            result.forEach(key, value -> {
                if (!this.proxies.containsKey(key)) {
                    this.cache.put(key, CompletableFuture.completedFuture(value));
                }
            });
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AsyncBulkCompleter$NullMapCompletionException.class */
        public static final class NullMapCompletionException extends CompletionException {
            private static final long serialVersionUID = 1;

            public NullMapCompletionException() {
                super("null map", null);
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AsyncAsMapView.class */
    public static final class AsyncAsMapView<K, V> implements ConcurrentMap<K, CompletableFuture<V>> {
        final LocalAsyncCache<K, V> asyncCache;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public /* bridge */ /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
            return merge((AsyncAsMapView<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2), biFunction);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public /* bridge */ /* synthetic */ Object replace(Object obj, Object obj2) {
            return replace((AsyncAsMapView<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public /* bridge */ /* synthetic */ boolean replace(Object obj, Object obj2, Object obj3) {
            return replace((AsyncAsMapView<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2), (CompletableFuture) ((CompletableFuture) obj3));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public /* bridge */ /* synthetic */ Object putIfAbsent(Object obj, Object obj2) {
            return putIfAbsent((AsyncAsMapView<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map
        public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
            return put((AsyncAsMapView<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2));
        }

        public AsyncAsMapView(LocalAsyncCache<K, V> asyncCache) {
            this.asyncCache = (LocalAsyncCache) Objects.requireNonNull(asyncCache);
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.asyncCache.cache().isEmpty();
        }

        @Override // java.util.Map
        public int size() {
            return this.asyncCache.cache().size();
        }

        @Override // java.util.Map
        public void clear() {
            this.asyncCache.cache().clear();
        }

        @Override // java.util.Map
        public boolean containsKey(Object key) {
            return this.asyncCache.cache().containsKey(key);
        }

        @Override // java.util.Map
        public boolean containsValue(Object value) {
            return this.asyncCache.cache().containsValue(value);
        }

        @Override // java.util.Map
        public CompletableFuture<V> get(Object key) {
            return (CompletableFuture) this.asyncCache.cache().get(key);
        }

        public CompletableFuture<V> putIfAbsent(K key, CompletableFuture<V> value) {
            CompletableFuture<V> prior = this.asyncCache.cache().putIfAbsent(key, value);
            long startTime = this.asyncCache.cache().statsTicker().read();
            if (prior == null) {
                this.asyncCache.handleCompletion(key, value, startTime, false);
            }
            return prior;
        }

        public CompletableFuture<V> put(K key, CompletableFuture<V> value) {
            CompletableFuture<V> prior = (CompletableFuture) this.asyncCache.cache().put(key, value);
            long startTime = this.asyncCache.cache().statsTicker().read();
            this.asyncCache.handleCompletion(key, value, startTime, false);
            return prior;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends CompletableFuture<V>> map) {
            map.forEach(this::put);
        }

        public CompletableFuture<V> replace(K key, CompletableFuture<V> value) {
            CompletableFuture<V> prior = this.asyncCache.cache().replace(key, value);
            long startTime = this.asyncCache.cache().statsTicker().read();
            if (prior != null) {
                this.asyncCache.handleCompletion(key, value, startTime, false);
            }
            return prior;
        }

        public boolean replace(K key, CompletableFuture<V> oldValue, CompletableFuture<V> newValue) {
            boolean replaced = this.asyncCache.cache().replace(key, oldValue, newValue);
            long startTime = this.asyncCache.cache().statsTicker().read();
            if (replaced) {
                this.asyncCache.handleCompletion(key, newValue, startTime, false);
            }
            return replaced;
        }

        @Override // java.util.Map
        public CompletableFuture<V> remove(Object key) {
            return (CompletableFuture) this.asyncCache.cache().remove(key);
        }

        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public boolean remove(Object key, Object value) {
            return this.asyncCache.cache().remove(key, value);
        }

        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public CompletableFuture<V> computeIfAbsent(K key, Function<? super K, ? extends CompletableFuture<V>> mappingFunction) {
            Objects.requireNonNull(mappingFunction);
            CompletableFuture<V>[] result = new CompletableFuture[1];
            long startTime = this.asyncCache.cache().statsTicker().read();
            CompletableFuture<V> future = this.asyncCache.cache().computeIfAbsent(key, k -> {
                result[0] = (CompletableFuture) mappingFunction.apply(k);
                return result[0];
            }, false, false);
            if (result[0] == null) {
                if (future != null && this.asyncCache.cache().isRecordingStats()) {
                    future.whenComplete(r, e -> {
                        if (r != null || e == null) {
                            this.asyncCache.cache().statsCounter().recordHits(1);
                        }
                    });
                }
            } else {
                this.asyncCache.handleCompletion(key, result[0], startTime, true);
            }
            return future;
        }

        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public CompletableFuture<V> computeIfPresent(K key, BiFunction<? super K, ? super CompletableFuture<V>, ? extends CompletableFuture<V>> remappingFunction) {
            Objects.requireNonNull(remappingFunction);
            CompletableFuture<V>[] result = new CompletableFuture[1];
            long startTime = this.asyncCache.cache().statsTicker().read();
            this.asyncCache.cache().compute(key, k, oldValue -> {
                result[0] = oldValue == null ? null : (CompletableFuture) remappingFunction.apply(k, oldValue);
                return result[0];
            }, false, false, false);
            if (result[0] != null) {
                this.asyncCache.handleCompletion(key, result[0], startTime, false);
            }
            return result[0];
        }

        @Override // java.util.concurrent.ConcurrentMap, java.util.Map
        public CompletableFuture<V> compute(K key, BiFunction<? super K, ? super CompletableFuture<V>, ? extends CompletableFuture<V>> remappingFunction) {
            Objects.requireNonNull(remappingFunction);
            CompletableFuture<V>[] result = new CompletableFuture[1];
            long startTime = this.asyncCache.cache().statsTicker().read();
            this.asyncCache.cache().compute(key, k, oldValue -> {
                result[0] = (CompletableFuture) remappingFunction.apply(k, oldValue);
                return result[0];
            }, false, false, false);
            if (result[0] != null) {
                this.asyncCache.handleCompletion(key, result[0], startTime, false);
            }
            return result[0];
        }

        public CompletableFuture<V> merge(K key, CompletableFuture<V> value, BiFunction<? super CompletableFuture<V>, ? super CompletableFuture<V>, ? extends CompletableFuture<V>> remappingFunction) {
            Objects.requireNonNull(value);
            Objects.requireNonNull(remappingFunction);
            CompletableFuture<V>[] result = new CompletableFuture[1];
            long startTime = this.asyncCache.cache().statsTicker().read();
            this.asyncCache.cache().compute(key, k, oldValue -> {
                result[0] = oldValue == null ? value : (CompletableFuture) remappingFunction.apply(oldValue, value);
                return result[0];
            }, false, false, false);
            if (result[0] != null) {
                this.asyncCache.handleCompletion(key, result[0], startTime, false);
            }
            return result[0];
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            return this.asyncCache.cache().keySet();
        }

        @Override // java.util.Map
        public Collection<CompletableFuture<V>> values() {
            return this.asyncCache.cache().values();
        }

        @Override // java.util.Map
        public Set<Map.Entry<K, CompletableFuture<V>>> entrySet() {
            return this.asyncCache.cache().entrySet();
        }

        @Override // java.util.Map
        public boolean equals(Object o) {
            return this.asyncCache.cache().equals(o);
        }

        @Override // java.util.Map
        public int hashCode() {
            return this.asyncCache.cache().hashCode();
        }

        public String toString() {
            return this.asyncCache.cache().toString();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$CacheView.class */
    public static final class CacheView<K, V> extends AbstractCacheView<K, V> {
        private static final long serialVersionUID = 1;
        final LocalAsyncCache<K, V> asyncCache;

        public CacheView(LocalAsyncCache<K, V> asyncCache) {
            this.asyncCache = (LocalAsyncCache) Objects.requireNonNull(asyncCache);
        }

        @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache.AbstractCacheView
        LocalAsyncCache<K, V> asyncCache() {
            return this.asyncCache;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AbstractCacheView.class */
    public static abstract class AbstractCacheView<K, V> implements Cache<K, V>, Serializable {
        transient AsMapView<K, V> asMapView;

        abstract LocalAsyncCache<K, V> asyncCache();

        @Override // com.github.benmanes.caffeine.cache.Cache
        public V getIfPresent(Object key) {
            CompletableFuture<V> future = asyncCache().cache().getIfPresent(key, true);
            return (V) Async.getIfReady(future);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.Cache
        public Map<K, V> getAllPresent(Iterable<?> keys) {
            Set<Object> uniqueKeys = new LinkedHashSet<>();
            Iterator<?> it = keys.iterator();
            while (it.hasNext()) {
                uniqueKeys.add(it.next());
            }
            int misses = 0;
            Map<Object, Object> result = new LinkedHashMap<>();
            for (Object key : uniqueKeys) {
                CompletableFuture<V> future = (CompletableFuture) asyncCache().cache().get(key);
                Object value = Async.getIfReady(future);
                if (value == null) {
                    misses++;
                } else {
                    result.put(key, value);
                }
            }
            asyncCache().cache().statsCounter().recordMisses(misses);
            asyncCache().cache().statsCounter().recordHits(result.size());
            return Collections.unmodifiableMap(result);
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public V get(K key, Function<? super K, ? extends V> mappingFunction) {
            return (V) resolve(asyncCache().get((LocalAsyncCache<K, V>) key, (Function<? super LocalAsyncCache<K, V>, ? extends V>) mappingFunction));
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public Map<K, V> getAll(Iterable<? extends K> keys, Function<Iterable<? extends K>, Map<K, V>> mappingFunction) {
            return (Map) resolve(asyncCache().getAll(keys, mappingFunction));
        }

        public static <T> T resolve(CompletableFuture<T> future) throws Error {
            try {
                return future.get();
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            } catch (ExecutionException e2) {
                if (e2.getCause() instanceof AsyncBulkCompleter.NullMapCompletionException) {
                    throw new NullPointerException(e2.getCause().getMessage());
                }
                if (e2.getCause() instanceof RuntimeException) {
                    throw ((RuntimeException) e2.getCause());
                }
                if (e2.getCause() instanceof Error) {
                    throw ((Error) e2.getCause());
                }
                throw new CompletionException(e2.getCause());
            }
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public void put(K key, V value) {
            Objects.requireNonNull(value);
            asyncCache().cache().put(key, CompletableFuture.completedFuture(value));
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public void putAll(Map<? extends K, ? extends V> map) {
            map.forEach(this::put);
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public void invalidate(Object key) {
            asyncCache().cache().remove(key);
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public void invalidateAll(Iterable<?> keys) {
            asyncCache().cache().invalidateAll(keys);
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public void invalidateAll() {
            asyncCache().cache().clear();
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public long estimatedSize() {
            return asyncCache().cache().size();
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public CacheStats stats() {
            return asyncCache().cache().statsCounter().snapshot();
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public void cleanUp() {
            asyncCache().cache().cleanUp();
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public Policy<K, V> policy() {
            return asyncCache().policy();
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public ConcurrentMap<K, V> asMap() {
            if (this.asMapView == null) {
                AsMapView<K, V> asMapView = new AsMapView<>(asyncCache().cache());
                this.asMapView = asMapView;
                return asMapView;
            }
            return this.asMapView;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AsMapView.class */
    public static final class AsMapView<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
        final LocalCache<K, CompletableFuture<V>> delegate;
        Collection<V> values;
        Set<Map.Entry<K, V>> entries;

        AsMapView(LocalCache<K, CompletableFuture<V>> delegate) {
            this.delegate = delegate;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.delegate.size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.delegate.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object key) {
            return this.delegate.containsKey(key);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(Object value) {
            Objects.requireNonNull(value);
            for (CompletableFuture<V> valueFuture : this.delegate.values()) {
                if (value.equals(Async.getIfReady(valueFuture))) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object key) {
            return (V) Async.getIfReady((CompletableFuture) this.delegate.get(key));
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public V putIfAbsent(K key, V value) {
            Objects.requireNonNull(value);
            while (true) {
                CompletableFuture<V> priorFuture = (CompletableFuture) this.delegate.get(key);
                if (priorFuture != null) {
                    if (!priorFuture.isDone()) {
                        Async.getWhenSuccessful(priorFuture);
                    } else {
                        V prior = (V) Async.getWhenSuccessful(priorFuture);
                        if (prior != null) {
                            return prior;
                        }
                    }
                }
                boolean[] added = {false};
                CompletableFuture<V> computed = this.delegate.compute(key, k, valueFuture -> {
                    added[0] = valueFuture == null || (valueFuture.isDone() && Async.getIfReady(valueFuture) == null);
                    return added[0] ? CompletableFuture.completedFuture(value) : valueFuture;
                }, false, false, false);
                if (added[0]) {
                    return null;
                }
                V prior2 = (V) Async.getWhenSuccessful(computed);
                if (prior2 != null) {
                    return prior2;
                }
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K key, V value) {
            Objects.requireNonNull(value);
            CompletableFuture<V> oldValueFuture = (CompletableFuture) this.delegate.put(key, CompletableFuture.completedFuture(value));
            return (V) Async.getWhenSuccessful(oldValueFuture);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object key) {
            CompletableFuture<V> oldValueFuture = (CompletableFuture) this.delegate.remove(key);
            return (V) Async.getWhenSuccessful(oldValueFuture);
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public boolean remove(Object key, Object value) {
            Objects.requireNonNull(key);
            if (value == null) {
                return false;
            }
            boolean[] done = {false};
            boolean[] removed = {false};
            do {
                CompletableFuture<V> future = (CompletableFuture) this.delegate.get(key);
                if (future == null || future.isCompletedExceptionally()) {
                    return false;
                }
                Async.getWhenSuccessful(future);
                this.delegate.compute(key, k, oldValueFuture -> {
                    if (oldValueFuture == null) {
                        done[0] = true;
                        return null;
                    } else if (!oldValueFuture.isDone()) {
                        return oldValueFuture;
                    } else {
                        done[0] = true;
                        Object ifReady = Async.getIfReady(oldValueFuture);
                        removed[0] = value.equals(ifReady);
                        if (ifReady != null && !removed[0]) {
                            return oldValueFuture;
                        }
                        return null;
                    }
                }, false, false, true);
            } while (!done[0]);
            return removed[0];
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public V replace(K key, V value) {
            Objects.requireNonNull(value);
            Object[] objArr = new Object[1];
            boolean[] done = {false};
            do {
                CompletableFuture<V> future = (CompletableFuture) this.delegate.get(key);
                if (future == null || future.isCompletedExceptionally()) {
                    return null;
                }
                Async.getWhenSuccessful(future);
                this.delegate.compute(key, k, oldValueFuture -> {
                    if (oldValueFuture == null) {
                        done[0] = true;
                        return null;
                    } else if (!oldValueFuture.isDone()) {
                        return oldValueFuture;
                    } else {
                        done[0] = true;
                        objArr[0] = Async.getIfReady(oldValueFuture);
                        if (objArr[0] != null) {
                            return CompletableFuture.completedFuture(value);
                        }
                        return null;
                    }
                }, false, false, false);
            } while (!done[0]);
            return (V) objArr[0];
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public boolean replace(K key, V oldValue, V newValue) {
            Objects.requireNonNull(oldValue);
            Objects.requireNonNull(newValue);
            boolean[] done = {false};
            boolean[] replaced = {false};
            do {
                CompletableFuture<V> future = (CompletableFuture) this.delegate.get(key);
                if (future == null || future.isCompletedExceptionally()) {
                    return false;
                }
                Async.getWhenSuccessful(future);
                this.delegate.compute(key, k, oldValueFuture -> {
                    if (oldValueFuture == null) {
                        done[0] = true;
                        return null;
                    } else if (!oldValueFuture.isDone()) {
                        return oldValueFuture;
                    } else {
                        done[0] = true;
                        replaced[0] = oldValue.equals(Async.getIfReady(oldValueFuture));
                        return replaced[0] ? CompletableFuture.completedFuture(newValue) : oldValueFuture;
                    }
                }, false, false, false);
            } while (!done[0]);
            return replaced[0];
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
            V result;
            Objects.requireNonNull(mappingFunction);
            while (true) {
                CompletableFuture<V> priorFuture = (CompletableFuture) this.delegate.get(key);
                if (priorFuture != null) {
                    if (!priorFuture.isDone()) {
                        Async.getWhenSuccessful(priorFuture);
                    } else {
                        V prior = (V) Async.getWhenSuccessful(priorFuture);
                        if (prior != null) {
                            this.delegate.statsCounter().recordHits(1);
                            return prior;
                        }
                    }
                }
                CompletableFuture<V>[] future = new CompletableFuture[1];
                CompletableFuture<V> computed = this.delegate.compute(key, k, valueFuture -> {
                    if (valueFuture != null && valueFuture.isDone() && Async.getIfReady(valueFuture) != null) {
                        return valueFuture;
                    }
                    Object apply = this.delegate.statsAware(mappingFunction, true).apply(mappingFunction);
                    if (apply == null) {
                        return null;
                    }
                    key[0] = CompletableFuture.completedFuture(apply);
                    return key[0];
                }, false, false, false);
                result = (V) Async.getWhenSuccessful(computed);
                if (computed == future[0] || result != null) {
                    break;
                }
            }
            return result;
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            CompletableFuture<V> valueFuture;
            Objects.requireNonNull(remappingFunction);
            Object[] objArr = new Object[1];
            do {
                Async.getWhenSuccessful((CompletableFuture) this.delegate.get(key));
                valueFuture = this.delegate.computeIfPresent(key, k, oldValueFuture -> {
                    if (!oldValueFuture.isDone()) {
                        return oldValueFuture;
                    }
                    Object ifReady = Async.getIfReady(oldValueFuture);
                    if (ifReady == null) {
                        return null;
                    }
                    objArr[0] = remappingFunction.apply(key, ifReady);
                    if (objArr[0] != null) {
                        return CompletableFuture.completedFuture(objArr[0]);
                    }
                    return null;
                });
                if (objArr[0] != null) {
                    return (V) objArr[0];
                }
            } while (valueFuture != null);
            return null;
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            CompletableFuture<V> valueFuture;
            Objects.requireNonNull(remappingFunction);
            Object[] objArr = new Object[1];
            do {
                Async.getWhenSuccessful((CompletableFuture) this.delegate.get(key));
                valueFuture = this.delegate.compute(key, k, oldValueFuture -> {
                    if (oldValueFuture != null && !oldValueFuture.isDone()) {
                        return oldValueFuture;
                    }
                    remappingFunction[0] = this.delegate.statsAware(remappingFunction, false, true, true).apply(objArr, Async.getIfReady(oldValueFuture));
                    if (remappingFunction[0] != null) {
                        return CompletableFuture.completedFuture(remappingFunction[0]);
                    }
                    return null;
                }, false, false, false);
                if (objArr[0] != null) {
                    return (V) objArr[0];
                }
            } while (valueFuture != null);
            return null;
        }

        @Override // java.util.Map, java.util.concurrent.ConcurrentMap
        public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            CompletableFuture<V> mergedValueFuture;
            Objects.requireNonNull(value);
            Objects.requireNonNull(remappingFunction);
            CompletableFuture<V> newValueFuture = CompletableFuture.completedFuture(value);
            boolean[] merged = {false};
            do {
                Async.getWhenSuccessful((CompletableFuture) this.delegate.get(key));
                mergedValueFuture = this.delegate.merge(key, newValueFuture, oldValueFuture, valueFuture -> {
                    if (oldValueFuture != null && !oldValueFuture.isDone()) {
                        return oldValueFuture;
                    }
                    merged[0] = true;
                    Object ifReady = Async.getIfReady(oldValueFuture);
                    if (ifReady == null) {
                        return valueFuture;
                    }
                    Object apply = remappingFunction.apply(ifReady, value);
                    if (apply == null) {
                        return null;
                    }
                    if (apply == ifReady) {
                        return oldValueFuture;
                    }
                    if (apply == value) {
                        return valueFuture;
                    }
                    return CompletableFuture.completedFuture(apply);
                });
                if (merged[0]) {
                    break;
                }
            } while (mergedValueFuture != newValueFuture);
            return (V) Async.getWhenSuccessful(mergedValueFuture);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.delegate.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> values() {
            if (this.values == null) {
                Values values = new Values();
                this.values = values;
                return values;
            }
            return this.values;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            if (this.entries == null) {
                EntrySet entrySet = new EntrySet();
                this.entries = entrySet;
                return entrySet;
            }
            return this.entries;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AsMapView$Values.class */
        public final class Values extends AbstractCollection<V> {
            private Values() {
                AsMapView.this = r4;
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean isEmpty() {
                return AsMapView.this.isEmpty();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AsMapView.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object o) {
                return AsMapView.this.containsValue(o);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AsMapView.this.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<V> iterator() {
                return new Iterator<V>() { // from class: com.github.benmanes.caffeine.cache.LocalAsyncCache.AsMapView.Values.1
                    Iterator<Map.Entry<K, V>> iterator;

                    {
                        Values.this = this;
                        this.iterator = AsMapView.this.entrySet().iterator();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.iterator.hasNext();
                    }

                    @Override // java.util.Iterator
                    public V next() {
                        return this.iterator.next().getValue();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.iterator.remove();
                    }
                };
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncCache$AsMapView$EntrySet.class */
        public final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
            private EntrySet() {
                AsMapView.this = r4;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return AsMapView.this.isEmpty();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AsMapView.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) o;
                Object obj = AsMapView.this.get(entry.getKey());
                return obj != null && obj.equals(entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                return AsMapView.this.remove(entry.getKey(), entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AsMapView.this.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new Iterator<Map.Entry<K, V>>() { // from class: com.github.benmanes.caffeine.cache.LocalAsyncCache.AsMapView.EntrySet.1
                    Iterator<Map.Entry<K, CompletableFuture<V>>> iterator;
                    Map.Entry<K, V> cursor;
                    K removalKey;

                    {
                        EntrySet.this = this;
                        this.iterator = AsMapView.this.delegate.entrySet().iterator();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        while (this.cursor == null && this.iterator.hasNext()) {
                            Map.Entry<K, CompletableFuture<V>> entry = this.iterator.next();
                            Object ifReady = Async.getIfReady(entry.getValue());
                            if (ifReady != null) {
                                this.cursor = new WriteThroughEntry(AsMapView.this, entry.getKey(), ifReady);
                            }
                        }
                        return this.cursor != null;
                    }

                    @Override // java.util.Iterator
                    public Map.Entry<K, V> next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        K key = this.cursor.getKey();
                        Map.Entry<K, V> entry = this.cursor;
                        this.removalKey = key;
                        this.cursor = null;
                        return entry;
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        Caffeine.requireState(this.removalKey != null);
                        AsMapView.this.delegate.remove(this.removalKey);
                        this.removalKey = null;
                    }
                };
            }
        }
    }
}
