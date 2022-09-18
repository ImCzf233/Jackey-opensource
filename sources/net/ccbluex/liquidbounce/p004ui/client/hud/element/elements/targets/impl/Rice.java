package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.Particle;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtension;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: Rice.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0006\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0014\u00106\u001a\u0004\u0018\u0001072\b\u00104\u001a\u0004\u0018\u000105H\u0016J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0002J\u0010\u0010;\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u0010<\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u0010=\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u0010>\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\bR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b\u0018\u0010\u0016R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a¢\u0006\b\n��\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b\u001f\u0010\u0016R\u0011\u0010 \u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b!\u0010\u0012R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b'\u0010\u0012R\u0011\u0010(\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b)\u0010\u0016R\u0011\u0010*\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b+\u0010%R\u0011\u0010,\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b-\u0010\u0016R\u0011\u0010.\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b/\u0010\u0012R\u0011\u00100\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b1\u0010%¨\u0006?"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Rice;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "inst", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;)V", "generateAmountValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getGenerateAmountValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "gotDamaged", "", "gradientDistanceValue", "getGradientDistanceValue", "gradientLoopValue", "getGradientLoopValue", "gradientRoundedBarValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getGradientRoundedBarValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "maxParticleSize", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getMaxParticleSize", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "minParticleSize", "getMinParticleSize", "particleList", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/Particle;", "getParticleList", "()Ljava/util/List;", "particleRange", "getParticleRange", "riceParticle", "getRiceParticle", "riceParticleCircle", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getRiceParticleCircle", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "riceParticleFade", "getRiceParticleFade", "riceParticleFadingSpeed", "getRiceParticleFadingSpeed", "riceParticleRect", "getRiceParticleRect", "riceParticleSpeed", "getRiceParticleSpeed", "riceParticleSpin", "getRiceParticleSpin", "riceParticleTriangle", "getRiceParticleTriangle", "drawTarget", "", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getColorAtIndex", "", "i", "handleBlur", "handleDamage", "handleShadow", "handleShadowCut", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Rice */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Rice.class */
public final class Rice extends TargetStyle {
    private boolean gotDamaged;
    @NotNull
    private final IntegerValue gradientLoopValue = new IntegerValue("GradientLoop", 4, 1, 40, new Rice$gradientLoopValue$1(this));
    @NotNull
    private final IntegerValue gradientDistanceValue = new IntegerValue("GradientDistance", 50, 1, 200, new Rice$gradientDistanceValue$1(this));
    @NotNull
    private final BoolValue gradientRoundedBarValue = new BoolValue("GradientRoundedBar", true, new Rice$gradientRoundedBarValue$1(this));
    @NotNull
    private final BoolValue riceParticle = new BoolValue("Rice-Particle", true, new Rice$riceParticle$1(this));
    @NotNull
    private final BoolValue riceParticleSpin = new BoolValue("Rice-ParticleSpin", true, new Rice$riceParticleSpin$1(this));
    @NotNull
    private final IntegerValue generateAmountValue = new IntegerValue("GenerateAmount", 10, 1, 40, new Rice$generateAmountValue$1(this));
    @NotNull
    private final ListValue riceParticleCircle = new ListValue("Circle-Particles", new String[]{"Outline", "Solid", "None"}, "Solid", new Rice$riceParticleCircle$1(this));
    @NotNull
    private final ListValue riceParticleRect = new ListValue("Rect-Particles", new String[]{"Outline", "Solid", "None"}, "Outline", new Rice$riceParticleRect$1(this));
    @NotNull
    private final ListValue riceParticleTriangle = new ListValue("Triangle-Particles", new String[]{"Outline", "Solid", "None"}, "Outline", new Rice$riceParticleTriangle$1(this));
    @NotNull
    private final FloatValue riceParticleSpeed = new FloatValue("Rice-ParticleSpeed", 0.05f, 0.01f, 0.2f, new Rice$riceParticleSpeed$1(this));
    @NotNull
    private final BoolValue riceParticleFade = new BoolValue("Rice-ParticleFade", true, new Rice$riceParticleFade$1(this));
    @NotNull
    private final FloatValue riceParticleFadingSpeed = new FloatValue("ParticleFadingSpeed", 0.05f, 0.01f, 0.2f, new Rice$riceParticleFadingSpeed$1(this));
    @NotNull
    private final FloatValue particleRange = new FloatValue("Rice-ParticleRange", 50.0f, 0.0f, 50.0f, new Rice$particleRange$1(this));
    @NotNull
    private final FloatValue minParticleSize = new FloatValue(new Rice$minParticleSize$2(this)) { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Rice$minParticleSize$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            float v = Rice.this.getMaxParticleSize().get().floatValue();
            if (v < newValue) {
                set((Rice$minParticleSize$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final FloatValue maxParticleSize = new FloatValue(new Rice$maxParticleSize$2(this)) { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Rice$maxParticleSize$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            float v = Rice.this.getMinParticleSize().get().floatValue();
            if (v > newValue) {
                set((Rice$maxParticleSize$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final List<Particle> particleList = new ArrayList();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Rice(@NotNull Target inst) {
        super("Rice", inst, true);
        Intrinsics.checkNotNullParameter(inst, "inst");
    }

    @NotNull
    public final IntegerValue getGradientLoopValue() {
        return this.gradientLoopValue;
    }

    @NotNull
    public final IntegerValue getGradientDistanceValue() {
        return this.gradientDistanceValue;
    }

    @NotNull
    public final BoolValue getGradientRoundedBarValue() {
        return this.gradientRoundedBarValue;
    }

    @NotNull
    public final BoolValue getRiceParticle() {
        return this.riceParticle;
    }

    @NotNull
    public final BoolValue getRiceParticleSpin() {
        return this.riceParticleSpin;
    }

    @NotNull
    public final IntegerValue getGenerateAmountValue() {
        return this.generateAmountValue;
    }

    @NotNull
    public final ListValue getRiceParticleCircle() {
        return this.riceParticleCircle;
    }

    @NotNull
    public final ListValue getRiceParticleRect() {
        return this.riceParticleRect;
    }

    @NotNull
    public final ListValue getRiceParticleTriangle() {
        return this.riceParticleTriangle;
    }

    @NotNull
    public final FloatValue getRiceParticleSpeed() {
        return this.riceParticleSpeed;
    }

    @NotNull
    public final BoolValue getRiceParticleFade() {
        return this.riceParticleFade;
    }

    @NotNull
    public final FloatValue getRiceParticleFadingSpeed() {
        return this.riceParticleFadingSpeed;
    }

    @NotNull
    public final FloatValue getParticleRange() {
        return this.particleRange;
    }

    @NotNull
    public final FloatValue getMinParticleSize() {
        return this.minParticleSize;
    }

    @NotNull
    public final FloatValue getMaxParticleSize() {
        return this.maxParticleSize;
    }

    @NotNull
    public final List<Particle> getParticleList() {
        return this.particleList;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void drawTarget(@NotNull EntityPlayer entity) {
        int i;
        int j;
        String str;
        Intrinsics.checkNotNullParameter(entity, "entity");
        updateAnim(entity.func_110143_aJ());
        GameFontRenderer font = Fonts.fontSFUI40;
        String name = Intrinsics.stringPlus("Name: ", entity.func_70005_c_());
        DecimalFormat decimalFormat2 = getDecimalFormat2();
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        String info = Intrinsics.stringPlus("Distance: ", decimalFormat2.format(PlayerExtension.getDistanceToEntityBox(entity2, (Entity) entity)));
        String healthName = getDecimalFormat2().format(Float.valueOf(getEasingHealth()));
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(font.func_78256_a(name), font.func_78256_a(info)) + 40.0f, 125.0f);
        String format = getDecimalFormat2().format(Float.valueOf(entity.func_110138_aP()));
        Intrinsics.checkNotNullExpressionValue(format, "decimalFormat2.format(entity.maxHealth)");
        float maxHealthLength = font.func_78256_a(format);
        RenderUtils.drawRoundedRect(0.0f, 0.0f, 10.0f + length, 55.0f, 8.0f, getTargetInstance().getBgColor().getRGB());
        if (this.riceParticle.get().booleanValue()) {
            if (this.gotDamaged) {
                int i2 = 0;
                int intValue = this.generateAmountValue.get().intValue();
                if (0 <= intValue) {
                    do {
                        j = i2;
                        i2++;
                        float parSize = RandomUtils.nextFloat(this.minParticleSize.get().floatValue(), this.maxParticleSize.get().floatValue());
                        float parDistX = RandomUtils.nextFloat(-this.particleRange.get().floatValue(), this.particleRange.get().floatValue());
                        float parDistY = RandomUtils.nextFloat(-this.particleRange.get().floatValue(), this.particleRange.get().floatValue());
                        String firstChar = RandomUtils.random(1, (StringsKt.equals(this.riceParticleCircle.get(), "none", true) ? "" : "c") + (StringsKt.equals(this.riceParticleRect.get(), "none", true) ? "" : "r") + (StringsKt.equals(this.riceParticleTriangle.get(), "none", true) ? "" : "t"));
                        ShapeType.Companion companion = ShapeType.Companion;
                        if (Intrinsics.areEqual(firstChar, "c")) {
                            String lowerCase = this.riceParticleCircle.get().toLowerCase();
                            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                            str = Intrinsics.stringPlus("c_", lowerCase);
                        } else if (Intrinsics.areEqual(firstChar, "r")) {
                            String lowerCase2 = this.riceParticleRect.get().toLowerCase();
                            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                            str = Intrinsics.stringPlus("r_", lowerCase2);
                        } else {
                            String lowerCase3 = this.riceParticleTriangle.get().toLowerCase();
                            Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
                            str = Intrinsics.stringPlus("t_", lowerCase3);
                        }
                        ShapeType drawType = companion.getTypeFromName(str);
                        if (drawType == null) {
                            break;
                        }
                        List<Particle> list = this.particleList;
                        float[] fArr = {0.0f, 1.0f};
                        Color white = Color.white;
                        Intrinsics.checkNotNullExpressionValue(white, "white");
                        Color blendColors = BlendUtils.blendColors(fArr, new Color[]{white, getTargetInstance().getBarColor()}, RandomUtils.nextBoolean() ? RandomUtils.nextFloat(0.5f, 1.0f) : 0.0f);
                        Intrinsics.checkNotNullExpressionValue(blendColors, "blendColors(\n           …loat(0.5F, 1.0F) else 0F)");
                        list.add(new Particle(blendColors, parDistX, parDistY, parSize, drawType));
                    } while (j != intValue);
                }
                this.gotDamaged = false;
            }
            List deleteQueue = new ArrayList();
            Iterable $this$forEach$iv = this.particleList;
            for (Object element$iv : $this$forEach$iv) {
                Particle particle = (Particle) element$iv;
                if (particle.getAlpha() > 0.0f) {
                    particle.render(20.0f, 20.0f, getRiceParticleFade().get().booleanValue(), getRiceParticleSpeed().get().floatValue(), getRiceParticleFadingSpeed().get().floatValue(), getRiceParticleSpin().get().booleanValue());
                } else {
                    deleteQueue.add(particle);
                }
            }
            this.particleList.removeAll(deleteQueue);
        }
        float scaleHT = RangesKt.coerceIn(entity.field_70737_aN / RangesKt.coerceAtLeast(entity.field_70738_aO, 1), 0.0f, 1.0f);
        if (MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()) != null) {
            ResourceLocation func_178837_g = MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()).func_178837_g();
            Intrinsics.checkNotNullExpressionValue(func_178837_g, "mc.netHandler.getPlayerI…ty.uniqueID).locationSkin");
            drawHead(func_178837_g, 5.0f + (15.0f * scaleHT * 0.2f), 5.0f + (15.0f * scaleHT * 0.2f), 1.0f - (scaleHT * 0.2f), 30, 30, 1.0f, 0.4f + ((1.0f - scaleHT) * 0.6f), 0.4f + ((1.0f - scaleHT) * 0.6f), 1.0f - getTargetInstance().getFadeProgress());
        }
        GlStateManager.func_179117_G();
        font.drawString(name, 39.0f, 11.0f, getColor(-1).getRGB());
        font.drawString(info, 39.0f, 23.0f, getColor(-1).getRGB());
        float barWidth = ((length - 5.0f) - maxHealthLength) * RangesKt.coerceIn(getEasingHealth() / entity.func_110138_aP(), 0.0f, 1.0f);
        Stencil.write(false);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (this.gradientRoundedBarValue.get().booleanValue()) {
            if (barWidth > 0.0f) {
                RenderUtils.fastRoundedRect(5.0f, 42.0f, 5.0f + barWidth, 48.0f, 3.0f);
            }
        } else {
            RenderUtils.quickDrawRect(5.0f, 42.0f, 5.0f + barWidth, 48.0f);
        }
        GL11.glDisable(3042);
        Stencil.erase(true);
        String lowerCase4 = getTargetInstance().getColorModeValue().get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase4, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase4, "custom") ? true : Intrinsics.areEqual(lowerCase4, "health")) {
            RenderUtils.drawRect(5.0f, 42.0f, length - maxHealthLength, 48.0f, getTargetInstance().getBarColor().getRGB());
        } else {
            int i3 = 0;
            int intValue2 = this.gradientLoopValue.get().intValue() - 1;
            if (0 <= intValue2) {
                do {
                    i = i3;
                    i3++;
                    double barStart = (i / this.gradientLoopValue.get().intValue()) * ((length - 5.0f) - maxHealthLength);
                    double barEnd = ((i + 1) / this.gradientLoopValue.get().intValue()) * ((length - 5.0f) - maxHealthLength);
                    RenderUtils.drawGradientSideways(5.0d + barStart, 42.0d, 5.0d + barEnd, 48.0d, getColorAtIndex(i), getColorAtIndex(i + 1));
                } while (i != intValue2);
            }
        }
        Stencil.dispose();
        GlStateManager.func_179117_G();
        Intrinsics.checkNotNullExpressionValue(healthName, "healthName");
        font.drawString(healthName, 10.0f + barWidth, 41.0f, getColor(-1).getRGB());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final int getColorAtIndex(int i) {
        int i2;
        String str = getTargetInstance().getColorModeValue().get();
        switch (str.hashCode()) {
            case -1815582866:
                if (str.equals("Slowly")) {
                    Color LiquidSlowly = ColorUtils.LiquidSlowly(System.nanoTime(), i * this.gradientDistanceValue.get().intValue(), getTargetInstance().getSaturationValue().get().floatValue(), getTargetInstance().getBrightnessValue().get().floatValue());
                    Intrinsics.checkNotNull(LiquidSlowly);
                    i2 = LiquidSlowly.getRGB();
                    break;
                }
                i2 = -1;
                break;
            case -1656737386:
                if (str.equals("Rainbow")) {
                    i2 = RenderUtils.getRainbowOpaque(getTargetInstance().getWaveSecondValue().get().intValue(), getTargetInstance().getSaturationValue().get().floatValue(), getTargetInstance().getBrightnessValue().get().floatValue(), i * this.gradientDistanceValue.get().intValue());
                    break;
                }
                i2 = -1;
                break;
            case 83201:
                if (str.equals("Sky")) {
                    i2 = RenderUtils.SkyRainbow(i * this.gradientDistanceValue.get().intValue(), getTargetInstance().getSaturationValue().get().floatValue(), getTargetInstance().getBrightnessValue().get().floatValue());
                    break;
                }
                i2 = -1;
                break;
            case 2181788:
                if (str.equals("Fade")) {
                    i2 = ColorUtils.fade(new Color(getTargetInstance().getRedValue().get().intValue(), getTargetInstance().getGreenValue().get().intValue(), getTargetInstance().getBlueValue().get().intValue()), i * this.gradientDistanceValue.get().intValue(), 100).getRGB();
                    break;
                }
                i2 = -1;
                break;
            case 74357737:
                if (str.equals("Mixer")) {
                    i2 = ColorMixer.getMixedColor(i * this.gradientDistanceValue.get().intValue(), getTargetInstance().getWaveSecondValue().get().intValue()).getRGB();
                    break;
                }
                i2 = -1;
                break;
            default:
                i2 = -1;
                break;
        }
        return getColor(i2).getRGB();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleDamage(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        this.gotDamaged = true;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleBlur(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        GameFontRenderer font = Fonts.fontSFUI40;
        String name = Intrinsics.stringPlus("Name: ", entity.func_70005_c_());
        DecimalFormat decimalFormat2 = getDecimalFormat2();
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        String info = Intrinsics.stringPlus("Distance: ", decimalFormat2.format(PlayerExtension.getDistanceToEntityBox(entity2, (Entity) entity)));
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(font.func_78256_a(name), font.func_78256_a(info)) + 40.0f, 125.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtils.fastRoundedRect(0.0f, 0.0f, 10.0f + length, 55.0f, 8.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleShadowCut(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        handleBlur(entity);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleShadow(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        GameFontRenderer font = Fonts.fontSFUI40;
        String name = Intrinsics.stringPlus("Name: ", entity.func_70005_c_());
        DecimalFormat decimalFormat2 = getDecimalFormat2();
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        String info = Intrinsics.stringPlus("Distance: ", decimalFormat2.format(PlayerExtension.getDistanceToEntityBox(entity2, (Entity) entity)));
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(font.func_78256_a(name), font.func_78256_a(info)) + 40.0f, 125.0f);
        RenderUtils.originalRoundedRect(0.0f, 0.0f, 10.0f + length, 55.0f, 8.0f, getShadowOpaque().getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    @Nullable
    public Border getBorder(@Nullable EntityPlayer entity) {
        if (entity == null) {
            return new Border(0.0f, 0.0f, 135.0f, 55.0f);
        }
        GameFontRenderer font = Fonts.fontSFUI40;
        String name = Intrinsics.stringPlus("Name: ", entity.func_70005_c_());
        DecimalFormat decimalFormat2 = getDecimalFormat2();
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        String info = Intrinsics.stringPlus("Distance: ", decimalFormat2.format(PlayerExtension.getDistanceToEntityBox(entity2, (Entity) entity)));
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(font.func_78256_a(name), font.func_78256_a(info)) + 40.0f, 125.0f);
        return new Border(0.0f, 0.0f, 10.0f + length, 55.0f);
    }
}
