package net.ccbluex.liquidbounce.p004ui.client.altmanager;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import me.liuli.elixir.account.MicrosoftAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.account.MojangAccount;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiAltManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0010\n��\n\u0002\u0010\r\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010��\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, m53d2 = {"<anonymous>", "", "account", "Lme/liuli/elixir/account/MinecraftAccount;", "kotlin.jvm.PlatformType", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$actionPerformed$accounts$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$actionPerformed$accounts$1.class */
final class GuiAltManager$actionPerformed$accounts$1 extends Lambda implements Function1<MinecraftAccount, CharSequence> {
    public static final GuiAltManager$actionPerformed$accounts$1 INSTANCE = new GuiAltManager$actionPerformed$accounts$1();

    GuiAltManager$actionPerformed$accounts$1() {
        super(1);
    }

    @NotNull
    public final CharSequence invoke(MinecraftAccount account) {
        return account instanceof MojangAccount ? ((MojangAccount) account).getEmail() + ':' + ((MojangAccount) account).getPassword() : account instanceof MicrosoftAccount ? ((MicrosoftAccount) account).getName() + ':' + account.getSession().getToken() : account.getName();
    }
}
