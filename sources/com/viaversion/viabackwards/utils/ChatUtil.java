package com.viaversion.viabackwards.utils;

import java.util.regex.Pattern;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/utils/ChatUtil.class */
public class ChatUtil {
    private static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
    private static final Pattern UNUSED_COLOR_PATTERN_PREFIX = Pattern.compile("(?>(?>§[0-fk-or])*(§r))|(?>(?>§[0-f])*(§[0-f]))");

    public static String removeUnusedColor(String legacy, char defaultColor) {
        return removeUnusedColor(legacy, defaultColor, false);
    }

    public static String removeUnusedColor(String legacy, char defaultColor, boolean isPrefix) {
        if (legacy == null) {
            return null;
        }
        Pattern pattern = isPrefix ? UNUSED_COLOR_PATTERN_PREFIX : UNUSED_COLOR_PATTERN;
        String legacy2 = pattern.matcher(legacy).replaceAll("$1$2");
        StringBuilder builder = new StringBuilder();
        char last = defaultColor;
        int i = 0;
        while (i < legacy2.length()) {
            char current = legacy2.charAt(i);
            if (current != 167 || i == legacy2.length() - 1) {
                builder.append(current);
            } else {
                i++;
                char current2 = legacy2.charAt(i);
                if (current2 != last) {
                    builder.append((char) 167).append(current2);
                    last = current2;
                }
            }
            i++;
        }
        return builder.toString();
    }
}
