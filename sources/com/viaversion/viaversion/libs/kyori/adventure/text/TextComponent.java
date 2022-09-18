package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent.class */
public interface TextComponent extends BuildableComponent<TextComponent, Builder>, ScopedComponent<TextComponent> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder.class */
    public interface Builder extends ComponentBuilder<TextComponent, Builder> {
        @NotNull
        String content();

        @Contract("_ -> this")
        @NotNull
        Builder content(@NotNull final String content);
    }

    @NotNull
    String content();

    @Contract(pure = true)
    @NotNull
    TextComponent content(@NotNull final String content);

    @ApiStatus.ScheduledForRemoval
    @Deprecated
    @NotNull
    static TextComponent ofChildren(@NotNull final ComponentLike... components) {
        Component joined = Component.join(JoinConfiguration.noSeparators(), components);
        return joined instanceof TextComponent ? (TextComponent) joined : Component.text().append(joined).build();
    }
}
