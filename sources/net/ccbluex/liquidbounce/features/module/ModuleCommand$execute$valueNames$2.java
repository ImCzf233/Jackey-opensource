package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\r\n��\n\u0002\u0018\u0002\n��\u0010��\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\n¢\u0006\u0002\b\u0004"}, m53d2 = {"<anonymous>", "", "it", "Lnet/ccbluex/liquidbounce/value/Value;", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/ModuleCommand$execute$valueNames$2.class */
final class ModuleCommand$execute$valueNames$2 extends Lambda implements Function1<Value<?>, CharSequence> {
    public static final ModuleCommand$execute$valueNames$2 INSTANCE = new ModuleCommand$execute$valueNames$2();

    ModuleCommand$execute$valueNames$2() {
        super(1);
    }

    @NotNull
    public final CharSequence invoke(@NotNull Value<?> it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String lowerCase = it.getName().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        return lowerCase;
    }
}
