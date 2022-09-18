package net.ccbluex.liquidbounce.p004ui.client.hud.element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Element.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n��\n\u0002\u0010\b\n\u0002\b\u0007\b&\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010>\u001a\u00020\u0011H\u0016J\b\u0010?\u001a\u00020@H\u0016J\n\u0010A\u001a\u0004\u0018\u00010\u000bH&J\b\u0010B\u001a\u00020@H\u0016J\u0010\u0010C\u001a\u00020@2\u0006\u0010D\u001a\u00020EH\u0016J\u0018\u0010F\u001a\u00020@2\u0006\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020JH\u0016J \u0010K\u001a\u00020@2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010L\u001a\u00020JH\u0016J\u0010\u0010M\u001a\u00020@2\u0006\u0010N\u001a\u00020\u0011H\u0016J\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\b\u0010P\u001a\u00020@H\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n��\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R$\u0010'\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R$\u0010,\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R&\u0010\u0005\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b/\u0010 \"\u0004\b0\u0010\"R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001e\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000307068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b:\u0010)\"\u0004\b;\u0010+R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b<\u0010)\"\u0004\b=\u0010+¨\u0006Q"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "border", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getBorder", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setBorder", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;)V", "drag", "", "getDrag", "()Z", "setDrag", "(Z)V", "info", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "name", "", "getName", "()Ljava/lang/String;", "prevMouseX", "getPrevMouseX", "()F", "setPrevMouseX", "(F)V", "prevMouseY", "getPrevMouseY", "setPrevMouseY", "value", "renderX", "getRenderX", "()D", "setRenderX", "(D)V", "renderY", "getRenderY", "setRenderY", "getScale", "setScale", "getSide", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "setSide", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "getX", "setX", "getY", "setY", "createElement", "destroyElement", "", "drawElement", "handleBlurShader", "handleDamage", "ent", "Lnet/minecraft/entity/player/EntityPlayer;", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseButton", "handleShadowShader", "cut", "isInBorder", "updateElement", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Element */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Element.class */
public abstract class Element extends MinecraftInstance {

    /* renamed from: x */
    private double f352x;

    /* renamed from: y */
    private double f353y;
    @NotNull
    private Side side;
    @NotNull
    private final ElementInfo info;
    private float scale;
    @Nullable
    private Border border;
    private boolean drag;
    private float prevMouseX;
    private float prevMouseY;

    /* compiled from: Element.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Element$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Element$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Side.Horizontal.values().length];
            iArr[Side.Horizontal.LEFT.ordinal()] = 1;
            iArr[Side.Horizontal.MIDDLE.ordinal()] = 2;
            iArr[Side.Horizontal.RIGHT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Side.Vertical.values().length];
            iArr2[Side.Vertical.UP.ordinal()] = 1;
            iArr2[Side.Vertical.MIDDLE.ordinal()] = 2;
            iArr2[Side.Vertical.DOWN.ordinal()] = 3;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    @Nullable
    public abstract Border drawElement();

    public Element() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Element(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 2.0d : d, (i & 2) != 0 ? 2.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? Side.Companion.m2845default() : side);
    }

    public final double getX() {
        return this.f352x;
    }

    public final void setX(double d) {
        this.f352x = d;
    }

    public final double getY() {
        return this.f353y;
    }

    public final void setY(double d) {
        this.f353y = d;
    }

    public Element(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkNotNullParameter(side, "side");
        this.f352x = x;
        this.f353y = y;
        this.side = side;
        ElementInfo elementInfo = (ElementInfo) getClass().getAnnotation(ElementInfo.class);
        if (elementInfo != null) {
            this.info = elementInfo;
            this.scale = 1.0f;
            setScale(scale);
            return;
        }
        throw new IllegalArgumentException("Passed element with missing element info");
    }

    @NotNull
    public final Side getSide() {
        return this.side;
    }

    public final void setSide(@NotNull Side side) {
        Intrinsics.checkNotNullParameter(side, "<set-?>");
        this.side = side;
    }

    @NotNull
    public final ElementInfo getInfo() {
        return this.info;
    }

    public final void setScale(float value) {
        if (this.info.disableScale()) {
            return;
        }
        this.scale = value;
    }

    public final float getScale() {
        if (this.info.disableScale()) {
            return 1.0f;
        }
        return this.scale;
    }

    @NotNull
    public final String getName() {
        return this.info.name();
    }

    public final double getRenderX() {
        switch (WhenMappings.$EnumSwitchMapping$0[this.side.getHorizontal().ordinal()]) {
            case 1:
                return this.f352x;
            case 2:
                return (new ScaledResolution(MinecraftInstance.f362mc).func_78326_a() / 2) - this.f352x;
            case 3:
                return new ScaledResolution(MinecraftInstance.f362mc).func_78326_a() - this.f352x;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final void setRenderX(double value) {
        switch (WhenMappings.$EnumSwitchMapping$0[this.side.getHorizontal().ordinal()]) {
            case 1:
                this.f352x += value;
                return;
            case 2:
            case 3:
                this.f352x -= value;
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final double getRenderY() {
        switch (WhenMappings.$EnumSwitchMapping$1[this.side.getVertical().ordinal()]) {
            case 1:
                return this.f353y;
            case 2:
                return (new ScaledResolution(MinecraftInstance.f362mc).func_78328_b() / 2) - this.f353y;
            case 3:
                return new ScaledResolution(MinecraftInstance.f362mc).func_78328_b() - this.f353y;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final void setRenderY(double value) {
        switch (WhenMappings.$EnumSwitchMapping$1[this.side.getVertical().ordinal()]) {
            case 1:
                this.f353y += value;
                return;
            case 2:
            case 3:
                this.f353y -= value;
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Nullable
    public final Border getBorder() {
        return this.border;
    }

    public final void setBorder(@Nullable Border border) {
        this.border = border;
    }

    public final boolean getDrag() {
        return this.drag;
    }

    public final void setDrag(boolean z) {
        this.drag = z;
    }

    public final float getPrevMouseX() {
        return this.prevMouseX;
    }

    public final void setPrevMouseX(float f) {
        this.prevMouseX = f;
    }

    public final float getPrevMouseY() {
        return this.prevMouseY;
    }

    public final void setPrevMouseY(float f) {
        this.prevMouseY = f;
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

    public boolean createElement() {
        return true;
    }

    public void destroyElement() {
    }

    public void updateElement() {
    }

    public boolean isInBorder(double x, double y) {
        Border border = this.border;
        if (border == null) {
            return false;
        }
        float minX = Math.min(border.getX(), border.getX2());
        float minY = Math.min(border.getY(), border.getY2());
        float maxX = Math.max(border.getX(), border.getX2());
        float maxY = Math.max(border.getY(), border.getY2());
        return ((double) minX) <= x && ((double) minY) <= y && ((double) maxX) >= x && ((double) maxY) >= y;
    }

    public void handleMouseClick(double x, double y, int mouseButton) {
    }

    public void handleKey(char c, int keyCode) {
    }

    public void handleDamage(@NotNull EntityPlayer ent) {
        Intrinsics.checkNotNullParameter(ent, "ent");
    }

    public void handleBlurShader() {
    }

    public void handleShadowShader(boolean cut) {
    }
}
