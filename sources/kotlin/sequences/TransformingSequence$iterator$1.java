package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0015\n��\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\t\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\u000e\u0010\u0007\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n��\u001a\u0004\b\u0003\u0010\u0004¨\u0006\t"}, m53d2 = {"kotlin/sequences/TransformingSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/TransformingSequence$iterator$1.class */
public final class TransformingSequence$iterator$1 implements Iterator<R>, KMarkers {
    @NotNull
    private final Iterator<T> iterator;
    final /* synthetic */ TransformingSequence<T, R> this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public TransformingSequence$iterator$1(TransformingSequence<T, R> transformingSequence) {
        Sequence sequence;
        this.this$0 = transformingSequence;
        sequence = ((TransformingSequence) this.this$0).sequence;
        this.iterator = sequence.iterator();
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.iterator;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [R, java.lang.Object] */
    @Override // java.util.Iterator
    public R next() {
        Function1 function1;
        function1 = ((TransformingSequence) this.this$0).transformer;
        return function1.invoke(this.iterator.next());
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator.hasNext();
    }
}
