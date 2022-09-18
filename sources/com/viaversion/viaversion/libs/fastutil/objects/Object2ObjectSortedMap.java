package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectSortedMap.class */
public interface Object2ObjectSortedMap<K, V> extends Object2ObjectMap<K, V>, SortedMap<K, V> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectSortedMap$FastSortedEntrySet.class */
    public interface FastSortedEntrySet<K, V> extends ObjectSortedSet<Object2ObjectMap.Entry<K, V>>, Object2ObjectMap.FastEntrySet<K, V> {
        ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator();

        ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap.Entry<K, V> entry);
    }

    Object2ObjectSortedMap<K, V> subMap(K k, K k2);

    Object2ObjectSortedMap<K, V> headMap(K k);

    Object2ObjectSortedMap<K, V> tailMap(K k);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
    ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    ObjectSortedSet<K> keySet();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    ObjectCollection<V> values();

    Comparator<? super K> comparator();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
        return object2ObjectEntrySet();
    }
}
