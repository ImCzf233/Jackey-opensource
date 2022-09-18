package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import net.minecraft.client.renderer.BlockFluidRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({BlockFluidRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinBlockFluidRenderer.class */
public class MixinBlockFluidRenderer {
    @ModifyConstant(method = {"renderFluid"}, constant = {@Constant(floatValue = 0.001f)})
    private float fixFluidStitching(float original) {
        return 0.0f;
    }
}
