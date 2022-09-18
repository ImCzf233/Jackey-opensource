package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.hud.Config;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;

/* compiled from: ThemeCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/ThemeCommand$execute$1.class */
final class ThemeCommand$execute$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ThemeCommand this$0;
    final /* synthetic */ String $url;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThemeCommand$execute$1(ThemeCommand $receiver, String $url) {
        super(0);
        this.this$0 = $receiver;
        this.$url = $url;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        try {
            this.this$0.chat("§9Loading theme...");
            String theme = HttpUtils.get(this.$url);
            this.this$0.chat("§9Set theme settings...");
            LiquidBounce.INSTANCE.setStarting(true);
            LiquidBounce.INSTANCE.getHud().clearElements();
            LiquidBounce.INSTANCE.setHud(new Config(theme).toHUD());
            LiquidBounce.INSTANCE.setStarting(false);
            this.this$0.chat("§6Theme applied successfully.");
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Updated HUD Theme.", Notification.Type.SUCCESS));
            this.this$0.playEdit();
        } catch (Exception exception) {
            exception.printStackTrace();
            this.this$0.chat("Failed to fetch theme.");
        }
    }
}
