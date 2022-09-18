package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/DoubleBinaryTag.class */
public interface DoubleBinaryTag extends NumberBinaryTag {
    double value();

    @NotNull
    /* renamed from: of */
    static DoubleBinaryTag m135of(final double value) {
        return new DoubleBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<DoubleBinaryTag> type() {
        return BinaryTagTypes.DOUBLE;
    }
}
