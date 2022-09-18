package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSets.class */
public final class ObjectSets {
    static final int ARRAY_SET_CUTOFF = 4;
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final ObjectSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new ObjectArraySet(ObjectArrays.EMPTY_ARRAY));

    private ObjectSets() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSets$EmptySet.class */
    public static class EmptySet<K> extends ObjectCollections.EmptyCollection<K> implements ObjectSet<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object o) {
            return (o instanceof Set) && ((Set) o).isEmpty();
        }

        private Object readResolve() {
            return ObjectSets.EMPTY_SET;
        }
    }

    public static <K> ObjectSet<K> emptySet() {
        return EMPTY_SET;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSets$Singleton.class */
    public static class Singleton<K> extends AbstractObjectSet<K> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K element;

        public Singleton(K element) {
            this.element = element;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Objects.equals(k, this.element);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.singleton(this.element);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return 1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return new Object[]{this.element};
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> action) {
            action.accept((K) this.element);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super K> filter) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }
    }

    public static <K> ObjectSet<K> singleton(K element) {
        return new Singleton(element);
    }

    public static <K> ObjectSet<K> synchronize(ObjectSet<K> s) {
        return new SynchronizedSet(s);
    }

    public static <K> ObjectSet<K> synchronize(ObjectSet<K> s, Object sync) {
        return new SynchronizedSet(s, sync);
    }

    public static <K> ObjectSet<K> unmodifiable(ObjectSet<? extends K> s) {
        return new UnmodifiableSet(s);
    }
}
