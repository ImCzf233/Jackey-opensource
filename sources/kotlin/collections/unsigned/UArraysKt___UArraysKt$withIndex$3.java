package kotlin.collections.unsigned;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _UArrays.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010(\n\u0002\u0018\u0002\n��\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, m53d2 = {"<anonymous>", "", "Lkotlin/UByte;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/unsigned/UArraysKt___UArraysKt$withIndex$3.class */
final class UArraysKt___UArraysKt$withIndex$3 extends Lambda implements Functions<Iterator<? extends UByte>> {
    final /* synthetic */ byte[] $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UArraysKt___UArraysKt$withIndex$3(byte[] $this_withIndex) {
        super(0);
        this.$this_withIndex = $this_withIndex;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    /* renamed from: invoke */
    public final Iterator<? extends UByte> invoke2() {
        return UByteArray.m1283iteratorimpl(this.$this_withIndex);
    }
}
