package kotlin;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinVersion.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\bÂ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, m53d2 = {"Lkotlin/KotlinVersionCurrentValue;", "", "()V", PropertyDescriptor.GET, "Lkotlin/KotlinVersion;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/KotlinVersionCurrentValue.class */
final class KotlinVersionCurrentValue {
    @NotNull
    public static final KotlinVersionCurrentValue INSTANCE = new KotlinVersionCurrentValue();

    private KotlinVersionCurrentValue() {
    }

    @JvmStatic
    @NotNull
    public static final KotlinVersion get() {
        return new KotlinVersion(1, 6, 10);
    }
}
