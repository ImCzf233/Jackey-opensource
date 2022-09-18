package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import co.p000uk.hexeption.utils.OutlineUtils;
import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP2D;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.render.NoRender;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({RendererLivingEntity.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRendererLivingEntity.class */
public abstract class MixinRendererLivingEntity extends MixinRender {
    @Shadow
    protected ModelBase field_77045_g;

    @Inject(method = {"doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private <T extends EntityLivingBase> void injectChamsPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        NoRender noRender = (NoRender) LiquidBounce.moduleManager.getModule(NoRender.class);
        if (noRender.getState() && noRender.shouldStopRender(entity)) {
            callbackInfo.cancel();
        } else if (chams.getState() && chams.getTargetsValue().get().booleanValue() && chams.getLegacyMode().get().booleanValue() && EntityUtils.isSelected(entity, false)) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1000000.0f);
        }
    }

    @Inject(method = {"doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V"}, m23at = {@AbstractC1790At("RETURN")})
    private <T extends EntityLivingBase> void injectChamsPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        NoRender noRender = (NoRender) LiquidBounce.moduleManager.getModule(NoRender.class);
        if (chams.getState() && chams.getTargetsValue().get().booleanValue() && chams.getLegacyMode().get().booleanValue() && EntityUtils.isSelected(entity, false)) {
            if (!noRender.getState() || !noRender.shouldStopRender(entity)) {
                GL11.glPolygonOffset(1.0f, 1000000.0f);
                GL11.glDisable(32823);
            }
        }
    }

    @Inject(method = {"canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private <T extends EntityLivingBase> void canRenderName(T entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        NoRender noRender = (NoRender) LiquidBounce.moduleManager.getModule(NoRender.class);
        if (!ESP.renderNameTags || ((LiquidBounce.moduleManager.getModule(NameTags.class).getState() && EntityUtils.isSelected(entity, false)) || ESP2D.shouldCancelNameTag(entity) || (noRender.getState() && noRender.getNameTagsValue().get().booleanValue()))) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"renderModel"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    protected <T extends EntityLivingBase> void renderModel(T p_renderModel_1_, float p_renderModel_2_, float p_renderModel_3_, float p_renderModel_4_, float p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, CallbackInfo ci) {
        boolean visible = !p_renderModel_1_.func_82150_aj();
        TrueSight trueSight = (TrueSight) LiquidBounce.moduleManager.getModule(TrueSight.class);
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        boolean chamsFlag = chams.getState() && chams.getTargetsValue().get().booleanValue() && !chams.getLegacyMode().get().booleanValue() && EntityUtils.isSelected(p_renderModel_1_, false);
        boolean semiVisible = !visible && (!p_renderModel_1_.func_98034_c(Minecraft.func_71410_x().field_71439_g) || (trueSight.getState() && trueSight.getEntitiesValue().get().booleanValue()));
        if (visible || semiVisible) {
            if (!func_180548_c(p_renderModel_1_)) {
                return;
            }
            if (semiVisible) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.func_179132_a(false);
                GlStateManager.func_179147_l();
                GlStateManager.func_179112_b(770, 771);
                GlStateManager.func_179092_a(516, 0.003921569f);
            }
            ESP esp = (ESP) LiquidBounce.moduleManager.getModule(ESP.class);
            if (esp.getState() && EntityUtils.isSelected(p_renderModel_1_, false)) {
                Minecraft mc = Minecraft.func_71410_x();
                boolean fancyGraphics = mc.field_71474_y.field_74347_j;
                mc.field_71474_y.field_74347_j = false;
                float gamma = mc.field_71474_y.field_74333_Y;
                mc.field_71474_y.field_74333_Y = 100000.0f;
                String lowerCase = esp.modeValue.get().toLowerCase();
                boolean z = true;
                switch (lowerCase.hashCode()) {
                    case -1106245566:
                        if (lowerCase.equals("outline")) {
                            z = true;
                            break;
                        }
                        break;
                    case -941784056:
                        if (lowerCase.equals("wireframe")) {
                            z = false;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        GL11.glPushMatrix();
                        GL11.glPushAttrib(1048575);
                        GL11.glPolygonMode(1032, 6913);
                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        RenderUtils.glColor(esp.getColor(p_renderModel_1_));
                        GL11.glLineWidth(esp.wireframeWidth.get().floatValue());
                        this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                        break;
                    case true:
                        ClientUtils.disableFastRender();
                        GlStateManager.func_179117_G();
                        Color color = esp.getColor(p_renderModel_1_);
                        OutlineUtils.setColor(color);
                        OutlineUtils.renderOne(esp.outlineWidth.get().floatValue());
                        this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                        OutlineUtils.setColor(color);
                        OutlineUtils.renderTwo();
                        this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                        OutlineUtils.setColor(color);
                        OutlineUtils.renderThree();
                        this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                        OutlineUtils.setColor(color);
                        OutlineUtils.renderFour(color);
                        this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                        OutlineUtils.setColor(color);
                        OutlineUtils.renderFive();
                        OutlineUtils.setColor(Color.WHITE);
                        break;
                }
                mc.field_71474_y.field_74347_j = fancyGraphics;
                mc.field_71474_y.field_74333_Y = gamma;
            }
            boolean textured = chams.getTexturedValue().get().booleanValue();
            Color chamsColor = new Color(0);
            String str = chams.getColorModeValue().get();
            boolean z2 = true;
            switch (str.hashCode()) {
                case -1656737386:
                    if (str.equals("Rainbow")) {
                        z2 = true;
                        break;
                    }
                    break;
                case -884013110:
                    if (str.equals("LiquidSlowly")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 83201:
                    if (str.equals("Sky")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 2181788:
                    if (str.equals("Fade")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 74357737:
                    if (str.equals("Mixer")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 2029746065:
                    if (str.equals("Custom")) {
                        z2 = false;
                        break;
                    }
                    break;
            }
            switch (z2) {
                case false:
                    chamsColor = new Color(chams.getRedValue().get().intValue(), chams.getGreenValue().get().intValue(), chams.getBlueValue().get().intValue());
                    break;
                case true:
                    chamsColor = new Color(RenderUtils.getRainbowOpaque(chams.getMixerSecondsValue().get().intValue(), chams.getSaturationValue().get().floatValue(), chams.getBrightnessValue().get().floatValue(), 0));
                    break;
                case true:
                    chamsColor = RenderUtils.skyRainbow(0, chams.getSaturationValue().get().floatValue(), chams.getBrightnessValue().get().floatValue());
                    break;
                case true:
                    chamsColor = ColorUtils.LiquidSlowly(System.nanoTime(), 0, chams.getSaturationValue().get().floatValue(), chams.getBrightnessValue().get().floatValue());
                    break;
                case true:
                    chamsColor = ColorMixer.getMixedColor(0, chams.getMixerSecondsValue().get().intValue());
                    break;
                case true:
                    chamsColor = ColorUtils.fade(new Color(chams.getRedValue().get().intValue(), chams.getGreenValue().get().intValue(), chams.getBlueValue().get().intValue(), chams.getAlphaValue().get().intValue()), 0, 100);
                    break;
            }
            Color chamsColor2 = ColorUtils.reAlpha(chamsColor, chams.getAlphaValue().get().intValue());
            if (chamsFlag) {
                Color chamsColor22 = new Color(0);
                String str2 = chams.getBehindColorModeValue().get();
                boolean z3 = true;
                switch (str2.hashCode()) {
                    case -123233529:
                        if (str2.equals("Opposite")) {
                            z3 = true;
                            break;
                        }
                        break;
                    case 82033:
                        if (str2.equals("Red")) {
                            z3 = true;
                            break;
                        }
                        break;
                    case 2569350:
                        if (str2.equals("Same")) {
                            z3 = false;
                            break;
                        }
                        break;
                }
                switch (z3) {
                    case false:
                        chamsColor22 = chamsColor2;
                        break;
                    case true:
                        chamsColor22 = ColorUtils.getOppositeColor(chamsColor2);
                        break;
                    case true:
                        chamsColor22 = new Color(-1104346);
                        break;
                }
                GL11.glPushMatrix();
                GL11.glEnable(10754);
                GL11.glPolygonOffset(1.0f, 1000000.0f);
                OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0f, 240.0f);
                if (!textured) {
                    GL11.glEnable(3042);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glBlendFunc(770, 771);
                    GL11.glColor4f(chamsColor22.getRed() / 255.0f, chamsColor22.getGreen() / 255.0f, chamsColor22.getBlue() / 255.0f, chamsColor22.getAlpha() / 255.0f);
                }
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
            }
            this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
            if (chamsFlag) {
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                if (!textured) {
                    GL11.glColor4f(chamsColor2.getRed() / 255.0f, chamsColor2.getGreen() / 255.0f, chamsColor2.getBlue() / 255.0f, chamsColor2.getAlpha() / 255.0f);
                }
                this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                if (!textured) {
                    GL11.glEnable(3553);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glDisable(3042);
                    GL11.glEnable(2896);
                }
                GL11.glPolygonOffset(1.0f, -1000000.0f);
                GL11.glDisable(10754);
                GL11.glPopMatrix();
            }
            if (semiVisible) {
                GlStateManager.func_179084_k();
                GlStateManager.func_179092_a(516, 0.1f);
                GlStateManager.func_179121_F();
                GlStateManager.func_179132_a(true);
            }
        }
        ci.cancel();
    }
}
