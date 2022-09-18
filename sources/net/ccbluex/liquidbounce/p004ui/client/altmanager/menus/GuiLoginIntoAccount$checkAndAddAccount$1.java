package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus;

import com.thealtening.AltService;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import me.liuli.elixir.account.MinecraftAccount;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.Session;

/* compiled from: GuiLoginIntoAccount.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiLoginIntoAccount$checkAndAddAccount$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiLoginIntoAccount$checkAndAddAccount$1.class */
public final class GuiLoginIntoAccount$checkAndAddAccount$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ MinecraftAccount $minecraftAccount;
    final /* synthetic */ GuiLoginIntoAccount this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiLoginIntoAccount$checkAndAddAccount$1(MinecraftAccount $minecraftAccount, GuiLoginIntoAccount $receiver) {
        super(0);
        this.$minecraftAccount = $minecraftAccount;
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        GuiButton guiButton;
        GuiButton guiButton2;
        GuiAltManager guiAltManager;
        String str;
        GuiAltManager guiAltManager2;
        try {
            AltService.EnumAltService oldService = GuiAltManager.Companion.getAltService().getCurrentService();
            if (oldService != AltService.EnumAltService.MOJANG) {
                GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.MOJANG);
            }
            this.$minecraftAccount.update();
            if (this.this$0.getDirectLogin()) {
                Minecraft.func_71410_x().field_71449_j = new Session(this.$minecraftAccount.getSession().getUsername(), this.$minecraftAccount.getSession().getUuid(), this.$minecraftAccount.getSession().getToken(), "mojang");
                this.this$0.status = "§aLogged into " + ((Object) this.this$0.field_146297_k.field_71449_j.func_111285_a()) + '.';
            } else {
                LiquidBounce.INSTANCE.getFileManager().accountsConfig.addAccount(this.$minecraftAccount);
                LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().accountsConfig);
                this.this$0.status = "§aThe account has been added.";
            }
            guiAltManager = this.this$0.prevGui;
            str = this.this$0.status;
            guiAltManager.setStatus(str);
            Minecraft minecraft = this.this$0.field_146297_k;
            guiAltManager2 = this.this$0.prevGui;
            minecraft.func_147108_a(guiAltManager2);
        } catch (Exception e) {
            this.this$0.status = Intrinsics.stringPlus("§c", e.getMessage());
            guiButton = this.this$0.clipboardButton;
            if (guiButton == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clipboardButton");
                guiButton = null;
            }
            guiButton.field_146124_l = true;
            guiButton2 = this.this$0.addButton;
            if (guiButton2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("addButton");
                guiButton2 = null;
            }
            guiButton2.field_146124_l = true;
        }
    }
}
