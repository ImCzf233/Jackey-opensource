package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ScoreComponentImpl.class */
public final class ScoreComponentImpl extends AbstractComponent implements ScoreComponent {
    private final String name;
    private final String objective;
    @Deprecated
    @Nullable
    private final String value;

    public ScoreComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, @NotNull final String name, @NotNull final String objective, @Nullable final String value) {
        super(children, style);
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent
    @NotNull
    public String name() {
        return this.name;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent
    @NotNull
    public ScoreComponent name(@NotNull final String name) {
        return Objects.equals(this.name, name) ? this : new ScoreComponentImpl(this.children, this.style, (String) Objects.requireNonNull(name, "name"), this.objective, this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent
    @NotNull
    public String objective() {
        return this.objective;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent
    @NotNull
    public ScoreComponent objective(@NotNull final String objective) {
        return Objects.equals(this.objective, objective) ? this : new ScoreComponentImpl(this.children, this.style, this.name, (String) Objects.requireNonNull(objective, "objective"), this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent
    @Deprecated
    @Nullable
    public String value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent
    @Deprecated
    @NotNull
    public ScoreComponent value(@Nullable final String value) {
        return Objects.equals(this.value, value) ? this : new ScoreComponentImpl(this.children, this.style, this.name, this.objective, value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public ScoreComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new ScoreComponentImpl(children, this.style, this.name, this.objective, this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public ScoreComponent style(@NotNull final Style style) {
        return new ScoreComponentImpl(this.children, style, this.name, this.objective, this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreComponent) || !super.equals(other)) {
            return false;
        }
        ScoreComponent that = (ScoreComponent) other;
        return Objects.equals(this.name, that.name()) && Objects.equals(this.objective, that.objective()) && Objects.equals(this.value, that.value());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * ((31 * ((31 * result) + this.name.hashCode())) + this.objective.hashCode())) + Objects.hashCode(this.value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of("name", this.name), ExaminableProperty.m90of("objective", this.objective), ExaminableProperty.m90of("value", this.value)}), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public ScoreComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ScoreComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends AbstractComponentBuilder<ScoreComponent, ScoreComponent.Builder> implements ScoreComponent.Builder {
        @Nullable
        private String name;
        @Nullable
        private String objective;
        @Nullable
        private String value;

        public BuilderImpl() {
        }

        BuilderImpl(@NotNull final ScoreComponent component) {
            super(component);
            this.name = component.name();
            this.objective = component.objective();
            this.value = component.value();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent.Builder
        @NotNull
        public ScoreComponent.Builder name(@NotNull final String name) {
            this.name = name;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent.Builder
        @NotNull
        public ScoreComponent.Builder objective(@NotNull final String objective) {
            this.objective = objective;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent.Builder
        @Deprecated
        @NotNull
        public ScoreComponent.Builder value(@Nullable final String value) {
            this.value = value;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public ScoreComponent build() {
            if (this.name == null) {
                throw new IllegalStateException("name must be set");
            }
            if (this.objective != null) {
                return new ScoreComponentImpl(this.children, buildStyle(), this.name, this.objective, this.value);
            }
            throw new IllegalStateException("objective must be set");
        }
    }
}
