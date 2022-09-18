package net.ccbluex.liquidbounce.p004ui.client.altmanager;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiAltManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0012\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\u0010��\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004H\n¢\u0006\u0002\b\u0005"}, m53d2 = {"<anonymous>", "", "exception", "Ljava/lang/Exception;", "Lkotlin/Exception;", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$actionPerformed$4 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$actionPerformed$4.class */
final class GuiAltManager$actionPerformed$4 extends Lambda implements Function1<Exception, Unit> {
    final /* synthetic */ GuiAltManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiAltManager$actionPerformed$4(GuiAltManager $receiver) {
        super(1);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Exception exc) {
        invoke2(exc);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke */
    public final void invoke2(@NotNull Exception exception) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        this.this$0.setStatus("§cLogin failed due to '" + ((Object) exception.getMessage()) + "'.");
    }
}
