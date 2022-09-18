package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectLists.class */
public final class ObjectLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ObjectLists() {
    }

    public static <K> ObjectList<K> shuffle(ObjectList<K> l, Random random) {
        int i = l.size();
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int p = random.nextInt(i + 1);
                K t = l.get(i);
                l.set(i, l.get(p));
                l.set(p, t);
            } else {
                return l;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectLists$EmptyList.class */
    public static class EmptyList<K> extends ObjectCollections.EmptyCollection<K> implements ObjectList<K>, RandomAccess, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return compareTo((List) ((List) obj));
        }

        protected EmptyList() {
        }

        @Override // java.util.List
        public K get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        public void add(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        public K set(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        public int indexOf(Object k) {
            return -1;
        }

        @Override // java.util.List
        public int lastIndexOf(Object k) {
            return -1;
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        public void replaceAll(UnaryOperator<K> operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public void sort(Comparator<? super K> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void unstableSort(Comparator<? super K> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections.EmptyCollection, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator(int i) {
            if (i == 0) {
                return ObjectIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void getElements(int from, Object[] a, int offset, int length) {
            if (from == 0 && length == 0 && offset >= 0 && offset <= a.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void addElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        public int compareTo(List<? extends K> o) {
            return (o != this && !o.isEmpty()) ? -1 : 0;
        }

        public Object clone() {
            return ObjectLists.EMPTY_LIST;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections.EmptyCollection, java.util.Collection
        public int hashCode() {
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object o) {
            return (o instanceof List) && ((List) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return ObjectLists.EMPTY_LIST;
        }
    }

    public static <K> ObjectList<K> emptyList() {
        return EMPTY_LIST;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectLists$Singleton.class */
    public static class Singleton<K> extends AbstractObjectList<K> implements RandomAccess, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final K element;

        protected Singleton(K element) {
            this.element = element;
        }

        @Override // java.util.List
        public K get(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object k) {
            return Objects.equals(k, this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public int indexOf(Object k) {
            return Objects.equals(k, this.element) ? 0 : -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            return new Object[]{this.element};
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectListIterator<K> iterator() {
            return listIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.singleton(this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            ObjectListIterator<K> l = listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from > to) {
                throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            if (from != 0 || to != 1) {
                return ObjectLists.EMPTY_LIST;
            }
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.lang.Iterable
        public void forEach(Consumer<? super K> action) {
            action.accept((K) this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public boolean addAll(int i, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super K> filter) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        public void replaceAll(UnaryOperator<K> operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public void sort(Comparator<? super K> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void unstableSort(Comparator<? super K> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void getElements(int from, Object[] a, int offset, int length) {
            if (offset < 0) {
                throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
            }
            if (offset + length > a.length) {
                throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
            }
            if (from + length > size()) {
                throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
            }
            if (length <= 0) {
                return;
            }
            a[offset] = this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void addElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void size(int size) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }
    }

    public static <K> ObjectList<K> singleton(K element) {
        return new Singleton(element);
    }

    public static <K> ObjectList<K> synchronize(ObjectList<K> l) {
        return l instanceof RandomAccess ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l);
    }

    public static <K> ObjectList<K> synchronize(ObjectList<K> l, Object sync) {
        if (l instanceof RandomAccess) {
            return new SynchronizedRandomAccessList(l, sync);
        }
        return new SynchronizedList(l, sync);
    }

    public static <K> ObjectList<K> unmodifiable(ObjectList<? extends K> l) {
        return l instanceof RandomAccess ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectLists$ImmutableListBase.class */
    public static abstract class ImmutableListBase<K> extends AbstractObjectList<K> implements ObjectList<K> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        @Deprecated
        public final void add(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        @Deprecated
        public final boolean addAll(int index, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        @Deprecated
        public final K remove(int index) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        @Deprecated
        public final boolean removeIf(Predicate<? super K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.List
        @Deprecated
        public final void replaceAll(UnaryOperator<K> operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        @Deprecated
        public final K set(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        @Deprecated
        public final void size(int size) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        @Deprecated
        public final void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        @Deprecated
        public final void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        @Deprecated
        public final void setElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        @Deprecated
        public final void sort(Comparator<? super K> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        @Deprecated
        public final void unstableSort(Comparator<? super K> comparator) {
            throw new UnsupportedOperationException();
        }
    }
}
