package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AccessOrderDeque.class */
final class AccessOrderDeque<E extends AccessOrder<E>> extends AbstractLinkedDeque<E> {

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AccessOrderDeque$AccessOrder.class */
    public interface AccessOrder<T extends AccessOrder<T>> {
        T getPreviousInAccessOrder();

        void setPreviousInAccessOrder(T t);

        T getNextInAccessOrder();

        void setNextInAccessOrder(T t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public /* bridge */ /* synthetic */ Object getNext(Object obj) {
        return getNext((AccessOrderDeque<E>) ((AccessOrder) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public /* bridge */ /* synthetic */ Object getPrevious(Object obj) {
        return getPrevious((AccessOrderDeque<E>) ((AccessOrder) obj));
    }

    @Override // com.github.benmanes.caffeine.cache.AbstractLinkedDeque, java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public boolean contains(Object o) {
        return (o instanceof AccessOrder) && contains((AccessOrder) o);
    }

    public boolean contains(AccessOrder<?> e) {
        return (e.getPreviousInAccessOrder() == null && e.getNextInAccessOrder() == null && e != this.first) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public boolean remove(Object o) {
        return (o instanceof AccessOrder) && remove((AccessOrderDeque<E>) ((AccessOrder) o));
    }

    public boolean remove(E e) {
        if (contains((AccessOrder<?>) e)) {
            unlink(e);
            return true;
        }
        return false;
    }

    public E getPrevious(E e) {
        return (E) e.getPreviousInAccessOrder();
    }

    public void setPrevious(E e, E prev) {
        e.setPreviousInAccessOrder(prev);
    }

    public E getNext(E e) {
        return (E) e.getNextInAccessOrder();
    }

    public void setNext(E e, E next) {
        e.setNextInAccessOrder(next);
    }
}
