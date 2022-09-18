package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityMobSpawnerRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntityMobSpawnerRenderer.class */
public class MixinTileEntityMobSpawnerRenderer {
    @Inject(method = {"renderMob"}, cancellable = true, m23at = {@AbstractC1790At("HEAD")})
    private static void injectPaintingSpawnerFix(MobSpawnerBaseLogic mobSpawnerLogic, double posX, double posY, double posZ, float partialTicks, CallbackInfo ci) {
        Entity entity = mobSpawnerLogic.func_180612_a(mobSpawnerLogic.func_98271_a());
        if (entity == null || (entity instanceof EntityPainting)) {
            ci.cancel();
        }
    }
}
