package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/StringBinaryTag.class */
public interface StringBinaryTag extends BinaryTag {
    @NotNull
    String value();

    @NotNull
    /* renamed from: of */
    static StringBinaryTag m127of(@NotNull final String value) {
        return new StringBinaryTagImpl(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<StringBinaryTag> type() {
        return BinaryTagTypes.STRING;
    }
}
