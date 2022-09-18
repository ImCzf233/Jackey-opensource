package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;

/* compiled from: AutoSettingsCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand$execute$1.class */
final class AutoSettingsCommand$execute$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ String $url;
    final /* synthetic */ AutoSettingsCommand this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoSettingsCommand$execute$1(String $url, AutoSettingsCommand $receiver) {
        super(0);
        this.$url = $url;
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        try {
            String settings = HttpUtils.get(this.$url);
            this.this$0.chat("Applying settings...");
            SettingsUtils.INSTANCE.executeScript(settings);
            this.this$0.chat("§6Settings applied successfully");
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Updated Settings", Notification.Type.SUCCESS));
            this.this$0.playEdit();
        } catch (Exception exception) {
            exception.printStackTrace();
            this.this$0.chat("Failed to fetch auto settings.");
        }
    }
}
