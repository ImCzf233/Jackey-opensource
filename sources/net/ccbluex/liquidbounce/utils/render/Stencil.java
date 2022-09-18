package net.ccbluex.liquidbounce.utils.render;

import kotlin.jvm.internal.CharCompanionObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/Stencil.class */
public class Stencil {

    /* renamed from: mc */
    static Minecraft f379mc = Minecraft.func_71410_x();

    public static void dispose() {
        GL11.glDisable(2960);
        GlStateManager.func_179118_c();
        GlStateManager.func_179084_k();
    }

    public static void erase(boolean invert) {
        GL11.glStencilFunc(invert ? 514 : 517, 1, (int) CharCompanionObject.MAX_VALUE);
        GL11.glStencilOp(7680, 7680, 7681);
        GlStateManager.func_179135_a(true, true, true, true);
        GlStateManager.func_179141_d();
        GlStateManager.func_179147_l();
        GL11.glAlphaFunc(516, 0.0f);
    }

    public static void write(boolean renderClipLayer) {
        checkSetupFBO();
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(519, 1, (int) CharCompanionObject.MAX_VALUE);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!renderClipLayer) {
            GlStateManager.func_179135_a(false, false, false, false);
        }
    }

    public static void write(boolean renderClipLayer, Framebuffer fb, boolean clearStencil, boolean invert) {
        checkSetupFBO(fb);
        if (clearStencil) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
            GL11.glEnable(2960);
        }
        GL11.glStencilFunc(519, invert ? 0 : 1, (int) CharCompanionObject.MAX_VALUE);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!renderClipLayer) {
            GlStateManager.func_179135_a(false, false, false, false);
        }
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = f379mc.func_147110_a();
        if (fbo != null && fbo.field_147624_h > -1) {
            setupFBO(fbo);
            fbo.field_147624_h = -1;
        }
    }

    public static void checkSetupFBO(Framebuffer fbo) {
        if (fbo != null && fbo.field_147624_h > -1) {
            setupFBO(fbo);
            fbo.field_147624_h = -1;
        }
    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.field_147624_h);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
}
