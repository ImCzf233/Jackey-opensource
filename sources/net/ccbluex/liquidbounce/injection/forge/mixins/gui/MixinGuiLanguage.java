package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({GuiLanguage.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiLanguage.class */
public class MixinGuiLanguage extends GuiScreen {
    public void func_146281_b() {
        this.field_146297_k.field_71456_v.func_146158_b().func_146245_b();
    }
}
