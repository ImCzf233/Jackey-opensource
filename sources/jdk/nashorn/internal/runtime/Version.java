package jdk.nashorn.internal.runtime;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Version.class */
public final class Version {
    private static final String VERSION_RB_NAME = "jdk.nashorn.internal.runtime.resources.version";
    private static ResourceBundle versionRB;

    private Version() {
    }

    public static String version() {
        return version("release");
    }

    public static String fullVersion() {
        return version("full");
    }

    private static String version(String key) {
        if (versionRB == null) {
            try {
                versionRB = ResourceBundle.getBundle(VERSION_RB_NAME);
            } catch (MissingResourceException e) {
                return "version not available";
            }
        }
        try {
            return versionRB.getString(key);
        } catch (MissingResourceException e2) {
            return "version not available";
        }
    }
}
