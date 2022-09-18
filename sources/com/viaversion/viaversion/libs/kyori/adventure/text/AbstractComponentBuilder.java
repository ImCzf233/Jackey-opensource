package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/AbstractComponentBuilder.class */
public abstract class AbstractComponentBuilder<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>> implements ComponentBuilder<C, B> {
    protected List<Component> children;
    @Nullable
    private Style style;
    private Style.Builder styleBuilder;

    public AbstractComponentBuilder() {
        this.children = Collections.emptyList();
    }

    public AbstractComponentBuilder(@NotNull final C component) {
        this.children = Collections.emptyList();
        List<Component> children = component.children();
        if (!children.isEmpty()) {
            this.children = new ArrayList(children);
        }
        if (component.hasStyling()) {
            this.style = component.style();
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B append(@NotNull final Component component) {
        if (component == Component.empty()) {
            return this;
        }
        prepareChildren();
        this.children.add((Component) Objects.requireNonNull(component, "component"));
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B append(@NotNull final Component... components) {
        Objects.requireNonNull(components, "components");
        boolean prepared = false;
        for (Component component : components) {
            if (component != Component.empty()) {
                if (!prepared) {
                    prepareChildren();
                    prepared = true;
                }
                this.children.add((Component) Objects.requireNonNull(component, "components[?]"));
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B append(@NotNull final ComponentLike... components) {
        Objects.requireNonNull(components, "components");
        boolean prepared = false;
        for (ComponentLike componentLike : components) {
            Component component = componentLike.asComponent();
            if (component != Component.empty()) {
                if (!prepared) {
                    prepareChildren();
                    prepared = true;
                }
                this.children.add((Component) Objects.requireNonNull(component, "components[?]"));
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B append(@NotNull final Iterable<? extends ComponentLike> components) {
        Objects.requireNonNull(components, "components");
        boolean prepared = false;
        for (ComponentLike like : components) {
            Component component = like.asComponent();
            if (component != Component.empty()) {
                if (!prepared) {
                    prepareChildren();
                    prepared = true;
                }
                this.children.add((Component) Objects.requireNonNull(component, "components[?]"));
            }
        }
        return this;
    }

    private void prepareChildren() {
        if (this.children == Collections.emptyList()) {
            this.children = new ArrayList();
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B applyDeep(@NotNull final Consumer<? super ComponentBuilder<?, ?>> consumer) {
        apply(consumer);
        if (this.children == Collections.emptyList()) {
            return this;
        }
        ListIterator<Component> it = this.children.listIterator();
        while (it.hasNext()) {
            Component child = it.next();
            if (child instanceof BuildableComponent) {
                ComponentBuilder<?, ?> childBuilder = ((BuildableComponent) child).toBuilder();
                childBuilder.applyDeep(consumer);
                it.set(childBuilder.build());
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B mapChildren(@NotNull final Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function) {
        BuildableComponent<?, ?> mappedChild;
        if (this.children == Collections.emptyList()) {
            return this;
        }
        ListIterator<Component> it = this.children.listIterator();
        while (it.hasNext()) {
            Component child = it.next();
            if ((child instanceof BuildableComponent) && child != (mappedChild = (BuildableComponent) Objects.requireNonNull(function.apply((BuildableComponent) child), "mappedChild"))) {
                it.set(mappedChild);
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B mapChildrenDeep(@NotNull final Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function) {
        if (this.children == Collections.emptyList()) {
            return this;
        }
        ListIterator<Component> it = this.children.listIterator();
        while (it.hasNext()) {
            Component child = it.next();
            if (child instanceof BuildableComponent) {
                BuildableComponent<?, ?> mappedChild = (BuildableComponent) Objects.requireNonNull(function.apply((BuildableComponent) child), "mappedChild");
                if (mappedChild.children().isEmpty()) {
                    if (child != mappedChild) {
                        it.set(mappedChild);
                    }
                } else {
                    ComponentBuilder builder = mappedChild.toBuilder();
                    builder.mapChildrenDeep(function);
                    it.set(builder.build());
                }
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public List<Component> children() {
        return Collections.unmodifiableList(this.children);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B style(@NotNull final Style style) {
        this.style = style;
        this.styleBuilder = null;
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B style(@NotNull final Consumer<Style.Builder> consumer) {
        consumer.accept(styleBuilder());
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B font(@Nullable final Key font) {
        styleBuilder().font(font);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B color(@Nullable final TextColor color) {
        styleBuilder().color(color);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B colorIfAbsent(@Nullable final TextColor color) {
        styleBuilder().colorIfAbsent(color);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        styleBuilder().decoration(decoration, state);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B clickEvent(@Nullable final ClickEvent event) {
        styleBuilder().clickEvent(event);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B hoverEvent(@Nullable final HoverEventSource<?> source) {
        styleBuilder().hoverEvent(source);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B insertion(@Nullable final String insertion) {
        styleBuilder().insertion(insertion);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B mergeStyle(@NotNull final Component that, @NotNull final Set<Style.Merge> merges) {
        styleBuilder().merge(that.style(), merges);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder
    @NotNull
    public B resetStyle() {
        this.style = null;
        this.styleBuilder = null;
        return this;
    }

    private Style.Builder styleBuilder() {
        if (this.styleBuilder == null) {
            if (this.style != null) {
                this.styleBuilder = this.style.toBuilder();
                this.style = null;
            } else {
                this.styleBuilder = Style.style();
            }
        }
        return this.styleBuilder;
    }

    public final boolean hasStyle() {
        return (this.styleBuilder == null && this.style == null) ? false : true;
    }

    @NotNull
    public Style buildStyle() {
        if (this.styleBuilder != null) {
            return this.styleBuilder.build();
        }
        if (this.style != null) {
            return this.style;
        }
        return Style.empty();
    }
}
