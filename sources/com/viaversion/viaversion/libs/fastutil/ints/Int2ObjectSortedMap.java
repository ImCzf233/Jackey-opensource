package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectSortedMap.class */
public interface Int2ObjectSortedMap<V> extends Int2ObjectMap<V>, SortedMap<Integer, V> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectSortedMap$FastSortedEntrySet.class */
    public interface FastSortedEntrySet<V> extends ObjectSortedSet<Int2ObjectMap.Entry<V>>, Int2ObjectMap.FastEntrySet<V> {
        ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator();

        ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap.Entry<V> entry);
    }

    Int2ObjectSortedMap<V> subMap(int i, int i2);

    Int2ObjectSortedMap<V> headMap(int i);

    Int2ObjectSortedMap<V> tailMap(int i);

    int firstIntKey();

    int lastIntKey();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    IntSortedSet keySet();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    ObjectCollection<V> values();

    IntComparator comparator();

    @Deprecated
    default Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
        return subMap(from.intValue(), to.intValue());
    }

    @Deprecated
    default Int2ObjectSortedMap<V> headMap(Integer to) {
        return headMap(to.intValue());
    }

    @Deprecated
    default Int2ObjectSortedMap<V> tailMap(Integer from) {
        return tailMap(from.intValue());
    }

    @Override // java.util.SortedMap
    @Deprecated
    default Integer firstKey() {
        return Integer.valueOf(firstIntKey());
    }

    @Override // java.util.SortedMap
    @Deprecated
    default Integer lastKey() {
        return Integer.valueOf(lastIntKey());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    @Deprecated
    default ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
        return int2ObjectEntrySet();
    }
}
