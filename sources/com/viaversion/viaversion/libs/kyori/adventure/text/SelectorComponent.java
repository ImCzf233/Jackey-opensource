package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/SelectorComponent.class */
public interface SelectorComponent extends BuildableComponent<SelectorComponent, Builder>, ScopedComponent<SelectorComponent> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/SelectorComponent$Builder.class */
    public interface Builder extends ComponentBuilder<SelectorComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder pattern(@NotNull final String pattern);

        @Contract("_ -> this")
        @NotNull
        Builder separator(@Nullable final ComponentLike separator);
    }

    @NotNull
    String pattern();

    @Contract(pure = true)
    @NotNull
    SelectorComponent pattern(@NotNull final String pattern);

    @Nullable
    Component separator();

    @NotNull
    SelectorComponent separator(@Nullable final ComponentLike separator);
}
