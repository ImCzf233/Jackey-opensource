package net.ccbluex.liquidbounce.p004ui.client.altmanager;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.minecraft.client.Minecraft;

/* compiled from: GuiAltManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$GuiList$elementClicked$1$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList$elementClicked$1$1.class */
public final class GuiAltManager$GuiList$elementClicked$1$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ GuiAltManager this$0;
    final /* synthetic */ GuiAltManager.GuiList this$1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiAltManager$GuiList$elementClicked$1$1(GuiAltManager $receiver, GuiAltManager.GuiList $receiver2) {
        super(0);
        this.this$0 = $receiver;
        this.this$1 = $receiver2;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Minecraft minecraft;
        GuiAltManager guiAltManager = this.this$0;
        StringBuilder append = new StringBuilder().append("§aLogged into ");
        minecraft = this.this$1.field_148161_k;
        guiAltManager.setStatus(append.append((Object) minecraft.field_71449_j.func_111285_a()).append('.').toString());
    }
}
