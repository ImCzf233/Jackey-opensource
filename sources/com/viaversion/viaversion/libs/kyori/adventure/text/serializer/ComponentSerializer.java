package com.viaversion.viaversion.libs.kyori.adventure.text.serializer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/ComponentSerializer.class */
public interface ComponentSerializer<I extends Component, O extends Component, R> {
    @NotNull
    O deserialize(@NotNull final R input);

    @NotNull
    R serialize(@NotNull final I component);

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Deprecated
    @Nullable
    @ApiStatus.ScheduledForRemoval
    default O deseializeOrNull(@Nullable final R input) {
        return deserializeOrNull(input);
    }

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default O deserializeOrNull(@Nullable final R input) {
        return deserializeOr(input, null);
    }

    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    default O deserializeOr(@Nullable final R input, @Nullable final O fallback) {
        return input == null ? fallback : deserialize(input);
    }

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default R serializeOrNull(@Nullable final I component) {
        return serializeOr(component, null);
    }

    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    default R serializeOr(@Nullable final I component, @Nullable final R fallback) {
        return component == null ? fallback : serialize(component);
    }
}
