package com.viaversion.viaversion.libs.kyori.examination.string;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/string/Strings.class */
final class Strings {
    private Strings() {
    }

    @NotNull
    public static String withSuffix(final String string, final char suffix) {
        return string + suffix;
    }

    @NotNull
    public static String wrapIn(final String string, final char wrap) {
        return wrap + string + wrap;
    }

    public static int maxLength(final Stream<String> strings) {
        return strings.mapToInt((v0) -> {
            return v0.length();
        }).max().orElse(0);
    }

    @NotNull
    public static String repeat(@NotNull final String string, final int count) {
        if (count == 0) {
            return "";
        }
        if (count == 1) {
            return string;
        }
        StringBuilder sb = new StringBuilder(string.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(string);
        }
        return sb.toString();
    }

    @NotNull
    public static String padEnd(@NotNull final String string, final int minLength, final char padding) {
        return string.length() >= minLength ? string : String.format("%-" + minLength + "s", Character.valueOf(padding));
    }
}
