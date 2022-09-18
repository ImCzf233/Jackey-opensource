package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KTypeProjection;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeReference.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\r\n��\n\u0002\u0018\u0002\n��\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, m53d2 = {"<anonymous>", "", "it", "Lkotlin/reflect/KTypeProjection;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/TypeReference$asString$args$1.class */
public final class TypeReference$asString$args$1 extends Lambda implements Function1<KTypeProjection, CharSequence> {
    final /* synthetic */ TypeReference this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeReference$asString$args$1(TypeReference $receiver) {
        super(1);
        this.this$0 = $receiver;
    }

    @NotNull
    public final CharSequence invoke(@NotNull KTypeProjection it) {
        String asString;
        Intrinsics.checkNotNullParameter(it, "it");
        asString = this.this$0.asString(it);
        return asString;
    }
}
