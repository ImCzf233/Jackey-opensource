package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.TargetMark;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/RenderUtils.class */
public final class RenderUtils extends MinecraftInstance {
    public static int deltaTime;
    private static final Frustum frustrum;
    protected static float zLevel;
    private static final Map<Integer, Boolean> glCapMap = new HashMap();
    private static final int[] DISPLAY_LISTS_2D = new int[4];

    static {
        for (int i = 0; i < DISPLAY_LISTS_2D.length; i++) {
            DISPLAY_LISTS_2D[i] = GL11.glGenLists(1);
        }
        GL11.glNewList(DISPLAY_LISTS_2D[0], 4864);
        quickDrawRect(-7.0f, 2.0f, -4.0f, 3.0f);
        quickDrawRect(4.0f, 2.0f, 7.0f, 3.0f);
        quickDrawRect(-7.0f, 0.5f, -6.0f, 3.0f);
        quickDrawRect(6.0f, 0.5f, 7.0f, 3.0f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[1], 4864);
        quickDrawRect(-7.0f, 3.0f, -4.0f, 3.3f);
        quickDrawRect(4.0f, 3.0f, 7.0f, 3.3f);
        quickDrawRect(-7.3f, 0.5f, -7.0f, 3.3f);
        quickDrawRect(7.0f, 0.5f, 7.3f, 3.3f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[2], 4864);
        quickDrawRect(4.0f, -20.0f, 7.0f, -19.0f);
        quickDrawRect(-7.0f, -20.0f, -4.0f, -19.0f);
        quickDrawRect(6.0f, -20.0f, 7.0f, -17.5f);
        quickDrawRect(-7.0f, -20.0f, -6.0f, -17.5f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[3], 4864);
        quickDrawRect(7.0f, -20.0f, 7.3f, -17.5f);
        quickDrawRect(-7.3f, -20.0f, -7.0f, -17.5f);
        quickDrawRect(4.0f, -20.3f, 7.3f, -20.0f);
        quickDrawRect(-7.3f, -20.3f, -4.0f, -20.0f);
        GL11.glEndList();
        frustrum = new Frustum();
        zLevel = 0.0f;
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel2) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(x + 0, y + height, zLevel2).func_181673_a((textureX + 0) * 0.00390625f, (textureY + height) * 0.00390625f).func_181675_d();
        worldrenderer.func_181662_b(x + width, y + height, zLevel2).func_181673_a((textureX + width) * 0.00390625f, (textureY + height) * 0.00390625f).func_181675_d();
        worldrenderer.func_181662_b(x + width, y + 0, zLevel2).func_181673_a((textureX + width) * 0.00390625f, (textureY + 0) * 0.00390625f).func_181675_d();
        worldrenderer.func_181662_b(x + 0, y + 0, zLevel2).func_181673_a((textureX + 0) * 0.00390625f, (textureY + 0) * 0.00390625f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.func_174813_aQ()) || entity.field_70158_ak;
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = f362mc.func_175606_aa();
        frustrum.func_78547_a(current.field_70165_t, current.field_70163_u, current.field_70161_v);
        return frustrum.func_78546_a(bb);
    }

    public static float interpolate(float current, float old, float scale) {
        return old + ((current - old) * scale);
    }

    public static double interpolate(double current, double old, double scale) {
        return old + ((current - old) * scale);
    }

    public static int SkyRainbow(int var2, float st, float bright) {
        double v1 = (Math.ceil(System.currentTimeMillis() + (var2 * 109)) / 5.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (v1 / 360.0d))) < 0.5d ? -((float) (v1 / 360.0d)) : (float) (v1 / 360.0d), st, bright).getRGB();
    }

    public static Color skyRainbow(int var2, float st, float bright) {
        double v1 = (Math.ceil(System.currentTimeMillis() + (var2 * 109)) / 5.0d) % 360.0d;
        return Color.getHSBColor(((double) ((float) (v1 / 360.0d))) < 0.5d ? -((float) (v1 / 360.0d)) : (float) (v1 / 360.0d), st, bright);
    }

    public static int getRainbowOpaque(int seconds, float saturation, float brightness, int index) {
        float hue = ((float) ((System.currentTimeMillis() + index) % (seconds * 1000))) / (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getNormalRainbow(int delay, float sat, float brg) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0d);
        return Color.getHSBColor((float) ((rainbowState % 360.0d) / 360.0d), sat, brg).getRGB();
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }

    public static void drawExhiRect(float x, float y, float x2, float y2) {
        drawRect(x - 3.5f, y - 3.5f, x2 + 3.5f, y2 + 3.5f, Color.black.getRGB());
        drawRect(x - 3.0f, y - 3.0f, x2 + 3.0f, y2 + 3.0f, new Color(50, 50, 50).getRGB());
        drawRect(x - 2.5f, y - 2.5f, x2 + 2.5f, y2 + 2.5f, new Color(26, 26, 26).getRGB());
        drawRect(x - 0.5f, y - 0.5f, x2 + 0.5f, y2 + 0.5f, new Color(50, 50, 50).getRGB());
        drawRect(x, y, x2, y2, new Color(18, 18, 18).getRGB());
    }

    public static void drawExhiRect(float x, float y, float x2, float y2, float alpha) {
        drawRect(x - 3.5f, y - 3.5f, x2 + 3.5f, y2 + 3.5f, new Color(0.0f, 0.0f, 0.0f, alpha).getRGB());
        drawRect(x - 3.0f, y - 3.0f, x2 + 3.0f, y2 + 3.0f, new Color(0.19607843f, 0.19607843f, 0.19607843f, alpha).getRGB());
        drawRect(x - 2.5f, y - 2.5f, x2 + 2.5f, y2 + 2.5f, new Color(0.101960786f, 0.101960786f, 0.101960786f, alpha).getRGB());
        drawRect(x - 0.5f, y - 0.5f, x2 + 0.5f, y2 + 0.5f, new Color(0.19607843f, 0.19607843f, 0.19607843f, alpha).getRGB());
        drawRect(x, y, x2, y2, new Color(0.07058824f, 0.07058824f, 0.07058824f, alpha).getRGB());
    }

    public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
        drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color, true);
    }

    public static void originalRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
        float alpha = ((color >> 24) & 255) / 255.0f;
        float red = ((color >> 16) & 255) / 255.0f;
        float green = ((color >> 8) & 255) / 255.0f;
        float blue = (color & 255) / 255.0f;
        if (paramXStart > paramXEnd) {
            paramXStart = paramXEnd;
            paramXEnd = paramXStart;
        }
        if (paramYStart > paramYEnd) {
            paramYStart = paramYEnd;
            paramYEnd = paramYStart;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179131_c(red, green, blue, alpha);
        worldrenderer.func_181668_a(9, DefaultVertexFormats.field_181705_e);
        double d = 0.0d;
        while (true) {
            double i = d;
            if (i > 90.0d) {
                break;
            }
            worldrenderer.func_181662_b(x2 + (Math.sin(i * 0.017453292519943295d) * radius), y2 + (Math.cos(i * 0.017453292519943295d) * radius), 0.0d).func_181675_d();
            d = i + 1.0d;
        }
        double d2 = 90.0d;
        while (true) {
            double i2 = d2;
            if (i2 > 180.0d) {
                break;
            }
            worldrenderer.func_181662_b(x2 + (Math.sin(i2 * 0.017453292519943295d) * radius), y1 + (Math.cos(i2 * 0.017453292519943295d) * radius), 0.0d).func_181675_d();
            d2 = i2 + 1.0d;
        }
        double d3 = 180.0d;
        while (true) {
            double i3 = d3;
            if (i3 > 270.0d) {
                break;
            }
            worldrenderer.func_181662_b(x1 + (Math.sin(i3 * 0.017453292519943295d) * radius), y1 + (Math.cos(i3 * 0.017453292519943295d) * radius), 0.0d).func_181675_d();
            d3 = i3 + 1.0d;
        }
        double d4 = 270.0d;
        while (true) {
            double i4 = d4;
            if (i4 <= 360.0d) {
                worldrenderer.func_181662_b(x1 + (Math.sin(i4 * 0.017453292519943295d) * radius), y2 + (Math.cos(i4 * 0.017453292519943295d) * radius), 0.0d).func_181675_d();
                d4 = i4 + 1.0d;
            } else {
                tessellator.func_78381_a();
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                return;
            }
        }
    }

    public static void newDrawRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            left = right;
            right = left;
        }
        if (top < bottom) {
            top = bottom;
            bottom = top;
        }
        float f3 = ((color >> 24) & 255) / 255.0f;
        float f = ((color >> 16) & 255) / 255.0f;
        float f1 = ((color >> 8) & 255) / 255.0f;
        float f2 = (color & 255) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179131_c(f, f1, f2, f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(left, bottom, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(right, top, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(left, top, 0.0d).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void newDrawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            left = right;
            right = left;
        }
        if (top < bottom) {
            top = bottom;
            bottom = top;
        }
        float f3 = ((color >> 24) & 255) / 255.0f;
        float f = ((color >> 16) & 255) / 255.0f;
        float f1 = ((color >> 8) & 255) / 255.0f;
        float f2 = (color & 255) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179131_c(f, f1, f2, f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(left, bottom, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(right, top, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(left, top, 0.0d).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush) {
        float alpha = ((color >> 24) & 255) / 255.0f;
        float red = ((color >> 16) & 255) / 255.0f;
        float green = ((color >> 8) & 255) / 255.0f;
        float blue = (color & 255) / 255.0f;
        if (paramXStart > paramXEnd) {
            paramXStart = paramXEnd;
            paramXEnd = paramXStart;
        }
        if (paramYStart > paramYEnd) {
            paramYStart = paramYEnd;
            paramYEnd = paramYStart;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        if (popPush) {
            GL11.glPushMatrix();
        }
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        double d = 0.0d;
        while (true) {
            double i = d;
            if (i > 90.0d) {
                break;
            }
            GL11.glVertex2d(x2 + (Math.sin(i * 0.017453292519943295d) * radius), y2 + (Math.cos(i * 0.017453292519943295d) * radius));
            d = i + 1.0d;
        }
        double d2 = 90.0d;
        while (true) {
            double i2 = d2;
            if (i2 > 180.0d) {
                break;
            }
            GL11.glVertex2d(x2 + (Math.sin(i2 * 0.017453292519943295d) * radius), y1 + (Math.cos(i2 * 0.017453292519943295d) * radius));
            d2 = i2 + 1.0d;
        }
        double d3 = 180.0d;
        while (true) {
            double i3 = d3;
            if (i3 > 270.0d) {
                break;
            }
            GL11.glVertex2d(x1 + (Math.sin(i3 * 0.017453292519943295d) * radius), y1 + (Math.cos(i3 * 0.017453292519943295d) * radius));
            d3 = i3 + 1.0d;
        }
        double d4 = 270.0d;
        while (true) {
            double i4 = d4;
            if (i4 > 360.0d) {
                break;
            }
            GL11.glVertex2d(x1 + (Math.sin(i4 * 0.017453292519943295d) * radius), y2 + (Math.cos(i4 * 0.017453292519943295d) * radius));
            d4 = i4 + 1.0d;
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        if (popPush) {
            GL11.glPopMatrix();
        }
    }

    public static void customRounded(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float rTL, float rTR, float rBR, float rBL, int color) {
        float alpha = ((color >> 24) & 255) / 255.0f;
        float red = ((color >> 16) & 255) / 255.0f;
        float green = ((color >> 8) & 255) / 255.0f;
        float blue = (color & 255) / 255.0f;
        if (paramXStart > paramXEnd) {
            paramXStart = paramXEnd;
            paramXEnd = paramXStart;
        }
        if (paramYStart > paramYEnd) {
            paramYStart = paramYEnd;
            paramYEnd = paramYStart;
        }
        double xTL = paramXStart + rTL;
        double yTL = paramYStart + rTL;
        double xTR = paramXEnd - rTR;
        double yTR = paramYStart + rTR;
        double xBR = paramXEnd - rBR;
        double yBR = paramYEnd - rBR;
        double xBL = paramXStart + rBL;
        double yBL = paramYEnd - rBL;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        double d = 0.0d;
        while (true) {
            double i = d;
            if (i > 90.0d) {
                break;
            }
            GL11.glVertex2d(xBR + (Math.sin(i * 0.017453292519943295d) * rBR), yBR + (Math.cos(i * 0.017453292519943295d) * rBR));
            d = i + 1.0d;
        }
        double d2 = 90.0d;
        while (true) {
            double i2 = d2;
            if (i2 > 180.0d) {
                break;
            }
            GL11.glVertex2d(xTR + (Math.sin(i2 * 0.017453292519943295d) * rTR), yTR + (Math.cos(i2 * 0.017453292519943295d) * rTR));
            d2 = i2 + 1.0d;
        }
        double d3 = 180.0d;
        while (true) {
            double i3 = d3;
            if (i3 > 270.0d) {
                break;
            }
            GL11.glVertex2d(xTL + (Math.sin(i3 * 0.017453292519943295d) * rTL), yTL + (Math.cos(i3 * 0.017453292519943295d) * rTL));
            d3 = i3 + 1.0d;
        }
        double d4 = 270.0d;
        while (true) {
            double i4 = d4;
            if (i4 <= 360.0d) {
                GL11.glVertex2d(xBL + (Math.sin(i4 * 0.017453292519943295d) * rBL), yBL + (Math.cos(i4 * 0.017453292519943295d) * rBL));
                d4 = i4 + 1.0d;
            } else {
                GL11.glEnd();
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
                return;
            }
        }
    }

    public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
        if (paramXStart > paramXEnd) {
            paramXStart = paramXEnd;
            paramXEnd = paramXStart;
        }
        if (paramYStart > paramYEnd) {
            paramYStart = paramYEnd;
            paramYEnd = paramYStart;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(9);
        double d = 0.0d;
        while (true) {
            double i = d;
            if (i > 90.0d) {
                break;
            }
            GL11.glVertex2d(x2 + (Math.sin(i * 0.017453292519943295d) * radius), y2 + (Math.cos(i * 0.017453292519943295d) * radius));
            d = i + 1.0d;
        }
        double d2 = 90.0d;
        while (true) {
            double i2 = d2;
            if (i2 > 180.0d) {
                break;
            }
            GL11.glVertex2d(x2 + (Math.sin(i2 * 0.017453292519943295d) * radius), y1 + (Math.cos(i2 * 0.017453292519943295d) * radius));
            d2 = i2 + 1.0d;
        }
        double d3 = 180.0d;
        while (true) {
            double i3 = d3;
            if (i3 > 270.0d) {
                break;
            }
            GL11.glVertex2d(x1 + (Math.sin(i3 * 0.017453292519943295d) * radius), y1 + (Math.cos(i3 * 0.017453292519943295d) * radius));
            d3 = i3 + 1.0d;
        }
        double d4 = 270.0d;
        while (true) {
            double i4 = d4;
            if (i4 <= 360.0d) {
                GL11.glVertex2d(x1 + (Math.sin(i4 * 0.017453292519943295d) * radius), y2 + (Math.cos(i4 * 0.017453292519943295d) * radius));
                d4 = i4 + 1.0d;
            } else {
                GL11.glEnd();
                GL11.glDisable(2848);
                return;
            }
        }
    }

    public static void drawTriAngle(float cx, float cy, float r, float n, Color color, boolean polygon) {
        float cx2 = (float) (cx * 2.0d);
        float cy2 = (float) (cy * 2.0d);
        double b = 6.2831852d / n;
        double p = Math.cos(b);
        double s = Math.sin(b);
        double x = (float) (r * 2.0d);
        double y = 0.0d;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GL11.glLineWidth(1.0f);
        enableGlCap(2848);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179117_G();
        glColor(color);
        GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
        worldrenderer.func_181668_a(polygon ? 9 : 2, DefaultVertexFormats.field_181705_e);
        for (int ii = 0; ii < n; ii++) {
            worldrenderer.func_181662_b(x + cx2, y + cy2, 0.0d).func_181675_d();
            double t = x;
            x = (p * x) - (s * y);
            y = (s * t) + (p * y);
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = ((col1 >> 24) & 255) / 255.0f;
        float f2 = ((col1 >> 16) & 255) / 255.0f;
        float f3 = ((col1 >> 8) & 255) / 255.0f;
        float f4 = (col1 & 255) / 255.0f;
        float f5 = ((col2 >> 24) & 255) / 255.0f;
        float f6 = ((col2 >> 16) & 255) / 255.0f;
        float f7 = ((col2 >> 8) & 255) / 255.0f;
        float f8 = (col2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = ((startColor >> 24) & 255) / 255.0f;
        float f1 = ((startColor >> 16) & 255) / 255.0f;
        float f2 = ((startColor >> 8) & 255) / 255.0f;
        float f3 = (startColor & 255) / 255.0f;
        float f4 = ((endColor >> 24) & 255) / 255.0f;
        float f5 = ((endColor >> 16) & 255) / 255.0f;
        float f6 = ((endColor >> 8) & 255) / 255.0f;
        float f7 = (endColor & 255) / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179103_j(7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(right, top, zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b(left, top, zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b(left, bottom, zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
    }

    public static void drawGradientSideways(float left, float top, float right, float bottom, int col1, int col2) {
        float f = ((col1 >> 24) & 255) / 255.0f;
        float f2 = ((col1 >> 16) & 255) / 255.0f;
        float f3 = ((col1 >> 8) & 255) / 255.0f;
        float f4 = (col1 & 255) / 255.0f;
        float f5 = ((col2 >> 24) & 255) / 255.0f;
        float f6 = ((col2 >> 16) & 255) / 255.0f;
        float f7 = ((col2 >> 8) & 255) / 255.0f;
        float f8 = (col2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2f(left, top);
        GL11.glVertex2f(left, bottom);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2f(right, bottom);
        GL11.glVertex2f(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline) {
        RenderManager renderManager = f362mc.func_175598_ae();
        Timer timer = f362mc.field_71428_T;
        double x = blockPos.func_177958_n() - renderManager.field_78725_b;
        double y = blockPos.func_177956_o() - renderManager.field_78726_c;
        double z = blockPos.func_177952_p() - renderManager.field_78723_d;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0d, y + 1.0d, z + 1.0d);
        Block block = BlockUtils.getBlock(blockPos);
        if (block != null) {
            EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
            double posX = ((EntityPlayer) entityPlayerSP).field_70142_S + ((((EntityPlayer) entityPlayerSP).field_70165_t - ((EntityPlayer) entityPlayerSP).field_70142_S) * timer.field_74281_c);
            double posY = ((EntityPlayer) entityPlayerSP).field_70137_T + ((((EntityPlayer) entityPlayerSP).field_70163_u - ((EntityPlayer) entityPlayerSP).field_70137_T) * timer.field_74281_c);
            double posZ = ((EntityPlayer) entityPlayerSP).field_70136_U + ((((EntityPlayer) entityPlayerSP).field_70161_v - ((EntityPlayer) entityPlayerSP).field_70136_U) * timer.field_74281_c);
            axisAlignedBB = block.func_180646_a(f362mc.field_71441_e, blockPos).func_72314_b(0.0020000000949949026d, 0.0020000000949949026d, 0.0020000000949949026d).func_72317_d(-posX, -posY, -posZ);
        }
        GL11.glBlendFunc(770, 771);
        enableGlCap(3042);
        disableGlCap(3553, 2929);
        GL11.glDepthMask(false);
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : outline ? 26 : 35);
        drawFilledBox(axisAlignedBB);
        if (outline) {
            GL11.glLineWidth(1.0f);
            enableGlCap(2848);
            glColor(color);
            drawSelectionBoundingBox(axisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawEntityBox(Entity entity, Color color, boolean outline) {
        RenderManager renderManager = f362mc.func_175598_ae();
        Timer timer = f362mc.field_71428_T;
        GL11.glBlendFunc(770, 771);
        enableGlCap(3042);
        disableGlCap(3553, 2929);
        GL11.glDepthMask(false);
        double x = (entity.field_70142_S + ((entity.field_70165_t - entity.field_70142_S) * timer.field_74281_c)) - renderManager.field_78725_b;
        double y = (entity.field_70137_T + ((entity.field_70163_u - entity.field_70137_T) * timer.field_74281_c)) - renderManager.field_78726_c;
        double z = (entity.field_70136_U + ((entity.field_70161_v - entity.field_70136_U) * timer.field_74281_c)) - renderManager.field_78723_d;
        AxisAlignedBB entityBox = entity.func_174813_aQ();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(((entityBox.field_72340_a - entity.field_70165_t) + x) - 0.05d, (entityBox.field_72338_b - entity.field_70163_u) + y, ((entityBox.field_72339_c - entity.field_70161_v) + z) - 0.05d, (entityBox.field_72336_d - entity.field_70165_t) + x + 0.05d, (entityBox.field_72337_e - entity.field_70163_u) + y + 0.15d, (entityBox.field_72334_f - entity.field_70161_v) + z + 0.05d);
        if (outline) {
            GL11.glLineWidth(1.0f);
            enableGlCap(2848);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            drawSelectionBoundingBox(axisAlignedBB);
        }
        glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 35);
        drawFilledBox(axisAlignedBB);
        GlStateManager.func_179117_G();
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        glColor(color);
        drawFilledBox(axisAlignedBB);
        GlStateManager.func_179117_G();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawPlatform(double y, Color color, double size) {
        RenderManager renderManager = f362mc.func_175598_ae();
        double renderY = y - renderManager.field_78726_c;
        drawAxisAlignedBB(new AxisAlignedBB(size, renderY + 0.02d, size, -size, renderY, -size), color);
    }

    public static void drawPlatform(Entity entity, Color color) {
        RenderManager renderManager = f362mc.func_175598_ae();
        Timer timer = f362mc.field_71428_T;
        TargetMark targetMark = (TargetMark) LiquidBounce.moduleManager.getModule(TargetMark.class);
        if (targetMark == null) {
            return;
        }
        double x = (entity.field_70142_S + ((entity.field_70165_t - entity.field_70142_S) * timer.field_74281_c)) - renderManager.field_78725_b;
        double y = (entity.field_70137_T + ((entity.field_70163_u - entity.field_70137_T) * timer.field_74281_c)) - renderManager.field_78726_c;
        double z = (entity.field_70136_U + ((entity.field_70161_v - entity.field_70136_U) * timer.field_74281_c)) - renderManager.field_78723_d;
        AxisAlignedBB axisAlignedBB = entity.func_174813_aQ().func_72317_d(-entity.field_70165_t, -entity.field_70163_u, -entity.field_70161_v).func_72317_d(x, y - targetMark.moveMarkValue.get().floatValue(), z);
        drawAxisAlignedBB(new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e + 0.2d, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e + 0.26d, axisAlignedBB.field_72334_f), color);
    }

    public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawEntityOnScreen(double posX, double posY, float scale, EntityLivingBase entity) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179142_g();
        GlStateManager.func_179137_b(posX, posY, 50.0d);
        GlStateManager.func_179152_a(-scale, scale, scale);
        GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179137_b(0.0d, 0.0d, 0.0d);
        RenderManager rendermanager = f362mc.func_175598_ae();
        rendermanager.func_178631_a(180.0f);
        rendermanager.func_178633_a(false);
        rendermanager.func_147940_a(entity, 0.0d, 0.0d, 0.0d, 0.0f, 1.0f);
        rendermanager.func_178633_a(true);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase entity) {
        drawEntityOnScreen(posX, posY, scale, entity);
    }

    public static void quickDrawRect(float x, float y, float x2, float y2) {
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
    }

    public static void drawRect(float x, float y, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            left = right;
            right = left;
        }
        if (top < bottom) {
            top = bottom;
            bottom = top;
        }
        float f3 = ((color >> 24) & 255) / 255.0f;
        float f = ((color >> 16) & 255) / 255.0f;
        float f1 = ((color >> 8) & 255) / 255.0f;
        float f2 = (color & 255) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179131_c(f, f1, f2, f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(left, bottom, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(right, top, 0.0d).func_181675_d();
        worldrenderer.func_181662_b(left, top, 0.0d).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void quickDrawRect(float x, float y, float x2, float y2, int color) {
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
    }

    public static void drawRect(float x, float y, float x2, float y2, Color color) {
        drawRect(x, y, x2, y2, color.getRGB());
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
        drawRect(x, y, x2, y2, color2);
        drawBorder(x, y, x2, y2, width, color1);
    }

    public static void drawBorder(float x, float y, float x2, float y2, float width, int color1) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        glColor(color1);
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawRectBasedBorder(float x, float y, float x2, float y2, float width, int color1) {
        drawRect(x - (width / 2.0f), y - (width / 2.0f), x2 + (width / 2.0f), y + (width / 2.0f), color1);
        drawRect(x - (width / 2.0f), y + (width / 2.0f), x + (width / 2.0f), y2 + (width / 2.0f), color1);
        drawRect(x2 - (width / 2.0f), y + (width / 2.0f), x2 + (width / 2.0f), y2 + (width / 2.0f), color1);
        drawRect(x + (width / 2.0f), y2 - (width / 2.0f), x2 - (width / 2.0f), y2 + (width / 2.0f), color1);
    }

    public static void drawRectBasedBorder(double x, double y, double x2, double y2, double width, int color1) {
        newDrawRect(x - (width / 2.0d), y - (width / 2.0d), x2 + (width / 2.0d), y + (width / 2.0d), color1);
        newDrawRect(x - (width / 2.0d), y + (width / 2.0d), x + (width / 2.0d), y2 + (width / 2.0d), color1);
        newDrawRect(x2 - (width / 2.0d), y + (width / 2.0d), x2 + (width / 2.0d), y2 + (width / 2.0d), color1);
        newDrawRect(x + (width / 2.0d), y2 - (width / 2.0d), x2 - (width / 2.0d), y2 + (width / 2.0d), color1);
    }

    public static void quickDrawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
        quickDrawRect(x, y, x2, y2, color2);
        glColor(color1);
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
    }

    public static void drawLoadingCircle(float x, float y) {
        for (int i = 0; i < 4; i++) {
            int rot = (int) (((System.nanoTime() / 5000000) * i) % 360);
            drawCircle(x, y, i * 10, rot - 180, rot);
        }
    }

    public static void drawCircle(float x, float y, float radius, float lineWidth, int start, int end, Color color) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        glColor(color);
        GL11.glEnable(2848);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        float f = end;
        while (true) {
            float i = f;
            if (i >= start) {
                GL11.glVertex2f((float) (x + (Math.cos((i * 3.141592653589793d) / 180.0d) * radius * 1.001f)), (float) (y + (Math.sin((i * 3.141592653589793d) / 180.0d) * radius * 1.001f)));
                f = i - 4.0f;
            } else {
                GL11.glEnd();
                GL11.glDisable(2848);
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                return;
            }
        }
    }

    public static void drawCircle(float x, float y, float radius, float lineWidth, int start, int end) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        glColor(Color.WHITE);
        GL11.glEnable(2848);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        float f = end;
        while (true) {
            float i = f;
            if (i >= start) {
                GL11.glVertex2f((float) (x + (Math.cos((i * 3.141592653589793d) / 180.0d) * radius * 1.001f)), (float) (y + (Math.sin((i * 3.141592653589793d) / 180.0d) * radius * 1.001f)));
                f = i - 4.0f;
            } else {
                GL11.glEnd();
                GL11.glDisable(2848);
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                return;
            }
        }
    }

    public static void drawCircle(float x, float y, float radius, int start, int end) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        glColor(Color.WHITE);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        float f = end;
        while (true) {
            float i = f;
            if (i >= start) {
                GL11.glVertex2f((float) (x + (Math.cos((i * 3.141592653589793d) / 180.0d) * radius * 1.001f)), (float) (y + (Math.sin((i * 3.141592653589793d) / 180.0d) * radius * 1.001f)));
                f = i - 4.0f;
            } else {
                GL11.glEnd();
                GL11.glDisable(2848);
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                return;
            }
        }
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
        double dAngle = 6.283185307179586d / 50;
        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < 50; i++) {
            float x = (float) (radius * Math.sin(i * dAngle));
            float y = (float) (radius * Math.cos(i * dAngle));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.func_179124_c(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawFilledCircle(float xx, float yy, float radius, Color color) {
        double dAngle = 6.283185307179586d / 50;
        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < 50; i++) {
            float x = (float) (radius * Math.sin(i * dAngle));
            float y = (float) (radius * Math.cos(i * dAngle));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.func_179124_c(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        f362mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        f362mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage2(ResourceLocation image, float x, float y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(x, y, x);
        f362mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a(0, 0, 0.0f, 0.0f, width, height, width, height);
        GL11.glTranslatef(-x, -y, -x);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage3(ResourceLocation image, float x, float y, int width, int height, float r, float g, float b, float al) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, al);
        GL11.glTranslatef(x, y, x);
        f362mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a(0, 0, 0.0f, 0.0f, width, height, width, height);
        GL11.glTranslatef(-x, -y, -x);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawExhiEnchants(ItemStack stack, float x, float y) {
        RenderHelper.func_74518_a();
        GlStateManager.func_179097_i();
        GlStateManager.func_179084_k();
        GlStateManager.func_179117_G();
        if (stack.func_77973_b() instanceof ItemArmor) {
            int prot = EnchantmentHelper.func_77506_a(Enchantment.field_180310_c.field_77352_x, stack);
            int unb = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
            int thorn = EnchantmentHelper.func_77506_a(Enchantment.field_92091_k.field_77352_x, stack);
            if (prot > 0) {
                drawExhiOutlined(prot + "", drawExhiOutlined("P", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(prot), getMainColor(prot), true);
                y += 4.0f;
            }
            if (unb > 0) {
                drawExhiOutlined(unb + "", drawExhiOutlined("U", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(unb), getMainColor(unb), true);
                y += 4.0f;
            }
            if (thorn > 0) {
                drawExhiOutlined(thorn + "", drawExhiOutlined("T", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(thorn), getMainColor(thorn), true);
                y += 4.0f;
            }
        }
        if (stack.func_77973_b() instanceof ItemBow) {
            int power = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, stack);
            int punch = EnchantmentHelper.func_77506_a(Enchantment.field_77344_u.field_77352_x, stack);
            int flame = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, stack);
            int unb2 = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
            if (power > 0) {
                drawExhiOutlined(power + "", drawExhiOutlined("Pow", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(power), getMainColor(power), true);
                y += 4.0f;
            }
            if (punch > 0) {
                drawExhiOutlined(punch + "", drawExhiOutlined("Pun", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(punch), getMainColor(punch), true);
                y += 4.0f;
            }
            if (flame > 0) {
                drawExhiOutlined(flame + "", drawExhiOutlined("F", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(flame), getMainColor(flame), true);
                y += 4.0f;
            }
            if (unb2 > 0) {
                drawExhiOutlined(unb2 + "", drawExhiOutlined("U", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(unb2), getMainColor(unb2), true);
                y += 4.0f;
            }
        }
        if (stack.func_77973_b() instanceof ItemSword) {
            int sharp = EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, stack);
            int kb = EnchantmentHelper.func_77506_a(Enchantment.field_180313_o.field_77352_x, stack);
            int fire = EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack);
            int unb3 = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
            if (sharp > 0) {
                drawExhiOutlined(sharp + "", drawExhiOutlined("S", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(sharp), getMainColor(sharp), true);
                y += 4.0f;
            }
            if (kb > 0) {
                drawExhiOutlined(kb + "", drawExhiOutlined("K", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(kb), getMainColor(kb), true);
                y += 4.0f;
            }
            if (fire > 0) {
                drawExhiOutlined(fire + "", drawExhiOutlined("F", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(fire), getMainColor(fire), true);
                y += 4.0f;
            }
            if (unb3 > 0) {
                drawExhiOutlined(unb3 + "", drawExhiOutlined("U", x, y, 0.35f, -16777216, -1, true), y, 0.35f, getBorderColor(unb3), getMainColor(unb3), true);
                float f = y + 4.0f;
            }
        }
        GlStateManager.func_179126_j();
        RenderHelper.func_74520_c();
    }

    private static float drawExhiOutlined(String text, float x, float y, float borderWidth, int borderColor, int mainColor, boolean drawText) {
        Fonts.fontTahomaSmall.drawString(text, x, y - borderWidth, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x, y + borderWidth, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x - borderWidth, y, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x + borderWidth, y, borderColor);
        if (drawText) {
            Fonts.fontTahomaSmall.drawString(text, x, y, mainColor);
        }
        return (x + Fonts.fontTahomaSmall.getWidth(text)) - 2.0f;
    }

    private static int getMainColor(int level) {
        if (level == 4) {
            return -5636096;
        }
        return -1;
    }

    private static int getBorderColor(int level) {
        if (level == 2) {
            return 1884684117;
        }
        if (level == 3) {
            return 1879091882;
        }
        if (level == 4) {
            return 1890189312;
        }
        if (level >= 5) {
            return 1895803392;
        }
        return 1895825407;
    }

    public static void glColor(int red, int green, int blue, int alpha) {
        GlStateManager.func_179131_c(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
    }

    public static void glColor(Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        GlStateManager.func_179131_c(red, green, blue, alpha);
    }

    public static void glColor(int hex) {
        float alpha = ((hex >> 24) & 255) / 255.0f;
        float red = ((hex >> 16) & 255) / 255.0f;
        float green = ((hex >> 8) & 255) / 255.0f;
        float blue = (hex & 255) / 255.0f;
        GlStateManager.func_179131_c(red, green, blue, alpha);
    }

    public static void draw2D(EntityLivingBase entity, double posX, double posY, double posZ, int color, int backgroundColor) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(posX, posY, posZ);
        GlStateManager.func_179114_b(-f362mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179139_a(-0.1d, -0.1d, 0.1d);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179132_a(true);
        glColor(color);
        GL11.glCallList(DISPLAY_LISTS_2D[0]);
        glColor(backgroundColor);
        GL11.glCallList(DISPLAY_LISTS_2D[1]);
        GlStateManager.func_179137_b(0.0d, 21.0d + ((-(entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b)) * 12.0d), 0.0d);
        glColor(color);
        GL11.glCallList(DISPLAY_LISTS_2D[2]);
        glColor(backgroundColor);
        GL11.glCallList(DISPLAY_LISTS_2D[3]);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GlStateManager.func_179121_F();
    }

    public static void draw2D(BlockPos blockPos, int color, int backgroundColor) {
        RenderManager renderManager = f362mc.func_175598_ae();
        double posX = (blockPos.func_177958_n() + 0.5d) - renderManager.field_78725_b;
        double posY = blockPos.func_177956_o() - renderManager.field_78726_c;
        double posZ = (blockPos.func_177952_p() + 0.5d) - renderManager.field_78723_d;
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(posX, posY, posZ);
        GlStateManager.func_179114_b(-f362mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179139_a(-0.1d, -0.1d, 0.1d);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179132_a(true);
        glColor(color);
        GL11.glCallList(DISPLAY_LISTS_2D[0]);
        glColor(backgroundColor);
        GL11.glCallList(DISPLAY_LISTS_2D[1]);
        GlStateManager.func_179109_b(0.0f, 9.0f, 0.0f);
        glColor(color);
        GL11.glCallList(DISPLAY_LISTS_2D[2]);
        glColor(backgroundColor);
        GL11.glCallList(DISPLAY_LISTS_2D[3]);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GlStateManager.func_179121_F();
    }

    public static void renderNameTag(String string, double x, double y, double z) {
        RenderManager renderManager = f362mc.func_175598_ae();
        GL11.glPushMatrix();
        GL11.glTranslated(x - renderManager.field_78725_b, y - renderManager.field_78726_c, z - renderManager.field_78723_d);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-f362mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f362mc.func_175598_ae().field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-0.05f, -0.05f, 0.05f);
        setGlCap(2896, false);
        setGlCap(2929, false);
        setGlCap(3042, true);
        GL11.glBlendFunc(770, 771);
        int width = Fonts.font35.func_78256_a(string) / 2;
        Gui.func_73734_a((-width) - 1, -1, width + 1, Fonts.font35.field_78288_b, Integer.MIN_VALUE);
        Fonts.font35.func_175065_a(string, -width, 1.5f, Color.WHITE.getRGB(), true);
        resetCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public static void drawLine(float x, float y, float x1, float y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public static void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public static void makeScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scaledResolution = new ScaledResolution(f362mc);
        int factor = scaledResolution.func_78325_e();
        GL11.glScissor((int) (x * factor), (int) ((scaledResolution.func_78328_b() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    public static void resetCaps() {
        glCapMap.forEach((v0, v1) -> {
            setGlState(v0, v1);
        });
    }

    public static void enableGlCap(int cap) {
        setGlCap(cap, true);
    }

    public static void enableGlCap(int... caps) {
        for (int cap : caps) {
            setGlCap(cap, true);
        }
    }

    public static void disableGlCap(int cap) {
        setGlCap(cap, true);
    }

    public static void disableGlCap(int... caps) {
        for (int cap : caps) {
            setGlCap(cap, false);
        }
    }

    public static void setGlCap(int cap, boolean state) {
        glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
        setGlState(cap, state);
    }

    public static void setGlState(int cap, boolean state) {
        if (state) {
            GL11.glEnable(cap);
        } else {
            GL11.glDisable(cap);
        }
    }
}
