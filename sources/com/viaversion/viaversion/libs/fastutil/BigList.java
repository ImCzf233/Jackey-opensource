package com.viaversion.viaversion.libs.fastutil;

import java.util.Collection;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/BigList.class */
public interface BigList<K> extends Collection<K>, Size64 {
    K get(long j);

    K remove(long j);

    K set(long j, K k);

    void add(long j, K k);

    void size(long j);

    boolean addAll(long j, Collection<? extends K> collection);

    long indexOf(Object obj);

    long lastIndexOf(Object obj);

    BigListIterator<K> listIterator();

    BigListIterator<K> listIterator(long j);

    BigList<K> subList(long j, long j2);

    @Override // java.util.Collection, com.viaversion.viaversion.libs.fastutil.Size64
    @Deprecated
    default int size() {
        return super.size();
    }
}
