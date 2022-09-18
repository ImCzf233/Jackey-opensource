package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectList.class */
public interface ObjectList<K> extends List<K>, Comparable<List<? extends K>>, ObjectCollection<K> {
    @Override // java.util.List, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    ObjectListIterator<K> iterator();

    @Override // java.util.List
    ObjectListIterator<K> listIterator();

    @Override // java.util.List
    ObjectListIterator<K> listIterator(int i);

    @Override // java.util.List
    ObjectList<K> subList(int i, int i2);

    void size(int i);

    void getElements(int i, Object[] objArr, int i2, int i3);

    void removeElements(int i, int i2);

    void addElements(int i, K[] kArr);

    void addElements(int i, K[] kArr, int i2, int i3);

    @Override // java.util.List, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    default ObjectSpliterator<K> spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractObjectList.IndexBasedSpliterator(this, 0);
        }
        return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), ObjectSpliterators.LIST_SPLITERATOR_CHARACTERISTICS);
    }

    default void setElements(K[] a) {
        setElements(0, a);
    }

    default void setElements(int index, K[] a) {
        setElements(index, a, 0, a.length);
    }

    default void setElements(int index, K[] a, int offset, int length) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
        }
        ObjectArrays.ensureOffsetLength(a, offset, length);
        if (index + length > size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")");
        }
        ObjectListIterator<K> iter = listIterator(index);
        int i = 0;
        while (i < length) {
            iter.next();
            int i2 = i;
            i++;
            iter.set(a[offset + i2]);
        }
    }

    default boolean addAll(int index, ObjectList<? extends K> l) {
        return addAll(index, (Collection) l);
    }

    default boolean addAll(ObjectList<? extends K> l) {
        return addAll(size(), (ObjectList) l);
    }

    /* renamed from: of */
    static <K> ObjectList<K> m166of() {
        return ObjectImmutableList.m176of();
    }

    /* renamed from: of */
    static <K> ObjectList<K> m165of(K e) {
        return ObjectLists.singleton(e);
    }

    /* renamed from: of */
    static <K> ObjectList<K> m164of(K e0, K e1) {
        return ObjectImmutableList.m175of(e0, e1);
    }

    /* renamed from: of */
    static <K> ObjectList<K> m163of(K e0, K e1, K e2) {
        return ObjectImmutableList.m175of(e0, e1, e2);
    }

    @SafeVarargs
    /* renamed from: of */
    static <K> ObjectList<K> m162of(K... a) {
        switch (a.length) {
            case 0:
                return m166of();
            case 1:
                return m165of((Object) a[0]);
            default:
                return ObjectImmutableList.m175of((Object[]) a);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.List
    default void sort(Comparator<? super K> comparator) {
        Object[] array = toArray();
        if (comparator == null) {
            ObjectArrays.stableSort(array);
        } else {
            ObjectArrays.stableSort(array, comparator);
        }
        setElements(array);
    }

    /* JADX WARN: Multi-variable type inference failed */
    default void unstableSort(Comparator<? super K> comparator) {
        Object[] array = toArray();
        if (comparator == null) {
            ObjectArrays.unstableSort(array);
        } else {
            ObjectArrays.unstableSort(array, comparator);
        }
        setElements(array);
    }
}
