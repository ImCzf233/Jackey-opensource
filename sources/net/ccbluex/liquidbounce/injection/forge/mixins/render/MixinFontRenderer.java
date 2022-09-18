package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({FontRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinFontRenderer.class */
public abstract class MixinFontRenderer {
    @Shadow
    protected abstract void func_78265_b();

    @Inject(method = {"drawString(Ljava/lang/String;FFIZ)I"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 0, shift = AbstractC1790At.Shift.AFTER)})
    private void resetStyle(CallbackInfoReturnable<Integer> ci) {
        func_78265_b();
    }

    @ModifyVariable(method = {"renderString"}, m18at = @AbstractC1790At("HEAD"), require = 1, ordinal = 0)
    private String renderString(String string) {
        if (string == null) {
            return null;
        }
        if (LiquidBounce.eventManager == null) {
            return string;
        }
        TextEvent textEvent = new TextEvent(string);
        LiquidBounce.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }

    @ModifyVariable(method = {"getStringWidth"}, m18at = @AbstractC1790At("HEAD"), require = 1, ordinal = 0)
    private String getStringWidth(String string) {
        if (string == null) {
            return null;
        }
        if (LiquidBounce.eventManager == null) {
            return string;
        }
        TextEvent textEvent = new TextEvent(string);
        LiquidBounce.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }
}
