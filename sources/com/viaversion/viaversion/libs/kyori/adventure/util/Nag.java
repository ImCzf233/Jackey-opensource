package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Nag.class */
public abstract class Nag extends RuntimeException {
    public static void print(@NotNull final Nag nag) {
        nag.printStackTrace();
    }

    public Nag(final String message) {
        super(message);
    }
}
