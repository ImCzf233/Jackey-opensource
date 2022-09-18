package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus;

import com.thealtening.AltService;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

/* compiled from: GuiSessionLogin.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiSessionLogin$actionPerformed$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiSessionLogin$actionPerformed$1.class */
final class GuiSessionLogin$actionPerformed$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ GuiSessionLogin this$0;

    /* compiled from: GuiSessionLogin.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiSessionLogin$actionPerformed$1$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiSessionLogin$actionPerformed$1$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoginUtils.LoginResult.values().length];
            iArr[LoginUtils.LoginResult.LOGGED.ordinal()] = 1;
            iArr[LoginUtils.LoginResult.FAILED_PARSE_TOKEN.ordinal()] = 2;
            iArr[LoginUtils.LoginResult.INVALID_ACCOUNT_DATA.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiSessionLogin$actionPerformed$1(GuiSessionLogin $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        GuiTextField guiTextField;
        ?? r5;
        String str;
        GuiButton guiButton;
        guiTextField = this.this$0.sessionTokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField = null;
        }
        String func_146179_b = guiTextField.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b, "sessionTokenField.text");
        LoginUtils.LoginResult loginResult = LoginUtils.loginSessionId(func_146179_b);
        GuiSessionLogin guiSessionLogin = this.this$0;
        switch (WhenMappings.$EnumSwitchMapping$0[loginResult.ordinal()]) {
            case 1:
                ?? r52 = this;
                if (GuiAltManager.Companion.getAltService().getCurrentService() != AltService.EnumAltService.MOJANG) {
                    try {
                        guiSessionLogin = guiSessionLogin;
                        GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.MOJANG);
                        this = this;
                    } catch (IllegalAccessException e) {
                        IllegalAccessException illegalAccessException = e;
                        ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", illegalAccessException);
                        r52 = illegalAccessException;
                    } catch (NoSuchFieldException e2) {
                        ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", e2);
                        this = this;
                    }
                }
                str = "§cYour name is now §f§l" + ((Object) r52.this$0.field_146297_k.field_71449_j.func_111285_a()) + "§c";
                r5 = r52;
                break;
            case 2:
                str = "§cFailed to parse Session ID!";
                this = this;
                break;
            case 3:
                str = "§cInvalid Session ID!";
                this = this;
                break;
            default:
                str = "";
                this = this;
                break;
        }
        guiSessionLogin.status = str;
        guiButton = r5.this$0.loginButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            guiButton = null;
        }
        guiButton.field_146124_l = true;
    }
}
