package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Arraylist.kt */
@ElementInfo(name = "Arraylist", single = true)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010F\u001a\u0004\u0018\u00010GH\u0016J\u000e\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020-J\u0010\u0010K\u001a\u00020I2\u0006\u0010L\u001a\u00020-H\u0002J\b\u0010M\u001a\u00020NH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u001a\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b\u001e\u0010\u001cR\u0011\u0010\u001f\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b \u0010\u001cR\u000e\u0010!\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\"\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b#\u0010\u001cR\u000e\u0010$\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00102\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00104\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00105\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00106\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00107\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00108\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00109\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010:\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020-0,X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010<\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010=\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010>\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010?\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010@\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010A\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010B\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010C\u001a\u00020DX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010E\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006O"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "abcOrder", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "animationSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "blurStrength", "blurValue", "brightnessValue", "cRainbowDistValue", "cRainbowSecValue", "caseValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorAlphaValue", "getColorAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorBlueValue", "getColorBlueValue", "colorGreenValue", "getColorGreenValue", "colorModeValue", "colorRedValue", "getColorRedValue", "fadeDistanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "hAnimation", "liquidSlowlyDistanceValue", "mixerDistValue", "mixerSecValue", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "nameBreak", "rectLeftValue", "rectRightValue", "saturationValue", "shadow", "shadowColorBlueValue", "shadowColorGreenValue", "shadowColorMode", "shadowColorRedValue", "shadowNoCutValue", "shadowShaderValue", "shadowStrength", "skyDistanceValue", "sortedModules", "spaceValue", "tags", "tagsArrayColor", "tagsStyleValue", "textHeightValue", "textYValue", "vAnimation", "x2", "", "y2", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getModName", "", "mod", "getModTag", "m", "updateElement", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist.class */
public final class Arraylist extends Element {
    @NotNull
    private final ListValue colorModeValue;
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final BoolValue shadowShaderValue;
    @NotNull
    private final BoolValue shadowNoCutValue;
    @NotNull
    private final IntegerValue shadowStrength;
    @NotNull
    private final ListValue shadowColorMode;
    @NotNull
    private final IntegerValue shadowColorRedValue;
    @NotNull
    private final IntegerValue shadowColorGreenValue;
    @NotNull
    private final IntegerValue shadowColorBlueValue;
    @NotNull
    private final IntegerValue colorRedValue;
    @NotNull
    private final IntegerValue colorGreenValue;
    @NotNull
    private final IntegerValue colorBlueValue;
    @NotNull
    private final IntegerValue colorAlphaValue;
    @NotNull
    private final FloatValue saturationValue;
    @NotNull
    private final FloatValue brightnessValue;
    @NotNull
    private final IntegerValue skyDistanceValue;
    @NotNull
    private final IntegerValue cRainbowSecValue;
    @NotNull
    private final IntegerValue cRainbowDistValue;
    @NotNull
    private final IntegerValue mixerSecValue;
    @NotNull
    private final IntegerValue mixerDistValue;
    @NotNull
    private final IntegerValue liquidSlowlyDistanceValue;
    @NotNull
    private final IntegerValue fadeDistanceValue;
    @NotNull
    private final ListValue hAnimation;
    @NotNull
    private final ListValue vAnimation;
    @NotNull
    private final FloatValue animationSpeed;
    @NotNull
    private final BoolValue nameBreak;
    @NotNull
    private final BoolValue abcOrder;
    @NotNull
    private final BoolValue tags;
    @NotNull
    private final ListValue tagsStyleValue;
    @NotNull
    private final BoolValue shadow;
    @NotNull
    private final IntegerValue backgroundColorRedValue;
    @NotNull
    private final IntegerValue backgroundColorGreenValue;
    @NotNull
    private final IntegerValue backgroundColorBlueValue;
    @NotNull
    private final IntegerValue backgroundColorAlphaValue;
    @NotNull
    private final ListValue rectRightValue;
    @NotNull
    private final ListValue rectLeftValue;
    @NotNull
    private final ListValue caseValue;
    @NotNull
    private final FloatValue spaceValue;
    @NotNull
    private final FloatValue textHeightValue;
    @NotNull
    private final FloatValue textYValue;
    @NotNull
    private final BoolValue tagsArrayColor;
    @NotNull
    private final FontValue fontValue;

