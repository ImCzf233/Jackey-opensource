package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.Closeable;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.slf4j.Marker;

/* compiled from: TabGUI.kt */
@ElementInfo(name = "TabGUI")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018��2\u00020\u0001:\u0002DEB\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\n\u00109\u001a\u0004\u0018\u00010:H\u0016J\u0018\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020,H\u0016J\u0010\u0010@\u001a\u00020<2\u0006\u0010A\u001a\u00020BH\u0002J\b\u0010C\u001a\u00020<H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010-\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n��R\u0018\u00102\u001a\f\u0012\b\u0012\u000604R\u00020��03X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00105\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00106\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00107\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00108\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��¨\u0006F"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "arrowsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "backgroundAlphaValue", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "blueValue", "blurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRainbow", "Lnet/ccbluex/liquidbounce/value/ListValue;", "borderRedValue", "borderStrength", "borderValue", "cRainbowBrgValue", "cRainbowSatValue", "cRainbowSecValue", "categoryMenu", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "itemY", "", "lowerCaseValue", "oldRainbowBrightnessValue", "oldRainbowSaturationValue", "rainbowX", "rainbowY", "rectangleRainbow", "redValue", "selectedCategory", "", "selectedModule", "skyBrightnessValue", "skySaturationValue", "tabHeight", "tabY", "tabs", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab;", "textFade", "textPositionY", "textShadow", "width", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleKey", "", "c", "", "keyCode", "parseAction", "action", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action;", "updateAnimation", "Action", "Tab", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.TabGUI */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI.class */
public final class TabGUI extends Element {
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final FloatValue rainbowX;
    @NotNull
    private final FloatValue rainbowY;
    @NotNull
    private final IntegerValue redValue;
    @NotNull
    private final IntegerValue greenValue;
    @NotNull
    private final IntegerValue blueValue;
    @NotNull
    private final IntegerValue alphaValue;
    @NotNull
    private final ListValue rectangleRainbow;
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
    private final FloatValue borderStrength;
    @NotNull
    private final IntegerValue borderRedValue;
    @NotNull
    private final IntegerValue borderGreenValue;
    @NotNull
    private final IntegerValue borderBlueValue;
    @NotNull
    private final IntegerValue borderAlphaValue;
    @NotNull
    private final ListValue borderRainbow;
    @NotNull
    private final FloatValue skySaturationValue;
    @NotNull
    private final FloatValue skyBrightnessValue;
    @NotNull
    private final IntegerValue cRainbowSecValue;
    @NotNull
    private final FloatValue cRainbowSatValue;
    @NotNull
    private final FloatValue cRainbowBrgValue;
    @NotNull
    private final FloatValue oldRainbowSaturationValue;
    @NotNull
    private final FloatValue oldRainbowBrightnessValue;
    @NotNull
    private final BoolValue arrowsValue;
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final BoolValue textShadow;
    @NotNull
    private final BoolValue textFade;
    @NotNull
    private final FloatValue textPositionY;
    @NotNull
    private final FloatValue width;
    @NotNull
    private final FloatValue tabHeight;
    @NotNull
    private final BoolValue lowerCaseValue;
    @NotNull
    private final List<Tab> tabs;
    private boolean categoryMenu;
    private int selectedCategory;
    private int selectedModule;
    private float tabY;
    private float itemY;

    /* compiled from: TabGUI.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action;", "", "(Ljava/lang/String;I)V", "UP", "DOWN", "LEFT", "RIGHT", "TOGGLE", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.TabGUI$Action */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action.class */
    public enum Action {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        TOGGLE
    }

