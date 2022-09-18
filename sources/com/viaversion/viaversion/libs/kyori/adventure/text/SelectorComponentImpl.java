package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/SelectorComponentImpl.class */
public final class SelectorComponentImpl extends AbstractComponent implements SelectorComponent {
    private final String pattern;
    @Nullable
    private final Component separator;

    public SelectorComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, @NotNull final String pattern, @Nullable final ComponentLike separator) {
        super(children, style);
        this.pattern = pattern;
        this.separator = ComponentLike.unbox(separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent
    @NotNull
    public String pattern() {
        return this.pattern;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent
    @NotNull
    public SelectorComponent pattern(@NotNull final String pattern) {
        return Objects.equals(this.pattern, pattern) ? this : new SelectorComponentImpl(this.children, this.style, (String) Objects.requireNonNull(pattern, "pattern"), this.separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent
    @NotNull
    public SelectorComponent separator(@Nullable final ComponentLike separator) {
        return new SelectorComponentImpl(this.children, this.style, this.pattern, separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public SelectorComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new SelectorComponentImpl(children, this.style, this.pattern, this.separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public SelectorComponent style(@NotNull final Style style) {
        return new SelectorComponentImpl(this.children, style, this.pattern, this.separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SelectorComponent) || !super.equals(other)) {
            return false;
        }
        SelectorComponent that = (SelectorComponent) other;
        return Objects.equals(this.pattern, that.pattern()) && Objects.equals(this.separator, that.separator());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * ((31 * result) + this.pattern.hashCode())) + Objects.hashCode(this.separator);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of("pattern", this.pattern), ExaminableProperty.m91of("separator", this.separator)}), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public SelectorComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/SelectorComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends AbstractComponentBuilder<SelectorComponent, SelectorComponent.Builder> implements SelectorComponent.Builder {
        @Nullable
        private String pattern;
        @Nullable
        private Component separator;

        public BuilderImpl() {
        }

        BuilderImpl(@NotNull final SelectorComponent component) {
            super(component);
            this.pattern = component.pattern();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent.Builder
        @NotNull
        public SelectorComponent.Builder pattern(@NotNull final String pattern) {
            this.pattern = pattern;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent.Builder
        @NotNull
        public SelectorComponent.Builder separator(@Nullable final ComponentLike separator) {
            this.separator = ComponentLike.unbox(separator);
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public SelectorComponent build() {
            if (this.pattern == null) {
                throw new IllegalStateException("pattern must be set");
            }
            return new SelectorComponentImpl(this.children, buildStyle(), this.pattern, this.separator);
        }
    }
}
