package net.ccbluex.liquidbounce.p004ui.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/* compiled from: GuiKeybindHelper.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J \u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\tH\u0016J\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0005H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n��\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0016"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiKeybindHelper;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "pressedKey", "", "getPrevGui", "()Lnet/minecraft/client/gui/GuiScreen;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.GuiKeybindHelper */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiKeybindHelper.class */
public final class GuiKeybindHelper extends GuiScreen {
    @NotNull
    private final GuiScreen prevGui;
    private int pressedKey;

    public GuiKeybindHelper(@NotNull GuiScreen prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    @NotNull
    public final GuiScreen getPrevGui() {
        return this.prevGui;
    }

    public void func_73866_w_() {
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) - 100, this.field_146295_m - 30, "Back"));
    }

    protected void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        this.field_146297_k.func_147108_a(this.prevGui);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146276_q_();
        Fonts.font40.drawCenteredString("Pressed key:", this.field_146294_l / 2.0f, (this.field_146295_m / 2.0f) - 12.0f, Color.LIGHT_GRAY.getRGB());
        GameFontRenderer gameFontRenderer = Fonts.font72;
        String keyName = this.pressedKey == 0 ? "Listening..." : Keyboard.getKeyName(this.pressedKey);
        Intrinsics.checkNotNullExpressionValue(keyName, "if (pressedKey == 0) \"Li…rd.getKeyName(pressedKey)");
        gameFontRenderer.drawCenteredString(keyName, this.field_146294_l / 2.0f, this.field_146295_m / 2.0f, -1);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
            return;
        }
        this.pressedKey = keyCode;
        super.func_73869_a(typedChar, keyCode);
    }
}
