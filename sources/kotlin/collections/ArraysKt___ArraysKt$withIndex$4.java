package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Arrays.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010(\n\u0002\u0010\b\n��\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, m53d2 = {"<anonymous>", "", "", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/ArraysKt___ArraysKt$withIndex$4.class */
final class ArraysKt___ArraysKt$withIndex$4 extends Lambda implements Functions<Iterator<? extends Integer>> {
    final /* synthetic */ int[] $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArraysKt___ArraysKt$withIndex$4(int[] $receiver) {
        super(0);
        this.$this_withIndex = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    /* renamed from: invoke */
    public final Iterator<? extends Integer> invoke2() {
        return ArrayIteratorsKt.iterator(this.$this_withIndex);
    }
}
