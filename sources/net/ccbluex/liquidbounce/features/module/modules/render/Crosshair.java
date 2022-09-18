package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "Crosshair", description = "The CS:GO.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Crosshair.class */
public class Crosshair extends Module {
    public ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "LiquidSlowly", "Sky", "Fade", "Mixer"}, "Custom");
    public IntegerValue colorRedValue = new IntegerValue("Red", 0, 0, 255);
    public IntegerValue colorGreenValue = new IntegerValue("Green", 0, 0, 255);
    public IntegerValue colorBlueValue = new IntegerValue("Blue", 0, 0, 255);
    public IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    public FloatValue widthVal = new FloatValue("Width", 2.0f, 0.25f, 10.0f);
    public FloatValue sizeVal = new FloatValue("Size/Length", 7.0f, 0.25f, 15.0f);
    public FloatValue gapVal = new FloatValue("Gap", 5.0f, 0.25f, 15.0f);
    public BoolValue dynamicVal = new BoolValue("Dynamic", true);
    public BoolValue hitMarkerVal = new BoolValue("HitMarker", true);
    public BoolValue noVanillaCH = new BoolValue("NoVanillaCrossHair", true);

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        ScaledResolution scaledRes = new ScaledResolution(f362mc);
        float width = this.widthVal.get().floatValue();
        float size = this.sizeVal.get().floatValue();
        float gap = this.gapVal.get().floatValue();
        GL11.glPushMatrix();
        RenderUtils.drawBorderedRect((scaledRes.func_78326_a() / 2.0f) - width, (((scaledRes.func_78328_b() / 2.0f) - gap) - size) - (isMoving() ? 2 : 0), (scaledRes.func_78326_a() / 2.0f) + 1.0f + width, ((scaledRes.func_78328_b() / 2.0f) - gap) - (isMoving() ? 2 : 0), 0.5f, new Color(0, 0, 0, this.colorAlphaValue.get().intValue()).getRGB(), getCrosshairColor().getRGB());
        RenderUtils.drawBorderedRect((scaledRes.func_78326_a() / 2.0f) - width, ((((scaledRes.func_78328_b() / 2.0f) + gap) + 1.0f) + (isMoving() ? 2 : 0)) - 0.15f, (scaledRes.func_78326_a() / 2.0f) + 1.0f + width, (((((scaledRes.func_78328_b() / 2.0f) + 1.0f) + gap) + size) + (isMoving() ? 2 : 0)) - 0.15f, 0.5f, new Color(0, 0, 0, this.colorAlphaValue.get().intValue()).getRGB(), getCrosshairColor().getRGB());
        RenderUtils.drawBorderedRect(((((scaledRes.func_78326_a() / 2.0f) - gap) - size) - (isMoving() ? 2 : 0)) + 0.15f, (scaledRes.func_78328_b() / 2.0f) - width, (((scaledRes.func_78326_a() / 2.0f) - gap) - (isMoving() ? 2 : 0)) + 0.15f, (scaledRes.func_78328_b() / 2) + 1.0f + width, 0.5f, new Color(0, 0, 0, this.colorAlphaValue.get().intValue()).getRGB(), getCrosshairColor().getRGB());
        RenderUtils.drawBorderedRect((scaledRes.func_78326_a() / 2.0f) + 1.0f + gap + (isMoving() ? 2 : 0), (scaledRes.func_78328_b() / 2.0f) - width, (scaledRes.func_78326_a() / 2.0f) + size + gap + 1.0f + (isMoving() ? 2 : 0), (scaledRes.func_78328_b() / 2) + 1.0f + width, 0.5f, new Color(0, 0, 0, this.colorAlphaValue.get().intValue()).getRGB(), getCrosshairColor().getRGB());
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
        EntityLivingBase target = ((KillAura) LiquidBounce.moduleManager.getModule(KillAura.class)).getTarget();
        if (this.hitMarkerVal.get().booleanValue() && target != null && target.field_70737_aN > 0) {
            GL11.glPushMatrix();
            GlStateManager.func_179147_l();
            GlStateManager.func_179090_x();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, target.field_70737_aN / target.field_70738_aO);
            GL11.glEnable(2848);
            GL11.glLineWidth(1.0f);
            GL11.glBegin(3);
            GL11.glVertex2f((scaledRes.func_78326_a() / 2.0f) + gap, (scaledRes.func_78328_b() / 2.0f) + gap);
            GL11.glVertex2f((scaledRes.func_78326_a() / 2.0f) + gap + size, (scaledRes.func_78328_b() / 2.0f) + gap + size);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex2f((scaledRes.func_78326_a() / 2.0f) - gap, (scaledRes.func_78328_b() / 2.0f) - gap);
            GL11.glVertex2f(((scaledRes.func_78326_a() / 2.0f) - gap) - size, ((scaledRes.func_78328_b() / 2.0f) - gap) - size);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex2f((scaledRes.func_78326_a() / 2.0f) - gap, (scaledRes.func_78328_b() / 2.0f) + gap);
            GL11.glVertex2f(((scaledRes.func_78326_a() / 2.0f) - gap) - size, (scaledRes.func_78328_b() / 2.0f) + gap + size);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex2f((scaledRes.func_78326_a() / 2.0f) + gap, (scaledRes.func_78328_b() / 2.0f) - gap);
            GL11.glVertex2f((scaledRes.func_78326_a() / 2.0f) + gap + size, ((scaledRes.func_78328_b() / 2.0f) - gap) - size);
            GL11.glEnd();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
            GL11.glPopMatrix();
        }
    }

    private boolean isMoving() {
        return this.dynamicVal.get().booleanValue() && MovementUtils.isMoving();
    }

    private Color getCrosshairColor() {
        String str = this.colorModeValue.get();
        boolean z = true;
        switch (str.hashCode()) {
            case -1656737386:
                if (str.equals("Rainbow")) {
                    z = true;
                    break;
                }
                break;
            case -884013110:
                if (str.equals("LiquidSlowly")) {
                    z = true;
                    break;
                }
                break;
            case 83201:
                if (str.equals("Sky")) {
                    z = true;
                    break;
                }
                break;
            case 74357737:
                if (str.equals("Mixer")) {
                    z = true;
                    break;
                }
                break;
            case 2029746065:
                if (str.equals("Custom")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue(), this.colorAlphaValue.get().intValue());
            case true:
                return new Color(RenderUtils.getRainbowOpaque(this.mixerSecondsValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0));
            case true:
                return ColorUtils.reAlpha(RenderUtils.skyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue()), this.colorAlphaValue.get().intValue());
            case true:
                return ColorUtils.reAlpha(ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue()), this.colorAlphaValue.get().intValue());
            case true:
                return ColorUtils.reAlpha(ColorMixer.getMixedColor(0, this.mixerSecondsValue.get().intValue()), this.colorAlphaValue.get().intValue());
            default:
                return ColorUtils.reAlpha(ColorUtils.fade(new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue()), 0, 100), this.colorAlphaValue.get().intValue());
        }
    }
}
