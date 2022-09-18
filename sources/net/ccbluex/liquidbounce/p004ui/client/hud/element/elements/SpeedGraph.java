package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: SpeedGraph.kt */
@ElementInfo(name = "SpeedGraph")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u001e\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u0015j\b\u0012\u0004\u0012\u00020\u0003`\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001c"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/SpeedGraph;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRedValue", "height", "lastSpeed", "lastTick", "", "smoothness", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "speedList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "thickness", "width", "yMultiplier", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.SpeedGraph */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/SpeedGraph.class */
public final class SpeedGraph extends Element {
    @NotNull
    private final FloatValue yMultiplier;
    @NotNull
    private final IntegerValue height;
    @NotNull
    private final IntegerValue width;
    @NotNull
    private final FloatValue thickness;
    @NotNull
    private final FloatValue smoothness;
    @NotNull
    private final IntegerValue colorRedValue;
    @NotNull
    private final IntegerValue colorGreenValue;
    @NotNull
    private final IntegerValue colorBlueValue;
    @NotNull
    private final ArrayList<Double> speedList;
    private int lastTick;
    private double lastSpeed;

    public SpeedGraph() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ SpeedGraph(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 75.0d : d, (i & 2) != 0 ? 110.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpeedGraph(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.yMultiplier = new FloatValue("yMultiplier", 7.0f, 1.0f, 20.0f);
        this.height = new IntegerValue("Height", 50, 30, 150);
        this.width = new IntegerValue("Width", 150, 100, TokenId.ABSTRACT);
        this.thickness = new FloatValue("Thickness", 2.0f, 1.0f, 3.0f);
        this.smoothness = new FloatValue("Smoothness", 0.5f, 0.0f, 1.0f);
        this.colorRedValue = new IntegerValue("R", 0, 0, 255);
        this.colorGreenValue = new IntegerValue("G", 111, 0, 255);
        this.colorBlueValue = new IntegerValue("B", 255, 0, 255);
        this.speedList = new ArrayList<>();
        this.lastTick = -1;
        this.lastSpeed = 0.01d;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        int width = this.width.get().intValue();
        int i = this.lastTick;
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        if (i != entityPlayerSP.field_70173_aa) {
            EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP2);
            this.lastTick = entityPlayerSP2.field_70173_aa;
            EntityPlayerSP entityPlayerSP3 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP3);
            double z2 = entityPlayerSP3.field_70161_v;
            EntityPlayerSP entityPlayerSP4 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP4);
            double z1 = entityPlayerSP4.field_70166_s;
            EntityPlayerSP entityPlayerSP5 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP5);
            double x2 = entityPlayerSP5.field_70165_t;
            EntityPlayerSP entityPlayerSP6 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP6);
            double x1 = entityPlayerSP6.field_70169_q;
            double speed = Math.sqrt(((z2 - z1) * (z2 - z1)) + ((x2 - x1) * (x2 - x1)));
            if (speed < 0.0d) {
                speed = -speed;
            }
            double speed2 = (((this.lastSpeed * 0.9d) + (speed * 0.1d)) * this.smoothness.get().doubleValue()) + (speed * (1 - this.smoothness.get().floatValue()));
            this.lastSpeed = speed2;
            this.speedList.add(Double.valueOf(speed2));
            while (this.speedList.size() > width) {
                this.speedList.remove(0);
            }
        }
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(this.thickness.get().floatValue());
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBegin(1);
        int size = this.speedList.size();
        int start = size > width ? size - width : 0;
        int i2 = start;
        int i3 = size - 1;
        while (i2 < i3) {
            int i4 = i2;
            i2++;
            double y = this.speedList.get(i4).doubleValue() * 10 * this.yMultiplier.get().doubleValue();
            double y1 = this.speedList.get(i4 + 1).doubleValue() * 10 * this.yMultiplier.get().doubleValue();
            RenderUtils.glColor(new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue(), 255));
            GL11.glVertex2d(i4 - start, (this.height.get().intValue() + 1) - RangesKt.coerceAtMost(y, this.height.get().intValue()));
            GL11.glVertex2d((i4 + 1.0d) - start, (this.height.get().intValue() + 1) - RangesKt.coerceAtMost(y1, this.height.get().intValue()));
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GlStateManager.func_179117_G();
        return new Border(0.0f, 0.0f, width, this.height.get().intValue() + 2);
    }
}
