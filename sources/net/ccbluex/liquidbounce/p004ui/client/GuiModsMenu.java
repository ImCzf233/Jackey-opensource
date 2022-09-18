package net.ccbluex.liquidbounce.p004ui.client;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.concurrent.Thread;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.discord.ClientRichPresence;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.GuiModList;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiModsMenu.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\nH\u0014R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0017"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiModsMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "changeDisplayState", "buttonId", "", "state", "", "drawScreen", "mouseX", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.GuiModsMenu */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiModsMenu.class */
public final class GuiModsMenu extends GuiScreen {
    @NotNull
    private final GuiScreen prevGui;

    public GuiModsMenu(@NotNull GuiScreen prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    public void func_73866_w_() {
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 48, "Forge Mods"));
        this.field_146292_n.add(new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 48 + 25, "Scripts"));
        this.field_146292_n.add(new GuiButton(2, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 48 + 50, Intrinsics.stringPlus("Rich Presence: ", LiquidBounce.INSTANCE.getClientRichPresence().getShowRichPresenceValue() ? "§aON" : "§cOFF")));
        this.field_146292_n.add(new GuiButton(3, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 48 + 75, "Background Settings"));
        this.field_146292_n.add(new GuiButton(4, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 48 + 100, "Back"));
    }

    protected void func_146284_a(@NotNull GuiButton button) {
        boolean z;
        Intrinsics.checkNotNullParameter(button, "button");
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(new GuiModList(this));
                return;
            case 1:
                this.field_146297_k.func_147108_a(new GuiScripts(this));
                return;
            case 2:
                ClientRichPresence rpc = LiquidBounce.INSTANCE.getClientRichPresence();
                boolean state = !rpc.getShowRichPresenceValue();
                if (!state) {
                    rpc.shutdown();
                    changeDisplayState(2, state);
                    z = false;
                } else if (!state) {
                    throw new NoWhenBranchMatchedException();
                } else {
                    Ref.BooleanRef value = new Ref.BooleanRef();
                    value.element = true;
                    Thread.thread$default(false, false, null, null, 0, new GuiModsMenu$actionPerformed$1(value, rpc), 31, null);
                    changeDisplayState(2, value.element);
                    z = value.element;
                }
                rpc.setShowRichPresenceValue(z);
                return;
            case 3:
                this.field_146297_k.func_147108_a(new GuiBackground(this));
                return;
            case 4:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            default:
                return;
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
        } else {
            super.func_73869_a(typedChar, keyCode);
        }
    }

    private final void changeDisplayState(int buttonId, boolean state) {
        String str;
        GuiButton button = (GuiButton) this.field_146292_n.get(buttonId);
        String displayName = button.field_146126_j;
        if (!state) {
            Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
            str = StringsKt.replace$default(displayName, "§aON", "§cOFF", false, 4, (Object) null);
        } else if (!state) {
            throw new NoWhenBranchMatchedException();
        } else {
            Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
            str = StringsKt.replace$default(displayName, "§cOFF", "§aON", false, 4, (Object) null);
        }
        button.field_146126_j = str;
    }
}
