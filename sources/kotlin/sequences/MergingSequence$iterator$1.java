package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0015\n��\n\u0002\u0010(\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u000e\u0010\t\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n��\u001a\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n��\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u000b"}, m53d2 = {"kotlin/sequences/MergingSequence$iterator$1", "", "iterator1", "getIterator1", "()Ljava/util/Iterator;", "iterator2", "getIterator2", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/MergingSequence$iterator$1.class */
public final class MergingSequence$iterator$1 implements Iterator<V>, KMarkers {
    @NotNull
    private final Iterator<T1> iterator1;
    @NotNull
    private final Iterator<T2> iterator2;
    final /* synthetic */ MergingSequence<T1, T2, V> this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public MergingSequence$iterator$1(MergingSequence<T1, T2, V> mergingSequence) {
        Sequence sequence;
        Sequence sequence2;
        this.this$0 = mergingSequence;
        sequence = ((MergingSequence) this.this$0).sequence1;
        this.iterator1 = sequence.iterator();
        sequence2 = ((MergingSequence) this.this$0).sequence2;
        this.iterator2 = sequence2.iterator();
    }

    @NotNull
    public final Iterator<T1> getIterator1() {
        return this.iterator1;
    }

    @NotNull
    public final Iterator<T2> getIterator2() {
        return this.iterator2;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [V, java.lang.Object] */
    @Override // java.util.Iterator
    public V next() {
        Function2 function2;
        function2 = ((MergingSequence) this.this$0).transform;
        return function2.invoke(this.iterator1.next(), this.iterator2.next());
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator1.hasNext() && this.iterator2.hasNext();
    }
}
