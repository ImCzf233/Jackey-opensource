package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: Tracers.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0016"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Tracers;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorRedValue", "thicknessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "drawTraces", "", "entity", "Lnet/minecraft/entity/Entity;", "color", "Ljava/awt/Color;", "drawHeight", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"})
@ModuleInfo(name = "Tracers", description = "Draws a line to targets around you.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Tracers.class */
public final class Tracers extends Module {
    @NotNull
    private final ListValue colorMode = new ListValue("Color", new String[]{"Custom", "DistanceColor", "Rainbow"}, "Custom");
    @NotNull
    private final FloatValue thicknessValue = new FloatValue("Thickness", 2.0f, 1.0f, 5.0f);
    @NotNull
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    @NotNull
    private final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    @NotNull
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Color color;
        Intrinsics.checkNotNullParameter(event, "event");
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(this.thicknessValue.get().floatValue());
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBegin(1);
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if (entity != null && !Intrinsics.areEqual(entity, MinecraftInstance.f362mc.field_71439_g) && EntityUtils.isSelected(entity, false)) {
                int dist = (int) (MinecraftInstance.f362mc.field_71439_g.func_70032_d(entity) * 2);
                if (dist > 255) {
                    dist = 255;
                }
                String colorMode = this.colorMode.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(colorMode, "this as java.lang.String).toLowerCase()");
                if (EntityUtils.isFriend(entity)) {
                    color = new Color(0, 0, 255, 150);
                } else if (colorMode.equals("custom")) {
                    color = new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue(), 150);
                } else if (colorMode.equals("distancecolor")) {
                    color = new Color(255 - dist, dist, 0, 150);
                } else {
                    color = colorMode.equals("rainbow") ? ColorUtils.rainbow() : new Color(255, 255, 255, 150);
                }
                Color color2 = color;
                drawTraces(entity, color2, true);
            }
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GlStateManager.func_179117_G();
    }

    public final void drawTraces(@NotNull Entity entity, @NotNull Color color, boolean drawHeight) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        Intrinsics.checkNotNullParameter(color, "color");
        double x = (entity.field_70142_S + ((entity.field_70165_t - entity.field_70142_S) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78725_b;
        double y = (entity.field_70137_T + ((entity.field_70163_u - entity.field_70137_T) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78726_c;
        double z = (entity.field_70136_U + ((entity.field_70161_v - entity.field_70136_U) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78723_d;
        Vec3 eyeVector = new Vec3(0.0d, 0.0d, 1.0d).func_178789_a((float) (-Math.toRadians(MinecraftInstance.f362mc.field_71439_g.field_70125_A))).func_178785_b((float) (-Math.toRadians(MinecraftInstance.f362mc.field_71439_g.field_70177_z)));
        RenderUtils.glColor(color);
        GL11.glVertex3d(eyeVector.field_72450_a, MinecraftInstance.f362mc.field_71439_g.func_70047_e() + eyeVector.field_72448_b, eyeVector.field_72449_c);
        if (drawHeight) {
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y + entity.field_70131_O, z);
            return;
        }
        GL11.glVertex3d(x, y + (entity.field_70131_O / 2.0d), z);
    }
}
