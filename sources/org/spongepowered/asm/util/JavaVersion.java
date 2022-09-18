package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/JavaVersion.class */
public abstract class JavaVersion {
    private static double current = 0.0d;

    private JavaVersion() {
    }

    public static double current() {
        if (current == 0.0d) {
            current = resolveCurrentVersion();
        }
        return current;
    }

    private static double resolveCurrentVersion() {
        String version = System.getProperty("java.version");
        Matcher matcher = Pattern.compile("[0-9]+\\.[0-9]+").matcher(version);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 1.6d;
    }
}
