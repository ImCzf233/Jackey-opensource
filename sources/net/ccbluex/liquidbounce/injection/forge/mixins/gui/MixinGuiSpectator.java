package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.p004ui.font.AWTFontRenderer;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiSpectator.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiSpectator.class */
public class MixinGuiSpectator {
    @Inject(method = {"renderTooltip"}, m23at = {@AbstractC1790At("RETURN")})
    private void renderTooltipPost(ScaledResolution p_175264_1_, float p_175264_2_, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new Render2DEvent(p_175264_2_));
        AWTFontRenderer.Companion.garbageCollectionTick();
    }
}
