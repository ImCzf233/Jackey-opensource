package kotlin.p002io;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReadWrite.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0017\n��\n\u0002\u0010(\n\u0002\u0010\u000e\n��\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0006\u001a\u00020\u0004H\u0096\u0002J\t\u0010\u0007\u001a\u00020\u0002H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n��¨\u0006\b"}, m53d2 = {"kotlin/io/LinesSequence$iterator$1", "", "", "done", "", "nextValue", "hasNext", "next", "kotlin-stdlib"})
/* renamed from: kotlin.io.LinesSequence$iterator$1 */
/* loaded from: Jackey Client b2.jar:kotlin/io/LinesSequence$iterator$1.class */
public final class LinesSequence$iterator$1 implements Iterator<String>, KMarkers {
    @Nullable
    private String nextValue;
    private boolean done;
    final /* synthetic */ ReadWrite this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public LinesSequence$iterator$1(ReadWrite $receiver) {
        this.this$0 = $receiver;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        BufferedReader bufferedReader;
        if (this.nextValue == null && !this.done) {
            bufferedReader = this.this$0.reader;
            this.nextValue = bufferedReader.readLine();
            if (this.nextValue == null) {
                this.done = true;
            }
        }
        return this.nextValue != null;
    }

    @Override // java.util.Iterator
    @NotNull
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String answer = this.nextValue;
        this.nextValue = null;
        Intrinsics.checkNotNull(answer);
        return answer;
    }
}
