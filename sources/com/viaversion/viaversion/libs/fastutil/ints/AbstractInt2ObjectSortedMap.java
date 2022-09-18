package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectSortedMap.class */
public abstract class AbstractInt2ObjectSortedMap<V> extends AbstractInt2ObjectMap<V> implements Int2ObjectSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public IntSortedSet keySet() {
        return new KeySet();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectSortedMap$KeySet.class */
    public class KeySet extends AbstractIntSortedSet {
        protected KeySet() {
            AbstractInt2ObjectSortedMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return AbstractInt2ObjectSortedMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AbstractInt2ObjectSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbstractInt2ObjectSortedMap.this.clear();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        public IntComparator comparator() {
            return AbstractInt2ObjectSortedMap.this.comparator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int firstInt() {
            return AbstractInt2ObjectSortedMap.this.firstIntKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int lastInt() {
            return AbstractInt2ObjectSortedMap.this.lastIntKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet headSet(int to) {
            return AbstractInt2ObjectSortedMap.this.headMap(to).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet tailSet(int from) {
            return AbstractInt2ObjectSortedMap.this.tailMap(from).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2ObjectSortedMap.this.subMap(from, to).keySet();
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet] */
        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2ObjectSortedMap.this.int2ObjectEntrySet().iterator(new AbstractInt2ObjectMap.BasicEntry(from, (Object) null)));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSortedSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return new KeySetIterator(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectSortedMap$KeySetIterator.class */
    public static class KeySetIterator<V> implements IntBidirectionalIterator {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> f71i;

        public KeySetIterator(ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i) {
            this.f71i = i;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return ((Int2ObjectMap.Entry) this.f71i.next()).getIntKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return this.f71i.previous().getIntKey();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f71i.hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.f71i.hasPrevious();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new ValuesCollection();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectSortedMap$ValuesCollection.class */
    public class ValuesCollection extends AbstractObjectCollection<V> {
        protected ValuesCollection() {
            AbstractInt2ObjectSortedMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object k) {
            return AbstractInt2ObjectSortedMap.this.containsValue(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return AbstractInt2ObjectSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            AbstractInt2ObjectSortedMap.this.clear();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectSortedMap$ValuesIterator.class */
    public static class ValuesIterator<V> implements ObjectIterator<V> {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> f72i;

        public ValuesIterator(ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i) {
            this.f72i = i;
        }

        @Override // java.util.Iterator
        public V next() {
            return ((Int2ObjectMap.Entry) this.f72i.next()).getValue();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f72i.hasNext();
        }
    }
}
