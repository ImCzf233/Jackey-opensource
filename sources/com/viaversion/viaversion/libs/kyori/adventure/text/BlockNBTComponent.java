package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.regex.Matcher;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent.class */
public interface BlockNBTComponent extends NBTComponent<BlockNBTComponent, Builder>, ScopedComponent<BlockNBTComponent> {
    @NotNull
    Pos pos();

    @Contract(pure = true)
    @NotNull
    BlockNBTComponent pos(@NotNull final Pos pos);

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent localPos(final double left, final double up, final double forwards) {
        return pos(LocalPos.m124of(left, up, forwards));
    }

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent worldPos(final WorldPos.Coordinate x, final WorldPos.Coordinate y, final WorldPos.Coordinate z) {
        return pos(WorldPos.m123of(x, y, z));
    }

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent absoluteWorldPos(final int x, final int y, final int z) {
        return worldPos(WorldPos.Coordinate.absolute(x), WorldPos.Coordinate.absolute(y), WorldPos.Coordinate.absolute(z));
    }

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent relativeWorldPos(final int x, final int y, final int z) {
        return worldPos(WorldPos.Coordinate.relative(x), WorldPos.Coordinate.relative(y), WorldPos.Coordinate.relative(z));
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent$Builder.class */
    public interface Builder extends NBTComponentBuilder<BlockNBTComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder pos(@NotNull final Pos pos);

        @Contract("_, _, _ -> this")
        @NotNull
        default Builder localPos(final double left, final double up, final double forwards) {
            return pos(LocalPos.m124of(left, up, forwards));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default Builder worldPos(final WorldPos.Coordinate x, final WorldPos.Coordinate y, final WorldPos.Coordinate z) {
            return pos(WorldPos.m123of(x, y, z));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default Builder absoluteWorldPos(final int x, final int y, final int z) {
            return worldPos(WorldPos.Coordinate.absolute(x), WorldPos.Coordinate.absolute(y), WorldPos.Coordinate.absolute(z));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default Builder relativeWorldPos(final int x, final int y, final int z) {
            return worldPos(WorldPos.Coordinate.relative(x), WorldPos.Coordinate.relative(y), WorldPos.Coordinate.relative(z));
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent$Pos.class */
    public interface Pos extends Examinable {
        @NotNull
        String asString();

        @NotNull
        static Pos fromString(@NotNull final String input) throws IllegalArgumentException {
            Matcher localMatch = BlockNBTComponentImpl.Tokens.LOCAL_PATTERN.matcher(input);
            if (localMatch.matches()) {
                return LocalPos.m124of(Double.parseDouble(localMatch.group(1)), Double.parseDouble(localMatch.group(3)), Double.parseDouble(localMatch.group(5)));
            }
            Matcher worldMatch = BlockNBTComponentImpl.Tokens.WORLD_PATTERN.matcher(input);
            if (worldMatch.matches()) {
                return WorldPos.m123of(BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(1), worldMatch.group(2)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(3), worldMatch.group(4)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(5), worldMatch.group(6)));
            }
            throw new IllegalArgumentException("Cannot convert position specification '" + input + "' into a position");
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent$LocalPos.class */
    public interface LocalPos extends Pos {
        double left();

        /* renamed from: up */
        double mo121up();

        double forwards();

        @NotNull
        /* renamed from: of */
        static LocalPos m124of(final double left, final double up, final double forwards) {
            return new BlockNBTComponentImpl.LocalPosImpl(left, up, forwards);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent$WorldPos.class */
    public interface WorldPos extends Pos {
        @NotNull
        /* renamed from: x */
        Coordinate mo120x();

        @NotNull
        /* renamed from: y */
        Coordinate mo119y();

        @NotNull
        /* renamed from: z */
        Coordinate mo118z();

        @NotNull
        /* renamed from: of */
        static WorldPos m123of(@NotNull final Coordinate x, @NotNull final Coordinate y, @NotNull final Coordinate z) {
            return new BlockNBTComponentImpl.WorldPosImpl(x, y, z);
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent$WorldPos$Coordinate.class */
        public interface Coordinate extends Examinable {

            /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/BlockNBTComponent$WorldPos$Coordinate$Type.class */
            public enum Type {
                ABSOLUTE,
                RELATIVE
            }

            int value();

            @NotNull
            Type type();

            @NotNull
            static Coordinate absolute(final int value) {
                return m122of(value, Type.ABSOLUTE);
            }

            @NotNull
            static Coordinate relative(final int value) {
                return m122of(value, Type.RELATIVE);
            }

            @NotNull
            /* renamed from: of */
            static Coordinate m122of(final int value, @NotNull final Type type) {
                return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(value, type);
            }
        }
    }
}