    /* renamed from: x2 */
    private int f355x2;

    /* renamed from: y2 */
    private float f356y2;
    @NotNull
    private List<? extends Module> modules;
    @NotNull
    private List<? extends Module> sortedModules;

    /* compiled from: Arraylist.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Side.Horizontal.values().length];
            iArr[Side.Horizontal.RIGHT.ordinal()] = 1;
            iArr[Side.Horizontal.MIDDLE.ordinal()] = 2;
            iArr[Side.Horizontal.LEFT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public Arraylist() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Arraylist(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0d : d, (i & 2) != 0 ? 2.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.UP) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Arraylist(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.colorModeValue = new ListValue("Color", new String[]{"Custom", "Random", "Sky", "CRainbow", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
        this.blurValue = new BoolValue("Blur", false);
        this.blurStrength = new FloatValue("Blur-Strength", 0.0f, 0.0f, 30.0f, new Arraylist$blurStrength$1(this));
        this.shadowShaderValue = new BoolValue("Shadow", false);
        this.shadowNoCutValue = new BoolValue("Shadow-NoCut", false);
        this.shadowStrength = new IntegerValue("Shadow-Strength", 1, 1, 30, new Arraylist$shadowStrength$1(this));
        this.shadowColorMode = new ListValue("Shadow-Color", new String[]{"Background", "Text", "Custom"}, "Background", new Arraylist$shadowColorMode$1(this));
        this.shadowColorRedValue = new IntegerValue("Shadow-Red", 0, 0, 255, new Arraylist$shadowColorRedValue$1(this));
        this.shadowColorGreenValue = new IntegerValue("Shadow-Green", 111, 0, 255, new Arraylist$shadowColorGreenValue$1(this));
        this.shadowColorBlueValue = new IntegerValue("Shadow-Blue", 255, 0, 255, new Arraylist$shadowColorBlueValue$1(this));
        this.colorRedValue = new IntegerValue("Red", 0, 0, 255);
        this.colorGreenValue = new IntegerValue("Green", 111, 0, 255);
        this.colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
        this.colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 0.9f, 0.0f, 1.0f);
        this.brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
        this.skyDistanceValue = new IntegerValue("Sky-Distance", 2, 0, 4);
        this.cRainbowSecValue = new IntegerValue("CRainbow-Seconds", 2, 1, 10);
        this.cRainbowDistValue = new IntegerValue("CRainbow-Distance", 2, 1, 6);
        this.mixerSecValue = new IntegerValue("Mixer-Seconds", 2, 1, 10);
        this.mixerDistValue = new IntegerValue("Mixer-Distance", 2, 0, 10);
        this.liquidSlowlyDistanceValue = new IntegerValue("LiquidSlowly-Distance", 90, 1, 90);
        this.fadeDistanceValue = new IntegerValue("Fade-Distance", 50, 1, 100);
        this.hAnimation = new ListValue("HorizontalAnimation", new String[]{"Default", "None", "Slide", "Astolfo"}, "Default");
        this.vAnimation = new ListValue("VerticalAnimation", new String[]{"None", "LiquidSense", "Slide", "Rise", "Astolfo"}, "None");
        this.animationSpeed = new FloatValue("Animation-Speed", 0.25f, 0.01f, 1.0f);
        this.nameBreak = new BoolValue("NameBreak", true);
        this.abcOrder = new BoolValue("Alphabetical-Order", false);
        this.tags = new BoolValue("Tags", true);
        this.tagsStyleValue = new ListValue("TagsStyle", new String[]{"-", CallSiteDescriptor.OPERATOR_DELIMITER, "()", "[]", "<>", "Default"}, "-");
        this.shadow = new BoolValue("ShadowText", true);
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255);
        this.rectRightValue = new ListValue("Rect-Right", new String[]{"None", "Left", "Right", "Outline", "Special", "Top"}, "None");
        this.rectLeftValue = new ListValue("Rect-Left", new String[]{"None", "Left", "Right"}, "None");
        this.caseValue = new ListValue("Case", new String[]{"None", "Lower", "Upper"}, "None");
        this.spaceValue = new FloatValue("Space", 0.0f, 0.0f, 5.0f);
        this.textHeightValue = new FloatValue("TextHeight", 11.0f, 1.0f, 20.0f);
        this.textYValue = new FloatValue("TextY", 1.0f, 0.0f, 20.0f);
        this.tagsArrayColor = new BoolValue("TagsArrayColor", false);
        GameFontRenderer font40 = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(font40, "font40");
        this.fontValue = new FontValue("Font", font40);
        this.modules = CollectionsKt.emptyList();
        this.sortedModules = CollectionsKt.emptyList();
    }

    @NotNull
    public final IntegerValue getColorRedValue() {
        return this.colorRedValue;
    }

    @NotNull
    public final IntegerValue getColorGreenValue() {
        return this.colorGreenValue;
    }

    @NotNull
    public final IntegerValue getColorBlueValue() {
        return this.colorBlueValue;
    }

    @NotNull
    public final IntegerValue getColorAlphaValue() {
        return this.colorAlphaValue;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x019c, code lost:
        if ((r0.getSlide() == 0.0f) == false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x04b0, code lost:
        if (r0.equals("Slide") == false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x04be, code lost:
        if (r0.equals("Rise") == false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0550, code lost:
        r0.setArrayY((float) net.ccbluex.liquidbounce.utils.AnimationUtils.animate(r33, r0.getArrayY(), (r14.animationSpeed.get().floatValue() * 0.025d) * r0));
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03ab  */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public net.ccbluex.liquidbounce.p004ui.client.hud.element.Border drawElement() {
        /*
            Method dump skipped, instructions count: 4920
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Arraylist.drawElement():net.ccbluex.liquidbounce.ui.client.hud.element.Border");
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0096 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x003b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0134 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d9 A[SYNTHETIC] */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateElement() {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Arraylist.updateElement():void");
    }

    private final String getModTag(Module m) {
        if (!this.tags.get().booleanValue() || m.getTag() == null) {
            return "";
        }
        String returnTag = Intrinsics.stringPlus(" ", this.tagsArrayColor.get().booleanValue() ? "" : "§7");
        if (!StringsKt.equals(this.tagsStyleValue.get(), "default", true)) {
            returnTag = returnTag + this.tagsStyleValue.get().charAt(0) + ((StringsKt.equals(this.tagsStyleValue.get(), "-", true) || StringsKt.equals(this.tagsStyleValue.get(), CallSiteDescriptor.OPERATOR_DELIMITER, true)) ? " " : "");
        }
        String returnTag2 = Intrinsics.stringPlus(returnTag, m.getTag());
        if (!StringsKt.equals(this.tagsStyleValue.get(), "default", true) && !StringsKt.equals(this.tagsStyleValue.get(), "-", true) && !StringsKt.equals(this.tagsStyleValue.get(), CallSiteDescriptor.OPERATOR_DELIMITER, true)) {
            returnTag2 = Intrinsics.stringPlus(returnTag2, Character.valueOf(this.tagsStyleValue.get().charAt(1)));
        }
        return returnTag2;
    }

    @NotNull
    public final String getModName(@NotNull Module mod) {
        Intrinsics.checkNotNullParameter(mod, "mod");
        String displayName = Intrinsics.stringPlus(this.nameBreak.get().booleanValue() ? mod.getSpacedName() : mod.getName(), getModTag(mod));
        String lowerCase = this.caseValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase, "lower")) {
            String lowerCase2 = displayName.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
            displayName = lowerCase2;
        } else if (Intrinsics.areEqual(lowerCase, "upper")) {
            String upperCase = displayName.toUpperCase();
            Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase()");
            displayName = upperCase;
        }
        return displayName;
    }
}
