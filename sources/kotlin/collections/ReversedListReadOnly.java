package kotlin.collections;

import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReversedViews.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0012\u0018��*\u0006\b��\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028��0\u0004¢\u0006\u0002\u0010\u0005J\u0016\u0010\n\u001a\u00028��2\u0006\u0010\u000b\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0002\u0010\fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028��0\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\r"}, m53d2 = {"Lkotlin/collections/ReversedListReadOnly;", "T", "Lkotlin/collections/AbstractList;", "delegate", "", "(Ljava/util/List;)V", "size", "", "getSize", "()I", PropertyDescriptor.GET, "index", "(I)Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/ReversedListReadOnly.class */
class ReversedListReadOnly<T> extends AbstractList<T> {
    @NotNull
    private final List<T> delegate;

    /* JADX WARN: Multi-variable type inference failed */
    public ReversedListReadOnly(@NotNull List<? extends T> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.delegate.size();
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public T get(int index) {
        int reverseElementIndex$CollectionsKt__ReversedViewsKt;
        List<T> list = this.delegate;
        reverseElementIndex$CollectionsKt__ReversedViewsKt = ReversedViews.reverseElementIndex$CollectionsKt__ReversedViewsKt(this, index);
        return list.get(reverseElementIndex$CollectionsKt__ReversedViewsKt);
    }
}
