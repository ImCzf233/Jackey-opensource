package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.Metadata;
import kotlin.concurrent.Thread;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.CrackedAccount;
import me.liuli.elixir.account.MicrosoftAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.account.MojangAccount;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/* compiled from: GuiLoginIntoAccount.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018��2\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\bH\u0016J\u0018\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J \u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0016J\u0018\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0019H\u0016J \u0010\"\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0019H\u0016J\b\u0010$\u001a\u00020\u0012H\u0016J\b\u0010%\u001a\u00020\u0012H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n��R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\rX\u0082.¢\u0006\u0002\n��¨\u0006&"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiLoginIntoAccount;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "directLogin", "", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;Z)V", "addButton", "Lnet/minecraft/client/gui/GuiButton;", "clipboardButton", "getDirectLogin", "()Z", "password", "Lnet/minecraft/client/gui/GuiTextField;", "status", "", "username", "actionPerformed", "", "button", "checkAndAddAccount", "usernameText", "passwordText", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiLoginIntoAccount */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiLoginIntoAccount.class */
public final class GuiLoginIntoAccount extends GuiScreen {
    @NotNull
    private final GuiAltManager prevGui;
    private final boolean directLogin;
    private GuiButton addButton;
    private GuiButton clipboardButton;
    private GuiTextField username;
    private GuiTextField password;
    @NotNull
    private String status;

