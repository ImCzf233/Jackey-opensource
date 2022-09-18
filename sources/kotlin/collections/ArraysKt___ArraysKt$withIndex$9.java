package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Arrays.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010(\n\u0002\u0010\f\n��\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, m53d2 = {"<anonymous>", "", "", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/ArraysKt___ArraysKt$withIndex$9.class */
final class ArraysKt___ArraysKt$withIndex$9 extends Lambda implements Functions<Iterator<? extends Character>> {
    final /* synthetic */ char[] $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArraysKt___ArraysKt$withIndex$9(char[] $receiver) {
        super(0);
        this.$this_withIndex = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    /* renamed from: invoke */
    public final Iterator<? extends Character> invoke2() {
        return ArrayIteratorsKt.iterator(this.$this_withIndex);
    }
}
