package org.spongepowered.asm.lib.tree.analysis;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/SmallSet.class */
class SmallSet<E> extends AbstractSet<E> implements Iterator<E> {

    /* renamed from: e1 */
    E f424e1;

    /* renamed from: e2 */
    E f425e2;

    public static final <T> Set<T> emptySet() {
        return new SmallSet(null, null);
    }

    public SmallSet(E e1, E e2) {
        this.f424e1 = e1;
        this.f425e2 = e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return new SmallSet(this.f424e1, this.f425e2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        if (this.f424e1 == null) {
            return 0;
        }
        return this.f425e2 == null ? 1 : 2;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.f424e1 != null;
    }

    @Override // java.util.Iterator
    public E next() {
        if (this.f424e1 == null) {
            throw new NoSuchElementException();
        }
        E e = this.f424e1;
        this.f424e1 = this.f425e2;
        this.f425e2 = null;
        return e;
    }

    @Override // java.util.Iterator
    public void remove() {
    }

    public Set<E> union(SmallSet<E> s) {
        if ((s.f424e1 == this.f424e1 && s.f425e2 == this.f425e2) || (s.f424e1 == this.f425e2 && s.f425e2 == this.f424e1)) {
            return this;
        }
        if (s.f424e1 == null) {
            return this;
        }
        if (this.f424e1 == null) {
            return s;
        }
        if (s.f425e2 == null) {
            if (this.f425e2 == null) {
                return new SmallSet(this.f424e1, s.f424e1);
            }
            if (s.f424e1 == this.f424e1 || s.f424e1 == this.f425e2) {
                return this;
            }
        }
        if (this.f425e2 == null && (this.f424e1 == s.f424e1 || this.f424e1 == s.f425e2)) {
            return s;
        }
        HashSet<E> r = new HashSet<>(4);
        r.add(this.f424e1);
        if (this.f425e2 != null) {
            r.add(this.f425e2);
        }
        r.add(s.f424e1);
        if (s.f425e2 != null) {
            r.add(s.f425e2);
        }
        return r;
    }
}
