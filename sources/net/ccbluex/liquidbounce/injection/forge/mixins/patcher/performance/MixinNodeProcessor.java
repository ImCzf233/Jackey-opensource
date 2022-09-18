package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.performance;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NodeProcessor.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/performance/MixinNodeProcessor.class */
public class MixinNodeProcessor {
    @Shadow
    protected IBlockAccess field_176169_a;

    @Inject(method = {"postProcess"}, m23at = {@AbstractC1790At("HEAD")})
    private void patcher$cleanupBlockAccess(CallbackInfo ci) {
        this.field_176169_a = null;
    }
}
