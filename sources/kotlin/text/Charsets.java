package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Charsets.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0011\u0010\t\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0014"}, m53d2 = {"Lkotlin/text/Charsets;", "", "()V", "ISO_8859_1", "Ljava/nio/charset/Charset;", "US_ASCII", "UTF_16", "UTF_16BE", "UTF_16LE", "UTF_32", "UTF32", "()Ljava/nio/charset/Charset;", "UTF_32BE", "UTF32_BE", "UTF_32LE", "UTF32_LE", "UTF_8", "utf_32", "utf_32be", "utf_32le", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/text/Charsets.class */
public final class Charsets {
    @NotNull
    public static final Charsets INSTANCE = new Charsets();
    @JvmPlatformAnnotations
    @NotNull
    public static final Charset UTF_8;
    @JvmPlatformAnnotations
    @NotNull
    public static final Charset UTF_16;
    @JvmPlatformAnnotations
    @NotNull
    public static final Charset UTF_16BE;
    @JvmPlatformAnnotations
    @NotNull
    public static final Charset UTF_16LE;
    @JvmPlatformAnnotations
    @NotNull
    public static final Charset US_ASCII;
    @JvmPlatformAnnotations
    @NotNull
    public static final Charset ISO_8859_1;
    @Nullable
    private static Charset utf_32;
    @Nullable
    private static Charset utf_32le;
    @Nullable
    private static Charset utf_32be;

    private Charsets() {
    }

    static {
        Charset forName = Charset.forName("UTF-8");
        Intrinsics.checkNotNullExpressionValue(forName, "forName(\"UTF-8\")");
        UTF_8 = forName;
        Charset forName2 = Charset.forName("UTF-16");
        Intrinsics.checkNotNullExpressionValue(forName2, "forName(\"UTF-16\")");
        UTF_16 = forName2;
        Charset forName3 = Charset.forName("UTF-16BE");
        Intrinsics.checkNotNullExpressionValue(forName3, "forName(\"UTF-16BE\")");
        UTF_16BE = forName3;
        Charset forName4 = Charset.forName("UTF-16LE");
        Intrinsics.checkNotNullExpressionValue(forName4, "forName(\"UTF-16LE\")");
        UTF_16LE = forName4;
        Charset forName5 = Charset.forName("US-ASCII");
        Intrinsics.checkNotNullExpressionValue(forName5, "forName(\"US-ASCII\")");
        US_ASCII = forName5;
        Charset forName6 = Charset.forName("ISO-8859-1");
        Intrinsics.checkNotNullExpressionValue(forName6, "forName(\"ISO-8859-1\")");
        ISO_8859_1 = forName6;
    }

    @JvmName(name = "UTF32")
    @NotNull
    public final Charset UTF32() {
        Charset charset = utf_32;
        if (charset != null) {
            return charset;
        }
        Charset charset2 = Charset.forName("UTF-32");
        Intrinsics.checkNotNullExpressionValue(charset2, "forName(\"UTF-32\")");
        utf_32 = charset2;
        return charset2;
    }

    @JvmName(name = "UTF32_LE")
    @NotNull
    public final Charset UTF32_LE() {
        Charset charset = utf_32le;
        if (charset != null) {
            return charset;
        }
        Charset charset2 = Charset.forName("UTF-32LE");
        Intrinsics.checkNotNullExpressionValue(charset2, "forName(\"UTF-32LE\")");
        utf_32le = charset2;
        return charset2;
    }

    @JvmName(name = "UTF32_BE")
    @NotNull
    public final Charset UTF32_BE() {
        Charset charset = utf_32be;
        if (charset != null) {
            return charset;
        }
        Charset charset2 = Charset.forName("UTF-32BE");
        Intrinsics.checkNotNullExpressionValue(charset2, "forName(\"UTF-32BE\")");
        utf_32be = charset2;
        return charset2;
    }
}
