package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001d\n��\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\t\u0010\u000b\u001a\u00020\fH\u0096\u0002J\u000e\u0010\r\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028��0\u0001¢\u0006\b\n��\u001a\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f"}, m53d2 = {"kotlin/sequences/TakeSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "left", "", "getLeft", "()I", "setLeft", "(I)V", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/TakeSequence$iterator$1.class */
public final class TakeSequence$iterator$1 implements Iterator<T>, KMarkers {
    private int left;
    @NotNull
    private final Iterator<T> iterator;
    final /* synthetic */ TakeSequence<T> this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public TakeSequence$iterator$1(TakeSequence<T> takeSequence) {
        int i;
        Sequence sequence;
        this.this$0 = takeSequence;
        i = ((TakeSequence) this.this$0).count;
        this.left = i;
        sequence = ((TakeSequence) this.this$0).sequence;
        this.iterator = sequence.iterator();
    }

    public final int getLeft() {
        return this.left;
    }

    public final void setLeft(int i) {
        this.left = i;
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.iterator;
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [T, java.lang.Object] */
    @Override // java.util.Iterator
    public T next() {
        if (this.left == 0) {
            throw new NoSuchElementException();
        }
        this.left--;
        return this.iterator.next();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.left > 0 && this.iterator.hasNext();
    }
}
