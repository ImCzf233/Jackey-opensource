package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Iterator;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntSet.class */
public abstract class AbstractIntSet extends AbstractIntCollection implements Cloneable, IntSet {
    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    public abstract Iterator<Integer> iterator2();

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        Set<?> s = (Set) o;
        if (s.size() != size()) {
            return false;
        }
        if (s instanceof IntSet) {
            return containsAll((IntSet) s);
        }
        return containsAll(s);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int h = 0;
        int n = size();
        ?? iterator2 = iterator2();
        while (true) {
            int i = n;
            n--;
            if (i != 0) {
                int k = iterator2.nextInt();
                h += k;
            } else {
                return h;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSet
    public boolean remove(int k) {
        return super.rem(k);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    public boolean rem(int k) {
        return remove(k);
    }
}
