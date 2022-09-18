package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.NoAchievements;
import net.minecraft.client.gui.achievement.GuiAchievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiAchievement.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiAchievement.class */
public class MixinGuiAchievement {
    @Inject(method = {"updateAchievementWindow"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void injectAchievements(CallbackInfo ci) {
        if (LiquidBounce.moduleManager != null && LiquidBounce.moduleManager.getModule(NoAchievements.class) != null && LiquidBounce.moduleManager.getModule(NoAchievements.class).getState()) {
            ci.cancel();
        }
    }
}
