package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "TargetMark", spacedName = "Target Mark", description = "Displays your KillAura's target in 3D.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/TargetMark.class */
public class TargetMark extends Module {
    private EntityLivingBase entity;
    private double yPos;

    /* renamed from: bb */
    private AxisAlignedBB f339bb;
    private KillAura aura;
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Default", "Box", "Jello", "Tracers"}, "Default");
    private final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Mixer", "Health"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final FloatValue jelloAlphaValue = new FloatValue("JelloEndAlphaPercent", 0.4f, 0.0f, 1.0f, "x", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("jello"));
    });
    private final FloatValue jelloWidthValue = new FloatValue("JelloCircleWidth", 3.0f, 0.01f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("jello"));
    });
    private final FloatValue jelloGradientHeightValue = new FloatValue("JelloGradientHeight", 3.0f, 1.0f, 8.0f, "m", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("jello"));
    });
    private final FloatValue jelloFadeSpeedValue = new FloatValue("JelloFadeSpeed", 0.1f, 0.01f, 0.5f, "x", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("jello"));
    });
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    public final FloatValue moveMarkValue = new FloatValue("MoveMarkY", 0.6f, 0.0f, 2.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("default"));
    });
    private final FloatValue thicknessValue = new FloatValue("Thickness", 1.0f, 0.1f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("tracers"));
    });
    private final BoolValue colorTeam = new BoolValue("Team", false);
    private double direction = 1.0d;
    private double progress = 0.0d;

    /* renamed from: al */
    private float f338al = 0.0f;
    private long lastMS = System.currentTimeMillis();
    private long lastDeltaMS = 0;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onInitialize() {
        this.aura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (this.modeValue.get().equalsIgnoreCase("jello") && !this.aura.getTargetModeValue().get().equalsIgnoreCase("multi")) {
            this.f338al = AnimationUtils.changer(this.f338al, this.aura.getTarget() != null ? this.jelloFadeSpeedValue.get().floatValue() : -this.jelloFadeSpeedValue.get().floatValue(), 0.0f, this.colorAlphaValue.get().intValue() / 255.0f);
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        Tracers tracers;
        if (!this.modeValue.get().equalsIgnoreCase("jello") || this.aura.getTargetModeValue().get().equalsIgnoreCase("multi")) {
            if (this.modeValue.get().equalsIgnoreCase("default")) {
                if (this.aura.getTargetModeValue().get().equalsIgnoreCase("multi") || this.aura.getTarget() == null) {
                    return;
                }
                RenderUtils.drawPlatform(this.aura.getTarget(), this.aura.getHitable() ? ColorUtils.reAlpha(getColor(this.aura.getTarget()), this.colorAlphaValue.get().intValue()) : new Color(255, 0, 0, this.colorAlphaValue.get().intValue()));
                return;
            } else if (this.modeValue.get().equalsIgnoreCase("tracers")) {
                if (this.aura.getTargetModeValue().get().equalsIgnoreCase("multi") || this.aura.getTarget() == null || (tracers = (Tracers) LiquidBounce.moduleManager.getModule(Tracers.class)) == null) {
                    return;
                }
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glEnable(2848);
                GL11.glLineWidth(this.thicknessValue.get().floatValue());
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glBegin(1);
                int dist = (int) (f362mc.field_71439_g.func_70032_d(this.aura.getTarget()) * 2.0f);
                if (dist > 255) {
                }
                tracers.drawTraces(this.aura.getTarget(), getColor(this.aura.getTarget()), false);
                GL11.glEnd();
                GL11.glEnable(3553);
                GL11.glDisable(2848);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GlStateManager.func_179117_G();
                return;
            } else if (this.aura.getTargetModeValue().get().equalsIgnoreCase("multi") || this.aura.getTarget() == null) {
                return;
            } else {
                RenderUtils.drawEntityBox(this.aura.getTarget(), this.aura.getHitable() ? ColorUtils.reAlpha(getColor(this.aura.getTarget()), this.colorAlphaValue.get().intValue()) : new Color(255, 0, 0, this.colorAlphaValue.get().intValue()), false);
                return;
            }
        }
        double lastY = this.yPos;
        if (this.f338al > 0.0f) {
            if (System.currentTimeMillis() - this.lastMS >= 1000) {
                this.direction = -this.direction;
                this.lastMS = System.currentTimeMillis();
            }
            long weird = this.direction > 0.0d ? System.currentTimeMillis() - this.lastMS : 1000 - (System.currentTimeMillis() - this.lastMS);
            this.progress = weird / 1000.0d;
            this.lastDeltaMS = System.currentTimeMillis() - this.lastMS;
        } else {
            this.lastMS = System.currentTimeMillis() - this.lastDeltaMS;
        }
        if (this.aura.getTarget() != null) {
            this.entity = this.aura.getTarget();
            this.f339bb = this.entity.func_174813_aQ();
        }
        if (this.f339bb == null || this.entity == null) {
            return;
        }
        double radius = this.f339bb.field_72336_d - this.f339bb.field_72340_a;
        double height = this.f339bb.field_72337_e - this.f339bb.field_72338_b;
        double posX = this.entity.field_70142_S + ((this.entity.field_70165_t - this.entity.field_70142_S) * f362mc.field_71428_T.field_74281_c);
        double posY = this.entity.field_70137_T + ((this.entity.field_70163_u - this.entity.field_70137_T) * f362mc.field_71428_T.field_74281_c);
        double posZ = this.entity.field_70136_U + ((this.entity.field_70161_v - this.entity.field_70136_U) * f362mc.field_71428_T.field_74281_c);
        this.yPos = easeInOutQuart(this.progress) * height;
        double deltaY = (this.direction > 0.0d ? this.yPos - lastY : lastY - this.yPos) * (-this.direction) * this.jelloGradientHeightValue.get().floatValue();
        if (this.f338al <= 0.0f && this.entity != null) {
            this.entity = null;
            return;
        }
        Color colour = getColor(this.entity);
        float r = colour.getRed() / 255.0f;
        float g = colour.getGreen() / 255.0f;
        float b = colour.getBlue() / 255.0f;
        pre3D();
        GL11.glTranslated(-f362mc.func_175598_ae().field_78730_l, -f362mc.func_175598_ae().field_78731_m, -f362mc.func_175598_ae().field_78728_n);
        GL11.glBegin(8);
        for (int i = 0; i <= 360; i++) {
            double calc = (i * 3.141592653589793d) / 180.0d;
            double posX2 = posX - (Math.sin(calc) * radius);
            double posZ2 = posZ + (Math.cos(calc) * radius);
            GL11.glColor4f(r, g, b, 0.0f);
            GL11.glVertex3d(posX2, posY + this.yPos + deltaY, posZ2);
            GL11.glColor4f(r, g, b, this.f338al * this.jelloAlphaValue.get().floatValue());
            GL11.glVertex3d(posX2, posY + this.yPos, posZ2);
        }
        GL11.glEnd();
        drawCircle(posX, posY + this.yPos, posZ, this.jelloWidthValue.get().floatValue(), radius, r, g, b, this.f338al);
        post3D();
    }

    public final Color getColor(Entity ent) {
        int index;
        if (ent instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) ent;
            if (this.colorModeValue.get().equalsIgnoreCase("Health")) {
                return BlendUtils.getHealthColor(entityLivingBase.func_110143_aJ(), entityLivingBase.func_110138_aP());
            }
            if (this.colorTeam.get().booleanValue()) {
                char[] chars = entityLivingBase.func_145748_c_().func_150254_d().toCharArray();
                int color = Integer.MAX_VALUE;
                int i = 0;
                while (true) {
                    if (i < chars.length) {
                        if (chars[i] != 167 || i + 1 >= chars.length || (index = GameFontRenderer.getColorIndex(chars[i + 1])) < 0 || index > 15) {
                            i++;
                        } else {
                            color = ColorUtils.hexColors[index];
                            break;
                        }
                    } else {
                        break;
                    }
                }
                return new Color(color);
            }
        }
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
                return new Color(RenderUtils.getRainbowOpaque(this.mixerSecondsValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0));
            case true:
                return RenderUtils.skyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            case true:
                return ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            case true:
                return ColorMixer.getMixedColor(0, this.mixerSecondsValue.get().intValue());
            default:
                return ColorUtils.fade(new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue()), 0, 100);
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

    private void drawCircle(double x, double y, double z, float width, double radius, float red, float green, float blue, float alp) {
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        GL11.glColor4f(red, green, blue, alp);
        for (int i = 0; i <= 360; i++) {
            double posX = x - (Math.sin((i * 3.141592653589793d) / 180.0d) * radius);
            double posZ = z + (Math.cos((i * 3.141592653589793d) / 180.0d) * radius);
            GL11.glVertex3d(posX, y, posZ);
        }
        GL11.glEnd();
    }

    private double easeInOutQuart(double x) {
        return x < 0.5d ? 8.0d * x * x * x * x : 1.0d - (Math.pow(((-2.0d) * x) + 2.0d, 4.0d) / 2.0d);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.modeValue.get();
    }
}
