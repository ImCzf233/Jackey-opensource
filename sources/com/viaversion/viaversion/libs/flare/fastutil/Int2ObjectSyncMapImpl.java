package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapImpl.class */
public final class Int2ObjectSyncMapImpl<V> extends AbstractInt2ObjectMap<V> implements Int2ObjectSyncMap<V> {
    private static final long serialVersionUID = 1;
    private final transient Object lock = new Object();
    private volatile transient Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>> read;
    private volatile transient boolean amended;
    private transient Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>> dirty;
    private transient int misses;
    private final transient IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> function;
    private transient Int2ObjectSyncMapImpl<V>.EntrySetView entrySet;

    public Int2ObjectSyncMapImpl(final IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> function, final int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
        this.function = function;
        this.read = function.apply(initialCapacity);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        promote();
        int size = 0;
        ObjectIterator<Int2ObjectSyncMap.ExpungingEntry<V>> it = this.read.values().iterator();
        while (it.hasNext()) {
            Int2ObjectSyncMap.ExpungingEntry<V> value = it.next();
            if (value.exists()) {
                size++;
            }
        }
        return size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean isEmpty() {
        promote();
        ObjectIterator<Int2ObjectSyncMap.ExpungingEntry<V>> it = this.read.values().iterator();
        while (it.hasNext()) {
            Int2ObjectSyncMap.ExpungingEntry<V> value = it.next();
            if (value.exists()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean containsValue(final Object value) {
        ObjectIterator<Int2ObjectMap.Entry<V>> it = int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<V> entry = it.next();
            if (Objects.equals(entry.getValue(), value)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean containsKey(final int key) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
        return entry != null && entry.exists();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V get(final int key) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
        if (entry != null) {
            return entry.get();
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V getOrDefault(final int key, final V defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
        return entry != null ? entry.getOr(defaultValue) : defaultValue;
    }

    public Int2ObjectSyncMap.ExpungingEntry<V> getEntry(final int key) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.read.get(key);
                entry = expungingEntry;
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    entry = this.dirty.get(key);
                    missLocked();
                }
            }
        }
        return entry;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V computeIfAbsent(final int key, final IntFunction<? extends V> mappingFunction) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        Int2ObjectSyncMap.InsertionResult<V> result = entry2 != null ? entry2.computeIfAbsent(key, mappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndCompute(key, mappingFunction)) {
                        if (entry3.exists()) {
                            this.dirty.put(key, (int) entry3);
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
                        this.dirty.put(key, (int) new ExpungingEntryImpl(computed));
                    }
                    return computed;
                }
            }
        }
        return result.current();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V computeIfAbsent(final int key, final Int2ObjectFunction<? extends V> mappingFunction) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        Int2ObjectSyncMap.InsertionResult<V> result = entry2 != null ? entry2.computeIfAbsentPrimitive(key, mappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndComputePrimitive(key, mappingFunction)) {
                        if (entry3.exists()) {
                            this.dirty.put(key, (int) entry3);
                        }
                        return entry3.get();
                    }
                    result = entry3.computeIfAbsentPrimitive(key, mappingFunction);
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.computeIfAbsentPrimitive(key, mappingFunction);
                    if (result.current() == null) {
                        this.dirty.remove(key);
                    }
                    missLocked();
                } else {
                    if (!this.amended) {
                        dirtyLocked();
                        this.amended = true;
                    }
                    V computed = mappingFunction.get(key);
                    if (computed != null) {
                        this.dirty.put(key, (int) new ExpungingEntryImpl(computed));
                    }
                    return computed;
                }
            }
        }
        if (result != null) {
            return result.current();
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V computeIfPresent(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        Int2ObjectSyncMap.InsertionResult<V> result = entry2 != null ? entry2.computeIfPresent(key, remappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V compute(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        Int2ObjectSyncMap.InsertionResult<V> result = entry2 != null ? entry2.compute(key, remappingFunction) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndCompute(key, remappingFunction)) {
                        if (entry3.exists()) {
                            this.dirty.put(key, (int) entry3);
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
                    V computed = remappingFunction.apply(Integer.valueOf(key), null);
                    if (computed != null) {
                        this.dirty.put(key, (int) new ExpungingEntryImpl(computed));
                    }
                    return computed;
                }
            }
        }
        return result.current();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V putIfAbsent(final int key, final V value) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(value, "value");
        Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        Int2ObjectSyncMap.InsertionResult<V> result = entry2 != null ? entry2.setIfAbsent(value) : null;
        if (result == null || result.operation() == 2) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    if (entry3.tryUnexpungeAndSet(value)) {
                        this.dirty.put(key, (int) entry3);
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
                    this.dirty.put(key, (int) new ExpungingEntryImpl(value));
                }
            }
        }
        if (result != null) {
            return result.previous();
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V put(final int key, final V value) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry;
        Objects.requireNonNull(value, "value");
        Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.read.get(key);
        V previous = entry2 != null ? entry2.get() : null;
        if (entry2 == null || !entry2.trySet(value)) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> entry3 = this.read.get(key);
                if (entry3 != null) {
                    previous = entry3.get();
                    if (entry3.tryUnexpungeAndSet(value)) {
                        this.dirty.put(key, (int) entry3);
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
                    this.dirty.put(key, (int) new ExpungingEntryImpl(value));
                }
            }
            return previous;
        }
        return previous;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V remove(final int key) {
        Int2ObjectSyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.read.get(key);
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean remove(final int key, final Object value) {
        Objects.requireNonNull(value, "value");
        Int2ObjectSyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            synchronized (this.lock) {
                Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.read.get(key);
                entry = expungingEntry;
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    Int2ObjectSyncMap.ExpungingEntry<V> entry2 = this.dirty.get(key);
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V replace(final int key, final V value) {
        Objects.requireNonNull(value, "value");
        Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
        if (entry != null) {
            return entry.tryReplace(value);
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean replace(final int key, final V oldValue, final V newValue) {
        Objects.requireNonNull(oldValue, "oldValue");
        Objects.requireNonNull(newValue, "newValue");
        Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
        return entry != null && entry.replace(oldValue, newValue);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public void forEach(final BiConsumer<? super Integer, ? super V> action) {
        Objects.requireNonNull(action, "action");
        promote();
        ObjectIterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> it = this.read.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>> that = it.next();
            Object obj = (V) that.getValue().get();
            if (obj != null) {
                action.accept(Integer.valueOf(that.getIntKey()), obj);
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public void putAll(final Map<? extends Integer, ? extends V> map) {
        Objects.requireNonNull(map, "map");
        for (Map.Entry<? extends Integer, ? extends V> entry : map.entrySet()) {
            put(entry.getKey().intValue(), (int) entry.getValue());
        }
    }

    @Override // java.util.Map
    public void replaceAll(final BiFunction<? super Integer, ? super V, ? extends V> function) {
        Objects.requireNonNull(function, "function");
        promote();
        ObjectIterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> it = this.read.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>> that = it.next();
            Int2ObjectSyncMap.ExpungingEntry<V> entry = that.getValue();
            Object obj = (V) entry.get();
            if (obj != null) {
                entry.tryReplace(function.apply(Integer.valueOf(that.getIntKey()), obj));
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        synchronized (this.lock) {
            this.read = this.function.apply(this.read.size());
            this.dirty = null;
            this.amended = false;
            this.misses = 0;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
        if (this.entrySet != null) {
            return this.entrySet;
        }
        Int2ObjectSyncMapImpl<V>.EntrySetView entrySetView = new EntrySetView();
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
        Int2ObjectMaps.fastForEach(this.read, entry -> {
            if (!((Int2ObjectSyncMap.ExpungingEntry) entry.getValue()).tryExpunge()) {
                this.dirty.put(entry.getIntKey(), (int) ((Int2ObjectSyncMap.ExpungingEntry) entry.getValue()));
            }
        });
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapImpl$ExpungingEntryImpl.class */
    public static final class ExpungingEntryImpl<V> implements Int2ObjectSyncMap.ExpungingEntry<V> {
        private static final AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
        private static final Object EXPUNGED = new Object();
        private volatile Object value;

        ExpungingEntryImpl(final V value) {
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public boolean exists() {
            return (this.value == null || this.value == EXPUNGED) ? false : true;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public V get() {
            if (this.value == EXPUNGED) {
                return null;
            }
            return (V) this.value;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public V getOr(final V other) {
            Object value = this.value;
            return (value == null || value == EXPUNGED) ? other : (V) this.value;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public Int2ObjectSyncMap.InsertionResult<V> setIfAbsent(final V value) {
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public Int2ObjectSyncMap.InsertionResult<V> computeIfAbsent(final int key, final IntFunction<? extends V> function) {
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public Int2ObjectSyncMap.InsertionResult<V> computeIfAbsentPrimitive(final int key, final Int2ObjectFunction<? extends V> function) {
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
                    v = function.containsKey(key) ? function.get(key) : null;
                    next = v;
                }
            } while (!atomicReferenceFieldUpdater.compareAndSet(this, null, v));
            return new InsertionResultImpl((byte) 1, null, next);
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public Int2ObjectSyncMap.InsertionResult<V> computeIfPresent(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
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
                    v = remappingFunction.apply(Integer.valueOf(key), previous);
                    next = v;
                }
            } while (!atomicReferenceFieldUpdater.compareAndSet(this, previous, v));
            return new InsertionResultImpl((byte) 1, previous, next);
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public Int2ObjectSyncMap.InsertionResult<V> compute(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
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
                    v = remappingFunction.apply(Integer.valueOf(key), previous);
                    next = v;
                }
            } while (!atomicReferenceFieldUpdater.compareAndSet(this, previous, v));
            return new InsertionResultImpl(previous != next ? (byte) 1 : (byte) 0, previous, next);
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public void set(final V value) {
            UPDATER.set(this, value);
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public boolean tryExpunge() {
            while (this.value == null) {
                if (UPDATER.compareAndSet(this, null, EXPUNGED)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public boolean tryUnexpungeAndSet(final V value) {
            return UPDATER.compareAndSet(this, EXPUNGED, value);
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public boolean tryUnexpungeAndCompute(final int key, final IntFunction<? extends V> function) {
            if (this.value == EXPUNGED) {
                Object value = function.apply(key);
                return UPDATER.compareAndSet(this, EXPUNGED, value);
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public boolean tryUnexpungeAndComputePrimitive(final int key, final Int2ObjectFunction<? extends V> function) {
            if (this.value == EXPUNGED) {
                Object value = function.containsKey(key) ? function.get(key) : null;
                return UPDATER.compareAndSet(this, EXPUNGED, value);
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.ExpungingEntry
        public boolean tryUnexpungeAndCompute(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
            if (this.value == EXPUNGED) {
                Object value = remappingFunction.apply(Integer.valueOf(key), null);
                return UPDATER.compareAndSet(this, EXPUNGED, value);
            }
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapImpl$InsertionResultImpl.class */
    static final class InsertionResultImpl<V> implements Int2ObjectSyncMap.InsertionResult<V> {
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

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.InsertionResult
        public byte operation() {
            return this.operation;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.InsertionResult
        public V previous() {
            return this.previous;
        }

        @Override // com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap.InsertionResult
        public V current() {
            return this.current;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapImpl$MapEntry.class */
    public final class MapEntry implements Int2ObjectMap.Entry<V> {
        private final int key;
        private V value;

        MapEntry(final int key, final V value) {
            Int2ObjectSyncMapImpl.this = this$0;
            this.key = key;
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.Entry
        public int getIntKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(final V value) {
            Objects.requireNonNull(value, "value");
            Int2ObjectSyncMapImpl int2ObjectSyncMapImpl = Int2ObjectSyncMapImpl.this;
            int i = this.key;
            this.value = value;
            return (V) int2ObjectSyncMapImpl.put(i, (int) value);
        }

        public String toString() {
            return "Int2ObjectSyncMapImpl.MapEntry{key=" + getIntKey() + ", value=" + getValue() + "}";
        }

        @Override // java.util.Map.Entry
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Int2ObjectMap.Entry)) {
                return false;
            }
            Int2ObjectMap.Entry<?> that = (Int2ObjectMap.Entry) other;
            return Objects.equals(Integer.valueOf(getIntKey()), Integer.valueOf(that.getIntKey())) && Objects.equals(getValue(), that.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Objects.hash(Integer.valueOf(getIntKey()), getValue());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapImpl$EntrySetView.class */
    public final class EntrySetView extends AbstractObjectSet<Int2ObjectMap.Entry<V>> {
        EntrySetView() {
            Int2ObjectSyncMapImpl.this = this$0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectSyncMapImpl.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(final Object entry) {
            if (!(entry instanceof Int2ObjectMap.Entry)) {
                return false;
            }
            Int2ObjectMap.Entry<?> mapEntry = (Int2ObjectMap.Entry) entry;
            Object obj = Int2ObjectSyncMapImpl.this.get(mapEntry.getIntKey());
            return obj != null && Objects.equals(obj, mapEntry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(final Object entry) {
            if (!(entry instanceof Int2ObjectMap.Entry)) {
                return false;
            }
            Int2ObjectMap.Entry<?> mapEntry = (Int2ObjectMap.Entry) entry;
            return Int2ObjectSyncMapImpl.this.remove(mapEntry.getIntKey()) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectSyncMapImpl.this.clear();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
            Int2ObjectSyncMapImpl.this.promote();
            return new EntryIterator(Int2ObjectSyncMapImpl.this.read.int2ObjectEntrySet().iterator());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapImpl$EntryIterator.class */
    public final class EntryIterator implements ObjectIterator<Int2ObjectMap.Entry<V>> {
        private final Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> backingIterator;
        private Int2ObjectMap.Entry<V> next = nextValue();
        private Int2ObjectMap.Entry<V> current;

        EntryIterator(final Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> backingIterator) {
            Int2ObjectSyncMapImpl.this = this$0;
            this.backingIterator = backingIterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public Int2ObjectMap.Entry<V> next() {
            Int2ObjectMap.Entry<V> entry = this.next;
            this.current = entry;
            if (entry == null) {
                throw new NoSuchElementException();
            }
            this.next = nextValue();
            return this.current;
        }

        private Int2ObjectMap.Entry<V> nextValue() {
            while (this.backingIterator.hasNext()) {
                Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>> entry = this.backingIterator.next();
                V value = entry.getValue().get();
                if (value != null) {
                    return new MapEntry(entry.getIntKey(), value);
                }
            }
            return null;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            Int2ObjectSyncMapImpl.this.remove(this.current.getIntKey());
            this.current = null;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(final Consumer<? super Int2ObjectMap.Entry<V>> action) {
            Objects.requireNonNull(action, "action");
            if (this.next != null) {
                action.accept(this.next);
            }
            this.backingIterator.forEachRemaining(entry -> {
                Object obj = ((Int2ObjectSyncMap.ExpungingEntry) action.getValue()).get();
                if (obj != null) {
                    action.accept(new MapEntry(action.getIntKey(), obj));
                }
            });
        }
    }
}
