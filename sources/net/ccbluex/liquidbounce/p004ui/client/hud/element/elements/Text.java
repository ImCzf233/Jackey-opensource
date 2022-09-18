package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: Text.kt */
@ElementInfo(name = "Text")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018�� J2\u00020\u0001:\u0001JB-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u00108\u001a\u0004\u0018\u000109H\u0016J(\u0010:\u001a\u00020;2\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00062\u0006\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020\u0006H\u0002J\u0012\u0010>\u001a\u0004\u0018\u00010\r2\u0006\u0010?\u001a\u00020\rH\u0002J\u0018\u0010@\u001a\u00020;2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020$H\u0016J \u0010D\u001a\u00020;2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010E\u001a\u00020$H\u0016J\u0010\u0010F\u001a\u00020\r2\u0006\u0010?\u001a\u00020\rH\u0002J\u000e\u0010G\u001a\u00020��2\u0006\u0010A\u001a\u00020HJ\b\u0010I\u001a\u00020;H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u001a\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010,\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00102\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00104\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00105\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\r07X\u0082\u000e¢\u0006\u0002\n��¨\u0006K"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "autoComplete", "", "backgroundValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "bgalphaValue", "bgblueValue", "bggreenValue", "bgredValue", "blueValue", "blurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "brightnessValue", "cRainbowSecValue", "display", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "distanceValue", "editMode", "", "editTicks", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gradientAmountValue", "greenValue", "lastX", "lastZ", "lineValue", "pointer", "prevClick", "", "rainbowList", "Lnet/ccbluex/liquidbounce/value/ListValue;", "redValue", "saturationValue", "shadow", "skeetRectValue", "speedStr", "suggestion", "", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawExhiRect", "", "x2", "y2", "getReplacement", AsmConstants.STR, "handleKey", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "Ljava/awt/Color;", "updateElement", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text.class */
public final class Text extends Element {
    @NotNull
    private final TextValue displayString;
    @NotNull
    private final BoolValue backgroundValue;
    @NotNull
    private final BoolValue skeetRectValue;
    @NotNull
    private final BoolValue lineValue;
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final IntegerValue redValue;
    @NotNull
    private final IntegerValue greenValue;
    @NotNull
    private final IntegerValue blueValue;
    @NotNull
    private final IntegerValue alphaValue;
    @NotNull
    private final IntegerValue bgredValue;
    @NotNull
    private final IntegerValue bggreenValue;
    @NotNull
    private final IntegerValue bgblueValue;
    @NotNull
    private final IntegerValue bgalphaValue;
    @NotNull
    private final ListValue rainbowList;
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
    private final BoolValue shadow;
    @NotNull
    private FontValue fontValue;
    private boolean editMode;
    private int editTicks;
    private long prevClick;
    private double lastX;
    private double lastZ;
    @NotNull
    private String speedStr;
    @NotNull
    private List<String> suggestion;
    @NotNull
    private String displayText;
    private int pointer;
    @NotNull
    private String autoComplete;
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @NotNull
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT_INT = new DecimalFormat("0");

    /* compiled from: Text.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Side.Horizontal.values().length];
            iArr[Side.Horizontal.LEFT.ordinal()] = 1;
            iArr[Side.Horizontal.MIDDLE.ordinal()] = 2;
            iArr[Side.Horizontal.RIGHT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public Text() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Text(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 10.0d : d, (i & 2) != 0 ? 10.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? Side.Companion.m2845default() : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Text(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.displayString = new TextValue("DisplayText", "");
        this.backgroundValue = new BoolValue("Background", true);
        this.skeetRectValue = new BoolValue("SkeetRect", false);
        this.lineValue = new BoolValue("Line", true);
        this.blurValue = new BoolValue("Blur", true);
        this.blurStrength = new FloatValue("BlurStrength", 1.0f, 0.0f, 30.0f);
        this.redValue = new IntegerValue("Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Blue", 255, 0, 255);
        this.alphaValue = new IntegerValue("Alpha", 255, 0, 255);
        this.bgredValue = new IntegerValue("Background-Red", 0, 0, 255);
        this.bggreenValue = new IntegerValue("Background-Green", 0, 0, 255);
        this.bgblueValue = new IntegerValue("Background-Blue", 0, 0, 255);
        this.bgalphaValue = new IntegerValue("Background-Alpha", 120, 0, 255);
        this.rainbowList = new ListValue("Rainbow", new String[]{"Off", "CRainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Off");
        this.saturationValue = new FloatValue("Saturation", 0.9f, 0.0f, 1.0f);
        this.brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
        this.cRainbowSecValue = new IntegerValue("Seconds", 2, 1, 10);
        this.distanceValue = new IntegerValue("Line-Distance", 0, 0, TokenId.Identifier);
        this.gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);
        this.shadow = new BoolValue("Shadow", true);
        GameFontRenderer font40 = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(font40, "font40");
        this.fontValue = new FontValue("Font", font40);
        this.speedStr = "";
        this.suggestion = new ArrayList();
        this.displayText = getDisplay();
        this.autoComplete = "";
    }

    /* compiled from: Text.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "DECIMAL_FORMAT_INT", "getDECIMAL_FORMAT_INT", "HOUR_FORMAT", "getHOUR_FORMAT", "defaultClient", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final SimpleDateFormat getDATE_FORMAT() {
            return Text.DATE_FORMAT;
        }

        @NotNull
        public final SimpleDateFormat getHOUR_FORMAT() {
            return Text.HOUR_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT() {
            return Text.DECIMAL_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT_INT() {
            return Text.DECIMAL_FORMAT_INT;
        }

        @NotNull
        public final Text defaultClient() {
            Text text = new Text(5.0d, 5.0d, 1.0f, null, 8, null);
            text.displayString.set("%clientName%");
            text.shadow.set(true);
            FontValue fontValue = text.fontValue;
            GameFontRenderer font40 = Fonts.font40;
            Intrinsics.checkNotNullExpressionValue(font40, "font40");
            fontValue.set(font40);
            text.setColor(new Color(255, 255, 255));
            return text;
        }
    }

    private final String getDisplay() {
        String str;
        if ((this.displayString.get().length() == 0) && !this.editMode) {
            str = "Text Element";
        } else {
            str = this.displayString.get();
        }
        String textContent = str;
        return ColorUtils.translateAlternateColorCodes(multiReplace(textContent));
    }

    /* JADX WARN: Code restructure failed: missing block: B:111:0x046a, code lost:
        if (r9.equals("cps") == false) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0484, code lost:
        if (r9.equals("lcps") == false) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x057a, code lost:
        return java.lang.String.valueOf(net.ccbluex.liquidbounce.utils.CPSCounter.getCPS(net.ccbluex.liquidbounce.utils.CPSCounter.MouseButton.LEFT));
     */
    /* JADX WARN: Removed duplicated region for block: B:165:0x05c8 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.String getReplacement(java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 1482
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Text.getReplacement(java.lang.String):java.lang.String");
    }

    private final String multiReplace(String str) {
        int lastPercent = -1;
        StringBuilder result = new StringBuilder();
        int i = 0;
        int length = str.length();
        while (i < length) {
            int i2 = i;
            i++;
            if (str.charAt(i2) == '%') {
                if (lastPercent != -1) {
                    if (lastPercent + 1 != i2) {
                        String substring = str.substring(lastPercent + 1, i2);
                        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                        String replacement = getReplacement(substring);
                        if (replacement != null) {
                            result.append(replacement);
                            lastPercent = -1;
                        }
                    }
                    result.append((CharSequence) str, lastPercent, i2);
                }
                lastPercent = i2;
            } else if (lastPercent == -1) {
                result.append(str.charAt(i2));
            }
        }
        if (lastPercent != -1) {
            result.append((CharSequence) str, lastPercent, str.length());
        }
        String sb = result.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "result.toString()");
        return sb;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int color = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
        FontRenderer fontRenderer = this.fontValue.get();
        String rainbowType = this.rainbowList.get();
        switch (WhenMappings.$EnumSwitchMapping$0[getSide().getHorizontal().ordinal()]) {
            case 1:
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                break;
            case 2:
                GL11.glTranslatef((-fontRenderer.func_78256_a(this.displayText)) / 2.0f, 0.0f, (-fontRenderer.func_78256_a(this.displayText)) / 2.0f);
                break;
            case 3:
                GL11.glTranslatef(-fontRenderer.func_78256_a(this.displayText), 0.0f, -fontRenderer.func_78256_a(this.displayText));
                break;
        }
        float floatX = (float) getRenderX();
        float floatY = (float) getRenderY();
        if (this.blurValue.get().booleanValue()) {
            GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            BlurUtils.blurArea((floatX * getScale()) - (2.0f * getScale()), (floatY * getScale()) - (2.0f * getScale()), (floatX + fontRenderer.func_78256_a(this.displayText) + 2.0f) * getScale(), (floatY + fontRenderer.field_78288_b) * getScale(), this.blurStrength.get().floatValue());
            GL11.glPopMatrix();
            GL11.glScalef(getScale(), getScale(), getScale());
            GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
        }
        if (this.backgroundValue.get().booleanValue()) {
            RenderUtils.drawRect(-2.0f, -2.0f, fontRenderer.func_78256_a(this.displayText) + 2.0f, fontRenderer.field_78288_b + 0.0f, new Color(this.bgredValue.get().intValue(), this.bggreenValue.get().intValue(), this.bgblueValue.get().intValue(), this.bgalphaValue.get().intValue()));
        }
        if (this.skeetRectValue.get().booleanValue()) {
            drawExhiRect(-4.0f, this.lineValue.get().booleanValue() ? -5.0f : -4.0f, fontRenderer.func_78256_a(this.displayText) + 4.0f, fontRenderer.field_78288_b + 2.0f);
        }
        int FadeColor = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()), 0, 100).getRGB();
        Color LiquidSlowly = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
        Integer LiquidSlowly2 = LiquidSlowly == null ? null : Integer.valueOf(LiquidSlowly.getRGB());
        Intrinsics.checkNotNull(LiquidSlowly2);
        int liquidSlowli = LiquidSlowly2.intValue();
        int mixerColor = ColorMixer.getMixedColor(0, this.cRainbowSecValue.get().intValue()).getRGB();
        if (this.lineValue.get().booleanValue()) {
            double barLength = fontRenderer.func_78256_a(this.displayText) + 4.0f;
            int i6 = 0;
            int intValue = this.gradientAmountValue.get().intValue() - 1;
            if (0 <= intValue) {
                do {
                    i3 = i6;
                    i6++;
                    double barStart = (i3 / this.gradientAmountValue.get().intValue()) * barLength;
                    double barEnd = ((i3 + 1) / this.gradientAmountValue.get().intValue()) * barLength;
                    double d = (-2.0d) + barStart;
                    double d2 = (-2.0d) + barEnd;
                    switch (rainbowType.hashCode()) {
                        case -884013110:
                            if (rainbowType.equals("LiquidSlowly")) {
                                Color LiquidSlowly3 = ColorUtils.LiquidSlowly(System.nanoTime(), i3 * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                Intrinsics.checkNotNull(LiquidSlowly3);
                                i4 = LiquidSlowly3.getRGB();
                                break;
                            }
                            i4 = color;
                            break;
                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i4 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), i3 * this.distanceValue.get().intValue());
                                break;
                            }
                            i4 = color;
                            break;
                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i4 = RenderUtils.SkyRainbow(i3 * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                break;
                            }
                            i4 = color;
                            break;
                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i4 = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), i3 * this.distanceValue.get().intValue(), 100).getRGB();
                                break;
                            }
                            i4 = color;
                            break;
                        case 74357737:
                            if (rainbowType.equals("Mixer")) {
                                i4 = ColorMixer.getMixedColor(i3 * this.distanceValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB();
                                break;
                            }
                            i4 = color;
                            break;
                        default:
                            i4 = color;
                            break;
                    }
                    switch (rainbowType.hashCode()) {
                        case -884013110:
                            if (rainbowType.equals("LiquidSlowly")) {
                                Color LiquidSlowly4 = ColorUtils.LiquidSlowly(System.nanoTime(), (i3 + 1) * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                Intrinsics.checkNotNull(LiquidSlowly4);
                                i5 = LiquidSlowly4.getRGB();
                                break;
                            }
                            i5 = color;
                            break;
                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i5 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), (i3 + 1) * this.distanceValue.get().intValue());
                                break;
                            }
                            i5 = color;
                            break;
                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i5 = RenderUtils.SkyRainbow((i3 + 1) * this.distanceValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                                break;
                            }
                            i5 = color;
                            break;
                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i5 = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), (i3 + 1) * this.distanceValue.get().intValue(), 100).getRGB();
                                break;
                            }
                            i5 = color;
                            break;
                        case 74357737:
                            if (rainbowType.equals("Mixer")) {
                                i5 = ColorMixer.getMixedColor((i3 + 1) * this.distanceValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB();
                                break;
                            }
                            i5 = color;
                            break;
                        default:
                            i5 = color;
                            break;
                    }
                    RenderUtils.drawGradientSideways(d, -3.0d, d2, -2.0d, i4, i5);
                } while (i3 != intValue);
            }
        }
        String str = this.displayText;
        switch (rainbowType.hashCode()) {
            case -884013110:
                if (rainbowType.equals("LiquidSlowly")) {
                    i = liquidSlowli;
                    break;
                }
                i = color;
                break;
            case -852561933:
                if (rainbowType.equals("CRainbow")) {
                    i = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0);
                    break;
                }
                i = color;
                break;
            case 83201:
                if (rainbowType.equals("Sky")) {
                    i = RenderUtils.SkyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                    break;
                }
                i = color;
                break;
            case 2181788:
                if (rainbowType.equals("Fade")) {
                    i = FadeColor;
                    break;
                }
                i = color;
                break;
            case 74357737:
                if (rainbowType.equals("Mixer")) {
                    i = mixerColor;
                    break;
                }
                i = color;
                break;
            default:
                i = color;
                break;
        }
        fontRenderer.func_175065_a(str, 0.0f, 0.0f, i, this.shadow.get().booleanValue());
        if (this.editMode && (MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner)) {
            if (this.editTicks <= 40) {
                float func_78256_a = fontRenderer.func_78256_a(this.displayText) + 2.0f;
                switch (rainbowType.hashCode()) {
                    case -884013110:
                        if (rainbowType.equals("LiquidSlowly")) {
                            i2 = liquidSlowli;
                            break;
                        }
                        i2 = color;
                        break;
                    case -852561933:
                        if (rainbowType.equals("CRainbow")) {
                            i2 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0);
                            break;
                        }
                        i2 = color;
                        break;
                    case 83201:
                        if (rainbowType.equals("Sky")) {
                            i2 = RenderUtils.SkyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                            break;
                        }
                        i2 = color;
                        break;
                    case 2181788:
                        if (rainbowType.equals("Fade")) {
                            i2 = FadeColor;
                            break;
                        }
                        i2 = color;
                        break;
                    case 74357737:
                        if (rainbowType.equals("Mixer")) {
                            i2 = mixerColor;
                            break;
                        }
                        i2 = color;
                        break;
                    default:
                        i2 = color;
                        break;
                }
                fontRenderer.func_175065_a("_", func_78256_a, 0.0f, i2, this.shadow.get().booleanValue());
            }
            if (this.suggestion.size() > 0) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                int totalLength = fontRenderer.func_78256_a(this.suggestion.get(0));
                Iterable $this$forEachIndexed$iv = this.suggestion;
                int index$iv = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    int index = index$iv;
                    index$iv = index + 1;
                    if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    String suggest = (String) item$iv;
                    RenderUtils.drawRect(fontRenderer.func_78256_a(this.displayText) + 2.0f, (fontRenderer.field_78288_b * index) + 5.0f, fontRenderer.func_78256_a(this.displayText) + 6.0f + totalLength, (fontRenderer.field_78288_b * index) + 5.0f + fontRenderer.field_78288_b, index == this.pointer ? new Color(90, 90, 90, 120).getRGB() : new Color(0, 0, 0, 120).getRGB());
                    fontRenderer.func_175063_a(suggest, fontRenderer.func_78256_a(this.displayText) + 4.0f, (fontRenderer.field_78288_b * index) + 5.0f, -1);
                }
            }
        }
        if (this.editMode && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner)) {
            this.editMode = false;
            updateElement();
        }
        switch (WhenMappings.$EnumSwitchMapping$0[getSide().getHorizontal().ordinal()]) {
            case 1:
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                break;
            case 2:
                GL11.glTranslatef(fontRenderer.func_78256_a(this.displayText) / 2.0f, 0.0f, fontRenderer.func_78256_a(this.displayText) / 2.0f);
                break;
            case 3:
                GL11.glTranslatef(fontRenderer.func_78256_a(this.displayText), 0.0f, fontRenderer.func_78256_a(this.displayText));
                break;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[getSide().getHorizontal().ordinal()]) {
            case 1:
                return new Border(-2.0f, -2.0f, fontRenderer.func_78256_a(this.displayText) + 2.0f, fontRenderer.field_78288_b);
            case 2:
                return new Border((-fontRenderer.func_78256_a(this.displayText)) / 2.0f, -2.0f, (fontRenderer.func_78256_a(this.displayText) / 2.0f) + 2.0f, fontRenderer.field_78288_b);
            case 3:
                return new Border(2.0f, -2.0f, (-fontRenderer.func_78256_a(this.displayText)) - 2.0f, fontRenderer.field_78288_b);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final void drawExhiRect(float x, float y, float x2, float y2) {
        RenderUtils.drawRect(x - 1.5f, y - 1.5f, x2 + 1.5f, y2 + 1.5f, new Color(8, 8, 8).getRGB());
        RenderUtils.drawRect(x - 1, y - 1, x2 + 1, y2 + 1, new Color(49, 49, 49).getRGB());
        RenderUtils.drawBorderedRect(x + 2.0f, y + 2.0f, x2 - 2.0f, y2 - 2.0f, 0.5f, new Color(18, 18, 18).getRGB(), new Color(28, 28, 28).getRGB());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:22:0x00b2
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void updateElement() {
        /*
            Method dump skipped, instructions count: 1003
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Text.updateElement():void");
    }

    /* renamed from: updateElement$lambda-3 */
    private static final String m2861updateElement$lambda3(Ref.ObjectRef suggestStr, String s) {
        Intrinsics.checkNotNullParameter(suggestStr, "$suggestStr");
        Intrinsics.checkNotNullParameter(s, "s");
        StringBuilder append = new StringBuilder().append("§7").append((String) suggestStr.element).append("§r");
        String substring = s.substring(RangesKt.coerceIn(((String) suggestStr.element).length(), 0, s.length()), s.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return append.append(substring).toString();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void handleMouseClick(double x, double y, int mouseButton) {
        if (isInBorder(x, y) && mouseButton == 0) {
            if (System.currentTimeMillis() - this.prevClick <= 250) {
                this.editMode = true;
            }
            this.prevClick = System.currentTimeMillis();
            return;
        }
        this.editMode = false;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void handleKey(char c, int keyCode) {
        if (this.editMode && (MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner)) {
            if (keyCode == 14) {
                if (this.displayString.get().length() > 0) {
                    TextValue textValue = this.displayString;
                    String substring = this.displayString.get().substring(0, this.displayString.get().length() - 1);
                    Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                    textValue.set(substring);
                }
                updateElement();
            } else if (keyCode == 200) {
                if (this.suggestion.size() > 1) {
                    if (this.pointer <= 0) {
                        this.pointer = this.suggestion.size() - 1;
                    } else {
                        this.pointer--;
                    }
                }
                updateElement();
            } else if (keyCode == 208) {
                if (this.suggestion.size() > 1) {
                    if (this.pointer >= this.suggestion.size() - 1) {
                        this.pointer = 0;
                    } else {
                        this.pointer++;
                    }
                }
                updateElement();
            } else {
                switch (keyCode) {
                    case 15:
                    case 28:
                        this.displayString.set(Intrinsics.stringPlus(this.displayString.get(), this.autoComplete));
                        updateElement();
                        return;
                    default:
                        if (ChatAllowedCharacters.func_71566_a(c) || c == 167) {
                            this.displayString.set(Intrinsics.stringPlus(this.displayString.get(), Character.valueOf(c)));
                        }
                        updateElement();
                        return;
                }
            }
        }
    }

    @NotNull
    public final Text setColor(@NotNull Color c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.redValue.set((IntegerValue) Integer.valueOf(c.getRed()));
        this.greenValue.set((IntegerValue) Integer.valueOf(c.getGreen()));
        this.blueValue.set((IntegerValue) Integer.valueOf(c.getBlue()));
        return this;
    }
}
