package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({RenderPlayer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRenderPlayer.class */
public class MixinRenderPlayer {
    @Redirect(method = {"renderRightArm"}, m17at = @AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;isSneak:Z", ordinal = 0))
    private void resetArmState(ModelPlayer modelPlayer, boolean value) {
        modelPlayer.field_78117_n = false;
        modelPlayer.field_78093_q = false;
    }
}
