package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

/* compiled from: Regex.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0018\u0002\n��\u0010��\u001a\u0004\u0018\u00010\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "Lkotlin/text/MatchResult;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/Regex$findAll$1.class */
public final class Regex$findAll$1 extends Lambda implements Functions<MatchResult> {
    final /* synthetic */ Regex this$0;
    final /* synthetic */ CharSequence $input;
    final /* synthetic */ int $startIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Regex$findAll$1(Regex $receiver, CharSequence $input, int $startIndex) {
        super(0);
        this.this$0 = $receiver;
        this.$input = $input;
        this.$startIndex = $startIndex;
    }

    @Override // kotlin.jvm.functions.Functions
    @Nullable
    public final MatchResult invoke() {
        return this.this$0.find(this.$input, this.$startIndex);
    }
}
