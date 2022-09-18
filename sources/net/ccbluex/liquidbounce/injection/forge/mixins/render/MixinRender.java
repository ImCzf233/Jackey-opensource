package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.RenderEntityEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Render.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRender.class */
public abstract class MixinRender {
    @Shadow
    public abstract <T extends Entity> boolean func_180548_c(T t);

    @Inject(method = {"doRender"}, m23at = {@AbstractC1790At("HEAD")})
    private void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new RenderEntityEvent(entity, x, y, z, entityYaw, partialTicks));
    }
}
