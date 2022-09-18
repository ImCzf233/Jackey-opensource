package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/CompoundTagSetter.class */
public interface CompoundTagSetter<R> {
    @NotNull
    R put(@NotNull final String key, @NotNull final BinaryTag tag);

    @NotNull
    R put(@NotNull final CompoundBinaryTag tag);

    @NotNull
    R put(@NotNull final Map<String, ? extends BinaryTag> tags);

    @NotNull
    R remove(@NotNull final String key, @Nullable final Consumer<? super BinaryTag> removed);

    @NotNull
    default R remove(@NotNull final String key) {
        return remove(key, null);
    }

    @NotNull
    default R putBoolean(@NotNull final String key, final boolean value) {
        return put(key, value ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
    }

    @NotNull
    default R putByte(@NotNull final String key, final byte value) {
        return put(key, ByteBinaryTag.m136of(value));
    }

    @NotNull
    default R putShort(@NotNull final String key, final short value) {
        return put(key, ShortBinaryTag.m128of(value));
    }

    @NotNull
    default R putInt(@NotNull final String key, final int value) {
        return put(key, IntBinaryTag.m132of(value));
    }

    @NotNull
    default R putLong(@NotNull final String key, final long value) {
        return put(key, LongBinaryTag.m129of(value));
    }

    @NotNull
    default R putFloat(@NotNull final String key, final float value) {
        return put(key, FloatBinaryTag.m134of(value));
    }

    @NotNull
    default R putDouble(@NotNull final String key, final double value) {
        return put(key, DoubleBinaryTag.m135of(value));
    }

    @NotNull
    default R putByteArray(@NotNull final String key, final byte[] value) {
        return put(key, ByteArrayBinaryTag.m137of(value));
    }

    @NotNull
    default R putString(@NotNull final String key, @NotNull final String value) {
        return put(key, StringBinaryTag.m127of(value));
    }

    @NotNull
    default R putIntArray(@NotNull final String key, final int[] value) {
        return put(key, IntArrayBinaryTag.m133of(value));
    }

    @NotNull
    default R putLongArray(@NotNull final String key, final long[] value) {
        return put(key, LongArrayBinaryTag.m130of(value));
    }
}
