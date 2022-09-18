package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap.class */
public class Int2ObjectOpenHashMap<V> extends AbstractInt2ObjectMap<V> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;

    /* renamed from: n */
    protected transient int f84n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f85f;
    protected transient Int2ObjectMap.FastEntrySet<V> entries;
    protected transient IntSet keys;
    protected transient ObjectCollection<V> values;

    public Int2ObjectOpenHashMap(int expected, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f85f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f84n = arraySize;
        this.minN = arraySize;
        this.mask = this.f84n - 1;
        this.maxFill = HashCommon.maxFill(this.f84n, f);
        this.key = new int[this.f84n + 1];
        this.value = (V[]) new Object[this.f84n + 1];
    }

    public Int2ObjectOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Int2ObjectOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2ObjectOpenHashMap(Map<? extends Integer, ? extends V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2ObjectOpenHashMap(Map<? extends Integer, ? extends V> m) {
        this(m, 0.75f);
    }

    public Int2ObjectOpenHashMap(Int2ObjectMap<V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2ObjectOpenHashMap(Int2ObjectMap<V> m) {
        this((Int2ObjectMap) m, 0.75f);
    }

    public Int2ObjectOpenHashMap(int[] k, V[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put(k[i], (int) v[i]);
        }
    }

    public Int2ObjectOpenHashMap(int[] k, V[] v) {
        this(k, v, 0.75f);
    }

    public int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f85f);
        if (needed > this.f84n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f85f))));
        if (needed > this.f84n) {
            rehash(needed);
        }
    }

    public V removeEntry(int pos) {
        V oldValue = this.value[pos];
        this.value[pos] = null;
        this.size--;
        shiftKeys(pos);
        if (this.f84n > this.minN && this.size < this.maxFill / 4 && this.f84n > 16) {
            rehash(this.f84n / 2);
        }
        return oldValue;
    }

    public V removeNullEntry() {
        this.containsNullKey = false;
        V oldValue = this.value[this.f84n];
        this.value[this.f84n] = null;
        this.size--;
        if (this.f84n > this.minN && this.size < this.maxFill / 4 && this.f84n > 16) {
            rehash(this.f84n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public void putAll(Map<? extends Integer, ? extends V> m) {
        if (this.f85f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.f84n : -(this.f84n + 1);
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return -(pos + 1);
        }
        if (k == curr2) {
            return pos;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return -(pos + 1);
            }
        } while (k != curr);
        return pos;
    }

    private void insert(int pos, int k, V v) {
        if (pos == this.f84n) {
            this.containsNullKey = true;
        }
        this.key[pos] = k;
        this.value[pos] = v;
        int i = this.size;
        this.size = i + 1;
        if (i >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f85f));
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V put(int k, V v) {
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
        int curr;
        int[] key = this.key;
        while (true) {
            int last = pos;
            int i = last + 1;
            int i2 = this.mask;
            while (true) {
                pos = i & i2;
                curr = key[pos];
                if (curr == 0) {
                    key[last] = 0;
                    this.value[last] = null;
                    return;
                }
                int slot = HashCommon.mix(curr) & this.mask;
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V remove(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                return removeNullEntry();
            }
            return this.defRetValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return this.defRetValue;
        }
        if (k == curr2) {
            return removeEntry(pos);
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return this.defRetValue;
            }
        } while (k != curr);
        return removeEntry(pos);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V get(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f84n] : this.defRetValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return this.defRetValue;
        }
        if (k == curr2) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return this.defRetValue;
            }
        } while (k != curr);
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean containsKey(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return false;
        }
        if (k == curr2) {
            return true;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return false;
            }
        } while (k != curr);
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean containsValue(Object v) {
        V[] value = this.value;
        int[] key = this.key;
        if (this.containsNullKey && Objects.equals(value[this.f84n], v)) {
            return true;
        }
        int i = this.f84n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                if (key[i] != 0 && Objects.equals(value[i], v)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V getOrDefault(int k, V defaultValue) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f84n] : defaultValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return defaultValue;
        }
        if (k == curr2) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return defaultValue;
            }
        } while (k != curr);
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V putIfAbsent(int k, V v) {
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        insert((-pos) - 1, k, v);
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean remove(int k, Object v) {
        if (k == 0) {
            if (this.containsNullKey && Objects.equals(v, this.value[this.f84n])) {
                removeNullEntry();
                return true;
            }
            return false;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr = key[mix];
        if (curr == 0) {
            return false;
        }
        if (k == curr && Objects.equals(v, this.value[pos])) {
            removeEntry(pos);
            return true;
        }
        while (true) {
            int i = (pos + 1) & this.mask;
            pos = i;
            int curr2 = key[i];
            if (curr2 == 0) {
                return false;
            }
            if (k == curr2 && Objects.equals(v, this.value[pos])) {
                removeEntry(pos);
                return true;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean replace(int k, V oldValue, V v) {
        int pos = find(k);
        if (pos < 0 || !Objects.equals(oldValue, this.value[pos])) {
            return false;
        }
        this.value[pos] = v;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V replace(int k, V v) {
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V computeIfAbsent(int k, IntFunction<? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        V newValue = mappingFunction.apply(k);
        insert((-pos) - 1, k, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V computeIfPresent(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        V newValue = remappingFunction.apply(Integer.valueOf(k), (Object) this.value[pos]);
        if (newValue == null) {
            if (k == 0) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        this.value[pos] = newValue;
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V compute(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        V newValue = remappingFunction.apply(Integer.valueOf(k), pos >= 0 ? (Object) this.value[pos] : (Object) null);
        if (newValue == null) {
            if (pos >= 0) {
                if (k == 0) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public V merge(int k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
            if (k == 0) {
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
        Arrays.fill(this.key, 0);
        Arrays.fill(this.value, (Object) null);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$MapEntry.class */
    public final class MapEntry implements Int2ObjectMap.Entry<V>, Map.Entry<Integer, V>, IntObjectPair<V> {
        int index;

        MapEntry(int index) {
            Int2ObjectOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Int2ObjectOpenHashMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.Entry
        public int getIntKey() {
            return Int2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntObjectPair
        public int leftInt() {
            return Int2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Int2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public V right() {
            return Int2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V oldValue = Int2ObjectOpenHashMap.this.value[this.index];
            Int2ObjectOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public IntObjectPair<V> right(V v) {
            Int2ObjectOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getKey() {
            return Integer.valueOf(Int2ObjectOpenHashMap.this.key[this.index]);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<Integer, V> e = (Map.Entry) o;
            return Int2ObjectOpenHashMap.this.key[this.index] == e.getKey().intValue() && Objects.equals(Int2ObjectOpenHashMap.this.value[this.index], e.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Int2ObjectOpenHashMap.this.key[this.index] ^ (Int2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Int2ObjectOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Int2ObjectOpenHashMap.this.key[this.index] + "=>" + Int2ObjectOpenHashMap.this.value[this.index];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int pos;
        int last;

        /* renamed from: c */
        int f86c;
        boolean mustReturnNullKey;
        IntArrayList wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            Int2ObjectOpenHashMap.this = r4;
            this.pos = Int2ObjectOpenHashMap.this.f84n;
            this.last = -1;
            this.f86c = Int2ObjectOpenHashMap.this.size;
            this.mustReturnNullKey = Int2ObjectOpenHashMap.this.containsNullKey;
        }

        public boolean hasNext() {
            return this.f86c != 0;
        }

        public int nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f86c--;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i = Int2ObjectOpenHashMap.this.f84n;
                this.last = i;
                return i;
            }
            int[] key = Int2ObjectOpenHashMap.this.key;
            do {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    int k = this.wrapped.getInt((-this.pos) - 1);
                    int mix = HashCommon.mix(k);
                    int i3 = Int2ObjectOpenHashMap.this.mask;
                    while (true) {
                        int p = mix & i3;
                        if (k != key[p]) {
                            mix = p + 1;
                            i3 = Int2ObjectOpenHashMap.this.mask;
                        } else {
                            return p;
                        }
                    }
                }
            } while (key[this.pos] == 0);
            int i4 = this.pos;
            this.last = i4;
            return i4;
        }

        public void forEachRemaining(ConsumerType action) {
            int p;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i = Int2ObjectOpenHashMap.this.f84n;
                this.last = i;
                acceptOnIndex(action, i);
                this.f86c--;
            }
            int[] key = Int2ObjectOpenHashMap.this.key;
            while (this.f86c != 0) {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    int k = this.wrapped.getInt((-this.pos) - 1);
                    int mix = HashCommon.mix(k);
                    int i3 = Int2ObjectOpenHashMap.this.mask;
                    while (true) {
                        p = mix & i3;
                        if (k == key[p]) {
                            break;
                        }
                        mix = p + 1;
                        i3 = Int2ObjectOpenHashMap.this.mask;
                    }
                    acceptOnIndex(action, p);
                    this.f86c--;
                } else if (key[this.pos] != 0) {
                    int i4 = this.pos;
                    this.last = i4;
                    acceptOnIndex(action, i4);
                    this.f86c--;
                }
            }
        }

        private void shiftKeys(int pos) {
            int curr;
            int[] key = Int2ObjectOpenHashMap.this.key;
            while (true) {
                int last = pos;
                int i = last + 1;
                int i2 = Int2ObjectOpenHashMap.this.mask;
                while (true) {
                    pos = i & i2;
                    curr = key[pos];
                    if (curr == 0) {
                        key[last] = 0;
                        Int2ObjectOpenHashMap.this.value[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr) & Int2ObjectOpenHashMap.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i = pos + 1;
                        i2 = Int2ObjectOpenHashMap.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i = pos + 1;
                        i2 = Int2ObjectOpenHashMap.this.mask;
                    }
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Int2ObjectOpenHashMap.this.value[last] = Int2ObjectOpenHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Int2ObjectOpenHashMap.this.f84n) {
                Int2ObjectOpenHashMap.this.containsNullKey = false;
                Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.f84n] = null;
            } else if (this.pos >= 0) {
                shiftKeys(this.last);
            } else {
                Int2ObjectOpenHashMap.this.remove(this.wrapped.getInt((-this.pos) - 1));
                this.last = -1;
                return;
            }
            Int2ObjectOpenHashMap.this.size--;
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends Int2ObjectOpenHashMap<V>.MapIterator<Consumer<? super Int2ObjectMap.Entry<V>>> implements ObjectIterator<Int2ObjectMap.Entry<V>> {
        private Int2ObjectOpenHashMap<V>.MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private EntryIterator() {
            super();
            Int2ObjectOpenHashMap.this = r5;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public Int2ObjectOpenHashMap<V>.MapEntry next() {
            Int2ObjectOpenHashMap<V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> action, int index) {
            Int2ObjectOpenHashMap<V>.MapEntry mapEntry = new MapEntry(index);
            this.entry = mapEntry;
            action.accept(mapEntry);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$FastEntryIterator.class */
    private final class FastEntryIterator extends Int2ObjectOpenHashMap<V>.MapIterator<Consumer<? super Int2ObjectMap.Entry<V>>> implements ObjectIterator<Int2ObjectMap.Entry<V>> {
        private final Int2ObjectOpenHashMap<V>.MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FastEntryIterator() {
            super();
            Int2ObjectOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public Int2ObjectOpenHashMap<V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$MapSpliterator.class */
    public abstract class MapSpliterator<ConsumerType, SplitType extends Int2ObjectOpenHashMap<V>.MapSpliterator<ConsumerType, SplitType>> {
        int pos;
        int max;

        /* renamed from: c */
        int f87c;
        boolean mustReturnNull;
        boolean hasSplit;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            Int2ObjectOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Int2ObjectOpenHashMap.this.f84n;
            this.f87c = 0;
            this.mustReturnNull = Int2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            Int2ObjectOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Int2ObjectOpenHashMap.this.f84n;
            this.f87c = 0;
            this.mustReturnNull = Int2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        public boolean tryAdvance(ConsumerType action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f87c++;
                acceptOnIndex(action, Int2ObjectOpenHashMap.this.f84n);
                return true;
            }
            int[] key = Int2ObjectOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    this.f87c++;
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
                this.f87c++;
                acceptOnIndex(action, Int2ObjectOpenHashMap.this.f84n);
            }
            int[] key = Int2ObjectOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    acceptOnIndex(action, this.pos);
                    this.f87c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (!this.hasSplit) {
                return Int2ObjectOpenHashMap.this.size - this.f87c;
            }
            return Math.min(Int2ObjectOpenHashMap.this.size - this.f87c, ((long) ((Int2ObjectOpenHashMap.this.realSize() / Int2ObjectOpenHashMap.this.f84n) * (this.max - this.pos))) + (this.mustReturnNull ? 1 : 0));
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
            int[] key = Int2ObjectOpenHashMap.this.key;
            while (this.pos < this.max && n > 0) {
                int i = this.pos;
                this.pos = i + 1;
                if (key[i] != 0) {
                    skipped++;
                    n--;
                }
            }
            return skipped;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$EntrySpliterator.class */
    public final class EntrySpliterator extends Int2ObjectOpenHashMap<V>.MapSpliterator<Consumer<? super Int2ObjectMap.Entry<V>>, Int2ObjectOpenHashMap<V>.EntrySpliterator> implements ObjectSpliterator<Int2ObjectMap.Entry<V>> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
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
            Int2ObjectOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Int2ObjectOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final Int2ObjectOpenHashMap<V>.EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new EntrySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSet<Int2ObjectMap.Entry<V>> implements Int2ObjectMap.FastEntrySet<V> {
        private MapEntrySet() {
            Int2ObjectOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.FastEntrySet
        public ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            int curr;
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            Object value = e.getValue();
            if (k == 0) {
                return Int2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.f84n], value);
            }
            int[] key = Int2ObjectOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2ObjectOpenHashMap.this.mask;
            int pos = mix;
            int curr2 = key[mix];
            if (curr2 == 0) {
                return false;
            }
            if (k == curr2) {
                return Objects.equals(Int2ObjectOpenHashMap.this.value[pos], value);
            }
            do {
                int i = (pos + 1) & Int2ObjectOpenHashMap.this.mask;
                pos = i;
                curr = key[i];
                if (curr == 0) {
                    return false;
                }
            } while (k != curr);
            return Objects.equals(Int2ObjectOpenHashMap.this.value[pos], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            Object value = e.getValue();
            if (k == 0) {
                if (Int2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.f84n], value)) {
                    Int2ObjectOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            int[] key = Int2ObjectOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2ObjectOpenHashMap.this.mask;
            int pos = mix;
            int curr = key[mix];
            if (curr == 0) {
                return false;
            }
            if (curr == k) {
                if (Objects.equals(Int2ObjectOpenHashMap.this.value[pos], value)) {
                    Int2ObjectOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Int2ObjectOpenHashMap.this.mask;
                pos = i;
                int curr2 = key[i];
                if (curr2 == 0) {
                    return false;
                }
                if (curr2 == k && Objects.equals(Int2ObjectOpenHashMap.this.value[pos], value)) {
                    Int2ObjectOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
            if (Int2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.f84n], Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.f84n]));
            }
            int pos = Int2ObjectOpenHashMap.this.f84n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Int2ObjectOpenHashMap.this.key[pos] != 0) {
                        consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectOpenHashMap.this.key[pos], Int2ObjectOpenHashMap.this.value[pos]));
                    }
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
            AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();
            if (Int2ObjectOpenHashMap.this.containsNullKey) {
                entry.key = Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.f84n];
                entry.value = Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.f84n];
                consumer.accept(entry);
            }
            int pos = Int2ObjectOpenHashMap.this.f84n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Int2ObjectOpenHashMap.this.key[pos] != 0) {
                        entry.key = Int2ObjectOpenHashMap.this.key[pos];
                        entry.value = Int2ObjectOpenHashMap.this.value[pos];
                        consumer.accept(entry);
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public Int2ObjectMap.FastEntrySet<V> int2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends Int2ObjectOpenHashMap<V>.MapIterator<java.util.function.IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((KeyIterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Int2ObjectOpenHashMap.this = r5;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2ObjectOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2ObjectOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$KeySpliterator.class */
    public final class KeySpliterator extends Int2ObjectOpenHashMap<V>.MapSpliterator<java.util.function.IntConsumer, Int2ObjectOpenHashMap<V>.KeySpliterator> implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 257;

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((KeySpliterator) intConsumer);
        }

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            return super.tryAdvance((KeySpliterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySpliterator() {
            super();
            Int2ObjectOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Int2ObjectOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            if (this.hasSplit) {
                return POST_SPLIT_CHARACTERISTICS;
            }
            return 321;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2ObjectOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final Int2ObjectOpenHashMap<V>.KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new KeySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractIntSet {
        private KeySet() {
            Int2ObjectOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return new KeySpliterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer consumer) {
            if (Int2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.f84n]);
            }
            int pos = Int2ObjectOpenHashMap.this.f84n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    int k = Int2ObjectOpenHashMap.this.key[pos];
                    if (k != 0) {
                        consumer.accept(k);
                    }
                } else {
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectOpenHashMap.this.size;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return Int2ObjectOpenHashMap.this.containsKey(k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            int oldSize = Int2ObjectOpenHashMap.this.size;
            Int2ObjectOpenHashMap.this.remove(k);
            return Int2ObjectOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectOpenHashMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends Int2ObjectOpenHashMap<V>.MapIterator<Consumer<? super V>> implements ObjectIterator<V> {
        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
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
            Int2ObjectOpenHashMap.this = r5;
        }

        final void acceptOnIndex(Consumer<? super V> action, int index) {
            action.accept((Object) Int2ObjectOpenHashMap.this.value[index]);
        }

        @Override // java.util.Iterator
        public V next() {
            return Int2ObjectOpenHashMap.this.value[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectOpenHashMap$ValueSpliterator.class */
    public final class ValueSpliterator extends Int2ObjectOpenHashMap<V>.MapSpliterator<Consumer<? super V>, Int2ObjectOpenHashMap<V>.ValueSpliterator> implements ObjectSpliterator<V> {
        private static final int POST_SPLIT_CHARACTERISTICS = 0;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
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
            Int2ObjectOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Int2ObjectOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 0 : 64;
        }

        final void acceptOnIndex(Consumer<? super V> action, int index) {
            action.accept((Object) Int2ObjectOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final Int2ObjectOpenHashMap<V>.ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new ValueSpliterator(pos, max, mustReturnNull, true);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.1
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
                    if (Int2ObjectOpenHashMap.this.containsNullKey) {
                        consumer.accept((Object) Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.f84n]);
                    }
                    int pos = Int2ObjectOpenHashMap.this.f84n;
                    while (true) {
                        int i = pos;
                        pos--;
                        if (i != 0) {
                            if (Int2ObjectOpenHashMap.this.key[pos] != 0) {
                                consumer.accept((Object) Int2ObjectOpenHashMap.this.value[pos]);
                            }
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Int2ObjectOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object v) {
                    return Int2ObjectOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Int2ObjectOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f85f));
        if (l >= this.f84n || this.size > HashCommon.maxFill(l, this.f85f)) {
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
        int[] key = this.key;
        V[] value = this.value;
        int mask = newN - 1;
        int[] newKey = new int[newN + 1];
        V[] newValue = (V[]) new Object[newN + 1];
        int i2 = this.f84n;
        int j = realSize();
        while (true) {
            int i3 = j;
            j--;
            if (i3 != 0) {
                do {
                    i2--;
                } while (key[i2] == 0);
                int mix = HashCommon.mix(key[i2]) & mask;
                int pos = mix;
                if (newKey[mix] != 0) {
                    do {
                        i = (pos + 1) & mask;
                        pos = i;
                    } while (newKey[i] != 0);
                }
                newKey[pos] = key[i2];
                newValue[pos] = value[i2];
            } else {
                newValue[newN] = value[this.f84n];
                this.f84n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.f84n, this.f85f);
                this.key = newKey;
                this.value = newValue;
                return;
            }
        }
    }

    public Int2ObjectOpenHashMap<V> clone() {
        try {
            Int2ObjectOpenHashMap<V> c = (Int2ObjectOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (int[]) this.key.clone();
            c.value = (V[]) ((Object[]) this.value.clone());
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public int hashCode() {
        int h = 0;
        int j = realSize();
        int i = 0;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0) {
                break;
            }
            while (this.key[i] == 0) {
                i++;
            }
            int t = this.key[i];
            if (this != this.value[i]) {
                t ^= this.value[i] == null ? 0 : this.value[i].hashCode();
            }
            h += t;
            i++;
        }
        if (this.containsNullKey) {
            h += this.value[this.f84n] == null ? 0 : this.value[this.f84n].hashCode();
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int[] key = this.key;
        V[] value = this.value;
        Int2ObjectOpenHashMap<V>.EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                int e = i.nextEntry();
                s.writeInt(key[e]);
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
        this.f84n = HashCommon.arraySize(this.size, this.f85f);
        this.maxFill = HashCommon.maxFill(this.f84n, this.f85f);
        this.mask = this.f84n - 1;
        int[] key = new int[this.f84n + 1];
        this.key = key;
        V[] value = (V[]) new Object[this.f84n + 1];
        this.value = value;
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int k = s.readInt();
                Object readObject = s.readObject();
                if (k == 0) {
                    pos = this.f84n;
                    this.containsNullKey = true;
                } else {
                    int mix = HashCommon.mix(k);
                    int i3 = this.mask;
                    while (true) {
                        pos = mix & i3;
                        if (key[pos] != 0) {
                            mix = pos + 1;
                            i3 = this.mask;
                        }
                    }
                }
                key[pos] = k;
                value[pos] = readObject;
            } else {
                return;
            }
        }
    }

    private void checkTable() {
    }
}
