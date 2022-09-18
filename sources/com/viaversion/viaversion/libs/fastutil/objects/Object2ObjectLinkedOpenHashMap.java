package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap.class */
public class Object2ObjectLinkedOpenHashMap<K, V> extends AbstractObject2ObjectSortedMap<K, V> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;

    /* renamed from: n */
    protected transient int f138n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f139f;
    protected transient Object2ObjectSortedMap.FastSortedEntrySet<K, V> entries;
    protected transient ObjectSortedSet<K> keys;
    protected transient ObjectCollection<V> values;

    public Object2ObjectLinkedOpenHashMap(int expected, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f139f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f138n = arraySize;
        this.minN = arraySize;
        this.mask = this.f138n - 1;
        this.maxFill = HashCommon.maxFill(this.f138n, f);
        this.key = (K[]) new Object[this.f138n + 1];
        this.value = (V[]) new Object[this.f138n + 1];
        this.link = new long[this.f138n + 1];
    }

    public Object2ObjectLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Object2ObjectLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
        this(m, 0.75f);
    }

    public Object2ObjectLinkedOpenHashMap(Object2ObjectMap<K, V> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2ObjectLinkedOpenHashMap(Object2ObjectMap<K, V> m) {
        this((Object2ObjectMap) m, 0.75f);
    }

    public Object2ObjectLinkedOpenHashMap(K[] k, V[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put(k[i], v[i]);
        }
    }

    public Object2ObjectLinkedOpenHashMap(K[] k, V[] v) {
        this(k, v, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f139f);
        if (needed > this.f138n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f139f))));
        if (needed > this.f138n) {
            rehash(needed);
        }
    }

    public V removeEntry(int pos) {
        V oldValue = this.value[pos];
        this.value[pos] = null;
        this.size--;
        fixPointers(pos);
        shiftKeys(pos);
        if (this.f138n > this.minN && this.size < this.maxFill / 4 && this.f138n > 16) {
            rehash(this.f138n / 2);
        }
        return oldValue;
    }

    public V removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.f138n] = null;
        V oldValue = this.value[this.f138n];
        this.value[this.f138n] = null;
        this.size--;
        fixPointers(this.f138n);
        if (this.f138n > this.minN && this.size < this.maxFill / 4 && this.f138n > 16) {
            rehash(this.f138n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> m) {
        if (this.f139f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(K k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.f138n : -(this.f138n + 1);
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
        if (pos == this.f138n) {
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
            rehash(HashCommon.arraySize(this.size + 1, this.f139f));
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
            fixPointers(pos, last);
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
        if (pos == this.f138n) {
            this.containsNullKey = false;
            this.key[this.f138n] = null;
            this.value[this.f138n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f138n > this.minN && this.size < this.maxFill / 4 && this.f138n > 16) {
            rehash(this.f138n / 2);
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
        if (pos == this.f138n) {
            this.containsNullKey = false;
            this.key[this.f138n] = null;
            this.value[this.f138n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f138n > this.minN && this.size < this.maxFill / 4 && this.f138n > 16) {
            rehash(this.f138n / 2);
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

    public V getAndMoveToFirst(K k) {
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f138n);
                return this.value[this.f138n];
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
            moveIndexToFirst(pos);
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
        moveIndexToFirst(pos);
        return this.value[pos];
    }

    public V getAndMoveToLast(K k) {
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f138n);
                return this.value[this.f138n];
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
            moveIndexToLast(pos);
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
        moveIndexToLast(pos);
        return this.value[pos];
    }

    public V putAndMoveToFirst(K k, V v) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f138n);
                return setValue(this.f138n, v);
            }
            this.containsNullKey = true;
            pos = this.f138n;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    moveIndexToFirst(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
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
            rehash(HashCommon.arraySize(this.size, this.f139f));
        }
        return this.defRetValue;
    }

    public V putAndMoveToLast(K k, V v) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f138n);
                return setValue(this.f138n, v);
            }
            this.containsNullKey = true;
            pos = this.f138n;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    moveIndexToLast(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
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
            rehash(HashCommon.arraySize(this.size, this.f139f));
        }
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V get(Object k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f138n] : this.defRetValue;
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
        if (this.containsNullKey && Objects.equals(value[this.f138n], v)) {
            return true;
        }
        int i = this.f138n;
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
            return this.containsNullKey ? this.value[this.f138n] : defaultValue;
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
            if (this.containsNullKey && Objects.equals(v, this.value[this.f138n])) {
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
        this.last = -1;
        this.first = -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$MapEntry.class */
    public final class MapEntry implements Object2ObjectMap.Entry<K, V>, Map.Entry<K, V>, Pair<K, V> {
        int index;

        MapEntry(int index) {
            Object2ObjectLinkedOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Object2ObjectLinkedOpenHashMap.this = this$0;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Object2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public K left() {
            return Object2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Object2ObjectLinkedOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public V right() {
            return Object2ObjectLinkedOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V oldValue = Object2ObjectLinkedOpenHashMap.this.value[this.index];
            Object2ObjectLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public Pair<K, V> right(V v) {
            Object2ObjectLinkedOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, V> e = (Map.Entry) o;
            return Objects.equals(Object2ObjectLinkedOpenHashMap.this.key[this.index], e.getKey()) && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (Object2ObjectLinkedOpenHashMap.this.key[this.index] == null ? 0 : Object2ObjectLinkedOpenHashMap.this.key[this.index].hashCode()) ^ (Object2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Object2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Object2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Object2ObjectLinkedOpenHashMap.this.value[this.index];
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

    @Override // java.util.SortedMap
    public K firstKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // java.util.SortedMap
    public K lastKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
    public Object2ObjectSortedMap<K, V> tailMap(K from) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
    public Object2ObjectSortedMap<K, V> headMap(K to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
    public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
    public Comparator<? super K> comparator() {
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int prev;
        int next;
        int curr;
        int index;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        protected MapIterator() {
            Object2ObjectLinkedOpenHashMap.this = r4;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = r4.first;
            this.index = 0;
        }

        private MapIterator(K from) {
            Object2ObjectLinkedOpenHashMap.this = r6;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (from == null) {
                if (r6.containsNullKey) {
                    this.next = (int) r6.link[r6.f138n];
                    this.prev = r6.f138n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            } else if (Objects.equals(r6.key[r6.last], from)) {
                this.prev = r6.last;
                this.index = r6.size;
            } else {
                int mix = HashCommon.mix(from.hashCode());
                int i = r6.mask;
                while (true) {
                    int pos = mix & i;
                    if (r6.key[pos] != null) {
                        if (r6.key[pos].equals(from)) {
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
                this.index = Object2ObjectLinkedOpenHashMap.this.size;
            } else {
                int pos = Object2ObjectLinkedOpenHashMap.this.first;
                this.index = 1;
                while (pos != this.prev) {
                    pos = (int) Object2ObjectLinkedOpenHashMap.this.link[pos];
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
            this.next = (int) Object2ObjectLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int) (Object2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                this.index--;
            }
            return this.curr;
        }

        public void forEachRemaining(ConsumerType action) {
            while (hasNext()) {
                this.curr = this.next;
                this.next = (int) Object2ObjectLinkedOpenHashMap.this.link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    this.index++;
                }
                acceptOnIndex(action, this.curr);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:38:0x018f, code lost:
            r0[r0] = r0;
            com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.this.value[r0] = com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.this.value[r14];
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x01ac, code lost:
            if (r11.next != r14) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x01af, code lost:
            r11.next = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x01b9, code lost:
            if (r11.prev != r14) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x01bc, code lost:
            r11.prev = r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void remove() {
            /*
                Method dump skipped, instructions count: 462
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.MapIterator.remove():void");
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

        public void set(Object2ObjectMap.Entry<K, V> ok) {
            throw new UnsupportedOperationException();
        }

        public void add(Object2ObjectMap.Entry<K, V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>> implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
        private Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Object2ObjectMap.Entry) ((Object2ObjectMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Object2ObjectMap.Entry) ((Object2ObjectMap.Entry) obj));
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator() {
            super();
            Object2ObjectLinkedOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator(K from) {
            super(from);
            Object2ObjectLinkedOpenHashMap.this = r6;
        }

        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
            Object2ObjectLinkedOpenHashMap<K, V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
            Object2ObjectLinkedOpenHashMap<K, V>.MapEntry mapEntry = new MapEntry(previousEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.MapIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$FastEntryIterator.class */
    public final class FastEntryIterator extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>> implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
        final Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Object2ObjectMap.Entry) ((Object2ObjectMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Object2ObjectMap.Entry) ((Object2ObjectMap.Entry) obj));
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator() {
            super();
            Object2ObjectLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator(K from) {
            super(from);
            Object2ObjectLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
            this.entry.index = previousEntry();
            return this.entry;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSortedSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectSortedMap.FastSortedEntrySet<K, V> {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;

        private MapEntrySet() {
            Object2ObjectLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public /* bridge */ /* synthetic */ ObjectBidirectionalIterator iterator(Object obj) {
            return iterator((Object2ObjectMap.Entry) ((Object2ObjectMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ObjectLinkedOpenHashMap.this), 81);
        }

        @Override // java.util.SortedSet
        public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
            return null;
        }

        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> fromElement, Object2ObjectMap.Entry<K, V> toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.SortedSet
        public Object2ObjectMap.Entry<K, V> first() {
            if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Object2ObjectLinkedOpenHashMap.this.first);
        }

        @Override // java.util.SortedSet
        public Object2ObjectMap.Entry<K, V> last() {
            if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Object2ObjectLinkedOpenHashMap.this.last);
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
                return Object2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.f138n], value);
            }
            K[] key2 = Object2ObjectLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask;
            int pos = mix;
            K curr2 = key2[mix];
            if (curr2 == null) {
                return false;
            }
            if (key.equals(curr2)) {
                return Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], value);
            }
            do {
                int i = (pos + 1) & Object2ObjectLinkedOpenHashMap.this.mask;
                pos = i;
                curr = key2[i];
                if (curr == null) {
                    return false;
                }
            } while (!key.equals(curr));
            return Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], value);
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
                if (Object2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.f138n], value)) {
                    Object2ObjectLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            K[] key2 = Object2ObjectLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask;
            int pos = mix;
            K curr = key2[mix];
            if (curr == null) {
                return false;
            }
            if (curr.equals(key)) {
                if (Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], value)) {
                    Object2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Object2ObjectLinkedOpenHashMap.this.mask;
                pos = i;
                K curr2 = key2[i];
                if (curr2 == null) {
                    return false;
                }
                if (curr2.equals(key) && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], value)) {
                    Object2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2ObjectLinkedOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2ObjectLinkedOpenHashMap.this.clear();
        }

        public ObjectListIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
            return new EntryIterator(from.getKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap.FastSortedEntrySet, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap.FastEntrySet
        public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap.FastSortedEntrySet
        public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap.Entry<K, V> from) {
            return new FastEntryIterator(from.getKey());
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            int i = Object2ObjectLinkedOpenHashMap.this.size;
            int next = Object2ObjectLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Object2ObjectLinkedOpenHashMap.this.link[curr];
                    consumer.accept(new AbstractObject2ObjectMap.BasicEntry(Object2ObjectLinkedOpenHashMap.this.key[curr], Object2ObjectLinkedOpenHashMap.this.value[curr]));
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
            int i = Object2ObjectLinkedOpenHashMap.this.size;
            int next = Object2ObjectLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Object2ObjectLinkedOpenHashMap.this.link[curr];
                    entry.key = Object2ObjectLinkedOpenHashMap.this.key[curr];
                    entry.value = Object2ObjectLinkedOpenHashMap.this.value[curr];
                    consumer.accept(entry);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
    public Object2ObjectSortedMap.FastSortedEntrySet<K, V> object2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super K>> implements ObjectListIterator<K> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((KeyIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator(K k) {
            super(k);
            Object2ObjectLinkedOpenHashMap.this = r6;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            return Object2ObjectLinkedOpenHashMap.this.key[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Object2ObjectLinkedOpenHashMap.this = r4;
        }

        final void acceptOnIndex(Consumer<? super K> action, int index) {
            action.accept((Object) Object2ObjectLinkedOpenHashMap.this.key[index]);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            return Object2ObjectLinkedOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractObjectSortedSet<K> {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;

        private KeySet() {
            Object2ObjectLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public ObjectListIterator<K> iterator(K from) {
            return new KeyIterator(from);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectListIterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ObjectLinkedOpenHashMap.this), 81);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            int i = Object2ObjectLinkedOpenHashMap.this.size;
            int next = Object2ObjectLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Object2ObjectLinkedOpenHashMap.this.link[curr];
                    consumer.accept((Object) Object2ObjectLinkedOpenHashMap.this.key[curr]);
                } else {
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2ObjectLinkedOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Object2ObjectLinkedOpenHashMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            int oldSize = Object2ObjectLinkedOpenHashMap.this.size;
            Object2ObjectLinkedOpenHashMap.this.remove(k);
            return Object2ObjectLinkedOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2ObjectLinkedOpenHashMap.this.clear();
        }

        @Override // java.util.SortedSet
        public K first() {
            if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.first];
        }

        @Override // java.util.SortedSet
        public K last() {
            if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.last];
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> tailSet(K from) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> headSet(K to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> subSet(K from, K to) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectSortedMap, com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectSortedSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectLinkedOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super V>> implements ObjectListIterator<V> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((ValueIterator) consumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public V previous() {
            return Object2ObjectLinkedOpenHashMap.this.value[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Object2ObjectLinkedOpenHashMap.this = r4;
        }

        final void acceptOnIndex(Consumer<? super V> action, int index) {
            action.accept((Object) Object2ObjectLinkedOpenHashMap.this.value[index]);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public V next() {
            return Object2ObjectLinkedOpenHashMap.this.value[nextEntry()];
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectSortedMap, com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap.1
                private static final int SPLITERATOR_CHARACTERISTICS = 80;

                @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
                public ObjectIterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
                public ObjectSpliterator<V> spliterator() {
                    return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ObjectLinkedOpenHashMap.this), 80);
                }

                @Override // java.lang.Iterable
                public void forEach(Consumer<? super V> consumer) {
                    int i = Object2ObjectLinkedOpenHashMap.this.size;
                    int next = Object2ObjectLinkedOpenHashMap.this.first;
                    while (true) {
                        int i2 = i;
                        i--;
                        if (i2 != 0) {
                            int curr = next;
                            next = (int) Object2ObjectLinkedOpenHashMap.this.link[curr];
                            consumer.accept((Object) Object2ObjectLinkedOpenHashMap.this.value[curr]);
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Object2ObjectLinkedOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object v) {
                    return Object2ObjectLinkedOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Object2ObjectLinkedOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f139f));
        if (l >= this.f138n || this.size > HashCommon.maxFill(l, this.f139f)) {
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
        K[] key = this.key;
        V[] value = this.value;
        int mask = newN - 1;
        K[] newKey = (K[]) new Object[newN + 1];
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
            if (key[i] == null) {
                pos = newN;
            } else {
                int mix = HashCommon.mix(key[i].hashCode());
                while (true) {
                    pos = mix & mask;
                    if (newKey[pos] == null) {
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
        this.f138n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.f138n, this.f139f);
        this.key = newKey;
        this.value = newValue;
    }

    public Object2ObjectLinkedOpenHashMap<K, V> clone() {
        try {
            Object2ObjectLinkedOpenHashMap<K, V> c = (Object2ObjectLinkedOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (K[]) ((Object[]) this.key.clone());
            c.value = (V[]) ((Object[]) this.value.clone());
            c.link = (long[]) this.link.clone();
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
            h += this.value[this.f138n] == null ? 0 : this.value[this.f138n].hashCode();
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        K[] key = this.key;
        V[] value = this.value;
        Object2ObjectLinkedOpenHashMap<K, V>.EntryIterator i = new EntryIterator();
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
        this.f138n = HashCommon.arraySize(this.size, this.f139f);
        this.maxFill = HashCommon.maxFill(this.f138n, this.f139f);
        this.mask = this.f138n - 1;
        K[] key = (K[]) new Object[this.f138n + 1];
        this.key = key;
        V[] value = (V[]) new Object[this.f138n + 1];
        this.value = value;
        long[] link = new long[this.f138n + 1];
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
            Object readObject = s.readObject();
            Object readObject2 = s.readObject();
            if (readObject == null) {
                pos = this.f138n;
                this.containsNullKey = true;
            } else {
                int mix = HashCommon.mix(readObject.hashCode());
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
            key[pos] = readObject;
            value[pos] = readObject2;
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
