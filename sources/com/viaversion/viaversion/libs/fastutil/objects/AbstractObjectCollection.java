package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.AbstractCollection;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectCollection.class */
public abstract class AbstractObjectCollection<K> extends AbstractCollection<K> implements ObjectCollection<K> {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public abstract ObjectIterator<K> iterator();

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator<K> i = iterator();
        int n = size();
        boolean first = true;
        s.append("{");
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                Object k = i.next();
                if (this == k) {
                    s.append("(this collection)");
                } else {
                    s.append(String.valueOf(k));
                }
            } else {
                s.append("}");
                return s.toString();
            }
        }
    }
}
