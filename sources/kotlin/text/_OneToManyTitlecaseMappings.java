package kotlin.text;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010\u000e\n\u0002\u0010\f\n��\u001a\f\u0010��\u001a\u00020\u0001*\u00020\u0002H��¨\u0006\u0003"}, m53d2 = {"titlecaseImpl", "", "", "kotlin-stdlib"})
/* renamed from: kotlin.text._OneToManyTitlecaseMappingsKt */
/* loaded from: Jackey Client b2.jar:kotlin/text/_OneToManyTitlecaseMappingsKt.class */
public final class _OneToManyTitlecaseMappings {
    @NotNull
    public static final String titlecaseImpl(char $this$titlecaseImpl) {
        String uppercase = String.valueOf($this$titlecaseImpl).toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(uppercase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        if (uppercase.length() > 1) {
            if ($this$titlecaseImpl == 329) {
                return uppercase;
            }
            char charAt = uppercase.charAt(0);
            String substring = uppercase.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            String lowerCase = substring.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            return charAt + lowerCase;
        }
        return String.valueOf(Character.toTitleCase($this$titlecaseImpl));
    }
}
