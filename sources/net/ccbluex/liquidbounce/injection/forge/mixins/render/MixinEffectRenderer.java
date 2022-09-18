package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityParticleEmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EffectRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinEffectRenderer.class */
public abstract class MixinEffectRenderer {
    @Shadow
    private List<EntityParticleEmitter> field_178933_d;

    @Shadow
    protected abstract void func_178922_a(int i);

    @Overwrite
    public void func_78868_a() {
        for (int i = 0; i < 4; i++) {
            try {
                func_178922_a(i);
            } catch (ConcurrentModificationException e) {
                return;
            }
        }
        Iterator<EntityParticleEmitter> it = this.field_178933_d.iterator();
        while (it.hasNext()) {
            EntityParticleEmitter entityParticleEmitter = it.next();
            entityParticleEmitter.func_70071_h_();
            if (entityParticleEmitter.field_70128_L) {
                it.remove();
            }
        }
    }

    @Inject(method = {"addBlockDestroyEffects", "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void removeBlockBreakingParticles(CallbackInfo ci) {
        if (Animations.noBlockParticles.get().booleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = {"addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/MovingObjectPosition;)V"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true, remap = false)
    private void removeBlockBreakingParticles_Forge(CallbackInfo ci) {
        if (Animations.noBlockParticles.get().booleanValue()) {
            ci.cancel();
        }
    }
}
