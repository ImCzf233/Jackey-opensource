package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/NBTComponentImpl.class */
public abstract class NBTComponentImpl<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends AbstractComponent implements NBTComponent<C, B> {
    static final boolean INTERPRET_DEFAULT = false;
    final String nbtPath;
    final boolean interpret;
    @Nullable
    final Component separator;

    public NBTComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator) {
        super(children, style);
        this.nbtPath = nbtPath;
        this.interpret = interpret;
        this.separator = ComponentLike.unbox(separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public String nbtPath() {
        return this.nbtPath;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    public boolean interpret() {
        return this.interpret;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NBTComponent) || !super.equals(other)) {
            return false;
        }
        NBTComponent<?, ?> that = (NBTComponent) other;
        return Objects.equals(this.nbtPath, that.nbtPath()) && this.interpret == that.interpret() && Objects.equals(this.separator, that.separator());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * ((31 * ((31 * result) + this.nbtPath.hashCode())) + Boolean.hashCode(this.interpret))) + Objects.hashCode(this.separator);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of("nbtPath", this.nbtPath), ExaminableProperty.m88of("interpret", this.interpret), ExaminableProperty.m91of("separator", this.separator)}), super.examinablePropertiesWithoutChildren());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/NBTComponentImpl$BuilderImpl.class */
    public static abstract class BuilderImpl<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends AbstractComponentBuilder<C, B> implements NBTComponentBuilder<C, B> {
        @Nullable
        protected String nbtPath;
        protected boolean interpret;
        @Nullable
        protected Component separator;

        public BuilderImpl() {
            this.interpret = false;
        }

        public BuilderImpl(@NotNull final C component) {
            super(component);
            this.interpret = false;
            this.nbtPath = component.nbtPath();
            this.interpret = component.interpret();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder
        @NotNull
        public B nbtPath(@NotNull final String nbtPath) {
            this.nbtPath = nbtPath;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder
        @NotNull
        public B interpret(final boolean interpret) {
            this.interpret = interpret;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder
        @NotNull
        public B separator(@Nullable final ComponentLike separator) {
            this.separator = ComponentLike.unbox(separator);
            return this;
        }
    }
}
