package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityChestRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntityChestRenderer.class */
public class MixinTileEntityChestRenderer {
    @Inject(method = {"renderTileEntityAt"}, m23at = {@AbstractC1790At("HEAD")})
    private void injectChamsPre(CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && chams.getChestsValue().get().booleanValue()) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1000000.0f);
        }
    }

    @Inject(method = {"renderTileEntityAt"}, m23at = {@AbstractC1790At("RETURN")})
    private void injectChamsPost(CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && chams.getChestsValue().get().booleanValue()) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }
}
