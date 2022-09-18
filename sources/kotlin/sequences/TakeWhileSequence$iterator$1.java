package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��#\n��\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028��0\u0001¢\u0006\b\n��\u001a\u0004\b\u0003\u0010\u0004R\u001e\u0010\u0005\u001a\u0004\u0018\u00018��X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, m53d2 = {"kotlin/sequences/TakeWhileSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", Constants.OBJECT, "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/TakeWhileSequence$iterator$1.class */
public final class TakeWhileSequence$iterator$1 implements Iterator<T>, KMarkers {
    @NotNull
    private final Iterator<T> iterator;
    private int nextState = -1;
    @Nullable
    private T nextItem;
    final /* synthetic */ TakeWhileSequence<T> this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public TakeWhileSequence$iterator$1(TakeWhileSequence<T> takeWhileSequence) {
        Sequence sequence;
        this.this$0 = takeWhileSequence;
        sequence = ((TakeWhileSequence) this.this$0).sequence;
        this.iterator = sequence.iterator();
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.iterator;
    }

    public final int getNextState() {
        return this.nextState;
    }

    public final void setNextState(int i) {
        this.nextState = i;
    }

    @Nullable
    public final T getNextItem() {
        return this.nextItem;
    }

    public final void setNextItem(@Nullable T t) {
        this.nextItem = t;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
    private final void calcNext() {
        Function1 function1;
        if (this.iterator.hasNext()) {
            ?? next = this.iterator.next();
            function1 = ((TakeWhileSequence) this.this$0).predicate;
            if (((Boolean) function1.invoke(next)).booleanValue()) {
                this.nextState = 1;
                this.nextItem = next;
                return;
            }
        }
        this.nextState = 0;
    }

    @Override // java.util.Iterator
    public T next() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        T t = this.nextItem;
        this.nextItem = null;
        this.nextState = -1;
        return t;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.nextState == -1) {
            calcNext();
        }
        return this.nextState == 1;
    }
}
