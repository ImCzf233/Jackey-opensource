package com.viaversion.viaversion.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/SynchronizedListWrapper.class */
public final class SynchronizedListWrapper<E> implements List<E> {
    private final List<E> list;
    private final Consumer<E> addHandler;

    public SynchronizedListWrapper(List<E> inputList, Consumer<E> addHandler) {
        this.list = inputList;
        this.addHandler = addHandler;
    }

    public List<E> originalList() {
        return this.list;
    }

    private void handleAdd(E o) {
        this.addHandler.accept(o);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        int size;
        synchronized (this) {
            size = this.list.size();
        }
        return size;
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        boolean isEmpty;
        synchronized (this) {
            isEmpty = this.list.isEmpty();
        }
        return isEmpty;
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object o) {
        boolean contains;
        synchronized (this) {
            contains = this.list.contains(o);
        }
        return contains;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        Object[] array;
        synchronized (this) {
            array = this.list.toArray();
        }
        return array;
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(E o) {
        boolean add;
        synchronized (this) {
            handleAdd(o);
            add = this.list.add(o);
        }
        return add;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object o) {
        boolean remove;
        synchronized (this) {
            remove = this.list.remove(o);
        }
        return remove;
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> c) {
        boolean addAll;
        synchronized (this) {
            for (E o : c) {
                handleAdd(o);
            }
            addAll = this.list.addAll(c);
        }
        return addAll;
    }

    @Override // java.util.List
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean addAll;
        synchronized (this) {
            for (E o : c) {
                handleAdd(o);
            }
            addAll = this.list.addAll(index, c);
        }
        return addAll;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        synchronized (this) {
            this.list.clear();
        }
    }

    @Override // java.util.List
    public E get(int index) {
        E e;
        synchronized (this) {
            e = this.list.get(index);
        }
        return e;
    }

    @Override // java.util.List
    public E set(int index, E element) {
        E e;
        synchronized (this) {
            e = this.list.set(index, element);
        }
        return e;
    }

    @Override // java.util.List
    public void add(int index, E element) {
        synchronized (this) {
            this.list.add(index, element);
        }
    }

    @Override // java.util.List
    public E remove(int index) {
        E remove;
        synchronized (this) {
            remove = this.list.remove(index);
        }
        return remove;
    }

    @Override // java.util.List
    public int indexOf(Object o) {
        int indexOf;
        synchronized (this) {
            indexOf = this.list.indexOf(o);
        }
        return indexOf;
    }

    @Override // java.util.List
    public int lastIndexOf(Object o) {
        int lastIndexOf;
        synchronized (this) {
            lastIndexOf = this.list.lastIndexOf(o);
        }
        return lastIndexOf;
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override // java.util.List
    public List<E> subList(int fromIndex, int toIndex) {
        List<E> subList;
        synchronized (this) {
            subList = this.list.subList(fromIndex, toIndex);
        }
        return subList;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> c) {
        boolean retainAll;
        synchronized (this) {
            retainAll = this.list.retainAll(c);
        }
        return retainAll;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> c) {
        boolean removeAll;
        synchronized (this) {
            removeAll = this.list.removeAll(c);
        }
        return removeAll;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> c) {
        boolean containsAll;
        synchronized (this) {
            containsAll = this.list.containsAll(c);
        }
        return containsAll;
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] a) {
        T[] tArr;
        synchronized (this) {
            tArr = (T[]) this.list.toArray(a);
        }
        return tArr;
    }

    @Override // java.util.List
    public void sort(Comparator<? super E> c) {
        synchronized (this) {
            this.list.sort(c);
        }
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        synchronized (this) {
            this.list.forEach(consumer);
        }
    }

    @Override // java.util.Collection
    public boolean removeIf(Predicate<? super E> filter) {
        boolean removeIf;
        synchronized (this) {
            removeIf = this.list.removeIf(filter);
        }
        return removeIf;
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object o) {
        boolean equals;
        if (this == o) {
            return true;
        }
        synchronized (this) {
            equals = this.list.equals(o);
        }
        return equals;
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        int hashCode;
        synchronized (this) {
            hashCode = this.list.hashCode();
        }
        return hashCode;
    }

    public String toString() {
        String obj;
        synchronized (this) {
            obj = this.list.toString();
        }
        return obj;
    }
}
