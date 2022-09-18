package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ScoreComponent.class */
public interface ScoreComponent extends BuildableComponent<ScoreComponent, Builder>, ScopedComponent<ScoreComponent> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ScoreComponent$Builder.class */
    public interface Builder extends ComponentBuilder<ScoreComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder name(@NotNull final String name);

        @Contract("_ -> this")
        @NotNull
        Builder objective(@NotNull final String objective);

        @Contract("_ -> this")
        @Deprecated
        @NotNull
        Builder value(@Nullable final String value);
    }

    @NotNull
    String name();

    @Contract(pure = true)
    @NotNull
    ScoreComponent name(@NotNull final String name);

    @NotNull
    String objective();

    @Contract(pure = true)
    @NotNull
    ScoreComponent objective(@NotNull final String objective);

    @Deprecated
    @Nullable
    String value();

    @Contract(pure = true)
    @Deprecated
    @NotNull
    ScoreComponent value(@Nullable final String value);
}
