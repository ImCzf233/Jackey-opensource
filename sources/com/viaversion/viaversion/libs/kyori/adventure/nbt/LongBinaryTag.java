package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/LongBinaryTag.class */
public interface LongBinaryTag extends NumberBinaryTag {
    long value();

    @NotNull
    /* renamed from: of */
    static LongBinaryTag m129of(final long value) {
        return new LongBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<LongBinaryTag> type() {
        return BinaryTagTypes.LONG;
    }
}
