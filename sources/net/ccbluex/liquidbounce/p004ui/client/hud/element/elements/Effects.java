package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

/* compiled from: Effects.kt */
@ElementInfo(name = "Effects")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "anotherStyle", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "shadow", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Effects */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects.class */
public final class Effects extends Element {
    @NotNull
    private final BoolValue anotherStyle;
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final BoolValue shadow;

    /* compiled from: Effects.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Effects$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Side.Horizontal.values().length];
            iArr[Side.Horizontal.RIGHT.ordinal()] = 1;
            iArr[Side.Horizontal.LEFT.ordinal()] = 2;
            iArr[Side.Horizontal.MIDDLE.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Side.Vertical.values().length];
            iArr2[Side.Vertical.UP.ordinal()] = 1;
            iArr2[Side.Vertical.DOWN.ordinal()] = 2;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public Effects() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Effects(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 2.0d : d, (i & 2) != 0 ? 10.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Effects(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.anotherStyle = new BoolValue("New", false);
        GameFontRenderer font35 = Fonts.font35;
        Intrinsics.checkNotNullExpressionValue(font35, "font35");
        this.fontValue = new FontValue("Font", font35);
        this.shadow = new BoolValue("Shadow", true);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        String str;
        String str2;
        FontRenderer fontRenderer = this.fontValue.get();
        float y = 0.0f;
        float width = 0.0f;
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        for (PotionEffect effect : MinecraftInstance.f362mc.field_71439_g.func_70651_bq()) {
            Potion potion = Potion.field_76425_a[effect.func_76456_a()];
            if (effect.func_76458_c() == 1) {
                str = "II";
            } else if (effect.func_76458_c() == 2) {
                str = "III";
            } else if (effect.func_76458_c() == 3) {
                str = "IV";
            } else if (effect.func_76458_c() == 4) {
                str = "V";
            } else if (effect.func_76458_c() == 5) {
                str = "VI";
            } else if (effect.func_76458_c() == 6) {
                str = "VII";
            } else if (effect.func_76458_c() == 7) {
                str = "VIII";
            } else if (effect.func_76458_c() == 8) {
                str = "IX";
            } else if (effect.func_76458_c() == 9) {
                str = "X";
            } else {
                str = effect.func_76458_c() > 10 ? "X+" : "I";
            }
            String number = str;
            int duration = effect.func_100011_g() ? 30 : effect.func_76459_b() / 20;
            if (this.anotherStyle.get().booleanValue()) {
                str2 = ((Object) I18n.func_135052_a(potion.func_76393_a(), new Object[0])) + ' ' + number + ' ' + (duration < 15 ? "§c" : duration < 30 ? "§6" : "§7") + ((Object) Potion.func_76389_a(effect));
            } else {
                str2 = ((Object) I18n.func_135052_a(potion.func_76393_a(), new Object[0])) + ' ' + number + "§f: §7" + ((Object) Potion.func_76389_a(effect));
            }
            String name = str2;
            float stringWidth = fontRenderer.func_78256_a(name);
            if (getSide().getHorizontal() == Side.Horizontal.RIGHT) {
                if (width > (-stringWidth)) {
                    width = -stringWidth;
                }
            } else if (width < stringWidth) {
                width = stringWidth;
            }
            switch (WhenMappings.$EnumSwitchMapping$0[getSide().getHorizontal().ordinal()]) {
                case 1:
                    fontRenderer.func_175065_a(name, -stringWidth, y + (getSide().getVertical() == Side.Vertical.UP ? -fontRenderer.field_78288_b : 0.0f), potion.func_76401_j(), this.shadow.get().booleanValue());
                    break;
                case 2:
                case 3:
                    fontRenderer.func_175065_a(name, 0.0f, y + (getSide().getVertical() == Side.Vertical.UP ? -fontRenderer.field_78288_b : 0.0f), potion.func_76401_j(), this.shadow.get().booleanValue());
                    break;
            }
            switch (WhenMappings.$EnumSwitchMapping$1[getSide().getVertical().ordinal()]) {
                case 1:
                    y -= fontRenderer.field_78288_b + (this.anotherStyle.get().booleanValue() ? 1.0f : 0.0f);
                    break;
                case 2:
                    y += fontRenderer.field_78288_b + (this.anotherStyle.get().booleanValue() ? 1.0f : 0.0f);
                    break;
            }
        }
        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        if (width == 0.0f) {
            width = getSide().getHorizontal() == Side.Horizontal.RIGHT ? -40.0f : 40.0f;
        }
        if (y == 0.0f) {
            y = getSide().getVertical() == Side.Vertical.UP ? -fontRenderer.field_78288_b : fontRenderer.field_78288_b;
        }
        return new Border(0.0f, 0.0f, width, y);
    }
}
