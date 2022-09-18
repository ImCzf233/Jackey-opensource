package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/UiUtils.class */
public class UiUtils {
    public static int width() {
        return new ScaledResolution(Minecraft.func_71410_x()).func_78326_a();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.func_71410_x()).func_78328_b();
    }

    public static int anima(int target, int speed) {
        int a = 0;
        if (0 < target) {
            a = 0 + speed;
        }
        if (a > target) {
            a -= speed;
        }
        return a;
    }

    private static float clamp(float t, float x, float y) {
        return t < x ? x : t > y ? y : t;
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        new ScaledResolution(Minecraft.func_71410_x());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(image);
        Gui.func_146110_a(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, Color color) {
        new ScaledResolution(Minecraft.func_71410_x());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(color.getRed() / 255.0f, color.getBlue() / 255.0f, color.getRed() / 255.0f, 1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(image);
        Gui.func_146110_a(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawRoundRect(double d, double e, double g, double h, int color) {
        drawRect(d + 1.0d, e, g - 1.0d, h, color);
        drawRect(d, e + 1.0d, d + 1.0d, h - 1.0d, color);
        drawRect(d + 1.0d, e + 1.0d, d + 0.5d, e + 0.5d, color);
        drawRect(d + 1.0d, e + 1.0d, d + 0.5d, e + 0.5d, color);
        drawRect(g - 1.0d, e + 1.0d, g - 0.5d, e + 0.5d, color);
        drawRect(g - 1.0d, e + 1.0d, g, h - 1.0d, color);
        drawRect(d + 1.0d, h - 1.0d, d + 0.5d, h - 0.5d, color);
        drawRect(g - 1.0d, h - 1.0d, g - 0.5d, h - 0.5d, color);
    }

    public static void drawRoundRectWithRect(double d, double e, double g, double h, int color) {
        drawRect(d + 1.0d, e, g - 1.0d, h, color);
        drawRect(d, e + 1.0d, d + 1.0d, h - 1.0d, color);
        drawRect(d + 1.0d, e + 1.0d, d + 0.5d, e + 0.5d, color);
        drawRect(d + 1.0d, e + 1.0d, d + 0.5d, e + 0.5d, color);
        drawRect(g - 1.0d, e + 1.0d, g - 0.5d, e + 0.5d, color);
        drawRect(g - 1.0d, e + 1.0d, g, h - 1.0d, color);
        drawRect(d + 1.0d, h - 1.0d, d + 0.5d, h - 0.5d, color);
        drawRect(g - 1.0d, h - 1.0d, g - 0.5d, h - 0.5d, color);
    }

    public static void startGlScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.func_71410_x();
        int scaleFactor = 1;
        int k = mc.field_71474_y.field_74335_Z;
        if (k == 0) {
            k = 1000;
        }
        while (scaleFactor < k && mc.field_71443_c / (scaleFactor + 1) >= 320 && mc.field_71440_d / (scaleFactor + 1) >= 240) {
            scaleFactor++;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        GL11.glScissor(x * scaleFactor, mc.field_71440_d - ((y + height) * scaleFactor), width * scaleFactor, height * scaleFactor);
    }

    public static void stopGlScissor() {
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
        float f = ((col1 >> 24) & 255) / 255.0f;
        float f1 = ((col1 >> 16) & 255) / 255.0f;
        float f2 = ((col1 >> 8) & 255) / 255.0f;
        float f3 = (col1 & 255) / 255.0f;
        float f4 = ((col2 >> 24) & 255) / 255.0f;
        float f5 = ((col2 >> 16) & 255) / 255.0f;
        float f6 = ((col2 >> 8) & 255) / 255.0f;
        float f7 = (col2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = ((col1 >> 24) & 255) / 255.0f;
        float f1 = ((col1 >> 16) & 255) / 255.0f;
        float f2 = ((col1 >> 8) & 255) / 255.0f;
        float f3 = (col1 & 255) / 255.0f;
        float f4 = ((col2 >> 24) & 255) / 255.0f;
        float f5 = ((col2 >> 16) & 255) / 255.0f;
        float f6 = ((col2 >> 8) & 255) / 255.0f;
        float f7 = (col2 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
    }

    public static void outlineRect(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(x, y, x + width, y1, borderColor);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(x1 - width, y, x1, y1, borderColor);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void fastShadowRoundedRect(float x, float y, float x2, float y2, float rad, float width, float r, float g, float b, float al) {
        Stencil.write(true);
        RenderUtils.drawRoundedRect(x, y, x2, y2, rad, new Color(r, g, b, al).getRGB());
        Stencil.erase(false);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glColor4f(r, g, b, al);
        GL11.glBegin(8);
        GL11.glVertex2f(x + (width / 2.0f), y + (width / 2.0f));
        GL11.glColor4f(r, g, b, 0.0f);
        GL11.glVertex2f(x - width, y - width);
        GL11.glColor4f(r, g, b, al);
        GL11.glVertex2f(x2 - (width / 2.0f), y + (width / 2.0f));
        GL11.glColor4f(r, g, b, 0.0f);
        GL11.glVertex2f(x2 + width, y - width);
        GL11.glColor4f(r, g, b, al);
        GL11.glVertex2f(x2 - (width / 2.0f), y2 - (width / 2.0f));
        GL11.glColor4f(r, g, b, 0.0f);
        GL11.glVertex2f(x2 + width, y2 + width);
        GL11.glColor4f(r, g, b, al);
        GL11.glVertex2f(x + (width / 2.0f), y2 - (width / 2.0f));
        GL11.glColor4f(r, g, b, 0.0f);
        GL11.glVertex2f(x - width, y2 + width);
        GL11.glColor4f(r, g, b, al);
        GL11.glVertex2f(x + (width / 2.0f), y + (width / 2.0f));
        GL11.glColor4f(r, g, b, 0.0f);
        GL11.glVertex2f(x - width, y - width);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        Stencil.dispose();
    }

    public static void fastShadowRoundedRect(float x, float y, float x2, float y2, float rad, float width, Color color) {
        fastShadowRoundedRect(x, y, x2, y2, rad, width, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public static void drawRect(double x, double y, double x2, double y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        RenderUtils.glColor(new Color(color));
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawBorder(float x, float y, float x2, float y2, float strength, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        RenderUtils.glColor(new Color(color));
        GL11.glBegin(7);
        GL11.glVertex2f(x - strength, y - strength);
        GL11.glVertex2f(x2 + strength, y - strength);
        GL11.glVertex2f(x2 + strength, y + strength);
        GL11.glVertex2f(x - strength, y + strength);
        GL11.glVertex2f(x - strength, y2 - strength);
        GL11.glVertex2f(x2 + strength, y2 - strength);
        GL11.glVertex2f(x2 + strength, y2 + strength);
        GL11.glVertex2f(x - strength, y2 + strength);
        GL11.glVertex2f(x - strength, y + strength);
        GL11.glVertex2f(x + strength, y + strength);
        GL11.glVertex2f(x + strength, y2 - strength);
        GL11.glVertex2f(x - strength, y2 - strength);
        GL11.glVertex2f(x2 - strength, y + strength);
        GL11.glVertex2f(x2 + strength, y + strength);
        GL11.glVertex2f(x2 + strength, y2 - strength);
        GL11.glVertex2f(x2 - strength, y2 - strength);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
