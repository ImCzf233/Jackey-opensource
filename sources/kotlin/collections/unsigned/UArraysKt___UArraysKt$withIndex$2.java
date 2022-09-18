package kotlin.collections.unsigned;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _UArrays.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010(\n\u0002\u0018\u0002\n��\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, m53d2 = {"<anonymous>", "", "Lkotlin/ULong;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/unsigned/UArraysKt___UArraysKt$withIndex$2.class */
final class UArraysKt___UArraysKt$withIndex$2 extends Lambda implements Functions<Iterator<? extends ULong>> {
    final /* synthetic */ long[] $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UArraysKt___UArraysKt$withIndex$2(long[] $this_withIndex) {
        super(0);
        this.$this_withIndex = $this_withIndex;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    /* renamed from: invoke */
    public final Iterator<? extends ULong> invoke2() {
        return ULongArray.m1441iteratorimpl(this.$this_withIndex);
    }
}
