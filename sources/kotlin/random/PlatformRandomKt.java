package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformRandom.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u001e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010��\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H��\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007¨\u0006\n"}, m53d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/random/PlatformRandomKt.class */
public final class PlatformRandomKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final java.util.Random asJavaRandom(@NotNull Random $this$asJavaRandom) {
        Intrinsics.checkNotNullParameter($this$asJavaRandom, "<this>");
        AbstractPlatformRandom abstractPlatformRandom = $this$asJavaRandom instanceof AbstractPlatformRandom ? (AbstractPlatformRandom) $this$asJavaRandom : null;
        return abstractPlatformRandom == null ? new KotlinRandom($this$asJavaRandom) : abstractPlatformRandom.getImpl();
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random asKotlinRandom(@NotNull java.util.Random $this$asKotlinRandom) {
        Intrinsics.checkNotNullParameter($this$asKotlinRandom, "<this>");
        KotlinRandom kotlinRandom = $this$asKotlinRandom instanceof KotlinRandom ? (KotlinRandom) $this$asKotlinRandom : null;
        return kotlinRandom == null ? new PlatformRandom($this$asKotlinRandom) : kotlinRandom.getImpl();
    }

    @InlineOnly
    private static final Random defaultPlatformRandom() {
        return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
    }

    public static final double doubleFromParts(int hi26, int low27) {
        return ((hi26 << 27) + low27) / 9.007199254740992E15d;
    }
}
