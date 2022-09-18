package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectCollection.class */
public interface ObjectCollection<K> extends Collection<K>, ObjectIterable<K> {
    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    ObjectIterator<K> iterator();

    @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 64);
    }
}
