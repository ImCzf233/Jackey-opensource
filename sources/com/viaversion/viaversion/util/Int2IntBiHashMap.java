package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/Int2IntBiHashMap.class */
public class Int2IntBiHashMap implements Int2IntBiMap {
    private final Int2IntMap map;
    private final Int2IntBiHashMap inverse;

    public Int2IntBiHashMap() {
        this.map = new Int2IntOpenHashMap();
        this.inverse = new Int2IntBiHashMap(this);
    }

    private Int2IntBiHashMap(Int2IntBiHashMap inverse) {
        this.map = new Int2IntOpenHashMap();
        this.inverse = inverse;
    }

    @Override // com.viaversion.viaversion.util.Int2IntBiMap
    public Int2IntBiMap inverse() {
        return this.inverse;
    }

    @Override // com.viaversion.viaversion.util.Int2IntBiMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int put(int key, int value) {
        if (!containsKey(key) || value != get(key)) {
            Preconditions.checkArgument(!containsValue(value), "value already present: %s", new Object[]{Integer.valueOf(value)});
            this.map.put(key, value);
            this.inverse.map.put(value, key);
            return defaultReturnValue();
        }
        return value;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean remove(int key, int value) {
        this.map.remove(key, value);
        return this.inverse.map.remove(key, value);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int get(int key) {
        return this.map.get(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        this.map.clear();
        this.inverse.map.clear();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.map.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public void defaultReturnValue(int rv) {
        this.map.defaultReturnValue(rv);
        this.inverse.map.defaultReturnValue(rv);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int defaultReturnValue() {
        return this.map.defaultReturnValue();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
        return this.map.int2IntEntrySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntSet keySet() {
        return this.map.keySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntSet values() {
        return this.inverse.map.keySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean containsKey(int key) {
        return this.map.containsKey(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean containsValue(int value) {
        return this.inverse.map.containsKey(value);
    }
}
