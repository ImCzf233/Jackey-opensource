package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.PointersImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/Pointers.class */
public interface Pointers extends Buildable<Pointers, Builder> {
    @NotNull
    <T> Optional<T> get(@NotNull final Pointer<T> pointer);

    <T> boolean supports(@NotNull final Pointer<T> pointer);

    @Contract(pure = true)
    @NotNull
    static Pointers empty() {
        return PointersImpl.EMPTY;
    }

    @Contract(pure = true)
    @NotNull
    static Builder builder() {
        return new PointersImpl.BuilderImpl();
    }

    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default <T> T getOrDefault(@NotNull final Pointer<T> pointer, @Nullable final T defaultValue) {
        return get(pointer).orElse(defaultValue);
    }

    default <T> T getOrDefaultFrom(@NotNull final Pointer<T> pointer, @NotNull final Supplier<? extends T> defaultValue) {
        return get(pointer).orElseGet(defaultValue);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/Pointers$Builder.class */
    public interface Builder extends Buildable.Builder<Pointers> {
        @Contract("_, _ -> this")
        @NotNull
        <T> Builder withDynamic(@NotNull final Pointer<T> pointer, @NotNull Supplier<T> value);

        @Contract("_, _ -> this")
        @NotNull
        default <T> Builder withStatic(@NotNull final Pointer<T> pointer, @Nullable final T value) {
            return withDynamic(pointer, () -> {
                return value;
            });
        }
    }
}
