package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ByteBinaryTag.class */
public interface ByteBinaryTag extends NumberBinaryTag {
    public static final ByteBinaryTag ZERO = new ByteBinaryTagImpl((byte) 0);
    public static final ByteBinaryTag ONE = new ByteBinaryTagImpl((byte) 1);

    byte value();

    @NotNull
    /* renamed from: of */
    static ByteBinaryTag m136of(final byte value) {
        if (value == 0) {
            return ZERO;
        }
        if (value == 1) {
            return ONE;
        }
        return new ByteBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<ByteBinaryTag> type() {
        return BinaryTagTypes.BYTE;
    }
}
