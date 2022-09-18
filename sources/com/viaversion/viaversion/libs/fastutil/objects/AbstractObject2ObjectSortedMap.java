package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectSortedMap.class */
public abstract class AbstractObject2ObjectSortedMap<K, V> extends AbstractObject2ObjectMap<K, V> implements Object2ObjectSortedMap<K, V> {
    private static final long serialVersionUID = -1773560792952436569L;

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectSortedMap$KeySet.class */
    public class KeySet extends AbstractObjectSortedSet<K> {
        protected KeySet() {
            AbstractObject2ObjectSortedMap.this = this$0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return AbstractObject2ObjectSortedMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AbstractObject2ObjectSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbstractObject2ObjectSortedMap.this.clear();
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return AbstractObject2ObjectSortedMap.this.comparator();
        }

        @Override // java.util.SortedSet
        public K first() {
            return (K) AbstractObject2ObjectSortedMap.this.firstKey();
        }

        @Override // java.util.SortedSet
        public K last() {
            return (K) AbstractObject2ObjectSortedMap.this.lastKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2ObjectSortedMap.this.headMap((AbstractObject2ObjectSortedMap) to).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2ObjectSortedMap.this.tailMap((AbstractObject2ObjectSortedMap) from).keySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2ObjectSortedMap.this.subMap((Object) from, (Object) to).keySet();
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet] */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2ObjectSortedMap.this.object2ObjectEntrySet().iterator(new AbstractObject2ObjectMap.BasicEntry(from, null)));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectSortedMap$KeySetIterator.class */
    public static class KeySetIterator<K, V> implements ObjectBidirectionalIterator<K> {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> f127i;

        public KeySetIterator(ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i) {
            this.f127i = i;
        }

        @Override // java.util.Iterator
        public K next() {
            return ((Object2ObjectMap.Entry) this.f127i.next()).getKey();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            return this.f127i.previous().getKey();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f127i.hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.f127i.hasPrevious();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new ValuesCollection();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectSortedMap$ValuesCollection.class */
    public class ValuesCollection extends AbstractObjectCollection<V> {
        protected ValuesCollection() {
            AbstractObject2ObjectSortedMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object k) {
            return AbstractObject2ObjectSortedMap.this.containsValue(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return AbstractObject2ObjectSortedMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            AbstractObject2ObjectSortedMap.this.clear();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectSortedMap$ValuesIterator.class */
    public static class ValuesIterator<K, V> implements ObjectIterator<V> {

        /* renamed from: i */
        protected final ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> f128i;

        public ValuesIterator(ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i) {
            this.f128i = i;
        }

        @Override // java.util.Iterator
        public V next() {
            return ((Object2ObjectMap.Entry) this.f128i.next()).getValue();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f128i.hasNext();
        }
    }
}
