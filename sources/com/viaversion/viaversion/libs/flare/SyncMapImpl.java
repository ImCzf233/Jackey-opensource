package com.viaversion.viaversion.libs.flare;

import com.viaversion.viaversion.libs.flare.SyncMap;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMapImpl.class */
public final class SyncMapImpl<K, V> extends AbstractMap<K, V> implements SyncMap<K, V> {
    private final transient Object lock = new Object();
    private volatile transient Map<K, SyncMap.ExpungingEntry<V>> read;
    private volatile transient boolean amended;
    private transient Map<K, SyncMap.ExpungingEntry<V>> dirty;
    private transient int misses;
    private final transient IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function;
    private transient SyncMapImpl<K, V>.EntrySetView entrySet;

    public SyncMapImpl(final IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function, final int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
        this.function = function;
        this.read = function.apply(initialCapacity);
    }

    @Override // java.util.AbstractMap, java.util.Map, com.viaversion.viaversion.libs.flare.SyncMap
    public int size() {
        promote();
        int size = 0;
        for (SyncMap.ExpungingEntry<V> value : this.read.values()) {
            if (value.exists()) {
                size++;
            }
        }
        return size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        promote();
        for (SyncMap.ExpungingEntry<V> value : this.read.values()) {
            if (value.exists()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(final Object key) {
        SyncMap.ExpungingEntry<V> entry = getEntry(key);
        return entry != null && entry.exists();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(final Object key) {
        SyncMap.ExpungingEntry<V> entry = getEntry(key);
        if (entry != null) {
            return entry.get();
        }
        return null;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V getOrDefault(final Object key, final V defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        SyncMap.ExpungingEntry<V> entry = getEntry(key);
        return entry != null ? entry.getOr(defaultValue) : defaultValue;
    }

    private SyncMap.ExpungingEntry<V> getEntry(final Object key) {
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(key);
                entry = expungingEntry;
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    entry = this.dirty.get(key);
                    missLocked();
                }
            }
        }
        return entry;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V computeIfAbsent(final K key, final Function<? super K, ? extends V> mappingFunction) {
        SyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        SyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        SyncMap.InsertionResult<V> result = entry2 != null ? entry2.computeIfAbsent(key, mappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndCompute((SyncMap.ExpungingEntry<V>) key, (Function<? super SyncMap.ExpungingEntry<V>, ? extends V>) mappingFunction)) {
                        if (entry3.exists()) {
                            this.dirty.put(key, entry3);
                        }
                        return entry3.get();
                    }
                    result = entry3.computeIfAbsent(key, mappingFunction);
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.computeIfAbsent(key, mappingFunction);
                    if (result.current() == null) {
                        this.dirty.remove(key);
                    }
                    missLocked();
                } else {
                    if (!this.amended) {
                        dirtyLocked();
                        this.amended = true;
                    }
                    V computed = mappingFunction.apply(key);
                    if (computed != null) {
                        this.dirty.put(key, new ExpungingEntryImpl(computed));
                    }
                    return computed;
                }
            }
        }
        return result.current();
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V computeIfPresent(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        SyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        SyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        SyncMap.InsertionResult<V> result = entry2 != null ? entry2.computeIfPresent(key, remappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    result = entry3.computeIfPresent(key, remappingFunction);
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.computeIfPresent(key, remappingFunction);
                    if (result.current() == null) {
                        this.dirty.remove(key);
                    }
                    missLocked();
                }
            }
        }
        if (result != null) {
            return result.current();
        }
        return null;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V compute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        SyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        SyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        SyncMap.InsertionResult<V> result = entry2 != null ? entry2.compute(key, remappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndCompute((SyncMap.ExpungingEntry<V>) key, (BiFunction<? super SyncMap.ExpungingEntry<V>, ? super V, ? extends V>) remappingFunction)) {
                        if (entry3.exists()) {
                            this.dirty.put(key, entry3);
                        }
                        return entry3.get();
                    }
                    result = entry3.compute(key, remappingFunction);
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.compute(key, remappingFunction);
                    if (result.current() == null) {
                        this.dirty.remove(key);
                    }
                    missLocked();
                } else {
                    if (!this.amended) {
                        dirtyLocked();
                        this.amended = true;
                    }
                    V computed = remappingFunction.apply(key, null);
                    if (computed != null) {
                        this.dirty.put(key, new ExpungingEntryImpl(computed));
                    }
                    return computed;
                }
            }
        }
        return result.current();
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V putIfAbsent(final K key, final V value) {
        SyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        SyncMap.InsertionResult<V> result = entry2 != null ? entry2.setIfAbsent(value) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndSet(value)) {
                        this.dirty.put(key, entry3);
                    } else {
                        result = entry3.setIfAbsent(value);
                    }
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.setIfAbsent(value);
                    missLocked();
                } else {
                    if (!this.amended) {
                        dirtyLocked();
                        this.amended = true;
                    }
                    this.dirty.put(key, new ExpungingEntryImpl(value));
                }
            }
        }
        if (result != null) {
            return result.previous();
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(final K key, final V value) {
        SyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        V previous = entry2 != null ? entry2.get() : null;
        if (entry2 == null || !entry2.trySet(value)) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    previous = entry3.get();
                    if (entry3.tryUnexpungeAndSet(value)) {
                        this.dirty.put(key, entry3);
                    } else {
                        entry3.set(value);
                    }
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    previous = entry.get();
                    entry.set(value);
                    missLocked();
                } else {
                    if (!this.amended) {
                        dirtyLocked();
                        this.amended = true;
                    }
                    this.dirty.put(key, new ExpungingEntryImpl(value));
                }
            }
            return previous;
        }
        return previous;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(final Object key) {
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(key);
                entry = expungingEntry;
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    entry = this.dirty.remove(key);
                    missLocked();
                }
            }
        }
        if (entry != null) {
            return entry.clear();
        }
        return null;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(final Object key, final Object value) {
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            synchronized (this.lock) {
                SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(key);
                entry = expungingEntry;
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    SyncMap.ExpungingEntry<V> entry2 = this.dirty.get(key);
                    boolean present = entry2 != null && entry2.replace(value, null);
                    if (present) {
                        this.dirty.remove(key);
                    }
                    missLocked();
                    return present;
                }
            }
        }
        return entry != null && entry.replace(value, null);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V replace(final K key, final V value) {
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry = getEntry(key);
        if (entry != null) {
            return entry.tryReplace(value);
        }
        return null;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(final K key, final V oldValue, final V newValue) {
        Objects.requireNonNull(oldValue, "oldValue");
        Objects.requireNonNull(newValue, "newValue");
        SyncMap.ExpungingEntry<V> entry = getEntry(key);
        return entry != null && entry.replace(oldValue, newValue);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public void forEach(final BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action, "action");
        promote();
        for (Map.Entry<K, SyncMap.ExpungingEntry<V>> that : this.read.entrySet()) {
            Object obj = (V) that.getValue().get();
            if (obj != null) {
                action.accept((K) that.getKey(), obj);
            }
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function, "function");
        promote();
        for (Map.Entry<K, SyncMap.ExpungingEntry<V>> that : this.read.entrySet()) {
            SyncMap.ExpungingEntry<V> entry = that.getValue();
            Object obj = (V) entry.get();
            if (obj != null) {
                entry.tryReplace(function.apply((K) that.getKey(), obj));
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map, com.viaversion.viaversion.libs.flare.SyncMap
    public void clear() {
        synchronized (this.lock) {
            this.read = this.function.apply(this.read.size());
            this.dirty = null;
            this.amended = false;
            this.misses = 0;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map, com.viaversion.viaversion.libs.flare.SyncMap
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.entrySet != null) {
            return this.entrySet;
        }
        SyncMapImpl<K, V>.EntrySetView entrySetView = new EntrySetView();
        this.entrySet = entrySetView;
        return entrySetView;
    }

    public void promote() {
        if (this.amended) {
            synchronized (this.lock) {
                if (this.amended) {
                    promoteLocked();
                }
            }
        }
    }

    private void promoteLocked() {
        if (this.dirty != null) {
            this.read = this.dirty;
        }
        this.amended = false;
        this.dirty = null;
        this.misses = 0;
    }

    private void missLocked() {
        int i = this.misses + 1;
        this.misses = i;
        if (i >= this.dirty.size()) {
            promoteLocked();
        }
    }

    private void dirtyLocked() {
        if (this.dirty != null) {
            return;
        }
        this.dirty = this.function.apply(this.read.size());
        for (Map.Entry<K, SyncMap.ExpungingEntry<V>> entry : this.read.entrySet()) {
            if (!entry.getValue().tryExpunge()) {
                this.dirty.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMapImpl$ExpungingEntryImpl.class */
    public static final class ExpungingEntryImpl<V> implements SyncMap.ExpungingEntry<V> {
        private static final AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
        private static final Object EXPUNGED = new Object();
        private volatile Object value;

        ExpungingEntryImpl(final V value) {
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public boolean exists() {
            return (this.value == null || this.value == EXPUNGED) ? false : true;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public V get() {
            if (this.value == EXPUNGED) {
                return null;
            }
            return (V) this.value;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public V getOr(final V other) {
            Object value = this.value;
            return (value == null || value == EXPUNGED) ? other : (V) this.value;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public SyncMap.InsertionResult<V> setIfAbsent(final V value) {
            do {
                Object previous = this.value;
                if (previous == EXPUNGED) {
                    return new InsertionResultImpl((byte) 2, null, null);
                }
                if (previous != null) {
                    return new InsertionResultImpl((byte) 0, previous, previous);
                }
            } while (!UPDATER.compareAndSet(this, null, value));
            return new InsertionResultImpl((byte) 1, null, value);
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public <K> SyncMap.InsertionResult<V> computeIfAbsent(final K key, final Function<? super K, ? extends V> function) {
            AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> atomicReferenceFieldUpdater;
            V v;
            V next = null;
            do {
                Object previous = this.value;
                if (previous == EXPUNGED) {
                    return new InsertionResultImpl((byte) 2, null, null);
                }
                if (previous != null) {
                    return new InsertionResultImpl((byte) 0, previous, previous);
                }
                atomicReferenceFieldUpdater = UPDATER;
                if (next != null) {
                    v = next;
                } else {
                    v = function.apply(key);
                    next = v;
                }
            } while (!atomicReferenceFieldUpdater.compareAndSet(this, null, v));
            return new InsertionResultImpl((byte) 1, null, next);
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public <K> SyncMap.InsertionResult<V> computeIfPresent(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            Object previous;
            AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> atomicReferenceFieldUpdater;
            V v;
            V next = null;
            do {
                previous = this.value;
                if (previous == EXPUNGED) {
                    return new InsertionResultImpl((byte) 2, null, null);
                }
                if (previous == null) {
                    return new InsertionResultImpl((byte) 0, null, null);
                }
                atomicReferenceFieldUpdater = UPDATER;
                if (next != null) {
                    v = next;
                } else {
                    v = remappingFunction.apply(key, previous);
                    next = v;
                }
            } while (!atomicReferenceFieldUpdater.compareAndSet(this, previous, v));
            return new InsertionResultImpl((byte) 1, previous, next);
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public <K> SyncMap.InsertionResult<V> compute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            Object previous;
            AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> atomicReferenceFieldUpdater;
            V v;
            V next = null;
            do {
                previous = this.value;
                if (previous == EXPUNGED) {
                    return new InsertionResultImpl((byte) 2, null, null);
                }
                atomicReferenceFieldUpdater = UPDATER;
                if (next != null) {
                    v = next;
                } else {
                    v = remappingFunction.apply(key, previous);
                    next = v;
                }
            } while (!atomicReferenceFieldUpdater.compareAndSet(this, previous, v));
            return new InsertionResultImpl(previous != next ? (byte) 1 : (byte) 0, previous, next);
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public void set(final V value) {
            UPDATER.set(this, value);
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public boolean replace(final Object compare, final V value) {
            Object previous;
            do {
                previous = this.value;
                if (previous == EXPUNGED || !Objects.equals(previous, compare)) {
                    return false;
                }
            } while (!UPDATER.compareAndSet(this, previous, value));
            return true;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public V clear() {
            V v;
            do {
                v = (V) this.value;
                if (v == null || v == EXPUNGED) {
                    return null;
                }
            } while (!UPDATER.compareAndSet(this, v, null));
            return v;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public boolean trySet(final V value) {
            Object previous;
            do {
                previous = this.value;
                if (previous == EXPUNGED) {
                    return false;
                }
            } while (!UPDATER.compareAndSet(this, previous, value));
            return true;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public V tryReplace(final V value) {
            V v;
            do {
                v = (V) this.value;
                if (v == null || v == EXPUNGED) {
                    return null;
                }
            } while (!UPDATER.compareAndSet(this, v, value));
            return v;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public boolean tryExpunge() {
            while (this.value == null) {
                if (UPDATER.compareAndSet(this, null, EXPUNGED)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public boolean tryUnexpungeAndSet(final V value) {
            return UPDATER.compareAndSet(this, EXPUNGED, value);
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public <K> boolean tryUnexpungeAndCompute(final K key, final Function<? super K, ? extends V> function) {
            if (this.value == EXPUNGED) {
                Object value = function.apply(key);
                return UPDATER.compareAndSet(this, EXPUNGED, value);
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.ExpungingEntry
        public <K> boolean tryUnexpungeAndCompute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            if (this.value == EXPUNGED) {
                Object value = remappingFunction.apply(key, null);
                return UPDATER.compareAndSet(this, EXPUNGED, value);
            }
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMapImpl$InsertionResultImpl.class */
    static final class InsertionResultImpl<V> implements SyncMap.InsertionResult<V> {
        private static final byte UNCHANGED = 0;
        private static final byte UPDATED = 1;
        private static final byte EXPUNGED = 2;
        private final byte operation;
        private final V previous;
        private final V current;

        InsertionResultImpl(final byte operation, final V previous, final V current) {
            this.operation = operation;
            this.previous = previous;
            this.current = current;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.InsertionResult
        public byte operation() {
            return this.operation;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.InsertionResult
        public V previous() {
            return this.previous;
        }

        @Override // com.viaversion.viaversion.libs.flare.SyncMap.InsertionResult
        public V current() {
            return this.current;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMapImpl$MapEntry.class */
    public final class MapEntry implements Map.Entry<K, V> {
        private final K key;
        private V value;

        MapEntry(final K key, final V value) {
            SyncMapImpl.this = this$0;
            this.key = key;
            this.value = value;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(final V value) {
            Objects.requireNonNull(value, "value");
            SyncMapImpl syncMapImpl = SyncMapImpl.this;
            K k = this.key;
            this.value = value;
            return (V) syncMapImpl.put(k, value);
        }

        public String toString() {
            return "SyncMapImpl.MapEntry{key=" + getKey() + ", value=" + getValue() + "}";
        }

        @Override // java.util.Map.Entry
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> that = (Map.Entry) other;
            return Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Objects.hash(getKey(), getValue());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMapImpl$EntrySetView.class */
    final class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        EntrySetView() {
            SyncMapImpl.this = this$0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return SyncMapImpl.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(final Object entry) {
            if (!(entry instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> mapEntry = (Map.Entry) entry;
            Object obj = SyncMapImpl.this.get(mapEntry.getKey());
            return obj != null && Objects.equals(obj, mapEntry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(final Object entry) {
            if (!(entry instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> mapEntry = (Map.Entry) entry;
            return SyncMapImpl.this.remove(mapEntry.getKey()) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            SyncMapImpl.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            SyncMapImpl.this.promote();
            return new EntryIterator(SyncMapImpl.this.read.entrySet().iterator());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMapImpl$EntryIterator.class */
    final class EntryIterator implements Iterator<Map.Entry<K, V>> {
        private final Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator;
        private Map.Entry<K, V> next = nextValue();
        private Map.Entry<K, V> current;

        EntryIterator(final Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator) {
            SyncMapImpl.this = this$0;
            this.backingIterator = backingIterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            Map.Entry<K, V> entry = this.next;
            this.current = entry;
            if (entry == null) {
                throw new NoSuchElementException();
            }
            this.next = nextValue();
            return this.current;
        }

        private Map.Entry<K, V> nextValue() {
            while (this.backingIterator.hasNext()) {
                Map.Entry<K, SyncMap.ExpungingEntry<V>> entry = this.backingIterator.next();
                V value = entry.getValue().get();
                if (value != null) {
                    return new MapEntry(entry.getKey(), value);
                }
            }
            return null;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            SyncMapImpl.this.remove(this.current.getKey());
            this.current = null;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(final Consumer<? super Map.Entry<K, V>> action) {
            Objects.requireNonNull(action, "action");
            if (this.next != null) {
                action.accept(this.next);
            }
            this.backingIterator.forEachRemaining(entry -> {
                Object obj = ((SyncMap.ExpungingEntry) action.getValue()).get();
                if (obj != null) {
                    action.accept(new MapEntry(action.getKey(), obj));
                }
            });
        }
    }
}
