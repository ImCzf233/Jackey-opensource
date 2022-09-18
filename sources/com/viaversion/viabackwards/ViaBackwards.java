package com.viaversion.viabackwards;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.api.ViaBackwardsPlatform;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/ViaBackwards.class */
public class ViaBackwards {
    private static ViaBackwardsPlatform platform;
    private static com.viaversion.viabackwards.api.ViaBackwardsConfig config;

    public static void init(ViaBackwardsPlatform platform2, com.viaversion.viabackwards.api.ViaBackwardsConfig config2) {
        Preconditions.checkArgument(platform2 != null, "ViaBackwards is already initialized");
        platform = platform2;
        config = config2;
    }

    public static ViaBackwardsPlatform getPlatform() {
        return platform;
    }

    public static com.viaversion.viabackwards.api.ViaBackwardsConfig getConfig() {
        return config;
    }
}
