package net.ccbluex.liquidbounce;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* compiled from: LiquidBounce.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/LiquidBounce$startClient$1.class */
public final class LiquidBounce$startClient$1 extends Lambda implements Functions<Unit> {
    public static final LiquidBounce$startClient$1 INSTANCE = new LiquidBounce$startClient$1();

    LiquidBounce$startClient$1() {
        super(0);
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        try {
            LiquidBounce.INSTANCE.getClientRichPresence().setup();
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable);
        }
    }
}
