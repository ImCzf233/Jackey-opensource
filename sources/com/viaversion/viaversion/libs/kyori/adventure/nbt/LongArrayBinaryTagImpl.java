package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text = "\"long[\" + this.value.length + \"]\"", childrenArray = "this.value", hasChildren = "this.value.length > 0")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/LongArrayBinaryTagImpl.class */
public final class LongArrayBinaryTagImpl extends ArrayBinaryTagImpl implements LongArrayBinaryTag {
    final long[] value;

    public LongArrayBinaryTagImpl(final long[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag
    public long[] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag
    public int size() {
        return this.value.length;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag
    public long get(final int index) {
        checkIndex(index, this.value.length);
        return this.value[index];
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag, java.lang.Iterable
    /* renamed from: iterator */
    public Iterator<Long> iterator2() {
        return new PrimitiveIterator.OfLong() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTagImpl.1
            private int index;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < LongArrayBinaryTagImpl.this.value.length - 1;
            }

            @Override // java.util.PrimitiveIterator.OfLong
            public long nextLong() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                long[] jArr = LongArrayBinaryTagImpl.this.value;
                int i = this.index;
                this.index = i + 1;
                return jArr[i];
            }
        };
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag, java.lang.Iterable
    /* renamed from: spliterator */
    public Spliterator<Long> spliterator2() {
        return Arrays.spliterator(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag
    @NotNull
    public LongStream stream() {
        return Arrays.stream(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag
    public void forEachLong(@NotNull final LongConsumer action) {
        int length = this.value.length;
        for (int i = 0; i < length; i++) {
            action.accept(this.value[i]);
        }
    }

    public static long[] value(final LongArrayBinaryTag tag) {
        return tag instanceof LongArrayBinaryTagImpl ? ((LongArrayBinaryTagImpl) tag).value : tag.value();
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LongArrayBinaryTagImpl that = (LongArrayBinaryTagImpl) other;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m82of("value", this.value));
    }
}
