package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.p004ui.client.GuiKeybindHelper;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiIngameMenu.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiIngameMenu.class */
public abstract class MixinGuiIngameMenu extends MixinGuiScreen {
    @Inject(method = {"initGui"}, m23at = {@AbstractC1790At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        if (!this.field_146297_k.func_71387_A()) {
            this.field_146292_n.add(new GuiButton(1337, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 128, "Reconnect"));
        }
        this.field_146292_n.add(new GuiButton(727, (this.field_146294_l / 2) - 100, this.field_146295_m - 30, "Keybind Helper"));
    }

    @Inject(method = {"actionPerformed"}, m23at = {@AbstractC1790At("HEAD")})
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        if (button.field_146127_k == 1337) {
            this.field_146297_k.field_71441_e.func_72882_A();
            ServerUtils.connectToLastServer();
        }
        if (button.field_146127_k == 727) {
            this.field_146297_k.func_147108_a(new GuiKeybindHelper((GuiIngameMenu) this));
        }
    }
}
