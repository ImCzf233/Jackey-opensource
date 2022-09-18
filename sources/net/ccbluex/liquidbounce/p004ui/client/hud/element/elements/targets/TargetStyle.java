package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets;

import java.awt.Color;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: TargetStyle.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018��2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ&\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-JX\u00101\u001a\u00020+2\u0006\u00102\u001a\u00020 2\u0006\u0010,\u001a\u00020\u00122\u0006\u0010.\u001a\u00020\u00122\u0006\u00103\u001a\u00020\u00122\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-2\u0006\u00104\u001a\u00020\u00122\u0006\u00105\u001a\u00020\u00122\u0006\u00106\u001a\u00020\u00122\b\b\u0002\u00107\u001a\u00020\u0012J<\u00101\u001a\u00020+2\u0006\u00102\u001a\u00020 2\b\b\u0002\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-2\b\b\u0002\u00107\u001a\u00020\u0012J\u0010\u00108\u001a\u00020+2\u0006\u00109\u001a\u00020:H&J\u0014\u0010;\u001a\u0004\u0018\u00010<2\b\u00109\u001a\u0004\u0018\u00010:H&J\u000e\u0010=\u001a\u00020\u001c2\u0006\u0010>\u001a\u00020\u001cJ\u000e\u0010=\u001a\u00020\u001c2\u0006\u0010>\u001a\u00020-J\u0010\u0010?\u001a\u00020+2\u0006\u0010@\u001a\u00020:H\u0016J\u0010\u0010A\u001a\u00020+2\u0006\u0010@\u001a\u00020:H\u0016J\u0010\u0010B\u001a\u00020+2\u0006\u0010@\u001a\u00020:H\u0016J\u0010\u0010C\u001a\u00020+2\u0006\u0010@\u001a\u00020:H\u0016J\u0010\u0010D\u001a\u00020+2\u0006\u0010E\u001a\u00020\u0012H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\n¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\fR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n��\u001a\u0004\b!\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030'0&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006F"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "name", "", "targetInstance", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "shaderSupport", "", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;Z)V", "decimalFormat", "Ljava/text/DecimalFormat;", "getDecimalFormat", "()Ljava/text/DecimalFormat;", "decimalFormat2", "getDecimalFormat2", "decimalFormat3", "getDecimalFormat3", "easingHealth", "", "getEasingHealth", "()F", "setEasingHealth", "(F)V", "getName", "()Ljava/lang/String;", "getShaderSupport", "()Z", "shadowOpaque", "Ljava/awt/Color;", "getShadowOpaque", "()Ljava/awt/Color;", "shieldIcon", "Lnet/minecraft/util/ResourceLocation;", "getShieldIcon", "()Lnet/minecraft/util/ResourceLocation;", "getTargetInstance", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "drawArmorIcon", "", "x", "", "y", "width", "height", "drawHead", "skin", "scale", "red", "green", "blue", "alpha", "drawTarget", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getColor", "color", "handleBlur", "player", "handleDamage", "handleShadow", "handleShadowCut", "updateAnim", "targetHealth", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.TargetStyle */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle.class */
public abstract class TargetStyle extends MinecraftInstance {
    @NotNull
    private final String name;
    @NotNull
    private final Target targetInstance;
    private final boolean shaderSupport;
    private float easingHealth;
    @NotNull
    private final ResourceLocation shieldIcon = new ResourceLocation("liquidbounce+/shield.png");
    @NotNull
    private final DecimalFormat decimalFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
    @NotNull
    private final DecimalFormat decimalFormat2 = new DecimalFormat("##0.0", new DecimalFormatSymbols(Locale.ENGLISH));
    @NotNull
    private final DecimalFormat decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));

    public abstract void drawTarget(@NotNull EntityPlayer entityPlayer);

    @Nullable
    public abstract Border getBorder(@Nullable EntityPlayer entityPlayer);

    public TargetStyle(@NotNull String name, @NotNull Target targetInstance, boolean shaderSupport) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(targetInstance, "targetInstance");
        this.name = name;
        this.targetInstance = targetInstance;
        this.shaderSupport = shaderSupport;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Target getTargetInstance() {
        return this.targetInstance;
    }

    public final boolean getShaderSupport() {
        return this.shaderSupport;
    }

    public final float getEasingHealth() {
        return this.easingHealth;
    }

    public final void setEasingHealth(float f) {
        this.easingHealth = f;
    }

    @NotNull
    public final ResourceLocation getShieldIcon() {
        return this.shieldIcon;
    }

    @NotNull
    public final DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }

    @NotNull
    public final DecimalFormat getDecimalFormat2() {
        return this.decimalFormat2;
    }

    @NotNull
    public final DecimalFormat getDecimalFormat3() {
        return this.decimalFormat3;
    }

    @NotNull
    public final Color getShadowOpaque() {
        Color color;
        String lowerCase = this.targetInstance.getShadowColorMode().get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase, "background")) {
            color = this.targetInstance.getBgColor();
        } else {
            color = Intrinsics.areEqual(lowerCase, "custom") ? new Color(this.targetInstance.getShadowColorRedValue().get().intValue(), this.targetInstance.getShadowColorGreenValue().get().intValue(), this.targetInstance.getShadowColorBlueValue().get().intValue()) : this.targetInstance.getBarColor();
        }
        return ColorUtils.reAlpha(color, 1.0f - this.targetInstance.getAnimProgress());
    }

    public void updateAnim(float targetHealth) {
        if (this.targetInstance.getNoAnimValue().get().booleanValue()) {
            this.easingHealth = targetHealth;
        } else {
            this.easingHealth += ((targetHealth - this.easingHealth) / ((float) Math.pow(2.0f, 10.0f - this.targetInstance.getGlobalAnimSpeed().get().floatValue()))) * RenderUtils.deltaTime;
        }
    }

    public void handleDamage(@NotNull EntityPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
    }

    public void handleBlur(@NotNull EntityPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
    }

    public void handleShadowCut(@NotNull EntityPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
    }

    public void handleShadow(@NotNull EntityPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
    }

    @NotNull
    public List<Value<?>> getValues() {
        Object[] declaredFields = getClass().getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "javaClass.declaredFields");
        Object[] $this$map$iv = declaredFields;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        int i = 0;
        int length = $this$map$iv.length;
        while (i < length) {
            Object item$iv$iv = $this$map$iv[i];
            i++;
            Field valueField = (Field) item$iv$iv;
            valueField.setAccessible(true);
            destination$iv$iv.add(valueField.get(this));
        }
        Iterable $this$filterIsInstance$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (element$iv$iv instanceof Value) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv2;
    }

    @NotNull
    public final Color getColor(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "color");
        return ColorUtils.reAlpha(color, (color.getAlpha() / 255.0f) * (1.0f - this.targetInstance.getFadeProgress()));
    }

    @NotNull
    public final Color getColor(int color) {
        return getColor(new Color(color));
    }

    public static /* synthetic */ void drawHead$default(TargetStyle targetStyle, ResourceLocation resourceLocation, int i, int i2, int i3, int i4, float f, int i5, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawHead");
        }
        if ((i5 & 2) != 0) {
            i = 2;
        }
        if ((i5 & 4) != 0) {
            i2 = 2;
        }
        if ((i5 & 32) != 0) {
            f = 1.0f;
        }
        targetStyle.drawHead(resourceLocation, i, i2, i3, i4, f);
    }

    public final void drawHead(@NotNull ResourceLocation skin, int x, int y, int width, int height, float alpha) {
        Intrinsics.checkNotNullParameter(skin, "skin");
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        MinecraftInstance.f362mc.func_110434_K().func_110577_a(skin);
        Gui.func_152125_a(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static /* synthetic */ void drawHead$default(TargetStyle targetStyle, ResourceLocation resourceLocation, float f, float f2, float f3, int i, int i2, float f4, float f5, float f6, float f7, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawHead");
        }
        if ((i3 & 512) != 0) {
            f7 = 1.0f;
        }
        targetStyle.drawHead(resourceLocation, f, f2, f3, i, i2, f4, f5, f6, f7);
    }

    public final void drawHead(@NotNull ResourceLocation skin, float x, float y, float scale, int width, int height, float red, float green, float blue, float alpha) {
        Intrinsics.checkNotNullParameter(skin, "skin");
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0f);
        GL11.glScalef(scale, scale, scale);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glColor4f(RangesKt.coerceIn(red, 0.0f, 1.0f), RangesKt.coerceIn(green, 0.0f, 1.0f), RangesKt.coerceIn(blue, 0.0f, 1.0f), RangesKt.coerceIn(alpha, 0.0f, 1.0f));
        MinecraftInstance.f362mc.func_110434_K().func_110577_a(skin);
        Gui.func_152125_a(0, 0, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public final void drawArmorIcon(int x, int y, int width, int height) {
        GlStateManager.func_179118_c();
        RenderUtils.drawImage(this.shieldIcon, x, y, width, height);
        GlStateManager.func_179141_d();
    }
}
