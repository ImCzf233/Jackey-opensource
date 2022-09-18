package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/* compiled from: Radar.kt */
@ElementInfo(name = "Radar", disableScale = true, priority = 1)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018�� )2\u00020\u0001:\u0001)B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\n\u0010'\u001a\u0004\u0018\u00010(H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��¨\u0006*"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundAlphaValue", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "blueValue", "blurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRedValue", "borderStrengthValue", "borderValue", "brightnessValue", "cRainbowSecValue", "distanceValue", "exhiValue", "fovSizeValue", "gradientAmountValue", "greenValue", "lineValue", "playerShapeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "playerSizeValue", "rainbowList", "redValue", "saturationValue", "sizeValue", "viewDistanceValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Radar */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar.class */
public final class Radar extends Element {
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final FloatValue sizeValue;
    @NotNull
    private final FloatValue viewDistanceValue;
    @NotNull
    private final ListValue playerShapeValue;
    @NotNull
    private final FloatValue playerSizeValue;
    @NotNull
    private final FloatValue fovSizeValue;
    @NotNull
    private final BoolValue exhiValue;
    @NotNull
    private final BoolValue lineValue;
    @NotNull
    private final ListValue rainbowList;
    @NotNull
    private final IntegerValue redValue;
    @NotNull
    private final IntegerValue greenValue;
    @NotNull
    private final IntegerValue blueValue;
    @NotNull
    private final IntegerValue alphaValue;
    @NotNull
    private final FloatValue saturationValue;
    @NotNull
    private final FloatValue brightnessValue;
    @NotNull
    private final IntegerValue cRainbowSecValue;
    @NotNull
    private final IntegerValue distanceValue;
    @NotNull
    private final IntegerValue gradientAmountValue;
    @NotNull
    private final IntegerValue backgroundRedValue;
    @NotNull
    private final IntegerValue backgroundGreenValue;
    @NotNull
    private final IntegerValue backgroundBlueValue;
    @NotNull
    private final IntegerValue backgroundAlphaValue;
    @NotNull
    private final BoolValue borderValue;
    @NotNull
    private final FloatValue borderStrengthValue;
    @NotNull
    private final IntegerValue borderRedValue;
    @NotNull
    private final IntegerValue borderGreenValue;
    @NotNull
    private final IntegerValue borderBlueValue;
    @NotNull
    private final IntegerValue borderAlphaValue;
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final float SQRT_OF_TWO = (float) Math.sqrt(2.0f);

    public Radar() {
        this(0.0d, 0.0d, 3, null);
    }

    public Radar(double x, double y) {
        super(x, y, 0.0f, null, 12, null);
        this.blurValue = new BoolValue("Blur", false);
        this.blurStrength = new FloatValue("Blur-Strength", 0.0f, 0.0f, 30.0f);
        this.sizeValue = new FloatValue("Size", 90.0f, 30.0f, 500.0f);
        this.viewDistanceValue = new FloatValue("View Distance", 4.0f, 0.5f, 32.0f);
        this.playerShapeValue = new ListValue("Player Shape", new String[]{"Rectangle", "Circle"}, "Triangle");
        this.playerSizeValue = new FloatValue("Player Size", 2.0f, 0.5f, 20.0f);
        this.fovSizeValue = new FloatValue("FOV Size", 10.0f, 0.0f, 50.0f);
        this.exhiValue = new BoolValue("Use Exhi Rect", true);
        this.lineValue = new BoolValue("Line", false);
        this.rainbowList = new ListValue("Line-Rainbow", new String[]{"Off", "CRainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Off");
        this.redValue = new IntegerValue("Line-Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Line-Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Line-Blue", 255, 0, 255);
        this.alphaValue = new IntegerValue("Line-Alpha", 255, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 0.9f, 0.0f, 1.0f);
        this.brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
        this.cRainbowSecValue = new IntegerValue("Seconds", 2, 1, 10);
        this.distanceValue = new IntegerValue("Line-Distance", 0, 0, TokenId.Identifier);
        this.gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);
        this.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
        this.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
        this.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
        this.backgroundAlphaValue = new IntegerValue("Background Alpha", 50, 0, 255);
        this.borderValue = new BoolValue("Border", false);
        this.borderStrengthValue = new FloatValue("Border Strength", 2.0f, 1.0f, 5.0f);
        this.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
        this.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
        this.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
        this.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
    }

