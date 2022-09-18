package com.viaversion.viaversion.libs.fastutil.objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectBidirectionalIterable.class */
public interface ObjectBidirectionalIterable<K> extends ObjectIterable<K> {
    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection
    ObjectBidirectionalIterator<K> iterator();
}
