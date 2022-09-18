package net.ccbluex.liquidbounce.utils.render.shader;

import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/shader/FramebufferShader.class */
public abstract class FramebufferShader extends Shader {
    private static Framebuffer framebuffer;
    protected float red;
    protected float green;
    protected float blue;
    protected float alpha = 1.0f;
    protected float radius = 2.0f;
    protected float quality = 1.0f;
    private boolean entityShadows;

    public FramebufferShader(String fragmentShader) {
        super(fragmentShader);
    }

    public void startDraw(float partialTicks) {
        GlStateManager.func_179141_d();
        GlStateManager.func_179094_E();
        GlStateManager.func_179123_a();
        framebuffer = setupFrameBuffer(framebuffer);
        framebuffer.func_147614_f();
        framebuffer.func_147610_a(true);
        this.entityShadows = f362mc.field_71474_y.field_181151_V;
        f362mc.field_71474_y.field_181151_V = false;
        f362mc.field_71460_t.func_78479_a(partialTicks, 0);
    }

    public void stopDraw(Color color, float radius, float quality) {
        f362mc.field_71474_y.field_181151_V = this.entityShadows;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        f362mc.func_147110_a().func_147610_a(true);
        this.red = color.getRed() / 255.0f;
        this.green = color.getGreen() / 255.0f;
        this.blue = color.getBlue() / 255.0f;
        this.alpha = color.getAlpha() / 255.0f;
        this.radius = radius;
        this.quality = quality;
        f362mc.field_71460_t.func_175072_h();
        RenderHelper.func_74518_a();
        startShader();
        f362mc.field_71460_t.func_78478_c();
        drawFramebuffer(framebuffer);
        stopShader();
        f362mc.field_71460_t.func_175072_h();
        GlStateManager.func_179121_F();
        GlStateManager.func_179099_b();
    }

    public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
        if (frameBuffer != null) {
            frameBuffer.func_147608_a();
        }
        return new Framebuffer(f362mc.field_71443_c, f362mc.field_71440_d, true);
    }

    public void drawFramebuffer(Framebuffer framebuffer2) {
        ScaledResolution scaledResolution = new ScaledResolution(f362mc);
        GL11.glBindTexture(3553, framebuffer2.field_147617_g);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0d, 1.0d);
        GL11.glVertex2d(0.0d, 0.0d);
        GL11.glTexCoord2d(0.0d, 0.0d);
        GL11.glVertex2d(0.0d, scaledResolution.func_78328_b());
        GL11.glTexCoord2d(1.0d, 0.0d);
        GL11.glVertex2d(scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
        GL11.glTexCoord2d(1.0d, 1.0d);
        GL11.glVertex2d(scaledResolution.func_78326_a(), 0.0d);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
}
