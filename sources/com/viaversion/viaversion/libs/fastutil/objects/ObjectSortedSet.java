package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.SortedSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSortedSet.class */
public interface ObjectSortedSet<K> extends ObjectSet<K>, SortedSet<K>, ObjectBidirectionalIterable<K> {
    ObjectBidirectionalIterator<K> iterator(K k);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    ObjectBidirectionalIterator<K> iterator();

    ObjectSortedSet<K> subSet(K k, K k2);

    ObjectSortedSet<K> headSet(K k);

    ObjectSortedSet<K> tailSet(K k);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, java.util.Set
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorFromSorted(iterator(), Size64.sizeOf(this), 85, comparator());
    }
}
