package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ByteArrayBinaryTag.class */
public interface ByteArrayBinaryTag extends ArrayBinaryTag, Iterable<Byte> {
    byte[] value();

    int size();

    byte get(final int index);

    @NotNull
    /* renamed from: of */
    static ByteArrayBinaryTag m137of(final byte... value) {
        return new ByteArrayBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<ByteArrayBinaryTag> type() {
        return BinaryTagTypes.BYTE_ARRAY;
    }
}
