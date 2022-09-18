package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.ShadowUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: ScoreboardElement.kt */
@ElementInfo(name = "Scoreboard")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010=\u001a\u00020>H\u0002J\n\u0010?\u001a\u0004\u0018\u00010@H\u0016J\b\u0010A\u001a\u00020BH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u001e\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\u00190\u0018j\b\u0012\u0004\u0012\u00020\u0019`\u001aX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190!X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\"R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010,\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010-\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00102\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00104\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00105\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00106\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00107\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00108\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00109\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010:\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010;\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010<\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��¨\u0006C"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "antiSnipeMatch", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "bgRoundedValue", "blurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "brightnessValue", "cRainbowSecValue", "cachedDomains", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "changeDomain", "delayValue", "domainFontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "domainFontYValue", "domainList", "", "[Ljava/lang/String;", "domainShadowValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "fontValue", "garbageTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "hypickleRegex", "Lkotlin/text/Regex;", "outlineWidthValue", "rectColorBlueAlpha", "rectColorBlueValue", "rectColorGreenValue", "rectColorModeValue", "rectColorRedValue", "rectHeight", "rectValue", "roundStrength", "saturationValue", "shadowColorBlueValue", "shadowColorGreenValue", "shadowColorMode", "shadowColorRedValue", "shadowShaderValue", "shadowStrength", "shadowValue", "showRedNumbersValue", "useVanillaBackground", "backgroundColor", "Ljava/awt/Color;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "updateElement", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.ScoreboardElement */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement.class */
public final class ScoreboardElement extends Element {
    @NotNull
    private final BoolValue useVanillaBackground;
    @NotNull
    private final IntegerValue backgroundColorRedValue;
    @NotNull
    private final IntegerValue backgroundColorGreenValue;
    @NotNull
    private final IntegerValue backgroundColorBlueValue;
    @NotNull
    private final IntegerValue backgroundColorAlphaValue;
    @NotNull
    private final BoolValue rectValue;
    @NotNull
    private final IntegerValue rectHeight;
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final BoolValue shadowShaderValue;
    @NotNull
    private final FloatValue shadowStrength;
    @NotNull
    private final ListValue shadowColorMode;
    @NotNull
    private final IntegerValue shadowColorRedValue;
    @NotNull
    private final IntegerValue shadowColorGreenValue;
    @NotNull
    private final IntegerValue shadowColorBlueValue;
    @NotNull
    private final BoolValue bgRoundedValue;
    @NotNull
    private final FloatValue roundStrength;
    @NotNull
    private final ListValue rectColorModeValue;
    @NotNull
    private final IntegerValue rectColorRedValue;
    @NotNull
    private final IntegerValue rectColorGreenValue;
    @NotNull
    private final IntegerValue rectColorBlueValue;
    @NotNull
    private final IntegerValue rectColorBlueAlpha;
    @NotNull
    private final FloatValue saturationValue;
    @NotNull
    private final FloatValue brightnessValue;
    @NotNull
    private final IntegerValue cRainbowSecValue;
    @NotNull
    private final IntegerValue delayValue;
    @NotNull
    private final BoolValue shadowValue;
    @NotNull
    private final BoolValue antiSnipeMatch;
    @NotNull
    private final BoolValue changeDomain;
    @NotNull
    private final BoolValue showRedNumbersValue;
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final FontValue domainFontValue;
    @NotNull
    private final FloatValue domainFontYValue;
    @NotNull
    private final ListValue domainShadowValue;
    @NotNull
    private final FloatValue outlineWidthValue;
    @NotNull
    private final String[] domainList;
    @NotNull
    private final ArrayList<String> cachedDomains;
    @NotNull
    private final MSTimer garbageTimer;
    @NotNull
    private final Regex hypickleRegex;

    public ScoreboardElement() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ ScoreboardElement(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 5.0d : d, (i & 2) != 0 ? 0.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.MIDDLE) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScoreboardElement(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.useVanillaBackground = new BoolValue("UseVanillaBackground", false);
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255, new ScoreboardElement$backgroundColorRedValue$1(this));
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255, new ScoreboardElement$backgroundColorGreenValue$1(this));
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255, new ScoreboardElement$backgroundColorBlueValue$1(this));
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 95, 0, 255, new ScoreboardElement$backgroundColorAlphaValue$1(this));
        this.rectValue = new BoolValue("Rect", false);
        this.rectHeight = new IntegerValue("Rect-Height", 1, 1, 10, new ScoreboardElement$rectHeight$1(this));
        this.blurValue = new BoolValue("Blur", false);
        this.blurStrength = new FloatValue("Blur-Strength", 0.0f, 0.0f, 30.0f, new ScoreboardElement$blurStrength$1(this));
        this.shadowShaderValue = new BoolValue("Shadow", false);
        this.shadowStrength = new FloatValue("Shadow-Strength", 0.0f, 0.0f, 30.0f, new ScoreboardElement$shadowStrength$1(this));
        this.shadowColorMode = new ListValue("Shadow-Color", new String[]{"Background", "Custom"}, "Background", new ScoreboardElement$shadowColorMode$1(this));
        this.shadowColorRedValue = new IntegerValue("Shadow-Red", 0, 0, 255, new ScoreboardElement$shadowColorRedValue$1(this));
        this.shadowColorGreenValue = new IntegerValue("Shadow-Green", 111, 0, 255, new ScoreboardElement$shadowColorGreenValue$1(this));
        this.shadowColorBlueValue = new IntegerValue("Shadow-Blue", 255, 0, 255, new ScoreboardElement$shadowColorBlueValue$1(this));
        this.bgRoundedValue = new BoolValue("Rounded", false);
        this.roundStrength = new FloatValue("Rounded-Strength", 5.0f, 0.0f, 30.0f, new ScoreboardElement$roundStrength$1(this));
        this.rectColorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "LiquidSlowly", "Fade", "Sky", "Mixer"}, "Custom");
        this.rectColorRedValue = new IntegerValue("Red", 0, 0, 255);
        this.rectColorGreenValue = new IntegerValue("Green", 111, 0, 255);
        this.rectColorBlueValue = new IntegerValue("Blue", 255, 0, 255);
        this.rectColorBlueAlpha = new IntegerValue("Alpha", 255, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 0.9f, 0.0f, 1.0f);
        this.brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
        this.cRainbowSecValue = new IntegerValue("Seconds", 2, 1, 10);
        this.delayValue = new IntegerValue("Delay", 50, 0, 200);
        this.shadowValue = new BoolValue("Shadow", false);
        this.antiSnipeMatch = new BoolValue("AntiSnipeMatch", true);
        this.changeDomain = new BoolValue("ChangeDomain", false);
        this.showRedNumbersValue = new BoolValue("ShowRedNumbers", false);
        FontRenderer minecraftFont = Fonts.minecraftFont;
        Intrinsics.checkNotNullExpressionValue(minecraftFont, "minecraftFont");
        this.fontValue = new FontValue("Font", minecraftFont);
        FontRenderer minecraftFont2 = Fonts.minecraftFont;
        Intrinsics.checkNotNullExpressionValue(minecraftFont2, "minecraftFont");
        this.domainFontValue = new FontValue("DomainFont", minecraftFont2);
        this.domainFontYValue = new FloatValue("Domain-TextY", 0.0f, 0.0f, 3.0f);
        this.domainShadowValue = new ListValue("Domain-Shadow", new String[]{"None", "Outline", "Default"}, "None");
        this.outlineWidthValue = new FloatValue("OutlineWidth", 0.5f, 0.5f, 2.0f);
        this.domainList = new String[]{".ac", ".academy", ".accountant", ".accountants", ".actor", ".adult", ".ag", ".agency", ".ai", ".airforce", ".am", ".amsterdam", ".apartments", ".app", ".archi", ".army", ".art", ".asia", ".associates", ".at", ".attorney", ".au", ".auction", ".auto", ".autos", ".baby", ".band", ".bar", ".barcelona", ".bargains", ".bayern", ".be", ".beauty", ".beer", ".berlin", ".best", ".bet", ".bid", ".bike", ".bingo", ".bio", ".biz", ".biz.pl", ".black", ".blog", ".blue", ".boats", ".boston", ".boutique", ".build", ".builders", ".business", ".buzz", ".bz", ".ca", ".cab", ".cafe", ".camera", ".camp", ".capital", ".car", ".cards", ".care", ".careers", ".cars", ".casa", ".cash", ".casino", ".catering", ".cc", ".center", ".ceo", ".ch", ".charity", ".chat", ".cheap", ".church", ".city", ".cl", ".claims", ".cleaning", ".clinic", ".clothing", ".cloud", ".club", ".cn", ".co", ".co.in", ".co.jp", ".co.kr", ".co.nz", ".co.uk", ".co.za", ".coach", ".codes", ".coffee", ".college", ".com", ".com.ag", ".com.au", ".com.br", ".com.bz", ".com.cn", ".com.co", ".com.es", ".com.mx", ".com.pe", ".com.ph", ".com.pl", ".com.ru", ".com.tw", ".community", ".company", ".computer", ".condos", ".construction", ".consulting", ".contact", ".contractors", ".cooking", ".cool", ".country", ".coupons", ".courses", ".credit", ".creditcard", ".cricket", ".cruises", ".cymru", ".cz", ".dance", ".date", ".dating", ".de", ".deals", ".degree", ".delivery", ".democrat", ".dental", ".dentist", ".design", ".dev", ".diamonds", ".digital", ".direct", ".directory", ".discount", ".dk", ".doctor", ".dog", ".domains", ".download", ".earth", ".education", ".email", ".energy", ".engineer", ".engineering", ".enterprises", ".equipment", ".es", ".estate", ".eu", ".events", ".exchange", ".expert", ".exposed", ".express", ".fail", ".faith", ".family", ".fan", ".fans", ".farm", ".fashion", ".film", ".finance", ".financial", ".firm.in", ".fish", ".fishing", ".fit", ".fitness", ".flights", ".florist", ".fm", ".football", ".forsale", ".foundation", ".fr", ".fun", ".fund", ".furniture", ".futbol", ".fyi", ".gallery", ".games", ".garden", ".gay", ".gen.in", ".gg", ".gifts", ".gives", ".glass", ".global", ".gmbh", ".gold", ".golf", ".graphics", ".gratis", ".green", ".gripe", ".group", ".gs", ".guide", ".guru", ".hair", ".haus", ".health", ".healthcare", ".hockey", ".holdings", ".holiday", ".homes", ".horse", ".hospital", ".host", ".house", ".idv.tw", ".immo", ".immobilien", ".in", ".inc", ".ind.in", ".industries", ".info", ".info.pl", ".ink", ".institute", ".insure", ".international", ".investments", ".io", ".irish", ".ist", ".istanbul", ".it", ".jetzt", ".jewelry", ".jobs", ".jp", ".kaufen", ".kim", ".kitchen", ".kiwi", ".kr", ".la", ".land", ".law", ".lawyer", ".lease", ".legal", ".lgbt", ".life", ".lighting", ".limited", ".limo", ".live", ".llc", ".loan", ".loans", ".london", ".love", ".ltd", ".ltda", ".luxury", ".maison", ".makeup", ".management", ".market", ".marketing", ".mba", ".me", ".me.uk", ".media", ".melbourne", ".memorial", ".men", ".menu", ".miami", ".mobi", ".moda", ".moe", ".money", ".monster", ".mortgage", ".motorcycles", ".movie", ".ms", ".mx", ".nagoya", ".name", ".navy", ".ne", ".ne.kr", ".net", ".net.ag", ".net.au", ".net.br", ".net.bz", ".net.cn", ".net.co", ".net.in", ".net.nz", ".net.pe", ".net.ph", ".net.pl", ".net.ru", ".network", ".news", ".ninja", ".nl", ".no", ".nom.co", ".nom.es", ".nom.pe", ".nrw", ".nyc", ".okinawa", ".one", ".onl", ".online", ".org", ".org.ag", ".org.au", ".org.cn", ".org.es", ".org.in", ".org.nz", ".org.pe", ".org.ph", ".org.pl", ".org.ru", ".org.uk", ".page", ".paris", ".partners", ".parts", ".party", ".pe", ".pet", ".ph", ".photography", ".photos", ".pictures", ".pink", ".pizza", ".pl", ".place", ".plumbing", ".plus", ".poker", ".porn", ".press", ".pro", ".productions", ".promo", ".properties", ".protection", ".pub", ".pw", ".quebec", ".quest", ".racing", ".re.kr", ".realestate", ".recipes", ".red", ".rehab", ".reise", ".reisen", ".rent", ".rentals", ".repair", ".report", ".republican", ".rest", ".restaurant", ".review", ".reviews", ".rich", ".rip", ".rocks", ".rodeo", ".ru", ".run", ".ryukyu", ".sale", ".salon", ".sarl", ".school", ".schule", ".science", ".se", ".security", ".services", ".sex", ".sg", ".sh", ".shiksha", ".shoes", ".shop", ".shopping", ".show", ".singles", ".site", ".ski", ".skin", ".soccer", ".social", ".software", ".solar", ".solutions", ".space", ".storage", ".store", ".stream", ".studio", ".study", ".style", ".supplies", ".supply", ".support", ".surf", ".surgery", ".sydney", ".systems", ".tax", ".taxi", ".team", ".tech", ".technology", ".tel", ".tennis", ".theater", ".theatre", ".tienda", ".tips", ".tires", ".today", ".tokyo", ".tools", ".tours", ".town", ".toys", ".top", ".trade", ".training", ".travel", ".tube", ".tv", ".tw", ".uk", ".university", ".uno", ".us", ".vacations", ".vegas", ".ventures", ".vet", ".viajes", ".video", ".villas", ".vin", ".vip", ".vision", ".vodka", ".vote", ".voto", ".voyage", ".wales", ".watch", ".webcam", ".website", ".wedding", ".wiki", ".win", ".wine", ".work", ".works", ".world", ".ws", ".wtf", ".xxx", ".xyz", ".yachts", ".yoga", ".yokohama", ".zone", ".vn"};
        this.cachedDomains = new ArrayList<>();
        this.garbageTimer = new MSTimer();
        this.hypickleRegex = new Regex("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]");
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void updateElement() {
        if (this.garbageTimer.hasTimePassed(30000L) || this.cachedDomains.size() > 50) {
            this.cachedDomains.clear();
            this.garbageTimer.reset();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        ArrayList arrayList;
        int i;
        int i2;
        int z;
        int i3;
        int i4;
        int colorIndex;
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(AntiBlind.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind");
        }
        AntiBlind antiBlind = (AntiBlind) module;
        if (antiBlind.getState() && antiBlind.getScoreBoard().get().booleanValue()) {
            return null;
        }
        FontRenderer fontRenderer = this.fontValue.get();
        int backColor = backgroundColor().getRGB();
        String rectColorMode = this.rectColorModeValue.get();
        int rectCustomColor = new Color(this.rectColorRedValue.get().intValue(), this.rectColorGreenValue.get().intValue(), this.rectColorBlueValue.get().intValue(), this.rectColorBlueAlpha.get().intValue()).getRGB();
        Scoreboard worldScoreboard = MinecraftInstance.f362mc.field_71441_e.func_96441_U();
        Intrinsics.checkNotNullExpressionValue(worldScoreboard, "mc.theWorld.scoreboard");
        ScoreObjective currObjective = null;
        ScorePlayerTeam playerTeam = worldScoreboard.func_96509_i(MinecraftInstance.f362mc.field_71439_g.func_70005_c_());
        if (playerTeam != null && (colorIndex = playerTeam.func_178775_l().func_175746_b()) >= 0) {
            currObjective = worldScoreboard.func_96539_a(3 + colorIndex);
        }
        ScoreObjective scoreObjective = currObjective;
        if (scoreObjective == null) {
            scoreObjective = worldScoreboard.func_96539_a(1);
        }
        if (scoreObjective == null) {
            return null;
        }
        ScoreObjective objective = scoreObjective;
        Scoreboard scoreboard = objective.func_96682_a();
        Intrinsics.checkNotNullExpressionValue(scoreboard, "objective.scoreboard");
        Collection func_96534_i = scoreboard.func_96534_i(objective);
        ArrayList scores = Lists.newArrayList(Iterables.filter(func_96534_i, ScoreboardElement::m2858drawElement$lambda0));
        if (scores.size() > 15) {
            arrayList = Lists.newArrayList(Iterables.skip(scores, func_96534_i.size() - 15));
        } else {
            arrayList = scores;
        }
        ArrayList<Score> scoreCollection = arrayList;
        int maxWidth = fontRenderer.func_78256_a(objective.func_96678_d());
        Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(HUD.class);
        if (module2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
        }
        HUD hud = (HUD) module2;
        for (Score score : scoreCollection) {
            String name = ScorePlayerTeam.func_96667_a(scoreboard.func_96509_i(score.func_96653_e()), score.func_96653_e());
            String stripColor = ColorUtils.stripColor(name);
            Intrinsics.checkNotNull(stripColor);
            String stripped = StringUtils.fixString(stripColor);
            if (this.changeDomain.get().booleanValue()) {
                if (this.cachedDomains.contains(stripped)) {
                    name = hud.getDomainValue().get();
                } else if (ServerUtils.isHypixelDomain(stripped)) {
                    name = hud.getDomainValue().get();
                    this.cachedDomains.add(stripped);
                } else {
                    String[] strArr = this.domainList;
                    int i5 = 0;
                    int length = strArr.length;
                    while (true) {
                        if (i5 < length) {
                            String domain = strArr[i5];
                            i5++;
                            Intrinsics.checkNotNullExpressionValue(stripped, "stripped");
                            if (StringsKt.contains((CharSequence) stripped, (CharSequence) domain, true)) {
                                name = hud.getDomainValue().get();
                                this.cachedDomains.add(stripped);
                                break;
                            }
                        }
                    }
                }
            }
            String width = ((Object) name) + ": " + EnumChatFormatting.RED + score.func_96652_c();
            maxWidth = RangesKt.coerceAtLeast(maxWidth, fontRenderer.func_78256_a(width));
        }
        int maxHeight = scoreCollection.size() * fontRenderer.field_78288_b;
        int l1 = getSide().getHorizontal() == Side.Horizontal.LEFT ? maxWidth + 3 : (-maxWidth) - 3;
        int FadeColor = ColorUtils.fade(new Color(this.rectColorRedValue.get().intValue(), this.rectColorGreenValue.get().intValue(), this.rectColorBlueValue.get().intValue(), this.rectColorBlueAlpha.get().intValue()), 0, 100).getRGB();
        Color LiquidSlowly = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
        Integer LiquidSlowly2 = LiquidSlowly == null ? null : Integer.valueOf(LiquidSlowly.getRGB());
        Intrinsics.checkNotNull(LiquidSlowly2);
        int liquidSlowli = LiquidSlowly2.intValue();
        int mixerColor = ColorMixer.getMixedColor(0, this.cRainbowSecValue.get().intValue()).getRGB();
        if (scoreCollection.size() > 0) {
            if (this.shadowShaderValue.get().booleanValue()) {
                GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glPushMatrix();
                ShadowUtils.INSTANCE.shadow(this.shadowStrength.get().floatValue(), new ScoreboardElement$drawElement$1(this, l1, maxHeight, fontRenderer), new ScoreboardElement$drawElement$2(this, l1, maxHeight, fontRenderer));
                GL11.glPopMatrix();
                GL11.glScalef(getScale(), getScale(), getScale());
                GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
            }
            if (this.blurValue.get().booleanValue()) {
                GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glPushMatrix();
                if (this.bgRoundedValue.get().booleanValue()) {
                    if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                        BlurUtils.blurAreaRounded(((float) getRenderX()) + ((l1 + 2.0f) * getScale()), ((float) getRenderY()) + ((-2.0f) * getScale()), ((float) getRenderX()) + ((-5.0f) * getScale()), ((float) getRenderY()) + ((maxHeight + fontRenderer.field_78288_b) * getScale()), this.roundStrength.get().floatValue(), this.blurStrength.get().floatValue());
                    } else {
                        BlurUtils.blurAreaRounded(((float) getRenderX()) + ((l1 - 2.0f) * getScale()), ((float) getRenderY()) + ((-2.0f) * getScale()), ((float) getRenderX()) + (5.0f * getScale()), ((float) getRenderY()) + ((maxHeight + fontRenderer.field_78288_b) * getScale()), this.roundStrength.get().floatValue(), this.blurStrength.get().floatValue());
                    }
                } else if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                    BlurUtils.blurArea(((float) getRenderX()) + ((l1 + 2.0f) * getScale()), ((float) getRenderY()) + ((-2.0f) * getScale()), ((float) getRenderX()) + ((-5.0f) * getScale()), ((float) getRenderY()) + ((maxHeight + fontRenderer.field_78288_b) * getScale()), this.blurStrength.get().floatValue());
                } else {
                    BlurUtils.blurArea(((float) getRenderX()) + ((l1 - 2.0f) * getScale()), ((float) getRenderY()) + ((-2.0f) * getScale()), ((float) getRenderX()) + (5.0f * getScale()), ((float) getRenderY()) + ((maxHeight + fontRenderer.field_78288_b) * getScale()), this.blurStrength.get().floatValue());
                }
                GL11.glPopMatrix();
                GL11.glScalef(getScale(), getScale(), getScale());
                GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
            }
            if (this.bgRoundedValue.get().booleanValue()) {
                Stencil.write(false);
                GlStateManager.func_179147_l();
                GlStateManager.func_179090_x();
                GlStateManager.func_179120_a(770, 771, 1, 0);
                RenderUtils.fastRoundedRect(l1 + (getSide().getHorizontal() == Side.Horizontal.LEFT ? 2.0f : -2.0f), this.rectValue.get().booleanValue() ? (-2.0f) - this.rectHeight.get().intValue() : -2.0f, getSide().getHorizontal() == Side.Horizontal.LEFT ? -5.0f : 5.0f, maxHeight + fontRenderer.field_78288_b, this.roundStrength.get().floatValue());
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
                Stencil.erase(true);
            }
            if (this.useVanillaBackground.get().booleanValue()) {
                if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                    Gui.func_73734_a(l1 + 2, -2, -5, (-2) + fontRenderer.field_78288_b + 1, 1610612736);
                    Gui.func_73734_a(l1 + 2, (-2) + fontRenderer.field_78288_b + 1, -5, maxHeight + fontRenderer.field_78288_b, 1342177280);
                } else {
                    Gui.func_73734_a(l1 - 2, -2, 5, (-2) + fontRenderer.field_78288_b + 1, 1610612736);
                    Gui.func_73734_a(l1 - 2, (-2) + fontRenderer.field_78288_b + 1, 5, maxHeight + fontRenderer.field_78288_b, 1342177280);
                }
            } else if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                Gui.func_73734_a(l1 + 2, -2, -5, maxHeight + fontRenderer.field_78288_b, backColor);
            } else {
                Gui.func_73734_a(l1 - 2, -2, 5, maxHeight + fontRenderer.field_78288_b, backColor);
            }
            if (this.rectValue.get().booleanValue()) {
                String lowerCase = rectColorMode.toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -132200566:
                        if (lowerCase.equals("liquidslowly")) {
                            i4 = liquidSlowli;
                            break;
                        }
                        i4 = rectCustomColor;
                        break;
                    case 113953:
                        if (lowerCase.equals("sky")) {
                            i4 = RenderUtils.SkyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                            break;
                        }
                        i4 = rectCustomColor;
                        break;
                    case 3135100:
                        if (lowerCase.equals("fade")) {
                            i4 = FadeColor;
                            break;
                        }
                        i4 = rectCustomColor;
                        break;
                    case 103910409:
                        if (lowerCase.equals("mixer")) {
                            i4 = mixerColor;
                            break;
                        }
                        i4 = rectCustomColor;
                        break;
                    case 973576630:
                        if (lowerCase.equals("rainbow")) {
                            i4 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0);
                            break;
                        }
                        i4 = rectCustomColor;
                        break;
                    default:
                        i4 = rectCustomColor;
                        break;
                }
                int rectColor = i4;
                if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                    Gui.func_73734_a(l1 + 2, -2, -5, (-2) - this.rectHeight.get().intValue(), rectColor);
                } else {
                    Gui.func_73734_a(l1 - 2, -2, 5, (-2) - this.rectHeight.get().intValue(), rectColor);
                }
            }
            if (this.bgRoundedValue.get().booleanValue()) {
                Stencil.dispose();
            }
        }
        Intrinsics.checkNotNullExpressionValue(scoreCollection, "scoreCollection");
        ArrayList $this$forEachIndexed$iv = scoreCollection;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index = index$iv;
            index$iv = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Score score2 = (Score) item$iv;
            String name2 = ScorePlayerTeam.func_96667_a(scoreboard.func_96509_i(score2.func_96653_e()), score2.func_96653_e());
            String scorePoints = new StringBuilder().append(EnumChatFormatting.RED).append(score2.func_96652_c()).toString();
            int height = maxHeight - (index * fontRenderer.field_78288_b);
            boolean changed = false;
            String stripColor2 = ColorUtils.stripColor(name2);
            Intrinsics.checkNotNull(stripColor2);
            String stripped2 = StringUtils.fixString(stripColor2);
            GlStateManager.func_179117_G();
            if (this.changeDomain.get().booleanValue() && this.cachedDomains.contains(stripped2)) {
                name2 = hud.getDomainValue().get();
                changed = true;
            }
            if (this.antiSnipeMatch.get().booleanValue()) {
                Regex regex = this.hypickleRegex;
                Intrinsics.checkNotNullExpressionValue(stripped2, "stripped");
                if (regex.containsMatchIn(stripped2)) {
                    name2 = "";
                }
            }
            if (changed) {
                String stringZ = "";
                int i6 = 0;
                int length2 = name2.length() - 1;
                if (0 <= length2) {
                    do {
                        z = i6;
                        i6++;
                        if (StringsKt.equals(rectColorMode, "Sky", true)) {
                            i3 = RenderUtils.SkyRainbow(z * this.delayValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                        } else if (StringsKt.equals(rectColorMode, "Rainbow", true)) {
                            i3 = RenderUtils.getRainbowOpaque(this.cRainbowSecValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), z * this.delayValue.get().intValue());
                        } else if (StringsKt.equals(rectColorMode, "LiquidSlowly", true)) {
                            Color LiquidSlowly3 = ColorUtils.LiquidSlowly(System.nanoTime(), z * this.delayValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                            Intrinsics.checkNotNull(LiquidSlowly3);
                            i3 = LiquidSlowly3.getRGB();
                        } else if (StringsKt.equals(rectColorMode, "Fade", true)) {
                            i3 = ColorUtils.fade(new Color(this.rectColorRedValue.get().intValue(), this.rectColorGreenValue.get().intValue(), this.rectColorBlueValue.get().intValue(), this.rectColorBlueAlpha.get().intValue()), z * this.delayValue.get().intValue(), 100).getRGB();
                        } else {
                            i3 = StringsKt.equals(rectColorMode, "Mixer", true) ? ColorMixer.getMixedColor(z * this.delayValue.get().intValue(), this.cRainbowSecValue.get().intValue()).getRGB() : rectCustomColor;
                        }
                        int typeColor = i3;
                        if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                            String lowerCase2 = this.domainShadowValue.get().toLowerCase();
                            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                            switch (lowerCase2.hashCode()) {
                                case -1106245566:
                                    if (lowerCase2.equals("outline")) {
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), ((-3.0f) + this.domainFontValue.get().func_78256_a(stringZ)) - this.outlineWidthValue.get().floatValue(), height + this.domainFontYValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), (-3.0f) + this.domainFontValue.get().func_78256_a(stringZ) + this.outlineWidthValue.get().floatValue(), height + this.domainFontYValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), (-3.0f) + this.domainFontValue.get().func_78256_a(stringZ), (height + this.domainFontYValue.get().floatValue()) - this.outlineWidthValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), (-3.0f) + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue() + this.outlineWidthValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), (-3.0f) + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue(), typeColor, this.shadowValue.get().booleanValue());
                                        break;
                                    }
                                    break;
                                case 3387192:
                                    if (lowerCase2.equals("none")) {
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), (-3.0f) + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue(), typeColor, false);
                                        break;
                                    }
                                    break;
                                case 1544803905:
                                    if (lowerCase2.equals("default")) {
                                        this.domainFontValue.get().func_175063_a(String.valueOf(name2.charAt(z)), (-3.0f) + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue(), typeColor);
                                        break;
                                    }
                                    break;
                            }
                        } else {
                            String lowerCase3 = this.domainShadowValue.get().toLowerCase();
                            Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
                            switch (lowerCase3.hashCode()) {
                                case -1106245566:
                                    if (lowerCase3.equals("outline")) {
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), (l1 + this.domainFontValue.get().func_78256_a(stringZ)) - this.outlineWidthValue.get().floatValue(), height + this.domainFontYValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), l1 + this.domainFontValue.get().func_78256_a(stringZ) + this.outlineWidthValue.get().floatValue(), height + this.domainFontYValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), l1 + this.domainFontValue.get().func_78256_a(stringZ), (height + this.domainFontYValue.get().floatValue()) - this.outlineWidthValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), l1 + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue() + this.outlineWidthValue.get().floatValue(), Color.black.getRGB(), this.shadowValue.get().booleanValue());
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), l1 + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue(), typeColor, this.shadowValue.get().booleanValue());
                                        break;
                                    }
                                    break;
                                case 3387192:
                                    if (lowerCase3.equals("none")) {
                                        this.domainFontValue.get().func_175065_a(String.valueOf(name2.charAt(z)), l1 + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue(), typeColor, false);
                                        break;
                                    }
                                    break;
                                case 1544803905:
                                    if (lowerCase3.equals("default")) {
                                        this.domainFontValue.get().func_175063_a(String.valueOf(name2.charAt(z)), l1 + this.domainFontValue.get().func_78256_a(stringZ), height + this.domainFontYValue.get().floatValue(), typeColor);
                                        break;
                                    }
                                    break;
                            }
                        }
                        stringZ = Intrinsics.stringPlus(stringZ, Character.valueOf(name2.charAt(z)));
                    } while (z != length2);
                }
            } else if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                fontRenderer.func_175065_a(name2, -3.0f, height, -1, this.shadowValue.get().booleanValue());
            } else {
                fontRenderer.func_175065_a(name2, l1, height, -1, this.shadowValue.get().booleanValue());
            }
            if (this.showRedNumbersValue.get().booleanValue()) {
                if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                    fontRenderer.func_175065_a(scorePoints, (l1 + 1) - fontRenderer.func_78256_a(scorePoints), height, -1, this.shadowValue.get().booleanValue());
                } else {
                    fontRenderer.func_175065_a(scorePoints, 5 - fontRenderer.func_78256_a(scorePoints), height, -1, this.shadowValue.get().booleanValue());
                }
            }
            if (index == scoreCollection.size() - 1) {
                String displayName = objective.func_96678_d();
                GlStateManager.func_179117_G();
                if (getSide().getHorizontal() == Side.Horizontal.LEFT) {
                    i2 = maxWidth / 2;
                    i = fontRenderer.func_78256_a(displayName);
                } else {
                    i2 = l1 + (maxWidth / 2);
                    i = fontRenderer.func_78256_a(displayName);
                }
                fontRenderer.func_175065_a(displayName, i2 - (i / 2), height - fontRenderer.field_78288_b, -1, this.shadowValue.get().booleanValue());
            }
        }
        return getSide().getHorizontal() == Side.Horizontal.LEFT ? new Border(maxWidth + 5, -2.0f, -5.0f, maxHeight + fontRenderer.field_78288_b) : new Border((-maxWidth) - 5, -2.0f, 5.0f, maxHeight + fontRenderer.field_78288_b);
    }

    /* renamed from: drawElement$lambda-0 */
    private static final boolean m2858drawElement$lambda0(Score input) {
        if ((input == null ? null : input.func_96653_e()) != null) {
            String func_96653_e = input.func_96653_e();
            Intrinsics.checkNotNullExpressionValue(func_96653_e, "input.playerName");
            if (!StringsKt.startsWith$default(func_96653_e, "#", false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    private final Color backgroundColor() {
        return new Color(this.backgroundColorRedValue.get().intValue(), this.backgroundColorGreenValue.get().intValue(), this.backgroundColorBlueValue.get().intValue(), this.backgroundColorAlphaValue.get().intValue());
    }
}
