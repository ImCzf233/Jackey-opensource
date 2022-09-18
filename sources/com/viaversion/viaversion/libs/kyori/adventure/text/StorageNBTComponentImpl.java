package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/StorageNBTComponentImpl.class */
public final class StorageNBTComponentImpl extends NBTComponentImpl<StorageNBTComponent, StorageNBTComponent.Builder> implements StorageNBTComponent {
    private final Key storage;

    public StorageNBTComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final Key storage) {
        super(children, style, nbtPath, interpret, separator);
        this.storage = storage;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public StorageNBTComponent nbtPath(@NotNull final String nbtPath) {
        return Objects.equals(this.nbtPath, nbtPath) ? this : new StorageNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.storage);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public StorageNBTComponent interpret(final boolean interpret) {
        return this.interpret == interpret ? this : new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.storage);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public StorageNBTComponent separator(@Nullable final ComponentLike separator) {
        return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.storage);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent
    @NotNull
    public Key storage() {
        return this.storage;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent
    @NotNull
    public StorageNBTComponent storage(@NotNull final Key storage) {
        return Objects.equals(this.storage, storage) ? this : new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, storage);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public StorageNBTComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new StorageNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.storage);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public StorageNBTComponent style(@NotNull final Style style) {
        return new StorageNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.storage);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StorageNBTComponent) || !super.equals(other)) {
            return false;
        }
        StorageNBTComponentImpl that = (StorageNBTComponentImpl) other;
        return Objects.equals(this.storage, that.storage());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + this.storage.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.m91of("storage", this.storage)), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    public StorageNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/StorageNBTComponentImpl$BuilderImpl.class */
    public static class BuilderImpl extends NBTComponentImpl.BuilderImpl<StorageNBTComponent, StorageNBTComponent.Builder> implements StorageNBTComponent.Builder {
        @Nullable
        private Key storage;

        public BuilderImpl() {
        }

        BuilderImpl(@NotNull final StorageNBTComponent component) {
            super(component);
            this.storage = component.storage();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent.Builder
        public StorageNBTComponent.Builder storage(@NotNull final Key storage) {
            this.storage = storage;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public StorageNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.storage != null) {
                return new StorageNBTComponentImpl(this.children, buildStyle(), this.nbtPath, this.interpret, this.separator, this.storage);
            }
            throw new IllegalStateException("storage must be set");
        }
    }
}
