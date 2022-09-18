package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/IntBinaryTag.class */
public interface IntBinaryTag extends NumberBinaryTag {
    int value();

    @NotNull
    /* renamed from: of */
    static IntBinaryTag m132of(final int value) {
        return new IntBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<IntBinaryTag> type() {
        return BinaryTagTypes.INT;
    }
}
