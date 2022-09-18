package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.account.MinecraftAccount;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiLoginProgress.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\u0018��2\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0016\u0010\u0007\u001a\u0012\u0012\b\u0012\u00060\tj\u0002`\n\u0012\u0004\u0012\u00020\u00060\b\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\fJ \u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016¨\u0006\u0013"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiLoginProgress;", "Lnet/minecraft/client/gui/GuiScreen;", "minecraftAccount", "Lme/liuli/elixir/account/MinecraftAccount;", "success", "Lkotlin/Function0;", "", "error", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "done", "(Lme/liuli/elixir/account/MinecraftAccount;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiLoginProgress */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiLoginProgress.class */
public final class GuiLoginProgress extends GuiScreen {
    public GuiLoginProgress(@NotNull MinecraftAccount minecraftAccount, @NotNull Functions<Unit> success, @NotNull Function1<? super Exception, Unit> error, @NotNull Functions<Unit> done) {
        Intrinsics.checkNotNullParameter(minecraftAccount, "minecraftAccount");
        Intrinsics.checkNotNullParameter(success, "success");
        Intrinsics.checkNotNullParameter(error, "error");
        Intrinsics.checkNotNullParameter(done, "done");
        GuiAltManager.Companion.login(minecraftAccount, success, error, done);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        new ScaledResolution(Minecraft.func_71410_x());
        func_146276_q_();
        func_73732_a(this.field_146289_q, "Logging into account...", this.field_146294_l / 2, (this.field_146295_m / 2) - 60, 16777215);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
}
