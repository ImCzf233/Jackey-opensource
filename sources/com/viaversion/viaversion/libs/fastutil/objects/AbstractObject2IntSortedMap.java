package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntSortedMap.class */
public abstract class AbstractObject2IntSortedMap<K> extends AbstractObject2IntMap<K> implements Object2IntSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntSortedMap$KeySet.class */
    public class KeySet extends AbstractObjectSortedSet<K> {
        protected KeySet() {
            AbstractObject2IntSortedMap.this = this$0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return AbstractObject2IntSortedMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AbstractObject2IntSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbstractObject2IntSortedMap.this.clear();
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return AbstractObject2IntSortedMap.this.comparator();
        }

        @Override // java.util.SortedSet
        public K first() {
            return (K) AbstractObject2IntSortedMap.this.firstKey();
        }

        @Override // java.util.SortedSet
        public K last() {
            return (K) AbstractObject2IntSortedMap.this.lastKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2IntSortedMap.this.headMap((AbstractObject2IntSortedMap) to).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2IntSortedMap.this.tailMap((AbstractObject2IntSortedMap) from).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2IntSortedMap.this.subMap((Object) from, (Object) to).keySet();
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet] */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2IntSortedMap.this.object2IntEntrySet().iterator(new AbstractObject2IntMap.BasicEntry(from, 0)));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntSortedMap$KeySetIterator.class */
    public static class KeySetIterator<K> implements ObjectBidirectionalIterator<K> {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Object2IntMap.Entry<K>> f123i;

        public KeySetIterator(ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i) {
            this.f123i = i;
        }

        @Override // java.util.Iterator
        public K next() {
            return ((Object2IntMap.Entry) this.f123i.next()).getKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            return this.f123i.previous().getKey();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f123i.hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.f123i.hasPrevious();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public IntCollection values() {
        return new ValuesCollection();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntSortedMap$ValuesCollection.class */
    public class ValuesCollection extends AbstractIntCollection {
        protected ValuesCollection() {
            AbstractObject2IntSortedMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new ValuesIterator(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return AbstractObject2IntSortedMap.this.containsValue(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return AbstractObject2IntSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            AbstractObject2IntSortedMap.this.clear();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntSortedMap$ValuesIterator.class */
    public static class ValuesIterator<K> implements IntIterator {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Object2IntMap.Entry<K>> f124i;

        public ValuesIterator(ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i) {
            this.f124i = i;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return ((Object2IntMap.Entry) this.f124i.next()).getIntValue();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f124i.hasNext();
        }
    }
}
