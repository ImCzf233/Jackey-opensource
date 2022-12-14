package com.viaversion.viaversion.libs.kyori.examination;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/Examiner.class */
public interface Examiner<R> {
    @NotNull
    R examine(@NotNull final String name, @NotNull final Stream<? extends ExaminableProperty> properties);

    @NotNull
    R examine(@Nullable final Object value);

    @NotNull
    R examine(final boolean value);

    @NotNull
    R examine(final boolean[] values);

    @NotNull
    R examine(final byte value);

    @NotNull
    R examine(final byte[] values);

    @NotNull
    R examine(final char value);

    @NotNull
    R examine(final char[] values);

    @NotNull
    R examine(final double value);

    @NotNull
    R examine(final double[] values);

    @NotNull
    R examine(final float value);

    @NotNull
    R examine(final float[] values);

    @NotNull
    R examine(final int value);

    @NotNull
    R examine(final int[] values);

    @NotNull
    R examine(final long value);

    @NotNull
    R examine(final long[] values);

    @NotNull
    R examine(final short value);

    @NotNull
    R examine(final short[] values);

    @NotNull
    R examine(@Nullable final String value);

    @NotNull
    default R examine(@NotNull final Examinable examinable) {
        return examine(examinable.examinableName(), examinable.examinableProperties());
    }
}
