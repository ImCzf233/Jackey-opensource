package net.ccbluex.liquidbounce.p004ui.client;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import net.ccbluex.liquidbounce.discord.ClientRichPresence;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* compiled from: GuiModsMenu.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.GuiModsMenu$actionPerformed$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiModsMenu$actionPerformed$1.class */
final class GuiModsMenu$actionPerformed$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Ref.BooleanRef $value;
    final /* synthetic */ ClientRichPresence $rpc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiModsMenu$actionPerformed$1(Ref.BooleanRef $value, ClientRichPresence $rpc) {
        super(0);
        this.$value = $value;
        this.$rpc = $rpc;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        boolean z;
        Ref.BooleanRef booleanRef = this.$value;
        try {
            booleanRef = booleanRef;
            this.$rpc.setup();
            z = true;
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable);
            z = false;
        }
        booleanRef.element = z;
    }
}
