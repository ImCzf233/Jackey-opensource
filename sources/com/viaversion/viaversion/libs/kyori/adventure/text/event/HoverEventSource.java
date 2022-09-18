package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEventSource.class */
public interface HoverEventSource<V> {
    @NotNull
    HoverEvent<V> asHoverEvent(@NotNull final UnaryOperator<V> op);

    @Nullable
    static <V> HoverEvent<V> unbox(@Nullable final HoverEventSource<V> source) {
        if (source != null) {
            return source.asHoverEvent();
        }
        return null;
    }

    @NotNull
    default HoverEvent<V> asHoverEvent() {
        return asHoverEvent(UnaryOperator.identity());
    }
}
