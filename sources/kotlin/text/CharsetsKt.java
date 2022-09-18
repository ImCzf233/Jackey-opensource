package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Charsets.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\u001a\u0011\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b¨\u0006\u0004"}, m53d2 = {"charset", "Ljava/nio/charset/Charset;", "charsetName", "", "kotlin-stdlib"})
@JvmName(name = "CharsetsKt")
/* loaded from: Jackey Client b2.jar:kotlin/text/CharsetsKt.class */
public final class CharsetsKt {
    @InlineOnly
    private static final Charset charset(String charsetName) {
        Intrinsics.checkNotNullParameter(charsetName, "charsetName");
        Charset forName = Charset.forName(charsetName);
        Intrinsics.checkNotNullExpressionValue(forName, "forName(charsetName)");
        return forName;
    }
}
