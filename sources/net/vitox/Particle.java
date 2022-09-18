package net.vitox;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.vitox.particle.util.RenderUtils;

/* loaded from: Jackey Client b2.jar:net/vitox/Particle.class */
public class Particle {

    /* renamed from: x */
    public float f380x;

    /* renamed from: y */
    public float f381y;
    private int height;
    private int width;
    private final float ySpeed = new Random().nextInt(5);
    private final float xSpeed = new Random().nextInt(5);
    public final float size = genRandom();

    public Particle(int x, int y) {
        this.f380x = x;
        this.f381y = y;
    }

    private float lint1(float f) {
        return (1.02f * (1.0f - f)) + (1.0f * f);
    }

    private float lint2(float f) {
        return 1.02f + (f * (-0.01999998f));
    }

    public void connect(float x, float y) {
        RenderUtils.connectPoints(getX(), getY(), x, y);
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getX() {
        return this.f380x;
    }

    public void setX(int x) {
        this.f380x = x;
    }

    public float getY() {
        return this.f381y;
    }

    public void setY(int y) {
        this.f381y = y;
    }

    public void interpolation() {
        for (int n = 0; n <= 64; n++) {
            float f = n / 64.0f;
            float p1 = lint1(f);
            float p2 = lint2(f);
            if (p1 != p2) {
                this.f381y -= f;
                this.f380x -= f;
            }
        }
    }

    public void fall() {
        Minecraft mc = Minecraft.func_71410_x();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        this.f381y += this.ySpeed;
        this.f380x += this.xSpeed;
        if (this.f381y > mc.field_71440_d) {
            this.f381y = 1.0f;
        }
        if (this.f380x > mc.field_71443_c) {
            this.f380x = 1.0f;
        }
        if (this.f380x < 1.0f) {
            this.f380x = scaledResolution.func_78326_a();
        }
        if (this.f381y < 1.0f) {
            this.f381y = scaledResolution.func_78328_b();
        }
    }

    private float genRandom() {
        return (float) (0.30000001192092896d + (Math.random() * 1.2999999523162842d));
    }
}
