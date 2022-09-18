package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: StringBuilderJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\bÂ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��¨\u0006\u0005"}, m53d2 = {"Lkotlin/text/SystemProperties;", "", "()V", "LINE_SEPARATOR", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/text/SystemProperties.class */
final class SystemProperties {
    @NotNull
    public static final SystemProperties INSTANCE = new SystemProperties();
    @JvmPlatformAnnotations
    @NotNull
    public static final String LINE_SEPARATOR;

    private SystemProperties() {
    }

    static {
        String property = System.getProperty("line.separator");
        Intrinsics.checkNotNull(property);
        LINE_SEPARATOR = property;
    }
}