    public /* synthetic */ Radar(double d, double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 5.0d : d, (i & 2) != 0 ? 130.0d : d2);
    }

    /* compiled from: Radar.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0005"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar$Companion;", "", "()V", "SQRT_OF_TWO", "", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Radar$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        int i;
        int i2;
        int i3;
        Entity renderViewEntity = MinecraftInstance.f362mc.func_175606_aa();
        float size = this.sizeValue.get().floatValue();
        float viewDistance = this.viewDistanceValue.get().floatValue() * 16.0f;
        double maxDisplayableDistanceSquare = (viewDistance + this.fovSizeValue.get().floatValue()) * (viewDistance + this.fovSizeValue.get().floatValue());
        float halfSize = size / 2.0f;
        String rainbowType = this.rainbowList.get();
        int cColor = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
        if (this.blurValue.get().booleanValue()) {
            float floatX = (float) getRenderX();
            float floatY = (float) getRenderY();
            GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
            GL11.glPushMatrix();
            BlurUtils.blurArea(floatX, floatY, floatX + size, floatY + size, this.blurStrength.get().floatValue());
            GL11.glPopMatrix();
            GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
        }
        if (this.exhiValue.get().booleanValue()) {
            RenderUtils.drawExhiRect(0.0f, this.lineValue.get().booleanValue() ? -1.0f : 0.0f, size, size);
        } else {
            RenderUtils.drawRect(0.0f, 0.0f, size, size, new Color(this.backgroundRedValue.get().intValue(), this.backgroundGreenValue.get().intValue(), this.backgroundBlueValue.get().intValue(), this.backgroundAlphaValue.get().intValue()).getRGB());
        }
        if (this.lineValue.get().booleanValue()) {
            double barLength = size;
            int i4 = 0;
            int intValue = this.gradientAmountValue.get().intValue() - 1;
            if (0 <= intValue) {
                do {
                    i = i4;
                    i4++;
                    double barStart = (i / this.gradientAmountValue.get().intValue()) * barLength;
                    double barEnd = ((i + 1) / this.gradientAmountValue.get().intValue()) * barLength;
                    switch (rainbowType.hashCode()) {
                        case -884013110:
                            if (rainbowType.equals("LiquidSlowly")) {
                                Color LiquidSlowly = ColorUtils.LiquidSlowly(System.nanoTime(), i * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                Intrinsics.checkNotNull(LiquidSlowly);
                                i2 = LiquidSlowly.getRGB();
                                break;
                            }
                            i2 = cColor;
                            break;
                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i2 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), i * this.distanceValue.get().intValue());
                                break;
                            }
                            i2 = cColor;
                            break;
                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i2 = RenderUtils.SkyRainbow(i * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                break;
                            }
                            i2 = cColor;
                            break;
                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i2 = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), i * this.distanceValue.get().intValue(), 100).getRGB();
                                break;
                            }
                            i2 = cColor;
                            break;
                        case 74357737:
                            if (rainbowType.equals("Mixer")) {
                                i2 = ColorMixer.getMixedColor(i * this.distanceValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB();
                                break;
                            }
                            i2 = cColor;
                            break;
                        default:
                            i2 = cColor;
                            break;
                    }
                    switch (rainbowType.hashCode()) {
                        case -884013110:
                            if (rainbowType.equals("LiquidSlowly")) {
                                Color LiquidSlowly2 = ColorUtils.LiquidSlowly(System.nanoTime(), (i + 1) * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                Intrinsics.checkNotNull(LiquidSlowly2);
                                i3 = LiquidSlowly2.getRGB();
                                break;
                            }
                            i3 = cColor;
                            break;
                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i3 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), (i + 1) * this.distanceValue.get().intValue());
                                break;
                            }
                            i3 = cColor;
                            break;
                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i3 = RenderUtils.SkyRainbow((i + 1) * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                break;
                            }
                            i3 = cColor;
                            break;
                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i3 = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), (i + 1) * this.distanceValue.get().intValue(), 100).getRGB();
                                break;
                            }
                            i3 = cColor;
                            break;
                        case 74357737:
                            if (rainbowType.equals("Mixer")) {
                                i3 = ColorMixer.getMixedColor((i + 1) * this.distanceValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB();
                                break;
                            }
                            i3 = cColor;
                            break;
                        default:
                            i3 = cColor;
                            break;
                    }
                    RenderUtils.drawGradientSideways(barStart, -1.0d, barEnd, 0.0d, i2, i3);
                } while (i != intValue);
            }
        }
        if (this.borderValue.get().booleanValue()) {
            float strength = this.borderStrengthValue.get().floatValue() / 2.0f;
            int borderColor = new Color(this.borderRedValue.get().intValue(), this.borderGreenValue.get().intValue(), this.borderBlueValue.get().intValue(), this.borderAlphaValue.get().intValue()).getRGB();
            RenderUtils.drawRect(halfSize - strength, 0.0f, halfSize + strength, size, borderColor);
            RenderUtils.drawRect(0.0f, halfSize - strength, halfSize - strength, halfSize + strength, borderColor);
            RenderUtils.drawRect(halfSize + strength, halfSize - strength, size, halfSize + strength, borderColor);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RenderUtils.makeScissorBox((float) getX(), (float) getY(), ((float) getX()) + ((float) Math.ceil(size)), ((float) getY()) + ((float) Math.ceil(size)));
        GL11.glEnable(3089);
        GL11.glPushMatrix();
        GL11.glTranslatef(halfSize, halfSize, 0.0f);
        GL11.glRotatef(renderViewEntity.field_70177_z, 0.0f, 0.0f, -1.0f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        boolean circleMode = StringsKt.equals(this.playerShapeValue.get(), "circle", true);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        if (circleMode) {
            GL11.glEnable(2832);
        }
        float playerSize = this.playerSizeValue.get().floatValue();
        GL11.glEnable(2881);
        worldRenderer.func_181668_a(0, DefaultVertexFormats.field_181706_f);
        GL11.glPointSize(playerSize);
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if (entity != null && entity != MinecraftInstance.f362mc.field_71439_g && EntityUtils.isSelected(entity, false)) {
                Vector2f positionRelativeToPlayer = new Vector2f((float) (renderViewEntity.field_70165_t - entity.field_70165_t), (float) (renderViewEntity.field_70161_v - entity.field_70161_v));
                if (maxDisplayableDistanceSquare >= positionRelativeToPlayer.lengthSquared()) {
                    boolean transform = this.fovSizeValue.get().floatValue() > 0.0f;
                    if (transform) {
                        GL11.glPushMatrix();
                        GL11.glTranslatef((positionRelativeToPlayer.x / viewDistance) * size, (positionRelativeToPlayer.y / viewDistance) * size, 0.0f);
                        GL11.glRotatef(entity.field_70177_z, 0.0f, 0.0f, 1.0f);
                    }
                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(ESP.class);
                    if (module == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.ESP");
                    }
                    Color color = ((ESP) module).getColor(entity);
                    worldRenderer.func_181662_b((positionRelativeToPlayer.x / viewDistance) * size, (positionRelativeToPlayer.y / viewDistance) * size, 0.0d).func_181666_a(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f).func_181675_d();
                    if (transform) {
                        GL11.glPopMatrix();
                    }
                } else {
                    continue;
                }
            }
        }
        tessellator.func_78381_a();
        if (circleMode) {
            GL11.glDisable(2832);
        }
        GL11.glDisable(2881);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        return new Border(0.0f, 0.0f, size, size);
    }
}