    public GuiLoginIntoAccount(@NotNull GuiAltManager prevGui, boolean directLogin) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
        this.directLogin = directLogin;
        this.status = "§7Idle...";
    }

    public /* synthetic */ GuiLoginIntoAccount(GuiAltManager guiAltManager, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(guiAltManager, (i & 2) != 0 ? false : z);
    }

    public final boolean getDirectLogin() {
        return this.directLogin;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        List list = this.field_146292_n;
        GuiButton it = new GuiButton(2, (this.field_146294_l / 2) - 100, 113, "Clipboard");
        this.clipboardButton = it;
        list.add(it);
        this.field_146292_n.add(new GuiButton(3, (this.field_146294_l / 2) - 100, 143, "Login with Microsoft"));
        List list2 = this.field_146292_n;
        GuiButton it2 = new GuiButton(1, (this.field_146294_l / 2) - 100, this.field_146295_m - 54, 98, 20, this.directLogin ? "Login" : "Add");
        this.addButton = it2;
        list2.add(it2);
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) + 2, this.field_146295_m - 54, 98, 20, "Back"));
        this.username = new GuiTextField(2, Fonts.font40, (this.field_146294_l / 2) - 100, 60, 200, 20);
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146195_b(true);
        GuiTextField guiTextField2 = this.username;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField2 = null;
        }
        guiTextField2.func_146203_f(Integer.MAX_VALUE);
        GameFontRenderer font40 = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(font40, "font40");
        this.password = new GuiPasswordField(3, font40, (this.field_146294_l / 2) - 100, 85, 200, 20);
        GuiTextField guiTextField3 = this.password;
        if (guiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiTextField3 = null;
        }
        guiTextField3.func_146203_f(Integer.MAX_VALUE);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        RenderUtils.drawRect(30.0f, 30.0f, this.field_146294_l - 30.0f, this.field_146295_m - 30.0f, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString(this.directLogin ? "Direct Login" : "Add Account", this.field_146294_l / 2.0f, 34.0f, 16777215);
        Fonts.font40.drawCenteredString("§7Login with Mojang", this.field_146294_l / 2.0f, 49.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, this.field_146294_l / 2.0f, this.field_146295_m - 64.0f, 16777215);
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146194_f();
        GuiTextField guiTextField2 = this.password;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiTextField2 = null;
        }
        guiTextField2.func_146194_f();
        GuiTextField guiTextField3 = this.username;
        if (guiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField3 = null;
        }
        String func_146179_b = guiTextField3.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b, "username.text");
        if (func_146179_b.length() == 0) {
            GuiTextField guiTextField4 = this.username;
            if (guiTextField4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("username");
                guiTextField4 = null;
            }
            if (!guiTextField4.func_146206_l()) {
                Fonts.font40.drawCenteredString("§7Username / E-Mail", (this.field_146294_l / 2) - 55, 66.0f, 16777215);
            }
        }
        GuiTextField guiTextField5 = this.password;
        if (guiTextField5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiTextField5 = null;
        }
        String func_146179_b2 = guiTextField5.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b2, "password.text");
        if (func_146179_b2.length() == 0) {
            GuiTextField guiTextField6 = this.password;
            if (guiTextField6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("password");
                guiTextField6 = null;
            }
            if (!guiTextField6.func_146206_l()) {
                Fonts.font40.drawCenteredString("§7Password", (this.field_146294_l / 2) - 74, 91.0f, 16777215);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    public void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 1:
                GuiTextField guiTextField = this.username;
                if (guiTextField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    guiTextField = null;
                }
                String usernameText = guiTextField.func_146179_b();
                GuiTextField guiTextField2 = this.password;
                if (guiTextField2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("password");
                    guiTextField2 = null;
                }
                String passwordText = guiTextField2.func_146179_b();
                Intrinsics.checkNotNullExpressionValue(usernameText, "usernameText");
                Intrinsics.checkNotNullExpressionValue(passwordText, "passwordText");
                checkAndAddAccount(usernameText, passwordText);
                return;
            case 2:
                try {
                    Object data = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    if (data == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    String clipboardData = (String) data;
                    List accountData = new Regex(CallSiteDescriptor.TOKEN_DELIMITER).split(clipboardData, 2);
                    if (!StringsKt.contains$default((CharSequence) clipboardData, (CharSequence) CallSiteDescriptor.TOKEN_DELIMITER, false, 2, (Object) null) || accountData.size() != 2) {
                        this.status = "§cInvalid clipboard data. (Use: E-Mail:Password)";
                        return;
                    } else {
                        checkAndAddAccount(accountData.get(0), accountData.get(1));
                        return;
                    }
                } catch (UnsupportedFlavorException e) {
                    this.status = "§cClipboard flavor unsupported!";
                    ClientUtils.getLogger().error("Failed to read data from clipboard.", e);
                    return;
                }
            case 3:
                MicrosoftAccount.Companion.buildFromOpenBrowser$default(MicrosoftAccount.Companion, new MicrosoftAccount.OAuthHandler() { // from class: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiLoginIntoAccount$actionPerformed$1
                    @Override // me.liuli.elixir.account.MicrosoftAccount.OAuthHandler
                    public void authError(@NotNull String error) {
                        Intrinsics.checkNotNullParameter(error, "error");
                        GuiLoginIntoAccount.this.status = Intrinsics.stringPlus("§c", error);
                    }

                    @Override // me.liuli.elixir.account.MicrosoftAccount.OAuthHandler
                    public void authResult(@NotNull MicrosoftAccount account) {
                        GuiAltManager guiAltManager;
                        String str;
                        GuiAltManager guiAltManager2;
                        Intrinsics.checkNotNullParameter(account, "account");
                        if (LiquidBounce.INSTANCE.getFileManager().accountsConfig.accountExists(account)) {
                            GuiLoginIntoAccount.this.status = "§cThe account has already been added.";
                            return;
                        }
                        LiquidBounce.INSTANCE.getFileManager().accountsConfig.addAccount(account);
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().accountsConfig);
                        GuiLoginIntoAccount.this.status = "§aThe account has been added.";
                        guiAltManager = GuiLoginIntoAccount.this.prevGui;
                        str = GuiLoginIntoAccount.this.status;
                        guiAltManager.setStatus(str);
                        Minecraft minecraft = GuiLoginIntoAccount.this.field_146297_k;
                        guiAltManager2 = GuiLoginIntoAccount.this.prevGui;
                        minecraft.func_147108_a(guiAltManager2);
                    }

                    @Override // me.liuli.elixir.account.MicrosoftAccount.OAuthHandler
                    public void openUrl(@NotNull String url) {
                        Intrinsics.checkNotNullParameter(url, "url");
                        MiscUtils.showURL(url);
                    }
                }, null, 2, null);
                return;
            default:
                return;
        }
    }

    public void func_73869_a(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
            case 1:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 15:
                GuiTextField[] guiTextFieldArr = new GuiTextField[2];
                GuiTextField guiTextField = this.username;
                if (guiTextField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    guiTextField = null;
                }
                guiTextFieldArr[0] = guiTextField;
                GuiTextField guiTextField2 = this.password;
                if (guiTextField2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("password");
                    guiTextField2 = null;
                }
                guiTextFieldArr[1] = guiTextField2;
                TabUtils.tab(guiTextFieldArr);
                return;
            case 28:
                GuiButton guiButton = this.addButton;
                if (guiButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("addButton");
                    guiButton = null;
                }
                func_146284_a(guiButton);
                return;
            default:
                GuiTextField guiTextField3 = this.username;
                if (guiTextField3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    guiTextField3 = null;
                }
                if (guiTextField3.func_146206_l()) {
                    GuiTextField guiTextField4 = this.username;
                    if (guiTextField4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("username");
                        guiTextField4 = null;
                    }
                    guiTextField4.func_146201_a(typedChar, keyCode);
                }
                GuiTextField guiTextField5 = this.password;
                if (guiTextField5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("password");
                    guiTextField5 = null;
                }
                if (guiTextField5.func_146206_l()) {
                    GuiTextField guiTextField6 = this.password;
                    if (guiTextField6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("password");
                        guiTextField6 = null;
                    }
                    guiTextField6.func_146201_a(typedChar, keyCode);
                }
                super.func_73869_a(typedChar, keyCode);
                return;
        }
    }

    public void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146192_a(mouseX, mouseY, mouseButton);
        GuiTextField guiTextField2 = this.password;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiTextField2 = null;
        }
        guiTextField2.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146178_a();
        GuiTextField guiTextField2 = this.password;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiTextField2 = null;
        }
        guiTextField2.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }

    private final void checkAndAddAccount(String usernameText, String passwordText) {
        MojangAccount mojangAccount;
        if (usernameText.length() == 0) {
            return;
        }
        if (passwordText.length() == 0) {
            CrackedAccount crackedAccount = new CrackedAccount();
            crackedAccount.setName(usernameText);
            mojangAccount = crackedAccount;
        } else {
            MojangAccount mojangAccount2 = new MojangAccount();
            mojangAccount2.setEmail(usernameText);
            mojangAccount2.setPassword(passwordText);
            mojangAccount = mojangAccount2;
        }
        MinecraftAccount minecraftAccount = mojangAccount;
        if (LiquidBounce.INSTANCE.getFileManager().accountsConfig.accountExists(minecraftAccount)) {
            this.status = "§cThe account has already been added.";
            return;
        }
        GuiButton guiButton = this.clipboardButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clipboardButton");
            guiButton = null;
        }
        guiButton.field_146124_l = false;
        GuiButton guiButton2 = this.addButton;
        if (guiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("addButton");
            guiButton2 = null;
        }
        guiButton2.field_146124_l = false;
        Thread.thread$default(false, false, null, "Account-Checking-Task", 0, new GuiLoginIntoAccount$checkAndAddAccount$1(minecraftAccount, this), 23, null);
    }
}
