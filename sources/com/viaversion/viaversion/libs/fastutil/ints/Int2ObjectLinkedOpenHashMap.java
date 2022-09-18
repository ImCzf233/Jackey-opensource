package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap.class */
public class Int2ObjectLinkedOpenHashMap<V> extends AbstractInt2ObjectSortedMap<V> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;

    /* renamed from: n */
    protected transient int f82n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f83f;
    protected transient Int2ObjectSortedMap.FastSortedEntrySet<V> entries;
    protected transient IntSortedSet keys;
    protected transient ObjectCollection<V> values;

    public Int2ObjectLinkedOpenHashMap(int expected, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f83f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f82n = arraySize;
        this.minN = arraySize;
        this.mask = this.f82n - 1;
        this.maxFill = HashCommon.maxFill(this.f82n, f);
        this.key = new int[this.f82n + 1];
        this.value = (V[]) new Object[this.f82n + 1];
        this.link = new long[this.f82n + 1];
    }

    public Int2ObjectLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Int2ObjectLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2ObjectLinkedOpenHashMap(Map<? extends Integer, ? extends V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2ObjectLinkedOpenHashMap(Map<? extends Integer, ? extends V> m) {
        this(m, 0.75f);
    }

    public Int2ObjectLinkedOpenHashMap(Int2ObjectMap<V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2ObjectLinkedOpenHashMap(Int2ObjectMap<V> m) {
        this((Int2ObjectMap) m, 0.75f);
    }

    public Int2ObjectLinkedOpenHashMap(int[] k, V[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put(k[i], (int) v[i]);
        }
    }

    public Int2ObjectLinkedOpenHashMap(int[] k, V[] v) {
        this(k, v, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f83f);
        if (needed > this.f82n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f83f))));
        if (needed > this.f82n) {
            rehash(needed);
        }
    }

    public V removeEntry(int pos) {
        V oldValue = this.value[pos];
        this.value[pos] = null;
        this.size--;
        fixPointers(pos);
        shiftKeys(pos);
        if (this.f82n > this.minN && this.size < this.maxFill / 4 && this.f82n > 16) {
            rehash(this.f82n / 2);
        }
        return oldValue;
    }

    public V removeNullEntry() {
        this.containsNullKey = false;
        V oldValue = this.value[this.f82n];
        this.value[this.f82n] = null;
        this.size--;
        fixPointers(this.f82n);
        if (this.f82n > this.minN && this.size < this.maxFill / 4 && this.f82n > 16) {
            rehash(this.f82n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public void putAll(Map<? extends Integer, ? extends V> m) {
        if (this.f83f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.f82n : -(this.f82n + 1);
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
        if (pos == this.f82n) {
            this.containsNullKey = true;
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            this.last = pos;
            this.first = pos;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i = this.last;
            jArr[i] = jArr[i] ^ ((this.link[this.last] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[pos] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
            this.last = pos;
        }
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f83f));
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
            fixPointers(pos, last);
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

    private V setValue(int pos, V v) {
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    public V removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = this.first;
        this.first = (int) this.link[pos];
        if (0 <= this.first) {
            long[] jArr = this.link;
            int i = this.first;
            jArr[i] = jArr[i] | (-4294967296L);
        }
        this.size--;
        V v = this.value[pos];
        if (pos == this.f82n) {
            this.containsNullKey = false;
            this.value[this.f82n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f82n > this.minN && this.size < this.maxFill / 4 && this.f82n > 16) {
            rehash(this.f82n / 2);
        }
        return v;
    }

    public V removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = this.last;
        this.last = (int) (this.link[pos] >>> 32);
        if (0 <= this.last) {
            long[] jArr = this.link;
            int i = this.last;
            jArr[i] = jArr[i] | JSType.MAX_UINT;
        }
        this.size--;
        V v = this.value[pos];
        if (pos == this.f82n) {
            this.containsNullKey = false;
            this.value[this.f82n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f82n > this.minN && this.size < this.maxFill / 4 && this.f82n > 16) {
            rehash(this.f82n / 2);
        }
        return v;
    }

    private void moveIndexToFirst(int i) {
        if (this.size == 1 || this.first == i) {
            return;
        }
        if (this.last == i) {
            this.last = (int) (this.link[i] >>> 32);
            long[] jArr = this.link;
            int i2 = this.last;
            jArr[i2] = jArr[i2] | JSType.MAX_UINT;
        } else {
            long linki = this.link[i];
            int prev = (int) (linki >>> 32);
            int next = (int) linki;
            long[] jArr2 = this.link;
            jArr2[prev] = jArr2[prev] ^ ((this.link[prev] ^ (linki & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr3 = this.link;
            jArr3[next] = jArr3[next] ^ ((this.link[next] ^ (linki & (-4294967296L))) & (-4294967296L));
        }
        long[] jArr4 = this.link;
        int i3 = this.first;
        jArr4[i3] = jArr4[i3] ^ ((this.link[this.first] ^ ((i & JSType.MAX_UINT) << 32)) & (-4294967296L));
        this.link[i] = (-4294967296L) | (this.first & JSType.MAX_UINT);
        this.first = i;
    }

    private void moveIndexToLast(int i) {
        if (this.size == 1 || this.last == i) {
            return;
        }
        if (this.first == i) {
            this.first = (int) this.link[i];
            long[] jArr = this.link;
            int i2 = this.first;
            jArr[i2] = jArr[i2] | (-4294967296L);
        } else {
            long linki = this.link[i];
            int prev = (int) (linki >>> 32);
            int next = (int) linki;
            long[] jArr2 = this.link;
            jArr2[prev] = jArr2[prev] ^ ((this.link[prev] ^ (linki & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr3 = this.link;
            jArr3[next] = jArr3[next] ^ ((this.link[next] ^ (linki & (-4294967296L))) & (-4294967296L));
        }
        long[] jArr4 = this.link;
        int i3 = this.last;
        jArr4[i3] = jArr4[i3] ^ ((this.link[this.last] ^ (i & JSType.MAX_UINT)) & JSType.MAX_UINT);
        this.link[i] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
        this.last = i;
    }

    public V getAndMoveToFirst(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f82n);
                return this.value[this.f82n];
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
            moveIndexToFirst(pos);
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
        moveIndexToFirst(pos);
        return this.value[pos];
    }

    public V getAndMoveToLast(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f82n);
                return this.value[this.f82n];
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
            moveIndexToLast(pos);
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
        moveIndexToLast(pos);
        return this.value[pos];
    }

    public V putAndMoveToFirst(int k, V v) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f82n);
                return setValue(this.f82n, v);
            }
            this.containsNullKey = true;
            pos = this.f82n;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    moveIndexToFirst(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
                moveIndexToFirst(pos);
                return setValue(pos, v);
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            int i2 = pos;
            this.last = i2;
            this.first = i2;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i3 = this.first;
            jArr[i3] = jArr[i3] ^ ((this.link[this.first] ^ ((pos & JSType.MAX_UINT) << 32)) & (-4294967296L));
            this.link[pos] = (-4294967296L) | (this.first & JSType.MAX_UINT);
            this.first = pos;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size, this.f83f));
        }
        return this.defRetValue;
    }

    public V putAndMoveToLast(int k, V v) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f82n);
                return setValue(this.f82n, v);
            }
            this.containsNullKey = true;
            pos = this.f82n;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    moveIndexToLast(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
                moveIndexToLast(pos);
                return setValue(pos, v);
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            int i2 = pos;
            this.last = i2;
            this.first = i2;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i3 = this.last;
            jArr[i3] = jArr[i3] ^ ((this.link[this.last] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[pos] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
            this.last = pos;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size, this.f83f));
        }
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V get(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f82n] : this.defRetValue;
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
        if (this.containsNullKey && Objects.equals(value[this.f82n], v)) {
            return true;
        }
        int i = this.f82n;
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
            return this.containsNullKey ? this.value[this.f82n] : defaultValue;
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
            if (this.containsNullKey && Objects.equals(v, this.value[this.f82n])) {
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
        this.last = -1;
        this.first = -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$MapEntry.class */
    public final class MapEntry implements Int2ObjectMap.Entry<V>, Map.Entry<Integer, V>, IntObjectPair<V> {
        int index;

        MapEntry(int index) {
            Int2ObjectLinkedOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Int2ObjectLinkedOpenHashMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.Entry
        public int getIntKey() {
            return Int2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntObjectPair
        public int leftInt() {
            return Int2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Int2ObjectLinkedOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public V right() {
            return Int2ObjectLinkedOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V oldValue = Int2ObjectLinkedOpenHashMap.this.value[this.index];
            Int2ObjectLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public IntObjectPair<V> right(V v) {
            Int2ObjectLinkedOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getKey() {
            return Integer.valueOf(Int2ObjectLinkedOpenHashMap.this.key[this.index]);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<Integer, V> e = (Map.Entry) o;
            return Int2ObjectLinkedOpenHashMap.this.key[this.index] == e.getKey().intValue() && Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Int2ObjectLinkedOpenHashMap.this.key[this.index] ^ (Int2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Int2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Int2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Int2ObjectLinkedOpenHashMap.this.value[this.index];
        }
    }

    protected void fixPointers(int i) {
        if (this.size == 0) {
            this.last = -1;
            this.first = -1;
        } else if (this.first == i) {
            this.first = (int) this.link[i];
            if (0 <= this.first) {
                long[] jArr = this.link;
                int i2 = this.first;
                jArr[i2] = jArr[i2] | (-4294967296L);
            }
        } else if (this.last == i) {
            this.last = (int) (this.link[i] >>> 32);
            if (0 <= this.last) {
                long[] jArr2 = this.link;
                int i3 = this.last;
                jArr2[i3] = jArr2[i3] | JSType.MAX_UINT;
            }
        } else {
            long linki = this.link[i];
            int prev = (int) (linki >>> 32);
            int next = (int) linki;
            long[] jArr3 = this.link;
            jArr3[prev] = jArr3[prev] ^ ((this.link[prev] ^ (linki & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr4 = this.link;
            jArr4[next] = jArr4[next] ^ ((this.link[next] ^ (linki & (-4294967296L))) & (-4294967296L));
        }
    }

    protected void fixPointers(int s, int d) {
        if (this.size == 1) {
            this.last = d;
            this.first = d;
            this.link[d] = -1;
        } else if (this.first == s) {
            this.first = d;
            long[] jArr = this.link;
            int i = (int) this.link[s];
            jArr[i] = jArr[i] ^ ((this.link[(int) this.link[s]] ^ ((d & JSType.MAX_UINT) << 32)) & (-4294967296L));
            this.link[d] = this.link[s];
        } else if (this.last == s) {
            this.last = d;
            long[] jArr2 = this.link;
            int i2 = (int) (this.link[s] >>> 32);
            jArr2[i2] = jArr2[i2] ^ ((this.link[(int) (this.link[s] >>> 32)] ^ (d & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[d] = this.link[s];
        } else {
            long links = this.link[s];
            int prev = (int) (links >>> 32);
            int next = (int) links;
            long[] jArr3 = this.link;
            jArr3[prev] = jArr3[prev] ^ ((this.link[prev] ^ (d & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr4 = this.link;
            jArr4[next] = jArr4[next] ^ ((this.link[next] ^ ((d & JSType.MAX_UINT) << 32)) & (-4294967296L));
            this.link[d] = links;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
    public int firstIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
    public int lastIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
    public Int2ObjectSortedMap<V> tailMap(int from) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
    public Int2ObjectSortedMap<V> headMap(int to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
    public Int2ObjectSortedMap<V> subMap(int from, int to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
    public IntComparator comparator() {
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int prev;
        int next;
        int curr;
        int index;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        protected MapIterator() {
            Int2ObjectLinkedOpenHashMap.this = r4;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = r4.first;
            this.index = 0;
        }

        private MapIterator(int from) {
            Int2ObjectLinkedOpenHashMap.this = r6;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (from == 0) {
                if (r6.containsNullKey) {
                    this.next = (int) r6.link[r6.f82n];
                    this.prev = r6.f82n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            } else if (r6.key[r6.last] == from) {
                this.prev = r6.last;
                this.index = r6.size;
            } else {
                int mix = HashCommon.mix(from);
                int i = r6.mask;
                while (true) {
                    int pos = mix & i;
                    if (r6.key[pos] != 0) {
                        if (r6.key[pos] == from) {
                            this.next = (int) r6.link[pos];
                            this.prev = pos;
                            return;
                        }
                        mix = pos + 1;
                        i = r6.mask;
                    } else {
                        throw new NoSuchElementException("The key " + from + " does not belong to this map.");
                    }
                }
            }
        }

        public boolean hasNext() {
            return this.next != -1;
        }

        public boolean hasPrevious() {
            return this.prev != -1;
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
            } else if (this.next == -1) {
                this.index = Int2ObjectLinkedOpenHashMap.this.size;
            } else {
                int pos = Int2ObjectLinkedOpenHashMap.this.first;
                this.index = 1;
                while (pos != this.prev) {
                    pos = (int) Int2ObjectLinkedOpenHashMap.this.link[pos];
                    this.index++;
                }
            }
        }

        public int nextIndex() {
            ensureIndexKnown();
            return this.index;
        }

        public int previousIndex() {
            ensureIndexKnown();
            return this.index - 1;
        }

        public int nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int) Int2ObjectLinkedOpenHashMap.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                this.index++;
            }
            return this.curr;
        }

        public int previousEntry() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int) (Int2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                this.index--;
            }
            return this.curr;
        }

        public void forEachRemaining(ConsumerType action) {
            while (hasNext()) {
                this.curr = this.next;
                this.next = (int) Int2ObjectLinkedOpenHashMap.this.link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    this.index++;
                }
                acceptOnIndex(action, this.curr);
            }
        }

        public void remove() {
            int curr;
            ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                this.index--;
                this.prev = (int) (Int2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int) Int2ObjectLinkedOpenHashMap.this.link[this.curr];
            }
            Int2ObjectLinkedOpenHashMap.this.size--;
            if (this.prev == -1) {
                Int2ObjectLinkedOpenHashMap.this.first = this.next;
            } else {
                long[] jArr = Int2ObjectLinkedOpenHashMap.this.link;
                int i = this.prev;
                jArr[i] = jArr[i] ^ ((Int2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (this.next & JSType.MAX_UINT)) & JSType.MAX_UINT);
            }
            if (this.next == -1) {
                Int2ObjectLinkedOpenHashMap.this.last = this.prev;
            } else {
                long[] jArr2 = Int2ObjectLinkedOpenHashMap.this.link;
                int i2 = this.next;
                jArr2[i2] = jArr2[i2] ^ ((Int2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((this.prev & JSType.MAX_UINT) << 32)) & (-4294967296L));
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos == Int2ObjectLinkedOpenHashMap.this.f82n) {
                Int2ObjectLinkedOpenHashMap.this.containsNullKey = false;
                Int2ObjectLinkedOpenHashMap.this.value[Int2ObjectLinkedOpenHashMap.this.f82n] = null;
                return;
            }
            int[] key = Int2ObjectLinkedOpenHashMap.this.key;
            while (true) {
                int last = pos;
                int i3 = last + 1;
                int i4 = Int2ObjectLinkedOpenHashMap.this.mask;
                while (true) {
                    pos = i3 & i4;
                    curr = key[pos];
                    if (curr == 0) {
                        key[last] = 0;
                        Int2ObjectLinkedOpenHashMap.this.value[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr) & Int2ObjectLinkedOpenHashMap.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i3 = pos + 1;
                        i4 = Int2ObjectLinkedOpenHashMap.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i3 = pos + 1;
                        i4 = Int2ObjectLinkedOpenHashMap.this.mask;
                    }
                }
                key[last] = curr;
                Int2ObjectLinkedOpenHashMap.this.value[last] = Int2ObjectLinkedOpenHashMap.this.value[pos];
                if (this.next == pos) {
                    this.next = last;
                }
                if (this.prev == pos) {
                    this.prev = last;
                }
                Int2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
            }
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

        public int back(int n) {
            int i = n;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0 || !hasPrevious()) {
                    break;
                }
                previousEntry();
            }
            return (n - i) - 1;
        }

        public void set(Int2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }

        public void add(Int2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends Int2ObjectLinkedOpenHashMap<V>.MapIterator<Consumer<? super Int2ObjectMap.Entry<V>>> implements ObjectListIterator<Int2ObjectMap.Entry<V>> {
        private Int2ObjectLinkedOpenHashMap<V>.MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Int2ObjectMap.Entry) ((Int2ObjectMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Int2ObjectMap.Entry) ((Int2ObjectMap.Entry) obj));
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator() {
            super();
            Int2ObjectLinkedOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator(int from) {
            super(from);
            Int2ObjectLinkedOpenHashMap.this = r6;
        }

        final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public Int2ObjectLinkedOpenHashMap<V>.MapEntry next() {
            Int2ObjectLinkedOpenHashMap<V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public Int2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
            Int2ObjectLinkedOpenHashMap<V>.MapEntry mapEntry = new MapEntry(previousEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap.MapIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$FastEntryIterator.class */
    public final class FastEntryIterator extends Int2ObjectLinkedOpenHashMap<V>.MapIterator<Consumer<? super Int2ObjectMap.Entry<V>>> implements ObjectListIterator<Int2ObjectMap.Entry<V>> {
        final Int2ObjectLinkedOpenHashMap<V>.MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Int2ObjectMap.Entry) ((Int2ObjectMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Int2ObjectMap.Entry) ((Int2ObjectMap.Entry) obj));
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator() {
            super();
            Int2ObjectLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator(int from) {
            super(from);
            Int2ObjectLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public Int2ObjectLinkedOpenHashMap<V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public Int2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
            this.entry.index = previousEntry();
            return this.entry;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSortedSet<Int2ObjectMap.Entry<V>> implements Int2ObjectSortedMap.FastSortedEntrySet<V> {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;

        private MapEntrySet() {
            Int2ObjectLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public /* bridge */ /* synthetic */ ObjectBidirectionalIterator iterator(Object obj) {
            return iterator((Int2ObjectMap.Entry) ((Int2ObjectMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2ObjectLinkedOpenHashMap.this), 81);
        }

        @Override // java.util.SortedSet
        public Comparator<? super Int2ObjectMap.Entry<V>> comparator() {
            return null;
        }

        public ObjectSortedSet<Int2ObjectMap.Entry<V>> subSet(Int2ObjectMap.Entry<V> fromElement, Int2ObjectMap.Entry<V> toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Int2ObjectMap.Entry<V>> headSet(Int2ObjectMap.Entry<V> toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Int2ObjectMap.Entry<V>> tailSet(Int2ObjectMap.Entry<V> fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.SortedSet
        public Int2ObjectMap.Entry<V> first() {
            if (Int2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Int2ObjectLinkedOpenHashMap.this.first);
        }

        @Override // java.util.SortedSet
        public Int2ObjectMap.Entry<V> last() {
            if (Int2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Int2ObjectLinkedOpenHashMap.this.last);
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
                return Int2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[Int2ObjectLinkedOpenHashMap.this.f82n], value);
            }
            int[] key = Int2ObjectLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2ObjectLinkedOpenHashMap.this.mask;
            int pos = mix;
            int curr2 = key[mix];
            if (curr2 == 0) {
                return false;
            }
            if (k == curr2) {
                return Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[pos], value);
            }
            do {
                int i = (pos + 1) & Int2ObjectLinkedOpenHashMap.this.mask;
                pos = i;
                curr = key[i];
                if (curr == 0) {
                    return false;
                }
            } while (k != curr);
            return Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[pos], value);
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
                if (Int2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[Int2ObjectLinkedOpenHashMap.this.f82n], value)) {
                    Int2ObjectLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            int[] key = Int2ObjectLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2ObjectLinkedOpenHashMap.this.mask;
            int pos = mix;
            int curr = key[mix];
            if (curr == 0) {
                return false;
            }
            if (curr == k) {
                if (Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[pos], value)) {
                    Int2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Int2ObjectLinkedOpenHashMap.this.mask;
                pos = i;
                int curr2 = key[i];
                if (curr2 == 0) {
                    return false;
                }
                if (curr2 == k && Objects.equals(Int2ObjectLinkedOpenHashMap.this.value[pos], value)) {
                    Int2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectLinkedOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectLinkedOpenHashMap.this.clear();
        }

        public ObjectListIterator<Int2ObjectMap.Entry<V>> iterator(Int2ObjectMap.Entry<V> from) {
            return new EntryIterator(from.getIntKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap.FastSortedEntrySet, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.FastEntrySet
        public ObjectListIterator<Int2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap.FastSortedEntrySet
        public ObjectListIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap.Entry<V> from) {
            return new FastEntryIterator(from.getIntKey());
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
            int i = Int2ObjectLinkedOpenHashMap.this.size;
            int next = Int2ObjectLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Int2ObjectLinkedOpenHashMap.this.link[curr];
                    consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectLinkedOpenHashMap.this.key[curr], Int2ObjectLinkedOpenHashMap.this.value[curr]));
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
            AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();
            int i = Int2ObjectLinkedOpenHashMap.this.size;
            int next = Int2ObjectLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Int2ObjectLinkedOpenHashMap.this.link[curr];
                    entry.key = Int2ObjectLinkedOpenHashMap.this.key[curr];
                    entry.value = Int2ObjectLinkedOpenHashMap.this.value[curr];
                    consumer.accept(entry);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public Int2ObjectSortedMap.FastSortedEntrySet<V> int2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends Int2ObjectLinkedOpenHashMap<V>.MapIterator<java.util.function.IntConsumer> implements IntListIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((KeyIterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator(int k) {
            super(k);
            Int2ObjectLinkedOpenHashMap.this = r6;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return Int2ObjectLinkedOpenHashMap.this.key[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Int2ObjectLinkedOpenHashMap.this = r4;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2ObjectLinkedOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2ObjectLinkedOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractIntSortedSet {
        private static final int SPLITERATOR_CHARACTERISTICS = 337;

        private KeySet() {
            Int2ObjectLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntListIterator iterator(int from) {
            return new KeyIterator(from);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSortedSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return new KeyIterator();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2ObjectLinkedOpenHashMap.this), 337);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer consumer) {
            int i = Int2ObjectLinkedOpenHashMap.this.size;
            int next = Int2ObjectLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Int2ObjectLinkedOpenHashMap.this.link[curr];
                    consumer.accept(Int2ObjectLinkedOpenHashMap.this.key[curr]);
                } else {
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectLinkedOpenHashMap.this.size;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return Int2ObjectLinkedOpenHashMap.this.containsKey(k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            int oldSize = Int2ObjectLinkedOpenHashMap.this.size;
            Int2ObjectLinkedOpenHashMap.this.remove(k);
            return Int2ObjectLinkedOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectLinkedOpenHashMap.this.clear();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int firstInt() {
            if (Int2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Int2ObjectLinkedOpenHashMap.this.key[Int2ObjectLinkedOpenHashMap.this.first];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int lastInt() {
            if (Int2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Int2ObjectLinkedOpenHashMap.this.key[Int2ObjectLinkedOpenHashMap.this.last];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        public IntComparator comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet tailSet(int from) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet headSet(int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet subSet(int from, int to) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectSortedMap, com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public IntSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectLinkedOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends Int2ObjectLinkedOpenHashMap<V>.MapIterator<Consumer<? super V>> implements ObjectListIterator<V> {
        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((ValueIterator) consumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public V previous() {
            return Int2ObjectLinkedOpenHashMap.this.value[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Int2ObjectLinkedOpenHashMap.this = r4;
        }

        final void acceptOnIndex(Consumer<? super V> action, int index) {
            action.accept((Object) Int2ObjectLinkedOpenHashMap.this.value[index]);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public V next() {
            return Int2ObjectLinkedOpenHashMap.this.value[nextEntry()];
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectSortedMap, com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap.1
                private static final int SPLITERATOR_CHARACTERISTICS = 80;

                @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
                public ObjectIterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
                public ObjectSpliterator<V> spliterator() {
                    return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2ObjectLinkedOpenHashMap.this), 80);
                }

                @Override // java.lang.Iterable
                public void forEach(Consumer<? super V> consumer) {
                    int i = Int2ObjectLinkedOpenHashMap.this.size;
                    int next = Int2ObjectLinkedOpenHashMap.this.first;
                    while (true) {
                        int i2 = i;
                        i--;
                        if (i2 != 0) {
                            int curr = next;
                            next = (int) Int2ObjectLinkedOpenHashMap.this.link[curr];
                            consumer.accept((Object) Int2ObjectLinkedOpenHashMap.this.value[curr]);
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Int2ObjectLinkedOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object v) {
                    return Int2ObjectLinkedOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Int2ObjectLinkedOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f83f));
        if (l >= this.f82n || this.size > HashCommon.maxFill(l, this.f83f)) {
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
        int pos;
        int[] key = this.key;
        V[] value = this.value;
        int mask = newN - 1;
        int[] newKey = new int[newN + 1];
        V[] newValue = (V[]) new Object[newN + 1];
        int i = this.first;
        int prev = -1;
        int newPrev = -1;
        long[] link = this.link;
        long[] newLink = new long[newN + 1];
        this.first = -1;
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0) {
                break;
            }
            if (key[i] == 0) {
                pos = newN;
            } else {
                int mix = HashCommon.mix(key[i]);
                while (true) {
                    pos = mix & mask;
                    if (newKey[pos] == 0) {
                        break;
                    }
                    mix = pos + 1;
                }
            }
            newKey[pos] = key[i];
            newValue[pos] = value[i];
            if (prev != -1) {
                int i3 = newPrev;
                newLink[i3] = newLink[i3] ^ ((newLink[newPrev] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
                int i4 = pos;
                newLink[i4] = newLink[i4] ^ ((newLink[pos] ^ ((newPrev & JSType.MAX_UINT) << 32)) & (-4294967296L));
                newPrev = pos;
            } else {
                int i5 = pos;
                this.first = i5;
                newPrev = i5;
                newLink[pos] = -1;
            }
            int t = i;
            i = (int) link[i];
            prev = t;
        }
        this.link = newLink;
        this.last = newPrev;
        if (newPrev != -1) {
            int i6 = newPrev;
            newLink[i6] = newLink[i6] | JSType.MAX_UINT;
        }
        this.f82n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.f82n, this.f83f);
        this.key = newKey;
        this.value = newValue;
    }

    public Int2ObjectLinkedOpenHashMap<V> clone() {
        try {
            Int2ObjectLinkedOpenHashMap<V> c = (Int2ObjectLinkedOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (int[]) this.key.clone();
            c.value = (V[]) ((Object[]) this.value.clone());
            c.link = (long[]) this.link.clone();
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
            h += this.value[this.f82n] == null ? 0 : this.value[this.f82n].hashCode();
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int[] key = this.key;
        V[] value = this.value;
        Int2ObjectLinkedOpenHashMap<V>.EntryIterator i = new EntryIterator();
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
        this.f82n = HashCommon.arraySize(this.size, this.f83f);
        this.maxFill = HashCommon.maxFill(this.f82n, this.f83f);
        this.mask = this.f82n - 1;
        int[] key = new int[this.f82n + 1];
        this.key = key;
        V[] value = (V[]) new Object[this.f82n + 1];
        this.value = value;
        long[] link = new long[this.f82n + 1];
        this.link = link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 == 0) {
                break;
            }
            int k = s.readInt();
            Object readObject = s.readObject();
            if (k == 0) {
                pos = this.f82n;
                this.containsNullKey = true;
            } else {
                int mix = HashCommon.mix(k);
                int i3 = this.mask;
                while (true) {
                    pos = mix & i3;
                    if (key[pos] == 0) {
                        break;
                    }
                    mix = pos + 1;
                    i3 = this.mask;
                }
            }
            key[pos] = k;
            value[pos] = readObject;
            if (this.first != -1) {
                int i4 = prev;
                link[i4] = link[i4] ^ ((link[prev] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
                int i5 = pos;
                link[i5] = link[i5] ^ ((link[pos] ^ ((prev & JSType.MAX_UINT) << 32)) & (-4294967296L));
                prev = pos;
            } else {
                int i6 = pos;
                this.first = i6;
                prev = i6;
                int i7 = pos;
                link[i7] = link[i7] | (-4294967296L);
            }
        }
        this.last = prev;
        if (prev != -1) {
            int i8 = prev;
            link[i8] = link[i8] | JSType.MAX_UINT;
        }
    }

    private void checkTable() {
    }
}
