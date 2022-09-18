package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Iterator;
import java.util.Spliterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMapSet.class */
public final class Int2ObjectSyncMapSet extends AbstractIntSet implements IntSet {
    private static final long serialVersionUID = 1;
    private final Int2ObjectSyncMap<Boolean> map;
    private final IntSet set;

    public Int2ObjectSyncMapSet(final Int2ObjectSyncMap<Boolean> map) {
        this.map = map;
        this.set = map.keySet();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.map.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean contains(final int key) {
        return this.map.containsKey(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
    public boolean remove(final int key) {
        return this.map.remove(key) != null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(final int key) {
        return this.map.put(key, (int) Boolean.TRUE) == null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean containsAll(final IntCollection collection) {
        return this.set.containsAll(collection);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean removeAll(final IntCollection collection) {
        return this.set.removeAll(collection);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean retainAll(final IntCollection collection) {
        return this.set.retainAll(collection);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    public Iterator<Integer> iterator2() {
        return this.set.iterator2();
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    public Spliterator<Integer> spliterator2() {
        return this.set.spliterator2();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toArray(int[] original) {
        return this.set.toArray(original);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toIntArray() {
        return this.set.toIntArray();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, java.util.Collection, java.util.Set
    public boolean equals(final Object other) {
        return other == this || this.set.equals(other);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection
    public String toString() {
        return this.set.toString();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return this.set.hashCode();
    }
}
