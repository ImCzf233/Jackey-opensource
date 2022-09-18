package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectSet.class */
public abstract class AbstractObjectSet<K> extends AbstractObjectCollection<K> implements Cloneable, ObjectSet<K> {
    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public abstract ObjectIterator<K> iterator();

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
        return containsAll(s);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int h = 0;
        int n = size();
        ObjectIterator<K> i = iterator();
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                K k = i.next();
                h += k == null ? 0 : k.hashCode();
            } else {
                return h;
            }
        }
    }
}
