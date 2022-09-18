package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMarkers;

/* compiled from: AbstractMap.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0013\n��\n\u0002\u0010(\n��\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\t\u0010\u0002\u001a\u00020\u0003H\u0096\u0002J\u000e\u0010\u0004\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, m53d2 = {"kotlin/collections/AbstractMap$values$1$iterator$1", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/AbstractMap$values$1$iterator$1.class */
public final class AbstractMap$values$1$iterator$1 implements Iterator<V>, KMarkers {
    final /* synthetic */ Iterator<Map.Entry<K, V>> $entryIterator;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractMap$values$1$iterator$1(Iterator<? extends Map.Entry<? extends K, ? extends V>> it) {
        this.$entryIterator = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.$entryIterator.hasNext();
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [V, java.lang.Object] */
    @Override // java.util.Iterator
    public V next() {
        return ((Map.Entry) this.$entryIterator.next()).getValue();
    }
}
