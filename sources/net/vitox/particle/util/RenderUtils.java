package net.vitox.particle.util;

import org.lwjgl.opengl.GL11;

/* loaded from: Jackey Client b2.jar:net/vitox/particle/util/RenderUtils.class */
public class RenderUtils {
    public static void connectPoints(float xOne, float yOne, float xTwo, float yTwo) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(0.5f);
        GL11.glBegin(1);
        GL11.glVertex2f(xOne, yOne);
        GL11.glVertex2f(xTwo, yTwo);
        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float x, float y, float radius, int color) {
        float alpha = ((color >> 24) & 255) / 255.0f;
        float red = ((color >> 16) & 255) / 255.0f;
        float green = ((color >> 8) & 255) / 255.0f;
        float blue = (color & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glLineWidth(1.0f);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; i++) {
            GL11.glVertex2d(x + (Math.sin((i * 3.141592653589793d) / 180.0d) * radius), y + (Math.cos((i * 3.141592653589793d) / 180.0d) * radius));
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
