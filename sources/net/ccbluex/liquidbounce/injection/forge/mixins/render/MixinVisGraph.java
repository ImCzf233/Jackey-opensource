package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.minecraft.client.renderer.chunk.VisGraph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({VisGraph.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinVisGraph.class */
public class MixinVisGraph {
    @Inject(method = {"func_178606_a"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void func_178606_a(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(XRay.class).getState()) {
            callbackInfo.cancel();
        }
    }
}
