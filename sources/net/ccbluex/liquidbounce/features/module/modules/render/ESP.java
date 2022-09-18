package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.text.DecimalFormat;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.WorldToScreen;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

@ModuleInfo(name = "ESP", description = "Allows you to see targets through walls.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/ESP.class */
public class ESP extends Module {
    public static boolean renderNameTags = true;
    private DecimalFormat decimalFormat = new DecimalFormat("0.0");
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "OtherBox", "WireFrame", "2D", "Real2D", "Outline", "ShaderOutline", "ShaderGlow"}, "Box");
    public final BoolValue real2dcsgo = new BoolValue("2D-CSGOStyle", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("real2d"));
    });
    public final BoolValue real2dShowHealth = new BoolValue("2D-ShowHealth", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("real2d"));
    });
    public final BoolValue real2dShowHeldItem = new BoolValue("2D-ShowHeldItem", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("real2d"));
    });
    public final BoolValue real2dShowName = new BoolValue("2D-ShowEntityName", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("real2d"));
    });
    public final BoolValue real2dOutline = new BoolValue("2D-Outline", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("real2d"));
    });
    public final FloatValue outlineWidth = new FloatValue("Outline-Width", 3.0f, 0.5f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("outline"));
    });
    public final FloatValue wireframeWidth = new FloatValue("WireFrame-Width", 2.0f, 0.5f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("wireframe"));
    });
    private final FloatValue shaderOutlineRadius = new FloatValue("ShaderOutline-Radius", 1.35f, 1.0f, 2.0f, "x", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("shaderoutline"));
    });
    private final FloatValue shaderGlowRadius = new FloatValue("ShaderGlow-Radius", 2.3f, 2.0f, 3.0f, "x", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("shaderglow"));
    });
    private final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Health", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    private final BoolValue colorTeam = new BoolValue("Team", false);

    /* JADX WARN: Multi-variable type inference failed */
    @EventTarget
    public void onRender3D(Render3DEvent event) {
        String mode = this.modeValue.get();
        Matrix4f mvMatrix = WorldToScreen.getMatrix(2982);
        Matrix4f projectionMatrix = WorldToScreen.getMatrix(2983);
        boolean real2d = mode.equalsIgnoreCase("real2d");
        if (real2d) {
            GL11.glPushAttrib(8192);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0d, f362mc.field_71443_c, f362mc.field_71440_d, 0.0d, -1.0d, 1.0d);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            GlStateManager.func_179098_w();
            GlStateManager.func_179132_a(true);
            GL11.glLineWidth(1.0f);
        }
        for (EntityLivingBase entityLivingBase : f362mc.field_71441_e.field_72996_f) {
            if (entityLivingBase != null && entityLivingBase != f362mc.field_71439_g && EntityUtils.isSelected(entityLivingBase, false) && RenderUtils.isInViewFrustrum((Entity) entityLivingBase)) {
                EntityLivingBase entityLiving = entityLivingBase;
                Color color = getColor(entityLiving);
                String lowerCase = mode.toLowerCase();
                boolean z = true;
                switch (lowerCase.hashCode()) {
                    case -1171135301:
                        if (lowerCase.equals("otherbox")) {
                            z = true;
                            break;
                        }
                        break;
                    case -934973296:
                        if (lowerCase.equals("real2d")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1650:
                        if (lowerCase.equals("2d")) {
                            z = true;
                            break;
                        }
                        break;
                    case 97739:
                        if (lowerCase.equals("box")) {
                            z = false;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                    case true:
                        RenderUtils.drawEntityBox(entityLivingBase, color, !mode.equalsIgnoreCase("otherbox"));
                        continue;
                    case true:
                        RenderManager renderManager = f362mc.func_175598_ae();
                        Timer timer = f362mc.field_71428_T;
                        double posX = (entityLiving.field_70142_S + ((entityLiving.field_70165_t - entityLiving.field_70142_S) * timer.field_74281_c)) - renderManager.field_78725_b;
                        double posY = (entityLiving.field_70137_T + ((entityLiving.field_70163_u - entityLiving.field_70137_T) * timer.field_74281_c)) - renderManager.field_78726_c;
                        double posZ = (entityLiving.field_70136_U + ((entityLiving.field_70161_v - entityLiving.field_70136_U) * timer.field_74281_c)) - renderManager.field_78723_d;
                        RenderUtils.draw2D(entityLiving, posX, posY, posZ, color.getRGB(), Color.BLACK.getRGB());
                        continue;
                    case true:
                        RenderManager renderManager2 = f362mc.func_175598_ae();
                        Timer timer2 = f362mc.field_71428_T;
                        AxisAlignedBB bb = entityLiving.func_174813_aQ().func_72317_d(-entityLiving.field_70165_t, -entityLiving.field_70163_u, -entityLiving.field_70161_v).func_72317_d(entityLiving.field_70142_S + ((entityLiving.field_70165_t - entityLiving.field_70142_S) * timer2.field_74281_c), entityLiving.field_70137_T + ((entityLiving.field_70163_u - entityLiving.field_70137_T) * timer2.field_74281_c), entityLiving.field_70136_U + ((entityLiving.field_70161_v - entityLiving.field_70136_U) * timer2.field_74281_c)).func_72317_d(-renderManager2.field_78725_b, -renderManager2.field_78726_c, -renderManager2.field_78723_d);
                        double[] dArr = {new double[]{bb.field_72340_a, bb.field_72338_b, bb.field_72339_c}, new double[]{bb.field_72340_a, bb.field_72337_e, bb.field_72339_c}, new double[]{bb.field_72336_d, bb.field_72337_e, bb.field_72339_c}, new double[]{bb.field_72336_d, bb.field_72338_b, bb.field_72339_c}, new double[]{bb.field_72340_a, bb.field_72338_b, bb.field_72334_f}, new double[]{bb.field_72340_a, bb.field_72337_e, bb.field_72334_f}, new double[]{bb.field_72336_d, bb.field_72337_e, bb.field_72334_f}, new double[]{bb.field_72336_d, bb.field_72338_b, bb.field_72334_f}};
                        float minX = f362mc.field_71443_c;
                        float minY = f362mc.field_71440_d;
                        float maxX = 0.0f;
                        float maxY = 0.0f;
                        for (Object[] objArr : dArr) {
                            Vector2f screenPos = WorldToScreen.worldToScreen(new Vector3f((float) objArr[0], (float) objArr[1], (float) objArr[2]), mvMatrix, projectionMatrix, f362mc.field_71443_c, f362mc.field_71440_d);
                            if (screenPos != null) {
                                minX = Math.min(screenPos.x, minX);
                                minY = Math.min(screenPos.y, minY);
                                maxX = Math.max(screenPos.x, maxX);
                                maxY = Math.max(screenPos.y, maxY);
                            }
                        }
                        if (minX < f362mc.field_71443_c) {
                            if (minY < f362mc.field_71440_d && maxX > 0.0f && maxY > 0.0f) {
                                if (this.real2dOutline.get().booleanValue()) {
                                    GL11.glLineWidth(2.0f);
                                    GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                                    if (this.real2dcsgo.get().booleanValue()) {
                                        float distX = (maxX - minX) / 3.0f;
                                        float distY = (maxY - minY) / 3.0f;
                                        GL11.glBegin(3);
                                        GL11.glVertex2f(minX, minY + distY);
                                        GL11.glVertex2f(minX, minY);
                                        GL11.glVertex2f(minX + distX, minY);
                                        GL11.glEnd();
                                        GL11.glBegin(3);
                                        GL11.glVertex2f(minX, maxY - distY);
                                        GL11.glVertex2f(minX, maxY);
                                        GL11.glVertex2f(minX + distX, maxY);
                                        GL11.glEnd();
                                        GL11.glBegin(3);
                                        GL11.glVertex2f(maxX - distX, minY);
                                        GL11.glVertex2f(maxX, minY);
                                        GL11.glVertex2f(maxX, minY + distY);
                                        GL11.glEnd();
                                        GL11.glBegin(3);
                                        GL11.glVertex2f(maxX - distX, maxY);
                                        GL11.glVertex2f(maxX, maxY);
                                        GL11.glVertex2f(maxX, maxY - distY);
                                        GL11.glEnd();
                                    } else {
                                        GL11.glBegin(2);
                                        GL11.glVertex2f(minX, minY);
                                        GL11.glVertex2f(minX, maxY);
                                        GL11.glVertex2f(maxX, maxY);
                                        GL11.glVertex2f(maxX, minY);
                                        GL11.glEnd();
                                    }
                                    GL11.glLineWidth(1.0f);
                                }
                                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                                if (this.real2dcsgo.get().booleanValue()) {
                                    float distX2 = (maxX - minX) / 3.0f;
                                    float distY2 = (maxY - minY) / 3.0f;
                                    GL11.glBegin(3);
                                    GL11.glVertex2f(minX, minY + distY2);
                                    GL11.glVertex2f(minX, minY);
                                    GL11.glVertex2f(minX + distX2, minY);
                                    GL11.glEnd();
                                    GL11.glBegin(3);
                                    GL11.glVertex2f(minX, maxY - distY2);
                                    GL11.glVertex2f(minX, maxY);
                                    GL11.glVertex2f(minX + distX2, maxY);
                                    GL11.glEnd();
                                    GL11.glBegin(3);
                                    GL11.glVertex2f(maxX - distX2, minY);
                                    GL11.glVertex2f(maxX, minY);
                                    GL11.glVertex2f(maxX, minY + distY2);
                                    GL11.glEnd();
                                    GL11.glBegin(3);
                                    GL11.glVertex2f(maxX - distX2, maxY);
                                    GL11.glVertex2f(maxX, maxY);
                                    GL11.glVertex2f(maxX, maxY - distY2);
                                    GL11.glEnd();
                                } else {
                                    GL11.glBegin(2);
                                    GL11.glVertex2f(minX, minY);
                                    GL11.glVertex2f(minX, maxY);
                                    GL11.glVertex2f(maxX, maxY);
                                    GL11.glVertex2f(maxX, minY);
                                    GL11.glEnd();
                                }
                                if (this.real2dShowHealth.get().booleanValue()) {
                                    float barHeight = (maxY - minY) * (1.0f - (entityLiving.func_110143_aJ() / entityLiving.func_110138_aP()));
                                    GL11.glColor4f(0.1f, 1.0f, 0.1f, 1.0f);
                                    GL11.glBegin(7);
                                    GL11.glVertex2f(maxX + 2.0f, minY + barHeight);
                                    GL11.glVertex2f(maxX + 2.0f, maxY);
                                    GL11.glVertex2f(maxX + 4.0f, maxY);
                                    GL11.glVertex2f(maxX + 4.0f, minY + barHeight);
                                    GL11.glEnd();
                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                    GL11.glEnable(3553);
                                    GL11.glEnable(2929);
                                    f362mc.field_71466_p.func_175063_a(this.decimalFormat.format(entityLiving.func_110143_aJ()) + " HP", maxX + 4.0f, minY + barHeight, -1);
                                    GL11.glDisable(3553);
                                    GL11.glDisable(2929);
                                    GlStateManager.func_179117_G();
                                }
                                if (this.real2dShowHeldItem.get().booleanValue() && entityLiving.func_70694_bm() != null && entityLiving.func_70694_bm().func_77973_b() != null) {
                                    GL11.glEnable(3553);
                                    GL11.glEnable(2929);
                                    int stringWidth = f362mc.field_71466_p.func_78256_a(entityLiving.func_70694_bm().func_82833_r());
                                    f362mc.field_71466_p.func_175063_a(entityLiving.func_70694_bm().func_82833_r(), (minX + ((maxX - minX) / 2.0f)) - (stringWidth / 2), maxY + 2.0f, -1);
                                    GL11.glDisable(3553);
                                    GL11.glDisable(2929);
                                }
                                if (this.real2dShowName.get().booleanValue()) {
                                    GL11.glEnable(3553);
                                    GL11.glEnable(2929);
                                    int stringWidth2 = f362mc.field_71466_p.func_78256_a(entityLiving.func_145748_c_().func_150254_d());
                                    f362mc.field_71466_p.func_175063_a(entityLiving.func_145748_c_().func_150254_d(), (minX + ((maxX - minX) / 2.0f)) - (stringWidth2 / 2), minY - 12.0f, -1);
                                    GL11.glDisable(3553);
                                    GL11.glDisable(2929);
                                    break;
                                } else {
                                    break;
                                }
                            }
                        } else {
                            continue;
                        }
                        break;
                }
            }
        }
        if (real2d) {
            GL11.glEnable(2929);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        FramebufferShader framebufferShader;
        float f;
        String mode = this.modeValue.get().toLowerCase();
        if (mode.equalsIgnoreCase("shaderoutline")) {
            framebufferShader = OutlineShader.OUTLINE_SHADER;
        } else {
            framebufferShader = mode.equalsIgnoreCase("shaderglow") ? GlowShader.GLOW_SHADER : null;
        }
        FramebufferShader shader = framebufferShader;
        if (shader == null) {
            return;
        }
        shader.startDraw(event.getPartialTicks());
        renderNameTags = false;
        try {
            for (Entity entity : f362mc.field_71441_e.field_72996_f) {
                if (EntityUtils.isSelected(entity, false)) {
                    f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                }
            }
        } catch (Exception ex) {
            ClientUtils.getLogger().error("An error occurred while rendering all entities for shader esp", ex);
        }
        renderNameTags = true;
        if (mode.equalsIgnoreCase("shaderoutline")) {
            f = this.shaderOutlineRadius.get().floatValue();
        } else {
            f = mode.equalsIgnoreCase("shaderglow") ? this.shaderGlowRadius.get().floatValue() : 1.0f;
        }
        float radius = f;
        shader.stopDraw(getColor(null), radius, 1.0f);
    }

    public final Color getColor(Entity entity) {
        int index;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            if (this.colorModeValue.get().equalsIgnoreCase("Health")) {
                return BlendUtils.getHealthColor(entityLivingBase.func_110143_aJ(), entityLivingBase.func_110138_aP());
            }
            if (entityLivingBase.field_70737_aN > 0) {
                return Color.RED;
            }
            if (EntityUtils.isFriend(entityLivingBase)) {
                return Color.BLUE;
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
            case 2181788:
                if (str.equals("Fade")) {
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
            case true:
                return ColorUtils.fade(new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue()), 0, 100);
            default:
                return Color.white;
        }
    }
}
