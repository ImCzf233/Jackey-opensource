package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
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

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap.class */
public class Int2IntOpenHashMap extends AbstractInt2IntMap implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;

    /* renamed from: n */
    protected transient int f78n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f79f;
    protected transient Int2IntMap.FastEntrySet entries;
    protected transient IntSet keys;
    protected transient IntCollection values;

    public Int2IntOpenHashMap(int expected, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f79f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f78n = arraySize;
        this.minN = arraySize;
        this.mask = this.f78n - 1;
        this.maxFill = HashCommon.maxFill(this.f78n, f);
        this.key = new int[this.f78n + 1];
        this.value = new int[this.f78n + 1];
    }

    public Int2IntOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Int2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2IntOpenHashMap(Map<? extends Integer, ? extends Integer> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2IntOpenHashMap(Map<? extends Integer, ? extends Integer> m) {
        this(m, 0.75f);
    }

    public Int2IntOpenHashMap(Int2IntMap m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2IntOpenHashMap(Int2IntMap m) {
        this(m, 0.75f);
    }

    public Int2IntOpenHashMap(int[] k, int[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put(k[i], v[i]);
        }
    }

    public Int2IntOpenHashMap(int[] k, int[] v) {
        this(k, v, 0.75f);
    }

    public int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f79f);
        if (needed > this.f78n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f79f))));
        if (needed > this.f78n) {
            rehash(needed);
        }
    }

    public int removeEntry(int pos) {
        int oldValue = this.value[pos];
        this.size--;
        shiftKeys(pos);
        if (this.f78n > this.minN && this.size < this.maxFill / 4 && this.f78n > 16) {
            rehash(this.f78n / 2);
        }
        return oldValue;
    }

    public int removeNullEntry() {
        this.containsNullKey = false;
        int oldValue = this.value[this.f78n];
        this.size--;
        if (this.f78n > this.minN && this.size < this.maxFill / 4 && this.f78n > 16) {
            rehash(this.f78n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public void putAll(Map<? extends Integer, ? extends Integer> m) {
        if (this.f79f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.f78n : -(this.f78n + 1);
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

    private void insert(int pos, int k, int v) {
        if (pos == this.f78n) {
            this.containsNullKey = true;
        }
        this.key[pos] = k;
        this.value[pos] = v;
        int i = this.size;
        this.size = i + 1;
        if (i >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f79f));
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int put(int k, int v) {
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

    public int addTo(int k, int incr) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                return addToValue(this.f78n, incr);
            }
            pos = this.f78n;
            this.containsNullKey = true;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    return addToValue(pos, incr);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
                return addToValue(pos, incr);
            }
        }
        this.key[pos] = k;
        this.value[pos] = this.defRetValue + incr;
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f79f));
        }
        return this.defRetValue;
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int remove(int k) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int get(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f78n] : this.defRetValue;
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean containsValue(int v) {
        int[] value = this.value;
        int[] key = this.key;
        if (this.containsNullKey && value[this.f78n] == v) {
            return true;
        }
        int i = this.f78n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                if (key[i] != 0 && value[i] == v) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int getOrDefault(int k, int defaultValue) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f78n] : defaultValue;
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int putIfAbsent(int k, int v) {
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        insert((-pos) - 1, k, v);
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean remove(int k, int v) {
        if (k == 0) {
            if (this.containsNullKey && v == this.value[this.f78n]) {
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
        if (k == curr && v == this.value[pos]) {
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
            if (k == curr2 && v == this.value[pos]) {
                removeEntry(pos);
                return true;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean replace(int k, int oldValue, int v) {
        int pos = find(k);
        if (pos < 0 || oldValue != this.value[pos]) {
            return false;
        }
        this.value[pos] = v;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int replace(int k, int v) {
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfAbsent(int k, java.util.function.IntUnaryOperator mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        int newValue = mappingFunction.applyAsInt(k);
        insert((-pos) - 1, k, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfAbsent(int key, Int2IntFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(key);
        if (pos >= 0) {
            return this.value[pos];
        }
        if (!mappingFunction.containsKey(key)) {
            return this.defRetValue;
        }
        int newValue = mappingFunction.get(key);
        insert((-pos) - 1, key, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfAbsentNullable(int k, IntFunction<? extends Integer> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        Integer newValue = mappingFunction.apply(k);
        if (newValue == null) {
            return this.defRetValue;
        }
        int v = newValue.intValue();
        insert((-pos) - 1, k, v);
        return v;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfPresent(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(k), Integer.valueOf(this.value[pos]));
        if (newValue == null) {
            if (k == 0) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int compute(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        Integer newValue = remappingFunction.apply(Integer.valueOf(k), pos >= 0 ? Integer.valueOf(this.value[pos]) : null);
        if (newValue == null) {
            if (pos >= 0) {
                if (k == 0) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int merge(int k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return v;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
        if (newValue == null) {
            if (k == 0) {
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
        Arrays.fill(this.key, 0);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$MapEntry.class */
    public final class MapEntry implements Int2IntMap.Entry, Map.Entry<Integer, Integer>, IntIntPair {
        int index;

        MapEntry(int index) {
            Int2IntOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Int2IntOpenHashMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        public int getIntKey() {
            return Int2IntOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
        public int leftInt() {
            return Int2IntOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        public int getIntValue() {
            return Int2IntOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
        public int rightInt() {
            return Int2IntOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        public int setValue(int v) {
            int oldValue = Int2IntOpenHashMap.this.value[this.index];
            Int2IntOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
        public IntIntPair right(int v) {
            Int2IntOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getKey() {
            return Integer.valueOf(Int2IntOpenHashMap.this.key[this.index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getValue() {
            return Integer.valueOf(Int2IntOpenHashMap.this.value[this.index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        @Deprecated
        public Integer setValue(Integer v) {
            return Integer.valueOf(setValue(v.intValue()));
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<Integer, Integer> e = (Map.Entry) o;
            return Int2IntOpenHashMap.this.key[this.index] == e.getKey().intValue() && Int2IntOpenHashMap.this.value[this.index] == e.getValue().intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Int2IntOpenHashMap.this.key[this.index] ^ Int2IntOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Int2IntOpenHashMap.this.key[this.index] + "=>" + Int2IntOpenHashMap.this.value[this.index];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int pos;
        int last;

        /* renamed from: c */
        int f80c;
        boolean mustReturnNullKey;
        IntArrayList wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            Int2IntOpenHashMap.this = r4;
            this.pos = Int2IntOpenHashMap.this.f78n;
            this.last = -1;
            this.f80c = Int2IntOpenHashMap.this.size;
            this.mustReturnNullKey = Int2IntOpenHashMap.this.containsNullKey;
        }

        public boolean hasNext() {
            return this.f80c != 0;
        }

        public int nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f80c--;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i = Int2IntOpenHashMap.this.f78n;
                this.last = i;
                return i;
            }
            int[] key = Int2IntOpenHashMap.this.key;
            do {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    int k = this.wrapped.getInt((-this.pos) - 1);
                    int mix = HashCommon.mix(k);
                    int i3 = Int2IntOpenHashMap.this.mask;
                    while (true) {
                        int p = mix & i3;
                        if (k != key[p]) {
                            mix = p + 1;
                            i3 = Int2IntOpenHashMap.this.mask;
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
                int i = Int2IntOpenHashMap.this.f78n;
                this.last = i;
                acceptOnIndex(action, i);
                this.f80c--;
            }
            int[] key = Int2IntOpenHashMap.this.key;
            while (this.f80c != 0) {
                int i2 = this.pos - 1;
                this.pos = i2;
                if (i2 < 0) {
                    this.last = Integer.MIN_VALUE;
                    int k = this.wrapped.getInt((-this.pos) - 1);
                    int mix = HashCommon.mix(k);
                    int i3 = Int2IntOpenHashMap.this.mask;
                    while (true) {
                        p = mix & i3;
                        if (k == key[p]) {
                            break;
                        }
                        mix = p + 1;
                        i3 = Int2IntOpenHashMap.this.mask;
                    }
                    acceptOnIndex(action, p);
                    this.f80c--;
                } else if (key[this.pos] != 0) {
                    int i4 = this.pos;
                    this.last = i4;
                    acceptOnIndex(action, i4);
                    this.f80c--;
                }
            }
        }

        private void shiftKeys(int pos) {
            int curr;
            int[] key = Int2IntOpenHashMap.this.key;
            while (true) {
                int last = pos;
                int i = last + 1;
                int i2 = Int2IntOpenHashMap.this.mask;
                while (true) {
                    pos = i & i2;
                    curr = key[pos];
                    if (curr == 0) {
                        key[last] = 0;
                        return;
                    }
                    int slot = HashCommon.mix(curr) & Int2IntOpenHashMap.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i = pos + 1;
                        i2 = Int2IntOpenHashMap.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i = pos + 1;
                        i2 = Int2IntOpenHashMap.this.mask;
                    }
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Int2IntOpenHashMap.this.value[last] = Int2IntOpenHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Int2IntOpenHashMap.this.f78n) {
                Int2IntOpenHashMap.this.containsNullKey = false;
            } else if (this.pos >= 0) {
                shiftKeys(this.last);
            } else {
                Int2IntOpenHashMap.this.remove(this.wrapped.getInt((-this.pos) - 1));
                this.last = -1;
                return;
            }
            Int2IntOpenHashMap.this.size--;
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends MapIterator<Consumer<? super Int2IntMap.Entry>> implements ObjectIterator<Int2IntMap.Entry> {
        private MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private EntryIterator() {
            super();
            Int2IntOpenHashMap.this = r5;
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public MapEntry next() {
            MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
            MapEntry mapEntry = new MapEntry(index);
            this.entry = mapEntry;
            action.accept(mapEntry);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$FastEntryIterator.class */
    private final class FastEntryIterator extends MapIterator<Consumer<? super Int2IntMap.Entry>> implements ObjectIterator<Int2IntMap.Entry> {
        private final MapEntry entry;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FastEntryIterator() {
            super();
            Int2IntOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        @Override // java.util.Iterator
        public MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$MapSpliterator.class */
    public abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>> {
        int pos;
        int max;

        /* renamed from: c */
        int f81c;
        boolean mustReturnNull;
        boolean hasSplit;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            Int2IntOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Int2IntOpenHashMap.this.f78n;
            this.f81c = 0;
            this.mustReturnNull = Int2IntOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            Int2IntOpenHashMap.this = r4;
            this.pos = 0;
            this.max = Int2IntOpenHashMap.this.f78n;
            this.f81c = 0;
            this.mustReturnNull = Int2IntOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        public boolean tryAdvance(ConsumerType action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f81c++;
                acceptOnIndex(action, Int2IntOpenHashMap.this.f78n);
                return true;
            }
            int[] key = Int2IntOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    this.f81c++;
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
                this.f81c++;
                acceptOnIndex(action, Int2IntOpenHashMap.this.f78n);
            }
            int[] key = Int2IntOpenHashMap.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    acceptOnIndex(action, this.pos);
                    this.f81c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (!this.hasSplit) {
                return Int2IntOpenHashMap.this.size - this.f81c;
            }
            return Math.min(Int2IntOpenHashMap.this.size - this.f81c, ((long) ((Int2IntOpenHashMap.this.realSize() / Int2IntOpenHashMap.this.f78n) * (this.max - this.pos))) + (this.mustReturnNull ? 1 : 0));
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
            int[] key = Int2IntOpenHashMap.this.key;
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$EntrySpliterator.class */
    public final class EntrySpliterator extends MapSpliterator<Consumer<? super Int2IntMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Int2IntMap.Entry> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;

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
            Int2IntOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Int2IntOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new EntrySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet {
        private MapEntrySet() {
            Int2IntOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.FastEntrySet
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            int curr;
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer) || e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            int v = ((Integer) e.getValue()).intValue();
            if (k == 0) {
                return Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.f78n] == v;
            }
            int[] key = Int2IntOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask;
            int pos = mix;
            int curr2 = key[mix];
            if (curr2 == 0) {
                return false;
            }
            if (k == curr2) {
                return Int2IntOpenHashMap.this.value[pos] == v;
            }
            do {
                int i = (pos + 1) & Int2IntOpenHashMap.this.mask;
                pos = i;
                curr = key[i];
                if (curr == 0) {
                    return false;
                }
            } while (k != curr);
            return Int2IntOpenHashMap.this.value[pos] == v;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer) || e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            int v = ((Integer) e.getValue()).intValue();
            if (k == 0) {
                if (Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.f78n] == v) {
                    Int2IntOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            int[] key = Int2IntOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask;
            int pos = mix;
            int curr = key[mix];
            if (curr == 0) {
                return false;
            }
            if (curr == k) {
                if (Int2IntOpenHashMap.this.value[pos] == v) {
                    Int2IntOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Int2IntOpenHashMap.this.mask;
                pos = i;
                int curr2 = key[i];
                if (curr2 == 0) {
                    return false;
                }
                if (curr2 == k && Int2IntOpenHashMap.this.value[pos] == v) {
                    Int2IntOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
            if (Int2IntOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.f78n], Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.f78n]));
            }
            int pos = Int2IntOpenHashMap.this.f78n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Int2IntOpenHashMap.this.key[pos] != 0) {
                        consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[pos], Int2IntOpenHashMap.this.value[pos]));
                    }
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
            AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
            if (Int2IntOpenHashMap.this.containsNullKey) {
                entry.key = Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.f78n];
                entry.value = Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.f78n];
                consumer.accept(entry);
            }
            int pos = Int2IntOpenHashMap.this.f78n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    if (Int2IntOpenHashMap.this.key[pos] != 0) {
                        entry.key = Int2IntOpenHashMap.this.key[pos];
                        entry.value = Int2IntOpenHashMap.this.value[pos];
                        consumer.accept(entry);
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public Int2IntMap.FastEntrySet int2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends MapIterator<java.util.function.IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((KeyIterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Int2IntOpenHashMap.this = r5;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2IntOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2IntOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$KeySpliterator.class */
    public final class KeySpliterator extends MapSpliterator<java.util.function.IntConsumer, KeySpliterator> implements IntSpliterator {
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
            Int2IntOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Int2IntOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            if (this.hasSplit) {
                return POST_SPLIT_CHARACTERISTICS;
            }
            return 321;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2IntOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new KeySpliterator(pos, max, mustReturnNull, true);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractIntSet {
        private KeySet() {
            Int2IntOpenHashMap.this = r4;
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
            if (Int2IntOpenHashMap.this.containsNullKey) {
                consumer.accept(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.f78n]);
            }
            int pos = Int2IntOpenHashMap.this.f78n;
            while (true) {
                int i = pos;
                pos--;
                if (i != 0) {
                    int k = Int2IntOpenHashMap.this.key[pos];
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
            return Int2IntOpenHashMap.this.size;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return Int2IntOpenHashMap.this.containsKey(k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            int oldSize = Int2IntOpenHashMap.this.size;
            Int2IntOpenHashMap.this.remove(k);
            return Int2IntOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntOpenHashMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends MapIterator<java.util.function.IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((ValueIterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Int2IntOpenHashMap.this = r5;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2IntOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2IntOpenHashMap.this.value[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntOpenHashMap$ValueSpliterator.class */
    public final class ValueSpliterator extends MapSpliterator<java.util.function.IntConsumer, ValueSpliterator> implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 256;

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((ValueSpliterator) intConsumer);
        }

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            return super.tryAdvance((ValueSpliterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator() {
            super();
            Int2IntOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            super(pos, max, mustReturnNull, hasSplit);
            Int2IntOpenHashMap.this = r8;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 256 : 320;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2IntOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
            return new ValueSpliterator(pos, max, mustReturnNull, true);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.1
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
                public void forEach(java.util.function.IntConsumer consumer) {
                    if (Int2IntOpenHashMap.this.containsNullKey) {
                        consumer.accept(Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.f78n]);
                    }
                    int pos = Int2IntOpenHashMap.this.f78n;
                    while (true) {
                        int i = pos;
                        pos--;
                        if (i != 0) {
                            if (Int2IntOpenHashMap.this.key[pos] != 0) {
                                consumer.accept(Int2IntOpenHashMap.this.value[pos]);
                            }
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Int2IntOpenHashMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
                public boolean contains(int v) {
                    return Int2IntOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Int2IntOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f79f));
        if (l >= this.f78n || this.size > HashCommon.maxFill(l, this.f79f)) {
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
        int[] value = this.value;
        int mask = newN - 1;
        int[] newKey = new int[newN + 1];
        int[] newValue = new int[newN + 1];
        int i2 = this.f78n;
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
                newValue[newN] = value[this.f78n];
                this.f78n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.f78n, this.f79f);
                this.key = newKey;
                this.value = newValue;
                return;
            }
        }
    }

    public Int2IntOpenHashMap clone() {
        try {
            Int2IntOpenHashMap c = (Int2IntOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (int[]) this.key.clone();
            c.value = (int[]) this.value.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
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
            h += t ^ this.value[i];
            i++;
        }
        if (this.containsNullKey) {
            h += this.value[this.f78n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int[] key = this.key;
        int[] value = this.value;
        EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                int e = i.nextEntry();
                s.writeInt(key[e]);
                s.writeInt(value[e]);
            } else {
                return;
            }
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        s.defaultReadObject();
        this.f78n = HashCommon.arraySize(this.size, this.f79f);
        this.maxFill = HashCommon.maxFill(this.f78n, this.f79f);
        this.mask = this.f78n - 1;
        int[] key = new int[this.f78n + 1];
        this.key = key;
        int[] value = new int[this.f78n + 1];
        this.value = value;
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int k = s.readInt();
                int v = s.readInt();
                if (k == 0) {
                    pos = this.f78n;
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
                value[pos] = v;
            } else {
                return;
            }
        }
    }

    private void checkTable() {
    }
}
