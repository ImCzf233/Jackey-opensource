package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/TextColor.class */
public interface TextColor extends Comparable<TextColor>, Examinable, RGBLike, StyleBuilderApplicable, TextFormat {
    int value();

    @NotNull
    static TextColor color(final int value) {
        int truncatedValue = value & 16777215;
        NamedTextColor named = NamedTextColor.ofExact(truncatedValue);
        return named != null ? named : new TextColorImpl(truncatedValue);
    }

    @NotNull
    static TextColor color(@NotNull final RGBLike rgb) {
        return rgb instanceof TextColor ? (TextColor) rgb : color(rgb.red(), rgb.green(), rgb.blue());
    }

    @NotNull
    static TextColor color(@NotNull final HSVLike hsv) {
        float s = hsv.mo99s();
        float v = hsv.mo98v();
        if (s == 0.0f) {
            return color(v, v, v);
        }
        float h = hsv.mo100h() * 6.0f;
        int i = (int) Math.floor(h);
        float f = h - i;
        float p = v * (1.0f - s);
        float q = v * (1.0f - (s * f));
        float t = v * (1.0f - (s * (1.0f - f)));
        if (i == 0) {
            return color(v, t, p);
        }
        if (i == 1) {
            return color(q, v, p);
        }
        if (i == 2) {
            return color(p, v, t);
        }
        if (i == 3) {
            return color(p, q, v);
        }
        if (i == 4) {
            return color(t, p, v);
        }
        return color(v, p, q);
    }

    @NotNull
    static TextColor color(final int r, final int g, final int b) {
        return color(((r & 255) << 16) | ((g & 255) << 8) | (b & 255));
    }

    @NotNull
    static TextColor color(final float r, final float g, final float b) {
        return color((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f));
    }

    @Nullable
    static TextColor fromHexString(@NotNull final String string) {
        if (string.startsWith("#")) {
            try {
                int hex = Integer.parseInt(string.substring(1), 16);
                return color(hex);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @Nullable
    static TextColor fromCSSHexString(@NotNull final String string) {
        if (string.startsWith("#")) {
            String hexString = string.substring(1);
            if (hexString.length() != 3 && hexString.length() != 6) {
                return null;
            }
            try {
                int hex = Integer.parseInt(hexString, 16);
                if (hexString.length() == 6) {
                    return color(hex);
                }
                int red = ((hex & 3840) >> 8) | ((hex & 3840) >> 4);
                int green = ((hex & 240) >> 4) | (hex & 240);
                int blue = ((hex & 15) << 4) | (hex & 15);
                return color(red, green, blue);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @NotNull
    default String asHexString() {
        return String.format("#%06x", Integer.valueOf(value()));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike
    default int red() {
        return (value() >> 16) & 255;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike
    default int green() {
        return (value() >> 8) & 255;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike
    default int blue() {
        return value() & 255;
    }

    @NotNull
    static TextColor lerp(final float t, @NotNull final RGBLike a, @NotNull final RGBLike b) {
        float clampedT = Math.min(1.0f, Math.max(0.0f, t));
        int ar = a.red();
        int br = b.red();
        int ag = a.green();
        int bg = b.green();
        int ab = a.blue();
        int bb = b.blue();
        return color(Math.round(ar + (clampedT * (br - ar))), Math.round(ag + (clampedT * (bg - ag))), Math.round(ab + (clampedT * (bb - ab))));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable
    default void styleApply(final Style.Builder style) {
        style.color(this);
    }

    default int compareTo(final TextColor that) {
        return Integer.compare(value(), that.value());
    }

    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m90of("value", asHexString()));
    }
}
