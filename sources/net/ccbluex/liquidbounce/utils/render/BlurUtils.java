package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
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

/* compiled from: BlurUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n��\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0012\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\u001eH\u0007J0\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0007J8\u0010$\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\u0006\u0010%\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0007JJ\u0010&\u001a\u00020\u00152\u0006\u0010'\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\n2\b\b\u0002\u0010,\u001a\u00020\u001cH\u0002J\b\u0010-\u001a\u00020\u0015H\u0002J\u001e\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\b2\u0006\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020\bR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��¨\u00060"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/render/BlurUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "framebuffer", "Lnet/minecraft/client/shader/Framebuffer;", "kotlin.jvm.PlatformType", "frbuffer", "lastFactor", "", "lastH", "", "lastHeight", "lastStrength", "lastW", "lastWeight", "lastWidth", "lastX", "lastY", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "blur", "", "posX", "posY", "posXEnd", "posYEnd", "blurStrength", "displayClipMask", "", "triggerMethod", "Lkotlin/Function0;", "blurArea", "x", "y", "x2", "y2", "blurAreaRounded", "rad", "setValues", "strength", "w", "h", "width", "height", "force", "setupFramebuffers", "sizeHasChanged", "scaleFactor", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/BlurUtils.class */
public final class BlurUtils extends MinecraftInstance {
    private static int lastFactor;
    private static int lastWidth;
    private static int lastHeight;
    private static int lastWeight;
    private static float lastX;
    private static float lastY;
    private static float lastW;
    private static float lastH;
    @NotNull
    public static final BlurUtils INSTANCE = new BlurUtils();
    @NotNull
    private static final ShaderGroup shaderGroup = new ShaderGroup(MinecraftInstance.f362mc.func_110434_K(), MinecraftInstance.f362mc.func_110442_L(), MinecraftInstance.f362mc.func_147110_a(), new ResourceLocation("shaders/post/blurArea.json"));
    private static final Framebuffer framebuffer = shaderGroup.field_148035_a;
    private static final Framebuffer frbuffer = shaderGroup.func_177066_a("result");
    private static float lastStrength = 5.0f;

    private BlurUtils() {
    }

    private final void setupFramebuffers() {
        try {
            shaderGroup.func_148026_a(MinecraftInstance.f362mc.field_71443_c, MinecraftInstance.f362mc.field_71440_d);
        } catch (Exception e) {
            ClientUtils.getLogger().error("Exception caught while setting up shader group", e);
        }
    }

    static /* synthetic */ void setValues$default(BlurUtils blurUtils, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, int i, Object obj) {
        if ((i & 128) != 0) {
            z = false;
        }
        blurUtils.setValues(f, f2, f3, f4, f5, f6, f7, z);
    }

    private final void setValues(float strength, float x, float y, float w, float h, float width, float height, boolean force) {
        if (!force) {
            if (strength == lastStrength) {
                if (lastX == x) {
                    if (lastY == y) {
                        if (lastW == w) {
                            if (lastH == h) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        lastStrength = strength;
        lastX = x;
        lastY = y;
        lastW = w;
        lastH = h;
        int i = 0;
        while (i < 2) {
            int i2 = i;
            i++;
            ((Shader) shaderGroup.field_148031_d.get(i2)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            ((Shader) shaderGroup.field_148031_d.get(i2)).func_148043_c().func_147991_a("BlurXY").func_148087_a(x, (height - y) - h);
            ((Shader) shaderGroup.field_148031_d.get(i2)).func_148043_c().func_147991_a("BlurCoord").func_148087_a(w, h);
        }
    }

    @JvmStatic
    public static final void blur(float posX, float posY, float posXEnd, float posYEnd, float blurStrength, boolean displayClipMask, @NotNull Functions<Unit> triggerMethod) {
        Intrinsics.checkNotNullParameter(triggerMethod, "triggerMethod");
        if (!OpenGlHelper.func_148822_b()) {
            return;
        }
        float x = posX;
        float y = posY;
        float x2 = posXEnd;
        float y2 = posYEnd;
        if (x > x2) {
            x = x2;
            x2 = x;
        }
        if (y > y2) {
            y = y2;
            y2 = y;
        }
        ScaledResolution sc = new ScaledResolution(MinecraftInstance.f362mc);
        int scaleFactor = sc.func_78325_e();
        int width = sc.func_78326_a();
        int height = sc.func_78328_b();
        if (INSTANCE.sizeHasChanged(scaleFactor, width, height)) {
            INSTANCE.setupFramebuffers();
            INSTANCE.setValues(blurStrength, x, y, x2 - x, y2 - y, width, height, true);
        }
        BlurUtils blurUtils = INSTANCE;
        lastFactor = scaleFactor;
        BlurUtils blurUtils2 = INSTANCE;
        lastWidth = width;
        BlurUtils blurUtils3 = INSTANCE;
        lastHeight = height;
        setValues$default(INSTANCE, blurStrength, x, y, x2 - x, y2 - y, width, height, false, 128, null);
        framebuffer.func_147610_a(true);
        shaderGroup.func_148018_a(MinecraftInstance.f362mc.field_71428_T.field_74281_c);
        MinecraftInstance.f362mc.func_147110_a().func_147610_a(true);
        Stencil.write(displayClipMask);
        triggerMethod.invoke();
        Stencil.erase(true);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GlStateManager.func_179094_E();
        GlStateManager.func_179135_a(true, true, true, false);
        GlStateManager.func_179097_i();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179098_w();
        GlStateManager.func_179140_f();
        GlStateManager.func_179118_c();
        frbuffer.func_147612_c();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        double f2 = frbuffer.field_147621_c / frbuffer.field_147622_a;
        double f3 = frbuffer.field_147618_d / frbuffer.field_147620_b;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        worldrenderer.func_181662_b(0.0d, height, 0.0d).func_181673_a(0.0d, 0.0d).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(width, height, 0.0d).func_181673_a(f2, 0.0d).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(width, 0.0d, 0.0d).func_181673_a(f2, f3).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(0.0d, 0.0d, 0.0d).func_181673_a(0.0d, f3).func_181669_b(255, 255, 255, 255).func_181675_d();
        tessellator.func_78381_a();
        frbuffer.func_147606_d();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179135_a(true, true, true, true);
        GlStateManager.func_179121_F();
        GlStateManager.func_179084_k();
        Stencil.dispose();
        GlStateManager.func_179141_d();
    }

    @JvmStatic
    public static final void blurArea(float x, float y, float x2, float y2, float blurStrength) {
        BlurUtils blurUtils = INSTANCE;
        blur(x, y, x2, y2, blurStrength, false, new BlurUtils$blurArea$1(x, y, x2, y2));
    }

    @JvmStatic
    public static final void blurAreaRounded(float x, float y, float x2, float y2, float rad, float blurStrength) {
        BlurUtils blurUtils = INSTANCE;
        blur(x, y, x2, y2, blurStrength, false, new BlurUtils$blurAreaRounded$1(x, y, x2, y2, rad));
    }

    public final boolean sizeHasChanged(int scaleFactor, int width, int height) {
        return (lastFactor == scaleFactor && lastWidth == width && lastHeight == height) ? false : true;
    }
}
