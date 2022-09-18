package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/FloatBinaryTag.class */
public interface FloatBinaryTag extends NumberBinaryTag {
    float value();

    @NotNull
    /* renamed from: of */
    static FloatBinaryTag m134of(final float value) {
        return new FloatBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<FloatBinaryTag> type() {
        return BinaryTagTypes.FLOAT;
    }
}
