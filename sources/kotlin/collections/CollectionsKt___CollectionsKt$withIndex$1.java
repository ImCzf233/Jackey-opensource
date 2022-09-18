package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Collections.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010(\n\u0002\b\u0002\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, m53d2 = {"<anonymous>", "", "T", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/CollectionsKt___CollectionsKt$withIndex$1.class */
final class CollectionsKt___CollectionsKt$withIndex$1 extends Lambda implements Functions<Iterator<? extends T>> {
    final /* synthetic */ Iterable<T> $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CollectionsKt___CollectionsKt$withIndex$1(Iterable<? extends T> iterable) {
        super(0);
        this.$this_withIndex = iterable;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    public final Iterator<T> invoke() {
        return this.$this_withIndex.iterator();
    }
}
