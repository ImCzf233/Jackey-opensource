package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTag.class */
public interface IntArrayBinaryTag extends ArrayBinaryTag, Iterable<Integer> {
    int[] value();

    int size();

    int get(final int index);

    @Override // java.lang.Iterable
    /* renamed from: iterator */
    Iterator<Integer> iterator2();

    @Override // java.lang.Iterable
    /* renamed from: spliterator */
    Spliterator<Integer> spliterator2();

    @NotNull
    IntStream stream();

    void forEachInt(@NotNull final IntConsumer action);

    @NotNull
    /* renamed from: of */
    static IntArrayBinaryTag m133of(final int... value) {
        return new IntArrayBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<IntArrayBinaryTag> type() {
        return BinaryTagTypes.INT_ARRAY;
    }
}
