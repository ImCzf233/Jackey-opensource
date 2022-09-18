package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSortedSets.class */
public final class ObjectSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ObjectSortedSets() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSortedSets$EmptySet.class */
    public static class EmptySet<K> extends ObjectSets.EmptySet<K> implements ObjectSortedSet<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> subSet(K from, K to) {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> headSet(K from) {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> tailSet(K to) {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // java.util.SortedSet
        public K first() {
            throw new NoSuchElementException();
        }

        @Override // java.util.SortedSet
        public K last() {
            throw new NoSuchElementException();
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.EmptySet
        public Object clone() {
            return ObjectSortedSets.EMPTY_SET;
        }

        private Object readResolve() {
            return ObjectSortedSets.EMPTY_SET;
        }
    }

    public static <K> ObjectSet<K> emptySet() {
        return EMPTY_SET;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSortedSets$Singleton.class */
    public static class Singleton<K> extends ObjectSets.Singleton<K> implements ObjectSortedSet<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final Comparator<? super K> comparator;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.Singleton, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ ObjectBidirectionalIterator iterator() {
            return super.iterator();
        }

        protected Singleton(K element, Comparator<? super K> comparator) {
            super(element);
            this.comparator = comparator;
        }

        Singleton(K element) {
            this(element, null);
        }

        final int compare(K k1, K k2) {
            return this.comparator == null ? ((Comparable) k1).compareTo(k2) : this.comparator.compare(k1, k2);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public ObjectBidirectionalIterator<K> iterator(K from) {
            ObjectBidirectionalIterator<K> i = iterator();
            if (compare(this.element, from) <= 0) {
                i.next();
            }
            return i;
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.Singleton, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.singleton(this.element, this.comparator);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> subSet(K from, K to) {
            if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) {
                return this;
            }
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> headSet(K to) {
            if (compare(this.element, to) < 0) {
                return this;
            }
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
        public ObjectSortedSet<K> tailSet(K from) {
            if (compare(from, this.element) <= 0) {
                return this;
            }
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // java.util.SortedSet
        public K first() {
            return this.element;
        }

        @Override // java.util.SortedSet
        public K last() {
            return this.element;
        }
    }

    public static <K> ObjectSortedSet<K> singleton(K element) {
        return new Singleton(element);
    }

    public static <K> ObjectSortedSet<K> singleton(K element, Comparator<? super K> comparator) {
        return new Singleton(element, comparator);
    }

    public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s) {
        return new SynchronizedSortedSet(s);
    }

    public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s, Object sync) {
        return new SynchronizedSortedSet(s, sync);
    }

    public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> s) {
        return new UnmodifiableSortedSet(s);
    }
}
