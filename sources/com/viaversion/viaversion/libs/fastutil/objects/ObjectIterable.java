package com.viaversion.viaversion.libs.fastutil.objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterable.class */
public interface ObjectIterable<K> extends Iterable<K> {
    @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    ObjectIterator<K> iterator();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }
}
