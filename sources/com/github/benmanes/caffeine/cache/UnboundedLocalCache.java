package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.LocalAsyncCache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache.class */
public final class UnboundedLocalCache<K, V> implements LocalCache<K, V> {
    final RemovalListener<K, V> removalListener;
    final ConcurrentHashMap<K, V> data;
    final StatsCounter statsCounter;
    final boolean isRecordingStats;
    final CacheWriter<K, V> writer;
    final Executor executor;
    final Ticker ticker;
    transient Set<K> keySet;
    transient Collection<V> values;
    transient Set<Map.Entry<K, V>> entrySet;

    UnboundedLocalCache(Caffeine<? super K, ? super V> builder, boolean async) {
        this.data = new ConcurrentHashMap<>(builder.getInitialCapacity());
        this.statsCounter = builder.getStatsCounterSupplier().get();
        this.removalListener = (RemovalListener<K, V>) builder.getRemovalListener(async);
        this.isRecordingStats = builder.isRecordingStats();
        this.writer = (CacheWriter<K, V>) builder.getCacheWriter(async);
        this.executor = builder.getExecutor();
        this.ticker = builder.getTicker();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public boolean hasWriteTime() {
        return false;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V getIfPresent(Object key, boolean recordStats) {
        V value = this.data.get(key);
        if (recordStats) {
            if (value == null) {
                this.statsCounter.recordMisses(1);
            } else {
                this.statsCounter.recordHits(1);
            }
        }
        return value;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V getIfPresentQuietly(Object key, long[] writeTime) {
        return this.data.get(key);
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public long estimatedSize() {
        return this.data.mappingCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Map<K, V> getAllPresent(Iterable<?> keys) {
        Set<Object> uniqueKeys = new LinkedHashSet<>();
        Iterator<?> it = keys.iterator();
        while (it.hasNext()) {
            uniqueKeys.add(it.next());
        }
        int misses = 0;
        Map<Object, Object> result = new LinkedHashMap<>(uniqueKeys.size());
        for (Object key : uniqueKeys) {
            Object value = this.data.get(key);
            if (value == null) {
                misses++;
            } else {
                result.put(key, value);
            }
        }
        this.statsCounter.recordMisses(misses);
        this.statsCounter.recordHits(result.size());
        return Collections.unmodifiableMap(result);
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public void cleanUp() {
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public StatsCounter statsCounter() {
        return this.statsCounter;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public boolean hasRemovalListener() {
        return this.removalListener != null;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public RemovalListener<K, V> removalListener() {
        return this.removalListener;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public void notifyRemoval(K key, V value, RemovalCause cause) {
        Objects.requireNonNull(removalListener(), "Notification should be guarded with a check");
        this.executor.execute(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0017: INVOKE  
              (wrap: java.util.concurrent.Executor : 0x000b: IGET  (r0v4 java.util.concurrent.Executor A[REMOVE]) = 
              (r6v0 'this' com.github.benmanes.caffeine.cache.UnboundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.UnboundedLocalCache<K, V>), IMMUTABLE_TYPE, THIS])
             com.github.benmanes.caffeine.cache.UnboundedLocalCache.executor java.util.concurrent.Executor)
              (wrap: java.lang.Runnable : 0x0012: INVOKE_CUSTOM (r1v2 java.lang.Runnable A[REMOVE]) = 
              (r6v0 'this' com.github.benmanes.caffeine.cache.UnboundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.UnboundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r7v0 'key' K A[D('key' K), DONT_INLINE])
              (r8v0 'value' V A[D('value' V), DONT_INLINE])
              (r9v0 'cause' com.github.benmanes.caffeine.cache.RemovalCause A[D('cause' com.github.benmanes.caffeine.cache.RemovalCause), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.lang.Runnable.run():void
             call insn: ?: INVOKE  
              (r1 I:com.github.benmanes.caffeine.cache.UnboundedLocalCache)
              (r2 I:java.lang.Object)
              (r3 I:java.lang.Object)
              (r4 I:com.github.benmanes.caffeine.cache.RemovalCause)
             type: DIRECT call: com.github.benmanes.caffeine.cache.UnboundedLocalCache.lambda$notifyRemoval$0(java.lang.Object, java.lang.Object, com.github.benmanes.caffeine.cache.RemovalCause):void)
             type: INTERFACE call: java.util.concurrent.Executor.execute(java.lang.Runnable):void in method: com.github.benmanes.caffeine.cache.UnboundedLocalCache.notifyRemoval(K, V, com.github.benmanes.caffeine.cache.RemovalCause):void, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache.class
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            Caused by: java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
            	at java.base/java.util.Objects.checkIndex(Unknown Source)
            	at java.base/java.util.ArrayList.get(Unknown Source)
            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
            	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            r0 = r6
            com.github.benmanes.caffeine.cache.RemovalListener r0 = r0.removalListener()
            java.lang.String r1 = "Notification should be guarded with a check"
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0, r1)
            r0 = r6
            java.util.concurrent.Executor r0 = r0.executor
            r1 = r6
            r2 = r7
            r3 = r8
            r4 = r9
            void r1 = () -> { // java.lang.Runnable.run():void
                r1.lambda$notifyRemoval$0(r2, r3, r4);
            }
            r0.execute(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.UnboundedLocalCache.notifyRemoval(java.lang.Object, java.lang.Object, com.github.benmanes.caffeine.cache.RemovalCause):void");
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public boolean isRecordingStats() {
        return this.isRecordingStats;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Executor executor() {
        return this.executor;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Ticker expirationTicker() {
        return Ticker.disabledTicker();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Ticker statsTicker() {
        return this.ticker;
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.data.forEach(action);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[1];
        this.data.replaceAll(key, value -> {
            if (notificationKey[0] != null) {
                notifyRemoval(notificationKey[0], objArr[0], RemovalCause.REPLACED);
                objArr[0] = null;
                notificationKey[0] = null;
            }
            Object requireNonNull = Objects.requireNonNull(objArr2.apply(function, value));
            if (requireNonNull != value) {
                this.writer.write(function, requireNonNull);
            }
            if (hasRemovalListener() && requireNonNull != value) {
                notificationKey[0] = function;
                objArr[0] = value;
            }
            return requireNonNull;
        });
        if (objArr[0] != null) {
            notifyRemoval(objArr[0], objArr2[0], RemovalCause.REPLACED);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction, boolean recordStats, boolean recordLoad) {
        Objects.requireNonNull(mappingFunction);
        V value = this.data.get(key);
        if (value != null) {
            if (recordStats) {
                this.statsCounter.recordHits(1);
            }
            return value;
        }
        boolean[] missed = new boolean[1];
        V value2 = this.data.computeIfAbsent(key, k -> {
            missed[0] = true;
            if (missed) {
                return statsAware(recordStats, mappingFunction).apply(recordLoad);
            }
            return recordStats.apply(recordLoad);
        });
        if (!missed[0] && recordStats) {
            this.statsCounter.recordHits(1);
        }
        return value2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        if (!this.data.containsKey(key)) {
            return null;
        }
        Object[] objArr = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        V nv = this.data.computeIfPresent(key, k, value -> {
            Object apply = statsAware(remappingFunction, false, true, true).apply(objArr, value);
            remappingFunction[0] = apply == null ? RemovalCause.EXPLICIT : RemovalCause.REPLACED;
            if (hasRemovalListener() && apply != value) {
                cause[0] = value;
            }
            return apply;
        });
        if (objArr[0] != null) {
            notifyRemoval(key, objArr[0], cause[0]);
        }
        return nv;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, boolean recordMiss, boolean recordLoad, boolean recordLoadFailure) {
        Objects.requireNonNull(remappingFunction);
        return remap(key, statsAware(remappingFunction, recordMiss, recordLoad, recordLoadFailure));
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        return remap(key, k, oldValue -> {
            return oldValue == null ? value : statsAware(value).apply(oldValue, value);
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    V remap(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Object[] objArr = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        V nv = this.data.compute(key, k, value -> {
            Object apply = remappingFunction.apply(objArr, value);
            if (value == null && apply == null) {
                return null;
            }
            remappingFunction[0] = apply == null ? RemovalCause.EXPLICIT : RemovalCause.REPLACED;
            if (hasRemovalListener() && value != null && apply != value) {
                cause[0] = value;
            }
            return apply;
        });
        if (objArr[0] != null) {
            notifyRemoval(key, objArr[0], cause[0]);
        }
        return nv;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override // java.util.Map
    public int size() {
        return this.data.size();
    }

    @Override // java.util.Map
    public void clear() {
        if (!hasRemovalListener() && this.writer == CacheWriter.disabledWriter()) {
            this.data.clear();
            return;
        }
        Iterator<K> it = this.data.keySet().iterator();
        while (it.hasNext()) {
            K key = it.next();
            remove(key);
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object key) {
        return this.data.containsKey(key);
    }

    @Override // java.util.Map
    public boolean containsValue(Object value) {
        return this.data.containsValue(value);
    }

    @Override // java.util.Map
    public V get(Object key) {
        return getIfPresent(key, false);
    }

    @Override // java.util.Map
    public V put(K key, V value) {
        return put(key, value, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V put(K key, V value, boolean notifyWriter) {
        Objects.requireNonNull(value);
        Object[] objArr = new Object[1];
        if (this.writer == CacheWriter.disabledWriter() || !notifyWriter) {
            objArr[0] = this.data.put(key, value);
        } else {
            this.data.compute(key, k, v -> {
                if (value != v) {
                    this.writer.write(value, value);
                }
                key[0] = v;
                return value;
            });
        }
        if (hasRemovalListener() && objArr[0] != null && objArr[0] != value) {
            notifyRemoval(key, objArr[0], RemovalCause.REPLACED);
        }
        return (V) objArr[0];
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V putIfAbsent(K key, V value) {
        Objects.requireNonNull(value);
        boolean[] wasAbsent = new boolean[1];
        V val = this.data.computeIfAbsent(key, k -> {
            this.writer.write(key, key);
            value[0] = true;
            return key;
        });
        if (wasAbsent[0]) {
            return null;
        }
        return val;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        if (!hasRemovalListener() && this.writer == CacheWriter.disabledWriter()) {
            this.data.putAll(map);
        } else {
            map.forEach(this::put);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public V remove(Object key) {
        Object[] objArr = new Object[1];
        if (this.writer != CacheWriter.disabledWriter()) {
            this.data.computeIfPresent(key, k, v -> {
                this.writer.delete(castKey, v, RemovalCause.EXPLICIT);
                key[0] = v;
                return null;
            });
        } else {
            objArr[0] = this.data.remove(key);
        }
        if (hasRemovalListener() && objArr[0] != null) {
            notifyRemoval(key, objArr[0], RemovalCause.EXPLICIT);
        }
        return (V) objArr[0];
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object key, Object value) {
        if (value == null) {
            Objects.requireNonNull(key);
            return false;
        }
        Object[] objArr = new Object[1];
        this.data.computeIfPresent(key, k, v -> {
            if (v.equals(value)) {
                this.writer.delete(value, v, RemovalCause.EXPLICIT);
                key[0] = v;
                return null;
            }
            return v;
        });
        boolean removed = objArr[0] != null;
        if (hasRemovalListener() && removed) {
            notifyRemoval(key, objArr[0], RemovalCause.EXPLICIT);
        }
        return removed;
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V replace(K key, V value) {
        Objects.requireNonNull(value);
        Object[] objArr = new Object[1];
        this.data.computeIfPresent(key, k, v -> {
            if (value != v) {
                this.writer.write(value, value);
            }
            key[0] = v;
            return value;
        });
        if (hasRemovalListener() && objArr[0] != null && objArr[0] != value) {
            notifyRemoval(key, value, RemovalCause.REPLACED);
        }
        return (V) objArr[0];
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K key, V oldValue, V newValue) {
        Objects.requireNonNull(oldValue);
        Objects.requireNonNull(newValue);
        Object[] objArr = new Object[1];
        this.data.computeIfPresent(key, k, v -> {
            if (v.equals(oldValue)) {
                if (oldValue != v) {
                    this.writer.write(newValue, oldValue);
                }
                key[0] = v;
                return oldValue;
            }
            return v;
        });
        boolean replaced = objArr[0] != null;
        if (hasRemovalListener() && replaced && objArr[0] != newValue) {
            notifyRemoval(key, objArr[0], RemovalCause.REPLACED);
        }
        return replaced;
    }

    @Override // java.util.Map
    public boolean equals(Object o) {
        return this.data.equals(o);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.data.hashCode();
    }

    public String toString() {
        return this.data.toString();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks == null) {
            KeySetView keySetView = new KeySetView(this);
            this.keySet = keySetView;
            return keySetView;
        }
        return ks;
    }

    @Override // java.util.Map
    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs == null) {
            ValuesView valuesView = new ValuesView(this);
            this.values = valuesView;
            return valuesView;
        }
        return vs;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es = this.entrySet;
        if (es == null) {
            EntrySetView entrySetView = new EntrySetView(this);
            this.entrySet = entrySetView;
            return entrySetView;
        }
        return es;
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$KeySetView.class */
    static final class KeySetView<K> extends AbstractSet<K> {
        final UnboundedLocalCache<K, ?> cache;

        KeySetView(UnboundedLocalCache<K, ?> cache) {
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return this.cache.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.cache.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.cache.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            return this.cache.containsKey(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return this.cache.remove(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new KeyIterator(this.cache);
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set
        public Spliterator<K> spliterator() {
            return this.cache.data.keySet().spliterator();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$KeyIterator.class */
    static final class KeyIterator<K> implements Iterator<K> {
        final UnboundedLocalCache<K, ?> cache;
        final Iterator<K> iterator;
        K current;

        KeyIterator(UnboundedLocalCache<K, ?> cache) {
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
            this.iterator = cache.data.keySet().iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public K next() {
            this.current = this.iterator.next();
            return this.current;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            this.cache.remove(this.current);
            this.current = null;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$ValuesView.class */
    static final class ValuesView<K, V> extends AbstractCollection<V> {
        final UnboundedLocalCache<K, V> cache;

        ValuesView(UnboundedLocalCache<K, V> cache) {
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.cache.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.cache.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.cache.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object o) {
            return this.cache.containsValue(o);
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super V> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Map.Entry<K, V> entry : this.cache.data.entrySet()) {
                if (filter.test((V) entry.getValue())) {
                    removed |= this.cache.remove(entry.getKey(), entry.getValue());
                }
            }
            return removed;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValuesIterator(this.cache);
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Spliterator<V> spliterator() {
            return this.cache.data.values().spliterator();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$ValuesIterator.class */
    static final class ValuesIterator<K, V> implements Iterator<V> {
        final UnboundedLocalCache<K, V> cache;
        final Iterator<Map.Entry<K, V>> iterator;
        Map.Entry<K, V> entry;

        ValuesIterator(UnboundedLocalCache<K, V> cache) {
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
            this.iterator = cache.data.entrySet().iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public V next() {
            this.entry = this.iterator.next();
            return this.entry.getValue();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.entry == null) {
                throw new IllegalStateException();
            }
            this.cache.remove(this.entry.getKey());
            this.entry = null;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$EntrySetView.class */
    static final class EntrySetView<K, V> extends AbstractSet<Map.Entry<K, V>> {
        final UnboundedLocalCache<K, V> cache;

        EntrySetView(UnboundedLocalCache<K, V> cache) {
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return this.cache.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.cache.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.cache.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            V value = this.cache.get(entry.getKey());
            return value != null && value.equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) obj;
            return this.cache.remove(entry.getKey(), entry.getValue());
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super Map.Entry<K, V>> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Map.Entry<K, V> entry : this.cache.data.entrySet()) {
                if (filter.test(entry)) {
                    removed |= this.cache.remove(entry.getKey(), entry.getValue());
                }
            }
            return removed;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(this.cache);
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator(this.cache);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$EntryIterator.class */
    static final class EntryIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        final UnboundedLocalCache<K, V> cache;
        final Iterator<Map.Entry<K, V>> iterator;
        Map.Entry<K, V> entry;

        EntryIterator(UnboundedLocalCache<K, V> cache) {
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
            this.iterator = cache.data.entrySet().iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            this.entry = this.iterator.next();
            return new WriteThroughEntry(this.cache, this.entry.getKey(), this.entry.getValue());
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.entry == null) {
                throw new IllegalStateException();
            }
            this.cache.remove(this.entry.getKey());
            this.entry = null;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$EntrySpliterator.class */
    static final class EntrySpliterator<K, V> implements Spliterator<Map.Entry<K, V>> {
        final Spliterator<Map.Entry<K, V>> spliterator;
        final UnboundedLocalCache<K, V> cache;

        EntrySpliterator(UnboundedLocalCache<K, V> cache) {
            this(cache, cache.data.entrySet().spliterator());
        }

        EntrySpliterator(UnboundedLocalCache<K, V> cache, Spliterator<Map.Entry<K, V>> spliterator) {
            this.spliterator = (Spliterator) Objects.requireNonNull(spliterator);
            this.cache = (UnboundedLocalCache) Objects.requireNonNull(cache);
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
            Objects.requireNonNull(action);
            this.spliterator.forEachRemaining(entry -> {
                action.accept(new WriteThroughEntry(this.cache, action.getKey(), action.getValue()));
            });
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> action) {
            Objects.requireNonNull(action);
            return this.spliterator.tryAdvance(entry -> {
                action.accept(new WriteThroughEntry(this.cache, action.getKey(), action.getValue()));
            });
        }

        @Override // java.util.Spliterator
        public EntrySpliterator<K, V> trySplit() {
            Spliterator<Map.Entry<K, V>> split = this.spliterator.trySplit();
            if (split == null) {
                return null;
            }
            return new EntrySpliterator<>(this.cache, split);
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.spliterator.estimateSize();
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.spliterator.characteristics();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$UnboundedLocalManualCache.class */
    public static class UnboundedLocalManualCache<K, V> implements LocalManualCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final UnboundedLocalCache<K, V> cache;
        Policy<K, V> policy;

        public UnboundedLocalManualCache(Caffeine<K, V> builder) {
            this.cache = new UnboundedLocalCache<>(builder, false);
        }

        @Override // com.github.benmanes.caffeine.cache.LocalManualCache
        public UnboundedLocalCache<K, V> cache() {
            return this.cache;
        }

        @Override // com.github.benmanes.caffeine.cache.Cache
        public Policy<K, V> policy() {
            if (this.policy == null) {
                UnboundedPolicy unboundedPolicy = new UnboundedPolicy(this.cache, Function.identity());
                this.policy = unboundedPolicy;
                return unboundedPolicy;
            }
            return this.policy;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        Object writeReplace() {
            SerializationProxy<K, V> proxy = new SerializationProxy<>();
            proxy.isRecordingStats = this.cache.isRecordingStats;
            proxy.removalListener = (RemovalListener<K, V>) this.cache.removalListener;
            proxy.ticker = this.cache.ticker;
            proxy.writer = (CacheWriter<K, V>) this.cache.writer;
            return proxy;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$UnboundedPolicy.class */
    static final class UnboundedPolicy<K, V> implements Policy<K, V> {
        final UnboundedLocalCache<K, V> cache;
        final Function<V, V> transformer;

        UnboundedPolicy(UnboundedLocalCache<K, V> cache, Function<V, V> transformer) {
            this.transformer = transformer;
            this.cache = cache;
        }

        @Override // com.github.benmanes.caffeine.cache.Policy
        public boolean isRecordingStats() {
            return this.cache.isRecordingStats;
        }

        @Override // com.github.benmanes.caffeine.cache.Policy
        public V getIfPresentQuietly(Object key) {
            return this.transformer.apply(this.cache.data.get(key));
        }

        @Override // com.github.benmanes.caffeine.cache.Policy
        public Optional<Policy.Eviction<K, V>> eviction() {
            return Optional.empty();
        }

        @Override // com.github.benmanes.caffeine.cache.Policy
        public Optional<Policy.Expiration<K, V>> expireAfterAccess() {
            return Optional.empty();
        }

        @Override // com.github.benmanes.caffeine.cache.Policy
        public Optional<Policy.Expiration<K, V>> expireAfterWrite() {
            return Optional.empty();
        }

        @Override // com.github.benmanes.caffeine.cache.Policy
        public Optional<Policy.Expiration<K, V>> refreshAfterWrite() {
            return Optional.empty();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$UnboundedLocalLoadingCache.class */
    public static final class UnboundedLocalLoadingCache<K, V> extends UnboundedLocalManualCache<K, V> implements LocalLoadingCache<K, V> {
        private static final long serialVersionUID = 1;
        final Function<K, V> mappingFunction;
        final CacheLoader<? super K, V> loader;
        final Function<Iterable<? extends K>, Map<K, V>> bulkMappingFunction;

        public UnboundedLocalLoadingCache(Caffeine<K, V> builder, CacheLoader<? super K, V> loader) {
            super(builder);
            this.loader = loader;
            this.mappingFunction = LocalLoadingCache.newMappingFunction(loader);
            this.bulkMappingFunction = LocalLoadingCache.newBulkMappingFunction(loader);
        }

        @Override // com.github.benmanes.caffeine.cache.LocalLoadingCache
        public CacheLoader<? super K, V> cacheLoader() {
            return this.loader;
        }

        @Override // com.github.benmanes.caffeine.cache.LocalLoadingCache
        public Function<K, V> mappingFunction() {
            return this.mappingFunction;
        }

        @Override // com.github.benmanes.caffeine.cache.LocalLoadingCache
        public Function<Iterable<? extends K>, Map<K, V>> bulkMappingFunction() {
            return this.bulkMappingFunction;
        }

        @Override // com.github.benmanes.caffeine.cache.UnboundedLocalCache.UnboundedLocalManualCache
        Object writeReplace() {
            SerializationProxy<K, V> proxy = (SerializationProxy) super.writeReplace();
            proxy.loader = this.loader;
            return proxy;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$UnboundedLocalAsyncCache.class */
    public static final class UnboundedLocalAsyncCache<K, V> implements LocalAsyncCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final UnboundedLocalCache<K, CompletableFuture<V>> cache;
        ConcurrentMap<K, CompletableFuture<V>> mapView;
        LocalAsyncCache.CacheView<K, V> cacheView;
        Policy<K, V> policy;

        public UnboundedLocalAsyncCache(Caffeine<K, V> builder) {
            this.cache = new UnboundedLocalCache<>(builder, true);
        }

        @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
        public UnboundedLocalCache<K, CompletableFuture<V>> cache() {
            return this.cache;
        }

        @Override // com.github.benmanes.caffeine.cache.AsyncCache
        public ConcurrentMap<K, CompletableFuture<V>> asMap() {
            if (this.mapView == null) {
                LocalAsyncCache.AsyncAsMapView asyncAsMapView = new LocalAsyncCache.AsyncAsMapView(this);
                this.mapView = asyncAsMapView;
                return asyncAsMapView;
            }
            return this.mapView;
        }

        @Override // com.github.benmanes.caffeine.cache.AsyncCache
        public Cache<K, V> synchronous() {
            if (this.cacheView == null) {
                LocalAsyncCache.CacheView<K, V> cacheView = new LocalAsyncCache.CacheView<>(this);
                this.cacheView = cacheView;
                return cacheView;
            }
            return this.cacheView;
        }

        @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
        public Policy<K, V> policy() {
            UnboundedLocalCache<K, CompletableFuture<V>> unboundedLocalCache = this.cache;
            Function<CompletableFuture<V>, V> transformer = Async::getIfReady;
            if (this.policy == null) {
                UnboundedPolicy unboundedPolicy = new UnboundedPolicy(unboundedLocalCache, transformer);
                this.policy = unboundedPolicy;
                return unboundedPolicy;
            }
            return this.policy;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        Object writeReplace() {
            SerializationProxy<K, V> proxy = new SerializationProxy<>();
            proxy.isRecordingStats = this.cache.isRecordingStats;
            proxy.removalListener = (RemovalListener<K, CompletableFuture<V>>) this.cache.removalListener;
            proxy.ticker = this.cache.ticker;
            proxy.writer = (CacheWriter<K, CompletableFuture<V>>) this.cache.writer;
            proxy.async = true;
            return proxy;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/UnboundedLocalCache$UnboundedLocalAsyncLoadingCache.class */
    public static final class UnboundedLocalAsyncLoadingCache<K, V> extends LocalAsyncLoadingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final UnboundedLocalCache<K, CompletableFuture<V>> cache;
        ConcurrentMap<K, CompletableFuture<V>> mapView;
        Policy<K, V> policy;

        public UnboundedLocalAsyncLoadingCache(Caffeine<K, V> builder, AsyncCacheLoader<? super K, V> loader) {
            super(loader);
            this.cache = new UnboundedLocalCache<>(builder, true);
        }

        @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
        public LocalCache<K, CompletableFuture<V>> cache() {
            return this.cache;
        }

        @Override // com.github.benmanes.caffeine.cache.AsyncCache
        public ConcurrentMap<K, CompletableFuture<V>> asMap() {
            if (this.mapView == null) {
                LocalAsyncCache.AsyncAsMapView asyncAsMapView = new LocalAsyncCache.AsyncAsMapView(this);
                this.mapView = asyncAsMapView;
                return asyncAsMapView;
            }
            return this.mapView;
        }

        @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
        public Policy<K, V> policy() {
            UnboundedLocalCache<K, CompletableFuture<V>> unboundedLocalCache = this.cache;
            Function<CompletableFuture<V>, V> transformer = Async::getIfReady;
            if (this.policy == null) {
                UnboundedPolicy unboundedPolicy = new UnboundedPolicy(unboundedLocalCache, transformer);
                this.policy = unboundedPolicy;
                return unboundedPolicy;
            }
            return this.policy;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        Object writeReplace() {
            SerializationProxy<K, V> proxy = new SerializationProxy<>();
            proxy.isRecordingStats = this.cache.isRecordingStats();
            proxy.removalListener = (RemovalListener<K, CompletableFuture<V>>) this.cache.removalListener();
            proxy.ticker = this.cache.ticker;
            proxy.writer = (CacheWriter<K, CompletableFuture<V>>) this.cache.writer;
            proxy.loader = (AsyncCacheLoader<K, V>) this.loader;
            proxy.async = true;
            return proxy;
        }
    }
}
