package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text = "\"int[\" + this.value.length + \"]\"", childrenArray = "this.value", hasChildren = "this.value.length > 0")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTagImpl.class */
public final class IntArrayBinaryTagImpl extends ArrayBinaryTagImpl implements IntArrayBinaryTag {
    final int[] value;

    public IntArrayBinaryTagImpl(final int... value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag
    public int[] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag
    public int size() {
        return this.value.length;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag
    public int get(final int index) {
        checkIndex(index, this.value.length);
        return this.value[index];
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag, java.lang.Iterable
    /* renamed from: iterator */
    public Iterator<Integer> iterator2() {
        return new PrimitiveIterator.OfInt() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTagImpl.1
            private int index;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < IntArrayBinaryTagImpl.this.value.length - 1;
            }

            @Override // java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayBinaryTagImpl.this.value;
                int i = this.index;
                this.index = i + 1;
                return iArr[i];
            }
        };
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag, java.lang.Iterable
    /* renamed from: spliterator */
    public Spliterator<Integer> spliterator2() {
        return Arrays.spliterator(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag
    @NotNull
    public IntStream stream() {
        return Arrays.stream(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag
    public void forEachInt(@NotNull final IntConsumer action) {
        int length = this.value.length;
        for (int i = 0; i < length; i++) {
            action.accept(this.value[i]);
        }
    }

    public static int[] value(final IntArrayBinaryTag tag) {
        return tag instanceof IntArrayBinaryTagImpl ? ((IntArrayBinaryTagImpl) tag).value : tag.value();
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        IntArrayBinaryTagImpl that = (IntArrayBinaryTagImpl) other;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m83of("value", this.value));
    }
}
