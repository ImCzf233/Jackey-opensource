package de.gerrygames.viarewind;

import de.gerrygames.viarewind.api.ViaRewindConfig;
import de.gerrygames.viarewind.api.ViaRewindPlatform;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/ViaRewind.class */
public class ViaRewind {
    private static ViaRewindPlatform platform;
    private static ViaRewindConfig config;

    public static void init(ViaRewindPlatform platform2, ViaRewindConfig config2) {
        platform = platform2;
        config = config2;
    }

    public static ViaRewindPlatform getPlatform() {
        return platform;
    }

    public static ViaRewindConfig getConfig() {
        return config;
    }
}
