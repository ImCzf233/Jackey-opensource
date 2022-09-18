package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Indent.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, m53d2 = {"<anonymous>", "", "line", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__IndentKt$getIndentFunction$2.class */
public final class StringsKt__IndentKt$getIndentFunction$2 extends Lambda implements Function1<String, String> {
    final /* synthetic */ String $indent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringsKt__IndentKt$getIndentFunction$2(String $indent) {
        super(1);
        this.$indent = $indent;
    }

    @NotNull
    public final String invoke(@NotNull String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        return Intrinsics.stringPlus(this.$indent, line);
    }
}
