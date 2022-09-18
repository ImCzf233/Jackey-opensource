package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Regex.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0014\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\u0010��\u001a\u00020\u0001\"\u0014\b��\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u000e\u0010\u0005\u001a\n \u0006*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b"}, m53d2 = {"<anonymous>", "", "T", "Lkotlin/text/FlagEnum;", "", "it", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Enum;)Ljava/lang/Boolean;"})
/* loaded from: Jackey Client b2.jar:kotlin/text/RegexKt$fromInt$1$1.class */
final class RegexKt$fromInt$1$1 extends Lambda implements Function1<T, Boolean> {
    final /* synthetic */ int $value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RegexKt$fromInt$1$1(int $value) {
        super(1);
        this.$value = $value;
    }

    @NotNull
    public final Boolean invoke(Enum it) {
        return Boolean.valueOf((this.$value & ((FlagEnum) it).getMask()) == ((FlagEnum) it).getValue());
    }
}
