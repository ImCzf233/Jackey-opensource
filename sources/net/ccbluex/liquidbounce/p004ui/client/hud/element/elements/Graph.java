package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: Graph.kt */
@ElementInfo(name = "Graph")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u000204H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u001e\u0010,\u001a\u0012\u0012\u0004\u0012\u00020\u00060-j\b\u0012\u0004\u0012\u00020\u0006`.X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��¨\u00065"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Graph;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "averageLayer", "Lnet/ccbluex/liquidbounce/value/ListValue;", "averageNumber", "avgUpdateDelay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "avgtimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "bgalphaValue", "bgblueValue", "bggreenValue", "bgredValue", "bordRad", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "bordalpha", "bordblueValue", "bordgreenValue", "bordredValue", "displayGraphName", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "graphValue", "lastValue", "", "lastX", "lastZ", "maxGraphValues", "maxHeight", "nameValue", "showAverageLine", "speedVal", "thickness", "timer", "updateDelay", "valueStore", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "xMultiplier", "yMultiplier", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "updateElement", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Graph */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Graph.class */
public final class Graph extends Element {
    @NotNull
    private final ListValue graphValue;
    @NotNull
    private final IntegerValue updateDelay;
    @NotNull
    private final FloatValue xMultiplier;
    @NotNull
    private final FloatValue yMultiplier;
    @NotNull
    private final IntegerValue maxGraphValues;
    @NotNull
    private final FloatValue maxHeight;
    @NotNull
    private final FloatValue thickness;
    @NotNull
    private final BoolValue displayGraphName;
    @NotNull
    private final BoolValue nameValue;
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final BoolValue showAverageLine;
    @NotNull
    private final ListValue averageLayer;
    @NotNull
    private final IntegerValue avgUpdateDelay;
    @NotNull
    private final IntegerValue bgredValue;
    @NotNull
    private final IntegerValue bggreenValue;
    @NotNull
    private final IntegerValue bgblueValue;
    @NotNull
    private final IntegerValue bgalphaValue;
    @NotNull
    private final IntegerValue bordredValue;
    @NotNull
    private final IntegerValue bordgreenValue;
    @NotNull
    private final IntegerValue bordblueValue;
    @NotNull
    private final IntegerValue bordalpha;
    @NotNull
    private final FloatValue bordRad;
    @NotNull
    private final ArrayList<Float> valueStore;
    @NotNull
    private final MSTimer timer;
    @NotNull
    private final MSTimer avgtimer;
    private float averageNumber;
    private double lastX;
    private double lastZ;
    private float speedVal;
    @NotNull
    private String lastValue;

    public Graph() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Graph(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 75.0d : d, (i & 2) != 0 ? 110.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Graph(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.graphValue = new ListValue("Graph-Value", new String[]{"Speed", "BPS", "Packet-In", "Packet-Out"}, "Speed");
        this.updateDelay = new IntegerValue("Update-Delay", 1000, 0, Level.TRACE_INT);
        this.xMultiplier = new FloatValue("xMultiplier", 4.0f, 1.0f, 20.0f);
        this.yMultiplier = new FloatValue("yMultiplier", 7.0f, 0.1f, 20.0f);
        this.maxGraphValues = new IntegerValue("MaxGraphValues", 25, 1, TokenId.ABSTRACT);
        this.maxHeight = new FloatValue("MaxHeight", 50.0f, 30.0f, 150.0f);
        this.thickness = new FloatValue("Thickness", 2.0f, 1.0f, 3.0f);
        this.displayGraphName = new BoolValue("Display-Name", true);
        this.nameValue = new BoolValue("Name-Value", true);
        FontRenderer minecraftFont = Fonts.minecraftFont;
        Intrinsics.checkNotNullExpressionValue(minecraftFont, "minecraftFont");
        this.fontValue = new FontValue("Font", minecraftFont);
        this.showAverageLine = new BoolValue("Show-Average", true);
        this.averageLayer = new ListValue("Average-Layer", new String[]{"Top", "Bottom"}, "Bottom");
        this.avgUpdateDelay = new IntegerValue("Average-Update-Delay", 1000, 0, Level.TRACE_INT);
        this.bgredValue = new IntegerValue("Background-Red", 0, 0, 255);
        this.bggreenValue = new IntegerValue("Background-Green", 0, 0, 255);
        this.bgblueValue = new IntegerValue("Background-Blue", 0, 0, 255);
        this.bgalphaValue = new IntegerValue("Background-Alpha", 120, 0, 255);
        this.bordredValue = new IntegerValue("Border-Red", 255, 0, 255);
        this.bordgreenValue = new IntegerValue("Border-Green", 255, 0, 255);
        this.bordblueValue = new IntegerValue("Border-Blue", 255, 0, 255);
        this.bordalpha = new IntegerValue("Border-Alpha", 255, 0, 255);
        this.bordRad = new FloatValue("Border-Width", 3.0f, 0.0f, 10.0f);
        this.valueStore = new ArrayList<>();
        this.timer = new MSTimer();
        this.avgtimer = new MSTimer();
        this.lastValue = "";
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void updateElement() {
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        this.speedVal = ((float) Math.sqrt(Math.pow(this.lastX - MinecraftInstance.f362mc.field_71439_g.field_70165_t, 2.0d) + Math.pow(this.lastZ - MinecraftInstance.f362mc.field_71439_g.field_70161_v, 2.0d))) * 20.0f * MinecraftInstance.f362mc.field_71428_T.field_74278_d;
        this.lastX = MinecraftInstance.f362mc.field_71439_g.field_70165_t;
        this.lastZ = MinecraftInstance.f362mc.field_71439_g.field_70161_v;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        String str;
        String str2;
        String str3;
        FontRenderer font = this.fontValue.get();
        float width = this.maxGraphValues.get().floatValue() * this.xMultiplier.get().floatValue();
        int markColor = new Color(0.1f, 1.0f, 0.1f).getRGB();
        int bgColor = new Color(this.bgredValue.get().intValue(), this.bggreenValue.get().intValue(), this.bgblueValue.get().intValue(), this.bgalphaValue.get().intValue()).getRGB();
        int borderColor = new Color(this.bordredValue.get().intValue(), this.bordgreenValue.get().intValue(), this.bordblueValue.get().intValue(), this.bordalpha.get().intValue()).getRGB();
        float defaultX = 0.0f;
        if (MinecraftInstance.f362mc.field_71439_g == null || !Intrinsics.areEqual(this.lastValue, this.graphValue.get())) {
            this.valueStore.clear();
            this.averageNumber = 0.0f;
        }
        this.lastValue = this.graphValue.get();
        if (this.timer.hasTimePassed(this.updateDelay.get().intValue())) {
            String lowerCase = this.graphValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case 97765:
                    if (lowerCase.equals("bps")) {
                        this.valueStore.add(Float.valueOf(this.speedVal));
                        break;
                    }
                    break;
                case 109641799:
                    if (lowerCase.equals("speed")) {
                        this.valueStore.add(Float.valueOf(MovementUtils.getSpeed() * 10.0f));
                        break;
                    }
                    break;
                case 154201225:
                    if (lowerCase.equals("packet-out")) {
                        this.valueStore.add(Float.valueOf(PacketUtils.avgOutBound));
                        break;
                    }
                    break;
                case 1806089354:
                    if (lowerCase.equals("packet-in")) {
                        this.valueStore.add(Float.valueOf(PacketUtils.avgInBound));
                        break;
                    }
                    break;
            }
            while (this.valueStore.size() > this.maxGraphValues.get().intValue()) {
                this.valueStore.remove(0);
            }
            this.timer.reset();
        }
        if (this.valueStore.isEmpty()) {
            return new Border(0.0f, 0.0f, width, this.maxHeight.get().floatValue() + 2.0f);
        }
        if (this.avgtimer.hasTimePassed(this.avgUpdateDelay.get().intValue())) {
            float f = this.averageNumber;
            Float f2 = this.valueStore.get(this.valueStore.size() - 1);
            Intrinsics.checkNotNullExpressionValue(f2, "valueStore[valueStore.size - 1]");
            this.averageNumber = (f + f2.floatValue()) / 2.0f;
            this.avgtimer.reset();
        }
        if (StringsKt.startsWith(this.graphValue.get(), "packet", true)) {
            str = String.valueOf((int) this.valueStore.get(this.valueStore.size() - 1).floatValue());
        } else {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArr = {this.valueStore.get(this.valueStore.size() - 1)};
            String format = String.format("%.2f", Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
            str = format;
        }
        String working = str;
        if (StringsKt.startsWith(this.graphValue.get(), "packet", true)) {
            str2 = String.valueOf((int) this.averageNumber);
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            Object[] objArr2 = {Float.valueOf(this.averageNumber)};
            String format2 = String.format("%.2f", Arrays.copyOf(objArr2, objArr2.length));
            Intrinsics.checkNotNullExpressionValue(format2, "format(format, *args)");
            str2 = format2;
        }
        String average = str2;
        if (this.displayGraphName.get().booleanValue()) {
            if (this.nameValue.get().booleanValue()) {
                String lowerCase2 = this.graphValue.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                switch (lowerCase2.hashCode()) {
                    case 97765:
                        if (lowerCase2.equals("bps")) {
                            str3 = "Player speed (" + working + " blocks/s)";
                            break;
                        }
                        str3 = "Outbound packets (" + working + " packets/s)";
                        break;
                    case 109641799:
                        if (lowerCase2.equals("speed")) {
                            str3 = "Player speed (" + working + " blocks/tick)";
                            break;
                        }
                        str3 = "Outbound packets (" + working + " packets/s)";
                        break;
                    case 1806089354:
                        if (lowerCase2.equals("packet-in")) {
                            str3 = "Inbound packets (" + working + " packets/s)";
                            break;
                        }
                        str3 = "Outbound packets (" + working + " packets/s)";
                        break;
                    default:
                        str3 = "Outbound packets (" + working + " packets/s)";
                        break;
                }
            } else {
                String lowerCase3 = this.graphValue.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
                switch (lowerCase3.hashCode()) {
                    case 97765:
                        if (lowerCase3.equals("bps")) {
                            str3 = "Player blocks/s";
                            break;
                        }
                        str3 = "Outbound packets";
                        break;
                    case 109641799:
                        if (lowerCase3.equals("speed")) {
                            str3 = "Player speed";
                            break;
                        }
                        str3 = "Outbound packets";
                        break;
                    case 1806089354:
                        if (lowerCase3.equals("packet-in")) {
                            str3 = "Inbound packets";
                            break;
                        }
                        str3 = "Outbound packets";
                        break;
                    default:
                        str3 = "Outbound packets";
                        break;
                }
            }
            String displayString = str3;
            GlStateManager.func_179094_E();
            GlStateManager.func_179137_b(0.5d, (-6.0d) - (font.field_78288_b / 2.0d), 0.0d);
            GlStateManager.func_179152_a(0.75f, 0.75f, 0.75f);
            font.func_175063_a(displayString, 0.0f, 0.0f, -1);
            GlStateManager.func_179121_F();
        }
        if (this.bgalphaValue.get().intValue() > 0.0f) {
            RenderUtils.drawRect(-1.0f, -1.0f, (width - this.xMultiplier.get().floatValue()) + 1.0f, this.maxHeight.get().floatValue() + 1.0f, bgColor);
        }
        if (this.bordalpha.get().intValue() > 0.0f) {
            RenderUtils.drawBorder(-1.0f, -1.0f, (width - this.xMultiplier.get().floatValue()) + 1.0f, this.maxHeight.get().floatValue() + 1.0f, this.bordRad.get().floatValue(), borderColor);
        }
        float avgheight = Math.min(this.averageNumber * this.yMultiplier.get().floatValue(), this.maxHeight.get().floatValue());
        float firstheight = Math.min(this.valueStore.get(this.valueStore.size() - 1).floatValue() * this.yMultiplier.get().floatValue(), this.maxHeight.get().floatValue());
        if (this.showAverageLine.get().booleanValue() && !this.nameValue.get().booleanValue()) {
            font.func_175063_a(average, (-font.func_78256_a(average)) - 3.0f, (this.maxHeight.get().floatValue() - avgheight) - (font.field_78288_b / 2.0f), markColor);
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GL11.glEnable(2848);
        GL11.glLineWidth(this.thickness.get().floatValue());
        GlStateManager.func_179120_a(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        if (this.showAverageLine.get().booleanValue() && StringsKt.equals(this.averageLayer.get(), "bottom", true)) {
            GlStateManager.func_179131_c(0.1f, 1.0f, 0.1f, 1.0f);
            worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
            worldRenderer.func_181662_b(0.0d, this.maxHeight.get().floatValue() - avgheight, 0.0d).func_181675_d();
            worldRenderer.func_181662_b(width - this.xMultiplier.get().floatValue(), this.maxHeight.get().floatValue() - avgheight, 0.0d).func_181675_d();
            tessellator.func_78381_a();
        }
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        Iterator<Float> it = this.valueStore.iterator();
        while (it.hasNext()) {
            Float valu = it.next();
            float height = Math.min(valu.floatValue() * this.yMultiplier.get().floatValue(), this.maxHeight.get().floatValue());
            worldRenderer.func_181662_b(defaultX, this.maxHeight.get().floatValue() - height, 0.0d).func_181675_d();
            defaultX += this.xMultiplier.get().floatValue();
        }
        tessellator.func_78381_a();
        if (this.showAverageLine.get().booleanValue() && StringsKt.equals(this.averageLayer.get(), "top", true)) {
            GlStateManager.func_179131_c(0.1f, 1.0f, 0.1f, 1.0f);
            worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
            worldRenderer.func_181662_b(0.0d, this.maxHeight.get().floatValue() - avgheight, 0.0d).func_181675_d();
            worldRenderer.func_181662_b(width - this.xMultiplier.get().floatValue(), this.maxHeight.get().floatValue() - avgheight, 0.0d).func_181675_d();
            tessellator.func_78381_a();
        }
        GL11.glDisable(2848);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
        if (this.nameValue.get().booleanValue()) {
            font.func_175063_a(average, (width - this.xMultiplier.get().floatValue()) + 3.0f, (this.maxHeight.get().floatValue() - avgheight) - (font.field_78288_b / 2.0f), markColor);
        } else {
            font.func_175063_a(working, (width - this.xMultiplier.get().floatValue()) + 3.0f, (this.maxHeight.get().floatValue() - firstheight) - (font.field_78288_b / 2.0f), -1);
        }
        return new Border(0.0f, 0.0f, width, this.maxHeight.get().floatValue() + 2.0f);
    }
}
