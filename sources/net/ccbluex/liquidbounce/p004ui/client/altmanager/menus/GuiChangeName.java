package net.ccbluex.liquidbounce.p004ui.client.altmanager.menus;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

/* compiled from: GuiChangeName.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J \u0010\u0018\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000fH\u0016J\b\u0010\u001a\u001a\u00020\nH\u0016J\b\u0010\u001b\u001a\u00020\nH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001c"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiChangeName;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "name", "Lnet/minecraft/client/gui/GuiTextField;", "status", "", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.menus.GuiChangeName */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/menus/GuiChangeName.class */
public final class GuiChangeName extends GuiScreen {
    @NotNull
    private final GuiAltManager prevGui;
    @Nullable
    private GuiTextField name;
    @Nullable
    private String status;

    public GuiChangeName(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.field_146292_n.add(new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 96, "Change"));
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 120, "Back"));
        this.name = new GuiTextField(2, Fonts.font40, (this.field_146294_l / 2) - 100, 60, 200, 20);
        GuiTextField guiTextField = this.name;
        Intrinsics.checkNotNull(guiTextField);
        guiTextField.func_146195_b(true);
        GuiTextField guiTextField2 = this.name;
        Intrinsics.checkNotNull(guiTextField2);
        guiTextField2.func_146180_a(this.field_146297_k.func_110432_I().func_111285_a());
        GuiTextField guiTextField3 = this.name;
        Intrinsics.checkNotNull(guiTextField3);
        guiTextField3.func_146203_f(16);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        RenderUtils.drawRect(30.0f, 30.0f, this.field_146294_l - 30.0f, this.field_146295_m - 30.0f, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Change Name", this.field_146294_l / 2.0f, 34.0f, 16777215);
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String str = this.status == null ? "" : this.status;
        Intrinsics.checkNotNull(str);
        gameFontRenderer.drawCenteredString(str, this.field_146294_l / 2.0f, (this.field_146295_m / 4.0f) + 84, 16777215);
        GuiTextField guiTextField = this.name;
        Intrinsics.checkNotNull(guiTextField);
        guiTextField.func_146194_f();
        GuiTextField guiTextField2 = this.name;
        Intrinsics.checkNotNull(guiTextField2);
        String func_146179_b = guiTextField2.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b, "name!!.text");
        if (func_146179_b.length() == 0) {
            GuiTextField guiTextField3 = this.name;
            Intrinsics.checkNotNull(guiTextField3);
            if (!guiTextField3.func_146206_l()) {
                Fonts.font40.drawCenteredString("§7Username", (this.field_146294_l / 2.0f) - 74, 66.0f, 16777215);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    public void func_146284_a(@NotNull GuiButton button) throws IOException {
        Intrinsics.checkNotNullParameter(button, "button");
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 1:
                GuiTextField guiTextField = this.name;
                Intrinsics.checkNotNull(guiTextField);
                String func_146179_b = guiTextField.func_146179_b();
                Intrinsics.checkNotNullExpressionValue(func_146179_b, "name!!.text");
                if (func_146179_b.length() == 0) {
                    this.status = "§cEnter a name!";
                    return;
                }
                GuiTextField guiTextField2 = this.name;
                Intrinsics.checkNotNull(guiTextField2);
                if (!StringsKt.equals(guiTextField2.func_146179_b(), this.field_146297_k.func_110432_I().func_111285_a(), true)) {
                    this.status = "§cJust change the upper and lower case!";
                    return;
                }
                Minecraft minecraft = this.field_146297_k;
                GuiTextField guiTextField3 = this.name;
                Intrinsics.checkNotNull(guiTextField3);
                minecraft.field_71449_j = new Session(guiTextField3.func_146179_b(), this.field_146297_k.func_110432_I().func_148255_b(), this.field_146297_k.func_110432_I().func_148254_d(), this.field_146297_k.func_110432_I().func_152428_f().name());
                LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                StringBuilder append = new StringBuilder().append("§aChanged name to §7");
                GuiTextField guiTextField4 = this.name;
                Intrinsics.checkNotNull(guiTextField4);
                this.status = append.append((Object) guiTextField4.func_146179_b()).append("§c.").toString();
                GuiAltManager guiAltManager = this.prevGui;
                String str = this.status;
                if (str == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                }
                guiAltManager.setStatus(str);
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            default:
                return;
        }
    }

    public void func_73869_a(char typedChar, int keyCode) throws IOException {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
            return;
        }
        GuiTextField guiTextField = this.name;
        Intrinsics.checkNotNull(guiTextField);
        if (guiTextField.func_146206_l()) {
            GuiTextField guiTextField2 = this.name;
            Intrinsics.checkNotNull(guiTextField2);
            guiTextField2.func_146201_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    public void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        GuiTextField guiTextField = this.name;
        Intrinsics.checkNotNull(guiTextField);
        guiTextField.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        GuiTextField guiTextField = this.name;
        Intrinsics.checkNotNull(guiTextField);
        guiTextField.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        super.func_146281_b();
    }
}
