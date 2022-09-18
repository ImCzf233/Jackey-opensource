package net.ccbluex.liquidbounce.p004ui.client.altmanager;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: GuiAltManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$actionPerformed$3 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$actionPerformed$3.class */
final class GuiAltManager$actionPerformed$3 extends Lambda implements Functions<Unit> {
    final /* synthetic */ GuiAltManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiAltManager$actionPerformed$3(GuiAltManager $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        this.this$0.setStatus("§aLogged in as " + ((Object) this.this$0.field_146297_k.field_71449_j.func_111285_a()) + '.');
    }
}
