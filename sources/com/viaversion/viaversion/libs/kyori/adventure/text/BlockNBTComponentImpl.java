package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.ShadyPines;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponentImpl.class */
public final class BlockNBTComponentImpl extends NBTComponentImpl<BlockNBTComponent, BlockNBTComponent.Builder> implements BlockNBTComponent {
    private final BlockNBTComponent.Pos pos;

    public BlockNBTComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final BlockNBTComponent.Pos pos) {
        super(children, style, nbtPath, interpret, separator);
        this.pos = pos;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public BlockNBTComponent nbtPath(@NotNull final String nbtPath) {
        return Objects.equals(this.nbtPath, nbtPath) ? this : new BlockNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.pos);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public BlockNBTComponent interpret(final boolean interpret) {
        return this.interpret == interpret ? this : new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.pos);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent
    @NotNull
    public BlockNBTComponent separator(@Nullable final ComponentLike separator) {
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.pos);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent
    @NotNull
    public BlockNBTComponent.Pos pos() {
        return this.pos;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent
    @NotNull
    public BlockNBTComponent pos(@NotNull final BlockNBTComponent.Pos pos) {
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, pos);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public BlockNBTComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new BlockNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.pos);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public BlockNBTComponent style(@NotNull final Style style) {
        return new BlockNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.pos);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlockNBTComponent) || !super.equals(other)) {
            return false;
        }
        BlockNBTComponent that = (BlockNBTComponent) other;
        return Objects.equals(this.pos, that.pos());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + this.pos.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl, com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.m91of("pos", this.pos)), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    public BlockNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends NBTComponentImpl.BuilderImpl<BlockNBTComponent, BlockNBTComponent.Builder> implements BlockNBTComponent.Builder {
        @Nullable
        private BlockNBTComponent.Pos pos;

        public BuilderImpl() {
        }

        BuilderImpl(@NotNull final BlockNBTComponent component) {
            super(component);
            this.pos = component.pos();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.Builder
        public BlockNBTComponent.Builder pos(@NotNull final BlockNBTComponent.Pos pos) {
            this.pos = pos;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public BlockNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.pos != null) {
                return new BlockNBTComponentImpl(this.children, buildStyle(), this.nbtPath, this.interpret, this.separator, this.pos);
            }
            throw new IllegalStateException("pos must be set");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponentImpl$LocalPosImpl.class */
    public static final class LocalPosImpl implements BlockNBTComponent.LocalPos {
        private final double left;

        /* renamed from: up */
        private final double f179up;
        private final double forwards;

        public LocalPosImpl(final double left, final double up, final double forwards) {
            this.left = left;
            this.f179up = up;
            this.forwards = forwards;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.LocalPos
        public double left() {
            return this.left;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.LocalPos
        /* renamed from: up */
        public double mo121up() {
            return this.f179up;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.LocalPos
        public double forwards() {
            return this.forwards;
        }

        @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m95of("left", this.left), ExaminableProperty.m95of("up", this.f179up), ExaminableProperty.m95of("forwards", this.forwards)});
        }

        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BlockNBTComponent.LocalPos)) {
                return false;
            }
            BlockNBTComponent.LocalPos that = (BlockNBTComponent.LocalPos) other;
            return ShadyPines.equals(that.left(), left()) && ShadyPines.equals(that.mo121up(), mo121up()) && ShadyPines.equals(that.forwards(), forwards());
        }

        public int hashCode() {
            int result = Double.hashCode(this.left);
            return (31 * ((31 * result) + Double.hashCode(this.f179up))) + Double.hashCode(this.forwards);
        }

        public String toString() {
            return String.format("^%f ^%f ^%f", Double.valueOf(this.left), Double.valueOf(this.f179up), Double.valueOf(this.forwards));
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.Pos
        @NotNull
        public String asString() {
            return Tokens.serializeLocal(this.left) + ' ' + Tokens.serializeLocal(this.f179up) + ' ' + Tokens.serializeLocal(this.forwards);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponentImpl$WorldPosImpl.class */
    public static final class WorldPosImpl implements BlockNBTComponent.WorldPos {

        /* renamed from: x */
        private final BlockNBTComponent.WorldPos.Coordinate f180x;

        /* renamed from: y */
        private final BlockNBTComponent.WorldPos.Coordinate f181y;

        /* renamed from: z */
        private final BlockNBTComponent.WorldPos.Coordinate f182z;

        public WorldPosImpl(final BlockNBTComponent.WorldPos.Coordinate x, final BlockNBTComponent.WorldPos.Coordinate y, final BlockNBTComponent.WorldPos.Coordinate z) {
            this.f180x = (BlockNBTComponent.WorldPos.Coordinate) Objects.requireNonNull(x, "x");
            this.f181y = (BlockNBTComponent.WorldPos.Coordinate) Objects.requireNonNull(y, "y");
            this.f182z = (BlockNBTComponent.WorldPos.Coordinate) Objects.requireNonNull(z, "z");
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos
        @NotNull
        /* renamed from: x */
        public BlockNBTComponent.WorldPos.Coordinate mo120x() {
            return this.f180x;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos
        @NotNull
        /* renamed from: y */
        public BlockNBTComponent.WorldPos.Coordinate mo119y() {
            return this.f181y;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos
        @NotNull
        /* renamed from: z */
        public BlockNBTComponent.WorldPos.Coordinate mo118z() {
            return this.f182z;
        }

        @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("x", this.f180x), ExaminableProperty.m91of("y", this.f181y), ExaminableProperty.m91of("z", this.f182z)});
        }

        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BlockNBTComponent.WorldPos)) {
                return false;
            }
            BlockNBTComponent.WorldPos that = (BlockNBTComponent.WorldPos) other;
            return this.f180x.equals(that.mo120x()) && this.f181y.equals(that.mo119y()) && this.f182z.equals(that.mo118z());
        }

        public int hashCode() {
            int result = this.f180x.hashCode();
            return (31 * ((31 * result) + this.f181y.hashCode())) + this.f182z.hashCode();
        }

        public String toString() {
            return this.f180x.toString() + ' ' + this.f181y.toString() + ' ' + this.f182z.toString();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.Pos
        @NotNull
        public String asString() {
            return Tokens.serializeCoordinate(mo120x()) + ' ' + Tokens.serializeCoordinate(mo119y()) + ' ' + Tokens.serializeCoordinate(mo118z());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponentImpl$WorldPosImpl$CoordinateImpl.class */
        public static final class CoordinateImpl implements BlockNBTComponent.WorldPos.Coordinate {
            private final int value;
            private final BlockNBTComponent.WorldPos.Coordinate.Type type;

            public CoordinateImpl(final int value, @NotNull final BlockNBTComponent.WorldPos.Coordinate.Type type) {
                this.value = value;
                this.type = (BlockNBTComponent.WorldPos.Coordinate.Type) Objects.requireNonNull(type, "type");
            }

            @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate
            public int value() {
                return this.value;
            }

            @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate
            @NotNull
            public BlockNBTComponent.WorldPos.Coordinate.Type type() {
                return this.type;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
            @NotNull
            public Stream<? extends ExaminableProperty> examinableProperties() {
                return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m93of("value", this.value), ExaminableProperty.m91of("type", this.type)});
            }

            public boolean equals(@Nullable final Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof BlockNBTComponent.WorldPos.Coordinate)) {
                    return false;
                }
                BlockNBTComponent.WorldPos.Coordinate that = (BlockNBTComponent.WorldPos.Coordinate) other;
                return value() == that.value() && type() == that.type();
            }

            public int hashCode() {
                int result = this.value;
                return (31 * result) + this.type.hashCode();
            }

            public String toString() {
                return (this.type == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE ? "~" : "") + this.value;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponentImpl$Tokens.class */
    public static final class Tokens {
        static final Pattern LOCAL_PATTERN = Pattern.compile("^\\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?)$");
        static final Pattern WORLD_PATTERN = Pattern.compile("^(~?)(\\d+) (~?)(\\d+) (~?)(\\d+)$");
        static final String LOCAL_SYMBOL = "^";
        static final String RELATIVE_SYMBOL = "~";
        static final String ABSOLUTE_SYMBOL = "";

        private Tokens() {
        }

        public static BlockNBTComponent.WorldPos.Coordinate deserializeCoordinate(final String prefix, final String value) {
            int i = Integer.parseInt(value);
            if (prefix.equals(ABSOLUTE_SYMBOL)) {
                return BlockNBTComponent.WorldPos.Coordinate.absolute(i);
            }
            if (prefix.equals(RELATIVE_SYMBOL)) {
                return BlockNBTComponent.WorldPos.Coordinate.relative(i);
            }
            throw new AssertionError();
        }

        static String serializeLocal(final double value) {
            return LOCAL_SYMBOL + value;
        }

        static String serializeCoordinate(final BlockNBTComponent.WorldPos.Coordinate coordinate) {
            return (coordinate.type() == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE ? RELATIVE_SYMBOL : ABSOLUTE_SYMBOL) + coordinate.value();
        }
    }
}
