package kotlin.text;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Matcher;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* compiled from: Regex.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��>\n��\n\u0002\u0010\"\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n��\u001a-\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b��\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"}, m53d2 = {"fromInt", "", "T", "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", AsmConstants.CODERANGE, "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/text/RegexKt.class */
public final class RegexKt {
    public static final /* synthetic */ int access$toInt(Iterable $receiver) {
        return toInt($receiver);
    }

    public static final /* synthetic */ MatchResult access$findNext(Matcher $receiver, int from, CharSequence input) {
        return findNext($receiver, from, input);
    }

    public static final /* synthetic */ MatchResult access$matchEntire(Matcher $receiver, CharSequence input) {
        return matchEntire($receiver, input);
    }

    public static final int toInt(Iterable<? extends FlagEnum> iterable) {
        int accumulator$iv = 0;
        for (Object element$iv : iterable) {
            int value = accumulator$iv;
            FlagEnum option = (FlagEnum) element$iv;
            accumulator$iv = value | option.getValue();
        }
        return accumulator$iv;
    }

    private static final /* synthetic */ <T extends Enum<T> & FlagEnum> Set<T> fromInt(int value) {
        Intrinsics.reifiedOperationMarker(4, "T");
        EnumSet allOf = EnumSet.allOf(Enum.class);
        EnumSet $this$fromInt_u24lambda_u2d1 = allOf;
        Intrinsics.checkNotNullExpressionValue($this$fromInt_u24lambda_u2d1, "");
        Intrinsics.needClassReification();
        CollectionsKt.retainAll($this$fromInt_u24lambda_u2d1, new RegexKt$fromInt$1$1(value));
        Set<T> unmodifiableSet = Collections.unmodifiableSet(allOf);
        Intrinsics.checkNotNullExpressionValue(unmodifiableSet, "unmodifiableSet(EnumSet.…mask == it.value }\n    })");
        return unmodifiableSet;
    }

    public static final MatchResult findNext(Matcher $this$findNext, int from, CharSequence input) {
        if (!$this$findNext.find(from)) {
            return null;
        }
        return new MatcherMatchResult($this$findNext, input);
    }

    public static final MatchResult matchEntire(Matcher $this$matchEntire, CharSequence input) {
        if (!$this$matchEntire.matches()) {
            return null;
        }
        return new MatcherMatchResult($this$matchEntire, input);
    }

    public static final IntRange range(java.util.regex.MatchResult $this$range) {
        return RangesKt.until($this$range.start(), $this$range.end());
    }

    public static final IntRange range(java.util.regex.MatchResult $this$range, int groupIndex) {
        return RangesKt.until($this$range.start(groupIndex), $this$range.end(groupIndex));
    }
}
