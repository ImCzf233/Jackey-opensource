package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiGameOver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiGameOver.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiGameOver.class */
public class MixinGuiGameOver {
    @Shadow
    private int field_146347_a;

    @Inject(method = {"initGui"}, m23at = {@AbstractC1790At("HEAD")})
    private void allowClickable(CallbackInfo ci) {
        this.field_146347_a = 0;
    }
}
