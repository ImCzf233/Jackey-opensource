package net.ccbluex.liquidbounce.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: RegexUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n��\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005¢\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012¨\u0006\u0013"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/RegexUtils;", "", "()V", "match", "", "", "matcher", "Ljava/util/regex/Matcher;", "(Ljava/util/regex/Matcher;)[Ljava/lang/String;", "text", "pattern", "Ljava/util/regex/Pattern;", "(Ljava/lang/String;Ljava/util/regex/Pattern;)[Ljava/lang/String;", "(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "round", "", "value", "places", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/RegexUtils.class */
public final class RegexUtils {
    @NotNull
    public static final RegexUtils INSTANCE = new RegexUtils();

    private RegexUtils() {
    }

    @NotNull
    public final String[] match(@NotNull Matcher matcher) {
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        List result = new ArrayList();
        while (matcher.find()) {
            String group = matcher.group();
            Intrinsics.checkNotNullExpressionValue(group, "matcher.group()");
            result.add(group);
        }
        List $this$toTypedArray$iv = result;
        Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
        if (array == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        return (String[]) array;
    }

    @NotNull
    public final String[] match(@NotNull String text, @NotNull Pattern pattern) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Matcher matcher = pattern.matcher(text);
        Intrinsics.checkNotNullExpressionValue(matcher, "pattern.matcher(text)");
        return match(matcher);
    }

    @NotNull
    public final String[] match(@NotNull String text, @NotNull String pattern) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Pattern compile = Pattern.compile(pattern);
        Intrinsics.checkNotNullExpressionValue(compile, "compile(pattern)");
        return match(text, compile);
    }

    public final double round(double value, int places) {
        if (!(places >= 0)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
