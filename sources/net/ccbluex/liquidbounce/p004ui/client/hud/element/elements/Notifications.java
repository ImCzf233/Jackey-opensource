package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Notifications.kt */
@ElementInfo(name = "Notifications", single = true)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020%H\u0002R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n��\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n��\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u000b¢\u0006\b\n��\u001a\u0004\b\u0017\u0010\rR\u0011\u0010\u0018\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u0011R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n��\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020\u001d¢\u0006\b\n��\u001a\u0004\b!\u0010\u001fR\u0011\u0010\"\u001a\u00020\u001d¢\u0006\b\n��\u001a\u0004\b#\u0010\u001f¨\u0006'"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "animationSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getAnimationSpeed", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "barValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getBarValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "bgAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getBgAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blurStrength", "getBlurStrength", "blurValue", "getBlurValue", "exampleNotification", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "hAnimModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getHAnimModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "styleValue", "getStyleValue", "vAnimModeValue", "getVAnimModeValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getNotifBorder", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notifications */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications.class */
public final class Notifications extends Element {
    @NotNull
    private final ListValue styleValue;
    @NotNull
    private final BoolValue barValue;
    @NotNull
    private final IntegerValue bgAlphaValue;
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final ListValue hAnimModeValue;
    @NotNull
    private final ListValue vAnimModeValue;
    @NotNull
    private final FloatValue animationSpeed;
    @NotNull
    private final Notification exampleNotification;

    public Notifications() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Notifications(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0d : d, (i & 2) != 0 ? 30.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Notifications(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.styleValue = new ListValue("Style", new String[]{"Full", "Compact", "Material"}, "Material");
        this.barValue = new BoolValue("Bar", true, new Notifications$barValue$1(this));
        this.bgAlphaValue = new IntegerValue("Background-Alpha", 120, 0, 255, new Notifications$bgAlphaValue$1(this));
        this.blurValue = new BoolValue("Blur", false, new Notifications$blurValue$1(this));
        this.blurStrength = new FloatValue("Strength", 0.0f, 0.0f, 30.0f, new Notifications$blurStrength$1(this));
        this.hAnimModeValue = new ListValue("H-Animation", new String[]{"LiquidBounce", "Smooth"}, "LiquidBounce");
        this.vAnimModeValue = new ListValue("V-Animation", new String[]{"None", "Smooth"}, "Smooth");
        this.animationSpeed = new FloatValue("Speed", 0.5f, 0.01f, 1.0f, new Notifications$animationSpeed$1(this));
        this.exampleNotification = new Notification("Example Notification", Notification.Type.INFO);
    }

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final BoolValue getBarValue() {
        return this.barValue;
    }

    @NotNull
    public final IntegerValue getBgAlphaValue() {
        return this.bgAlphaValue;
    }

    @NotNull
    public final BoolValue getBlurValue() {
        return this.blurValue;
    }

    @NotNull
    public final FloatValue getBlurStrength() {
        return this.blurStrength;
    }

    @NotNull
    public final ListValue getHAnimModeValue() {
        return this.hAnimModeValue;
    }

    @NotNull
    public final ListValue getVAnimModeValue() {
        return this.vAnimModeValue;
    }

    @NotNull
    public final FloatValue getAnimationSpeed() {
        return this.animationSpeed;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        float f;
        float animationY = 30.0f;
        List<Notification> notifications = new ArrayList();
        for (Notification i : LiquidBounce.INSTANCE.getHud().getNotifications()) {
            notifications.add(i);
        }
        if ((MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner) && notifications.isEmpty()) {
            this.exampleNotification.drawNotification(30.0f - ((!StringsKt.equals(this.styleValue.get(), "material", true) || getSide().getVertical() == Side.Vertical.DOWN) ? 0.0f : (this.exampleNotification.getNotifHeight() - 5.0f) - (this.barValue.get().booleanValue() ? 2.0f : 0.0f)), this);
        } else {
            int indexz = 0;
            for (Notification i2 : notifications) {
                if (indexz == 0 && StringsKt.equals(this.styleValue.get(), "material", true) && getSide().getVertical() != Side.Vertical.DOWN) {
                    animationY -= i2.getNotifHeight() - (this.barValue.get().booleanValue() ? 2.0f : 0.0f);
                }
                i2.drawNotification(animationY, this);
                if (indexz < notifications.size() - 1) {
                    indexz++;
                }
                float f2 = animationY;
                String lowerCase = this.styleValue.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                if (Intrinsics.areEqual(lowerCase, "compact")) {
                    f = 20.0f;
                } else if (Intrinsics.areEqual(lowerCase, "full")) {
                    f = 30.0f;
                } else {
                    f = (getSide().getVertical() == Side.Vertical.DOWN ? i2.getNotifHeight() : ((Notification) notifications.get(indexz)).getNotifHeight()) + 5.0f + (this.barValue.get().booleanValue() ? 2.0f : 0.0f);
                }
                animationY = f2 + (f * (getSide().getVertical() == Side.Vertical.DOWN ? 1.0f : -1.0f));
            }
        }
        if (MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner) {
            this.exampleNotification.setFadeState(Notification.FadeState.STAY);
            this.exampleNotification.setX(StringsKt.equals(this.styleValue.get(), "material", true) ? 160.0f : this.exampleNotification.getTextLength() + 8.0f);
            if (this.exampleNotification.getStayTimer().hasTimePassed(this.exampleNotification.getDisplayTime())) {
                this.exampleNotification.getStayTimer().reset();
            }
            return getNotifBorder();
        }
        return null;
    }

    private final Border getNotifBorder() {
        String lowerCase = this.styleValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        return Intrinsics.areEqual(lowerCase, "full") ? new Border(-130.0f, -58.0f, 0.0f, -30.0f) : Intrinsics.areEqual(lowerCase, "compact") ? new Border(-102.0f, -48.0f, 0.0f, -30.0f) : getSide().getVertical() == Side.Vertical.DOWN ? new Border(-160.0f, -50.0f, 0.0f, -30.0f) : new Border(-160.0f, -20.0f, 0.0f, 0.0f);
    }
}
