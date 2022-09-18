package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntSortedMap.class */
public interface Int2IntSortedMap extends Int2IntMap, SortedMap<Integer, Integer> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntSortedMap$FastSortedEntrySet.class */
    public interface FastSortedEntrySet extends ObjectSortedSet<Int2IntMap.Entry>, Int2IntMap.FastEntrySet {
        ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator();

        ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator(Int2IntMap.Entry entry);
    }

    Int2IntSortedMap subMap(int i, int i2);

    Int2IntSortedMap headMap(int i);

    Int2IntSortedMap tailMap(int i);

    int firstIntKey();

    int lastIntKey();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    IntSortedSet keySet();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    IntCollection values();

    IntComparator comparator();

    @Deprecated
    default Int2IntSortedMap subMap(Integer from, Integer to) {
        return subMap(from.intValue(), to.intValue());
    }

    @Deprecated
    default Int2IntSortedMap headMap(Integer to) {
        return headMap(to.intValue());
    }

    @Deprecated
    default Int2IntSortedMap tailMap(Integer from) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    @Deprecated
    default Set<Map.Entry<Integer, Integer>> entrySet() {
        return int2IntEntrySet();
    }
}
