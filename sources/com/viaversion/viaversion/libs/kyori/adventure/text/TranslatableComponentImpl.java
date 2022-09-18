package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponentImpl.class */
public final class TranslatableComponentImpl extends AbstractComponent implements TranslatableComponent {
    private final String key;
    private final List<Component> args;

    public TranslatableComponentImpl(@NotNull final List<Component> children, @NotNull final Style style, @NotNull final String key, @NotNull final ComponentLike[] args) {
        this(children, style, key, Arrays.asList(args));
    }

    public TranslatableComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, @NotNull final String key, @NotNull final List<? extends ComponentLike> args) {
        super(children, style);
        this.key = (String) Objects.requireNonNull(key, LocalCacheFactory.KEY);
        this.args = ComponentLike.asComponents(args);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent
    @NotNull
    public String key() {
        return this.key;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent
    @NotNull
    public TranslatableComponent key(@NotNull final String key) {
        return Objects.equals(this.key, key) ? this : new TranslatableComponentImpl(this.children, this.style, (String) Objects.requireNonNull(key, LocalCacheFactory.KEY), this.args);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent
    @NotNull
    public List<Component> args() {
        return this.args;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent
    @NotNull
    public TranslatableComponent args(@NotNull final ComponentLike... args) {
        return new TranslatableComponentImpl(this.children, this.style, this.key, args);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent
    @NotNull
    public TranslatableComponent args(@NotNull final List<? extends ComponentLike> args) {
        return new TranslatableComponentImpl(this.children, this.style, this.key, args);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public TranslatableComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new TranslatableComponentImpl(children, this.style, this.key, this.args);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public TranslatableComponent style(@NotNull final Style style) {
        return new TranslatableComponentImpl(this.children, style, this.key, this.args);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslatableComponent) || !super.equals(other)) {
            return false;
        }
        TranslatableComponent that = (TranslatableComponent) other;
        return Objects.equals(this.key, that.key()) && Objects.equals(this.args, that.args());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * ((31 * result) + this.key.hashCode())) + this.args.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of(LocalCacheFactory.KEY, this.key), ExaminableProperty.m91of("args", this.args)}), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public TranslatableComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends AbstractComponentBuilder<TranslatableComponent, TranslatableComponent.Builder> implements TranslatableComponent.Builder {
        @Nullable
        private String key;
        private List<? extends Component> args;

        public BuilderImpl() {
            this.args = Collections.emptyList();
        }

        BuilderImpl(@NotNull final TranslatableComponent component) {
            super(component);
            this.args = Collections.emptyList();
            this.key = component.key();
            this.args = component.args();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent.Builder
        @NotNull
        public TranslatableComponent.Builder key(@NotNull final String key) {
            this.key = key;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent.Builder
        @NotNull
        public TranslatableComponent.Builder args(@NotNull final ComponentBuilder<?, ?> arg) {
            return args(Collections.singletonList(arg.build()));
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent.Builder
        @NotNull
        public TranslatableComponent.Builder args(@NotNull final ComponentBuilder<?, ?>... args) {
            return args.length == 0 ? args(Collections.emptyList()) : args((List) Stream.of((Object[]) args).map((v0) -> {
                return v0.build();
            }).collect(Collectors.toList()));
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent.Builder
        @NotNull
        public TranslatableComponent.Builder args(@NotNull final Component arg) {
            return args(Collections.singletonList(arg));
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent.Builder
        @NotNull
        public TranslatableComponent.Builder args(@NotNull final ComponentLike... args) {
            return args.length == 0 ? args(Collections.emptyList()) : args(Arrays.asList(args));
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent.Builder
        @NotNull
        public TranslatableComponent.Builder args(@NotNull final List<? extends ComponentLike> args) {
            this.args = ComponentLike.asComponents(args);
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public TranslatableComponentImpl build() {
            if (this.key == null) {
                throw new IllegalStateException("key must be set");
            }
            return new TranslatableComponentImpl(this.children, buildStyle(), this.key, this.args);
        }
    }
}
