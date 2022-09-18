package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: Model.kt */
@ElementInfo(name = "Model")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0018"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Model;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "customPitch", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "customYaw", "pitchMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rotate", "", "rotateDirection", "", "yawMode", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawEntityOnScreen", "", "yaw", "pitch", "entityLivingBase", "Lnet/minecraft/entity/EntityLivingBase;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Model */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Model.class */
public final class Model extends Element {
    @NotNull
    private final ListValue yawMode;
    @NotNull
    private final FloatValue customYaw;
    @NotNull
    private final ListValue pitchMode;
    @NotNull
    private final FloatValue customPitch;
    private float rotate;
    private boolean rotateDirection;

    public Model() {
        this(0.0d, 0.0d, 3, null);
    }

    public Model(double x, double y) {
        super(x, y, 0.0f, null, 12, null);
        this.yawMode = new ListValue("Yaw", new String[]{"Player", "Animation", "Custom"}, "Animation");
        this.customYaw = new FloatValue("CustomYaw", 0.0f, -180.0f, 180.0f);
        this.pitchMode = new ListValue("Pitch", new String[]{"Player", "Custom"}, "Player");
        this.customPitch = new FloatValue("CustomPitch", 0.0f, -90.0f, 90.0f);
    }

    public /* synthetic */ Model(double d, double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 40.0d : d, (i & 2) != 0 ? 100.0d : d2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        float f;
        float f2;
        String lowerCase = this.yawMode.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    f = this.customYaw.get().floatValue();
                    break;
                }
                f = 0.0f;
                break;
            case -985752863:
                if (lowerCase.equals("player")) {
                    f = MinecraftInstance.f362mc.field_71439_g.field_70177_z;
                    break;
                }
                f = 0.0f;
                break;
            case 1118509956:
                if (lowerCase.equals("animation")) {
                    int delta = RenderUtils.deltaTime;
                    if (this.rotateDirection) {
                        if (this.rotate <= 70.0f) {
                            this.rotate += 0.12f * delta;
                        } else {
                            this.rotateDirection = false;
                            this.rotate = 70.0f;
                        }
                    } else if (this.rotate >= -70.0f) {
                        this.rotate -= 0.12f * delta;
                    } else {
                        this.rotateDirection = true;
                        this.rotate = -70.0f;
                    }
                    f = this.rotate;
                    break;
                }
                f = 0.0f;
                break;
            default:
                f = 0.0f;
                break;
        }
        float yaw = f;
        String lowerCase2 = this.pitchMode.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase2, "player")) {
            f2 = MinecraftInstance.f362mc.field_71439_g.field_70125_A;
        } else {
            f2 = Intrinsics.areEqual(lowerCase2, "custom") ? this.customPitch.get().floatValue() : 0.0f;
        }
        float pitch = f2;
        float pitch2 = pitch > 0.0f ? -pitch : Math.abs(pitch);
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        drawEntityOnScreen(yaw, pitch2, (EntityLivingBase) entityPlayerSP);
        return new Border(30.0f, 10.0f, -30.0f, -100.0f);
    }

    private final void drawEntityOnScreen(float yaw, float pitch, EntityLivingBase entityLivingBase) {
        GlStateManager.func_179117_G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179142_g();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(0.0f, 0.0f, 50.0f);
        GlStateManager.func_179152_a(-50.0f, 50.0f, 50.0f);
        GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
        float renderYawOffset = entityLivingBase.field_70761_aq;
        float rotationYaw = entityLivingBase.field_70177_z;
        float rotationPitch = entityLivingBase.field_70125_A;
        float prevRotationYawHead = entityLivingBase.field_70758_at;
        float rotationYawHead = entityLivingBase.field_70759_as;
        GlStateManager.func_179114_b(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((-((float) Math.atan(pitch / 40.0f))) * 20.0f, 1.0f, 0.0f, 0.0f);
        entityLivingBase.field_70761_aq = ((float) Math.atan(yaw / 40.0f)) * 20.0f;
        entityLivingBase.field_70177_z = ((float) Math.atan(yaw / 40.0f)) * 40.0f;
        entityLivingBase.field_70125_A = (-((float) Math.atan(pitch / 40.0f))) * 20.0f;
        entityLivingBase.field_70759_as = entityLivingBase.field_70177_z;
        entityLivingBase.field_70758_at = entityLivingBase.field_70177_z;
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        RenderManager renderManager = MinecraftInstance.f362mc.func_175598_ae();
        renderManager.func_178631_a(180.0f);
        renderManager.func_178633_a(false);
        renderManager.func_147940_a((Entity) entityLivingBase, 0.0d, 0.0d, 0.0d, 0.0f, 1.0f);
        renderManager.func_178633_a(true);
        entityLivingBase.field_70761_aq = renderYawOffset;
        entityLivingBase.field_70177_z = rotationYaw;
        entityLivingBase.field_70125_A = rotationPitch;
        entityLivingBase.field_70758_at = prevRotationYawHead;
        entityLivingBase.field_70759_as = rotationYawHead;
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GlStateManager.func_179117_G();
    }
}
