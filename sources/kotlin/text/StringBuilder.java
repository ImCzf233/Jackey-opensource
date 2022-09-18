package kotlin.text;

import kotlin.Annotations;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��F\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\r\n��\u001a>\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\b\tH\u0087\bø\u0001��\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a6\u0010��\u001a\u00020\u00012\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\b\tH\u0087\bø\u0001��\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u001f\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\f0\u000e\"\u0004\u0018\u00010\f¢\u0006\u0002\u0010\u000f\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0010\u001a\u0015\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u0007H\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\r\u001a\u00020\u0012H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\r\u001a\u00020\u0013H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\r\u001a\u00020\u0014H\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u0015H\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0087\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0016"}, m53d2 = {"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "obj", "", "value", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "appendLine", "", "", "", "", "kotlin-stdlib"}, m48xs = "kotlin/text/StringsKt")
/* renamed from: kotlin.text.StringsKt__StringBuilderKt */
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__StringBuilderKt.class */
class StringBuilder extends StringBuilderJVM {
    @Annotations(message = "Use append(value: Any?) instead", replaceWith = @ReplaceWith(expression = "append(value = obj)", imports = {}), level = DeprecationLevel.WARNING)
    @InlineOnly
    private static final java.lang.StringBuilder append(java.lang.StringBuilder $this$append, Object obj) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        java.lang.StringBuilder append = $this$append.append(obj);
        Intrinsics.checkNotNullExpressionValue(append, "this.append(obj)");
        return append;
    }

    @InlineOnly
    private static final String buildString(Function1<? super java.lang.StringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        builderAction.invoke(sb);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final String buildString(int capacity, Function1<? super java.lang.StringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        java.lang.StringBuilder sb = new java.lang.StringBuilder(capacity);
        builderAction.invoke(sb);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    public static final java.lang.StringBuilder append(@NotNull java.lang.StringBuilder $this$append, @NotNull String... value) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        int i = 0;
        int length = value.length;
        while (i < length) {
            String item = value[i];
            i++;
            $this$append.append(item);
        }
        return $this$append;
    }

    @NotNull
    public static final java.lang.StringBuilder append(@NotNull java.lang.StringBuilder $this$append, @NotNull Object... value) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        int i = 0;
        int length = value.length;
        while (i < length) {
            Object item = value[i];
            i++;
            $this$append.append(item);
        }
        return $this$append;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        java.lang.StringBuilder append = $this$appendLine.append('\n');
        Intrinsics.checkNotNullExpressionValue(append, "append('\\n')");
        return append;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine, CharSequence value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        java.lang.StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        java.lang.StringBuilder append2 = append.append('\n');
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine, String value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        java.lang.StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        java.lang.StringBuilder append2 = append.append('\n');
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine, Object value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        java.lang.StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        java.lang.StringBuilder append2 = append.append('\n');
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine, char[] value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        java.lang.StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        java.lang.StringBuilder append2 = append.append('\n');
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine, char value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        java.lang.StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        java.lang.StringBuilder append2 = append.append('\n');
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.lang.StringBuilder appendLine(java.lang.StringBuilder $this$appendLine, boolean value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        java.lang.StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        java.lang.StringBuilder append2 = append.append('\n');
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }
}
