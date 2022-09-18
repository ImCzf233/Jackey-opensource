package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/EntityNBTComponentImpl.class */
public final class EntityNBTComponentImpl extends NBTComponentImpl<EntityNBTComponent, EntityNBTComponent.Builder> implements EntityNBTComponent {
    private final String selector;

    EntityNBTComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final String selector) {
        super(children, style, nbtPath, interpret, separator);
        this.selector = selector;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public EntityNBTComponent nbtPath(@NotNull final String nbtPath) {
        return Objects.equals(this.nbtPath, nbtPath) ? this : new EntityNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.selector);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public EntityNBTComponent interpret(final boolean interpret) {
        return this.interpret == interpret ? this : new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.selector);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public EntityNBTComponent separator(@Nullable final ComponentLike separator) {
        return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.selector);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent
    @NotNull
    public String selector() {
        return this.selector;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent
    @NotNull
    public EntityNBTComponent selector(@NotNull final String selector) {
        return Objects.equals(this.selector, selector) ? this : new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, selector);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public EntityNBTComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new EntityNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.selector);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public EntityNBTComponent style(@NotNull final Style style) {
        return new EntityNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.selector);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EntityNBTComponent) || !super.equals(other)) {
            return false;
        }
        EntityNBTComponentImpl that = (EntityNBTComponentImpl) other;
        return Objects.equals(this.selector, that.selector());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + this.selector.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.m90of("selector", this.selector)), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    public EntityNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/EntityNBTComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends NBTComponentImpl.BuilderImpl<EntityNBTComponent, EntityNBTComponent.Builder> implements EntityNBTComponent.Builder {
        @Nullable
        private String selector;

        public BuilderImpl() {
        }

        BuilderImpl(@NotNull final EntityNBTComponent component) {
            super(component);
            this.selector = component.selector();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent.Builder
        public EntityNBTComponent.Builder selector(@NotNull final String selector) {
            this.selector = selector;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public EntityNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.selector != null) {
                return new EntityNBTComponentImpl(this.children, buildStyle(), this.nbtPath, this.interpret, this.separator, this.selector);
            }
            throw new IllegalStateException("selector must be set");
        }
    }
}
