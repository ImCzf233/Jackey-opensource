package kotlin;

import kotlin.internal.AccessibleLateinitPropertyLiteral;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty0;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\"#\u0010��\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b��\u0010\u0005¨\u0006\u0006"}, m53d2 = {"isInitialized", "", "Lkotlin/reflect/KProperty0;", "isInitialized$annotations", "(Lkotlin/reflect/KProperty0;)V", "(Lkotlin/reflect/KProperty0;)Z", "kotlin-stdlib"})
@JvmName(name = "LateinitKt")
/* renamed from: kotlin.LateinitKt */
/* loaded from: Jackey Client b2.jar:kotlin/LateinitKt.class */
public final class Lateinit {
    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void isInitialized$annotations(KProperty0 kProperty0) {
    }

    private static final boolean isInitialized(@AccessibleLateinitPropertyLiteral KProperty0<?> kProperty0) {
        Intrinsics.checkNotNullParameter(kProperty0, "<this>");
        throw new Standard("Implementation is intrinsic");
    }
}
