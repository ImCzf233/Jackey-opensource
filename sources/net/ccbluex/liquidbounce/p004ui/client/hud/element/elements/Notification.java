package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.awt.Color;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: Notifications.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��`\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��2\u00020\u0001:\u0002DEB\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0016\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u00182\u0006\u0010B\u001a\u00020CR\u001a\u0010\f\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030 X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010)\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020\u0003X\u0082D¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n��R\u001a\u00100\u001a\u000201X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001a\u00106\u001a\u000207X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010<\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b=\u0010+\"\u0004\b>\u0010-¨\u0006F"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "", "message", "", "type", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$Type;", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$Type;)V", "(Ljava/lang/String;)V", "displayLength", "", "(Ljava/lang/String;J)V", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$Type;J)V", "displayTime", "getDisplayTime", "()J", "setDisplayTime", "(J)V", "fadeState", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;", "getFadeState", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;", "setFadeState", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;)V", "fadeStep", "", "firstY", "imgError", "Lnet/minecraft/util/ResourceLocation;", "imgInfo", "imgSuccess", "imgWarning", "messageList", "", "getMessageList", "()Ljava/util/List;", "setMessageList", "(Ljava/util/List;)V", "newError", "newInfo", "newSuccess", "newWarning", "notifHeight", "getNotifHeight", "()F", "setNotifHeight", "(F)V", "notifyDir", "stay", "stayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getStayTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "setStayTimer", "(Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;)V", "textLength", "", "getTextLength", "()I", "setTextLength", "(I)V", "x", "getX", "setX", "drawNotification", "", "animationY", "parent", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications;", "FadeState", "Type", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification.class */
public final class Notification {
    @NotNull
    private final String notifyDir;
    @NotNull
    private final ResourceLocation imgSuccess;
    @NotNull
    private final ResourceLocation imgError;
    @NotNull
    private final ResourceLocation imgWarning;
    @NotNull
    private final ResourceLocation imgInfo;
    @NotNull
    private final ResourceLocation newSuccess;
    @NotNull
    private final ResourceLocation newError;
    @NotNull
    private final ResourceLocation newWarning;
    @NotNull
    private final ResourceLocation newInfo;

    /* renamed from: x */
    private float f357x;
    private int textLength;
    @NotNull
    private FadeState fadeState;
    private long displayTime;
    @NotNull
    private MSTimer stayTimer;
    private float notifHeight;
    @NotNull
    private String message;
    @NotNull
    private List<String> messageList;
    private float stay;
    private float fadeStep;
    private float firstY;
    @NotNull
    private Type type;

    /* compiled from: Notifications.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;", "", "(Ljava/lang/String;I)V", "IN", "STAY", "OUT", "END", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification$FadeState */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState.class */
    public enum FadeState {
        IN,
        STAY,
        OUT,
        END
    }

    /* compiled from: Notifications.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$Type;", "", "(Ljava/lang/String;I)V", "SUCCESS", "INFO", "WARNING", "ERROR", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification$Type */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$Type.class */
    public enum Type {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }

    /* compiled from: Notifications.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Type.values().length];
            iArr[Type.SUCCESS.ordinal()] = 1;
            iArr[Type.ERROR.ordinal()] = 2;
            iArr[Type.INFO.ordinal()] = 3;
            iArr[Type.WARNING.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FadeState.values().length];
            iArr2[FadeState.IN.ordinal()] = 1;
            iArr2[FadeState.STAY.ordinal()] = 2;
            iArr2[FadeState.OUT.ordinal()] = 3;
            iArr2[FadeState.END.ordinal()] = 4;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public Notification(@NotNull String message, @NotNull Type type, long displayLength) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(type, "type");
        this.notifyDir = "liquidbounce+/notification/";
        this.imgSuccess = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "checkmark.png"));
        this.imgError = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "error.png"));
        this.imgWarning = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "warning.png"));
        this.imgInfo = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "info.png"));
        this.newSuccess = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "new/checkmark.png"));
        this.newError = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "new/error.png"));
        this.newWarning = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "new/warning.png"));
        this.newInfo = new ResourceLocation(Intrinsics.stringPlus(this.notifyDir, "new/info.png"));
        this.fadeState = FadeState.IN;
        this.stayTimer = new MSTimer();
        this.message = "";
        this.message = message;
        List<String> func_78271_c = Fonts.font40.func_78271_c(message, 105);
        Intrinsics.checkNotNullExpressionValue(func_78271_c, "font40.listFormattedStringToWidth(message, 105)");
        this.messageList = func_78271_c;
        this.notifHeight = (this.messageList.size() * (Fonts.font40.field_78288_b + 2.0f)) + 8.0f;
        this.type = type;
        this.displayTime = displayLength;
        this.firstY = 19190.0f;
        this.stayTimer.reset();
        this.textLength = Fonts.font40.func_78256_a(message);
    }

    public final float getX() {
        return this.f357x;
    }

    public final void setX(float f) {
        this.f357x = f;
    }

    public final int getTextLength() {
        return this.textLength;
    }

    public final void setTextLength(int i) {
        this.textLength = i;
    }

    @NotNull
    public final FadeState getFadeState() {
        return this.fadeState;
    }

    public final void setFadeState(@NotNull FadeState fadeState) {
        Intrinsics.checkNotNullParameter(fadeState, "<set-?>");
        this.fadeState = fadeState;
    }

    public final long getDisplayTime() {
        return this.displayTime;
    }

    public final void setDisplayTime(long j) {
        this.displayTime = j;
    }

    @NotNull
    public final MSTimer getStayTimer() {
        return this.stayTimer;
    }

    public final void setStayTimer(@NotNull MSTimer mSTimer) {
        Intrinsics.checkNotNullParameter(mSTimer, "<set-?>");
        this.stayTimer = mSTimer;
    }

    public final float getNotifHeight() {
        return this.notifHeight;
    }

    public final void setNotifHeight(float f) {
        this.notifHeight = f;
    }

    @NotNull
    public final List<String> getMessageList() {
        return this.messageList;
    }

    public final void setMessageList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.messageList = list;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Notification(@NotNull String message, @NotNull Type type) {
        this(message, type, 2000L);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(type, "type");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Notification(@NotNull String message) {
        this(message, Type.INFO, 500L);
        Intrinsics.checkNotNullParameter(message, "message");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Notification(@NotNull String message, long displayLength) {
        this(message, Type.INFO, displayLength);
        Intrinsics.checkNotNullParameter(message, "message");
    }

    public final void drawNotification(float animationY, @NotNull Notifications parent) {
        int i;
        ResourceLocation resourceLocation;
        int i2;
        int i3;
        int i4;
        ResourceLocation resourceLocation2;
        int i5;
        int i6;
        int i7;
        int i8;
        Intrinsics.checkNotNullParameter(parent, "parent");
        int delta = RenderUtils.deltaTime;
        String style = parent.getStyleValue().get();
        boolean barMaterial = parent.getBarValue().get().booleanValue();
        boolean blur = parent.getBlurValue().get().booleanValue();
        float strength = parent.getBlurStrength().get().floatValue();
        String hAnimMode = parent.getHAnimModeValue().get();
        String vAnimMode = parent.getVAnimModeValue().get();
        float animSpeed = parent.getAnimationSpeed().get().floatValue();
        parent.getSide();
        float originalX = (float) parent.getRenderX();
        float originalY = (float) parent.getRenderY();
        float width = StringsKt.equals(style, "material", true) ? 160.0f : this.textLength + 8.0f;
        Color backgroundColor = new Color(0, 0, 0, parent.getBgAlphaValue().get().intValue());
        switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
            case 1:
                i = new Color(80, 255, 80).getRGB();
                break;
            case 2:
                i = new Color(255, 80, 80).getRGB();
                break;
            case 3:
                i = new Color(255, 255, 255).getRGB();
                break;
            case 4:
                i = new Color(255, 255, 0).getRGB();
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        int enumColor = i;
        if (StringsKt.equals(vAnimMode, "smooth", true)) {
            if (this.firstY == 19190.0f) {
                this.firstY = animationY;
            } else {
                this.firstY = AnimationUtils.animate(animationY, this.firstY, 0.02f * delta);
            }
        } else {
            this.firstY = animationY;
        }
        float y = this.firstY;
        String lowerCase = style.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 3154575:
                if (lowerCase.equals("full")) {
                    float dist = ((this.f357x + 1) + 26.0f) - ((this.f357x - 8) - this.textLength);
                    float kek = ((-this.f357x) - 1) - 26.0f;
                    GlStateManager.func_179117_G();
                    if (blur) {
                        GL11.glTranslatef(-originalX, -originalY, 0.0f);
                        GL11.glPushMatrix();
                        BlurUtils.blurArea(originalX + kek, (originalY - 28.0f) - y, originalX + (-this.f357x) + 8 + this.textLength, originalY + (-y), strength);
                        GL11.glPopMatrix();
                        GL11.glTranslatef(originalX, originalY, 0.0f);
                    }
                    RenderUtils.drawRect((-this.f357x) + 8 + this.textLength, -y, kek, (-28.0f) - y, backgroundColor.getRGB());
                    GL11.glPushMatrix();
                    GlStateManager.func_179118_c();
                    switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                        case 1:
                            resourceLocation = this.imgSuccess;
                            break;
                        case 2:
                            resourceLocation = this.imgError;
                            break;
                        case 3:
                            resourceLocation = this.imgInfo;
                            break;
                        case 4:
                            resourceLocation = this.imgWarning;
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    RenderUtils.drawImage2(resourceLocation, kek, (-27.0f) - y, 26, 26);
                    GlStateManager.func_179141_d();
                    GL11.glPopMatrix();
                    GlStateManager.func_179117_G();
                    if (this.fadeState == FadeState.STAY && !this.stayTimer.hasTimePassed(this.displayTime)) {
                        RenderUtils.drawRect(kek, -y, kek + (dist * (this.stayTimer.hasTimePassed(this.displayTime) ? 0.0f : ((float) (this.displayTime - (System.currentTimeMillis() - this.stayTimer.time))) / ((float) this.displayTime))), (-1.0f) - y, enumColor);
                    } else if (this.fadeState == FadeState.IN) {
                        RenderUtils.drawRect(kek, -y, kek + dist, (-1.0f) - y, enumColor);
                    }
                    GlStateManager.func_179117_G();
                    Fonts.font40.drawString(this.message, (-this.f357x) + 2, (-18.0f) - y, -1);
                    break;
                }
                break;
            case 299066663:
                if (lowerCase.equals("material")) {
                    GlStateManager.func_179117_G();
                    GL11.glPushMatrix();
                    GL11.glTranslatef(-this.f357x, ((-y) - this.notifHeight) - (barMaterial ? 2.0f : 0.0f), 0.0f);
                    float f = this.notifHeight + (barMaterial ? 2.0f : 0.0f) + 1.0f;
                    switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                        case 1:
                            i2 = new Color(72, 210, 48, 70).getRGB();
                            break;
                        case 2:
                            i2 = new Color(227, 28, 28, 70).getRGB();
                            break;
                        case 3:
                            i2 = new Color(255, 255, 255, 70).getRGB();
                            break;
                        case 4:
                            i2 = new Color(245, 212, 25, 70).getRGB();
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    RenderUtils.originalRoundedRect(1.0f, -1.0f, 159.0f, f, 1.0f, i2);
                    float f2 = (this.notifHeight + (barMaterial ? 2.0f : 0.0f)) - 1.0f;
                    switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                        case 1:
                            i3 = new Color(72, 210, 48, 70).getRGB();
                            break;
                        case 2:
                            i3 = new Color(227, 28, 28, 70).getRGB();
                            break;
                        case 3:
                            i3 = new Color(255, 255, 255, 70).getRGB();
                            break;
                        case 4:
                            i3 = new Color(245, 212, 25, 70).getRGB();
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    RenderUtils.originalRoundedRect(-1.0f, 1.0f, 161.0f, f2, 1.0f, i3);
                    float f3 = this.notifHeight + (barMaterial ? 2.0f : 0.0f) + 0.5f;
                    switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                        case 1:
                            i4 = new Color(72, 210, 48, 80).getRGB();
                            break;
                        case 2:
                            i4 = new Color(227, 28, 28, 80).getRGB();
                            break;
                        case 3:
                            i4 = new Color(255, 255, 255, 80).getRGB();
                            break;
                        case 4:
                            i4 = new Color(245, 212, 25, 80).getRGB();
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    RenderUtils.originalRoundedRect(-0.5f, -0.5f, 160.5f, f3, 1.0f, i4);
                    if (barMaterial) {
                        Stencil.write(true);
                        float f4 = this.notifHeight + 2.0f;
                        switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                            case 1:
                                i6 = new Color(72, 210, 48, 255).getRGB();
                                break;
                            case 2:
                                i6 = new Color(227, 28, 28, 255).getRGB();
                                break;
                            case 3:
                                i6 = new Color(255, 255, 255, 255).getRGB();
                                break;
                            case 4:
                                i6 = new Color(245, 212, 25, 255).getRGB();
                                break;
                            default:
                                throw new NoWhenBranchMatchedException();
                        }
                        RenderUtils.originalRoundedRect(0.0f, 0.0f, 160.0f, f4, 1.0f, i6);
                        Stencil.erase(true);
                        if (this.fadeState == FadeState.STAY) {
                            float f5 = this.notifHeight;
                            float currentTimeMillis = 160.0f * (this.stayTimer.hasTimePassed(this.displayTime) ? 1.0f : ((float) (System.currentTimeMillis() - this.stayTimer.time)) / ((float) this.displayTime));
                            float f6 = this.notifHeight + 2.0f;
                            switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                                case 1:
                                    i7 = new Color(162, 240, 138, 255).getRGB();
                                    break;
                                case 2:
                                    i7 = new Color(247, 118, 118, 255).getRGB();
                                    break;
                                case 3:
                                    i7 = new Color(155, 155, 155, 255).getRGB();
                                    break;
                                case 4:
                                    i7 = new Color(175, 142, 25, 255).getRGB();
                                    break;
                                default:
                                    throw new NoWhenBranchMatchedException();
                            }
                            RenderUtils.newDrawRect(0.0f, f5, currentTimeMillis, f6, i7);
                        }
                        Stencil.dispose();
                    } else {
                        float f7 = this.notifHeight;
                        switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                            case 1:
                                i5 = new Color(72, 210, 48, 255).getRGB();
                                break;
                            case 2:
                                i5 = new Color(227, 28, 28, 255).getRGB();
                                break;
                            case 3:
                                i5 = new Color(255, 255, 255, 255).getRGB();
                                break;
                            case 4:
                                i5 = new Color(245, 212, 25, 255).getRGB();
                                break;
                            default:
                                throw new NoWhenBranchMatchedException();
                        }
                        RenderUtils.originalRoundedRect(0.0f, 0.0f, 160.0f, f7, 1.0f, i5);
                    }
                    float yHeight = 7.0f;
                    for (String s : this.messageList) {
                        Fonts.font40.drawString(s, 30.0f, yHeight, this.type == Type.ERROR ? -1 : 0);
                        yHeight += Fonts.font40.field_78288_b + 2.0f;
                    }
                    GL11.glPushMatrix();
                    GlStateManager.func_179118_c();
                    switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                        case 1:
                            resourceLocation2 = this.newSuccess;
                            break;
                        case 2:
                            resourceLocation2 = this.newError;
                            break;
                        case 3:
                            resourceLocation2 = this.newInfo;
                            break;
                        case 4:
                            resourceLocation2 = this.newWarning;
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    RenderUtils.drawImage3(resourceLocation2, 9.0f, (this.notifHeight / 2.0f) - 6.0f, 12, 12, this.type == Type.ERROR ? 1.0f : 0.0f, this.type == Type.ERROR ? 1.0f : 0.0f, this.type == Type.ERROR ? 1.0f : 0.0f, 1.0f);
                    GlStateManager.func_179141_d();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    GlStateManager.func_179117_G();
                    break;
                }
                break;
            case 950483747:
                if (lowerCase.equals("compact")) {
                    GlStateManager.func_179117_G();
                    if (blur) {
                        GL11.glTranslatef(-originalX, -originalY, 0.0f);
                        GL11.glPushMatrix();
                        BlurUtils.blurAreaRounded((originalX + (-this.f357x)) - 5.0f, (originalY - 18.0f) - y, originalX + (-this.f357x) + 8.0f + this.textLength, originalY + (-y), 3.0f, strength);
                        GL11.glPopMatrix();
                        GL11.glTranslatef(originalX, originalY, 0.0f);
                    }
                    RenderUtils.customRounded((-this.f357x) + 8.0f + this.textLength, -y, (-this.f357x) - 2.0f, (-18.0f) - y, 0.0f, 3.0f, 3.0f, 0.0f, backgroundColor.getRGB());
                    float f8 = (-this.f357x) - 2.0f;
                    float f9 = -y;
                    float f10 = (-this.f357x) - 5.0f;
                    float f11 = (-18.0f) - y;
                    switch (WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
                        case 1:
                            i8 = new Color(80, 255, 80).getRGB();
                            break;
                        case 2:
                            i8 = new Color(255, 80, 80).getRGB();
                            break;
                        case 3:
                            i8 = new Color(255, 255, 255).getRGB();
                            break;
                        case 4:
                            i8 = new Color(255, 255, 0).getRGB();
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    RenderUtils.customRounded(f8, f9, f10, f11, 3.0f, 0.0f, 0.0f, 3.0f, i8);
                    GlStateManager.func_179117_G();
                    Fonts.font40.drawString(this.message, (-this.f357x) + 3, (-13.0f) - y, -1);
                    break;
                }
                break;
        }
        switch (WhenMappings.$EnumSwitchMapping$1[this.fadeState.ordinal()]) {
            case 1:
                if (this.f357x < width) {
                    if (StringsKt.equals(hAnimMode, "smooth", true)) {
                        this.f357x = AnimationUtils.animate(width, this.f357x, animSpeed * 0.025f * delta);
                    } else {
                        this.f357x = net.ccbluex.liquidbounce.utils.render.AnimationUtils.easeOut(this.fadeStep, width) * width;
                    }
                    this.fadeStep += delta / 4.0f;
                }
                if (this.f357x >= width) {
                    this.fadeState = FadeState.STAY;
                    this.f357x = width;
                    this.fadeStep = width;
                }
                this.stay = 60.0f;
                this.stayTimer.reset();
                return;
            case 2:
                if (this.stay > 0.0f) {
                    this.stay = 0.0f;
                    this.stayTimer.reset();
                }
                if (this.stayTimer.hasTimePassed(this.displayTime)) {
                    this.fadeState = FadeState.OUT;
                    return;
                }
                return;
            case 3:
                if (this.f357x > 0.0f) {
                    if (StringsKt.equals(hAnimMode, "smooth", true)) {
                        this.f357x = AnimationUtils.animate((-width) / 2.0f, this.f357x, animSpeed * 0.025f * delta);
                    } else {
                        this.f357x = net.ccbluex.liquidbounce.utils.render.AnimationUtils.easeOut(this.fadeStep, width) * width;
                    }
                    this.fadeStep -= delta / 4.0f;
                    return;
                }
                this.fadeState = FadeState.END;
                return;
            case 4:
                LiquidBounce.INSTANCE.getHud().removeNotification(this);
                return;
            default:
                return;
        }
    }
}
