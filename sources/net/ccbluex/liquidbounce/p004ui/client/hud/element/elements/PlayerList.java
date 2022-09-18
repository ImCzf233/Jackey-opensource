package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtension;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlayerList.kt */
@ElementInfo(name = "PlayerList")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n��¨\u0006 "}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/PlayerList;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "bgalphaValue", "bgblueValue", "bggreenValue", "bgredValue", "blueValue", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "cRainbowSecValue", "decimalFormat3", "Ljava/text/DecimalFormat;", "distanceValue", "fontOffsetValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gradientAmountValue", "greenValue", "lineValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "rainbowList", "Lnet/ccbluex/liquidbounce/value/ListValue;", "redValue", "reverseValue", "saturationValue", "shadowValue", "sortValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.PlayerList */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/PlayerList.class */
public final class PlayerList extends Element {
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final DecimalFormat decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
    @NotNull
    private final ListValue sortValue = new ListValue("Sort", new String[]{"Alphabet", "Distance", "Health"}, "Alphabet");
    @NotNull
    private final FloatValue fontOffsetValue = new FloatValue("Font-Offset", 0.0f, 3.0f, -3.0f);
    @NotNull
    private final BoolValue reverseValue = new BoolValue("Reverse", false);
    @NotNull
    private final BoolValue shadowValue = new BoolValue("Shadow", false);
    @NotNull
    private final BoolValue lineValue = new BoolValue("Line", true);
    @NotNull
    private final IntegerValue redValue = new IntegerValue("Red", 255, 0, 255);
    @NotNull
    private final IntegerValue greenValue = new IntegerValue("Green", 255, 0, 255);
    @NotNull
    private final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255);
    @NotNull
    private final IntegerValue alphaValue = new IntegerValue("Alpha", 255, 0, 255);
    @NotNull
    private final IntegerValue bgredValue = new IntegerValue("Background-Red", 0, 0, 255);
    @NotNull
    private final IntegerValue bggreenValue = new IntegerValue("Background-Green", 0, 0, 255);
    @NotNull
    private final IntegerValue bgblueValue = new IntegerValue("Background-Blue", 0, 0, 255);
    @NotNull
    private final IntegerValue bgalphaValue = new IntegerValue("Background-Alpha", 120, 0, 255);
    @NotNull
    private final ListValue rainbowList = new ListValue("Rainbow", new String[]{"Off", "CRainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Off");
    @NotNull
    private final FloatValue saturationValue = new FloatValue("Saturation", 0.9f, 0.0f, 1.0f);
    @NotNull
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    @NotNull
    private final IntegerValue cRainbowSecValue = new IntegerValue("Seconds", 2, 1, 10);
    @NotNull
    private final IntegerValue distanceValue = new IntegerValue("Line-Distance", 0, 0, TokenId.Identifier);
    @NotNull
    private final IntegerValue gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);

    public PlayerList() {
        super(0.0d, 0.0d, 0.0f, null, 15, null);
        GameFontRenderer font35 = Fonts.font35;
        Intrinsics.checkNotNullExpressionValue(font35, "font35");
        this.fontValue = new FontValue("Font", font35);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        int i;
        int i2;
        int i3;
        Entity entity;
        boolean reverse = this.reverseValue.get().booleanValue();
        FontRenderer font = this.fontValue.get();
        float fontOffset = this.fontOffsetValue.get().floatValue();
        String rainbowType = this.rainbowList.get();
        font.func_78256_a("Name (0)");
        float hpLength = font.func_78256_a("Health");
        float distLength = font.func_78256_a("Distance");
        float height = 4.0f + font.field_78288_b;
        int color = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
        Color bgColor = new Color(this.bgredValue.get().intValue(), this.bggreenValue.get().intValue(), this.bgblueValue.get().intValue(), this.bgalphaValue.get().intValue());
        Iterable iterable = MinecraftInstance.f362mc.field_71441_e.field_73010_i;
        Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.playerEntities");
        Iterable $this$filter$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            EntityLivingBase entityLivingBase = (EntityPlayer) element$iv$iv;
            if (!AntiBot.isBot(entityLivingBase) && !Intrinsics.areEqual(entityLivingBase, MinecraftInstance.f362mc.field_71439_g)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List playerList = CollectionsKt.toMutableList((Collection) ((List) destination$iv$iv));
        float nameLength = font.func_78256_a("Name (" + playerList.size() + ')');
        String str = this.sortValue.get();
        if (Intrinsics.areEqual(str, "Alphabet")) {
            CollectionsKt.sortWith(playerList, new Comparator() { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.PlayerList$drawElement$$inlined$compareBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    EntityPlayer it = (EntityPlayer) t;
                    String func_70005_c_ = it.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(func_70005_c_, "it.name");
                    String lowerCase = func_70005_c_.toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                    String str2 = lowerCase;
                    EntityPlayer it2 = (EntityPlayer) t2;
                    String func_70005_c_2 = it2.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(func_70005_c_2, "it.name");
                    String lowerCase2 = func_70005_c_2.toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                    return ComparisonsKt.compareValues(str2, lowerCase2);
                }
            });
        } else if (Intrinsics.areEqual(str, "Distance")) {
            CollectionsKt.sortWith(playerList, PlayerList::m2855drawElement$lambda2);
        } else {
            CollectionsKt.sortWith(playerList, PlayerList::m2856drawElement$lambda3);
        }
        if (reverse) {
            playerList = CollectionsKt.toMutableList((Collection) CollectionsKt.reversed(playerList));
        }
        Iterable $this$forEach$iv = playerList;
        for (Object element$iv : $this$forEach$iv) {
            Entity entity2 = (EntityPlayer) element$iv;
            if (font.func_78256_a(entity2.func_70005_c_()) > nameLength) {
                nameLength = font.func_78256_a(entity2.func_70005_c_());
            }
            if (font.func_78256_a(Intrinsics.stringPlus(this.decimalFormat3.format(Float.valueOf(entity2.func_110143_aJ())), " HP")) > hpLength) {
                hpLength = font.func_78256_a(Intrinsics.stringPlus(this.decimalFormat3.format(Float.valueOf(entity2.func_110143_aJ())), " HP"));
            }
            DecimalFormat decimalFormat = this.decimalFormat3;
            Intrinsics.checkNotNullExpressionValue(MinecraftInstance.f362mc.field_71439_g, "mc.thePlayer");
            if (font.func_78256_a(Intrinsics.stringPlus(decimalFormat.format(PlayerExtension.getDistanceToEntityBox(entity, entity2)), "m")) > distLength) {
                DecimalFormat decimalFormat2 = this.decimalFormat3;
                Entity entity3 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(entity3, "mc.thePlayer");
                distLength = font.func_78256_a(Intrinsics.stringPlus(decimalFormat2.format(PlayerExtension.getDistanceToEntityBox(entity3, entity2)), "m"));
            }
        }
        if (this.lineValue.get().booleanValue()) {
            double barLength = nameLength + hpLength + distLength + 50.0f;
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
                            i2 = color;
                            break;
                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i2 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), i * this.distanceValue.get().intValue());
                                break;
                            }
                            i2 = color;
                            break;
                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i2 = RenderUtils.SkyRainbow(i * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                break;
                            }
                            i2 = color;
                            break;
                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i2 = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), i * this.distanceValue.get().intValue(), 100).getRGB();
                                break;
                            }
                            i2 = color;
                            break;
                        case 74357737:
                            if (rainbowType.equals("Mixer")) {
                                i2 = ColorMixer.getMixedColor(i * this.distanceValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB();
                                break;
                            }
                            i2 = color;
                            break;
                        default:
                            i2 = color;
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
                            i3 = color;
                            break;
                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i3 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), (i + 1) * this.distanceValue.get().intValue());
                                break;
                            }
                            i3 = color;
                            break;
                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i3 = RenderUtils.SkyRainbow((i + 1) * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                break;
                            }
                            i3 = color;
                            break;
                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i3 = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), (i + 1) * this.distanceValue.get().intValue(), 100).getRGB();
                                break;
                            }
                            i3 = color;
                            break;
                        case 74357737:
                            if (rainbowType.equals("Mixer")) {
                                i3 = ColorMixer.getMixedColor((i + 1) * this.distanceValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB();
                                break;
                            }
                            i3 = color;
                            break;
                        default:
                            i3 = color;
                            break;
                    }
                    RenderUtils.drawGradientSideways(barStart, -1.0d, barEnd, 0.0d, i2, i3);
                } while (i != intValue);
            }
        }
        RenderUtils.drawRect(0.0f, 0.0f, nameLength + hpLength + distLength + 50.0f, 4.0f + font.field_78288_b, bgColor.getRGB());
        font.func_175065_a("Name (" + playerList.size() + ')', 5.0f, 3.0f, -1, this.shadowValue.get().booleanValue());
        font.func_175065_a("Distance", 5.0f + nameLength + 10.0f, 3.0f, -1, this.shadowValue.get().booleanValue());
        font.func_175065_a("Health", 5.0f + nameLength + distLength + 20.0f, 3.0f, -1, this.shadowValue.get().booleanValue());
        Iterable $this$forEachIndexed$iv = playerList;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int i5 = index$iv;
            index$iv = i5 + 1;
            if (i5 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Entity entity4 = (EntityPlayer) item$iv;
            RenderUtils.drawRect(0.0f, height, nameLength + hpLength + distLength + 50.0f, height + 2.0f + font.field_78288_b, bgColor.getRGB());
            font.func_175065_a(entity4.func_70005_c_(), 5.0f, height + 1.0f + fontOffset, -1, this.shadowValue.get().booleanValue());
            DecimalFormat decimalFormat3 = this.decimalFormat3;
            Entity entity5 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entity5, "mc.thePlayer");
            font.func_175065_a(Intrinsics.stringPlus(decimalFormat3.format(PlayerExtension.getDistanceToEntityBox(entity5, entity4)), "m"), 5.0f + nameLength + 10.0f, height + 1.0f + fontOffset, -1, this.shadowValue.get().booleanValue());
            font.func_175065_a(Intrinsics.stringPlus(this.decimalFormat3.format(Float.valueOf(entity4.func_110143_aJ())), " HP"), 5.0f + nameLength + distLength + 20.0f, height + 1.0f + fontOffset, -1, this.shadowValue.get().booleanValue());
            height += 2.0f + font.field_78288_b;
        }
        return new Border(0.0f, 0.0f, nameLength + hpLength + distLength + 50.0f, 4.0f + height + font.field_78288_b);
    }

    /* renamed from: drawElement$lambda-2 */
    private static final int m2855drawElement$lambda2(EntityPlayer a, EntityPlayer b) {
        Entity entity = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity, "mc.thePlayer");
        Intrinsics.checkNotNullExpressionValue(a, "a");
        double distanceToEntityBox = PlayerExtension.getDistanceToEntityBox(entity, (Entity) a);
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        Intrinsics.checkNotNullExpressionValue(b, "b");
        return Double.compare(distanceToEntityBox, PlayerExtension.getDistanceToEntityBox(entity2, (Entity) b));
    }

    /* renamed from: drawElement$lambda-3 */
    private static final int m2856drawElement$lambda3(EntityPlayer a, EntityPlayer b) {
        return Float.compare(a.func_110143_aJ(), b.func_110143_aJ());
    }
}
