package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

/* compiled from: Regex.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\u0010��\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, m53d2 = {"<anonymous>", "Lkotlin/text/MatchGroup;", "it", "", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/MatcherMatchResult$groups$1$iterator$1.class */
final class MatcherMatchResult$groups$1$iterator$1 extends Lambda implements Function1<Integer, MatchGroup> {
    final /* synthetic */ MatcherMatchResult$groups$1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MatcherMatchResult$groups$1$iterator$1(MatcherMatchResult$groups$1 $receiver) {
        super(1);
        this.this$0 = $receiver;
    }

    @Nullable
    public final MatchGroup invoke(int it) {
        return this.this$0.get(it);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ MatchGroup invoke(Integer num) {
        return invoke(num.intValue());
    }
}
