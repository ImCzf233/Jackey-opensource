package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/Pointered.class */
public interface Pointered {
    @NotNull
    default <T> Optional<T> get(@NotNull final Pointer<T> pointer) {
        return pointers().get(pointer);
    }

    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default <T> T getOrDefault(@NotNull final Pointer<T> pointer, @Nullable final T defaultValue) {
        return (T) pointers().getOrDefault(pointer, defaultValue);
    }

    default <T> T getOrDefaultFrom(@NotNull final Pointer<T> pointer, @NotNull final Supplier<? extends T> defaultValue) {
        return (T) pointers().getOrDefaultFrom(pointer, defaultValue);
    }

    @NotNull
    default Pointers pointers() {
        return Pointers.empty();
    }
}
