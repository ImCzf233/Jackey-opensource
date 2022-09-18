package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: CharRenderer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\b\n��\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JV\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/CharRenderer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "small", "", "(Z)V", "deFormat", "Ljava/text/DecimalFormat;", "moveX", "", "getMoveX", "()[F", "setMoveX", "([F)V", "moveY", "getMoveY", "setMoveY", "numberList", "", "", "getSmall", "()Z", "renderChar", "", "number", "orgX", "orgY", "initX", "initY", "scaleX", "scaleY", "shadow", "fontSpeed", "color", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.CharRenderer */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/CharRenderer.class */
public final class CharRenderer extends MinecraftInstance {
    private final boolean small;
    @NotNull
    private float[] moveY = new float[20];
    @NotNull
    private float[] moveX = new float[20];
    @NotNull
    private final List<String> numberList = CollectionsKt.listOf((Object[]) new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."});
    @NotNull
    private final DecimalFormat deFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));

    public CharRenderer(boolean small) {
        this.small = small;
        int i = 0;
        while (i < 20) {
            int i2 = i;
            i++;
            this.moveX[i2] = 0.0f;
            this.moveY[i2] = 0.0f;
        }
    }

    public final boolean getSmall() {
        return this.small;
    }

    @NotNull
    public final float[] getMoveY() {
        return this.moveY;
    }

    public final void setMoveY(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.moveY = fArr;
    }

    @NotNull
    public final float[] getMoveX() {
        return this.moveX;
    }

    public final void setMoveX(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.moveX = fArr;
    }

    public final float renderChar(float number, float orgX, float orgY, float initX, float initY, float scaleX, float scaleY, boolean shadow, float fontSpeed, int color) {
        String reFormat = this.deFormat.format(number);
        GameFontRenderer fontRend = this.small ? Fonts.font40 : Fonts.font72;
        int delta = RenderUtils.deltaTime;
        ScaledResolution scaledRes = new ScaledResolution(MinecraftInstance.f362mc);
        int indexX = 0;
        int indexY = 0;
        float animX = 0.0f;
        float cutY = initY + (fontRend.field_78288_b * 0.75f);
        GL11.glEnable(3089);
        RenderUtils.makeScissorBox(0.0f, (orgY + initY) - (4.0f * scaleY), scaledRes.func_78326_a(), (orgY + cutY) - (4.0f * scaleY));
        Intrinsics.checkNotNullExpressionValue(reFormat, "reFormat");
        char[] charArray = reFormat.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int i = 0;
        int length = charArray.length;
        while (i < length) {
            char c = charArray[i];
            i++;
            this.moveX[indexX] = AnimationUtils.animate(animX, this.moveX[indexX], fontSpeed * 0.025f * delta);
            float animX2 = this.moveX[indexX];
            int pos = this.numberList.indexOf(String.valueOf(c));
            float expectAnim = (fontRend.field_78288_b + 2.0f) * pos;
            float expectAnimMin = (fontRend.field_78288_b + 2.0f) * (pos - 2);
            float expectAnimMax = (fontRend.field_78288_b + 2.0f) * (pos + 2);
            if (pos >= 0) {
                this.moveY[indexY] = AnimationUtils.animate(expectAnim, this.moveY[indexY], fontSpeed * 0.02f * delta);
                GL11.glTranslatef(0.0f, initY - this.moveY[indexY], 0.0f);
                Iterable $this$forEachIndexed$iv = this.numberList;
                int index$iv = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    int index = index$iv;
                    index$iv = index + 1;
                    if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    String num = (String) item$iv;
                    if ((fontRend.field_78288_b + 2.0f) * index >= expectAnimMin && (fontRend.field_78288_b + 2.0f) * index <= expectAnimMax) {
                        fontRend.func_175065_a(num, initX + getMoveX()[indexX], (fontRend.field_78288_b + 2.0f) * index, color, shadow);
                    }
                }
                GL11.glTranslatef(0.0f, (-initY) + this.moveY[indexY], 0.0f);
            } else {
                this.moveY[indexY] = 0.0f;
                fontRend.func_175065_a(String.valueOf(c), initX + this.moveX[indexX], initY, color, shadow);
            }
            animX = animX2 + fontRend.func_78256_a(String.valueOf(c));
            indexX++;
            indexY++;
        }
        GL11.glDisable(3089);
        return animX;
    }
}
