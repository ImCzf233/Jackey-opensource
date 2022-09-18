package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "AsianHat", spacedName = "Asian Hat", description = "not your typical china hat", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/AsianHat.class */
public class AsianHat extends Module {
    private final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final IntegerValue colorEndAlphaValue = new IntegerValue("EndAlpha", 255, 0, 255);
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    private final IntegerValue spaceValue = new IntegerValue("Color-Space", 0, 0, 100);
    private final BoolValue noFirstPerson = new BoolValue("NoFirstPerson", true);
    private final BoolValue hatBorder = new BoolValue("HatBorder", true);
    private final BoolValue hatRotation = new BoolValue("HatRotation", true);
    private final IntegerValue borderAlphaValue = new IntegerValue("BorderAlpha", 255, 0, 255);
    private final FloatValue borderWidthValue = new FloatValue("BorderWidth", 1.0f, 0.1f, 4.0f);
    private final List<double[]> positions = new ArrayList();
    private double lastRadius = 0.0d;

    private void checkPosition(double radius) {
        if (radius != this.lastRadius) {
            this.positions.clear();
            for (int i = 0; i <= 360; i++) {
                this.positions.add(new double[]{(-Math.sin((i * 3.141592653589793d) / 180.0d)) * radius, Math.cos((i * 3.141592653589793d) / 180.0d) * radius});
            }
        }
        this.lastRadius = radius;
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        float f;
        float f2;
        EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
        if (entityPlayerSP != null) {
            if (this.noFirstPerson.get().booleanValue() && f362mc.field_71474_y.field_74320_O == 0) {
                return;
            }
            AxisAlignedBB bb = entityPlayerSP.func_174813_aQ();
            float partialTicks = event.getPartialTicks();
            double radius = bb.field_72336_d - bb.field_72340_a;
            double height = bb.field_72337_e - bb.field_72338_b;
            double posX = ((EntityLivingBase) entityPlayerSP).field_70142_S + ((((EntityLivingBase) entityPlayerSP).field_70165_t - ((EntityLivingBase) entityPlayerSP).field_70142_S) * partialTicks);
            double posY = ((EntityLivingBase) entityPlayerSP).field_70137_T + ((((EntityLivingBase) entityPlayerSP).field_70163_u - ((EntityLivingBase) entityPlayerSP).field_70137_T) * partialTicks);
            double posZ = ((EntityLivingBase) entityPlayerSP).field_70136_U + ((((EntityLivingBase) entityPlayerSP).field_70161_v - ((EntityLivingBase) entityPlayerSP).field_70136_U) * partialTicks);
            double viewX = -f362mc.func_175598_ae().field_78730_l;
            double viewY = -f362mc.func_175598_ae().field_78731_m;
            double viewZ = -f362mc.func_175598_ae().field_78728_n;
            Color colour = getColor(entityPlayerSP, 0);
            float r = colour.getRed() / 255.0f;
            float g = colour.getGreen() / 255.0f;
            float b = colour.getBlue() / 255.0f;
            float al = this.colorAlphaValue.get().intValue() / 255.0f;
            float Eal = this.colorEndAlphaValue.get().intValue() / 255.0f;
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer worldrenderer = tessellator.func_178180_c();
            checkPosition(radius);
            GL11.glPushMatrix();
            GlStateManager.func_179137_b(viewX + posX, ((viewY + posY) + height) - 0.4d, viewZ + posZ);
            pre3D();
            if (this.hatRotation.get().booleanValue()) {
                Rotations rotMod = (Rotations) LiquidBounce.moduleManager.getModule(Rotations.class);
                float yaw = RenderUtils.interpolate(((EntityLivingBase) entityPlayerSP).field_70177_z, ((EntityLivingBase) entityPlayerSP).field_70126_B, partialTicks);
                float pitch = RenderUtils.interpolate(((EntityLivingBase) entityPlayerSP).field_70125_A, ((EntityLivingBase) entityPlayerSP).field_70127_C, partialTicks);
                if (rotMod != null && rotMod.shouldRotate()) {
                    if (RotationUtils.targetRotation != null) {
                        f = RotationUtils.targetRotation.getYaw();
                    } else {
                        f = RotationUtils.serverRotation != null ? RotationUtils.serverRotation.getYaw() : yaw;
                    }
                    yaw = f;
                    if (RotationUtils.targetRotation != null) {
                        f2 = RotationUtils.targetRotation.getPitch();
                    } else {
                        f2 = RotationUtils.serverRotation != null ? RotationUtils.serverRotation.getPitch() : pitch;
                    }
                    pitch = f2;
                }
                GlStateManager.func_179114_b(-yaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.func_179114_b(pitch, 1.0f, 0.0f, 0.0f);
            }
            worldrenderer.func_181668_a(9, DefaultVertexFormats.field_181706_f);
            worldrenderer.func_181662_b(0.0d, 0.7d, 0.0d).func_181666_a(r, g, b, al).func_181675_d();
            int i = 0;
            for (double[] smolPos : this.positions) {
                if (this.spaceValue.get().intValue() > 0 && !this.colorModeValue.get().equalsIgnoreCase("Custom")) {
                    Color colour2 = getColor(entityPlayerSP, i * this.spaceValue.get().intValue());
                    float r2 = colour2.getRed() / 255.0f;
                    float g2 = colour2.getGreen() / 255.0f;
                    float b2 = colour2.getBlue() / 255.0f;
                    worldrenderer.func_181662_b(smolPos[0], 0.4d, smolPos[1]).func_181666_a(r2, g2, b2, Eal).func_181675_d();
                } else {
                    worldrenderer.func_181662_b(smolPos[0], 0.4d, smolPos[1]).func_181666_a(r, g, b, Eal).func_181675_d();
                }
                i++;
            }
            worldrenderer.func_181662_b(0.0d, 0.7d, 0.0d).func_181666_a(r, g, b, al).func_181675_d();
            tessellator.func_78381_a();
            if (this.hatBorder.get().booleanValue()) {
                float lineAlp = this.borderAlphaValue.get().intValue() / 255.0f;
                GL11.glLineWidth(this.borderWidthValue.get().floatValue());
                worldrenderer.func_181668_a(2, DefaultVertexFormats.field_181706_f);
                int i2 = 0;
                for (double[] smolPos2 : this.positions) {
                    if (this.spaceValue.get().intValue() > 0 && !this.colorModeValue.get().equalsIgnoreCase("Custom")) {
                        Color colour22 = getColor(entityPlayerSP, i2 * this.spaceValue.get().intValue());
                        float r22 = colour22.getRed() / 255.0f;
                        float g22 = colour22.getGreen() / 255.0f;
                        float b22 = colour22.getBlue() / 255.0f;
                        worldrenderer.func_181662_b(smolPos2[0], 0.4d, smolPos2[1]).func_181666_a(r22, g22, b22, lineAlp).func_181675_d();
                    } else {
                        worldrenderer.func_181662_b(smolPos2[0], 0.4d, smolPos2[1]).func_181666_a(r, g, b, lineAlp).func_181675_d();
                    }
                    i2++;
                }
                tessellator.func_78381_a();
            }
            post3D();
            GL11.glPopMatrix();
        }
    }

    public final Color getColor(Entity ent, int index) {
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
                return new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue());
            case true:
                return new Color(RenderUtils.getRainbowOpaque(this.mixerSecondsValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), index));
            case true:
                return RenderUtils.skyRainbow(index, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            case true:
                return ColorUtils.LiquidSlowly(System.nanoTime(), index, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            case true:
                return ColorMixer.getMixedColor(index, this.mixerSecondsValue.get().intValue());
            default:
                return ColorUtils.fade(new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue()), index, 100);
        }
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
        GL11.glDisable(2884);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
