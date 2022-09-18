package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/TriState.class */
public enum TriState {
    NOT_SET,
    FALSE,
    TRUE;

    @NotNull
    public static TriState byBoolean(final boolean value) {
        return value ? TRUE : FALSE;
    }

    @NotNull
    public static TriState byBoolean(@Nullable final Boolean value) {
        return value == null ? NOT_SET : byBoolean(value.booleanValue());
    }
}
