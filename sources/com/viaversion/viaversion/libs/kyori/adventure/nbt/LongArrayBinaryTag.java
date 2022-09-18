package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/LongArrayBinaryTag.class */
public interface LongArrayBinaryTag extends ArrayBinaryTag, Iterable<Long> {
    long[] value();

    int size();

    long get(final int index);

    @Override // java.lang.Iterable
    /* renamed from: iterator */
    Iterator<Long> iterator2();

    @Override // java.lang.Iterable
    /* renamed from: spliterator */
    Spliterator<Long> spliterator2();

    @NotNull
    LongStream stream();

    void forEachLong(@NotNull final LongConsumer action);

    @NotNull
    /* renamed from: of */
    static LongArrayBinaryTag m130of(final long... value) {
        return new LongArrayBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<LongArrayBinaryTag> type() {
        return BinaryTagTypes.LONG_ARRAY;
    }
}
