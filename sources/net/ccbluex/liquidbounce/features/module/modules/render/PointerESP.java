package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

/* compiled from: PointerESP.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0013"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/PointerESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blueValue", "greenValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "noInViewValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "redValue", "sizeValue", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"})
@ModuleInfo(name = "PointerESP", spacedName = "Pointer ESP", description = "Tracers but it's arrow.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/PointerESP.class */
public final class PointerESP extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Solid", "Line"}, "Solid");
    @NotNull
    private final IntegerValue redValue = new IntegerValue("Red", 140, 0, 255);
    @NotNull
    private final IntegerValue greenValue = new IntegerValue("Green", 140, 0, 255);
    @NotNull
    private final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255);
    @NotNull
    private final IntegerValue alphaValue = new IntegerValue("Alpha", 255, 0, 255);
    @NotNull
    private final IntegerValue sizeValue = new IntegerValue("Size", 100, 50, 200);
    @NotNull
    private final FloatValue radiusValue = new FloatValue("TriangleRadius", 2.2f, 1.0f, 10.0f, "m");
    @NotNull
    private final BoolValue noInViewValue = new BoolValue("NoEntityInView", true);

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ScaledResolution sr = new ScaledResolution(MinecraftInstance.f362mc);
        Color color = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue());
        GlStateManager.func_179094_E();
        int size = 50 + this.sizeValue.get().intValue();
        double xOffset = ((sr.func_78326_a() / 2) - 24.5d) - (this.sizeValue.get().doubleValue() / 2.0d);
        double yOffset = ((sr.func_78328_b() / 2) - 25.2d) - (this.sizeValue.get().doubleValue() / 2.0d);
        double playerOffsetX = MinecraftInstance.f362mc.field_71439_g.field_70165_t;
        double playerOffSetZ = MinecraftInstance.f362mc.field_71439_g.field_70161_v;
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if (EntityUtils.isSelected(entity, true) && (!this.noInViewValue.get().booleanValue() || !RenderUtils.isInViewFrustrum(entity))) {
                double pos1 = ((entity.field_70165_t + ((entity.field_70165_t - entity.field_70142_S) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - playerOffsetX) * 0.2d;
                double pos2 = ((entity.field_70161_v + ((entity.field_70161_v - entity.field_70136_U) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - playerOffSetZ) * 0.2d;
                double cos = Math.cos(MinecraftInstance.f362mc.field_71439_g.field_70177_z * 0.017453292519943295d);
                double sin = Math.sin(MinecraftInstance.f362mc.field_71439_g.field_70177_z * 0.017453292519943295d);
                double rotY = -((pos2 * cos) - (pos1 * sin));
                double rotX = -((pos1 * cos) + (pos2 * sin));
                double var7 = 0 - rotX;
                double var9 = 0 - rotY;
                if (MathHelper.func_76133_a((var7 * var7) + (var9 * var9)) < (size / 2) - 4) {
                    float angle = (float) ((Math.atan2(rotY - 0, rotX - 0) * 180) / 3.141592653589793d);
                    double x = ((size / 2) * Math.cos(Math.toRadians(angle))) + xOffset + (size / 2);
                    double y = ((size / 2) * Math.sin(Math.toRadians(angle))) + yOffset + (size / 2);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179137_b(x, y, 0.0d);
                    GlStateManager.func_179114_b(angle, 0.0f, 0.0f, 1.0f);
                    GlStateManager.func_179139_a(1.0d, 1.0d, 1.0d);
                    RenderUtils.drawTriAngle(0.0f, 0.0f, this.radiusValue.get().floatValue(), 3.0f, color, StringsKt.equals(this.modeValue.get(), "solid", true));
                    GlStateManager.func_179121_F();
                }
            }
        }
        GlStateManager.func_179121_F();
    }
}
