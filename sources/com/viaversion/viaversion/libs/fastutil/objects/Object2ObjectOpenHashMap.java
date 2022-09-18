package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap.class */
public class Object2ObjectOpenHashMap<K, V> extends AbstractObject2ObjectMap<K, V> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;

    /* renamed from: n */
    protected transient int f140n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f141f;
    protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
    protected transient ObjectSet<K> keys;
    protected transient ObjectCollection<V> values;

    public Object2ObjectOpenHashMap(int expected, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f141f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f140n = arraySize;
        this.minN = arraySize;
        this.mask = this.f140n - 1;
        this.maxFill = HashCommon.maxFill(this.f140n, f);
        this.key = (K[]) new Object[this.f140n + 1];
        this.value = (V[]) new Object[this.f140n + 1];
    }

    public Object2ObjectOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Object2ObjectOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> m) {
        this(m, 0.75f);
    }

    public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> m) {
        this((Object2ObjectMap) m, 0.75f);
    }

    public Object2ObjectOpenHashMap(K[] k, V[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put(k[i], v[i]);
        }
    }

    public Object2ObjectOpenHashMap(K[] k, V[] v) {
        this(k, v, 0.75f);
    }

    public int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f141f);
        if (needed > this.f140n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f141f))));
        if (needed > this.f140n) {
            rehash(needed);
        }
    }

    public V removeEntry(int pos) {
        V oldValue = this.value[pos];
        this.value[pos] = null;
        this.size--;
        shiftKeys(pos);
        if (this.f140n > this.minN && this.size < this.maxFill / 4 && this.f140n > 16) {
            rehash(this.f140n / 2);
        }
        return oldValue;
    }

    public V removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.f140n] = null;
        V oldValue = this.value[this.f140n];
        this.value[this.f140n] = null;
        this.size--;
        if (this.f140n > this.minN && this.size < this.maxFill / 4 && this.f140n > 16) {
            rehash(this.f140n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> m) {
        if (this.f141f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(K k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.f140n : -(this.f140n + 1);
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return -(pos + 1);
        }
        if (k.equals(curr2)) {
            return pos;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return -(pos + 1);
            }
        } while (!k.equals(curr));
        return pos;
    }

    private void insert(int pos, K k, V v) {
        if (pos == this.f140n) {
            this.containsNullKey = true;
        }
        this.key[pos] = k;
        this.value[pos] = v;
        int i = this.size;
        this.size = i + 1;
        if (i >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f141f));
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V put(K k, V v) {
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return this.defRetValue;
        }
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    protected final void shiftKeys(int pos) {
        K curr;
        K[] key = this.key;
        while (true) {
            int last = pos;
            int i = last + 1;
            int i2 = this.mask;
            while (true) {
                pos = i & i2;
                curr = key[pos];
                if (curr == null) {
                    key[last] = null;
                    this.value[last] = null;
                    return;
                }
                int slot = HashCommon.mix(curr.hashCode()) & this.mask;
                if (last > pos) {
                    if (last >= slot && slot > pos) {
                        break;
                    }
                    i = pos + 1;
                    i2 = this.mask;
                } else if (last < slot && slot <= pos) {
                    i = pos + 1;
                    i2 = this.mask;
                }
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V remove(Object k) {
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                return removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return this.defRetValue;
        }
        if (k.equals(curr2)) {
            return removeEntry(pos);
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return this.defRetValue;
            }
        } while (!k.equals(curr));
        return removeEntry(pos);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V get(Object k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f140n] : this.defRetValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return this.defRetValue;
        }
        if (k.equals(curr2)) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return this.defRetValue;
            }
        } while (!k.equals(curr));
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.Function
    public boolean containsKey(Object k) {
        K curr;
        if (k == null) {
            return this.containsNullKey;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return false;
        }
        if (k.equals(curr2)) {
            return true;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return false;
            }
        } while (!k.equals(curr));
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public boolean containsValue(Object v) {
        V[] value = this.value;
        K[] key = this.key;
        if (this.containsNullKey && Objects.equals(value[this.f140n], v)) {
            return true;
        }
        int i = this.f140n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                if (key[i] != null && Objects.equals(value[i], v)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V getOrDefault(Object k, V defaultValue) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f140n] : defaultValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return defaultValue;
        }
        if (k.equals(curr2)) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return defaultValue;
            }
        } while (!k.equals(curr));
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public V putIfAbsent(K k, V v) {
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        insert((-pos) - 1, k, v);
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public boolean remove(Object k, Object v) {
        if (k == null) {
            if (this.containsNullKey && Objects.equals(v, this.value[this.f140n])) {
                removeNullEntry();
                return true;
            }
            return false;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr = key[mix];
        if (curr == null) {
            return false;
        }
        if (k.equals(curr) && Objects.equals(v, this.value[pos])) {
            removeEntry(pos);
            return true;
        }
        while (true) {
            int i = (pos + 1) & this.mask;
            pos = i;
            K curr2 = key[i];
            if (curr2 == null) {
                return false;
            }
            if (k.equals(curr2) && Objects.equals(v, this.value[pos])) {
                removeEntry(pos);
                return true;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public boolean replace(K k, V oldValue, V v) {
        int pos = find(k);
        if (pos < 0 || !Objects.equals(oldValue, this.value[pos])) {
            return false;
        }
        this.value[pos] = v;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public V replace(K k, V v) {
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
    public V computeIfAbsent(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(key);
        if (pos >= 0) {
            return this.value[pos];
        }
        if (!mappingFunction.containsKey(key)) {
            return this.defRetValue;
        }
        V newValue = mappingFunction.get(key);
        insert((-pos) - 1, key, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        V newValue = remappingFunction.apply(k, (Object) this.value[pos]);
        if (newValue == null) {
            if (k == null) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        this.value[pos] = newValue;
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public V compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        V newValue = remappingFunction.apply(k, pos >= 0 ? (Object) this.value[pos] : (Object) null);
        if (newValue == null) {
            if (pos >= 0) {
                if (k == null) {
                    removeNullEntry();
                } else {
                    removeEntry(pos);
                }
            }
            return this.defRetValue;
        } else if (pos >= 0) {
            this.value[pos] = newValue;
            return newValue;
        } else {
            insert((-pos) - 1, k, newValue);
            return newValue;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0 || this.value[pos] == null) {
            if (v == null) {
                return this.defRetValue;
            }
            insert((-pos) - 1, k, v);
            return v;
        }
        V newValue = remappingFunction.apply((Object) this.value[pos], v);
        if (newValue == null) {
            if (k == null) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        this.value[pos] = newValue;
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, (Object) null);
        Arrays.fill(this.value, (Object) null);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$MapEntry.class */
    public final class MapEntry implements Object2ObjectMap.Entry<K, V>, Map.Entry<K, V>, Pair<K, V> {
        int index;

        MapEntry(int index) {
            Object2ObjectOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Object2ObjectOpenHashMap.this = this$0;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Object2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public K left() {
            return Object2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Object2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public V right() {
            return Object2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V oldValue = Object2ObjectOpenHashMap.this.value[this.index];
            Object2ObjectOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public Pair<K, V> right(V v) {
            Object2ObjectOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, V> e = (Map.Entry) o;
            return Objects.equals(Object2ObjectOpenHashMap.this.key[this.index], e.getKey()) && Objects.equals(Object2ObjectOpenHashMap.this.value[this.index], e.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (Object2ObjectOpenHashMap.this.key[this.index] == null ? 0 : Object2ObjectOpenHashMap.this.key[this.index].hashCode()) ^ (Object2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Object2ObjectOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Object2ObjectOpenHashMap.this.key[this.index] + "=>" + Object2ObjectOpenHashMap.this.value[this.index];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int pos;
        int last;

        /* renamed from: c */
        int f142c;
        boolean mustReturnNullKey;
        ObjectArrayList<K> wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            Object2ObjectOpenHashMap.this = r4;
            this.pos = Object2ObjectOpenHashMap.this.f140n;
            this.last = -1;
            this.f142c = Object2ObjectOpenHashMap.this.size;
            this.mustReturnNullKey = Object2ObjectOpenHashMap.this.containsNullKey;
        }

        public boolean hasNext() {
            return this.f142c != 0;
        }

        public int nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f142c--;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i = Object2ObjectOpenHashMap.this.f140n;
                this.last = i;
                return i;
            }
            K[] key = Object2ObjectOpenHashMap.this.key;
            do {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-this.pos) - 1);
                    int mix = HashCommon.mix(k.hashCode());
                    int i3 = Object2ObjectOpenHashMap.this.mask;
                    while (true) {
                        int p = mix & i3;
                        if (!k.equals(key[p])) {
                            mix = p + 1;
                            i3 = Object2ObjectOpenHashMap.this.mask;
                        } else {
                            return p;
                        }
                    }
                }
            } while (key[this.pos] == null);
            int i4 = this.pos;
            this.last = i4;
            return i4;
        }

        public void forEachRemaining(ConsumerType action) {
            int p;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i = Object2ObjectOpenHashMap.this.f140n;
                this.last = i;
                acceptOnIndex(action, i);
                this.f142c--;
            }
            K[] key = Object2ObjectOpenHashMap.this.key;
            while (this.f142c != 0) {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-this.pos) - 1);
                    int mix = HashCommon.mix(k.hashCode());
                    int i3 = Object2ObjectOpenHashMap.this.mask;
                    while (true) {
                        p = mix & i3;
                        if (k.equals(key[p])) {
                            break;
                        }
                        mix = p + 1;
                        i3 = Object2ObjectOpenHashMap.this.mask;
                    }
                    acceptOnIndex(action, p);
                    this.f142c--;
                } else if (key[this.pos] != null) {
                    int i4 = this.pos;
                    this.last = i4;
                    acceptOnIndex(action, i4);
                    this.f142c--;
                }
            }
        }

        private void shiftKeys(int pos) {
            K curr;
            K[] key = Object2ObjectOpenHashMap.this.key;
            while (true) {
                int last = pos;
                int i = last + 1;
                int i2 = Object2ObjectOpenHashMap.this.mask;
                while (true) {
                    pos = i & i2;
                    curr = key[pos];
                    if (curr == null) {
                        key[last] = null;
                        Object2ObjectOpenHashMap.this.value[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr.hashCode()) & Object2ObjectOpenHashMap.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i = pos + 1;
                        i2 = Object2ObjectOpenHashMap.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i = pos + 1;
                        i2 = Object2ObjectOpenHashMap.this.mask;
                    }
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Object2ObjectOpenHashMap.this.value[last] = Object2ObjectOpenHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Object2ObjectOpenHashMap.this.f140n) {
                Object2ObjectOpenHashMap.this.containsNullKey = false;
                Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.f140n] = null;
                Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.f140n] = null;
            } else if (this.pos >= 0) {
                shiftKeys(this.last);
            } else {
                Object2ObjectOpenHashMap.this.remove(this.wrapped.set((-this.pos) - 1, null));
                this.last = -1;
                return;
            }
            Object2ObjectOpenHashMap.this.size--;
            this.last = -1;
        }

        public int skip(int n) {
            int i = n;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0 || !hasNext()) {
                    break;
                }
                nextEntry();
            }
            return (n - i) - 1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>> implements ObjectIterator<Object2ObjectMap.Entry<K, V>> {
        private Object2ObjectOpenHashMap<K, V>.MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private EntryIterator() {
            super();
            Object2ObjectOpenHashMap.this = r5;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public Object2ObjectOpenHashMap<K, V>.MapEntry next() {
            Object2ObjectOpenHashMap<K, V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
            Object2ObjectOpenHashMap<K, V>.MapEntry mapEntry = new MapEntry(index);
            this.entry = mapEntry;
            action.accept(mapEntry);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$FastEntryIterator.class */
    private final class FastEntryIterator extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>> implements ObjectIterator<Object2ObjectMap.Entry<K, V>> {
        private final Object2ObjectOpenHashMap<K, V>.MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FastEntryIterator() {
            super();
            Object2ObjectOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public Object2ObjectOpenHashMap<K, V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$MapSpliterator.class */
    public abstract class MapSpliterator<ConsumerType, SplitType extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<ConsumerType, SplitType>> {
        int pos;
        int max;

        /* renamed from: c */
        int f143c;
        boolean mustReturnNull;
        boolean hasSplit;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            Object2ObjectOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Object2ObjectOpenHashMap.this.f140n;
            this.f143c = 0;
            this.mustReturnNull = Object2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            Object2ObjectOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Object2ObjectOpenHashMap.this.f140n;
            this.f143c = 0;
            this.mustReturnNull = Object2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        public boolean tryAdvance(ConsumerType action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f143c++;
                acceptOnIndex(action, Object2ObjectOpenHashMap.this.f140n);
                return true;
            }
            K[] key = Object2ObjectOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    this.f143c++;
                    int i = this.pos;
                    this.pos = i + 1;
                    acceptOnIndex(action, i);
                    return true;
                }
                this.pos++;
            }
            return false;
        }

        public void forEachRemaining(ConsumerType action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f143c++;
                acceptOnIndex(action, Object2ObjectOpenHashMap.this.f140n);
            }
            K[] key = Object2ObjectOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    acceptOnIndex(action, this.pos);
                    this.f143c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (!this.hasSplit) {
                return Object2ObjectOpenHashMap.this.size - this.f143c;
            }
            return Math.min(Object2ObjectOpenHashMap.this.size - this.f143c, ((long) ((Object2ObjectOpenHashMap.this.realSize() / Object2ObjectOpenHashMap.this.f140n) * (this.max - this.pos))) + (this.mustReturnNull ? 1 : 0));
        }

        public SplitType trySplit() {
            int retLen;
            if (this.pos >= this.max - 1 || (retLen = (this.max - this.pos) >> 1) <= 1) {
                return null;
            }
            int myNewPos = this.pos + retLen;
            int retPos = this.pos;
            SplitType split = makeForSplit(retPos, myNewPos, this.mustReturnNull);
            this.pos = myNewPos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return split;
        }

        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0) {
                return 0L;
            }
            long skipped = 0;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                skipped = 0 + 1;
                n--;
            }
            K[] key = Object2ObjectOpenHashMap.this.key;
            while (this.pos < this.max && n > 0) {
                int i = this.pos;
                this.pos = i + 1;
                if (key[i] != null) {
                    skipped++;
                    n--;
                }
            }
            return skipped;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$EntrySpliterator.class */
    public final class EntrySpliterator extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super Object2ObjectMap.Entry<K, V>>, Object2ObjectOpenHashMap<K, V>.EntrySpliterator> implements ObjectSpliterator<Object2ObjectMap.Entry<K, V>> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapSpliterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntrySpliterator) consumer);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance((EntrySpliterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EntrySpliterator() {
            super();
            Object2ObjectOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Object2ObjectOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapSpliterator
        public final Object2ObjectOpenHashMap<K, V>.EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new EntrySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
        private MapEntrySet() {
            Object2ObjectOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap.FastEntrySet
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            K curr;
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            Object key = e.getKey();
            Object value = e.getValue();
            if (key == null) {
                return Object2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.f140n], value);
            }
            K[] key2 = Object2ObjectOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2ObjectOpenHashMap.this.mask;
            int pos = mix;
            K curr2 = key2[mix];
            if (curr2 == null) {
                return false;
            }
            if (key.equals(curr2)) {
                return Objects.equals(Object2ObjectOpenHashMap.this.value[pos], value);
            }
            do {
                int i = (pos + 1) & Object2ObjectOpenHashMap.this.mask;
                pos = i;
                curr = key2[i];
                if (curr == null) {
                    return false;
                }
            } while (!key.equals(curr));
            return Objects.equals(Object2ObjectOpenHashMap.this.value[pos], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            Object key = e.getKey();
            Object value = e.getValue();
            if (key == null) {
                if (Object2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.f140n], value)) {
                    Object2ObjectOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            K[] key2 = Object2ObjectOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2ObjectOpenHashMap.this.mask;
            int pos = mix;
            K curr = key2[mix];
            if (curr == null) {
                return false;
            }
            if (curr.equals(key)) {
                if (Objects.equals(Object2ObjectOpenHashMap.this.value[pos], value)) {
                    Object2ObjectOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Object2ObjectOpenHashMap.this.mask;
                pos = i;
                K curr2 = key2[i];
                if (curr2 == null) {
                    return false;
                }
                if (curr2.equals(key) && Objects.equals(Object2ObjectOpenHashMap.this.value[pos], value)) {
                    Object2ObjectOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2ObjectOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            if (Object2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractObject2ObjectMap.BasicEntry(Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.f140n], Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.f140n]));
            }
            int pos = Object2ObjectOpenHashMap.this.f140n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Object2ObjectOpenHashMap.this.key[pos] != null) {
                        consumer.accept(new AbstractObject2ObjectMap.BasicEntry(Object2ObjectOpenHashMap.this.key[pos], Object2ObjectOpenHashMap.this.value[pos]));
                    }
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
            if (Object2ObjectOpenHashMap.this.containsNullKey) {
                entry.key = Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.f140n];
                entry.value = Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.f140n];
                consumer.accept(entry);
            }
            int pos = Object2ObjectOpenHashMap.this.f140n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Object2ObjectOpenHashMap.this.key[pos] != null) {
                        entry.key = Object2ObjectOpenHashMap.this.key[pos];
                        entry.value = Object2ObjectOpenHashMap.this.value[pos];
                        consumer.accept(entry);
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
    public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((KeyIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Object2ObjectOpenHashMap.this = r5;
        }

        final void acceptOnIndex(Consumer<? super K> action, int index) {
            action.accept((Object) Object2ObjectOpenHashMap.this.key[index]);
        }

        @Override // java.util.Iterator
        public K next() {
            return Object2ObjectOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$KeySpliterator.class */
    public final class KeySpliterator extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super K>, Object2ObjectOpenHashMap<K, V>.KeySpliterator> implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapSpliterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((KeySpliterator) consumer);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance((KeySpliterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySpliterator() {
            super();
            Object2ObjectOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Object2ObjectOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        final void acceptOnIndex(Consumer<? super K> action, int index) {
            action.accept((Object) Object2ObjectOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapSpliterator
        public final Object2ObjectOpenHashMap<K, V>.KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new KeySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractObjectSet<K> {
        private KeySet() {
            Object2ObjectOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return new KeySpliterator();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            if (Object2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept((Object) Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.f140n]);
            }
            int pos = Object2ObjectOpenHashMap.this.f140n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    Object obj = (Object) Object2ObjectOpenHashMap.this.key[pos];
                    if (obj != 0) {
                        consumer.accept(obj);
                    }
                } else {
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Object2ObjectOpenHashMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            int oldSize = Object2ObjectOpenHashMap.this.size;
            Object2ObjectOpenHashMap.this.remove(k);
            return Object2ObjectOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2ObjectOpenHashMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super V>> implements ObjectIterator<V> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((ValueIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Object2ObjectOpenHashMap.this = r5;
        }

        final void acceptOnIndex(Consumer<? super V> action, int index) {
            action.accept((Object) Object2ObjectOpenHashMap.this.value[index]);
        }

        @Override // java.util.Iterator
        public V next() {
            return Object2ObjectOpenHashMap.this.value[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectOpenHashMap$ValueSpliterator.class */
    public final class ValueSpliterator extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super V>, Object2ObjectOpenHashMap<K, V>.ValueSpliterator> implements ObjectSpliterator<V> {
        private static final int POST_SPLIT_CHARACTERISTICS = 0;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapSpliterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((ValueSpliterator) consumer);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance((ValueSpliterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator() {
            super();
            Object2ObjectOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Object2ObjectOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 0 : 64;
        }

        final void acceptOnIndex(Consumer<? super V> action, int index) {
            action.accept((Object) Object2ObjectOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.MapSpliterator
        public final Object2ObjectOpenHashMap<K, V>.ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new ValueSpliterator(pos, max, mustReturnNull, true);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap.1
                @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
                public ObjectIterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
                public ObjectSpliterator<V> spliterator() {
                    return new ValueSpliterator();
                }

                @Override // java.lang.Iterable
                public void forEach(Consumer<? super V> consumer) {
                    if (Object2ObjectOpenHashMap.this.containsNullKey) {
                        consumer.accept((Object) Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.f140n]);
                    }
                    int pos = Object2ObjectOpenHashMap.this.f140n;
                    while (true) {
                        int i = pos;
                        pos--;
                        if (i != 0) {
                            if (Object2ObjectOpenHashMap.this.key[pos] != null) {
                                consumer.accept((Object) Object2ObjectOpenHashMap.this.value[pos]);
                            }
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Object2ObjectOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object v) {
                    return Object2ObjectOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Object2ObjectOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f141f));
        if (l >= this.f140n || this.size > HashCommon.maxFill(l, this.f141f)) {
            return true;
        }
        try {
            rehash(l);
            return true;
        } catch (OutOfMemoryError e) {
            return false;
        }
    }

    protected void rehash(int newN) {
        int i;
        K[] key = this.key;
        V[] value = this.value;
        int mask = newN - 1;
        K[] newKey = (K[]) new Object[newN + 1];
        V[] newValue = (V[]) new Object[newN + 1];
        int i2 = this.f140n;
        int j = realSize();
        while (true) {
            int i3 = j;
            j--;
            if (i3 != 0) {
                do {
                    i2--;
                } while (key[i2] == null);
                int mix = HashCommon.mix(key[i2].hashCode()) & mask;
                int pos = mix;
                if (newKey[mix] != null) {
                    do {
                        i = (pos + 1) & mask;
                        pos = i;
                    } while (newKey[i] != null);
                }
                newKey[pos] = key[i2];
                newValue[pos] = value[i2];
            } else {
                newValue[newN] = value[this.f140n];
                this.f140n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.f140n, this.f141f);
                this.key = newKey;
                this.value = newValue;
                return;
            }
        }
    }

    public Object2ObjectOpenHashMap<K, V> clone() {
        try {
            Object2ObjectOpenHashMap<K, V> c = (Object2ObjectOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (K[]) ((Object[]) this.key.clone());
            c.value = (V[]) ((Object[]) this.value.clone());
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public int hashCode() {
        int h = 0;
        int j = realSize();
        int i = 0;
        int t = 0;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0) {
                break;
            }
            while (this.key[i] == null) {
                i++;
            }
            if (this != this.key[i]) {
                t = this.key[i].hashCode();
            }
            if (this != this.value[i]) {
                t ^= this.value[i] == null ? 0 : this.value[i].hashCode();
            }
            h += t;
            i++;
        }
        if (this.containsNullKey) {
            h += this.value[this.f140n] == null ? 0 : this.value[this.f140n].hashCode();
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        K[] key = this.key;
        V[] value = this.value;
        Object2ObjectOpenHashMap<K, V>.EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                int e = i.nextEntry();
                s.writeObject(key[e]);
                s.writeObject(value[e]);
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        s.defaultReadObject();
        this.f140n = HashCommon.arraySize(this.size, this.f141f);
        this.maxFill = HashCommon.maxFill(this.f140n, this.f141f);
        this.mask = this.f140n - 1;
        K[] key = (K[]) new Object[this.f140n + 1];
        this.key = key;
        V[] value = (V[]) new Object[this.f140n + 1];
        this.value = value;
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                Object readObject = s.readObject();
                Object readObject2 = s.readObject();
                if (readObject == null) {
                    pos = this.f140n;
                    this.containsNullKey = true;
                } else {
                    int mix = HashCommon.mix(readObject.hashCode());
                    int i3 = this.mask;
                    while (true) {
                        pos = mix & i3;
                        if (key[pos] != 0) {
                            mix = pos + 1;
                            i3 = this.mask;
                        }
                    }
                }
                key[pos] = readObject;
                value[pos] = readObject2;
            } else {
                return;
            }
        }
    }

    private void checkTable() {
    }
}
