package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/NamedTextColor.class */
public final class NamedTextColor implements TextColor {
    private static final int BLACK_VALUE = 0;
    private static final int DARK_BLUE_VALUE = 170;
    private final String name;
    private final int value;
    private final HSVLike hsv = HSVLike.fromRGB(red(), green(), blue());
    public static final NamedTextColor BLACK = new NamedTextColor("black", 0);
    public static final NamedTextColor DARK_BLUE = new NamedTextColor("dark_blue", 170);
    private static final int DARK_GREEN_VALUE = 43520;
    public static final NamedTextColor DARK_GREEN = new NamedTextColor("dark_green", DARK_GREEN_VALUE);
    private static final int DARK_AQUA_VALUE = 43690;
    public static final NamedTextColor DARK_AQUA = new NamedTextColor("dark_aqua", DARK_AQUA_VALUE);
    private static final int DARK_RED_VALUE = 11141120;
    public static final NamedTextColor DARK_RED = new NamedTextColor("dark_red", DARK_RED_VALUE);
    private static final int DARK_PURPLE_VALUE = 11141290;
    public static final NamedTextColor DARK_PURPLE = new NamedTextColor("dark_purple", DARK_PURPLE_VALUE);
    private static final int GOLD_VALUE = 16755200;
    public static final NamedTextColor GOLD = new NamedTextColor("gold", GOLD_VALUE);
    private static final int GRAY_VALUE = 11184810;
    public static final NamedTextColor GRAY = new NamedTextColor("gray", GRAY_VALUE);
    private static final int DARK_GRAY_VALUE = 5592405;
    public static final NamedTextColor DARK_GRAY = new NamedTextColor("dark_gray", DARK_GRAY_VALUE);
    private static final int BLUE_VALUE = 5592575;
    public static final NamedTextColor BLUE = new NamedTextColor("blue", BLUE_VALUE);
    private static final int GREEN_VALUE = 5635925;
    public static final NamedTextColor GREEN = new NamedTextColor("green", GREEN_VALUE);
    private static final int AQUA_VALUE = 5636095;
    public static final NamedTextColor AQUA = new NamedTextColor("aqua", AQUA_VALUE);
    private static final int RED_VALUE = 16733525;
    public static final NamedTextColor RED = new NamedTextColor("red", RED_VALUE);
    private static final int LIGHT_PURPLE_VALUE = 16733695;
    public static final NamedTextColor LIGHT_PURPLE = new NamedTextColor("light_purple", LIGHT_PURPLE_VALUE);
    private static final int YELLOW_VALUE = 16777045;
    public static final NamedTextColor YELLOW = new NamedTextColor("yellow", YELLOW_VALUE);
    private static final int WHITE_VALUE = 16777215;
    public static final NamedTextColor WHITE = new NamedTextColor("white", WHITE_VALUE);
    private static final List<NamedTextColor> VALUES = Collections.unmodifiableList(Arrays.asList(BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE));
    public static final Index<String, NamedTextColor> NAMES = Index.create(constant -> {
        return constant.name;
    }, VALUES);

    @Nullable
    public static NamedTextColor ofExact(final int value) {
        if (value == 0) {
            return BLACK;
        }
        if (value == 170) {
            return DARK_BLUE;
        }
        if (value == DARK_GREEN_VALUE) {
            return DARK_GREEN;
        }
        if (value == DARK_AQUA_VALUE) {
            return DARK_AQUA;
        }
        if (value == DARK_RED_VALUE) {
            return DARK_RED;
        }
        if (value == DARK_PURPLE_VALUE) {
            return DARK_PURPLE;
        }
        if (value == GOLD_VALUE) {
            return GOLD;
        }
        if (value == GRAY_VALUE) {
            return GRAY;
        }
        if (value == DARK_GRAY_VALUE) {
            return DARK_GRAY;
        }
        if (value == BLUE_VALUE) {
            return BLUE;
        }
        if (value == GREEN_VALUE) {
            return GREEN;
        }
        if (value == AQUA_VALUE) {
            return AQUA;
        }
        if (value == RED_VALUE) {
            return RED;
        }
        if (value == LIGHT_PURPLE_VALUE) {
            return LIGHT_PURPLE;
        }
        if (value == YELLOW_VALUE) {
            return YELLOW;
        }
        if (value != WHITE_VALUE) {
            return null;
        }
        return WHITE;
    }

    @NotNull
    public static NamedTextColor nearestTo(@NotNull final TextColor any) {
        if (any instanceof NamedTextColor) {
            return (NamedTextColor) any;
        }
        Objects.requireNonNull(any, "color");
        float matchedDistance = Float.MAX_VALUE;
        NamedTextColor match = VALUES.get(0);
        int length = VALUES.size();
        for (int i = 0; i < length; i++) {
            NamedTextColor potential = VALUES.get(i);
            float distance = distance(any.asHSV(), potential.asHSV());
            if (distance < matchedDistance) {
                match = potential;
                matchedDistance = distance;
            }
            if (distance == 0.0f) {
                break;
            }
        }
        return match;
    }

    private static float distance(@NotNull final HSVLike self, @NotNull final HSVLike other) {
        float hueDistance = 3.0f * Math.min(Math.abs(self.mo100h() - other.mo100h()), 1.0f - Math.abs(self.mo100h() - other.mo100h()));
        float saturationDiff = self.mo99s() - other.mo99s();
        float valueDiff = self.mo98v() - other.mo98v();
        return (hueDistance * hueDistance) + (saturationDiff * saturationDiff) + (valueDiff * valueDiff);
    }

    private NamedTextColor(final String name, final int value) {
        this.name = name;
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor
    public int value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike
    @NotNull
    public HSVLike asHSV() {
        return this.hsv;
    }

    @NotNull
    public String toString() {
        return this.name;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor, com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(Stream.of(ExaminableProperty.m90of("name", this.name)), super.examinableProperties());
    }
}
