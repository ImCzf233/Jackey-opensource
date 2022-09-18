package com.viaversion.viaversion.libs.fastutil.objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectSortedSet.class */
public abstract class AbstractObjectSortedSet<K> extends AbstractObjectSet<K> implements ObjectSortedSet<K> {
    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public abstract ObjectBidirectionalIterator<K> iterator();
}
