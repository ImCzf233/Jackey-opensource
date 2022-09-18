package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/flattener/FlattenerListener.class */
public interface FlattenerListener {
    void component(@NotNull final String text);

    default void pushStyle(@NotNull final Style style) {
    }

    default void popStyle(@NotNull final Style style) {
    }
}
