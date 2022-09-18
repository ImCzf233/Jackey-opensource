package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.altgenerator;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.util.List;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/* compiled from: GuiTheAltening.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018�� \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0014J \u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0014J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012H\u0014J\b\u0010\u001d\u001a\u00020\u000eH\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n��¨\u0006 "}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/menus/altgenerator/GuiTheAltening;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "apiKeyField", "Lnet/minecraft/client/gui/GuiTextField;", "generateButton", "Lnet/minecraft/client/gui/GuiButton;", "loginButton", "status", "", "tokenField", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.altgenerator.GuiTheAltening */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/altgenerator/GuiTheAltening.class */
public final class GuiTheAltening extends GuiScreen {
    @NotNull
    private final GuiAltManager prevGui;
    private GuiButton loginButton;
    private GuiButton generateButton;
    private GuiTextField apiKeyField;
    private GuiTextField tokenField;
    @NotNull
    private String status = "";
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static String apiKey = "";

    public GuiTheAltening(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    /* compiled from: GuiTheAltening.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/menus/altgenerator/GuiTheAltening$Companion;", "", "()V", "apiKey", "", "getApiKey", "()Ljava/lang/String;", "setApiKey", "(Ljava/lang/String;)V", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.altgenerator.GuiTheAltening$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/altgenerator/GuiTheAltening$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final String getApiKey() {
            return GuiTheAltening.apiKey;
        }

        public final void setApiKey(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            GuiTheAltening.apiKey = str;
        }
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.loginButton = new GuiButton(2, (this.field_146294_l / 2) - 100, 75, "Login");
        List list = this.field_146292_n;
        GuiButton guiButton = this.loginButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            guiButton = null;
        }
        list.add(guiButton);
        this.generateButton = new GuiButton(1, (this.field_146294_l / 2) - 100, 140, "Generate");
        List list2 = this.field_146292_n;
        GuiButton guiButton2 = this.generateButton;
        if (guiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
            guiButton2 = null;
        }
        list2.add(guiButton2);
        this.field_146292_n.add(new GuiButton(3, (this.field_146294_l / 2) - 100, this.field_146295_m - 54, 98, 20, "Buy"));
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) + 2, this.field_146295_m - 54, 98, 20, "Back"));
        this.tokenField = new GuiTextField(666, Fonts.font40, (this.field_146294_l / 2) - 100, 50, 200, 20);
        GuiTextField guiTextField = this.tokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            guiTextField = null;
        }
        guiTextField.func_146195_b(true);
        GuiTextField guiTextField2 = this.tokenField;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            guiTextField2 = null;
        }
        guiTextField2.func_146203_f(Integer.MAX_VALUE);
        GameFontRenderer font40 = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(font40, "font40");
        this.apiKeyField = new GuiPasswordField(1337, font40, (this.field_146294_l / 2) - 100, 115, 200, 20);
        GuiTextField guiTextField3 = this.apiKeyField;
        if (guiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField3 = null;
        }
        guiTextField3.func_146203_f(18);
        GuiTextField guiTextField4 = this.apiKeyField;
        if (guiTextField4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField4 = null;
        }
        guiTextField4.func_146180_a(apiKey);
        super.func_73866_w_();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        RenderUtils.drawRect(30.0f, 30.0f, this.field_146294_l - 30.0f, this.field_146295_m - 30.0f, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("TheAltening", this.field_146294_l / 2.0f, 6.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, this.field_146294_l / 2.0f, 18.0f, 16777215);
        GuiTextField guiTextField = this.apiKeyField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField = null;
        }
        guiTextField.func_146194_f();
        GuiTextField guiTextField2 = this.tokenField;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            guiTextField2 = null;
        }
        guiTextField2.func_146194_f();
        Fonts.font40.drawCenteredString("§7Token:", (this.field_146294_l / 2.0f) - 84, 40.0f, 16777215);
        Fonts.font40.drawCenteredString("§7API-Key:", (this.field_146294_l / 2.0f) - 78, 105.0f, 16777215);
        Fonts.font40.drawCenteredString("§7Use coupon code 'liquidbounce' for 20% off!", this.field_146294_l / 2.0f, this.field_146295_m - 65.0f, 16777215);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 1:
                GuiButton guiButton = this.loginButton;
                if (guiButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                    guiButton = null;
                }
                guiButton.field_146124_l = false;
                GuiButton guiButton2 = this.generateButton;
                if (guiButton2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generateButton");
                    guiButton2 = null;
                }
                guiButton2.field_146124_l = false;
                Companion companion = Companion;
                GuiTextField guiTextField = this.apiKeyField;
                if (guiTextField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
                    guiTextField = null;
                }
                String func_146179_b = guiTextField.func_146179_b();
                Intrinsics.checkNotNullExpressionValue(func_146179_b, "apiKeyField.text");
                apiKey = func_146179_b;
                TheAltening altening = new TheAltening(apiKey);
                TheAltening.Asynchronous asynchronous = new TheAltening.Asynchronous(altening);
                this.status = "§cGenerating account...";
                asynchronous.getAccountData().thenAccept((v1) -> {
                    m2836actionPerformed$lambda0(r1, v1);
                }).handle((v1, v2) -> {
                    return m2837actionPerformed$lambda1(r1, v1, v2);
                }).whenComplete((BiConsumer<? super U, ? super Throwable>) (v1, v2) -> {
                    m2838actionPerformed$lambda2(r1, v1, v2);
                });
                return;
            case 2:
                GuiButton guiButton3 = this.loginButton;
                if (guiButton3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                    guiButton3 = null;
                }
                guiButton3.field_146124_l = false;
                GuiButton guiButton4 = this.generateButton;
                if (guiButton4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generateButton");
                    guiButton4 = null;
                }
                guiButton4.field_146124_l = false;
                new Thread(() -> {
                    m2839actionPerformed$lambda3(r2);
                }).start();
                return;
            case 3:
                MiscUtils.showURL("https://thealtening.com/?ref=liquidbounce");
                return;
            default:
                return;
        }
    }

    /* renamed from: actionPerformed$lambda-0 */
    private static final void m2836actionPerformed$lambda0(GuiTheAltening this$0, AccountData account) {
        String str;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.status = Intrinsics.stringPlus("§aGenerated account: §b§l", account.getUsername());
        try {
            this$0.status = "§cSwitching Alt Service...";
            GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.THEALTENING);
            this$0.status = "§cLogging in...";
            YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
            yggdrasilUserAuthentication.setUsername(account.getToken());
            yggdrasilUserAuthentication.setPassword(LiquidBounce.CLIENT_NAME);
            GuiTheAltening guiTheAltening = this$0;
            try {
                guiTheAltening = guiTheAltening;
                yggdrasilUserAuthentication.logIn();
                this$0.field_146297_k.field_71449_j = new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                this$0.prevGui.setStatus("§aYour name is now §b§l" + ((Object) yggdrasilUserAuthentication.getSelectedProfile().getName()) + "§c.");
                this$0.field_146297_k.func_147108_a(this$0.prevGui);
                str = "";
            } catch (AuthenticationException e) {
                GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.MOJANG);
                ClientUtils.getLogger().error("Failed to login.", e);
                str = Intrinsics.stringPlus("§cFailed to login: ", e.getMessage());
            }
            guiTheAltening.status = str;
        } catch (Throwable throwable) {
            this$0.status = "§cFailed to login. Unknown error.";
            ClientUtils.getLogger().error("Failed to login.", throwable);
        }
        GuiButton guiButton = this$0.loginButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            guiButton = null;
        }
        guiButton.field_146124_l = true;
        GuiButton guiButton2 = this$0.generateButton;
        if (guiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
            guiButton2 = null;
        }
        guiButton2.field_146124_l = true;
    }

    /* renamed from: actionPerformed$lambda-1 */
    private static final Unit m2837actionPerformed$lambda1(GuiTheAltening this$0, Void $noName_0, Throwable err) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.status = "§cFailed to generate account.";
        ClientUtils.getLogger().error("Failed to generate account.", err);
        return Unit.INSTANCE;
    }

    /* renamed from: actionPerformed$lambda-2 */
    private static final void m2838actionPerformed$lambda2(GuiTheAltening this$0, Unit $noName_0, Throwable $noName_1) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        GuiButton guiButton = this$0.loginButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            guiButton = null;
        }
        guiButton.field_146124_l = true;
        GuiButton guiButton2 = this$0.generateButton;
        if (guiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
            guiButton2 = null;
        }
        guiButton2.field_146124_l = true;
    }

    /* renamed from: actionPerformed$lambda-3 */
    private static final void m2839actionPerformed$lambda3(GuiTheAltening this$0) {
        String str;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            this$0.status = "§cSwitching Alt Service...";
            GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.THEALTENING);
            this$0.status = "§cLogging in...";
            YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
            GuiTextField guiTextField = this$0.tokenField;
            if (guiTextField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                guiTextField = null;
            }
            yggdrasilUserAuthentication.setUsername(guiTextField.func_146179_b());
            yggdrasilUserAuthentication.setPassword(LiquidBounce.CLIENT_NAME);
            GuiTheAltening guiTheAltening = this$0;
            try {
                guiTheAltening = guiTheAltening;
                yggdrasilUserAuthentication.logIn();
                this$0.field_146297_k.field_71449_j = new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                this$0.prevGui.setStatus("§aYour name is now §b§l" + ((Object) yggdrasilUserAuthentication.getSelectedProfile().getName()) + "§c.");
                this$0.field_146297_k.func_147108_a(this$0.prevGui);
                str = "§aYour name is now §b§l" + ((Object) yggdrasilUserAuthentication.getSelectedProfile().getName()) + "§c.";
            } catch (AuthenticationException e) {
                GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.MOJANG);
                ClientUtils.getLogger().error("Failed to login.", e);
                str = Intrinsics.stringPlus("§cFailed to login: ", e.getMessage());
            }
            guiTheAltening.status = str;
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to login.", throwable);
            this$0.status = "§cFailed to login. Unknown error.";
        }
        GuiButton guiButton = this$0.loginButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            guiButton = null;
        }
        guiButton.field_146124_l = true;
        GuiButton guiButton2 = this$0.generateButton;
        if (guiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
            guiButton2 = null;
        }
        guiButton2.field_146124_l = true;
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
            return;
        }
        GuiTextField guiTextField = this.apiKeyField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField = null;
        }
        if (guiTextField.func_146206_l()) {
            GuiTextField guiTextField2 = this.apiKeyField;
            if (guiTextField2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
                guiTextField2 = null;
            }
            guiTextField2.func_146201_a(typedChar, keyCode);
        }
        GuiTextField guiTextField3 = this.tokenField;
        if (guiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            guiTextField3 = null;
        }
        if (guiTextField3.func_146206_l()) {
            GuiTextField guiTextField4 = this.tokenField;
            if (guiTextField4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                guiTextField4 = null;
            }
            guiTextField4.func_146201_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        GuiTextField guiTextField = this.apiKeyField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField = null;
        }
        guiTextField.func_146192_a(mouseX, mouseY, mouseButton);
        GuiTextField guiTextField2 = this.tokenField;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            guiTextField2 = null;
        }
        guiTextField2.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        GuiTextField guiTextField = this.apiKeyField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField = null;
        }
        guiTextField.func_146178_a();
        GuiTextField guiTextField2 = this.tokenField;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            guiTextField2 = null;
        }
        guiTextField2.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        Companion companion = Companion;
        GuiTextField guiTextField = this.apiKeyField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            guiTextField = null;
        }
        String func_146179_b = guiTextField.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b, "apiKeyField.text");
        apiKey = func_146179_b;
        super.func_146281_b();
    }
}
