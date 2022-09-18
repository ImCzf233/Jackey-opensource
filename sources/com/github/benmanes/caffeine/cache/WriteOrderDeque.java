package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.WriteOrderDeque.WriteOrder;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WriteOrderDeque.class */
final class WriteOrderDeque<E extends WriteOrder<E>> extends AbstractLinkedDeque<E> {

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WriteOrderDeque$WriteOrder.class */
    public interface WriteOrder<T extends WriteOrder<T>> {
        T getPreviousInWriteOrder();

        void setPreviousInWriteOrder(T t);

        T getNextInWriteOrder();

        void setNextInWriteOrder(T t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public /* bridge */ /* synthetic */ Object getNext(Object obj) {
        return getNext((WriteOrderDeque<E>) ((WriteOrder) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public /* bridge */ /* synthetic */ Object getPrevious(Object obj) {
        return getPrevious((WriteOrderDeque<E>) ((WriteOrder) obj));
    }

    @Override // com.github.benmanes.caffeine.cache.AbstractLinkedDeque, java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public boolean contains(Object o) {
        return (o instanceof WriteOrder) && contains((WriteOrder) o);
    }

    boolean contains(WriteOrder<?> e) {
        return (e.getPreviousInWriteOrder() == null && e.getNextInWriteOrder() == null && e != this.first) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public boolean remove(Object o) {
        return (o instanceof WriteOrder) && remove((WriteOrderDeque<E>) ((WriteOrder) o));
    }

    public boolean remove(E e) {
        if (contains((WriteOrder<?>) e)) {
            unlink(e);
            return true;
        }
        return false;
    }

    public E getPrevious(E e) {
        return (E) e.getPreviousInWriteOrder();
    }

    public void setPrevious(E e, E prev) {
        e.setPreviousInWriteOrder(prev);
    }

    public E getNext(E e) {
        return (E) e.getNextInWriteOrder();
    }

    public void setNext(E e, E next) {
        e.setNextInWriteOrder(next);
    }
}
