package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/NumberBinaryTag.class */
public interface NumberBinaryTag extends BinaryTag {
    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    BinaryTagType<? extends NumberBinaryTag> type();

    byte byteValue();

    double doubleValue();

    float floatValue();

    int intValue();

    long longValue();

    short shortValue();
}
