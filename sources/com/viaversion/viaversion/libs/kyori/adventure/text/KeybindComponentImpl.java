package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/KeybindComponentImpl.class */
public final class KeybindComponentImpl extends AbstractComponent implements KeybindComponent {
    private final String keybind;

    public KeybindComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, @NotNull final String keybind) {
        super(children, style);
        this.keybind = (String) Objects.requireNonNull(keybind, "keybind");
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent
    @NotNull
    public String keybind() {
        return this.keybind;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent
    @NotNull
    public KeybindComponent keybind(@NotNull final String keybind) {
        return Objects.equals(this.keybind, keybind) ? this : new KeybindComponentImpl(this.children, this.style, (String) Objects.requireNonNull(keybind, "keybind"));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public KeybindComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new KeybindComponentImpl(children, this.style, this.keybind);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public KeybindComponent style(@NotNull final Style style) {
        return new KeybindComponentImpl(this.children, style, this.keybind);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KeybindComponent) || !super.equals(other)) {
            return false;
        }
        KeybindComponent that = (KeybindComponent) other;
        return Objects.equals(this.keybind, that.keybind());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + this.keybind.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.m90of("keybind", this.keybind)), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public KeybindComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/KeybindComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends AbstractComponentBuilder<KeybindComponent, KeybindComponent.Builder> implements KeybindComponent.Builder {
        @Nullable
        private String keybind;

        public BuilderImpl() {
        }

        BuilderImpl(@NotNull final KeybindComponent component) {
            super(component);
            this.keybind = component.keybind();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent.Builder
        @NotNull
        public KeybindComponent.Builder keybind(@NotNull final String keybind) {
            this.keybind = keybind;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public KeybindComponent build() {
            if (this.keybind == null) {
                throw new IllegalStateException("keybind must be set");
            }
            return new KeybindComponentImpl(this.children, buildStyle(), this.keybind);
        }
    }
}
