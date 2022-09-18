package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.awt.Color;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StackType;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.render.EnchantEffect;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderItem.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRenderItem.class */
public abstract class MixinRenderItem {
    @Shadow
    @Final
    private TextureManager field_175057_n;
    @Shadow
    @Final
    private static ResourceLocation field_110798_h;

    @Shadow
    public abstract void func_175035_a(IBakedModel iBakedModel, int i);

    @Inject(method = {"renderEffect"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderEffect(IBakedModel model, CallbackInfo callbackInfo) {
        EnchantEffect enchantEffect = (EnchantEffect) LiquidBounce.moduleManager.getModule(EnchantEffect.class);
        if (enchantEffect.getState()) {
            int rainbowColour = RenderUtils.getRainbowOpaque(enchantEffect.rainbowSpeedValue.get().intValue(), enchantEffect.rainbowSatValue.get().floatValue(), enchantEffect.rainbowBrgValue.get().floatValue(), (((int) Minecraft.func_71386_F()) % 2) * enchantEffect.rainbowDelayValue.get().intValue() * 10);
            int skyColor = RenderUtils.SkyRainbow(0, enchantEffect.rainbowSatValue.get().floatValue(), enchantEffect.rainbowBrgValue.get().floatValue());
            int mixerColor = ColorMixer.getMixedColor(0, enchantEffect.rainbowSpeedValue.get().intValue()).getRGB();
            int currentColor = new Color(enchantEffect.redValue.get().intValue(), enchantEffect.greenValue.get().intValue(), enchantEffect.blueValue.get().intValue()).getRGB();
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179143_c(514);
            GlStateManager.func_179140_f();
            GlStateManager.func_179112_b((int) StackType.REPEAT_INC, 1);
            this.field_175057_n.func_110577_a(field_110798_h);
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(8.0f, 8.0f, 8.0f);
            float f = (((float) (Minecraft.func_71386_F() % 3000)) / 3000.0f) / 8.0f;
            GlStateManager.func_179109_b(f, 0.0f, 0.0f);
            GlStateManager.func_179114_b(-50.0f, 0.0f, 0.0f, 1.0f);
            String lowerCase = enchantEffect.modeValue.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case -1349088399:
                    if (lowerCase.equals("custom")) {
                        z = false;
                        break;
                    }
                    break;
                case 113953:
                    if (lowerCase.equals("sky")) {
                        z = true;
                        break;
                    }
                    break;
                case 103910409:
                    if (lowerCase.equals("mixer")) {
                        z = true;
                        break;
                    }
                    break;
                case 973576630:
                    if (lowerCase.equals("rainbow")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    func_175035_a(model, currentColor);
                    break;
                case true:
                    func_175035_a(model, rainbowColour);
                    break;
                case true:
                    func_175035_a(model, skyColor);
                case true:
                    func_175035_a(model, mixerColor);
                    break;
            }
            GlStateManager.func_179121_F();
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(8.0f, 8.0f, 8.0f);
            float f1 = (((float) (Minecraft.func_71386_F() % 4873)) / 4873.0f) / 8.0f;
            GlStateManager.func_179109_b(-f1, 0.0f, 0.0f);
            GlStateManager.func_179114_b(10.0f, 0.0f, 0.0f, 1.0f);
            String lowerCase2 = enchantEffect.modeValue.get().toLowerCase();
            boolean z2 = true;
            switch (lowerCase2.hashCode()) {
                case -1349088399:
                    if (lowerCase2.equals("custom")) {
                        z2 = false;
                        break;
                    }
                    break;
                case 113953:
                    if (lowerCase2.equals("sky")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 103910409:
                    if (lowerCase2.equals("mixer")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 973576630:
                    if (lowerCase2.equals("rainbow")) {
                        z2 = true;
                        break;
                    }
                    break;
            }
            switch (z2) {
                case false:
                    func_175035_a(model, currentColor);
                    break;
                case true:
                    func_175035_a(model, rainbowColour);
                    break;
                case true:
                    func_175035_a(model, skyColor);
                case true:
                    func_175035_a(model, mixerColor);
                    break;
            }
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179145_e();
            GlStateManager.func_179143_c(515);
            GlStateManager.func_179132_a(true);
            this.field_175057_n.func_110577_a(TextureMap.field_110575_b);
            callbackInfo.cancel();
        }
    }
}
