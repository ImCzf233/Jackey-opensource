package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntSortedMap.class */
public abstract class AbstractInt2IntSortedMap extends AbstractInt2IntMap implements Int2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntSortedSet keySet() {
        return new KeySet();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntSortedMap$KeySet.class */
    public class KeySet extends AbstractIntSortedSet {
        protected KeySet() {
            AbstractInt2IntSortedMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return AbstractInt2IntSortedMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AbstractInt2IntSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbstractInt2IntSortedMap.this.clear();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        public IntComparator comparator() {
            return AbstractInt2IntSortedMap.this.comparator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int firstInt() {
            return AbstractInt2IntSortedMap.this.firstIntKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int lastInt() {
            return AbstractInt2IntSortedMap.this.lastIntKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet headSet(int to) {
            return AbstractInt2IntSortedMap.this.headMap(to).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet tailSet(int from) {
            return AbstractInt2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2IntSortedMap.this.subMap(from, to).keySet();
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet] */
        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2IntSortedMap.this.int2IntEntrySet().iterator(new AbstractInt2IntMap.BasicEntry(from, 0)));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSortedSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return new KeySetIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntSortedMap$KeySetIterator.class */
    public static class KeySetIterator implements IntBidirectionalIterator {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Int2IntMap.Entry> f67i;

        public KeySetIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> i) {
            this.f67i = i;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return ((Int2IntMap.Entry) this.f67i.next()).getIntKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return this.f67i.previous().getIntKey();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f67i.hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.f67i.hasPrevious();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntCollection values() {
        return new ValuesCollection();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntSortedMap$ValuesCollection.class */
    public class ValuesCollection extends AbstractIntCollection {
        protected ValuesCollection() {
            AbstractInt2IntSortedMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new ValuesIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return AbstractInt2IntSortedMap.this.containsValue(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return AbstractInt2IntSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            AbstractInt2IntSortedMap.this.clear();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntSortedMap$ValuesIterator.class */
    public static class ValuesIterator implements IntIterator {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Int2IntMap.Entry> f68i;

        public ValuesIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> i) {
            this.f68i = i;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return ((Int2IntMap.Entry) this.f68i.next()).getIntValue();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f68i.hasNext();
        }
    }
}
