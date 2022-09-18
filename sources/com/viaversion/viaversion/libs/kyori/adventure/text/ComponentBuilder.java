package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ComponentBuilder.class */
public interface ComponentBuilder<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>> extends Buildable.Builder<C>, ComponentBuilderApplicable, ComponentLike {
    @Contract("_ -> this")
    @NotNull
    B append(@NotNull final Component component);

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull final Component... components);

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull final ComponentLike... components);

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull final Iterable<? extends ComponentLike> components);

    @Contract("_ -> this")
    @NotNull
    B applyDeep(@NotNull final Consumer<? super ComponentBuilder<?, ?>> action);

    @Contract("_ -> this")
    @NotNull
    B mapChildren(@NotNull final Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function);

    @Contract("_ -> this")
    @NotNull
    B mapChildrenDeep(@NotNull final Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function);

    @NotNull
    List<Component> children();

    @Contract("_ -> this")
    @NotNull
    B style(@NotNull final Style style);

    @Contract("_ -> this")
    @NotNull
    B style(@NotNull final Consumer<Style.Builder> consumer);

    @Contract("_ -> this")
    @NotNull
    B font(@Nullable final Key font);

    @Contract("_ -> this")
    @NotNull
    B color(@Nullable final TextColor color);

    @Contract("_ -> this")
    @NotNull
    B colorIfAbsent(@Nullable final TextColor color);

    @Contract("_, _ -> this")
    @NotNull
    B decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state);

    @Contract("_ -> this")
    @NotNull
    B clickEvent(@Nullable final ClickEvent event);

    @Contract("_ -> this")
    @NotNull
    B hoverEvent(@Nullable final HoverEventSource<?> source);

    @Contract("_ -> this")
    @NotNull
    B insertion(@Nullable final String insertion);

    @Contract("_, _ -> this")
    @NotNull
    B mergeStyle(@NotNull final Component that, @NotNull final Set<Style.Merge> merges);

    @NotNull
    B resetStyle();

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
    @NotNull
    C build();

    @Contract("_ -> this")
    @NotNull
    default B append(@NotNull final ComponentLike component) {
        return append(component.asComponent());
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent] */
    @Contract("_ -> this")
    @NotNull
    default B append(@NotNull final ComponentBuilder<?, ?> builder) {
        return append((Component) builder.build());
    }

    @Contract("_ -> this")
    @NotNull
    default B apply(@NotNull final Consumer<? super ComponentBuilder<?, ?>> consumer) {
        consumer.accept(this);
        return this;
    }

    @Contract("_, _ -> this")
    @NotNull
    default B decorations(@NotNull final Set<TextDecoration> decorations, final boolean flag) {
        TextDecoration.State state = TextDecoration.State.byBoolean(flag);
        decorations.forEach(decoration -> {
            decoration(state, state);
        });
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    default B decorate(@NotNull final TextDecoration decoration) {
        return decoration(decoration, TextDecoration.State.TRUE);
    }

    @Contract("_ -> this")
    @NotNull
    default B decorate(@NotNull final TextDecoration... decorations) {
        for (TextDecoration textDecoration : decorations) {
            decorate(textDecoration);
        }
        return this;
    }

    @Contract("_, _ -> this")
    @NotNull
    default B decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return decoration(decoration, TextDecoration.State.byBoolean(flag));
    }

    @Contract("_ -> this")
    @NotNull
    default B mergeStyle(@NotNull final Component that) {
        return mergeStyle(that, Style.Merge.all());
    }

    @Contract("_, _ -> this")
    @NotNull
    default B mergeStyle(@NotNull final Component that, final Style.Merge... merges) {
        return mergeStyle(that, Style.Merge.m107of(merges));
    }

    @Contract("_ -> this")
    @NotNull
    default B applicableApply(@NotNull final ComponentBuilderApplicable applicable) {
        applicable.componentBuilderApply(this);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable
    default void componentBuilderApply(@NotNull final ComponentBuilder<?, ?> component) {
        component.append((ComponentBuilder<?, ?>) this);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike
    @NotNull
    default Component asComponent() {
        return build();
    }
}
