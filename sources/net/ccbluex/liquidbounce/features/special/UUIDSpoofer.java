package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UUIDSpoofer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0004H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\n"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/special/UUIDSpoofer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "spoofId", "", "getSpoofId", "()Ljava/lang/String;", "setSpoofId", "(Ljava/lang/String;)V", "getUUID", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/special/UUIDSpoofer.class */
public final class UUIDSpoofer extends MinecraftInstance {
    @NotNull
    public static final UUIDSpoofer INSTANCE = new UUIDSpoofer();
    @Nullable
    private static String spoofId;

    private UUIDSpoofer() {
    }

    @Nullable
    public final String getSpoofId() {
        return spoofId;
    }

    public final void setSpoofId(@Nullable String str) {
        spoofId = str;
    }

    @JvmStatic
    @NotNull
    public static final String getUUID() {
        String str;
        UUIDSpoofer uUIDSpoofer = INSTANCE;
        if (spoofId == null) {
            str = MinecraftInstance.f362mc.field_71449_j.func_148255_b();
        } else {
            UUIDSpoofer uUIDSpoofer2 = INSTANCE;
            str = spoofId;
            Intrinsics.checkNotNull(str);
        }
        String str2 = str;
        Intrinsics.checkNotNullExpressionValue(str2, "if (spoofId == null) mc.…n.playerID else spoofId!!");
        return StringsKt.replace$default(str2, "-", "", false, 4, (Object) null);
    }
}
