package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({GuiOptions.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiOptions.class */
public class MixinGuiOptions extends GuiScreen {
    public void func_146281_b() {
        this.field_146297_k.field_71474_y.func_74303_b();
    }
}
