package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��!\n��\n\u0002\u0010(\n��\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028��0\u0001¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u001e\u0010\u000b\u001a\u0004\u0018\u00018��X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0016"}, m53d2 = {"kotlin/sequences/DropWhileSequence$iterator$1", "", "dropState", "", "getDropState", "()I", "setDropState", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", Constants.OBJECT, "drop", "", "hasNext", "", "next", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/DropWhileSequence$iterator$1.class */
public final class DropWhileSequence$iterator$1 implements Iterator<T>, KMarkers {
    @NotNull
    private final Iterator<T> iterator;
    private int dropState = -1;
    @Nullable
    private T nextItem;
    final /* synthetic */ DropWhileSequence<T> this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public DropWhileSequence$iterator$1(DropWhileSequence<T> dropWhileSequence) {
        Sequence sequence;
        this.this$0 = dropWhileSequence;
        sequence = ((DropWhileSequence) this.this$0).sequence;
        this.iterator = sequence.iterator();
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.iterator;
    }

    public final int getDropState() {
        return this.dropState;
    }

    public final void setDropState(int i) {
        this.dropState = i;
    }

    @Nullable
    public final T getNextItem() {
        return this.nextItem;
    }

    public final void setNextItem(@Nullable T t) {
        this.nextItem = t;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
    private final void drop() {
        Function1 function1;
        while (this.iterator.hasNext()) {
            ?? next = this.iterator.next();
            function1 = ((DropWhileSequence) this.this$0).predicate;
            if (!((Boolean) function1.invoke(next)).booleanValue()) {
                this.nextItem = next;
                this.dropState = 1;
                return;
            }
        }
        this.dropState = 0;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
    @Override // java.util.Iterator
    public T next() {
        if (this.dropState == -1) {
            drop();
        }
        if (this.dropState == 1) {
            T t = this.nextItem;
            this.nextItem = null;
            this.dropState = 0;
            return t;
        }
        return this.iterator.next();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.dropState == -1) {
            drop();
        }
        return this.dropState == 1 || this.iterator.hasNext();
    }
}
