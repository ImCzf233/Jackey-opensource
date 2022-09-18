package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��#\n��\n\u0002\u0010(\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\t\u0010\u0010\u001a\u00020\u0011H\u0096\u0002J\u000e\u0010\u0012\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\u0004R\u001e\u0010\u0002\u001a\u0004\u0018\u00018��X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0007\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0013"}, m53d2 = {"kotlin/sequences/GeneratorSequence$iterator$1", "", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", Constants.OBJECT, "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/GeneratorSequence$iterator$1.class */
public final class GeneratorSequence$iterator$1 implements Iterator<T>, KMarkers {
    @Nullable
    private T nextItem;
    private int nextState = -2;
    final /* synthetic */ GeneratorSequence<T> this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public GeneratorSequence$iterator$1(GeneratorSequence<T> generatorSequence) {
        this.this$0 = generatorSequence;
    }

    @Nullable
    public final T getNextItem() {
        return this.nextItem;
    }

    public final void setNextItem(@Nullable T t) {
        this.nextItem = t;
    }

    public final int getNextState() {
        return this.nextState;
    }

    public final void setNextState(int i) {
        this.nextState = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void calcNext() {
        T t;
        Function1 function1;
        Functions functions;
        if (this.nextState == -2) {
            functions = ((GeneratorSequence) this.this$0).getInitialValue;
            t = functions.invoke();
        } else {
            function1 = ((GeneratorSequence) this.this$0).getNextValue;
            T t2 = this.nextItem;
            Intrinsics.checkNotNull(t2);
            t = function1.invoke(t2);
        }
        this.nextItem = t;
        this.nextState = this.nextItem == 0 ? 0 : 1;
    }

    @Override // java.util.Iterator
    @NotNull
    public T next() {
        if (this.nextState < 0) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        T t = this.nextItem;
        if (t == 0) {
            throw new NullPointerException("null cannot be cast to non-null type T of kotlin.sequences.GeneratorSequence");
        }
        this.nextState = -1;
        return t;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.nextState < 0) {
            calcNext();
        }
        return this.nextState == 1;
    }
}
