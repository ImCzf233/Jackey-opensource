package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ScopedComponent.class */
public interface ScopedComponent<C extends Component> extends Component {
    @Override // 
    @NotNull
    C children(@NotNull final List<? extends ComponentLike> children);

    @Override // 
    @NotNull
    C style(@NotNull final Style style);

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C style(@NotNull final Consumer<Style.Builder> style) {
        return (C) super.style(style);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C style(final Style.Builder style) {
        return (C) super.style(style);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C mergeStyle(@NotNull final Component that) {
        return (C) super.mergeStyle(that);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C mergeStyle(@NotNull final Component that, final Style.Merge... merges) {
        return (C) super.mergeStyle(that, merges);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C append(@NotNull final Component component) {
        if (component == Component.empty()) {
            return this;
        }
        List<Component> oldChildren = children();
        return children(MonkeyBars.addOne(oldChildren, (Component) Objects.requireNonNull(component, "component")));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C append(@NotNull final ComponentLike component) {
        return (C) super.append(component);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C append(@NotNull final ComponentBuilder<?, ?> builder) {
        return (C) super.append(builder);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C mergeStyle(@NotNull final Component that, @NotNull final Set<Style.Merge> merges) {
        return (C) super.mergeStyle(that, merges);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C color(@Nullable final TextColor color) {
        return (C) super.color(color);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C colorIfAbsent(@Nullable final TextColor color) {
        return (C) super.colorIfAbsent(color);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default Component decorate(@NotNull final TextDecoration decoration) {
        return super.decorate(decoration);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return (C) super.decoration(decoration, flag);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        return (C) super.decoration(decoration, state);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C clickEvent(@Nullable final ClickEvent event) {
        return (C) super.clickEvent(event);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C hoverEvent(@Nullable final HoverEventSource<?> event) {
        return (C) super.hoverEvent(event);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    default C insertion(@Nullable final String insertion) {
        return (C) super.insertion(insertion);
    }
}
