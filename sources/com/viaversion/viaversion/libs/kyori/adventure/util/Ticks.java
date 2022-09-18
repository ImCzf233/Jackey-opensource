package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.time.Duration;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Ticks.class */
public interface Ticks {
    public static final int TICKS_PER_SECOND = 20;
    public static final long SINGLE_TICK_DURATION_MS = 50;

    @NotNull
    static Duration duration(final long ticks) {
        return Duration.ofMillis(ticks * 50);
    }
}
