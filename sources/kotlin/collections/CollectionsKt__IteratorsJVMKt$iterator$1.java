package kotlin.collections;

import java.util.Enumeration;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMarkers;

/* compiled from: IteratorsJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0013\n��\n\u0002\u0010(\n��\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\t\u0010\u0002\u001a\u00020\u0003H\u0096\u0002J\u000e\u0010\u0004\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, m53d2 = {"kotlin/collections/CollectionsKt__IteratorsJVMKt$iterator$1", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/CollectionsKt__IteratorsJVMKt$iterator$1.class */
public final class CollectionsKt__IteratorsJVMKt$iterator$1 implements Iterator<T>, KMarkers {
    final /* synthetic */ Enumeration<T> $this_iterator;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public CollectionsKt__IteratorsJVMKt$iterator$1(Enumeration<T> enumeration) {
        this.$this_iterator = enumeration;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.$this_iterator.hasMoreElements();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [T, java.lang.Object] */
    @Override // java.util.Iterator
    public T next() {
        return this.$this_iterator.nextElement();
    }
}
