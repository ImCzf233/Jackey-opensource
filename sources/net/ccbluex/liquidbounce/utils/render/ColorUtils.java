package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ColorUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0007\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0007J\u0018\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000fH\u0007J \u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tH\u0007J\b\u0010\u0018\u001a\u00020\tH\u0007J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000fH\u0007J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\rH\u0007J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000bH\u0007J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000fH\u0007J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\rH\u0007J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\u0018\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000fH\u0007J\u0018\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\rH\u0007J\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001aH\u0007J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001aH\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n��¨\u0006!"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/render/ColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "hexColors", "", "LiquidSlowly", "Ljava/awt/Color;", "time", "", "count", "", "qd", "", "sq", "TwoRainbow", "offset", "alpha", "fade", "color", "index", "getOppositeColor", "rainbow", "randomMagicText", "", "text", "reAlpha", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/ColorUtils.class */
public final class ColorUtils {
    @NotNull
    public static final ColorUtils INSTANCE = new ColorUtils();
    private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
    @JvmPlatformAnnotations
    @NotNull
    public static final int[] hexColors = new int[16];

    private ColorUtils() {
    }

    static {
        int i = 0;
        while (i < 16) {
            int i2 = i;
            i++;
            int baseColor = ((i2 >> 3) & 1) * 85;
            int red = (((i2 >> 2) & 1) * 170) + baseColor + (i2 == 6 ? 85 : 0);
            int green = (((i2 >> 1) & 1) * 170) + baseColor;
            int blue = ((i2 & 1) * 170) + baseColor;
            hexColors[i2] = ((red & 255) << 16) | ((green & 255) << 8) | (blue & 255);
        }
    }

    @JvmStatic
    @Nullable
    public static final String stripColor(@Nullable String input) {
        Pattern pattern = COLOR_PATTERN;
        if (input == null) {
            return null;
        }
        return pattern.matcher(input).replaceAll("");
    }

    @JvmStatic
    @NotNull
    public static final String translateAlternateColorCodes(@NotNull String textToTranslate) {
        Intrinsics.checkNotNullParameter(textToTranslate, "textToTranslate");
        char[] chars = textToTranslate.toCharArray();
        Intrinsics.checkNotNullExpressionValue(chars, "this as java.lang.String).toCharArray()");
        int i = 0;
        int length = chars.length - 1;
        while (i < length) {
            int i2 = i;
            i++;
            if (chars[i2] == '&' && StringsKt.contains((CharSequence) "0123456789AaBbCcDdEeFfKkLlMmNnOoRr", chars[i2 + 1], true)) {
                chars[i2] = 167;
                chars[i2 + 1] = Character.toLowerCase(chars[i2 + 1]);
            }
        }
        return new String(chars);
    }

    @NotNull
    public final String randomMagicText(@NotNull String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        StringBuilder stringBuilder = new StringBuilder();
        char[] charArray = text.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int i = 0;
        int length = charArray.length;
        while (i < length) {
            char c = charArray[i];
            i++;
            if (ChatAllowedCharacters.func_71566_a(c)) {
                int index = new Random().nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ�������������� !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~��ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■��".length());
                char[] charArray2 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ�������������� !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~��ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■��".toCharArray();
                Intrinsics.checkNotNullExpressionValue(charArray2, "this as java.lang.String).toCharArray()");
                stringBuilder.append(charArray2[index]);
            }
        }
        String sb = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "stringBuilder.toString()");
        return sb;
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow() {
        Color currentColor = new Color(Color.HSBtoRGB((((float) (System.nanoTime() + 400000)) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((((float) (System.nanoTime() + offset)) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(float alpha) {
        ColorUtils colorUtils = INSTANCE;
        return rainbow(400000L, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int alpha) {
        ColorUtils colorUtils = INSTANCE;
        return rainbow(400000L, alpha / 255);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, int alpha) {
        ColorUtils colorUtils = INSTANCE;
        return rainbow(offset, alpha / 255);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB((((float) (System.nanoTime() + offset)) / 1.0E10f) % 1, 1.0f, 1.0f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, alpha);
    }

    @JvmStatic
    @Nullable
    public static final Color LiquidSlowly(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((((float) time) + (count * (-3000000.0f))) / 2) / 1.0E9f, qd, sq));
        return new Color((color.getRed() / 255.0f) * 1, (color.getGreen() / 255.0f) * 1, (color.getBlue() / 255.0f) * 1, color.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color TwoRainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB((((float) (System.nanoTime() + offset)) / 8.9999999E10f) % 1, 0.75f, 0.8f));
        return new Color((currentColor.getRed() / 255.0f) * 1.0f, (currentColor.getGreen() / 255.0f) * 1.0f, (currentColor.getBlue() / 255.0f) * 1.0f, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color fade(@NotNull Color color, int index, int count) {
        Intrinsics.checkNotNullParameter(color, "color");
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((((((float) (System.currentTimeMillis() % 2000)) / 1000.0f) + ((index / count) * 2.0f)) % 2.0f) - 1.0f);
        float[] hsb = {0.0f, 0.0f, (0.5f + (0.5f * brightness)) % 2.0f};
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, int alpha) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), RangesKt.coerceIn(alpha, 0, 255));
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, float alpha) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, RangesKt.coerceIn(alpha, 0.0f, 1.0f));
    }

    @JvmStatic
    @NotNull
    public static final Color getOppositeColor(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
    }
}
