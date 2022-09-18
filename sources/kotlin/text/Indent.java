package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��\u001e\n��\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010��\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"}, m53d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, m48xs = "kotlin/text/StringsKt")
/* renamed from: kotlin.text.StringsKt__IndentKt */
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__IndentKt.class */
public class Indent extends Appendable {
    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = CallSiteDescriptor.OPERATOR_DELIMITER;
        }
        return StringsKt.trimMargin(str, str2);
    }

    @NotNull
    public static final String trimMargin(@NotNull String $this$trimMargin, @NotNull String marginPrefix) {
        Intrinsics.checkNotNullParameter($this$trimMargin, "<this>");
        Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin($this$trimMargin, "", marginPrefix);
    }

    public static /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = CallSiteDescriptor.OPERATOR_DELIMITER;
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    @NotNull
    public static final String replaceIndentByMargin(@NotNull String $this$replaceIndentByMargin, @NotNull String newIndent, @NotNull String marginPrefix) {
        String str;
        int i;
        String str2;
        boolean z;
        Intrinsics.checkNotNullParameter($this$replaceIndentByMargin, "<this>");
        Intrinsics.checkNotNullParameter(newIndent, "newIndent");
        Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
        if (!(!StringsKt.isBlank(marginPrefix))) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        List lines = StringsKt.lines($this$replaceIndentByMargin);
        int resultSizeEstimate$iv = $this$replaceIndentByMargin.length() + (newIndent.length() * lines.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex$iv = CollectionsKt.getLastIndex(lines);
        List $this$mapIndexedNotNull$iv$iv = lines;
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv$iv = 0;
        for (Object item$iv$iv$iv$iv : $this$mapIndexedNotNull$iv$iv) {
            int index$iv$iv$iv = index$iv$iv$iv$iv;
            index$iv$iv$iv$iv = index$iv$iv$iv + 1;
            if (index$iv$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String value$iv = (String) item$iv$iv$iv$iv;
            if ((index$iv$iv$iv == 0 || index$iv$iv$iv == lastIndex$iv) && StringsKt.isBlank(value$iv)) {
                str = null;
            } else {
                String $this$indexOfFirst$iv = value$iv;
                int i2 = 0;
                int length = $this$indexOfFirst$iv.length();
                while (true) {
                    if (i2 >= length) {
                        i = -1;
                        break;
                    }
                    int index$iv = i2;
                    i2++;
                    char it = $this$indexOfFirst$iv.charAt(index$iv);
                    if (!CharsKt.isWhitespace(it)) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (z) {
                        i = index$iv;
                        break;
                    }
                }
                int firstNonWhitespaceIndex = i;
                if (firstNonWhitespaceIndex == -1) {
                    str2 = null;
                } else if (StringsKt.startsWith$default(value$iv, marginPrefix, firstNonWhitespaceIndex, false, 4, (Object) null)) {
                    String substring = value$iv.substring(firstNonWhitespaceIndex + marginPrefix.length());
                    Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
                    str2 = substring;
                } else {
                    str2 = null;
                }
                String str3 = str2;
                str = str3 == null ? value$iv : indentAddFunction$iv.invoke(str3);
            }
            String str4 = str;
            if (str4 != null) {
                destination$iv$iv$iv.add(str4);
            }
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(resultSizeEstimate$iv), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkNotNullExpressionValue(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }

    @NotNull
    public static final String trimIndent(@NotNull String $this$trimIndent) {
        Intrinsics.checkNotNullParameter($this$trimIndent, "<this>");
        return StringsKt.replaceIndent($this$trimIndent, "");
    }

    public static /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    @NotNull
    public static final String replaceIndent(@NotNull String $this$replaceIndent, @NotNull String newIndent) {
        int i;
        String str;
        Intrinsics.checkNotNullParameter($this$replaceIndent, "<this>");
        Intrinsics.checkNotNullParameter(newIndent, "newIndent");
        List lines = StringsKt.lines($this$replaceIndent);
        List $this$filter$iv = lines;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String p0 = (String) element$iv$iv;
            if (!StringsKt.isBlank(p0)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String p02 = (String) item$iv$iv;
            destination$iv$iv2.add(Integer.valueOf(indentWidth$StringsKt__IndentKt(p02)));
        }
        Integer num = (Integer) CollectionsKt.minOrNull((Iterable<Double>) ((List) destination$iv$iv2));
        if (num != null) {
            i = num.intValue();
        } else {
            i = 0;
        }
        int minCommonIndent = i;
        int resultSizeEstimate$iv = $this$replaceIndent.length() + (newIndent.length() * lines.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex$iv = CollectionsKt.getLastIndex(lines);
        List $this$mapIndexedNotNull$iv$iv = lines;
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv$iv = 0;
        for (Object item$iv$iv$iv$iv : $this$mapIndexedNotNull$iv$iv) {
            int index$iv$iv$iv = index$iv$iv$iv$iv;
            index$iv$iv$iv$iv = index$iv$iv$iv + 1;
            if (index$iv$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String value$iv = (String) item$iv$iv$iv$iv;
            if ((index$iv$iv$iv != 0 && index$iv$iv$iv != lastIndex$iv) || !StringsKt.isBlank(value$iv)) {
                String drop = StringsKt.drop(value$iv, minCommonIndent);
                str = drop == null ? value$iv : indentAddFunction$iv.invoke(drop);
            } else {
                str = null;
            }
            String str2 = str;
            if (str2 != null) {
                destination$iv$iv$iv.add(str2);
            }
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(resultSizeEstimate$iv), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkNotNullExpressionValue(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }

    public static /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String $this$prependIndent, @NotNull String indent) {
        Intrinsics.checkNotNullParameter($this$prependIndent, "<this>");
        Intrinsics.checkNotNullParameter(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence($this$prependIndent), new StringsKt__IndentKt$prependIndent$1(indent)), "\n", null, null, 0, null, null, 62, null);
    }

    private static final int indentWidth$StringsKt__IndentKt(String $this$indentWidth) {
        int i;
        boolean z;
        String $this$indexOfFirst$iv = $this$indentWidth;
        int i2 = 0;
        int length = $this$indexOfFirst$iv.length();
        while (true) {
            if (i2 >= length) {
                i = -1;
                break;
            }
            int index$iv = i2;
            i2++;
            if (!CharsKt.isWhitespace($this$indexOfFirst$iv.charAt(index$iv))) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                i = index$iv;
                break;
            }
        }
        int it = i;
        return it == -1 ? $this$indentWidth.length() : it;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String indent) {
        return indent.length() == 0 ? StringsKt__IndentKt$getIndentFunction$1.INSTANCE : new StringsKt__IndentKt$getIndentFunction$2(indent);
    }

    private static final String reindent$StringsKt__IndentKt(List<String> list, int resultSizeEstimate, Function1<? super String, String> function1, Function1<? super String, String> function12) {
        String str;
        int lastIndex = CollectionsKt.getLastIndex(list);
        List<String> $this$mapIndexedNotNull$iv = list;
        Collection destination$iv$iv = new ArrayList();
        int index$iv$iv$iv = 0;
        for (Object item$iv$iv$iv : $this$mapIndexedNotNull$iv) {
            int index$iv$iv = index$iv$iv$iv;
            index$iv$iv$iv = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String value = (String) item$iv$iv$iv;
            if ((index$iv$iv == 0 || index$iv$iv == lastIndex) && StringsKt.isBlank(value)) {
                str = null;
            } else {
                String invoke = function12.invoke(value);
                str = invoke == null ? value : function1.invoke(invoke);
            }
            String str2 = str;
            if (str2 != null) {
                destination$iv$iv.add(str2);
            }
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv, new StringBuilder(resultSizeEstimate), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkNotNullExpressionValue(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }
}
