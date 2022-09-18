package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent.class */
public interface TranslatableComponent extends BuildableComponent<TranslatableComponent, Builder>, ScopedComponent<TranslatableComponent> {
    @NotNull
    String key();

    @Contract(pure = true)
    @NotNull
    TranslatableComponent key(@NotNull final String key);

    @NotNull
    List<Component> args();

    @Contract(pure = true)
    @NotNull
    TranslatableComponent args(@NotNull final ComponentLike... args);

    @Contract(pure = true)
    @NotNull
    TranslatableComponent args(@NotNull final List<? extends ComponentLike> args);

    @Contract(pure = true)
    @NotNull
    default TranslatableComponent key(@NotNull final Translatable translatable) {
        return key(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent$Builder.class */
    public interface Builder extends ComponentBuilder<TranslatableComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder key(@NotNull final String key);

        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final ComponentBuilder<?, ?> arg);

        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final ComponentBuilder<?, ?>... args);

        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final Component arg);

        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final ComponentLike... args);

        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final List<? extends ComponentLike> args);

        @Contract(pure = true)
        @NotNull
        default Builder key(@NotNull final Translatable translatable) {
            return key(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey());
        }
    }
}
