package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntSortedMap.class */
public interface Object2IntSortedMap<K> extends Object2IntMap<K>, SortedMap<K, Integer> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntSortedMap$FastSortedEntrySet.class */
    public interface FastSortedEntrySet<K> extends ObjectSortedSet<Object2IntMap.Entry<K>>, Object2IntMap.FastEntrySet<K> {
        ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator();

        ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap.Entry<K> entry);
    }

    Object2IntSortedMap<K> subMap(K k, K k2);

    Object2IntSortedMap<K> headMap(K k);

    Object2IntSortedMap<K> tailMap(K k);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    ObjectSortedSet<K> keySet();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    IntCollection values();

    Comparator<? super K> comparator();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    @Deprecated
    default ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
        return object2IntEntrySet();
    }
}
