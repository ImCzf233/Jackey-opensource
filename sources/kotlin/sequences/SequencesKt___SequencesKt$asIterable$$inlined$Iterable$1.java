package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterables.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0011\n��\n\u0002\u0010\u001c\n��\n\u0002\u0010(\n��*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00028��0\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028��0\u0003H\u0096\u0002¨\u0006\u0004¸\u0006��"}, m53d2 = {"kotlin/collections/CollectionsKt__IterablesKt$Iterable$1", "", "iterator", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1.class */
public final class SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 implements Iterable<T>, KMarkers {
    final /* synthetic */ Sequence $this_asIterable$inlined;

    public SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(Sequence sequence) {
        this.$this_asIterable$inlined = sequence;
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<T> iterator() {
        return this.$this_asIterable$inlined.iterator();
    }
}
