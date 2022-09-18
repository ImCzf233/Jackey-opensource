package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Fucker.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Fucker$resetOnWorldValue$1.class */
final class Fucker$resetOnWorldValue$1 extends Lambda implements Functions<Boolean> {
    public static final Fucker$resetOnWorldValue$1 INSTANCE = new Fucker$resetOnWorldValue$1();

    Fucker$resetOnWorldValue$1() {
        super(0);
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    public final Boolean invoke() {
        BoolValue boolValue;
        boolValue = Fucker.ignoreFirstBlockValue;
        return boolValue.get();
    }
}
