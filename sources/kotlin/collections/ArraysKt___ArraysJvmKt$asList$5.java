package kotlin.collections;

import java.util.RandomAccess;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: _ArraysJvm.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��'\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001��\b\n\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m53d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$5", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", PropertyDescriptor.GET, "index", "(I)Ljava/lang/Float;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/ArraysKt___ArraysJvmKt$asList$5.class */
public final class ArraysKt___ArraysJvmKt$asList$5 extends AbstractList<Float> implements RandomAccess {
    final /* synthetic */ float[] $this_asList;

    public ArraysKt___ArraysJvmKt$asList$5(float[] $receiver) {
        this.$this_asList = $receiver;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof Float)) {
            return false;
        }
        return contains(((Number) element).floatValue());
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object element) {
        if (!(element instanceof Float)) {
            return -1;
        }
        return indexOf(((Number) element).floatValue());
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object element) {
        if (!(element instanceof Float)) {
            return -1;
        }
        return lastIndexOf(((Number) element).floatValue());
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.$this_asList.length;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.$this_asList.length == 0;
    }

    public boolean contains(float element) {
        boolean z;
        float[] $this$any$iv = this.$this_asList;
        int i = 0;
        int length = $this$any$iv.length;
        while (i < length) {
            float element$iv = $this$any$iv[i];
            i++;
            if (Float.floatToIntBits(element$iv) == Float.floatToIntBits(element)) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    @NotNull
    public Float get(int index) {
        return Float.valueOf(this.$this_asList[index]);
    }

    public int indexOf(float element) {
        boolean z;
        float[] $this$indexOfFirst$iv = this.$this_asList;
        int i = 0;
        int length = $this$indexOfFirst$iv.length;
        while (i < length) {
            int index$iv = i;
            i++;
            float it = $this$indexOfFirst$iv[index$iv];
            if (Float.floatToIntBits(it) == Float.floatToIntBits(element)) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                return index$iv;
            }
        }
        return -1;
    }

    public int lastIndexOf(float element) {
        float[] $this$indexOfLast$iv = this.$this_asList;
        int length = $this$indexOfLast$iv.length - 1;
        if (0 <= length) {
            do {
                int index$iv = length;
                length--;
                float it = $this$indexOfLast$iv[index$iv];
                if (Float.floatToIntBits(it) == Float.floatToIntBits(element)) {
                    return index$iv;
                }
            } while (0 <= length);
            return -1;
        }
        return -1;
    }
}
