package net.ccbluex.liquidbounce.p004ui.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiPasswordField.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n��\u0018��2\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/elements/GuiPasswordField;", "Lnet/minecraft/client/gui/GuiTextField;", "componentId", "", "fontrendererObj", "Lnet/minecraft/client/gui/FontRenderer;", "x", "y", "par5Width", "par6Height", "(ILnet/minecraft/client/gui/FontRenderer;IIII)V", "drawTextBox", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.elements.GuiPasswordField */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/elements/GuiPasswordField.class */
public final class GuiPasswordField extends GuiTextField {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuiPasswordField(int componentId, @NotNull FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
        Intrinsics.checkNotNullParameter(fontrendererObj, "fontrendererObj");
    }

    public void func_146194_f() {
        String realText = func_146179_b();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int length = func_146179_b().length();
        while (i < length) {
            i++;
            stringBuilder.append('*');
        }
        func_146180_a(stringBuilder.toString());
        super.func_146194_f();
        func_146180_a(realText);
    }
}
