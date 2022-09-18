package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoHypixel;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.Crosshair;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.p004ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GuiIngame.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiInGame.class */
public abstract class MixinGuiInGame extends MixinGui {
    @Shadow
    @Final
    protected static ResourceLocation field_110330_c;
    @Shadow
    public GuiPlayerTabOverlay field_175196_v;

    @Shadow
    protected abstract void func_175184_a(int i, int i2, int i3, float f, EntityPlayer entityPlayer);

    @Inject(method = {"showCrosshair"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void injectCrosshair(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        Crosshair crossHair = (Crosshair) LiquidBounce.moduleManager.getModule(Crosshair.class);
        if (crossHair.getState() && crossHair.noVanillaCH.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"renderScoreboard"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderScoreboard(ScoreObjective scoreObjective, ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
        if (scoreObjective != null) {
            AutoHypixel.gameMode = ColorUtils.stripColor(scoreObjective.func_96678_d());
        }
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if ((antiBlind.getState() && antiBlind.getScoreBoard().get().booleanValue()) || LiquidBounce.moduleManager.getModule(HUD.class).getState()) {
            callbackInfo.cancel();
        }
    }

    @ModifyConstant(method = {"renderScoreboard"}, constant = {@Constant(intValue = 553648127)})
    private int fixTextBlending(int original) {
        return -1;
    }

    @Inject(method = {"renderBossHealth"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderBossHealth(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if (antiBlind.getState() && antiBlind.getBossHealth().get().booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"renderTooltip"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo callbackInfo) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        if ((Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) && hud.getState()) {
            Minecraft mc = Minecraft.func_71410_x();
            EntityPlayer entityPlayer = (EntityPlayer) mc.func_175606_aa();
            boolean blackHB = hud.getBlackHotbarValue().get().booleanValue();
            int middleScreen = sr.func_78326_a() / 2;
            float posInv = hud.getAnimPos(entityPlayer.field_71071_by.field_70461_c * 20.0f);
            GlStateManager.func_179117_G();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            mc.func_110434_K().func_110577_a(field_110330_c);
            float f = this.field_73735_i;
            this.field_73735_i = -90.0f;
            GlStateManager.func_179117_G();
            if (blackHB) {
                RenderUtils.originalRoundedRect(middleScreen - 91, sr.func_78328_b() - 2, middleScreen + 91, sr.func_78328_b() - 22, 3.0f, Integer.MIN_VALUE);
                RenderUtils.originalRoundedRect((middleScreen - 91) + posInv, sr.func_78328_b() - 2, (middleScreen - 91) + posInv + 22.0f, sr.func_78328_b() - 22, 3.0f, Integer.MAX_VALUE);
            } else {
                func_175174_a(middleScreen - 91.0f, sr.func_78328_b() - 22, 0, 0, 182, 22);
                func_175174_a(((middleScreen - 91.0f) + posInv) - 1.0f, (sr.func_78328_b() - 22) - 1, 0, 22, 24, 22);
            }
            this.field_73735_i = f;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179091_B();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            RenderHelper.func_74520_c();
            for (int j = 0; j < 9; j++) {
                int k = ((sr.func_78326_a() / 2) - 90) + (j * 20) + 2;
                int l = (sr.func_78328_b() - 19) - (blackHB ? 1 : 0);
                func_175184_a(j, k, l, partialTicks, entityPlayer);
            }
            RenderHelper.func_74518_a();
            GlStateManager.func_179101_C();
            GlStateManager.func_179084_k();
            GlStateManager.func_179117_G();
            LiquidBounce.eventManager.callEvent(new Render2DEvent(partialTicks));
            AWTFontRenderer.Companion.garbageCollectionTick();
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"renderTooltip"}, m23at = {@AbstractC1790At("TAIL")})
    private void renderTooltipPost(ScaledResolution sr, float partialTicks, CallbackInfo callbackInfo) {
        if (!ClassUtils.hasClass("net.labymod.api.LabyModAPI")) {
            LiquidBounce.eventManager.callEvent(new Render2DEvent(partialTicks));
            AWTFontRenderer.Companion.garbageCollectionTick();
        }
    }

    @Inject(method = {"renderPumpkinOverlay"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderPumpkinOverlay(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if (antiBlind.getState() && antiBlind.getPumpkinEffect().get().booleanValue()) {
            callbackInfo.cancel();
        }
    }
}