    /* compiled from: TabGUI.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.TabGUI$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Action.values().length];
            iArr[Action.UP.ordinal()] = 1;
            iArr[Action.DOWN.ordinal()] = 2;
            iArr[Action.LEFT.ordinal()] = 3;
            iArr[Action.RIGHT.ordinal()] = 4;
            iArr[Action.TOGGLE.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TabGUI() {
        this(0.0d, 0.0d, 3, null);
    }

    public TabGUI(double x, double y) {
        super(x, y, 0.0f, null, 12, null);
        this.blurValue = new BoolValue("Blur", true);
        this.blurStrength = new FloatValue("BlurStrength", 1.0f, 0.0f, 30.0f);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0f, -2000.0f, 2000.0f);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0f, -2000.0f, 2000.0f);
        this.redValue = new IntegerValue("Rectangle Red", 0, 0, 255);
        this.greenValue = new IntegerValue("Rectangle Green", 148, 0, 255);
        this.blueValue = new IntegerValue("Rectangle Blue", 255, 0, 255);
        this.alphaValue = new IntegerValue("Rectangle Alpha", 140, 0, 255);
        this.rectangleRainbow = new ListValue("Rectangle Rainbow", new String[]{"Off", "Normal", "CRainbow", "OldRainbow", "Sky", "Fade"}, "Off");
        this.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
        this.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
        this.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
        this.backgroundAlphaValue = new IntegerValue("Background Alpha", 150, 0, 255);
        this.borderValue = new BoolValue("Border", true);
        this.borderStrength = new FloatValue("Border Strength", 2.0f, 1.0f, 5.0f);
        this.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
        this.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
        this.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
        this.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
        this.borderRainbow = new ListValue("Border Rainbow", new String[]{"Off", "Normal", "CRainbow", "OldRainbow", "Sky", "Fade"}, "Off");
        this.skySaturationValue = new FloatValue("Sky-Saturation", 0.9f, 0.0f, 1.0f);
        this.skyBrightnessValue = new FloatValue("Sky-Brightness", 1.0f, 0.0f, 1.0f);
        this.cRainbowSecValue = new IntegerValue("CRainbow-Seconds", 2, 1, 10);
        this.cRainbowSatValue = new FloatValue("CRainbow-Saturation", 0.9f, 0.0f, 1.0f);
        this.cRainbowBrgValue = new FloatValue("CRainbow-Brightness", 1.0f, 0.0f, 1.0f);
        this.oldRainbowSaturationValue = new FloatValue("OldRainbow-Saturation", 0.9f, 0.0f, 1.0f);
        this.oldRainbowBrightnessValue = new FloatValue("OldRainbow-Brightness", 1.0f, 0.0f, 1.0f);
        this.arrowsValue = new BoolValue("Arrows", true);
        GameFontRenderer font35 = Fonts.font35;
        Intrinsics.checkNotNullExpressionValue(font35, "font35");
        this.fontValue = new FontValue("Font", font35);
        this.textShadow = new BoolValue("TextShadow", false);
        this.textFade = new BoolValue("TextFade", true);
        this.textPositionY = new FloatValue("TextPosition-Y", 2.0f, 0.0f, 5.0f);
        this.width = new FloatValue("Width", 60.0f, 55.0f, 100.0f);
        this.tabHeight = new FloatValue("TabHeight", 12.0f, 10.0f, 15.0f);
        this.lowerCaseValue = new BoolValue("LowerCase", false);
        this.tabs = new ArrayList();
        this.categoryMenu = true;
        ModuleCategory[] values = ModuleCategory.values();
        int i = 0;
        int length = values.length;
        while (i < length) {
            ModuleCategory category = values[i];
            i++;
            Tab tab = new Tab(this, category.getDisplayName());
            Iterable $this$filter$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Module module = (Module) element$iv$iv;
                if (category == module.getCategory()) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable $this$forEach$iv = (List) destination$iv$iv;
            for (Object element$iv : $this$forEach$iv) {
                Module e = (Module) element$iv;
                tab.getModules().add(e);
            }
            this.tabs.add(tab);
        }
    }

    public /* synthetic */ TabGUI(double d, double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 5.0d : d, (i & 2) != 0 ? 25.0d : d2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        Color color;
        int i;
        RainbowShader rainbowShader;
        String str;
        float f;
        float f2;
        int i2;
        updateAnimation();
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        FontRenderer fontRenderer = this.fontValue.get();
        boolean rectangleRainbowEnabled = StringsKt.equals(this.rectangleRainbow.get(), "normal", true);
        Color backgroundColor = new Color(this.backgroundRedValue.get().intValue(), this.backgroundGreenValue.get().intValue(), this.backgroundBlueValue.get().intValue(), this.backgroundAlphaValue.get().intValue());
        if (!StringsKt.equals(this.borderRainbow.get(), "Normal", true)) {
            color = new Color(this.borderRedValue.get().intValue(), this.borderGreenValue.get().intValue(), this.borderBlueValue.get().intValue(), this.borderAlphaValue.get().intValue());
        } else {
            color = Color.black;
        }
        Color borderColor = color;
        float guiHeight = this.tabs.size() * this.tabHeight.get().floatValue();
        if (this.blurValue.get().booleanValue()) {
            GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            BlurUtils.blurArea((((float) getRenderX()) * getScale()) + (1.0f * getScale()), ((float) getRenderY()) * getScale(), (((float) getRenderX()) * getScale()) + (this.width.get().floatValue() * getScale()), (((float) getRenderY()) * getScale()) + (guiHeight * getScale()), this.blurStrength.get().floatValue());
            GL11.glPopMatrix();
            GL11.glScalef(getScale(), getScale(), getScale());
            GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
        }
        RenderUtils.drawRect(1.0f, 0.0f, this.width.get().floatValue(), guiHeight, backgroundColor.getRGB());
        boolean rainbow = StringsKt.equals(this.borderRainbow.get(), "normal", true);
        if (this.borderValue.get().booleanValue()) {
            RainbowShader.Companion companion = RainbowShader.Companion;
            float x$iv = (this.rainbowX.get().floatValue() > 0.0f ? 1 : (this.rainbowX.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.rainbowX.get().floatValue();
            float y$iv = (this.rainbowY.get().floatValue() > 0.0f ? 1 : (this.rainbowY.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.rainbowY.get().floatValue();
            float offset$iv = ((float) (System.currentTimeMillis() % 10000)) / 10000.0f;
            RainbowShader instance$iv = RainbowShader.INSTANCE;
            if (rainbow) {
                instance$iv.setStrengthX(x$iv);
                instance$iv.setStrengthY(y$iv);
                instance$iv.setOffset(offset$iv);
                instance$iv.startShader();
            }
            rainbowShader = instance$iv;
            try {
                RainbowShader rainbowShader2 = rainbowShader;
                float floatValue = this.width.get().floatValue();
                float floatValue2 = this.borderStrength.get().floatValue();
                String str2 = this.borderRainbow.get();
                switch (str2.hashCode()) {
                    case -1955878649:
                        if (str2.equals("Normal")) {
                            i2 = 0;
                            break;
                        } else {
                            i2 = borderColor.getRGB();
                            break;
                        }
                    case -852561933:
                        if (str2.equals("CRainbow")) {
                            i2 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.cRainbowSatValue.get().floatValue(), this.cRainbowBrgValue.get().floatValue(), 0);
                            break;
                        } else {
                            i2 = borderColor.getRGB();
                            break;
                        }
                    case 83201:
                        if (str2.equals("Sky")) {
                            i2 = RenderUtils.SkyRainbow(0, this.skySaturationValue.get().floatValue(), this.skyBrightnessValue.get().floatValue());
                            break;
                        } else {
                            i2 = borderColor.getRGB();
                            break;
                        }
                    case 2181788:
                        if (str2.equals("Fade")) {
                            Intrinsics.checkNotNullExpressionValue(borderColor, "borderColor");
                            i2 = ColorUtils.fade(borderColor, 0, 100).getRGB();
                            break;
                        } else {
                            i2 = borderColor.getRGB();
                            break;
                        }
                    case 549352879:
                        if (str2.equals("OldRainbow")) {
                            i2 = RenderUtils.getNormalRainbow(0, this.oldRainbowSaturationValue.get().floatValue(), this.oldRainbowBrightnessValue.get().floatValue());
                            break;
                        } else {
                            i2 = borderColor.getRGB();
                            break;
                        }
                    default:
                        i2 = borderColor.getRGB();
                        break;
                }
                RenderUtils.drawBorder(1.0f, 0.0f, floatValue, guiHeight, floatValue2, i2);
                Unit unit = Unit.INSTANCE;
                Closeable.closeFinally(rainbowShader, null);
            } finally {
            }
        }
        String str3 = this.rectangleRainbow.get();
        switch (str3.hashCode()) {
            case -1955878649:
                if (str3.equals("Normal")) {
                    i = 0;
                    break;
                }
                i = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
                break;
            case -852561933:
                if (str3.equals("CRainbow")) {
                    i = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.cRainbowSatValue.get().floatValue(), this.cRainbowBrgValue.get().floatValue(), 0);
                    break;
                }
                i = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
                break;
            case 83201:
                if (str3.equals("Sky")) {
                    i = RenderUtils.SkyRainbow(0, this.skySaturationValue.get().floatValue(), this.skyBrightnessValue.get().floatValue());
                    break;
                }
                i = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
                break;
            case 2181788:
                if (str3.equals("Fade")) {
                    i = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()), 0, 100).getRGB();
                    break;
                }
                i = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
                break;
            case 549352879:
                if (str3.equals("OldRainbow")) {
                    i = RenderUtils.getNormalRainbow(0, this.oldRainbowSaturationValue.get().floatValue(), this.oldRainbowBrightnessValue.get().floatValue());
                    break;
                }
                i = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
                break;
            default:
                i = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()).getRGB();
                break;
        }
        int rectColor = i;
        RainbowShader.Companion companion2 = RainbowShader.Companion;
        float x$iv2 = (this.rainbowX.get().floatValue() > 0.0f ? 1 : (this.rainbowX.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.rainbowX.get().floatValue();
        float y$iv2 = (this.rainbowY.get().floatValue() > 0.0f ? 1 : (this.rainbowY.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.rainbowY.get().floatValue();
        float offset$iv2 = ((float) (System.currentTimeMillis() % 10000)) / 10000.0f;
        RainbowShader instance$iv2 = RainbowShader.INSTANCE;
        if (rectangleRainbowEnabled) {
            instance$iv2.setStrengthX(x$iv2);
            instance$iv2.setStrengthY(y$iv2);
            instance$iv2.setOffset(offset$iv2);
            instance$iv2.startShader();
        }
        rainbowShader = instance$iv2;
        try {
            RainbowShader rainbowShader3 = rainbowShader;
            RenderUtils.drawRect(1.0f, (1 + this.tabY) - 1, this.width.get().floatValue(), this.tabY + this.tabHeight.get().floatValue(), rectColor);
            Unit unit2 = Unit.INSTANCE;
            Closeable.closeFinally(rainbowShader, null);
            GlStateManager.func_179117_G();
            float y = 1.0f;
            Iterable $this$forEachIndexed$iv = this.tabs;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index = index$iv;
                index$iv = index + 1;
                if (index < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Tab tab = (Tab) item$iv;
                if (this.lowerCaseValue.get().booleanValue()) {
                    String lowerCase = tab.getTabName().toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                    str = lowerCase;
                } else {
                    str = tab.getTabName();
                }
                String tabName = str;
                if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                    f = ((this.width.get().floatValue() - fontRenderer.func_78256_a(tabName)) - tab.getTextFade()) - 3;
                } else {
                    f = tab.getTextFade() + 5;
                }
                float textX = f;
                float textY = y + this.textPositionY.get().floatValue();
                int textColor = this.selectedCategory == index ? 16777215 : new Color(210, 210, 210).getRGB();
                fontRenderer.func_175065_a(tabName, textX, textY, textColor, this.textShadow.get().booleanValue());
                if (this.arrowsValue.get().booleanValue()) {
                    if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                        fontRenderer.func_175065_a((this.categoryMenu || this.selectedCategory != index) ? "-" : Marker.ANY_NON_NULL_MARKER, 3.0f, y + 2.0f, 16777215, this.textShadow.get().booleanValue());
                    } else {
                        fontRenderer.func_175065_a((this.categoryMenu || this.selectedCategory != index) ? Marker.ANY_NON_NULL_MARKER : "-", this.width.get().floatValue() - 8.0f, y + 2.0f, 16777215, this.textShadow.get().booleanValue());
                    }
                }
                if (index == this.selectedCategory && !this.categoryMenu) {
                    if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                        f2 = 1.0f - tab.getMenuWidth();
                    } else {
                        f2 = this.width.get().floatValue() + 5;
                    }
                    float tabX = f2;
                    tab.drawTab(tabX, y, rectColor, backgroundColor.getRGB(), borderColor.getRGB(), this.borderStrength.get().floatValue(), this.lowerCaseValue.get().booleanValue(), fontRenderer, StringsKt.equals(this.borderRainbow.get(), "Normal", true), rectangleRainbowEnabled, this.blurValue.get().booleanValue(), this.blurStrength.get().floatValue(), getScale(), getRenderX(), getRenderY());
                }
                y += this.tabHeight.get().floatValue();
            }
            AWTFontRenderer.Companion.setAssumeNonVolatile(false);
            return new Border(1.0f, 0.0f, this.width.get().floatValue(), guiHeight);
        } finally {
            try {
            } finally {
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void handleKey(char c, int keyCode) {
        switch (keyCode) {
            case 28:
                parseAction(Action.TOGGLE);
                return;
            case 200:
                parseAction(Action.UP);
                return;
            case 203:
                parseAction(getSide().getHorizontal() == Side.Horizontal.RIGHT ? Action.RIGHT : Action.LEFT);
                return;
            case 205:
                parseAction(getSide().getHorizontal() == Side.Horizontal.RIGHT ? Action.LEFT : Action.RIGHT);
                return;
            case 208:
                parseAction(Action.DOWN);
                return;
            default:
                return;
        }
    }

    private final void updateAnimation() {
        int delta = RenderUtils.deltaTime;
        float xPos = this.tabHeight.get().floatValue() * this.selectedCategory;
        if (((int) this.tabY) != ((int) xPos)) {
            if (xPos > this.tabY) {
                this.tabY += 0.1f * delta;
            } else {
                this.tabY -= 0.1f * delta;
            }
        } else {
            this.tabY = xPos;
        }
        float xPos2 = this.tabHeight.get().floatValue() * this.selectedModule;
        if (((int) this.itemY) != ((int) xPos2)) {
            if (xPos2 > this.itemY) {
                this.itemY += 0.5f * delta;
            } else {
                this.itemY -= 0.5f * delta;
            }
        } else {
            this.itemY = xPos2;
        }
        if (this.categoryMenu) {
            this.itemY = 0.0f;
        }
        if (this.textFade.get().booleanValue()) {
            Iterable $this$forEachIndexed$iv = this.tabs;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index = index$iv;
                index$iv = index + 1;
                if (index < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Tab tab = (Tab) item$iv;
                if (index == this.selectedCategory) {
                    if (tab.getTextFade() < 4.0f) {
                        tab.setTextFade(tab.getTextFade() + (0.1f * delta));
                    }
                    if (tab.getTextFade() > 4.0f) {
                        tab.setTextFade(4.0f);
                    }
                } else {
                    if (tab.getTextFade() > 0.0f) {
                        tab.setTextFade(tab.getTextFade() - (0.1f * delta));
                    }
                    if (tab.getTextFade() < 0.0f) {
                        tab.setTextFade(0.0f);
                    }
                }
            }
            return;
        }
        for (Tab tab2 : this.tabs) {
            if (tab2.getTextFade() > 0.0f) {
                tab2.setTextFade(tab2.getTextFade() - (0.1f * delta));
            }
            if (tab2.getTextFade() < 0.0f) {
                tab2.setTextFade(0.0f);
            }
        }
    }

    private final void parseAction(Action action) {
        boolean toggle = false;
        switch (WhenMappings.$EnumSwitchMapping$0[action.ordinal()]) {
            case 1:
                if (this.categoryMenu) {
                    this.selectedCategory--;
                    int i = this.selectedCategory;
                    if (this.selectedCategory < 0) {
                        this.selectedCategory = this.tabs.size() - 1;
                        this.tabY = this.tabHeight.get().floatValue() * this.selectedCategory;
                        break;
                    }
                } else {
                    this.selectedModule--;
                    int i2 = this.selectedModule;
                    if (this.selectedModule < 0) {
                        this.selectedModule = this.tabs.get(this.selectedCategory).getModules().size() - 1;
                        this.itemY = this.tabHeight.get().floatValue() * this.selectedModule;
                        break;
                    }
                }
                break;
            case 2:
                if (this.categoryMenu) {
                    this.selectedCategory++;
                    int i3 = this.selectedCategory;
                    if (this.selectedCategory > this.tabs.size() - 1) {
                        this.selectedCategory = 0;
                        this.tabY = this.tabHeight.get().floatValue() * this.selectedCategory;
                        break;
                    }
                } else {
                    this.selectedModule++;
                    int i4 = this.selectedModule;
                    if (this.selectedModule > this.tabs.get(this.selectedCategory).getModules().size() - 1) {
                        this.selectedModule = 0;
                        this.itemY = this.tabHeight.get().floatValue() * this.selectedModule;
                        break;
                    }
                }
                break;
            case 3:
                if (!this.categoryMenu) {
                    this.categoryMenu = true;
                    break;
                }
                break;
            case 4:
                if (!this.categoryMenu) {
                    toggle = true;
                    break;
                } else {
                    this.categoryMenu = false;
                    this.selectedModule = 0;
                    break;
                }
            case 5:
                if (!this.categoryMenu) {
                    toggle = true;
                    break;
                }
                break;
        }
        if (toggle) {
            int sel = this.selectedModule;
            this.tabs.get(this.selectedCategory).getModules().get(sel).toggle();
        }
    }

    /* compiled from: TabGUI.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��L\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0082\u0004\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J~\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!2\u0006\u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006,"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab;", "", "tabName", "", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI;Ljava/lang/String;)V", "menuWidth", "", "getMenuWidth", "()I", "setMenuWidth", "(I)V", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "getModules", "()Ljava/util/List;", "getTabName", "()Ljava/lang/String;", "textFade", "", "getTextFade", "()F", "setTextFade", "(F)V", "drawTab", "", "x", "y", "color", "backgroundColor", "borderColor", "borderStrength", "lowerCase", "", "fontRenderer", "Lnet/minecraft/client/gui/FontRenderer;", "borderRainbow", "rectRainbow", "blur", "blurStrength", "scale", "renderX", "", "renderY", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.TabGUI$Tab */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab.class */
    public final class Tab {
        @NotNull
        private final String tabName;
        @NotNull
        private final List<Module> modules = new ArrayList();
        private int menuWidth;
        private float textFade;
        final /* synthetic */ TabGUI this$0;

        public Tab(@NotNull TabGUI this$0, String tabName) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(tabName, "tabName");
            this.this$0 = this$0;
            this.tabName = tabName;
        }

        @NotNull
        public final String getTabName() {
            return this.tabName;
        }

        @NotNull
        public final List<Module> getModules() {
            return this.modules;
        }

        public final int getMenuWidth() {
            return this.menuWidth;
        }

        public final void setMenuWidth(int i) {
            this.menuWidth = i;
        }

        public final float getTextFade() {
            return this.textFade;
        }

        public final void setTextFade(float f) {
            this.textFade = f;
        }

        public final void drawTab(float x, float y, int color, int backgroundColor, int borderColor, float borderStrength, boolean lowerCase, @NotNull FontRenderer fontRenderer, boolean borderRainbow, boolean rectRainbow, boolean blur, float blurStrength, float scale, double renderX, double renderY) {
            RainbowShader rainbowShader;
            String str;
            String str2;
            String str3;
            Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
            int maxWidth = 0;
            for (Module module : this.modules) {
                if (lowerCase) {
                    String lowerCase2 = module.getName().toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                    str2 = lowerCase2;
                } else {
                    str2 = module.getName();
                }
                if (fontRenderer.func_78256_a(str2) + 4 > maxWidth) {
                    if (lowerCase) {
                        String lowerCase3 = module.getName().toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
                        str3 = lowerCase3;
                    } else {
                        str3 = module.getName();
                    }
                    maxWidth = (int) (fontRenderer.func_78256_a(str3) + 7.0f);
                }
            }
            this.menuWidth = maxWidth;
            float menuHeight = this.modules.size() * this.this$0.tabHeight.get().floatValue();
            if (this.this$0.borderValue.get().booleanValue()) {
                RainbowShader.Companion companion = RainbowShader.Companion;
                float x$iv = (this.this$0.rainbowX.get().floatValue() > 0.0f ? 1 : (this.this$0.rainbowX.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.this$0.rainbowX.get().floatValue();
                float y$iv = (this.this$0.rainbowY.get().floatValue() > 0.0f ? 1 : (this.this$0.rainbowY.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.this$0.rainbowY.get().floatValue();
                float offset$iv = ((float) (System.currentTimeMillis() % 10000)) / 10000.0f;
                RainbowShader instance$iv = RainbowShader.INSTANCE;
                if (borderRainbow) {
                    instance$iv.setStrengthX(x$iv);
                    instance$iv.setStrengthY(y$iv);
                    instance$iv.setOffset(offset$iv);
                    instance$iv.startShader();
                }
                rainbowShader = instance$iv;
                try {
                    RainbowShader rainbowShader2 = rainbowShader;
                    RenderUtils.drawBorder(x - 1.0f, y - 1.0f, (x + getMenuWidth()) - 2.0f, (y + menuHeight) - 1.0f, borderStrength, borderColor);
                    Unit unit = Unit.INSTANCE;
                    Closeable.closeFinally(rainbowShader, null);
                } finally {
                }
            }
            if (blur) {
                GL11.glTranslated(-renderX, -renderY, 0.0d);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glPushMatrix();
                BlurUtils.blurArea(((((float) renderX) + x) - 1.0f) * scale, ((((float) renderY) + y) - 1.0f) * scale, (((((float) renderX) + x) + this.menuWidth) - 2.0f) * scale, (((((float) renderY) + y) + menuHeight) - 1.0f) * scale, blurStrength);
                GL11.glPopMatrix();
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslated(renderX, renderY, 0.0d);
            }
            RenderUtils.drawRect(x - 1.0f, y - 1.0f, (x + this.menuWidth) - 2.0f, (y + menuHeight) - 1.0f, backgroundColor);
            RainbowShader.Companion companion2 = RainbowShader.Companion;
            float x$iv2 = (this.this$0.rainbowX.get().floatValue() > 0.0f ? 1 : (this.this$0.rainbowX.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.this$0.rainbowX.get().floatValue();
            float y$iv2 = (this.this$0.rainbowY.get().floatValue() > 0.0f ? 1 : (this.this$0.rainbowY.get().floatValue() == 0.0f ? 0 : -1)) == 0 ? 0.0f : 1.0f / this.this$0.rainbowY.get().floatValue();
            float offset$iv2 = ((float) (System.currentTimeMillis() % 10000)) / 10000.0f;
            RainbowShader instance$iv2 = RainbowShader.INSTANCE;
            if (rectRainbow) {
                instance$iv2.setStrengthX(x$iv2);
                instance$iv2.setStrengthY(y$iv2);
                instance$iv2.setOffset(offset$iv2);
                instance$iv2.startShader();
            }
            rainbowShader = instance$iv2;
            TabGUI tabGUI = this.this$0;
            try {
                RainbowShader rainbowShader3 = rainbowShader;
                RenderUtils.drawRect(x - 1.0f, (y + tabGUI.itemY) - 1, (x + getMenuWidth()) - 2.0f, ((y + tabGUI.itemY) + tabGUI.tabHeight.get().floatValue()) - 1, color);
                Unit unit2 = Unit.INSTANCE;
                Closeable.closeFinally(rainbowShader, null);
                GlStateManager.func_179117_G();
                Iterable $this$forEachIndexed$iv = this.modules;
                TabGUI tabGUI2 = this.this$0;
                int index$iv = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    int index = index$iv;
                    index$iv = index + 1;
                    if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Module module2 = (Module) item$iv;
                    int moduleColor = module2.getState() ? 16777215 : new Color(205, 205, 205).getRGB();
                    if (lowerCase) {
                        String lowerCase4 = module2.getName().toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase4, "this as java.lang.String).toLowerCase()");
                        str = lowerCase4;
                    } else {
                        str = module2.getName();
                    }
                    fontRenderer.func_175065_a(str, x + 2.0f, y + (tabGUI2.tabHeight.get().floatValue() * index) + tabGUI2.textPositionY.get().floatValue(), moduleColor, tabGUI2.textShadow.get().booleanValue());
                }
            } finally {
                try {
                } finally {
                }
            }
        }
    }
}
