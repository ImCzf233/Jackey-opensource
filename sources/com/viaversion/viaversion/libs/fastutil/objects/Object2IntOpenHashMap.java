package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
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
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap.class */
public class Object2IntOpenHashMap<K> extends AbstractObject2IntMap<K> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;

    /* renamed from: n */
    protected transient int f134n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f135f;
    protected transient Object2IntMap.FastEntrySet<K> entries;
    protected transient ObjectSet<K> keys;
    protected transient IntCollection values;

    public Object2IntOpenHashMap(int expected, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f135f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f134n = arraySize;
        this.minN = arraySize;
        this.mask = this.f134n - 1;
        this.maxFill = HashCommon.maxFill(this.f134n, f);
        this.key = (K[]) new Object[this.f134n + 1];
        this.value = new int[this.f134n + 1];
    }

    public Object2IntOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Object2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> m) {
        this(m, 0.75f);
    }

    public Object2IntOpenHashMap(Object2IntMap<K> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2IntOpenHashMap(Object2IntMap<K> m) {
        this((Object2IntMap) m, 0.75f);
    }

    public Object2IntOpenHashMap(K[] k, int[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put((Object2IntOpenHashMap<K>) k[i], v[i]);
        }
    }

    public Object2IntOpenHashMap(K[] k, int[] v) {
        this(k, v, 0.75f);
    }

    public int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f135f);
        if (needed > this.f134n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f135f))));
        if (needed > this.f134n) {
            rehash(needed);
        }
    }

    public int removeEntry(int pos) {
        int oldValue = this.value[pos];
        this.size--;
        shiftKeys(pos);
        if (this.f134n > this.minN && this.size < this.maxFill / 4 && this.f134n > 16) {
            rehash(this.f134n / 2);
        }
        return oldValue;
    }

    public int removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.f134n] = null;
        int oldValue = this.value[this.f134n];
        this.size--;
        if (this.f134n > this.minN && this.size < this.maxFill / 4 && this.f134n > 16) {
            rehash(this.f134n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
    public void putAll(Map<? extends K, ? extends Integer> m) {
        if (this.f135f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(K k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.f134n : -(this.f134n + 1);
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

    private void insert(int pos, K k, int v) {
        if (pos == this.f134n) {
            this.containsNullKey = true;
        }
        this.key[pos] = k;
        this.value[pos] = v;
        int i = this.size;
        this.size = i + 1;
        if (i >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f135f));
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int put(K k, int v) {
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    private int addToValue(int pos, int incr) {
        int oldValue = this.value[pos];
        this.value[pos] = oldValue + incr;
        return oldValue;
    }

    public int addTo(K k, int incr) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                return addToValue(this.f134n, incr);
            }
            pos = this.f134n;
            this.containsNullKey = true;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    return addToValue(pos, incr);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
                return addToValue(pos, incr);
            }
        }
        this.key[pos] = k;
        this.value[pos] = this.defRetValue + incr;
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f135f));
        }
        return this.defRetValue;
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int removeInt(Object k) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int getInt(Object k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f134n] : this.defRetValue;
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.Function
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean containsValue(int v) {
        int[] value = this.value;
        K[] key = this.key;
        if (this.containsNullKey && value[this.f134n] == v) {
            return true;
        }
        int i = this.f134n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                if (key[i] != null && value[i] == v) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int getOrDefault(Object k, int defaultValue) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f134n] : defaultValue;
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int putIfAbsent(K k, int v) {
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        insert((-pos) - 1, k, v);
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean remove(Object k, int v) {
        if (k == null) {
            if (this.containsNullKey && v == this.value[this.f134n]) {
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
        if (k.equals(curr) && v == this.value[pos]) {
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
            if (k.equals(curr2) && v == this.value[pos]) {
                removeEntry(pos);
                return true;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean replace(K k, int oldValue, int v) {
        int pos = find(k);
        if (pos < 0 || oldValue != this.value[pos]) {
            return false;
        }
        this.value[pos] = v;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int replace(K k, int v) {
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        int newValue = mappingFunction.applyAsInt(k);
        insert((-pos) - 1, k, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(key);
        if (pos >= 0) {
            return this.value[pos];
        }
        if (!mappingFunction.containsKey(key)) {
            return this.defRetValue;
        }
        int newValue = mappingFunction.getInt(key);
        insert((-pos) - 1, key, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        Integer newValue = remappingFunction.apply(k, Integer.valueOf(this.value[pos]));
        if (newValue == null) {
            if (k == null) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        int[] iArr = this.value;
        int intValue = newValue.intValue();
        iArr[pos] = intValue;
        return intValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        Integer newValue = remappingFunction.apply(k, pos >= 0 ? Integer.valueOf(this.value[pos]) : null);
        if (newValue == null) {
            if (pos >= 0) {
                if (k == null) {
                    removeNullEntry();
                } else {
                    removeEntry(pos);
                }
            }
            return this.defRetValue;
        }
        int newVal = newValue.intValue();
        if (pos < 0) {
            insert((-pos) - 1, k, newVal);
            return newVal;
        }
        this.value[pos] = newVal;
        return newVal;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int merge(K k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return v;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
        if (newValue == null) {
            if (k == null) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        int[] iArr = this.value;
        int intValue = newValue.intValue();
        iArr[pos] = intValue;
        return intValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, (Object) null);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$MapEntry.class */
    public final class MapEntry implements Object2IntMap.Entry<K>, Map.Entry<K, Integer>, ObjectIntPair<K> {
        int index;

        MapEntry(int index) {
            Object2IntOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Object2IntOpenHashMap.this = this$0;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Object2IntOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public K left() {
            return Object2IntOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        public int getIntValue() {
            return Object2IntOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair
        public int rightInt() {
            return Object2IntOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        public int setValue(int v) {
            int oldValue = Object2IntOpenHashMap.this.value[this.index];
            Object2IntOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair
        public ObjectIntPair<K> right(int v) {
            Object2IntOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getValue() {
            return Integer.valueOf(Object2IntOpenHashMap.this.value[this.index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        @Deprecated
        public Integer setValue(Integer v) {
            return Integer.valueOf(setValue(v.intValue()));
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, Integer> e = (Map.Entry) o;
            return Objects.equals(Object2IntOpenHashMap.this.key[this.index], e.getKey()) && Object2IntOpenHashMap.this.value[this.index] == e.getValue().intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (Object2IntOpenHashMap.this.key[this.index] == null ? 0 : Object2IntOpenHashMap.this.key[this.index].hashCode()) ^ Object2IntOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Object2IntOpenHashMap.this.key[this.index] + "=>" + Object2IntOpenHashMap.this.value[this.index];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int pos;
        int last;

        /* renamed from: c */
        int f136c;
        boolean mustReturnNullKey;
        ObjectArrayList<K> wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            Object2IntOpenHashMap.this = r4;
            this.pos = Object2IntOpenHashMap.this.f134n;
            this.last = -1;
            this.f136c = Object2IntOpenHashMap.this.size;
            this.mustReturnNullKey = Object2IntOpenHashMap.this.containsNullKey;
        }

        public boolean hasNext() {
            return this.f136c != 0;
        }

        public int nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f136c--;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i = Object2IntOpenHashMap.this.f134n;
                this.last = i;
                return i;
            }
            K[] key = Object2IntOpenHashMap.this.key;
            do {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-this.pos) - 1);
                    int mix = HashCommon.mix(k.hashCode());
                    int i3 = Object2IntOpenHashMap.this.mask;
                    while (true) {
                        int p = mix & i3;
                        if (!k.equals(key[p])) {
                            mix = p + 1;
                            i3 = Object2IntOpenHashMap.this.mask;
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
                int i = Object2IntOpenHashMap.this.f134n;
                this.last = i;
                acceptOnIndex(action, i);
                this.f136c--;
            }
            K[] key = Object2IntOpenHashMap.this.key;
            while (this.f136c != 0) {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-this.pos) - 1);
                    int mix = HashCommon.mix(k.hashCode());
                    int i3 = Object2IntOpenHashMap.this.mask;
                    while (true) {
                        p = mix & i3;
                        if (k.equals(key[p])) {
                            break;
                        }
                        mix = p + 1;
                        i3 = Object2IntOpenHashMap.this.mask;
                    }
                    acceptOnIndex(action, p);
                    this.f136c--;
                } else if (key[this.pos] != null) {
                    int i4 = this.pos;
                    this.last = i4;
                    acceptOnIndex(action, i4);
                    this.f136c--;
                }
            }
        }

        private void shiftKeys(int pos) {
            K curr;
            K[] key = Object2IntOpenHashMap.this.key;
            while (true) {
                int last = pos;
                int i = last + 1;
                int i2 = Object2IntOpenHashMap.this.mask;
                while (true) {
                    pos = i & i2;
                    curr = key[pos];
                    if (curr == null) {
                        key[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr.hashCode()) & Object2IntOpenHashMap.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i = pos + 1;
                        i2 = Object2IntOpenHashMap.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i = pos + 1;
                        i2 = Object2IntOpenHashMap.this.mask;
                    }
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Object2IntOpenHashMap.this.value[last] = Object2IntOpenHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Object2IntOpenHashMap.this.f134n) {
                Object2IntOpenHashMap.this.containsNullKey = false;
                Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.f134n] = null;
            } else if (this.pos >= 0) {
                shiftKeys(this.last);
            } else {
                Object2IntOpenHashMap.this.removeInt(this.wrapped.set((-this.pos) - 1, null));
                this.last = -1;
                return;
            }
            Object2IntOpenHashMap.this.size--;
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends Object2IntOpenHashMap<K>.MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectIterator<Object2IntMap.Entry<K>> {
        private Object2IntOpenHashMap<K>.MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private EntryIterator() {
            super();
            Object2IntOpenHashMap.this = r5;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public Object2IntOpenHashMap<K>.MapEntry next() {
            Object2IntOpenHashMap<K>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
            Object2IntOpenHashMap<K>.MapEntry mapEntry = new MapEntry(index);
            this.entry = mapEntry;
            action.accept(mapEntry);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$FastEntryIterator.class */
    private final class FastEntryIterator extends Object2IntOpenHashMap<K>.MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectIterator<Object2IntMap.Entry<K>> {
        private final Object2IntOpenHashMap<K>.MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FastEntryIterator() {
            super();
            Object2IntOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public Object2IntOpenHashMap<K>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$MapSpliterator.class */
    public abstract class MapSpliterator<ConsumerType, SplitType extends Object2IntOpenHashMap<K>.MapSpliterator<ConsumerType, SplitType>> {
        int pos;
        int max;

        /* renamed from: c */
        int f137c;
        boolean mustReturnNull;
        boolean hasSplit;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            Object2IntOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Object2IntOpenHashMap.this.f134n;
            this.f137c = 0;
            this.mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            Object2IntOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Object2IntOpenHashMap.this.f134n;
            this.f137c = 0;
            this.mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        public boolean tryAdvance(ConsumerType action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f137c++;
                acceptOnIndex(action, Object2IntOpenHashMap.this.f134n);
                return true;
            }
            K[] key = Object2IntOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    this.f137c++;
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
                this.f137c++;
                acceptOnIndex(action, Object2IntOpenHashMap.this.f134n);
            }
            K[] key = Object2IntOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    acceptOnIndex(action, this.pos);
                    this.f137c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (!this.hasSplit) {
                return Object2IntOpenHashMap.this.size - this.f137c;
            }
            return Math.min(Object2IntOpenHashMap.this.size - this.f137c, ((long) ((Object2IntOpenHashMap.this.realSize() / Object2IntOpenHashMap.this.f134n) * (this.max - this.pos))) + (this.mustReturnNull ? 1 : 0));
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
            K[] key = Object2IntOpenHashMap.this.key;
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$EntrySpliterator.class */
    public final class EntrySpliterator extends Object2IntOpenHashMap<K>.MapSpliterator<Consumer<? super Object2IntMap.Entry<K>>, Object2IntOpenHashMap<K>.EntrySpliterator> implements ObjectSpliterator<Object2IntMap.Entry<K>> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapSpliterator
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
            Object2IntOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Object2IntOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapSpliterator
        public final Object2IntOpenHashMap<K>.EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new EntrySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSet<Object2IntMap.Entry<K>> implements Object2IntMap.FastEntrySet<K> {
        private MapEntrySet() {
            Object2IntOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
            return new EntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.FastEntrySet
        public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            K curr;
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object key = e.getKey();
            int v = ((Integer) e.getValue()).intValue();
            if (key == null) {
                return Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.f134n] == v;
            }
            K[] key2 = Object2IntOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2IntOpenHashMap.this.mask;
            int pos = mix;
            K curr2 = key2[mix];
            if (curr2 == null) {
                return false;
            }
            if (key.equals(curr2)) {
                return Object2IntOpenHashMap.this.value[pos] == v;
            }
            do {
                int i = (pos + 1) & Object2IntOpenHashMap.this.mask;
                pos = i;
                curr = key2[i];
                if (curr == null) {
                    return false;
                }
            } while (!key.equals(curr));
            return Object2IntOpenHashMap.this.value[pos] == v;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object key = e.getKey();
            int v = ((Integer) e.getValue()).intValue();
            if (key == null) {
                if (Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.f134n] == v) {
                    Object2IntOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            K[] key2 = Object2IntOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2IntOpenHashMap.this.mask;
            int pos = mix;
            K curr = key2[mix];
            if (curr == null) {
                return false;
            }
            if (curr.equals(key)) {
                if (Object2IntOpenHashMap.this.value[pos] == v) {
                    Object2IntOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Object2IntOpenHashMap.this.mask;
                pos = i;
                K curr2 = key2[i];
                if (curr2 == null) {
                    return false;
                }
                if (curr2.equals(key) && Object2IntOpenHashMap.this.value[pos] == v) {
                    Object2IntOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2IntOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2IntOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            if (Object2IntOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractObject2IntMap.BasicEntry(Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.f134n], Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.f134n]));
            }
            int pos = Object2IntOpenHashMap.this.f134n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Object2IntOpenHashMap.this.key[pos] != null) {
                        consumer.accept(new AbstractObject2IntMap.BasicEntry(Object2IntOpenHashMap.this.key[pos], Object2IntOpenHashMap.this.value[pos]));
                    }
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();
            if (Object2IntOpenHashMap.this.containsNullKey) {
                entry.key = Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.f134n];
                entry.value = Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.f134n];
                consumer.accept(entry);
            }
            int pos = Object2IntOpenHashMap.this.f134n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Object2IntOpenHashMap.this.key[pos] != null) {
                        entry.key = Object2IntOpenHashMap.this.key[pos];
                        entry.value = Object2IntOpenHashMap.this.value[pos];
                        consumer.accept(entry);
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends Object2IntOpenHashMap<K>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapIterator
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
            Object2IntOpenHashMap.this = r5;
        }

        final void acceptOnIndex(Consumer<? super K> action, int index) {
            action.accept((Object) Object2IntOpenHashMap.this.key[index]);
        }

        @Override // java.util.Iterator
        public K next() {
            return Object2IntOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$KeySpliterator.class */
    public final class KeySpliterator extends Object2IntOpenHashMap<K>.MapSpliterator<Consumer<? super K>, Object2IntOpenHashMap<K>.KeySpliterator> implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapSpliterator
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
            Object2IntOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Object2IntOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        final void acceptOnIndex(Consumer<? super K> action, int index) {
            action.accept((Object) Object2IntOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapSpliterator
        public final Object2IntOpenHashMap<K>.KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new KeySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractObjectSet<K> {
        private KeySet() {
            Object2IntOpenHashMap.this = r4;
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
            if (Object2IntOpenHashMap.this.containsNullKey) {
                consumer.accept((Object) Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.f134n]);
            }
            int pos = Object2IntOpenHashMap.this.f134n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    Object obj = (Object) Object2IntOpenHashMap.this.key[pos];
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
            return Object2IntOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Object2IntOpenHashMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            int oldSize = Object2IntOpenHashMap.this.size;
            Object2IntOpenHashMap.this.removeInt(k);
            return Object2IntOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2IntOpenHashMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends Object2IntOpenHashMap<K>.MapIterator<IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining((ValueIterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Object2IntOpenHashMap.this = r5;
        }

        public final void acceptOnIndex(IntConsumer action, int index) {
            action.accept(Object2IntOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Object2IntOpenHashMap.this.value[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntOpenHashMap$ValueSpliterator.class */
    public final class ValueSpliterator extends Object2IntOpenHashMap<K>.MapSpliterator<IntConsumer, Object2IntOpenHashMap<K>.ValueSpliterator> implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 256;

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining((ValueSpliterator) intConsumer);
        }

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
            return super.tryAdvance((ValueSpliterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator() {
            super();
            Object2IntOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Object2IntOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 256 : 320;
        }

        public final void acceptOnIndex(IntConsumer action, int index) {
            action.accept(Object2IntOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.MapSpliterator
        public final Object2IntOpenHashMap<K>.ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new ValueSpliterator(pos, max, mustReturnNull, true);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.1
                @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
                /* renamed from: iterator */
                public Iterator<Integer> iterator2() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
                /* renamed from: spliterator */
                public Spliterator<Integer> spliterator2() {
                    return new ValueSpliterator();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
                public void forEach(IntConsumer consumer) {
                    if (Object2IntOpenHashMap.this.containsNullKey) {
                        consumer.accept(Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.f134n]);
                    }
                    int pos = Object2IntOpenHashMap.this.f134n;
                    while (true) {
                        int i = pos;
                        pos--;
                        if (i != 0) {
                            if (Object2IntOpenHashMap.this.key[pos] != null) {
                                consumer.accept(Object2IntOpenHashMap.this.value[pos]);
                            }
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Object2IntOpenHashMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
                public boolean contains(int v) {
                    return Object2IntOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Object2IntOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f135f));
        if (l >= this.f134n || this.size > HashCommon.maxFill(l, this.f135f)) {
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
        int[] value = this.value;
        int mask = newN - 1;
        K[] newKey = (K[]) new Object[newN + 1];
        int[] newValue = new int[newN + 1];
        int i2 = this.f134n;
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
                newValue[newN] = value[this.f134n];
                this.f134n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.f134n, this.f135f);
                this.key = newKey;
                this.value = newValue;
                return;
            }
        }
    }

    public Object2IntOpenHashMap<K> clone() {
        try {
            Object2IntOpenHashMap<K> c = (Object2IntOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (K[]) ((Object[]) this.key.clone());
            c.value = (int[]) this.value.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
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
            t ^= this.value[i];
            h += t;
            i++;
        }
        if (this.containsNullKey) {
            h += this.value[this.f134n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        K[] key = this.key;
        int[] value = this.value;
        Object2IntOpenHashMap<K>.EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                int e = i.nextEntry();
                s.writeObject(key[e]);
                s.writeInt(value[e]);
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        s.defaultReadObject();
        this.f134n = HashCommon.arraySize(this.size, this.f135f);
        this.maxFill = HashCommon.maxFill(this.f134n, this.f135f);
        this.mask = this.f134n - 1;
        K[] key = (K[]) new Object[this.f134n + 1];
        this.key = key;
        int[] value = new int[this.f134n + 1];
        this.value = value;
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                Object readObject = s.readObject();
                int v = s.readInt();
                if (readObject == null) {
                    pos = this.f134n;
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
                value[pos] = v;
            } else {
                return;
            }
        }
    }

    private void checkTable() {
    }
}
