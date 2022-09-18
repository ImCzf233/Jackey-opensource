package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSet.class */
public interface ObjectSet<K> extends ObjectCollection<K>, Set<K> {
    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    ObjectIterator<K> iterator();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 65);
    }

    /* renamed from: of */
    static <K> ObjectSet<K> m153of() {
        return ObjectSets.UNMODIFIABLE_EMPTY_SET;
    }

    /* renamed from: of */
    static <K> ObjectSet<K> m152of(K e) {
        return ObjectSets.singleton(e);
    }

    /* renamed from: of */
    static <K> ObjectSet<K> m151of(K e0, K e1) {
        ObjectArraySet<K> innerSet = new ObjectArraySet<>(2);
        innerSet.add(e0);
        if (!innerSet.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        return ObjectSets.unmodifiable(innerSet);
    }

    /* renamed from: of */
    static <K> ObjectSet<K> m150of(K e0, K e1, K e2) {
        ObjectArraySet<K> innerSet = new ObjectArraySet<>(3);
        innerSet.add(e0);
        if (!innerSet.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        if (!innerSet.add(e2)) {
            throw new IllegalArgumentException("Duplicate element: " + e2);
        }
        return ObjectSets.unmodifiable(innerSet);
    }

    @SafeVarargs
    /* renamed from: of */
    static <K> ObjectSet<K> m149of(K... a) {
        ObjectSet<K> objectSet;
        switch (a.length) {
            case 0:
                return m153of();
            case 1:
                return m152of((Object) a[0]);
            case 2:
                return m151of((Object) a[0], (Object) a[1]);
            case 3:
                return m150of((Object) a[0], (Object) a[1], (Object) a[2]);
            default:
                if (a.length <= 4) {
                    objectSet = new ObjectArraySet<>(a.length);
                } else {
                    objectSet = new ObjectOpenHashSet<>(a.length);
                }
                ObjectSet<K> innerSet = objectSet;
                for (K element : a) {
                    if (!innerSet.add(element)) {
                        throw new IllegalArgumentException("Duplicate element: " + element);
                    }
                }
                return ObjectSets.unmodifiable(innerSet);
        }
    }
}
