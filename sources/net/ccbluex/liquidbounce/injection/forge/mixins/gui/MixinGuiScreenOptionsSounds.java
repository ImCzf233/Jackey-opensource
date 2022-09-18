package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.minecraft.client.gui.GuiScreenOptionsSounds$Button"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiScreenOptionsSounds.class */
public class MixinGuiScreenOptionsSounds {
    @Redirect(method = {"mouseDragged(Lnet/minecraft/client/Minecraft;II)V"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;saveOptions()V"))
    private void cancelSaving(GameSettings instance) {
    }

    @Inject(method = {"mouseReleased(II)V"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundHandler;playSound(Lnet/minecraft/client/audio/ISound;)V")})
    private void save(int mouseX, int mouseY, CallbackInfo ci) {
        Minecraft.func_71410_x().field_71474_y.func_74303_b();
    }
}
