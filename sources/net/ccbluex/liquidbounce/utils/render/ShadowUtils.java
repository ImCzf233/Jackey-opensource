package net.ccbluex.liquidbounce.utils.render;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: ShadowUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000bJ*\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001d"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/render/ShadowUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "blurDirectory", "Lnet/minecraft/util/ResourceLocation;", "frameBuffer", "Lnet/minecraft/client/shader/Framebuffer;", "initFramebuffer", "lastHeight", "", "lastStrength", "", "lastWidth", "resultBuffer", "getResultBuffer", "()Lnet/minecraft/client/shader/Framebuffer;", "setResultBuffer", "(Lnet/minecraft/client/shader/Framebuffer;)V", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "initShaderIfRequired", "", "sc", "Lnet/minecraft/client/gui/ScaledResolution;", "strength", "shadow", "drawMethod", "Lkotlin/Function0;", "cutMethod", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/ShadowUtils.class */
public final class ShadowUtils extends MinecraftInstance {
    @Nullable
    private static Framebuffer initFramebuffer;
    @Nullable
    private static Framebuffer frameBuffer;
    @Nullable
    private static Framebuffer resultBuffer;
    @Nullable
    private static ShaderGroup shaderGroup;
    private static int lastWidth;
    private static int lastHeight;
    private static float lastStrength;
    @NotNull
    public static final ShadowUtils INSTANCE = new ShadowUtils();
    @NotNull
    private static final ResourceLocation blurDirectory = new ResourceLocation("liquidbounce+/shadow.json");

    private ShadowUtils() {
    }

    @Nullable
    public final Framebuffer getResultBuffer() {
        return resultBuffer;
    }

    public final void setResultBuffer(@Nullable Framebuffer framebuffer) {
        resultBuffer = framebuffer;
    }

    public final void initShaderIfRequired(@NotNull ScaledResolution sc, float strength) throws IOException {
        Intrinsics.checkNotNullParameter(sc, "sc");
        int width = sc.func_78326_a();
        int height = sc.func_78328_b();
        int factor = sc.func_78325_e();
        if (lastWidth != width || lastHeight != height || initFramebuffer == null || frameBuffer == null || shaderGroup == null) {
            initFramebuffer = new Framebuffer(width * factor, height * factor, true);
            Framebuffer framebuffer = initFramebuffer;
            Intrinsics.checkNotNull(framebuffer);
            framebuffer.func_147604_a(0.0f, 0.0f, 0.0f, 0.0f);
            Framebuffer framebuffer2 = initFramebuffer;
            Intrinsics.checkNotNull(framebuffer2);
            framebuffer2.func_147607_a(9729);
            shaderGroup = new ShaderGroup(MinecraftInstance.f362mc.func_110434_K(), MinecraftInstance.f362mc.func_110442_L(), initFramebuffer, blurDirectory);
            ShaderGroup shaderGroup2 = shaderGroup;
            Intrinsics.checkNotNull(shaderGroup2);
            shaderGroup2.func_148026_a(width * factor, height * factor);
            ShaderGroup shaderGroup3 = shaderGroup;
            Intrinsics.checkNotNull(shaderGroup3);
            frameBuffer = shaderGroup3.field_148035_a;
            ShaderGroup shaderGroup4 = shaderGroup;
            Intrinsics.checkNotNull(shaderGroup4);
            resultBuffer = shaderGroup4.func_177066_a("braindead");
            lastWidth = width;
            lastHeight = height;
            lastStrength = strength;
            int i = 0;
            while (i < 2) {
                int i2 = i;
                i++;
                ShaderGroup shaderGroup5 = shaderGroup;
                Intrinsics.checkNotNull(shaderGroup5);
                ((Shader) shaderGroup5.field_148031_d.get(i2)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            }
        }
        if (!(lastStrength == strength)) {
            lastStrength = strength;
            int i3 = 0;
            while (i3 < 2) {
                int i4 = i3;
                i3++;
                ShaderGroup shaderGroup6 = shaderGroup;
                Intrinsics.checkNotNull(shaderGroup6);
                ((Shader) shaderGroup6.field_148031_d.get(i4)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            }
        }
    }

    public final void shadow(float strength, @NotNull Functions<Unit> drawMethod, @NotNull Functions<Unit> cutMethod) {
        Intrinsics.checkNotNullParameter(drawMethod, "drawMethod");
        Intrinsics.checkNotNullParameter(cutMethod, "cutMethod");
        if (!OpenGlHelper.func_148822_b()) {
            return;
        }
        ScaledResolution sc = new ScaledResolution(MinecraftInstance.f362mc);
        int width = sc.func_78326_a();
        int height = sc.func_78328_b();
        initShaderIfRequired(sc, strength);
        if (initFramebuffer == null || resultBuffer == null || frameBuffer == null) {
            return;
        }
        MinecraftInstance.f362mc.func_147110_a().func_147609_e();
        Framebuffer framebuffer = initFramebuffer;
        Intrinsics.checkNotNull(framebuffer);
        framebuffer.func_147614_f();
        Framebuffer framebuffer2 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer2);
        framebuffer2.func_147614_f();
        Framebuffer framebuffer3 = initFramebuffer;
        Intrinsics.checkNotNull(framebuffer3);
        framebuffer3.func_147610_a(true);
        drawMethod.invoke();
        Framebuffer framebuffer4 = frameBuffer;
        Intrinsics.checkNotNull(framebuffer4);
        framebuffer4.func_147610_a(true);
        ShaderGroup shaderGroup2 = shaderGroup;
        Intrinsics.checkNotNull(shaderGroup2);
        shaderGroup2.func_148018_a(MinecraftInstance.f362mc.field_71428_T.field_74281_c);
        MinecraftInstance.f362mc.func_147110_a().func_147610_a(true);
        Framebuffer framebuffer5 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer5);
        Framebuffer framebuffer6 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer6);
        double fr_width = framebuffer5.field_147621_c / framebuffer6.field_147622_a;
        Framebuffer framebuffer7 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer7);
        Framebuffer framebuffer8 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer8);
        double fr_height = framebuffer7.field_147618_d / framebuffer8.field_147620_b;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GL11.glPushMatrix();
        GlStateManager.func_179140_f();
        GlStateManager.func_179118_c();
        GlStateManager.func_179098_w();
        GlStateManager.func_179097_i();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179135_a(true, true, true, true);
        Stencil.write(false);
        cutMethod.invoke();
        Stencil.erase(false);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        Framebuffer framebuffer9 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer9);
        framebuffer9.func_147612_c();
        GL11.glTexParameteri(3553, 10242, 33071);
        GL11.glTexParameteri(3553, 10243, 33071);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        worldrenderer.func_181662_b(0.0d, height, 0.0d).func_181673_a(0.0d, 0.0d).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(width, height, 0.0d).func_181673_a(fr_width, 0.0d).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(width, 0.0d, 0.0d).func_181673_a(fr_width, fr_height).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(0.0d, 0.0d, 0.0d).func_181673_a(0.0d, fr_height).func_181669_b(255, 255, 255, 255).func_181675_d();
        tessellator.func_78381_a();
        Framebuffer framebuffer10 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer10);
        framebuffer10.func_147606_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a(true);
        Stencil.dispose();
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
    }
}
