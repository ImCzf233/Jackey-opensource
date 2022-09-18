package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/EndBinaryTag.class */
public interface EndBinaryTag extends BinaryTag {
    @NotNull
    static EndBinaryTag get() {
        return EndBinaryTagImpl.INSTANCE;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<EndBinaryTag> type() {
        return BinaryTagTypes.END;
    }
}
