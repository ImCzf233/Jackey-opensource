package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus;

import java.util.List;
import kotlin.Metadata;
import kotlin.concurrent.Thread;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/* compiled from: GuiSessionLogin.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0014J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0014J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0014J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001d"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiSessionLogin;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "loginButton", "Lnet/minecraft/client/gui/GuiButton;", "sessionTokenField", "Lnet/minecraft/client/gui/GuiTextField;", "status", "", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiSessionLogin */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiSessionLogin.class */
public final class GuiSessionLogin extends GuiScreen {
    @NotNull
    private final GuiAltManager prevGui;
    private GuiButton loginButton;
    private GuiTextField sessionTokenField;
    @NotNull
    private String status = "";

    public GuiSessionLogin(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.loginButton = new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 96, "Login");
        List list = this.field_146292_n;
        GuiButton guiButton = this.loginButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            guiButton = null;
        }
        list.add(guiButton);
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 120, "Back"));
        this.sessionTokenField = new GuiTextField(666, Fonts.font40, (this.field_146294_l / 2) - 100, 80, 200, 20);
        GuiTextField guiTextField = this.sessionTokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField = null;
        }
        guiTextField.func_146195_b(true);
        GuiTextField guiTextField2 = this.sessionTokenField;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField2 = null;
        }
        guiTextField2.func_146203_f(Integer.MAX_VALUE);
        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }
        super.func_73866_w_();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        RenderUtils.drawRect(30.0f, 30.0f, this.field_146294_l - 30.0f, this.field_146295_m - 30.0f, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("Session Login", this.field_146294_l / 2.0f, 36.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, this.field_146294_l / 2.0f, (this.field_146295_m / 4.0f) + 80.0f, 16777215);
        GuiTextField guiTextField = this.sessionTokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField = null;
        }
        guiTextField.func_146194_f();
        Fonts.font40.drawCenteredString("§7Session Token:", (this.field_146294_l / 2.0f) - 65.0f, 66.0f, 16777215);
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
                this.status = "§aLogging in...";
                Thread.thread$default(false, false, null, null, 0, new GuiSessionLogin$actionPerformed$1(this), 31, null);
                return;
            default:
                return;
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
            return;
        }
        GuiTextField guiTextField = this.sessionTokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField = null;
        }
        if (guiTextField.func_146206_l()) {
            GuiTextField guiTextField2 = this.sessionTokenField;
            if (guiTextField2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
                guiTextField2 = null;
            }
            guiTextField2.func_146201_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        GuiTextField guiTextField = this.sessionTokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField = null;
        }
        guiTextField.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        GuiTextField guiTextField = this.sessionTokenField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            guiTextField = null;
        }
        guiTextField.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        super.func_146281_b();
    }
}
