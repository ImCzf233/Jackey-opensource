package kotlin.collections;

import java.util.List;
import java.util.RandomAccess;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��,\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\b��\u0018��*\u0006\b��\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028��0\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u000e\u001a\u00028��2\u0006\u0010\u000f\u001a\u00020\tH\u0096\u0002¢\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028��0\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, m53d2 = {"Lkotlin/collections/MovingSubList;", "E", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "list", "", "(Ljava/util/List;)V", "_size", "", "fromIndex", "size", "getSize", "()I", PropertyDescriptor.GET, "index", "(I)Ljava/lang/Object;", "move", "", "toIndex", "kotlin-stdlib"})
/* renamed from: kotlin.collections.MovingSubList */
/* loaded from: Jackey Client b2.jar:kotlin/collections/MovingSubList.class */
public final class SlidingWindow<E> extends AbstractList<E> implements RandomAccess {
    @NotNull
    private final List<E> list;
    private int fromIndex;
    private int _size;

    /* JADX WARN: Multi-variable type inference failed */
    public SlidingWindow(@NotNull List<? extends E> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.list = list;
    }

    public final void move(int fromIndex, int toIndex) {
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, this.list.size());
        this.fromIndex = fromIndex;
        this._size = toIndex - fromIndex;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public E get(int index) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this._size);
        return this.list.get(this.fromIndex + index);
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this._size;
    }
}
