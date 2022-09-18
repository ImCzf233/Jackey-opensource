package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ShortBinaryTag.class */
public interface ShortBinaryTag extends NumberBinaryTag {
    short value();

    @NotNull
    /* renamed from: of */
    static ShortBinaryTag m128of(final short value) {
        return new ShortBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<ShortBinaryTag> type() {
        return BinaryTagTypes.SHORT;
    }
}
