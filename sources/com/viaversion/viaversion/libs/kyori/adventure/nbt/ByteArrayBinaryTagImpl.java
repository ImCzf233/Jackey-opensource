package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text = "\"byte[\" + this.value.length + \"]\"", childrenArray = "this.value", hasChildren = "this.value.length > 0")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ByteArrayBinaryTagImpl.class */
public final class ByteArrayBinaryTagImpl extends ArrayBinaryTagImpl implements ByteArrayBinaryTag {
    final byte[] value;

    public ByteArrayBinaryTagImpl(final byte[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag
    public byte[] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag
    public int size() {
        return this.value.length;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag
    public byte get(final int index) {
        checkIndex(index, this.value.length);
        return this.value[index];
    }

    public static byte[] value(final ByteArrayBinaryTag tag) {
        return tag instanceof ByteArrayBinaryTagImpl ? ((ByteArrayBinaryTagImpl) tag).value : tag.value();
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ByteArrayBinaryTagImpl that = (ByteArrayBinaryTagImpl) other;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m87of("value", this.value));
    }

    @Override // java.lang.Iterable
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTagImpl.1
            private int index;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < ByteArrayBinaryTagImpl.this.value.length - 1;
            }

            @Override // java.util.Iterator
            public Byte next() {
                byte[] bArr = ByteArrayBinaryTagImpl.this.value;
                int i = this.index;
                this.index = i + 1;
                return Byte.valueOf(bArr[i]);
            }
        };
    }
}
