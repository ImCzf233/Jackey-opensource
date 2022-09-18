package kotlin.collections;

import java.util.RandomAccess;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: _ArraysJvm.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001f\n��\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u000b*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0002H\u0016J\b\u0010\u000f\u001a\u00020\u0002H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, m53d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$7", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "element", PropertyDescriptor.GET, "index", "(I)Ljava/lang/Boolean;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/ArraysKt___ArraysJvmKt$asList$7.class */
public final class ArraysKt___ArraysJvmKt$asList$7 extends AbstractList<Boolean> implements RandomAccess {
    final /* synthetic */ boolean[] $this_asList;

    public ArraysKt___ArraysJvmKt$asList$7(boolean[] $receiver) {
        this.$this_asList = $receiver;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof Boolean)) {
            return false;
        }
        return contains(((Boolean) element).booleanValue());
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object element) {
        if (!(element instanceof Boolean)) {
            return -1;
        }
        return indexOf(((Boolean) element).booleanValue());
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object element) {
        if (!(element instanceof Boolean)) {
            return -1;
        }
        return lastIndexOf(((Boolean) element).booleanValue());
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.$this_asList.length;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.$this_asList.length == 0;
    }

    public boolean contains(boolean element) {
        return ArraysKt.contains(this.$this_asList, element);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    @NotNull
    public Boolean get(int index) {
        return Boolean.valueOf(this.$this_asList[index]);
    }

    public int indexOf(boolean element) {
        return ArraysKt.indexOf(this.$this_asList, element);
    }

    public int lastIndexOf(boolean element) {
        return ArraysKt.lastIndexOf(this.$this_asList, element);
    }
}
